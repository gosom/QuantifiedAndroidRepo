package quantifiedAndroid.classes;


import android.media.AudioFormat; 
import android.media.AudioRecord; 
import android.media.MediaRecorder; 
import android.util.Log; 
public class MyRecorder extends Thread{ 
  public static final int DEFAULT_SAMPLE_RATE = 8000; 
  private static final int DEFAULT_BUFFER_SIZE = 4096; 
  private static final int CALLBACK_PERIOD = 4000;  // 500 msec (sample rate / callback   //period) 
private static final String TAG = "MyRecorder";
  private final AudioRecord recorder;
private boolean stopped = false;
  
  public MyRecorder() { 
    this(DEFAULT_SAMPLE_RATE); 
  }
  
  private MyRecorder(int sampleRate) { 
    recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 
        sampleRate, AudioFormat.CHANNEL_CONFIGURATION_DEFAULT, 
        AudioFormat.ENCODING_DEFAULT, DEFAULT_BUFFER_SIZE); 
    start();
  }
  @Override
  public void run() { 
	  try{
		  while(!stopped) { 
              short[] buffer = new short[256];

              int N = recorder.read(buffer,0,buffer.length);

          } 
	  } catch(Throwable x) { 
	      Log.w(TAG,"Error reading voice audio",x);
	    } finally { 
	      close();
	    }
  }
  
  public void close() {
	stopped = true;
	
}

public void start2() { 
    recorder.setPositionNotificationPeriod(CALLBACK_PERIOD); 
    recorder.setRecordPositionUpdateListener(new 
	AudioRecord.OnRecordPositionUpdateListener() { 
      public void onMarkerReached(AudioRecord recorder) { 
        Log.e(this.getClass().getSimpleName(), "onMarkerReached Called"); 
      } 
      public void onPeriodicNotification(AudioRecord recorder) { 
        Log.e(this.getClass().getSimpleName(), "onPeriodicNotification Called"); 
      } 
    }); 
    recorder.startRecording(); 
  }
}
   