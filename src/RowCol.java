public class RowCol {

	private int x;
	private int y;

    public RowCol(int x, int y) { 
		this.x = x;
		this.y = y;
    }

    public boolean equals(RowCol x){
        return getX() == x.getX() && getY() == x.getY();
    }

	public void setX(int x) {
    	this.x = x;
	}

	public int getX() {    
		return x;
	}

	public void setY(int y) {
    	this.y = y;
	}

	public int getY() {    
		return y;
	}

}
