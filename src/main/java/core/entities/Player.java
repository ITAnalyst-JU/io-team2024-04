package core.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends AbstractEntity implements InputProcessor {

    private Vector2 currentVelocity = new Vector2(0, 0);
    private float yVelocityLimit = 60 * 4;
    private float gravity = 60 * 9;
    private float xVelocityBase = 60 * 2;

    private boolean rightKeyPressed = false, leftKeyPressed = false;

    private boolean didGameFinish = false;

    public Player(Sprite sprite, TiledMapTileLayer mapLayer) {
        super(sprite, mapLayer);
        setSize(mapLayer.getTileWidth(), mapLayer.getTileHeight());
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public boolean ifLevelFinished() {
        return didGameFinish;
    }

    private void update(float timeDelta) {
        currentVelocity.y -= gravity * timeDelta;

        if (currentVelocity.y < -yVelocityLimit) {
            currentVelocity.y = -yVelocityLimit;
        }
        if (currentVelocity.y > yVelocityLimit) {
            currentVelocity.y = yVelocityLimit;
        }

        currentVelocity.x = 0;
        if (rightKeyPressed) {
            currentVelocity.x += xVelocityBase;
        }
        if (leftKeyPressed) {
            currentVelocity.x -= xVelocityBase;
        }

        float oldX = getX(), newX = oldX + currentVelocity.x * timeDelta, oldY = getY(), newY = oldY + currentVelocity.y * timeDelta;

        if (detectCollisionWithTile(newX, newY, propertyNameFinishing)) {
            this.didGameFinish = true;
        }


        if (!detectCollisionWithTile(newX, newY, propertyNameCollision)) {
            setX(newX);
            setY(newY);
        } else {
            currentVelocity.y = 0;
            Vector2 newPosition = detectCollisionWithTilePrecise(new Vector2(oldX, oldY), new Vector2(newX, newY), propertyNameCollision);
            setX(newPosition.x);
            setY(newPosition.y);
        }
    }

    @Override
    public boolean keyDown(int keyNo) {
        switch (keyNo) {
            case Input.Keys.A:
                leftKeyPressed = true;
                break;
            case Input.Keys.D:
                rightKeyPressed = true;
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
                leftKeyPressed = false;
                break;
            case Input.Keys.D:
                rightKeyPressed = false;
                break;

        }
        return true;
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
