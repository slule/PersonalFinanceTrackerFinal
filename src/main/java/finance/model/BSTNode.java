package finance.model;
/**
 * This represents a node in the binary search tree used to store expenses.
 * Each node contains:
 *  An {@link Expense} object as its data
 *  References to left and right child nodes
 * 
 * This class is used internally by @link ExpenseBST to maintain a sorted
 * collection of expenses by date.
 * 
 * @author Shalom Lule
 * 5/4/2025
 * CIS153 final project
 */

public class BSTNode {
	//opens the expense data stored in tghis node 
	Expense expense;
	BSTNode left;
	BSTNode right;

	/**
     * Constructs a new BST node with the given expense.
     * 
     * @param expense The expense to store in this node (cannot be null)
     * @throws IllegalArgumentException if expense is null
     */

	public BSTNode(Expense expense) {
		this.expense = expense;
		this.left = null;
		this.right = null;
	}
}
