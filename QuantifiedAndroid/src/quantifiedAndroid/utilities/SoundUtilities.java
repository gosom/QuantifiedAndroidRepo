package quantifiedAndroid.utilities;

import java.io.IOException;
import java.io.File;
import java.util.Calendar;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Environment;
import android.telephony.PhoneStateListener;

public class SoundUtilities {
	
	
	public static void stop_recording(MediaRecorder recorder) throws IOException {
	
		recorder.stop();
		recorder.release();
		
	}
	
	
	public static void start_recording(MediaRecorder recorder, int source) throws IOException {
		
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            throw new IOException("SD Card is not mounted.  It is " + state
                    + ".");
        }
        
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
        		"/QuantifiedAndroid");
        boolean success = false;
        if(!folder.exists()){
        	success = folder.mkdir();
        }else
        	success = true;
        
        if(!success)
        	throw new IOException("Could not create Folder on sdcard");
        
        Calendar cal = Calendar.getInstance();
        String filename = folder.getAbsolutePath()+"/"+Long.toString(cal.getTimeInMillis())+".wav";
        
        recorder.setAudioSource(source);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(filename);
        
        recorder.prepare();
        
        recorder.start();
	
	}

	
}
