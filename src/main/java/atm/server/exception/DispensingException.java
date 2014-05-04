package atm.server.exception;

/**
 * The Class DispensingException.
 */
public class DispensingException extends Exception{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8981706116030040794L;

	/**
	 * Instantiates a new dispensing exception.
	 *
	 * @param message the message
	 */
	public DispensingException(String message){
		super(message);
	}
}
