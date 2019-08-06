package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Class tests ServiceBay in conjunction with GarageTest. Tests for proper
 * constructor, occupy, and toString funcionality including exception handling.
 * 
 * @author Nick Garner
 *
 */
public class ServiceBayTest {

	/**
	 * Runs before every test to reset bay numbering to start at 101
	 * 
	 * @throws Exception If error occurs during setup
	 */
	@Before
	public void setUp() throws Exception {
		ServiceBay.startBayNumberingAt101();
	}

	/**
	 * Tests that getBayID returns the proper bayID String
	 */
	@Test
	public void testGetBayID() {
		ServiceBay bay1 = new ServiceBay();
		assertEquals("101", bay1.getBayID());
	}

	/**
	 * Tests that occupy() properly changes the ServiceBay's occupied field and
	 * properly throws a BayOccupiedException when necessary
	 */
	@Test
	public void testOccupy() {
		try {
			ServiceBay bay1 = new ServiceBay();
			Vehicle car1 = new RegularCar("NC-1234 Doe, John", 3);
			Vehicle car2 = new RegularCar("NC-1234 Doe, John", 3);
			bay1.occupy(car1);
			bay1.occupy(car2);
			fail();
		} catch (BadVehicleInformationException e) {
			fail();
		} catch (BayOccupiedException e) {
			assertEquals("Bay is currently occupied.", e.getMessage());
		} catch (BayCarMismatchException e) {
			fail();
		}
	}

	/**
	 * Tests that ServiceBay.toString outputs the proper String
	 */
	@Test
	public void testToString() {
		try {
			ServiceBay bay1 = new ServiceBay();
			Vehicle car1 = new RegularCar("NC-1234 Doe, John", 3);
			assertEquals("101: EMPTY", bay1.toString());
			bay1.occupy(car1);
			assertEquals("101: NC-1234  Doe, John", bay1.toString());
		} catch (BadVehicleInformationException e) {
			fail();
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			fail();
		}
	}

}
