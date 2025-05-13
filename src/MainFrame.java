import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame{
	public MainFrame() {
		super("Pang");
		createMenuBar();
	}
	
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenu optionsMenu;
	private JMenu helpMenu;
	JMenuItem newItem;
	JMenuItem loginItem;
	JMenuItem registerItem;
	JMenuItem quitItem;
	JMenuItem historyItem;
	JMenuItem scoresItem;
	JMenuItem difficultyItem;
	JMenuItem aboutItem;
	
	private void createMenuBar() {
		menuBar = new JMenuBar();
		gameMenu = new JMenu("Game");
		optionsMenu = new JMenu("Options");
		helpMenu = new JMenu("Help");

		newItem = new JMenuItem("New");
		loginItem = new JMenuItem("Login");
		registerItem = new JMenuItem("Register");
		quitItem = new JMenuItem("Quit");
		historyItem = new JMenuItem("History");
		scoresItem = new JMenuItem("View high scores");
		difficultyItem = new JMenuItem("Choose difficulty");
		aboutItem = new JMenuItem("About");
		
		MenuBarHandler handler = new MenuBarHandler();
		newItem.addActionListener(handler);
		loginItem.addActionListener(handler);
		registerItem.addActionListener(handler);
		quitItem.addActionListener(handler);
		historyItem.addActionListener(handler);
		scoresItem.addActionListener(handler);
		difficultyItem.addActionListener(handler);
		aboutItem.addActionListener(handler);
		
		gameMenu.add(newItem);
		gameMenu.add(loginItem);
		gameMenu.add(registerItem);
		gameMenu.add(quitItem);
		optionsMenu.add(historyItem);
		optionsMenu.add(scoresItem);
		optionsMenu.add(difficultyItem);
		helpMenu.add(aboutItem);
		
		menuBar.add(gameMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);	
		setJMenuBar(menuBar);
	}
	private class MenuBarHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == newItem) {
				System.out.println("Oyun başla");
			}
			else if(event.getSource() == loginItem) {
				LoginFrame loginFrame = new LoginFrame();
				loginFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				loginFrame.setSize(400, 300);
				loginFrame.setVisible(true);
			}
		}
		
	}
	
}
