package core.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.assets.AssetManagerFactory;
import core.entities.*;
import core.general.Constants;
import core.general.UserInputController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelFactory {
    private LevelManager savedManager;

    public LevelManager createLevel(LevelEnum levelNumber, AssetManagerFactory assetManagerFactory) {
        savedManager = new TemporaryFactoryObject().createLevel(levelNumber.getLevelNumber(), assetManagerFactory);
        savedManager.create();
        return savedManager;
    }

    public LevelManager getSavedLevel() {
        return savedManager;
    }

    public void clearSavedLevel() {
        if (savedManager != null) {
            savedManager.dispose();
        }
        savedManager = null;
    }

    private static class TemporaryFactoryObject {

        private TiledMap map;
        private World world;
        Vector2 entitySize;
        private LevelContactListener contactListener;
        private Player player;
        private List<IEntity> entities;
        private EntityManager entityManager;
        private OrthogonalTiledMapRenderer renderer;
        private OrthographicCamera camera;
        private Map<Integer, IEntity> buttonActions;
        private UserInputController inputProcessor;

        private LevelManager createLevel(int levelNumber, AssetManagerFactory assetManagerFactory) { // 1-indexed
            String name;
            try {
                name = Constants.LevelNames.Prefix + Constants.LevelNames.List[levelNumber - 1];
            } catch (Exception e) {
                throw new IllegalArgumentException("LevelFactory: Map corresponding to level not found.");
            }
            map = assetManagerFactory.getAssetManagerGetter().getMap(name); // TODO: move to a separate package

            world = new World(Constants.Physics.Gravity, true);
            entitySize = new Vector2(((TiledMapTileLayer) map.getLayers().get(Constants.LayerNames.Tiles)).getTileHeight(),
                    ((TiledMapTileLayer) map.getLayers().get(Constants.LayerNames.Tiles)).getTileWidth());

            loadMap();
            contactListener = new LevelContactListener();

            player = new EntityFactory(entitySize, world).getPlayer(map.getLayers().get("player").getObjects().get(0));
            entities = new ArrayList<>();
            entities.add(player);
            loadEntities();

            inputProcessor = new UserInputController();

            inputProcessor.addObserver(player);
            world.setContactListener(contactListener);
            Gdx.input.setInputProcessor(inputProcessor);

            entityManager = new BasicEntityManager();
            entityManager.loadEntities(entities);
            entityManager.saveState();

            renderer = new OrthogonalTiledMapRenderer(map);
            camera = new OrthographicCamera();

            return new LevelManager(map, renderer, camera, world, entityManager, player, contactListener, buttonActions, inputProcessor, levelNumber);

        }


        public void loadMap() {
            for (MapLayer layer : map.getLayers()) {
                String layerName = layer.getName();
                if (!(layerName.equals(Constants.LayerNames.Collision) || layerName.equals(Constants.LayerNames.Deadly) || layerName.equals(Constants.LayerNames.CollisionNoJump))) {//TODO: change this to constant list
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

        private void loadEntities() {
            EntityFactory factory = new EntityFactory(entitySize, world);
            for (MapObject obj : map.getLayers().get("entities").getObjects()) {
                entities.add(factory.getEntity(obj, true));
            }
            for (MapObject obj : map.getLayers().get("bodyEntities").getObjects()) {
                entities.add(factory.getEntity(obj, false));
            }
            this.buttonActions = factory.getButtonsMap();
        }
    }
}
