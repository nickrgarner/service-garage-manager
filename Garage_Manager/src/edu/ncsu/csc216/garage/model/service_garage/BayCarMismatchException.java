package edu.ncsu.csc216.garage.model.service_garage;

/**
 * Custom exception class to throw when RegularCar attempts to occupy a
 * HybridElectricBay
 * 
 * @author Nick Garner
 *
 */
public class BayCarMismatchException extends Exception {

	/** Serial identifier */
	private static final long serialVersionUID = 1L;

	/**
	 *  Constructs BayCarMismatchException with default message
	 */
	public BayCarMismatchException() {
		this("Vehicle type does not match Bay type.");
	}

	/**
	 * Constructs BayCarMismatchException with custom message
	 * @param message The message to set for the exception
	 */
	public BayCarMismatchException(String message) {
		super(message);
	}
}