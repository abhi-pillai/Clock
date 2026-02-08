package Clock.AlarmClock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = null;
        LocalTime alarmTime = null;
        System.out.println("---------------Welcome to the Alarm Clock!---------------");
        System.out.println("Choose mode:\n1. 24-hour format\n2. 12-hour format");
        while(true) {
            int mode = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            switch (mode) {
                case 1:
                    formatter = DateTimeFormatter.ofPattern("HH:mm");
                    while (alarmTime == null) {
                        try {
                            System.out.print("Enter alarm time (HH:mm): ");
                            String inputTime = sc.nextLine();
                            alarmTime = LocalTime.parse(inputTime, formatter);
                            System.out.println("Alarm set for: " + alarmTime);
                        } 
                        catch (DateTimeParseException e) {
                        System.out.println("Invalid format. Please use HH:mm (e.g., 14:30)");
                        }
                    }
                    break;
                case 2:
                    formatter = DateTimeFormatter.ofPattern("hh:mm a"); 
                    while (alarmTime == null) {
                        try {
                            System.out.print("Enter alarm time (hh:mm AM/PM): ");
                            String inputTime = sc.nextLine();
                            alarmTime = LocalTime.parse(inputTime, formatter);
                            System.out.println("Alarm set for: " + alarmTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
                        } 
                        catch (DateTimeParseException e) {
                        System.out.println("Invalid format. Please use hh:mm AM/PM (e.g., 02:30 PM)");
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid mode selected. Please choose 1 or 2.");
            }
            if (alarmTime != null) {
                break; // Exit the loop once a valid alarm time is set
            }  
        }    
    }
       
}
