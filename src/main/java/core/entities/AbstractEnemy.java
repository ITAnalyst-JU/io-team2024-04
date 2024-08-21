package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class AbstractEnemy extends AbstractEntity {

    public AbstractEnemy(Sprite sprite, Vector2 size, World world, BodyDef.BodyType bodyType) {
        super(sprite, size, world, bodyType);
    }

    @Override
    public void update() {
        if(toRemove) {
            remove();
        }
        super.update();
    }

}
