package edu.ncsu.csc216.garage.model.vehicle;

import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.garage.model.util.SimpleIterator;

/**
 * Class defines state and behavior for a list of Vehicles modeled after a
 * custom linked list. Vehicles are grouped based on their service tier and are
 * added to the back of their respective group when created. Class functionality
 * is included for basic add, get, remove as well as creating a list from data
 * from an input file.
 * 
 * @author Nick Garner
 *
 */
public class VehicleList {

	/** Node representing the front of the class */
	private Node head;

	/**
	 * Constructs an empty VehicleList and populates it with Vehicle objects based
	 * on a Scanner object of an input text file
	 * 
	 * @param input The Scanner object instantiated with a text file of Vehicles
	 */
	public VehicleList(Scanner input) {
		this();
		if (input != null) {
			input.useDelimiter("\n");
			try {
				while (input.hasNext()) {
					String line = input.next();
					try {
						if (line.substring(0, 1).toLowerCase().equals("r")) {
							add(new RegularCar(line.substring(4).trim(), Integer.valueOf(line.substring(2, 4).trim())));
						} else if (line.substring(0, 1).toLowerCase().equals("e")) {
							add(new HybridElectricCar(line.substring(4).trim(), Integer.valueOf(line.substring(2, 4).trim())));
						}
					} catch (BadVehicleInformationException e) {
						// Don't add
					}
				}
			} catch (NullPointerException e) {
				// End of input
			}
		}
	}

	/**
	 * Creates a new, empty VehicleList with head initialized to a null value and
	 * null pointer.
	 */
	public VehicleList() {
		head = new Node(null, null);
	}

	/**
	 * Instantiates a new cursor object initalized to point to head.
	 * 
	 * @return Returns a new iterator pointing to the front of the list.
	 */
	public SimpleIterator<Vehicle> iterator() {
		Cursor cursor = new Cursor();
		return cursor;
	}

	/**
	 * Traverses full list for Vehicles meeting filter and removes Vehicle at
	 * specified position
	 * 
	 * @param filter   The search term to filter the list by
	 * @param position The index in the filtered list to remove
	 * @return Returns the Vehicle that was removed
	 */
	public Vehicle remove(String filter, int position) {
		Node current = head;
		Node previous = null;
		int counter = -1;
		try {
			while (current.next != null && counter < position) {
				// Check if current node meets filter, if so increment counter
				if (current.v.meetsFilter(filter)) {
					counter++;
				}
				// If you've hit the position, remove and return
				if (counter == position) {
					if (current == head) {
						Vehicle output = head.v;
						head = head.next;
						return output;
					} else {
						Vehicle output = current.v;
						previous.next = current.next;
						return output;
					}
				}
				// If haven't hit the position, advance pointers and loop
				previous = current;
				current = current.next;
			}
			// If skipping loop altogether because you're removing only element, remove head
			if (current == head) {
				Vehicle output = head.v;
				head = head.next;
				return output;
			}
			// If outside loop because you're at the end and trying to remove end, remove end
			if (current.v.meetsFilter(filter)) {
				counter++;
				if (counter == position) {
					Vehicle output = current.v;
					previous.next = current.next;
					return output;
				}
			}
			// If exit loop without a return, it's not there
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Returns the Vehicle at the specified position in the filtered list
	 * 
	 * @param filter   The search term to filter the list by
	 * @param position The index in the filtered list to remove
	 * @return Returns the Vehicle at the specified position in the filtered list
	 */
	public Vehicle get(String filter, int position) {
		Node current = head;
		int counter = -1;
		while (current != null & counter < position) {
			if (current.v.meetsFilter(filter)) {
				counter++;
			}
			if (counter == position) {
				return current.v;
			} else {
				current = current.next;
			}
		}
		return null;
	}

	/**
	 * Adds the given Vehicle object to the list at the end of its service tier
	 * grouping.
	 * 
	 * @param carToAdd The Vehicle object to add to the list.
	 */
	public void add(Vehicle carToAdd) {
		Node current = head;
		Node previous = null;

		// Move down the list until either hitting end or hitting vehicle with lower
		// service tier
		try {
			if (head.v == null) {
				head = new Node(carToAdd, head.next);
			} else {
				while (current != null && current.v.compareToTier(carToAdd) >= 0) {
					previous = current;
					current = current.next;
				}
				// If at head, make head the new node, else insert into list
				if (current == head) {
					head = new Node(carToAdd, head);
				} else {
					previous.next = new Node(carToAdd, current);
				}
			}
		} catch (NullPointerException e) {
			head = new Node(carToAdd, null);
		}
	}

//	private Node findTrailingNode(String s, int i) {
//		// TODO
//		return null;
//	}

	/**
	 * Traverses through the list sequentially and prints any Vehicle that meets the
	 * filter
	 * 
	 * @param filter The search term to filter the Vehicle list by.
	 * @return String representation of every Vehicle meeting the filter text,
	 *         separated by newlines.
	 */
	public String filteredList(String filter) {
		try {
			Node current = head;
			String output = "";
			while (current != null) {
				if (current.v.meetsFilter(filter)) {
					output += current.v.toString() + "\n";
				}
				current = current.next;
			}
			return output;
		} catch (NullPointerException e) {
			return "";
		}
	}

	/**
	 * Inner class defines state and behavior for Cursor object to serve as a
	 * SimpleIterator for the VehicleList. Cursor is initialized to point to list
	 * head at instantiation and provides functionality for checking if there is a
	 * next element and for returning the next element in the list.
	 * 
	 * @author Nick Garner
	 *
	 */
	private class Cursor implements SimpleIterator<Vehicle> {

		/** The node object that traverses the list */
		private Node cursor;

		/**
		 * Creates a new Cursor object and initializes the pointer to the head of the
		 * list.
		 */
		private Cursor() {
			cursor = head;
		}

		/**
		 * Returns true if iterator has more elements to traverse.
		 * 
		 * @return True if the iterator has more elements to traverse.
		 */
		public boolean hasNext() {
//			if (cursor.next == null) {
//				throw new NoSuchElementException();
//			} else {
			try {
				return cursor.next != null;
			} catch (NullPointerException e) {
				return false;
			}
		}

		/**
		 * Gets the next element in the list and pushes the iterator down the list.
		 * 
		 * @return The next element in the list.
		 * @throws NoSuchElementException If there is no next element.
		 */
		public Vehicle next() {
			if (cursor == null) {
				throw new NoSuchElementException();
			}
			if (head.v == null) {
				throw new NoSuchElementException();
			}
			Vehicle data = cursor.v;
			cursor = cursor.next;
			return data;
		}
	}

	/**
	 * Inner class defines state and behavior for elements in the CustomLinkedList.
	 * Each element contains a Vehicle object as its data and a reference to the
	 * next element in the list.
	 * 
	 * @author Nick Garner
	 *
	 */
	private class Node {

		/** The Vehicle data contained in this Node */
		public Vehicle v;
		/** Reference to the next node in the list */
		public Node next;

		/**
		 * Constructs a Node in the list with the specified Vehicle data and a reference
		 * to the next element in the list.
		 * 
		 * @param car  The Vehicle data to contain in this Node.
		 * @param next Pointer to the next element in the list.
		 */
		public Node(Vehicle car, Node next) {
			v = car;
			this.next = next;
		}

	}
}