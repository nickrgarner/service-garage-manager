package edu.ncsu.csc216.garage.model.vehicle;

/**
 * Custom exception class is thrown when attempting to create a Vehicle object
 * with illegal instance data values.
 * 
 * @author Nick Garner
 *
 */
public class BadVehicleInformationException extends Exception {

	/** Serial identifier */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a BadVehicleInformationException with default message
	 */
	public BadVehicleInformationException() {
		this("Vehicle information is not valid.");
	}

	/**
	 * Constructs a BadVehicleInformationException with custom message
	 * 
	 * @param message The message to set for the exception
	 */
	public BadVehicleInformationException(String message) {
		super(message);
	}
}