import java.util.Random;
//mark's territory

public class Enemy extends Unit{
    public Enemy(int hp, int defense, String name){
    	super(hp, hp, 0, defense, name);
    }
    public Enemy(String name) { 
        super(HP, HP, 0, DEFENSE, name);
        rng = new Random(System.currentTimeMillis());
        generateAttack(MINATTACK, MAXATTACK);    }
    private Random rng;
    private static final int HP = 100;
    private static final int DEFENSE = 2;
    private static final int MINATTACK=1;
    private static final int MAXATTACK=5;
    public void generateAttack(int min, int max){
    	
    setAttack(min + rng.nextInt(max-min+1));
    }
    
}
