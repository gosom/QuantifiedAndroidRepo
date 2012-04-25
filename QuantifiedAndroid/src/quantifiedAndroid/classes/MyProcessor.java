package quantifiedAndroid.classes;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class MyProcessor {
	
	private String TAG = "MyProcessor";
	private String AUDIO_RECORDER_FOLDER;
	private String AUDIO_RECORDER_TEMP_FILE;
	private FileOutputStream os;
	private DataOutputStream dos;
	
	public MyProcessor(String folder, String file)
	{
		AUDIO_RECORDER_FOLDER = folder;
		AUDIO_RECORDER_TEMP_FILE = file;
		
		try {
			os = new FileOutputStream(getTempFilename());
			dos = new DataOutputStream(os);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.w(TAG, "Could not create outputStream");
		}
		
	}
	
	public void writeToFile(short[] buffer)
	{
		if(dos != null)
		{
			for(int i=0;i<buffer.length;i++)
			{
				try {
					dos.writeShort(buffer[i]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.w(TAG, "Could not write to file");
				}
			}
		}
	}
	
	public void closeFile()
	{
		Log.i(TAG, "Closing");
		if(os != null)
		{
			try {
				dos.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.w(TAG, "Could not close file");
			}
		}
	}
	
	private String getTempFilename(){
    	
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);
        
        if(!file.exists()){
                file.mkdirs();
        }
        
        File tempFile = new File(filepath,AUDIO_RECORDER_TEMP_FILE);
        
        if(tempFile.exists())
                tempFile.delete();
        
        return (file.getAbsolutePath() + "/" + AUDIO_RECORDER_TEMP_FILE);
    } 

}
