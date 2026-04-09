import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Test Class – UC13 : Loop vs Stream Performance Benchmarking
// ─────────────────────────────────────────────────────────────────────────────
public class TrainConsistManagementAppTest {

    // ── Shared threshold ──────────────────────────────────────────────────────
    private static final int THRESHOLD = 60;

    // ── Shared test dataset ───────────────────────────────────────────────────
    private List<Bogie> getSampleBogies() {
        return Arrays.asList(
                new Bogie("B001", "Sleeper Coach",  "Passenger", 72),   // > 60 → included
                new Bogie("B002", "Local Coach",    "Passenger", 50),   // ≤ 60 → excluded
                new Bogie("B003", "AC Coach",       "Passenger", 64),   // > 60 → included
                new Bogie("B004", "General Coach",  "Passenger", 60),   // ≤ 60 → excluded
                new Bogie("B005", "Express Coach",  "Passenger", 90)    // > 60 → included
        );
    }

    // ── TC01 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC01 - Loop filtering correctly returns bogies with capacity > 60")
    void testLoopFilteringLogic() {
        List<Bogie> bogieList  = getSampleBogies();
        List<Bogie> result     = TrainConsistManagementApp.filterByLoop(bogieList);

        // Only B001 (72), B003 (64), B005 (90) qualify
        assertEquals(3, result.size());

        // Confirm all returned bogies actually satisfy the rule
        result.forEach(b ->
                assertTrue(b.getCapacity() > THRESHOLD,
                        "Bogie " + b.getBogieId() + " should have capacity > " + THRESHOLD)
        );

        // Confirm excluded bogies are not present
        List<String> ids = result.stream()
                .map(Bogie::getBogieId)
                .collect(Collectors.toList());
        assertFalse(ids.contains("B002"));   // capacity 50 → must be excluded
        assertFalse(ids.contains("B004"));   // capacity 60 → must be excluded
    }

    // ── TC02 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC02 - Stream filtering correctly returns bogies with capacity > 60")
    void testStreamFilteringLogic() {
        List<Bogie> bogieList = getSampleBogies();
        List<Bogie> result    = TrainConsistManagementApp.filterByStream(bogieList);

        assertEquals(3, result.size());

        result.forEach(b ->
                assertTrue(b.getCapacity() > THRESHOLD,
                        "Bogie " + b.getBogieId() + " should have capacity > " + THRESHOLD)
        );

        List<String> ids = result.stream()
                .map(Bogie::getBogieId)
                .collect(Collectors.toList());
        assertFalse(ids.contains("B002"));
        assertFalse(ids.contains("B004"));
    }

    // ── TC03 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC03 - Loop and Stream produce identical results for the same dataset")
    void testLoopAndStreamResultsMatch() {
        List<Bogie> bogieList    = getSampleBogies();
        List<Bogie> loopResult   = TrainConsistManagementApp.filterByLoop(bogieList);
        List<Bogie> streamResult = TrainConsistManagementApp.filterByStream(bogieList);

        // Same count
        assertEquals(loopResult.size(), streamResult.size());

        // Same bogie IDs in both results
        List<String> loopIds   = loopResult.stream()
                .map(Bogie::getBogieId)
                .sorted()
                .collect(Collectors.toList());
        List<String> streamIds = streamResult.stream()
                .map(Bogie::getBogieId)
                .sorted()
                .collect(Collectors.toList());
        assertEquals(loopIds, streamIds);
    }

    // ── TC04 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC04 - System.nanoTime() produces a positive elapsed time")
    void testExecutionTimeMeasurement() {
        List<Bogie> bogieList = getSampleBogies();

        long loopTime   = TrainConsistManagementApp.benchmarkLoop(bogieList);
        long streamTime = TrainConsistManagementApp.benchmarkStream(bogieList);

        // Elapsed time must always be > 0
        assertTrue(loopTime   > 0, "Loop execution time must be positive");
        assertTrue(streamTime > 0, "Stream execution time must be positive");
    }

    // ── TC05 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC05 - Large dataset is filtered correctly by both methods")
    void testLargeDatasetProcessing() {
        // Generate 10,000 bogies with alternating capacities 55 and 75
        List<Bogie> largeList = new ArrayList<>();
        for (int i = 1; i <= 10_000; i++) {
            int cap = (i % 2 == 0) ? 75 : 55;   // 5000 above 60, 5000 below
            largeList.add(new Bogie("B" + i, "Coach" + i, "Passenger", cap));
        }

        List<Bogie> loopResult   = TrainConsistManagementApp.filterByLoop(largeList);
        List<Bogie> streamResult = TrainConsistManagementApp.filterByStream(largeList);

        // Exactly 5000 bogies have capacity 75 (> 60)
        assertEquals(5000, loopResult.size());
        assertEquals(5000, streamResult.size());

        // Both methods agree
        assertEquals(loopResult.size(), streamResult.size());
    }
}