package shared;

public class BankAccountHandler implements DataStructureHandler<BankAccount> {

    private final DataStructure<BankAccount> dataStructure;
    private final AccountFileOptions option;

    public BankAccountHandler(DataStructure<BankAccount> dataStructure, AccountFileOptions options) {
        this.dataStructure = dataStructure;
        this.option = options;
    }

    @Override
    public void processLine(String line) {
        var bankLineData = line.split(";");
        var bankAccount = new BankAccount(
                Long.parseLong(bankLineData[0]),
                Long.parseLong(bankLineData[1]),
                Double.parseDouble(bankLineData[2]),
                new Cpf(bankLineData[3])
        );
        dataStructure.add(bankAccount);
    }

    @Override
    public DataStructure<BankAccount> dataStructure() {
        return dataStructure;
    }

    public String getFileName() {
        return option.getFileName();
    }
}
