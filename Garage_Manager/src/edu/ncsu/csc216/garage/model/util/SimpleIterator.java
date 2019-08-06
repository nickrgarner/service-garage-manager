package edu.ncsu.csc216.garage.model.util;

import java.util.NoSuchElementException;

/**
 * Interface declares required methods for SimpleIterator Cursor inner class in
 * VehicleList.
 * 
 * @author Nick Garner
 *
 * @param <E> Generic type parameter
 */
public interface SimpleIterator<E> {

	/**
	 * Returns true if iterator has more elements to traverse.
	 * 
	 * @return True if the iterator has more elements to traverse.
	 */
	boolean hasNext();

	/**
	 * Gets the next element in the list and pushes the iterator down the list.
	 * 
	 * @return The next element in the list.
	 * @throws NoSuchElementException If there is no next element.
	 */
	E next() throws NoSuchElementException;
}
