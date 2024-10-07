package bank_system.repository;

import bank_system.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountRepositoryTest {
    private BankAccountRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new BankAccountRepository();
    }

    private BankAccount createBankAccount() {
        return new BankAccount(0L,1000);
    }

    @Test
    void testSave() {
        BankAccount account = createBankAccount();
        repository.save(account);

        assertEquals(account, repository.findById(account.getId()),
                "The saved account should be retrievable by ID");
    }

    @Test
    void testDelete() {
        BankAccount account = createBankAccount();
        repository.save(account);
        repository.delete(account);

        assertThrows(IllegalArgumentException.class, () -> {
            repository.findById(account.getId());
        }, "Account should not be found after deletion");
    }

    @Test
    void testUpdate() {
        BankAccount account = new BankAccount(0L,1000);
        repository.save(account);

        BankAccount updatedAccount = new BankAccount(0L,2000);
        repository.update(updatedAccount);

        assertEquals(2000, repository.findById(account.getId()).getBalance(),
                "The account balance should be updated to the new value");
    }

    @Test
    void testUpdateNonExistentAccount() {
        BankAccount account = createBankAccount();

        assertThrows(IllegalArgumentException.class, () -> {
            repository.update(account);
        }, "Should throw an exception when trying to update a non-existent account");
    }

    @Test
    void testFindById() {
        BankAccount account = createBankAccount();
        repository.save(account);

        assertEquals(account, repository.findById(account.getId()),
                "Should return the correct account by ID");
    }

    @Test
    void testFindByIdNonExistent() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.findById(999L);
        }, "Should throw an exception when trying to find a non-existent account");
    }
}
