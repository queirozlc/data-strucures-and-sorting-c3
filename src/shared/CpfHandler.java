package shared;

public record CpfHandler(DataStructure<Long> dataStructure) implements DataStructureHandler<Long> {

    @Override
    public void processLine(String line) {
        if (!dataStructure.contains(Long.parseLong(line))) {
            dataStructure.add(Long.parseLong(line));
        }
    }

    @Override
    public String getFileName() {
        return "CPF.txt";
    }
}
