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

public class MovieAdapter extends ArrayAdapter<Movie>
{
	// Array for movies
	private ArrayList<Movie>	Movies;

	// COnstructor
	public MovieAdapter(Context context, int textViewResourceId,
			ArrayList<Movie> movies)
	{
		super(context, textViewResourceId, movies);

		this.Movies = movies;
	}

	// Override getView Method
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;


		//Check if null before continuing
		if (v == null)
		{
			// Inflate the list layout
			LayoutInflater vi = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_view, null);
		}

		// fill in the values
		Movie aMovie = Movies.get(position);
		if (aMovie != null)
		{
			TextView title = (TextView) v.findViewById(R.id.movieTitle);
			TextView year = (TextView) v.findViewById(R.id.movieYear);
			TextView rating = (TextView) v.findViewById(R.id.movieRating);
			ImageView movieThumb = (ImageView) v.findViewById(R.id.movieThumb);

			if (title != null)
			{
				title.setText(aMovie.title);
			}

			if (year != null)
			{
				year.setText(aMovie.year);
			}

			if (year != null)
			{
				double rate = Double.parseDouble(aMovie.rating);
				if (rate >= 4)
					rating.setTextColor(Color.GREEN);
				if (rate >= 2 && rate < 4)
					rating.setTextColor(Color.BLACK);
				if (rate >= 0 && rate <2)
					rating.setTextColor(Color.RED);
				rating.setText(aMovie.rating + "/5.0");
			}
			
			if(movieThumb != null)
			{
				imageParser ip = new imageParser();
				movieThumb.setImageBitmap(ip.getBitmap(aMovie.thumb_url));
			}
			
		}
		return v;
	}

}
