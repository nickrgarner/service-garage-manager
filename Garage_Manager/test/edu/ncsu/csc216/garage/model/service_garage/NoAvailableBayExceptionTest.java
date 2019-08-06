package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests NoAvailableBayException null constructor for correct default message
 * @author Nick Garner
 *
 */
public class NoAvailableBayExceptionTest {

	/**
	 * Tests NoAvailableBayException null constructor for correct default message
	 */
	@Test
	public void testNoAvailableBayException() {
		NoAvailableBayException e = new NoAvailableBayException();
		assertEquals("No bay is available.", e.getMessage());
	}

}
