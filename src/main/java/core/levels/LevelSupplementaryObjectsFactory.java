package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.assets.IAssetManagerGetter;
import core.entities.EntityFactory;
import core.entities.decorators.DecoratorFactory;

// This class exists only for testing LevelFactory to be possible.
public class LevelSupplementaryObjectsFactory implements ILevelSupplementaryObjectsFactory {

    // Untestable
    @Override
    public OrthogonalTiledMapRenderer getRenderer(TiledMap map) {
        return new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public OrthographicCamera getCamera() {
        return new OrthographicCamera();
    }

    @Override
    public EntityFactory getEntityFactory(Vector2 baseEntitySize, World world, IAssetManagerGetter assetManager, DecoratorFactory decoratorFactory) {
        return new EntityFactory(baseEntitySize, world, assetManager, decoratorFactory);
    }
}
