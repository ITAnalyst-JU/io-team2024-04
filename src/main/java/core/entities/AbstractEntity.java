package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import core.utilities.Constants;
import core.utilities.Constants.Physics;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEntity extends BodyEntity{
    protected final Sprite sprite;

    public AbstractEntity(Sprite sprite, World world, BodyDef.BodyType bodyType, Vector2 size, Vector2 position) {
        super(world, bodyType, size, position);
        this.sprite = sprite;
        sprite.setSize(size.x, size.y);
    }

    @Override
    public void update() {
        Vector2 position = this.getPosition();
        sprite.setPosition(position.x - sprite.getWidth()/2f, position.y - sprite.getHeight()/2f);
    };

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
