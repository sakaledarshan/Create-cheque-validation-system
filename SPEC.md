# Cheque Validation System - Specification

## Project Overview
- **Project Name**: Cheque Validation System
- **Type**: Java Desktop Application
- **Core Functionality**: Validates cheque details including cheque number, account number, date, signature status, and available balance before processing payments
- **Target Users**: Bank staff, tellers, or automated banking systems

## Functionality Specification

### Core Features

1. **Cheque Data Input**
   - Cheque number (6-10 digits)
   - Account number (8-12 digits)
   - Cheque date (DD/MM/YYYY format)
   - Payee name
   - Amount (positive number)
   - Signature status (present/missing)

2. **Validation Rules**
   - **Cheque Number**: Must be 6-10 digits, numeric only
   - **Account Number**: Must be 8-12 digits, numeric only
   - **Date**: Must not be expired (more than 6 months old) or future dated
   - **Signature**: Must be present and verified
   - **Balance**: Must be sufficient (>= cheque amount)

3. **Validation Results**
   - Display validation status for each field
   - Show overall approval/rejection message
   - Provide detailed error messages for failed validations

### User Interactions
- Input cheque details via console
- View validation results for each field
- See final approval/rejection decision

### Data Handling
- In-memory storage for demo (simulated bank database)
- Mock account balance data for validation testing

## Acceptance Criteria
1. All five validation checks (cheque number, account number, date, signature, balance) work correctly
2. Clear error messages displayed for invalid fields
3. Overall approval message shown when all validations pass
4. Overall rejection message shown when any validation fails
5. Code compiles without errors
6. Program runs and produces expected output