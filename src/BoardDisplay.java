import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.*;


public class BoardDisplay extends Display{

    public static final int TILE_SIZE = Game.TILE_SIZE;
    public static final Color EMPTY_TILE_COLOR = Color.LIGHT_GRAY;
    public static final Color LINE_COLOR = Color.WHITE;
    
    public static final Color FIRST = Color.ORANGE;
    public static final Color SECOND = Color.BLACK;
    public static final Color THIRD = new Color(80,80,220);
    
    public static final Font MULTI_FONT = new Font("Times New Roman",Font.BOLD,30);

    public static final String ICON_DIR = "SpriteSheets/";
    public static final Image NO_ICON = new GImage("").getImage();
    public static final Image RED_ICON = new GImage(ICON_DIR+"RED_tile.png").getImage();
    public static final Image BLUE_ICON = new GImage(ICON_DIR+"BLUE_tile.png").getImage();
    public static final Image GREEN_ICON = new GImage(ICON_DIR+"GREEN_tile.png").getImage();
    public static final Image YELLOW_ICON = new GImage(ICON_DIR+"YELLOW_tile.png").getImage();
    public static final Image PINK_ICON = new GImage(ICON_DIR+"PINK_tile.png").getImage();

    ArrayList<GLabel> multiLabels = new ArrayList<GLabel>();
    GLabel currentToolTip = null;
    
    Board board;
    GImage[][] imgs;
    GRect[][] tiles;
    int rows, cols;
    int mouseX, mouseY, lastMouseX,lastMouseY;
    int updatesSinceLastMove = 0;

    BoardDisplay(Board board){
        super();
        this.board = board;
        rows = board.getTiles().length;
        cols = board.getTiles()[0].length;
        tiles = new GRect[rows][cols];
        imgs = new GImage[rows][cols];

        initBack();
        initTiles();
        initGrid();
    }

    public void update(){
        updateTiles();
        updateMultipliers();
        updateToolTips();
    }

    public void updateToolTips() {
    	Tile currTile = getCurrentTile(mouseX,mouseY);
    	
    	if (mouseX == lastMouseX && mouseY == lastMouseY && currTile != null) {
    		updatesSinceLastMove++;
    		if(updatesSinceLastMove > 50 && currentToolTip == null) {
	        	GLabel toolTip = new GLabel(TileType.getToolTipText(currTile.getType()));
	        	toolTip.setLocation(mouseX,mouseY);
		        currentToolTip = toolTip;
		        addObject(currentToolTip);
    		}
        } else {
        	if(currentToolTip != null) {
        		removeObject(currentToolTip);
        		currentToolTip = null;
        	}
        	updatesSinceLastMove = 0;
        }
    	
    	lastMouseX = mouseX;
    	lastMouseY = mouseY;
    	
    }
    
    public Tile getCurrentTile(int x, int y) {
    	if(outOfBounds(x,y)) return null;
    	return board.getTiles()[y/TILE_SIZE][x/TILE_SIZE];
    }
    
    public boolean outOfBounds(int x, int y) {
    	if(x/TILE_SIZE < 0 || x/TILE_SIZE >cols-1) return true;
    	if(y/TILE_SIZE < 0 || y/TILE_SIZE >rows-1) return true;
    	return false;
    }
    
    public GImage getCurrentImage(int x, int y) {
    	if(outOfBounds(x,y)) return null;
    	return imgs[y/TILE_SIZE][x/TILE_SIZE];
    }
    
    public void setCurrentMousePosition(int x, int y) {
    	setMouseX(x);
    	setMouseY(y);
    }
    
    public void setMouseX(int x) {
    	mouseX = x;
    }
    
    public void setMouseY(int y) {
    	mouseY = y;
    }
    
    public void updateMultipliers() {
    	for(GLabel l : multiLabels) {
    		if(l.getColor().equals(Color.BLACK) || l.getColor().equals(THIRD)) {
    			l.setColor(FIRST);
    		} else if(l.getColor().equals(FIRST)) {
    			l.setColor(SECOND);
    		} else if(l.getColor().equals(SECOND)) {
    			l.setColor(THIRD);
    		}
    		
    	}
    }
    
    public void removeMultipliers() {
    	for(GLabel l : multiLabels) {
    		removeObject(l);
    	}
    	multiLabels = new ArrayList<GLabel>();
    }
    
    public GLabel addMultiLabel(Match m, double currMultiplier) {
    	GLabel l = new GLabel("x" + currMultiplier);
    	RowCol[] loc = m.getPositions();
    	l.setLocation(loc[loc.length/2].getX()*TILE_SIZE,loc[loc.length/2].getY()*TILE_SIZE + TILE_SIZE/2);
    	l.setFont(MULTI_FONT);
    	addObject(l);
    	ArrayList<GLabel> mLabels = (ArrayList<GLabel>) multiLabels.clone();
        mLabels.add(l);
        multiLabels = mLabels;
    	return l;
    }
    
    public void updateTiles() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                Tile tile = board.getTiles()[y][x];
                GRect square = tiles[y][x];
                GImage image = imgs[y][x];

                Color color;
                Image icon;
                if(tile == null) {
                    color = EMPTY_TILE_COLOR;
                    icon = NO_ICON;
                } else {
                    color = TileType.getColor(tile.getType());
                    icon = getIcon(tile.getType());
                }

                square.setColor(color);
                image.setImage(icon);
                image.setSize(TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public void displayBoard(){
        int rows = board.getTiles().length;
        int cols = board.getTiles()[0].length;
        init(board, rows, cols);
    }

    private void init(Board board, int rows, int cols){
        initTiles();
    }

    private void initBack(){
        GRect background = new GRect(0, 0, TILE_SIZE*cols, TILE_SIZE*rows);
        background.setColor(EMPTY_TILE_COLOR);
        background.setFilled(true);
        addObject(background);
    }

    private void initGrid(){
        for (int y = 0; y < rows+1; y++) {
            int yPos =TILE_SIZE * y;
            int xStart = 0;
            int xEnd = TILE_SIZE*cols;
            GLine line = new GLine(xStart, yPos, xEnd, yPos);
            line.setColor(LINE_COLOR);
            addObject(line);
        }

        for (int x = 0; x < cols+1; x++) {
            int xPos = TILE_SIZE*x;
            int yEnd = TILE_SIZE * rows;
            GLine line = new GLine(xPos, 0, xPos, yEnd);
            line.setColor(LINE_COLOR);
            addObject(line);
        }
    }

    private void initTiles(){
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                GRect t = new GRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                t.setColor(EMPTY_TILE_COLOR);
                t.setFilled(true);
                tiles[y][x] = t;

                addObject(tiles[y][x]);

                GImage i = new GImage("");
                i.setSize(TILE_SIZE, TILE_SIZE);
                i.setLocation(x*TILE_SIZE, y*TILE_SIZE);
                imgs[y][x] = i;

                addObject(imgs[y][x]);
            }
        }
    }

    private Image getIcon(TileType t){
        if(t == null) return NO_ICON;
        switch(t){
            case RED:
                return RED_ICON;
            case BLUE:
                return BLUE_ICON;
            case GREEN:
                return GREEN_ICON;
            case YELLOW:
                return YELLOW_ICON;
            case PINK:
                return PINK_ICON;
            default:
                return NO_ICON;
        }
    }

}
