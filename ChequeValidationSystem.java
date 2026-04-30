import java.util.Scanner;

/**
 * Cheque Validation System
 * Main application class that handles user input and displays validation results
 * Enhanced UI Design with colors and polished interface
 */
public class ChequeValidationSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static BankDatabase bankDatabase = new BankDatabase();

    // ANSI Color codes for Windows compatibility
    private static final String RESET = "\033[0m";
    private static final String BOLD = "\033[1m";
    private static final String CYAN = "\033[36m";
    private static final String GREEN = "\033[32m";
    private static final String RED = "\033[31m";
    private static final String YELLOW = "\033[33m";
    private static final String BLUE = "\033[34m";
    private static final String MAGENTA = "\033[35m";

    public static void main(String[] args) {
        // Check if GUI mode is requested
        if (args.length > 0 && args[0].equalsIgnoreCase("--gui")) {
            // Launch GUI version
            ChequeValidationGUI.main(args);
            return;
        }

        printHeader();

        // Ask user to choose interface
        System.out.println(CYAN + "  Choose your interface:" + RESET);
        System.out.println("  1. Console Interface (Current)");
        System.out.println("  2. Graphical User Interface (GUI)");
        System.out.println();
        System.out.print(BOLD + "  Enter your choice (1 or 2): " + RESET);

        String choice = scanner.nextLine().trim();

        if (choice.equals("2")) {
            System.out.println(YELLOW + "  Launching GUI version..." + RESET);
            System.out.println(CYAN + "  Note: Close this console window after the GUI opens." + RESET);
            System.out.println();

            // Launch GUI in a separate thread
            new Thread(() -> {
                ChequeValidationGUI.main(new String[]{});
            }).start();

            // Wait a moment then exit console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Ignore
            }
            return;
        }

        // Continue with console version
        boolean continueProcessing = true;
        while (continueProcessing) {
            printChequeInputScreen();
            continueProcessing = askToContinue();
        }

        printFooter();
        scanner.close();
    }

    /**
     * Print the main header with branding
     */
    private static void printHeader() {
        System.out.println();
        System.out.println(BLUE + BOLD + "╔" + "═".repeat(58) + "╗" + RESET);
        System.out.println(BLUE + "║" + " ".repeat(15) + CYAN + "🏦 CHEQUE VALIDATION SYSTEM 🏦" + BLUE + " ".repeat(15) + "║" + RESET);
        System.out.println(BLUE + BOLD + "╚" + "═".repeat(58) + "╝" + RESET);
        System.out.println();
        System.out.println(CYAN + "  Welcome to the Secure Cheque Processing Portal" + RESET);
        System.out.println(YELLOW + "  " + "▓".repeat(50) + RESET);
        System.out.println();
    }

    /**
     * Print cheque input screen
     */
    private static void printChequeInputScreen() {
        System.out.println(MAGENTA + BOLD + "┌" + "─".repeat(60) + "┐" + RESET);
        System.out.println(MAGENTA + "│" + CYAN + " 📝 ENTER CHEQUE DETAILS" + " ".repeat(28) + MAGENTA + "│" + RESET);
        System.out.println(MAGENTA + "└" + "─".repeat(60) + "┘" + RESET);
        System.out.println();

        // Input cheque number
        System.out.print(BOLD + "  👉 Cheque Number " + RESET + "(6-10 digits): ");
        String chequeNumber = scanner.nextLine().trim();

        // Input account number
        System.out.print(BOLD + "  👉 Account Number " + RESET + "(8-12 digits): ");
        String accountNumber = scanner.nextLine().trim();

        // Input cheque date
        System.out.print(BOLD + "  👉 Cheque Date " + RESET + "(DD/MM/YYYY): ");
        String chequeDate = scanner.nextLine().trim();

        // Input payee name
        System.out.print(BOLD + "  👉 Payee Name " + RESET + ": ");
        String payeeName = scanner.nextLine().trim();

        // Input amount
        System.out.print(BOLD + "  👉 Amount " + RESET + "($): ");
        double amount = 0;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            amount = -1;
        }

        // Input signature status
        System.out.print(BOLD + "  👉 Signature Status " + RESET + "(PRESENT/MISSING): ");
        String signatureStatus = scanner.nextLine().trim().toUpperCase();

        // Create Cheque object
        Cheque cheque = new Cheque(chequeNumber, accountNumber, chequeDate, 
                                   payeeName, amount, signatureStatus);

        // Validate and display results
        System.out.println();
        printValidationResults(cheque);
    }

    /**
     * Print validation results with enhanced UI
     */
    private static void printValidationResults(Cheque cheque) {
        ChequeValidator validator = new ChequeValidator(bankDatabase);
        ValidationResult result = validator.validate(cheque);

        // Results header
        System.out.println(BLUE + BOLD + "┌" + "─".repeat(60) + "┐" + RESET);
        System.out.println(BLUE + "│" + CYAN + " ✅ VALIDATION RESULTS " + " ".repeat(33) + BLUE + "│" + RESET);
        System.out.println(BLUE + BOLD + "└" + "─".repeat(60) + "┘" + RESET);
        System.out.println();

        // Cheque info summary
        System.out.println(CYAN + "  📋 Cheque Summary:" + RESET);
        System.out.println("  ┌" + "─".repeat(56) + "┐");
        System.out.printf("  │ %-12s: %-42s │%n", "Cheque #", cheque.getChequeNumber());
        System.out.printf("  │ %-12s: %-42s │%n", "Account", cheque.getAccountNumber());
        System.out.printf("  │ %-12s: %-42s │%n", "Date", cheque.getChequeDate());
        System.out.printf("  │ %-12s: %-42s │%n", "Payee", truncate(cheque.getPayeeName(), 42));
        System.out.printf("  │ %-12s: $%-41s │%n", "Amount", String.format("%,.2f", cheque.getAmount()));
        System.out.printf("  │ %-12s: %-42s │%n", "Signature", cheque.getSignatureStatus());
        System.out.println("  └" + "─".repeat(56) + "┘");
        System.out.println();

        // Validation table header
        System.out.println(CYAN + "  📊 Field-by-Field Validation:" + RESET);
        System.out.println("  ┌" + "─".repeat(20) + "┬" + "─".repeat(16) + "┬" + "─".repeat(20) + "┐");
        System.out.printf("  │ %-19s │ %-15s │ %-19s │%n", "Validation Field", "Status", "Details");
        System.out.println("  ├" + "─".repeat(20) + "┼" + "─".repeat(16) + "┼" + "─".repeat(20) + "┤");

        // Cheque Number
        printValidationRow("Cheque Number", result.isChequeNumberValid(), 
            result.isChequeNumberValid() ? "Valid format" : result.getChequeNumberError());

        // Account Number
        printValidationRow("Account Number", result.isAccountNumberValid(), 
            result.isAccountNumberValid() ? "Valid format" : result.getAccountNumberError());

        // Date
        printValidationRow("Cheque Date", result.isDateValid(), 
            result.isDateValid() ? "Not expired" : result.getDateError());

        // Signature
        printValidationRow("Signature Status", result.isSignatureValid(), 
            result.isSignatureValid() ? "Verified" : result.getSignatureError());

        // Balance
        printValidationRow("Available Balance", result.isBalanceValid(), 
            result.isBalanceValid() ? "Sufficient" : result.getBalanceError());

        System.out.println("  └" + "─".repeat(20) + "┴" + "─".repeat(16) + "┴" + "─".repeat(20) + "┘");
        System.out.println();

        // Overall result
        if (result.isApproved()) {
            printApprovedMessage();
        } else {
            printRejectedMessage(result.getRejectionReason());
        }
    }

    /**
     * Print a single validation row
     */
    private static void printValidationRow(String field, boolean isValid, String details) {
        String status = isValid ? GREEN + "✓ PASS" + RESET : RED + "✗ FAIL" + RESET;
        String colorDetails = isValid ? GREEN : RED;
        System.out.printf("  │ %-19s │ %-15s │ " + colorDetails + "%-19s" + RESET + " │%n", 
            field, status, truncate(details, 19));
    }

    /**
     * Print approved message
     */
    private static void printApprovedMessage() {
        System.out.println(GREEN + "  ╔" + "═".repeat(56) + "╗" + RESET);
        System.out.println(GREEN + "  ║" + BOLD + " ✅ CHEQUE APPROVED - PAYMENT AUTHORIZED ✅" + " ".repeat(8) + GREEN + "║" + RESET);
        System.out.println(GREEN + "  ╠" + "═".repeat(56) + "╣" + RESET);
        System.out.println(GREEN + "  ║" + "  All validation checks passed successfully." + " ".repeat(14) + GREEN + "║" + RESET);
        System.out.println(GREEN + "  ║" + "  Payment processing is now authorized. ✅" + " ".repeat(19) + GREEN + "║" + RESET);
        System.out.println(GREEN + "  ╚" + "═".repeat(56) + "╝" + RESET);
    }

    /**
     * Print rejected message
     */
    private static void printRejectedMessage(String reason) {
        System.out.println(RED + "  ╔" + "═".repeat(56) + "╗" + RESET);
        System.out.println(RED + "  ║" + BOLD + " ❌ CHEQUE REJECTED - PAYMENT DENIED ❌" + " ".repeat(9) + RED + "║" + RESET);
        System.out.println(RED + "  ╠" + "═".repeat(56) + "╣" + RESET);
        System.out.println(RED + "  ║" + "  Reason: " + truncate(reason, 44) + RED + " ║" + RESET);
        System.out.println(RED + "  ║" + "  Please review the validation errors above." + " ".repeat(15) + RED + "║" + RESET);
        System.out.println(RED + "  ╚" + "═".repeat(56) + "╝" + RESET);
    }

    /**
     * Ask user if they want to continue processing
     */
    private static boolean askToContinue() {
        System.out.println();
        System.out.print(BOLD + CYAN + "  🔄 Process another cheque? (Y/N): " + RESET);
        String response = scanner.nextLine().trim().toUpperCase();
        return response.equals("Y") || response.equals("YES");
    }

    /**
     * Print footer
     */
    private static void printFooter() {
        System.out.println();
        System.out.println(YELLOW + "  Thank you for using Cheque Validation System!" + RESET);
        System.out.println(CYAN + "  " + "👋 Goodbye! Have a great day!" + RESET);
        System.out.println();
    }

    /**
     * Truncate string to specified length
     */
    private static String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 2) + "..";
    }
}