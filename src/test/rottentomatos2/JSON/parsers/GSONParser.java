package test.rottentomatos2.JSON.parsers;

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

import test.rottentomatos2.classes.Movie;
import test.rottentomatos2.classes.MovieDetails;
import android.util.Log;

import com.google.gson.stream.JsonReader;

/**
 * This class uses the GSON library to parse JSON data
 * 
 * @author Muhammad
 * 
 */
public class GSONParser
{
	/**
	 * A public method to get the JSON data in an ArrayList of Movie Objects
	 * 
	 * @param searchParam
	 *            The movie the user wants to search for.
	 * @param noOfResults
	 *            The maximum number of results to return.
	 * @return Returns an ArrayList of Movie Objects.
	 */
	public static ArrayList<Movie> getMovies(String searchParam, int noOfResults)
	{
		ArrayList<Movie> movies = new ArrayList<Movie>();
		try
		{
			movies = readJsonStream(getInputStream(searchParam, noOfResults));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return movies;
	}

	/**
	 * Downloads the JSON Data and puts it into an InputStream.
	 * 
	 * @param searchParam
	 *            The movie the user wants to search for.
	 * @param noOfResults
	 *            The maximum number of results to return.
	 * @return Returns an InputStream Object that can be parsed through a
	 *         reader.
	 */
	private static InputStream getInputStream(String searchParam,
			int noOfResults)
	{
		InputStream instream = null;

		// Try parsing the URL into a URI
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
				// JSON Response Read
				instream = entity.getContent();
			}
		} catch (ClientProtocolException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return instream;

	}

	/**
	 * Reads the JSON Data from an InputStream
	 * 
	 * @param in
	 *            The InputStream of downloaded JSON data.
	 * @return Returns an ArrayList of Movie objects.
	 * @throws IOException
	 */
	private static ArrayList<Movie> readJsonStream(InputStream in)
			throws IOException
	{
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try
		{
			return getMovieArray(reader);
		} finally
		{
			reader.close();
		}
	}

	/**
	 * Loops through the JSON Array of Movie Objects
	 * 
	 * @param reader
	 *            The com.google.JsonReader object which points to its current
	 *            position the the JSON data.
	 * @return Returns a ArrayList of Movie objects.
	 * @throws IOException
	 */
	private static ArrayList<Movie> getMovieArray(JsonReader reader)
			throws IOException
	{
		ArrayList<Movie> movies = new ArrayList<Movie>();

		reader.beginObject();

		while (reader.hasNext())
		{
			String name = reader.nextName();
			if (name.equals("movies"))
			{
				reader.beginArray();

				while (reader.hasNext())
				{
					movies.add(getMovie(reader));
				}
				reader.endArray();
			}
			else
			{
				reader.skipValue();
			}
		}

		reader.endObject();

		return movies;
	}

	/**
	 * Reads the various variables of the Movie object individually.
	 * 
	 * @param reader
	 *            The com.google.JsonReader object which points to its current
	 *            position the the JSON data.
	 * @return Returns a complete Movie Object.
	 */
	private static Movie getMovie(JsonReader reader) throws IOException
	{
		String id = null, title = null, year = null, rating = null, thumb_url = null, synopsis = null;
		MovieDetails details = null;

		reader.beginObject();
		while (reader.hasNext())
		{
			String name = reader.nextName();

			if (name.equals("id"))
			{
				id = reader.nextString();
			}
			else if (name.equals("title"))
			{
				title = reader.nextString();
			}
			else if (name.equals("year"))
			{
				year = reader.nextString();
			}
			else if (name.equals("ratings"))
			{
				rating = getRating(reader);
			}
			else if (name.equals("synopsis"))
			{
				synopsis = reader.nextString();
				if (synopsis.length() < 5)
				{
					synopsis = "No Synopsis is available.";
				}
			}
			else if (name.equals("posters"))
			{
				thumb_url = getThumbnail(reader);
			}
			else if (name.equals("abridged_cast"))
			{
				details = new MovieDetails(synopsis, getCastArray(reader));
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();

		return new Movie(id, title, year, rating, thumb_url, details);
	}

	/**
	 * Iterates the JSON Object "ratings" to get the "audience_score" Value.
	 * 
	 * @param reader
	 *            The com.google.JsonReader object which points to its current
	 *            position the the JSON data.
	 * @return Returns a String containing the rating of the movie.
	 * @throws IOException
	 */
	private static String getRating(JsonReader reader) throws IOException
	{
		String rating = null;

		reader.beginObject();

		while (reader.hasNext())
		{
			String name = reader.nextName();

			if (name.equals("audience_score"))
			{
				rating = reader.nextString();
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();

		return rating;
	}

	/**
	 * Iterates the JSON Object "posters" to get the URL of the "thumbnail"
	 * Value.
	 * 
	 * @param reader
	 *            The com.google.JsonReader object which points to its current
	 *            position the the JSON data.
	 * @return Returns a String containing the URL of the Thumbnail for the
	 *         movie's Poster.
	 * @throws IOException
	 */
	private static String getThumbnail(JsonReader reader) throws IOException
	{
		String thumbnail = null;

		reader.beginObject();

		while (reader.hasNext())
		{
			String name = reader.nextName();

			if (name.equals("thumbnail"))
			{
				thumbnail = reader.nextString();
			}
			else
			{
				reader.skipValue();
			}
		}
		
		reader.endObject();

		return thumbnail;
	}

	/**
	 * Iterates the JSON Array "abridged_cast" to get all the casts from the
	 * movie.
	 * 
	 * @param reader
	 *            The com.google.JsonReader object which points to its current
	 *            position the the JSON data.
	 * @return Returns an ArrayList of Strings containing the names of cast in
	 *         the movie.
	 * @throws IOException
	 */
	private static ArrayList<String> getCastArray(JsonReader reader)
			throws IOException
	{
		ArrayList<String> cast = new ArrayList<String>();

		reader.beginArray();
		while (reader.hasNext())
		{
			cast.add(getCast(reader));
		}
		reader.endArray();

		return cast;
	}

	/**
	 * Iterates the JSON Object Cast to get only its "Name" Value.
	 * 
	 * @param reader
	 *            The com.google.JsonReader object which points to its current
	 *            position the the JSON data.
	 * @return Returns a String containing the name of the Cast.
	 * @throws IOException
	 */
	private static String getCast(JsonReader reader) throws IOException
	{
		String castName = null;

		reader.beginObject();
		while (reader.hasNext())
		{
			String name = reader.nextName();

			if (name.equals("name"))
			{
				castName = reader.nextString();
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();

		return castName;
	}

}
