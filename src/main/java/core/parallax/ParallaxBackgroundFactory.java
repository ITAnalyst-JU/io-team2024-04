package core.parallax;

import com.badlogic.gdx.graphics.Pixmap;

import java.util.Arrays;

public class ParallaxBackgroundFactory {
    public static ParallaxBackground createParallaxBackgroundScrolling(Pixmap[] pixmaps, ScreenHook screenHook) {
        return new ParallaxBackground(prepareParallaxLayers(pixmaps, screenHook), screenHook, true);
    }

    public static ParallaxBackground createParallaxBackgroundInteractive(Pixmap[] pixmaps, ScreenHook screenHook) {
        return new ParallaxBackground(prepareParallaxLayers(pixmaps, screenHook), screenHook, false);
    }

    private static ParallaxLayer[] prepareParallaxLayers(Pixmap[] pixmaps, ScreenHook screenHook) {
        final float[] prevFactor = {0.f};
        final float[] currFactor = {0.1f};
        return Arrays.stream(pixmaps)
                .map(pixmap -> {
                    ParallaxLayer layer = new ParallaxLayer(pixmap, currFactor[0], true, true, screenHook);
                    float temp = currFactor[0];
                    currFactor[0] += prevFactor[0];
                    prevFactor[0] = temp;
                    return layer;
                })
                .toArray(ParallaxLayer[]::new);
    }
}
