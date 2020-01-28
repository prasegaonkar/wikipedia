package wikipedia;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Request {
	private String wikipediaContent;
	private List<String> questions;
	private String jumbledAnswers;

	public String getWikipediaContent() {
		return wikipediaContent;
	}

	public void setWikipediaContent(String wikipediaContent) {
		this.wikipediaContent = wikipediaContent;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public String getJumbledAnswers() {
		return jumbledAnswers;
	}

	public List<String> getJumbledAnswersAsList() {
		if (jumbledAnswers != null) {
			return Stream.of(jumbledAnswers.split(";")).collect(Collectors.toList());
		}
		return null;
	}

	public void setJumbledAnswers(String jumbledAnswers) {
		this.jumbledAnswers = jumbledAnswers;
	}

	public List<String> getWikipediaContentAsList() {
		if (wikipediaContent != null) {
			return Stream.of(wikipediaContent.split("\\.")).collect(Collectors.toList());
		}
		return null;
	}

}
