import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HistoryFrame extends JFrame{
	private String info = "";
	JLabel infoText;
	HistoryFrame(User user){
		super("History");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBackground(Color.BLACK);
		String history = user.getHistory();
        if (history.isEmpty()) {
            add(new JLabel("No history found for user: " + user.getUsername()));
        } else {
            String[] lines = history.split("\n");
            for (String line : lines) {
            	String[] l = line.split(",");
            	String s = String.format("Date:" + l[1] + ", Time:" + l[2] + ", Score:" + l[3] + "\n");
                JLabel label = new JLabel(s);
                label.setFont(new Font("Monospaced",Font.BOLD,16));
                label.setForeground(Color.WHITE);
                panel.add(label);
            }
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);
	}
	
}
