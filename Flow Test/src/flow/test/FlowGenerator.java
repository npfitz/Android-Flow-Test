package flow.test;

import org.json.JSONArray;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class FlowGenerator {
	
	public static RelativeLayout generateFlow(JSONArray photos, int unit, Context context, LinearLayout.LayoutParams lp, int[] images_used){
		RelativeLayout rl = new RelativeLayout(context);
		rl.setLayoutParams(lp);
		
		ImageView iv;
		RelativeLayout.LayoutParams params;		
		int[][] matrix = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
				
		for(int y = 0; y < matrix.length; y++){
			for(int x = 0; x < matrix[0].length; x++){
				
				if(matrixFull(matrix) || images_used[0] == photos.length())	
					break;
				
				if(matrix[y][x] == 0){
					int width = (int)(Math.random() * 10);
					int height = (int)(Math.random() * 10);
					
					width = width%maxWidth(x, y, matrix);
					height = height%maxHeight(x, y, matrix);
					
					width++;
					height++;
					
					iv = new ImageView(context);
					params = new RelativeLayout.LayoutParams(unit * width, unit * height);
					params.leftMargin = x * unit;
					params.topMargin = y * unit;
					iv.setLayoutParams(params);
					iv.setScaleType(ScaleType.CENTER_CROP);
					//iv.setBackgroundColor((int)(Math.random()*0xFFFFFF) + 0xFF000000);
					FetchImage fi = new FetchImage(iv, width*unit, height*unit);					
					try{
						fi.execute(photos.getJSONObject(images_used[0]));
					}
					catch(Exception e){
						System.out.println(e.toString());
					}
					
					
					rl.addView(iv);
					images_used[0]++;
					
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
	
	

}
