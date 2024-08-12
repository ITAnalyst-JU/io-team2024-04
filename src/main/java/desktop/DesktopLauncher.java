package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import core.orchestrator.SupremeOrchestrator;
import core.views.ScreenAbstractFactory;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new Lwjgl3Application(
			SupremeOrchestratorComposer.composeSupremeOrchestrator(
					new SupremeOrchestrator(),
					new ScreenAbstractFactory()),
            ConfigCreator.GetDesktopDefaultConfig());
	}
}
