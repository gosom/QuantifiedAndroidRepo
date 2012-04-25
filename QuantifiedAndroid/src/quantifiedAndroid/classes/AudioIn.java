package quantifiedAndroid.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.os.Environment;
import android.util.Log;

public class AudioIn extends Thread { 
    
	private static final int RECORDER_BPP = 16;
	private static final int RECORDER_SAMPLERATE = 22050;
	private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
	private static final String AUDIO_RECORDER_FOLDER = "QuantifiedAndroid";
	private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";
	private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
	private AudioRecord recorder;
	private int N;
	private FileOutputStream os;
	
	public boolean stopped = false;
    private String TAG = "AUDIO_IN";

    public AudioIn() { 
            
    	android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        String filename = getTempFilename();
        N = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);

        recorder = new AudioRecord(AudioSource.MIC,
      		  					RECORDER_SAMPLERATE,
                                   AudioFormat.CHANNEL_IN_MONO,
                                   AudioFormat.ENCODING_PCM_16BIT,
                                   N*10);
        
        try {
                os = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
                Log.e("EROR", e.getMessage());
                e.printStackTrace();
        }
     }

    @Override
    public void run() { 
           byte[][]   buffers  = new byte[256][160];
           int         ix       = 0;

           try { // ... initialise

                
                  recorder.startRecording();

                  // ... loop

                  while(!stopped) { 
                     byte[] buffer = buffers[ix++ % buffers.length];

                     N = recorder.read(buffer,0,buffers.length);

                     //process(buffer);
                     os.write(buffer);
                     
                 }
            } catch(Throwable x) { 
              Log.e("EROR", "Error reading voice audio",x);
            } finally { 
              close();
            }
        }

     public void close() { 
    	 
         stopped = true;
         recorder.stop();
         recorder.release();
         try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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