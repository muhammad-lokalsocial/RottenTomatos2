package test.rottentomatos2;

import java.util.ArrayList;
import java.util.HashMap;

import test.rottentomatos2.image.parser.imageParser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewMovieActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_movie);

		//Get the intent from the previous activity
		Intent i = getIntent();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> information = (HashMap<String, Object>) i
				.getSerializableExtra(SearchResultActivity.EXTRA_MOVIE);

		//Set data to Views
		TextView title = (TextView) findViewById(R.id.tvTitle);
		TextView year = (TextView) findViewById(R.id.tvYear);
		TextView sypnosis = (TextView) findViewById(R.id.tvSypnosis);
		TextView cast = (TextView) findViewById(R.id.tvCast);
		ImageView thumbnail = (ImageView) findViewById(R.id.ivThumbnail);
		
		title.setText(information.get("title").toString());
		year.setText(information.get("year").toString());
		sypnosis.setText(information.get("sypnosis").toString());
		
		@SuppressWarnings("unchecked")
		ArrayList<String> listOfCast = (ArrayList<String>) information.get("cast");
		String castText = "Cast: \n";
		for(String s : listOfCast)
		{
			castText += s + "\n";
		}
		cast.setText(castText);
		
		imageParser ip = new imageParser();
		thumbnail.setImageBitmap(ip.getBitmap(information.get("thumb_url").toString()));
	}

}
