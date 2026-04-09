import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Test Class – UC12 : Bogie Safety Validation
//               via Lambda and Functional Interfaces
// ─────────────────────────────────────────────────────────────────────────────
public class TrainConsistManagementAppTest {

    // ── TC01 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC01 - Train is safe when all cylindrical bogies carry Petroleum")
    void testSafety_AllBogiesValid() {
        List<GoodsBogie> bogieList = Arrays.asList(
                new GoodsBogie("G001", "Cylindrical", "Petroleum"),
                new GoodsBogie("G002", "Cylindrical", "Petroleum"),
                new GoodsBogie("G003", "Open",        "Coal")
        );

        // Cylindrical bogies carry Petroleum → rule passes
        // Open bogie carries Coal → unrestricted → passes
        assertTrue(TrainConsistManagementApp.isTrainSafe(bogieList));
    }

    // ── TC02 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC02 - Cylindrical bogie carrying Coal fails safety validation")
    void testSafety_CylindricalWithInvalidCargo() {
        List<GoodsBogie> bogieList = Arrays.asList(
                new GoodsBogie("G001", "Cylindrical", "Coal"),      // VIOLATION
                new GoodsBogie("G002", "Open",        "Grain")
        );

        // Cylindrical bogie carries Coal → rule fails → allMatch() returns false
        assertFalse(TrainConsistManagementApp.isTrainSafe(bogieList));
    }

    // ── TC03 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC03 - Non-cylindrical bogies can carry any cargo without failing")
    void testSafety_NonCylindricalBogiesAllowed() {
        List<GoodsBogie> bogieList = Arrays.asList(
                new GoodsBogie("G001", "Open", "Coal"),
                new GoodsBogie("G002", "Box",  "Grain"),
                new GoodsBogie("G003", "Open", "Fertilizer")
        );

        // No cylindrical bogies → all pass unconditionally
        assertTrue(TrainConsistManagementApp.isTrainSafe(bogieList));
    }

    // ── TC04 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC04 - One cylindrical violation makes entire train unsafe")
    void testSafety_MixedBogiesWithViolation() {
        List<GoodsBogie> bogieList = Arrays.asList(
                new GoodsBogie("G001", "Cylindrical", "Petroleum"),  // valid
                new GoodsBogie("G002", "Open",        "Grain"),      // valid
                new GoodsBogie("G003", "Cylindrical", "Coal"),       // VIOLATION
                new GoodsBogie("G004", "Box",         "Fertilizer")  // valid
        );

        // One cylindrical bogie carries Coal → allMatch() short-circuits → false
        assertFalse(TrainConsistManagementApp.isTrainSafe(bogieList));
    }

    // ── TC05 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC05 - Empty bogie list returns true (no violations possible)")
    void testSafety_EmptyBogieList() {
        List<GoodsBogie> bogieList = new ArrayList<>();

        // allMatch() on empty stream always returns true (vacuous truth)
        assertTrue(TrainConsistManagementApp.isTrainSafe(bogieList));
    }
}