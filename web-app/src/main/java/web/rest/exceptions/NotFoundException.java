package web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Chris on 6/28/14.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2086819984118619980L;

	public NotFoundException() {
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}
}