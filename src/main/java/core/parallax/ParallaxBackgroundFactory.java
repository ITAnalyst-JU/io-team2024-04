package core.parallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.Arrays;

public class ParallaxBackgroundFactory {
    public static ParallaxBackground createParallaxBackgroundScrolling(Camera camera, Pixmap[] pixmaps) {
        return new ParallaxBackground(prepareParallaxLayers(camera, pixmaps), camera, true);
    }

    public static ParallaxBackground createParallaxBackgroundInteractive(Camera camera, Pixmap[] pixmaps) {
        return new ParallaxBackground(prepareParallaxLayers(camera, pixmaps), camera, false);
    }

    private static ParallaxLayer[] prepareParallaxLayers(Camera camera, Pixmap[] pixmaps) {
        final float[] prevFactor = {0.f};
        final float[] currFactor = {0.1f};
        return Arrays.stream(pixmaps)
                .map(pixmap -> {
                    ParallaxLayer layer = new ParallaxLayer(pixmap, currFactor[0], true, true, camera);
                    float temp = currFactor[0];
                    currFactor[0] += prevFactor[0];
                    prevFactor[0] = temp;
                    return layer;
                })
                .toArray(ParallaxLayer[]::new);
    }
}
