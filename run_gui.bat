@echo off
echo Cheque Validation System - GUI Demo
echo ===================================
echo.
echo Compiling Java files...
javac *.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)
echo.
echo Compilation successful!
echo.
echo Launching GUI application...
echo (Close the GUI window when done)
echo.
java ChequeValidationGUI
echo.
echo GUI application closed.
pause