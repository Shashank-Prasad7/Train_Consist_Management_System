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
        return String.format(
                "Bogie{id='%s', name='%s', type='%s', capacity=%d}",
                bogieId, bogieName, bogieType, capacity
        );
    }
}

// ─────────────────────────────────────────────
//  Main Class – UC13 : Loop vs Stream
//               Performance Benchmarking
// ─────────────────────────────────────────────
public class TrainConsistManagementApp {

    private static final int CAPACITY_THRESHOLD = 60;

    // ── Loop-Based Filtering ───────────────────────────────────────────────
    public static List<Bogie> filterByLoop(List<Bogie> bogieList) {
        List<Bogie> result = new ArrayList<>();
        for (Bogie b : bogieList) {
            if (b.getCapacity() > CAPACITY_THRESHOLD) {
                result.add(b);
            }
        }
        return result;
    }

    // ── Stream-Based Filtering ─────────────────────────────────────────────
    public static List<Bogie> filterByStream(List<Bogie> bogieList) {
        return bogieList.stream()
                .filter(b -> b.getCapacity() > CAPACITY_THRESHOLD)
                .collect(Collectors.toList());
    }

    // ── Benchmark Runner ───────────────────────────────────────────────────
    public static long benchmarkLoop(List<Bogie> bogieList) {
        long start  = System.nanoTime();                    // start time
        filterByLoop(bogieList);
        long end    = System.nanoTime();                    // end time
        return end - start;                                 // elapsed ns
    }

    public static long benchmarkStream(List<Bogie> bogieList) {
        long start  = System.nanoTime();                    // start time
        filterByStream(bogieList);
        long end    = System.nanoTime();                    // end time
        return end - start;                                 // elapsed ns
    }

    // ── main ──────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("  UC13 : Loop vs Stream Performance         ");
        System.out.println("         Benchmarking with System.nanoTime()");
        System.out.println("===========================================");
        System.out.println("  Filter Rule : capacity > " + CAPACITY_THRESHOLD);
        System.out.println("===========================================");

        // ── Step 1 : User enters number of bogies ─────────────────────────
        System.out.print("\nEnter number of bogies : ");
        int n = Integer.parseInt(sc.nextLine().trim());

        List<Bogie> bogieList = new ArrayList<>();

        // ── Step 2 : User enters each bogie's details ─────────────────────
        System.out.println("\nEnter bogie details :");
        System.out.println("-------------------------------------------");

        for (int i = 1; i <= n; i++) {
            System.out.println("\n  Bogie #" + i);

            System.out.print("    Bogie ID     : ");
            String id = sc.nextLine().trim();

            System.out.print("    Bogie Name   : ");
            String name = sc.nextLine().trim();

            System.out.print("    Bogie Type   : ");
            String type = sc.nextLine().trim();

            System.out.print("    Capacity     : ");
            int cap = Integer.parseInt(sc.nextLine().trim());

            bogieList.add(new Bogie(id, name, type, cap));
        }

        // ── Step 3 : Display entered bogies ───────────────────────────────
        System.out.println("\n===========================================");
        System.out.println("[ Bogie List ]");
        bogieList.forEach(b -> System.out.println("  " + b));

        // ── Step 4 : Warm-up pass (avoids JIT skewing first run) ──────────
        filterByLoop(bogieList);
        filterByStream(bogieList);

        // ── Step 5 : Benchmark loop ────────────────────────────────────────
        long loopTime         = benchmarkLoop(bogieList);
        List<Bogie> loopResult = filterByLoop(bogieList);

        // ── Step 6 : Benchmark stream ──────────────────────────────────────
        long streamTime         = benchmarkStream(bogieList);
        List<Bogie> streamResult = filterByStream(bogieList);

        // ── Step 7 : Display results ───────────────────────────────────────
        System.out.println("\n===========================================");
        System.out.println("[ Filtered Bogies  (capacity > " + CAPACITY_THRESHOLD + ") ]");
        System.out.println("-------------------------------------------");
        loopResult.forEach(b -> System.out.println("  " + b));

        System.out.println("\n===========================================");
        System.out.println("[ Performance Benchmark Results ]");
        System.out.println("-------------------------------------------");
        System.out.printf("  Loop-Based  Execution Time : %,d ns  (%s ms)%n",
                loopTime,
                String.format("%.4f", loopTime / 1_000_000.0));
        System.out.printf("  Stream-Based Execution Time : %,d ns  (%s ms)%n",
                streamTime,
                String.format("%.4f", streamTime / 1_000_000.0));
        System.out.println("-------------------------------------------");
        System.out.printf("  Filtered Count : %d bogie(s) (both methods agree: %b)%n",
                loopResult.size(),
                loopResult.size() == streamResult.size());

        String faster = loopTime <= streamTime ? "Loop-Based" : "Stream-Based";
        System.out.println("  Faster Method : " + faster);

        System.out.println("\n===========================================");
        System.out.println("  Benchmark complete. Program continues...");
        System.out.println("===========================================");

        sc.close();
    }
}