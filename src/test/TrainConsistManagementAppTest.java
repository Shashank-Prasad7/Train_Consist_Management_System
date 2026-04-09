import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.*;

// ─────────────────────────────────────────────────────────────────────────────
//  Test Class – UC11 : Validate Train ID and Cargo Code using Regex
// ─────────────────────────────────────────────────────────────────────────────
public class TrainConsistManagementAppTest {

    // ── TC01 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC01 - Valid Train ID TRN-1234 is accepted")
    void testRegex_ValidTrainID() {
        assertTrue(TrainConsistManagementApp.isValidTrainId("TRN-1234"));
    }

    // ── TC02 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC02 - Incorrectly formatted Train IDs are rejected")
    void testRegex_InvalidTrainIDFormat() {
        assertFalse(TrainConsistManagementApp.isValidTrainId("TRAIN12"));
        assertFalse(TrainConsistManagementApp.isValidTrainId("TRN12A"));
        assertFalse(TrainConsistManagementApp.isValidTrainId("1234-TRN"));
        assertFalse(TrainConsistManagementApp.isValidTrainId("trn-1234")); // lowercase
        assertFalse(TrainConsistManagementApp.isValidTrainId("TRN1234"));  // missing hyphen
    }

    // ── TC03 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC03 - Valid Cargo Code PET-AB is accepted")
    void testRegex_ValidCargoCode() {
        assertTrue(TrainConsistManagementApp.isValidCargoCode("PET-AB"));
    }

    // ── TC04 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC04 - Incorrectly formatted Cargo Codes are rejected")
    void testRegex_InvalidCargoCodeFormat() {
        assertFalse(TrainConsistManagementApp.isValidCargoCode("PET-ab"));  // lowercase
        assertFalse(TrainConsistManagementApp.isValidCargoCode("PET123"));  // digits
        assertFalse(TrainConsistManagementApp.isValidCargoCode("AB-PET"));  // wrong order
        assertFalse(TrainConsistManagementApp.isValidCargoCode("pet-AB"));  // lowercase prefix
    }

    // ── TC05 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC05 - Train ID with wrong digit count is rejected")
    void testRegex_TrainIDDigitLengthValidation() {
        assertFalse(TrainConsistManagementApp.isValidTrainId("TRN-123"));    // 3 digits
        assertFalse(TrainConsistManagementApp.isValidTrainId("TRN-12345"));  // 5 digits
        assertFalse(TrainConsistManagementApp.isValidTrainId("TRN-"));       // 0 digits
        assertTrue(TrainConsistManagementApp.isValidTrainId("TRN-9999"));    // exactly 4 - valid
    }

    // ── TC06 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC06 - Cargo Code with lowercase letters is rejected")
    void testRegex_CargoCodeUppercaseValidation() {
        assertFalse(TrainConsistManagementApp.isValidCargoCode("pet-AB"));
        assertFalse(TrainConsistManagementApp.isValidCargoCode("PET-ab"));
        assertFalse(TrainConsistManagementApp.isValidCargoCode("Pet-Ab"));
        assertTrue(TrainConsistManagementApp.isValidCargoCode("OIL-XY"));   // valid uppercase
    }

    // ── TC07 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC07 - Empty input returns invalid for both Train ID and Cargo Code")
    void testRegex_EmptyInputHandling() {
        assertFalse(TrainConsistManagementApp.isValidTrainId(""));
        assertFalse(TrainConsistManagementApp.isValidCargoCode(""));
    }

    // ── TC08 ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("TC08 - Extra characters beyond the pattern are rejected (exact match)")
    void testRegex_ExactPatternMatch() {
        // Train ID with extra characters
        assertFalse(TrainConsistManagementApp.isValidTrainId("TRN-1234X"));
        assertFalse(TrainConsistManagementApp.isValidTrainId("XTRN-1234"));
        assertFalse(TrainConsistManagementApp.isValidTrainId(" TRN-1234"));
        assertFalse(TrainConsistManagementApp.isValidTrainId("TRN-1234 "));

        // Cargo Code with extra characters
        assertFalse(TrainConsistManagementApp.isValidCargoCode("PET-ABC"));   // 3 letters suffix
        assertFalse(TrainConsistManagementApp.isValidCargoCode("XPET-AB"));
        assertFalse(TrainConsistManagementApp.isValidCargoCode("PET-AB "));
        assertFalse(TrainConsistManagementApp.isValidCargoCode(" PET-AB"));
    }
}