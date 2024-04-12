package lesson2;

public class ConditionalSC {
    public static void main(String[] args) {
        int timeofday=10;
        int Daytime=6;
    switch (timeofday) {
        default: System.out.println("Invalid Entry"); break;

        case 1: System.out.println("good Morning"); break;
        case 2: System.out.println("Good Afternoon"); break;
        case 3: System.out.println("good Evening"); break;
        case 4: System.out.println("Wow morning"); break;
        case 5: System.out.println("Friday"); break;
        case 6: System.out.println("Saturday"); break; // timeofday =((true)?5:0):((false)?7:2): break;
    
            
    }
    }
    
}
