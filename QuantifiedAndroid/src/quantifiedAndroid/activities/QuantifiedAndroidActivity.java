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
	SoundUtilities SoundUtil;
	private final String TAG = "QuatifiedAndroidActivity";
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {      
       
	   super.onCreate(savedInstanceState);
	   
	   setContentView(R.layout.main);
	   
	   start = (Button) findViewById(R.id.start_button);
	   SoundUtil = new SoundUtilities(MediaRecorder.AudioSource.MIC);
	   
	}
	
	
   public void startRecording(View view){
	   
		   SoundUtil.start_recording();
	   
   }
   
   
   public void stopRecording(View view){
	   
	   SoundUtil.stop_recording();
		   
   }
   
	public void startService(View view){
		
		startService(new Intent(this, MyService.class));
		
	}
	
	
	public void stopService(View view){
		
		stopService(new Intent(this, MyService.class));
	
	}
	
	


	
}