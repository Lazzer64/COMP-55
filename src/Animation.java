import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import acm.graphics.*;
import acm.graphics.GRectangle;
import acm.program.*;
import acm.util.*;


public class Animation extends GObject{
    private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 (You will have to play around with this)
    private int currentFrame;               // animations current frame
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation

    private boolean stopped;                // has animations stopped

    
    private List<Frame> frames = new ArrayList<Frame>();    // Arraylist of frames 

    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        this.stopped = false;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();
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

}
