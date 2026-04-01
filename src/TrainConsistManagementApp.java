import java.util.LinkedList;
import java.util.Scanner;

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> trainConsist = new LinkedList<>();

        System.out.println("=== UC4: Advanced Train Consist Management (User-Defined) ===");
        boolean running = true;

        while (running) {
            System.out.println("\n--- Current Consist: " + trainConsist + " ---");
            System.out.println("1. Add to Front (Engine)");
            System.out.println("2. Add to End (Guard/Cargo)");
            System.out.println("3. Insert at Specific Position (e.g., Pantry)");
            System.out.println("4. Remove First Bogie");
            System.out.println("5. Remove Last Bogie");
            System.out.println("6. Exit");
            System.out.print("Select action: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter bogie to add at FRONT: ");
                    trainConsist.addFirst(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter bogie to add at END: ");
                    trainConsist.addLast(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter position (Index): ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    if (index >= 0 && index <= trainConsist.size()) {
                        System.out.print("Enter bogie name: ");
                        trainConsist.add(index, scanner.nextLine());
                    } else {
                        System.out.println("Invalid position!");
                    }
                    break;
                case 4:
                    if (!trainConsist.isEmpty()) {
                        System.out.println("Detached from front: " + trainConsist.removeFirst());
                    }
                    break;
                case 5:
                    if (!trainConsist.isEmpty()) {
                        System.out.println("Detached from rear: " + trainConsist.removeLast());
                    }
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
        System.out.println("Final Train Setup: " + trainConsist);
    }
}