package flow.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.List;


import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PullTags extends AsyncTask<Void, Void, List<Tag>> {

	FlowLayout cloud;
	
	public PullTags(FlowLayout inCloud){
		cloud = inCloud;
	}
	
	
	@Override
	protected List<Tag> doInBackground(Void... voids) {
		Long start = System.nanoTime();
		URL url = null;
		String feature = ((FlowTest)(cloud.getContext().getApplicationContext())).getFeature();
		TagArray list_of_tags = new TagArray();
		
		
		for(int page_number = 1; page_number < 16; page_number++){
			
			try{
				url = new URL("https://api.500px.com/v1/photos?feature="+feature+"&consumer_key=0lS9iBNZjRvSIdyPX42LW04uU3g7KiMvhvGDXqOW&page="+page_number+"&tags=true");
			
				HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();
				
				InputStream in = conn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String text = reader.readLine();				
				String[] split = text.split("\"photos\":");
				
				JSONArray PicArray = new JSONArray(split[1]);
				
				for(int i = 0; i < PicArray.length(); i++){
					
					JSONArray temp = PicArray.getJSONObject(i).getJSONArray("tags");
					
					for(int j = 0; j < temp.length(); j++)
						list_of_tags.addTag(temp.getString(j));
										
				}				
			}
			catch(Exception e){
				System.out.println(e.toString());
			}			
		}
		
		
		list_of_tags.sort();
		System.out.println("Time to get Tag Array: " + (double)(System.nanoTime() - start)/1000000000.0);
		
		return list_of_tags.tags.subList(0, 100); 
	}
	
	protected void onPostExecute(List<Tag> list_of_tags){
		
		while(!list_of_tags.isEmpty()){
			
			int pick = (int) ((Math.random() * 1000)%list_of_tags.size());
			
			TextView tv = new TextView(cloud.getContext());
			tv.setText(list_of_tags.get(pick).tag);
			tv.setTextSize(list_of_tags.get(pick).frequency * 5);
			tv.setTextColor((int)(Math.random() * 0xFFFFFF) + 0xFF000000);
			tv.setOnClickListener(new tagClickListener());
			cloud.addView(tv);
			list_of_tags.remove(pick);
		}			
	}
}


class tagClickListener implements OnClickListener{

	public void onClick(View v) {
		
		TextView temp = (TextView)v;		
		String tag = (String) temp.getText();
		((FlowTest)(v.getContext().getApplicationContext())).set_Search(tag);
		
		Activity activity = (Activity)v.getContext();
		activity.finish();
		
	}
	
}






