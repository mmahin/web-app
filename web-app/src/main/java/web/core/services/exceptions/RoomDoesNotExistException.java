package web.core.services.exceptions;

public class RoomDoesNotExistException extends RuntimeException {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1310603808926809793L;

	public RoomDoesNotExistException(Throwable cause) {
			super(cause);
		}

		public RoomDoesNotExistException(String message, Throwable cause) {
			super(message, cause);
		}

		public RoomDoesNotExistException(String message) {
			super(message);
		}

		public RoomDoesNotExistException() {
		}
}
