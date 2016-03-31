import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
public class Board {

    int num_rows;
    int num_cols;

    Tile[][] tiles;

    static private Tile R(int x, int y) {return new Tile(new RowCol(x,y),TileType.RED);}
    static private Tile G(int x, int y) {return new Tile(new RowCol(x,y),TileType.GREEN);}
    static private Tile B(int x, int y) {return new Tile(new RowCol(x,y),TileType.BLUE);}

    public static void main(String[] args) {
        Board board = new Board(5, 6);

        board.tiles[4] = new Tile[]{R(0,4), R(1,4), R(2,4), B(3,4), B(4,4), B(5,4)};
        System.out.println(board.findMatchInRow(4));

        System.out.println("Initial Board...");
        System.out.println("----------");
        System.out.println(board);

        board.moveTile(new RowCol(0,0), new RowCol(1,0));
        System.out.println("Move 0,0 to 1,0");
        System.out.println("----------");
        System.out.println(board);

        Queue<Match> matches = board.getMatches();

        while(!matches.isEmpty()) {

            for (Match m: matches) {
                System.out.print(m.getType()+":");
                for(RowCol r: m.getPositions()) System.out.print("("+r.getX()+","+r.getY()+") ");
                System.out.println();
            }
            System.out.println("----------");
            System.out.println(board);

            board.dropTiles();
            System.out.println("Drop Tiles");
            System.out.println("----------");
            System.out.println(board); 

            board.refillBoard();
            System.out.println("Refill");
            System.out.println("----------");
            System.out.println(board);

            matches = board.getMatches();
        } 
        System.out.println("Final Board");
        System.out.println("----------");
        System.out.println(board);

    }

    public Board(int num_rows, int num_cols){
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        tiles = new Tile[num_rows][num_cols];
        initTiles();
    }

    public Tile[][] getTiles(){
        return tiles;
    }

    public void moveTile(RowCol start, RowCol end) {
        int x = end.getX() - start.getX();
        int y = end.getY() - start.getY();

        if(y == 0) shiftRow(start.getY(),x);
        else shiftCol(start.getX(),y);
    }

    public Queue<Match> getMatches() {

        Queue<Match> matchQ = new LinkedList<Match>();
        for (Match m: findAllMatches()) matchQ.add(m);

        removeMatches();
        return matchQ;
    }

    public void dropTiles() {
        for (int i = 0; i < num_cols; i++) {
            System.out.println(i);
            dropTilesInCol(i);
        }
    }

    public void refillBoard() {
        for (int col = 0; col < num_cols; col++) {
            Tile[] fill = getRefillTiles(col);
            for(int i = 0; i < fill.length; i++){
                tiles[i][col] = fill[i];
            }
        }
    } 

    public String toString(){
        String s = "";
        for (int row = 0; row < num_rows; row++) {
            for (int col = 0; col < num_cols; col++) {
                if(tiles[row][col] != null) s += tiles[row][col].getType().toString().charAt(0) + " ";
                else s += "X ";
            }
            s += "\n";
        } 
        return s;
    }

    // Helpers

    private Tile[] getRefillTiles(int col) {
        int numRefill = 0;
        for(int i = 0; i < num_rows; i++){
            if(tiles[i][col] == null) numRefill++;
            else break;
        }

        Tile[] refill = new Tile[numRefill];

        for(int i = 0; i < numRefill; i++) refill[i] = new Tile(new RowCol(col,i), TileType.randomType());

        return refill;
    }

    private void dropTilesInCol(int col) {
        for (int i = num_rows-1; i > 0; i--) {
            Tile open = tiles[i][col];

            if(open == null){

                for(int e = i-1; e >= 0; e--){
                    Tile fill = tiles[e][col];

                    if(fill != null){
                        tiles[e][col].getPosition().setY(i);
                        placeTile(tiles[e][col]);
                        tiles[e][col] = null;
                        break;
                    }
                }
            }
        }
    }


    private void removeMatches() {
        for(Match m: findAllMatches())
            for(RowCol t: m.getPositions()) 
                tiles[t.getY()][t.getX()] = null;
    }

    private List<Match> findAllMatches() {
        List<Match> matches = new ArrayList<Match>();

        for (int row = 0; row < num_rows; row++) 
            if(findMatchInRow(row) != null) matches.add(findMatchInRow(row));
        for (int col = 0; col < num_cols; col++) 
            if(findMatchInCol(col) != null) matches.add(findMatchInCol(col));
        return matches;
    }


    private Match findMatchIn(Tile[] tiles) {
        int size = tiles.length;
        
        for(int i = 0; i < size; i++){
            List<Tile> match = new LinkedList<Tile>();
            TileType type = tiles[i].getType();

            for(int e = i; e < size; e++){
                if(tiles[e] != null && tiles[e].getType() == type) match.add(tiles[e]);
                else e = size;
            }

            if(match.size() >= 3){
                Tile[] m = new Tile[match.size()]; match.toArray(m);
                return new Match(m);
            }

        }
        return null;
    }

    private Match findMatchInRow(int row) {
        return findMatchIn(tiles[row]);
    }

    private Match findMatchInCol(int col) {
        Tile[] match = new Tile[num_rows];
        for(int i = 0; i < num_rows; i++) match[i] = tiles[i][col];
        return findMatchIn(match);
    }

    private Tile[] shift(Tile[] shifts, boolean isCol, int amnt) {
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
        Tile[] shifts = new Tile[num_cols];

        for(int i = 0; i < num_rows; i++) shifts[i] = tiles[i][col];

        shifts = shift(shifts, true, amnt);

        for(int i = 0; i < num_rows; i++) tiles[i][col] = shifts[i];
    }

    private void initTiles() {
        for (int row = 0; row < num_rows; row++) {
            for (int col = 0; col < num_cols; col++) {
                placeTile(new Tile(new RowCol(col,row), TileType.randomType()));
            }
        }
    }

    private Tile tileAt(RowCol pos){
        return tiles[pos.getY()][pos.getX()];
    }

    private void placeTile(Tile tile){
        tiles[tile.getPosition().getY()][tile.getPosition().getX()] = tile;
    }

}
