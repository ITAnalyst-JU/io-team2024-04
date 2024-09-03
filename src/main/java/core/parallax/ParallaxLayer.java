package core.parallax;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.w3c.dom.Text;

public class ParallaxLayer {
    private final Pixmap pixmap;
    private Texture texture;
    private final float factor;
    private final boolean wrapHorizontally;
    private final boolean wrapVertically;

    public ParallaxLayer(Pixmap pixmap, float factor, boolean wrapHorizontally, boolean wrapVertically, ScreenHook screenHook) {
        this.factor = factor;
        this.wrapHorizontally = wrapHorizontally;
        this.wrapVertically = wrapVertically;
        this.pixmap = pixmap;
        this.texture = getResizedTexture(screenHook);

    }

    private Texture getResizedTexture(ScreenHook screenHook) {
        Pixmap scaled = new Pixmap(screenHook.width(), screenHook.height(), pixmap.getFormat());
        scaled.drawPixmap(pixmap,
                0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, scaled.getWidth(), scaled.getHeight());
        Texture texture = new Texture(scaled);
        texture.setWrap(
                this.wrapHorizontally ? Texture.TextureWrap.Repeat : Texture.TextureWrap.ClampToEdge,
                this.wrapVertically ? Texture.TextureWrap.Repeat : Texture.TextureWrap.ClampToEdge
        );
        return texture;
    }

    public void draw(Batch batch, ScreenHook screenHook) {
        int xOffset = (int) (screenHook.centerX() * factor);
        int yOffset = (int) (screenHook.centerY() * factor);

        TextureRegion region = new TextureRegion(texture);
        region.setRegionX(xOffset % texture.getWidth());
        region.setRegionY(yOffset % texture.getHeight());
        region.setRegionWidth(wrapHorizontally ? screenHook.width() : texture.getWidth());
        region.setRegionHeight(wrapVertically ? screenHook.height() : texture.getHeight());

        batch.draw(region, 0, 0, screenHook.width(), screenHook.height());
    }

    public void resize(ScreenHook screenHook) {
        texture.dispose();
        this.texture = getResizedTexture(screenHook);
    }
}