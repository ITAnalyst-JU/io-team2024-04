package core.entities.decorators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import core.entities.IEntity;
import core.general.Constants;

public class MovingDecorator extends BaseEntityDecorator {
    private final MovementDirection direction;
    private final float minPosition, maxPosition;

    public MovingDecorator(IEntity wrapped, MovementDirection direction, Vector2 movementBounds) {
        super(wrapped);
        wrapped.getBody().setType(BodyDef.BodyType.KinematicBody);
        this.direction = direction;
        this.minPosition = movementBounds.x;
        this.maxPosition = movementBounds.y;
        switch (direction) {
            case HORIZONTAL:
                wrapped.getBody().setLinearVelocity(new Vector2(Constants.Physics.EntitiesMoveSpeed, 0));
                break;
            case VERTICAL:
                wrapped.getBody().setLinearVelocity(new Vector2(0, Constants.Physics.EntitiesMoveSpeed));
                break;
        }
    }

    public enum MovementDirection {
        VERTICAL,
        HORIZONTAL
    }

    @Override
    public void update() {
        Body body = wrapped.getBody();
        switch (direction) {
            case HORIZONTAL:
                if (body.getPosition().x < minPosition) {
                    body.setLinearVelocity(new Vector2(Constants.Physics.EntitiesMoveSpeed, 0));
                } else if (body.getPosition().x > maxPosition) {
                    body.setLinearVelocity(new Vector2(-Constants.Physics.EntitiesMoveSpeed, 0));
                }
                break;
            case VERTICAL:
                if (body.getPosition().y < minPosition) {
                    body.setLinearVelocity(new Vector2(0, Constants.Physics.EntitiesMoveSpeed));
                } else if (body.getPosition().y > maxPosition) {
                    body.setLinearVelocity(new Vector2(0, -Constants.Physics.EntitiesMoveSpeed));
                }
                break;
        }
        super.update();
    }
}
