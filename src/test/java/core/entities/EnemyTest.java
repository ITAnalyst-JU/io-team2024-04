package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnemyTest {
    private final Vector2 zeroVector = new Vector2(0, 0);

    @Test
    public void testTypeProperty() {
        Enemy platform = new Enemy(new Sprite(), new World(new Vector2(0, 0), true), SpriteMovingEntity.MovementDirection.STATIC, zeroVector, zeroVector);
        assertThat(platform.getType()).isEqualTo("enemy");
    }
}
