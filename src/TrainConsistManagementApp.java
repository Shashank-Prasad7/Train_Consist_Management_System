import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Reusing the Bogie class from UC7
class Bogie {
    String name;
    int capacity;

    public Bogie(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return String.format("[%s | Cap: %d]", name, capacity);
    }
}

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Bogie> allBogies = new ArrayList<>();

        System.out.println("=== UC8: Stream API Filtering (User-Defined) ===");
        System.out.println("Enter bogie details. Type 'done' to finish.");

        // 1. User creates a list of bogies
        while (true) {
            System.out.print("Enter Bogie Name: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) break;

            System.out.print("Enter Capacity: ");
            try {
                int cap = Integer.parseInt(scanner.nextLine());
                allBogies.add(new Bogie(name, cap));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        if (allBogies.isEmpty()) {
            System.out.println("No bogies to process.");
        } else {
            System.out.print("\nEnter the minimum capacity threshold to filter by: ");
            int threshold = scanner.nextInt();

            // 2. Convert list into a stream
            // 3. Apply filter() with a Lambda condition
            // 4. Collect results into a new list
            List<Bogie> filteredBogies = allBogies.stream()
                    .filter(b -> b.capacity > threshold) // The condition
                    .collect(Collectors.toList());      // Terminal operation

            // 5. Display filtered results
            System.out.println("\n--- Filtering Results (Capacity > " + threshold + ") ---");
            if (filteredBogies.isEmpty()) {
                System.out.println("No bogies match the criteria.");
            } else {
                filteredBogies.forEach(System.out::println);
            }
        }

        scanner.close();
    }
}