package flow.test;

import android.app.Activity;
import android.os.Bundle;

public class CloudActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);
        
        PullTags pt = new PullTags((FlowLayout)findViewById(R.id.cloud));
        pt.execute();
        
        
        
	}
 }
	

