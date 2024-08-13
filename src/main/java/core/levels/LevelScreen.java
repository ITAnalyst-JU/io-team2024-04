package core.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;

import core.entities.AbstractEntity;
import core.entities.BasicEnemy;
import core.entities.MovingEnemy;
import core.entities.MovingPlatform;
import core.entities.MovingPlatform.MovementDirection;
import core.entities.Player;
import core.orchestrator.SupremeOrchestrator;
import core.utilities.Constants;
import core.utilities.WorldContactListener;
import core.views.AbstractScreen;
import core.views.ScreenState;

public class LevelScreen extends AbstractScreen implements LevelInterface {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private World world;

    private Player player;
    private long beginTime;

    private Vector2 playerBeginPosition;
    private String mapName;

    //TODO: Find better solution
    private List<AbstractEntity> entities;

    boolean isLevelFinished = false;
    boolean playerDied = false;

    public LevelScreen(SupremeOrchestrator orchestrator, Vector2 playerBeginPosition, String mapName) {
        super(orchestrator);
        this.playerBeginPosition = playerBeginPosition;
        this.mapName = mapName;
    }

    @Override
    public void show() {
        world = new World(Constants.Physics.Gravity, false);
        map = new TmxMapLoader().load(mapName);
        player = new Player(new Sprite(new Texture("player/player.png")),
                (TiledMapTileLayer)map.getLayers().get(Constants.LayerNames.Tiles), world);

        entities = new ArrayList<>();

        //TODO: Refactor this
        map.getLayers().get("Entities Layer").getObjects().forEach(obj -> {
            if(obj.getProperties().containsKey("enemy")) {
                if(obj.getProperties().get("enemy").equals("moving")) {
                    MovingEnemy enemy = new MovingEnemy(new Sprite(new Texture("entities/enemy.png")), (TiledMapTileLayer)map.getLayers().get(0));
                    enemy.setPosition((float)obj.getProperties().get("x"), (float)obj.getProperties().get("y"));
                    enemy.setMovementBounds((float)obj.getProperties().get("minX"), (float)obj.getProperties().get("maxX"));
                    entities.add(enemy);
                }
                else if(obj.getProperties().get("enemy").equals("basic")){
                    BasicEnemy enemy = new BasicEnemy(new Sprite(new Texture("entities/enemy.png")), (TiledMapTileLayer)map.getLayers().get(0));
                    enemy.setPosition((float)obj.getProperties().get("x"), (float)obj.getProperties().get("y"));
                    entities.add(enemy);
                }
            }
            else if(obj.getProperties().containsKey("platform")) {
                if(obj.getProperties().get("platform").equals("moving")) {
                    MovingPlatform.MovementDirection direction;
                    if(obj.getProperties().get("direction").equals("static")) {
                        direction = MovementDirection.Static;
                    }
                    else if(obj.getProperties().get("direction").equals("horizontal")) {
                        direction = MovementDirection.Horizontal;
                    }
                    else if(obj.getProperties().get("direction").equals("vertical")) {
                        direction = MovementDirection.Vertical;
                    }
                    else {
                        throw new RuntimeException();
                    }

                    MovingPlatform platform = new MovingPlatform(new Sprite(new Texture("entities/platform.png")), (TiledMapTileLayer)map.getLayers().get(0), direction);
                    platform.setPosition((float)obj.getProperties().get("x"), (float)obj.getProperties().get("y"));
                    if(direction == MovementDirection.Horizontal) {
                        platform.setMovementBounds((float)obj.getProperties().get("minX"), (float)obj.getProperties().get("maxX"));
                    }
                    else if(direction == MovementDirection.Vertical) {
                        // For reasons uncomprehensible to human mind, Tiled Y axis is inverted
                        // TODO: Replace magic numbers with map values
                        platform.setMovementBounds(960 - (float)obj.getProperties().get("maxY"), 960 - (float)obj.getProperties().get("minY"));
                    }
                    entities.add(platform);
                }
            }
        });
        

        setPlayerDied();

        this.loadMap(map);
        world.setContactListener(new WorldContactListener(this));

        renderer = new OrthogonalTiledMapRenderer(map);
        camera  = new OrthographicCamera();
        Gdx.input.setInputProcessor(player);
        beginTime = TimeUtils.millis();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        if (playerDied) {
            playerDied = false;
            player.setPosition(playerBeginPosition);
        }
        player.update();

        camera.position.set(player.update(), 0);
        camera.update();

        renderer.setView(camera);
        renderer.render();

        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        entities.forEach(e -> e.draw(renderer.getBatch()));
        renderer.getBatch().end();

        if (isLevelFinished) {
            long timePassed = TimeUtils.timeSinceMillis(beginTime);

            this.notifyOrchestator(ScreenState.MENU);
        }
    }

    public void loadMap(TiledMap map) {
        for (MapLayer layer : map.getLayers()) {
            String layerName = layer.getName();
            if (layerName.equals(Constants.LayerNames.Tiles) || layerName.equals("Entities Layer")) {//TODO: change this to a constant
                continue;
            }
            for (MapObject object : layer.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    RectangleMapObject rectangleObject = (RectangleMapObject) object;
                    Rectangle rectangle = rectangleObject.getRectangle();
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set(rectangle.getX() / Constants.Physics.Scale + rectangle.getWidth() / Constants.Physics.Scale / 2f, rectangle.getY() / Constants.Physics.Scale + rectangle.getHeight() / Constants.Physics.Scale / 2f);
                    bodyDef.fixedRotation = true;
                    Body body = world.createBody(bodyDef);
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.setAsBox(rectangle.getWidth() / Constants.Physics.Scale / 2f, rectangle.getHeight() / Constants.Physics.Scale / 2f);
                    Fixture fixture = body.createFixture(polygonShape, 0f);
                    fixture.setUserData(layerName);
                    polygonShape.dispose();
                }
            }
        }
    }

    @Override
    public void resize(int i, int i1) {
        camera.viewportWidth = (float)i/3;
        camera.viewportHeight = (float)i1/3;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        map.dispose();
        renderer.dispose();
    }

    @Override
    public void setLevelFinished() {
        this.isLevelFinished = true;
    }

    @Override
    public void setPlayerDied() {
        this.playerDied = true;
    }
}
