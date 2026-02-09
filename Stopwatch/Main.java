package Clock.Stopwatch;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Stopwatch stopwatch = new Stopwatch();
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
        sc.close();
    }
}
