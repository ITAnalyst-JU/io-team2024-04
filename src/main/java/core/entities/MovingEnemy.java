package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class MovingEnemy extends AbstractEnemy {

    private final float maxSpeed = 3f;

    private float minX = 0, maxX = 0;

    public MovingEnemy(Sprite sprite, Vector2 size, World world) {
        super(sprite, size, world, BodyDef.BodyType.KinematicBody);
        body.setLinearVelocity(maxSpeed, 0);
    }

    public void setMovementBounds(float minX, float maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public void update() {
        if (body.getPosition().x < minX) {
            body.setLinearVelocity(maxSpeed, 0);
        } else if (body.getPosition().x > maxX) {
            body.setLinearVelocity(-maxSpeed, 0);
        }

        super.update();
    }

}
