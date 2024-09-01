package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.general.Constants;

import java.util.TreeMap;
import java.util.Map;

public class BaseEntity implements IEntity {
    Body body;

    protected Map<String, Object> state;

    // Impossible to inject dependence on Body class.
    BaseEntity(World world, Vector2 size, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(position.x / Constants.Physics.Scale, position.y / Constants.Physics.Scale);
        body = world.createBody(bodyDef);
        body.setLinearDamping(0);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(size.x / Constants.Physics.Scale / 2f, size.y / Constants.Physics.Scale / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = Constants.Physics.Friction;
        fixtureDef.restitution = 0;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        polygonShape.dispose();
    }

    @Override
    public String getType() {
        return "default";
    }

    @Override
    public Vector2 getPosition () {
        Vector2 position = body.getPosition();
        position.x *= Constants.Physics.Scale;
        position.y *= Constants.Physics.Scale;
        return position;
    }

    @Override
    public void setPosition(Vector2 positionToHave, boolean preserveVelocity) {
        if (!preserveVelocity) {
            body.setLinearVelocity(0, 0);
        }
        Vector2 position = new Vector2(positionToHave);
        position.x /= Constants.Physics.Scale;
        position.y /= Constants.Physics.Scale;
        body.setTransform(position, 0);
    }

    @Override
    public void saveState () {
        state = new TreeMap<>();
        //reflection could possibly be used, to get all fields of a class automatically
        state.put("position", new Vector2(body.getPosition()));
        state.put("velocity", new Vector2(body.getLinearVelocity()));
        state.put("gravity", body.getGravityScale());
    }

    @Override
    public void recoverState () {
        body.setTransform((Vector2)state.get("position"), 0);
        body.setLinearVelocity((Vector2)state.get("velocity"));
        body.setGravityScale((float)state.get("gravity"));
    }

    @Override
    public void hide () {
        setPosition(Constants.Physics.DeletedLocation, false);
        body.setGravityScale(0);
    }

    @Override
    public void dispose () {
        body.getWorld().destroyBody(body);
    }

    @Override
    public void update() {
        return;
    }

    @Override
    public void draw(Batch batch) {
        return;
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof BaseEntity)) {
            return false;
        }
        BaseEntity other = (BaseEntity) obj;
        return body.equals(other.body);
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public boolean getUserDamageable() {
        return false;
    }

    @Override
    public int getButtonNumber() {
        return -1;
    }

    @Override
    public boolean damage() {
        return false;
    }

    @Override
    public boolean buttonAction(IEntity baseInstance) {
        return false;
    }
}
