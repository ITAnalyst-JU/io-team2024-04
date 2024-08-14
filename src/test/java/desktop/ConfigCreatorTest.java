package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConfigCreatorTest {
    @Test
    public void testSetDesktopDefaultConfig() {
        var config = mock(Lwjgl3ApplicationConfiguration.class);
        ConfigCreator.SetDesktopDefaultConfig(config);
        verify(config).setForegroundFPS(60);
        verify(config).setTitle("Gradle Demon Adventures");
        verify(config).setWindowedMode(1280, 720);
    }
}
