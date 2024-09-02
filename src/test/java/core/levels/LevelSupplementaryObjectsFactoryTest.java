package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.assets.IAssetManagerGetter;
import core.entities.EntityFactory;
import core.entities.decorators.DecoratorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LevelSupplementaryObjectsFactoryTest {
    @Test
    void testGetCamera() {
        ILevelSupplementaryObjectsFactory factory = new LevelSupplementaryObjectsFactory();
        OrthographicCamera camera = factory.getCamera();

        Assertions.assertThat(camera).isNotNull();
    }

    @Test
    void testGetEntityFactory() {
        ILevelSupplementaryObjectsFactory factory = new LevelSupplementaryObjectsFactory();
        Vector2 baseEntitySize = Mockito.mock(Vector2.class);
        World world = Mockito.mock(World.class);
        IAssetManagerGetter assetManager = Mockito.mock(IAssetManagerGetter.class);
        DecoratorFactory decoratorFactory = Mockito.mock(DecoratorFactory.class);
        EntityFactory entityFactory = factory.getEntityFactory(baseEntitySize, world, assetManager, decoratorFactory);

        Assertions.assertThat(entityFactory).isNotNull();
    }
}