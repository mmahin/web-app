package web.core.services.exceptions;

public class AccountNotMatchingException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2408112042531497213L;

	public AccountNotMatchingException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNotMatchingException(Throwable cause) {
		super(cause);
	}
	
	public AccountNotMatchingException(String message) {
		super(message);
	}

	public AccountNotMatchingException() {
		super();
	}
}
