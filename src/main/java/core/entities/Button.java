package core.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Button extends BodyOnlyEntity {
    private final int number;

    public Button(World world, BodyDef.BodyType bodyType, Vector2 size, Vector2 position, int number) {
        super(world, bodyType, size, position);
        this.number = number;
        this.type = "button";
    }

    public int getNumber() {
        return number;
    }
}
