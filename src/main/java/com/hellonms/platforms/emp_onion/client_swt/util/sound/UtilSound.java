package com.hellonms.platforms.emp_onion.client_swt.util.sound;

import java.io.ByteArrayInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class UtilSound {

	public static void playSound(byte[] buf) {
		try {
			AudioFileFormat audioFileFormat = AudioSystem.getAudioFileFormat(new ByteArrayInputStream(buf));
			AudioFormat audioFormat = audioFileFormat.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
			line.start();

			line.write(buf, 0, buf.length);

			line.drain();
			line.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
