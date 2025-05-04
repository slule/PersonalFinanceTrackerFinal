package finance.persistance;

import finance.model.Expense;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PersistenceManager {
	private static final String DATA_FILE = "expenses.json";
	private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

	public static void saveExpenses(List<Expense> expenses) throws IOException {
		mapper.writeValue(new File(DATA_FILE), expenses);
	}

	public static List<Expense> loadExpenses() throws IOException {
		File file = new File(DATA_FILE);
		if (!file.exists()) {
			return List.of();
		}
		return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Expense.class));
	}
}