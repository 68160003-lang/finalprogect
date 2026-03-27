public class Player {

    String name;
    int score;
    String choice;
    boolean isUser;

    int winStreak = 0;

    public Player(String name){
        this.name = name;
        this.score = 0;
    }

    public void addScore(){
        score++;
    }

    public void win(){
        winStreak++; // 🔥 ชนะ
    }

    public void lose(){
        winStreak = 0; // 🔥 แพ้รีเซ็ต
    }
    public Player(String name, boolean isUser){
        this.name = name;
        this.score = 0;
        this.isUser = isUser;
    }
}