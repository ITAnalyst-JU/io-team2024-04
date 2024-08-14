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

import core.utilities.Constants.Physics;

public abstract class AbstractEntity {
    protected Sprite sprite;
    protected Body body;

    public AbstractEntity(Sprite sprite, TiledMapTileLayer mapLayer, World world) {
        this.sprite = sprite;

        sprite.setSize(mapLayer.getTileWidth(), mapLayer.getTileHeight());
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setLinearDamping(0);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(sprite.getWidth()/Physics.Scale / 2f, sprite.getHeight()/Physics.Scale / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
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

    public abstract Vector2 update();

    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
