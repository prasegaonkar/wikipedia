package wikipedia;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//crawl/ingest
//tokenize
//filter stop words
//stemming - find root word
//indexing- map of {root word tokens : content indices (pointers), in order of rank}
//querying - results in decreasing order of relevance

public class App {
	public static void main(String[] args) {

		Request request = new Request();
		request.setWikipediaContent(
				"Zebras are several species of African equids (horse family) united by their distinctive black and white stripes. Their stripes come in different patterns, unique to each individual. They are generally social animals that live in small harems to large herds. Unlike their closest relatives, horses and donkeys, zebras have never been truly domesticated. There are three species of zebras: the plains zebra, the Grévy's zebra and the mountain zebra. The plains zebra and the mountain zebra belong to the subgenus Hippotigris, but Grévy's zebra is the sole species of subgenus Dolichohippus. The latter resembles an ass, to which it is closely related, while the former two are more horse-like. All three belong to the genus Equus, along with other living equids. The unique stripes of zebras make them one of the animals most familiar to people. They occur in a variety of habitats, such as grasslands, savannas, woodlands, thorny scrublands, mountains, and coastal hills. However, various anthropogenic factors have had a severe impact on zebra populations, in particular hunting for skins and habitat destruction. Grévy's zebra and the mountain zebra are endangered. While plains zebras are much more plentiful, one subspecies - the Quagga - became extinct in the late 19th century. Though there is currently a plan, called the Quagga Project, that aims to breed zebras that are phenotypically similar to the Quagga, in a process called breeding back.");
		request.setQuestions(Arrays.asList("Which Zebras are endangered?", "What is the aim of the Quagga Project?",
				"Which animals are some of their closest relatives?", "Which are the three species of zebras?",
				"Which subgenus do the plains zebra and the mountain zebra belong to?"));
		request.setJumbledAnswers(
				"subgenus Hippotigris; the plains zebra, the Grévy's zebra and the mountain zebra;horses and donkeys;aims to breed zebras that are phenotypically similar to the quagga; Grévy's zebra and the mountain zebra");

		new App().run(request);
	}

	private static final List<String> STOP_WORDS = Arrays.asList("what", "when", "how", "where", "which", "a", "an",
			"the", "they", "their", "it", "is", "are", "of", "by", "from", "and", "in", "at", "but", "on", "to", "have",
			"has");

	private static final Map<String, String> ROOT_WORDS = new HashMap<>();

	static {
		ROOT_WORDS.put("zebras", "zebra");
	}

	public Response run(Request request) {

		final List<String> questions = request.getQuestions();

		final List<String> bestMatchedContentListing = questions.stream()
				.map(question -> findBestMatchingContent(request.getWikipediaContentAsList(), question))
				.collect(Collectors.toList());

		final List<String> sortedAnswers = bestMatchedContentListing.stream()
				.map(content -> findMatchingAnswerFromJumbledList(content, request.getJumbledAnswersAsList()))
				.map(String::trim).collect(Collectors.toList());

		System.out.println();
		for (int i = 0; i < questions.size(); i++) {
			System.out.println(questions.get(i));
			System.out.println(sortedAnswers.get(i));
			System.out.println("......................");
		}

		Response response = new Response();
		response.setAnswers(sortedAnswers);
		return response;
	}

	private String findMatchingAnswerFromJumbledList(String content, List<String> jumbledAnswersAsList) {
		Predicate<String> predicate = ja -> content.matches("(?i).*" + ja + ".*");
		return jumbledAnswersAsList.stream().filter(predicate).findFirst().get();
	}

	private String findBestMatchingContent(List<String> wikipediaContentAsList, String question) {
		Set<String> tokens = Stream.of(question.replaceAll("\\p{Punct}", "").split("\\s"))
				.filter(this::filterNonRelevantWords).map(word -> {
					String rootWord = ROOT_WORDS.get(word.toLowerCase());
					if (rootWord == null) {
						return word;
					}
					return rootWord;
				}).collect(Collectors.toSet());

		String bestMatchedContent = null;
		int maxMatchedTokens = 0;
		for (String content : wikipediaContentAsList) {
			int numOfMatchedTokens = getNumberOfMatchedTokens(tokens, content);
			if (numOfMatchedTokens > maxMatchedTokens) {
				maxMatchedTokens = numOfMatchedTokens;
				bestMatchedContent = content;
			} else if (numOfMatchedTokens == maxMatchedTokens) {
				bestMatchedContent = Stream.of(bestMatchedContent, content).collect(Collectors.joining("."));
			}
		}
		System.out.println("Tokens: " + tokens);
		System.out.println("Best Matched Content: " + bestMatchedContent);
		System.out.println("Confidence level: " + maxMatchedTokens);
		System.out.println("Against the question: " + question);
		System.out.println("*****************");
		return bestMatchedContent;
	}

	private int getNumberOfMatchedTokens(Set<String> tokens, String content) {
		AtomicInteger count = new AtomicInteger(0);
		tokens.forEach(token -> {
			if (content.matches("(?i).*" + token + ".*")) {
				count.getAndIncrement();
			}
		});
		return count.get();
	}

	private boolean filterNonRelevantWords(String str) {
		return (false == STOP_WORDS.contains(str.toLowerCase()));
	}

}
