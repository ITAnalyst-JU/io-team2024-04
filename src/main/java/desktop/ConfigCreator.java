package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


// TODO: rename this to default runtime config creator

// NOTE: this is hardcoded in compilation time for purpose
// after initializing gdx context, app will read proper config
public class ConfigCreator {
    public static Lwjgl3ApplicationConfiguration SetDesktopDefaultConfig(Lwjgl3ApplicationConfiguration config) {
        config.setForegroundFPS(DEFAULT_FOREGROUND_FPS);
        config.setTitle(DEFAULT_TITLE);
        config.setWindowedMode(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        return config;
    }
}
