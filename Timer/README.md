# ⏱️ Command Line Timer

A simple, lightweight command-line timer built in Java that counts down from a user-specified time and plays an alarm sound when finished.

---

## Features

- Accepts time input in `HH:MM:SS` format
- Live countdown display that updates in place in the terminal
- Plays a `.wav` alarm sound when the timer finishes
- Press **Enter** to stop the alarm after it goes off
- Runs the countdown on a separate thread, keeping the main thread free

---

## Prerequisites

- Java 8 or higher
- A `.wav` audio file for the alarm (optional — the timer works without it)

---

## Project Structure

```
Clock/
├── src/
│   ├── Main.java       # Entry point — handles user input and launches the timer
│   ├── Timer.java      # Core timer logic — countdown, display, and alarm playback
│   └── alarm.wav       # Alarm sound file (place your .wav file here)
```

---

## Getting Started

### 1. Clone or download the project

```bash
git clone https://github.com/your-username/cli-timer.git
cd cli-timer
```

### 2. Add your alarm sound (optional)

Place a `.wav` file at the following path:

```
Clock/src/alarm.wav
```

If no file is found, the timer will still work — it will just print a message saying the sound file was not found.

### 3. Compile

```bash
javac -d out src/Clock/Timer/Main.java src/Clock/Timer/Timer.java
```

### 4. Run

```bash
java -cp out Clock.Timer.Main
```

---

## Usage

```
--------------- Welcome to the Timer! ---------------
Enter timer in HH:MM:SS format: 00:01:30
Timer started:
00:01:29
...
00:00:00

Timer finished! Press Enter to exit.
```

Enter your desired duration in `HH:MM:SS` format (e.g., `00:05:00` for 5 minutes). The countdown will tick down every second and update on the same line. Once it hits zero, the alarm plays and you can press **Enter** to stop it.

---

## Configuration

The alarm file path is set in `Main.java`:

```java
String filepath = "Clock\\src\\alarm.wav";
```

Update this path to point to wherever your `.wav` file lives on your system. On macOS/Linux, use forward slashes:

```java
String filepath = "Clock/src/alarm.wav";
```

---

## Known Limitations

- Only `.wav` files are supported for the alarm sound (MP3 is not supported natively by Java).
- The filepath uses a Windows-style backslash by default — update it for macOS/Linux.
- The timer does not support pausing or resetting mid-countdown.

---

## License

This project is open source and free to use for personal or educational purposes.