package main.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

    private Vector2 currentVelocity = new Vector2(0, 0);

    private float yVelocityLimit = 60 * 3;
    private float gravity = 60 * 1.8f;

    private float xVelocityBase = 60 * 4;

    private int xKeyPressed = 0;

    public Player(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    private void update(float timeDelta) {
        currentVelocity.y -= gravity * timeDelta;

        if (currentVelocity.y < -yVelocityLimit) {
            currentVelocity.y = -yVelocityLimit;
        }
        if (currentVelocity.y > yVelocityLimit) {
            currentVelocity.y = yVelocityLimit;
        }

        currentVelocity.x = xKeyPressed * xVelocityBase;


        setX(getX() + currentVelocity.x * timeDelta);
        setY(getY() + currentVelocity.y * timeDelta);
    }


    @Override
    public boolean keyDown(int keyNo) {
        switch (keyNo) {
            case Input.Keys.A:
                xKeyPressed = -1;
                break;
            case Input.Keys.D:
                xKeyPressed = 1;
                break;
            case Input.Keys.W:
                currentVelocity.y = yVelocityLimit;
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keyNo) {
        switch (keyNo) {
            case Input.Keys.A:
            case Input.Keys.D:
                xKeyPressed = 0;
                break;

        }
        return true;
    }

    // We don't use other methods right now.
    // They have to be here because Java doesn't support inheritance after multiple classes.

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
