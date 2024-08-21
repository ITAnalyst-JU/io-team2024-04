package core.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import core.entities.*;
import core.utilities.Constants;
import core.utilities.WorldContactListener;
import core.views.ScreenEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AbstractLevel {
    private TiledMap map;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private World world;

    private Player player;
    private long beginTime;

    private final Vector2 playerBeginPosition;
    private final String mapName;

    private WorldContactListener contactListener;

    private List<AbstractEntity> entities;

    private boolean gameEnded;

    public AbstractLevel(Vector2 playerBeginPosition, String mapName) {
        this.playerBeginPosition = playerBeginPosition; // TODO: encode in map, like other entities
        this.mapName = mapName;
    }

    public void create() {
        world = new World(Constants.Physics.Gravity, true);
        map = new TmxMapLoader().load(mapName);
        Vector2 entitySize = new Vector2(((TiledMapTileLayer)map.getLayers().get(Constants.LayerNames.Tiles)).getTileHeight(),
                ((TiledMapTileLayer)map.getLayers().get(Constants.LayerNames.Tiles)).getTileWidth());

        player = new Player(new Sprite(new Texture("player/player.png")), entitySize, world);
        entities = new ArrayList<>();
        entities.add(player);
        loadEntities(entitySize);
        loadMap();

        contactListener = new WorldContactListener();
        contactListener.setPlayerDead(true);
        world.setContactListener(contactListener); // instead of setting position, and because dieing works exactly like spawning (and always should)
        Gdx.input.setInputProcessor(player);

        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        gameEnded = false;
        beginTime = TimeUtils.millis();
    }

    public void step() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        if (contactListener.isPlayerDead()) {
            contactListener.setPlayerDead(false);
            player.setPosition(playerBeginPosition, false);
        }
        for (AbstractEntity entity : entities) {
            entity.update();
        }

        camera.position.set(player.getPosition(), 0);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        Batch batch = renderer.getBatch();
        batch.begin();
        for(AbstractEntity entity : entities) {
            entity.draw(batch);
        }
        entities.forEach(e -> e.draw(renderer.getBatch()));
        batch.end();

        if (contactListener.isGameEnded()) {
            gameEnded = true;
            long timePassed = TimeUtils.timeSinceMillis(beginTime);
        }
    }

    public void dispose() {
        // TODO: this sequence breaks game, free resources properly
        // renderer.dispose();
        // map.dispose();
        // world.dispose();
    }

    private void loadEntities(Vector2 entitySize) {
        for (MapObject obj : map.getLayers().get("Entities Layer").getObjects()){
            if(obj.getProperties().containsKey("enemy")) {
                if(obj.getProperties().get("enemy").equals("moving")) {
                    MovingEnemy enemy = new MovingEnemy(new Sprite(new Texture("entities/enemy.png")), entitySize, world);
                    enemy.setPosition(new Vector2((float)obj.getProperties().get("x")/ Constants.Physics.Scale, (float)obj.getProperties().get("y")/ Constants.Physics.Scale), false);
                    enemy.setMovementBounds((float)obj.getProperties().get("minX")/ Constants.Physics.Scale, (float)obj.getProperties().get("maxX")/ Constants.Physics.Scale);
                    entities.add(enemy);
                }
                else if(obj.getProperties().get("enemy").equals("basic")){
                    BasicEnemy enemy = new BasicEnemy(new Sprite(new Texture("entities/enemy.png")), entitySize, world);
                    enemy.setPosition(new Vector2((float)obj.getProperties().get("x")/ Constants.Physics.Scale, (float)obj.getProperties().get("y")/ Constants.Physics.Scale), false);
                    entities.add(enemy);
                }
            }
            else if(obj.getProperties().containsKey("platform")) {
                if(obj.getProperties().get("platform").equals("moving")) {
                    MovingPlatform.MovementDirection direction;
                    if(obj.getProperties().get("direction").equals("static")) {
                        direction = MovingPlatform.MovementDirection.STATIC;
                    }
                    else if(obj.getProperties().get("direction").equals("horizontal")) {
                        direction = MovingPlatform.MovementDirection.HORIZONTAL;
                    }
                    else if(obj.getProperties().get("direction").equals("vertical")) {
                        direction = MovingPlatform.MovementDirection.VERTICAL;
                    }
                    else {
                        throw new RuntimeException();
                    }

                    MovingPlatform platform = new MovingPlatform(new Sprite(new Texture("entities/platform.png")), entitySize, world);
                    platform.setPosition(new Vector2((float)obj.getProperties().get("x")/ Constants.Physics.Scale, (float)obj.getProperties().get("y")/ Constants.Physics.Scale), false);
                    if(direction == MovingPlatform.MovementDirection.HORIZONTAL) {
                        platform.setMovementBounds((float)obj.getProperties().get("minX")/ Constants.Physics.Scale, (float)obj.getProperties().get("maxX")/ Constants.Physics.Scale);
                    }
                    else if(direction == MovingPlatform.MovementDirection.VERTICAL) {
                        // For reasons uncomprehensible to human mind, Tiled Y axis is inverted
                        // TODO: Replace magic numbers with map values
                        platform.setMovementBounds((960 - (float)obj.getProperties().get("maxY"))/ Constants.Physics.Scale, (960 - (float)obj.getProperties().get("minY"))/ Constants.Physics.Scale);
                    }
                    platform.direction = direction;
                    entities.add(platform);
                }
            }
        }
    }

    public void loadMap() {

        for (MapLayer layer : map.getLayers()) {
            String layerName = layer.getName();
            if (layerName.equals(Constants.LayerNames.Tiles) || layerName.equals(Constants.LayerNames.Entities)) {//TODO: change this to a constant
                continue;
            }
            for (MapObject object : layer.getObjects()) {
                if (object instanceof RectangleMapObject rectangleObject) {
                    Rectangle rectangle = rectangleObject.getRectangle();
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set(rectangle.getX() / Constants.Physics.Scale + rectangle.getWidth() / Constants.Physics.Scale / 2f, rectangle.getY() / Constants.Physics.Scale + rectangle.getHeight() / Constants.Physics.Scale / 2f);
                    bodyDef.fixedRotation = true;
                    Body body = world.createBody(bodyDef);
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.setAsBox(rectangle.getWidth() / Constants.Physics.Scale / 2f, rectangle.getHeight() / Constants.Physics.Scale / 2f);
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = polygonShape;
                    fixtureDef.density = 0f;
                    fixtureDef.friction = 0f;
                    Fixture fixture = body.createFixture(fixtureDef);

                    fixture.setUserData(layerName);
                    polygonShape.dispose();
                }
            }
        }
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void resize(int i, int i1) {
        camera.viewportWidth = (float)i/3;
        camera.viewportHeight = (float)i1/3;
    }
}
