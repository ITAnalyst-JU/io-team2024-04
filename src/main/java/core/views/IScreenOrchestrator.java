package core.views;

import com.badlogic.gdx.Screen;
import core.assets.AssetManagerFactory;
import core.levels.LevelManager;
import core.levels.LevelEnum;

// NOTE: strategy design pattern
public interface IScreenOrchestrator {
    Screen getScreen(ScreenEnum screenEnum, AssetManagerFactory assetManagerFactory);
    LevelEnum getNextLevel();
    ScreenEnum getNextScreenEnum();

    // TODO: consider making it with domain event
    void respondToLoadedLevel(LevelManager level, AssetManagerFactory assetManagerFactory);
    void dispose();
}
