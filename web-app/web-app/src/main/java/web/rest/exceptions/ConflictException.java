package web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Chris on 6/30/14.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2130977697263761962L;

	public ConflictException() {
	}

	public ConflictException(Throwable cause) {
		super(cause);
	}
}
