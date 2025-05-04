package finance.ui;

import finance.model.Expense;
import finance.model.FinanceTracker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleInterface {
	private FinanceTracker tracker;
	private Scanner scanner;
	private DateTimeFormatter dateFormatter;

	public ConsoleInterface() {
		tracker = new FinanceTracker();
		scanner = new Scanner(System.in);
		dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}

	public void run() {
		while (true) {
			printMenu();
			String choice = scanner.nextLine();

			switch (choice) {
			case "1":
				addExpense();
				break;
			case "2":
				viewExpensesByDateRange();
				break;
			case "3":
				viewCategorySummary();
				break;
			case "4":
				viewAllExpensesSorted();
				break;
			case "5":
				viewExpensesByCategory();
				break;
			case "6":
				System.out.println("Exiting...");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void printMenu() {
		System.out.println("\nPersonal Finance Tracker");
		System.out.println("1. Add Expense");
		System.out.println("2. View Expenses by Date Range");
		System.out.println("3. View Category Summary");
		System.out.println("4. View All Expenses (Sorted)");
		System.out.println("5. View Expenses by Category");
		System.out.println("6. Exit");
		System.out.print("Enter your choice: ");
	}

	private void addExpense() {
		try {
			System.out.print("Enter amount: ");
			double amount = Double.parseDouble(scanner.nextLine());

			System.out.print("Enter category: ");
			String category = scanner.nextLine();

			System.out.print("Enter date (YYYY-MM-DD): ");
			LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormatter);

			System.out.print("Enter description (optional): ");
			String description = scanner.nextLine();

			tracker.addExpense(amount, category, date, description);
			System.out.println("Expense added successfully!");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void viewExpensesByDateRange() {
		try {
			System.out.print("Enter start date (YYYY-MM-DD): ");
			LocalDate startDate = LocalDate.parse(scanner.nextLine(), dateFormatter);

			System.out.print("Enter end date (YYYY-MM-DD): ");
			LocalDate endDate = LocalDate.parse(scanner.nextLine(), dateFormatter);

			List<Expense> expenses = tracker.getExpensesByDateRange(startDate, endDate);
			System.out.printf("\nExpenses between %s and %s:\n", startDate, endDate);
			expenses.forEach(System.out::println);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void viewCategorySummary() {
		Map<String, Double> summary = tracker.getCategorySummary();
		System.out.println("\nCategory Summary:");
		summary.forEach((category, total) -> System.out.printf("%s: $%.2f\n", category, total));
	}

	private void viewAllExpensesSorted() {
		System.out.print("Sort by (date/amount): ");
		String sortBy = scanner.nextLine().toLowerCase();

		if (!sortBy.equals("date") && !sortBy.equals("amount")) {
			System.out.println("Invalid sort option. Using 'date' by default.");
			sortBy = "date";
		}

		List<Expense> expenses = tracker.getSortedExpenses(sortBy);
		System.out.printf("\nAll Expenses (Sorted by %s):\n", sortBy);
		expenses.forEach(System.out::println);
	}

	private void viewExpensesByCategory() {
		System.out.print("Enter category: ");
		String category = scanner.nextLine();

		List<Expense> expenses = tracker.getCategoryExpenses(category);
		System.out.printf("\nExpenses for %s:\n", category);
		expenses.forEach(System.out::println);

		double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
		System.out.printf("Total: $%.2f\n", total);
	}
}
