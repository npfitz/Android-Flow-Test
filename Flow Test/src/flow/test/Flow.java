package flow.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Flow extends ScrollView {

	public Flow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void onScrollChanged(int l, int t, int oldl, int oldt){
		
		 View view = (View) getChildAt(getChildCount()-1);
	     int diff = (view.getBottom()-(getHeight()+getScrollY()));
	        
	     if( diff == 0 && !((FlowTest)this.getContext().getApplicationContext()).isSearching){
	    	 ((FlowTest)this.getContext().getApplicationContext()).isSearching = true;
	    	 RequestPhotos rp = new RequestPhotos((LinearLayout)this.findViewById(R.id.flow));
	    	 rp.execute();	               
	     }		
	     super.onScrollChanged(l, t, oldl, oldt);
	}	
}
