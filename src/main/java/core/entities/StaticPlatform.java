package core.entities;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class StaticPlatform extends AbstractPlatform {
    public StaticPlatform(Sprite sprite, Vector2 position, World world) {
        super(sprite, position, world, BodyDef.BodyType.StaticBody);
    }
}
