package edu.ncsu.csc216.garage.model.service_garage;

/**
 * Custom exception class to be thrown when Vehicle attempts to occupy a
 * ServiceBay with a Vehicle already occupying it.
 * 
 * @author Nick Garner
 *
 */
public class BayOccupiedException extends Exception {

	/** Serial identifier */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a BayOccupiedException with default message
	 */
	public BayOccupiedException() {
		this("Bay is occupied.");
	}

	/**
	 * Constructs a BayOccupiedException with custom message
	 * 
	 * @param message The message to set for the exception
	 */
	public BayOccupiedException(String message) {
		super(message);
	}
}