package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.general.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BodyOnlyEntityTest {
    private static final Vector2 zeroVector = new Vector2(0, 0);
    private static class TestClass extends BodyOnlyEntity {
        public TestClass() {
            super(new World(new Vector2(0, 0), true), BodyDef.BodyType.KinematicBody, zeroVector, zeroVector);
        }
        public TestClass(World world, BodyDef.BodyType bodyType, Vector2 size, Vector2 position) {
            super(world, bodyType, size, position);
        }
    }
    private static class DummyObject{}

    @Test
    public void testGetPosition() {
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);
        TestClass testClass = new TestClass(new World(new Vector2(0, 0), true), BodyDef.BodyType.KinematicBody, size, position);

        var res = testClass.getPosition();

        Assertions.assertThat(res).isEqualTo(position);
    }

    @Test
    public void testConstructorParameters() {
        Vector2 size = new Vector2(2, 3);
        Vector2 position = new Vector2(5, 6);

        TestClass testClass = new TestClass(new World(new Vector2(0, 0), true), BodyDef.BodyType.KinematicBody, size, position);

        Assertions.assertThat(testClass.body.getType()).isEqualTo(BodyDef.BodyType.KinematicBody);
        Assertions.assertThat(testClass.body.getLinearVelocity()).isEqualTo(zeroVector);
        Assertions.assertThat(testClass.body.getGravityScale()).isEqualTo(1);
        Assertions.assertThat(testClass.body.isFixedRotation()).isTrue();
        Assertions.assertThat(testClass.body.getFixtureList().get(0).getDensity()).isEqualTo(1);
        Assertions.assertThat(testClass.body.getFixtureList().get(0).getFriction()).isEqualTo(Constants.Physics.Friction);
        Assertions.assertThat(testClass.body.getFixtureList().get(0).getRestitution()).isEqualTo(0);
        Assertions.assertThat(testClass.body.getFixtureList().get(0).getUserData()).isEqualTo(testClass);
    }

    @Test
    public void testSetPositionBasic() {
        TestClass testClass = new TestClass();

        testClass.setPosition(new Vector2(21, 15), false);
        var res = testClass.getPosition();

        Assertions.assertThat(res).isEqualTo(new Vector2(21, 15));
    }

    @Test
    public void testSetPositionPreservesVelocity() {
        TestClass testClass = new TestClass();
        testClass.body.setLinearVelocity(2, 9);
        testClass.setPosition(new Vector2(21, 15), true);
        var res = testClass.body.getLinearVelocity();

        Assertions.assertThat(res).isEqualTo(new Vector2(2, 9));
    }

    @Test
    public void testSetPositionDoesNotPreserveVelocityy() {
        TestClass testClass = new TestClass();
        testClass.body.setLinearVelocity(2, 9);
        testClass.setPosition(new Vector2(21, 15), false);
        var res = testClass.body.getLinearVelocity();

        Assertions.assertThat(res).isEqualTo(zeroVector);
    }

    @Test
    public void testSetPositionDoesNotChangeGivenVector() {
        TestClass testClass = new TestClass();
        Vector2 position = new Vector2(2, 14);
        testClass.setPosition(position, false);
        Assertions.assertThat(position).isEqualTo(new Vector2(2, 14));
    }

    @Test
    public void testBodyOnlyEntityDefaultType() {
        TestClass testClass = new TestClass();
        Assertions.assertThat(testClass.getType()).isEqualTo("default");
    }

    @Test
    public void testChangeType() {
        TestClass testClass = new TestClass();
        testClass.setType("newType");
        Assertions.assertThat(testClass.getType()).isEqualTo("newType");
    }

    @Test
    public void testHide() {
        TestClass testClass = new TestClass();
        testClass.hide();
        Assertions.assertThat(testClass.getPosition()).isEqualTo(Constants.Physics.DeletedLocation);
    }

    @Test
    public void testPositionSaveRecover() {
        Vector2 position = new Vector2(76, 22);
        TestClass testClass = new TestClass();
        testClass.setPosition(position, false);
        testClass.saveState();
        testClass.setPosition(new Vector2(0, 0), false);
        testClass.recoverState();
        Assertions.assertThat(testClass.getPosition()).isEqualTo(position);
    }

    // This test is extremely shady, as it uses private fields of the class
    @Test
    public void testGravityVelocitySaveRecover() {
        TestClass testClass = new TestClass();
        testClass.body.setLinearVelocity(2, 9);
        testClass.saveState();
        testClass.body.setGravityScale(0.5f);
        testClass.body.setLinearVelocity(24, 8);
        testClass.recoverState();
        Assertions.assertThat(testClass.body.getGravityScale()).isEqualTo(1);
        Assertions.assertThat(testClass.body.getLinearVelocity()).isEqualTo(new Vector2(2, 9));
    }

    @Test
    public void testDispose() {
        TestClass testClass = new TestClass();
        testClass.dispose();
        Assertions.assertThat(testClass.body.getWorld().getBodyCount()).isEqualTo(0);
    }

    @Test
    public void testUpdate() {
        TestClass testClass = new TestClass();
        testClass.body = Mockito.spy(testClass.body);
        testClass.update();
        Mockito.verifyNoInteractions(testClass.body);
    }

    @Test
    public void testDraw() {
        TestClass testClass = new TestClass();
        var batch = Mockito.mock(Batch.class);
        testClass.draw(batch);
        Mockito.verifyNoInteractions(batch);
    }

    @Test
    public void testHashCode() {
        TestClass testClass = new TestClass();
        TestClass testClass2 = new TestClass();

        var hash = testClass.hashCode();
        var hash2 = testClass2.hashCode();

        Assertions.assertThat(hash).isNotEqualTo(hash2);
    }

    @Test
    public void testEqualsNull() {
        TestClass testClass = new TestClass();
        TestClass testClass2 = null;

        var res = testClass.equals(testClass2);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testEqualsDifferentClass() {
        TestClass testClass = new TestClass();
        DummyObject dummyObject = new DummyObject();

        var res = testClass.equals(dummyObject);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testEqualsFalse() {
        TestClass testClass = new TestClass();
        Object object = new Object();

        var res = testClass.equals(object);

        Assertions.assertThat(res).isFalse();
    }

    @Test
    public void testEqualsTrue() {
        TestClass testClass = new TestClass();
        TestClass testClass2 = testClass;

        var res = testClass.equals(testClass2);

        Assertions.assertThat(res).isTrue();
    }
}
