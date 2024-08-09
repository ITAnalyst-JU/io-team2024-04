package core.orchestrator;

import core.views.*;

// TODO: maybe it should be builder pattern
public class SupremeOrchestratorFactory {
    public static SupremeOrchestrator GetDefaultSupremeOrchestrator() {
        return new SupremeOrchestrator(
            new LoadingScreen(),
            new PreferencesScreen(),
            new MenuScreen(),
            new MainScreen(),
            new EndScreen()
        );
    }
}
