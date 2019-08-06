package edu.ncsu.csc216.garage.model.dealer;

import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;
import edu.ncsu.csc216.garage.model.util.SimpleIterator;
import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;
import edu.ncsu.csc216.garage.model.vehicle.Tiered;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;
import edu.ncsu.csc216.garage.model.vehicle.VehicleList;

/**
 * Class defines state and behavior for a ServiceManager object that acts as the
 * single entry point of the GUI into the back-end model of the program.
 * ServiceManager oversees and manipulates a Garage list of ServiceBays as well
 * as a VehicleList of Vehicles awaiting service.
 * 
 * @author Nick Garner
 *
 */
public class ServiceManager implements Manageable {

	/** List of Vehicles awaiting service */
	private VehicleList waitingCars;
	/** List of ServiceBays */
	private Garage myGarage;

	/**
	 * Constructs a new ServiceManager object with an empty waiting list and 8
	 * initial service bays
	 */
	public ServiceManager() {
		this(null);
	}

	/**
	 * Constructs a new ServiceManager object with 8 initial service bays and
	 * initializes the waiting list with data from the Scanner parameter
	 * 
	 * @param input The Scanner data to initialize the waiting list with
	 */
	public ServiceManager(Scanner input) {
		waitingCars = new VehicleList(input);
		myGarage = new Garage();
	}

	/**
	 * Constructs a vehicle object from the given parameters and adds it to the
	 * waiting list
	 * 
	 * @param vehicleType R for RegularCars or E for HybridElectricCars
	 * @param license     The license plate number of the Vehicle
	 * @param owner       The name of the owner of the Vehicle
	 * @param status      Integer value representing the level of service tier of
	 *                    the Vehicle
	 */
	@Override
	public void putOnWaitingList(String vehicleType, String license, String owner, int status) {
		if (vehicleType.equals("E")) {
			try {
				Vehicle carToAdd = new HybridElectricCar(license, owner, status);
				waitingCars.add(carToAdd);
			} catch (BadVehicleInformationException e) {
				// Do nothing
			}
		} else if (vehicleType.equals("R")) {
			try {
				Vehicle carToAdd = new RegularCar(license, owner, status);
				waitingCars.add(carToAdd);
			} catch (BadVehicleInformationException e) {
				// Do nothing
			}
		}
	}

	/**
	 * Adds the given Tiered object to the waiting list
	 */
	@Override
	public void putOnWaitingList(Tiered v) {
		Vehicle temp = (Vehicle)v;
		waitingCars.add(temp);
	}

	/**
	 * Returns the Tiered item in the filtered waiting list at the given position
	 * 
	 * @param filter   The search term to filter the waiting list by
	 * @param position The position in the filtered list of the Tiered item to
	 *                 return
	 * @return Returns the Tiered item in the filtered waiting list at the given
	 *         position
	 */
	@Override
	public Tiered getWaitingItem(String filter, int position) {
		try {
			return waitingCars.get(filter, position);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Removes the Tiered item in the filtered waiting list at the given position
	 * and returns it.
	 * 
	 * @param filter   The search term to filter the waiting list by.
	 * @param position The position in the filtered waiting list of the Tiered item
	 *                 to remove.
	 * @return Returns the Tiered item that was removed.
	 */
	@Override
	public Tiered remove(String filter, int position) {
		return waitingCars.remove(filter, position);
	}

	/**
	 * Traverses the waiting list sequentially and fills any empty bays as long as
	 * there are compatible Vehicles on the waiting list
	 */
	@Override
	public void fillServiceBays() {
		try {
			SimpleIterator<Vehicle> cursor = waitingCars.iterator();
			Vehicle temp = null;
			int counter = 0;
			while (myGarage.numberOfEmptyBays() > 0) {
				temp = cursor.next();
				try {
					temp.pickServiceBay(myGarage);
					waitingCars.remove("", counter);
				} catch (NoAvailableBayException e) {
					// Skip this car
					counter++;
				}
			}
		} catch (NoSuchElementException e) {
			// Do nothing
		}
	}

	/**
	 * Releases the occupying Vehicle in the ServiceBay at the given index and
	 * returns it.
	 * 
	 * @param i The index of the ServiceBay to empty
	 * @return The Vehicle that was occupying the ServiceBay
	 * @throws IllegalArgumentException If i is out of array bounds.
	 */
	@Override
	public Tiered releaseFromService(int i) {
		try {
			return myGarage.release(i);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Adds a new ServiceBay to the Garage object
	 */
	@Override
	public void addNewBay() {
		myGarage.addRepairBay();

	}

	/**
	 * Returns String representation of all Vehicles on the waiting list meeting the
	 * given String filter.
	 * 
	 * @param filter The search term to filter the list by.
	 */
	@Override
	public String printWaitList(String filter) {
		return waitingCars.filteredList(filter);
	}

	/**
	 * Prints the String representation of all ServiceBays currently open
	 */
	@Override
	public String printServiceBays() {
		String output = "";
		for (int i = 0; i < myGarage.getSize(); i++) {
			output += myGarage.getBayAt(i).toString() + "\n";
		}
		return output;
	}
}