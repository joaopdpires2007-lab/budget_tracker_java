public class Budget {
    private final double initial;
    private double moneyLeft;
    private double min, max;

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

    public void defineMax(double max) {
        this.max = max;
    }

    public void defineMin(double min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Initial money: " + initial + "\nMoney left: " + moneyLeft;
    }
}
