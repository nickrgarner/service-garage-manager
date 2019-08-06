package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests BayOccupiedException null constructor for correct default message
 * 
 * @author Nick Garner
 *
 */
public class BayOccupiedExceptionTest {

	/**
	 * Tests BayOccupiedException null constructor for correct default message
	 */
	@Test
	public void testBayOccupiedException() {
		BayOccupiedException e = new BayOccupiedException();
		assertEquals("Bay is occupied.", e.getMessage());
	}

}
