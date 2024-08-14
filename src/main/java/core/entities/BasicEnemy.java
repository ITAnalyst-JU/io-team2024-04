package core.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class BasicEnemy extends AbstractEnemy {

    private final Vector2 currentVelocity = new Vector2(0, 0);
    private final float yVelocityLimit = 60 * 4;
    private final float gravity = 60 * 9;

    public BasicEnemy(Sprite sprite, TiledMapTileLayer mapLayer) {
        super(sprite, mapLayer);
        setSize(mapLayer.getTileWidth(), mapLayer.getTileHeight());
    }

    @Override
    protected void update(float timeDelta) {
        // currentVelocity.y -= gravity * timeDelta;

        // if (currentVelocity.y < -yVelocityLimit) {
        //     currentVelocity.y = -yVelocityLimit;
        // }
        // if (currentVelocity.y > yVelocityLimit) {
        //     currentVelocity.y = yVelocityLimit;
        // }

        // float oldX = getX(), newX = oldX + currentVelocity.x * timeDelta, oldY = getY(),
        //         newY = oldY + currentVelocity.y * timeDelta;

        // if (!detectCollisionWithTile(newX, newY, propertyNameCollision)) {
        //     setX(newX);
        //     setY(newY);
        // } else {
        //     currentVelocity.y = 0;
        //     Vector2 newPosition = detectCollisionWithTilePrecise(new Vector2(oldX, oldY), new Vector2(newX, newY),
        //             propertyNameCollision);
        //     setX(newPosition.x);
        //     setY(newPosition.y);
        // }
    }
}
