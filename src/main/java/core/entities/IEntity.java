package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface IEntity {
    String getType();
    Vector2 getPosition();
    void setPosition(Vector2 position, boolean preserveVelocity);
    void saveState();
    void recoverState();
    void hide();
    void dispose();
    void update();
    void draw(Batch batch);
    Body getBody();
    // Following do not adhere to the interface segregation principle.
    boolean getUserDamageable();
    int getButtonNumber();
    boolean damage();
    boolean buttonAction(IEntity baseInstance);
}
