package wikipedia.index;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndexedValue {
	private final String token;
	private final String content;
	private final int tokenFrequency;
	private final float rank;

	public IndexedValue(String token, String content) {
		this.token = token;
		this.content = content;
		this.tokenFrequency = calculateTokenFrequency();
		this.rank = calculateRank(this.tokenFrequency);
	}

	public String getToken() {
		return token;
	}

	public String getContent() {
		return content;
	}

	public int getTokenFrequency() {
		return tokenFrequency;
	}

	public float getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return "[tokenFrequency=" + tokenFrequency + ", rank=" + rank + "content=" + content + "]";
	}

	private float calculateRank(int f) {
		return 1000 * ((float) f) / content.length();
	}

	private int calculateTokenFrequency() {
		int count = 0;
		Pattern p = Pattern.compile(token, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(content);
		while (m.find()) {
			count++;
		}
		return count;
	}
}
