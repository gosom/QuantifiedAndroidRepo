package quantifiedAndroid.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;

    public class MyBroadcastReceiver extends BroadcastReceiver {


        @Override
         public void onReceive(Context context, Intent intent) {
             Bundle extras = intent.getExtras();
             if (extras != null) {
                    String state = extras.getString(TelephonyManager.EXTRA_STATE);
                    Log.w("DEBUG", state);
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        String phoneNumber = extras
                                .getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        Log.w("DEBUG", phoneNumber);
                    }
             }
        }
        
        

    }