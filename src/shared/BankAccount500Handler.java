package shared;

import java.util.Arrays;

public class BankAccount500Handler implements DataStructureHandler<BankAccount> {

    private final DataStructure<BankAccount> dataStructure;

    public BankAccount500Handler(DataStructure<BankAccount> dataStructure) {
        this.dataStructure = dataStructure;
    }

    @Override
    public void processLine(String line) {
        var bankLine = line.split(" ");
        Arrays.stream(bankLine)
                .map(bankAccountLine -> Arrays.stream(bankAccountLine.split(";"))
                        .map(String::trim)
                        .toArray(String[]::new))
                .forEach(bankAccount -> dataStructure.add(new BankAccount(
                        Long.parseLong(bankAccount[0]),
                        Long.parseLong(bankAccount[1]),
                        Double.parseDouble(bankAccount[2]),
                        Long.parseLong(bankAccount[3])
                )));
    }

    @Override
    public DataStructure<BankAccount> getStructure() {
        return dataStructure;
    }
}
