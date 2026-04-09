import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Test Class – UC14 : Custom Exception for Invalid Passenger Bogie Capacity
// ─────────────────────────────────────────────────────────────────────────────
public class TrainConsistManagementAppTest {

    // ── TC01 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC01 - Valid capacity bogie is created successfully without exception")
    void testException_ValidCapacityCreation() {
        assertDoesNotThrow(() -> {
            PassengerBogie bogie = new PassengerBogie("P001", "Sleeper", 72);
            assertNotNull(bogie);
        });
    }

    // ── TC02 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC02 - Negative capacity throws InvalidCapacityException")
    void testException_NegativeCapacityThrowsException() {
        assertThrows(InvalidCapacityException.class, () -> {
            new PassengerBogie("P002", "AC Coach", -10);
        });
    }

    // ── TC03 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC03 - Zero capacity throws InvalidCapacityException")
    void testException_ZeroCapacityThrowsException() {
        assertThrows(InvalidCapacityException.class, () -> {
            new PassengerBogie("P003", "General", 0);
        });
    }

    // ── TC04 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC04 - Exception message equals 'Capacity must be greater than zero'")
    void testException_ExceptionMessageValidation() {
        InvalidCapacityException ex = assertThrows(
                InvalidCapacityException.class, () -> {
                    new PassengerBogie("P004", "Sleeper", -5);
                }
        );
        assertEquals("Capacity must be greater than zero", ex.getMessage());
    }

    // ── TC05 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC05 - Valid bogie stores correct type and capacity after creation")
    void testException_ObjectIntegrityAfterCreation() throws InvalidCapacityException {
        PassengerBogie bogie = new PassengerBogie("P005", "AC Coach", 64);

        assertEquals("P005",     bogie.getBogieId());
        assertEquals("AC Coach", bogie.getBogieType());
        assertEquals(64,         bogie.getCapacity());
    }

    // ── TC06 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC06 - Multiple valid bogies can be created without exception")
    void testException_MultipleValidBogiesCreation() {
        assertDoesNotThrow(() -> {
            PassengerBogie b1 = new PassengerBogie("P006", "Sleeper",  72);
            PassengerBogie b2 = new PassengerBogie("P007", "AC Coach", 64);
            PassengerBogie b3 = new PassengerBogie("P008", "General",  90);

            assertNotNull(b1);
            assertNotNull(b2);
            assertNotNull(b3);

            assertEquals(72, b1.getCapacity());
            assertEquals(64, b2.getCapacity());
            assertEquals(90, b3.getCapacity());
        });
    }
}