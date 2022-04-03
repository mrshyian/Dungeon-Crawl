package com.codecool.dungeoncrawl.logic;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlayMusic {

    public static void setSoundTrack(String link, float volume){
        try {
            playMusic(link, volume);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void playMusic(String link, float volume) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File file = new File(link);

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = volume+ gainControl.getMinimum();
        gainControl.setValue(gain);
        clip.start();

//        clip.stop();
//        clip.setMicrosecondPosition(0);
//        clip.close();
    }
}