package core.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public abstract class AbstractPlatform extends AbstractEntity {

    // Is this class even necessary? What is common between static and moving platforms, which isn't automatically
    // loaded or common with other

    public AbstractPlatform(Sprite sprite, Vector2 position, World world, BodyType bodyType) {
        super(sprite, position, world, bodyType);
    }
    
}
