package flow.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import android.os.AsyncTask;
import android.widget.LinearLayout;


public class RequestPhotos extends AsyncTask<Integer, Void, Vector<Picture>> {

	LinearLayout flow;
	
	public RequestPhotos(LinearLayout inList){
		flow = inList;
	}
	
	@Override
	protected Vector<Picture> doInBackground(Integer... page) {
		
		URL url = null;
		Vector<Picture> retval = new Vector<Picture>();
		
		String feature = ((FlowTest)(flow.getContext().getApplicationContext())).getFeature();
		
		for(int j = 1; j < 3; j++){
			try{
				url = new URL("https://api.500px.com/v1/photos?feature="+feature+"&consumer_key=0lS9iBNZjRvSIdyPX42LW04uU3g7KiMvhvGDXqOW&page="+j);
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
				
				JSONArray PicArray = new JSONArray(split[1]);
				
				for(int i = 0; i < PicArray.length(); i++)
					retval.add(new Picture(PicArray.getJSONObject(i)));			
							
			}
			catch(Exception e){			
				System.out.println(e.toString());
			}		
		}	
		return retval;
	}
	
	protected void onPostExecute(Vector<Picture> photos)
	{		
		int margin = 5;
		
		int unit = ((flow.getWidth() - (margin*5))/4);
				
		while(!photos.isEmpty()){
			flow.addView(FlowGenerator.generateFlow(photos, unit, flow.getContext(), new LinearLayout.LayoutParams(flow.getWidth(), (flow.getWidth()/2) - margin), margin));		
			flow.invalidate();
		}
				
	}
	

}