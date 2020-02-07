package wikipedia.search;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import wikipedia.index.IndexedValue;

public class SearchResult {
	private final String searchQuery;
	private final Map<String, SearchResultMetrics> results = new HashMap<>();

	public SearchResult(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public List<String> getResultsSortedBy(Comparator<SearchResultMetrics> c) {
		return results.entrySet().stream().sorted(Entry.comparingByValue(c)).map(Entry::getKey).collect(toList());
	}

	public void collect(IndexedValue s) {
		String content = s.getContent();
		results.computeIfAbsent(content, k -> new SearchResultMetrics());
		SearchResultMetrics m = results.get(content);
		m.addCount(s.getTokenFrequency());
		m.updateRank(s.getRank());
		m.updateMatchingToken(s.getToken());
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		results.forEach((k, v) -> builder.append(v + "=>" + k).append(System.lineSeparator()));
		return "[\nsearchQuery=" + searchQuery + "\n" + builder.toString() + "]";
	}
}
