import java.util.EnumMap;
public class Board {

    int num_rows;
    int num_cols;

    Tile[][] tiles;

    public static void main(String[] args) {
        Board board = new Board(5, 5);

        System.out.println("Initial Board...");
        System.out.println("----------");
        System.out.println(board);

        board.moveTile(new RowCol(0,0), new RowCol(1,0));
        System.out.println("Move 0,0 to 1,0");
        System.out.println("----------");
        System.out.println(board);

        EnumMap<TileType,Integer> matches = board.getMatches();

        while(!matches.isEmpty()) {

            System.out.println("Get Matches - R:"+matches.get(TileType.RED)+" G:"+matches.get(TileType.GREEN)+" B:"+matches.get(TileType.BLUE));
            System.out.println("----------");
            System.out.println(board);

            board.dropTiles();
            System.out.println("Drop TIles");
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

    public EnumMap<TileType,Integer> getMatches() {

        EnumMap<TileType,Integer> numMatches = new EnumMap<TileType,Integer>(TileType.class);
        RowCol[][] matches = findAllMatches();
        
        for(int i = 0; i < matches.length; i++){
            if(matches[i] != null){
                TileType type = tileAt(matches[i][0]).getType();
                if(numMatches.containsKey(type)) numMatches.put(type,numMatches.get(type)+1);
                else numMatches.put(type,1);
            }
        }

        removeMatches();
        return numMatches;
    }

    public void dropTiles() {
        for (int i = 0; i < num_rows; i++) {
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
        for(RowCol[] r: findAllMatches())
            if(r!=null) for(RowCol t: r) 
                if(t!=null) tiles[t.getY()][t.getX()] = null;
    }

    private RowCol[][] findAllMatches() {
        RowCol[][] matches = new RowCol[num_cols+num_cols][];

        for (int row = 0; row < num_rows; row++) matches[row] = findMatchInRow(row);
        for (int col = 0; col < num_cols; col++) matches[num_rows+col] = findMatchInCol(col); 
        return matches;
    }


    private RowCol[] findMatchIn(Tile[] tiles) {
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
        return findMatchIn(tiles[row]);
    }

    private RowCol[] findMatchInCol(int col) {
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
