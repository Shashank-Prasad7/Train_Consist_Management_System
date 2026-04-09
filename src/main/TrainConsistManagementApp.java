import java.util.*;
import java.util.regex.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Main Class – UC11 : Validate Train ID and Cargo Code using Regex
// ─────────────────────────────────────────────────────────────────────────────
public class TrainConsistManagementApp {

    // ── Regex Patterns ────────────────────────────────────────────────────────
    //  Train ID   : TRN- followed by exactly 4 digits       e.g. TRN-1234
    //  Cargo Code : 3 uppercase letters - 2 uppercase letters  e.g. PET-AB
    static final String TRAIN_ID_REGEX   = "TRN-\\d{4}";
    static final String CARGO_CODE_REGEX = "[A-Z]{3}-[A-Z]{2}";

    // ── Validation Helpers ────────────────────────────────────────────────────
    public static boolean isValidTrainId(String input) {
        Pattern pattern = Pattern.compile(TRAIN_ID_REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isValidCargoCode(String input) {
        Pattern pattern = Pattern.compile(CARGO_CODE_REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    // ── Display Helper ────────────────────────────────────────────────────────
    private static void printResult(String label, String input, boolean valid) {
        String status = valid ? "VALID" : "INVALID";
        System.out.printf("  %-14s : %-20s ->  %s%n", label, input, status);
    }

    // ── main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("  UC11 : Train ID & Cargo Code Validation  ");
        System.out.println("         via Regular Expressions            ");
        System.out.println("===========================================");
        System.out.println("  Format Rules:");
        System.out.println("    Train ID   -> TRN-XXXX  (exactly 4 digits)");
        System.out.println("    Cargo Code -> AAA-BB    (uppercase letters only)");
        System.out.println("===========================================");

        boolean continueValidation = true;

        while (continueValidation) {

            // ── Step 1 : User enters Train ID ─────────────────────────────
            System.out.print("\nEnter Train ID    (e.g. TRN-1234) : ");
            String trainId = sc.nextLine().trim();

            // ── Step 2 : Compile Pattern and run Matcher ───────────────────
            boolean trainValid = isValidTrainId(trainId);

            // ── Step 3 : User enters Cargo Code ───────────────────────────
            System.out.print("Enter Cargo Code  (e.g. PET-AB)   : ");
            String cargoCode = sc.nextLine().trim();

            // ── Step 4 : Compile Pattern and run Matcher ───────────────────
            boolean cargoValid = isValidCargoCode(cargoCode);

            // ── Step 5 : Display validation results ───────────────────────
            System.out.println("\n-------------------------------------------");
            System.out.println("[ Validation Results ]");
            printResult("Train ID",   trainId,   trainValid);
            printResult("Cargo Code", cargoCode, cargoValid);
            System.out.println("-------------------------------------------");

            if (trainValid && cargoValid) {
                System.out.println("  Both inputs accepted. Processing...");
            } else {
                if (!trainValid)
                    System.out.println("  ERROR : Train ID must match format  -> TRN-XXXX (4 digits)");
                if (!cargoValid)
                    System.out.println("  ERROR : Cargo Code must match format -> AAA-BB (uppercase only)");
            }

            // ── Step 6 : Continue or exit ──────────────────────────────────
            System.out.print("\nValidate another entry? (yes/no) : ");
            String choice = sc.nextLine().trim().toLowerCase();
            continueValidation = choice.equals("yes") || choice.equals("y");
        }

        System.out.println("\n===========================================");
        System.out.println("  Validation complete. Program continues...");
        System.out.println("===========================================");

        sc.close();
    }
}