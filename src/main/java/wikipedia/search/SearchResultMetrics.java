package wikipedia.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResultMetrics {
	private int matchingTokenCount = 0;
	private float rank = 1f;
	private List<String> matchingTokens = new ArrayList<>();

	public int getMatchingTokensCount() {
		return matchingTokenCount;
	}

	public void addCount(int count) {
		this.matchingTokenCount += count;
	}

	public float getRank() {
		return rank;
	}

	public void updateRank(float rank) {
		this.rank *= rank;
	}

	public void updateMatchingToken(String matchingToken) {
		matchingTokens.add(matchingToken);
	}

	@Override
	public String toString() {
		return "[matchingTokenCount=" + matchingTokenCount + ", rank=" + rank + ", matchingTokens=" + matchingTokens
				+ "]";
	}

}
