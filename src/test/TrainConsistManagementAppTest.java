import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Test Class – UC9 : Group Bogies by Category
// ─────────────────────────────────────────────────────────────────────────────
public class TrainConsistManagementAppTest {

    // ── TC01 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC01 - Bogie object is created with correct values")
    void testBogieConstructorAndGetters() {
        Bogie bogie = new Bogie("B001", "Sleeper Coach", "Passenger");

        assertEquals("B001",          bogie.getBogieId());
        assertEquals("Sleeper Coach", bogie.getBogieName());
        assertEquals("Passenger",     bogie.getBogieType());
    }

    // ── TC02 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC02 - Bogie toString() returns correct formatted string")
    void testBogieToString() {
        Bogie bogie = new Bogie("B001", "Sleeper Coach", "Passenger");

        String expected = "Bogie{id='B001', name='Sleeper Coach', type='Passenger'}";
        assertEquals(expected, bogie.toString());
    }

    // ── TC03 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC03 - Bogies are grouped into the correct number of categories")
    void testGroupingByTypeReturnsCorrectGroupCount() {
        List<Bogie> bogieList = Arrays.asList(
            new Bogie("B001", "Sleeper",   "Passenger"),
            new Bogie("B002", "Pantry Car", "Food"),
            new Bogie("B003", "AC Coach",  "Passenger")
        );

        Map<String, List<Bogie>> grouped = bogieList.stream()
                .collect(Collectors.groupingBy(Bogie::getBogieType));

        assertEquals(2, grouped.size());  // Passenger + Food
    }

    // ── TC04 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC04 - Each group contains the correct bogies")
    void testGroupingByTypePutsCorrectBogiesInEachGroup() {
        List<Bogie> bogieList = Arrays.asList(
            new Bogie("B001", "Sleeper",    "Passenger"),
            new Bogie("B002", "Pantry Car", "Food"),
            new Bogie("B003", "AC Coach",   "Passenger")
        );

        Map<String, List<Bogie>> grouped = bogieList.stream()
                .collect(Collectors.groupingBy(Bogie::getBogieType));

        assertEquals(2, grouped.get("Passenger").size());
        assertEquals(1, grouped.get("Food").size());
    }

    // ── TC05 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC05 - Count per category is correct")
    void testCountByTypeIsCorrect() {
        List<Bogie> bogieList = Arrays.asList(
            new Bogie("B001", "Sleeper",    "Passenger"),
            new Bogie("B002", "Pantry Car", "Food"),
            new Bogie("B003", "AC Coach",   "Passenger"),
            new Bogie("B004", "Engine",     "Locomotive")
        );

        Map<String, Long> countByType = bogieList.stream()
                .collect(Collectors.groupingBy(
                        Bogie::getBogieType,
                        Collectors.counting()
                ));

        assertEquals(2L, countByType.get("Passenger"));
        assertEquals(1L, countByType.get("Food"));
        assertEquals(1L, countByType.get("Locomotive"));
    }

    // ── TC06 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC06 - Empty bogie list produces an empty grouped map")
    void testEmptyListProducesEmptyGroup() {
        List<Bogie> bogieList = new ArrayList<>();

        Map<String, List<Bogie>> grouped = bogieList.stream()
                .collect(Collectors.groupingBy(Bogie::getBogieType));

        assertTrue(grouped.isEmpty());
    }

    // ── TC07 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC07 - All bogies of the same type fall into one group")
    void testAllSameTypeFallsIntoOneGroup() {
        List<Bogie> bogieList = Arrays.asList(
            new Bogie("B001", "Coach A", "Passenger"),
            new Bogie("B002", "Coach B", "Passenger"),
            new Bogie("B003", "Coach C", "Passenger")
        );

        Map<String, List<Bogie>> grouped = bogieList.stream()
                .collect(Collectors.groupingBy(Bogie::getBogieType));

        assertEquals(1, grouped.size());
        assertEquals(3, grouped.get("Passenger").size());
    }
}
