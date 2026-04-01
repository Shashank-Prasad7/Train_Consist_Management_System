import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> passengerBogies = new ArrayList<>();

        System.out.println("=== Train Consist Management App ===");
        boolean running = true;

        while (running) {
            System.out.println("\nOptions: 1. Add  2. Remove  3. Check  4. Display  5. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter bogie name to add: ");
                    String newBogie = scanner.nextLine();
                    passengerBogies.add(newBogie);
                    System.out.println(newBogie + " added to consist.");
                    break;

                case 2:
                    System.out.print("Enter bogie name to remove: ");
                    String toRemove = scanner.nextLine();
                    if (passengerBogies.remove(toRemove)) {
                        System.out.println(toRemove + " decoupled successfully.");
                    } else {
                        System.out.println("Error: Bogie not found.");
                    }
                    break;

                case 3:
                    System.out.print("Search for bogie: ");
                    String toFind = scanner.nextLine();
                    if (passengerBogies.contains(toFind)) {
                        System.out.println("Result: " + toFind + " is in the consist.");
                    } else {
                        System.out.println("Result: Bogie not found.");
                    }
                    break;

                case 4:
                    System.out.println("Current Consist: " + passengerBogies);
                    System.out.println("Total Count: " + passengerBogies.size());
                    break;

                case 5:
                    running = false;
                    System.out.println("Exiting... Safe travels!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
}