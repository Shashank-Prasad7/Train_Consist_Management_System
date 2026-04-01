import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

// 1. Create a Bogie class (The Blueprint)
class Bogie {
    String name;
    int capacity;

    public Bogie(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return String.format("[%s | Capacity: %d]", name, capacity);
    }
}

public class TrainConsistManagementApp{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 2. Create a List<Bogie> to store custom objects
        List<Bogie> trainConsist = new ArrayList<>();

        System.out.println("=== UC7: Custom Bogie Sorting (User-Defined) ===");
        System.out.println("Enter bogie details. Type 'done' to finish adding.");

        while (true) {
            System.out.print("\nEnter Bogie Name: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) break;

            System.out.print("Enter Seating Capacity: ");
            try {
                int capacity = Integer.parseInt(scanner.nextLine());
                // 3. Create object and store in List
                trainConsist.add(new Bogie(name, capacity));
            } catch (NumberFormatException e) {
                System.out.println("Invalid capacity. Please enter a number.");
            }
        }

        if (trainConsist.isEmpty()) {
            System.out.println("No bogies to sort.");
        } else {
            System.out.println("\nOriginal Order: " + trainConsist);

            // 4. Use Comparator and sort() method with a Lambda Expression
            // Sorting by capacity (Ascending)
            trainConsist.sort(Comparator.comparingInt(b -> b.capacity));

            // 5. Display the sorted results
            System.out.println("Sorted Order (By Capacity): " + trainConsist);
        }

        scanner.close();
    }
}