package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

//import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**
 * Tests RegularCar for proper exception handling and toString method
 * 
 * @author Nick Garner
 *
 */
public class RegularCarTest {

	/** Garage to hold test Vehicles */
	public static final Garage GARAGE1 = new Garage();
	/** Garage to hold test Vehicles */
	public static final Garage GARAGE2 = new Garage();

//	@Before
//	public void setUp() throws Exception {
//	}

	/**
	 * Tests that pickServiceBay appropriately throws exception if no bay is
	 * available
	 */
	@Test
	public void testPickServiceBay() {
		// No available bay
		try {
			RegularCar car1 = new RegularCar("NC-123", "Doe, John", 2);
			RegularCar car2 = new RegularCar("VA-2265", "Smith, Jane", 3);
			RegularCar car3 = new RegularCar("NC-8919", "Name, First", 1);
			RegularCar car4 = new RegularCar("IA-2019", "Vance, Bob", 2);
			RegularCar car5 = new RegularCar("GA-3298", "Scott, Michael", 1);
			HybridElectricCar car6 = new HybridElectricCar("PA-1209", "Schrute, Dwight", 3);
			RegularCar car9 = new RegularCar("TX-1090", "Beasley, Pam", 2);
			car1.pickServiceBay(GARAGE1);
			car2.pickServiceBay(GARAGE1);
			car3.pickServiceBay(GARAGE1);
			car4.pickServiceBay(GARAGE1);
			car5.pickServiceBay(GARAGE1);
			car6.pickServiceBay(GARAGE1);
			car9.pickServiceBay(GARAGE1);
			fail();
		} catch (BadVehicleInformationException e) {
			e.printStackTrace();
		} catch (NoAvailableBayException e) {
			assertEquals("No bay is available.", e.getMessage());
		}

		// Success
		try {
			RegularCar car1 = new RegularCar("NC-123", "Doe, John", 2);
			RegularCar car2 = new RegularCar("VA-2265", "Smith, Jane", 3);
			RegularCar car3 = new RegularCar("NC-8919", "Name, First", 1);
			RegularCar car4 = new RegularCar("IA-2019", "Vance, Bob", 2);
			RegularCar car5 = new RegularCar("GA-3298", "Scott, Michael", 1);
			HybridElectricCar car6 = new HybridElectricCar("PA-1209", "Schrute, Dwight", 3);
			RegularCar car9 = new RegularCar("TX-1090", "Beasley, Pam", 2);
			car1.pickServiceBay(GARAGE2);
			car2.pickServiceBay(GARAGE2);
			car3.pickServiceBay(GARAGE2);
			car4.pickServiceBay(GARAGE2);
			car6.pickServiceBay(GARAGE2);
			car5.pickServiceBay(GARAGE2);
			car9.pickServiceBay(GARAGE2);
		} catch (BadVehicleInformationException e) {
			e.printStackTrace();
		} catch (NoAvailableBayException e) {
			assertEquals("No bay is available.", e.getMessage());
		}
	}

	/**
	 * Tests that toString produces the proper String representation of the
	 * RegularCar
	 */
	@Test
	public void testToString() {
		try {
			RegularCar car1 = new RegularCar("NC-123", "Doe, John", 2);
			assertEquals("R Gold      NC-123    Doe, John", car1.toString());
		} catch (BadVehicleInformationException e) {
			fail();
		}
	}

}
