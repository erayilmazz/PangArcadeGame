import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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



public class LoginFrame extends JFrame{
	private JTextField nameField;
	private JLabel nameLabel;
	private JPasswordField passwordField;
	private JLabel passwordLabel;
	private JButton loginButton;
	private JButton helpButton;
	public LoginFrame(){
		super("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(400, 200);
		setVisible(true);
		setLocationRelativeTo(null);
		
		
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
		Icon loginIcon = new ImageIcon(getClass().getResource("/resources/login.png"));
		loginButton = new JButton("Login", loginIcon);
		Icon helpIcon = new ImageIcon(getClass().getResource("/resources/help.png"));
		helpButton = new JButton("Help", helpIcon);
		buttonPanel.add(loginButton);
		buttonPanel.add(helpButton);
		
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(namePanel);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(passwordPanel);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(buttonPanel);
		add(mainPanel);
		
		ButtonHandler handler = new ButtonHandler();
		loginButton.addActionListener(handler);
		helpButton.addActionListener(handler);
	}
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String passText = new String(passwordField.getPassword());
			String nameText = new String(nameField.getText());
			if(event.getSource() == loginButton) {
				if (nameText.length() == 0) {
					JOptionPane.showMessageDialog(null, "No name");
				}
				else if(passText.length() == 0) {
					JOptionPane.showMessageDialog(null, "No password");
				}else {
					if(checkIsUsernameExist(nameText) == false) {
						JOptionPane.showMessageDialog(null, "User name not exist");
					}
					else if(checkIsPasswordTrue(nameText, passText) == false) {
						JOptionPane.showMessageDialog(null, "Wrong password");
					}
					else {
						MainFrame.accountActive = true;
						MainFrame.user = new User(nameText);
						int ok = JOptionPane.showConfirmDialog(null, "Logged in","",JOptionPane.DEFAULT_OPTION);
						if(ok == JOptionPane.OK_OPTION) dispose();
					}
					
				}
				//daha önce bu kullanıcı kayıtlı mı kontrolü
				//kayıtlıysa şifresi doğru mu kontrol et
				//uygun şifre kontrolü
				//oyun başlatma ekranı ve useri texte kaydetme
				
			}
			else if(event.getSource() == helpButton) {
				int ok = JOptionPane.showConfirmDialog(null, "If you don't have an account, register","",JOptionPane.DEFAULT_OPTION);
				if(ok == JOptionPane.OK_OPTION) {
					new RegisterFrame();
					dispose();
				}
				
			}
		}
	}
	private boolean checkIsUsernameExist(String username) {
		try {
			File file = new File("src/datas/userdata.csv");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
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
		}
		return false;
	}
	private boolean checkIsPasswordTrue(String username, String password) {
		try {
			File file = new File("src/datas/userdata.csv");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null) {
				String[] splitedLine = line.split(",");
				String lineName = splitedLine[0];
				String linePassword = splitedLine[1];
				if (lineName.equals(username)) {
					if(linePassword.equals(password)) return true;
				}
			}
			bufferedReader.close();
			fileReader.close();
		}catch (IOException e){
			System.out.println("An error occurred while reading from the file: ");
		}
		return false;
	}
	
}
