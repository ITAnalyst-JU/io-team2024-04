package core.parallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParallaxBackground extends Actor {
    private final ParallaxLayer[] layers;
    private final Camera camera;
    private final float PARALLAX_SPEED = 100.f;

    private final boolean autoScrolling;
    public ParallaxBackground(ParallaxLayer[] layers, Camera camera, boolean autoScrolling) {
        this.layers = layers;
        this.camera = camera;
        this.autoScrolling = autoScrolling;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setProjectionMatrix(camera.combined);
        for (ParallaxLayer layer : layers) {
            layer.draw(batch);
        }
        batch.end();
        batch.begin();
    }

    @Override
    public void act(float delta) {
        if (autoScrolling) {
            camera.position.x += PARALLAX_SPEED * delta;
            camera.update();
        }
    }
}
