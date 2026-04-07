import javax.swing.SwingUtilities;
import javax.swing.UIManager;
public class MainGame {
    public static void main(String[] args) {
        SoundPlayer.play("click.wav");
        SwingUtilities.invokeLater(() -> {
            new MenuGUI();
        });
    }
}