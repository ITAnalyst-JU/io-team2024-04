package core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.assets.IAssetManager;
import core.assets.IAssetManagerGetter;
import core.entities.decorators.*;
import core.general.Constants;

import java.util.HashMap;
import java.util.Map;

public class EntityFactory {
    private final Vector2 baseEntitySize;
    private final World world;
    private final IAssetManagerGetter assetManager;

    private final Map<Integer, IEntity> buttonsMap = new HashMap<>();

    public EntityFactory(Vector2 baseEntitySize, World world, IAssetManagerGetter assetManager) {
        this.baseEntitySize = baseEntitySize;
        this.world = world;
        this.assetManager = assetManager;
    }

    public Map<Integer, IEntity> getButtonsMap() {
        return buttonsMap;
    }

    public IEntity getEntity(MapObject mapObject, boolean visible) {
        if (! (mapObject instanceof RectangleMapObject)) {
            throw new UnsupportedOperationException("We only produce from rectangles");
        }
        RectangleMapObject rectangleMapObject = (RectangleMapObject)mapObject;
        Rectangle rectangle = rectangleMapObject.getRectangle();
        MapProperties objProperties = rectangleMapObject.getProperties();

        Vector2 size;
        if (visible) {
            size = new Vector2(baseEntitySize);
            if (objProperties.get("width") instanceof Integer) { // We have key with 0.0f instead of no key because Java or sth
                size.x *= (int) objProperties.get("width");
            }
        } else {
            size = new Vector2(rectangle.getWidth(), rectangle.getHeight());
        }

        Vector2 position = new Vector2();
        position.x = (rectangle.getX() + rectangle.getWidth()/2f);
        position.y = (rectangle.getY() + rectangle.getHeight()/2f);

        IEntity entity = new BaseEntity(world, size, position);

        String type = (String)mapObject.getProperties().get("type");
        entity = new GenericTypeDecorator(entity, type);

        if ("button".equals(type)) {
            int number = (int)objProperties.get("number");
            entity = new ButtonDecorator(entity, number);
        }
        if (!visible) {
            entity.getBody().getFixtureList().get(0).setUserData(entity);
            return entity;
        }

        boolean userDamageable = false;
        if ((objProperties.containsKey("isUserDamageable") && (boolean)objProperties.get("isUserDamageable"))
                || (!objProperties.containsKey("isUserDamageable") && objProperties.containsKey("lives"))) {
            entity = new UserDamageableDecorator(entity);
            userDamageable = true;
        }

        String texturePath;
        if ("platform".equals(type)) {
            if (!userDamageable) {
                texturePath = "entities/platform.png";
            } else {
                texturePath = "entities/platform2.png";
            }
        } else if ("enemy".equals(type)) {
            texturePath = "entities/enemy.png";
        } else {
            throw new UnsupportedOperationException("Unknown object name: " + type);
        }
        Texture texture = assetManager.getTexture(texturePath);
        entity = new SpriteDecorator(entity, new Sprite(texture), size);

        MovingDecorator.MovementDirection movementDirection;
        Vector2 movementBounds = new Vector2();
        if (rectangle.getHeight() == 0 && rectangle.getWidth() == 0) { // A point
            movementDirection = null;
        } else if (rectangle.getHeight() > rectangle.getWidth()) { // vertical
            movementDirection = MovingDecorator.MovementDirection.VERTICAL;
            movementBounds.x = (rectangle.getY() + size.y / 2f) / Constants.Physics.Scale;
            movementBounds.y = (rectangle.getY() - size.y / 2f + rectangle.getHeight()) / Constants.Physics.Scale;
        } else { // horizontal
            movementDirection = MovingDecorator.MovementDirection.HORIZONTAL;
            movementBounds.x = (rectangle.getX() + size.x / 2f) / Constants.Physics.Scale;
            movementBounds.y = (rectangle.getX() - size.x / 2f + rectangle.getWidth()) / Constants.Physics.Scale;
        }
        if (movementDirection != null) {
            entity = new MovingDecorator(entity, movementDirection, movementBounds);
        }

        if (objProperties.containsKey("lives")) {
            entity = new DamageableDecorator(entity, (int)objProperties.get("lives"));
        }

        if (objProperties.containsKey("number")) {
            int number = (int)objProperties.get("number");
            entity = new ButtonActionDamageDecorator(entity);
            buttonsMap.put(number, entity);
        }

        entity.getBody().getFixtureList().get(0).setUserData(entity);
        return entity;
    }

    public Player getPlayer(MapObject mapObject) {
        if (! (mapObject instanceof RectangleMapObject)) {
            throw new UnsupportedOperationException("We only produce from rectangles");
        }
        IEntity entity = new BaseEntity(world, baseEntitySize, ((RectangleMapObject)mapObject).getRectangle().getPosition(new Vector2()));
        Texture texture = assetManager.getTexture("player/player.png");
        entity = new SpriteDecorator(entity, new Sprite(texture), baseEntitySize);

        Player player = new Player(entity);

        player.getBody().getFixtureList().get(0).setUserData(player);

        return player;
    }
}
