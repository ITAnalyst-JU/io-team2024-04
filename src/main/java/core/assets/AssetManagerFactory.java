package core.assets;

import com.badlogic.gdx.assets.AssetManager;


public class AssetManagerFactory implements IAssetManagerFactory {

    private final AssetManager assetManager;

    public AssetManagerFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public IAssetManager getAssetManager() {
        return new SupremeAssetManager(assetManager);
    }

    @Override
    public IAssetManagerGetter getAssetManagerGetter() {
        return new SupremeAssetManager(assetManager);
    }

    @Override
    public IAssetManagerLoader getAssetManagerLoader() {
        return new SupremeAssetManager(assetManager);
    }
}
