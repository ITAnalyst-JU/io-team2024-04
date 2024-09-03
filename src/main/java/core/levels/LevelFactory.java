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
import core.assets.IAssetManagerGetter;
import core.entities.*;
import core.entities.decorators.DecoratorFactory;
import core.general.Constants;
import core.general.UserInputController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelFactory implements ILevelFactory {
    private ILevelManager savedManager;
    ILevelSupplementaryObjectsFactory supplementaryObjectsFactory;

    public LevelFactory(ILevelSupplementaryObjectsFactory supplementaryObjectsFactory) {
        this.supplementaryObjectsFactory = supplementaryObjectsFactory;
    }

    @Override
    public ILevelManager createLevel(LevelEnum levelNumber, AssetManagerFactory assetManagerFactory) {
        savedManager = new TemporaryFactoryObject(assetManagerFactory, this.supplementaryObjectsFactory).createLevel(levelNumber.getLevelNumber(), assetManagerFactory);
        savedManager.create();
        return savedManager;
    }

    @Override
    public ILevelManager getSavedLevel() {
        return savedManager;
    }

    @Override
    public void clearSavedLevel() {
        if (savedManager != null) {
            savedManager.dispose();
        }
        savedManager = null;
    }

    private static class TemporaryFactoryObject {

        private final IAssetManagerGetter assetManager;
        private final ILevelSupplementaryObjectsFactory supplementaryObjectsFactory;

        private TiledMap map;
        private World world;
        Vector2 entitySize;
        private LevelContactListener contactListener;
        private Player player;
        private List<IEntity> entities;
        private IEntityManager IEntityManager;
        private OrthogonalTiledMapRenderer renderer;
        private OrthographicCamera camera;
        private Map<Integer, IEntity> buttonActions;
        private UserInputController inputProcessor;

        TemporaryFactoryObject(AssetManagerFactory assetManagerFactory, ILevelSupplementaryObjectsFactory supplementaryObjectsFactory) {
            assetManager = assetManagerFactory.getAssetManagerGetter();
            this.supplementaryObjectsFactory = supplementaryObjectsFactory;
        }

        private LevelManager createLevel(int levelNumber, AssetManagerFactory assetManagerFactory) { // 1-indexed
            String name;
            try {
                name = Constants.LevelNames.Prefix + Constants.LevelNames.List[levelNumber - 1];
            } catch (Exception e) {
                // This will never throw unless enum has levels without corresponding maps.
                throw new IllegalArgumentException("LevelFactory: Map corresponding to level not found.");
            }
            map = assetManager.getMap(name);

            world = new World(Constants.Physics.Gravity, true);
            entitySize = new Vector2(((TiledMapTileLayer) map.getLayers().get(Constants.LayerNames.Tiles)).getTileHeight(),
                    ((TiledMapTileLayer) map.getLayers().get(Constants.LayerNames.Tiles)).getTileWidth());

            loadMap();
            contactListener = new LevelContactListener();

            player = new EntityFactory(entitySize, world, assetManager, new DecoratorFactory()).getPlayer(map.getLayers().get("player").getObjects().get(0));
            entities = new ArrayList<>();
            entities.add(player);
            loadEntities();

            inputProcessor = new UserInputController();

            inputProcessor.addObserver(player);
            world.setContactListener(contactListener);
            Gdx.input.setInputProcessor(inputProcessor);

            IEntityManager = new EntityManager();
            IEntityManager.loadEntities(entities);
            IEntityManager.saveState();

            renderer = supplementaryObjectsFactory.getRenderer(map);
            camera = supplementaryObjectsFactory.getCamera();

            var backgroundMap = assetManager.getMap(Constants.LevelNames.Prefix + Constants.LevelNames.Background);
            var backgroundRenderer = supplementaryObjectsFactory.getRenderer(backgroundMap);
            var backgroundCamera = supplementaryObjectsFactory.getCamera();
            backgroundCamera.position.set(new Vector2(160, 120), 0);
            var backgroundViewport = supplementaryObjectsFactory.getViewport(320, 240, backgroundCamera);

            return new LevelManager(map, renderer, camera, world, IEntityManager, player, contactListener, buttonActions, inputProcessor, levelNumber, backgroundRenderer, backgroundViewport);
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
            EntityFactory factory = supplementaryObjectsFactory.getEntityFactory(entitySize, world, assetManager, new DecoratorFactory());
            for (MapObject obj : map.getLayers().get(Constants.LayerNames.Entities).getObjects()) {
                entities.add(factory.getEntity(obj, true));
            }
            for (MapObject obj : map.getLayers().get(Constants.LayerNames.BodyEntities).getObjects()) {
                entities.add(factory.getEntity(obj, false));
            }
            this.buttonActions = factory.getButtonsMap();
        }
    }
}
