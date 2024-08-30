package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends SpriteMovingEntity implements ButtonAction {
    private final boolean userDamageable;

    public Platform(Sprite sprite, World world, MovementDirection direction, Vector2 size, Vector2 position, boolean isUserDamageable) {
        super(sprite, world, direction, size, position);
        this.type = "platform";
        this.userDamageable = isUserDamageable;
    }

    public boolean isUserDamageable() {
        return userDamageable;
    }

    @Override
    public boolean buttonAction() {
        return this.damage();
    }
}
