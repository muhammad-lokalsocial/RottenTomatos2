package test.rottentomatos2.classes;

public class MovieDetails extends Movie
{
	public String sypnosis;
	public String [] cast;
	
	public MovieDetails(String Id, String Title, String Year, String Rating,
			String Thumb_url, String Sypnosis, String[] Cast)
	{
		super(Id, Title, Year, Rating, Thumb_url);
		this.sypnosis = Sypnosis;
		this.cast = Cast;
		
	}
	
	
	
	
}
