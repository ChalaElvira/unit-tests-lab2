package bank_system.service;

import bank_system.model.BankAccount;
import bank_system.repository.BankAccountRepository;

public class BankAccountService {
    BankAccountRepository bankAccountRepository;
    private Long id;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.id = 0L;
    }

    public BankAccount createAccount(double balance) {
        BankAccount account = new BankAccount(this.id++, balance);
        this.bankAccountRepository.save(account);
        return account;
    }

    public double getBalance(Long id) {
        BankAccount account = this.bankAccountRepository.findById(id);
        return account.getBalance();
    }

    public void withdraw(Long id, double amount) {
        BankAccount account = bankAccountRepository.findById(id);
        ifExist(account);
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        bankAccountRepository.update(account);
    }

    public void deposit(Long accountId, double amount) {
        BankAccount account = bankAccountRepository.findById(accountId);
        ifExist(account);
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        account.setBalance(account.getBalance() + amount);
        bankAccountRepository.update(account);
    }

    private void ifExist(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
    }
}
