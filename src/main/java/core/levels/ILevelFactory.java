package core.levels;

import core.assets.AssetManagerFactory;

public interface ILevelFactory {
    ILevelManager createLevel(LevelEnum levelNumber, AssetManagerFactory assetManagerFactory);

    ILevelManager getSavedLevel();

    void clearSavedLevel();
}
