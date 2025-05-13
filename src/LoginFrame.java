import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;



public class LoginFrame extends JFrame{
	private JTextField nameField;
	private JLabel nameLabel;
	private JPasswordField passwordField;
	private JLabel passwordLabel;
	private JButton loginButton;
	private JButton helpButton;
	public LoginFrame(){
		super("Login");
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		setLayout(new FlowLayout());
		
		nameLabel = new JLabel("Name");
		add(nameLabel);
		
		nameField = new JTextField(16);
		add(nameField);
		
		passwordLabel = new JLabel("Password");
		add(passwordLabel);
		
		passwordField = new JPasswordField(16);
		add(passwordField);
		
		Icon loginIcon = new ImageIcon(getClass().getResource("/resources/login.png"));
		loginButton = new JButton("Login", loginIcon);
		add(loginButton);
		
		Icon helpIcon = new ImageIcon(getClass().getResource("/resources/help.png"));
		helpButton = new JButton("Help", helpIcon);
		add(helpButton);
		
		ButtonHandler handler = new ButtonHandler();
		loginButton.addActionListener(handler);
		helpButton.addActionListener(handler);
	}
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getActionCommand().equals("loginIcon")) {
				JOptionPane.showMessageDialog(null, "login");
			}
			else if(event.getActionCommand().equals("helpIcon")) {
				JOptionPane.showMessageDialog(null, "help");
			}
		}
	}
	
}
