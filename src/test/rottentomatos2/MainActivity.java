package test.rottentomatos2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public static final String EXTRA_SEARCHPARAM = "test.rottentomatos2.SEARCHPARAM";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void btnSearch_Click(View view)
    {
    	Intent intent = new Intent(this, SearchResultActivity.class);
    	EditText et = (EditText) findViewById(R.id.tbSearch);
    	String searchtext = et.getText().toString();
    	intent.putExtra(EXTRA_SEARCHPARAM, et.getText().toString());
    	startActivity(intent);
    }
}
