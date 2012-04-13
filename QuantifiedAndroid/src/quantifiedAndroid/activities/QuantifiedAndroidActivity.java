package quantifiedAndroid.activities;

import quantifiedAndroid.packages.namespace.R;
import quantifiedAndroid.services.MyService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuantifiedAndroidActivity extends Activity {
	
	Button start;
	
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {      
       
	   super.onCreate(savedInstanceState);
	   
	   setContentView(R.layout.main);
	   
	   start = (Button) findViewById(R.id.start_button);
	   
	}
	
	
	public void startService(View view){
		
		startService(new Intent(this, MyService.class));
		
	}
	
	
	public void stopService(View view){
		
		stopService(new Intent(this, MyService.class));
	
	}
	
	


	
}