package web.core.services.exceptions;
/**
* Created by Chris on 6/30/14.
*/
public class RoomExistsException extends RuntimeException {
/**
	 * 
	 */
	private static final long serialVersionUID = 9012615494714904860L;
public RoomExistsException() {
}
public RoomExistsException(String message) {
super(message);
}
public RoomExistsException(String message, Throwable cause) {
super(message, cause);
}
public RoomExistsException(Throwable cause) {
super(cause);
}
}