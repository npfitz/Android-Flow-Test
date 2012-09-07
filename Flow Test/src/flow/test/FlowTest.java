package flow.test;

import android.app.Application;

public class FlowTest extends Application {

	private ThreadManager tm;
	private String feature;
	
	public void onCreate(){
		super.onCreate();
		tm = new ThreadManager();
		feature = "popular";
	}
	
	
	public ThreadManager getThreadManager(){
		return tm;
	}
	
	public String getFeature(){
		return feature;
	}
	
	public void setFeature(String inFeature){
		feature = inFeature;
	}
	
	
}
