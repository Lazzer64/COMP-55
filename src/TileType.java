import java.awt.Color;
import java.util.Random;
public enum TileType {
    RED, BLUE, GREEN, YELLOW, PINK;

    public static Color getColor(TileType t){
        switch(t){
            case RED:
                return Color.RED;
            case BLUE:
                return new Color(50,50,255);
            case GREEN:
                return Color.GREEN;
            case YELLOW:
                return Color.YELLOW;
            case PINK:
                return new Color(255,175,255);
            default:
                return Color.BLACK;
        }
    }
    
    public static String getToolTipText(TileType t) {
    	switch(t){
        case RED:
            return "Deals damage to enemy";
        case BLUE:
            return "Deals damage to enemy";
        case GREEN:
            return "Deals damage to enemy";
        case YELLOW:
            return "Deals damage to enemy";
        case PINK:
            return "Heals you";
        default:
            return "Unknown";
    }
    }

    public static TileType randomType() {

        Random r = new Random();
        int roll = r.nextInt(5);

        if(roll == 0) return TileType.RED;
        if(roll == 1) return TileType.BLUE;
        if(roll == 2) return TileType.YELLOW;
        if(roll == 4) return TileType.PINK;
        return TileType.GREEN;
    }


}

