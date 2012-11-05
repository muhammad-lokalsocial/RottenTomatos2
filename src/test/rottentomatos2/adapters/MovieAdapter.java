package test.rottentomatos2.adapters;

import java.util.ArrayList;
import test.rottentomatos2.R;
import test.rottentomatos2.classes.Movie;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import test.rottentomatos2.image.parser.*;

/**
 * MovieAdapter helps pass search results to be displayed in
 * a certain format in the ListView after the user has
 * searched for a movie.
 * @author Muhammad
 *
 */

public class MovieAdapter extends ArrayAdapter<Movie>
{
	// Array for movies
	private ArrayList<Movie>	Movies;

	/**
	 * Constructor for the MovieAdapter Class.
	 * @param context The current context.
	 * @param textViewResourceId The layout of the ListView Items.
	 * @param movies A list of objects to represent in the view.
	 */
	public MovieAdapter(Context context, int textViewResourceId,
			ArrayList<Movie> movies)
	{
		super(context, textViewResourceId, movies);

		this.Movies = movies;
	}


	@Override
	/**
	 * An Override for the ArrayAdapter's getView method.
	 * This method inflates the layout of each ListView Item
	 * and fills in the data from the Movies object based on the
	 * Position of the item in the ListView.
	 * This method is called for every single item in the array Movies
	 */
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;

		// Check if null before continuing
		if (v == null)
		{
			// Inflate the ListView Item Layout
			LayoutInflater vi = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_view, null);
		}

		// Fill in the values of each views in the Layout
		Movie aMovie = Movies.get(position);
		if (aMovie != null)
		{
			TextView title = (TextView) v.findViewById(R.id.movieTitle);
			TextView year = (TextView) v.findViewById(R.id.movieYear);
			TextView rating = (TextView) v.findViewById(R.id.movieRating);
			ImageView movieThumb = (ImageView) v.findViewById(R.id.movieThumb);
			TextView id = (TextView) v.findViewById(R.id.movieId);

			if (title != null)
			{
				title.setText(aMovie.title);
			}

			if (year != null)
			{
				year.setText(aMovie.year);
			}

			if (rating != null)
			{
				//Setting the color of the rating based on the rating
				double rate = Double.parseDouble(aMovie.rating);
				if (rate >= 4)
					rating.setTextColor(Color.GREEN);
				if (rate >= 2 && rate < 4)
					rating.setTextColor(Color.BLACK);
				if (rate >= 0 && rate < 2)
					rating.setTextColor(Color.RED);
				rating.setText(aMovie.rating + "/5.0");
			}

			if (movieThumb != null)
			{
				imageParser ip = new imageParser();
				movieThumb.setImageBitmap(ip.getBitmap(aMovie.thumb_url));
			}
			
			if (id != null)
			{
				id.setText(aMovie.id);
			}

		}
		return v;
	}

}
