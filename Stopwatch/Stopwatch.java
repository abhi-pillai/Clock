package Clock.Stopwatch;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Stopwatch implements Runnable {
    private String elapsedTime;
    private int laps = 0;
    boolean running = true;
    Scanner sc = new Scanner(System.in);
    private final Object consoleLock = new Object();
    private volatile boolean printingLap = false;
    Stopwatch() {
        this.elapsedTime = "00:00:00:000";
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            synchronized (consoleLock) {
                running = false;
                System.out.println("\nStopwatch stopped");
            }
        }));

    }

    @Override
    public void run() {
        long startTime = System.nanoTime();

        System.out.println("Press Enter to record a lap time. 'Ctrl + C / Cmd + C' to stop the stopwatch.");

        new Thread(this::control).start();

        while (running) {
            if (!printingLap) {
                long elapsedMillis = (System.nanoTime() - startTime) / 1_000_000;

                long cs = (elapsedMillis / 10) % 100;
                long s = (elapsedMillis / 1000) % 60;
                long m = (elapsedMillis / 60000) % 60;
                long h = elapsedMillis / 3600000;

                elapsedTime = String.format("%02d:%02d:%02d:%02d", h, m, s, cs);

                synchronized (consoleLock) {
                    redraw();
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }

    }

    private void redraw() {
        System.out.print("\r\033[2K" + elapsedTime);
        System.out.flush();
    }


    public synchronized void lap() {
        printingLap = true;  // stop redraw temporarily
        System.out.println("\nLap " + (++laps) + ": " + elapsedTime);
        printingLap = false; // resume redraw
    }

    public synchronized void stop() {
        running = false;
    }

    private void control() {
        try {
            while (running && sc.hasNextLine()) {
                sc.nextLine(); // Enter = lap
                lap();
            }
        } catch (Exception ignored) {
            // Happens when Ctrl+C closes System.in
        }
    }


}
