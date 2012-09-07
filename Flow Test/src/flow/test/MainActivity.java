package flow.test;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;


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
    
    public boolean onOptionsItemSelected (MenuItem item){
    	
    	if(item.getItemId() == R.id.getFresh)
    		((FlowTest)getApplication()).setFeature("fresh_today");
    	
    	if(item.getItemId() == R.id.getPopular)
    		((FlowTest)getApplication()).setFeature("popular");
    	
    	if(item.getItemId() == R.id.getEditors)
    		((FlowTest)getApplication()).setFeature("editors");
    	
    	if(item.getItemId() == R.id.getUpcoming)
    		((FlowTest)getApplication()).setFeature("upcoming");
    	
    	((FlowTest)getApplication()).getThreadManager().clearQueue();
    	
    	flow.removeAllViews();
    	RequestPhotos rp = new RequestPhotos(flow);
        rp.execute(1);    	
    	
    	return true;
    }
    
}
