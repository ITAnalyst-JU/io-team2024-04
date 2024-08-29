package core.orchestrator;

import com.badlogic.gdx.Game;
import core.general.Observer;
import core.levels.AbstractLevel;
import core.levels.ILevelOrchestrator;
import core.levels.LevelEnum;
import core.views.*;

public class SupremeOrchestrator extends Game implements Observer<DomainEventEnum> {
    private final ILevelOrchestrator levelOrchestrator;
    private final IScreenOrchestrator screenOrchestrator;

    public SupremeOrchestrator(ILevelOrchestrator levelOrchestrator, IScreenOrchestrator screenOrchestrator) {
        this.levelOrchestrator = levelOrchestrator;
        this.screenOrchestrator = screenOrchestrator;
        ((ScreenOrchestrator) screenOrchestrator).addObserver(this);
    }

    @Override
    public void create() {
        this.setScreen(screenOrchestrator.getScreen(ScreenEnum.LOADING));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void notifyScreenOrchestratorLevelLoaded(AbstractLevel level) {
        this.screenOrchestrator.respondToLoadedLevel(level);
    }

    @Override
    public void respondToEvent(DomainEventEnum param) {
        switch (param) {
            case CHANGESCREEN:
                this.setScreen(screenOrchestrator.getScreen(screenOrchestrator.getNextScreenEnum()));
                break;
            case NEEDLEVEL:
                LevelEnum nextLevelEnum = this.screenOrchestrator.getNextLevel();
                AbstractLevel nextLevel = this.levelOrchestrator.getLevel(nextLevelEnum);
                this.notifyScreenOrchestratorLevelLoaded(nextLevel);
                this.setScreen(screenOrchestrator.getScreen(ScreenEnum.GAME));
                break;
        }
    }
}
