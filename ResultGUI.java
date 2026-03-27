import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ResultGUI extends JFrame {

    public ResultGUI(Player[] players){

        setTitle("Result");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        setLayout(new BorderLayout());

        Color cream = new Color(255,248,220);
        getContentPane().setBackground(cream);

        // จำผู้เล่นเรา
        Player you = players[0];

        // เรียงอันดับ
        Arrays.sort(players, (a,b)-> b.score - a.score);

        // เสียงถูกต้อง
        if(players[0] == you){
            SoundPlayer.play("win.wav");
        }else{
            SoundPlayer.play("lose.wav");
        }

        // ===== TITLE =====
        JLabel title = new JLabel("🏆 RESULT 🏆", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        add(title, BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel center = new JPanel(new GridLayout(4,1,15,15));
        center.setBackground(cream);

        Color[] borders = {
                new Color(255,215,0),
                new Color(192,192,192),
                new Color(205,127,50),
                Color.BLACK
        };

        for(int i=0;i<players.length;i++){

            JPanel box = new JPanel(new BorderLayout());
            box.setBackground(Color.WHITE);

            box.setBorder(BorderFactory.createLineBorder(borders[i], 6, true));

            JLabel label = new JLabel(
                    (i+1) + " : " + players[i].name +
                            "  Score: " + players[i].score +
                            "  🔥x" + players[i].winStreak,
                    JLabel.CENTER
            );

            label.setFont(new Font("Arial", Font.BOLD, i==0 ? 36 : 28));

            box.add(label);
            center.add(box);
        }

        add(center, BorderLayout.CENTER);

        // ===== BUTTON =====
        JPanel bottom = new JPanel();
        bottom.setBackground(cream);

        GameButton again = new GameButton("Play Again", new Color(173,216,230));
        GameButton exit = new GameButton("Exit", new Color(255,182,193));

        SoundPlayer.addClickSound(again);
        SoundPlayer.addClickSound(exit);

        bottom.add(again);
        bottom.add(exit);

        add(bottom, BorderLayout.SOUTH);

        again.addActionListener(e -> {

            // reset ค่า
            for(Player p : players){
                p.score = 0;
                p.winStreak = 0;
                p.choice = "";
            }

            // หา player จริง
            Player user = null;
            for(Player p : players){
                if(p.isUser){
                    user = p;
                    break;
                }
            }

            // ย้าย user ไป index 0
            Player[] newPlayers = new Player[4];
            newPlayers[0] = user;

            int index = 1;
            for(Player p : players){
                if(!p.isUser){
                    newPlayers[index++] = p;
                }
            }

            new GameGUI(newPlayers);
            dispose();
        });
        exit.addActionListener(e -> System.exit(0));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}