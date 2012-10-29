package test.rottentomatos.JSON.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import test.rottentomatos2.classes.Movie;
import android.util.Log;

public class JSONParserOne
{
	public JSONParserOne()
	{
	};

	public ArrayList<Movie> getMovies(String searchParam, int noOfResults)
	{
		ArrayList<Movie> movies = new ArrayList<Movie>();

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
				Log.i("Praeda", result);

				// A Simple JSONObject Creation
				JSONObject json = new JSONObject(result);
				Log.i("Praeda", "<jsonobject>\n" + json.toString()
						+ "\n</jsonobject>");

				JSONArray arr = null;
				try
				{
					Object j = json.get("movies");
					arr = (JSONArray) j;
				} catch (Exception ex)
				{
					Log.v("TEST", "Exception: " + ex.getMessage());
				}

				for (int i = 0; i < arr.length(); i++)
				{
					String id = "";
					String title = "";
					String year = "";
					String rating = "";
					String thumb_url = "";
					
					Movie movie = null;
					try
					{
						JSONObject m = (JSONObject) arr.get(i);
						id = m.getString("id");
						title = m.getString("title");
						year = m.getString("year");
						
						JSONObject r = (JSONObject) m.getJSONObject("ratings");
						rating = r.getString("audience_score");
						
						JSONObject p = (JSONObject) m.getJSONObject("posters");
						thumb_url = p.getString("thumbnail");
						
						movie = new Movie(id, title, year, rating,thumb_url);
						
					} catch (JSONException e)
					{
						// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return movies;
	}

	private static String convertStreamToString(InputStream is)
	{
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
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

	public ArrayList<Movie> getMoviesTwo(String searchTerm, int page)
	{
		String searchUrl = "";
		try
		{
			URI uri = new URI("http", "api.rottentomatoes.com",
					"/api/public/v1.0/movies.json",
					"apikey=nkfpkvm78avnskgbc2r2dtyb&q=" + searchTerm
							+ "&page_limit=" + page, null);
			searchUrl = uri.toASCIIString();
		} catch (URISyntaxException e1)
		{
			e1.printStackTrace();
		}

		ArrayList<Movie> movies = new ArrayList<Movie>();

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(searchUrl);

		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		String responseBody = null;
		try
		{
			responseBody = client.execute(get, responseHandler);
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();

		try
		{
			Object obj = parser.parse(responseBody);
			jsonObject = (JSONObject) obj;
		} catch (Exception ex)
		{
			Log.v("TEST", "Exception: " + ex.getMessage());
		}

		JSONArray arr = null;

		try
		{
			Object j = jsonObject.get("movies");
			arr = (JSONArray) j;
		} catch (Exception ex)
		{
			Log.v("TEST", "Exception: " + ex.getMessage());
		}

		for (int i = 0; i < arr.length(); i++)
		{
			Movie movie = null;
			try
			{
				JSONObject m = (JSONObject) arr.get(i);
				JSONObject r = (JSONObject) ((JSONObject) m).get("rating");
				JSONObject p = (JSONObject) ((JSONObject) m).get("posters");

				movie = new Movie(((JSONObject) m).get("id").toString(),
						((JSONObject) m).get("title").toString(),
						((JSONObject) m).get("year").toString(),
						((JSONObject) r).get("audience_score").toString(),
						((JSONObject) p).get("thumbnail").toString());
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (movie != null)
				movies.add(movie);
		}

		return movies;
	}

	// JSON Tags
	final String	TAG_TOTAL							= "total";
	final String	TAG_MOVIES							= "movies";
	final String	TAG_MOVIES_ID						= "id";
	final String	TAG_MOVIES_TITLE					= "title";
	final String	TAG_MOVIES_YEAR						= "year";
	final String	TAG_MOVIES_MPAA_RATING				= "mpaa_rating";
	final String	TAG_MOVIES_RUNTIME					= "runtime";
	final String	TAG_MOVIES_RELEASE_DATES			= "release_dates";
	final String	TAG_MOVIES_RELEASE_DATES_THEATER	= "theater";
	final String	TAG_MOVIES_RATINGS					= "ratinfs";
	final String	TAG_MOVIES_RATINGS_CRITICS_SCORE	= "critics_score";
	final String	TAG_MOVIES_RATINGS_AUDIENCE_SCORE	= "audience_score";
	final String	TAG_MOVIES_SYNOPSIS					= "synopsis";
	final String	TAG_MOVIES_POSTERS					= "posters";
	final String	TAG_MOVIES_POSTERS_THUMBNAIL		= "thumbnail";
	final String	TAG_MOVIES_POSTERS_PROFILE			= "profile";
	final String	TAG_MOVIES_POSTERS_DETAILED			= "detailed";
	final String	TAG_MOVIES_POSTERS_ORIGINAL			= "original";
	final String	TAG_MOVIES_ABRIDGED_CAST			= "abridged_cast";
	final String	TAG_MOVIES_ABRIDGED_CAST_NAME		= "name";
	final String	TAG_MOVIES_ABRIDGED_CAST_ID			= "id";
	final String	TAG_MOVIES_ABRIDGED_CAST_CHARACTERS	= "characters";
	final String	TAG_MOVIES_ALTERNATE_IDS			= "alternate_ids";
	final String	TAG_MOVIES_ALTERNATE_IDS_IMDB		= "imdb";
	final String	TAG_MOVIES_LINKS					= "links";
	final String	TAG_MOVIES_LINKS_SELF				= "self";
	final String	TAG_MOVIES_LINKS_ALTERNATE			= "alternate";
	final String	TAG_MOVIES_LINKS_CAST				= "cast";
	final String	TAG_MOVIES_LINKS_CLIPS				= "clips";
	final String	TAG_MOVIES_LINKS_REVIEWS			= "reviews";
	final String	TAG_MOVIES_LINKS_SIMILAR			= "similar";
	final String	TAG_LINKS							= "links";
	final String	TAG_LINKS_SELF						= "self";
	final String	TAG_LINKS_NEXT						= "next";
	final String	TAG_LINK_TEMPLATE					= "link_template";

	// OtherVars

	public ArrayList<Movie> parseResults(JSONArray jArray) throws JSONException
	{
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		JSONArray movies = jArray.getJSONArray(1);
		for (int i = 0; i < movies.length(); i++)
		{
			try
			{
				JSONObject o = movies.getJSONObject(i);

				String id, title, year, url, rating;
				id = o.getString(TAG_MOVIES_ID);
				title = o.getString(TAG_MOVIES_TITLE);
				year = o.getString(TAG_MOVIES_YEAR);
				double rate = ((Double.parseDouble(o.getJSONObject(
						TAG_MOVIES_RATINGS).getString(
						TAG_MOVIES_RATINGS_AUDIENCE_SCORE))) / 100) * 5;
				url = o.getJSONObject(TAG_MOVIES_POSTERS).getString(
						TAG_MOVIES_POSTERS_THUMBNAIL);
				// rating = Integer.toString((rate/100) * 5);
				rating = String.valueOf(rate);

				movieList.add(new Movie(id, title, year, rating, url));
			} catch (JSONException je)
			{
				je.printStackTrace();
			}
		}

		return movieList;
	}

	public JSONArray getSearchResult(String searchString, Integer results)
	{
		// Set Link with search parameters and limit
		String link = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=nkfpkvm78avnskgbc2r2dtyb&q="
				+ searchString + "&page_limit=" + results;

		// define httpclient and http get
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(link);

		// String builder to build json string
		StringBuilder builder = new StringBuilder();

		// Try connect and get json array
		try
		{
			// Execute get
			HttpResponse response = httpClient.execute(httpGet);

			// Check if successful = statuscode200
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200)
			{

				// Process json into a string
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null)
				{
					builder.append(line);
				}
			} else
			{
				Log.e(JSONParser.class.toString(), "Failed to download file");
			}

		} catch (ClientProtocolException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		JSONArray jArray = null;

		try
		{
			jArray = new JSONArray(builder.toString());
		} catch (JSONException je)
		{
			je.printStackTrace();
		}

		return jArray;
	}
}
