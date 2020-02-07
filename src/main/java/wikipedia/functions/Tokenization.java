package wikipedia.functions;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Tokenization implements Function<String, List<String>> {
	private final Function<String, String> function;

	public Tokenization(Function<String, String> function) {
		this.function = function;
	}

	@Override
	public List<String> apply(String content) {
		return new ArrayList<>(Stream.of(content.split("\\s+")).map(function).filter(x -> !x.isEmpty())
				.collect(toCollection(LinkedHashSet::new)));
	}
}
