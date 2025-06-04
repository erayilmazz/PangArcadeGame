import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class SoundManager {
	Clip clip;
	URL ballonExplode,arrowThrow,breakGlass,itemCollected, selectScreen,antalya,ankara,izmir,istanbul,bonus,gameover;
	
	public SoundManager() {
		ballonExplode = getClass().getResource("/resources/ballonExplode.wav");
		arrowThrow = getClass().getResource("/resources/arrowThrow.wav");
		breakGlass = getClass().getResource("/resources/breakGlass.wav");
		itemCollected = getClass().getResource("/resources/itemCollected.wav");
		selectScreen = getClass().getResource("/resources/selectScreen.wav");
		antalya = getClass().getResource("/resources/antalya.wav");
		ankara = getClass().getResource("/resources/ankara.wav");
		izmir = getClass().getResource("/resources/izmir.wav");
		istanbul = getClass().getResource("/resources/istanbul.wav");
		bonus = getClass().getResource("/resources/bonus.wav");
		gameover = getClass().getResource("/resources/gameover.wav");
		
	}
	public void playSound(URL soundURL) {
        new Thread(() -> {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-5);
                
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
	public void playMusic(URL soundURL) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10);
            if(soundURL != bonus) clip.loop(Clip.LOOP_CONTINUOUSLY);
         } catch (Exception e) {
        	 e.printStackTrace();
         }
   
    }
	public void setFile(URL soundURL) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void play() {
		clip.start();
	}
	
	public void stopMusic() {
		if(clip!=null) clip.stop();
	}

}
