import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame implements KeyListener{
	GameManager gm = new GameManager();
	GamePanel gamePanel = new GamePanel(gm);
	//SubPanel subPanel = new SubPanel();
	public MainFrame() {
		super("Pang");
		setLayout(new BorderLayout());
		createMenuBar();
		add(gamePanel,BorderLayout.CENTER);
		gm.setGamePanel(gamePanel);
		addKeyListener(this);
		gm.startGameLoop();
		
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
				loginFrame.setSize(400, 200);
				loginFrame.setVisible(true);
			}
			else if(event.getSource() == registerItem) {
				RegisterFrame registerFrame = new RegisterFrame();
				registerFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				registerFrame.setSize(400, 200);
				registerFrame.setVisible(true);
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			gm.player.setDirection("left");
			gm.player.goLeft(5);
		}
		else if (key == KeyEvent.VK_RIGHT) {
			gm.player.setDirection("right");
			gm.player.goRight(5);
		}
		
	}
	@Override
    public void keyReleased(KeyEvent e) {
        gm.player.setDirection("none");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
	
}
