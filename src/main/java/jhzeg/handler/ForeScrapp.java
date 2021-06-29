package jhzeg.handler;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jhzeg.pojos.ErrorMessage;
import jhzeg.pojos.Forecast;
import jhzeg.utilities.LambdaRequest;

public class ForeScrapp implements RequestHandler<Object, Object>
{
	ArrayList<Forecast> forecasts = new ArrayList<Forecast>();
	String jsonObject;
	String request;
	GsonBuilder gsonBuilder = new GsonBuilder();
	Gson gson = gsonBuilder.setPrettyPrinting().create();
	
	public Object handleRequest(Object input, Context context)
	{
		try
		{
			Document htmlDocument = Jsoup.connect("https://www.meteored.mx/clima_Nuevo+Laredo-America+Norte-Mexico-Tamaulipas-MMNL-1-22317.html").get();
			Elements ulTags = htmlDocument.getElementsByTag("ul");
			Elements liTags = ulTags.get(4).select("li");
			
			liTags.stream().forEach((li) -> 
			{
				if(!li.hasClass("horas"))
				{
					Forecast forecast = new Forecast(li.getElementsByClass("cuando").text().toString(),
							   li.getElementsByClass("temperatura").text().toString(), 
							   li.getElementsByClass("viento").text().toString());
					forecasts.add(forecast);
				}	
			});
			
			jsonObject = gson.toJson(forecasts);
			
			LambdaRequest lambdaRequest = new LambdaRequest();
			request = lambdaRequest.send(jsonObject, "/");
			forecasts.clear();
		}
		
		catch(Exception exception)
		{
			ErrorMessage errorText = new ErrorMessage(exception.getMessage().toString());

			LambdaRequest lambdaRequest = new LambdaRequest();
			request = lambdaRequest.send("Ocurrio el siguiente error: " + gson.toJson(errorText), "/error-handler");
		}
		
		return request;
	}
}
