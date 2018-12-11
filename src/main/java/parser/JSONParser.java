package parser;

import com.opencsv.CSVWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
	public static void main(String[] args) throws IOException {
		ArrayList<Game> gameArrayList = parseJSON();
		writeToFile(gameArrayList);
	}

	private static ArrayList<Game> parseJSON() throws IOException {
		String line = new BufferedReader(new FileReader(new File("src/main/resources/appid.json"))).readLine();

		JSONObject jsonObject = new JSONObject(line);
		JSONArray apps = jsonObject.getJSONObject("applist").getJSONArray("apps");

		ArrayList<Game> games = new ArrayList<>();

		for(int i = 0; i < apps.length(); i++){
			games.add(new Game(apps.getJSONObject(i).getInt("appid"), apps.getJSONObject(i).getString("name")));
		}

		return games;
	}

	private static void writeToFile(ArrayList<Game> gameArrayList){
		File file = new File("src/main/resources/appid.csv");
		try {
			FileWriter outputCSVFile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputCSVFile);
			List<String[]> data = new ArrayList<>();

			data.add(new String[] {"appid"});

			for(int i = 0; i < gameArrayList.size(); i++){
				data.add(new String[] {gameArrayList.get(i).appID.toString()});
			}

			writer.writeAll(data);
			writer.close();
			outputCSVFile.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}