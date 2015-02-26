package web.core.services.exceptions;


/**
 * Created by Chris on 6/30/14.
 */
public class AccountUpdateFailureException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8057270364102888854L;

	/**
	 * 
	 */

	public AccountUpdateFailureException(Throwable cause) {
		super(cause);
	}

	public AccountUpdateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountUpdateFailureException(String message) {
		super(message);
	}

	public AccountUpdateFailureException() {
	}
}