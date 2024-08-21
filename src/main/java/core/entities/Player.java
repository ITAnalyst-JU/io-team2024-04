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

    private final float yVelocityLimit = 10f;
    private final float xVelocityBase = 10f;

    private int sideKeyPressed = 0;

    public Player(Sprite sprite, Vector2 size, World world) {
        super(sprite, size, world, BodyDef.BodyType.DynamicBody);
    }

    public void update() {
        body.setLinearVelocity(sideKeyPressed * this.xVelocityBase, body.getLinearVelocity().y);
        super.update();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Player is immortal and cannot be killed.");
    }

    @Override
    public boolean keyDown(int keyNo) {
        switch (keyNo) {
            case Input.Keys.A:
                this.sideKeyPressed--;
                break;
            case Input.Keys.D:
                this.sideKeyPressed++;
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
                break;
            case Input.Keys.D:
                this.sideKeyPressed--;
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
