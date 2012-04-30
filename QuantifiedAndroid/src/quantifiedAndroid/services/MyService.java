package quantifiedAndroid.services;

import java.io.IOException;

import quantifiedAndroid.classes.MyProcessor;
import quantifiedAndroid.classes.MyRecorder;
import quantifiedAndroid.io.AudioIn;
import quantifiedAndroid.io.AudioReader;

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
		audioProcessed = audioSequence = 0;
        readError = AudioReader.Listener.ERR_OK;
        
		runnable.startReader(sampleRate, inputBlockSize * sampleDecimate,
							new AudioReader.Listener()
							{
								@Override
					            public final void onReadComplete(short[] buffer) {
					                receiveAudio(buffer);
					            }
					            @Override
					            public void onReadError(int error) {
					                //handleError(error);
					            }
							}
					);
		Log.i(TAG, "Start Recording");
	}
	
	
	private void stopRecording()
	{
		runnable.stopReader();
		pros.closeFile();
		Log.i(TAG, "Stopping Recording");
	}

	/**
     * Handle audio input.  This is called on the thread of the audio
     * reader.
     * 
     * @param   buffer      Audio data that was just read.
     */
    private final void receiveAudio(short[] buffer) {
        // Lock to protect updates to these local variables.  See run().
        synchronized (this) {
            audioData = buffer;
            ++audioSequence;
        }
        pros.writeToFile(audioData);
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

   private int sampleRate = 8000;
   private int inputBlockSize = 256;
   private int sampleDecimate = 1;
   
   private boolean first_state;
   private static final String TAG = "TestService";
   
   private TelephonyManager mTelephonyManager;
   
   private int CurState;
   private AudioReader runnable = new AudioReader();
   
// Buffered audio data, and sequence number of the latest block.
   private short[] audioData;
   private long audioSequence = 0;
   
   // If we got a read error, the error code.
   private int readError = AudioReader.Listener.ERR_OK;
   
   // Sequence number of the last block we processed.
   private long audioProcessed = 0;
   
   private MyProcessor pros = new MyProcessor("QuantifiedAndroid", "record_raw.raw"); 
   
}
