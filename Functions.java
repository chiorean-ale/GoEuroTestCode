import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.io.FileUtils;

public class Functions {

	public static JSONArray getJson(String url){

		InputStream is = getInputStream(url);
		String result = getString(is);
		JSONArray jsonArray = getJSON(result);
		if (jsonArray == null){
			return new JSONArray();
		} else {
			return jsonArray;
		}

	}

	public static void createCsv(JSONArray myJson){
		JSONArray monJason = parseJSON(myJson);
		getCsv(monJason);
	}
	// HTTP
	public static InputStream getInputStream(String url){

		InputStream is = null;

		try {	    	

			HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			return is;
		} catch(Exception e) {
			return null;
		}
	}

	public static String getString(InputStream is){

		String result = "";

		// Read response to string
		try {	    	
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			return result;
		} catch(Exception e) {
			return null;
		}
	}


	public static JSONArray getJSON(String result){

		JSONArray jsonArray = null;
		// Convert string to object
		try {
			jsonArray = new JSONArray(result);
			return jsonArray;
		} catch(Exception e){
			return null;
		}

	}

	public static JSONArray parseJSON(JSONArray jsonArray) {
		JSONArray resumeJsonArray = new JSONArray();

		for(int i=0; i< jsonArray.length();i++){
			JSONObject element = null;
			try {
				element = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				int _id = element.getInt("_id");
				String name = element.getString("name");
				String type = element.getString("type");
				JSONObject geo_position = element.getJSONObject("geo_position");
				double latitude = geo_position.getDouble("latitude");
				double longitude = geo_position.getDouble("longitude");

				JSONObject newJsonObj = new JSONObject();
				newJsonObj.put("_id", _id);
			    newJsonObj.put("name", name);
			    newJsonObj.put("type", type);
			    newJsonObj.put("latitude", latitude);
			    newJsonObj.put("longitude", longitude);
				
			    resumeJsonArray.put(newJsonObj);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resumeJsonArray;
	}

	public static void getCsv(JSONArray resumeJsonArray){
		File fileCsv = new File("fromJSON.csv");
		try {
			String csv = CDL.toString(resumeJsonArray);
			try {
				FileUtils.writeStringToFile(fileCsv, csv);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block

		}

	}

}
