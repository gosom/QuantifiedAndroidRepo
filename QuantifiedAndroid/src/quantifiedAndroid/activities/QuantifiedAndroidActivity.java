package quantifiedAndroid.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import quantifiedAndroid.classes.MyRecorder;
import quantifiedAndroid.packages.namespace.R;
import quantifiedAndroid.services.MyService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuantifiedAndroidActivity extends Activity {
	
	Button start;
	private final String TAG = "QuatifiedAndroidActivity";
	
	private MyRecorder myRecorder;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {      
       
	   super.onCreate(savedInstanceState);
	   
	   setContentView(R.layout.main);
	   
	   start = (Button) findViewById(R.id.start_button);
	   myRecorder = new MyRecorder();
	   
	}
	
	
   public void startRecording(View view){
	   
	   myRecorder.recorder.startRecording();
	   
	   myRecorder.isRecording= true;
	   
	   Thread recordingThread = new Thread(new Runnable() {
			//@Override
			public void run() {
				myRecorder.writeAudioDataToFile();
			}
		},"AudioRecorder Thread");
		
		recordingThread.start();
	   
	   
   }
   
   
   public void stopRecording(View view){
	   
	   if(myRecorder.isRecording)
		   myRecorder.isRecording = false;
	   
	   myRecorder.recorder.stop();
	   myRecorder.recorder.release();
       
   }
   
	public void startService(View view){
		
		startService(new Intent(this, MyService.class));
		
	}
	
	
	public void stopService(View view){
		
		stopService(new Intent(this, MyService.class));
	
	}
	
	


	
}