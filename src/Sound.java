import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sound {
	

	static URL url = MainMenu.class.getResource("Sounds/mainMenu.mid");
	static AudioClip menuMusic = Applet.newAudioClip(url);
	
	static URL credits = MainMenu.class.getResource("Sounds/credits.wav");
	static AudioClip creditMusic = Applet.newAudioClip(credits);
	
	static URL click = MainMenu.class.getResource("Sounds/click.wav");
	static AudioClip clicking = Applet.newAudioClip(click);
	
	static URL fight = MainMenu.class.getResource("Sounds/fighting.mid");
	static AudioClip fighting = Applet.newAudioClip(fight);
	
	static URL end = MainMenu.class.getResource("Sounds/ending.mid");
	static AudioClip ending = Applet.newAudioClip(end);
	
	static URL water = MainMenu.class.getResource("Sounds/Attacks/waterAttack.wav");
	static AudioClip waterAttack = Applet.newAudioClip(water);

	static URL heal = MainMenu.class.getResource("Sounds/Attacks/healing.wav");
	static AudioClip healing = Applet.newAudioClip(heal);
	
	static URL lightning = MainMenu.class.getResource("Sounds/Attacks/lightAttack.wav");
	static AudioClip lightAttack = Applet.newAudioClip(lightning);
	
	static URL fire = MainMenu.class.getResource("Sounds/Attacks/fireAttack.wav");
	static AudioClip fireAttack = Applet.newAudioClip(fire);
	
	static URL rock = MainMenu.class.getResource("Sounds/Attacks/rockAttack.wav");
	static AudioClip rockAttack = Applet.newAudioClip(rock);
	
	static URL powerUp = MainMenu.class.getResource("Sounds/Attacks/powerAbility.wav");
	static AudioClip poweringAbility = Applet.newAudioClip(powerUp);
	
	static URL nuke = MainMenu.class.getResource("Sounds/Attacks/nukeAbility.wav");
	static AudioClip nukeAbility = Applet.newAudioClip(nuke);
}
