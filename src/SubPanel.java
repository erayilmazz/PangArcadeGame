import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SubPanel extends JPanel{
	private GameManager gm;
	JLabel scoreLabel;
	JLabel diffLabel;
	JLabel levelLabel;
	JLabel usernameLabel;
	JLabel highscoreLabel;
	JPanel healthPanel;
	private ResourceManager resourceManager;
	private final String[] levelName = {"ANTALYA","ANKARA","IZMIR","ISTANBUL"};	
	public SubPanel(GameManager gm) {
		this.gm = gm;
		resourceManager = new ResourceManager();
		resourceManager.loadResources();
		setLayout(new GridLayout(2,3,6,6));
		setBackground(Color.BLACK);
		levelLabel = new JLabel();
		levelLabel.setFont(new Font("Monospaced",Font.BOLD,18));
		levelLabel.setForeground(Color.GRAY);
		levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		levelLabel.setVerticalAlignment(SwingConstants.CENTER);
		scoreLabel = new JLabel();
		scoreLabel.setFont(new Font("Monospaced",Font.BOLD,18));
		scoreLabel.setForeground(Color.GRAY);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setVerticalAlignment(SwingConstants.CENTER);
		usernameLabel = new JLabel();
		usernameLabel.setFont(new Font("Monospaced",Font.BOLD,18));
		usernameLabel.setForeground(Color.GRAY);
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setVerticalAlignment(SwingConstants.CENTER);
		highscoreLabel = new JLabel();
		highscoreLabel.setFont(new Font("Monospaced",Font.BOLD,18));
		highscoreLabel.setForeground(Color.GRAY);
		highscoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		highscoreLabel.setVerticalAlignment(SwingConstants.TOP);
		diffLabel = new JLabel();
		diffLabel.setFont(new Font("Monospaced",Font.BOLD,18));
		diffLabel.setForeground(Color.GRAY);
		diffLabel.setHorizontalAlignment(SwingConstants.CENTER);
		diffLabel.setVerticalAlignment(SwingConstants.TOP);
		healthPanel = new JPanel();
		healthPanel.setBackground(Color.BLACK);
		healthPanel.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
		add(levelLabel);
		add(scoreLabel);
		add(usernameLabel);
		add(healthPanel);
		add(highscoreLabel);
		add(diffLabel);
	}
	public void modifySubPanel() {
		if(!gm.isScoreScreen() && !gm.isGameOver()) {
			for (Component c : getComponents()) {
		        c.setVisible(true);
			}
			revalidate();
			repaint();
			levelLabel.setText("LEVEL " + gm.getCurrentLevel() + ": " + levelName[gm.getCurrentLevel() - 1]);
			scoreLabel.setText("SCORE: " + (gm.getTotalScore() + gm.getScore()));
			diffLabel.setText("DIFF: " + gm.getDiff().toUpperCase(Locale.ENGLISH));
			usernameLabel.setText("USERNAME: " + MainFrame.user.getUsername());	
			highscoreLabel.setText("HIGH-SCORE: " + (int) HighScoreFrame.getHighScore());
			healthPanel.removeAll();
			ImageIcon arrowIcon = new ImageIcon(resourceManager.getArrowImage(gm.getArrowType()));
			JLabel arrowLabel = new JLabel(arrowIcon);
			healthPanel.add(arrowLabel);
			ImageIcon healthIcon = new ImageIcon(resourceManager.healthImage);
			for(int i = 0; i < gm.player.getHealthBar(); i++) {
				if(i == 6) {
					JLabel plus = new JLabel("+");
					healthPanel.add(plus);
					break;
				}
				JLabel health = new JLabel(healthIcon);
				healthPanel.add(health);
			}
			healthPanel.revalidate();
			healthPanel.repaint();
		}else {
			 for (Component c : getComponents()) {
			        c.setVisible(false);
			 }
			 revalidate();
			 repaint();
		}
	}
}
