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
import core.entities.LevelContactListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractLevel {
    private TiledMap map;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private World world;
    private EntityManager entityManager;

    private Player player;
    private long beginTime;

    private final String mapName;

    private LevelContactListener contactListener;

    private boolean gameEnded;

    private Map<Integer, ButtonAction> buttonActions;

    public AbstractLevel(String mapName) {
        this.mapName = mapName;
    }

    public void create() {
        world = new World(Constants.Physics.Gravity, true);
        map = new TmxMapLoader().load(mapName);
        Vector2 entitySize = new Vector2(((TiledMapTileLayer)map.getLayers().get(Constants.LayerNames.Tiles)).getTileHeight(),
                ((TiledMapTileLayer)map.getLayers().get(Constants.LayerNames.Tiles)).getTileWidth());

        loadMap();
        contactListener = new LevelContactListener();
        contactListener.setPlayerDead(true); // TODO: change this !!!

        Vector2 playerBeginPosition = ((RectangleMapObject)map.getLayers().get("player").getObjects().get(0)).getRectangle().getPosition(new Vector2());
        player = new Player(new Sprite(new Texture("player/player.png")), world, contactListener, entitySize, playerBeginPosition);
        List<BodyEntity> entities = new ArrayList<>();
        entities.add(player);
        loadEntities(entitySize, entities);

        world.setContactListener(contactListener);
        Gdx.input.setInputProcessor(player);

        entityManager = new BasicEntityManager(contactListener);
        entityManager.loadEntities(entities);
        entityManager.saveState();

        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        gameEnded = false;
        beginTime = TimeUtils.millis();
    }

    public void step() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        if (contactListener.isPlayerDead()) {
            contactListener.setPlayerDead(false);
            entityManager.recoverState();
        }

        if (contactListener.playerLadderEvent) {
            contactListener.playerLadderEvent = false;
            if (contactListener.playerLadderContact) {
                player.ladderContactBegin();
            } else {
                player.ladderContactEnd();
            }
        }
        if (contactListener.revreseGravity) {
            contactListener.revreseGravity = false;
            player.reverseGravity();
        }
        if (contactListener.button != 0) {
            ButtonAction buttonAction = buttonActions.get(contactListener.button);
            contactListener.button = 0;
            boolean ifDelete = buttonAction.buttonAction();
            if (ifDelete) {
                entityManager.remove((BodyEntity)buttonAction);
            }
        }

        entityManager.update();

        camera.position.set(player.getPosition(), 0);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        Batch batch = renderer.getBatch();
        batch.begin();
        entityManager.render(batch);
        batch.end();

        if (contactListener.isGameEnded()) {
            gameEnded = true;
            long timePassed = TimeUtils.timeSinceMillis(beginTime);
        }
        if (contactListener.isCheckpointReached()) {
            entityManager.saveState();
            contactListener.setCheckpointReached(false);
        }
    }

    public long getTimePassed() {
        return TimeUtils.timeSinceMillis(beginTime);
    }

    public void dispose() {
        // TODO: this sequence breaks game, free resources properly
        // renderer.dispose();
        // map.dispose();
        // world.dispose();
    }

    private void loadEntities(Vector2 baseSize, List<BodyEntity> entities) {
        EntityFactory factory = new EntityFactory(baseSize, world);
        for (MapObject obj : map.getLayers().get("entities").getObjects()) {
            entities.add(factory.getAbstractEntity(obj));
        }
        for (MapObject obj : map.getLayers().get("bodyEntities").getObjects()) {
            entities.add(factory.getBodyEntity(obj));
        }
        this.buttonActions = factory.getButtonsMap();
    }

    public void loadMap() {
        for (MapLayer layer : map.getLayers()) {
            String layerName = layer.getName();
            if (! (layerName.equals(Constants.LayerNames.Collision) || layerName.equals(Constants.LayerNames.Deadly) || layerName.equals(Constants.LayerNames.CollisionNoJump))) {//TODO: change this to constant list
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
