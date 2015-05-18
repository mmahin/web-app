package web.core.services.exceptions;

public class InstitutionsEmptyException extends RuntimeException {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -2066970322004710067L;

	/**
	 * 
	 */

	public InstitutionsEmptyException(Throwable cause) {
			super(cause);
		}

		public InstitutionsEmptyException(String message, Throwable cause) {
			super(message, cause);
		}

		public InstitutionsEmptyException(String message) {
			super(message);
		}

		public InstitutionsEmptyException() {
		}
}
