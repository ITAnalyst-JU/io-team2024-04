package desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import core.assets.AssetManagerFactory;
import core.assets.SupremeAssetManager;
import core.levels.LevelFactory;
import core.orchestrator.SupremeOrchestrator;
import core.preferences.InternalPreferencesInteractorFactory;
import core.views.ScreenAbstractFactory;
import core.views.ScreenOrchestrator;
import core.views.SupremeInteractorFactory;
import desktop.config.ConfigFactory;
import desktop.config.ConfigRecord;
import desktop.handlers.FileHandler;
import desktop.handlers.JsonHandler;

import java.util.HashMap;

// NOTE: UNTESTABLE
public class DesktopLauncher {
    public static void main(String[] arg) {
        new Lwjgl3Application(
                new SupremeOrchestrator(
                        new ScreenOrchestrator(
                                new ScreenAbstractFactory(
                                        new SupremeInteractorFactory(
                                                new InternalPreferencesInteractorFactory())),
                                new HashMap<>()),
                        new LevelFactory(),
                        new AssetManagerFactory(new AssetManager())),
                new ConfigFactory(
                        new FileHandler(),
                        new JsonHandler<>(new ObjectMapper()
                                .enable(SerializationFeature.INDENT_OUTPUT), ConfigRecord.class))
                        .applyDesktopConfig(new Lwjgl3ApplicationConfiguration()));
    }
}
