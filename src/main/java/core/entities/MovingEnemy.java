package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import core.utilities.Constants;

public class MovingEnemy extends AbstractEnemy {

    private final float maxSpeed = 3f;
    private final float speedDelta = 1f;

    private float minX = 0, maxX = 0;
    private boolean movingRight = true;

    public MovingEnemy(Sprite sprite, TiledMapTileLayer mapLayer, World world) {
        super(sprite, mapLayer, world);
        body.setLinearVelocity(maxSpeed, 0);
    }

    public void setMovementBounds(float minX, float maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public Vector2 update() {
        if (body.getPosition().x < minX) {
            movingRight = true;
        } else if (body.getPosition().x > maxX) {
            movingRight = false;
        }
        if(movingRight) {
            if(body.getLinearVelocity().x < maxSpeed)
                body.setLinearVelocity(body.getLinearVelocity().x + speedDelta, body.getLinearVelocity().y);
        } else {
            if(body.getLinearVelocity().x > -maxSpeed)
                body.setLinearVelocity(body.getLinearVelocity().x - speedDelta, body.getLinearVelocity().y);
        }
        return super.update();
    }

}
