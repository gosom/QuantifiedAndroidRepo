package quantifiedAndroid.utilities;

import java.io.IOException;
import java.io.File;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Environment;
import android.telephony.PhoneStateListener;

public class SoundUtilities {
	
	public static void stop_recording(MediaRecorder recorder) throws IOException {
	
		try
		{
			recorder.stop();
			recorder.release();
		}catch(Exception e){
	        e.printStackTrace();
	    }
		
	}
	
	public static void start_recording(MediaRecorder recorder) throws IOException {
		
	    try {
	        String state = android.os.Environment.getExternalStorageState();
	        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
	            throw new IOException("SD Card is not mounted.  It is " + state
	                    + ".");
	        }
	        
	        /*recorder.setOnInfoListener(new OnInfoListener(){
	        	//@Override
	        	public void onInfo(MediaRecorder mr, int what, int extra) {        
	        		if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED)
	        		{
	        			mr.stop();
	        			mr.release();
	        		}
	        	}
	        });
	        */
	        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL );
	        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
	        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
	        recorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath()
	                				+ "/test.wav");
	        recorder.prepare();
	        recorder.start();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
