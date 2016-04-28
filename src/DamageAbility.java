
public class DamageAbility extends Ability{
	private int damageAmount;
	public DamageAbility(String name, int energyCost, int damageAmount) {
		super(name,energyCost);
		this.damageAmount = damageAmount;
	}

	@Override
	public String use(Player player, Enemy enemy) {
        if(canUse(player)) {
            player.decreaseEnergy(getEnergyCost());
			player.attack(enemy, damageAmount);
			return "Dealt " + damageAmount + " damage";
		} else return null;
	}
}
