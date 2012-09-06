package flow.test;


import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class FetchImage extends AsyncTask<Picture, Void, Bitmap> {

	ImageView image;
	BitmapFactory.Options opt;
	int height, width;
	double ratio;
	
	public FetchImage(ImageView inImage, int inWidth, int inHeight){
		image = inImage;		
		height = inHeight;
		width = inWidth;
		
		ratio = (double)width/(double)height;
		
		opt = new BitmapFactory.Options();
	}
	
	@Override
	protected Bitmap doInBackground(Picture... param) {

		URL url = null;
		Bitmap retval = null;
		
		JSONObject photo = null;
		
		String base = "https://api.500px.com/v1/photos/";
		try {
			
			//Get image info
			base +=  param[0].id + "?image_size=4&consumer_key=0lS9iBNZjRvSIdyPX42LW04uU3g7KiMvhvGDXqOW";
			url = new URL(base);
			
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						
			String text = reader.readLine();			
			photo = new JSONObject(text);
			conn.disconnect();
			
			param[0].url = photo.getJSONObject("photo").getJSONArray("images").getJSONObject(0).getString("url");
			
			/*
			if(param[0].aRatio > 1.0){
				opt.inDensity = param[0].width;
				opt.inTargetDensity = width;
			}
			else{
				opt.inDensity = param[0].height;
				opt.inTargetDensity = height;
			}
			*/
			//opt.inSampleSize = sample;
			
			
			//Get Bitmap
			url = new URL(param[0].url);
			HttpURLConnection conn2 = (HttpURLConnection)url.openConnection();
			conn2.connect();
			
			
			Bitmap large = BitmapFactory.decodeStream(conn2.getInputStream());
			
			double width_ratio = ((double)param[0].width/(double)width);
			double height_ratio = ((double)param[0].height/(double)height);
			
			if(ratio < 1){
				int new_height = (int) ((double)param[0].height / ((double)param[0].width/(double)width));
				int new_width =  (int) ((double)param[0].width / ((double)param[0].width/(double)width));
				retval = Bitmap.createScaledBitmap(large, new_width, new_height, true);
			}
			else{
				int new_height = (int) ((double)param[0].height / ((double)param[0].height/(double)height));
				int new_width =  (int) ((double)param[0].width / ((double)param[0].height/(double)height));
				retval = Bitmap.createScaledBitmap(large, new_width, new_height, true);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		
		return retval;
	}
	
	protected void onPostExecute(Bitmap bmp){
		image.setImageBitmap(bmp);
		
	}

	public int findSample(int img_width, int img_height, int req_width, int req_height){
		
		double width_ratio = (img_width / req_width);
		double height_ratio = (img_height / req_height);
		
		if(width_ratio > 5)
			width_ratio = 5;
		if(height_ratio > 5)
			height_ratio = 5;
		
		if(width_ratio < height_ratio)
			return (int)width_ratio;
		else
			return (int)height_ratio;
		
		
	}
	
}
