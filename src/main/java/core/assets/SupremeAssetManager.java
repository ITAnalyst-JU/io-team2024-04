package core.assets;

import com.badlogic.gdx.assets.AssetManager;

public class SupremeAssetManager implements IAssetManager{

    private final AssetManager assetManager;

    public SupremeAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    @Override
    public void queueAddImages() {

    }

    @Override
    public void queueAddSounds() {

    }

    @Override
    public void queueAddMusic() {

    }

    @Override
    public void queueAddFonts() {

    }

    @Override
    public void waitForImages() {

    }

    @Override
    public void waitForSounds() {

    }

    @Override
    public void waitForMusic() {

    }

    @Override
    public void waitForFonts() {

    }
}
