//Written by clung007
public class BattleBoatsBoard {

    private int drone;
    private int missle;
    private char[][] board;
    private BattleBoat[] boats;
    private int size;
    private boolean skipNextTurn = false;

    public BattleBoatsBoard(int size){
        this.size = size;
        //Build Board
        board = new char[size][size];
        for(int x=0; x<size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = '_';
            }
        }
        //STANDARD MODE
        if(size == 8){
            boats = new BattleBoat[5];
            drone = 1;
            missle = 1;
            //Place Boats
            boats[0] = new BattleBoat(2,'a',board);
            boats[1] = new BattleBoat(3,'b',board);
            boats[2] = new BattleBoat(3,'c',board);
            boats[3] = new BattleBoat(4,'d',board);
            boats[4] = new BattleBoat(5,'e',board);
        }
        //EXPERT MODE
        if(size == 12){
            boats = new BattleBoat[10];
            drone = 2;
            missle = 2;
            //Place Boats
            boats[0] = new BattleBoat(2,'a',board);
            boats[1] = new BattleBoat(2,'b',board);
            boats[2] = new BattleBoat(3,'c',board);
            boats[3] = new BattleBoat(3,'d',board);
            boats[4] = new BattleBoat(3,'e',board);
            boats[5] = new BattleBoat(3,'f',board);
            boats[6] = new BattleBoat(4,'g',board);
            boats[7] = new BattleBoat(4,'h',board);
            boats[8] = new BattleBoat(5,'i',board);
            boats[9] = new BattleBoat(5,'j',board);

        }
    }//End of Constructor

    //Getters and Setters
    public char[][] getBoard(){return board;}
    public boolean doSkip(){return skipNextTurn;}
    public int getMissles(){return missle;}
    public int getDrones(){return drone;}
    public int getSize(){return size;}

    //Skip Turn
    public void skipTurn(){
        skipNextTurn = false;
    }

    //Gets a boat if coordinates are correct
    public BattleBoat getBoat(int row, int col){
        for(BattleBoat boat : boats){
            if(boat.isBoatThere(row,col)){
                return boat;
            }
        }
        return null;
    }


    //Typical Fire
    //Returns a string depending on the outcome.
    public String fire(int row, int col){
        if(row < 0 || col < 0 || row>= size || col>= size){
            skipNextTurn = true;
            return "Out of Bounds. Penalty.";
        }
        char target = board[row][col];
        switch(target){
            case 'X':
                skipNextTurn = true;
                return "Already fired there! Penalty.";
            case 'O':
                skipNextTurn = true;
                return "Already fired there! Penalty.";
            case '_':
                board[row][col] = 'O';
                return "Miss";
            default:
                board[row][col] = 'X';
                return "Hit";
        }
    }//End of fire

    //Scans a row or column and returns spots with boats
    public int drone(String mode, int n){
        drone -= 1;
        int result = 0;
        for(int i = 0; i < size; i++){
            if(mode.equals("row") || mode.equals("r")){
                if(board[n][i] != 'O' && board[n][i] != '_'){
                        result += 1;
                }
            }
            if(mode.equals("col") || mode.equals("c")){
                if(board[i][n] != 'O' && board[i][n] != '_'){
                    result += 1;
                }
            }
        }
        return result;
    }//End of drone

    //Hits a 3x3 area, returns false if out of range, true if the launch was succesful
    public boolean missle(int row, int col){
        if(row >= size || col >= size || row < 0 || col < 0){
            System.out.println("Invalid Input, try again.");
            return false;
        }else{
            for(int i = -1; i<2; i++){
                for(int j = -1; j<2; j++){
                    fire(row + i,col + j);
                }
            }
            missle -= 1;
            skipNextTurn = false;//Prevent a false penalty
            return true;
        }
    }

    //Checks to see if Game is Over
    public boolean isGameOver(){
        for(BattleBoat boat : boats){
            if(!boat.isSunk(board)){
                return false;
            }
        }
        return true;
    }
    //Shows Boat Locations
    public String toString(){
        String result = "";
        for(int row = 0; row < size;row++){
            for(int col = 0; col < size; col++){
                result = result + "["+board[row][col]+"]";
            }
            result = result + "\n";
        }
        return result;
    }
    //Shows Info Only Avaiable to User
    public String toDisplay(){
        String result = "\n";
        for(int row = 0; row < size;row++){
            for(int col = 0; col < size; col++){
                if(board[row][col] == 'X'){
                    result = result + "["+this.getBoat(row,col).getSymbol()+"]";
                }else if(board[row][col] == 'O'){
                    result = result + "[O]";
                }else{
                    result = result + "[_]";
                }
            }
            result = result + "\n";
        }
        return result;
    }
}//End of Class
