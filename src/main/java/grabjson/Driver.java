package grabjson;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.List;

public class Driver {
	public static void main(String[] args) throws Exception {
		FileReader filereader = new FileReader("src/main/resources/appid.csv");
		CSVReader csvReader = new CSVReader(filereader);
		List<String[]> data = csvReader.readAll();
		data.remove(0);

		for (String[] d : data){
			for (String query : d){
				System.out.printf("\nGetting appid: %s", query);

				try {
					SteamSpy steamSpy = new SteamSpy(query);
					File file = new File("src/main/resources/json_files/" + query + ".json");

					FileWriter fileWriter = new FileWriter(file);
					fileWriter.write(steamSpy.getJsonObject().toString());
					fileWriter.close();

					System.out.printf("Successfully Copied JSON Object to %s.json", query);
					System.out.println("\nJSON Object: " + steamSpy.getJsonObject().toString());
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
