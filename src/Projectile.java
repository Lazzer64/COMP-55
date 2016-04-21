import acm.graphics.*;

public class Projectile implements Updatable {

	private Animation display;
	private int speed;
	private int distance;
    private int travelled;

    public Projectile(Animation display, int speed, int distance) { 
		this.display = display;
		this.speed = speed;
		this.distance = distance;
        this.travelled = 0;
    }

    public void update(){
        if(travelled >= distance) display.setVisible(false); // TODO change to a real solution
        display.update();
        display.move(speed,0);
        travelled += Math.abs(speed);
    }

	public void setDisplay(Animation display) {
    	this.display = display;
	}

	public Animation getDisplay() {    
		return display;
	}

	public void setSpeed(int speed) {
    	this.speed = speed;
	}

	public int getSpeed() {    
		return speed;
	}

	public void setDistance(int distance) {
    	this.distance = distance;
	}

	public int getDistance() {    
		return distance;
	}

}
