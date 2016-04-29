import java.awt.image.BufferedImage;
import java.awt.Color;
//mark's territory
public class Player extends Unit{

	private int energy;
	private int maxEnergy;
	private Ability[] abilities;
	
    public Player(int hp, int attack, int defense, int energy){
    	super("Player", hp, attack, defense);
    	this.energy = 1;
    	this.maxEnergy = energy;
    	setupAbilities();
    }

    public void setupAbilities() {
    	this.abilities = new Ability[3];
    	this.abilities[0] = new DamageAbility("Nuke", 25, 75);
    	this.abilities[1] = new HealAbility("Full Heal", 50);
    	this.abilities[2] = new RaiseStatsAbility("Level Up", 75, 20, 3);
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

    public Ability getAbility(String name) {
    	for(Ability a:abilities) {
    		if(a.getName().equals(name)) {
    			return a;
    		}
    	}
    	return null;
    }
    
    public Ability[] getAbilities() {
    	return abilities;
    }
    
    public int getNumAbilities() {
    	return abilities.length;
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
