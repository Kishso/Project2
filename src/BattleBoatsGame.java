//Written by clung007
import java.util.Scanner;

public class BattleBoatsGame {
    private int turns = 0;
    private int  shots = 0;
    private Scanner myScanner = new Scanner(System.in);
    private BattleBoatsBoard board;
    private String drone = "";
    private String help = "\nType in 'missle' or 'm' to use a missle.\n" +
            "Type in 'drone' or 'd' to use a drone.\n" +
            "Enter 'print' or 'p' to see the board again.\n"+
            "Enter coordinates in the format '<row> <col>' to fire.\n";

    public BattleBoatsGame(){
        String input = "";
        do{
            System.out.println("Type in 'standard' or 's' to play in standard mode.\n"+
                    "Type in 'expert' or 'e' to play in expert mode.\n");

            input = myScanner.nextLine();
            input = input.toLowerCase();
            if(input.equals("standard") || input.equals("s")){
                board = new BattleBoatsBoard(8);
                System.out.println(help);
            }else if(input.equals("expert") || input.equals("e")){
                board = new BattleBoatsBoard(12);
                System.out.println(help);
            }
        }while(!(input.equals("standard")||input.equals("s")||input.equals("expert")||input.equals("e")));
    }
    //Takes a turn, should return true if turn executed correctly
    public boolean turn(){
        turns += 1;
        //Skips a turn
        if(board.doSkip()){
            System.out.println("\nTurn Skipped.\n");
            board.skipTurn();
            return true;//Ends Turn
        }

        //Display Board
        System.out.println("\nCurrent Board:"+board.toDisplay());
        //Asks player what to do next
        do {
            String input = "";
            do {
                input = myScanner.nextLine();
                input = input.toLowerCase();
            }while((input.equals("")));//Check to see if anything was entered.

            //Print Board
            if (input.equals("print") || input.equals("p")) {
                System.out.print(drone);
                turns -= 1;//Doesnt count as a turn
                return true;
            }
            //Missile
            else if (input.equals("missle") || input.equals("m")) {
                if (board.getMissles() == 0) {
                    System.out.println("Out of Missles, try again.");
                } else {
                    boolean result = false;//To track wheather missle is invalid or not
                    do {
                        System.out.println("Enter center coordinates:");
                        String line = myScanner.nextLine();
                        String[] coords = line.split(" ");
                        try {
                            int row = Integer.parseInt(coords[0]);
                            int col = Integer.parseInt(coords[1]);
                            result = board.missle(row, col);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid coordinates, try again.");
                        }
                        if (result) { //If succesful turn is over
                            System.out.println("Missle Fired.\n");
                            return true;//Ends turn
                        }
                    } while (!result);//If false, invalid coordinates were entered. Try again.
                }
            }else if(input.equals("drone") || input.equals("d")) {//Drone
                if (board.getDrones() == 0) {
                    System.out.println("Out of Drones, try again.");
                }else{
                    String mode = "";
                    do {
                        System.out.println("Enter 'row' or 'col' to scan.");
                        mode = myScanner.nextLine();
                        mode = mode.toLowerCase();
                    } while (!(mode.equals("row") || mode.equals("col") || mode.equals("r") || mode.equals("c")));
                    int scan = 0;
                    do {
                        System.out.println("Enter a number to scan.");
                        scan = myScanner.nextInt();
                    } while (!(scan < board.getSize() && scan >= 0));
                    drone += "\nShip tiles found in "+mode+" "+scan+" : " + board.drone(mode,scan);
                    System.out.println(drone);
                    return true;//Ends turn
                }
            }else{//Fire
                try {
                    String[] coords = input.split(" ");
                    int row = Integer.parseInt(coords[0]);
                    int col = Integer.parseInt(coords[1]);
                    System.out.println(board.fire(row, col));
                    shots += 1;
                    return true;
                } catch (NumberFormatException n) {
                    System.out.println("Invalid input, try again.\n");
                    System.out.println(help);
                    turns -= 1;//Doesnt count as a turn
                } catch (ArrayIndexOutOfBoundsException a) {
                    System.out.println("Enter two numbers seperated by a space to fire, try again.\n");
                    turns -= 1;//Doesnt count as a turn
                }
            }
        } while(true);
    }

    public void main(){
        do{
            this.turn();
        }while(!(board.isGameOver()));
        System.out.println(board.toDisplay());
        System.out.println("\nYou sunk all the battle ships!");
        System.out.println("Total turns: "+turns);
        System.out.println("Total shots:"+shots);
    }
}
