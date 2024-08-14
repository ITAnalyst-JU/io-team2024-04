package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;

public abstract class AbstractPlatform extends AbstractEntity {

    public AbstractPlatform(Sprite sprite, TiledMapTileLayer mapLayer, World world) {
        super(sprite, mapLayer, world, true);
    }
    
}
