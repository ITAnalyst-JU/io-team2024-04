package core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.utilities.Constants;

public class EntityFactory {
    private final Vector2 baseEntitySize;
    private final World world;

    public EntityFactory(Vector2 baseEntitySize, World world) {
        this.baseEntitySize = baseEntitySize;
        this.world = world;
    }

    public AbstractEntity getAbstractEntity(MapObject mapObject) {
        if (! (mapObject instanceof RectangleMapObject)) {
            throw new UnsupportedOperationException("We only produce from rectangles");
        }
        RectangleMapObject obj = (RectangleMapObject) mapObject;
        var objProperties = obj.getProperties();

        String texturePath;
        if (objProperties.get("type").equals("platform")) {
            if (!objProperties.containsKey("lives")) {
                texturePath = "entities/platform.png";
            } else {
                texturePath = "entities/platform2.png";
            }
        } else if (objProperties.get("type").equals("enemy")) {
            texturePath = "entities/enemy.png";
        } else {
            throw new UnsupportedOperationException("Unknown object name: " + objProperties.get("type"));
        }
        Vector2 size = new Vector2(baseEntitySize);
        System.out.println(objProperties.get("width"));
        if (objProperties.get("width") instanceof Integer) { // We have key with 0.0f instead of no key because Java or sth
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

        Vector2 position = new Vector2();
        position.x = (rectangle.getX() + rectangle.getWidth()/2f);
        position.y = (rectangle.getY() + rectangle.getHeight()/2f);

        AbstractMovingEntity entity;
        if (objProperties.get("type").equals("platform")) {
            entity = new Platform(new Sprite(new Texture(texturePath)), world, movementDirection, size, position);
            if (objProperties.containsKey("lives")) {
                ((Platform)entity).setLife((int)objProperties.get("lives"));
            }
        } else if (objProperties.get("type").equals("enemy")) {
            entity = new Enemy(new Sprite(new Texture(texturePath)), world, movementDirection, size, position);
        } else {
            throw new UnsupportedOperationException("Unknown object name: " + objProperties.get("type"));
        }

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

    public BodyEntity getBodyEntity (MapObject mapObject) {
        if (! (mapObject instanceof RectangleMapObject)) {
            throw new UnsupportedOperationException("We only produce from rectangles");
        }
        Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
        Vector2 size = new Vector2(rectangle.getWidth(), rectangle.getHeight());
        Vector2 position = new Vector2();
        position.x = (rectangle.getX() + rectangle.getWidth()/2f);
        position.y = (rectangle.getY() + rectangle.getHeight()/2f);
        BodyEntity entity = new BodyEntity(world, BodyDef.BodyType.StaticBody, size, position);
        entity.setType((String)mapObject.getProperties().get("type")); //using casting to catch type errors
        return entity;
    }
}
