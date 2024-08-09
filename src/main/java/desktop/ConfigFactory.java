package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class ConfigFactory {
    public static Lwjgl3ApplicationConfiguration GetDesktopDefaultConfig() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("GradleDemonAdventures");
        return config;
    }
}
