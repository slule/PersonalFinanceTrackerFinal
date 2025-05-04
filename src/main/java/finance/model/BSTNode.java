package finance.model;

public class BSTNode {
	Expense expense;
	BSTNode left;
	BSTNode right;

	public BSTNode(Expense expense) {
		this.expense = expense;
		this.left = null;
		this.right = null;
	}
}
