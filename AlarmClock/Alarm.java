package Clock.AlarmClock;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
// import java.awt.Toolkit; // Optional: Uncomment if you want to use the beep sound when the alarm goes off
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Alarm implements Runnable {
    private final LocalTime alarmTime;
    private final String filepath;
    private final Scanner scanner;
    private final DateTimeFormatter formatter;
    
    public Alarm(LocalTime alarmTime, String filepath, Scanner sc, DateTimeFormatter formatter) {
        this.alarmTime = alarmTime;
        this.filepath = filepath;
        this.scanner = sc;
        this.formatter = formatter;
    }
    
    @Override
    public void run() {
        while (LocalTime.now().isBefore(alarmTime)) {
            try {
                Thread.sleep(1000); // Check every second
                LocalTime now = LocalTime.now();
                int hr = now.getHour();
                int min = now.getMinute();
                int sec = now.getSecond();
                String ampm = "";
                if(formatter.format(now).contains("M")) { // 12-hour format
                    ampm = formatter.format(now).substring(6);
                }
                 // Extract AM/PM if applicable
                System.out.printf("\r%02d:%02d:%02d %s ",hr,min,sec,ampm);
            } catch (InterruptedException e) {
                System.out.println("Alarm thread interrupted.");
            }
        }
        // Toolkit.getDefaultToolkit().beep();  // Optional: Beep sound when alarm goes off

        System.out.print("\nAlarm ringing! Press Enter to stop the alarm.");
        playAlarmSound(filepath);
    }
    private void playAlarmSound(String filepath) {
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
                scanner.nextLine();
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
