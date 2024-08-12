package desktop;

import core.orchestrator.SupremeOrchestrator;
import core.views.ScreenAbstractFactory;

public class SupremeOrchestratorComposer
{
    public static SupremeOrchestrator composeSupremeOrchestrator(
            SupremeOrchestrator supremeOrchestrator,
            ScreenAbstractFactory screenAbstractFactory)
    {
        supremeOrchestrator.setScreenAbstractFactory(screenAbstractFactory);
        screenAbstractFactory.setSupremeOrchestrator(supremeOrchestrator);
        return supremeOrchestrator;
    }
}
