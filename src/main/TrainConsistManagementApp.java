import java.util.*;
import java.util.stream.*;

// ─────────────────────────────────────────────
//  Bogie Entity
// ─────────────────────────────────────────────
class Bogie {
    private String bogieId;
    private String bogieName;
    private String bogieType;
    private int    capacity;

    public Bogie(String bogieId, String bogieName, String bogieType, int capacity) {
        this.bogieId   = bogieId;
        this.bogieName = bogieName;
        this.bogieType = bogieType;
        this.capacity  = capacity;
    }

    public String getBogieId()   { return bogieId;   }
    public String getBogieName() { return bogieName; }
    public String getBogieType() { return bogieType; }
    public int    getCapacity()  { return capacity;  }

    @Override
    public String toString() {
        return String.format("Bogie{id='%s', name='%s', type='%s', capacity=%d}",
                bogieId, bogieName, bogieType, capacity);
    }
}

// ─────────────────────────────────────────────
//  Main Class – UC10 : Aggregate Seating
//              Capacities Using Stream Reduce
// ─────────────────────────────────────────────
public class TrainConsistManagementApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Bogie> bogieList = new ArrayList<>();

        // ── Step 1 : User enters the number of bogies ──────────────────────
        System.out.print("\nEnter number of bogies to add : ");
        int n = Integer.parseInt(sc.nextLine().trim());

        // ── Step 2 : User enters each bogie's details ──────────────────────
        System.out.println("\nEnter bogie details (Id | Name | Type | Capacity) :");
        System.out.println("------------------------------------------------------");

        for (int i = 1; i <= n; i++) {
            System.out.println("\n  Bogie #" + i);

            System.out.print("    Bogie ID       : ");
            String id = sc.nextLine().trim();

            System.out.print("    Bogie Name     : ");
            String name = sc.nextLine().trim();

            System.out.print("    Bogie Type     : ");
            String type = sc.nextLine().trim();

            System.out.print("    Bogie Capacity : ");
            int capacity = Integer.parseInt(sc.nextLine().trim());

            bogieList.add(new Bogie(id, name, type, capacity));
        }

        // ── Step 3 : Display the flat list entered by user ─────────────────
        System.out.println("\n===========================================");
        System.out.println("[ Bogies Entered ]");
        bogieList.forEach(b -> System.out.println("  " + b));

        // ── Step 4 : Convert list → Stream → map() → reduce() ─────────────
        int totalCapacity = bogieList.stream()
                .map(b -> b.getCapacity())
                .reduce(0, Integer::sum);

        // ── Step 5 : Display total seating capacity ────────────────────────
        System.out.println("\n===========================================");
        System.out.println("[ Total Seating Capacity ]");
        System.out.println("-------------------------------------------");
        System.out.println("  Total Seats : " + totalCapacity);
        System.out.println("===========================================");

        sc.close();
    }
}
