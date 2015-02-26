package web.core.services.exceptions;


/**
 * Created by Chris on 6/30/14.
 */
public class RoomNotExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8326525478224262667L;

	/**
	 * 
	 */

	public RoomNotExistException(Throwable cause) {
		super(cause);
	}

	public RoomNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoomNotExistException(String message) {
		super(message);
	}

	public RoomNotExistException() {
	}
}