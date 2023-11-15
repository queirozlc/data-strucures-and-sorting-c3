package shared;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileProcessor {

    public static void processFile(String file, DataStructureHandler<?> handler) throws FileNotFoundException {
        var inputStreamReader = FileResourceUtils.getStreamReader(file);
        try (var input = new BufferedReader(inputStreamReader)) {
            input.lines().forEach(handler::processLine);
        } catch (IOException e) {
            throw new FileProcessorException("Error while processing file: " + file);
        }
    }

    public static void processFile(String file, DataStructureHandler<?>... handlers) throws FileNotFoundException {
        var inputStreamReader = FileResourceUtils.getStreamReader(file);
        try (var input = new BufferedReader(inputStreamReader)) {
            input.lines().forEach(line -> {
                for (var handler : handlers) {
                    handler.processLine(line);
                }
            });
        } catch (IOException e) {
            throw new FileProcessorException("Error while processing file: " + file);
        }
    }
}

class FileProcessorException extends RuntimeException {

    public FileProcessorException(String message) {
        super(message);
    }
}