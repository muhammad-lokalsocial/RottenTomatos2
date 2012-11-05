package test.rottentomatos2.classes;

import java.util.ArrayList;

/**
 * This class holds other detailed information regarding the Movie.
 * @author Muhammad
 *
 */
public class MovieDetails
{

	public String				sypnosis;
	public ArrayList<String>	cast;

	/**
	 * The constructor for creating a MovieDetails Object.
	 * @param Sypnosis The Synopsis of the Movie. Can sometimes be null.
	 * @param Cast A list of Casts on the Movie. Can sometimes be null.
	 */
	public MovieDetails(String Sypnosis, ArrayList<String> Cast)
	{
		this.sypnosis = Sypnosis;
		this.cast = Cast;

	}

}
