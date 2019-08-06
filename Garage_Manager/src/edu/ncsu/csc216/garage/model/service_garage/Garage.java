package edu.ncsu.csc216.garage.model.service_garage;

import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Class serves as a list archetype to hold ServiceBay objects and manage their
 * number. Garage starts with 8 open ServiceBays and can open a maximum of 30
 * total bays. Every third bay opened is automatically opened as a
 * HybridElectricBay.
 * 
 * @author Nick Garner
 *
 */
public class Garage {

	/** Total number of open ServiceBays */
	private int size;
	/** Array to hold open ServiceBays */
	private ServiceBay[] bay;
	/** Maximum allowed number of ServiceBays */
	private static final int MAX_BAYS = 30;
	/** Starting number of ServiceBays */
	private static final int DEFAULT_SIZE = 8;

	/**
	 * Constructs a new CustomArrayList of ServiceBays with a default size of 8.
	 */
	public Garage() {
		ServiceBay.startBayNumberingAt101();
		size = 0;
		bay = new ServiceBay[MAX_BAYS];
		initBays(DEFAULT_SIZE);
	}

	/**
	 * Bulk opens the first service bays in the Garage based on the int parameter
	 * given.
	 * 
	 * @param numberOfBays The number of bays to open in the Garage.
	 */
	private void initBays(int numberOfBays) {
		for (int i = 0; i < numberOfBays; i++) {
			addRepairBay();
		}
	}

	/**
	 * Adds a new service bay to the Garage list. Every three service bays opened
	 * will be a HybridElectricBay.
	 */
	public void addRepairBay() {
		try {
			if (getSize() % 3 == 0) {
				bay[getSize()] = new HybridElectricBay();
				size++;
			} else {
				for (int i = getSize(); i > 0; i--) {
					bay[i] = bay[i - 1];
				}
				bay[0] = new ServiceBay();
				size++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("No additional Service Bays can be opened.");
		}
	}

	/**
	 * Traverses the bayList sequentially and returns the current total number of
	 * empty service bays.
	 * 
	 * @return Returns current total number of empty bays.
	 */
	public int numberOfEmptyBays() {
		int emptyBays = 0;
		for (int i = 0; i < getSize(); i++) {
			if (!getBayAt(i).isOccupied()) {
				emptyBays++;
			}
		}
		return emptyBays;
	}

	/**
	 * Returns the ServiceBay at the given index.
	 * 
	 * @param index The list index at which to retrieve the ServiceBay object.
	 * @return Returns the ServiceBay at the given index.
	 */
	public ServiceBay getBayAt(int index) {
		return bay[index];
	}

	/**
	 * Returns the total number of created service bays.
	 * 
	 * @return Returns total number of created service bays.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Releases and returns the Vehicle in the ServiceBay at the specified index.
	 * 
	 * @param index Index of the ServiceBay to release the Vehicle from.
	 * @return Returns the Vehicle that was released.
	 */
	public Vehicle release(int index) {
		if (index >= 0 && index < size) {
			return getBayAt(index).release();
		} else {
			return null;
		}
	}
}