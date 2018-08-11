package it.unibo.hue.components;

import java.io.*;
import javax.sound.sampled.*;

public class BluePlayer {
	private static Clip clip;
	
	public static void init()
	{
		try {
			// Open an audio input stream.
			File file = new File("blue.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public static void play()
	{
		init();
		clip.start();
	}
	
	public static void stop()
	{
		clip.stop();
	}
}