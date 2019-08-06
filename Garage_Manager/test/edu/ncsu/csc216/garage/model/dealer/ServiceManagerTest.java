package edu.ncsu.csc216.garage.model.dealer;

import static org.junit.Assert.*;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;

/**
 * Tests ServiceManager class for proper functionality and exception handling
 * 
 * @author Nick Garner
 *
 */
public class ServiceManagerTest {

	/** RegularCar object to test */
	private RegularCar car1;
	/** RegularCar object to test */
	private RegularCar car2;
	/** RegularCar object to test */
	private RegularCar car3;
	/** RegularCar object to test */
	private RegularCar car4;
	/** RegularCar object to test */
	private RegularCar car5;
	/** RegularCar object to test */
	private RegularCar car6;
	/** HybridElectricCar object to test */
	private HybridElectricCar hev1;
	/** HybridElectricCar object to test */
	private HybridElectricCar hev2;
	/** HybridElectricCar object to test */
	private HybridElectricCar hev3;
	/** HybridElectricCar object to test */
	private HybridElectricCar hev4;
	/** String input to test Scanner constructor */
	private static final String LISTINPUT = "R 2      NC-123    Scott, Michael\n" + "E 1    NC-9910   Kapoor, Kelly\n"
			+ "R 3  LA-10293  Malone, Kevin\n" + "E 2      VA-8991   Bernard, Andrew\n"
			+ "R 1    IA-3901   Bratton, Creed\n";

	/**
	 * Runs before each test to instantiate a series of Vehicle objects
	 * 
	 * @throws Exception If error occurs during setup
	 */
	@Before
	public void setUp() throws Exception {
		try {
			car1 = new RegularCar("NC-123", "Scott, Michael", 2);
			car2 = new RegularCar("IA-3901", "Bratton, Creed", 1);
			car3 = new RegularCar("LA-10293", "Malone, Kevin", 3);
			car4 = new RegularCar("OK-39820", "Kinsey, Angela", 0);
			car5 = new RegularCar("VA-3301", "Schrute, Dwight", 3);
			car6 = new RegularCar("MI-19002", "Levinson, Jan", 3);
			hev1 = new HybridElectricCar("VA-8991", "Bernard, Andrew", 2);
			hev2 = new HybridElectricCar("NC-9910", "Kapoor, Kelly", 1);
			hev3 = new HybridElectricCar("DC-10098", "Beasley, Pamela", 0);
			hev4 = new HybridElectricCar("PA-12891", "Vance, Bob", 0);
		} catch (BadVehicleInformationException e) {
			fail();
		}
	}

	/**
	 * Tests that null constructor constructs 8 empty bays and an empty waitlist
	 */
	@Test
	public void testServiceManager() {
		ServiceManager testMgr = new ServiceManager();
		assertEquals("108: EMPTY\n106: EMPTY\n105: EMPTY\n103: EMPTY\n102: EMPTY\nE01: EMPTY\nE04: EMPTY\nE07: EMPTY\n",
				testMgr.printServiceBays());
//		try {
//			testMgr.printWaitList("");
//			fail();
//		} catch (NullPointerException e) {
//			assertNull(e.getMessage());
//		}
	}

	/**
	 * Tests that Scanner constructor constructs 8 empty bays and a waitlist based
	 * on Scanner input correctly
	 */
	@Test
	public void testServiceManagerScanner() {
		Scanner input = new Scanner(LISTINPUT);
		ServiceManager testMgr = new ServiceManager(input);
		assertEquals("108: EMPTY\n106: EMPTY\n105: EMPTY\n103: EMPTY\n102: EMPTY\nE01: EMPTY\nE04: EMPTY\nE07: EMPTY\n",
				testMgr.printServiceBays());
		assertEquals("R Platinum  LA-10293  Malone, Kevin\n" + "R Gold      NC-123    Scott, Michael\n"
				+ "E Gold      VA-8991   Bernard, Andrew\n" + "E Silver    NC-9910   Kapoor, Kelly\n"
				+ "R Silver    IA-3901   Bratton, Creed\n", testMgr.printWaitList(""));
	}

	/**
	 * Tests that putOnWaitingList properly constructs Vehicle from given params and
	 * adds to list in proper order
	 */
	@Test
	public void testPutOnWaitingListStringStringStringInt() {
		ServiceManager testMgr = new ServiceManager();
		testMgr.putOnWaitingList("E", "NC-123", "Doe, John", 2);
		testMgr.putOnWaitingList("R", "VA-321", "Smith, Jane", 3);
		testMgr.putOnWaitingList("Z", "IA-310", "Vehicle, Bad", 1);
		assertEquals("R Platinum  VA-321    Smith, Jane\nE Gold      NC-123    Doe, John\n", testMgr.printWaitList(""));
	}

	/**
	 * Tests that putOnWaitingListTiered properly adds Vehicle object
	 */
	@Test
	public void testPutOnWaitingListTiered() {
		ServiceManager testMgr = new ServiceManager();
		testMgr.putOnWaitingList(car1);
		assertEquals("R Gold      NC-123    Scott, Michael\n", testMgr.printWaitList(""));
	}

	/**
	 * Tests that getWaitingItem returns proper Vehicle object based on filter and
	 * position
	 */
	@Test
	public void testGetWaitingItem() {
		ServiceManager testMgr = new ServiceManager();
		testMgr.putOnWaitingList(car1);
		testMgr.putOnWaitingList(car2);
		testMgr.putOnWaitingList(car3);
		assertEquals(car3, testMgr.getWaitingItem("", 0));
		assertEquals(car1, testMgr.getWaitingItem("", 1));
		assertEquals(car2, testMgr.getWaitingItem("", 2));
	}

	/**
	 * Tests that remove removes and returns proper Vehicle
	 */
	@Test
	public void testRemove() {
		ServiceManager testMgr = new ServiceManager();
		testMgr.putOnWaitingList(car1);
		testMgr.putOnWaitingList(car2);
		testMgr.putOnWaitingList(car3);
		assertEquals(car1, testMgr.remove("", 1));
		assertEquals(car3, testMgr.remove("", 0));
		
		ServiceManager testMgr2 = new ServiceManager();
		testMgr2.putOnWaitingList(car1);
		testMgr2.putOnWaitingList(car2);
		testMgr2.putOnWaitingList(car3);
		testMgr2.putOnWaitingList(car4);
		testMgr2.putOnWaitingList(car5);
		testMgr2.putOnWaitingList(hev1);
		testMgr2.putOnWaitingList(hev2);
		testMgr2.putOnWaitingList(hev3);
		testMgr2.putOnWaitingList(car6);
		testMgr2.putOnWaitingList(hev4);
		assertEquals(car5, testMgr2.remove("sc", 0));
		testMgr2.remove("sc", 1);
	}

	/**
	 * Tests that fillServiceBays properly fills the correct bays based on the
	 * waitlist
	 */
	@Test
	public void testFillServiceBays() {
		ServiceManager testMgr = new ServiceManager();
		testMgr.putOnWaitingList(car1);
		testMgr.putOnWaitingList(car2);
		testMgr.putOnWaitingList(car3);
		testMgr.putOnWaitingList(car4);
		testMgr.putOnWaitingList(car5);
		testMgr.putOnWaitingList(hev1);
		testMgr.putOnWaitingList(hev2);
		testMgr.putOnWaitingList(hev3);
		testMgr.putOnWaitingList(car6);
		testMgr.putOnWaitingList(hev4);
		testMgr.fillServiceBays();
		assertEquals("108: LA-10293 Malone, Kevin\n" + "106: VA-3301  Schrute, Dwight\n"
				+ "105: MI-19002 Levinson, Jan\n" + "103: NC-123   Scott, Michael\n" + "102: IA-3901  Bratton, Creed\n"
				+ "E01: DC-10098 Beasley, Pamela\n" + "E04: NC-9910  Kapoor, Kelly\n"
				+ "E07: VA-8991  Bernard, Andrew\n", testMgr.printServiceBays());
		assertEquals("R None      OK-39820  Kinsey, Angela\n" + "E None      PA-12891  Vance, Bob\n", testMgr.printWaitList(""));
		assertEquals(car3, testMgr.releaseFromService(0));
		testMgr.addNewBay();
		
		ServiceManager testMgr2 = new ServiceManager();
		testMgr2.putOnWaitingList(car1);
		testMgr2.putOnWaitingList(car2);
		testMgr2.putOnWaitingList(car3);
		testMgr2.putOnWaitingList(car4);
		testMgr2.fillServiceBays();
		assertEquals("", testMgr2.printWaitList(""));
	}
}



























