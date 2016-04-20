import java.awt.image.BufferedImage;
import java.awt.Color;
//mark's territory
public class Player extends Unit{

    public Player(int hp, int attack, int defense){
    	super("Player", hp, attack, defense);
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
        return Animation.playerRedExplosion;
    }
}
