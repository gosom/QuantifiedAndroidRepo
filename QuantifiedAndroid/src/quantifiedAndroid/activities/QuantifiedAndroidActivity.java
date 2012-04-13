package quantifiedAndroid.activities;

import java.io.IOException;

import quantifiedAndroid.packages.namespace.R;
import quantifiedAndroid.services.MyService;
import quantifiedAndroid.utilities.SoundUtilities;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuantifiedAndroidActivity extends Activity {
	
	Button start;
	MediaRecorder recorder;
	private final String TAG = "QuatifiedAndroidActivity";
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {      
       
	   super.onCreate(savedInstanceState);
	   
	   setContentView(R.layout.main);
	   
	   start = (Button) findViewById(R.id.start_button);
	   recorder = new MediaRecorder();
	   
	}
	
	
   public void startRecording(View view){
	   
	   try {
		   SoundUtilities.start_recording(recorder, MediaRecorder.AudioSource.MIC);
	   } catch (IOException e) {
		   // TODO Auto-generated catch block
		   Log.i(TAG, e.getMessage());
	   }
	   
   }
   
   
   public void stopRecording(View view){
	   
	   try{
		    SoundUtilities.stop_recording(recorder);
	   } catch (IOException e){
		   
		   Log.i(TAG, e.getMessage());
	   }
	   
   }
   
	public void startService(View view){
		
		startService(new Intent(this, MyService.class));
		
	}
	
	
	public void stopService(View view){
		
		stopService(new Intent(this, MyService.class));
	
	}
	
	


	
}