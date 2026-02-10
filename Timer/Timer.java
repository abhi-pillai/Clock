package Clock.Timer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Timer implements Runnable {
    private int hours;
    private int minutes;
    private int seconds;
    private final String filepath;
    private final Scanner sc;

    public Timer(int hours, int minutes, int seconds, String filepath, Scanner sc) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.sc = sc;
        this.filepath = filepath;
    }

    @Override
    public void run() {
        System.out.println("Timer started: ");
        while (hours > 0 || minutes > 0 || seconds > 0) {
            try {
                Thread.sleep(1000); // Sleep for 1 second
                seconds--;
                if (seconds < 0) {
                    seconds = 59;
                    minutes--;
                    if (minutes < 0) {
                        minutes = 59;
                        hours--;
                    }
                }
                System.out.printf("\r%02d:%02d:%02d ", hours, minutes, seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nTimer finished! Press Enter to exit.");
        playAlarmSound();
        sc.close();
    }
     private void playAlarmSound() {
        // Optional: Implement sound playing logic here using Java Sound API or a library like JLayer
        // Example: Use Clip to play a WAV file
        File soundFile = new File(filepath);
        if (soundFile.exists()) {   
            try (javax.sound.sampled.AudioInputStream audioStream = javax.sound.sampled.AudioSystem.getAudioInputStream(soundFile)) {
                javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                // Wait for user to press Enter to stop the alarm
                sc.nextLine();
                clip.stop();
            } catch (UnsupportedAudioFileException e) {
                System.out.println("Audio file format not supported: " + e.getMessage());
            }
            catch (LineUnavailableException exception) {
                System.out.println("Audio line unavailable: " + exception.getMessage());
            }
            catch (IOException e) {
                System.out.println("Error reading audio file: " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Error playing alarm sound: " + e.getMessage());
            }
        } else {
            System.out.println("Alarm sound file not found: " + filepath);
        }
    }
}
