package parse_files;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;

public class CheckFiles {

	public static void main(String[] args) throws Exception {
		ArrayList<String[]> information = checkFiles();
		BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/cleanup.file"));

		for (String[] str : information) {
			for (String st : str) {
				writer.write(st + " ");
			}
			writer.write("\n");
		}
		writer.close();
	}

	public static ArrayList<String[]> checkFiles() throws Exception {
		File dir = new File("src/main/resources/json_files");
		File[] directoryListing = dir.listFiles();
		ArrayList<String[]> tempHolder = new ArrayList<>();

		if (directoryListing != null) {
			for (File child : directoryListing) {
				JSONParser parser = new JSONParser();

				try {

					Object obj = parser.parse(new FileReader(child));
					JSONObject jsonObject = (JSONObject) obj;
					String initialPrice = (String) jsonObject.get("initialprice");
					Long userScore = (Long) jsonObject.get("userscore");
					tempHolder.add(new String[] {initialPrice, Long.toString(userScore)});

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new Exception();
		}

		return tempHolder;
	}
}
