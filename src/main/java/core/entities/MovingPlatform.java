package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class MovingPlatform extends AbstractPlatform {

    private MovementDirection direction;

    private final float speed = 2f;

    private float minPosition = 0, maxPosition = 0;

    public MovingPlatform(Sprite sprite, Vector2 size, World world, MovementDirection direction) {
        super(sprite, size, world, BodyDef.BodyType.KinematicBody);
        this.direction = direction;
        switch (direction) {
            case HORIZONTAL:
                body.setLinearVelocity(speed, 0);
                break;
            case VERTICAL:
                body.setLinearVelocity(0, speed);
                break;
        }
    }

    public static enum MovementDirection {
        STATIC,
        VERTICAL,
        HORIZONTAL
    }

    public void setMovementBounds(float minPosition, float maxPosition) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    @Override
    public void update() {
        switch (direction) {
            case HORIZONTAL:
                if (body.getPosition().x < minPosition) {
                    body.setLinearVelocity(speed, 0);
                } else if (body.getPosition().x > maxPosition) {
                    body.setLinearVelocity(-speed, 0);
                }
                break;
            case VERTICAL:
                if (body.getPosition().y < minPosition) {
                    body.setLinearVelocity(0, speed);
                } else if (body.getPosition().y > maxPosition) {
                    body.setLinearVelocity(0, -speed);
                }
                break;
            case STATIC:
                break;
        }
        super.update();
    }

}
