package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import core.utilities.Constants;

public class MovingPlatform extends AbstractPlatform {

    public MovementDirection direction;

    private float speed = 60;
    private boolean reverse = false;

    private float minPosition, maxPosition;

    public MovingPlatform(Sprite sprite, TiledMapTileLayer mapLayer, World world) {
        super(sprite, mapLayer, world);
    }

    public static enum MovementDirection {
        STATIC,
        VERTICAl,
        HORIZONTAL
    }

    public void setMovementBounds(float minPosition, float maxPosition) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    @Override
    public Vector2 update() {
        //TODO: Implement platform movement
        Vector2 position = body.getPosition();
        position.x *= Constants.Physics.Scale;
        position.y *= Constants.Physics.Scale;
        sprite.setPosition(position.x - sprite.getWidth()/2f, position.y - sprite.getHeight()/2f);
        return position;
    }

}
