package test.rottentomatos2.classes;

import java.text.DecimalFormat;

/**
 * This class holds the information of a single movie
 * @author Muhammad
 *
 */
public class Movie
{

	public String		id;
	public String		title;
	public String		year;
	public String		rating;
	public String		thumb_url;
	public MovieDetails	details;

	/**
	 * The constructor for creating a Movie Object.
	 * @param Id The ID of the Movie
	 * @param Title The Title of the Movie
	 * @param Year The Year of the Movie's release.
	 * @param Rating The Audience's rating of the movie (x/100)
	 * @param Thumb_url The URL to the Thumbnail of the Movie's Poster.
	 * @param Details A MovieDetails object containing more detailed information about the Movie.
	 */
	public Movie(String Id, String Title, String Year, String Rating,
			String Thumb_url, MovieDetails Details)
	{
		this.id = Id;
		this.title = Title;
		this.year = Year;
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double amt = (Double.parseDouble(Rating) / 100) * 5;
		this.rating = twoDForm.format(amt);
		this.thumb_url = Thumb_url;
		this.details = Details;
	}
}
