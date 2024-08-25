package core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.utilities.Constants;

public class EntityFactory {
    private final Vector2 baseEntitySize;
    private final World world;

    public EntityFactory(Vector2 baseEntitySize, World world) {
        this.baseEntitySize = baseEntitySize;
        this.world = world;
    }

    public AbstractEntity getEntity (MapObject mapObject) {
        if (! (mapObject instanceof RectangleMapObject)) {
            throw new UnsupportedOperationException("We only produce from rectangles");
        }
        RectangleMapObject obj = (RectangleMapObject) mapObject;
        var objProperties = obj.getProperties();

        String texturePath;
        if (objProperties.get("type").equals("platform")) {
            texturePath = "entities/platform.png";
        } else if (objProperties.get("type").equals("enemy")) {
            texturePath = "entities/enemy.png";
        } else {
            throw new UnsupportedOperationException("Unknown object name: " + objProperties.get("type"));
        }
        Vector2 size = new Vector2(baseEntitySize);
        if (objProperties.get("width") instanceof Integer) { //Instead of no key, map editor creates key with 0.0f, and casting created exceptions because Java
            size.x *= (int) objProperties.get("width");
        }

        Rectangle rectangle = obj.getRectangle();
        AbstractMovingEntity.MovementDirection movementDirection;
        if (rectangle.getHeight() == 0 && rectangle.getWidth() == 0) { // A point
            movementDirection = AbstractMovingEntity.MovementDirection.STATIC;
        } else if (rectangle.getHeight() > rectangle.getWidth()) { // vertical
            movementDirection = AbstractMovingEntity.MovementDirection.VERTICAL;
        } else { // horizontal
            movementDirection = AbstractMovingEntity.MovementDirection.HORIZONTAL;
        }

        AbstractMovingEntity entity;
        if (objProperties.get("type").equals("platform")) {
            entity = new Platform(new Sprite(new Texture(texturePath)), size, world, movementDirection);
        } else if (objProperties.get("type").equals("enemy")) {
            entity = new Enemy(new Sprite(new Texture(texturePath)), size, world, movementDirection);
        } else {
            throw new UnsupportedOperationException("Unknown object name: " + objProperties.get("type"));
        }

        Vector2 position = new Vector2();
        position.x = (rectangle.getX() + rectangle.getWidth()/2f) / Constants.Physics.Scale;
        position.y = (rectangle.getY() + rectangle.getHeight()/2f) / Constants.Physics.Scale;
        entity.setPosition(position, true);
        switch(movementDirection) {
            case HORIZONTAL:
                entity.setMovementBounds(rectangle.getX() / Constants.Physics.Scale, (rectangle.getX() + rectangle.getWidth()) / Constants.Physics.Scale);
                break;
            case VERTICAL:
                entity.setMovementBounds(rectangle.getY() / Constants.Physics.Scale, (rectangle.getY() + rectangle.getHeight()) / Constants.Physics.Scale);
                break;
            case STATIC:
                break;
        }
        return entity;
    }
}
