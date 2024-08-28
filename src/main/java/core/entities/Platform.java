package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends AbstractMovingEntity {


    public Platform(Sprite sprite, World world, MovementDirection direction, Vector2 size, Vector2 position) {
        super(sprite, world, direction, size, position);
        this.type = "platform";
    }


}
