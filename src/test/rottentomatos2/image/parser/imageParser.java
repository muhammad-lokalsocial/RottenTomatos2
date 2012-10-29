package test.rottentomatos2.image.parser;

import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class imageParser
{

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
