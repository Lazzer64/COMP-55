import javax.sound.sampled.*;


public class Sound {
	private Clip soundClip;
	
	public static Sound music1 = new Sound("sound/music1.mp3");
	
	public Sound (String file){
		try{
			   AudioInputStream input = AudioSystem.getAudioInputStream(Sound.class.getResource(file));
			   soundClip = AudioSystem.getClip();
			   soundClip.open(input);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
}
