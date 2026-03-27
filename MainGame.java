import javax.swing.SwingUtilities;
import javax.swing.UIManager;
public class MainGame {
    public static void main(String[] args) {
        SoundPlayer.play("click.wav");
        SwingUtilities.invokeLater(() -> {
            UIManager.put("Label.font", FontManager.promptRegular(20f));
            UIManager.put("Button.font", FontManager.promptBold(18f));
            new MenuGUI();
        });
    }
}