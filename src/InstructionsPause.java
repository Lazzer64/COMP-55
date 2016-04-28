import java.awt.event.MouseEvent; 
public class InstructionsPause extends Instructions {

    private Main program; 
    private Pause pause;
    
	public InstructionsPause(Main app, Pause pause) {
        super(app);
        this.program = app;
        this.pause = pause;
	}

    public void mouseClicked(MouseEvent e) {
        if(program.getElementAt(e.getX(), e.getY()) == returnpic){
            program.switchToScreen(pause);
            Sound.clicking.play();
        }
    }
}