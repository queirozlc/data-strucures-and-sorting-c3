package shared;

public record BankForm(Long agency, Long account, double balance, Long ownerCpf) implements Comparable<BankForm> {
    @Override
    public int compareTo(BankForm o) {
        return this.agency.compareTo(o.agency);
    }

    @Override
    public String toString() {
        return "BankForm {" +
                "agency: " + agency +
                ", account: " + account +
                ", balance: " + balance +
                ", ownerCpf: " + ownerCpf +
                '}';
    }
}
