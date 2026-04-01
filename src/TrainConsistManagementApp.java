import java.util.ArrayList;
import java.util.List;

/**
 * UC1: Initialize Train Consist Management Application
 * This class handles the entry point and initial setup of the train consist.
 */
public class TrainConsistManagementApp {

    public static void main(String[] args) {
        // 1. Application prints welcome message
        System.out.println("=== Train Consist Management App ===");

        // 2. Train consist is initialized
        // Using List (Interface) and ArrayList (Implementation) for dynamic resizing
        List<String> trainConsist = new ArrayList<>();

        // 3. Initial bogie count is displayed
        // .size() will return 0 as the list was just dynamically initialized
        System.out.println("Initial Bogie Count: " + trainConsist.size());

        // 4. Program continues
        System.out.println("Application initialized successfully. Ready for management tasks.");
    }
}