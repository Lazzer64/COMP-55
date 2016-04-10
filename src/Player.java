//mark's territory
public class Player extends Unit{

    private static final int HP = 200;
    private static final int DEFENSE = 3;

    public Player() { 
        super(HP, HP, 0, DEFENSE, UnitType.PLAYER);
    }

}
