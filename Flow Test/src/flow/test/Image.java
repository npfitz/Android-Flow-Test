package flow.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class Image extends Activity {

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        
        ((FlowTest)getApplicationContext()).getThreadManager().addHighPriority(new FetchImage((ImageView)findViewById(R.id.pic), new Picture(getIntent().getExtras().getString("id"))));
    }
}
