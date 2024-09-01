package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class SpriteEntityTest {
    private static final Vector2 zeroVector = new Vector2(0, 0);
    private static class TestClass extends SpriteEntity {
        public TestClass() {
            super(new Sprite(), new World(new Vector2(0, 0), true), BodyDef.BodyType.StaticBody, zeroVector, zeroVector);
        }
        public TestClass(Sprite sprite, World world, BodyDef.BodyType bodyType, Vector2 size, Vector2 position) {
            super(sprite, world, bodyType, size, position);
        }
    }

    @Test
    public void testDefaultLife() {
        TestClass testClass = new TestClass();
        for (int i = 0; i < 100; i++) {
            assertThat(testClass.damage()).isFalse();
        }
    }

    @Test
    public void testLife() {
        TestClass testClass = new TestClass();
        testClass.setLife(7);
        for (int i = 0; i < 6; i++) {
            assertThat(testClass.damage()).isFalse();
        }
        assertThat(testClass.damage()).isTrue();
    }

    // It doesn't appear to be possible to test save and recover separately.
    @Test
    public void testLifeSaving() {
        TestClass testClass = new TestClass();
        testClass.setLife(1);
        testClass.saveState();
        testClass.damage();
        testClass.recoverState();
        assertThat(testClass.damage()).isTrue();
    }

    @Test
    public void testLifeSetting() {
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

    @Test
    public void testUpdate() {
        var sprite = Mockito.mock(Sprite.class);
        TestClass testClass = new TestClass(sprite, new World(new Vector2(0, 0), true), BodyDef.BodyType.StaticBody, zeroVector, new Vector2(10, 10));
        testClass.update();
        verify(sprite, Mockito.times(1)).setPosition(10, 10);
    }

    @Test
    public void testDraw() {
        var sprite = Mockito.mock(Sprite.class);
        var batch = Mockito.mock(com.badlogic.gdx.graphics.g2d.Batch.class);
        TestClass testClass = new TestClass(sprite, new World(new Vector2(0, 0), true), BodyDef.BodyType.StaticBody, zeroVector, zeroVector);
        testClass.draw(batch);
        verify(sprite, Mockito.times(1)).draw(batch);
    }
}
