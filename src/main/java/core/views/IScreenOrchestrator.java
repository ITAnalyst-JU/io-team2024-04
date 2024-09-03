package core.views;

import com.badlogic.gdx.Screen;
import core.assets.AssetManagerFactory;
import core.general.Observer;
import core.levels.ILevelManager;
import core.levels.LevelEnum;
import core.orchestrator.DomainEventEnum;

// NOTE: strategy design pattern
public interface IScreenOrchestrator {
    Screen getScreen(ScreenEnum screenEnum, AssetManagerFactory assetManagerFactory);
    LevelEnum getNextLevel();
    ScreenEnum getNextScreenEnum();

    // TODO: consider making it with domain event
    void respondToLoadedLevel(ILevelManager level, AssetManagerFactory assetManagerFactory);
    void respondToEndLevel(int levelNumber, AssetManagerFactory assetManagerFactory);
    void dispose();
    void addObserver(Observer<DomainEventEnum> observer);
}
