package finance.model;
/*
 * This represents the financial expense with amount, category, date, and description.
 * It implements @link Comparable to enable sorting by date. The class includes
 * validation for:
 * Positive amount values
 * Non-empty categories
 * Valid dates (not null and not in the future)
 * 
 * @author Shalom Lule
 * 5/4/2025
 * CIS153 final project
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Expense implements Comparable<Expense> {
	private double amount;
	private String category;
	private LocalDate date;
	private String description;

	// No-arg constructor
	public Expense() {
	}

	// Annotated constructor for JSON deserialization
	// This constructor is used by Jackson to create an instance of Expense from JSON data.
	@JsonCreator
	public Expense(@JsonProperty("amount") double amount, @JsonProperty("category") String category,
			@JsonProperty("date") LocalDate date, @JsonProperty("description") String description) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		if (category == null || category.trim().isEmpty()) {
			throw new IllegalArgumentException("Category cannot be empty");
		}
		if (date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		if (date.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Date cannot be in the future");
		}

		this.amount = amount;
		this.category = category.toLowerCase();
		this.date = date;
		this.description = description == null ? "" : description;
	}

	// Getters and setters for each field
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category.toLowerCase();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? "" : description;
	}

	@Override
	public int compareTo(Expense other) {
		return this.date.compareTo(other.date);
	}

	@Override
	public String toString() {
		return String.format("$%.2f on %s for %s - %s", amount, date, category, description);
	}
}
