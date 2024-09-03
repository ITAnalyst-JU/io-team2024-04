package core.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.*;
import core.entities.*;
import core.general.UserInputController;

import java.util.List;
import java.util.Map;

public class LevelManager implements ILevelManager {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final World world;
    private final IEntityManager IEntityManager;
    private final Player player;
    private final LevelContactListener contactListener;
    private final Map<Integer, IEntity> buttonActions;
    private final UserInputController inputController;
    private final int levelNumber;
    private final OrthogonalTiledMapRenderer backgroundRenderer;
    private final Viewport backgroundViewport;

    private long totalTime = 0;
    private long beginTime;
    public boolean gameEnded = false;


    // Do we need to have so many things? Unfortunately it seems yes.
    public LevelManager(TiledMap map, OrthogonalTiledMapRenderer renderer, OrthographicCamera camera, World world, IEntityManager IEntityManager, Player player, LevelContactListener contactListener, Map<Integer, IEntity> buttonActions,
                        UserInputController inputController, int levelNumber, OrthogonalTiledMapRenderer backgroundRenderer, Viewport backgroundViewport) {
        this.map = map;
        this.renderer = renderer;
        this.camera = camera;
        this.world = world;
        this.IEntityManager = IEntityManager;
        this.player = player;
        this.contactListener = contactListener;
        this.buttonActions = buttonActions;
        this.inputController = inputController;
        this.levelNumber = levelNumber;
        this.backgroundViewport = backgroundViewport;
        this.backgroundRenderer = backgroundRenderer;
    }

    @Override
    public void create() {
        // Nothing to do. Factory does everything. Possibly remove?
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(inputController);
        player.resetControls();
        beginTime = TimeUtils.millis();
    }

    // Untestable. LibGDX only measures time when the game is running.
    @Override
    public void pause() {
        totalTime += TimeUtils.timeSinceMillis(beginTime);
    }

    @Override
    public void step() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        IEntityManager.update();


        backgroundRenderer.setView((OrthographicCamera) backgroundViewport.getCamera());
        backgroundRenderer.render();

        camera.position.set(player.getPosition(), 0);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        Batch batch = renderer.getBatch();
        batch.begin();
        IEntityManager.render(batch);
        batch.end();

        handleCollisionEvents();
    }

    private void handleCollisionEvents() {
        List<LevelContactListener.Event> events = contactListener.getEvents();
        for (var event : events) {
            switch (event.type) {
                case LevelContactListener.Event.Type.Finish:
                    pause();
                    gameEnded = true;
                    break;
                case LevelContactListener.Event.Type.Checkpoint:
                    IEntityManager.remove(event.object);
                    IEntityManager.saveState();
                    break;
                case LevelContactListener.Event.Type.Platform:
                    if (event.object.getUserDamageable()) {
                        boolean destroyed = event.object.damage();
                        if (destroyed) {
                            IEntityManager.remove(event.object);
                        }
                    }
                    break;
                case LevelContactListener.Event.Type.Death:
                    IEntityManager.recoverState();
                    break;
                case LevelContactListener.Event.Type.GravityReverse:
                    player.reverseGravity();
                    break;
                case LevelContactListener.Event.Type.Button:
                    int num = event.object.getButtonNumber();
                    IEntity buttonAction = buttonActions.get(num);
                    boolean destroyed = buttonAction.buttonAction(buttonAction);
                    if (destroyed) {
                        IEntityManager.remove(buttonAction);
                    }
                    IEntityManager.remove(event.object);
                    break;
                case LevelContactListener.Event.Type.CollisionJumpContactBegin:
                    player.jumpContactBegin();
                    break;
                case LevelContactListener.Event.Type.CollisionJumpContactEnd:
                    player.jumpContactEnd();
                    break;
                case LevelContactListener.Event.Type.LadderContactBegin:
                    player.ladderContactBegin();
                    break;
                case LevelContactListener.Event.Type.LadderContactEnd:
                    player.ladderContactEnd();
                    break;
                case LevelContactListener.Event.Type.Trampoline:
                    player.trampolineContact();
                    break;
            }
        }
        events.clear();
    }


    @Override
    public void dispose() {
        world.dispose();
        renderer.dispose();
        map.dispose();
    }


    @Override
    public void resize(int i, int i1) {
        camera.viewportWidth = (float)i/3;
        camera.viewportHeight = (float)i1/3;
        camera.update();

        backgroundViewport.update(i, i1);
    }

    @Override
    public long getTimePassed() {
        return totalTime;
    }

    @Override
    public boolean isGameEnded() {
        return gameEnded;
    }
    
    @Override
    public UserInputController getInputController() {
        return inputController;
    }

    @Override
    public int getLevelNumber() {
        return levelNumber;
    }
}
