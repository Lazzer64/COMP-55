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
        
   	 //Testing the add score function in CONSOLE
        //Sorts Scores in order!
        HighscoreList hm = new HighscoreList();
	    hm.addScore("Tom", 55);
	    hm.addScore("Alex", 128);
	    hm.addScore("Mark", 99);
	    hm.addScore("Michael",45);
	    hm.addScore("Osvaldo",0);
	    System.out.print(hm.printScores());
      

    }
}
	

//Hello
