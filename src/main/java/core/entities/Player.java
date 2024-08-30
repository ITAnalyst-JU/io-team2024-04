package core.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.general.Constants;
import core.general.Observer;

public class Player extends SpriteEntity implements Observer<UserControlsEnum> {
    // Does not extend moving entity because movement is different.

    private int sideKeyPressed = 0;
    private int jumpsLeft = 0;
    private boolean ladderContact = false;
    private int ladderClimbing = 0;

    private final Vector2 tempSpeed = new Vector2(); // for performance reasons

    public Player(Sprite sprite, World world, Vector2 size, Vector2 position) {
        super(sprite, world, BodyDef.BodyType.DynamicBody, size, position);
        this.type = "player";
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
    public void respondToEvent(UserControlsEnum event) {
        switch (event) {
            case A_down:
            case D_up:
                this.sideKeyPressed--;
                break;
            case A_up:
            case D_down:
                this.sideKeyPressed++;
                break;
            case W_down:
                if (!ladderContact) {
                    if (jumpsLeft > 0) {
                        jumpsLeft--;
                        body.setLinearVelocity(body.getLinearVelocity().x, Constants.Physics.PlayerJumpSpeed);
                    }
                }
                ladderClimbing = 1;
                break;
            case S_down:
                ladderClimbing = -1;
                break;
            case W_up:
            case S_up:
                ladderClimbing = 0;
                break;

            default:
                break;
        }
    }
}
