package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class ConfigCreator {
    public static Lwjgl3ApplicationConfiguration SetDesktopDefaultConfig(Lwjgl3ApplicationConfiguration config) {
        config.setForegroundFPS(60);
        config.setTitle("Gradle Demon Adventures");
        config.setWindowedMode(1280, 720);
        return config;
    }
}
