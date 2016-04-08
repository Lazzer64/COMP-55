//mark's territory
public class Player extends Unit{

    public Player(String name) { 
        super(HP, HP, 0, DEFENSE, name);
    }
    private static final int HP = 200;
    private static final int DEFENSE = 3;
}
