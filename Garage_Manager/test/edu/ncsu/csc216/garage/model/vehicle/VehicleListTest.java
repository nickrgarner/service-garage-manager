package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.util.SimpleIterator;

/**
 * Tests VehicleList class for proper functionality and exception handling
 * 
 * @author Nick Garner
 *
 */
public class VehicleListTest {

	/** RegularCar object to test */
	private RegularCar car1;
	/** RegularCar object to test */
	private RegularCar car2;
	/** RegularCar object to test */
	private RegularCar car3;
	/** RegularCar object to test */
	private RegularCar car5;
	/** RegularCar object to test */
	private RegularCar car6;
	/** HybridElectricCar object to test */
	private HybridElectricCar hev1;
	/** HybridElectricCar object to test */
	private HybridElectricCar hev2;
	/** String of cars to test Scanner constructor */
	private static final String LISTINPUT = "R 2      NC-123    Scott, Michael\n" + "E 1    NC-9910   Kapoor, Kelly\n"
			+ "R 3  LA-10293  Malone, Kevin\n" + "E 2      VA-8991   Bernard, Andrew\n"
			+ "R 1    IA-3901   Bratton, Creed\n";
	
	/**
	 * Runs before each test to instantiate a series of Vehicle objects to use in
	 * testing.
	 * 
	 * @throws Exception If error occurs during setup
	 */
	@Before
	public void setUp() throws Exception {
		try {
			car1 = new RegularCar("NC-123", "Scott, Michael", 2);
			car2 = new RegularCar("IA-3901", "Bratton, Creed", 1);
			car3 = new RegularCar("LA-10293", "Malone, Kevin", 3);
			car5 = new RegularCar("VA-3301", "Schrute, Dwight", 3);
			car6 = new RegularCar("PA-83910", "Schrute, Mose", 0);
			hev1 = new HybridElectricCar("VA-8991", "Bernard, Andrew", 2);
			hev2 = new HybridElectricCar("NC-9910", "Kapoor, Kelly", 1);
		} catch (BadVehicleInformationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests VehicleList Scanner constructor for proper list instantiation
	 */
	@Test
	public void testVehicleListScanner() {
		Scanner input = new Scanner(LISTINPUT);
		VehicleList testList = new VehicleList(input);
		assertEquals("R Platinum  LA-10293  Malone, Kevin\n" + "R Gold      NC-123    Scott, Michael\n"
				+ "E Gold      VA-8991   Bernard, Andrew\n" + "E Silver    NC-9910   Kapoor, Kelly\n"
				+ "R Silver    IA-3901   Bratton, Creed\n", testList.filteredList(""));
	}

	/**
	 * Tests VehicleList null constructor for empty list
	 */
	@Test
	public void testVehicleList() {
		VehicleList testList = new VehicleList();
		try {
			testList.get("", 0);
		} catch (NullPointerException e) {
			assertNull(e.getMessage());
		}
	}

	/**
	 * Tests iterator for proper next and hasNext function
	 */
	@Test
	public void testIterator() {
		VehicleList testList = new VehicleList();
		SimpleIterator<Vehicle> nullParse = testList.iterator();
		try {
			nullParse.hasNext();
		} catch (NoSuchElementException e) {
			assertNull(e.getMessage());
		}
		try {
			nullParse.next();
			fail();
		} catch (NoSuchElementException e) {
			assertNull(e.getMessage());
		}
		testList.add(car1);
		testList.add(car2);
		testList.add(hev1);
		testList.add(hev2);
		testList.add(car3);
		testList.add(car5);
		testList.add(car6);
		SimpleIterator<Vehicle> parse = testList.iterator();
		assertTrue(parse.hasNext());
		assertEquals(car3, parse.next());
	}

	/**
	 * Tests that remove() removes and returns the proper Vehicle
	 */
	@Test
	public void testRemove() {
		VehicleList testList = new VehicleList();
		testList.add(car1);
		testList.add(car2);
		testList.add(hev1);
		testList.add(hev2);
		testList.add(car3);
		testList.add(car5);
		testList.add(car6);
		assertEquals(car2, testList.remove("", 4));
		assertEquals(car3, testList.remove("", 0));
		assertNull(testList.remove("", 7));
	}

	/**
	 * Tests that get() returns the proper Vehicle object
	 */
	@Test
	public void testGet() {
		VehicleList testList = new VehicleList();
		testList.add(car1);
		testList.add(car2);
		testList.add(hev1);
		testList.add(hev2);
		testList.add(car3);
		testList.add(car5);
		testList.add(car6);
		// Test without filter
		assertEquals(car3, testList.get("", 0));
		assertEquals(car1, testList.get("", 2));
		assertEquals(car6, testList.get("", 6));
		// Test running off the list
		assertNull(testList.get("", 7));
		// Test with filter
		assertEquals(car1, testList.get("sc", 1));
		assertEquals(car6, testList.get("sc", 2));
	}

	/**
	 * Tests that add() inserts the new Vehicle at the proper position in the
	 * ordered list
	 */
	@Test
	public void testAdd() {
		VehicleList testList = new VehicleList();
		testList.add(car1);
		testList.add(car2);
		testList.add(hev1);
		testList.add(hev2);
		testList.add(car3);
		assertEquals(car3, testList.get("", 0));
		assertEquals(car1, testList.get("", 1));
		assertEquals(hev1, testList.get("", 2));
		assertEquals(car2, testList.get("", 3));
		assertEquals(hev2, testList.get("", 4));
	}

	/**
	 * Tests that filteredList returns the proper String based on the given filter
	 */
	@Test
	public void testFilteredList() {
		VehicleList testList = new VehicleList();
		testList.add(car1);
		testList.add(car2);
		testList.add(hev1);
		testList.add(hev2);
		testList.add(car3);
		assertEquals("R Platinum  LA-10293  Malone, Kevin\n" + "R Gold      NC-123    Scott, Michael\n"
				+ "E Gold      VA-8991   Bernard, Andrew\n" + "R Silver    IA-3901   Bratton, Creed\n"
				+ "E Silver    NC-9910   Kapoor, Kelly\n", testList.filteredList(""));
		assertEquals("E Gold      VA-8991   Bernard, Andrew\n" + "R Silver    IA-3901   Bratton, Creed\n",
				testList.filteredList("b"));
	}
}