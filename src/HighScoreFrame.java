import java.awt.Color;
import java.awt.Font;
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
	public HighScoreFrame(){
		super("High Scores");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(400, 200);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(10,1));
		getContentPane().setBackground(Color.BLACK);
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
			label.setFont(new Font("Monospaced",Font.BOLD,16));
            label.setForeground(Color.WHITE);
			add(label);
        }	
	}
	public static double getHighScore() {
		File file = new File("src/datas/history.csv");
		double max = 0;
		try(Scanner scanner = new Scanner(file)){
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");
				double score = Double.valueOf(parts[3]);
				if(score > max) max = score;
			}
		 }catch (IOException e) {
		        e.printStackTrace();
		 }
		return max;
	}
}
