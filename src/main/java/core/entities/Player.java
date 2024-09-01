package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.general.Constants;
import core.general.Observer;
import core.general.UserControlsEnum;

public class Player extends SpriteEntity implements Observer<UserControlsEnum> {
    // Does not extend moving entity because movement is different.

    private boolean keyA = false;
    private boolean keyD = false;
    private boolean keyW = false;
    private boolean keyS = false;

    private int jumpsLeft = 0;
    private boolean ladderContact = false;

    private final Vector2 tempSpeed = new Vector2(); // for performance reasons

    public Player(Sprite sprite, World world, Vector2 size, Vector2 position) {
        super(sprite, world, BodyDef.BodyType.DynamicBody, size, position);
        this.type = "player";
    }

    public void resetControls() {
        keyA = false;
        keyD = false;
        keyW = false;
        keyS = false;
    }

    public void update() {
        tempSpeed.x = Constants.Physics.PlayerMoveSpeed * ((keyD ? 1 : 0) - (keyA ? 1 : 0));
        tempSpeed.y = body.getLinearVelocity().y;
        if (ladderContact) {
            tempSpeed.y = (Constants.Physics.PlayerJumpSpeed / 4f) * ((keyW ? 1 : 0) - (keyS ? 1 : 0));
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
                keyA = true;
                break;
            case A_up:
                keyA = false;
                break;
            case D_down:
                keyD = true;
                break;
            case D_up:
                keyD = false;
                break;
            case W_down:
                keyW = true;
                if (!ladderContact) {
                    if (jumpsLeft > 0) {
                        jumpsLeft--;
                        body.setLinearVelocity(body.getLinearVelocity().x, Constants.Physics.PlayerJumpSpeed);
                    }
                }
                break;
            case W_up:
                keyW = false;
                break;
            case S_down:
                keyS = true;
                break;
            case S_up:
                keyS = false;
                break;
            default:
                break;
        }
    }
}
