package wikipedia.functions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class StemmingTest {
	private Stemming function = null;

	@Before
	public void setup() {
		this.function = new Stemming();
	}

	@Test
	public void test() {
		assertThat(function.apply("zebra")).isEqualTo("zebra");
		assertThat(function.apply("zebras")).isEqualTo("zebra");
		assertThat(function.apply("random")).isEqualTo("random");

	}

}
