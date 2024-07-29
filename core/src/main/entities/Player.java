package main.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

    private Vector2 currentVelocity = new Vector2(0, 0);

    private float yVelocityLimit = 60 * 4;
    private float gravity = 60 * 9;
    private float xVelocityBase = 60 * 2;

    private boolean rightKeyPressed = false, leftKeyPressed = false;

    private TiledMapTileLayer mapLayer;

    public Player(Sprite sprite, TiledMapTileLayer mapLayer) {
        super(sprite);
        setSize(mapLayer.getTileWidth(), mapLayer.getTileHeight());
        this.mapLayer = mapLayer;
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

        currentVelocity.x = 0;
        if (rightKeyPressed) {
            currentVelocity.x += xVelocityBase;
        }
        if (leftKeyPressed) {
            currentVelocity.x -= xVelocityBase;
        }

        float newX = getX() + currentVelocity.x * timeDelta;
        float newY = getY() + currentVelocity.y * timeDelta;

        if (!isCellBlocked(newX, newY) && !isCellBlocked(newX, newY+getHeight()) && !isCellBlocked(newX+getWidth(), newY) && !isCellBlocked(newX+getWidth(), newY+getHeight())) {
            setX(newX);
            setY(newY);
        } else {
            currentVelocity.y = 0;
        }

//        setX(getX() + currentVelocity.x * timeDelta);
//        setY(getY() + currentVelocity.y * timeDelta);
    }

    // Collision detection may instead use layers.
    private boolean isCellBlocked(float x, float y) {
        try {
            return mapLayer.getCell((int) (x / mapLayer.getTileWidth()), (int) (y / mapLayer.getTileHeight())).getTile().getProperties().containsKey("hasCollision");
        } catch (NullPointerException e) {
            return true;
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
