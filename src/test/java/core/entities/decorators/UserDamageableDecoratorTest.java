package core.entities.decorators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.entities.BaseEntity;
import core.entities.IEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserDamageableDecoratorTest {

    @Test
    void testGetUserDamageable() {
        IEntity baseEntity = Mockito.mock(IEntity.class);

        IEntity entity = new UserDamageableDecorator(baseEntity);
        var result = entity.getUserDamageable();

        Assertions.assertThat(result).isTrue();
        Mockito.verifyNoInteractions(baseEntity);
    }
}