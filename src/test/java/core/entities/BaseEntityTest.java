package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.general.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class BaseEntityTest {

    @Test
    public void testConstructorBody() {
        World world = Mockito.mock(World.class);
        Body body = Mockito.mock(Body.class);
        Mockito.when(world.createBody(Mockito.any(BodyDef.class))).thenReturn(body);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        Fixture fixture = Mockito.mock(Fixture.class);
        Mockito.when(body.createFixture(Mockito.any(FixtureDef.class))).thenReturn(fixture);

        BaseEntity baseEntity = new BaseEntity(world, size, position);
        ArgumentCaptor<BodyDef> captor = ArgumentCaptor.forClass(BodyDef.class);

        Mockito.verify(world).createBody(captor.capture());
        Assertions.assertThat(captor.getValue().type).isEqualTo(BodyDef.BodyType.StaticBody);
        Assertions.assertThat(captor.getValue().fixedRotation).isTrue();
        Assertions.assertThat(captor.getValue().position).isEqualTo(new Vector2(5 / Constants.Physics.Scale, 6 / Constants.Physics.Scale));
    }

    @Test
    public void testConstructorFixtureDef() {
        World world = Mockito.mock(World.class);
        Body body = Mockito.mock(Body.class);
        Mockito.when(world.createBody(Mockito.any(BodyDef.class))).thenReturn(body);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        Fixture fixture = Mockito.mock(Fixture.class);
        Mockito.when(body.createFixture(Mockito.any(FixtureDef.class))).thenReturn(fixture);

        BaseEntity baseEntity = new BaseEntity(world, size, position);
        ArgumentCaptor<FixtureDef> captor = ArgumentCaptor.forClass(FixtureDef.class);

        Mockito.verify(body).createFixture(captor.capture());
        Assertions.assertThat(captor.getValue().density).isEqualTo(1f);
        Assertions.assertThat(captor.getValue().friction).isEqualTo(Constants.Physics.Friction);
        Assertions.assertThat(captor.getValue().restitution).isEqualTo(0);
        Assertions.assertThat(captor.getValue().shape).isInstanceOf(PolygonShape.class);
        Assertions.assertThat(captor.getValue().shape).isNotNull();
    }

    @Test
    public void testConstructorFixture() {
        World world = Mockito.mock(World.class);
        Body body = Mockito.mock(Body.class);
        Mockito.when(world.createBody(Mockito.any(BodyDef.class))).thenReturn(body);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        Fixture fixture = Mockito.mock(Fixture.class);
        Mockito.when(body.createFixture(Mockito.any(FixtureDef.class))).thenReturn(fixture);

        BaseEntity baseEntity = new BaseEntity(world, size, position);
        Mockito.verify(fixture).setUserData(baseEntity);
    }

    @Test
    public void testGetType() {
        World world = Mockito.mock(World.class);
        Body body = Mockito.mock(Body.class);
        Mockito.when(world.createBody(Mockito.any(BodyDef.class))).thenReturn(body);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        Fixture fixture = Mockito.mock(Fixture.class);
        Mockito.when(body.createFixture(Mockito.any(FixtureDef.class))).thenReturn(fixture);

        BaseEntity baseEntity = new BaseEntity(world, size, position);
        var res = baseEntity.getType();
        Assertions.assertThat(res).isEqualTo("default");
    }

    @Test
    public void testGetPosition() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        var res = baseEntity.getPosition();

        Assertions.assertThat(res).isEqualTo(new Vector2(5, 6));
    }

    @Test
    public void testSetPosition() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        baseEntity.setPosition(new Vector2(21, 15), false);
        var res = baseEntity.getPosition();

        Assertions.assertThat(res).isEqualTo(new Vector2(21, 15));
    }

    @Test
    public void testSetPositionPreserveVelocity() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        baseEntity.body.setType(BodyDef.BodyType.KinematicBody);
        baseEntity.body.setLinearVelocity(2, 9);
        baseEntity.setPosition(new Vector2(21, 15), true);
        var res = baseEntity.body.getLinearVelocity();

        Assertions.assertThat(res).isEqualTo(new Vector2(2, 9));
    }

    @Test
    public void testSaveRecoverState() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        baseEntity.body.setType(BodyDef.BodyType.KinematicBody);
        baseEntity.body.setLinearVelocity(2, 9);
        baseEntity.body.setGravityScale(3);
        baseEntity.saveState();
        baseEntity.body.setLinearVelocity(0, 0);
        baseEntity.body.setGravityScale(0);
        baseEntity.setPosition(new Vector2(0, 0), false);
        baseEntity.recoverState();
        var resPosition = baseEntity.getPosition();
        var resVelocity = baseEntity.body.getLinearVelocity();
        var resGravity = baseEntity.body.getGravityScale();

        Assertions.assertThat(resPosition).isEqualTo(new Vector2(5, 6));
        Assertions.assertThat(resVelocity).isEqualTo(new Vector2(2, 9));
        Assertions.assertThat(resGravity).isEqualTo(3);
    }

    @Test
    public void testHide() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        baseEntity.body.setType(BodyDef.BodyType.KinematicBody);
        baseEntity.body.setLinearVelocity(2, 9);
        baseEntity.hide();

        Vector2 wantedPosition = new Vector2(Constants.Physics.DeletedLocation.x / Constants.Physics.Scale, Constants.Physics.DeletedLocation.y / Constants.Physics.Scale);
        Assertions.assertThat(baseEntity.body.getPosition()).isEqualTo(wantedPosition);
        Assertions.assertThat(baseEntity.body.getGravityScale()).isEqualTo(0);
        Assertions.assertThat(baseEntity.body.getLinearVelocity()).isEqualTo(new Vector2(0, 0));
    }

    @Test
    public void testDispose() {
        World world = Mockito.mock(World.class);
        Body body = Mockito.mock(Body.class);
        Mockito.when(world.createBody(Mockito.any(BodyDef.class))).thenReturn(body);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        Fixture fixture = Mockito.mock(Fixture.class);
        Mockito.when(body.createFixture(Mockito.any(FixtureDef.class))).thenReturn(fixture);
        Mockito.when(body.getWorld()).thenReturn(world);

        BaseEntity baseEntity = new BaseEntity(world, size, position);
        baseEntity.dispose();

        Mockito.verify(world).destroyBody(body);
    }

    @Test
    public void testUpdate() {
        World world = Mockito.mock(World.class);
        Body body = Mockito.mock(Body.class);
        Mockito.when(world.createBody(Mockito.any(BodyDef.class))).thenReturn(body);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        Fixture fixture = Mockito.mock(Fixture.class);
        Mockito.when(body.createFixture(Mockito.any(FixtureDef.class))).thenReturn(fixture);

        BaseEntity baseEntity = new BaseEntity(world, size, position);
        Mockito.reset(body);
        baseEntity.update();

        Mockito.verifyNoInteractions(body);
    }

    @Test
    public void testDraw() {
        World world = Mockito.mock(World.class);
        Body body = Mockito.mock(Body.class);
        Mockito.when(world.createBody(Mockito.any(BodyDef.class))).thenReturn(body);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        Fixture fixture = Mockito.mock(Fixture.class);
        Mockito.when(body.createFixture(Mockito.any(FixtureDef.class))).thenReturn(fixture);
        Batch batch = Mockito.mock(Batch.class);

        BaseEntity baseEntity = new BaseEntity(world, size, position);
        Mockito.reset(body);
        baseEntity.update();
        baseEntity.draw(batch);

        Mockito.verifyNoInteractions(body, batch);
    }

    @Test
    public void testHashCodeTrue() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        var res = baseEntity.hashCode();
        var res2 = baseEntity.hashCode();

        Assertions.assertThat(res).isEqualTo(res2);
    }

    @Test
    public void testHashCodeFalse() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);
        BaseEntity baseEntity2 = new BaseEntity(world, size, position);

        var res = baseEntity.hashCode();
        var res2 = baseEntity2.hashCode();

        Assertions.assertThat(res).isNotEqualTo(res2);
    }

    @Test
    public void testEqualsTrue() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        var res = baseEntity.equals(baseEntity);

        Assertions.assertThat(res).isTrue();
    }

    @Test
    public void testEqualsFalse() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);
        BaseEntity baseEntity2 = new BaseEntity(world, size, position);

        var res = baseEntity.equals(baseEntity2);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testEqualsNull() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        var res = baseEntity.equals(null);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testGetBody() {
        World world = Mockito.mock(World.class);
        Body body = Mockito.mock(Body.class);
        Mockito.when(world.createBody(Mockito.any(BodyDef.class))).thenReturn(body);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        Fixture fixture = Mockito.mock(Fixture.class);
        Mockito.when(body.createFixture(Mockito.any(FixtureDef.class))).thenReturn(fixture);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        var res = baseEntity.getBody();
        var res2 = baseEntity.body;

        Assertions.assertThat(res).isEqualTo(res2);
        Assertions.assertThat(res).isEqualTo(body);
    }

    @Test
    public void testGetUserDamageable() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        var res = baseEntity.getUserDamageable();

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testGetButtonNumber() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        var res = baseEntity.getButtonNumber();

        Assertions.assertThat(res).isEqualTo(-1);
    }

    @Test
    public void testDamage() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        var res = baseEntity.damage();
        var res1 = baseEntity.damage();
        var res2 = baseEntity.damage();

        Assertions.assertThat(res).isFalse();
        Assertions.assertThat(res1).isFalse();
        Assertions.assertThat(res2).isFalse();
    }

    @Test
    public void testButtonAction() {
        World world = new World(new Vector2(0, 0), true);
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        BaseEntity baseEntity = new BaseEntity(world, size, position);

        var res = baseEntity.buttonAction(baseEntity);
        var res1 = baseEntity.buttonAction(baseEntity);
        var res2 = baseEntity.buttonAction(baseEntity);

        Assertions.assertThat(res).isFalse();
        Assertions.assertThat(res1).isFalse();
        Assertions.assertThat(res2).isFalse();
    }
}
