package edu.ncsu.csc216.garage.model.vehicle;

/**
 * Interface declares methods for returning and comparing Vehicle's service tier
 * 
 * @author Nick Garner
 *
 */
public interface Tiered {

	/**
	 * Returns int representing Vehicle's service tier
	 * 
	 * @return Service tier as int
	 */
	int getTier();

	/**
	 * Compares the service tier of this Vehicle to the given parameter Vehicle and
	 * returns an int comparison.
	 * 
	 * @param compare The Vehicle to compare tiers to this Vehicle
	 * @return Returns 0 if the Vehicles have the same service tier, less than 0 if
	 *         this Vehicle has a lower service tier, or greater than 0 if this
	 *         Vehicle has a higher service tier.
	 */
	int compareToTier(Tiered compare);
}
