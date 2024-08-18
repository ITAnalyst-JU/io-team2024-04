package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class AbstractEnemy extends AbstractEntity {

    public AbstractEnemy(Sprite sprite, TiledMapTileLayer mapLayer, World world) {
        super(sprite, mapLayer, world, BodyDef.BodyType.DynamicBody);
    }

    @Override
    public Vector2 update() {
        if(toRemove) {
            remove();
        }
        return super.update();
    }

}
