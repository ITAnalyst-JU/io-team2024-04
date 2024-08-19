package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.levels.LevelAbstractFactory;
import core.levels.LevelOrchestrator;
import core.orchestrator.SupremeOrchestrator;
import core.views.ScreenAbstractFactory;
import desktop.constants.ConfigConstants;

// NOTE: UNTESTABLE
public class DesktopLauncher {
    public static void main(String[] arg) {
        new Lwjgl3Application(
                new SupremeOrchestrator(
                        new ScreenAbstractFactory(),
                        new LevelOrchestrator(new LevelAbstractFactory())),
                ConfigFactory.getDesktopConfig());
    }
}
