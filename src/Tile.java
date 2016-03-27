public class Tile {

	private RowCol position;
	private TileType type;

    public Tile(RowCol position, TileType type) { 
		this.position = position;
		this.type = type;
    }

	public void setPosition(RowCol position) {
    	this.position = position;
	}

	public RowCol getPosition() {    
		return position;
	}

	public void setType(TileType type) {
    	this.type = type;
	}

	public TileType getType() {    
		return type;
	}

}

enum TileType {
    RED, BLUE, GREEN;
}
