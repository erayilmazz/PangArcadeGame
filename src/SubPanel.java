import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SubPanel extends JPanel{
	private GameManager gm;
	JLabel scoreLabel;
	JLabel diffLabel;
	JLabel levelLabel;
	JLabel usernameLabel;
	JLabel highscoreLabel;
	JPanel healthPanel;
	private ResourceManager resourceManager;
	public SubPanel(GameManager gm) {
		this.gm = gm;
		resourceManager = new ResourceManager();
		resourceManager.loadResources();
		setLayout(new GridLayout(2,3,6,6));
		setBackground(Color.BLACK);
		levelLabel = new JLabel("sa");
		scoreLabel = new JLabel("sa");
		usernameLabel = new JLabel("sa");
		highscoreLabel = new JLabel("sa");
		diffLabel = new JLabel("sa");
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
		levelLabel.setText("" + gm.getCurrentLevel());
		scoreLabel.setText("" + gm.getTotalScore() + gm.getScore());
		diffLabel.setText(gm.getDiff());
		usernameLabel.setText(MainFrame.user.getUsername());	
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
	}
}
