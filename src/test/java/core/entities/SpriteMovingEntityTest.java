package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.general.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpriteMovingEntityTest {
    private static final Vector2 zeroVector = new Vector2(0, 0);
    private static class TestClass extends SpriteMovingEntity {
        public TestClass() {
            super(new Sprite(), new World(new Vector2(0, 0), true), MovementDirection.STATIC, zeroVector, zeroVector);
        }
        public TestClass(Sprite sprite, World world, MovementDirection direction, Vector2 size, Vector2 position) {
            super(sprite, world, direction, size, position);
        }
    }

    @Test
    public void testBodyType() {
        TestClass testClass = new TestClass(new Sprite(), new World(new Vector2(0, 0), true), SpriteMovingEntity.MovementDirection.HORIZONTAL, zeroVector, zeroVector);

        Assertions.assertThat(testClass.body.getType()).isEqualTo(BodyDef.BodyType.KinematicBody);
    }

    @Test
    public void testConstructorHorizontal() {
        TestClass testClass = new TestClass(new Sprite(), new World(new Vector2(0, 0), true), SpriteMovingEntity.MovementDirection.HORIZONTAL, zeroVector, zeroVector);

        Assertions.assertThat(testClass.body.getType()).isEqualTo(BodyDef.BodyType.KinematicBody);
        Assertions.assertThat(testClass.body.getLinearVelocity()).isEqualTo(new Vector2(Constants.Physics.EntitiesMoveSpeed, 0));
    }

    @Test
    public void testConstructorVertical() {
        TestClass testClass = new TestClass(new Sprite(), new World(new Vector2(0, 0), true), SpriteMovingEntity.MovementDirection.VERTICAL, zeroVector, zeroVector);

        Assertions.assertThat(testClass.body.getType()).isEqualTo(BodyDef.BodyType.KinematicBody);
        Assertions.assertThat(testClass.body.getLinearVelocity()).isEqualTo(new Vector2(0, Constants.Physics.EntitiesMoveSpeed));
    }
}
