package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends AbstractMovingEntity {
    // Why is there one class for all platforms?
    // I don't think you should have a distinct class for every combination of properties an object might have.
    // We'd probably need about a dozen classes instead of a single one here.

    private int life;

    public Platform(Sprite sprite, World world, MovementDirection direction, Vector2 size, Vector2 position) {
        super(sprite, world, direction, size, position);

        life = Integer.MAX_VALUE;
        this.type = "platform";
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public boolean damage() {
        life--;
        return (life == 0);
    }

    @Override
    public void saveState() {
        super.saveState();
        state.put("life", life);
    }

    @Override
    public void recoverState() {
        super.recoverState();
        life = (int)state.get("life");
    }

}
