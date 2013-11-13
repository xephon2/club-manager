package main.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import main.club.ClubMember;
import main.runtime.RuntimeManager;
import net.miginfocom.swing.MigLayout;

/**
 * Creates the main application window.
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
     * @param applicationRuntimeManager RuntimeManager
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
        frame.setBounds(xApplicationPosition,
                yApplicationPosition,
                applicationWidth,
                applicationHeight);
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
                "Club Member ID",
                "First name",
                "Last Name",
                "User Name"},
                numberOfColumns);
        table = new JTable(tableModel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setBounds(100, 100, 600, 300);
        frame.add(new JScrollPane(table));
        frame.setVisible(true);

        int i = 0;
        for (ClubMember clubMember : runtimeManager.
                getClub().getClubMembers()) {
            tableModel.insertRow(i, new Object[]{
                    clubMember.getId(),
                    clubMember.getFirstName(),
                    clubMember.getLastName(),
                    clubMember.getUsername()});
        }

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(final TableModelEvent arg0) {
                System.out.println(arg0.getFirstRow());
                System.out.println(arg0.getColumn());
                getTableCellValue(arg0.getFirstRow(), arg0.getColumn());
            }
        });
    }

    /**
     * Get the value of a changed cell.
     * @param rowIndex row index of the table
     * @param colIndex column index of the table
     */
    public final void getTableCellValue(
            final int rowIndex, final int colIndex) {
        String value = table.getModel().getValueAt(
                rowIndex, colIndex).toString();
        System.out.println(value);
        }

    /**
     * Set the value of a table cell.
     * @param obj ?
     * @param rowIndex row index of the table
     * @param colIndex column index of the table
     */
    @SuppressWarnings("unused")
    private void setTableCellValue(
            final Object obj, final int rowIndex, final int colIndex) {
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
}
