package core.orchestrator;

import com.badlogic.gdx.Game;
import core.assets.AssetManagerFactory;
import core.general.Observer;
import core.levels.ILevelFactory;
import core.levels.ILevelManager;
import core.levels.LevelEnum;
import core.views.*;

public class SupremeOrchestrator extends Game implements Observer<DomainEventEnum> {
    private final IScreenOrchestrator screenOrchestrator;
    private final ILevelFactory levelFactory;

    private final AssetManagerFactory assetManagerFactory;

    public SupremeOrchestrator(IScreenOrchestrator screenOrchestrator, ILevelFactory levelFactory, AssetManagerFactory assetManagerFactory) {
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

    private void notifyScreenOrchestratorLevelLoaded(ILevelManager level, AssetManagerFactory assetManagerFactory) {
        this.screenOrchestrator.respondToLoadedLevel(level, assetManagerFactory);
    }

    private void notifyScreenOrchestratorLevelEnded(int levelNumber, AssetManagerFactory assetManagerFactory) {
        this.screenOrchestrator.respondToEndLevel(levelNumber, assetManagerFactory);
    }

    @Override
    public void respondToEvent(DomainEventEnum param) {
        ILevelManager nextLevel;
        switch (param) {
            case CHANGESCREEN:
                var nextScreen = screenOrchestrator.getNextScreenEnum();
                if (nextScreen == ScreenEnum.ENDGAME) {
                    this.notifyScreenOrchestratorLevelEnded(levelFactory.getSavedLevel().getLevelNumber(), assetManagerFactory);
                }
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
