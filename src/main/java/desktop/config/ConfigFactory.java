package desktop.config;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import desktop.handlers.FileHandler;
import desktop.handlers.JsonHandler;

import java.io.File;

import static desktop.constants.ConfigConstants.*;
import static desktop.constants.ErrorCodes.CONFIG_DESERIALIZATION_ERROR;

// NOTE: cannot use Gdx.*, context is not initialized

public class ConfigFactory {
    private final FileHandler fileHandler;
    private final JsonHandler<ConfigRecord> jsonHandler;

    public ConfigFactory(FileHandler fileHandler, JsonHandler<ConfigRecord> jsonHandler) {
        this.fileHandler = fileHandler;
        this.jsonHandler = jsonHandler;
    }

    public Lwjgl3ApplicationConfiguration applyDesktopConfig(
            Lwjgl3ApplicationConfiguration config) {

        ConfigRecord configRecord;
        File configFile = fileHandler.getFileDescriptor(CONFIG_FILE_PATH);
        if (configFile == null) {
            configFile = fileHandler.createFile(CONFIG_FILE_PATH);
            configRecord = new ConfigRecord(
                    DEFAULT_FOREGROUND_FPS,
                    DEFAULT_TITLE,
                    DEFAULT_WINDOW_WIDTH,
                    DEFAULT_WINDOW_HEIGHT,
                    DEFAULT_VSYNC_ENABLED,
                    DEFAULT_WINDOW_ICON);
            saveConfig(configFile, jsonHandler.serialize(configRecord));
        } else {
            configRecord = jsonHandler.deserialize(fileHandler.readFromFile(configFile));
        }

        if (configRecord == null) {
            System.err.println("[ConfigFactory.applyDesktopConfig] Failed to deserialize config from file!");
            throw new RuntimeException("Failed to deserialize config from file!");
        }
        config.setForegroundFPS(configRecord.foregroundFPS());
        config.setTitle(configRecord.title());
        config.setWindowedMode(configRecord.windowWidth(), configRecord.windowHeight());
        config.useVsync(configRecord.vsync());
        config.setWindowIcon(configRecord.icon());
        return config;
    }

    private void saveConfig(File file, String configJson) {
        fileHandler.writeToFile(file, configJson);
    }
}
