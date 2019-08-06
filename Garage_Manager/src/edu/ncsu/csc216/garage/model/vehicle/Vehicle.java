package edu.ncsu.csc216.garage.model.vehicle;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**
 * Abstract class defines shared state and behavior for Vehicle subclasses
 * RegularCar and HybridElectricCar. Class includes basic getters, setters, and
 * boolean flag for program filter function. Objects to be used as part of a
 * VehicleList to be manipulated by the ServiceManager class.
 * 
 * @author Nick Garner
 *
 */
public abstract class Vehicle implements Tiered {

	/** License plate of the Vehicle */
	private String license;
	/** Vehicle's owner's name */
	private String name;
	/** int representing the Vehicle's service tier */
	private int tierIndex;
	/** String array of the four service tier names */
	public static final String[] CUSTOMER_TIER = { "None", "Silver", "Gold", "Platinum" };

	/**
	 * Constructs a vehicle object from a given info String and service tier
	 * 
	 * @param info   String containing license plate and owner name of Vehicle
	 * @param status Int representing the service tier of the Vehicle
	 * @throws BadVehicleInformationException If any parameters give illegal values.
	 */
	public Vehicle(String info, int status) throws BadVehicleInformationException {
		setLicense(info.trim().substring(0, info.indexOf(" ")).trim());
		setName(info.trim().substring(info.indexOf(" ")).trim());
		setTier(status);
	}

	/**
	 * Constructs a Vehicle object from given license, owner name, and service tier
	 * 
	 * @param license The license plate number of the Vehicle
	 * @param owner   The owner of the Vehicle's name
	 * @param status  The service tier of the Vehicle
	 * @throws BadVehicleInformationException If any parameters give illegal values.
	 */
	public Vehicle(String license, String owner, int status) throws BadVehicleInformationException {
		//this (license.trim() + " " + owner.trim(), status);
		try {
			setLicense(license.trim());
			setName(owner.trim());
			setTier(status);
		} catch (NullPointerException e) {
			throw new BadVehicleInformationException();
		}
	}

	/**
	 * Vehicle traverses the list of ServiceBays and attempts to find an empty one
	 * to occupy
	 * 
	 * @param g The Garage list of ServiceBays to search.
	 * @throws NoAvailableBayException If no bay can be found to occupy.
	 */
	public abstract void pickServiceBay(Garage g) throws NoAvailableBayException;

	/**
	 * Returns true if the filter contains the license or owner name of this Vehicle
	 * or if the Vehicle owner name or license contain the filter String.
	 * 
	 * @param filter The String to search for matching Vehicles.
	 * @return Returns true if the filter contains the license or owner name of this
	 *         Vehicle or if the Vehicle owner name or license contain the filter
	 *         String.
	 */
	public boolean meetsFilter(String filter) {
		if (filter == null || filter.trim().equals("") || getName().toLowerCase().indexOf(filter.trim().toLowerCase()) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a String representation of this Vehicle's instance data, to be
	 * combined with subclass toPrint methods
	 * 
	 * @return String representation of this Vehicle's instance data
	 */
	@Override
	public String toString() {
		String tierPrint = CUSTOMER_TIER[getTier()];
		String licensePrint = getLicense();
		while (tierPrint.length() < 10) {
			tierPrint += " ";
		}
		while (licensePrint.length() < 10) {
			licensePrint += " ";
		}
		return tierPrint + licensePrint + getName();
	}

	/**
	 * Returns the Vehicle's owner name as a String
	 * 
	 * @return Returns owner name of the Vehicle
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the license plate of the Vehicle as a String
	 * 
	 * @return Returns the license plate of the Vehicle
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * Returns an int representing the service tier of this Vehicle
	 * 
	 * @return Returns an int representing the service tier of this Vehicle
	 */
	public int getTier() {
		return tierIndex;
	}

	/**
	 * Compares the service tier of this Vehicle to the given parameter Vehicle and
	 * returns an int comparison.
	 * 
	 * @param compare The Vehicle to compare tiers to this Vehicle
	 * @return Returns 0 if the Vehicles have the same service tier, less than 0 if
	 *         this Vehicle has a lower service tier, or greater than 0 if this
	 *         Vehicle has a higher service tier.
	 */
	public int compareToTier(Tiered compare) {
		if (this.getTier() == compare.getTier()) {
			return 0;
		} else {
			return Integer.valueOf(this.getTier()).compareTo(Integer.valueOf(compare.getTier()));
		}
	}

	/**
	 * Sets the Vehicle's tier to the given int parameter.
	 * 
	 * @param tierIndex The int representing the Vehicle's service tier.
	 * @throws BadVehicleInformationException If tierIndex is less than 0 or greater
	 *                                        than 3.
	 */
	public void setTier(int tierIndex) throws BadVehicleInformationException {
		if (tierIndex < 0 || tierIndex > 3) {
			throw new BadVehicleInformationException("Invalid tier.");
		}
		this.tierIndex = tierIndex;
	}

	/**
	 * Set's the owner name of the vehicle to the given parameter.
	 * 
	 * @param name The name to set the Vehicle's owner to.
	 * @throws BadVehicleInformationException If name parameter is blank or all
	 *                                        whitespace.
	 */
	private void setName(String name) throws BadVehicleInformationException {
		if (name == null || name.trim().equals("")) {
			throw new BadVehicleInformationException("Owner name cannot be blank.");
		} else {
			this.name = name;
		}
	}

	/**
	 * Set's the Vehicle's license plate to the given parameter.
	 * 
	 * @param license The String to set the Vehicle's license plate to.
	 * @throws BadVehicleInformationException If license parameter is blank or all
	 *                                        whitespace, or greater than 8 chars
	 *                                        after trim.
	 */
	private void setLicense(String license) throws BadVehicleInformationException {
		if (license == null || license.trim().equals("")) {
			throw new BadVehicleInformationException("License cannot be blank.");
		} else if (license.trim().contains(" ")) {
			throw new BadVehicleInformationException("License cannot contain interior whitespace.");
		} else if (license.trim().length() > 8) {
			throw new BadVehicleInformationException("License cannot be more than 8 characters.");
		} else {
			this.license = license;
		}
		this.license = license;
	}
}
