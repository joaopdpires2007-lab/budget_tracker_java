import java.util.Date;

/**
 * val -> the value of the transaction
 * description -> the description of the transaction
 * date -> the date of the transaction
 * positive -> if its spending (==false) if its earning (==true)
 * 
 */
public class Transaction {
    public double val;
    public String description;
    public Date date;
    public boolean positive;

    public Transaction(double val, String d, Date date, boolean p) {
        this.val = val;
        description = d;
        this.date = date;
        positive = p;
    }
}


