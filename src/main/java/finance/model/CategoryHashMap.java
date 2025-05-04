package finance.model;
/**
 * This is a specialized HashMap implementation which facilitates the categorizing and managing of expenses by their category.
 * This class provides efficient O(1) lookups for expense categories while maintaining
 * category names. by storing all categories in lowercase
 * calculates total amount calculation per category
 * Sorted view of all categories
 * Immutable view of expenses for thread safety
 *
 * @author Shalom Lule
 * 5/4/2025
 * CIS153 final project
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CategoryHashMap {
	private Map<String, List<Expense>> map;
	/**
     * Constructs an empty CategoryHashMap with default initial capacity.
     */
	public CategoryHashMap() {
		map = new HashMap<>();
	}

	public void addExpense(Expense expense) {
		String category = expense.getCategory().toLowerCase();
		map.putIfAbsent(category, new ArrayList<>());
		map.get(category).add(expense);
	}
	
	/**
     * Calculates the total amount spent in a specific category
     * Category name comparison is case-insensitive. Returns 0.0 if the category
     * doesn't exist.
     */
	public double getCategoryTotal(String category) {
		return map.getOrDefault(category.toLowerCase(), new ArrayList<>()).stream().mapToDouble(Expense::getAmount)
				.sum();
	}
 	/**
     * Returns an alphabetically sorted set of all categories.
     * The returned set is immutable and will not reflect subsequent changes to the map.
     */
	public Set<String> getAllCategories() {
		return new TreeSet<>(map.keySet());
	}
	/**
     * Retrieves all expenses for a specific category.
     * Returns an empty list if the category doesn't exist. The returned list is
     * a defensive copy to prevent external modification.
     */
	public List<Expense> getCategoryExpenses(String category) {
		return new ArrayList<>(map.getOrDefault(category.toLowerCase(), new ArrayList<>()));
	}
}