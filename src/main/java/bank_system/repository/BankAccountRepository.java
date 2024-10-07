package bank_system.repository;

import bank_system.model.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class BankAccountRepository {
    private final List<BankAccount> bankAccounts = new ArrayList<>();

    public void save(BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }

    public void delete(BankAccount bankAccount) {
        bankAccounts.remove(bankAccount);
    }

    public void update(BankAccount bankAccount) {
        for (int i = 0; i < bankAccounts.size(); i++) {
            BankAccount existingAccount = bankAccounts.get(i);
            if (bankAccount.getId().equals(existingAccount.getId())) {
                bankAccounts.set(i, new BankAccount(existingAccount.getId(), bankAccount.getBalance()));
                return;
            }
        }
        throw new IllegalArgumentException("Account not found");
    }


    public BankAccount findById(Long id) {
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getId().equals(id)) {
                return bankAccount;
            }
        }
        throw new IllegalArgumentException("Account not found");
    }
}
