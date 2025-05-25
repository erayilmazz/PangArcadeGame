import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SubPanel extends JPanel{
	private GameManager gm;
	JLabel scoreLabel;
	JLabel diffLabel;
	JLabel levelLabel;
	JLabel usernameLabel;
	JLabel highscoreLabel;
	JLabel health;
	public SubPanel(GameManager gm) {
		this.gm = gm;
		setLayout(new GridLayout(2,3,6,6));
		setBackground(Color.BLACK);
		levelLabel = new JLabel("sa");
		scoreLabel = new JLabel("sa");
		usernameLabel = new JLabel("sa");
		health = new JLabel("sa");
		highscoreLabel = new JLabel("sa");
		diffLabel = new JLabel("sa");
		add(levelLabel);
		add(scoreLabel);
		add(usernameLabel);
		add(health);
		add(highscoreLabel);
		add(diffLabel);
	}
	public void modifySubPanel() {
		levelLabel.setText("" + gm.getCurrentLevel());
		health.setText("" + gm.player.getHealthBar());
		scoreLabel.setText("" + gm.getScore());
		diffLabel.setText(gm.getDiff());
	}
}
