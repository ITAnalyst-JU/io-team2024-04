package core.parallax;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ParallaxBackgroundTest {
    @Test
    public void testDraw() {
        var layer = mock(ParallaxLayer.class);;
        var screenHook = mock(ScreenHook.class);
        var background = new ParallaxBackground(new ParallaxLayer[]{layer, layer, layer}, screenHook, false);
        var batch = mock(Batch.class);
        background.draw(batch, 0);
        verify(layer, times(3)).draw(batch, screenHook);
    }

    // TODO rest of the tests
}
