import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class LauncherFrame extends JFrame{
	
	JLabel logoLabel;
	JPanel midPanel;
	JButton loginButton;
	JButton registerButton;
	JPanel diffucultyPanel;
	JRadioButton novice;
	JRadioButton intermediate;
	JRadioButton advanced;
	JButton startButton;
	
	public LauncherFrame() {
		super("Pang Arcade Game Launcher");
		getContentPane().setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		ImageIcon pangLogo = new ImageIcon(getClass().getResource("/resources/pangOriginal.png"));
		logoLabel = new JLabel(pangLogo);
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(logoLabel, BorderLayout.NORTH);
		
		midPanel = new JPanel();
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		
		ButtonHandler handler = new ButtonHandler();
		loginButton = createMenuButton("LOGIN");
		loginButton.addActionListener(handler);
		
		registerButton = createMenuButton("REGISTER");
		registerButton.addActionListener(handler);
		
		diffucultyPanel = createDifficultyPanel();
		
		midPanel.add(loginButton);
		midPanel.add(registerButton);
		midPanel.add(diffucultyPanel);
		add(midPanel, BorderLayout.CENTER);
		
		startButton = createMenuButton("START GAME");
		startButton.addActionListener(handler);
		add(startButton, BorderLayout.WEST);
		
		
		
		
		
	}
	private JButton createMenuButton(String text) {
		JButton button = new JButton(text);
		return button;
	}
	
	private JPanel createDifficultyPanel() {
		JPanel panel = new JPanel();
		novice = new JRadioButton();
		intermediate = new JRadioButton();
		advanced = new JRadioButton();
		panel.add(novice);
		panel.add(intermediate);
		panel.add(advanced);
		return panel;
	}
	
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//
		}
	}
}
