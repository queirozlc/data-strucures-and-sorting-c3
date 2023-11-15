package shared;

public record BankAccount(Long agency, Long account, double balance, Long ownerCpf) implements Comparable<BankAccount> {
    @Override
    public int compareTo(BankAccount o) {
        // compare by agency and account
        return this.agency.compareTo(o.agency) + this.account.compareTo(o.account);
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
