package core.entities.decorators;

import com.badlogic.gdx.math.Vector2;
import core.entities.IEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BaseEntityDecoratorTest {
    public static class TestClass extends BaseEntityDecorator {
        public TestClass(IEntity wrapped) {
            super(wrapped);
        }
    }

    IEntity wrapped;
    BaseEntityDecorator baseEntityDecorator;

    @BeforeEach
    public void setUp() {
        wrapped = Mockito.mock(IEntity.class);
        baseEntityDecorator = new TestClass(wrapped);
    }

    @Test
    public void testGetType() {
        String type = "testType";
        Mockito.when(wrapped.getType()).thenReturn(type);

        var res = baseEntityDecorator.getType();

        Mockito.verify(wrapped).getType();
        Mockito.verifyNoMoreInteractions(wrapped);
        Assertions.assertThat(res).isEqualTo(type);
    }

    @Test
    public void testGetPosition() {
        Vector2 position = new Vector2(1, 2);
        Mockito.when(wrapped.getPosition()).thenReturn(position);

        var res = baseEntityDecorator.getPosition();

        Mockito.verify(wrapped).getPosition();
        Mockito.verifyNoMoreInteractions(wrapped);
        Assertions.assertThat(res).isEqualTo(position);
    }

    @Test
    public void testSetPosition() {
        Vector2 position = new Vector2(1, 2);
        boolean preserveVelocity = true;

        baseEntityDecorator.setPosition(position, preserveVelocity);

        Mockito.verify(wrapped).setPosition(position, preserveVelocity);
        Mockito.verifyNoMoreInteractions(wrapped);
    }

    @Test
    public void testSaveState() {
        baseEntityDecorator.saveState();

        Mockito.verify(wrapped).saveState();
        Mockito.verifyNoMoreInteractions(wrapped);
    }

    @Test
    public void testRecoverState() {
        baseEntityDecorator.recoverState();

        Mockito.verify(wrapped).recoverState();
        Mockito.verifyNoMoreInteractions(wrapped);
    }

    @Test
    public void testHide() {
        baseEntityDecorator.hide();

        Mockito.verify(wrapped).hide();
        Mockito.verifyNoMoreInteractions(wrapped);
    }

    @Test
    public void testDispose() {
        baseEntityDecorator.dispose();

        Mockito.verify(wrapped).dispose();
        Mockito.verifyNoMoreInteractions(wrapped);
    }

    @Test
    public void testUpdate() {
        baseEntityDecorator.update();

        Mockito.verify(wrapped).update();
        Mockito.verifyNoMoreInteractions(wrapped);
    }

    @Test
    public void testDraw() {
        var batch = Mockito.mock(com.badlogic.gdx.graphics.g2d.Batch.class);

        baseEntityDecorator.draw(batch);

        Mockito.verify(wrapped).draw(batch);
        Mockito.verifyNoMoreInteractions(wrapped);
    }

    @Test
    public void testHashCode() {
        var res = baseEntityDecorator.hashCode();

        // Verification with mocks doesn't work, unfortunately.
        Assertions.assertThat(res).isEqualTo(wrapped.hashCode());
    }

    @Test
    public void testEquals() {
        //This also cannot be verified with mocks.
        var res = baseEntityDecorator.equals(wrapped);

        Assertions.assertThat(res).isTrue();
    }

    @Test
    public void testGetBody() {
        var body = Mockito.mock(com.badlogic.gdx.physics.box2d.Body.class);
        Mockito.when(wrapped.getBody()).thenReturn(body);

        var res = baseEntityDecorator.getBody();

        Mockito.verify(wrapped).getBody();
        Mockito.verifyNoMoreInteractions(wrapped);
        Assertions.assertThat(res).isEqualTo(body);
    }

    @Test
    public void testGetUserDamageable() {
        Mockito.when(wrapped.getUserDamageable()).thenReturn(true);
        var res = baseEntityDecorator.getUserDamageable();

        Mockito.verify(wrapped).getUserDamageable();
        Mockito.verifyNoMoreInteractions(wrapped);
        Assertions.assertThat(res).isTrue();
    }

    @Test
    public void testGetButtonNumber() {
        int buttonNumber = 44;
        Mockito.when(wrapped.getButtonNumber()).thenReturn(buttonNumber);

        var res = baseEntityDecorator.getButtonNumber();

        Mockito.verify(wrapped).getButtonNumber();
        Mockito.verifyNoMoreInteractions(wrapped);
        Assertions.assertThat(res).isEqualTo(buttonNumber);
    }

    @Test
    public void testDamage() {
        Mockito.when(wrapped.damage()).thenReturn(true);
        var res = baseEntityDecorator.damage();

        Mockito.verify(wrapped).damage();
        Mockito.verifyNoMoreInteractions(wrapped);
        Assertions.assertThat(res).isTrue();
    }

    @Test
    public void testButtonAction() {
        var baseInstance = Mockito.mock(IEntity.class);
        Mockito.when(wrapped.buttonAction(baseInstance)).thenReturn(true);

        var res = baseEntityDecorator.buttonAction(baseInstance);

        Mockito.verify(wrapped).buttonAction(baseInstance);
        Mockito.verifyNoMoreInteractions(wrapped);
        Assertions.assertThat(res).isTrue();
    }
}
