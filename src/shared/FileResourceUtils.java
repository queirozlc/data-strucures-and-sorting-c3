package shared;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileResourceUtils {

    public static InputStreamReader getStreamReader(String file) throws FileNotFoundException {
        final String base = System.getProperty("user.dir");
        final String separator = System.getProperty("file.separator");
        final String path = base + separator + "src" + separator + "shared" + separator + "resources" + separator + file;
        final var resource = new File(path);
        return new InputStreamReader(new FileInputStream(resource), StandardCharsets.UTF_8);
    }
}
