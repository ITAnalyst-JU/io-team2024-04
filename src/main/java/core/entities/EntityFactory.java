package core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.general.Constants;

import java.util.HashMap;
import java.util.Map;

public class EntityFactory {
    private final Vector2 baseEntitySize;
    private final World world;

    private final Map<Integer, ButtonAction> buttonsMap = new HashMap<>();

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

        boolean userDamagable = false;
        if ((objProperties.containsKey("isUserDamageable") && (boolean)objProperties.get("isUserDamageable"))
                || (!objProperties.containsKey("isUserDamageable") && objProperties.containsKey("lives"))) {
            userDamagable = true;
        }

        String texturePath;
        if (objProperties.get("type").equals("platform")) {
            if (!userDamagable) {
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

            entity = new Platform(new Sprite(new Texture(texturePath)), world, movementDirection, size, position, userDamagable);
        } else if (objProperties.get("type").equals("enemy")) {
            entity = new Enemy(new Sprite(new Texture(texturePath)), world, movementDirection, size, position);
        } else {
            throw new UnsupportedOperationException("Unknown object name: " + objProperties.get("type"));
        }
        if (objProperties.containsKey("lives")) {
            (entity).setLife((int)objProperties.get("lives"));
        }

        switch(movementDirection) {
            case HORIZONTAL:
                entity.setMovementBounds((rectangle.getX() + size.x / 2f) / Constants.Physics.Scale, (rectangle.getX() - size.x / 2f + rectangle.getWidth()) / Constants.Physics.Scale);
                break;
            case VERTICAL:
                entity.setMovementBounds((rectangle.getY() + size.y / 2f) / Constants.Physics.Scale, (rectangle.getY() - size.y / 2f + rectangle.getHeight()) / Constants.Physics.Scale);
                break;
            case STATIC:
                break;
        }

        if (objProperties.containsKey("number")) {
            int number = (int)objProperties.get("number");
            buttonsMap.put(number, (ButtonAction)entity);
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
        String type = (String)mapObject.getProperties().get("type");
        BodyEntity entity;
        if ("button".equals(type)) {
            int number = (int)mapObject.getProperties().get("number");
            entity = new Button(world, BodyDef.BodyType.StaticBody, size, position, number);
        } else {
            entity = new BodyEntity(world, BodyDef.BodyType.StaticBody, size, position);
            entity.setType(type); //using casting to catch type errors
        }
        return entity;
    }

    public Map<Integer, ButtonAction> getButtonsMap() {
        return buttonsMap;
    }
}
