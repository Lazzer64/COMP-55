import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sound {
	static URL url = MainMenu.class.getResource("Sounds/mainMenu.mid");
	static AudioClip menuMusic = Applet.newAudioClip(url);
	
	static URL click = MainMenu.class.getResource("Sounds/click.wav");
	static AudioClip clicking = Applet.newAudioClip(click);
	
}
