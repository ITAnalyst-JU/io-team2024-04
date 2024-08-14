package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import core.utilities.Constants;

public class BasicEnemy extends AbstractEnemy {

    public BasicEnemy(Sprite sprite, TiledMapTileLayer mapLayer, World world) {
        super(sprite, mapLayer, world);
    }

    @Override
    public Vector2 update() {
        Vector2 position = body.getPosition();
        position.x *= Constants.Physics.Scale;
        position.y *= Constants.Physics.Scale;
        sprite.setPosition(position.x - sprite.getWidth()/2f, position.y - sprite.getHeight()/2f);
        return position;
    }
}
