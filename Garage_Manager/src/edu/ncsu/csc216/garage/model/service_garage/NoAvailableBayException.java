package edu.ncsu.csc216.garage.model.service_garage;

/**
 * Custom exception class is thrown when Vehicle attempts to pick a ServiceBay
 * to occupy and there are no empty bays for that Vehicle type.
 * 
 * @author Nick Garner
 *
 */
public class NoAvailableBayException extends Exception {

	/** Serial identifier */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a NoAvailableBayException with default message
	 */
	public NoAvailableBayException() {
		this("No bay is available.");
	}

	/**
	 * Constructs a NoAvailableBayException with custom message
	 * 
	 * @param message The message to set for the exception
	 */
	public NoAvailableBayException(String message) {
		super(message);
	}
}