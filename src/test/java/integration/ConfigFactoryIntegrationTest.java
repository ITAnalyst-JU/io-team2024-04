package integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

@Disabled
public class ConfigFactoryIntegrationTest {
    @BeforeAll
    public static void setUp() throws IOException {
        File file = new File("config.json");
        file.createNewFile();
    }

    @AfterAll
    public static void tearDown() {
        File file = new File("config.json");
        file.delete();
    }

    @Test
    public void testApplyDesktopConfigNoConfigFileCreatesNewFile() {
        // test code
    }

    @Test
    public void testApplyDesktopConfigConfigFileExists() {
        // test code
    }
}
