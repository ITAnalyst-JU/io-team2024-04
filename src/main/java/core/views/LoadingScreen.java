package core.views;

import core.orchestrator.SupremeOrchestrator;

public class LoadingScreen extends AbstractScreen {
    public LoadingScreen(SupremeOrchestrator supremeOrchestrator) {
        super(supremeOrchestrator);
    }

    @Override
    public void render(float delta) {
        super.notifyOrchestator(ScreenState.MENU);
    }
}