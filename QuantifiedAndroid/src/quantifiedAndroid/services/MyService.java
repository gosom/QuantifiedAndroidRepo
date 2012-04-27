package quantifiedAndroid.services;

import java.io.IOException;

import quantifiedAndroid.classes.MyRecorder;
import quantifiedAndroid.io.AudioIn;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioRecord;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	
   private boolean first_state;
   private static final String TAG = "TestService";
   
   private TelephonyManager mTelephonyManager;
   
   private int CurState;
   private AudioIn runnable;
   
   private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
	   
	    @Override
	    public void onCallStateChanged(int state, String incomingNumber) {

	    	super.onCallStateChanged(state, incomingNumber);
		    if(CurState == state){
		    	return;
		   }
		    CurState = state;
	        switch (state) {
	        
	        case TelephonyManager.CALL_STATE_OFFHOOK:
	        	Log.i(TAG, incomingNumber);
	        	startRecording();
	        	break;
	        
	        case TelephonyManager.CALL_STATE_RINGING:
	        	break;
	        
	        case TelephonyManager.CALL_STATE_IDLE:
	        		stopRecording();
	        	break;
	        
	        }
	    }
	   

	};
	
	private void startRecording()
	{
		runnable = new AudioIn();
		Log.i(TAG, "Start Recording");
	}
	
	
	private void stopRecording()
	{
		runnable.close();
		Log.i(TAG, "Stopping Recording");
	}

   
   @Override
   public void onCreate() {
   
	   super.onCreate();
       
	   this.showToastMessage("Service created...");      
	  
	   mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	   CurState = mTelephonyManager.getCallState();
	   mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	   
	   Log.i(TAG, "Service created...");
   
   }
 
   
   @Override
   public void onStart(Intent intent, int startId) {      
       
	   super.onStart(intent, startId);  
       Log.i(TAG, "Service started...");
   
   }
   
   
   @Override
   public void onDestroy() {
       
	   super.onDestroy();
	   
	   mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	   this.showToastMessage("Service destroyed...");
   Log.i(TAG, "Service stopped...");
   
   }

   
   @Override
   public IBinder onBind(Intent intent) {
       
	   return null;
   }
   
   
   private void showToastMessage(String msg){
		 
	   Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

	   toast.show();

	}

}
