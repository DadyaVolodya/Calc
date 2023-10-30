package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите операцию через пробел (Пр. 2 + 2 либо II + II)");
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        String[] data = input.split(" ");
        if (data.length != 3) {
            throw new IllegalArgumentException("Неверный формат выражения");
        }

        String operand1 = data[0];
        String operator = data[1];
        String operand2 = data[2];

        boolean isRoman = isRomanNumber(operand1) && isRomanNumber(operand2);
        boolean isArabic = isArabicNumber(operand1) && isArabicNumber(operand2);

        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Неверный формат чисел");
        }

        int num1, num2;
        if (isRoman) {
            num1 = romanToArabic(operand1);
            num2 = romanToArabic(operand2);
        } else {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 10");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль недопустимо");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неверный оператор");
        }

        if (isRoman) {
            if (result < 1) {
                throw new IllegalArgumentException("Результат вычислений с римскими числами не может быть отрицательным или нулевым");
            }
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    static boolean isRomanNumber(String number) {
        String romanPattern = "^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        return number.matches(romanPattern);
    }

    static boolean isArabicNumber(String number) {
        try {
            int value = Integer.parseInt(number);
            return value >= 1 && value <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static int romanToArabic(String number) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = number.length() - 1; i >= 0; i--) {
            int value = romanMap.get(number.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }

        return result;
    }

    static String arabicToRoman(int number) {
        StringBuilder roman = new StringBuilder();
        int[] arabianValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < arabianValues.length; i++) {
            while (number >= arabianValues[i]) {
                roman.append(romanSymbols[i]);
                number -= arabianValues[i];
            }
        }

        return roman.toString();
    }
}

