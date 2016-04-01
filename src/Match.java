public class Match {

    TileType type;
    Tile[] tiles;
    RowCol[] positions;

    public Match(Tile[] tiles){
        this.type = tiles[0].getType();
        this.tiles = tiles;
        populatePositions();
    }

    private void populatePositions(){
        if(tiles.length > 0);
        this.positions = new RowCol[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            positions[i] = tiles[i].getPosition();
        }
    }

    public int size(){
        return tiles.length;
    }

    public TileType getType(){
        return type;
    }

    public Tile[] getTiles(){
        return tiles;
    }

    public RowCol[] getPositions() {
        return positions;
    }
}
