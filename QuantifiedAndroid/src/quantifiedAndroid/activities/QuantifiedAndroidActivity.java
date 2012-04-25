package quantifiedAndroid.activities;


import quantifiedAndroid.classes.MyRecorder;
import quantifiedAndroid.packages.namespace.R;
import quantifiedAndroid.services.MyService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuantifiedAndroidActivity extends Activity {
	
	Button start;
	private final String TAG = "QuatifiedAndroidActivity";
	private MyRecorder runnable;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {      
       
	   super.onCreate(savedInstanceState);
	   
	   setContentView(R.layout.main);
	   
	   start = (Button) findViewById(R.id.start_button);
	   
	}
	
	
   public void startRecording(View view){
	   
	   runnable = new MyRecorder();
	   
   }
   
   
   public void stopRecording(View view){
	   
	   runnable.close();
   
   }
   
	public void startService(View view){
		
		startService(new Intent(this, MyService.class));
		
	}
	
	
	public void stopService(View view){
		
		stopService(new Intent(this, MyService.class));
	
	}
	
	


	
}