package flow.test;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;


public class MainActivity extends Activity {

	LinearLayout flow;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        flow = (LinearLayout)findViewById(R.id.flow);   
                      
        RequestPhotos rp = new RequestPhotos(flow);
        rp.execute();       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        
        return true;
    }
    
    public boolean onOptionsItemSelected (MenuItem item){
    	
    	switch(item.getItemId()){
    	
	    	case R.id.flow_stream_select:
	    		PopupMenu popup = new PopupMenu(this, findViewById(R.id.flow_stream_select));
	        	popup.inflate(R.menu.flow_select_menu);
	    		popup.setOnMenuItemClickListener(new FlowSelectListener(flow));
	        	popup.show();
	        	return true;
	    	case R.id.tag_cloud_menu:
	    		((FlowTest)flow.getContext().getApplicationContext()).getThreadManager().clearQueue();
	    		((FlowTest)flow.getContext().getApplicationContext()).getRepo().clear();
	    		((FlowTest)flow.getContext().getApplicationContext()).reset_page();
	    		flow.removeAllViews();	    		
	    		Intent myIntent = new Intent(this, CloudActivity.class);
	    		this.startActivityForResult(myIntent, 4);
	    		return true;
	    	
    	}
    	return false; 	
    }
    
    public void onDestroy(){
    	super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(requestCode == 4){
    		RequestPhotos rp = new RequestPhotos(flow);
            rp.execute();    
    	}    	
    }
}

class FlowSelectListener implements OnMenuItemClickListener{

	LinearLayout flow;
	
	public FlowSelectListener(LinearLayout inFlow){
		flow = inFlow;
	}
	public boolean onMenuItemClick(MenuItem item) {
		switch(item.getItemId()){
		case R.id.getFresh:
			((FlowTest)flow.getContext().getApplicationContext()).setFeature("fresh_today");
    		break;
		
		case R.id.getEditors:
			((FlowTest)flow.getContext().getApplicationContext()).setFeature("editors");
			break;
		
		case R.id.getPopular:
			((FlowTest)flow.getContext().getApplicationContext()).setFeature("popular");
			break;
			
		case R.id.getUpcoming:
			((FlowTest)flow.getContext().getApplicationContext()).setFeature("upcoming");
			break;
		}
		((FlowTest)flow.getContext().getApplicationContext()).getThreadManager().clearQueue();
		((FlowTest)flow.getContext().getApplicationContext()).getRepo().clear();
		((FlowTest)flow.getContext().getApplicationContext()).reset_page();
		flow.removeAllViews();
		System.gc();
    	RequestPhotos rp = new RequestPhotos(flow);
        rp.execute();
        return true;
	}
	
}
