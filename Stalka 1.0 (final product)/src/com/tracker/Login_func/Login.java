package com.tracker.Login_func;

/* SOURCE 1:local database interactiivty
	 * from : http://developer.android.com/resources/tutorials/notepad/notepad-ex2.html
	 *  Date 19/11/2011
	 *  
	 *  Source 2:
	 *  From : http://developer.android.com/resources/tutorials/views/hello-formstuff.html
	 *  Date:10/03/2012
	 *  
	 *  
	 *   Source 3:
 * from http://developer.android.com/guide/topics/ui/dialogs.html
 *Date  20/01/2012
 * 
	 */

import com.tracker.R;
import com.tracker.TrackerActivity;
import com.tracker.R.layout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

public class Login extends Activity {
    /** Called when the activity is first created. */
    
    
    ProgressDialog myProgressDialog = null;
    private DbAdapterUser dbHelper;
	private Context context = this;
       private  Cursor cursor;
       private String android_id;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        
        myProgressDialog = ProgressDialog.show(Login.this,"Please wait...", "Loading...", true); 
      //  dbHelper  = new DbAdapterUser(context);
      ///  dbHelper.open();
        /**
         * Getting android ID method
         * found code 
         * from: http://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id
         * Author :Anthony Forloney
         * Edited :7/05/2010
         * Date accesed :15/11/2011
         * 
         */
        android_id = Secure.getString(getBaseContext().getContentResolver(),Secure.ANDROID_ID);
        
        checkUser();
        
    	//Toast.makeText( getApplicationContext(),"not found" , Toast.LENGTH_LONG ).show();
    	
    	
    	 // myProgressDialog.dismiss();
    }
    
    public void checkUser(){
    	
    	        dbHelper  = new DbAdapterUser(context);
    	        dbHelper.open();
    	        // Opening user database table and checking if user exists.
    	      int i = dbHelper.getUser();
    	       String check = Integer.toString(i);
    	       // if the user does not exisit then redirect to the registration activity. 
    	       if(i==0){
    	        
    	        	dbHelper.close();
    	        	 
    	        	 Toast.makeText( getApplicationContext(),check , Toast.LENGTH_LONG ).show();
    	        	  Intent intent = new Intent(this, Newuser.class);
    	        	  startActivity(intent);
    	        	 
    	        	  finish();
    	        	  
    	        	// if the user exisits then redirect to main actiivty. 
    	       }else if(i==1){
   
    	
    	dbHelper.close();
    //	Toast.makeText( getApplicationContext(),"found" , Toast.LENGTH_LONG ).show();
    	Log.i("Login procces", "Record found");
  	  Intent intent2 = new Intent(this,TrackerActivity.class);
  	  startActivity(intent2);
  	  finish();

  	  			
    	        }
    
    
    }
    
    
    
    
    
    
}