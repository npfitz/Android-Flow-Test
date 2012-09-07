package flow.test;

import org.json.JSONException;
import org.json.JSONObject;

public class Picture {

	int height;
	int width;
	
	double aRatio;
	
	String id;
	
	String url;	
	boolean valid;
	
	public Picture(String inID){
		id = inID;
	}
	
	public Picture(JSONObject JSONpic){
		
		try {
			height = JSONpic.getInt("height");
			width = JSONpic.getInt("width");
			id = JSONpic.getString("id");
			
			aRatio = (double)width/(double)height;
			
			valid = true;
		} catch (JSONException e) {
			height = 0;
			width = 0;
			aRatio = 0;
			id = "";
			valid = false;
		}		
	}
	
	
	public boolean isLandscape(){		
		if((double)width/(double)height > 1)
			return true;
		else
			return false;
	}
	
	public boolean isPortrait(){		
		if((double)width/(double)height < 1)
			return true;
		else
			return false;
	}
	
	
	
}
