import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public record Transaction(Type type, BigDecimal amount, String description, LocalDate date) {

    public enum Type { SPEND, ADD }

    public Transaction {
        if (type == null || date == null) {
            throw new IllegalArgumentException("O tipo e a data são obrigatórios.");
        }
        if (amount == null) {
            throw new IllegalArgumentException("O valor é obrigatório.");
        }
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("O valor tem de ser positivo.");
        }
        description = (description == null) ? "" : description.trim();
    }

    public BigDecimal signedAmount() {
        return type == Type.ADD ? amount : amount.negate();
    }

    @Override
    public String toString() {
        return date + " | " + type + " | " + amount + " | " + description;
    }
}
