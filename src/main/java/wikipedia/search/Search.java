package wikipedia.search;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import wikipedia.exceptions.NoSearchResults;
import wikipedia.functions.Tokenization;
import wikipedia.index.InvertedIndex;

public class Search implements Function<String, List<String>> {
	private final InvertedIndex index;
	private final Comparator<SearchResultMetrics> comparator;
	private final Tokenization tokenization;

	public Search(InvertedIndex index, Comparator<SearchResultMetrics> comparator, Tokenization tokenization) {
		this.index = index;
		this.comparator = comparator;
		this.tokenization = tokenization;
	}

	@Override
	public List<String> apply(String searchString) {
		SearchResult result = index.search(searchString, tokenization);
		return result.getResultsSortedBy(comparator);
	}

	public String getTopResult(String searchString) {
		List<String> results = apply(searchString);
		return results.stream().findFirst().orElseThrow(NoSearchResults::new);
	}

}
