package core.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.general.Constants;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BodyOnlyEntityTest {
    private static final Vector2 zeroVector = new Vector2(0, 0);
    private static class TestClass extends BodyOnlyEntity {
        public TestClass() {
            super(new World(new Vector2(0, 0), true), BodyDef.BodyType.KinematicBody, zeroVector, zeroVector);
        }
    }
    private static class DummyObject{}

    @Test
    public void testSetPositionDoesNotChangeGivenVector() {
        TestClass testClass = new TestClass();
        Vector2 position = new Vector2(2, 14);
        testClass.setPosition(position, false);
        assertThat(position).isEqualTo(new Vector2(2, 14));
    }

    @Test
    public void testBodyOnlyEntityDefaultType() {
        TestClass testClass = new TestClass();
        assertThat(testClass.getType()).isEqualTo("default");
    }

    @Test
    public void testChangeType() {
        TestClass testClass = new TestClass();
        testClass.setType("newType");
        assertThat(testClass.getType()).isEqualTo("newType");
    }

    @Test
    public void testPositionChanging() {
        TestClass testClass = new TestClass();
        testClass.setPosition(new Vector2(21, 15), false);
        assertThat(testClass.getPosition()).isEqualTo(new Vector2(21, 15));
    }

    @Test
    public void testHide() {
        TestClass testClass = new TestClass();
        testClass.hide();
        assertThat(testClass.getPosition()).isEqualTo(Constants.Physics.DeletedLocation);
    }

    @Test
    public void testPositionSaveRecover() {
        Vector2 position = new Vector2(76, 22);
        TestClass testClass = new TestClass();
        testClass.setPosition(position, false);
        testClass.saveState();
        testClass.setPosition(new Vector2(0, 0), false);
        testClass.recoverState();
        assertThat(testClass.getPosition()).isEqualTo(position);
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
        assertThat(testClass.body.getGravityScale()).isEqualTo(1);
        assertThat(testClass.body.getLinearVelocity()).isEqualTo(new Vector2(2, 9));
    }
}
