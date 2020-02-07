package wikipedia.ingest;

import java.util.function.Consumer;

import wikipedia.functions.Tokenization;
import wikipedia.index.InvertedIndex;

public class ContentIngestion implements Consumer<String> {
	private final InvertedIndex index;
	private final Tokenization tokenization;

	public ContentIngestion(InvertedIndex index, Tokenization tokenization) {
		this.index = index;
		this.tokenization = tokenization;
	}

	@Override
	public void accept(String content) {
		index.index(content, tokenization);
	}

}
