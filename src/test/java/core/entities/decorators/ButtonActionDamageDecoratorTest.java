package core.entities.decorators;

import core.entities.IEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ButtonActionDamageDecoratorTest {

    @Test
    void testButtonAction() {
        IEntity baseEntity = Mockito.mock(IEntity.class);
        IEntity entity = new ButtonActionDamageDecorator(baseEntity);
        IEntity other = Mockito.mock(IEntity.class);

        entity.buttonAction(other);

        Mockito.verify(other).damage();
        Mockito.verifyNoMoreInteractions(other);
        Mockito.verifyNoInteractions(baseEntity);
    }
}