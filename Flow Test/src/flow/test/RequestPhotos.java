package flow.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class RequestPhotos extends AsyncTask<Integer, Void, JSONArray> {

	LinearLayout flow;
	
	public RequestPhotos(LinearLayout inList){
		flow = inList;
	}
	
	@Override
	protected JSONArray doInBackground(Integer... page) {
		
		URL url = null;
		JSONArray retval = null;
		
		try{
			url = new URL("https://api.500px.com/v1/photos?feature=popular&consumer_key=0lS9iBNZjRvSIdyPX42LW04uU3g7KiMvhvGDXqOW&page="+page[0]);
		}
		catch(Exception e){
			System.out.println(e.toString());			
		}
		
		try{
			
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String text = reader.readLine();
			
			String[] split = text.split("\"photos\":");
			
			retval = new JSONArray(split[1]);
			
			JSONObject temp = retval.getJSONObject(0).getJSONObject("user");
						
		}
		catch(Exception e){			
			System.out.println(e.toString());
		}		
		return retval;
	}
	
	protected void onPostExecute(JSONArray photos){
		
		int unit = (flow.getWidth()/4);
		int[] images_used = new int[]{0};
		
		while(images_used[0] < photos.length()){
			flow.addView(FlowGenerator.generateFlow(photos, unit, flow.getContext(), new LinearLayout.LayoutParams(unit*4, unit*3), images_used));		
			flow.invalidate();
		}
				
	}
	

}


/*int images_used = 0;		
int hor_used = 0;
int ver_used = 0;

LinearLayout temp_layout = null;		
while(images_used < photos.length()){			
	if(hor_used == 0 && ver_used == 0){
	
		int hor = (int)((Math.random()*100)%2) + 1;
		int ver = (int)((Math.random()*100)%2) + 1;
		
		if(hor == 2 && ver == 2){					
			ImageView temp = new ImageView(flow.getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(unit*2, unit*2);
			lp.setMargins(5, 5, 5, 5);
			temp.setLayoutParams(lp);
			temp.setBackgroundColor(0xFF999999);
			flow.addView(temp);
			FetchImage fi = new FetchImage(temp, unit*2, unit*2);
			try {
				fi.execute(photos.getJSONObject(images_used));
				images_used++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if(hor == 2 && ver == 1){
			ImageView temp = new ImageView(flow.getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(unit*2, unit);
			lp.setMargins(5, 5, 5, 5);
			temp.setLayoutParams(lp);
			temp.setBackgroundColor(0xFF999999);
								
			flow.addView(temp);
			FetchImage fi = new FetchImage(temp, unit*2, unit);
			try {
				fi.execute(photos.getJSONObject(images_used));
				images_used++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		//Shit gets real
		else if(hor == 1 && ver == 2){
			temp_layout = new LinearLayout(flow.getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(unit*2, unit*2);
			temp_layout.setLayoutParams(lp);
			temp_layout.setOrientation(0);
			
			ImageView temp = new ImageView(flow.getContext());
			lp = new LinearLayout.LayoutParams(unit, unit*2);
			lp.setMargins(5, 5, 5, 5);
			temp.setLayoutParams(lp);
			temp.setBackgroundColor(0xFF999999);
			temp_layout.addView(temp);
			FetchImage fi = new FetchImage(temp, unit, unit*2);
			try {
				fi.execute(photos.getJSONObject(images_used));
				images_used++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			hor_used += hor;
			ver_used += ver;
			
			
		}
		else if(hor == 1 && ver == 1){
			temp_layout = new LinearLayout(flow.getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(unit*2, unit);
			temp_layout.setLayoutParams(lp);
			temp_layout.setOrientation(0);
			
			ImageView temp = new ImageView(flow.getContext());
			lp = new LinearLayout.LayoutParams(unit, unit);
			lp.setMargins(5, 5, 5, 5);
			temp.setLayoutParams(lp);
			temp.setBackgroundColor(0xFF999999);
			temp_layout.addView(temp);
			FetchImage fi = new FetchImage(temp, unit, unit);
			try {
				fi.execute(photos.getJSONObject(images_used));
				images_used++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			if(images_used < photos.length()){
				temp = new ImageView(flow.getContext());
				lp = new LinearLayout.LayoutParams(unit, unit);
				lp.setMargins(5, 5, 5, 5);
				temp.setLayoutParams(lp);
				temp.setBackgroundColor(0xFF999999);
				temp_layout.addView(temp);
				fi = new FetchImage(temp, unit, unit);
				try {
					fi.execute(photos.getJSONObject(images_used));
					images_used++;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			flow.addView(temp_layout);
		}
		
	}
	else{
		
		int ver = (int)((Math.random()*100)%2) + 1;
		
		if(ver == 2){
			ImageView temp = new ImageView(flow.getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(unit, unit*2);
			lp.setMargins(5, 5, 5, 5);
			temp.setLayoutParams(lp);
			temp.setBackgroundColor(0xFF999999);
			temp_layout.addView(temp);
			FetchImage fi = new FetchImage(temp, unit, unit*2);
			try {
				fi.execute(photos.getJSONObject(images_used));
				images_used++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		else{
			
			LinearLayout more_temp = new LinearLayout(flow.getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(unit, unit*2);
			more_temp.setLayoutParams(lp);
			more_temp.setOrientation(1);
			
			ImageView temp = new ImageView(flow.getContext());
			lp = new LinearLayout.LayoutParams(unit, unit);
			lp.setMargins(5, 5, 5, 5);
			temp.setLayoutParams(lp);
			temp.setBackgroundColor(0xFF999999);
			more_temp.addView(temp);
			FetchImage fi = new FetchImage(temp, unit, unit);
			try {
				fi.execute(photos.getJSONObject(images_used));
				images_used++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			if(images_used < photos.length()){				
				temp = new ImageView(flow.getContext());
				temp.setLayoutParams(lp);
				temp.setBackgroundColor(0xFF999999);
				more_temp.addView(temp);
				fi = new FetchImage(temp, unit, unit);
				try {
					fi.execute(photos.getJSONObject(images_used));
					images_used++;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
				
			temp_layout.addView(more_temp);					
			
		}
		
		flow.addView(temp_layout);
		hor_used = 0;
		ver_used = 0;
	}		
	*/