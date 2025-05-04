package finance.ui;

import finance.model.Expense;
import finance.model.FinanceTracker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class GUIInterface extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FinanceTracker tracker;
	private DateTimeFormatter dateFormatter;

	private JTabbedPane tabbedPane;

	// Add Expense Tab
	private JTextField amountField;
	private JTextField categoryField;
	private JTextField dateField;
	private JTextField descField;

	// View Expenses Tab
	private JTable expensesTable;
	private DefaultTableModel expensesTableModel;
	private JTextField startDateField;
	private JTextField endDateField;
	private JTextField categoryViewField;
	private ButtonGroup sortButtonGroup;

	// Summary Tab
	private JTable summaryTable;
	private DefaultTableModel summaryTableModel;

	public GUIInterface() {
		tracker = new FinanceTracker();
		dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		initializeUI();
	}

	private void initializeUI() {
		setTitle("Personal Finance Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		tabbedPane = new JTabbedPane();
		createAddExpenseTab();
		createViewExpensesTab();
		createSummaryTab();

		add(tabbedPane);
	}

	private void createAddExpenseTab() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Amount
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel("Amount:"), gbc);

		gbc.gridx = 1;
		amountField = new JTextField(15);
		panel.add(amountField, gbc);

		// Category
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(new JLabel("Category:"), gbc);

		gbc.gridx = 1;
		categoryField = new JTextField(15);
		panel.add(categoryField, gbc);

		// Date
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);

		gbc.gridx = 1;
		dateField = new JTextField(15);
		panel.add(dateField, gbc);

		// Description
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(new JLabel("Description:"), gbc);

		gbc.gridx = 1;
		descField = new JTextField(15);
		panel.add(descField, gbc);

		// Add Button
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.CENTER;
		JButton addButton = new JButton("Add Expense");
		addButton.addActionListener(e -> addExpense());
		panel.add(addButton, gbc);

		tabbedPane.addTab("Add Expense", panel);
	}

	private void createViewExpensesTab() {
		JPanel panel = new JPanel(new BorderLayout());

		// Top panel with controls
		JPanel controlsPanel = new JPanel(new GridLayout(3, 1, 5, 5));

		// Date Range
		JPanel dateRangePanel = new JPanel();
		dateRangePanel.add(new JLabel("From:"));
		startDateField = new JTextField(10);
		dateRangePanel.add(startDateField);

		dateRangePanel.add(new JLabel("To:"));
		endDateField = new JTextField(10);
		dateRangePanel.add(endDateField);

		JButton dateRangeButton = new JButton("View Date Range");
		dateRangeButton.addActionListener(e -> viewDateRange());
		dateRangePanel.add(dateRangeButton);
		controlsPanel.add(dateRangePanel);

		// Sort Options
		JPanel sortPanel = new JPanel();
		sortPanel.add(new JLabel("Sort by:"));

		sortButtonGroup = new ButtonGroup();
		JRadioButton dateRadio = new JRadioButton("Date", true);
		dateRadio.setActionCommand("date"); // this will sort the expenses based on the dates
		JRadioButton amountRadio = new JRadioButton("Amount");
		amountRadio.setActionCommand("amount"); //this will sort the expenses based on amount
		sortButtonGroup.add(dateRadio);
		sortButtonGroup.add(amountRadio);

		sortPanel.add(dateRadio);
		sortPanel.add(amountRadio);

		JButton sortButton = new JButton("View All (Sorted)");
		sortButton.addActionListener(e -> viewSorted());
		sortPanel.add(sortButton);
		controlsPanel.add(sortPanel);

		// Category
		JPanel categoryPanel = new JPanel();
		categoryPanel.add(new JLabel("Category:"));
		categoryViewField = new JTextField(15);
		categoryPanel.add(categoryViewField);

		JButton categoryButton = new JButton("View Category");
		categoryButton.addActionListener(e -> viewCategory());
		categoryPanel.add(categoryButton);
		controlsPanel.add(categoryPanel);

		panel.add(controlsPanel, BorderLayout.NORTH);

		// Results Table
		expensesTableModel = new DefaultTableModel(new Object[] { "Date", "Amount", "Category", "Description" }, 0);
		expensesTable = new JTable(expensesTableModel);
		panel.add(new JScrollPane(expensesTable), BorderLayout.CENTER);

		// Edit/Delete Buttons
		JPanel editPanel = new JPanel();
		JButton editButton = new JButton("Edit Selected");
		JButton deleteButton = new JButton("Delete Selected");

		editButton.addActionListener(e -> editSelectedExpense());
		deleteButton.addActionListener(e -> deleteSelectedExpense());

		editPanel.add(editButton);
		editPanel.add(deleteButton);
		panel.add(editPanel, BorderLayout.SOUTH);

		tabbedPane.addTab("View Expenses", panel);
	}

	private void createSummaryTab() {
		JPanel panel = new JPanel(new BorderLayout());

		// Summary Table
		summaryTableModel = new DefaultTableModel(new Object[] { "Category", "Total" }, 0);
		summaryTable = new JTable(summaryTableModel);
		panel.add(new JScrollPane(summaryTable), BorderLayout.CENTER);

		// Refresh Button
		JButton refreshButton = new JButton("Refresh Summary");
		refreshButton.addActionListener(e -> refreshSummary());
		panel.add(refreshButton, BorderLayout.SOUTH);

		tabbedPane.addTab("Category Summary", panel);
	}

	private void addExpense() {
		try {
			double amount = Double.parseDouble(amountField.getText());
			String category = categoryField.getText();
			LocalDate date = LocalDate.parse(dateField.getText(), dateFormatter);
			String description = descField.getText();

			if (category.isEmpty()) {
				throw new IllegalArgumentException("Category is required");
			}

			tracker.addExpense(amount, category, date, description);
			JOptionPane.showMessageDialog(this, "Expense added successfully!");

			// Clear fields
			amountField.setText("");
			categoryField.setText("");
			dateField.setText("");
			descField.setText("");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void viewDateRange() {
		try {
			LocalDate startDate = LocalDate.parse(startDateField.getText(), dateFormatter);
			LocalDate endDate = LocalDate.parse(endDateField.getText(), dateFormatter);

			List<Expense> expenses = tracker.getExpensesByDateRange(startDate, endDate);
			updateExpensesTable(expenses);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void viewSorted() {
		String sortBy = sortButtonGroup.getSelection().getActionCommand().toLowerCase();
		List<Expense> expenses = tracker.getSortedExpenses(sortBy);
		updateExpensesTable(expenses);
	}

	private void viewCategory() {
		String category = categoryViewField.getText();

		if (category.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a category", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<Expense> expenses = tracker.getCategoryExpenses(category);
		updateExpensesTable(expenses);
	}

	private void refreshSummary() {
		summaryTableModel.setRowCount(0);
		Map<String, Double> summary = tracker.getCategorySummary();

		summary.forEach((category, total) -> {
			summaryTableModel.addRow(new Object[] { category, String.format("$%.2f", total) });
		});
	}

	private void updateExpensesTable(List<Expense> expenses) {
		expensesTableModel.setRowCount(0);

		for (Expense exp : expenses) {
			expensesTableModel.addRow(new Object[] { exp.getDate(), String.format("$%.2f", exp.getAmount()),
					exp.getCategory(), exp.getDescription() });
		}
	}

	private void editSelectedExpense() {
		int row = expensesTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Please select an expense to edit", "No Selection",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Expense oldExpense = getExpenseFromTableRow(row);

		// Create edit dialog
		JPanel panel = new JPanel(new GridLayout(4, 2));
		JTextField amountField = new JTextField(String.valueOf(oldExpense.getAmount()));
		JTextField categoryField = new JTextField(oldExpense.getCategory());
		JTextField dateField = new JTextField(oldExpense.getDate().toString());
		JTextField descField = new JTextField(oldExpense.getDescription());

		panel.add(new JLabel("Amount:"));
		panel.add(amountField);
		panel.add(new JLabel("Category:"));
		panel.add(categoryField);
		panel.add(new JLabel("Date (YYYY-MM-DD):"));
		panel.add(dateField);
		panel.add(new JLabel("Description:"));
		panel.add(descField);

		int result = JOptionPane.showConfirmDialog(this, panel, "Edit Expense", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			try {
				Expense newExpense = new Expense(Double.parseDouble(amountField.getText()), categoryField.getText(),
						LocalDate.parse(dateField.getText()), descField.getText());
				tracker.updateExpense(oldExpense, newExpense);
				updateExpensesTable(tracker.getSortedExpenses("date"));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void deleteSelectedExpense() {
		int row = expensesTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Please select an expense to delete", "No Selection",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Expense expense = getExpenseFromTableRow(row);
		int confirm = JOptionPane.showConfirmDialog(this, "Delete expense: " + expense + "?", "Confirm Delete",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			tracker.removeExpense(expense);
			updateExpensesTable(tracker.getSortedExpenses("date"));
		}
	}

	private Expense getExpenseFromTableRow(int row) {
		// Assuming your table model has columns: Date, Amount, Category, Description
		LocalDate date = LocalDate.parse(expensesTableModel.getValueAt(row, 0).toString());
		double amount = Double.parseDouble(((String) expensesTableModel.getValueAt(row, 1)).substring(1)); // Remove $
		String category = (String) expensesTableModel.getValueAt(row, 2);
		String description = (String) expensesTableModel.getValueAt(row, 3);

		return new Expense(amount, category, date, description);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GUIInterface gui = new GUIInterface();
			gui.setVisible(true);
		});
	}
}