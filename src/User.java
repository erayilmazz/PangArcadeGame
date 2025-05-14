import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class User {
	private String username;
	//private History userHistory
	User(String username){
		this.username = username;
	}
	private void saveScore(double score) {
		try {
			File file = new File("/files/history.txt");
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			String text = String.format(username,"|",date,"|", time, "|", score);
			bufferedWriter.write(text);
			bufferedWriter.close();
			fileWriter.close();
		}catch(IOException e) {
			System.out.println("An error accured while writing to history file");
		}
	}
}
