package quantifiedAndroid.classes;

import java.io.DataOutputStream;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;

public class MyRecorder extends Thread
{ 

	private static final int RECORDER_SAMPLERATE = 8000;
	private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	
	private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
	private static final String AUDIO_RECORDER_FOLDER = "QuantifiedAndroid";
	
	
	private boolean stopped = false;
	private String TAG = "MyRecorder";
	
	
	public MyRecorder()
	{
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        start();
	}
	
	@Override
	public void run()
    { 
		Log.i(TAG, "Running MyRecorder Thread");
		AudioRecord recorder = null;
		short[][] buffers = new short[256][160];
		int ix = 0;
		MyProcessor pros = new MyProcessor(AUDIO_RECORDER_FOLDER, AUDIO_RECORDER_TEMP_FILE); 
		
		try
		{
			
			int N = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);
			recorder = new AudioRecord(AudioSource.MIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, N*10);
			recorder.startRecording();
			
			while(!stopped)
			{
				Log.i("Map", "Writing new data to buffer");
				short[] buffer = buffers[ix++ % buffers.length];
				N = recorder.read(buffer, 0, buffer.length);
				pros.writeToFile(buffer);
				for(int i=0;i<10000;i++)
				{
					
				}
			}
		}
		catch(Throwable e)
		{
			Log.w(TAG, "Error reading voice audio", e);
		}
		finally
		{
			Log.i(TAG, "Stopping Recording");
			recorder.stop();
			recorder.release();
			pros.closeFile();
		}
    }
	
	public void close()
    { 
         stopped = true;
    }
	
}
