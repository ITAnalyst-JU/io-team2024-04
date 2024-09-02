package core.parallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParallaxLayer {
    private final Texture texture;
    private final float factor;
    private final boolean wrapHorizontally;
    private final boolean wrapVertically;

    private final Camera camera;

    public ParallaxLayer(Pixmap pixmap, float factor, boolean wrapHorizontally, boolean wrapVertically, Camera camera) {
        this.factor = factor;
        this.wrapHorizontally = wrapHorizontally;
        this.wrapVertically = wrapVertically;
        this.camera = camera;
        Pixmap scaled = new Pixmap((int) camera.viewportWidth, (int) camera.viewportHeight, pixmap.getFormat());
        scaled.drawPixmap(pixmap,
                0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, scaled.getWidth(), scaled.getHeight());
        this.texture = new Texture(scaled);
        this.texture.setWrap(
                this.wrapHorizontally ? Texture.TextureWrap.Repeat : Texture.TextureWrap.ClampToEdge,
                this.wrapVertically ? Texture.TextureWrap.Repeat : Texture.TextureWrap.ClampToEdge
        );
    }

    public void draw(Batch batch) {
        int xOffset = (int) (camera.position.x * factor);
        int yOffset = (int) (camera.position.y * factor);

        TextureRegion region = new TextureRegion(texture);
        region.setRegionX(xOffset % texture.getWidth());
        region.setRegionY(yOffset % texture.getHeight());
        region.setRegionWidth(wrapHorizontally ? (int) camera.viewportWidth : texture.getWidth());
        region.setRegionHeight(wrapVertically ? (int) camera.viewportHeight : texture.getHeight());

        batch.draw(region, camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2, camera.viewportWidth, camera.viewportHeight);
    }
}