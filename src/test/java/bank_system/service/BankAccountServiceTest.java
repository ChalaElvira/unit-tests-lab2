package bank_system.service;

import bank_system.model.BankAccount;
import bank_system.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountServiceTest {
    private BankAccountService service;

    @BeforeEach
    public void setUp() {
        BankAccountRepository repository = new BankAccountRepository();
        service = new BankAccountService(repository);
    }

    private BankAccount createAccountWithBalance(double initialBalance) {
        return service.createAccount(initialBalance);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 500, 500",
            "1500, 750, 750",
            "2000, 1500, 500"
    })
    void testWithdraw(double initialBalance, double withdrawAmount, double expectedBalance) {
        BankAccount account = createAccountWithBalance(initialBalance);
        service.withdraw(account.getId(), withdrawAmount);
        assertEquals(expectedBalance, service.getBalance(account.getId()),
                "Balance after withdrawal should match expected value");
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 500, 1500",
            "1500, 1000, 2500",
            "2000, 500, 2500"
    })
    void testDeposit(double initialBalance, double depositAmount, double expectedBalance) {
        BankAccount account = createAccountWithBalance(initialBalance);
        service.deposit(account.getId(), depositAmount);
        assertEquals(expectedBalance, service.getBalance(account.getId()),
                "Balance after deposit should match expected value");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -100, -500})
    void testDepositInvalidAmount(double depositAmount) {
        BankAccount account = createAccountWithBalance(1000);
        assertThrows(IllegalArgumentException.class, () -> {
            service.deposit(account.getId(), depositAmount);
        }, "Should throw an exception for invalid deposit amounts");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -100, -500})
    void testWithdrawInvalidAmount(double withdrawAmount) {
        BankAccount account = createAccountWithBalance(1000);
        assertThrows(IllegalArgumentException.class, () -> {
            service.withdraw(account.getId(), withdrawAmount);
        }, "Should throw an exception for invalid withdraw amounts");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-500, -1000})
    void testCreateAccountWithNegativeInitialBalance(double initialBalance) {
        assertThrows(IllegalArgumentException.class, () -> {
            service.createAccount(initialBalance);
        }, "Should throw an exception when creating an account with negative balance");
    }

    @Test
    void testMultipleTransactions() {
        BankAccount account = createAccountWithBalance(1000);
        service.deposit(account.getId(), 200);
        service.withdraw(account.getId(), 150);
        service.deposit(account.getId(), 350);
        service.withdraw(account.getId(), 400);
        assertEquals(1000, service.getBalance(account.getId()),
                "Balance after multiple transactions should be 1000");
    }

    @Test
    void testAccountNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getBalance(999L);
        }, "Should throw an exception if account ID is not found");
    }
}
