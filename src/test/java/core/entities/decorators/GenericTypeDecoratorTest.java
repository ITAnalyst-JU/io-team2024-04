package core.entities.decorators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.entities.BaseEntity;
import core.entities.IEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GenericTypeDecoratorTest {

    @Test
    public void testGetType() {
        IEntity baseEntity = Mockito.mock(IEntity.class);
        String type = "testType";

        IEntity entity = new GenericTypeDecorator(baseEntity, type);
        var result = entity.getType();

        Assertions.assertThat(result).isEqualTo(type);
        Mockito.verifyNoInteractions(baseEntity);
    }
}
