import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * ChequeValidator
 * Validates cheque details based on business rules
 */
public class ChequeValidator {
    private BankDatabase bankDatabase;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ChequeValidator(BankDatabase bankDatabase) {
        this.bankDatabase = bankDatabase;
    }

    /**
     * Validate all cheque details
     */
    public ValidationResult validate(Cheque cheque) {
        boolean chequeNumberValid = validateChequeNumber(cheque.getChequeNumber());
        boolean accountNumberValid = validateAccountNumber(cheque.getAccountNumber());
        boolean dateValid = validateDate(cheque.getChequeDate());
        boolean signatureValid = validateSignature(cheque.getSignatureStatus());
        boolean balanceValid = validateBalance(cheque.getAccountNumber(), cheque.getAmount());

        return new ValidationResult(
            chequeNumberValid,
            accountNumberValid,
            dateValid,
            signatureValid,
            balanceValid,
            getChequeNumberError(),
            getAccountNumberError(),
            getDateError(),
            getSignatureError(),
            getBalanceError()
        );
    }

    /**
     * Validate cheque number (6-10 digits)
     */
    private boolean validateChequeNumber(String chequeNumber) {
        clearErrors();
        
        if (chequeNumber == null || chequeNumber.isEmpty()) {
            setChequeNumberError("Empty");
            return false;
        }
        
        if (!chequeNumber.matches("\\d+")) {
            setChequeNumberError("Non-numeric");
            return false;
        }
        
        if (chequeNumber.length() < 6 || chequeNumber.length() > 10) {
            setChequeNumberError("Invalid length");
            return false;
        }
        
        return true;
    }

    /**
     * Validate account number (8-12 digits)
     */
    private boolean validateAccountNumber(String accountNumber) {
        clearErrors();
        
        if (accountNumber == null || accountNumber.isEmpty()) {
            setAccountNumberError("Empty");
            return false;
        }
        
        if (!accountNumber.matches("\\d+")) {
            setAccountNumberError("Non-numeric");
            return false;
        }
        
        if (accountNumber.length() < 8 || accountNumber.length() > 12) {
            setAccountNumberError("Invalid length");
            return false;
        }
        
        // Check if account exists in database
        if (!bankDatabase.accountExists(accountNumber)) {
            setAccountNumberError("Not found");
            return false;
        }
        
        return true;
    }

    /**
     * Validate cheque date (not expired, not future)
     */
    private boolean validateDate(String chequeDate) {
        clearErrors();
        
        if (chequeDate == null || chequeDate.isEmpty()) {
            setDateError("Empty");
            return false;
        }
        
        LocalDate date;
        try {
            date = LocalDate.parse(chequeDate, dateFormatter);
        } catch (DateTimeParseException e) {
            setDateError("Invalid format");
            return false;
        }
        
        LocalDate today = LocalDate.now();
        
        // Check if date is in the future
        if (date.isAfter(today)) {
            setDateError("Future date");
            return false;
        }
        
        // Check if cheque is expired (more than 6 months old)
        long monthsDiff = ChronoUnit.MONTHS.between(date, today);
        if (monthsDiff > 6) {
            setDateError("Expired (>6mo)");
            return false;
        }
        
        return true;
    }

    /**
     * Validate signature status
     */
    private boolean validateSignature(String signatureStatus) {
        clearErrors();
        
        if (signatureStatus == null || signatureStatus.isEmpty()) {
            setSignatureError("Empty");
            return false;
        }
        
        if (!signatureStatus.equals("PRESENT")) {
            setSignatureError("Missing");
            return false;
        }
        
        return true;
    }

    /**
     * Validate available balance
     */
    private boolean validateBalance(String accountNumber, double amount) {
        clearErrors();
        
        if (amount <= 0) {
            setBalanceError("Invalid amount");
            return false;
        }
        
        if (!bankDatabase.accountExists(accountNumber)) {
            setBalanceError("Account N/A");
            return false;
        }
        
        double availableBalance = bankDatabase.getBalance(accountNumber);
        if (availableBalance < amount) {
            setBalanceError("Insufficient");
            return false;
        }
        
        return true;
    }

    // Error storage
    private String chequeNumberError = "";
    private String accountNumberError = "";
    private String dateError = "";
    private String signatureError = "";
    private String balanceError = "";

    private void clearErrors() {
        chequeNumberError = "";
        accountNumberError = "";
        dateError = "";
        signatureError = "";
        balanceError = "";
    }

    private void setChequeNumberError(String error) {
        this.chequeNumberError = error;
    }

    private void setAccountNumberError(String error) {
        this.accountNumberError = error;
    }

    private void setDateError(String error) {
        this.dateError = error;
    }

    private void setSignatureError(String error) {
        this.signatureError = error;
    }

    private void setBalanceError(String error) {
        this.balanceError = error;
    }

    // Getters for errors
    private String getChequeNumberError() {
        return chequeNumberError;
    }

    private String getAccountNumberError() {
        return accountNumberError;
    }

    private String getDateError() {
        return dateError;
    }

    private String getSignatureError() {
        return signatureError;
    }

    private String getBalanceError() {
        return balanceError;
    }
}