package web.core.services.exceptions;


/**
 * Created by Chris on 6/30/14.
 */
public class UnothorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5131055513536726782L;

	/**
	 * 
	 */

	public UnothorizedException(Throwable cause) {
		super(cause);
	}

	public UnothorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnothorizedException(String message) {
		super(message);
	}

	public UnothorizedException() {
	}
}