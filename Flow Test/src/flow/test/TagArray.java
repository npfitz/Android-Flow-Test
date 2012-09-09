package flow.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class TagArray {

	public ArrayList<Tag> tags;
	
	public TagArray(){
		tags = new ArrayList<Tag>();
	}
	
	public void addTag(String x){
		for(int i = 0; i < tags.size(); i++){
			if(tags.get(i).tag.equals(x)){
				tags.get(i).incrementFrequency();
				return;
			}			
		}
		tags.add(new Tag(x));		
	}
	
	public void sort(){
		
		Collections.sort(tags, new TagCompare());
		
	}
	
}

class Tag {
	
	public String tag;
	public int frequency;
	
	public Tag(String inTag){
		tag = inTag;
		frequency = 1;
	}
	
	public void incrementFrequency(){
		frequency++;
	}
	
}

class TagCompare implements Comparator<Tag>{

	public int compare(Tag x, Tag y) {
		return y.frequency - x.frequency;
	}
	
}