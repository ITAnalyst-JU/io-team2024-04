package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import core.levels.LevelAbstractFactory;
import core.levels.LevelOrchestrator;
import core.orchestrator.SupremeOrchestrator;
import core.orchestrator.SupremeSurroundingsInteractorFactory;
import core.views.ScreenAbstractFactory;
import core.views.ScreenOrchestrator;
import desktop.config.ConfigFactory;
import desktop.config.ConfigRecord;
import desktop.handlers.FileHandler;
import desktop.handlers.JsonHandler;
import core.preferences.PreferencesInteractorFactory;
import core.preferences.UserPreferences;

import java.util.HashMap;

// NOTE: UNTESTABLE
public class DesktopLauncher {
    public static void main(String[] arg) {
        new Lwjgl3Application(
                new SupremeOrchestrator(
                        new LevelOrchestrator(new LevelAbstractFactory(), new HashMap<>()),
                        new ScreenOrchestrator(
                                new ScreenAbstractFactory(
                                        new SupremeSurroundingsInteractorFactory(
                                                new PreferencesInteractorFactory(
                                                        new UserPreferences()))),
                                new HashMap<>())),
                new ConfigFactory(
                        new FileHandler(),
                        new JsonHandler<>(new ObjectMapper()
                                .enable(SerializationFeature.INDENT_OUTPUT), ConfigRecord.class))
                        .applyDesktopConfig(new Lwjgl3ApplicationConfiguration()));
    }
}
