package testrismenu;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameSounds {
    private String soundsFolder = "src" + File.separator + "tetrisSounds" + File.separator;
    
    private String readyPath = soundsFolder + "ready.wav";
    private String goPath = soundsFolder + "go.wav";
    private String blockDownPath = soundsFolder + "blockDown.wav";
    private String clearLinesPath = soundsFolder + "clearLines.wav";
    private String gameOverPath = soundsFolder + "gameOver.wav";
    
    private Clip readySound, goSound, blockDownSound, clearLinesSound, gameOverSound;
    
    public GameSounds() {
        try {
            readySound = AudioSystem.getClip();
            goSound = AudioSystem.getClip();
            blockDownSound = AudioSystem.getClip();
            clearLinesSound = AudioSystem.getClip();
            gameOverSound = AudioSystem.getClip();
            
            readySound.open( AudioSystem.getAudioInputStream(new File(readyPath).getAbsoluteFile()));
            goSound.open( AudioSystem.getAudioInputStream(new File(goPath).getAbsoluteFile()));
            blockDownSound.open( AudioSystem.getAudioInputStream(new File(blockDownPath).getAbsoluteFile()));
            clearLinesSound.open( AudioSystem.getAudioInputStream(new File(clearLinesPath).getAbsoluteFile()));
            gameOverSound.open( AudioSystem.getAudioInputStream(new File(gameOverPath).getAbsoluteFile()));
        
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameSounds.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GameSounds.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameSounds.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void playReady() {
        new SoundThread(readyPath).start();
    }
    public void playGo() {
        new SoundThread(goPath).start();
    }
    public void playBlockDown() {
        new SoundThread(blockDownPath).start();
    }
    public void playClearLines() {
        new SoundThread(clearLinesPath).start();
    }
    public void playGameOver() {
        new SoundThread(gameOverPath).start();
    }
    
}
