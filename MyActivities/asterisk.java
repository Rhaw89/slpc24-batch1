package MyActivities;

import java.util.Scanner;

public class asterisk {
    public static void main(String[] args) {
       // Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter the number of rows for the pyramid: ");
        //int rows = scanner.nextInt();
        
        // Outer loop to handle number of rows
        int[] stls = {1,2,3,4,5};
      
        for (int i = 1; i < 6; i++) {
            // Inner loop to handle spaces
            for (int j = 0; j < 6 - i - 1; j++) {
                System.out.print("");
            }
            // Inner loop to handle stars
            for (int k = 0; k < 1 + i - 1 ; k++) {
                for ( int stl: stls){
                System.out.print(stl);
                }
            }
            // Move to the next line
            System.out.println();
        
    }
    }
}
