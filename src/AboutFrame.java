import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutFrame extends JFrame{
	AboutFrame(){
		super("About");
		setLayout(new GridLayout(4,1));
		add(new JLabel("Name: Eray"));
		add(new JLabel("Surname: Yılmaz"));
		add(new JLabel("School number: 20220702047"));
		add(new JLabel("Mail: eray.yilmaz3@std.yeditepe.edu.tr"));

	}
}
