package main.java.gui;

import main.java.club.ClubMember;
import main.java.club.Club.clubMemberProperties;
import main.java.runtime.RuntimeManager;

/**
 * Creates the main application window.
 * 
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class Application {

	/* *****************************************
	 * Class variables
	 */

	/** Stores the application windows. */
	private JFrame frame;

	/** Stores the instance of the RuntimeManager. */
	// TODO refactor
	private RuntimeManager runtimeManager;

	/** Stores the JTable. */
	private static JTable table;

	/** Stores the x-coordinate of the application. */
	private final int xApplicationPosition = 100;

	/** Stores the y-coordinate of the application. */
	private final int yApplicationPosition = 100;

	/** Stores the width of the application. */
	private final int applicationWidth = 450;

	/** Stores the height of the application. */
	private final int applicationHeight = 300;

	/** Stores the number of columns of the club member table. */
	private final int numberOfColumns = 4;

	/* *****************************************
	 * Constructor
	 */

	/**
	 * Create the application and store the RuntimeManager.
	 * 
	 * @param applicationRuntimeManager
	 *            RuntimeManager
	 */
	public Application(final RuntimeManager applicationRuntimeManager) {
		this.runtimeManager = applicationRuntimeManager;
		initialize();
	}

	/* *****************************************
	 * Getter and Setter
	 */

	/**
	 * Return the JFrame of the application window.
	 * 
	 * @return frame
	 */
	public final JFrame getFrame() {
		return frame;
	}

	/* *****************************************
	 * Methods
	 */

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(xApplicationPosition, yApplicationPosition,
				applicationWidth, applicationHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				exitApplication();
			}
		});
		mnFile.add(mntmExit);

		JMenu mnAction = new JMenu("Action");
		menuBar.add(mnAction);

		JMenuItem mntmShowClub = new JMenuItem("Show Club");
		mnAction.add(mntmShowClub);
		mntmShowClub.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				createClubTable();
			}
		});
	}

	/**
	 * Create the table for all club members.
	 */
	private void createClubTable() {
		DefaultTableModel tableModel = new DefaultTableModel(new Object[] {
				"Club Member ID", "First name", "Last Name", "User Name" },
				numberOfColumns);
		table = new JTable(tableModel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JScrollPane(table));
		frame.setVisible(true);

		int i = 0;
		for (ClubMember clubMember : runtimeManager.getClub().getClubMembers()) {
			tableModel.insertRow(i, new Object[] {
					clubMember.getClubMemberId(), clubMember.getFirstName(),
					clubMember.getLastName(), clubMember.getUsername() });
		}

		tableModel.addTableModelListener(new TableModelListener() {

			/**
			 * Create a listener for the club table. If the user changes a table
			 * entry, the row/column index is used to get the value and send it
			 * to an observer.
			 */
			@Override
			public void tableChanged(final TableModelEvent tableCellEvent) {
				handleTableCellEvent(tableCellEvent);
			}
		});
	}

	/**
	 * Handle events that are thrown by changing the value of an arbitrary table
	 * cell.
	 * 
	 * @param tableCellEvent
	 *            the cell change event
	 */
	private void handleTableCellEvent(final TableModelEvent tableCellEvent) {
		System.out.println("handleTableCellEvent()");
		int rowIndexOfChangedCell = tableCellEvent.getFirstRow();
		int columnIndexOfChangedCell = tableCellEvent.getColumn();
		System.out.println("The cell in row(" + rowIndexOfChangedCell + ") "
				+ ", column(" + columnIndexOfChangedCell
				+ ") has been changed.");

		/*
		 * If the changed cell is in the first column (club member ID), create a
		 * new club member.
		 */
		if (tableCellEvent.getColumn() == 0) {
			try {
				int tableClubMemberId = convertStringToInt(getTableCellValue(
						rowIndexOfChangedCell, 0));
				createNewClubMemberWithTableCellId(tableClubMemberId);
			} catch (NullPointerException e) {
				System.out.println("Club member ID is invalid");
			}
		} else {

			/*
			 * If the changed cell is not in the first column (club member ID),
			 * try to add or change the properties of an existing club member.
			 */
			try {
				int tableClubMemberId = convertStringToInt(getTableCellValue(
						rowIndexOfChangedCell, 0));

				clubMemberProperties propertyType = getPropertyTypeFromColumnIndex(columnIndexOfChangedCell);

				String propertyValue = getTableCellValue(rowIndexOfChangedCell, // row-index
						columnIndexOfChangedCell // column-index
				);

				this.runtimeManager.getClub().addOrChangeClubMemberProperties(
						tableClubMemberId, propertyType, propertyValue);

			} catch (NullPointerException e) {
				System.out.println("Property of club member"
						+ "cannot be changed.");
			}
		}
	}

	/**
	 * Get the value of a changed cell.
	 * 
	 * @param rowIndex
	 *            row index of the table
	 * @param colIndex
	 *            column index of the table
	 * @return table cell value
	 */
	public final String getTableCellValue(final int rowIndex, final int colIndex) {
		String value = table.getModel().getValueAt(rowIndex, colIndex)
				.toString();
		return value;
	}

	/**
	 * Set the value of a table cell.
	 * 
	 * @param obj
	 *            table cell
	 * @param rowIndex
	 *            row index of the table
	 * @param colIndex
	 *            column index of the table
	 */
	@SuppressWarnings("unused")
	private void setTableCellValue(final Object obj, final int rowIndex,
			final int colIndex) {
		table.getModel().setValueAt(obj, rowIndex, colIndex);
		System.out.println("Value is added");
	}

	/**
	 * Exit the application.
	 */
	private void exitApplication() {
		frame.dispose();
		System.exit(0);
	}

	/**
	 * Convert a String to an Integer.
	 * 
	 * @param string
	 *            input string
	 * @return output integer
	 */
	private int convertStringToInt(final String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			System.out.println("Cannot convert String to Integer.");
			return 0;
		}
	}

	/**
	 * Create a new clubMember, when the ID cell of the table is filled.
	 * 
	 * @param clubMemberId
	 *            clubMemberId from table cell
	 */
	private void createNewClubMemberWithTableCellId(final int clubMemberId) {
		System.out.println("createNewClubMemberWithTableCellId(): "
				+ "the mandatory ID cell has been filled. "
				+ "Try to create a new club member.");
		ClubMember newClubMember = new ClubMember(clubMemberId) {
		};
		runtimeManager.getClub().addClubMember(newClubMember);
	}

	/**
	 * Receive the index of a changed table column. Use the index to get the
	 * property type of the column and return it as a clubMemberProperties
	 * object.
	 * 
	 * @param tableColumnIndex
	 *            index of the changed table column
	 * @return type of the changed property
	 */
	private clubMemberProperties getPropertyTypeFromColumnIndex(
			final int tableColumnIndex) {
		switch (tableColumnIndex) {
		case 1:
			return clubMemberProperties.FIRSTNAME;
		case 2:
			return clubMemberProperties.LASTNAME;
		case 3:
			return clubMemberProperties.USERNAME;
		default:
			return null;
		}
	}
}
