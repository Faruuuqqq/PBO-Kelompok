/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KalkulatorScientific;

/*
Nama program : KalkulatorClass.java
Nama : Rosa Andini Ismayanti, Alesha Naila, Achmad Faruq Mahdison
NPM : 140810240048, 140810240068, 140810240080
Tanggal buat : 11/11/2025
Deskripsi : Class Kalkulator
----------------------------------------------------------------- */
import java.text.DecimalFormat;

public class KalkulatorClass {

    private double operand1 = 0;
    private String operator = "";
    private boolean startNewNumber = true; 
    
    private String currentDisplay = "0";
    private String expressionDisplay = "";

    // Format desimal
    private DecimalFormat df = new DecimalFormat("#.##########");

    // Getter untuk layar bawah (hasil/input)
    public String getDisplayValue() {
        return currentDisplay;
    }
    
    // Getter untuk layar atas (histori)
    public String getExpressionValue() {
        return expressionDisplay;
    }

    // --- Input Angka dan Desimal ---
    public void inputDigit(String digit) {
        if (startNewNumber) {
            currentDisplay = digit;
            startNewNumber = false;
            
            // Jika mulai angka baru, hapus histori lama (kecuali setelah operator)
            if (operator.isEmpty()) {
                expressionDisplay = "";
            }
        } else {
            if (currentDisplay.equals("0")) {
                currentDisplay = digit;
            } else {
                currentDisplay += digit;
            }
        }
    }

    public void inputDecimal() {
        if (startNewNumber) {
            currentDisplay = "0.";
            startNewNumber = false;
            if (operator.isEmpty()) {
                expressionDisplay = "";
            }
        } else if (!currentDisplay.contains(".")) {
            currentDisplay += ".";
        }
    }
    
    public void inputConstant(String constant) {
        if (constant.equals("π")) {
            currentDisplay = df.format(Math.PI);
            expressionDisplay = "π";
            startNewNumber = true;
        }
    }

    // --- Input Fungsi Dasar ---
    public void inputClear() {
        operand1 = 0;
        operator = "";
        currentDisplay = "0";
        expressionDisplay = ""; // Hapus di layar histori juga
        startNewNumber = true;
    }

    public void inputDelete() {
        if (startNewNumber) {
            return;
        }
        
        if (currentDisplay.length() > 1) {
            currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 1);
        } else {
            currentDisplay = "0";
            // Jangan set startNewNumber ke true di sini
        }
    }

    public void inputToggleSign() {
        if (currentDisplay.equals("0")) {
            return;
        }
        if (currentDisplay.startsWith("-")) {
            currentDisplay = currentDisplay.substring(1);
        } else {
            currentDisplay = "-" + currentDisplay;
        }
    }

    // --- Input Operasi Biner (+, -, *, /, ^) ---

    public void inputOperator(String op) {
        // Jika user ganti operator (misal 12 + lalu menekan *), ganti operatornya
        if (startNewNumber && !operator.isEmpty()) {
            operator = op;
            expressionDisplay = df.format(operand1) + " " + operator;
            return;
        }
        
        if (!operator.isEmpty()) {
            inputEquals();
        }
        
        operand1 = Double.parseDouble(currentDisplay);
        operator = op;
        
        // Perbarui layar histori
        expressionDisplay = df.format(operand1) + " " + operator;
        
        startNewNumber = true;
    }

    public void inputEquals() {
        if (operator.isEmpty() || startNewNumber) {
            return; 
        }

        double operand2 = Double.parseDouble(currentDisplay);
        
        // Simpan ekspresi lama
        String oldExpression = expressionDisplay; 
        
        switch (operator) {
            case "+":
                operand1 += operand2;
                break;
            case "-":
                operand1 -= operand2;
                break;
            case "*":
                operand1 *= operand2;
                break;
            case "÷":
                if (operand2 == 0) {
                    currentDisplay = "Error"; 
                    expressionDisplay = "Tidak bisa dibagi nol";
                    operator = "";
                    startNewNumber = true;
                    return;
                }
                operand1 /= operand2;
                break;
            case "Exp": 
                operand1 = operand1 * Math.pow(10, operand2);
                break;
            case "^": 
                operand1 = Math.pow(operand1, operand2);
                break;
        }

        // Perbarui kedua layar
        currentDisplay = df.format(operand1);
        expressionDisplay = oldExpression + " " + df.format(operand2) + " =";
        
        operator = ""; 
        startNewNumber = true;
    }

    // --- Input Operasi Uner (sin, cos, 1/x, dll) ---

    public void inputUnaryOperation(String op) {
        double number = Double.parseDouble(currentDisplay);
        double result = 0;
        
        // Siapkan teks untuk histori
        String expressionText = "";
        String formattedNumber = df.format(number);

        try {
            switch (op) {
                case "sin": 
                    result = Math.sin(Math.toRadians(number));
                    expressionText = "sin(" + formattedNumber + ")";
                    break;
                case "cos": 
                    result = Math.cos(Math.toRadians(number));
                    expressionText = "cos(" + formattedNumber + ")";
                    break;
                case "tan": 
                    result = Math.tan(Math.toRadians(number));
                    expressionText = "tan(" + formattedNumber + ")";
                    break;
                case "log": 
                    result = Math.log10(number);
                    expressionText = "log(" + formattedNumber + ")";
                    break;
                case "ln": 
                    result = Math.log(number);
                    expressionText = "ln(" + formattedNumber + ")";
                    break;
                case "√":
                    result = Math.sqrt(number);
                    expressionText = "√(" + formattedNumber + ")";
                    break;
                case "x!":
                    result = factorial((int) number); 
                    expressionText = "fact(" + formattedNumber + ")";
                    break;
                case "%":
                    result = number / 100.0;
                    expressionText = formattedNumber + "%";
                    break;
                case "sinh":
                    result = Math.sinh(Math.toRadians(number));
                    expressionText = "sinh(" + formattedNumber + ")";
                    break;
                case "cosh":
                    result = Math.cosh(Math.toRadians(number));
                    expressionText = "cosh(" + formattedNumber + ")";
                    break;
                case "tanh":
                    result = Math.tanh(Math.toRadians(number));
                    expressionText = "tanh(" + formattedNumber + ")";
                    break;
                case "1/x":
                    if (number == 0) {
                        throw new ArithmeticException("Divide by zero");
                    }
                    result = 1.0 / number;
                    expressionText = "1/(" + formattedNumber + ")";
                    break;
                case "x^2":
                    result = Math.pow(number, 2);
                    expressionText = "sqr(" + formattedNumber + ")";
                    break;
            }
            
            // Perbarui kedua layar
            currentDisplay = df.format(result);
            expressionDisplay = expressionText;
            
        } catch (Exception e) {
            currentDisplay = "Error";
            expressionDisplay = "Error";
        }
        
        startNewNumber = true; 
    }

    private double factorial(int n) {
        if (n < 0) return Double.NaN; 
        if (n == 0) return 1;
        double fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}