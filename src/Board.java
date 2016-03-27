import java.util.Random;

public class Board {

    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 5;

    Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println("BOARD...");
        System.out.println(board);
        int[] matches = board.getMatches();
        System.out.println("Red Matches: "+matches[0]);
        System.out.println("Green Matches: "+matches[1]);
        System.out.println("Blue Matches: "+matches[2]);
    }

    public Board(){
        initTiles();
    }

    public void moveTile(RowCol start, RowCol end) {
        int x = end.getX() - start.getX();
        int y = end.getY() - start.getY();

        shiftRow(0,x);
        shiftCol(0,y);
    }

    public int[] getMatches() {

        int[] numMatches = new int[] {0,0,0};
        RowCol[][] matches = new RowCol[NUM_COLS+NUM_COLS][];

        for (int row = 0; row < NUM_ROWS; row++) matches[row] = findMatchInRow(row);
        for (int col = 0; col < NUM_COLS; col++) matches[NUM_ROWS+col] = findMatchInCol(col); 
        
        for(int i = 0; i < matches.length; i++){
            if(matches[i] != null){
                switch(tileAt(matches[i][0]).getType()){
                    case RED:
                        numMatches[0]++;
                        break;
                    case GREEN:
                        numMatches[1]++;
                        break;
                    case BLUE:
                        numMatches[2]++;
                        break;
                }
            }
        }

        return numMatches;
    }

    public void refillBoard() {
        // TODO implement
    } 

    public String toString(){
        String s = "";
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                s += tiles[row][col].getType().toString().charAt(0) + " ";
            }
            s += "\n";
        } 
        return s;
    }

    // Helpers

    private RowCol[] findMatchInRow(int row) {
        RowCol[] match = new RowCol[NUM_COLS];
        for(int i = 0; i < NUM_COLS - 2; i++){

            int numMatch = 0; 
            while((i+numMatch) < NUM_COLS && tiles[row][i].getType() == tiles[row][i+numMatch].getType()){
                match[numMatch] = tiles[row][i+numMatch].getPosition();
                numMatch++;
            }

            if(numMatch >= 3) return match;

        }
        return null;
    }

    private RowCol[] findMatchInCol(int col) {
        RowCol[] match = new RowCol[NUM_ROWS];
        for(int i = 0; i < NUM_ROWS - 2; i++){

            int numMatch = 0; 
            while((i+numMatch) < NUM_ROWS && tiles[i][col].getType() == tiles[i+numMatch][col].getType()){
                match[numMatch] = tiles[i+numMatch][col].getPosition();
                numMatch++;
            }

            if(numMatch >= 3) return match;

        }
        return null;
    }

    private void shiftRow(int row, int amnt) {
        Tile temp;
        for(int e = 0; e < Math.abs(amnt); e++){
            if(amnt > 0){

                temp = tiles[row][NUM_COLS-1]; 
                for(int i = NUM_COLS-1; i > 0; i--) tiles[row][i] = tiles[row][i - 1]; 
                tiles[row][0] = temp;

            } else {

                temp = tiles[row][0]; 
                for(int i = 0; i < NUM_COLS-1; i++) tiles[row][i] = tiles[row][i + 1]; 
                tiles[row][NUM_COLS-1] = temp;

            }
        }
    }

    private void shiftCol(int col, int amnt) {
        Tile temp;
        for(int e = 0; e < Math.abs(amnt); e++){
            if(amnt > 0){

                temp = tiles[NUM_ROWS-1][col]; 
                for(int i = NUM_ROWS-1; i > 0; i--) tiles[i][col] = tiles[i - 1][col]; 
                tiles[0][col] = temp;

            } else {

                temp = tiles[0][col]; 
                for(int i = 0; i < NUM_ROWS-1; i++) tiles[i][col] = tiles[i+1][col]; 
                tiles[NUM_ROWS-1][col] = temp;

            }
        }
    }

    private void initTiles() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                tiles[row][col] = new Tile(new RowCol(row,col), randomType());
            }
        }
    }

    private TileType randomType() {

        Random r = new Random();
        int roll = r.nextInt(3);

        if(roll == 0) return TileType.RED;
        if(roll == 1) return TileType.BLUE;
        return TileType.GREEN;
    }

    private Tile tileAt(RowCol pos){
        return tiles[pos.getX()][pos.getY()];
    }

}
