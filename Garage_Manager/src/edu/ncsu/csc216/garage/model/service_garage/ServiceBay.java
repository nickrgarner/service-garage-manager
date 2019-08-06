package edu.ncsu.csc216.garage.model.service_garage;

import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Class defines state and behavior for ServiceBay objects to be held and
 * manipulated by Garage class. ServiceBay knows whether it is occupied and
 * knows what Vehicle it currently holds. Behavior is provided for filling and
 * emptying bays with Vehicles.
 * 
 * @author Nick Garner
 *
 */
public class ServiceBay {

	/** Tracks if bay is occupied */
	private boolean occupied;
	/** Unique identification for bay */
	private String bayID;
	/** Used to construct bayID for next bay to open */
	private static int nextNumber;
	/** Vehicle object currently in this bay */
	private Vehicle myVehicle;

//	public Vehicle getVehicle() {
//		return myVehicle;
//	}

	/**
	 * Static method to reset the first bayID to 101. Used for testing.
	 */
	public static void startBayNumberingAt101() {
		nextNumber = 1;
	}

	/**
	 * Constructs a ServiceBay with bayID based on nextNumber
	 */
	public ServiceBay() {
		this("");
	}

	/**
	 * Constructs a new ServiceBay with bayID corresponding to the given prefix and
	 * nextNumber. If no prefix given, default prefix is 1.
	 * 
	 * @param prefix Start of the bayID for this ServiceBay object.
	 */
	public ServiceBay(String prefix) {
		if (prefix == null || prefix.trim().equals("")) {
			if (nextNumber < 10) {
				bayID = "10" + nextNumber;
			} else {
				bayID = "1" + nextNumber;
			}
		} else {
			String prefixLetter = prefix.trim().substring(0, 1);
			if (nextNumber < 10) {
				bayID = prefixLetter + "0" + nextNumber;
			} else {
				bayID = prefixLetter + nextNumber;
			}
		}
		myVehicle = null;
		occupied = false;
		nextNumber++;
	}

	/**
	 * Returns the bay's ID as a String
	 * 
	 * @return Returns the bay's ID as a String
	 */
	public String getBayID() {
		return bayID;
	}

	/**
	 * Returns true if the bay is currently occupied by a Vehicle
	 * 
	 * @return True if the bay is currently occupied by a Vehicle
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * Resets myVehicle to null and occupied to false, and returns the Vehicle that
	 * was occupying this ServiceBay.
	 * 
	 * @return Returns the Vehicle that was occupying this ServiceBay.
	 */
	public Vehicle release() {
		Vehicle output = myVehicle;
		myVehicle = null;
		occupied = false;
		return output;
	}

	/**
	 * Checks if bay is occupied and updates myVehicle to param if not.
	 * 
	 * @param v Vehicle to occupy ServiceBay with.
	 * @throws BayOccupiedException    If bay is already occupied by another
	 *                                 Vehicle.
	 * @throws BayCarMismatchException If RegularCar attempts to occupy a
	 *                                 HybridElectricBay.
	 */
	public void occupy(Vehicle v) throws BayOccupiedException, BayCarMismatchException {
		if (occupied) {
			throw new BayOccupiedException("Bay is currently occupied.");
		}
		myVehicle = v;
		occupied = true;
	}

	/**
	 * Returns string representation of the ServiceBay. If empty, shows bayID and
	 * Empty. If occupied, shows bayID and owner and license of Vehicle.
	 */
	@Override
	public String toString() {
		if (!isOccupied()) {
			return bayID + ": EMPTY";
		} else {
			String licenseString = myVehicle.getLicense() + " ";
			if (licenseString.length() < 9) {
				while (licenseString.length() < 9) {
					licenseString += " ";
				}
//				for (int i = 0; i < 9 - licenseString.length(); i++) {
//					licenseString += " ";
//				}
			}
			return bayID + ": " + licenseString + myVehicle.getName();
		}
	}
}