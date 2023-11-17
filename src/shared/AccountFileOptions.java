package shared;

public enum AccountFileOptions {
    CONTA_500("conta500.txt"),
    CONTA_1000("conta1000.txt"),
    CONTA_5000("conta5000.txt"),
    CONTA_10000("conta10000.txt"),
    CONTA_50000("conta50000.txt");

    private final String file;

    AccountFileOptions(String file) {
        this.file = file;
    }

    public String getFileName() {
        return file;
    }
}
