package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import core.levels.AbstractLevel;
import core.levels.LevelAbstractFactory;
import core.orchestrator.SupremeOrchestrator;

public class MainScreen extends AbstractScreen {

    private AbstractLevel level;
    // TODO: delegate level abstract factory higher
    private final LevelAbstractFactory levelAbstractFactory;

    public MainScreen(SupremeOrchestrator supremeOrchestrator) {
        super(supremeOrchestrator);
        levelAbstractFactory = new LevelAbstractFactory();

    }

    @Override
    public void show() {
        if (level == null) {
            level = levelAbstractFactory.createLevel(1);
        }
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.getCamera().position.set(
            level.getPlayer().getX() + level.getPlayer().getWidth()/2,
            level.getPlayer().getY() + level.getPlayer().getHeight()/2, 0);
        level.getCamera().update();

        level.getRenderer().setView(level.getCamera());
        level.getRenderer().render();

        level.getRenderer().getBatch().begin();
        level.getPlayer().draw(level.getRenderer().getBatch());
        level.getRenderer().getBatch().end();

        if (level.getPlayer().ifLevelFinished()) {
            long timePassed = TimeUtils.timeSinceMillis(level.getBeginTime());
            //do something, like showing time. stopping game etc.
            notifyOrchestator(ScreenState.MENU);

        }
    }

    @Override
    public void resize(int width, int height) {
        level.getCamera().viewportWidth = (float) width / 3;
        level.getCamera().viewportHeight = (float) height / 3;
    }

    @Override
    public void dispose() {
        level.getMap().dispose();
        level.getRenderer().dispose();
    }
}
