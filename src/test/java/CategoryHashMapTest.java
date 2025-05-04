
import org.junit.jupiter.api.Test;

import finance.model.CategoryHashMap;
import finance.model.Expense;

import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class CategoryHashMapTest {

	@Test
	void testAddAndGetExpenses() {
		CategoryHashMap map = new CategoryHashMap();
		Expense foodExpense = new Expense(100.0, "Food", LocalDate.now(), "Lunch");
		Expense transportExpense = new Expense(50.0, "Transport", LocalDate.now(), "Bus");

		map.addExpense(foodExpense);
		map.addExpense(transportExpense);
		map.addExpense(new Expense(30.0, "Food", LocalDate.now(), "Coffee"));

		assertEquals(2, map.getAllCategories().size());
		assertEquals(130.0, map.getCategoryTotal("food"), 0.001);
		assertEquals(1, map.getCategoryExpenses("transport").size());
	}

	@Test
	void testCaseInsensitiveCategories() {
		CategoryHashMap map = new CategoryHashMap();
		map.addExpense(new Expense(100.0, "FOOD", LocalDate.now(), ""));
		map.addExpense(new Expense(50.0, "food", LocalDate.now(), ""));
		map.addExpense(new Expense(30.0, "Food", LocalDate.now(), ""));

		Set<String> categories = map.getAllCategories();
		assertEquals(1, categories.size());
		assertTrue(categories.contains("food"));
		assertEquals(180.0, map.getCategoryTotal("FOOD"), 0.001);
	}

	@Test
	void testEmptyCategory() {
		CategoryHashMap map = new CategoryHashMap();
		assertTrue(map.getAllCategories().isEmpty());
		assertEquals(0.0, map.getCategoryTotal("nonexistent"), 0.001);
		assertTrue(map.getCategoryExpenses("nonexistent").isEmpty());
	}
}
