package core.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.utilities.Constants;

public class Player extends AbstractEntity implements InputProcessor {

    private float yVelocityLimit = 10f;
    private float xVelocityBase = 10f;

    private int sideKeyPressed = 0;

    public Player(Sprite sprite, TiledMapTileLayer mapLayer, World world) {
        super(sprite, mapLayer, world);
    }

    public Vector2 update() {
        body.setLinearVelocity(sideKeyPressed * this.xVelocityBase, body.getLinearVelocity().y);
        Vector2 position = body.getPosition();
        position.x *= Constants.Physics.Scale;
        position.y *= Constants.Physics.Scale;
        sprite.setPosition(position.x - sprite.getWidth()/2f, position.y - sprite.getHeight()/2f);
        return position;
    }

    @Override
    public boolean keyDown(int keyNo) {
        switch (keyNo) {
            case Input.Keys.A:
                this.sideKeyPressed--;
//                body.applyLinearImpulse(new Vector2(-xVelocityBase, 0), body.getPosition(), false);
                break;
            case Input.Keys.D:
                this.sideKeyPressed++;
//                body.applyLinearImpulse(new Vector2(xVelocityBase, 0), body.getPosition(), false);
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
                this.sideKeyPressed++;
//                body.setLinearVelocity(0, body.getLinearVelocity().y);
                break;
            case Input.Keys.D:
                this.sideKeyPressed--;
//                body.setLinearVelocity(0, body.getLinearVelocity().y);
                break;
        }
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
