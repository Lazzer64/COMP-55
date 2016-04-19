import java.awt.image.BufferedImage;

enum SkillType {
	HEAL, ATTACK;
}

public class Skill {
	private String name;
	private int baseAmount;
	private BufferedImage[] animation;
	
	protected SkillType type;
	
	public Skill(String name, int baseAmount, BufferedImage[] animation, SkillType type) {
		this.name = name;
		this.baseAmount = baseAmount;
		this.animation = animation;
		this.type = type;
	}
	
	public void useOn(Unit target) {
		switch(type) {
			case HEAL:
				int tempHp = target.getHp();
				tempHp += baseAmount;
				if(tempHp > target.getMaxHp()) tempHp = target.getMaxHp();
				target.setHp(tempHp);
			case ATTACK:
				int totalDamage = baseAmount - target.getDefense();
				if(totalDamage > 0) {
					target.setHp(target.getHp() - totalDamage);
				}
		}
	}
	
	public BufferedImage[] getAnimation() {
		return this.animation;
	}
	
	public 
}
