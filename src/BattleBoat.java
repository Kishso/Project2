//Written by clung007
public class BattleBoat {
    private int[][] boat;
    private int length;
    private char symbol;

    //Automatic Constructor
    public BattleBoat(int size, char sym, char[][] board){

        boat = new int[size][2];
        length = size;
        symbol = sym;

        this.placeBoatRandom(board);

    }//End of Automatic Constructor

    //Manual Constructor
    public BattleBoat(int size, char sym, char[][] board, int originRow, int originCol, char dir){

        boat = new int[size][2];
        length = size;
        symbol = sym;

        this.fillSpaces(originRow, originCol,dir);
        if(this.placeBoat(board)){
            System.out.println("Boat Placed.");
        }else{
            System.out.println("Boat is Invalid.");
        }

    }//End of Manual Constructor

    //Getter and Setters
    public char getSymbol(){return symbol;}

    //Helper Method, returns true if coordinates belong to the boat
    public boolean isBoatThere(int row, int col){
        for(int[] coords: boat){
            if(coords[0] == row && coords[1] == col){
                return true;
            }
        }
        return false;
    }

    //Helper Method, takes in a coordinate and direction and calculates the reamining coordinates and stores them
    //in the boat variable
    //Used by: generateNewPlacement
    public void fillSpaces(int originRow,int originCol, char direction){
        if(direction == 'h'){
            for(int i = 0; i < length; i++){
                boat[i][0] = originRow;
                boat[i][1] = originCol + i;
            }
        }
        if(direction == 'v'){
            for(int i = 0; i < length; i++){
                boat[i][0] = originRow + i;
                boat[i][1] = originCol;
            }
        }
    }
    //Takes in a max value(size of the board) and generates a random direction and starting to coordinate,
    //then calls a helper method to fill in the remaining coordinates in boat
    //Used by: placeBoatRandom
    public void generateNewPlacement(int max){
        //Determine Direction
        char dir = ' ';
        if(Math.random() > 0.5){
            dir = 'h';
        }else{
            dir = 'v';
        }
        //Determine Origin
        int originX = (int)(Math.random()*max);
        int originY = (int)(Math.random()*max);

        this.fillSpaces(originX,originY,dir);
    }
    //Attempts to place a boat, returns false if space is occupied or out of bounds. If it can succesfully place
    //the boat, will modify the board and return true.
    //Used by: placeBoatRandom
    public boolean placeBoat(char[][] board){
        for(int i = 0; i < boat.length;i++){
            if((boat[i][0] > board.length-1) || (boat[i][1] > board.length-1) || !(board[boat[i][0]][boat[i][1]] == '_')){
                //System.out.println("Could not place boat");
                return false;
            }
        }
        for(int i = 0; i < boat.length;i++){
            board[boat[i][0]][boat[i][1]] = symbol;
            }
        return true;
        }
    //Keeps attempting to place the boat in a random location until successful
    //Used by: Constructor
    public void placeBoatRandom(char[][] board){
        do{
            this.generateNewPlacement(board.length);
        }while(!(this.placeBoat(board)));
    }

    public boolean isSunk(char[][] board){
        for(int[] coord: boat){//For each coord in boat
            if(board[coord[0]][coord[1]] != 'X'){//If is not hit
                return false;
            }
        }
        return true;
    }
}
