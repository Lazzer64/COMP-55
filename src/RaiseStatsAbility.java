
public class RaiseStatsAbility extends Ability{
	private int hpIncrease;
	private int defenseIncrease;
	RaiseStatsAbility(String name, int energyCost, int hpIncrease, int defenseIncrease) {
		super(name, energyCost);
		this.hpIncrease = hpIncrease;
		this.defenseIncrease = defenseIncrease;
	}
	@Override
	public String use(Player player, Enemy enemy) {
        if(canUse(player)) {
            player.decreaseEnergy(getEnergyCost());
            String result = "";
			increaseHp(player);
			result += "New Hp: " + player.getHp() + "/" + player.getMaxHp() + ";";
			increaseDefense(player);
			result += "New Defense: " + player.getDefense();
			return result;
		} else return null;
	}
	
	public void increaseHp(Player player) {
		player.setMaxHp(player.getMaxHp() + hpIncrease);
		player.heal(hpIncrease);
	}
	
	public void increaseDefense(Player player) {
		player.setDefense(player.getDefense()+defenseIncrease);
	}
}
