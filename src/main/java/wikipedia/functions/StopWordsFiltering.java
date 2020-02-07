package wikipedia.functions;

import java.util.function.Function;

public class StopWordsFiltering implements Function<String, String> {
	private static final String STOP_WORDS = "what|when|how|where|which|a|an|the|they|this|them|that|there|their|it|is|are|of|by|"
			+ "for|from|and|in|at|but|on|to|have|has|been|as|much|more|had|each|such|other|while";

	@Override
	public String apply(String content) {
		return content.replaceAll("(?i)\\b(" + STOP_WORDS + ")\\b", "").replaceAll("\\s+", " ")
				.replaceAll("\\p{Punct}", "").trim();
	}

}
