package jhzeg.pojos;

public class Forecast {
	String date;
	String temperature;
	String winds;
	
	public Forecast(String date, String temperature, String winds)
	{
		this.date = date;
		this.temperature = temperature;
		this.winds = winds;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public String getTemperature()
	{
		return this.temperature;
	}
	
	public String getWinds()
	{
		return this.winds;
	}
	
	@Override
	public String toString()
	{
		return date + " el clima sera de " + temperature + " con vientos de " + winds;
	}
}
