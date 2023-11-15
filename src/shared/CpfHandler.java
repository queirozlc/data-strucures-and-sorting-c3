package shared;

public class CpfHandler implements DataStructureHandler<Long> {
    private final DataStructure<Long> dataStructure;

    public CpfHandler(DataStructure<Long> dataStructure) {
        this.dataStructure = dataStructure;
    }

    @Override
    public void processLine(String line) {
        dataStructure.add(Long.parseLong(line));
    }

    @Override
    public DataStructure<Long> getStructure() {
        return dataStructure;
    }
}
