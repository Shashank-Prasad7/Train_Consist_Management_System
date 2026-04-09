import java.util.*;
import java.util.function.*;
import java.util.stream.*;

// ─────────────────────────────────────────────
//  GoodsBogie Entity
// ─────────────────────────────────────────────
class GoodsBogie {
    private String bogieId;
    private String bogieType;   // Cylindrical | Open | Box
    private String cargo;       // Petroleum | Coal | Grain | etc.

    public GoodsBogie(String bogieId, String bogieType, String cargo) {
        this.bogieId   = bogieId;
        this.bogieType = bogieType;
        this.cargo     = cargo;
    }

    public String getBogieId()   { return bogieId;   }
    public String getBogieType() { return bogieType; }
    public String getCargo()     { return cargo;     }

    @Override
    public String toString() {
        return String.format(
                "GoodsBogie{id='%s', type='%s', cargo='%s'}",
                bogieId, bogieType, cargo
        );
    }
}

// ─────────────────────────────────────────────
//  Safety Rules – Functional Interface (Predicate)
// ─────────────────────────────────────────────
class BogieRules {

    // Rule : Cylindrical bogie must carry only Petroleum
    //        All other bogie types are unrestricted
    public static final Predicate<GoodsBogie> CARGO_SAFETY_RULE =
            bogie -> {
                if (bogie.getBogieType().equalsIgnoreCase("Cylindrical")) {
                    return bogie.getCargo().equalsIgnoreCase("Petroleum");
                }
                return true;  // Non-cylindrical bogies pass unconditionally
            };
}

// ─────────────────────────────────────────────
//  Main Class – UC12 : Safety Validation via
//               Lambda & Functional Interfaces
// ─────────────────────────────────────────────
public class TrainConsistManagementApp {

    // ── Core validation method ─────────────────────────────────────────────
    public static boolean isTrainSafe(List<GoodsBogie> bogieList) {
        return bogieList.stream()
                .allMatch(BogieRules.CARGO_SAFETY_RULE);
    }

    // ── main ──────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<GoodsBogie> bogieList = new ArrayList<>();

        System.out.println("===========================================");
        System.out.println("  UC12 : Bogie Safety Validation            ");
        System.out.println("         via Lambda & Functional Interface   ");
        System.out.println("===========================================");
        System.out.println("  Safety Rule:");
        System.out.println("    Cylindrical bogie -> Petroleum ONLY");
        System.out.println("    Open / Box bogie  -> Any cargo allowed");
        System.out.println("===========================================");

        // ── Step 1 : User enters number of bogies ─────────────────────────
        System.out.print("\nEnter number of goods bogies : ");
        int n = Integer.parseInt(sc.nextLine().trim());

        // ── Step 2 : User enters each bogie's details ─────────────────────
        System.out.println("\nEnter bogie details :");
        System.out.println("-------------------------------------------");

        for (int i = 1; i <= n; i++) {
            System.out.println("\n  GoodsBogie #" + i);

            System.out.print("    Bogie ID    : ");
            String id = sc.nextLine().trim();

            System.out.print("    Bogie Type  (Cylindrical / Open / Box) : ");
            String type = sc.nextLine().trim();

            System.out.print("    Cargo       (Petroleum / Coal / Grain / etc.) : ");
            String cargo = sc.nextLine().trim();

            bogieList.add(new GoodsBogie(id, type, cargo));
        }

        // ── Step 3 : Display entered bogies ───────────────────────────────
        System.out.println("\n===========================================");
        System.out.println("[ Goods Bogie List ]");
        bogieList.forEach(b -> System.out.println("  " + b));

        // ── Step 4 : Apply allMatch() with lambda safety rule ──────────────
        System.out.println("\n[ Safety Validation ]");
        System.out.println("-------------------------------------------");

        bogieList.forEach(b -> {
            boolean pass = BogieRules.CARGO_SAFETY_RULE.test(b);
            System.out.printf("  %-10s | %-15s | %-12s | %s%n",
                    b.getBogieId(),
                    b.getBogieType(),
                    b.getCargo(),
                    pass ? "PASS" : "FAIL  <- Cylindrical must carry Petroleum"
            );
        });

        // ── Step 5 : Display overall result ───────────────────────────────
        boolean trainSafe = isTrainSafe(bogieList);

        System.out.println("-------------------------------------------");
        if (trainSafe) {
            System.out.println("  RESULT : Train is SAFETY COMPLIANT.");
        } else {
            System.out.println("  RESULT : Train is NOT SAFE.");
            System.out.println("           Fix cylindrical bogie cargo before dispatch.");
        }

        System.out.println("\n===========================================");
        System.out.println("  Validation complete. Program continues...");
        System.out.println("===========================================");

        sc.close();
    }
}