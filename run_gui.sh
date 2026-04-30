#!/bin/bash
echo "Cheque Validation System - GUI Demo"
echo "===================================="
echo
echo "Compiling Java files..."
javac *.java
if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi
echo
echo "Compilation successful!"
echo
echo "Launching GUI application..."
echo "(Close the GUI window when done)"
echo
java ChequeValidationGUI
echo
echo "GUI application closed."