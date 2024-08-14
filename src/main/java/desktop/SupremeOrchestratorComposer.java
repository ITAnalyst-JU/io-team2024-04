package desktop;

import core.levels.LevelOrchestrator;
import core.orchestrator.SupremeOrchestrator;
import core.views.ScreenAbstractFactory;

public class SupremeOrchestratorComposer
{
    public static SupremeOrchestrator composeSupremeOrchestrator(
            SupremeOrchestrator supremeOrchestrator,
            ScreenAbstractFactory screenAbstractFactory,
            LevelOrchestrator levelOrchestrator)
    {
        supremeOrchestrator.setScreenAbstractFactory(screenAbstractFactory);
        supremeOrchestrator.setLevelOrchestrator(levelOrchestrator);
        return supremeOrchestrator;
    }
}
