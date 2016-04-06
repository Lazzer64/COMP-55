import java.util.HashSet;

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

    public boolean overlaps(Match x){
        for(Tile t: tiles){
            for(Tile u: x.tiles){
                if(t == u) return true;
            }
        }
        return false;
    }
    public Match merge(Match x){

        HashSet<Tile> merger = new HashSet<Tile>();
        for(Tile t: tiles) merger.add(t);
        for(Tile t: x.tiles) merger.add(t);

        Tile[] merged = new Tile[merger.size()];
        merger.toArray(merged);
        return new Match(merged);
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
