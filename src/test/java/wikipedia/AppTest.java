package wikipedia;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AppTest {
	private Request request = null;
	private Response response = null;

	@Before
	public void setup() throws IOException, URISyntaxException {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final URL input = classLoader.getResource("input.txt");
		final List<String> lines = Files.readAllLines(Paths.get(input.toURI()));
		this.request = new Request();
		request.setWikipediaContent(lines.get(0));
		request.setQuestions(lines.subList(1, 6));
		request.setJumbledAnswers(lines.get(6));

		final URL output = classLoader.getResource("output.txt");
		this.response = new Response();
		response.setAnswers(Files.readAllLines(Paths.get(output.toURI())));
	}

	@Test
	public void test_run() {
		Response actual = new App().run(request);
		assertThat(actual.getAnswers()).containsExactlyElementsOf(response.getAnswers());
	}
}
