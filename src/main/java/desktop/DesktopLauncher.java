package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import core.orchestrator.SupremeOrchestratorFactory;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new Lwjgl3Application(
            SupremeOrchestratorFactory.GetDefaultSupremeOrchestrator(),
            ConfigFactory.GetDesktopDefaultConfig());
	}
}
