package core.parallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParallaxBackground extends Actor {
    private final ParallaxLayer[] layers;
    private final float PARALLAX_SPEED = 100.f;

    private final ScreenHook screenHook;

    private final boolean autoScrolling;
    public ParallaxBackground(ParallaxLayer[] layers, ScreenHook screenHook, boolean autoScrolling) {
        this.layers = layers;
        this.screenHook = screenHook;
        this.autoScrolling = autoScrolling;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (ParallaxLayer layer : layers) {
            layer.draw(batch, screenHook);
        }
    }

    @Override
    public void act(float delta) {
        if (autoScrolling) {
            screenHook.setCenterX(screenHook.centerX() + (int) (PARALLAX_SPEED * delta) % screenHook.width());
        }
    }

    public void resizeHook(int width, int height) {
        screenHook.setWidth(width);
        screenHook.setHeight(height);
        for (ParallaxLayer layer : layers) {
            layer.resize(screenHook);
        }
    }
}
