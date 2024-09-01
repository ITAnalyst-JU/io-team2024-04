package core.assets;

import com.badlogic.gdx.assets.AssetManager;


public class AssetManagerFactory {

    private final AssetManager assetManager;

    public AssetManagerFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public IAssetManager getAssetManager() {
        return new SupremeAssetManager(assetManager);
    }

    public IAssetManagerGetter getAssetManagerGetter() {
        return new SupremeAssetManager(assetManager);
    }

    public IAssetManagerLoader getAssetManagerLoader() {
        return new SupremeAssetManager(assetManager);
    }
}
