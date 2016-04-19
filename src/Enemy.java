import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.Random;
//mark's territory

public class Enemy extends Unit{

    private Random rng;

    public Enemy(String name, int hp, int attack, int defense){
    	super(name, hp, attack, defense);
    }

    public BufferedImage[] getAnimation() {
        switch(state){
            case IDLE:
                return Animation.enemy1Idle;
            case ATTACK:
                return Animation.enemy1Attack;
            case DEATH:
                return Animation.enemy1Die;
            default:
                return Animation.enemy1Idle;
        }
    }

    public BufferedImage[] getAttackAnimation(Color color){
        if(color.equals(TileType.getColor(TileType.RED))) return Animation.playerRedAttack;
        else if(color.equals(TileType.getColor(TileType.BLUE))) return Animation.playerBlueAttack;
        else if(color.equals(TileType.getColor(TileType.GREEN))) return Animation.playerGreenAttack;
        return Animation.playerRedAttack;
    }

    public void generateAttack(int min, int max){
        setAttack(min + rng.nextInt(max-min+1));
    }
    
}
