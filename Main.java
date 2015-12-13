import org.json.JSONArray;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cityName = args[0];
		//String cityName = "Paris";
		String beginningUrl = "http://api.goeuro.com/api/v2/position/suggest/en/";
		JSONArray myJson = Functions.getJson(beginningUrl + cityName);
		Functions.createCsv(myJson);

		
	}

}
