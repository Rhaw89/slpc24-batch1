import java.util.Scanner;

public class Snake {
    int count;
    public void Snake(){
        count = 4;
    }

    
        public static void main(String[] args) {
           // long x =10;
           // long y =(x=5);
           int y=1;
            boolean x = true || (y<4);
           // y *=x;   
          //  System.out.println(y);
          System.out.println("Input amount to load");
          Scanner intput = new Scanner(System.in);
          int inputload = intput.nextInt();
          int load = (inputload>100) ? (inputload*3) :0;
          System.out.println("adding crystal gem:"+load);

            System.out.println(x);
            Snake s = new Snake();

                     //short e = (short)1921222;
                      //System.out.println(e);
            } }
