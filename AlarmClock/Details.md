
# AlarmClock Project Documentation

## Overview
The AlarmClock project is a Java-based application that allows users to set alarms in either 24-hour or 12-hour format and plays an audio alarm at the specified time.

## Packages and Libraries Used

### Java Standard Library Packages

#### `java.time`
- **Purpose**: Provides modern date and time handling
- **Classes Used**:
    - `LocalTime`: Represents time without date information; used to store and compare alarm times
    - `DateTimeFormatter`: Formats and parses time strings according to specified patterns (24-hour and 12-hour formats)

#### `java.io`
- **Purpose**: Handles input/output operations
- **Classes Used**:
    - `File`: Represents the alarm sound file in the file system; checks file existence before playback

#### `java.util`
- **Purpose**: Provides utility classes and collections
- **Classes Used**:
    - `Scanner`: Reads user input from the console for time entry and alarm dismissal
    - `Locale`: Specifies locale information for 12-hour time format with AM/PM indicators

#### `javax.sound.sampled`
- **Purpose**: Provides Java Sound API for audio playback
- **Classes Used**:
    - `Clip`: Plays audio files in a loop; allows starting and stopping alarm sound
    - `AudioInputStream`: Reads audio data from the sound file
    - `AudioSystem`: Retrieves audio system resources (Clip and AudioInputStream)
    - `LineUnavailableException`: Exception handling for unavailable audio line
    - `UnsupportedAudioFileException`: Exception handling for unsupported audio formats

## Key Features

- **Multi-format Support**: Accepts both 24-hour (HH:mm) and 12-hour (hh:mm AM/PM) time formats
- **Real-time Countdown**: Displays current time in real-time until alarm triggers
- **Audio Playback**: Plays a looping WAV file alarm sound
- **Interactive Control**: Allows users to dismiss the alarm by pressing Enter
- **Thread-based Execution**: Runs alarm checking in a separate thread to maintain responsiveness
