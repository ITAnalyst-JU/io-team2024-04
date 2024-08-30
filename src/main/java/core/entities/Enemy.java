package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends SpriteMovingEntity {

    public Enemy(Sprite sprite, World world, SpriteMovingEntity.MovementDirection direction, Vector2 size, Vector2 position) {
        super(sprite, world, direction, size, position);
    }
}
