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
	
	  public  double calculatePowerDb(short[] sdata, int off, int samples) {
	        // Calculate the sum of the values, and the sum of the squared values.
	        // We need longs to avoid running out of bits.
	        double sum = 0;
	        double sqsum = 0;
	        for (int i = 0; i < samples; i++) {
	            final long v = sdata[off + i];
	            sum += v;
	            sqsum += v * v;
	        }
	        
	        // sqsum is the sum of all (signal+bias)², so
	        //     sqsum = sum(signal²) + samples * bias²
	        // hence
	        //     sum(signal²) = sqsum - samples * bias²
	        // Bias is simply the average value, i.e.
	        //     bias = sum / samples
	        // Since power = sum(signal²) / samples, we have
	        //     power = (sqsum - samples * sum² / samples²) / samples
	        // so
	        //     power = (sqsum - sum² / samples) / samples
	        double power = (sqsum - sum * sum / samples) / samples;

	        // Scale to the range 0 - 1.
	        power /= MAX_16_BIT * MAX_16_BIT;

	        // Convert to dB, with 0 being max power.  Add a fudge factor to make
	        // a "real" fully saturated input come to 0 dB.
	        return Math.log10(power) * 10f + FUDGE;
	    }
	  // Maximum signal amplitude for 16-bit data.
	    private static final float MAX_16_BIT = 32768;
	    
	    // This fudge factor is added to the output to make a realistically
	    // fully-saturated signal come to 0dB.  Without it, the signal would
	    // have to be solid samples of -32768 to read zero, which is not
	    // realistic.  This really is a fudge, because the best value depends
	    // on the input frequency and sampling rate.  We optimise here for
	    // a 1kHz signal at 16,000 samples/sec.
	    private static final float FUDGE = 0.6f;
	  
	public void writeToFile(short[] buffer)
	{
		if(dos != null)
		{
			for(int i=0;i<buffer.length;i++)
			{
				//Log.i("AMP", Short.toString(buffer[i]));
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
