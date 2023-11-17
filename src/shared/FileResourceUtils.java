package shared;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class FileResourceUtils {

    public static InputStreamReader getStreamReader(String file) throws FileNotFoundException {
        final var path = Paths.get("src", "shared", "resources", file);
        return new InputStreamReader(new FileInputStream(path.toFile()), StandardCharsets.UTF_8);
    }
}
