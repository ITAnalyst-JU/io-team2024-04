package core.views;

import core.orchestrator.SupremeOrchestrator;

public class ScreenAbstractFactory {
    private SupremeOrchestrator supremeOrchestrator;

    public void setSupremeOrchestrator(SupremeOrchestrator supremeOrchestrator) {
        this.supremeOrchestrator = supremeOrchestrator;
    }

    public AbstractScreen createScreen(ScreenState screenState) {
        return switch (screenState) {
            case MENU -> new MenuScreen(supremeOrchestrator);
            case PREFERENCES -> new PreferencesScreen(supremeOrchestrator);
            case APPLICATION -> new MainScreen(supremeOrchestrator);
            case ENDGAME -> new EndScreen(supremeOrchestrator);
            case LOADING -> new LoadingScreen(supremeOrchestrator);
            case LEVELSELECTION -> new LevelSelectionScreen(supremeOrchestrator);
            default -> throw new IllegalArgumentException("Invalid screen state: " + screenState);
        };
    }
}
