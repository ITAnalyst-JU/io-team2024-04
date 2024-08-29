package core.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class AbstractEntity extends BodyEntity{

    protected final Sprite sprite;
    protected int life;

    public AbstractEntity(Sprite sprite, World world, BodyDef.BodyType bodyType, Vector2 size, Vector2 position) {
        super(world, bodyType, size, position);
        this.sprite = sprite;
        sprite.setSize(size.x, size.y);
        this.life = Integer.MAX_VALUE;
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

    @Override
    public void saveState() {
        super.saveState();
        state.put("life", life);
    }

    @Override
    public void recoverState() {
        super.recoverState();
        life = (int)state.get("life");
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean damage() {
        life--;
        return (life == 0);
    }
}
