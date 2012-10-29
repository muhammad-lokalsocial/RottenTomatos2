package test.rottentomatos2;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import test.rottentomatos.JSON.parsers.JSONParserOne;
import test.rottentomatos2.adapters.MovieAdapter;
import test.rottentomatos2.classes.Movie;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class SearchResultActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

		// Load stuff here
		JSONParserOne parser = new JSONParserOne();
		Intent intent = getIntent();
		String searchParam = intent.getStringExtra(MainActivity.EXTRA_SEARCHPARAM);
		
		ArrayList<Movie> movies = parser.getMovies(searchParam, 10);

		ListView listView = (ListView) findViewById(R.id.ListViewId);
		listView.setAdapter(new MovieAdapter(this,
				android.R.layout.simple_list_item_2, movies));

	}

	// JSON keys
	final static String	TAG_TOTAL							= "total";
	final static String	TAG_MOVIES							= "movies";
	final static String	TAG_MOVIES_ID						= "id";
	final static String	TAG_MOVIES_TITLE					= "title";
	final static String	TAG_MOVIES_YEAR						= "year";
	final static String	TAG_MOVIES_MPAA_RATING				= "mpaa_rating";
	final static String	TAG_MOVIES_RUNTIME					= "runtime";
	final static String	TAG_MOVIES_RELEASE_DATES			= "release_dates";
	final static String	TAG_MOVIES_RELEASE_DATES_THEATER	= "theater";
	final static String	TAG_MOVIES_RATINGS					= "ratinfs";
	final static String	TAG_MOVIES_RATINGS_CRITICS_SCORE	= "critics_score";
	final static String	TAG_MOVIES_RATINGS_AUDIENCE_SCORE	= "audience_score";
	final static String	TAG_MOVIES_SYNOPSIS					= "synopsis";
	final static String	TAG_MOVIES_POSTERS					= "posters";
	final static String	TAG_MOVIES_POSTERS_THUMBNAIL		= "thumbnail";
	final static String	TAG_MOVIES_POSTERS_PROFILE			= "profile";
	final static String	TAG_MOVIES_POSTERS_DETAILED			= "detailed";
	final static String	TAG_MOVIES_POSTERS_ORIGINAL			= "original";
	final static String	TAG_MOVIES_ABRIDGED_CAST			= "abridged_cast";
	final static String	TAG_MOVIES_ABRIDGED_CAST_NAME		= "name";
	final static String	TAG_MOVIES_ABRIDGED_CAST_ID			= "id";
	final static String	TAG_MOVIES_ABRIDGED_CAST_CHARACTERS	= "characters";
	final static String	TAG_MOVIES_ALTERNATE_IDS			= "alternate_ids";
	final static String	TAG_MOVIES_ALTERNATE_IDS_IMDB		= "imdb";
	final static String	TAG_MOVIES_LINKS					= "links";
	final static String	TAG_MOVIES_LINKS_SELF				= "self";
	final static String	TAG_MOVIES_LINKS_ALTERNATE			= "alternate";
	final static String	TAG_MOVIES_LINKS_CAST				= "cast";
	final static String	TAG_MOVIES_LINKS_CLIPS				= "clips";
	final static String	TAG_MOVIES_LINKS_REVIEWS			= "reviews";
	final static String	TAG_MOVIES_LINKS_SIMILAR			= "similar";
	final static String	TAG_LINKS							= "links";
	final static String	TAG_LINKS_SELF						= "self";
	final static String	TAG_LINKS_NEXT						= "next";
	final static String	TAG_LINK_TEMPLATE					= "link_template";

}
