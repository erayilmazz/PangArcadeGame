import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class HistoryFrame extends JFrame{
	private String info = "";
	JLabel infoText;
	HistoryFrame(User user){
		super("History");
		setLayout(new GridLayout(10,1));
		String history = user.getHistory();
        if (history.isEmpty()) {
            add(new JLabel("No history found for user: " + user.getUsername()));
        } else {
            String[] lines = history.split("\n");
            for (String line : lines) {
            	String[] l = line.split(",");
            	String s = String.format("Date:" + l[1] + ", Time:" + l[2] + ", Score:" + l[3] + "\n");
                JLabel label = new JLabel(s);
                //label.setFont(new Font("Monospaced", Font.PLAIN, 14));
                add(label);
            }
        }
	}
	
}
