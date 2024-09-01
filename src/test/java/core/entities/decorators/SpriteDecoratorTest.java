package core.entities.decorators;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import core.entities.IEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SpriteDecoratorTest {

    @Test
    public void testConstructor() {
        IEntity wrapped = Mockito.mock(IEntity.class);
        Sprite sprite = Mockito.mock(Sprite.class);
        Vector2 size = new Vector2(5, 4);

        IEntity entity = new SpriteDecorator(wrapped, sprite, size);

        Mockito.verify(sprite).setSize(5, 4);
        Mockito.verifyNoMoreInteractions(wrapped, sprite);
    }

    @Test
    void testUpdate() {
        IEntity wrapped = Mockito.mock(IEntity.class);
        Sprite sprite = Mockito.mock(Sprite.class);
        Vector2 size = new Vector2(4, 4);
        IEntity entity = new SpriteDecorator(wrapped, sprite, size);
        Mockito.when(wrapped.getPosition()).thenReturn(new Vector2(10, 10));
        Mockito.when(sprite.getWidth()).thenReturn(4f);
        Mockito.when(sprite.getHeight()).thenReturn(4f);

        entity.update();

        Mockito.verify(sprite).setSize(4, 4);
        Mockito.verify(wrapped).update();
        Mockito.verify(wrapped).getPosition();
        Mockito.verify(sprite).getWidth();
        Mockito.verify(sprite).getHeight();
        Mockito.verify(sprite).setPosition(8, 8);
        Mockito.verifyNoMoreInteractions(wrapped, sprite);
    }

    @Test
    void testDraw() {
        IEntity wrapped = Mockito.mock(IEntity.class);
        Sprite sprite = Mockito.mock(Sprite.class);
        Vector2 size = new Vector2(4, 4);
        IEntity entity = new SpriteDecorator(wrapped, sprite, size);
        Batch batch = Mockito.mock(Batch.class);

        entity.draw(batch);

        Mockito.verify(sprite).setSize(4, 4);
        Mockito.verify(wrapped).draw(batch);
        Mockito.verify(sprite).draw(batch);
        Mockito.verifyNoMoreInteractions(wrapped, sprite);
    }
}