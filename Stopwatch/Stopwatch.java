package Clock.StopWatch;

public class Stopwatch implements Runnable {
    private String elapsedTime;
    private int laps = 0;
    boolean running = true;
    Stopwatch() {
        this.elapsedTime = "00:00:00:000";
    }
    @Override
    public void run() {
        long startTime = System.nanoTime();

        while (running) {
            long elapsedNanos = System.nanoTime() - startTime;
            long elapsedMillis = elapsedNanos / 1_000_000;

            long centiseconds = (elapsedMillis / 10) % 100;
            long seconds = (elapsedMillis / 1000) % 60;
            long minutes = (elapsedMillis / 60000) % 60;
            long hours = elapsedMillis / 3600000;

            System.out.printf(
                "\r%02d:%02d:%02d:%02d",
                hours, minutes, seconds, centiseconds
            );
            elapsedTime = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, centiseconds);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Stopwatch interrupted: " + e.getMessage());
            }

        }
        System.out.println("\nStopwatch stopped" );
    }

    public void lap() {
        laps++;
        System.out.println("\nLap " + laps + ": " + elapsedTime);
    }
    public void stop() {
        running = false;
    }
}
