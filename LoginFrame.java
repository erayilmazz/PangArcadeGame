import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame{
	private JTextField nameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton helpButton;
	
	public LoginFrame(){
		super("Login");
		setLayout(new FlowLayout());
		nameField = new JTextField(16);
		nameField.setText("Name");
		add(nameField);
		passwordField = new JPasswordField();
		passwordField.setText("Password");
		add(passwordField);
		Icon loginIcon = new ImageIcon(getClass().getResource("login.jpg"));
		loginButton = new JButton("Login", loginIcon);
		add(loginButton);
		Icon helpIcon = new ImageIcon(getClass().getResource("help.jpg"));
		helpButton = new JButton("Help", helpIcon);
		add(helpButton);
		ButtonHandler handler = new ButtonHandler();
		loginButton.addActionListener(handler);
		helpButton.addActionListener(handler);
	}
}
