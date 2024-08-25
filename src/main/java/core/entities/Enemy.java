package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends AbstractMovingEntity {

    public Enemy(Sprite sprite, Vector2 size, World world, AbstractMovingEntity.MovementDirection direction) {
        super(sprite, size, world, direction);
    }
}
