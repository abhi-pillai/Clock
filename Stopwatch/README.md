# ⏱️ Stopwatch

A lightweight command-line stopwatch application written in Java. It displays a live-updating elapsed time in your terminal and supports lap recording.

---

## Features

- Live elapsed time display that updates every 10ms
- Lap recording — press `Enter` to capture a lap time
- Clean terminal output using ANSI escape codes (`\r` + `\033[2K`) to rewrite the current line in place
- Graceful shutdown via `Ctrl+C` (or `Cmd+C` on macOS) with a shutdown hook
- Multithreaded design: timer display and input control run on separate threads

---

## Project Structure

```
Clock/Stopwatch/
├── Main.java        # Entry point — creates and starts the Stopwatch thread
└── Stopwatch.java   # Core stopwatch logic (timing, display, lap, control)
```

---

## Requirements

- Java 8 or higher

---

## How to Run

**1. Compile the source files:**

```bash
javac Clock/Stopwatch/Main.java Clock/Stopwatch/Stopwatch.java
```

**2. Run the application:**

```bash
java Clock.Stopwatch.Main
```

---

## Usage

Once running, the stopwatch starts immediately and displays the elapsed time in the format:

```
HH:MM:SS:cs
```

| Segment | Meaning       |
|---------|---------------|
| `HH`    | Hours         |
| `MM`    | Minutes       |
| `SS`    | Seconds       |
| `cs`    | Centiseconds  |

### Controls

| Action              | Key              |
|---------------------|------------------|
| Record a lap        | `Enter`          |
| Stop the stopwatch  | `Ctrl+C / Cmd+C` |

### Example Output

```
Press Enter to record a lap time. 'Ctrl + C / Cmd + C' to stop the stopwatch.
00:00:07:43
Lap 1: 00:00:07:43
Lap 2: 00:00:15:91

Stopwatch stopped
```

---

## Implementation Notes

For a detailed breakdown of the threading model, time calculation, console rendering, synchronization strategy, and shutdown handling, see [Implementation.md](Implementation.md).