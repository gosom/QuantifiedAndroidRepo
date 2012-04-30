package quantifiedAndroid.io;

import quantifiedAndroid.classes.MyProcessor;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;

public class AudioIn extends Thread{
	
	//Private variables
	private boolean stopped = false;
	private int sampleRate = 8000;
	private int channelMode = AudioFormat.CHANNEL_IN_MONO;
	private int encodingMode = AudioFormat.ENCODING_PCM_16BIT;
	private int bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelMode, encodingMode);
	private AudioRecord recorder = new AudioRecord(AudioSource.MIC, 
												   sampleRate, 
												   channelMode,
												   encodingMode,
												   bufferSize);
	private MyProcessor pros = new MyProcessor("QuantifiedAndroid", "record_raw.raw");
	private RecordListener recordListener = new RecordListener();
	private class RecordListener implements AudioRecord.OnRecordPositionUpdateListener {
		private short[] data;
		int numShortsRead = 0;
//		private short[] previous = new short[1];
	    public void onMarkerReached(AudioRecord recorder) {
	        Log.i(TAG, "onMarkedReached CALL");
	    }

	    public void onPeriodicNotification(AudioRecord recorder) {
	    	short[] data = new short[bufferSize/2];
	    	//numShortsRead = recorder.read(data, 0, bufferSize/2);
			pros.writeToFile(data);
	        Log.i(TAG, "onPeriodicNotification CALL");
	    }
	    
	    public void setData(short[] buffer)
	    {	
	    	synchronized(buffer)
	    	{
	    		data = buffer;
	    	}
	    }
	}
	private int intervalFrames = 8000; // sampleRate/interval = 200ms
	
	// Debugging
	private final String TAG = "AudioIn";
	
	
	public AudioIn()
	{
		start();
	}
	
	@Override
	public void run()
	{
		Log.i(TAG, "AudioIn Thread Started");
		recorder.setPositionNotificationPeriod(intervalFrames);
		recorder.setRecordPositionUpdateListener(recordListener);
		short[] data = new short[bufferSize/2];
		recorder.startRecording();
		recorder.read(data, 0, bufferSize/2);
		recordListener.setData(data);
		while (recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            recorder.read(data, 0, data.length);
            recordListener.setData(data);
            try {
                 Thread.sleep(1000); // sleep for 2s
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                Log.e("run Method", "recorder thread is interrupted");
                e.printStackTrace();
            }
        }
//		while(!stopped)
//		{
//			short[] data = new short[bufferSize/2];
//			recorder.read(data, 0, bufferSize/2);
//			recordListener.setData(data);
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	public void close()
	{
		stopped  = true;
		pros.closeFile();
		recorder.stop();
		recorder.release();
	}

}
