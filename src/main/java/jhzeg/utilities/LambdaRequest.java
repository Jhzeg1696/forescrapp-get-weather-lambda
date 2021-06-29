package jhzeg.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class LambdaRequest{
	private String URL = "https://4zfij7bi1h.execute-api.us-east-1.amazonaws.com/Beta";
	private String API_KEY = "pgJzOVE2gO5b8b61aNhva7RE939cRrtz2jC6ecq6"; 
	
	public String send(String json, String endpoint)
	{
		String response; 
		
		try 
		{
			URL url = new URL (URL + endpoint);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("x-api-key", API_KEY);
			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			
			OutputStream os = connection.getOutputStream();
			byte[] input = json.getBytes("utf-8");
		    os.write(input, 0, input.length);	
		    BufferedReader br = new BufferedReader( new InputStreamReader(connection.getInputStream(), "utf-8"));
		    
		    StringBuilder serverResponse = new StringBuilder();
			String responseLine = null;
			
			while ((responseLine = br.readLine()) != null) 
			{
				serverResponse.append(responseLine.trim());
			}
			
			response = serverResponse.toString();
		}	
		
		catch(MalformedURLException urlException) 
		{ 
			return "Lo siento, ocurrio el siguiente error: " + urlException.getMessage().toString();
		}
		
		catch(IOException ioException) 
		{ 
			return  "Lo siento, ocurrio el siguiente error: " + ioException.getMessage().toString();
		}
		
		return response;
	}
}
