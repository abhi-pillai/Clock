# Clock

A command-line Java application suite providing three time-management utilities: an alarm clock, a stopwatch, and a countdown timer.

## Features

- **Alarm Clock** - Set alarms with customizable time formats (12-hour/24-hour) and audio notifications
- **Stopwatch** - Track elapsed time with lap recording functionality
- **Timer** - Countdown timer with audio alerts when time expires

## Project Structure

```
Clock/
├── AlarmClock/
│   ├── Main.java
│   └── Alarm.java
├── Stopwatch/
│   ├── Main.java
│   └── Stopwatch.java
├── Timer/
│   ├── Main.java
│   └── Timer.java
└── src/
    └── alarm.wav
```

## Usage

Each utility can be run independently from its respective `Main.java` file:

- **Alarm Clock**: `Clock.AlarmClock.Main`
- **Stopwatch**: `Clock.Stopwatch.Main`
- **Timer**: `Clock.Timer.Main`

## Requirements

- Java 8 or higher
- Audio file (WAV format) for alarm/timer notifications (optional)

## Documentation

For detailed information about each component, refer to the individual README files in each subfolder:

- [Alarm Clock README](AlarmClock/README.md)
- [Stopwatch README](Stopwatch/README.md)
- [Timer README](Timer/README.md)

## Notes

All three utilities use multithreading to manage time-based operations and provide responsive user interfaces in the terminal.