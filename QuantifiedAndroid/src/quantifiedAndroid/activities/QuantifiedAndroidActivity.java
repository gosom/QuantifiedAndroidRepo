package quantifiedAndroid.activities;


import quantifiedAndroid.classes.MyProcessor;
import quantifiedAndroid.io.AudioIn;
import quantifiedAndroid.io.AudioReader;
import quantifiedAndroid.packages.namespace.R;
import quantifiedAndroid.services.MyService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class QuantifiedAndroidActivity extends Activity {
	
		Button start;
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
	   
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {      
       
	   super.onCreate(savedInstanceState);
	   
	   setContentView(R.layout.main);
	   
	   start = (Button) findViewById(R.id.start_button);
	   
	}
	
	
   public void startRecording(View view){
	   
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
   
   /* Handle audio input.  This is called on the thread of the audio
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
      double db = pros.calculatePowerDb(audioData, 0, buffer.length);
      Log.i("DECIBEL", Double.toString(db));
  }
   public void stopRecording(View view){
	   
	   runnable.stopReader();
		pros.closeFile();
		Log.i(TAG, "Stopping Recording");
   
   }
   
	public void startService(View view){
		
		startService(new Intent(this, MyService.class));
		
	}
	
	
	public void stopService(View view){
		
		stopService(new Intent(this, MyService.class));
	
	}
	
	


	
}