import java.util.*;
import java.util.stream.*;

// ─────────────────────────────────────────────
//  Bogie Entity
// ─────────────────────────────────────────────
class Bogie {
    private String bogieId;
    private String bogieName;
    private String bogieType;

    public Bogie(String bogieId, String bogieName, String bogieType) {
        this.bogieId   = bogieId;
        this.bogieName = bogieName;
        this.bogieType = bogieType;
    }

    public String getBogieId()   { return bogieId;   }
    public String getBogieName() { return bogieName; }
    public String getBogieType() { return bogieType; }

    @Override
    public String toString() {
        return String.format("Bogie{id='%s', name='%s', type='%s'}",
                bogieId, bogieName, bogieType);
    }
}

// ─────────────────────────────────────────────
//  Main Class – UC9 : Group Bogies by Category
//              (User-Defined Input)
// ─────────────────────────────────────────────
public class TrainConsistManagementApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Bogie> bogieList = new ArrayList<>();


        // ── Step 1 : User enters the number of bogies ──────────────────────
        System.out.print("\nEnter number of bogies to add : ");
        int n = Integer.parseInt(sc.nextLine().trim());

        // ── Step 2 : User enters each bogie's details ──────────────────────
        System.out.println("\nEnter bogie details (Id | Name | Type) :");
        System.out.println("-------------------------------------------");

        for (int i = 1; i <= n; i++) {
            System.out.println("\n  Bogie #" + i);

            System.out.print("    Bogie ID   : ");
            String id = sc.nextLine().trim();

            System.out.print("    Bogie Name : ");
            String name = sc.nextLine().trim();

            System.out.print("    Bogie Type : ");
            String type = sc.nextLine().trim();

            bogieList.add(new Bogie(id, name, type));
        }

        // ── Step 3 : Display the flat list entered by user ─────────────────
        System.out.println("\n===========================================");
        System.out.println("[ Bogies Entered ]");
        bogieList.forEach(b -> System.out.println("  " + b));

        // ── Step 4 : Convert list → Stream → groupingBy collector ──────────
        Map<String, List<Bogie>> groupedBogies =
                bogieList.stream()
                        .collect(
                                Collectors.groupingBy(Bogie::getBogieType)
                        );

        // ── Step 5 : Display grouped result ────────────────────────────────
        System.out.println("\n===========================================");
        System.out.println("[ Bogies Grouped by Type ]");
        System.out.println("-------------------------------------------");

        groupedBogies.forEach((type, bogies) -> {
            System.out.println("\n  Category : " + type
                    + "  (count = " + bogies.size() + ")");
            bogies.forEach(b -> System.out.println("    -> " + b));
        });

        // ── Step 6 : Count summary per group ───────────────────────────────
        System.out.println("\n-------------------------------------------");
        System.out.println("[ Group Count Summary ]");

        Map<String, Long> countByType =
                bogieList.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Bogie::getBogieType,
                                        Collectors.counting()
                                )
                        );

        countByType.forEach((type, count) ->
                System.out.printf("  %-20s : %d bogie(s)%n", type, count));



        sc.close();
    }
}