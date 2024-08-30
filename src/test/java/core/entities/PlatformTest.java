package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlatformTest {
    private final Vector2 zeroVector = new Vector2(0, 0);

    @Test
    public void testUserDamageableFalse() {
        Platform platform = new Platform(new Sprite(), new World(new Vector2(0, 0), true), SpriteMovingEntity.MovementDirection.STATIC, zeroVector, zeroVector, false);
        assertThat(platform.isUserDamageable()).isFalse();
    }

    @Test
    public void testUserDamageableTrue() {
        Platform platform = new Platform(new Sprite(), new World(new Vector2(0, 0), true), SpriteMovingEntity.MovementDirection.STATIC, zeroVector, zeroVector, true);
        assertThat(platform.isUserDamageable()).isTrue();
    }

    @Test
    public void testDamage() {
        Platform platform = new Platform(new Sprite(), new World(new Vector2(0, 0), true), SpriteMovingEntity.MovementDirection.STATIC, zeroVector, zeroVector, false);
        platform.setLife(1);
        assertThat(platform.damage()).isTrue();
    }

    @Test
    public void testButtonAction() {
        Platform platform = new Platform(new Sprite(), new World(new Vector2(0, 0), true), SpriteMovingEntity.MovementDirection.STATIC, zeroVector, zeroVector, false);
        platform.setLife(1);
        ButtonAction action = platform;
        assertThat(action.buttonAction()).isTrue();
    }

    @Test
    public void testTypeProperty() {
        Platform platform = new Platform(new Sprite(), new World(new Vector2(0, 0), true), SpriteMovingEntity.MovementDirection.STATIC, zeroVector, zeroVector, false);
        assertThat(platform.getType()).isEqualTo("platform");
    }
}
