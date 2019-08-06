package edu.ncsu.csc216.garage.model.vehicle;

import edu.ncsu.csc216.garage.model.service_garage.BayCarMismatchException;
import edu.ncsu.csc216.garage.model.service_garage.BayOccupiedException;
import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**
 * Class defines behavior specific to RegularCar objects to be distinguished
 * from HybridElectricCar objects. RegularCars can only fill regular
 * ServiceBays.
 * 
 * @author Nick Garner
 *
 */
public class RegularCar extends Vehicle {

	/**
	 * Constructs a RegularCar Object from a String containing both license and
	 * owner name and an int representing service tier.
	 * 
	 * @param info   String containing license and owner name, separated by a space
	 * @param status Int representing service tier of the HEV
	 * @throws BadVehicleInformationException If any of the parameters passed to the
	 *                                        setter methods are illegal values.
	 */
	public RegularCar(String info, int status) throws BadVehicleInformationException {
		super(info, status);
	}

	/**
	 * Creates a RegularCar object from the given license, owner, and status
	 * parameters
	 * 
	 * @param license The license plate of the RegularCar object
	 * @param owner   The owner name of the RegularCar
	 * @param status  Int representing the RegularCar's service tier
	 * @throws BadVehicleInformationException If an exception is thrown from any of
	 *                                        the super.setField methods
	 */
	public RegularCar(String license, String owner, int status) throws BadVehicleInformationException {
		//this(license.trim() + " " + owner.trim(), status);
		super(license, owner, status);
	}

	/**
	 * Traverses the list of ServiceBays starting at the top and attempts to find
	 * one to occupy.
	 * 
	 * @param g The Garage list of bays to search.
	 * @throws NoAvailableBayException If no valid bay can be found to occupy.
	 */
	public void pickServiceBay(Garage g) throws NoAvailableBayException {
		boolean pickSuccess = false;
		for (int i = 0; i < g.getSize(); i++) {
			try {
				g.getBayAt(i).occupy(this);
				// if successful, exit loop
				pickSuccess = true;
				i = g.getSize();
			} catch (BayOccupiedException e) {
				// do nothing
			} catch (BayCarMismatchException e) {
				// You've hit HEV bays, exit loop
				i = g.getSize();
			}
		}
		if (!pickSuccess) {
			throw new NoAvailableBayException();
		}
	}

	/**
	 * Returns a String representation of this RegularCar's instance data
	 * 
	 * @return String representation of this RegularCar's instance data
	 */
	@Override
	public String toString() {
		return "R " + super.toString();
	}
}
