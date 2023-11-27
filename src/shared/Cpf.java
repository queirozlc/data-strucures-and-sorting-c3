package shared;

public record Cpf(String value) implements Comparable<Cpf> {
    @Override
    public int compareTo(Cpf o) {
        final Long cpfNumeric = Long.parseLong(value);
        final Long oCpfNumeric = Long.parseLong(o.value);
        return cpfNumeric.compareTo(oCpfNumeric);
    }
}
