import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

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
		try {
			File file = new File("src/datas/history.csv");
			FileWriter fileWriter = new FileWriter(file,true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			LocalDate localDate = LocalDate.now();
			String date = String.format("%d.%d.%d",localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear());
			LocalTime localTime = LocalTime.now();
			String time = String.format("%d.%d",localTime.getHour(),localTime.getMinute());
			String text = String.format("%s,%s,%s,%.0f\n",getUsername(),date, time,score);
			bufferedWriter.write(text);
			bufferedWriter.close();
			fileWriter.close();
		}catch(IOException e) {
			System.out.println("An error accured while writing to history file");
		}
	}
	public String getHistory(){
		StringBuilder result = new StringBuilder();
		File file = new File("src/datas/history.csv");
		try(Scanner scanner = new Scanner(file)){
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.startsWith(getUsername())) {
					result.append(line).append("\n");
				}
			}
		 }catch (IOException e) {
		        e.printStackTrace();
		 }
		return result.toString();
	}
}
