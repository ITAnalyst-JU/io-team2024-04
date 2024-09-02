package core.entities.decorators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.entities.BaseEntity;
import core.entities.IEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ButtonDecoratorTest {

    @Test
    void testGetButtonNumber() {
        IEntity baseEntity = Mockito.mock(IEntity.class);
        int number = 18;

        IEntity entity = new ButtonDecorator(baseEntity, number);
        var result = entity.getButtonNumber();

        Assertions.assertThat(result).isEqualTo(number);
        Mockito.verifyNoInteractions(baseEntity);
    }
}