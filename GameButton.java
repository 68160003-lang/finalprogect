import javax.swing.*;
import java.awt.*;

public class GameButton extends JButton {

    Color color;

    public GameButton(String text, Color c){
        super(text);
        color=c;
        setFocusPainted(false);
        setContentAreaFilled(false);
        setFont(new Font("Arial",Font.BOLD,16));
    }

    protected void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D)g;
        g2.setColor(color);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),25,25);
        super.paintComponent(g);
    }
}