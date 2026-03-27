import javax.swing.*;
import java.awt.*;

public class MenuGUI extends JFrame {

    public MenuGUI(){

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // 🎨 พื้นหลังรูป (เฉพาะหน้าเมนู)
      JLabel bg = new JLabel(new ImageIcon(
              new ImageIcon("src/menu_bg.png")
                      .getImage()
                      .getScaledInstance(1920,1080,Image.SCALE_SMOOTH)
      ));

        bg.setLayout(new GridBagLayout());
        setContentPane(bg);

        // 🎵 เพลง
        SoundPlayer.stopBGM();
        SoundPlayer.playBGM("bgm.wav");

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(20,20,20,20);

        JLabel title = new JLabel("Rock Paper Scissors");
        title.setFont(new Font("Arial",Font.BOLD,60));
        title.setForeground(Color.WHITE); // 🔥 ให้เห็นชัดบนภาพ

        RoundedMenuButton start = new RoundedMenuButton("Start");
        RoundedMenuButton exit = new RoundedMenuButton("Exit");

        // 🔊 click
        SoundPlayer.addClickSound(start);
        SoundPlayer.addClickSound(exit);

        // 🔊 slider เสียง
        JSlider slider = new JSlider(0,100,(int)(Settings.volume*100));
        slider.setPreferredSize(new Dimension(300,50));

        slider.addChangeListener(e -> {
            Settings.volume = slider.getValue()/100f;

            if(Settings.volume <= 0){
                Settings.soundOn = false;
                SoundPlayer.stopBGM();
            }else{
                Settings.soundOn = true;
                SoundPlayer.updateBGMVolume();
            }
        });

        // 📌 layout
        g.gridy=0; bg.add(title,g);
        g.gridy=1; bg.add(start,g);
        g.gridy=2; bg.add(exit,g);
        g.gridy=3; bg.add(slider,g);

        // 🎮 ปุ่ม
        start.addActionListener(e -> {
            new NameInputGUI();
            dispose();
        });

        exit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}