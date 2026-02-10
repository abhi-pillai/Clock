# 🔧 Implementation Details

> Part of the [Stopwatch](README.md) project.

---

## Table of Contents

- [Architecture Overview](#architecture-overview)
- [Class Breakdown](#class-breakdown)
  - [Main.java](#mainjava)
  - [Stopwatch.java](#stopwatchjava)
- [Threading Model](#threading-model)
- [Time Calculation](#time-calculation)
- [Console Rendering](#console-rendering)
- [Thread Synchronization](#thread-synchronization)
- [Shutdown Handling](#shutdown-handling)
- [Known Limitations](#known-limitations)

---

## Architecture Overview

The application uses a two-thread model:

```
Main Thread
  └── Stopwatch Thread (timer loop + redraw)
        └── Control Thread (blocking input listener)
```

`Main` spawns a `Stopwatch` thread. Inside `Stopwatch.run()`, a second **control thread** is started to listen for `Enter` key presses without blocking the timer loop.

---

## Class Breakdown

### `Main.java`

The entry point. Its only responsibility is to instantiate `Stopwatch`, wrap it in a `Thread`, and start it.

```java
Stopwatch stopwatch = new Stopwatch();
Thread stopwatchThread = new Thread(stopwatch);
stopwatchThread.start();
```

`Stopwatch` implements `Runnable`, so it can be passed directly to `Thread`.

---

### `Stopwatch.java`

Contains all core logic. Key fields:

| Field           | Type              | Purpose                                               |
|-----------------|-------------------|-------------------------------------------------------|
| `elapsedTime`   | `String`          | Formatted time string displayed in the terminal       |
| `laps`          | `int`             | Counter for the number of laps recorded               |
| `running`       | `boolean`         | Loop control flag — set to `false` to stop the timer  |
| `consoleLock`   | `Object`          | Monitor object used to synchronize console writes     |
| `printingLap`   | `volatile boolean`| Flag to pause the redraw loop while a lap is printing |
| `sc`            | `Scanner`         | Reads `System.in` for Enter key presses               |

---

## Threading Model

### Timer Thread (`Stopwatch.run()`)

Runs the main loop. On each iteration (every ~10ms), it:

1. Checks `printingLap` — skips redraw if a lap is being printed
2. Computes elapsed time from `System.nanoTime()`
3. Acquires `consoleLock` and calls `redraw()`
4. Sleeps for 10ms

### Control Thread (`Stopwatch.control()`)

Started inside `run()` as an anonymous thread:

```java
new Thread(this::control).start();
```

Blocks on `sc.nextLine()` waiting for the user to press `Enter`. Each press triggers `lap()`. This thread runs independently so it never blocks the timer loop.

---

## Time Calculation

Elapsed time is derived from `System.nanoTime()`, which provides a high-resolution monotonic clock unaffected by system clock adjustments.

```java
long elapsedMillis = (System.nanoTime() - startTime) / 1_000_000;

long cs = (elapsedMillis / 10) % 100;   // centiseconds (0–99)
long s  = (elapsedMillis / 1000) % 60;  // seconds      (0–59)
long m  = (elapsedMillis / 60000) % 60; // minutes      (0–59)
long h  =  elapsedMillis / 3600000;     // hours
```

Each unit uses integer division and modulo to extract its place value from the total millisecond count. The result is formatted with `String.format`:

```java
elapsedTime = String.format("%02d:%02d:%02d:%02d", h, m, s, cs);
```

---

## Console Rendering

The live display is achieved by rewriting the current terminal line in place rather than printing new lines, keeping the output clean.

```java
System.out.print("\r\033[2K" + elapsedTime);
System.out.flush();
```

| Sequence    | Effect                                      |
|-------------|---------------------------------------------|
| `\r`        | Moves the cursor to the beginning of the line |
| `\033[2K`   | ANSI escape — erases the entire current line |

Together, they clear the previous time value before writing the updated one. `System.out.flush()` ensures the output is pushed to the terminal immediately rather than buffered.

> **Note:** ANSI escape codes are supported on most Unix/macOS terminals and on Windows Terminal / PowerShell in Windows 10+. Legacy Windows `cmd.exe` may not render them correctly.

---

## Thread Synchronization

Two mechanisms prevent race conditions between the timer thread and the control thread:

### 1. `consoleLock` — Mutual Exclusion on Console Writes

The timer thread acquires `consoleLock` before calling `redraw()`, and `lap()` is declared `synchronized` (locking on `this`). This prevents the timer from writing to the console at the same moment a lap line is being printed, which would produce garbled output.

```java
// Timer thread
synchronized (consoleLock) {
    redraw();
}

// Control thread calls:
public synchronized void lap() { ... }
```

### 2. `printingLap` — Guard Flag for the Redraw Loop

`printingLap` is declared `volatile` so its value is always read from main memory rather than a thread-local CPU cache.

```java
private volatile boolean printingLap = false;
```

It is set to `true` at the start of `lap()` and back to `false` at the end, signalling the timer loop to skip its redraw during that window. This prevents the `\r` cursor-reset from clobbering the lap line before it is fully printed.

---

## Shutdown Handling

A JVM shutdown hook is registered in the constructor to handle `Ctrl+C` (SIGINT) gracefully:

```java
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    synchronized (consoleLock) {
        running = false;
        System.out.println("\nStopwatch stopped");
    }
}));
```

Setting `running = false` causes the timer loop to exit cleanly. The hook also acquires `consoleLock` to ensure the exit message isn't interleaved with a concurrent redraw. The `Scanner` in `control()` will throw or return on stream close, which is caught and silently ignored.

---

## Known Limitations

- **ANSI support** — The `\033[2K` escape code may not render correctly on older Windows terminals (e.g., classic `cmd.exe`).
- **Centiseconds vs. milliseconds** — The display format shows centiseconds (1/100th of a second) despite the underlying calculation working in milliseconds. The label in the original code comments this as `cs`.
- **No pause/resume** — The stopwatch cannot be paused; only lapping and stopping are supported.
- **Single stopwatch instance** — The design does not support running multiple stopwatch instances concurrently from a single process.