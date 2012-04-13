package quantifiedAndroid.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	
   private static final String TAG = "TestService";
   
   
   @Override
   public void onCreate() {
   
	   super.onCreate();
       this.showToastMessage("Service created...");      
   
       Log.i(TAG, "Service created...");
       
   }
 
   
   @Override
   public void onStart(Intent intent, int startId) {      
       
	   super.onStart(intent, startId);  
       Log.i(TAG, "Service started...");
   
   }
   
   
   @Override
   public void onDestroy() {
       
	   super.onDestroy();
	   this.showToastMessage("Service destroyed...");
   
   }

   
   @Override
   public IBinder onBind(Intent intent) {
       
	   return null;
   }
   
   
   private void showToastMessage(String msg){
		 
	   Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

	   toast.show();

	}

}
