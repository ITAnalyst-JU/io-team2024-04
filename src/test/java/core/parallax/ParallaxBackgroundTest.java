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

    @Test
    public void testActScrolling() {
        var layer = mock(ParallaxLayer.class);
        var screenHook = new ScreenHook(0, 0, 13, 100);
        var background = new ParallaxBackground(new ParallaxLayer[]{layer, layer, layer}, screenHook, true);

        background.act(20.f);

        assertThat(screenHook.centerX()).isEqualTo((20 * 100) % 13);
    }

    @Test
    public void testActNotScrolling() {
        var layer = mock(ParallaxLayer.class);
        var screenHook = new ScreenHook(0, 0, 13, 100);
        var background = new ParallaxBackground(new ParallaxLayer[]{layer, layer, layer}, screenHook, false);

        background.act(20.f);

        assertThat(screenHook.centerX()).isEqualTo(0);
        assertThat(screenHook.centerY()).isEqualTo(0);
        assertThat(screenHook.width()).isEqualTo(13);
        assertThat(screenHook.height()).isEqualTo(100);
    }

    @Test
    public void testResizeHook() {
        var layer = mock(ParallaxLayer.class);
        var screenHook = new ScreenHook(0, 0, 13, 100);
        var background = new ParallaxBackground(new ParallaxLayer[]{layer, layer, layer}, screenHook, false);

        background.resizeHook(10, 20);

        assertThat(screenHook.width()).isEqualTo(10);
        assertThat(screenHook.height()).isEqualTo(20);
        verify(layer, times(3)).resize(screenHook);
    }
}
