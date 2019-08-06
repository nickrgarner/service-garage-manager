package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

//import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Tests HybridElectricBay.occupy for proper functionality and exception
 * handling
 * 
 * @author Nick Garner
 *
 */
public class HybridElectricBayTest {

//	@Before
//	public void setUp() throws Exception {
//	}

	/**
	 * Tests that occupy properly allows for HEV's to fill and throws exception for
	 * RegularCars
	 */
	@Test
	public void testOccupy() {
		try {
			ServiceBay bay1 = new HybridElectricBay();
			Vehicle car1 = new HybridElectricCar("NC-1234 Doe, John", 3);
			Vehicle car2 = new HybridElectricCar("NC-1234 Doe, John", 3);
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
		try {
			ServiceBay bay1 = new HybridElectricBay();
			Vehicle car1 = new RegularCar("NC-1234 Doe, John", 3);
			assertEquals("R", car1.toString().substring(0, 1));
			assertTrue(car1.toString().substring(0, 1).equals("R"));
			bay1.occupy(car1);
			fail();
		} catch (BadVehicleInformationException e) {
			fail();
		} catch (BayOccupiedException e) {
			fail();
		} catch (BayCarMismatchException e) {
			assertEquals("Cannot service Regular Cars in a Hybrid Electric Bay.", e.getMessage());
		}
	}

}
