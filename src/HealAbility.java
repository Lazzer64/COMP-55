
public class HealAbility extends Ability{
	private int healAmount;
	public HealAbility(String name, int energyCost, int healAmount) {
		super(name, energyCost);
		this.healAmount = healAmount;
	}

	@Override
	public String use(Player player, Enemy enemy) {
        if(canUse(player)) {
			player.decreaseEnergy(getEnergyCost());
			player.heal(healAmount);
			return "Healed for " + healAmount;
		} else return null;
	}
	
}
