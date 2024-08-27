package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

public class BodyEntity {
    protected Body body;
    protected String type;

    protected Map<String, Object> state;

    BodyEntity(World world, BodyDef.BodyType bodyType, Vector2 size, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(position.x / Constants.Physics.Scale, position.y / Constants.Physics.Scale);
        body = world.createBody(bodyDef);
        body.setLinearDamping(0);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(size.x/ Constants.Physics.Scale / 2f, size.y / Constants.Physics.Scale / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 10f;
        fixtureDef.restitution = 0;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        polygonShape.dispose();

        type = "default";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Vector2 getPosition () {
        Vector2 position = body.getPosition();
        position.x *= Constants.Physics.Scale;
        position.y *= Constants.Physics.Scale;
        return position;
    }

    public void setPosition(Vector2 position, boolean preserveVelocity) {
        if (!preserveVelocity) {
            body.setLinearVelocity(0, 0);
        }
        body.setTransform(position, 0);
    }

    public void saveState () {
        state = new HashMap<>();
        state.put("position", new Vector2(body.getPosition()));
        state.put("velocity", new Vector2(body.getLinearVelocity()));
        state.put("gravity", body.getGravityScale());
    }

    public void recoverState () {
        body.setTransform((Vector2)state.get("position"), 0);
        body.setLinearVelocity((Vector2)state.get("velocity"));
        body.setGravityScale((float)state.get("gravity"));
    }

    public void hide () {
        setPosition(Constants.Physics.DeletedLocation, false);
        body.setGravityScale(0);
    }

    public void dispose () {
        body.getWorld().destroyBody(body);
    }

    public void update() {
        return;
    }

    public void draw(Batch batch) {
        return;
    }
}
