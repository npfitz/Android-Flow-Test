package flow.test;

import org.json.JSONArray;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Flow_Adapter extends BaseAdapter {

	JSONArray[] fragments;
	
	public Flow_Adapter(JSONArray[] inarray){
		
		fragments = inarray;
	}
	
	public int getCount() {
		
		return fragments.length;
	}

	public Object getItem(int index) {
		
		return fragments[index];
	}

	public long getItemId(int index) {
		
		return System.identityHashCode(fragments[index]);
	}

	public View getView(int position, View current, ViewGroup parent) {
				
		int margin = 5;
		int unit = parent.getWidth() - (margin*2);
		
		LinearLayout frag = new LinearLayout(parent.getContext());
		
				
		JSONArray images = fragments[position];
		
		if(images.length() == 1){
			ImageView temp = new ImageView(frag.getContext());			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(unit, unit);
			lp.weight = 1;
			lp.setMargins(margin, margin, margin, margin);
			temp.setLayoutParams(lp);
			temp.setBackgroundColor(0xFF999999);			
			frag.addView(temp);
		}
		//If there are 2 images in the fragment
		else if(images.length() == 2){
			int orientation = (int)(Math.random() * 100) % 2;
			
			for(int i = 0; i < images.length(); i++){
									
				ImageView temp = new ImageView(frag.getContext());
				LinearLayout.LayoutParams lp;
				//Have 2 wide photos
				if(orientation == 1)
					lp = new LinearLayout.LayoutParams(unit, 0);
				//have 2 tall photos
				else
					lp = new LinearLayout.LayoutParams(0, unit);
					
				lp.weight = 1;
				lp.setMargins(margin, margin, margin, margin);
				temp.setLayoutParams(lp);
				temp.setBackgroundColor(0xFF999999);
				frag.addView(temp);
				
			}			
		}
		
		//If there are 3 images in the fragment
		else if(images.length() == 3){
			
			boolean big = false;
			
			for(int i = 0; i < images.length(); i++){
			
				//Crappy little algorithm to decide if this particular image will be
				//The large image
				
				int size;				
				if(i == images.length() - 1 && !big)
					size = 1;
				else if(big)
					size = 0;
				else{
					size = (int)(Math.random()*10)%2;
					if(size == 1)
						big = true;
				}
				
				ImageView temp = new ImageView(frag.getContext());
				LinearLayout.LayoutParams lp;
				if(size == 1){
					if(i == 1)
						lp = new LinearLayout.LayoutParams(unit/2, unit);
					else if(i == 2)
						lp = new LinearLayout.LayoutParams(unit, unit/2);
					else{
						int chance = (int)(Math.random()*100)%2;
						
						if(chance == 1)
							lp = new LinearLayout.LayoutParams(unit/2, unit);
						else
							lp = new LinearLayout.LayoutParams(unit, unit/2);
					}
				}
				else
					lp = new LinearLayout.LayoutParams(unit/2, unit/2);
				
				
				lp.weight = 1;
				lp.setMargins(margin, margin, margin, margin);
				temp.setLayoutParams(lp);
				temp.setBackgroundColor(0xFF999999);			
				frag.addView(temp);				
			}			
		}
		else{
			LinearLayout one = new LinearLayout(parent.getContext());
			LinearLayout two = new LinearLayout(parent.getContext());
			for(int i = 0; i < images.length(); i++){
				ImageView temp = new ImageView(frag.getContext());			
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(unit/2, unit/2);
				lp.weight = 1;
				lp.setMargins(margin, margin, margin, margin);
				temp.setLayoutParams(lp);
				temp.setBackgroundColor(0xFF999999);			
				
				if(i < images.length()/2)
					one.addView(temp);
				else
					two.addView(temp);
			}
			frag.addView(one);
			frag.addView(two);
			
			
		}	
		
		return frag;
	}

}
