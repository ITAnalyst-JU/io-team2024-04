package core.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public abstract class AbstractPlatform extends AbstractEntity {

    public AbstractPlatform(Sprite sprite, TiledMapTileLayer mapLayer) {
        super(sprite, mapLayer);
    }

    protected abstract void update(float timeDelta);

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }
    
}
