package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;

import static desktop.constants.ConfigConstants.*;
import static desktop.constants.ErrorCodes.*;

// NOTE: cannot use Gdx.*, context is not initialized

public class ConfigFactory {
    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    record ConfigRecord(
            @JsonProperty("foregroundFPS") int foregroundFPS,
            @JsonProperty("title") String title,
            @JsonProperty("windowWidth") int windowWidth,
            @JsonProperty("windowHeight") int windowHeight,
            @JsonProperty("vsync") boolean vsync,
            @JsonProperty("icon") String icon) {
    }

    public static Lwjgl3ApplicationConfiguration getDesktopConfig() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        ConfigRecord configRecord;
        File configFile = FileHandler.getFileDescriptor(CONFIG_FILE_PATH);
        if (configFile == null) {
            configFile = FileHandler.createFile(CONFIG_FILE_PATH);
            configRecord = new ConfigRecord(
                    DEFAULT_FOREGROUND_FPS,
                    DEFAULT_TITLE,
                    DEFAULT_WINDOW_WIDTH,
                    DEFAULT_WINDOW_HEIGHT,
                    DEFAULT_VSYNC_ENABLED,
                    DEFAULT_WINDOW_ICON);
            saveConfig(configFile, serializeConfig(configRecord));
        } else {
            configRecord = deserializeConfig(FileHandler.readFromFile(configFile));
        }

        assert configRecord != null; // configRecord is always initialized, IntelliJ doesn't see it
        config.setForegroundFPS(configRecord.foregroundFPS());
        config.setTitle(configRecord.title());
        config.setWindowedMode(configRecord.windowWidth(), configRecord.windowHeight());
        config.useVsync(configRecord.vsync());
        config.setWindowIcon(configRecord.icon());
        return config;
    }


    private static String serializeConfig(ConfigRecord configRecord) {
        try {
            return mapper.writeValueAsString(configRecord);
        } catch (Exception e) {
            System.err.println(
                    "[ConfigFactory.serializeConfig] Failed to serialize config record!");
            System.exit(CONFIG_SERIALIZATION_ERROR);
            return null; // not reachable
        }
    }

    private static void saveConfig(File file, String configJson) {
        FileHandler.writeToFile(file, configJson);
    }

    private static ConfigRecord deserializeConfig(String configJson) {
        try {
            return mapper.readValue(configJson, ConfigRecord.class);
        } catch (Exception e) {
            System.err.println(
                    "[ConfigFactory.deserializeConfig] Failed to deserialize config record!");
            System.exit(CONFIG_DESERIALIZATION_ERROR);
            return null; // not reachable
        }
    }
}
