package web.core.services.exceptions;


/**
 * Created by Chris on 6/30/14.
 */
public class AccountAndInstitotionsUpdateFailureException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7248007526348357819L;

	/**
	 * 
	 */

	public AccountAndInstitotionsUpdateFailureException(Throwable cause) {
		super(cause);
	}

	public AccountAndInstitotionsUpdateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountAndInstitotionsUpdateFailureException(String message) {
		super(message);
	}

	public AccountAndInstitotionsUpdateFailureException() {
	}
}