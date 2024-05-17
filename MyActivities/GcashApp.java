package MyActivities;

/**
 *
 * @author Cristopher Bago
 */
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class CheckBalance{
    private double balance;

    public CheckBalance(double balance) {
        this.balance = balance;
    }
}
class UserAuthentication {
    
    private int id;
    private String name;
    private String email;
    private int number;
    private int pin;
    private int amount;
    
    public UserAuthentication(int id, int pin, String name, String email, int number, int amount) {
        this.id = id;
        this.pin = pin;
        this.name = name;
        this.email = email;
        this.number = number;
        this.amount = amount;
        
    
    }
}
  
public class GcashApp {
    
            static final String userFilePath ="/workspaces/slpc24-batch1/MyActivities/userdata.txt";
            static int record =0;
            
            
            public static void main(String[] args) {
           
           Scanner scanner = new Scanner(System.in);
           
           boolean running = true;
           
           while (running) {
                System.out.println("GCash Application");
                System.out.println("1. Registration");
                System.out.println("2. Login");
                System.out.println("3. EXIT...");
                
                int Option = scanner.nextInt();
            
           
            switch (Option) {
               case 1:
                   Registration(scanner);
    
                         
                   break;
               case 2:
                   int id = LogIn(scanner);
                    if (id != -1) {
                       running = false;
                       
                       boolean loginPhase = true;
                       
                       while (loginPhase){
                           
                       System.out.println("1. Cash IN");
                       System.out.println("2. Cash OUT");
                       System.out.println("3. Check Balance");
                       System.out.println("4. Send Money");
                       System.out.println("5. Change PIN");
                       System.out.println("6. Log Out");
                       int x =scanner.nextInt();
                       
                       switch (x) {
                           case 1:
                                CashIn(scanner, id);
                               break;
                               case 2:
                                CashOut(scanner, id);
                               break;
                               case 3:
                                CheckBalance(id);
                               break;
                               case 4:
                                SendMoney(scanner, id);
                               break;
                               case 5:
                                   ChangePin(scanner, id);
                               break;
                               case 6:
                                 loginPhase = false;
                                 running = true;
                               break;
                           default:
                               System.out.println("Invalid Option!");
                               break;
                       }
                       }
                     
                  }
                   break;
               case 3:
                   running = false;
                   break;
               default:
                   System.out.println("Invalid Option!");
                   break;
            }
           }
           scanner.close();
       }
            
       public static void ChangePin(Scanner scanner, int id){
            
            try {
        File userPath = new File(userFilePath);
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(userPath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean change = false;

        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            int userId = Integer.parseInt(userData[0]);

            if (userId == id) {
                System.out.println("Enter Current PIN:");
                int currentPin = scanner.nextInt();

                if (currentPin == Integer.parseInt(userData[1])) {
                    System.out.println("Enter New PIN:");
                    int newPin = scanner.nextInt();

                    userData[1] = String.valueOf(newPin);
                    line = String.join(",", userData);
                    change = true;
                } else {
                    System.out.println("Incorrect PIN.");
                }
            }
            writer.write(line + System.lineSeparator());
        }

        reader.close();
        writer.close();

        if (change) {
            userPath.delete();
            tempFile.renameTo(userPath);
            System.out.println("PIN changed successfully.");
        } else {
            tempFile.delete();
        }
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Failed to change PIN.");
    }
  
           
           
       }
       public static void SendMoney(Scanner scanner, int Sender){
           System.out.println("Enter Reciever ID:");
           int recieverID = scanner.nextInt();
           System.out.println("Enter Amount to SEND:");
           double inputAmount = scanner.nextDouble();
           
           try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))){
               List<String> lines = new ArrayList<>();
               String line;
               while ((line = reader.readLine())!=null) {                   
                   String [] userdata = line.split(",");
                   if (Integer.parseInt(userdata[0])== Sender) {
                       double balance = Double.parseDouble(userdata[4]);
                       if ( balance>= inputAmount){
                           balance -= inputAmount;
                      
                       userdata[4] = String.valueOf(balance);
                       line = String.join(",", userdata);
                        }else{
                           System.out.println("Insufficient Balance!");
                           return;
                       }
                   }
                   if (Integer.parseInt(userdata[0])== recieverID) {
                       double balance = Double.parseDouble(userdata[4]);
                      
                           balance += inputAmount;
                      
                       userdata[4] = String.valueOf(balance);
                       line = String.join(",", userdata);
                   }   
                   lines.add(line);
               }
               try (BufferedWriter writeDataCashIn = new BufferedWriter(new FileWriter(userFilePath))){
                   for (String UpdateLine : lines) {
                       writeDataCashIn.write(UpdateLine);
                       writeDataCashIn.newLine();
                   }
                   System.out.println("Money Sent!");
                   
               } catch (IOException cashin) {
                   cashin.printStackTrace();
                   System.out.println("Failed to Transfer");
               }
           } catch (IOException e) {
               e.printStackTrace();
               System.out.println("Failed to read data");
           }
           
       }
       public static void CashOut(Scanner scanner, int id){
           System.out.println("Enter amount to CASH OUT");
           double inputAmount = scanner.nextDouble();
           
           try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))){
               List<String> lines = new ArrayList<>();
               String line;
               while ((line = reader.readLine())!=null) {                   
                   String [] userdata = line.split(",");
                   if (Integer.parseInt(userdata[0])== id) {
                       double balance = Double.parseDouble(userdata[4]);
                       balance -= inputAmount;
                       userdata[4] = String.valueOf(balance);
                       line = String.join(",", userdata);
                   }
                   lines.add(line);
               }
               try (BufferedWriter writeDataCashIn = new BufferedWriter(new FileWriter(userFilePath))){
                   for (String UpdateLine : lines) {
                       writeDataCashIn.write(UpdateLine);
                       writeDataCashIn.newLine();
                   }
                   System.out.println("Successfuly Cash OUT");
                   
               } catch (IOException cashin) {
                   cashin.printStackTrace();
                   System.out.println("Failed to Cash OUT");
               }
           } catch (IOException e) {
               e.printStackTrace();
               System.out.println("Failed to read data");
           }
       }
       public static void CashIn(Scanner scanner, int id){
           System.out.println("Enter amount to CASH IN");
           double inputAmount = scanner.nextDouble();
           
           try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))){
               List<String> lines = new ArrayList<>();
               String line;
               while ((line = reader.readLine())!=null) {                   
                   String [] userdata = line.split(",");
                   if (Integer.parseInt(userdata[0])== id) {
                       double balance = Double.parseDouble(userdata[4]);
                       balance += inputAmount;
                       userdata[4] = String.valueOf(balance);
                       line = String.join(",", userdata);
                   }
                   lines.add(line);
               }
               try (BufferedWriter writeDataCashIn = new BufferedWriter(new FileWriter(userFilePath))){
                   for (String UpdateLine : lines) {
                       writeDataCashIn.write(UpdateLine);
                       writeDataCashIn.newLine();
                   }
                   System.out.println("Successfuly Cash IN");
                   
               } catch (IOException cashin) {
                   cashin.printStackTrace();
                   System.out.println("Failed to Cash IN");
               }
           } catch (IOException e) {
               e.printStackTrace();
               System.out.println("Failed to read data");
           }
           
       
       }
       
       public static int LogIn(Scanner scanner){
           System.out.println("USER ID");
           int id = scanner.nextInt();
           System.out.println("Enter PIN");
           int pin = scanner.nextInt();
           
           try (BufferedReader login = new BufferedReader(new FileReader(userFilePath))){
               String line;
               while ((line = login.readLine())!=null) {                   
                   String []userdata =line.split(",");
                   if (Integer.parseInt(userdata[0]) == id && Integer.parseInt(userdata[1]) == pin) {
                       System.out.println("Log in Successfuly!");
                       return id;
                   }
               }
                              System.out.println("USERID or PIN not found!");

           } catch (IOException e) {
               e.printStackTrace();
           }
           return -1;
       }
       
       public static void CheckBalance(int id){
            
           try (BufferedReader checkbalance = new BufferedReader(new FileReader(userFilePath))) {
               String line;
               while ((line = checkbalance.readLine()) != null) { 
                   String [] userdata = line.split(",");
                   if (Integer.parseInt(userdata[0]) == id) {
                       double balance = Double.parseDouble(userdata[4]);
                       System.out.println("Hello  "+ userdata[2] + " Current Balance: $ "+ balance);
                       
                       return;
                   }
               }
               System.out.println("USER not found!");
           } catch (IOException e) {
               e.printStackTrace();
               System.out.println("Failed to read data!");
           }
       
       }
      
    public static void Registration(Scanner scanner){
        System.out.println("Registration Window");
        System.out.println("Enter USERID");
        int userID = scanner.nextInt();
        System.out.println("Enter PIN");
        int pin = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Name");
        String name = scanner.nextLine();
        System.out.println("Enter Email");
        String email = scanner.nextLine();
        System.out.println("Enter Number");
        int number = scanner.nextInt();
       DataRegistration(userID,pin,name,email,number);
        
      
    }
     public static void DataRegistration(int userID, int pin, String name, String email, int number){
         
           
           try (BufferedWriter write = new BufferedWriter(new FileWriter(userFilePath,true))) {
               String writeUser = String.format("%d,%d,%s,%s,%d%n", userID, pin, name, email, number);
               write.write(writeUser);
               System.out.println("Registration Complete");
               
            
         } catch (IOException e) {
             e.printStackTrace();
               System.out.println("Failed to Register");
         }finally{
               displayRecord();
           }
       }
     public static void displayRecord(){
               try(BufferedReader read = new BufferedReader(new FileReader(userFilePath))) {
                   String line;
                   String lastLine="";
                   while ((line = read.readLine())!= null) {
                       lastLine =line;
                       
                   }
                   System.out.println(lastLine);
         } catch (IOException e) {
             e.printStackTrace();
                   System.err.println("No line to display");
         }
     }    
}
