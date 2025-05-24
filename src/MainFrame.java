import java.awt.BorderLayout;
import javax.swing.JOptionPane;
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
	public static boolean accountActive = false;
	public static boolean diffSelected = false;
	public static boolean gameActive = false;
	public static String diff;
	GameManager gm;
	//SubPanel subPanel = new SubPanel();
	public MainFrame() {
		super("Pang");
		setLayout(new BorderLayout());
		createMenuBar();
		addKeyListener(this);
		
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
		
		
		
		MenuBarHandler handler = new MenuBarHandler();
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
	private class MenuBarHandler implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == newItem) {
				if(!accountActive) {
					JOptionPane.showMessageDialog(null, "Please log in","Error",JOptionPane.INFORMATION_MESSAGE);
				}
				else if (!diffSelected) {
					JOptionPane.showMessageDialog(null, "Please choose diff","Error",JOptionPane.INFORMATION_MESSAGE);
				}
				else if(accountActive && diffSelected) {
					gm = new GameManager(diff);
					GamePanel gamePanel = new GamePanel(gm);
					add(gamePanel,BorderLayout.CENTER);
					revalidate();
					repaint();
					gm.setGamePanel(gamePanel);
					gm.startGame();
					gameActive = true;
				}
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
			else if(event.getSource() == quitItem) {
				System.exit(0);
			}
			else if(event.getSource() == noviceItem) {
				diff = "novice";
				diffSelected = true;	
			}
			else if(event.getSource() == intermediateItem) {
				diff = "intermediate";
				diffSelected = true;	
			}
			else if(event.getSource() == advancedItem) {
				diff = "advanced";
				diffSelected = true;	
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
		else if (key == KeyEvent.VK_SPACE) {
			gm.createArrow();
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
