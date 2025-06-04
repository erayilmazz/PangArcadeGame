import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutFrame extends JFrame{
	AboutFrame(){
		super("About");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(400, 200);
		setVisible(true);
		setResizable(false);
		setLayout(new GridLayout(4,1));
		getContentPane().setBackground(Color.BLACK);
		String[] lines = {
			"Name: Eray",
			"Surname: Yılmaz",
			"School number: 20220702047",
			"Mail: eray.yilmaz3@std.yeditepe.edu.tr"
		};
		for(String text : lines) {
			JLabel label = new JLabel(text);
			label.setFont(new Font("Monospaced",Font.BOLD,16));
			label.setForeground(Color.WHITE);
			add(label);
		}
		setLocationRelativeTo(null);
	}
}
