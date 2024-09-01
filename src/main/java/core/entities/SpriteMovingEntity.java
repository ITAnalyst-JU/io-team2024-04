package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.general.Constants;

public abstract class SpriteMovingEntity extends SpriteEntity {
    private final Platform.MovementDirection direction;
    private float minPosition = 0, maxPosition = 0;

    public SpriteMovingEntity(Sprite sprite, World world, Platform.MovementDirection direction, Vector2 size, Vector2 position) {
        super(sprite, world, direction == MovementDirection.STATIC ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.KinematicBody, size, position);
        this.direction = direction;
        switch (direction) {
            case HORIZONTAL:
                body.setLinearVelocity(Constants.Physics.EntitiesMoveSpeed, 0);
                break;
            case VERTICAL:
                body.setLinearVelocity(0, Constants.Physics.EntitiesMoveSpeed);
                break;
            case STATIC:
                body.setLinearVelocity(0, 0);
        }
    }

    public enum MovementDirection {
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
                    body.setLinearVelocity(Constants.Physics.EntitiesMoveSpeed, 0);
                } else if (body.getPosition().x > maxPosition) {
                    body.setLinearVelocity(-Constants.Physics.EntitiesMoveSpeed, 0);
                }
                break;
            case VERTICAL:
                if (body.getPosition().y < minPosition) {
                    body.setLinearVelocity(0, Constants.Physics.EntitiesMoveSpeed);
                } else if (body.getPosition().y > maxPosition) {
                    body.setLinearVelocity(0, -Constants.Physics.EntitiesMoveSpeed);
                }
                break;
            case STATIC:
                break;
        }
        super.update();
    }
}
