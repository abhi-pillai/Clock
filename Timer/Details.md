# Implementation Details & Libraries

A technical breakdown of how the CLI Timer works, the design decisions made, and the libraries used.

---

## Architecture Overview

The project is split into two classes with a clear separation of responsibilities:

- **`Main.java`** handles all user interaction — it reads input, parses the time, and hands off to the timer.
- **`Timer.java`** owns all runtime logic — the countdown loop, terminal output, and alarm playback.

The timer runs on its own thread (via `Runnable`), which is a deliberate design choice explained below.

---

## Class Breakdown

### `Main.java`

The entry point. It does three things:

1. Prints a welcome message and prompts the user for input in `HH:MM:SS` format.
2. Splits the input string on `:` and parses each segment into an integer using `Integer.parseInt()`.
3. Constructs a `Timer` object and starts it on a new `Thread`.

The `Scanner` instance is passed into `Timer` so the alarm interaction (pressing Enter to stop) can reuse the same input stream rather than opening a second one.

### `Timer.java`

Implements `Runnable` so it can be executed on a separate thread. The `run()` method contains the core countdown loop:

- `Thread.sleep(1000)` pauses execution for one second on each iteration.
- After each sleep, `seconds` is decremented. When `seconds` drops below 0, it wraps to 59 and decrements `minutes`. The same cascades to `hours`.
- `System.out.printf("\r%02d:%02d:%02d ", ...)` uses a carriage return (`\r`) to overwrite the current terminal line, giving the appearance of a live in-place update rather than printing a new line each second.

Once the loop exits (all values reach 0), `playAlarmSound()` is called.

---

## The Threading Model

The `Timer` class implements `Runnable` and is launched via:

```java
Thread timerThread = new Thread(timer);
timerThread.start();
```

**Why a separate thread?**

`Thread.sleep(1000)` blocks the thread it runs on. If the timer ran on the main thread, the entire program would be blocked and unresponsive for the duration. Offloading to a background thread keeps the main thread free — important if you ever want to extend the app to support input commands (like pause/cancel) while the countdown is running.

---

## Libraries Used

### `java.util.Scanner`

**What it is:** Part of the Java standard library. Provides a simple API for reading input from streams — in this case, `System.in` (the terminal).

**Why it was used:** It's the most straightforward way to read a line of user input in a console application without any external dependencies. `sc.nextLine()` is also reused later in `playAlarmSound()` to wait for the user to press Enter and stop the alarm.

**Key usage:**
```java
Scanner sc = new Scanner(System.in);
String input = sc.nextLine();  // Read timer input
sc.nextLine();                 // Wait for Enter to stop alarm
sc.close();                    // Clean up the stream
```

---

### `javax.sound.sampled` (Java Sound API)

**What it is:** A built-in Java API (part of the JDK since Java 1.3) for capturing, processing, and playing back audio.

**Why it was used:** No external libraries are needed — it's included with any standard Java installation. It provides native support for uncompressed `.wav` files, which is the format used for the alarm sound.

**Why not MP3?** The Java Sound API does not natively support MP3. For MP3 support, a third-party library like [JLayer](http://www.javazoom.net/javalayer/javalayer.html) or [MP3SPI](http://www.javazoom.net/mp3spi/mp3spi.html) would be required. Using `.wav` keeps the project dependency-free.

**Key classes used:**

| Class | Purpose |
|---|---|
| `AudioInputStream` | Reads raw audio data from the `.wav` file |
| `AudioSystem` | Factory class — provides `getAudioInputStream()` and `getClip()` |
| `Clip` | Represents a pre-loaded audio clip that can be played, looped, and stopped |

**Key usage:**
```java
AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
Clip clip = AudioSystem.getClip();
clip.open(audioStream);
clip.loop(Clip.LOOP_CONTINUOUSLY);  // Keep looping until user presses Enter
clip.start();
sc.nextLine();                      // Block here until Enter is pressed
clip.stop();
```

`Clip.LOOP_CONTINUOUSLY` is a constant (`-1`) that tells the clip to repeat indefinitely until explicitly stopped. This ensures the alarm keeps sounding until the user acknowledges it.

---

### `java.io.File` and `java.io.IOException`

**What they are:** Core Java I/O classes. `File` represents a file path on the filesystem; `IOException` is the checked exception thrown by I/O operations.

**Why they were used:** Before attempting to load the audio file, the code checks `soundFile.exists()` to avoid throwing a cryptic exception when the file isn't present. Instead, a friendly message is printed. This is a safer defensive pattern than catching a `FileNotFoundException` after the fact.

---

## Exception Handling

The `playAlarmSound()` method handles several distinct failure modes separately, which makes debugging easier than catching a generic `Exception`:

| Exception | Cause |
|---|---|
| `UnsupportedAudioFileException` | The file exists but is not a format the Java Sound API recognises (e.g., MP3 passed as a WAV) |
| `LineUnavailableException` | The system's audio output line is in use or unavailable (e.g., no audio device) |
| `IOException` | A general read error while streaming the audio data |
| `Exception` | A catch-all for any other unexpected runtime issue during playback |

The `InterruptedException` in the main countdown loop is caught and its stack trace printed — in a production app, this would ideally restore the thread's interrupted status with `Thread.currentThread().interrupt()`.

---

## Potential Improvements

- **Pause / Resume** — Expose a flag that the main thread can toggle to pause the countdown, enabled by the threading model already in place.
- **Cross-platform filepath** — Replace the hardcoded Windows path (`Clock\\src\\alarm.wav`) with `System.getProperty("user.dir")` or a relative path using `File.separator` for portability.
- **MP3 support** — Integrate JLayer or use JavaFX's `MediaPlayer` for broader audio format support.
- **Restore interrupt status** — Replace `e.printStackTrace()` in the `InterruptedException` catch block with `Thread.currentThread().interrupt()` to correctly signal interruption to the JVM.
- **Input validation** — Add error handling around `Integer.parseInt()` so invalid formats (e.g., `abc:00:00`) don't crash with an unhandled `NumberFormatException`.