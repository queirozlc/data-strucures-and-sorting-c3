package shared;

public interface DataStructureHandler<T extends Comparable<T>> {

    void processLine(String line);

    DataStructure<T> getStructure();
}
