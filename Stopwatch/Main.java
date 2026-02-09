package Clock.Stopwatch;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS"); 
        LocalTime time = LocalTime.parse("00:00:00:000", formatter);
        String startTime = formatter.format(time);
        Scanner sc = new Scanner(System.in);
        Stopwatch stopwatch = new Stopwatch(startTime);
        Thread stopwatchThread = new Thread(stopwatch);
        stopwatchThread.start();
        System.out.print("\nPress Enter to record a lap time. Press *Stop* to stop the stopwatch.\n");
        while(true) {
            try {
                String input = sc.next();
                if (input.equalsIgnoreCase("stop")) {
                    stopwatch.stop();
                    break;
                } else {
                   stopwatch.lap();
                }
            } catch (Exception e) {
                System.out.println("Error reading input: " + e.getMessage());
            }
        }
    }
}
