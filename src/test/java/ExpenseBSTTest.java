
import org.junit.jupiter.api.Test;

import finance.model.Expense;
import finance.model.ExpenseBST;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseBSTTest {

	@Test
	void testInsertAndInorderTraversal() {
		ExpenseBST bst = new ExpenseBST();
		bst.insert(new Expense(100.0, "Food", LocalDate.of(2023, 1, 3), ""));
		bst.insert(new Expense(50.0, "Transport", LocalDate.of(2023, 1, 1), ""));
		bst.insert(new Expense(75.0, "Entertainment", LocalDate.of(2023, 1, 2), ""));

		List<Expense> expenses = bst.inorderTraversal();
		assertEquals(3, expenses.size());
		assertTrue(expenses.get(0).getDate().isBefore(expenses.get(1).getDate()));
		assertTrue(expenses.get(1).getDate().isBefore(expenses.get(2).getDate()));
	}

	@Test
	void testFindRange() {
		ExpenseBST bst = new ExpenseBST();
		LocalDate start = LocalDate.of(2023, 1, 2);
		LocalDate end = LocalDate.of(2023, 1, 4);

		bst.insert(new Expense(100.0, "Food", LocalDate.of(2023, 1, 1), "")); // Before
		bst.insert(new Expense(200.0, "Food", LocalDate.of(2023, 1, 2), "")); // Start
		bst.insert(new Expense(300.0, "Food", LocalDate.of(2023, 1, 3), "")); // Within
		bst.insert(new Expense(400.0, "Food", LocalDate.of(2023, 1, 4), "")); // End
		bst.insert(new Expense(500.0, "Food", LocalDate.of(2023, 1, 5), "")); // After

		List<Expense> range = bst.findRange(start, end);
		assertEquals(3, range.size());
		assertEquals(200.0, range.get(0).getAmount());
		assertEquals(400.0, range.get(2).getAmount());
	}

	@Test
	void testMergeSort() {
		ExpenseBST bst = new ExpenseBST();
		bst.insert(new Expense(300.0, "Food", LocalDate.of(2023, 1, 3), ""));
		bst.insert(new Expense(100.0, "Transport", LocalDate.of(2023, 1, 1), ""));
		bst.insert(new Expense(200.0, "Entertainment", LocalDate.of(2023, 1, 2), ""));

		List<Expense> byDate = bst.mergeSortExpenses("date");
		assertEquals(100.0, byDate.get(0).getAmount());

		List<Expense> byAmount = bst.mergeSortExpenses("amount");
		assertEquals(100.0, byAmount.get(0).getAmount());
	}
}
