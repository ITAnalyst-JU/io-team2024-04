package core.entities.decorators;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import core.entities.IEntity;

public class SpriteDecorator extends BaseEntityDecorator {
    private Sprite sprite;

    public SpriteDecorator(IEntity wrapped, Sprite sprite, Vector2 size) {
        super(wrapped);
        this.sprite = sprite;
        sprite.setSize(size.x, size.y);
    }

    @Override
    public void update() {
        wrapped.update();
        Vector2 position = this.getPosition();
        sprite.setPosition(position.x - sprite.getWidth()/2f, position.y - sprite.getHeight()/2f);
    };


    @Override
    public void draw(Batch batch) {
        wrapped.draw(batch);
        sprite.draw(batch);
    }
}
