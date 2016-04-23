import java.awt.image.BufferedImage;
import java.awt.Color;
//mark's territory
public class Player extends Unit{

	private int energy;
	private int maxEnergy;
	
    public Player(int hp, int attack, int defense, int energy){
    	super("Player", hp, attack, defense);
    	this.energy = 1;
    	maxEnergy = energy;
    }

    public BufferedImage[] getAnimation() {
        switch(state){
            case IDLE:
                return Animation.playerIdle;
            case ATTACK:
                return Animation.playerAttack;
            case DEATH:
                return Animation.playerDie;
            default:
                return Animation.playerIdle;
        }
    }

    public BufferedImage[] getAttackAnimation(Color color){
        if(color.equals(TileType.getColor(TileType.RED))) return Animation.playerRedAttack;
        else if(color.equals(TileType.getColor(TileType.BLUE))) return Animation.playerBlueAttack;
        else if(color.equals(TileType.getColor(TileType.GREEN))) return Animation.playerGreenAttack;
        return Animation.playerRedAttack;
    }

    public BufferedImage[] getAttackExplosionAnimation(Color color) {
        if(color.equals(TileType.getColor(TileType.RED))) return Animation.playerRedExplosion;
        else if(color.equals(TileType.getColor(TileType.BLUE))) return Animation.playerBlueExplosion;
        else if(color.equals(TileType.getColor(TileType.GREEN))) return Animation.playerGreenExplosion;
        else if(color.equals(TileType.getColor(TileType.PINK))) return Animation.playerHealingEffect;
        return Animation.playerRedExplosion;
    }
    
    public void increaseEnergy(int addedEnergy) {
    	energy += addedEnergy;
    	if(energy > maxEnergy) energy = maxEnergy;
    }
    
    public void decreaseEnergy(int subtractedEnergy) {
    	energy -= subtractedEnergy;
    	if(energy < 0) energy = 0;
    }
    
    public int getEnergy() {
    	return this.energy;
    }
    
    public int getMaxEnergy() { 
    	return this.maxEnergy;
    }
}
