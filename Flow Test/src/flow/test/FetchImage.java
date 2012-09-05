package flow.test;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class FetchImage extends AsyncTask<JSONObject, Void, Bitmap> {

	ImageView image;
	BitmapFactory.Options opt;
	int height, width;
	
	public FetchImage(ImageView inImage, int inWidth, int inHeight){
		image = inImage;
		
		height = inHeight;
		width = inWidth;
		opt = new BitmapFactory.Options();
		opt.inDensity = 160;
		opt.inTargetDensity = 160;
		
	}
	
	@Override
	protected Bitmap doInBackground(JSONObject... param) {

		URL url = null;
		Bitmap retval = null;
		
		JSONObject photo;
		
		String base = "https://api.500px.com/v1/photos/";
		try {
			
			//Get image info
			base +=  param[0].getString("id") + "?image_size=4&consumer_key=0lS9iBNZjRvSIdyPX42LW04uU3g7KiMvhvGDXqOW";
			url = new URL(base);
			
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String text = reader.readLine();			
			photo = new JSONObject(text);	
			
			conn.disconnect();
			
			
			//Get Bitmap
			url = new URL(photo.getJSONObject("photo").getJSONArray("images").getJSONObject(0).getString("url"));
			HttpURLConnection conn2 = (HttpURLConnection)url.openConnection();
			conn2.connect();
			
			//retval = Bitmap.createBitmap(BitmapFactory.decodeStream(conn2.getInputStream()), 0, 0, width, height);
			
						
			retval = BitmapFactory.decodeStream(conn2.getInputStream(), null, opt);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		
		return retval;
	}
	
	protected void onPostExecute(Bitmap bmp){
		image.setImageBitmap(bmp);
		image.invalidate();	
	}

	
	
}
