package web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Chris on 6/30/14.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2130977697263761962L;

	public NotAcceptableException() {
	}

	public NotAcceptableException(Throwable cause) {
		super(cause);
	}
}
