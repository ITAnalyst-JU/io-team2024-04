package core.levels;

import core.assets.IAssetManagerFactory;

public interface ILevelFactory {
    ILevelManager createLevel(LevelEnum levelNumber, IAssetManagerFactory assetManagerFactory);

    ILevelManager getSavedLevel();

    void clearSavedLevel();
}
