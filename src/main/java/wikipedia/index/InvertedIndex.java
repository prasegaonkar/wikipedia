package wikipedia.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wikipedia.functions.Tokenization;
import wikipedia.search.SearchResult;

public class InvertedIndex {
	private Map<String, List<IndexedValue>> index = new HashMap<>();

	public void index(String content, Tokenization tokenization) {
		final List<String> contentTokens = tokenization.apply(content);
		contentTokens.forEach(token -> {
			final String t = token.toLowerCase();
			final IndexedValue value = new IndexedValue(t, content);
			index.computeIfAbsent(t, x -> new ArrayList<>()).add(value);
		});
	}

	public SearchResult search(String searchString, Tokenization tokenization) {
		final List<String> searchTokens = tokenization.apply(searchString);

		final SearchResult result = new SearchResult(searchString);

		searchTokens.forEach(token -> {
			final List<IndexedValue> list = index.computeIfAbsent(token.toLowerCase(), x -> new ArrayList<>());
			list.forEach(result::collect);
		});

		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		index.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())).forEach(e -> builder
				.append(e.getKey() + "=>" + e.getValue().size() + "=>" + e.getValue()).append(System.lineSeparator()));
		return builder.toString();
	}
}
