package core.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.general.Constants;

public class Player extends AbstractEntity implements InputProcessor {
    // Does not extend moving entity because movement is different.

    private int sideKeyPressed = 0;
    private int jumpsLeft = 0;
    private boolean ladderContact = false;
    private int ladderClimbing = 0;

    private final Vector2 tempSpeed = new Vector2(); // for performance reasons

    public Player(Sprite sprite, World world, Vector2 size, Vector2 position) {
        super(sprite, world, BodyDef.BodyType.DynamicBody, size, position);
    }

    public void update() {
        tempSpeed.x = sideKeyPressed * Constants.Physics.PlayerMoveSpeed;
        tempSpeed.y = body.getLinearVelocity().y;
        if (ladderContact) {
            tempSpeed.y = ladderClimbing * Constants.Physics.PlayerJumpSpeed / 4f;
        }
        body.setLinearVelocity(tempSpeed);
        super.update();
    }

    public void jumpContactBegin() {
        jumpsLeft = Integer.MAX_VALUE;
    }

    public void jumpContactEnd() {
        jumpsLeft = 1;
    }

    public void ladderContactBegin() {
        body.setGravityScale(0f);
        this.ladderContact = true;
    }

    public void ladderContactEnd() {
        body.setGravityScale(1f);
        this.ladderContact = false;
    }

    public void reverseGravity() {
        body.setGravityScale((-1f) * body.getGravityScale());
    }

    public void trampolineContact() {
        tempSpeed.x = body.getLinearVelocity().x;
        tempSpeed.y = 2 * Constants.Physics.PlayerMoveSpeed;
        body.setLinearVelocity(tempSpeed);
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Player is immortal and cannot be killed.");
    }

    @Override
    public void dispose() {
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
                if (!ladderContact) {
                    if (jumpsLeft > 0) {
                        jumpsLeft--;
                        body.setLinearVelocity(body.getLinearVelocity().x, Constants.Physics.PlayerJumpSpeed);
                    }
                }
                ladderClimbing = 1;
                break;
            case Input.Keys.S:
                ladderClimbing = -1;
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
            case Input.Keys.W:
                ladderClimbing = 0;
                break;
            case Input.Keys.S:
                ladderClimbing = 0;
                break;
        }
        return true;
    }

    @Override
    public String toString() {
        return "player";
    }

    // We don't use other methods right now.
    // They have to be here because Java doesn't support multiple inheritance.
    // Also, LibGDX doesn't adhere to interface segregation principle.

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
