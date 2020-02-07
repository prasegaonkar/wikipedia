package wikipedia.exceptions;

public class ConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConfigurationException(String msg, Exception e) {
		super(msg, e);
	}

}
