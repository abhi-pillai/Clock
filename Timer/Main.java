package Clock.Timer;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String filepath = "Clock\\src\\alarm.wav"; // Path to your sound file (optional)
        System.out.println("---------------Welcome to the Timer!---------------");
        System.out.print("Enter timer in HH:MM:SS format: ");
        String input = sc.nextLine();
        String[] parts = input.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        Timer timer = new Timer(hours, minutes, seconds, filepath,sc);
        Thread timerThread = new Thread(timer);
        timerThread.start();
    }
}
