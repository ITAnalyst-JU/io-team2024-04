package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LevelSupplementaryObjectsFactoryTest {
    @Test
    void testGetCamera() {
        ILevelSupplementaryObjectsFactory factory = new LevelSupplementaryObjectsFactory();
        OrthographicCamera camera = factory.getCamera();

        Assertions.assertThat(camera).isNotNull();
    }
}