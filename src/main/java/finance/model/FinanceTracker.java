package finance.model;

import finance.persistance.PersistenceManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FinanceTracker {
	private ExpenseBST expenseBST;
	private CategoryHashMap categoryMap;

	public FinanceTracker() {
		expenseBST = new ExpenseBST();
		categoryMap = new CategoryHashMap();
		loadData();
	}

	private void loadData() {
		try {
			List<Expense> expenses = PersistenceManager.loadExpenses();
			expenses.forEach(expense -> {
				expenseBST.insert(expense);
				categoryMap.addExpense(expense);
			});
		} catch (IOException e) {
			System.err.println("Error loading data: " + e.getMessage());
		}
	}

	private void saveData() {
		try {
			PersistenceManager.saveExpenses(expenseBST.inorderTraversal());
		} catch (IOException e) {
			System.err.println("Error saving data: " + e.getMessage());
		}
	}

	public void addExpense(double amount, String category, LocalDate date, String description) {
		Expense expense = new Expense(amount, category, date, description);
		expenseBST.insert(expense);
		categoryMap.addExpense(expense);
		saveData();
	}

	public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
		return expenseBST.findRange(startDate, endDate);
	}

	public Map<String, Double> getCategorySummary() {
		return categoryMap.getAllCategories().stream()
				.collect(Collectors.toMap(category -> category, categoryMap::getCategoryTotal));
	}

	public List<Expense> getSortedExpenses(String sortBy) {
		return expenseBST.mergeSortExpenses(sortBy);
	}

	public List<Expense> getCategoryExpenses(String category) {
		return categoryMap.getCategoryExpenses(category);
	}

	public boolean removeExpense(Expense expense) {
		boolean removed = expenseBST.remove(expense);
		if (removed) {
			categoryMap.getCategoryExpenses(expense.getCategory()).remove(expense);
			saveData();
		}
		return removed;
	}

	public boolean updateExpense(Expense oldExpense, Expense newExpense) {
		if (removeExpense(oldExpense)) {
			addExpense(newExpense.getAmount(), newExpense.getCategory(), newExpense.getDate(),
					newExpense.getDescription());
			return true;
		}
		return false;
	}
}
