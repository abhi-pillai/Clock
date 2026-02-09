# AlarmClock
## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#️-technologies-used)
- [How to Run](#️-how-to-run)
- [Usage](#usage)
- [Contributing](#contributing)
- [Acknowledgments](#acknowledgments)

## Overview
AlarmClock is a Java-based application designed to provide reliable alarm functionality with an intuitive user interface. 

## Features
- Choose between **24-hour** and **12-hour (AM/PM)** time formats
- Real-time clock display updated every second
- Alarm triggers at the specified time
- Plays a `.wav` sound file when the alarm rings
- Alarm stops when the user presses **Enter**
- Runs alarm logic on a **separate thread**

## 🛠️ Technologies Used
- Java (JDK 8+ recommended)
- `java.time` API `LocalTime`, `DateTimeFormatter`)
- Java Sound API `javax.sound.sampled`)
- Multithreading `Runnable`, `Thread`)
- Console input using `Scanner`

## ▶️ How to Run
1. **Clone or download** the project
2. Make sure you have **Java installed**
   ```bash
   java -version
   ```
3. Place a valid .wav file named alarm.wav in the correct path
4. Compile the project:
```bash
    javac Clock/AlarmClock/*.java
```
5. Run the application:
```bash
    java Clock.AlarmClock.Main
```

## Usage
1. **Start the program**
2. **Choose time format:**
    - `1` → 24-hour format
    - `2` → 12-hour format
3. **Enter the alarm time:**
    - 24-hour: `HH:mm` (e.g., 14:30)
    - 12-hour: `hh:mm AM/PM` (e.g., 02:30 PM)
4. **Monitor the current time** - displays every second
5. **Stop the alarm** - press Enter when it rings

## Contributing
Contributions are welcome! Please follow standard Java conventions and submit pull requests.

## Acknowledgments
This project was developed following best practices and educational resources, including tutorials from BroCode, which provided valuable guidance on Java fundamentals and multithreading concepts.

[⬆ Back to Top](#alarmclock)
