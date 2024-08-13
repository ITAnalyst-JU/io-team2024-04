package core.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.utilities.Constants;
import core.utilities.Constants.Physics;

public class Player implements InputProcessor {
    private Sprite sprite;

    private Body body;

    private float yVelocityLimit = 10f;
    private float xVelocityBase = 10f;

    public Player(Sprite sprite, TiledMapTileLayer mapLayer, World world) {
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
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        polygonShape.dispose();
    }

    public Vector2 update() {
        Vector2 position = body.getPosition();
        position.x *= Constants.Physics.Scale;
        position.y *= Constants.Physics.Scale;
        sprite.setPosition(position.x - sprite.getWidth()/2f, position.y - sprite.getHeight()/2f);
        return position;
    }

    public void setPosition(Vector2 position) {
        body.setTransform(position, 0);
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public boolean keyDown(int keyNo) {
        switch (keyNo) {
            case Input.Keys.A:
                body.applyLinearImpulse(new Vector2(-xVelocityBase, 0), body.getPosition(), false);
                break;
            case Input.Keys.D:
                body.applyLinearImpulse(new Vector2(xVelocityBase, 0), body.getPosition(), false);
                break;
            case Input.Keys.W:
                body.setLinearVelocity(body.getLinearVelocity().x, yVelocityLimit);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keyNo) {
        switch (keyNo) {
            case Input.Keys.A:
            case Input.Keys.D:
                body.setLinearVelocity(0, body.getLinearVelocity().y);
                break;
        }
//        switch(keyNo) {
//            case Input.Keys.A:
//                physicsBody.applyLinearImpulse(new Vector2(xVelocityBase, 0), physicsBody.getPosition(), false);
//                break;
//            case Input.Keys.D:
//                physicsBody.applyLinearImpulse(new Vector2(-xVelocityBase, 0), physicsBody.getPosition(), false);
//                break;
//        }
        return true;
    }

    @Override public String toString() {
        return "player";
    }

    // We don't use other methods right now.
    // They have to be here because Java doesn't support multiple inheritance.

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
