package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractEntity extends Sprite {

    public static final String propertyNameCollision = "hasCollision";
    public static final String propertyNameFinishing = "isFinishing";

    private TiledMapTileLayer mapLayer;

    public AbstractEntity(Sprite sprite, TiledMapTileLayer mapLayer) {
        super(sprite);
        this.mapLayer = mapLayer;
    }

    private boolean checkCellProperty(float x, float y, String property) throws NullPointerException {
        return mapLayer.getCell((int) (x / mapLayer.getTileWidth()), (int) (y / mapLayer.getTileHeight())).getTile().getProperties().containsKey(property);
    }

    protected boolean detectCollisionWithTile(float x, float y, String property) {
        if (checkCellProperty(x, y, property) || checkCellProperty(x, y + getHeight(), property) || checkCellProperty(x + getWidth(), y, property) || checkCellProperty(x + getWidth(), y + getHeight(), property)) {
            return true;
        }
        return false;
    }

    protected Vector2 detectCollisionWithTilePrecise(Vector2 oldPosition, Vector2 newPosition, String property) {
        //TODO: return precise location of where can you move (by checking pixel by pixel).
        if (detectCollisionWithTile(newPosition.x, newPosition.y, property)) {
            return oldPosition;
        } else {
            return newPosition;
        }
    }
}
