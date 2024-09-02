package core.entities.decorators;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import core.entities.IEntity;

// This class doesn't really do anything, but it was impossible to test EntityFactory without it.
public class DecoratorFactory {
    public IEntity getButtonActionDamageDecorator(IEntity wrapped) {
        return new ButtonActionDamageDecorator(wrapped);
    }

    public IEntity getButtonDecorator(IEntity wrapped, int number) {
        return new ButtonDecorator(wrapped, number);
    }

    public IEntity getDamageableDecorator(IEntity wrapped, int life) {
        return new DamageableDecorator(wrapped, life);
    }

    public IEntity getGenericTypeDecorator(IEntity wrapped, String type) {
        return new GenericTypeDecorator(wrapped, type);
    }

    public IEntity getMovingDecorator(IEntity wrapped, MovingDecorator.MovementDirection direction, Vector2 movementBounds) {
        return new MovingDecorator(wrapped, direction, movementBounds);
    }

    public IEntity getSpriteDecorator(IEntity wrapped, Sprite sprite, Vector2 size) {
        return new SpriteDecorator(wrapped, sprite, size);
    }

    public IEntity getUserDamageableDecorator(IEntity entity) {
        return new UserDamageableDecorator(entity);
    }
}
