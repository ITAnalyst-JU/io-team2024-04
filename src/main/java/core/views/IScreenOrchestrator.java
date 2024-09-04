package core.views;

import com.badlogic.gdx.Screen;
import core.assets.IAssetManagerFactory;
import core.general.Observer;
import core.levels.ILevelManager;
import core.levels.LevelEnum;
import core.orchestrator.DomainEventEnum;

// NOTE: strategy design pattern
public interface IScreenOrchestrator {
    Screen getScreen(ScreenEnum screenEnum, IAssetManagerFactory assetManagerFactory);
    LevelEnum getNextLevel();
    ScreenEnum getNextScreenEnum();

    // TODO: consider making it with domain event
    void respondToLoadedLevel(ILevelManager level, IAssetManagerFactory assetManagerFactory);
    void respondToEndLevel(int levelNumber, IAssetManagerFactory assetManagerFactory);
    void dispose();
    void addObserver(Observer<DomainEventEnum> observer);
}
