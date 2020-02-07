package wikipedia.search.comparators;

import java.util.Comparator;

import wikipedia.search.SearchResultMetrics;

public class RankComparison implements Comparator<SearchResultMetrics> {

	@Override
	public int compare(SearchResultMetrics o1, SearchResultMetrics o2) {
		return Float.compare(o2.getRank(), o1.getRank());
	}

}
