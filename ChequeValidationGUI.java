import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Cheque Validation System - GUI Version
 * Main GUI application class that provides a graphical interface for cheque validation
 */
public class ChequeValidationGUI extends JFrame {
    private BankDatabase bankDatabase = new BankDatabase();

    // Input fields
    private JTextField chequeNumberField;
    private JTextField accountNumberField;
    private JTextField chequeDateField;
    private JTextField payeeNameField;
    private JTextField amountField;
    private JComboBox<String> signatureStatusCombo;

    // Output areas
    private JTextArea resultsArea;
    private JTextArea summaryArea;

    // Buttons
    private JButton validateButton;
    private JButton clearButton;

    public ChequeValidationGUI() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cheque Validation System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void initializeComponents() {
        // Input fields
        chequeNumberField = new JTextField(20);
        accountNumberField = new JTextField(20);
        chequeDateField = new JTextField(20);
        payeeNameField = new JTextField(20);
        amountField = new JTextField(20);
        signatureStatusCombo = new JComboBox<>(new String[]{"PRESENT", "MISSING"});

        // Output areas
        resultsArea = new JTextArea(15, 60);
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        resultsArea.setBackground(new Color(248, 248, 248));

        summaryArea = new JTextArea(5, 60);
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        summaryArea.setBackground(new Color(240, 240, 240));

        // Buttons
        validateButton = new JButton("Validate Cheque");
        validateButton.setBackground(new Color(46, 125, 50));
        validateButton.setForeground(Color.WHITE);
        validateButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        clearButton = new JButton("Clear Form");
        clearButton.setBackground(new Color(96, 96, 96));
        clearButton.setForeground(Color.WHITE);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(33, 150, 243));
        JLabel headerLabel = new JLabel("🏦 Cheque Validation System", JLabel.CENTER);
        headerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Cheque Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 1: Cheque Number
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Cheque Number (6-10 digits):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(chequeNumberField, gbc);

        // Row 2: Account Number
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Account Number (8-12 digits):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(accountNumberField, gbc);

        // Row 3: Cheque Date
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Cheque Date (DD/MM/YYYY):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(chequeDateField, gbc);

        // Row 4: Payee Name
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Payee Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(payeeNameField, gbc);

        // Row 5: Amount
        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Amount ($):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(amountField, gbc);

        // Row 6: Signature Status
        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(new JLabel("Signature Status:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(signatureStatusCombo, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(validateButton);
        buttonPanel.add(clearButton);
        inputPanel.add(buttonPanel, gbc);

        add(new JScrollPane(inputPanel), BorderLayout.WEST);

        // Output panel
        JPanel outputPanel = new JPanel(new BorderLayout());

        // Summary area
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Cheque Summary"));
        summaryPanel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);
        outputPanel.add(summaryPanel, BorderLayout.NORTH);

        // Results area
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Validation Results"));
        resultsPanel.add(new JScrollPane(resultsArea), BorderLayout.CENTER);
        outputPanel.add(resultsPanel, BorderLayout.CENTER);

        add(outputPanel, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateCheque();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }

    private void validateCheque() {
        try {
            // Get input values
            String chequeNumber = chequeNumberField.getText().trim();
            String accountNumber = accountNumberField.getText().trim();
            String chequeDate = chequeDateField.getText().trim();
            String payeeName = payeeNameField.getText().trim();
            String amountText = amountField.getText().trim();
            String signatureStatus = (String) signatureStatusCombo.getSelectedItem();

            // Parse amount
            double amount = 0;
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException ex) {
                showError("Invalid amount format. Please enter a valid number.");
                return;
            }

            // Create cheque object
            Cheque cheque = new Cheque(chequeNumber, accountNumber, chequeDate,
                                       payeeName, amount, signatureStatus);

            // Validate
            ChequeValidator validator = new ChequeValidator(bankDatabase);
            ValidationResult result = validator.validate(cheque);

            // Display results
            displaySummary(cheque);
            displayValidationResults(result);

        } catch (Exception ex) {
            showError("An error occurred during validation: " + ex.getMessage());
        }
    }

    private void displaySummary(Cheque cheque) {
        StringBuilder summary = new StringBuilder();
        summary.append("Cheque Number: ").append(cheque.getChequeNumber()).append("\n");
        summary.append("Account Number: ").append(cheque.getAccountNumber()).append("\n");
        summary.append("Date: ").append(cheque.getChequeDate()).append("\n");
        summary.append("Payee: ").append(cheque.getPayeeName()).append("\n");
        summary.append("Amount: $").append(String.format("%,.2f", cheque.getAmount())).append("\n");
        summary.append("Signature: ").append(cheque.getSignatureStatus()).append("\n");

        summaryArea.setText(summary.toString());
    }

    private void displayValidationResults(ValidationResult result) {
        StringBuilder results = new StringBuilder();
        results.append("FIELD-BY-FIELD VALIDATION RESULTS\n");
        results.append("===================================\n\n");

        // Cheque Number
        results.append(String.format("%-20s | %-8s | %s\n",
            "Cheque Number",
            result.isChequeNumberValid() ? "✓ PASS" : "✗ FAIL",
            result.isChequeNumberValid() ? "Valid format" : result.getChequeNumberError()));

        // Account Number
        results.append(String.format("%-20s | %-8s | %s\n",
            "Account Number",
            result.isAccountNumberValid() ? "✓ PASS" : "✗ FAIL",
            result.isAccountNumberValid() ? "Valid format" : result.getAccountNumberError()));

        // Date
        results.append(String.format("%-20s | %-8s | %s\n",
            "Cheque Date",
            result.isDateValid() ? "✓ PASS" : "✗ FAIL",
            result.isDateValid() ? "Not expired" : result.getDateError()));

        // Signature
        results.append(String.format("%-20s | %-8s | %s\n",
            "Signature Status",
            result.isSignatureValid() ? "✓ PASS" : "✗ FAIL",
            result.isSignatureValid() ? "Verified" : result.getSignatureError()));

        // Balance
        results.append(String.format("%-20s | %-8s | %s\n",
            "Available Balance",
            result.isBalanceValid() ? "✓ PASS" : "✗ FAIL",
            result.isBalanceValid() ? "Sufficient" : result.getBalanceError()));

        results.append("\n===================================\n");

        // Overall result
        if (result.isApproved()) {
            results.append("🎉 CHEQUE APPROVED - PAYMENT AUTHORIZED 🎉\n");
            results.append("All validation checks passed successfully.\n");
            results.append("Payment processing is now authorized.");
            resultsArea.setForeground(new Color(34, 139, 34)); // Dark green
        } else {
            results.append("❌ CHEQUE REJECTED - PAYMENT DENIED ❌\n");
            results.append("Reason: ").append(result.getRejectionReason()).append("\n");
            results.append("Please review the validation errors above.");
            resultsArea.setForeground(new Color(220, 20, 60)); // Crimson red
        }

        resultsArea.setText(results.toString());
    }

    private void clearForm() {
        chequeNumberField.setText("");
        accountNumberField.setText("");
        chequeDateField.setText("");
        payeeNameField.setText("");
        amountField.setText("");
        signatureStatusCombo.setSelectedIndex(0);
        summaryArea.setText("");
        resultsArea.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChequeValidationGUI().setVisible(true);
            }
        });
    }
}