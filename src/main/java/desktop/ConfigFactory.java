package desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;

// NOTE: cannot use Gdx.*, context is not initialized

public class ConfigFactory {
    private static final String CONFIG_PATH = "config/config.json";
    private static final int DEFAULT_FOREGROUND_FPS = 60;
    private static final String DEFAULT_TITLE = "Gradle Demon Adventures";
    private static final int DEFAULT_WINDOW_WIDTH = 1280;
    private static final int DEFAULT_WINDOW_HEIGHT = 720;
    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    record ConfigRecord(
        @JsonProperty("foregroundFPS") int foregroundFPS,
        @JsonProperty("title") String title,
        @JsonProperty("windowWidth") int windowWidth,
        @JsonProperty("windowHeight") int windowHeight) {}
    public static Lwjgl3ApplicationConfiguration getDesktopConfig() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        ConfigRecord configRecord;
        File configFile = new File(CONFIG_PATH);
        try (FileReader configFileReader = new FileReader(configFile)) {
            configRecord = deserializeConfig(configFileReader);
        } catch (FileNotFoundException e){
            configRecord = new ConfigRecord(
                    DEFAULT_FOREGROUND_FPS,
                    DEFAULT_TITLE,
                    DEFAULT_WINDOW_WIDTH,
                    DEFAULT_WINDOW_HEIGHT);
            String configJson = serializeConfig(configRecord);
            saveConfig(configJson);
        } catch (IOException e) {
            System.err.println("Failed to read config file!");
            System.exit(2);
            return null; // not reachable
        }

        assert configRecord != null; // configRecord is always initialized, IntelliJ doesn't see it
        config.setForegroundFPS(configRecord.foregroundFPS());
        config.setTitle(configRecord.title());
        config.setWindowedMode(configRecord.windowWidth(), configRecord.windowHeight());
        return config;
    }

    private static String serializeConfig(ConfigRecord configRecord) {
        try {
            return mapper.writeValueAsString(configRecord);
        } catch (Exception e) {
            System.err.println("[ConfigFactory] Failed to serialize config record!");
            System.exit(1);
            return null; // not reachable
        }
    }

    private static void saveConfig(String configJson) {
        BufferedWriter configFile = FileHandler.getFileWriter(CONFIG_PATH);
        try {
            configFile.write(configJson);
        } catch (IOException e) {
            System.err.println("[ConfigFactory] Failed to write config file!");
            System.exit(2);
        } finally {
            configFile.close();
        }
    }

    private static ConfigRecord deserializeConfig(String configJson) {
        try {
            return mapper.readValue(configJson, ConfigRecord.class);
        } catch (Exception e) {
            System.err.println("[ConfigFactory] Failed to deserialize config record!");
            System.exit(1);
            return null; // not reachable
        }
    }

}
