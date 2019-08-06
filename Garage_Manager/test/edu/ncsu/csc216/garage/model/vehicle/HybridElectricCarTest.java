package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

//import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**
 * Class tests that HybridElectricCar.pickServiceBay properly throws Exception
 * when bays are full
 * 
 * @author Nick Garner
 *
 */
public class HybridElectricCarTest {

	/** Garage to hold test Vehicles */
	public static final Garage GARAGE1 = new Garage();

//	@Before
//	public void setUp() throws Exception {
//	}

	/**
	 * Testing pickServiceBay for proper exception handling
	 */
	@Test
	public void testPickServiceBay() {
		try {
			RegularCar car1 = new RegularCar("NC-123", "Doe, John", 2);
			RegularCar car2 = new RegularCar("VA-2265", "Smith, Jane", 3);
			RegularCar car3 = new RegularCar("NC-8919", "Name, First", 1);
			RegularCar car4 = new RegularCar("IA-2019", "Vance, Bob", 2);
			RegularCar car5 = new RegularCar("GA-3298", "Scott, Michael", 1);
			HybridElectricCar car6 = new HybridElectricCar("PA-1209", "Schrute, Dwight", 3);
			HybridElectricCar car7 = new HybridElectricCar("OK-3910", "Halpert, Jim", 2);
			HybridElectricCar car8 = new HybridElectricCar("LA-01382", "Bernard, Andrew", 1);
			HybridElectricCar car9 = new HybridElectricCar("TX-1090", "Beasley, Pam", 2);
			car1.pickServiceBay(GARAGE1);
			car2.pickServiceBay(GARAGE1);
			car3.pickServiceBay(GARAGE1);
			car4.pickServiceBay(GARAGE1);
			car5.pickServiceBay(GARAGE1);
			car6.pickServiceBay(GARAGE1);
			car7.pickServiceBay(GARAGE1);
			car8.pickServiceBay(GARAGE1);
			car9.pickServiceBay(GARAGE1);
			fail();
		} catch (BadVehicleInformationException e) {
			fail();
		} catch (NoAvailableBayException e) {
			assertEquals("No bay is available.", e.getMessage());
		}
	}

}
