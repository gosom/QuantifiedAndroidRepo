package quantifiedAndroid.utilities;

import java.io.IOException;
import java.io.File;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Environment;
import android.telephony.PhoneStateListener;

public class SoundUtilities {
	
	
	public static void stop_recording(MediaRecorder recorder) throws IOException {
	
		recorder.stop();
		recorder.release();
		
	}
	
	
	public static void start_recording(MediaRecorder recorder) throws IOException {
		
	        String state = android.os.Environment.getExternalStorageState();
	        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
	            throw new IOException("SD Card is not mounted.  It is " + state
	                    + ".");
	        }
	        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL );
	        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
	        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
	        recorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.wav");
	        recorder.prepare();
	        recorder.start();
	
	}

	
}
