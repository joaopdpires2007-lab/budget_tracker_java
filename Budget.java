public class Budget {
    private final double initial;
    private double moneyLeft;
    private double min, max;
    private String name;

    public Budget(double initial, String name) {
        this.initial = initial;
        moneyLeft = initial;
        this.name = name;
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

    public void changeName(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "Budget name: " + name + "\nInitial money: " + initial + "\nMoney left: " + moneyLeft;
    }
}
