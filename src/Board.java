import java.util.Random;

public class Board {

    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 5;

    Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println("BOARD...");
        System.out.println(board);
        board.moveTile(new RowCol(0,0), new RowCol(1,0));
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

    private RowCol[] findMatch(Tile[] tiles) {
        int size = tiles.length;
        RowCol[] match = new RowCol[size];
        
        for(int i = 0; i < size - 2; i++){

            int numMatch = 0; 
            while((i+numMatch) < size && tiles[i].getType() == tiles[i+numMatch].getType()){
                match[numMatch] = tiles[i+numMatch].getPosition();
                numMatch++;
            }

            if(numMatch >= 3) return match;

        }
        return null;
    }

    private RowCol[] findMatchInRow(int row) {
        return findMatch(tiles[row]);
    }

    private RowCol[] findMatchInCol(int col) {
        Tile[] match = new Tile[NUM_ROWS];
        for(int i = 0; i < NUM_ROWS; i++) match[i] = tiles[i][col];
        return findMatch(match);
    }

    private Tile[] shift(Tile[] shifts, boolean isCol, int amnt) {
        Tile temp;
        int size = shifts.length;
        Tile[] shifted = new Tile[size];

        for(int i = 0; i < size; i++) {
            int newX = shifts[i].getPosition().getX();
            int newY = shifts[i].getPosition().getY();

            if(isCol) newY = (i + amnt)%size;
            else newX = (i + amnt)%size;

            if(newY < 0) newY = size + newY;
            if(newX < 0) newX = size + newX;

            RowCol newPos = new RowCol(newX,newY);
            shifts[i].setPosition(newPos);
        }

        for(int i = 0; i < size; i++){
            if(isCol) shifted[shifts[i].getPosition().getY()] = shifts[i];
            else shifted[shifts[i].getPosition().getX()] = shifts[i];
        }

        return shifted;
    }

    private void shiftRow(int row, int amnt) {
        tiles[row] = shift(tiles[row], false, amnt);
    }

    private void shiftCol(int col, int amnt) {
        Tile[] shifts = new Tile[NUM_COLS];

        for(int i = 0; i < NUM_ROWS; i++) shifts[i] = tiles[i][col];

        shifts = shift(shifts, true, amnt);

        for(int i = 0; i < NUM_ROWS; i++) tiles[i][col] = shifts[i];
    }

    private void initTiles() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                placeTile(new Tile(new RowCol(col,row), randomType()));
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
        return tiles[pos.getY()][pos.getX()];
    }

    private void placeTile(Tile tile){
        tiles[tile.getPosition().getY()][tile.getPosition().getX()] = tile;
    }

}
