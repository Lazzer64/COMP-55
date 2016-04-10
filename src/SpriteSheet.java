import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private BufferedImage spriteSheet;
    private int TILE_WIDTH;
    private int TILE_HEIGHT;
    
    public SpriteSheet(String fileName, int tileWidth, int tileHeight) {
    	spriteSheet = loadSpriteSheet(fileName);
    	TILE_WIDTH = tileWidth;
    	TILE_HEIGHT = tileHeight;
    }

    public  BufferedImage loadSpriteSheet(String file) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File("SpriteSheets/" + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) {
        return spriteSheet.getSubimage(xGrid * TILE_WIDTH, yGrid * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
    }
    
    public int getTileWidth() {
    	return TILE_WIDTH;
    }

    public int getTileHeight() {
    	return TILE_HEIGHT;
    }
    
    
}