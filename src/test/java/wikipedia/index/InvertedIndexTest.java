package wikipedia.index;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import wikipedia.functions.Stemming;
import wikipedia.functions.StopWordsFiltering;
import wikipedia.functions.Tokenization;
import wikipedia.search.SearchResult;
import wikipedia.search.comparators.RankComparison;
import wikipedia.search.comparators.TokenCountComparison;

public class InvertedIndexTest {

	private InvertedIndex index = null;
	private Tokenization tokenization = new Tokenization(new StopWordsFiltering().andThen(new Stemming()));

	@Before
	public void setup() {
		this.index = new InvertedIndex();
	}

	@Test
	public void test() {
		index.index("ABC t2 t3", tokenization);
		SearchResult result = index.search("abc", tokenization);
		assertThat(result).isNotNull();
		assertThat(result.getResultsSortedBy(new TokenCountComparison())).containsExactly("ABC t2 t3");
	}

	@Test
	public void test_missing_token() {
		index.index("DEF t2 t3", tokenization);
		SearchResult result = index.search("abc", tokenization);
		assertThat(result).isNotNull();
		assertThat(result.getResultsSortedBy(new TokenCountComparison()).size()).isEqualTo(0);
	}

	@Test
	public void test_more_token_matches_rank_higher() {
		index.index("ABC t1 t2", tokenization);
		index.index("DEF t2 t4", tokenization);
		index.index("GHI t1 t2", tokenization);
		final TokenCountComparison c = new TokenCountComparison();
		assertThat(index.search("abc t2", tokenization).getResultsSortedBy(c)).containsExactlyInAnyOrder("ABC t1 t2",
				"GHI t1 t2", "DEF t2 t4");
		assertThat(index.search("abc t2", tokenization).getResultsSortedBy(c).get(0)).isEqualTo("ABC t1 t2");
	}

	@Test
	public void test_better_rank_results_higher() {
		index.index("ABC t1 t2", tokenization);
		index.index("ABC t1 t2 t3", tokenization);
		index.index("DEF t2 t4", tokenization);
		index.index("GHI t1 t2", tokenization);
		final RankComparison c = new RankComparison();
		assertThat(index.search("abc t2", tokenization).getResultsSortedBy(c)).containsExactlyInAnyOrder("ABC t1 t2",
				"ABC t1 t2 t3", "GHI t1 t2", "DEF t2 t4");
		assertThat(index.search("abc t2", tokenization).getResultsSortedBy(c).get(0)).isEqualTo("ABC t1 t2");
		assertThat(index.search("abc t2", tokenization).getResultsSortedBy(c).get(1)).isEqualTo("ABC t1 t2 t3");

	}

}
