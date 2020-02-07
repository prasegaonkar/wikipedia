package wikipedia.search.comparators;

import java.util.Comparator;

import wikipedia.search.SearchResultMetrics;

public class TokenCountComparison implements Comparator<SearchResultMetrics> {

	@Override
	public int compare(SearchResultMetrics o1, SearchResultMetrics o2) {
		return Integer.compare(o2.getMatchingTokensCount(), o1.getMatchingTokensCount());
	}

}
