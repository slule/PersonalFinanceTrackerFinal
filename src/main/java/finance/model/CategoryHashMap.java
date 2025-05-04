package finance.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CategoryHashMap {
	private Map<String, List<Expense>> map;

	public CategoryHashMap() {
		map = new HashMap<>();
	}

	public void addExpense(Expense expense) {
		String category = expense.getCategory().toLowerCase();
		map.putIfAbsent(category, new ArrayList<>());
		map.get(category).add(expense);
	}

	public double getCategoryTotal(String category) {
		return map.getOrDefault(category.toLowerCase(), new ArrayList<>()).stream().mapToDouble(Expense::getAmount)
				.sum();
	}

	public Set<String> getAllCategories() {
		return new TreeSet<>(map.keySet());
	}

	public List<Expense> getCategoryExpenses(String category) {
		return new ArrayList<>(map.getOrDefault(category.toLowerCase(), new ArrayList<>()));
	}
}