import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HighScoreFrame extends JFrame{
	JLabel infoText;
	HighScoreFrame(){
		super("High Scores");
		setLayout(new GridLayout(10,1));
	
		File file = new File("src/datas/history.csv");
		List<Map.Entry<String,Double>> entries = new ArrayList<>();
		try(Scanner scanner = new Scanner(file)){
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");
				String username = parts[0];
				double score = Double.valueOf(parts[3]);
				entries.add(Map.entry(username, score));
			}
		 }catch (IOException e) {
		        e.printStackTrace();
		 }
		entries.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
		int count = Math.min(10, entries.size());
		for (int i = 0; i < count; i++) {
			Map.Entry<String, Double> entry = entries.get(i);
			JLabel label = new JLabel(i+1 + ". " + entry.getKey() + " => " + entry.getValue());
			add(label);
        }
		
	}
	
}
