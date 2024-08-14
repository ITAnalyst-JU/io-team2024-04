package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MovingPlatform extends AbstractPlatform {

    public MovementDirection direction;

    private final float speed = 60;
    private boolean reverse = false;

    private float minPosition, maxPosition;

    public MovingPlatform(Sprite sprite, TiledMapTileLayer mapLayer, MovementDirection direction) {
        super(sprite, mapLayer);
        this.direction = direction;
        setSize(mapLayer.getTileWidth(), mapLayer.getTileHeight());
    }

    public enum MovementDirection {
        Static,
        Vertical,
        Horizontal
    }

    public void setMovementBounds(float minPosition, float maxPosition) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    @Override
    protected void update(float timeDelta) {
        if(direction == MovementDirection.Static) {
        }
        else if (direction == MovementDirection.Vertical) {
            if (getY() >= maxPosition) {
                reverse = true;
            }
            else if (getY() <= minPosition) {
                reverse = false;
            }

            if (reverse) {
                setY(getY() - speed * timeDelta);
            }
            else {
                setY(getY() + speed * timeDelta);
            }
        }
        else if (direction == MovementDirection.Horizontal) {
            if (getX() >= maxPosition) {
                reverse = true;
            }
            else if (getX() <= minPosition) {
                reverse = false;
            }

            if (reverse) {
                setX(getX() - speed * timeDelta);
            }
            else {
                setX(getX() + speed * timeDelta);
            }
        }
    }
    
}
