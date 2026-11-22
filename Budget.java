import java.util.Date;
import java.util.List;

public class Budget {
    private final double initial;
    private double moneyLeft;
    private double min, max;
    private String name;
    private List<Transaction> tList;

    public Budget(double initial, String name) {
        this.initial = initial;
        moneyLeft = initial;
        this.name = name;
    }

    public void spend(double m, String description, Date date) {
        if (!underMin(m)) {
            System.out.println("Cannot spend money. Exceeds minimum limit.");
            return;
        }
        moneyLeft -= m;
        tList.add(new Transaction(m, description, date, false));
    }

    public void add(double m, String description, Date date) {
        if (underMax(m)) {
            moneyLeft += m;
            tList.add(new Transaction(m, description, date, true));
        } else {
            System.out.println("Cannot add money. Exceeds maximum limit.");
        }
    }

    public void defineMax(double max) {
        this.max = max;
    }

    private boolean underMax(double m) {
        return m <= max;
    }

    public void defineMin(double min) {
        this.min = min;
    }

    private boolean underMin(double m) {
        return m <= min;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "Budget name: " + name + "\nInitial money: " + initial + "\nMoney left: " + moneyLeft;
    }
}
