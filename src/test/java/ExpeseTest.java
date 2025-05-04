
import org.junit.jupiter.api.Test;

import finance.model.Expense;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void testExpenseCreation() {
        Expense expense = new Expense(100.0, "Food", LocalDate.now(), "Lunch");
        assertEquals(100.0, expense.getAmount(), 0.001);
        assertEquals("food", expense.getCategory()); // Test case conversion
        assertEquals("Lunch", expense.getDescription());
    }

    @Test
    void testExpenseComparison() {
        Expense earlier = new Expense(100.0, "Food", LocalDate.of(2023, 1, 1), "");
        Expense later = new Expense(100.0, "Food", LocalDate.of(2023, 1, 2), "");
        
        assertTrue(earlier.compareTo(later) < 0);
        assertTrue(later.compareTo(earlier) > 0);
        assertEquals(0, earlier.compareTo(earlier));
    }

    @Test
    void testNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Expense(-50.0, "Food", LocalDate.now(), "Invalid amount"),
            "Should throw exception for negative amount");
    }

    @Test
    void testEmptyCategoryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Expense(50.0, "", LocalDate.now(), "Empty category"),
            "Should throw exception for empty category");
    }

    @Test
    void testNullDateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Expense(50.0, "Food", null, "Null date"),
            "Should throw exception for null date");
    }

    @Test
    void testFutureDateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Expense(50.0, "Food", LocalDate.now().plusDays(1), "Future date"),
            "Should throw exception for future date");
    }
}

