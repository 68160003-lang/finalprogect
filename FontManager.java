import java.awt.*;
import java.io.File;

public class FontManager {

    public static Font promptRegular(float size){
        try{
            return Font.createFont(
                    Font.TRUETYPE_FONT,
                    new File("src/Prompt-Regular.ttf")
            ).deriveFont(size);
        }catch(Exception e){
            System.out.println("❌ โหลดฟ้อน Regular ไม่ได้");
            return new Font("Arial", Font.PLAIN, (int)size);
        }
    }

    public static Font promptBold(float size){
        try{
            return Font.createFont(
                    Font.TRUETYPE_FONT,
                    new File("src/Prompt-Bold.ttf")
            ).deriveFont(size);
        }catch(Exception e){
            System.out.println("❌ โหลดฟ้อน Bold ไม่ได้");
            return new Font("Arial", Font.BOLD, (int)size);
        }
    }
}