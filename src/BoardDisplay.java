import java.awt.Color;
import java.awt.Image;
import acm.graphics.*;

public class BoardDisplay extends Display{

    public static final int TILE_SIZE = Game.TILE_SIZE;
    public static final Color EMPTY_TILE_COLOR = Color.LIGHT_GRAY;
    public static final Color LINE_COLOR = Color.WHITE;

    public static final String ICON_DIR = "SpriteSheets/";
    public static final Image NO_ICON = new GImage("").getImage();
    public static final Image RED_ICON = new GImage(ICON_DIR+"RED_tile.png").getImage();
    public static final Image BLUE_ICON = new GImage(ICON_DIR+"BLUE_tile.png").getImage();
    public static final Image GREEN_ICON = new GImage(ICON_DIR+"GREEN_tile.png").getImage();
    public static final Image YELLOW_ICON = new GImage(ICON_DIR+"YELLOW_tile.png").getImage();
    public static final Image PINK_ICON = new GImage(ICON_DIR+"PINK_tile.png").getImage();

    Board board;
    GImage[][] imgs;
    GRect[][] tiles;
    int rows, cols;

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
