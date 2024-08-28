package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Block extends Platform implements ButtonAction{
    public Block(Sprite sprite, World world, MovementDirection direction, Vector2 size, Vector2 position) {
        super(sprite, world, direction, size, position);

        this.life = Integer.MAX_VALUE;
        this.type = "block";
    }

    @Override
    public boolean buttonAction() {
        return damage();
    }
}
