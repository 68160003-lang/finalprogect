import javax.swing.*;
import java.awt.*;

public class NameInputGUI extends JFrame {

    JTextField nameField;

    public NameInputGUI(){

        setTitle("Enter Name");

        // เต็มจอ
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        setLayout(new GridBagLayout());

        Color cream = new Color(255,248,220);
        getContentPane().setBackground(cream);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,15,15);

        // Title
        JLabel title = new JLabel("Enter Your Name");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        //  ช่องกรอก
        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 20));

        //  ปุ่มยืนยัน
        GameButton confirm = new GameButton("Confirm", new Color(170,240,180));

        // ปุ่มลบชื่อ
        GameButton reset = new GameButton("Reset", new Color(255,200,150));

        //ใส่เสียงถูกต้อง
        SoundPlayer.addClickSound(confirm);
        SoundPlayer.addClickSound(reset);

        // วางกลาง
        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy = 1;
        add(nameField, gbc);

        gbc.gridy = 2;
        add(confirm, gbc);

        gbc.gridy = 3;
        add(reset, gbc);

        // Action
        confirm.addActionListener(e -> {

            String name = nameField.getText();

            if(name.isEmpty()){
                name = "You";
            }

            Player[] players = new Player[4];
            players[0] = new Player(name, true); // 👤 เรา

            for(int i=1;i<4;i++){
                players[i] = new Player("AI-" + i, false); // 🤖 AI
            }

            new GameGUI(players);
            dispose();
        });

        reset.addActionListener(e -> nameField.setText(""));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}