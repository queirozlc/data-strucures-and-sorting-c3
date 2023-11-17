package shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriteable {

    public static void write(String fileName, String content, String title) {
        final var path = Paths.get("resultados", fileName);

        // if a directory does not exist, create it
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (var writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write(title);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
