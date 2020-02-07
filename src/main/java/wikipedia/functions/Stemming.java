package wikipedia.functions;

import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;

import wikipedia.exceptions.ConfigurationException;

public class Stemming implements Function<String, String> {
	private final Properties rootWords = new Properties();

	public Stemming() {
		try {
			rootWords.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("stemming.properties"));
		} catch (IOException e) {
			throw new ConfigurationException("Stemming initialization error", e);
		}
	}

	@Override
	public String apply(String word) {
		String rootWord = rootWords.getProperty(word.toLowerCase());
		if (rootWord == null) {
			return word;
		}
		return rootWord;
	}
}
