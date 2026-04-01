import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // HashMap<Key: Bogie Name, Value: Capacity>
        Map<String, Integer> bogieCapacities = new HashMap<>();

        System.out.println("=== UC6: Bogie Capacity Mapping (User-Defined) ===");
        System.out.println("Enter bogie details. Type 'done' as the name to finish.");

        while (true) {
            System.out.print("\nEnter Bogie Name (e.g., Sleeper): ");
            String name = scanner.nextLine();

            if (name.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter Seating Capacity for " + name + ": ");
            // Use try-catch or validation to ensure an integer is entered
            try {
                int capacity = Integer.parseInt(scanner.nextLine());

                // .put() inserts or updates the mapping
                bogieCapacities.put(name, capacity);
                System.out.println(">> Mapped: " + name + " -> " + capacity + " seats.");
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Please enter a valid numeric capacity.");
            }
        }

        // --- Iterating and Displaying Results ---
        System.out.println("\n--- Final Bogie Capacity Report ---");
        if (bogieCapacities.isEmpty()) {
            System.out.println("No capacity data recorded.");
        } else {
            // Using entrySet() to access both Key and Value efficiently
            for (Map.Entry<String, Integer> entry : bogieCapacities.entrySet()) {
                System.out.println("Bogie Type: " + entry.getKey() + " | Capacity: " + entry.getValue() + " seats");
            }
        }

        // --- Fast Lookup Example ---
        System.out.print("\nQuick Search: Enter a bogie name to check capacity: ");
        String searchKey = scanner.nextLine();
        if (bogieCapacities.containsKey(searchKey)) {
            System.out.println("Result: " + searchKey + " has a capacity of " + bogieCapacities.get(searchKey) + ".");
        } else {
            System.out.println("Result: No data found for '" + searchKey + "'.");
        }

        scanner.close();
    }
}