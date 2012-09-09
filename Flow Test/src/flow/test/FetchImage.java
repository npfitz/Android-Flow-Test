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

public class FetchImage extends AsyncTask<Void, Void, Bitmap> {

	ImageView image;
	BitmapFactory.Options opt;
	int height, width;
	double ratio;
	Picture pic;
	ThreadManager manager;
	
	public FetchImage(ImageView inImage, int inWidth, int inHeight, Picture inPic){
		image = inImage;		
		height = inHeight;
		width = inWidth;
		
		ratio = (double)width/(double)height;
		
		opt = new BitmapFactory.Options();
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		
		pic = inPic;
	}
	
	public FetchImage(ImageView inImage, Picture inPic){
		image = inImage;		
		height = 0;
		width = 0;
		pic = inPic;
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	}
	
	public void setManager(ThreadManager inManager){
		manager = inManager;
	}
	
	@Override
	protected Bitmap doInBackground(Void...param) {

		URL url = null;
		Bitmap retval = null;
		
		JSONObject photo = null;
		
		String base = "https://api.500px.com/v1/photos/";
		
		int real_height, real_width;
		try {
			
			//Get image info
			base +=  pic.id + "?image_size=4&consumer_key=0lS9iBNZjRvSIdyPX42LW04uU3g7KiMvhvGDXqOW";
			url = new URL(base);
			
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						
			String text = reader.readLine();			
			photo = new JSONObject(text);
			conn.disconnect();
			
			pic.url = photo.getJSONObject("photo").getJSONArray("images").getJSONObject(0).getString("url");
			
			//Get Bitmap
			url = new URL(pic.url);
			HttpURLConnection conn2 = (HttpURLConnection)url.openConnection();
			conn2.connect();
			
			
			Bitmap large = BitmapFactory.decodeStream(conn2.getInputStream(), null, opt);
			
			if(height == 0 && width == 0)
				return large;
			
			real_height = opt.outHeight;
			real_width = opt.outWidth;
			
			if((double)width/(double)height > 1.0){
				int new_height = (int)(real_height / ((double)real_width/(double)width));
				int new_width = (int)(real_width / ((double)real_width/(double)width));
				if(new_height - height > 0)
					retval = Bitmap.createBitmap(Bitmap.createScaledBitmap(large, new_width, new_height , true), 0, (new_height - height)/2, width, height);
				else
					retval = Bitmap.createScaledBitmap(large, new_width, new_height , true);
			}
			else{
				int new_height = (int)(real_height / ((double)real_height/(double)height));
				int new_width = (int)(real_width / ((double)real_height/(double)height));
				
				if(new_width - width > 0)
					retval = Bitmap.createBitmap(Bitmap.createScaledBitmap(large, new_width, new_height , true), (new_width - width)/2, 0, width, height);			
				else
					retval = Bitmap.createScaledBitmap(large, new_width, new_height , true);
			}
			
			
						
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		return retval;
	}
	
	protected void onPostExecute(Bitmap bmp){
		image.setImageBitmap(bmp);
		if(manager != null)
			manager.nextThread();
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
