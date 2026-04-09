import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Test Class – UC15 : Safe Cargo Assignment via try-catch-finally
// ─────────────────────────────────────────────────────────────────────────────
public class TrainConsistManagementAppTest {

    // ── TC01 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC01 - Cylindrical bogie with Petroleum is assigned safely")
    void testCargo_SafeAssignment() {
        GoodsBogie bogie = new GoodsBogie("G001", "Cylindrical");

        // Safe combination — no exception expected
        assertDoesNotThrow(() -> bogie.assignCargo("Petroleum"));

        // Cargo must be stored after safe assignment
        assertEquals("Petroleum", bogie.getCargo());
    }

    // ── TC02 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC02 - Rectangular bogie assigned Petroleum raises CargoSafetyException internally")
    void testCargo_UnsafeAssignmentHandled() {
        GoodsBogie bogie = new GoodsBogie("G002", "Rectangular");

        // assignCargo() catches the exception internally — so no exception escapes
        assertDoesNotThrow(() -> bogie.assignCargo("Petroleum"));

        // But cargo must NOT have been stored
        assertNull(bogie.getCargo(),
                "Cargo should remain null after unsafe assignment");
    }

    // ── TC03 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC03 - Cargo is not assigned to rectangular bogie after failure")
    void testCargo_CargoNotAssignedAfterFailure() {
        GoodsBogie bogie = new GoodsBogie("G003", "Rectangular");

        bogie.assignCargo("Petroleum");   // triggers CargoSafetyException internally

        // Unsafe cargo must never be stored
        assertNull(bogie.getCargo());
        assertNotEquals("Petroleum", bogie.getCargo());
    }

    // ── TC04 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC04 - Program continues after exception — multiple assignments processed")
    void testCargo_ProgramContinuesAfterException() {
        GoodsBogie cylindrical  = new GoodsBogie("G004", "Cylindrical");
        GoodsBogie rectangular  = new GoodsBogie("G005", "Rectangular");
        GoodsBogie anotherBogie = new GoodsBogie("G006", "Cylindrical");

        // None of these should terminate the program
        assertDoesNotThrow(() -> {
            cylindrical.assignCargo("Petroleum");   // safe
            rectangular.assignCargo("Petroleum");   // unsafe but caught
            anotherBogie.assignCargo("Coal");        // safe
        });

        // Verify outcome of each assignment
        assertEquals("Petroleum", cylindrical.getCargo());
        assertNull(rectangular.getCargo());           // unsafe — not stored
        assertEquals("Coal",      anotherBogie.getCargo());
    }

    // ── TC05 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC05 - finally block executes for both safe and unsafe assignments")
    void testCargo_FinallyBlockExecution() {
        // Redirect System.out to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        GoodsBogie safe   = new GoodsBogie("G007", "Cylindrical");
        GoodsBogie unsafe = new GoodsBogie("G008", "Rectangular");

        safe.assignCargo("Petroleum");     // safe path   → finally must run
        unsafe.assignCargo("Petroleum");   // unsafe path → finally must still run

        // Restore System.out
        System.setOut(originalOut);

        String output = outContent.toString();

        // finally block log message must appear twice (once per bogie)
        long finallyCount = output.lines()
                .filter(line -> line.contains(
                        "Cargo assignment validation complete"))
                .count();

        assertEquals(2, finallyCount,
                "finally block should execute for both safe and unsafe assignments");
    }
}