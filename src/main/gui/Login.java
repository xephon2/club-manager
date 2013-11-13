package main.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Creates the Login window.
 * @author Stefan Eike (s.eike85@gmail.com)
 */
public class Login extends JFrame {

    /* *****************************************
     * Class variables
     */

    /** Stores the ID of the Login window. */
    private static final long serialVersionUID = -5086216647204736683L;

    /** Stores the frame of the application window. */
    private JFrame frame;

    /** Stores the user name text field. */
    private JTextField userNameTextField;

    /** Stores the password text field. */
    private JTextField passwordTextField;

    /** Stores the RuntimeManager. */
    private main.runtime.RuntimeManager runtimeManager;

    /** Stores the x-coordinate of the application. */
    private final int xApplicationPosition = 100;

    /** Stores the y-coordinate of the application. */
    private final int yApplicationPosition = 100;

    /** Stores the width of the application. */
    private final int applicationWidth = 450;

    /** Stores the height of the application. */
    private final int applicationHeight = 300;

    /** Stores the top inset. */
    private final int topInset = 0;

    /** Stores the bottom inset. */
    private final int bottomInset = 0;

    /** Stores the left inset. */
    private final int leftInset = 5;

    /** Stores the right inset. */
    private final int rightInset = 5;

    /** Defines the number of columns of the user name and password field. */
    private final int textFieldColumns = 10;

    /** Defines the grid column number of the messages. */
    private final int messagesGridColumn = 3;

    /** Defines the grid column number of the OK button. */
    private final int buttonGridColumn = 5;


    /* *****************************************
     * Constructor
     */

    /**
     * Create the application.
     * @param loginRuntimeManager RuntimeManager
     */
    public Login(final main.runtime.RuntimeManager loginRuntimeManager) {
        this.runtimeManager = loginRuntimeManager;
        initialize();
    }


    /* *****************************************
     * Methods
     */

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(
                xApplicationPosition,
                yApplicationPosition,
                applicationWidth,
                applicationHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);

        JLabel loginMessage = new JLabel(
                main.utility.Messages.getString("Messages.LoginMessage"));
        GridBagConstraints gridBagConstraintsLogin = new GridBagConstraints();
        gridBagConstraintsLogin.gridwidth = 2;
        gridBagConstraintsLogin.insets = new Insets(
                topInset,
                leftInset,
                bottomInset,
                rightInset);
        gridBagConstraintsLogin.gridx = 0;
        gridBagConstraintsLogin.gridy = 0;
        frame.getContentPane().add(loginMessage, gridBagConstraintsLogin);

        JLabel userNameMessage = new JLabel(
                main.utility.Messages.getString("Messages.UserName"));
        GridBagConstraints gridBagConstraintsUserNameMessage
                = new GridBagConstraints();
        gridBagConstraintsUserNameMessage.anchor = GridBagConstraints.EAST;
        gridBagConstraintsUserNameMessage.insets = new Insets(
                topInset,
                leftInset,
                bottomInset,
                rightInset);
        gridBagConstraintsUserNameMessage.gridx = 0;
        gridBagConstraintsUserNameMessage.gridy = 2;
        frame.getContentPane().add(userNameMessage,
                gridBagConstraintsUserNameMessage);

        userNameTextField = new JTextField();
        GridBagConstraints userNameField = new GridBagConstraints();
        userNameField.insets = new Insets(
                topInset,
                leftInset,
                bottomInset,
                rightInset);
        userNameField.fill = GridBagConstraints.HORIZONTAL;
        userNameField.gridx = 1;
        userNameField.gridy = 2;
        frame.getContentPane().add(userNameTextField, userNameField);
        userNameTextField.setColumns(textFieldColumns);

        JLabel passwordMessage =
                new JLabel(main.utility.Messages.getString("Messages.Password"));
        GridBagConstraints gridBagConstraintPasswordMessage
        = new GridBagConstraints();
        gridBagConstraintPasswordMessage.anchor = GridBagConstraints.EAST;
        gridBagConstraintPasswordMessage.insets = new Insets(
                topInset,
                leftInset,
                bottomInset,
                rightInset);
        gridBagConstraintPasswordMessage.gridx = 0;
        gridBagConstraintPasswordMessage.gridy = messagesGridColumn;
        frame.getContentPane().add(passwordMessage,
                gridBagConstraintPasswordMessage);

        passwordTextField = new JPasswordField();
        GridBagConstraints passwordField = new GridBagConstraints();
        passwordField.insets = new Insets(
                topInset,
                leftInset,
                bottomInset,
                rightInset);
        passwordField.fill = GridBagConstraints.HORIZONTAL;
        passwordField.gridx = 1;
        passwordField.gridy = messagesGridColumn;
        frame.getContentPane().add(passwordTextField, passwordField);
        passwordTextField.setColumns(textFieldColumns);

        JButton btnOk = new JButton(main.utility.Messages.getString("Messages.OK"));
        GridBagConstraints okButtonField = new GridBagConstraints();
        okButtonField.gridx = 1;
        okButtonField.gridy = buttonGridColumn;
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                login();
            }
        });
        frame.getContentPane().add(btnOk, okButtonField);
    }

    /**
     * @return the Login frame
     */
    public final JFrame getFrame() {
        return this.frame;
    }

    /**
     * @return entered user name
     */
    public final String getUserNameFromTextField() {
        return userNameTextField.getText();
    }

    /**
     * @return entered password
     */
    public final String getPasswordFromPasswordField() {
        return passwordTextField.getText();
    }

    /**
     * Try to login the user.
     */
    public final void login() {
        // If the user is logged in, dispose the login window.
        if (runtimeManager.loginUser(getUserNameFromTextField(),
                getPasswordFromPasswordField())) {
            frame.dispose();
        }
    }
}
