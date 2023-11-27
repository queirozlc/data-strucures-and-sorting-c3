package shared;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class GroupProcessor {
    private final DataStructure<BankAccount> bankAccountDataStructure;

    public GroupProcessor(DataStructure<BankAccount> bankAccountDataStructure) {
        this.bankAccountDataStructure = bankAccountDataStructure;
    }

    public void process(Consumer<Consumer<Cpf>> cpfIterator, Consumer<Consumer<BankAccount>> bankIterator, String title, String outputFileName) {
        var sb = new StringBuilder();
        cpfIterator.accept(cpf -> {
            sb.append("CPF: ").append(cpf.value()).append("\n");
            AtomicReference<Double> totalBalance = new AtomicReference<>(0.0);
            AtomicBoolean found = new AtomicBoolean(false);
            bankIterator.accept(value -> {
                if (cpf.compareTo(value.ownerCpf()) == 0) {
                    sb.append("agência: ").append(value.agency()).append(" conta: ").append(value.account()).append(" saldo: ").append(value.balance()).append("\n");
                    totalBalance.updateAndGet(v -> v + value.balance());
                    found.set(true);
                }
            });
            if (!found.get()) {
                sb.append("INEXISTENTE\n");
            }
            if (found.get()) {
                sb.append("Saldo total: ").append(totalBalance.get()).append("\n\n");
            }

            sb.append("\n");
        });

        FileWriteable.write(outputFileName, sb.toString(), title);
        bankAccountDataStructure.clear();
    }
}
