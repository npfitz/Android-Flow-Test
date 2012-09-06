package flow.test;

import java.util.Vector;

import org.json.JSONArray;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class FlowGenerator {
	
	public static RelativeLayout generateFlow(Vector<Picture> photos, int unit, Context context, LinearLayout.LayoutParams lp, int margin){
		RelativeLayout rl = new RelativeLayout(context);
		rl.setLayoutParams(lp);
		
		ImageView iv;
		RelativeLayout.LayoutParams params;		
		int[][] matrix = new int[2][4];
				
		for(int y = 0; y < matrix.length; y++){
			for(int x = 0; x < matrix[0].length; x++){				
							
				if(matrixFull(matrix) || photos.isEmpty())	
					break;
				
				if(matrix[y][x] == 0){
					int width = (int)(Math.random() * 10);
					int height = (int)(Math.random() * 10);
					
					width = width%maxWidth(x, y, matrix);
					height = height%maxHeight(x, y, matrix);
					
					width++;
					height++;
					
					iv = new ImageView(context);
					params = new RelativeLayout.LayoutParams((unit * width) + ((width-1) * margin), (unit * height) + ((height-1) * margin));
					params.leftMargin = (x * unit) + ((x+1) * margin);
					params.topMargin = y * unit + ((y+1) * margin);
					iv.setLayoutParams(params);
					iv.setBackgroundColor(0xFF999999);
					iv.setScaleType(ScaleType.CENTER_CROP);
					
					int image_used = selectImage(photos, height, width);
					
					
					FetchImage fi = new FetchImage(iv, width*unit, height*unit);					
					try{
						fi.execute(photos.elementAt(image_used));
					}
					catch(Exception e){
						System.out.println(e.toString());
					}
					
					photos.remove(image_used);
					rl.addView(iv);
					
					for(int i = 0; i < height; i++){
						for(int j = 0; j < width; j++)
							matrix[y+i][x+j] = 1;
					}								
				}
			}
		}		
		return rl;
	}
	
	
	private static boolean matrixFull(int[][] x){
		for(int i = 0; i < x.length; i++){
			for(int j = 0; j < x[0].length; j++){
				if(x[i][j] == 0){
					return false;
				}
			}
		}
		return true;
	}
	
	private static int maxWidth(int x, int y, int[][] matrix){

		int i = 0;		
		while(x+i < matrix[y].length && matrix[y][x+i] != 1)
			i++;		
		return i;
	}
	
	private static int maxHeight(int x, int y, int[][] matrix){

		int i = 0;		
		while(y+i < matrix.length && matrix[y+i][x] != 1)
			i++;		
		return i;
	}
	
	private static int selectImage(Vector<Picture> photos, int height, int width){
		
		double reqRatio = (double)width/(double)height;
		
		for(int i = 0; i < photos.size(); i++){
			if(Math.abs(photos.elementAt(i).aRatio - reqRatio) < 0.15)
				return i;
		}
		
		if(height > width){
			for(int i = 0; i < photos.size(); i++){
				if(photos.elementAt(i).height > photos.elementAt(i).width)
					return i;
			}
		}
		else{
			for(int i = 0; i < photos.size(); i++){
				if(photos.elementAt(i).height <= photos.elementAt(i).width)
					return i;
			}
		}
		return 0;
	}

}
