package web.core.services.exceptions;


/**
 * Created by Chris on 6/30/14.
 */
public class AccountImageNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6394167194434982975L;

	/**
	 * 
	 */

	public AccountImageNotFoundException(Throwable cause) {
		super(cause);
	}

	public AccountImageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountImageNotFoundException(String message) {
		super(message);
	}

	public AccountImageNotFoundException() {
	}
}