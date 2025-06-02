import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame implements KeyListener{
	public static boolean accountActive = false;
	public static boolean diffSelected = false;
	public static boolean gameActive = false;
	public static String diff;
	GameManager gm;
	GamePanel gamePanel;
	SubPanel subPanel;
	public static User user;
	JLabel label;
	MenuBarHandler handler;
	//SubPanel subPanel = new SubPanel();
	public MainFrame() {
		super("Pang");
		setLayout(new BorderLayout());
		handler = new MenuBarHandler();
		ImageLoader id = new ImageLoader();
		Image pang = id.loadImage("/resources/pang.png",Color.WHITE);
		ImageIcon icon = new ImageIcon(pang);
		label = new JLabel(icon);
		add(label, BorderLayout.CENTER);
		createInvisibleButtons();
		createMenuBar();
		addKeyListener(this);
		setFocusable(true);
		requestFocusInWindow();
	}
	JButton startGameButton, loginButton, registerButton, noviceButton, intermediateButton, advancedButton;
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
	JMenu difficultyMenu;
	JMenuItem noviceItem;
	JMenuItem intermediateItem;
	JMenuItem advancedItem;
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
		difficultyMenu = new JMenu("Choose difficulty");
		noviceItem = new JMenuItem("Novice");
		intermediateItem = new JMenuItem("Intermediate");
		advancedItem = new JMenuItem("Advanced");
		aboutItem = new JMenuItem("About");
		
		
	
		newItem.addActionListener(handler);
		loginItem.addActionListener(handler);
		registerItem.addActionListener(handler);
		quitItem.addActionListener(handler);
		historyItem.addActionListener(handler);
		scoresItem.addActionListener(handler);
		noviceItem.addActionListener(handler);
		intermediateItem.addActionListener(handler);
		advancedItem.addActionListener(handler);
		aboutItem.addActionListener(handler);
		
		gameMenu.add(newItem);
		gameMenu.add(loginItem);
		gameMenu.add(registerItem);
		gameMenu.add(quitItem);
		optionsMenu.add(historyItem);
		optionsMenu.add(scoresItem);
		difficultyMenu.add(noviceItem);
		difficultyMenu.add(intermediateItem);
		difficultyMenu.add(advancedItem);
		optionsMenu.add(difficultyMenu);
		helpMenu.add(aboutItem);
		
		menuBar.add(gameMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);	
		setJMenuBar(menuBar);
	}
	private void createInvisibleButtons() {
		label.setLayout(null);
		
		loginButton = new JButton();
		loginButton.setBounds(300, 250, 180, 30);
		loginButton.addActionListener(handler);
		makeInvisible(loginButton);
		label.add(loginButton);
		
		registerButton = new JButton();
		registerButton.setBounds(270, 305, 230, 30);
		registerButton.addActionListener(handler);
		makeInvisible(registerButton);
		label.add(registerButton);
		
		noviceButton = new JButton();
		noviceButton.setBounds(100, 405, 130, 30);
		noviceButton.addActionListener(handler);
		makeInvisible(noviceButton);
		label.add(noviceButton);
		
		intermediateButton = new JButton();
		intermediateButton.setBounds(255, 405, 230, 30);
		intermediateButton.addActionListener(handler);
		makeInvisible(intermediateButton);
		label.add(intermediateButton);
		
		advancedButton = new JButton();
		advancedButton.setBounds(510, 405, 170, 30);
		advancedButton.addActionListener(handler);
		makeInvisible(advancedButton);
		label.add(advancedButton);
		
		startGameButton = new JButton();
		startGameButton.setBounds(230, 465, 300, 30);
		startGameButton.addActionListener(handler);
		makeInvisible(startGameButton);
		label.add(startGameButton);
	}
	private void makeInvisible(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	private class MenuBarHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == newItem || event.getSource() == startGameButton) {
				if(gameActive) {
					remove(gamePanel);
					remove(subPanel);
					gameActive = false;
				}
				if(!accountActive) {
					int ok = JOptionPane.showConfirmDialog(null, "Please log in","Error",JOptionPane.DEFAULT_OPTION);
					if(ok == JOptionPane.OK_OPTION) new LoginFrame();
				}
				else if (!diffSelected) {
					JOptionPane.showMessageDialog(null, "Please choose diff","Error",JOptionPane.INFORMATION_MESSAGE);
				}
				else if(accountActive && diffSelected) {
					remove(label);
					gm = new GameManager(diff);
					gamePanel = new GamePanel(gm);
					subPanel = new SubPanel(gm);
					add(gamePanel,BorderLayout.CENTER);
					add(subPanel, BorderLayout.SOUTH);
					subPanel.setPreferredSize(new Dimension(782,123));
					gm.setGamePanel(gamePanel);
					gm.setSubPanel(subPanel);
					gm.startGame();
					revalidate();
					repaint();
					gameActive = true;
					gamePanel.setFocusable(true);
					gamePanel.requestFocusInWindow();
					gamePanel.addKeyListener(MainFrame.this);
				}
			}
			else if(event.getSource() == loginItem || event.getSource() == loginButton) {
				new LoginFrame();
			}
			else if(event.getSource() == registerItem || event.getSource() == registerButton) {
				new RegisterFrame();
			}
			else if(event.getSource() == quitItem) {
				System.exit(0);
			}
			else if(event.getSource() == noviceItem || event.getSource() == noviceButton) {
				diff = "novice";
				diffSelected = true;	
			}
			else if(event.getSource() == intermediateItem || event.getSource() == intermediateButton) {
				diff = "intermediate";
				diffSelected = true;	
			}
			else if(event.getSource() == advancedItem || event.getSource() == advancedButton) {
				diff = "advanced";
				diffSelected = true;	
			}
			else if(event.getSource() == historyItem) {
				if(!accountActive) {
					int ok = JOptionPane.showConfirmDialog(null, "Please log in","Error",JOptionPane.DEFAULT_OPTION);
					if(ok == JOptionPane.OK_OPTION) new LoginFrame();
				}else {
					new HistoryFrame(user);
				}
				
			}
			else if(event.getSource() == scoresItem) {
				new HighScoreFrame();
			}
			else if(event.getSource() == aboutItem) {
				new AboutFrame();
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(gm !=null && !gm.isGamePaused && gameActive) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_LEFT) {
				gm.player.setDirection("left");
			}
			else if (key == KeyEvent.VK_RIGHT) {
				gm.player.setDirection("right");
			}
			else if (key == KeyEvent.VK_SPACE) {
				gm.createArrow();
			}
		}
			
	}
	@Override
    public void keyReleased(KeyEvent e) {
		if(gameActive && gm !=null && !gm.isGamePaused ) gm.player.setDirection("none");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    public void setUser(User user) {
		this.user = user;
	}
	
}
