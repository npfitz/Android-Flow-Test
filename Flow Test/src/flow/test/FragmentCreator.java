package flow.test;

import java.util.Vector;

import org.json.JSONArray;

public class FragmentCreator {

	Vector<JSONArray> fragments;
		
	public FragmentCreator(JSONArray inArray){
		fragments = new Vector<JSONArray>();
		go(inArray);
	}
	
	public void go(JSONArray images_info){
		
		int images_used = 0;
				
		try{
			while(images_used < images_info.length()){
				
				int images_in_fragment;
				JSONArray temp = new JSONArray();
				
				if((images_info.length() - images_used) >= 4){
					images_in_fragment = (int)(Math.random()*100)%4 + 1;
					
					for(int i = 0; i < images_in_fragment; i++)
						temp.put(images_info.get(images_used + i));						
					
				}
				else if((images_info.length() - images_used) >= 3){
					images_in_fragment = (int)(Math.random()*100)%3 + 1;
					
					for(int i = 0; i < images_in_fragment; i++)
						temp.put(images_info.get(images_used + i));
				}
				else if((images_info.length() - images_used) >= 2){
					images_in_fragment = (int)(Math.random()*100)%2 + 1;
					
					for(int i = 0; i < images_in_fragment; i++)
						temp.put(images_info.get(images_used + i));
				}
				else{
					images_in_fragment = 1;
					temp.put(images_info.get(images_used));
				}
				images_used += images_in_fragment;
				fragments.add(temp);
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		
		
		
	}	
}
