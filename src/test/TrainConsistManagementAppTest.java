import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Test Class – UC10 : Aggregate Seating Capacities via Stream Reduction
// ─────────────────────────────────────────────────────────────────────────────
public class TrainConsistManagementAppTest {

    // ── TC01 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC01 - reduce() correctly calculates total seating capacity")
    void testReduce_TotalSeatCalculation() {
        List<Bogie> bogieList = Arrays.asList(
                new Bogie("B001", "Sleeper Coach",  "Passenger",  72),
                new Bogie("B002", "AC Coach",       "Passenger",  64),
                new Bogie("B003", "General Coach",  "Passenger",  90)
        );

        int total = bogieList.stream()
                .mapToInt(Bogie::getCapacity)
                .reduce(0, Integer::sum);

        assertEquals(226, total);  // 72 + 64 + 90
    }

    // ── TC02 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC02 - Multiple bogies capacities are all aggregated correctly")
    void testReduce_MultipleBogiesAggregation() {
        List<Bogie> bogieList = Arrays.asList(
                new Bogie("B001", "Coach A", "Passenger",  50),
                new Bogie("B002", "Coach B", "Passenger",  60),
                new Bogie("B003", "Coach C", "Freight",    80),
                new Bogie("B004", "Coach D", "Passenger",  70)
        );

        int total = bogieList.stream()
                .mapToInt(Bogie::getCapacity)
                .reduce(0, Integer::sum);

        assertEquals(260, total);  // 50 + 60 + 80 + 70
    }

    // ── TC03 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC03 - Single bogie: total equals its own capacity")
    void testReduce_SingleBogieCapacity() {
        List<Bogie> bogieList = Arrays.asList(
                new Bogie("B001", "Express Coach", "Passenger", 100)
        );

        int total = bogieList.stream()
                .mapToInt(Bogie::getCapacity)
                .reduce(0, Integer::sum);

        assertEquals(100, total);
    }

    // ── TC04 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC04 - Empty bogie list returns 0 (identity value)")
    void testReduce_EmptyBogieList() {
        List<Bogie> bogieList = new ArrayList<>();

        int total = bogieList.stream()
                .mapToInt(Bogie::getCapacity)
                .reduce(0, Integer::sum);

        assertEquals(0, total);
    }

    // ── TC05 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC05 - map() correctly extracts capacity values from Bogie objects")
    void testReduce_CorrectCapacityExtraction() {
        Bogie b1 = new Bogie("B001", "Coach A", "Passenger", 55);
        Bogie b2 = new Bogie("B002", "Coach B", "Freight",   45);

        List<Integer> capacities = Arrays.asList(b1, b2).stream()
                .map(Bogie::getCapacity)
                .collect(Collectors.toList());

        assertEquals(55, capacities.get(0));
        assertEquals(45, capacities.get(1));
    }

    // ── TC06 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC06 - All bogies are included in the aggregation")
    void testReduce_AllBogiesIncluded() {
        List<Bogie> bogieList = Arrays.asList(
                new Bogie("B001", "Coach A", "Passenger", 30),
                new Bogie("B002", "Coach B", "Passenger", 30),
                new Bogie("B003", "Coach C", "Passenger", 30),
                new Bogie("B004", "Coach D", "Passenger", 30),
                new Bogie("B005", "Coach E", "Passenger", 30)
        );

        int total = bogieList.stream()
                .mapToInt(Bogie::getCapacity)
                .reduce(0, Integer::sum);

        // 5 bogies × 30 = 150  →  confirms every bogie was included
        assertEquals(150, total);
    }

    // ── TC07 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC07 - Original bogie list is unchanged after stream aggregation")
    void testReduce_OriginalListUnchanged() {
        List<Bogie> bogieList = new ArrayList<>(Arrays.asList(
                new Bogie("B001", "Coach A", "Passenger", 72),
                new Bogie("B002", "Coach B", "Freight",   48)
        ));

        int sizeBefore = bogieList.size();
        String firstIdBefore = bogieList.get(0).getBogieId();

        // perform aggregation
        bogieList.stream()
                .mapToInt(Bogie::getCapacity)
                .reduce(0, Integer::sum);

        // list must be identical after stream operation
        assertEquals(sizeBefore,    bogieList.size());
        assertEquals(firstIdBefore, bogieList.get(0).getBogieId());
    }
}