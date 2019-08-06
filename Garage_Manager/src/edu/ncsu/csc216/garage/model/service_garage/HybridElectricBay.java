package edu.ncsu.csc216.garage.model.service_garage;

import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Class defines unique constructor and occupy behavior for HybridElectricBay to
 * distinguish it from a regular ServiceBay. HybridElectricBays have a bayID
 * prefaced by "E" and throw a BayCarMismatchException when a RegularCar
 * attempts to occupy it.
 * 
 * @author Nick Garner
 *
 */
public class HybridElectricBay extends ServiceBay {

	/**
	 * Constructs a new HybridElectricBay using super(prefix) to generate a bayID
	 * starting with "E"
	 */
	public HybridElectricBay() {
		super("E");
	}

	/**
	 * Occupies the HybridElectricBay with the given Vehicle object.
	 * 
	 * @param v The Vehicle to attempt to occupy this HybridElectricBay with.
	 * @throws BayOccupiedException    If bay is already occupied.
	 * @throws BayCarMismatchException If regular car attempts to fill
	 *                                 HybridElectricBay.
	 */
	@Override
	public void occupy(Vehicle v) throws BayOccupiedException, BayCarMismatchException {
		if (v.toString().substring(0, 1).equals("R")) {
			throw new BayCarMismatchException("Cannot service Regular Cars in a Hybrid Electric Bay.");
		} else {
			super.occupy(v);
		}
	}

}
