import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    final static byte MONTHS_IN_YEAR = 12;
    final static byte PERCENT = 100;

    public static void main(String[] args) {
        int principal = (int) readNumber("Trazeni kredit (1,000 - 1,000,000 BAM)", 1_000, 1_000_000);
        float annualInterest = (float) readNumber("Kamatna stopa (%)", 1, 20);
        byte years = (byte) readNumber("Rok otplate (godina)", 1, 30);

        printMortgage(principal, annualInterest, years);
        printPaymentSchedule(principal, annualInterest, years);
    }

    private static void printMortgage(int principal, float annualInterest, byte years) {
        double mortgage = calculateMortgage(principal, annualInterest, years);
        String formattedMortgage = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println();
        System.out.print("Mjesecni anuitet: " + formattedMortgage);
        System.out.println();
    }

    private static void printPaymentSchedule(int principal, float annualInterest, byte years) {
        System.out.println();
        System.out.println("Plan otplate:");
        for (short month = 1; month <= years * MONTHS_IN_YEAR; month++) {
            double balance = calculateBalance(principal, annualInterest, years, month);
            String formattedBalance = NumberFormat.getCurrencyInstance().format(balance);
            System.out.println(formattedBalance);
        }
    }

    public static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;
        while (true) {
            System.out.print(prompt + ": ");
            value = scanner.nextDouble();
            if (value >= min && value <= max) break;
            System.out.println("Unesi vrijednost izmedju " + min + " i " + max);
        }
        return value;
    }

    public static double calculateBalance(int principal, float annualInterest, byte years, short numberOfPaymentsMade) {
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        short numberOfPayments = (short) (years * MONTHS_IN_YEAR);

        double powerOfInterest = Math.pow(1 + monthlyInterest, numberOfPayments);
        double powerOfMortgage = Math.pow(1 + monthlyInterest, numberOfPaymentsMade);

        return principal * ((powerOfInterest - powerOfMortgage) / (powerOfInterest - 1));
    }

    public static double calculateMortgage(int principal, float annualInterest, byte years) {
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        short numberOfPayments = (short) (years * MONTHS_IN_YEAR);

        double powerOfInterest = Math.pow(1 + monthlyInterest, numberOfPayments);
        return principal * ((monthlyInterest * powerOfInterest) / (powerOfInterest - 1));
    }
}