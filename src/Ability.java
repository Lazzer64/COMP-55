
public abstract class Ability {
	private String name;
	private int energyCost;
	
	public Ability(String name, int energyCost) {
		this.energyCost = energyCost;
		this.name = name;
	}

	public abstract String use(Player player,Enemy enemy);
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnergyCost() {
		return energyCost;
	}

	public void setEnergyCost(int energyCost) {
		this.energyCost = energyCost;
	}
	
	
}
