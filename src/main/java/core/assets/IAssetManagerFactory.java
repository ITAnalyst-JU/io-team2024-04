package core.assets;

public interface IAssetManagerFactory {
    IAssetManager getAssetManager();

    IAssetManagerGetter getAssetManagerGetter();

    IAssetManagerLoader getAssetManagerLoader();
}
