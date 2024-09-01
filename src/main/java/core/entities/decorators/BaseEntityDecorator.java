package core.entities.decorators;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import core.entities.IEntity;

public abstract class BaseEntityDecorator implements IEntity {
    protected IEntity wrapped;

    public BaseEntityDecorator(IEntity wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getType() {
        return wrapped.getType();
    }

    @Override
    public Vector2 getPosition() {
        return wrapped.getPosition();
    }

    @Override
    public void setPosition(Vector2 position, boolean preserveVelocity) {
        wrapped.setPosition(position, preserveVelocity);
    }

    @Override
    public void saveState() {
        wrapped.saveState();
    }

    @Override
    public void recoverState() {
        wrapped.recoverState();
    }

    @Override
    public void hide() {
        wrapped.hide();
    }

    @Override
    public void dispose() {
        wrapped.dispose();
    }

    @Override
    public void update() {
        wrapped.update();
    }

    @Override
    public void draw(Batch batch) {
        wrapped.draw(batch);
    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return wrapped.equals(obj);
    }

    @Override
    public Body getBody() {
        return wrapped.getBody();
    }

    @Override
    public boolean getUserDamageable() {
        return wrapped.getUserDamageable();
    }

    @Override
    public int getButtonNumber() {
        return wrapped.getButtonNumber();
    }

    @Override
    public boolean damage() {
        return wrapped.damage();
    }

    @Override
    public boolean buttonAction(IEntity baseInstance) {
        return wrapped.buttonAction(baseInstance);
    }
}
