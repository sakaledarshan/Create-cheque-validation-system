
public class Cheque {
    private String chequeNumber;
    private String accountNumber;
    private String chequeDate;
    private String payeeName;
    private double amount;
    private String signatureStatus;

    public Cheque(String chequeNumber, String accountNumber, String chequeDate,
                  String payeeName, double amount, String signatureStatus) {
        this.chequeNumber = chequeNumber;
        this.accountNumber = accountNumber;
        this.chequeDate = chequeDate;
        this.payeeName = payeeName;
        this.amount = amount;
        this.signatureStatus = signatureStatus;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public double getAmount() {
        return amount;
    }

    public String getSignatureStatus() {
        return signatureStatus;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSignatureStatus(String signatureStatus) {
        this.signatureStatus = signatureStatus;
    }

    @Override
    public String toString() {
        return "Cheque{" +
                "chequeNumber='" + chequeNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", chequeDate='" + chequeDate + '\'' +
                ", payeeName='" + payeeName + '\'' +
                ", amount=" + amount +
                ", signatureStatus='" + signatureStatus + '\'' +
                '}';
    }
}
