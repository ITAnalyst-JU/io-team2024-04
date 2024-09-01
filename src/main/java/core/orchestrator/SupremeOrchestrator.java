package core.orchestrator;

import com.badlogic.gdx.Game;
import core.assets.AssetManagerFactory;
import core.assets.IAssetManager;
import core.general.Observer;
import core.levels.LevelFactory;
import core.levels.LevelManager;
import core.levels.LevelEnum;
import core.views.*;

public class SupremeOrchestrator extends Game implements Observer<DomainEventEnum> {
    private final IScreenOrchestrator screenOrchestrator;
    private final LevelFactory levelFactory;

    private final AssetManagerFactory assetManagerFactory;


    public SupremeOrchestrator(IScreenOrchestrator screenOrchestrator, LevelFactory levelFactory, AssetManagerFactory assetManagerFactory) {
        this.screenOrchestrator = screenOrchestrator;
        ((ScreenOrchestrator) screenOrchestrator).addObserver(this);
        this.levelFactory = levelFactory;
        this.assetManagerFactory = assetManagerFactory;
    }

    @Override
    public void create() {
        this.setScreen(screenOrchestrator.getScreen(ScreenEnum.LOADING, assetManagerFactory));
    }

    @Override
    public void dispose() {
        super.dispose();
        screenOrchestrator.dispose();
        assetManagerFactory.getAssetManager().dispose();
    }

    private void notifyScreenOrchestratorLevelLoaded(LevelManager level, AssetManagerFactory assetManagerFactory) {
        this.screenOrchestrator.respondToLoadedLevel(level, assetManagerFactory);
    }

    @Override
    public void respondToEvent(DomainEventEnum param) {
        LevelManager nextLevel;
        switch (param) {
            case CHANGESCREEN:
                var nextScreen = screenOrchestrator.getNextScreenEnum();
                if (nextScreen == ScreenEnum.MENU) {
                    levelFactory.clearSavedLevel();
                }
                this.setScreen(screenOrchestrator.getScreen(nextScreen, assetManagerFactory));
                break;
            case NEEDLEVEL:
                LevelEnum nextLevelEnum = this.screenOrchestrator.getNextLevel();
                nextLevel = levelFactory.createLevel(nextLevelEnum, assetManagerFactory);
                this.notifyScreenOrchestratorLevelLoaded(nextLevel, assetManagerFactory);
                this.setScreen(screenOrchestrator.getScreen(ScreenEnum.GAME, assetManagerFactory));
                break;
            case RESUME_LEVEL:
                nextLevel = levelFactory.getSavedLevel();
                this.notifyScreenOrchestratorLevelLoaded(nextLevel, assetManagerFactory);
                this.setScreen(screenOrchestrator.getScreen(ScreenEnum.GAME, assetManagerFactory));
                break;
        }
    }
}
