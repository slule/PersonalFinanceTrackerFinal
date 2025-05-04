package finance.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseBST {
	private BSTNode root;

	public ExpenseBST() {
		root = null;
	}

	public void insert(Expense expense) {
		root = insertRec(root, expense);
	}

	private BSTNode insertRec(BSTNode node, Expense expense) {
		if (node == null) {
			return new BSTNode(expense);
		}

		if (expense.compareTo(node.expense) < 0) {
			node.left = insertRec(node.left, expense);
		} else if (expense.compareTo(node.expense) > 0) {
			node.right = insertRec(node.right, expense);
		}

		return node;
	}

	public boolean remove(Expense expense) {
		if (expense == null) {
			return false;
		}
		boolean[] removed = new boolean[1]; // Array to hold removal status
		root = removeRec(root, expense, removed);
		return removed[0];
	}

	private BSTNode removeRec(BSTNode node, Expense expense, boolean[] removed) {
		if (node == null) {
			return null;
		}

		int cmp = expense.compareTo(node.expense);
		if (cmp < 0) {
			node.left = removeRec(node.left, expense, removed);
		} else if (cmp > 0) {
			node.right = removeRec(node.right, expense, removed);
		} else {
			// Node to delete found
			removed[0] = true;

			// Node with only one child or no child
			if (node.left == null) {
				return node.right;
			} else if (node.right == null) {
				return node.left;
			}

			// Node with two children: Get the inorder successor (smallest in right subtree)
			node.expense = minValue(node.right);

			// Delete the inorder successor
			node.right = removeRec(node.right, node.expense, new boolean[1]);
		}
		return node;
	}

	// Helper method to find minimum value node (used in deletion)
	private Expense minValue(BSTNode node) {
		Expense min = node.expense;
		while (node.left != null) {
			min = node.left.expense;
			node = node.left;
		}
		return min;
	}

	public List<Expense> findRange(LocalDate startDate, LocalDate endDate) {
		List<Expense> expenses = new ArrayList<>();
		findRangeRec(root, startDate, endDate, expenses);
		return expenses;
	}

	private void findRangeRec(BSTNode node, LocalDate startDate, LocalDate endDate, List<Expense> expenses) {
		if (node == null)
			return;

		if (node.expense.getDate().isAfter(startDate)) {
			findRangeRec(node.left, startDate, endDate, expenses);
		}

		if (!node.expense.getDate().isBefore(startDate) && !node.expense.getDate().isAfter(endDate)) {
			expenses.add(node.expense);
		}

		if (node.expense.getDate().isBefore(endDate)) {
			findRangeRec(node.right, startDate, endDate, expenses);
		}
	}

	public List<Expense> inorderTraversal() {
		List<Expense> expenses = new ArrayList<>();
		inorderRec(root, expenses);
		return expenses;
	}

	private void inorderRec(BSTNode node, List<Expense> expenses) {
		if (node != null) {
			inorderRec(node.left, expenses);
			expenses.add(node.expense);
			inorderRec(node.right, expenses);
		}
	}

	public List<Expense> mergeSortExpenses(String sortBy) {
		List<Expense> expenses = inorderTraversal();
		return mergeSort(expenses, sortBy);
	}

	private List<Expense> mergeSort(List<Expense> expenses, String sortBy) {
		if (expenses.size() <= 1) {
			return expenses;
		}

		int mid = expenses.size() / 2;
		List<Expense> left = mergeSort(expenses.subList(0, mid), sortBy);
		List<Expense> right = mergeSort(expenses.subList(mid, expenses.size()), sortBy);

		return merge(left, right, sortBy);
	}

	private List<Expense> merge(List<Expense> left, List<Expense> right, String sortBy) {
		List<Expense> merged = new ArrayList<>();
		int i = 0, j = 0;

		while (i < left.size() && j < right.size()) {
			if (sortBy.equals("date")) {
				if (left.get(i).getDate().isBefore(right.get(j).getDate())
						|| left.get(i).getDate().isEqual(right.get(j).getDate())) {
					merged.add(left.get(i++));
				} else {
					merged.add(right.get(j++));
				}
			} else { // sort by amount
				if (left.get(i).getAmount() <= right.get(j).getAmount()) {
					merged.add(left.get(i++));
				} else {
					merged.add(right.get(j++));
				}
			}
		}

		while (i < left.size()) {
			merged.add(left.get(i++));
		}

		while (j < right.size()) {
			merged.add(right.get(j++));
		}

		return merged;
	}
}