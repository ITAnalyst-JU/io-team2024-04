package core.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ButtonTest {
    private final Vector2 zeroVector = new Vector2(0, 0);

    @Test
    public void ButtonNumberPropertyTest() {
        final int number = 17;
        Button button = new Button(new World(new Vector2(0, 0), true), BodyDef.BodyType.StaticBody, zeroVector, zeroVector, number);
        assertThat(button.getNumber()).isEqualTo(number);
    }

    @Test
    public void ButtonTypePropertyTest() {
        Button button = new Button(new World(new Vector2(0, 0), true), BodyDef.BodyType.StaticBody, zeroVector, zeroVector, 0);
        assertThat(button.getType()).isEqualTo("button");
    }
}
