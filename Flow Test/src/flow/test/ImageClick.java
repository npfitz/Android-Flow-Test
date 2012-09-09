package flow.test;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ImageClick implements OnClickListener {

	Picture pic;
	
	
	public ImageClick(Picture inPic){
		pic = inPic;
		
	}
	
	public void onClick(View arg0) {
		
		Intent myIntent = new Intent(arg0.getContext(), Image.class);
		myIntent.putExtra("id", pic.id);
		arg0.getContext().startActivity(myIntent);

	}

}
