package shared;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileProcessor {

    public static void processFile(DataStructureHandler<?> handler) throws FileNotFoundException {
        var inputStreamReader = FileResourceUtils.getStreamReader(handler.getFileName());
        try (var input = new BufferedReader(inputStreamReader)) {
            input.lines().forEach(handler::processLine);
        } catch (IOException e) {
            throw new FileProcessorException("Error while processing file: " + handler.getFileName());
        }
    }

    public static void processFile(DataStructureHandler<?>... handlers) throws FileNotFoundException {
        for (var handler : handlers) {
            processFile(handler);
        }
    }
}

class FileProcessorException extends RuntimeException {

    public FileProcessorException(String message) {
        super(message);
    }
}