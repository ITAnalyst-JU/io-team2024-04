package integration;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import desktop.handlers.FileHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.assertThat;

// NOTE: line coverage is not 100% because of errors that are unexpected

public class FileHandlerIntegrationTest {
    private static final String dummyFile1 = "dummyFile1";
    private static final String dummyFile2 = "dummyFile2";
    private static final String dummyFile3 = "dummyFile3";

    @BeforeAll
    public static void setup() throws IOException {
        File fd = new File(dummyFile2);
        fd.createNewFile();
    }

    @AfterAll
    public static void cleanup() {
        File fd = new File(dummyFile2);
        fd.delete();
        fd = new File(dummyFile1);
        fd.delete();
    }

    @Test
    public void testCreateFile() {
        var fileHandler = new FileHandler();
        var file = fileHandler.createFile(dummyFile1);
        assertThat(file).isNotNull();
        File fd = new File(dummyFile1);
        assertThat(fd.isFile()).isTrue();
    }

    @Test
    public void testGetFileDescriptorFileNotExist() {
        var fileHandler = new FileHandler();
        var file = fileHandler.getFileDescriptor(dummyFile3);
        assertThat(file).isNull();
    }

    @Test
    public void testGetFileDescriptorFileExist() {
        var fileHandler = new FileHandler();
        var file = fileHandler.getFileDescriptor(dummyFile2);
        assertThat(file).isNotNull();
    }

    @Test
    public void testReadFromFile() {
        var fileHandler = new FileHandler();
        var file = fileHandler.getFileDescriptor("fileHandlerIntegrationTest/dune");
        var content = fileHandler.readFromFile(file);
        assertThat(content).isEqualToIgnoringNewLines("Fear is the mind-killer. Fear is the little-death that brings total obliteration.");
    }

    @Test
    public void testWriteToFile() {
        var fileHandler = new FileHandler();
        var file = fileHandler.getFileDescriptor(dummyFile2);
        fileHandler.writeToFile(file, "Spice is life.");
        var content = fileHandler.readFromFile(file);
        assertThat(content).isEqualToIgnoringNewLines("Spice is life.");
    }
}
