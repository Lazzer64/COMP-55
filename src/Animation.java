import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;
import acm.graphics.*;
import acm.graphics.GRectangle;
import acm.program.*;
import acm.util.*;


public class Animation extends GObject{
	public static BufferedImage[] playerIdle = getFrames("AnimationSpritesheet" ,0,0,3, 32,32);
	public static BufferedImage[] playerAttack = getFrames("AnimationSpritesheet" ,0,2,3, 32,32);
	public static BufferedImage[] playerRangedAttack = getFrames("playerAttack", 0,0,4, 20,20);
	public static BufferedImage[] enemy1Idle = getFrames("AnimationSpritesheet" ,0,0,3, 32,32);
	public static BufferedImage[] enemy1Attack = getFrames("AnimationSpritesheet" ,0,1,3, 32,32);
	public static BufferedImage[] enemy1Die = getFrames("AnimationSpritesheet" ,0,3,3, 32,32);
	
    private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 (You will have to play around with this)
    private int currentFrame;               // animations current frame
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation
    
    // FIXME maybe we should be passing/storing a list of Frames instead of an array of BufferedImages
    private BufferedImage[] rawFrames;

    private boolean stopped;                // has animations stopped

    
    private List<Frame> frames;    // Arraylist of frames 


    public Animation(BufferedImage[] frames, int frameDelay) {
        init(frames, frameDelay);
    }

    public boolean equals(BufferedImage[] frames){
        return rawFrames == frames;
    }

    public void playAnimation(BufferedImage[] frames, int frameDelay){
        init(frames, frameDelay);
    }

    public void init(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        this.stopped = false;
        this.rawFrames = frames;

        this.frames = new ArrayList<Frame>();

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();
    }

    public static BufferedImage[] getFrames(String fileName, int startX, int startY, int numOfFrames, int tileWidth, int tileHeight) {
    	SpriteSheet image = new SpriteSheet(fileName, tileWidth, tileHeight);
    	BufferedImage[] result = new BufferedImage[numOfFrames];
    	int cols = image.getNumOfCols();
    	int j = 0;
    	for(int i = 0; i < numOfFrames; i++) {
    		if(startX + i < cols) {
    			result[i] = image.getSprite(startX + i, startY);
    		} else {
    			if(startX + i % cols == 0) j++;
    			result[i] = image.getSprite(startX + i - cols*j, startY + j);
    		}
    	}
    	return result;
    }
    
    public void start() {
        if (frames.size() == 0) {
            return;
        }
        stopped = false;
    }

    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }

    private void addFrame(BufferedImage frame, int duration) {
        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }
    
    public void update() {
            frameCount++;

            if (frameCount > frameDelay) {
                frameCount = 0;

                if (currentFrame + animationDirection > totalFrames - 1) {
                    currentFrame = 0;
                }
                else {
                	currentFrame += animationDirection;
                }
            }
    }

    /*@Override
    public void setColor(Color color) {
    	
    }*/
    
	@Override
	public GRectangle getBounds() {
		// TODO Auto-generated method stub
		BufferedImage temp = frames.get(currentFrame).getFrame();
		GRectangle result = new GRectangle(temp.getWidth(), temp.getHeight());
		return result;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(getSprite(),(int)getX(),(int)getY(),null);
	}

	private BufferedImage colorImage(BufferedImage image, Color color) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                if(color.equals(Color.RED)) {
                    pixels[0] = 255;
                    pixels[1] = 0;
                    pixels[2] = 0;
                } else if(color.equals(Color.GREEN)) {
                    pixels[0] = 0;
                    pixels[1] = 255;
                    pixels[2] = 0;
                } else if(color.equals(Color.BLUE)) {
                    pixels[0] = 0;
                    pixels[1] = 0;
                    pixels[2] = 255;
                } else {
                    pixels[0] = 255;
                    pixels[1] = 255;
                    pixels[2] = 255;
                }
                raster.setPixel(x, y, pixels);
            }
        }
        return image;
    }
}
