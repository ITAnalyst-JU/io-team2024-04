package core.assets;

public interface IAssetInteractor<T> {
    T getAsset(String assetPath);
}
