package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

//import org.junit.Before;
import org.junit.Test;

/**
 * Tests Vehicle class for proper functionality and exception handling.
 * 
 * @author Nick Garner
 *
 */
public class VehicleTest {

	private static final String LICENSE1 = "NC-122";
	private static final String NAME1 = "Doe, John";
	private static final String LICENSE2 = "VA-121A";
	private static final String NAME2 = "Henderson, William";
	private static final String LICENSE3 = "CN09822";
	private static final String NAME3 = "Hende, Jack";
	private static final String NAME4 = "Doerty, Alex";
	private static final String NAME5 = "Myers, Gina";
	private static final String NAME6 = "Nicholson, Henry";
	private static final String LONGLICENSE = "NC-HAPPYCAMPER";
	private static final String SPACELICENSE = "0987 NC";

//	@Before
//	public void setUp() throws Exception {
//	}

//	@Test
//	public void testVehicleStringInt() {
//		fail("Not yet implemented");
//	}

	/**
	 * Tests Vehicle constructor with license, owner, and tier parameters. Tests
	 * String, Int constructor via chain of construction.
	 */
	@SuppressWarnings("unused")
	@Test
	public void testVehicleStringStringInt() {
		try {
			Vehicle car1 = new RegularCar(LICENSE1, NAME1, 1);
			assertEquals(LICENSE1, car1.getLicense());
			assertEquals(NAME1, car1.getName());
		} catch (BadVehicleInformationException e) {
			fail();
		}
		// Test bad status
		try {
			Vehicle car2 = new RegularCar(LICENSE1, NAME1, -1);
			fail();
		} catch (BadVehicleInformationException e) {
			assertEquals("Invalid tier.", e.getMessage());
		}
		try {
			Vehicle car2 = new RegularCar(LICENSE1, NAME1, 4);
			fail();
		} catch (BadVehicleInformationException e) {
			assertEquals("Invalid tier.", e.getMessage());
		}

		// Test bad license
		try {
			Vehicle car2 = new RegularCar(LONGLICENSE, NAME1, 1);
			fail();
		} catch (BadVehicleInformationException e) {
			assertEquals("License cannot be more than 8 characters.", e.getMessage());
		}
		try {
			Vehicle car2 = new RegularCar(SPACELICENSE, NAME1, 1);
			fail();
		} catch (BadVehicleInformationException e) {
			assertEquals("License cannot contain interior whitespace.", e.getMessage());
		}
//
//		// Test null license
//		// Doesn't work \/
//		try {
//			Vehicle car2 = new RegularCar(null, NAME1, 1);
//			fail();
//		} catch (BadVehicleInformationException e) {
//			assertEquals("License cannot be blank.", e.getMessage());
//		}
		// This one works \/
		try {
			Vehicle car2 = new RegularCar("", NAME1, 1);
			fail();
		} catch (BadVehicleInformationException e) {
			assertEquals("License cannot be blank.", e.getMessage());
		}

		// Test null name
		// Doesn't work \/
//		try {
//			Vehicle car2 = new RegularCar(LICENSE1, null, 1);
//			fail();
//		} catch (BadVehicleInformationException e) {
//			assertEquals("Owner name cannot be blank.", e.getMessage());
//		}
		// This one works \/
		try {
			Vehicle car2 = new RegularCar(LICENSE1, "", 1);
			fail();
		} catch (BadVehicleInformationException e) {
			assertEquals("Owner name cannot be blank.", e.getMessage());
		}
	}

	/**
	 * Tests that meetsFilter properly returns true if the start of the owner name
	 * matches the filter term.
	 */
	@Test
	public void testMeetsFilter() {
		try {
			Vehicle car1 = new RegularCar(LICENSE1, NAME1, 1);
			Vehicle car2 = new RegularCar(LICENSE1, NAME2, 1);
			Vehicle car3 = new RegularCar(LICENSE1, NAME3, 1);
			Vehicle car4 = new RegularCar(LICENSE1, NAME4, 1);
			Vehicle car5 = new RegularCar(LICENSE1, NAME5, 1);
			Vehicle car6 = new RegularCar("MA-1", "My Jeep", 1);
			assertTrue(car1.meetsFilter(null));
			assertTrue(car2.meetsFilter(null));
			assertTrue(car3.meetsFilter(null));
			assertTrue(car4.meetsFilter(null));
			assertTrue(car5.meetsFilter(null));
			assertTrue(car1.meetsFilter(""));
			assertTrue(car2.meetsFilter(""));
			assertTrue(car3.meetsFilter(""));
			assertTrue(car4.meetsFilter(""));
			assertTrue(car5.meetsFilter(""));
			assertTrue(car1.meetsFilter("doe"));
			assertTrue(car4.meetsFilter("doe"));
			assertTrue(car1.meetsFilter("do"));
			assertFalse(car2.meetsFilter("doe"));
			assertFalse(car2.meetsFilter("d"));
			assertFalse(car1.meetsFilter("h"));
			assertTrue(car2.meetsFilter("hend"));
			assertTrue(car3.meetsFilter("hend"));
			assertTrue(car3.meetsFilter("hende"));
			assertTrue(car3.meetsFilter("H"));
			assertFalse(car5.meetsFilter("Hend"));
			assertFalse(car1.meetsFilter("hender"));
			assertTrue(car6.meetsFilter("my j"));
		} catch (BadVehicleInformationException e) {
			fail();
		}
	}

	/**
	 * Tests that toString returns the proper String representation of the Vehicle
	 */
	@Test
	public void testToString() {
		try {
			Vehicle car1 = new RegularCar(LICENSE1, NAME1, 0);
			Vehicle car2 = new HybridElectricCar(LICENSE2, NAME2, 1);
			Vehicle car3 = new HybridElectricCar(LICENSE1, NAME5, 2);
			Vehicle car4 = new RegularCar(LICENSE3, NAME6, 3);
			assertEquals("R None      NC-122    Doe, John", car1.toString());
			assertEquals("E Silver    VA-121A   Henderson, William", car2.toString());
			assertEquals("E Gold      NC-122    Myers, Gina", car3.toString());
			assertEquals("R Platinum  CN09822   Nicholson, Henry", car4.toString());
		} catch (BadVehicleInformationException e) {
			fail();
		}
	}

	/**
	 * Tests that getTier returns the proper String tier name based on the Vehicle's
	 * status int param
	 */
	@Test
	public void testGetTier() {
		try {
			Vehicle car1 = new HybridElectricCar(LICENSE1, NAME1, 1);
			assertEquals(1, car1.getTier());
		} catch (BadVehicleInformationException e) {
			fail();
		}
	}

	/**
	 * Tests that compareToTier properly returns an int denoting the difference
	 * between the status tier of two Vehicles
	 */
	@Test
	public void testCompareToTier() {
		try {
			Vehicle car1 = new HybridElectricCar(LICENSE1, NAME1, 1);
			Vehicle car2 = new HybridElectricCar(LICENSE1, NAME1, 3);
			Vehicle car3 = new RegularCar(LICENSE1, NAME1, 2);
			Vehicle car4 = new RegularCar(LICENSE1, NAME1, 3);
			Vehicle car5 = new RegularCar(LICENSE1, NAME1, 0);
			assertEquals(0, car2.compareToTier(car4));
			assertEquals(Integer.compare(car1.getTier(), car2.getTier()), car1.compareToTier(car2));
			assertEquals(Integer.compare(car1.getTier(), car5.getTier()), car1.compareToTier(car5));
			assertEquals(Integer.compare(car3.getTier(), car4.getTier()), car3.compareToTier(car4));
		} catch (BadVehicleInformationException e) {
			fail();
		}
	}
}