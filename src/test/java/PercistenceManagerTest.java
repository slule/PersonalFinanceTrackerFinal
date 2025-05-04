
import finance.model.Expense;
import finance.persistance.PersistenceManager;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PersistenceManagerTest {

	@Test
	void testSaveAndLoadExpenses() throws IOException {
		// Setup test data
		List<Expense> expenses = List.of(new Expense(100.0, "Food", LocalDate.of(2023, 1, 1), "Lunch"),
				new Expense(50.0, "Transport", LocalDate.of(2023, 1, 2), "Bus"));

		// Save and load
		PersistenceManager.saveExpenses(expenses);
		List<Expense> loaded = PersistenceManager.loadExpenses();

		// Verify
		assertEquals(2, loaded.size());
		assertEquals("food", loaded.get(0).getCategory());
		assertEquals(50.0, loaded.get(1).getAmount());

		// Cleanup
		Files.deleteIfExists(Path.of("expenses.json"));
	}

	@Test
	void testLoadNonExistentFile() throws IOException {
		Files.deleteIfExists(Path.of("expenses.json"));
		List<Expense> loaded = PersistenceManager.loadExpenses();
		assertTrue(loaded.isEmpty());
	}
}
