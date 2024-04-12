package MyActivities;

import java.util.Scanner;

public class asterisk {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of rows for the pyramid: ");
        int rows = scanner.nextInt();
        
        // Outer loop to handle number of rows
        for (int i = 0; i < rows; i++) {
            // Inner loop to handle spaces
            for (int j = 0; j < rows - i - 1; j++) {
                System.out.print("");
            }
            // Inner loop to handle stars
            for (int k = 0; k < 2 * i + 1; k++) {
                System.out.print("*");
            }
            // Move to the next line
            System.out.println();
        }
    }
}
