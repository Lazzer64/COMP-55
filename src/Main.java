public class Main extends GraphicsApplication{

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 600;
    public static final int REFRESH_RATE = 20; // In milliseconds

    public void run(){
    	
	    
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsPane p = new Game(this);
        switchToScreen(p);
		addKeyListeners();
		addMouseListeners();
        
   	 	//TEST METHOD: the add score function in CONSOLE
        /*HighscoreList hm = new HighscoreList();
	    hm.addScore("Tom", 55);
	    hm.addScore("Alex", 128);
	    hm.addScore("Mark", 99);
	    hm.addScore("Michael",45);
	    hm.addScore("Osvaldo",6);
	    hm.addScore("CPU1", 67);
	    hm.addScore("CPU2", 89);
	    hm.addScore("CPU3", 59);
	    hm.addScore("CPU4",29);
	    hm.addScore("CPU5",44);
	    
	    //OUTSIDE the top 10 list
	    hm.addScore("Test 11th",0);
	    System.out.print(hm.printScores());*/
      

    }
}
	

//Hello
