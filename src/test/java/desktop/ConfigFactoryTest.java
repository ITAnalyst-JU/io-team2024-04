package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import desktop.config.ConfigFactory;
import desktop.config.ConfigRecord;
import desktop.handlers.FileHandler;
import desktop.handlers.JsonHandler;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class ConfigFactoryTest {
    @Test
    public void testApplyDesktopConfigNoConfigFile() {
        var fileHandler = mock(FileHandler.class);
        var jsonHandler = mock(JsonHandler.class);
        var lwjgl3ApplicationConfiguration = mock(Lwjgl3ApplicationConfiguration.class);
        var configFactory = new ConfigFactory(fileHandler, jsonHandler);

        when(fileHandler.getFileDescriptor(anyString())).thenReturn(null);

        configFactory.applyDesktopConfig(lwjgl3ApplicationConfiguration);

        verify(fileHandler, times(1)).createFile("config/config.json");
        verify(jsonHandler, times(1)).serialize(new ConfigRecord(60, "Gradle Demon Adventures", 1280, 720, true, "icons/icon.png"));
        verify(lwjgl3ApplicationConfiguration, times(1)).setForegroundFPS(60);
        verify(lwjgl3ApplicationConfiguration, times(1)).setTitle("Gradle Demon Adventures");
        verify(lwjgl3ApplicationConfiguration, times(1)).setWindowedMode(1280, 720);
        verify(lwjgl3ApplicationConfiguration, times(1)).useVsync(true);
        verify(lwjgl3ApplicationConfiguration, times(1)).setWindowIcon("icons/icon.png");
    }

    @Test
    public void testApplyDesktopConfigConfigFileExists() {
        TestUtils.discardSysOut();
        var fileHandler = mock(FileHandler.class);
        var jsonHandler = mock(JsonHandler.class);
        var lwjgl3ApplicationConfiguration = mock(Lwjgl3ApplicationConfiguration.class);
        var configFactory = new ConfigFactory(fileHandler, jsonHandler);

        when(fileHandler.getFileDescriptor(anyString())).thenReturn(new File("dummy"));
        when(fileHandler.readFromFile(any(File.class))).thenReturn("dummy");
        when(jsonHandler.deserialize(anyString())).thenReturn(new ConfigRecord(60, "Gradle Demon Adventures", 1280, 720, true, "icons/icon.png"));

        configFactory.applyDesktopConfig(lwjgl3ApplicationConfiguration);

        verify(fileHandler, times(0)).createFile("config/config.json");
        verify(jsonHandler, times(0)).serialize(any(ConfigRecord.class));
        verify(jsonHandler, times(1)).deserialize(anyString());
        verify(lwjgl3ApplicationConfiguration, times(1)).setForegroundFPS(60);
        verify(lwjgl3ApplicationConfiguration, times(1)).setTitle("Gradle Demon Adventures");
        verify(lwjgl3ApplicationConfiguration, times(1)).setWindowedMode(1280, 720);
        verify(lwjgl3ApplicationConfiguration, times(1)).useVsync(true);
        verify(lwjgl3ApplicationConfiguration, times(1)).setWindowIcon("icons/icon.png");
    }

    @Test
    public void testApplyDesktopConfigThrows() {
        var fileHandler = mock(FileHandler.class);
        var jsonHandler = mock(JsonHandler.class);
        var lwjgl3ApplicationConfiguration = mock(Lwjgl3ApplicationConfiguration.class);
        var configFactory = new ConfigFactory(fileHandler, jsonHandler);

        when(fileHandler.getFileDescriptor(anyString())).thenReturn(new File("dummy"));
        when(fileHandler.readFromFile(any(File.class))).thenReturn("dummy");
        when(jsonHandler.deserialize(anyString())).thenReturn(null);

        assertThatThrownBy(() -> configFactory.applyDesktopConfig(lwjgl3ApplicationConfiguration))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Failed to deserialize config from file!");


    }
}
