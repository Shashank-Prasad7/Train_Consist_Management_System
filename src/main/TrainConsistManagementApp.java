import java.util.*;

// ─────────────────────────────────────────────
//  Custom Exception – UC14
// ─────────────────────────────────────────────
class InvalidCapacityException extends Exception {

    public InvalidCapacityException(String message) {
        super(message);
    }
}

// ─────────────────────────────────────────────
//  PassengerBogie Entity
// ─────────────────────────────────────────────
class PassengerBogie {
    private String bogieId;
    private String bogieType;
    private int    capacity;

    // Constructor throws custom exception if capacity is invalid
    public PassengerBogie(String bogieId, String bogieType, int capacity)
            throws InvalidCapacityException {

        if (capacity <= 0) {
            throw new InvalidCapacityException(
                    "Capacity must be greater than zero"
            );
        }

        this.bogieId   = bogieId;
        this.bogieType = bogieType;
        this.capacity  = capacity;
    }

    public String getBogieId()   { return bogieId;   }
    public String getBogieType() { return bogieType; }
    public int    getCapacity()  { return capacity;  }

    @Override
    public String toString() {
        return String.format(
                "PassengerBogie{id='%s', type='%s', capacity=%d}",
                bogieId, bogieType, capacity
        );
    }
}

// ─────────────────────────────────────────────
//  Main Class – UC14 : Custom Exception for
//               Invalid Passenger Bogie Capacity
// ─────────────────────────────────────────────
public class TrainConsistManagementApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<PassengerBogie> trainConsist = new ArrayList<>();

        System.out.println("===========================================");
        System.out.println("  UC14 : Passenger Bogie Capacity           ");
        System.out.println("         Validation via Custom Exception     ");
        System.out.println("===========================================");
        System.out.println("  Rule : Capacity must be > 0");
        System.out.println("===========================================");

        // ── Step 1 : User enters number of bogies to add ──────────────────
        System.out.print("\nHow many bogies do you want to add? : ");
        int n = Integer.parseInt(sc.nextLine().trim());

        // ── Step 2 : User enters each bogie's details ─────────────────────
        System.out.println("\nEnter bogie details :");
        System.out.println("-------------------------------------------");

        for (int i = 1; i <= n; i++) {
            System.out.println("\n  Bogie #" + i);

            System.out.print("    Bogie ID   : ");
            String id = sc.nextLine().trim();

            System.out.print("    Bogie Type : ");
            String type = sc.nextLine().trim();

            System.out.print("    Capacity   : ");
            int cap = Integer.parseInt(sc.nextLine().trim());

            // ── Step 3 : Validate capacity — throw if invalid ──────────────
            try {
                PassengerBogie bogie = new PassengerBogie(id, type, cap);
                trainConsist.add(bogie);
                System.out.println("    SUCCESS : Bogie added -> " + bogie);

            } catch (InvalidCapacityException e) {
                // ── Step 4 : Exception caught — display error, skip bogie ──
                System.out.println("    ERROR   : " + e.getMessage()
                        + " (entered: " + cap + ") — Bogie NOT added.");
            }
        }

        // ── Step 5 : Display final train consist ──────────────────────────
        System.out.println("\n===========================================");
        System.out.println("[ Final Train Consist ]");
        System.out.println("-------------------------------------------");

        if (trainConsist.isEmpty()) {
            System.out.println("  No valid bogies were added.");
        } else {
            trainConsist.forEach(b -> System.out.println("  " + b));
            System.out.println("\n  Total valid bogies : " + trainConsist.size());
        }

        System.out.println("\n===========================================");
        System.out.println("  Validation complete. Program continues...");
        System.out.println("===========================================");

        sc.close();
    }
}