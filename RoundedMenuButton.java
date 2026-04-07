import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedMenuButton extends JButton {

    private Color baseColor = new Color(255,105,180);   // ชมพู
    private Color hoverColor = new Color(255,130,200);  // ชมพูอ่อน
    private Color pressColor = new Color(240,90,160);   // ชมพูเข้ม

    private boolean hovered = false;
    private boolean pressed = false;

    public RoundedMenuButton(String text){
        super(text);

        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);

        setFont(new Font("Arial", Font.BOLD, 18));
        setPreferredSize(new Dimension(220,60));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        //hover + click animation
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                hovered = true;
                repaint();
            }
            public void mouseExited(MouseEvent e){
                hovered = false;
                pressed = false;
                repaint();
            }
            public void mousePressed(MouseEvent e){
                pressed = true;
                repaint();
            }
            public void mouseReleased(MouseEvent e){
                pressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // เงา
        g2.setColor(new Color(0,0,0,50));
        g2.fillRoundRect(5,5,width-5,height-5,40,40);

        // เลือกสีตามสถานะ
        if(pressed){
            g2.setColor(pressColor);
        }else if(hovered){
            g2.setColor(hoverColor);
        }else{
            g2.setColor(baseColor);
        }

        // ปุ่ม
        int offset = pressed ? 3 : 0;
        g2.fillRoundRect(0, offset, width-5, height-5, 40, 40);

        //  ข้อความ
        FontMetrics fm = g2.getFontMetrics();
        int x = (width - fm.stringWidth(getText())) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();

        g2.setColor(Color.WHITE);
        g2.drawString(getText(), x, y + offset);
    }
}
