package finance.model;

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

	// Annotated constructor
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

	// Getters and setters (required for property-based binding)
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

	// Rest of your existing methods...
	@Override
	public int compareTo(Expense other) {
		return this.date.compareTo(other.date);
	}

	@Override
	public String toString() {
		return String.format("$%.2f on %s for %s - %s", amount, date, category, description);
	}
}
