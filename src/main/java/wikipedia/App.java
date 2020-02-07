package wikipedia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import wikipedia.functions.Stemming;
import wikipedia.functions.StopWordsFiltering;
import wikipedia.functions.Tokenization;
import wikipedia.index.InvertedIndex;
import wikipedia.ingest.ContentIngestion;
import wikipedia.search.Search;
import wikipedia.search.SearchResultMetrics;
import wikipedia.search.comparators.RankComparison;

public class App {
	private final ContentIngestion ingestion;
	private final Search search;

	public App() {
		final StopWordsFiltering stopWordsFiltering = new StopWordsFiltering();
		final Stemming stemming = new Stemming();
		final Tokenization tokenization = new Tokenization(stopWordsFiltering.andThen(stemming));
		final Comparator<SearchResultMetrics> comparator = new RankComparison();
		final InvertedIndex invertedIndex = new InvertedIndex();

		this.ingestion = new ContentIngestion(invertedIndex, tokenization);
		this.search = new Search(invertedIndex, comparator, tokenization);
	}

	public static void main(String[] args) throws IOException {
		final String filePath = args.length > 0 ? args[0] : "src/test/resources/input.txt";
		final List<String> lines = Files.readAllLines(Paths.get(filePath));
		final Request request = new Request();
		request.setWikipediaContent(lines.get(0));
		request.setQuestions(lines.subList(1, 6));
		request.setJumbledAnswers(lines.get(6));
		final Response response = new App().run(request);
		response.getAnswers().forEach(System.out::println);

	}

	Response run(Request request) {
		request.getWikipediaContentAsList().forEach(ingestion::accept);

		final List<String> questions = request.getQuestions();
		final List<String> answers = questions.stream().map(search::getTopResult).collect(Collectors.toList());

		final List<String> sortedAnswers = answers.stream()
				.map(answer -> findInJumbledList(answer, request.getJumbledAnswersAsList())).map(String::trim)
				.collect(Collectors.toList());

		Response r = new Response();
		r.setAnswers(sortedAnswers);
		return r;
	}

	private String findInJumbledList(String answer, List<String> list) {
		return list.stream().filter(ja -> answer.matches("(?i).*" + ja + ".*")).findFirst()
				.orElseThrow(() -> new RuntimeException("No matching answer"));
	}
}