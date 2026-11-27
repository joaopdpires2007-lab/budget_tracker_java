import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Budget {
    private final BigDecimal initial;
    private final List<Transaction> transactions = new ArrayList<>();
    private String name;
    private BigDecimal min = BigDecimal.ZERO;
    private BigDecimal max; // null = sem limite

    public Budget(BigDecimal initial, String name) {
        if (initial == null || initial.signum() < 0) {
            throw new IllegalArgumentException("O valor inicial não pode ser negativo.");
        }
        this.initial = initial.setScale(2, RoundingMode.HALF_UP);
        changeName(name);
    }

    public void spend(BigDecimal amount, String description) {
        spend(amount, description, LocalDate.now());
    }

    public void spend(BigDecimal amount, String description, LocalDate date) {
        apply(new Transaction(Transaction.Type.SPEND, amount, description, date));
    }

    public void add(BigDecimal amount, String description) {
        add(amount, description, LocalDate.now());
    }

    public void add(BigDecimal amount, String description, LocalDate date) {
        apply(new Transaction(Transaction.Type.ADD, amount, description, date));
    }

    private void apply(Transaction t) {
        BigDecimal after = moneyLeft().add(t.signedAmount());
        if (after.compareTo(min) < 0) {
            throw new IllegalStateException("O saldo ficaria abaixo do mínimo (" + min + ").");
        }
        if (max != null && after.compareTo(max) > 0) {
            throw new IllegalStateException("O saldo ficaria acima do máximo (" + max + ").");
        }
        transactions.add(t);
    }

    public BigDecimal moneyLeft() {
        return transactions.stream()
                .map(Transaction::signedAmount)
                .reduce(initial, BigDecimal::add);
    }

    public void defineMin(BigDecimal min) {
        if (min == null) {
            throw new IllegalArgumentException("O mínimo é obrigatório.");
        }
        if (max != null && min.compareTo(max) > 0) {
            throw new IllegalArgumentException("O mínimo não pode ser superior ao máximo.");
        }
        this.min = min.setScale(2, RoundingMode.HALF_UP);
    }

    public void defineMax(BigDecimal max) {
        if (max != null && max.compareTo(min) < 0) {
            throw new IllegalArgumentException("O máximo não pode ser inferior ao mínimo.");
        }
        this.max = (max == null) ? null : max.setScale(2, RoundingMode.HALF_UP);
    }

    public void changeName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("O nome não pode estar vazio.");
        }
        this.name = newName.trim();
    }

    public String getName() { return name; }
    public BigDecimal getInitial() { return initial; }
    public BigDecimal getMin() { return min; }
    public BigDecimal getMax() { return max; }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public String toString() {
        return "Budget: " + name
                + "\nValor inicial: " + initial
                + "\nSaldo atual: " + moneyLeft()
                + "\nMínimo: " + min
                + "\nMáximo: " + (max == null ? "sem limite" : max);
    }
}
