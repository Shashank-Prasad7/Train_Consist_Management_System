import java.util.*;

// ─────────────────────────────────────────────
//  Custom Runtime Exception – UC15
// ─────────────────────────────────────────────
class CargoSafetyException extends RuntimeException {

    public CargoSafetyException(String message) {
        super(message);
    }
}

// ─────────────────────────────────────────────
//  GoodsBogie Entity
// ─────────────────────────────────────────────
class GoodsBogie {
    private String bogieId;
    private String shape;        // Cylindrical | Rectangular
    private String cargo;        // assigned cargo — null if not yet assigned

    public GoodsBogie(String bogieId, String shape) {
        this.bogieId = bogieId;
        this.shape   = shape;
        this.cargo   = null;
    }

    public String getBogieId() { return bogieId; }
    public String getShape()   { return shape;   }
    public String getCargo()   { return cargo;   }

    // ── Core assignment method with try-catch-finally ──────────────────────
    public void assignCargo(String cargoType) {
        System.out.println("\n  [ASSIGN] Bogie: " + bogieId
                + " | Shape: " + shape
                + " | Cargo: " + cargoType);
        try {
            // ── Business Rule : Petroleum must NOT go into Rectangular bogie
            if (shape.equalsIgnoreCase("Rectangular")
                    && cargoType.equalsIgnoreCase("Petroleum")) {
                throw new CargoSafetyException(
                        "Unsafe assignment: Petroleum cannot be loaded into a "
                                + "Rectangular bogie [" + bogieId + "]"
                );
            }

            // Safe — assign cargo
            this.cargo = cargoType;
            System.out.println("  [SUCCESS] Cargo '" + cargoType
                    + "' assigned to bogie " + bogieId + ".");

        } catch (CargoSafetyException e) {
            // Unsafe assignment caught — cargo remains null
            System.out.println("  [ERROR]   " + e.getMessage());

        } finally {
            // Executes always — safe or unsafe path
            System.out.println("  [LOG]     Cargo assignment validation complete "
                    + "for bogie " + bogieId + ".");
        }
    }

    @Override
    public String toString() {
        String assignedCargo = (cargo != null) ? cargo : "NOT ASSIGNED";
        return String.format(
                "GoodsBogie{id='%s', shape='%s', cargo='%s'}",
                bogieId, shape, assignedCargo
        );
    }
}

// ─────────────────────────────────────────────
//  Main Class – UC15 : Safe Cargo Assignment
//               via try-catch-finally
// ─────────────────────────────────────────────
public class TrainConsistManagementApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<GoodsBogie> bogieList = new ArrayList<>();

        System.out.println("===========================================");
        System.out.println("  UC15 : Safe Cargo Assignment              ");
        System.out.println("         via try-catch-finally              ");
        System.out.println("===========================================");
        System.out.println("  Safety Rule:");
        System.out.println("    Petroleum -> Cylindrical only");
        System.out.println("    Rectangular bogies -> any cargo EXCEPT Petroleum");
        System.out.println("===========================================");

        // ── Step 1 : User enters number of bogies ─────────────────────────
        System.out.print("\nEnter number of goods bogies : ");
        int n = Integer.parseInt(sc.nextLine().trim());

        // ── Step 2 : User creates each bogie ──────────────────────────────
        System.out.println("\nCreate bogies :");
        System.out.println("-------------------------------------------");

        for (int i = 1; i <= n; i++) {
            System.out.println("\n  Bogie #" + i);

            System.out.print("    Bogie ID : ");
            String id = sc.nextLine().trim();

            System.out.print("    Shape    (Cylindrical / Rectangular) : ");
            String shape = sc.nextLine().trim();

            bogieList.add(new GoodsBogie(id, shape));
        }

        // ── Step 3 : User assigns cargo to each bogie ─────────────────────
        System.out.println("\n===========================================");
        System.out.println("Assign cargo to each bogie :");
        System.out.println("-------------------------------------------");

        for (GoodsBogie bogie : bogieList) {
            System.out.print("\n  Cargo for bogie " + bogie.getBogieId()
                    + " [" + bogie.getShape() + "]"
                    + " (Petroleum / Coal / Grain / etc.) : ");
            String cargo = sc.nextLine().trim();

            // ── Step 4 : assignCargo() internally uses try-catch-finally ───
            bogie.assignCargo(cargo);
        }

        // ── Step 5 : Display final state of all bogies ────────────────────
        System.out.println("\n===========================================");
        System.out.println("[ Final Goods Bogie State ]");
        System.out.println("-------------------------------------------");
        bogieList.forEach(b -> System.out.println("  " + b));

        System.out.println("\n===========================================");
        System.out.println("  Program continues safely after all        ");
        System.out.println("  cargo assignments (valid and invalid).    ");
        System.out.println("===========================================");

        sc.close();
    }
}