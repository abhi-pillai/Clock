package Clock.Stopwatch;
public class Main {
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        Thread stopwatchThread = new Thread(stopwatch);
        stopwatchThread.start();
    }
}
