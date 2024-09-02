package core.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import core.assets.AssetManagerFactory;
import core.assets.IAssetManagerGetter;
import core.entities.EntityFactory;
import core.entities.IEntity;
import core.general.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LevelFactoryTest {

    // There are issues with file loading.
    // Tests have to emulate a loaded map, so it's not too deep, because it would take ages.

    private AssetManagerFactory assetManagerFactory;
    private IAssetManagerGetter assetManager;
    private TiledMap map;
    private Texture texture;
    private MapLayers mapLayers;
    private TiledMapTileLayer tileLayer;
    private MapLayer playerLayer;
    private MapObjects playerLayerMapObjects;
    private RectangleMapObject playerObject;
    private Rectangle playerRectangle;
    private MapLayer entitiesLayer;
    private MapLayer bodyEntitiesLayer;
    private MapObjects entitiesLayerObjects;
    private MapObjects bodyEntitiesLayerObjects;
    private MapLayer collisionLayer;
    private MapObjects collisionLayerObjects;

    private ILevelSupplementaryObjectsFactory supplementaryObjectsFactory;
    private EntityFactory entityFactory;
    private OrthogonalTiledMapRenderer renderer;


    @BeforeEach
    void setUp() {
        assetManagerFactory = Mockito.mock(AssetManagerFactory.class);
        assetManager = Mockito.mock(IAssetManagerGetter.class);
        map = Mockito.mock(TiledMap.class);
        texture = Mockito.mock(Texture.class);
        mapLayers = new MapLayers();
        tileLayer = Mockito.mock(TiledMapTileLayer.class);
        playerLayer = Mockito.mock(MapLayer.class);
        playerLayerMapObjects = Mockito.mock(MapObjects.class);
        playerObject = Mockito.mock(RectangleMapObject.class);
        playerRectangle = Mockito.mock(Rectangle.class);
        entitiesLayer = Mockito.mock(MapLayer.class);
        bodyEntitiesLayer = Mockito.mock(MapLayer.class);
        entitiesLayerObjects = new MapObjects();
        bodyEntitiesLayerObjects = new MapObjects();
        collisionLayer = Mockito.mock(MapLayer.class);
        collisionLayerObjects = new MapObjects();
        renderer = Mockito.mock(OrthogonalTiledMapRenderer.class);

        Mockito.when(assetManagerFactory.getAssetManagerGetter()).thenReturn(assetManager);
        Mockito.when(assetManager.getMap(Mockito.anyString())).thenReturn(map);
        Mockito.when(assetManager.getTexture(Mockito.anyString())).thenReturn(texture);
        Mockito.when(map.getLayers()).thenReturn(mapLayers);
        Mockito.when(tileLayer.getTileHeight()).thenReturn(10);
        Mockito.when(tileLayer.getTileWidth()).thenReturn(10);
        Mockito.when(tileLayer.getName()).thenReturn(Constants.LayerNames.Tiles);
        Mockito.when(playerLayer.getName()).thenReturn("player");
        Mockito.when(playerLayer.getObjects()).thenReturn(playerLayerMapObjects);
        Mockito.when(playerLayerMapObjects.get(0)).thenReturn(playerObject);
        Mockito.when(playerObject.getRectangle()).thenReturn(playerRectangle);
        Mockito.when(playerRectangle.getPosition(Mockito.any())).thenReturn(new Vector2(0, 0));
        Mockito.when(entitiesLayer.getName()).thenReturn(Constants.LayerNames.Entities);
        Mockito.when(bodyEntitiesLayer.getName()).thenReturn(Constants.LayerNames.BodyEntities);
        Mockito.when(entitiesLayer.getObjects()).thenReturn(entitiesLayerObjects);
        Mockito.when(bodyEntitiesLayer.getObjects()).thenReturn(bodyEntitiesLayerObjects);
        Mockito.when(collisionLayer.getName()).thenReturn(Constants.LayerNames.Collision);
        Mockito.when(collisionLayer.getObjects()).thenReturn(collisionLayerObjects);

        mapLayers.add(tileLayer);
        mapLayers.add(playerLayer);
        mapLayers.add(entitiesLayer);
        mapLayers.add(bodyEntitiesLayer);
        mapLayers.add(collisionLayer);

        com.badlogic.gdx.Gdx.input = Mockito.mock(com.badlogic.gdx.Input.class);
        supplementaryObjectsFactory = Mockito.mock(ILevelSupplementaryObjectsFactory.class);
        entityFactory = Mockito.mock(EntityFactory.class);
        Mockito.when(supplementaryObjectsFactory.getEntityFactory(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(entityFactory);
        Mockito.when(supplementaryObjectsFactory.getRenderer(Mockito.any())).thenReturn(renderer);
        Mockito.when(entityFactory.getEntity(Mockito.any(), Mockito.anyBoolean())).thenReturn(Mockito.mock(IEntity.class));

        entitiesLayerObjects.add(Mockito.mock(MapObject.class));
        bodyEntitiesLayerObjects.add(Mockito.mock(MapObject.class));
    }

    @Test
    void testSimple() {
        LevelFactory levelFactory = new LevelFactory(supplementaryObjectsFactory);
        levelFactory.createLevel(LevelEnum.LEVEL_1, assetManagerFactory);
    }

    @Test
    void testSimpleWithEntities() {
        entitiesLayerObjects.add(Mockito.mock(MapObject.class));
        bodyEntitiesLayerObjects.add(Mockito.mock(MapObject.class));

        LevelFactory levelFactory = new LevelFactory(supplementaryObjectsFactory);
        levelFactory.createLevel(LevelEnum.LEVEL_1, assetManagerFactory);
    }

    @Test
    void testSimpleWithCollisionObjects() {
        RectangleMapObject rectangleMapObject = Mockito.mock(RectangleMapObject.class);
        Rectangle rectangle = Mockito.mock(Rectangle.class);
        Mockito.when(rectangleMapObject.getRectangle()).thenReturn(rectangle);

        collisionLayerObjects.add(rectangleMapObject);
        collisionLayerObjects.add(Mockito.mock(MapObject.class));

        LevelFactory levelFactory = new LevelFactory(supplementaryObjectsFactory);
        levelFactory.createLevel(LevelEnum.LEVEL_1, assetManagerFactory);
    }

    @Test
    void testSimpleWithDeadlyObjects() {
        Mockito.when(collisionLayer.getName()).thenReturn(Constants.LayerNames.Deadly);
        RectangleMapObject rectangleMapObject = Mockito.mock(RectangleMapObject.class);
        Rectangle rectangle = Mockito.mock(Rectangle.class);
        Mockito.when(rectangleMapObject.getRectangle()).thenReturn(rectangle);

        collisionLayerObjects.add(rectangleMapObject);
        collisionLayerObjects.add(Mockito.mock(MapObject.class));

        LevelFactory levelFactory = new LevelFactory(supplementaryObjectsFactory);
        levelFactory.createLevel(LevelEnum.LEVEL_1, assetManagerFactory);
    }

    @Test
    void testSimpleWithNoJumpObjects() {
        Mockito.when(collisionLayer.getName()).thenReturn(Constants.LayerNames.CollisionNoJump);
        RectangleMapObject rectangleMapObject = Mockito.mock(RectangleMapObject.class);
        Rectangle rectangle = Mockito.mock(Rectangle.class);
        Mockito.when(rectangleMapObject.getRectangle()).thenReturn(rectangle);

        collisionLayerObjects.add(rectangleMapObject);
        collisionLayerObjects.add(Mockito.mock(MapObject.class));

        LevelFactory levelFactory = new LevelFactory(supplementaryObjectsFactory);
        levelFactory.createLevel(LevelEnum.LEVEL_1, assetManagerFactory);
    }

    @Test
    void testGetSavedLevel() {
        LevelFactory levelFactory = new LevelFactory(supplementaryObjectsFactory);
        var level = levelFactory.createLevel(LevelEnum.LEVEL_1, assetManagerFactory);
        var gottenLevel = levelFactory.getSavedLevel();

        Assertions.assertThat(level).isSameAs(gottenLevel);
    }

    @Test
    void testGetSavedLevelClear() {
        LevelFactory levelFactory = new LevelFactory(supplementaryObjectsFactory);
        levelFactory.createLevel(LevelEnum.LEVEL_1, assetManagerFactory);

        levelFactory.clearSavedLevel();

        // We can only indirectly test if dispose() was called.
        // But, other tests confirm that LevelManager.dispose calls renderer.dispose().
        // Still not ideal, but in my opinion better than nothing.
        Mockito.verify(renderer).dispose();
    }

    @Test
    void testGetSavedLevelInitializedNull() {
        LevelFactory levelFactory = new LevelFactory(supplementaryObjectsFactory);
        var gottenLevel = levelFactory.getSavedLevel();

        Assertions.assertThat(gottenLevel).isNull();
    }

    @Test
    void testGetSavedLevelClearNull() {
        LevelFactory levelFactory = new LevelFactory(supplementaryObjectsFactory);
        levelFactory.createLevel(LevelEnum.LEVEL_1, assetManagerFactory);
        levelFactory.clearSavedLevel();
        var gottenLevel = levelFactory.getSavedLevel();

        Assertions.assertThat(gottenLevel).isNull();
    }
}