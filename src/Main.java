import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String name;
        String tag;
        Scanner scanner = new Scanner(System.in);
        String ID;
        boolean run = true;
        while(run) {
            System.out.print("Welcome to LeagueAPI, enter a Riot ID: ");
            ID = scanner.nextLine();
            String[] array = ID.split("#");
            if(array.length == 2 && !array[0].equals("") && !array[1].equals("")) {
                run = false;
                name = array[0];
                tag = array[1];
                System.out.println("You are looking for " + ID);
            }
            else {
                System.out.println("Invalid Riot ID");
            }

        }

    }
}