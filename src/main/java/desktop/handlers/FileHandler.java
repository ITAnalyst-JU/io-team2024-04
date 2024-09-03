package desktop.handlers;

import java.io.*;

import static desktop.constants.ErrorCodes.*;

// NOTE: UNTESTABLE with UTs
// TODO: integration test

public class FileHandler {
    public File createFile(String path) {
        File file = new File(path);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.err.println(
                    "[FileHandler.createFile] Failed to create file: " + path);
            System.exit(CONFIG_FILE_CREATION_ERROR);
        }
        return file;
    }

    public File getFileDescriptor(String path) {
        File file = new File(path);
        if (!file.isFile()) return null;
        return file;
    }

    public String readFromFile(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println(
                    "[FileHandler.readFromFile] Failed to read file: " + file.getAbsolutePath());
            System.exit(BUFFERED_READER_CREATION_ERROR);
        }

        return content.toString();
    }

    public void writeToFile(File file, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println(
                    "[FileHandler.writeToFile] Failed to write to file: " + file.getAbsolutePath());
            System.exit(BUFFERED_WRITER_CREATION_ERROR);
        }
    }
}
