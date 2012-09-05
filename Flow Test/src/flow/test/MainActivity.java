package flow.test;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity {

	LinearLayout flow;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        flow = (LinearLayout)findViewById(R.id.flow);        
        
        RequestPhotos rp = new RequestPhotos(flow);
        rp.execute(1);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
