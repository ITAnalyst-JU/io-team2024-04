package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SpriteEntityTest {
    private static final Vector2 zeroVector = new Vector2(0, 0);
    private static class TestClass extends SpriteEntity {
        public TestClass() {
            super(new Sprite(), new World(new Vector2(0, 0), true), BodyDef.BodyType.StaticBody, zeroVector, zeroVector);
        }
    }

    @Test
    public void SpriteEntityDefaultLifeTest() {
        TestClass testClass = new TestClass();
        for (int i = 0; i < 100; i++) {
            assertThat(testClass.damage()).isFalse();
        }
    }

    @Test
    public void SpriteEntityLifeTest() {
        TestClass testClass = new TestClass();
        testClass.setLife(7);
        for (int i = 0; i < 6; i++) {
            assertThat(testClass.damage()).isFalse();
        }
        assertThat(testClass.damage()).isTrue();
    }

    // It doesn't appear to be possible to test save and recover separately.
    @Test
    public void SpriteEntityLifeSavingTest() {
        TestClass testClass = new TestClass();
        testClass.setLife(1);
        testClass.saveState();
        testClass.damage();
        testClass.recoverState();
        assertThat(testClass.damage()).isTrue();
    }

    @Test
    public void SpriteEntityLifeSetAdvancedTest() {
        TestClass testClass = new TestClass();
        testClass.setLife(1);
        testClass.saveState();
        testClass.damage();
        testClass.recoverState();
        testClass.damage();
        testClass.setLife(2);
        assertThat(testClass.damage()).isFalse();
        assertThat(testClass.damage()).isTrue();
    }
}
