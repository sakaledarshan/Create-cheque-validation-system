import java.util.HashMap;
import java.util.Map;

/**
 * BankDatabase
 * Simulates a bank database with account information and balances
 */
public class BankDatabase {
    private Map<String, Account> accounts;

    public BankDatabase() {
        // Initialize with some sample accounts
        accounts = new HashMap<>();
        initializeSampleAccounts();
    }

    /**
     * Initialize sample accounts for testing
     */
    private void initializeSampleAccounts() {
        // Add sample accounts with various balances
        accounts.put("12345678", new Account("12345678", "John Smith", 50000.00));
        accounts.put("1234567890", new Account("1234567890", "Jane Doe", 25000.00));
        accounts.put("98765432", new Account("98765432", "Bob Johnson", 100000.00));
        accounts.put("5544332211", new Account("5544332211", "Alice Williams", 7500.00));
        accounts.put("1122334455", new Account("1122334455", "Charlie Brown", 150000.00));
        accounts.put("99887766", new Account("99887766", "Diana Prince", 35000.00));
    }

    /**
     * Check if an account exists
     */
    public boolean accountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    /**
     * Get account balance
     */
    public double getBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }

    /**
     * Get account holder name
     */
    public String getAccountHolderName(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getHolderName() : null;
    }

    /**
     * Display all accounts (for debugging)
     */
    public void displayAllAccounts() {
        System.out.println("Available Accounts:");
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account acc = entry.getValue();
            System.out.printf("  Account: %s | Holder: %s | Balance: $%.2f%n",
                    entry.getKey(), acc.getHolderName(), acc.getBalance());
        }
    }

    /**
     * Inner class representing a bank account
     */
    private static class Account {
        private String accountNumber;
        private String holderName;
        private double balance;

        public Account(String accountNumber, String holderName, double balance) {
            this.accountNumber = accountNumber;
            this.holderName = holderName;
            this.balance = balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getHolderName() {
            return holderName;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}