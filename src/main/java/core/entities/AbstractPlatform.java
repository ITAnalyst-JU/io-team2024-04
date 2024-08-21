package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public abstract class AbstractPlatform extends AbstractEntity {

    public AbstractPlatform(Sprite sprite, Vector2 position, World world) {
        super(sprite, position, world, BodyType.KinematicBody);
    }
    
}
