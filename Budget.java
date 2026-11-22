public class Budget {
    private final double initial;
    private double moneyLeft;

    public Budget(double initial) {
        this.initial = initial;
        moneyLeft = initial;
    }

    public void spend(double m, String description) {
        moneyLeft -= m;
        System.out.println(this.toString());
    }

    public void add(double m, String description) {
        moneyLeft += m;
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Initial money: " + initial + "\nMoney left: " + moneyLeft;
    }
}
