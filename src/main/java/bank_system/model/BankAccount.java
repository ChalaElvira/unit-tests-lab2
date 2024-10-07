package bank_system.model;

public class BankAccount {
    private Long id;
    private double balance;

    public BankAccount(double initialBalance) {
        this(null, validateInitialBalance(initialBalance));
    }

    public BankAccount(Long id, double balance) {
        this.id = id;
        this.balance = validateInitialBalance(balance);
    }

    private static double validateInitialBalance(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        return initialBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
