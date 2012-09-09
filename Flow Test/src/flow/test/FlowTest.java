package flow.test;

import java.util.Vector;
import android.app.Application;

public class FlowTest extends Application {

	private ThreadManager tm;
	private String feature;
	private Vector<Picture> picture_repo;
	private int page_number;

	public boolean isSearching;
	
	public void onCreate(){
		super.onCreate();
		tm = new ThreadManager();
		feature = "popular";
		picture_repo = new Vector<Picture>();
		page_number = 1;
		isSearching = false;
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
	
	public Vector<Picture> getRepo(){
		return picture_repo;
	}
	
	public void incrementPage(){
		page_number++;
	}
	
	public void reset_page(){
		page_number = 1;
	}
	
	public int get_page(){
		return page_number;
	}
	
}
