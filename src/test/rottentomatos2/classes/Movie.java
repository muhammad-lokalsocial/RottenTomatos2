package test.rottentomatos2.classes;

import java.text.DecimalFormat;

public class Movie
{

	public String	id;
	public String	title;
	public String	year;
	public String	rating;
	public String	thumb_url;

	public Movie(String Id, String Title, String Year, String Rating,
			String Thumb_url)
	{
		this.id = Id;
		this.title = Title;
		this.year = Year;
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double amt = (Double.parseDouble(Rating)/ 100) * 5;		
		this.rating = twoDForm.format(amt);
		this.thumb_url = Thumb_url;
	}
}
