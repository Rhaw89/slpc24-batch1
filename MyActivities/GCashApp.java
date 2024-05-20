package MyActivities;
/**
 *
 * @author Cristopher Bago
 */
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.time.ZoneId;


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
    private int contactNumber;
    private int pin;
    private double amount;
    
    public UserAuthentication(int id, int pin, String name, String email, int contactNumber, double amount) {
        this.id = id;
        this.pin = pin;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.amount = amount;
            
    }
    public int getId() {
        return id;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getNumber() {
        return contactNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
  
public class GCashApp {
    
            static final String userFilePath ="/workspaces/slpc24-batch1/MyActivities/userdata.txt";
            static final String transactionFilePath ="/workspaces/slpc24-batch1/MyActivities/transaction.txt";
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
                       System.out.println("6. View Transactions");
                       System.out.println("7. Log Out");
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
                               viewTransactions(scanner, id);
                           break;
                               case 7:
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

       public static void viewTransactions(Scanner scanner, int id){

            try (BufferedReader reader = new BufferedReader(new FileReader(transactionFilePath))) {
                String line;
                boolean read = false;
                    System.out.println("Transactions:");
                while ((line = reader.readLine())!=null) {
                    String[] userdata = line.split(",");

                    if (userdata.length >=5 && Integer.parseInt(userdata[1].trim()) == id){
                        System.out.println(line);
                        read = true;
                    }
                    
                }
                if (!read) {
                    System.out.println("No Transactions found!");
                    
                }
              
            } catch (IOException e) {
                // TODO: handle exception
                    e.printStackTrace();
                    System.out.println("Failed to read Transaction!");
            }

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
            if (userData.length>=6){
               
                int userId = Integer.parseInt(userData[0].trim());
            
            

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
               boolean senderUpdate = false; 
               boolean recieverUpdate = false;

               while ((line = reader.readLine())!=null) {                   
                   String [] userdata = line.split(",");
                   if(userdata.length >=6){
                    String userL = userdata[0].trim();
                    String userBalance = userdata[5].trim();
                    if(!userL.isEmpty() && !userBalance.isEmpty()){
                        int useridL = Integer.parseInt(userL);
                        double userBal = Double.parseDouble(userBalance);
                        if(useridL == Sender){
                                if (userBal >= inputAmount){
                                    userBal -= inputAmount;
                                    userdata[5] = String.valueOf(userBal);
                                    line = String.join(",", userdata);
                                    senderUpdate = true;
                                } else{
                                    System.out.println("Insufficient Balance");
                                    return;
                                }   
                        }
                    }
                   }
                
                if(userdata.length >=6){
                    String userL = userdata[0].trim();
                    String userBalance = userdata[5].trim();
                    if(!userL.isEmpty() && !userBalance.isEmpty()){
                        int useridL = Integer.parseInt(userL);
                        double userBal = Double.parseDouble(userBalance);
                        if(useridL == recieverID){                              
                                    userBal += inputAmount;
                                    userdata[5] = String.valueOf(userBal);
                                    line = String.join(",", userdata);
                                    recieverUpdate = true;                              
                        }
                    }
                   }
                   lines.add(line);
               }
                if (senderUpdate && recieverUpdate) {
                    try (BufferedWriter writeData = new BufferedWriter(new FileWriter(userFilePath))){
                        for (String UpdateLine: lines) {
                            writeData.write(UpdateLine);
                            writeData.newLine();
                        }
                    } 
                    ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Manila"));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
                    recordTransaction("Send Money", Sender, recieverID, inputAmount, dateTime.format(formatter));

                    System.out.println("Money Sent!");
                }else{
                    System.out.println("Sender or Reciever not found!");
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

                        boolean Founduser =false;


                            while ((line = reader.readLine())!=null) {                   
                                String [] userdata = line.split(",");

                                if (userdata.length>=6){
                                String userIdL = userdata[0].trim();
                                String userAmount = userdata[5].trim();
                                if(!userIdL.isEmpty() && !userAmount.isEmpty()){
                                    int userL = Integer.parseInt(userIdL);
                                    double amountL = Double.parseDouble(userAmount);
                                    if (userL == id ){
                                        Founduser = true;
                                        amountL -= inputAmount;
                                        userdata[5] = String.valueOf(amountL);
                                        line = String.join(",", userdata);
                    
                            }
                        }
                        }
                        lines.add(line);
                        }
                            if (Founduser) {
                            
                            try(BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath))){
                                for (String UpdateLine : lines){
                                    writer.write(UpdateLine);
                                    writer.newLine();
                                }
                            }
                            ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Manila"));
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

                            recordTransaction("Cash Out", id, id, inputAmount, dateTime.format(formatter));
                            System.out.println("Cash Out Successfully");
                    }else{
                    System.out.println("User ID not found!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                System.out.println("Failed to read data");
                }catch(NumberFormatException e){
                        e.printStackTrace();
                        System.out.println("Invalid Number Format!");
                }
  
    }
       public static void CashIn(Scanner scanner, int id){
           System.out.println("Enter amount to CASH IN");
           double inputAmount = scanner.nextDouble();
          
           
           try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))){
               List<String> lines = new ArrayList<>();
               String line;

              boolean Founduser =false;


               while ((line = reader.readLine())!=null) {                   
                   String [] userdata = line.split(",");

                   if (userdata.length>=6){
                    String userIdL = userdata[0].trim();
                    String userAmount = userdata[5].trim();
                    if(!userIdL.isEmpty() && !userAmount.isEmpty()){
                        int userL = Integer.parseInt(userIdL);
                        double amountL = Double.parseDouble(userAmount);
                        if (userL == id ){
                            Founduser = true;
                            amountL += inputAmount;
                            userdata[5] = String.valueOf(amountL);
                            line = String.join(",", userdata);
         
               }
            }
            }
            lines.add(line);
            }
               if (Founduser) {
               
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath))){
                    for (String UpdateLine : lines){
                        writer.write(UpdateLine);
                        writer.newLine();
                    }
                }
                
                ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Manila"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

                recordTransaction("Cash In", id, id, inputAmount, dateTime.format(formatter));
                System.out.println("Cash In Successfully!");
       }else{
        System.out.println("User ID not found!");
       }
   } catch (IOException e) {
        e.printStackTrace();
    System.out.println("Failed to read data");
    }catch(NumberFormatException e){
            e.printStackTrace();
            System.out.println("Invalid Number Format!");
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

                   if (userdata.length>=2){
                    String useridL = userdata[0].trim();
                    String userpinL = userdata[1].trim();
                    if (!useridL.isEmpty() && !userpinL.isEmpty()){
                        int user = Integer.parseInt(useridL);
                        int userpin = Integer.parseInt(userpinL);
                        if (user == id && userpin == pin){
                  
                       System.out.println("Log in Successfuly!");
                       return id;
                 }
            }
            }
            }
                              System.out.println("USERID or PIN not found!");

           } catch (IOException e) {
                 e.printStackTrace();
                 System.out.println("Error reading file!");
           }catch(NumberFormatException e){
                 e.printStackTrace();
                System.out.println("Invalid Number Format!");
           }
           return -1;
       }
       
       public static void CheckBalance(int id){
            
           try (BufferedReader checkbalance = new BufferedReader(new FileReader(userFilePath))) {
               String line;
               boolean update = false;

               while ((line = checkbalance.readLine()) != null) { 
                   String [] userdata = line.split(",");
                  
                   if (userdata.length >=6){
                    String userIdL = userdata[0].trim();
                    String userBalance = userdata[5].trim();
                    if (!userIdL.isEmpty() && !userBalance.isEmpty()){
                            int idL = Integer.parseInt(userIdL);
                            double uBalance = Double.parseDouble(userBalance);
                    if(idL == id){
                        System.out.println(userdata[2] +" GCash Balance: Php "+ uBalance);
                        update = true;
                    }

                    }
                   }
                   
             } 
             }catch (IOException e) {
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
        double amount = 0.00;
        System.out.println("Enter Contact Number");
       int contactNumber = scanner.nextInt();

       DataRegistration(userID,pin,name,email,contactNumber, amount);
        
      
    }
     public static void DataRegistration(int userID, int pin, String name, String email, int contactNumber,double amount){
         
           
           try (BufferedWriter write = new BufferedWriter(new FileWriter(userFilePath,true))) {
               String writeUser = String.format("%d,%d,%s,%s,%d,%.2f%n", userID, pin, name, email, contactNumber, amount);
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
     public static void recordTransaction(String type, int senderId, int recieverId, double amount, String dateTime){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFilePath, true))){
                String transactionOutput = String.format("%s, %d, %d, %.2f, %s%n", type, senderId, recieverId, amount, dateTime);
                writer.write(transactionOutput);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to record trasaction!");
            }
     }  
}
