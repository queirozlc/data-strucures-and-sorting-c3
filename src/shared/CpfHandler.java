package shared;

public record CpfHandler(DataStructure<Cpf> dataStructure) implements DataStructureHandler<Cpf> {

    @Override
    public void processLine(String line) {
        var cpf = new Cpf(line);

        if (!dataStructure.contains(cpf)) {
            dataStructure.add(cpf);
        }
    }

    @Override
    public String getFileName() {
        return "CPF.txt";
    }
}
