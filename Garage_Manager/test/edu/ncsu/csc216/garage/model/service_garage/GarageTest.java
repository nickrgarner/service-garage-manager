package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Class tests that Garage class properly initializes with the correct
 * ServiceBay structure and that functionality provided for get, release, etc is
 * properly executed.
 * 
 * @author Nick Garner
 *
 */
public class GarageTest {

	/**
	 * Tests that Garage constructor properly sets up the initial 8 ServiceBays
	 */
	@Test
	public void testGarage() {
		Garage gTest = new Garage();
		assertEquals(8, gTest.getSize());
		assertEquals(8, gTest.numberOfEmptyBays());
		assertEquals("108", gTest.getBayAt(0).getBayID());
		assertEquals("E01", gTest.getBayAt(5).getBayID());
	}

	/**
	 * Tests that addServiceBay properly creates HEV bays as needed for ratio
	 */
	@Test
	public void testAddServiceBay() {
		Garage gTest = new Garage();
		gTest.addRepairBay();
		gTest.addRepairBay();
		gTest.addRepairBay();
		gTest.addRepairBay();
		gTest.addRepairBay();
		assertEquals(13, gTest.getSize());
		assertEquals("112", gTest.getBayAt(0).getBayID());
		assertEquals("105", gTest.getBayAt(5).getBayID());
		assertEquals("E13", gTest.getBayAt(12).getBayID());
	}
	
	/**
	 * Tests that release() properly returns the Vehicle being released and resets
	 * the ServiceBay's occupied field
	 */
	@Test
	public void testRelease() {
		try {
			Garage gTest = new Garage();
			Vehicle car1 = new RegularCar("NC-2218 Doe, John", 2);
			car1.pickServiceBay(gTest);
			assertEquals(7, gTest.numberOfEmptyBays());
			assertEquals(car1, gTest.release(0));
			assertEquals(8, gTest.numberOfEmptyBays());
		} catch (BadVehicleInformationException e) {
			fail();
		} catch (NoAvailableBayException e) {
			fail();
		}
	}
}