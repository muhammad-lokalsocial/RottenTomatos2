package test.rottentomatos2;

import java.util.ArrayList;
import java.util.HashMap;

import test.rottentomatos2.JSON.parsers.JSONParser;
import test.rottentomatos2.adapters.MovieAdapter;
import test.rottentomatos2.classes.Movie;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultActivity extends ListActivity
{

	public static final String EXTRA_MOVIE = "test.rottentomatos2.MOVIE";
	public ArrayList<Movie> movies = new ArrayList<Movie>();
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

		//Retrieve intent from previous activity
		Intent intent = getIntent();
		String searchParam = intent.getStringExtra(MainActivity.EXTRA_SEARCHPARAM);
		
		//Download JSON data
		JSONParser parser = new JSONParser();
		movies = parser.getMovies(searchParam, 10);

		//Set data to ListView
		ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(new MovieAdapter(this,
				android.R.layout.simple_list_item_2, movies));

		//Define the OnClick event by listening to the item.
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				//Create an intent to move to a new activity
				Intent i = new Intent(getApplicationContext(), ViewMovieActivity.class);
				
				//Get MovieID from hidden Textview
				TextView idView = (TextView) arg1.findViewById(R.id.movieId);
				String id = idView.getText().toString();
				
				//Search the list for the correct information to pass.
				Movie movieToPass = null;
				for(Movie m : movies)
				{
					if(m.id == id)
					{
						movieToPass = m;
						break;
					}
				}
				
				HashMap<String, Object> extraInformation = new HashMap<String, Object>();
				extraInformation.put("id", movieToPass.id); 
				extraInformation.put("title", movieToPass.title);
				extraInformation.put("year", movieToPass.year);
				extraInformation.put("rating", movieToPass.rating);
				extraInformation.put("thumb_url", movieToPass.thumb_url);
				extraInformation.put("sypnosis", movieToPass.details.sypnosis);
				extraInformation.put("cast", movieToPass.details.cast);
				
				//Add ID as an EXTRA parameter to pass to the next Activity
				i.putExtra(EXTRA_MOVIE, extraInformation);
				startActivity(i);
				
			}
		});

	}

	

}
