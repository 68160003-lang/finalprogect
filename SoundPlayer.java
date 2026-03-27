import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

public class SoundPlayer {

    private static Clip bgmClip;

    public static void click(){
        play("click.wav");
    }

    public static void play(String file){

        if(!Settings.soundOn) return;

        try{
            File f = new File("src/" + file);

            if(!f.exists()){
                System.out.println("❌ ไม่เจอ: " + f.getAbsolutePath());
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();

            clip.open(audio);
            setVolume(clip);

            clip.addLineListener(e -> {
                if(e.getType() == LineEvent.Type.STOP){
                    clip.close();
                }
            });

            clip.start();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playBGM(String file){

        if(!Settings.soundOn) return;

        try{
            if(bgmClip != null){
                bgmClip.stop();
                bgmClip.close();
            }

            File f = new File("src/" + file);

            if(!f.exists()){
                System.out.println("❌ ไม่เจอ BGM");
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(f);

            bgmClip = AudioSystem.getClip();
            bgmClip.open(audio);

            setVolume(bgmClip);

            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start(); // 🔥 สำคัญ

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void stopBGM(){
        if(bgmClip != null){
            bgmClip.stop();
        }
    }

    public static void updateBGMVolume(){
        if(bgmClip != null) setVolume(bgmClip);
    }

    private static void setVolume(Clip clip){
        try{
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float dB = (Settings.volume <= 0)
                    ? -80f
                    : (float)(Math.log10(Settings.volume) * 20);

            gain.setValue(dB);

        }catch(Exception e){
            System.out.println("⚠️ ไม่รองรับ volume");
        }
    }

    public static void addClickSound(AbstractButton btn){
        btn.addActionListener(e -> click());
    }
}