import javax.sound.sampled.*;



public class Sound {
	private Clip soundClip;
	
	public static Sound music1 = new Sound("sound/music1.mp3");
	
	
	/** Default constructor to load the specific music/sound file
	 * @param file
	 */
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
	
	/**Play the sound files found in Sounds folder
	 * 
	 */
	public void start()
	{
		
		try 
		{
			if (soundClip != null){
				new Thread()
				{
					public void run()
					{
						synchronized(soundClip)
						{
							soundClip.stop();
							soundClip.setFramePosition(0);
							soundClip.start();
						}
					}
					}
					.start();
				}
			}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		
	}
	
	
	
}
