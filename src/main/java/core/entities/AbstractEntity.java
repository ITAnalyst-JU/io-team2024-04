package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import core.utilities.Constants;
import core.utilities.Constants.Physics;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEntity {
    protected final Sprite sprite;
    protected Body body;

    protected Map<String, Object> state;

    public AbstractEntity(Sprite sprite, Vector2 size, World world, BodyDef.BodyType bodyType) {
        this.sprite = sprite;

        sprite.setSize(size.x, size.y);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setLinearDamping(0);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(sprite.getWidth()/Physics.Scale / 2f, sprite.getHeight()/Physics.Scale / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 10f;
        fixtureDef.restitution = 0;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        polygonShape.dispose();
    }

    public void setPosition(Vector2 position, boolean preserveVelocity) {
        if (!preserveVelocity) {
            body.setLinearVelocity(0, 0);
        }
        body.setTransform(position, 0);
    }

    public Vector2 getPosition () {
        Vector2 position = body.getPosition();
        position.x *= Constants.Physics.Scale;
        position.y *= Constants.Physics.Scale;
        return position;
    }

    public void update() {
        Vector2 position = this.getPosition();
        sprite.setPosition(position.x - sprite.getWidth()/2f, position.y - sprite.getHeight()/2f);
    };

    public void draw(Batch batch) {
        sprite.draw(batch);
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
        setPosition(Physics.DeletedLocation, false);
        body.setGravityScale(0);
    }

    public void dispose () {
         body.getWorld().destroyBody(body);
    }
}
