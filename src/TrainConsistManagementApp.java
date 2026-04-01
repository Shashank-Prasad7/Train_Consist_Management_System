import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        // 1. Initialize HashSet (Using Set interface for abstraction)
        Set<String> bogieIds = new HashSet<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Unique Bogie ID Tracker ===");

        // 2. User adds bogie IDs (including intentional duplicates)
        // In a real scenario, these would be IDs like "B-101", "A-202", etc.
        System.out.println("Enter 5 Bogie IDs (try entering the same ID twice):");

        for (int i = 0; i < 5; i++) {
            System.out.print("Enter ID " + (i + 1) + ": ");
            String id = scanner.nextLine();

            // 3. System inserts into HashSet
            boolean isAdded = bogieIds.add(id);

            if (!isAdded) {
                System.out.println(">> Duplicate detected! ID '" + id + "' was ignored.");
            }
        }

        // 4. Display final unique IDs
        System.out.println("\n--- Final Unique Bogie List ---");
        System.out.println("Unique IDs: " + bogieIds);
        System.out.println("Total Unique Count: " + bogieIds.size());

        scanner.close();
    }
}