import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
public class Board {

    int num_rows;
    int num_cols;

    Tile[][] tiles;
    Queue<Match> matches = new LinkedList<Match>();

    static private Tile R(int x, int y) {return new Tile(new RowCol(x,y),TileType.RED);}
    static private Tile G(int x, int y) {return new Tile(new RowCol(x,y),TileType.GREEN);}
    static private Tile B(int x, int y) {return new Tile(new RowCol(x,y),TileType.BLUE);}

    public static void main(String[] args) {
        Board board = new Board(5, 6);

        System.out.println("Initial Board...");
        System.out.println("----------");
        System.out.println(board);

        while(board.step()){
            System.out.println(board);
        }
        System.out.println(board);

    }

    public Board(int num_rows, int num_cols){
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        tiles = new Tile[num_rows][num_cols];
        initTiles();
    }

    // Return true if a change to the board was made
    public boolean step(){
        if(!matches.isEmpty()){
            removeMatch(matches.poll());
            return true;
        }
        boolean d = false;
        for(int i = num_rows-2; i >= 0; i--){
            if(dropRow(i)) d = true;
        }
        if(topRowEmpty()){
            refillRow();
            return true;
        }
        if(d) return true;
        return !makeMatches().isEmpty(); 
    }

    public Queue<Match> getMatches(){
        return matches;
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

    public Queue<Match> makeMatches() {

        Queue<Match> matchQ = new LinkedList<Match>();
        for (Match m: findAllMatches()){
            for(Match x: matchQ){
                if(m.overlaps(x)){
                   m = m.merge(x);
                   matchQ.remove(x);
                }
            }
            matchQ.add(m);
        }

        matches = matchQ;
        return matchQ;
    }

    public void dropAllTiles() {
        for (int i = 0; i < num_cols; i++) {
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

    private boolean isFilled() {
        for (int row = 0; row < num_rows; row++) {
            for (int col = 0; col < num_cols; col++) {
                if(tiles[row][col] == null) return false;
            }
        }
        return true;
    }

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

    private void removeMatch(Match match){
        for(RowCol o: match.getPositions()){
            tiles[o.getY()][o.getX()] = null;
        } 
    }

    private boolean refillRow() {
        boolean filled = false;
        for (int i = 0; i < num_cols; i++) {
            if(tiles[0][i] == null){
                placeTile(new Tile(new RowCol(i,0), TileType.randomType()));
                filled = true;
            }
        }
        return filled;
    }

    // Returns true if a tile was dropped
    private boolean dropRow(int row){
        boolean changed = false;
        for (int i = 0; i < num_cols; i++) {

            if(tiles[row][i] != null && tiles[row+1][i] == null) {

                tiles[row][i].setPosition(new RowCol(i,row+1));

                placeTile(tiles[row][i]);
                tiles[row][i] = null;

                changed = true;
            }
        }
        return changed;
    }

    private boolean topRowEmpty(){
        for(int i = 0; i < num_cols; i++){
            if(tiles[0][i] == null) return true;
        }
        return false;
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
            if(!findMatchesInRow(row).isEmpty()) for(Match m: findMatchesInRow(row)) matches.add(m);
        for (int col = 0; col < num_cols; col++) 
            if(!findMatchesInCol(col).isEmpty()) for(Match m: findMatchesInCol(col)) matches.add(m);
        return matches;
    }


    private List<Match> findMatchesIn(Tile[] tiles) {
        int size = tiles.length;
        List<Match> matches = new LinkedList<Match>();

        for(int i = 0; i < size; i++){
            List<Tile> match = new LinkedList<Tile>();
            if(tiles[i] != null){
                TileType type = tiles[i].getType();

                for(int e = i; e < size; e++){
                    if(tiles[e] != null && tiles[e].getType() == type) match.add(tiles[e]);
                    else e = size;
                }

                if(match.size() >= 3){
                    Tile[] m = new Tile[match.size()]; match.toArray(m);
                    matches.add(new Match(m));
                    i+=match.size()-1;
                } 
            }

        }
        return matches;
    }

    private List<Match> findMatchesInRow(int row) {
        return findMatchesIn(tiles[row]);
    }

    private List<Match> findMatchesInCol(int col) {
        Tile[] match = new Tile[num_rows];
        for(int i = 0; i < num_rows; i++) match[i] = tiles[i][col];
        return findMatchesIn(match);
    }

    private Tile[] shift(Tile[] shifts, boolean isCol, int amnt) {
        int size = num_cols;
        if(isCol) size = num_rows;
        
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
