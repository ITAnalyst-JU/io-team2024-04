package core.entities.decorators;

import core.entities.IEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DamageableDecoratorTest {

    @Test
    void testDamage() {
        IEntity baseEntity = Mockito.mock(IEntity.class);
        IEntity entity = new DamageableDecorator(baseEntity, 3);

        var result1 = entity.damage();
        var result2 = entity.damage();
        var result3 = entity.damage();

        Assertions.assertThat(result1).isFalse();
        Assertions.assertThat(result2).isFalse();
        Assertions.assertThat(result3).isTrue();
        Mockito.verifyNoInteractions(baseEntity);
    }

    @Test
    void testSaveState() {
        IEntity baseEntity = Mockito.mock(IEntity.class);
        IEntity entity = new DamageableDecorator(baseEntity, 2);

        var result1 = entity.damage();
        entity.saveState();
        var result2 = entity.damage();

        Assertions.assertThat(result1).isFalse();
        Assertions.assertThat(result2).isTrue();
        Mockito.verify(baseEntity).saveState();
        Mockito.verifyNoMoreInteractions(baseEntity);
    }

    @Test
    void testRecoverState() {
        IEntity baseEntity = Mockito.mock(IEntity.class);
        IEntity entity = new DamageableDecorator(baseEntity, 2);

        var result1 = entity.damage();
        entity.saveState();
        entity.damage();
        entity.recoverState();
        var result2 = entity.damage();

        Assertions.assertThat(result1).isFalse();
        Assertions.assertThat(result2).isTrue();
        Mockito.verify(baseEntity).saveState();
        Mockito.verify(baseEntity).recoverState();
        Mockito.verifyNoMoreInteractions(baseEntity);
    }
}