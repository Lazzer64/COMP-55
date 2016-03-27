import java.util.Random;

public class Board {

    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 5;

    Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println("BOARD...");
        System.out.println(board);
    }

    public Board(){
        initTiles();
    }

    public void moveTile(RowCol start, RowCol end) {
        // TODO implement
    }

    public int[] getMatches() {
        // TODO implement
        return null;
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

}
