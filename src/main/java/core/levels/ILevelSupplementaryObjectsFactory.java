package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.assets.IAssetManagerGetter;
import core.entities.EntityFactory;
import core.entities.decorators.DecoratorFactory;

public interface ILevelSupplementaryObjectsFactory {
    OrthogonalTiledMapRenderer getRenderer(TiledMap map);

    OrthographicCamera getCamera();

    EntityFactory getEntityFactory(Vector2 baseEntitySize, World world, IAssetManagerGetter assetManager, DecoratorFactory decoratorFactory);
}
