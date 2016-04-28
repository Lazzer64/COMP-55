
public class HealAbility extends Ability{
	public HealAbility(String name, int energyCost) {
		super(name, energyCost);
	}

	@Override
    public String use(Player player, Enemy enemy) {
        if(canUse(player)) {
            player.decreaseEnergy(getEnergyCost());
            player.heal(player.getMaxHp());
			return "Healed to full";
		} else return null;
	}
	
}