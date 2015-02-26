package web.core.services.exceptions;

/**
 * Created by Chris on 6/30/14.
 */
public class AccountExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1691578477426492833L;

	public AccountExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountExistsException(String message) {
		super(message);
	}

	public AccountExistsException() {
		super();
	}
}