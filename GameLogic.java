public class GameLogic {

    public boolean win(String a,String b){
        return (a.equals("Rock") && b.equals("Scissors")) ||
                (a.equals("Scissors") && b.equals("Paper")) ||
                (a.equals("Paper") && b.equals("Rock"));
    }
}