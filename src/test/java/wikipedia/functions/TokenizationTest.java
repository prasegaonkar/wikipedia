package wikipedia.functions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class TokenizationTest {
	private Tokenization function = null;

	@Before
	public void setup() {
		this.function = new Tokenization(new StopWordsFiltering().andThen(new Stemming()));
	}

	@Test
	public void test() {
		assertThat(function.apply("this is test")).containsExactly("test");
		assertThat(function.apply("this is not a test")).containsExactly("not", "test");
		assertThat(function.apply("nothing gets filtered out")).containsExactly("nothing", "gets", "filtered", "out");
		assertThat(function.apply("and removes   extra spaces")).containsExactly("removes", "extra", "spaces");
	}

}
