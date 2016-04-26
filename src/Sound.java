import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sound {
	static URL url = MainMenu.class.getResource("Sounds/mainMenu.mid");
	static AudioClip menuMusic = Applet.newAudioClip(url);
	
	static URL click = MainMenu.class.getResource("Sounds/click.wav");
	static AudioClip clicking = Applet.newAudioClip(click);
	
	static URL fight = MainMenu.class.getResource("Sounds/fighting.mid");
	static AudioClip fighting = Applet.newAudioClip(fight);
	
	static URL water = MainMenu.class.getResource("Sounds/Attacks/waterAttack.ogg.mid");
	static AudioClip waterAttack = Applet.newAudioClip(water);

	static URL heal = MainMenu.class.getResource("Sounds/Attacks/healing.ogg.mid");
	static AudioClip healing = Applet.newAudioClip(heal);
	
	static URL lightning = MainMenu.class.getResource("Sounds/Attacks/lightAttack.ogg.mid");
	static AudioClip lightAttack = Applet.newAudioClip(lightning);
	
	static URL fire = MainMenu.class.getResource("Sounds/Attacks/fireAttack.ogg.mid");
	static AudioClip fireAttack = Applet.newAudioClip(fire);
}
