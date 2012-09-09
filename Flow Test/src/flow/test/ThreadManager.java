package flow.test;

import java.util.Vector;

public class ThreadManager {

	Vector<FetchImage> queue;
	FetchImage currentTask;
	
	public ThreadManager(){
		queue = new Vector<FetchImage>();
		currentTask = null;
	}
	
	public void addTask(FetchImage newTask){
		newTask.setManager(this);
		
		if(currentTask == null){
			currentTask = newTask;
			currentTask.execute();
		}
		else
			queue.add(newTask);
	}
	
	public void addHighPriority(FetchImage newTask){
		newTask.setManager(this);
		
		if(currentTask == null){
			currentTask = newTask;
			currentTask.execute();
		}
		else
			queue.insertElementAt(newTask, 0);
	}
	
	public void clearQueue(){
		queue.clear();
	}
	
	public void nextThread(){
		if(!queue.isEmpty()){
			currentTask = queue.elementAt(0);
			queue.remove(0);
			currentTask.execute();
		}
		else
			currentTask = null;
	}
	
	
}
