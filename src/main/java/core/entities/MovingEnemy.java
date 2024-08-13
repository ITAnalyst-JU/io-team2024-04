package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class MovingEnemy extends AbstractEnemy {

    private float minX = 0, maxX = 0;

    protected float xVelocityBase = 30;
    protected float yVelocityBase = 100;

    private Vector2 currentVelocity = new Vector2(0, 0);
    private float yVelocityLimit = 60 * 4;
    private float gravity = 60 * 9;

    public MovingEnemy(Sprite sprite, TiledMapTileLayer mapLayer) {
        super(sprite, mapLayer);
        setSize(mapLayer.getTileWidth(), mapLayer.getTileHeight());
    }

    @Override
    protected void update(float timeDelta) {
        currentVelocity.y -= gravity * timeDelta;

        if (currentVelocity.y < -yVelocityLimit) {
            currentVelocity.y = -yVelocityLimit;
        }
        if (currentVelocity.y > yVelocityLimit) {
            currentVelocity.y = yVelocityLimit;
        }

        float oldX = getX(), newX = oldX + currentVelocity.x * timeDelta, oldY = getY(),
                newY = oldY + currentVelocity.y * timeDelta;

        if (!detectCollisionWithTile(newX, newY, propertyNameCollision)) {
            setX(newX);
            setY(newY);
        } else {
            currentVelocity.y = 0;
            Vector2 newPosition = detectCollisionWithTilePrecise(new Vector2(oldX, oldY), new Vector2(newX, newY),
                    propertyNameCollision);
            setX(newPosition.x);
            setY(newPosition.y);
        }

        if (currentVelocity.y == 0) {
            if (currentVelocity.x == 0 || getX() < minX) {
                currentVelocity.x = xVelocityBase;
            } else if (getX() > maxX) {
                currentVelocity.x = -xVelocityBase;
            }
            currentVelocity.y = yVelocityBase;
        }
    }

    public void setMovementBounds(float minX, float maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

}
