package core.orchestrator;

import com.badlogic.gdx.Game;
import core.general.Observer;
import core.levels.LevelFactory;
import core.levels.LevelManager;
import core.levels.LevelEnum;
import core.views.*;

public class SupremeOrchestrator extends Game implements Observer<DomainEventEnum> {
    private final IScreenOrchestrator screenOrchestrator;
    private final LevelFactory levelFactory;

    public SupremeOrchestrator(IScreenOrchestrator screenOrchestrator, LevelFactory levelFactory) {
        this.screenOrchestrator = screenOrchestrator;
        ((ScreenOrchestrator) screenOrchestrator).addObserver(this);
        this.levelFactory = levelFactory;
    }

    @Override
    public void create() {
        this.setScreen(screenOrchestrator.getScreen(ScreenEnum.LOADING));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void notifyScreenOrchestratorLevelLoaded(LevelManager level) {
        this.screenOrchestrator.respondToLoadedLevel(level);
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
                this.setScreen(screenOrchestrator.getScreen(nextScreen));
                break;
            case NEEDLEVEL:
                LevelEnum nextLevelEnum = this.screenOrchestrator.getNextLevel();
                nextLevel = levelFactory.createLevel(nextLevelEnum);
                this.notifyScreenOrchestratorLevelLoaded(nextLevel);
                this.setScreen(screenOrchestrator.getScreen(ScreenEnum.GAME));
                break;
            case RESUME_LEVEL:
                nextLevel = levelFactory.getSavedLevel();
                this.notifyScreenOrchestratorLevelLoaded(nextLevel);
                this.setScreen(screenOrchestrator.getScreen(ScreenEnum.GAME));
                break;
        }
    }
}
