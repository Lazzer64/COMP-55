import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent; 

interface GraphicsPane {

    public void showContents();
    public void hideContents();
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseClicked();
    public void mouseMoved();
    public void mouseDragged();
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
    public void keyTyped(KeyEvent e);

}
