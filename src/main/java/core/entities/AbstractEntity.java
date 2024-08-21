package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import core.utilities.Constants;
import core.utilities.Constants.Physics;

public abstract class AbstractEntity {
    protected Sprite sprite;
    protected Body body;
    
    protected boolean toRemove = false;

    public AbstractEntity(Sprite sprite, TiledMapTileLayer mapLayer, World world, BodyDef.BodyType bodyType) {
        this.sprite = sprite;

        sprite.setSize(mapLayer.getTileWidth(), mapLayer.getTileHeight());
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

    //TODO: update depending on timeDelta
    public Vector2 update() {
        Vector2 position = body.getPosition();
        position.x *= Constants.Physics.Scale;
        position.y *= Constants.Physics.Scale;
        sprite.setPosition(position.x - sprite.getWidth()/2f, position.y - sprite.getHeight()/2f);
        return position;
    };

    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    //TODO: Actually remove the body from the world
    public void remove() {
        // body.getWorld().destroyBody(body);
        setPosition(new Vector2(-100, -100), false);
        body.setGravityScale(0);
    }

    public void setToRemove() {
        toRemove = true;
    }
}
