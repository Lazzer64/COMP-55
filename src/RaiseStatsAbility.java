
public class RaiseStatsAbility extends Ability{
	private int hpIncrease;
	private int statIncrease;
	RaiseStatsAbility(String name, int energyCost, int hpIncrease, int statIncrease) {
		super(name, energyCost);
		this.hpIncrease = hpIncrease;
		this.statIncrease = statIncrease;
	}
	@Override
	public String use(Player player, Enemy enemy) {
        if(canUse(player)) {
        	Sound.poweringAbility.play();
            player.decreaseEnergy(getEnergyCost());
            String result = "";
			increaseHp(player);
			result += "New Hp: " + player.getHp() + "/" + player.getMaxHp() + ";";
			increaseDefense(player);
			result += "New Defense: " + player.getDefense();
			increaseAttack(player);
			result += "New Attack: " + player.getAttack();
			return result;
		} else return null;
	}
	
	public void increaseHp(Player player) {
		player.setMaxHp(player.getMaxHp() + hpIncrease);
		player.heal(hpIncrease);
	}
	
	public void increaseDefense(Player player) {
		player.setDefense(player.getDefense()+statIncrease);
	}
	
	public void increaseAttack(Player player) {
		player.setAttack(player.getAttack()+(int)(statIncrease/4));
	}
}
