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

import java.util.ArrayList;
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

        loadMap();
        contactListener = new WorldContactListener();
        contactListener.setPlayerDead(true);

        player = new Player(new Sprite(new Texture("player/player.png")), entitySize, world, contactListener);
        entities = new ArrayList<>();
        entities.add(player);
        loadEntities(entitySize);

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

    private void loadEntities(Vector2 baseSize) {
        EntityFactory factory = new EntityFactory(baseSize, world);
        for (MapObject obj : map.getLayers().get("entities").getObjects()) {
            entities.add(factory.getEntity(obj));
        }
    }

    public void loadMap() {

        for (MapLayer layer : map.getLayers()) {
            String layerName = layer.getName();
            if (layerName.equals(Constants.LayerNames.Tiles) || layerName.equals(Constants.LayerNames.Entities) || layerName.equals("entities")) {//TODO: change this to constant list
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
