package wikipedia.functions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class StopWordsFilteringTest {
	private StopWordsFiltering function = null;

	@Before
	public void setup() {
		this.function = new StopWordsFiltering();
	}

	@Test
	public void test() {
		assertThat(function.apply("this is test")).isEqualTo("test");
		assertThat(function.apply("this is not a test")).isEqualTo("not test");
		assertThat(function.apply("nothing gets filtered out")).isEqualTo("nothing gets filtered out");
		assertThat(function.apply("and removes   extra spaces")).isEqualTo("removes extra spaces");
	}

}
