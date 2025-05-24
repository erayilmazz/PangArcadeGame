
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;



public class RegisterFrame extends JFrame{
	static User user;
	private JTextField nameField;
	private JLabel nameLabel;
	private JPasswordField passwordField;
	private JLabel passwordLabel;
	private JButton registerButton;
	private JButton helpButton;
	public RegisterFrame(){
		super("Register");
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		nameLabel = new JLabel("Name");
		nameLabel.setPreferredSize(new Dimension(120, nameLabel.getPreferredSize().height));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameField = new JTextField(20);
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		
		JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		passwordLabel = new JLabel("Password");
		passwordLabel.setPreferredSize(new Dimension(120, passwordLabel.getPreferredSize().height));
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordField = new JPasswordField(20);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Icon registerIcon = new ImageIcon(getClass().getResource("/resources/login.png"));
		registerButton = new JButton("Register", registerIcon);
		Icon helpIcon = new ImageIcon(getClass().getResource("/resources/help.png"));
		helpButton = new JButton("Help", helpIcon);
		buttonPanel.add(registerButton);
		buttonPanel.add(helpButton);
		
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(namePanel);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(passwordPanel);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(buttonPanel);
		add(mainPanel);
		
		ButtonHandler handler = new ButtonHandler();
		registerButton.addActionListener(handler);
		helpButton.addActionListener(handler);
	}
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String passText = new String(passwordField.getPassword());
			String nameText = new String(nameField.getText());
			if(event.getSource() == registerButton) {
				if (nameText.length() == 0) {
					JOptionPane.showMessageDialog(null, "No name");
				}
				else if(passText.length() == 0) {
					JOptionPane.showMessageDialog(null, "No password");
				}
				else {
					if(checkIsUsernameExist(nameText) == true) {
						JOptionPane.showMessageDialog(null, "Username already exist.");
					}
					else {
						MainFrame.accountActive = true;
						createUser(nameText,passText);
						
					}
				}
			}
		}
	}
	private static boolean checkIsUsernameExist(String username) {
		try {
			File file = new File("src/datas/userdata.csv");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			bufferedReader.readLine();
			String line;
			while((line = bufferedReader.readLine()) != null) {
				String[] splitedLine = line.split(",");
				String lineName = splitedLine[0];
				if (lineName.equals(username)) return true;
			}
			bufferedReader.close();
			fileReader.close();
		}catch (IOException e){
			System.out.println("An error occurred while reading from the file: ");
			e.printStackTrace();
		}
		return false;
	}
	private static void createUser(String name, String password){
		try {
			String content = String.format("%s,%s\n",name,password);
			File file = new File("src/datas/userdata.csv");
			FileWriter fileWriter = new FileWriter(file,true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			if(file.length() == 0) {
				bufferedWriter.write("username,password");
				bufferedWriter.newLine();
			}
			bufferedWriter.write(content);
			bufferedWriter.close();
			fileWriter.close();
		}catch (IOException e){
			System.out.println("An error occurred while writing from the file: ");
		}
		user = new User(name);
	}
}



