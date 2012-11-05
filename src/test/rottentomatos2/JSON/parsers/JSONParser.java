package test.rottentomatos2.JSON.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.rottentomatos2.classes.Movie;
import test.rottentomatos2.classes.MovieDetails;
import android.util.Log;

/**
 * This class assists in the downloading and parsing of JSON data from a specified source.
 * @author Muhammad
 *
 */
public class JSONParser
{
	/**
	 * Basic empty constructor for the JSONParser Class.
	 */
	public JSONParser()
	{
	};

	/**
	 * Gets all movies based on the search parameter from the user. Results are limited based on the parameter.
	 * @param searchParam Search parameters used to find a/multiple Movies.
	 * @param noOfResults Max number of results to return in the JSON data.
	 * @return ArrayList of Movie Objects.
	 */
	public ArrayList<Movie> getMovies(String searchParam, int noOfResults)
	{
		ArrayList<Movie> movies = new ArrayList<Movie>();

		//Try parsing the URL into a URI
		String searchUrl = "";
		try
		{
			URI uri = new URI("http", "api.rottentomatoes.com",
					"/api/public/v1.0/movies.json",
					"apikey=nkfpkvm78avnskgbc2r2dtyb&q=" + searchParam
							+ "&page_limit=" + noOfResults, null);
			searchUrl = uri.toASCIIString();
		} catch (URISyntaxException e1)
		{
			e1.printStackTrace();
		}
		
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(searchUrl);

		// Execute the request
		HttpResponse response;
		try
		{
			response = httpclient.execute(httpget);
			// Examine the response status
			Log.i("HTTPCLIENT EXECUTE", response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null)
			{

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				Log.i("RT2", result);

				// A Simple JSONObject Creation
				JSONObject json = new JSONObject(result);
				Log.i("RT2", "<jsonobject>\n" + json.toString()
						+ "\n</jsonobject>");

				
				//Get first level array "movies"
				JSONArray arr = null;
				try
				{
					Object j = json.get("movies");
					arr = (JSONArray) j;
				} catch (Exception ex)
				{
					Log.v("Error on Movies", "Exception: " + ex.getMessage());
				}
				
				
				//Iterate through each "movie" object, store values in movie objects. Add to ArrayList.
				for (int i = 0; i < arr.length(); i++)
				{
					String id = "";
					String title = "";
					String year = "";
					String rating = "";
					String thumb_url = "";
					String sypnosis = "";
					ArrayList<String> cast = new ArrayList<String>();
					
					Movie movie = null;
					try
					{
						JSONObject m = (JSONObject) arr.get(i);
						id = m.getString("id");
						title = m.getString("title");
						year = m.getString("year");
						sypnosis = m.getString("synopsis");
						if(sypnosis.length() < 5)
						{
							sypnosis = "No Synopsis Available";
						}
						
						JSONObject r = (JSONObject) m.getJSONObject("ratings");
						rating = r.getString("audience_score");
						
						JSONObject p = (JSONObject) m.getJSONObject("posters");
						thumb_url = p.getString("thumbnail");
						
						JSONArray c = (JSONArray) m.getJSONArray("abridged_cast");
						
						for(int count = 0; count < c.length()-1; count++)
						{
							JSONObject singleC = (JSONObject) c.getJSONObject(count);
							cast.add(singleC.getString("name"));
						}
						
						movie = new Movie(id, title, year, rating,thumb_url, new MovieDetails(sypnosis, cast));
						
					} catch (JSONException e)
					{
						e.printStackTrace();
					}
					if (movie != null)
						movies.add(movie);
				}
				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (JSONException e)
		{
			e.printStackTrace();
		}

		return movies;
	}

	private static String convertStreamToString(InputStream is)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				is.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
