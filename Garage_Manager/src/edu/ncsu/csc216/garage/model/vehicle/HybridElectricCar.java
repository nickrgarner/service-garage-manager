package edu.ncsu.csc216.garage.model.vehicle;

import edu.ncsu.csc216.garage.model.service_garage.BayCarMismatchException;
import edu.ncsu.csc216.garage.model.service_garage.BayOccupiedException;
import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**
 * Class defines behavior specific to HybridElectricCar objects to be
 * distinguished from RegularCars. HybridElectricCars can fill both regular
 * ServiceBays and HybridElectricBays.
 * 
 * @author Nick Garner
 *
 */
public class HybridElectricCar extends Vehicle {

	/**
	 * Constructs a HybridElectricCar Object from a String containing both license
	 * and owner name and an int representing service tier.
	 * 
	 * @param info   String containing license and owner name, separated by a space
	 * @param status Int representing service tier of the HEV
	 * @throws BadVehicleInformationException If any of the parameters passed to the
	 *                                        setter methods are illegal values.
	 */
	public HybridElectricCar(String info, int status) throws BadVehicleInformationException {
		super(info, status);
	}

	/**
	 * Constructs a HybridElectricCar object from the given license, owner, and
	 * status parameters
	 * 
	 * @param license The license plate of the HybridElectricCar
	 * @param owner   The owner's name of the HybridElectricCar
	 * @param status  Int representing the service tier of the HybridElectricCar
	 * @throws BadVehicleInformationException If an exception is thrown by any of
	 *                                        the super.setField methods
	 */
	public HybridElectricCar(String license, String owner, int status) throws BadVehicleInformationException {
		//this(license.trim() + " " + owner.trim(), status);
		super (license, owner, status);
	}

	/**
	 * Traverses the list of ServiceBays starting at the bottom and attempts to find
	 * one to occupy.
	 * 
	 * @param g The Garage list of bays to search.
	 * @throws NoAvailableBayException If no valid bay can be found to occupy.
	 */
	public void pickServiceBay(Garage g) throws NoAvailableBayException {
		boolean pickSuccess = false;
		for (int i = g.getSize() - 1; i >= 0; i--) {
			try {
				g.getBayAt(i).occupy(this);
				// if successful, exit loop
				pickSuccess = true;
				i = 0;
			} catch (BayOccupiedException e) {
				// do nothing
			} catch (BayCarMismatchException e) {
				// do nothing
			}
		}
		if (!pickSuccess) {
			throw new NoAvailableBayException();
		}
	}

	/**
	 * Return's a String representation of this HybridElectricCar's instance data
	 * 
	 * @return String representation of this HybridElectricCar's instance data
	 */
	@Override
	public String toString() {
		return "E " + super.toString();
	}
}
