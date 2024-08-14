package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import core.utilities.Constants;

public class MovingEnemy extends AbstractEnemy {

    private float minX = 0, maxX = 0;

    public MovingEnemy(Sprite sprite, TiledMapTileLayer mapLayer, World world) {
        super(sprite, mapLayer, world);
    }

    public void setMovementBounds(float minX, float maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public Vector2 update() {
        //TODO: Implement enemy movement
        Vector2 position = body.getPosition();
        position.x *= Constants.Physics.Scale;
        position.y *= Constants.Physics.Scale;
        sprite.setPosition(position.x - sprite.getWidth()/2f, position.y - sprite.getHeight()/2f);
        return position;
    }

}
