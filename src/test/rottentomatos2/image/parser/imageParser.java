package test.rottentomatos2.image.parser;

import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * This class assists in downloading the Poster Thumbnail of the movie.
 * @author Muhammad
 *
 */
public class imageParser
{

	/**
	 * This method downloads the Bitmap Image from a URL
	 * @param thumburl The URL of the image location on the web.
	 * @return Returns a Bitmap object that can be assigned to a ImageView.
	 */
	public Bitmap getBitmap(String thumburl)
	{
		try
		{
			URL url = new URL(thumburl);
			return BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (Exception ex)
		{
			return null;
		}
	}
}
