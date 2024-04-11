/* This is 
* a Multiline
*Comment

*/

public class Main{
    public static void main(String[] args) {
        Plant plant1 = new Plant();
        plant1.numberOfLeaves = 10;
        plant1.numberOfStem = 2;
        System.out.println("Number of Leaves:"+plant1.numberOfLeaves);
        System.out.println("Number of Stem:"+plant1.numberOfStem);
        
        plant1.numberOfLeaves();
        System.out.println("number of Leaves:"+plant1.numberOfLeaves);
    }

}
/*
*plant class has numbberofLeaves and numberofStems
*also method addNumberOfLeaves to modify
* */

class Plant{
    int numberOfLeaves;
    int numberOfStem;
    /* addNumberOfLeaves - to modify numberOfLeaves
     * @Param
     */
    public void addNumberOfLeaves(){
        numberOfLeaves++;
    }
}