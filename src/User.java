import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class User {
	private String username;
	
	public User(String username){
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void saveScore(double score) {
		System.out.println("save score");
		try {
			File file = new File("src/datas/history.csv");
			FileWriter fileWriter = new FileWriter(file,true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			LocalDate localDate = LocalDate.now();
			String date = String.format("%f.%f.%f",localDate.getDayOfMonth(),localDate.getMonth(),localDate.getYear());
			LocalTime localTime = LocalTime.now();
			String time = String.format("%f.%f",localTime.getHour(),localTime.getMinute());
			String text = String.format("%s,%s,%s,%f",getUsername(),date, time,score);
			bufferedWriter.write(text);
			bufferedWriter.close();
			fileWriter.close();
		}catch(IOException e) {
			System.out.println("An error accured while writing to history file");
		}
	}
	private static String getHistory(){
		return "";
	}
}
