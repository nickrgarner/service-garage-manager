package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import edu.ncsu.csc216.garage.model.dealer.Manageable;
import edu.ncsu.csc216.garage.model.dealer.ServiceManager;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Class defines aesthetic and behavior of GUI for ServiceGarage program.
 * Implements and manipulates two main panels, one for a waitlist of Vehicles
 * and one for a list of ServiceBays.
 * 
 * @author Nick Garner
 *
 */
public class RepairGarageGUI extends JFrame implements ActionListener {

	/** ID number for object serialization */
	private static final long serialVersionUID = 1L;
	/** Window title for program */
	private static final String APP_TITLE = "Service Garage Manager";
	/** Instance of ServiceManager for GUI to interact with back-end model */
	private Manageable serviceMgr;
	/** Panel for the list of Vehicles awaiting service */
	private JPanel waitPanel;
	/** Panel for the list of Service Bays */
	private JPanel bayPanel;

	/**
	 * Constructs a new RepairGarageGUI with an empty waitlist and default service
	 * bay list
	 */
	public RepairGarageGUI() {
		this(null);
	}

	/**
	 * Constructs a new RepairGarageGui with waitlist populated from an input string
	 * 
	 * @param s String input to populate the waitlist with
	 */
	public RepairGarageGUI(String s) {
		super();
		String fileName = getFileName();
		if (fileName == null) {
			s = null;
		} else {
			s = readCarFile(fileName);
		}
		if (s == null) {
			serviceMgr = new ServiceManager();
		} else {
			Scanner input = new Scanner(s);
			serviceMgr = new ServiceManager(input);
		}

		// Set up general GUI info
		setSize(750, 500);
		setLocation(585, 290);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Set up waitlist panel
		waitPanel = new WaitPanel();

		// Set up Service Bays panel
		bayPanel = new BayPanel();

		// Add panels to the container
		Container c = getContentPane();
		c.setLayout(new GridLayout());
		c.add(waitPanel);
		c.add(bayPanel);

		// Set the GUI to visible
		setVisible(true);
	}
	
	/**
	 * Defines action to be performed based on the given action event
	 * 
	 * @param e ActionEvent to determine what action to take
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// No actions to define for main panel
	}

	/**
	 * Private helper method to parse a car input file for the GUI constructor
	 * 
	 * @param fileName Path of file to parse for input
	 * @return Returns a concatenated String of the file contents
	 */
	private String readCarFile(String fileName) {
		// Setup scanner, parse through by line and store in String output
		String output = "";
		try {
			output = new String(Files.readAllBytes(Paths.get(fileName)));
			return output;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Creates a new File Chooser dialog to select the vehicle waitlist file to load
	 * into the GUI
	 * 
	 * @return Returns the path of the file to load
	 */
	private String getFileName() {
		JFileChooser fc = new JFileChooser("./"); // Open JFileChoose to current working directory
		fc.setApproveButtonText("Select");
		int returnVal = Integer.MIN_VALUE;
		fc.setDialogTitle("Load Vehicle Waiting List");
		returnVal = fc.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			// If cancel, no file to load
			return null;
		}
		File carFile = fc.getSelectedFile();
		return carFile.getAbsolutePath();
	}

	/**
	 * Main method constructs a new RepairGarageGUI to run the program
	 * 
	 * @param args Arguments for main method
	 */
	public static void main(String[] args) {
		// Start with file chooser, pass txt into String, use to construct GUI
		// If they hit cancel, use null constructor
		new RepairGarageGUI();
	}

	/**
	 * Inner class defines layout and functionality of the Vehicle Waiting List
	 * panel, used to show which Vehicles are currently waiting for service and in
	 * what order
	 * 
	 * @author Nick Garner
	 *
	 */
	private class WaitPanel extends JPanel implements ActionListener {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Button for adding a new Vehicle to the waiting list */
		private JButton btnAddVehicle;
		/** Button for editing the selected Vehicle */
		private JButton btnEditVehicle;
		/** Button for removing the selected Vehicle from the waiting list */
		private JButton btnRemoveVehicle;
		/** Label for text filter */
		private JLabel lblFilter;
		/** Text field for inputting Vehicle filter */
		private JTextField txtFilter;
		/** String object to hold current filter */
		private String filter;
		/** Scrollpane for holding waiting list */
		private JScrollPane paneWaitlist;
		/** JList for displaying waitlist items */
		private JList<String> txtWaitlist;
		/** Backend model for JList */
		private DefaultListModel<String> waitListModel;
		/** Label for btnAddVehicle */
		private static final String ADDLABEL = "Add New Vehicle";
		/** Label for btnEditVehicle */
		private static final String EDITLABEL = "Edit Selected Vehicle";
		/** Label for btnRemoveVehicle */
		private static final String REMOVELABEL = "Remove Selected Vehicle";
		/** Title for panel */
		private static final String WAITTITLE = "Vehicles Awaiting Service";

		/**
		 * Constructs a new WaitPanel with Add, Edit, Remove Vehicle buttons, a text
		 * filter, and a JList to hold the waitlist information.
		 */
		public WaitPanel() {
			super(new BorderLayout());
			this.setBorder(new TitledBorder(WAITTITLE));

			// Instantiate components
			btnAddVehicle = new JButton(ADDLABEL);
			btnAddVehicle.addActionListener(this);
			btnEditVehicle = new JButton(EDITLABEL);
			btnEditVehicle.addActionListener(this);
			btnRemoveVehicle = new JButton(REMOVELABEL);
			btnRemoveVehicle.addActionListener(this);
			lblFilter = new JLabel("Display Filter:");
			txtFilter = new JTextField("");
			txtFilter.addActionListener(this);
			filter = txtFilter.getText();

			// Setup waiting JList
			waitListModel = new DefaultListModel<String>();
			txtWaitlist = new JList<String>(waitListModel);
			txtWaitlist.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			fillWaitModel();
			paneWaitlist = new JScrollPane(txtWaitlist);

			// Setup header panel with buttons and filter
			JPanel pnlButtons = new JPanel();
			pnlButtons.setLayout(new GridLayout(5, 1));
			pnlButtons.add(btnAddVehicle);
			pnlButtons.add(btnEditVehicle);
			pnlButtons.add(btnRemoveVehicle);
			pnlButtons.add(lblFilter);
			pnlButtons.add(txtFilter);

			// Add components
			add(pnlButtons, BorderLayout.NORTH);
			add(paneWaitlist, BorderLayout.CENTER);
		}
		
		/**
		 * Refreshes the list of Service Bays, to be used in ActionPerformed method
		 */
		private void fillWaitModel() {
			while (!waitListModel.isEmpty()) {
				waitListModel.remove(0);
			}
			Scanner input = new Scanner(serviceMgr.printWaitList(filter));
			input.useDelimiter("\n");
			while (input.hasNext()) {
				waitListModel.addElement(input.next());
			}
			input.close();
		}

		/**
		 * Defines action to be performed based on the given action event
		 * 
		 * @param e ActionEvent to determine what action to take
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == txtFilter) {
				filter = txtFilter.getText();
			} else if (e.getSource() == btnAddVehicle) {
				UserDialog pane = new UserDialog();
				pane.setVisible(true);
				Vehicle v = pane.getNewVehicle();
				if (v != null) {
					serviceMgr.putOnWaitingList(v);
				}
			} else if (e.getSource() == btnEditVehicle) {
				try {
					String selected = txtWaitlist.getSelectedValue();
					int hybrid = 0;
					if (selected.substring(0, 1).equals("E")) {
						hybrid = 1;
					}
					String license = selected.substring(12, 22).trim();
					String owner = selected.substring(22).trim();

					UserDialog pane = new UserDialog(license, owner, hybrid);
					pane.setVisible(true);
					Vehicle v = pane.getNewVehicle();
					if (v != null) { // Remove selected Vehicle and re-add at appropriate tier
						serviceMgr.remove(filter, txtWaitlist.getSelectedIndex());
						serviceMgr.putOnWaitingList(v);
					}
				} catch (NullPointerException exp) {
					JOptionPane.showMessageDialog(RepairGarageGUI.this, "No Vehicle selected.");
				}
			} else if (e.getSource() == btnRemoveVehicle) {
				if (txtWaitlist.getSelectedIndex() < 0) {
					JOptionPane.showMessageDialog(RepairGarageGUI.this, "No Vehicle selected.");
				} else {
					serviceMgr.remove(filter, txtWaitlist.getSelectedIndex());
				}
			}
			fillWaitModel();
		}
	}

	/**
	 * Inner class defines layout and functionality of the Service Bay list panel,
	 * used to show which Service Bays are available and which are occupied
	 * 
	 * @author Nick Garner
	 *
	 */
	private class BayPanel extends JPanel implements ActionListener {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Button for adding a new Service Bay */
		private JButton btnAddBay;
		/** Button for filling all empty Service Bays */
		private JButton btnFillBays;
		/** Button for releasing the Vehicle in the selected Service Bay */
		private JButton btnFinishRepair;
		/** Scroll pane to hold BayList */
		private JScrollPane paneBayList;
		/** JList to hold BayList items */
		private JList<String> txtBayList;
		/** Backend model for JList */
		private DefaultListModel<String> bayListModel;
		/** Button to quit the program */
		private JButton btnQuit;
		/** Title for panel */
		private static final String BAYTITLE = "Service Bays";
		/** Label for btnAddBay */
		private static final String ADDTITLE = "Add Service Bay";
		/** Label for btnFillBays */
		private static final String FILLTITLE = "Fill Service Bays";
		/** Label for btnFinishRepair */
		private static final String FINISHTITLE = "Finish Repair of Selected Vehicle";
		/** Label for btnQuit */
		private static final String QUITTITLE = "Quit";

		/**
		 * Constructs a new BayPanel with buttons for Add, Fill, and Release Service
		 * Bays and a JList containing Service Bay information
		 * 
		 */
		public BayPanel() {
			// Construct empty panel
			super(new BorderLayout());
			this.setBorder(new TitledBorder(BAYTITLE));

			// Instantiate components, add event listeners
			btnAddBay = new JButton(ADDTITLE);
			btnAddBay.addActionListener(this);
			btnFillBays = new JButton(FILLTITLE);
			btnFillBays.addActionListener(this);
			btnFinishRepair = new JButton(FINISHTITLE);
			btnFinishRepair.addActionListener(this);
			btnQuit = new JButton(QUITTITLE);
			btnQuit.addActionListener(this);

			// Setup bayList
			bayListModel = new DefaultListModel<String>();
			txtBayList = new JList<String>(bayListModel);
			txtBayList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			fillBayModel();
			paneBayList = new JScrollPane(txtBayList);

			// Setup header panel with buttons
			JPanel pnlButtons = new JPanel();
			pnlButtons.setLayout(new GridLayout(3, 1));
			pnlButtons.add(btnAddBay);
			pnlButtons.add(btnFillBays);
			pnlButtons.add(btnFinishRepair);

			// Add components
			add(pnlButtons, BorderLayout.NORTH);
			add(paneBayList, BorderLayout.CENTER);
			add(btnQuit, BorderLayout.SOUTH);
		}

		/**
		 * Refreshes the list of Service Bays, to be used in ActionPerformed method
		 */
		private void fillBayModel() {
			while (!bayListModel.isEmpty()) {
				bayListModel.remove(0);
			}
			Scanner input = new Scanner(serviceMgr.printServiceBays());
			input.useDelimiter("\n");
			while (input.hasNext()) {
				bayListModel.addElement(input.next());
			}
			input.close();
		}

		/**
		 * Defines action to be performed based on the given action event
		 * 
		 * @param e ActionEvent to determine what action to take
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAddBay) {
				try {
					serviceMgr.addNewBay();
				} catch (IllegalArgumentException exp) {
					JOptionPane.showMessageDialog(RepairGarageGUI.this, exp.getMessage());
				}
			} else if (e.getSource() == btnFillBays) {
				try {
					serviceMgr.fillServiceBays();					
				} catch (IllegalArgumentException exp) {
					JOptionPane.showMessageDialog(RepairGarageGUI.this, exp.getMessage());
				}
			} else if (e.getSource() == btnFinishRepair) {
				try {
					serviceMgr.releaseFromService(txtBayList.getSelectedIndex());
				} catch (IllegalArgumentException exp) {
					JOptionPane.showMessageDialog(RepairGarageGUI.this, "No Service Bay selected.");
				}
			} else if (e.getSource() == btnQuit) {
				System.exit(0);
			}
			fillBayModel();
		}

	}
}