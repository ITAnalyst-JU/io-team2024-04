package core.entities.decorators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import core.entities.IEntity;
import core.general.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MovingDecoratorTest {
    IEntity baseEntity;
    Vector2 movementBounds;
    IEntity entity;
    Body body;

    @BeforeEach
    public void setUp() {
        baseEntity = Mockito.mock(IEntity.class);
        movementBounds = new Vector2(-10, 10);
        body = Mockito.mock(Body.class);
        Mockito.when(baseEntity.getBody()).thenReturn(body);
    }

    @Test
    public void testConstructorHorizontal() {
        entity = new MovingDecorator(baseEntity, MovingDecorator.MovementDirection.HORIZONTAL, movementBounds);
        Mockito.verify(body).setType(BodyDef.BodyType.KinematicBody);
        Mockito.verify(body).setLinearVelocity(new Vector2(Constants.Physics.EntitiesMoveSpeed, 0));
    }

    @Test
    public void testConstructorVertical() {
        entity = new MovingDecorator(baseEntity, MovingDecorator.MovementDirection.VERTICAL, movementBounds);
        Mockito.verify(body).setType(BodyDef.BodyType.KinematicBody);
        Mockito.verify(body).setLinearVelocity(new Vector2(0, Constants.Physics.EntitiesMoveSpeed));
    }

    @Test
    public void testUpdateHorizontalSimple() {
        entity = new MovingDecorator(baseEntity, MovingDecorator.MovementDirection.HORIZONTAL, movementBounds);
        Mockito.clearInvocations(body, baseEntity);
        Mockito.when(body.getPosition()).thenReturn(new Vector2(0, 0));

        entity.update();

        Mockito.verify(baseEntity).getBody();
        Mockito.verify(baseEntity).update();
        Mockito.verify(body).getPosition();
        Mockito.verifyNoMoreInteractions(baseEntity, body);
    }

    @Test
    public void testUpdateVerticalSimple() {
        entity = new MovingDecorator(baseEntity, MovingDecorator.MovementDirection.VERTICAL, movementBounds);
        Mockito.clearInvocations(body, baseEntity);
        Mockito.when(body.getPosition()).thenReturn(new Vector2(0, 0));

        entity.update();

        Mockito.verify(baseEntity).getBody();
        Mockito.verify(baseEntity).update();
        Mockito.verify(body).getPosition();
        Mockito.verifyNoMoreInteractions(baseEntity, body);
    }

    @Test
    public void testUpdateHorizontalAdvanced() {
        entity = new MovingDecorator(baseEntity, MovingDecorator.MovementDirection.HORIZONTAL, movementBounds);
        Mockito.clearInvocations(body, baseEntity);

        Mockito.when(body.getPosition()).thenReturn(new Vector2(0, 0));
        entity.update();
        Mockito.clearInvocations(body, baseEntity);
        Mockito.when(body.getPosition()).thenReturn(new Vector2(11, 0));
        entity.update();

        Mockito.verify(baseEntity).getBody();
        Mockito.verify(baseEntity).update();
        Mockito.verify(body).getPosition();
        Mockito.verify(body).setLinearVelocity(new Vector2(-Constants.Physics.EntitiesMoveSpeed, 0));
        Mockito.verifyNoMoreInteractions(baseEntity, body);
    }

    @Test
    public void testUpdateVerticalAdvanced() {
        entity = new MovingDecorator(baseEntity, MovingDecorator.MovementDirection.VERTICAL, movementBounds);
        Mockito.clearInvocations(body, baseEntity);

        Mockito.when(body.getPosition()).thenReturn(new Vector2(0, 0));
        entity.update();
        Mockito.clearInvocations(body, baseEntity);
        Mockito.when(body.getPosition()).thenReturn(new Vector2(0, 11));
        entity.update();

        Mockito.verify(baseEntity).getBody();
        Mockito.verify(baseEntity).update();
        Mockito.verify(body).getPosition();
        Mockito.verify(body).setLinearVelocity(new Vector2(0, -Constants.Physics.EntitiesMoveSpeed));
        Mockito.verifyNoMoreInteractions(baseEntity, body);
    }


    @Test
    public void testUpdateHorizontalAdvanced2() {
        entity = new MovingDecorator(baseEntity, MovingDecorator.MovementDirection.HORIZONTAL, movementBounds);
        Mockito.clearInvocations(body, baseEntity);

        Mockito.when(body.getPosition()).thenReturn(new Vector2(0, 0));
        entity.update();
        Mockito.when(body.getPosition()).thenReturn(new Vector2(11, 0));
        entity.update();
        Mockito.clearInvocations(body, baseEntity);
        Mockito.when(body.getPosition()).thenReturn(new Vector2(-11, 0));
        entity.update();

        Mockito.verify(baseEntity).getBody();
        Mockito.verify(baseEntity).update();
        Mockito.verify(body).getPosition();
        Mockito.verify(body).setLinearVelocity(new Vector2(Constants.Physics.EntitiesMoveSpeed, 0));
        Mockito.verifyNoMoreInteractions(baseEntity, body);
    }

    @Test
    public void testUpdateVerticalAdvanced2() {
        entity = new MovingDecorator(baseEntity, MovingDecorator.MovementDirection.VERTICAL, movementBounds);
        Mockito.clearInvocations(body, baseEntity);

        Mockito.when(body.getPosition()).thenReturn(new Vector2(0, 0));
        entity.update();
        Mockito.when(body.getPosition()).thenReturn(new Vector2(0, 11));
        entity.update();
        Mockito.clearInvocations(body, baseEntity);
        Mockito.when(body.getPosition()).thenReturn(new Vector2(0, -11));
        entity.update();

        Mockito.verify(baseEntity).getBody();
        Mockito.verify(baseEntity).update();
        Mockito.verify(body).getPosition();
        Mockito.verify(body).setLinearVelocity(new Vector2(0, Constants.Physics.EntitiesMoveSpeed));
        Mockito.verifyNoMoreInteractions(baseEntity, body);
    }
}