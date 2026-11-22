# Budget_tracker_java

A small Java project for tracking personal budgets, where each budget represents a financial goal, a savings pot or a spending category (e.g. "Holidays", "Groceries", "Emergency fund").

> **Status: work in progress.** Only the core domain model exists so far. There is no terminal interface or file persistence yet; see the [Roadmap](#roadmap).

## What it is

The long-term goal is a terminal application that lets you:

- create and manage several budgets;
- record spending and incoming money, each with a description and date;
- save everything to a plain `.txt` file and load it back on demand.

At the moment the repository contains only the two classes that model a budget and its transactions.

## Current features

- **`Budget`**: a named budget with an initial amount and a history of transactions.
  - `spend(...)` and `add(...)` record transactions, with an optional explicit date.
  - The current balance is derived from the initial amount plus the transaction history, so the two can never get out of sync.
  - Optional minimum and maximum balance limits, enforced on every transaction.
  - Input validation (non-empty name, non-negative initial amount, positive transaction amounts, consistent limits).
- **`Transaction`**: an immutable record with a type (`SPEND` or `ADD`), an amount, a description and a date.
- Monetary values use `BigDecimal`, rounded to two decimal places, to avoid floating-point errors.

## Project structure

```
.
├── Budget.java
├── Transaction.java
└── README.md
```

## Requirements

- Java 16 or later (`Transaction` is a `record`).

## Building

```bash
javac Budget.java Transaction.java
```

There is no entry point yet. To try the classes, create a small class with a `main` method:

```java
import java.math.BigDecimal;

public class Demo {
    public static void main(String[] args) {
        Budget holidays = new Budget(new BigDecimal("500.00"), "Holidays");
        holidays.defineMin(new BigDecimal("50"));

        holidays.spend(new BigDecimal("45.50"), "Dinner");
        holidays.add(new BigDecimal("100"), "Top-up");

        System.out.println(holidays);                       // balance: 554.50
        holidays.getTransactions().forEach(System.out::println);
    }
}
```

```bash
javac Budget.java Transaction.java Demo.java
java Demo
```

## Design notes

- **Balance is derived, not stored.** `moneyLeft()` is computed from the initial amount and the transactions list. This keeps the history authoritative and makes rebuilding a budget from a file a matter of replaying its transactions.
- **Limits are balance limits.** `min` (default `0`) is the lowest balance allowed and `max` (default: none) is the highest. A transaction that would break either limit throws an `IllegalStateException`.
- **The domain layer does no I/O.** Neither class prints anything; presentation and persistence are meant to live in separate classes.

## Roadmap

- [x] `Budget` and `Transaction` domain classes
- [ ] `BudgetManager` to hold and manage multiple budgets
- [ ] Plain-text file format for budgets and transactions
- [ ] `Storage` class to save and load that file
- [ ] Terminal menu (`Main`) with input validation
- [ ] Summaries and alerts (total spent, percentage of limit used)
- [ ] Unit tests (JUnit)

## License

No license has been chosen yet.
