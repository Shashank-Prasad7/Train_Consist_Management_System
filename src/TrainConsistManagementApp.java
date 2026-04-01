import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // LinkedHashSet: Ensures uniqueness AND maintains insertion order
        Set<String> trainFormation = new LinkedHashSet<>();

        System.out.println("=== UC5: Ordered Unique Train Formation (User-Defined) ===");
        System.out.println("Enter bogies to attach. Type 'done' to finish.");

        while (true) {
            System.out.print("Enter Bogie Name: ");
            String bogie = scanner.nextLine();

            if (bogie.equalsIgnoreCase("done")) {
                break;
            }

            // add() returns false if the element already exists
            boolean isAdded = trainFormation.add(bogie);

            if (isAdded) {
                System.out.println(">> " + bogie + " successfully attached to the formation.");
            } else {
                System.out.println(">> Duplicate Alert! " + bogie + " is already in the formation. Entry ignored.");
            }
        }

        // Display the final formation
        System.out.println("\n--- Final Train Formation (In Order) ---");
        if (trainFormation.isEmpty()) {
            System.out.println("The train is empty.");
        } else {
            // Because it's a LinkedHashSet, this will print in the exact order entered
            System.out.println(String.join(" -> ", trainFormation));
        }

        System.out.println("Total Unique Bogies: " + trainFormation.size());
        scanner.close();
    }
}