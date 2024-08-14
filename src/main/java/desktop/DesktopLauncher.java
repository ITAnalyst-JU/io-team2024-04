package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.levels.LevelAbstractFactory;
import core.levels.LevelOrchestrator;
import core.orchestrator.SupremeOrchestrator;
import core.views.ScreenAbstractFactory;

public class DesktopLauncher {
	public static void main (String[] arg) {
		new Lwjgl3Application(
			SupremeOrchestratorComposer.composeSupremeOrchestrator(
					new SupremeOrchestrator(),
					new ScreenAbstractFactory(),
					new LevelOrchestrator(new LevelAbstractFactory())),
            ConfigCreator.SetDesktopDefaultConfig(new Lwjgl3ApplicationConfiguration()));
	}
}
