/**
 * ValidationResult
 * Stores the results of all cheque validations
 */
public class ValidationResult {
    private boolean chequeNumberValid;
    private boolean accountNumberValid;
    private boolean dateValid;
    private boolean signatureValid;
    private boolean balanceValid;

    private String chequeNumberError;
    private String accountNumberError;
    private String dateError;
    private String signatureError;
    private String balanceError;

    public ValidationResult(boolean chequeNumberValid, boolean accountNumberValid,
                           boolean dateValid, boolean signatureValid, boolean balanceValid,
                           String chequeNumberError, String accountNumberError,
                           String dateError, String signatureError, String balanceError) {
        this.chequeNumberValid = chequeNumberValid;
        this.accountNumberValid = accountNumberValid;
        this.dateValid = dateValid;
        this.signatureValid = signatureValid;
        this.balanceValid = balanceValid;

        this.chequeNumberError = chequeNumberError;
        this.accountNumberError = accountNumberError;
        this.dateError = dateError;
        this.signatureError = signatureError;
        this.balanceError = balanceError;
    }

    // Individual validation status
    public boolean isChequeNumberValid() {
        return chequeNumberValid;
    }

    public boolean isAccountNumberValid() {
        return accountNumberValid;
    }

    public boolean isDateValid() {
        return dateValid;
    }

    public boolean isSignatureValid() {
        return signatureValid;
    }

    public boolean isBalanceValid() {
        return balanceValid;
    }

    // Error messages
    public String getChequeNumberError() {
        return chequeNumberError;
    }

    public String getAccountNumberError() {
        return accountNumberError;
    }

    public String getDateError() {
        return dateError;
    }

    public String getSignatureError() {
        return signatureError;
    }

    public String getBalanceError() {
        return balanceError;
    }

    /**
     * Check if all validations passed
     */
    public boolean isApproved() {
        return chequeNumberValid && accountNumberValid && 
               dateValid && signatureValid && balanceValid;
    }

    /**
     * Get the primary rejection reason
     */
    public String getRejectionReason() {
        if (chequeNumberValid && accountNumberValid && 
            dateValid && signatureValid && balanceValid) {
            return "All validations passed";
        }

        if (!chequeNumberValid) return "Invalid cheque number - " + chequeNumberError;
        if (!accountNumberValid) return "Invalid account number - " + accountNumberError;
        if (!dateValid) return "Invalid or expired date - " + dateError;
        if (!signatureValid) return "Signature missing or invalid - " + signatureError;
        if (!balanceValid) return "Insufficient balance - " + balanceError;

        return "Unknown validation failure";
    }
}