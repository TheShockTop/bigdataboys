package grabjson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class SteamSpy {
	private static final String PROTOCOL = "https";
	private static final String HOST = "steamspy.com";
	private static final String API = "/api.php?request=appdetails&appid=";
	private JSONObject jsonObject;

	public SteamSpy(Integer query) throws MalformedURLException, IOException, JSONException {
		this.jsonObject = fetchJSONFile(createURL(query.toString()));
	}

	public SteamSpy(String query) throws MalformedURLException, IOException, JSONException {
		this.jsonObject = fetchJSONFile(createURL(query));
	}

	private URL createURL(String query) throws MalformedURLException {
		return new URL(PROTOCOL, HOST, API + query);
	}

	private JSONObject fetchJSONFile(URL url) throws IOException, JSONException {
		InputStream jsonFile = url.openStream();

		try {
			BufferedReader read = new BufferedReader(new InputStreamReader(jsonFile, Charset.forName("UTF-8")));
			String jsonText = readAll(read);
			return new JSONObject(jsonText);
		} finally {
			jsonFile.close();
		}
	}

	private static String readAll(BufferedReader read) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = read.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public JSONObject getJsonObject(){
		return this.jsonObject;
	}
}
