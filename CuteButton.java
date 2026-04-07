import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CuteButton extends JButton {

    Color base = new Color(255,182,193);
    Color hover = new Color(255,105,180);

    public CuteButton(String text){
        super(text);

        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);

        setPreferredSize(new Dimension(160,50));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                setBackground(hover);
                setSize(getWidth()+5,getHeight()+5);
            }
            public void mouseExited(MouseEvent e){
                setBackground(base);
                setSize(getWidth()-5,getHeight()-5);
            }
        });
    }

    protected void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D)g;

        g2.setColor(getModel().isRollover()?hover:base);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),30,30);

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g){}
}