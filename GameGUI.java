import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameGUI extends JFrame {

    Player[] players;
    GameLogic logic = new GameLogic();
    Random rand = new Random();

    JLabel[] images = new JLabel[4];
    JLabel[] nameLabels = new JLabel[4];

    JButton r, p, s;
    JPanel selectPanel;

    String playerChoice = "";

    ImageIcon rock,paper,scissors;

    // constructor แก้ error
    public GameGUI(Player[] players){

        this.players = players;

        setTitle("Game");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        setLayout(new BorderLayout());

        Color pink = new Color(255,220,230);
        JPanel bottom = new JPanel();
        bottom.setBackground(pink);


        rock = loadIcon("rock.png");
        paper = loadIcon("paper.png");
        scissors = loadIcon("scissors.png");

        // ===== LEFT (รายชื่อ) =====
        JPanel leftPanel = new JPanel(new GridLayout(4,1,10,10));
        leftPanel.setBackground(pink);

        for(int i=0;i<players.length;i++){

            JPanel box = new JPanel(new BorderLayout());
            box.setBackground(Color.WHITE);

            Color borderColor = randomColor();
            box.setBorder(BorderFactory.createLineBorder(borderColor, 3, true));

            nameLabels[i] = new JLabel(players[i].name + " : 0");
            nameLabels[i].setFont(new Font("Arial", Font.BOLD, 22));
            nameLabels[i].setHorizontalAlignment(JLabel.CENTER);

            box.add(nameLabels[i]);
            leftPanel.add(box);
        }

        add(leftPanel, BorderLayout.WEST);

        // ===== CENTER =====
        JPanel center = new JPanel(new GridLayout(1,4));
        center.setBackground(pink);

        for(int i=0;i<4;i++){
            images[i] = new JLabel(players[i].name, JLabel.CENTER);
            images[i].setVerticalTextPosition(JLabel.BOTTOM);
            images[i].setHorizontalTextPosition(JLabel.CENTER);
            center.add(images[i]);
        }

        add(center, BorderLayout.CENTER);

        // ===== SELECT BUTTON =====
        selectPanel = new JPanel();
        selectPanel.setBackground(pink);

        r = new JButton(rock);
        p = new JButton(paper);
        s = new JButton(scissors);

        selectPanel.add(r);
        selectPanel.add(p);
        selectPanel.add(s);

        add(selectPanel, BorderLayout.SOUTH);

        // ===== TOP RIGHT BUTTON =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,15,10));
        topPanel.setBackground(pink);

        GameButton confirm = new GameButton("Confirm", new Color(144,238,144));
        GameButton cancel = new GameButton("Cancel", new Color(173,216,230));
        GameButton exit = new GameButton("Exit", new Color(255,182,193));

        topPanel.add(confirm);
        topPanel.add(cancel);
        topPanel.add(exit);

        add(topPanel, BorderLayout.NORTH);

        // เสียงปุ่ม
        SoundPlayer.addClickSound(r);
        SoundPlayer.addClickSound(p);
        SoundPlayer.addClickSound(s);
        SoundPlayer.addClickSound(confirm);
        SoundPlayer.addClickSound(cancel);
        SoundPlayer.addClickSound(exit);

        // ===== EVENTS =====
        r.addActionListener(e->{
            playerChoice="Rock";
            images[0].setIcon(rock);
            animateSelection(r, p, s);
        });

        p.addActionListener(e->{
            playerChoice="Paper";
            images[0].setIcon(paper);
            animateSelection(p, r, s);
        });

        s.addActionListener(e->{
            playerChoice="Scissors";
            images[0].setIcon(scissors);
            animateSelection(s, r, p);
        });

        cancel.addActionListener(e->{
            playerChoice="";
            images[0].setIcon(null);
            resetButtons();
        });

        confirm.addActionListener(e->playRound());

        exit.addActionListener(e-> System.exit(0));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // ===== GAME =====
    private void playRound(){

        //ถ้ายังไม่เลือก ห้ามเล่น
        if(playerChoice.equals("")){
            JOptionPane.showMessageDialog(this, "Please select first!");
            return;
        }

        // ผู้เล่น
        players[0].choice = playerChoice;

        // AI เลือก
        for(int i=1;i<players.length;i++){
            players[i].choice = randomChoice();
        }

        // แสดงรูป
        for(int i=0;i<players.length;i++){
            ImageIcon icon = getIcon(players[i].choice);

            if(icon != null){
                images[i].setIcon(icon);
            }else{
                images[i].setIcon(null);
            }
        }

        // reset streak
        for(Player p:players){
            p.lose();
        }

        //  คำนวณผล
        for(int i=0;i<players.length;i++){
            for(int j=0;j<players.length;j++){
                if(i != j && logic.win(players[i].choice, players[j].choice)){
                    players[i].addScore();
                    players[i].win();
                }
            }
        }

        // อัปเดต UI + glow
        for(int i=0;i<players.length;i++){

            nameLabels[i].setText(
                    players[i].name + " : " + players[i].score +
                            " 🔥x" + players[i].winStreak
            );

            //glow ตอน streak 3+
            if(players[i].winStreak >= 3){
                images[i].setBorder(BorderFactory.createLineBorder(
                        new Color(255,140,0), 4, true));
            }else{
                images[i].setBorder(null);
            }
        }

        //รีเซ็ต
        playerChoice = "";
        resetButtons();

        //เช็คจบเกม (ชนะที่ 3 คะแนน)
        for(Player p:players){
            if(p.score >= 3){
                new ResultGUI(players);
                dispose();
                return;
            }
        }
    }
    // ===== ANIMATION =====
    private void animateSelection(JButton selected, JButton... others){

        for(JButton b : others){
            b.setVisible(false);
        }

        Timer grow = new Timer(10, null);

        grow.addActionListener(e -> {

            Dimension size = selected.getPreferredSize();

            if(size.width < 150){
                selected.setPreferredSize(new Dimension(size.width+5, size.height+5));
                selected.revalidate();
            }else{
                grow.stop();
            }

        });

        grow.start();
    }

    private void resetButtons(){

        for(Component c : selectPanel.getComponents()){
            if(c instanceof JButton){
                JButton b = (JButton)c;
                b.setVisible(true);
                b.setPreferredSize(new Dimension(100,100));
            }
        }

        revalidate();
        repaint();
    }

    // ===== UTIL =====
    private String randomChoice(){
        String[] c={"Rock","Paper","Scissors"};
        return c[rand.nextInt(3)];
    }

    private ImageIcon getIcon(String c){
        if(c.equals("Rock")) return rock;
        if(c.equals("Paper")) return paper;
        return scissors;
    }

    private Color randomColor(){
        int r = (int)(Math.random()*200)+30;
        int g = (int)(Math.random()*200)+30;
        int b = (int)(Math.random()*200)+30;
        return new Color(r,g,b);
    }

    private ImageIcon loadIcon(String name){
        java.net.URL url = getClass().getResource("/"+name);
        if(url == null){
            System.out.println("❌ ไม่เจอ: "+name);
            return null;
        }
        Image img = new ImageIcon(url).getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

}