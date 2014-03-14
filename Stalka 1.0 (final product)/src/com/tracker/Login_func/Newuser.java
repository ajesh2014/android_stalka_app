	package com.tracker.Login_func;

/**
 * Code reserched 
 * from: http://developer.android.com/guide/topics/search/index.html
 * date accessed :10/2011 - 12/2011
 * 
 *  Source 2
 * from http://developer.android.com/guide/topics/ui/dialogs.html
 *Date  20/01/2012
 * 
 * 
 */
import com.tracker.R;
import com.tracker.TrackerActivity;
import com.tracker.httpconnector;
import com.tracker.R.id;
import com.tracker.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Newuser extends Activity  {
	protected EditText fnameText;
	protected EditText lnameText;
	protected EditText usrameText;
	protected EditText emailText;
	protected EditText passwordText;
	protected EditText RepasswordText;
	protected String android_id;
	
	private String  firstname ;
	private String 	 lastname ;
	private String  uname;
	private String  email;
	private String password ;
	private String   passchk ;
	
	private String  type;
	
 private Intent intent;
 	
 	
  
 	
	
	  private DbAdapterUser dbHelper;
	
	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.newuser);
	        
 intent = new Intent(this,TrackerActivity.class);
	        
	       
	        
	        dbHelper = new DbAdapterUser (this);

	        // the spinners list data
	        final String[] androidBooks = 
	        	{
	        	"Student",
	        	"Nurse",
	        	"Police",
	        	"Teacher"
	        	};
	        // constucting array adpter for list in spinner
	        ArrayAdapter<String> adapter = 
	                new ArrayAdapter<String> (this, 
	                android.R.layout.simple_spinner_item,androidBooks);
	        // instanting spinner from xml 
	      final Spinner  sp = (Spinner)findViewById(R.id.userType); 
	      // setting the adpater to the list
	        sp.setAdapter(adapter);
	        // setting the item selected listner.
	        sp.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

	   public void onItemSelected(AdapterView arg0, View arg1,
	     int arg2, long arg3) {
		   // assign the clicked item to the String variable.
	type =  androidBooks [arg2];
		  
		  Toast.makeText( getApplicationContext(),type , Toast.LENGTH_LONG ).show();
		 
	   }

	   public void onNothingSelected(AdapterView arg0) {
	    // TODO Auto-generated method stub

	   }

	        });
	        
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
	        //instante the submit button 
	        final Button button = (Button) findViewById(R.id.submit);
	        // set a On click listener. 
	         button.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	        
	            	 // Instance the text boxes to get the typed data. 
	     	    	fnameText = (EditText) findViewById (R.id.fname);
	     	    	lnameText= (EditText) findViewById (R.id.lname);
	     	    	usrameText= (EditText) findViewById (R.id.username);
	     	    	emailText = (EditText) findViewById (R.id.email);
	     	    	passwordText = (EditText) findViewById (R.id.password);
	     	    	RepasswordText = (EditText) findViewById (R.id.repassword);
	     	    	
	     	    	
	     	    	 firstname = fnameText.getText().toString();
	     	    	 lastname = lnameText.getText().toString();
	     	    	 uname= usrameText.getText().toString();
	     	    	 email = emailText.getText().toString();
	     	    	 password = passwordText.getText().toString();
	     	    	passchk = RepasswordText.getText().toString();
	     	    	
	     	    	android_id = Secure.getString(getBaseContext().getContentResolver(),Secure.ANDROID_ID);
	     	    	
	     	    	
		        	
		        	// Declare new Alert dialogue
	  AlertDialog alertDialog = new AlertDialog.Builder(Newuser.this).create();
	  // Set title and message in the aleart dialogue 
  	alertDialog.setTitle("Register Confirmation");
  	alertDialog.setMessage("are you sure all details are correct?");
  	// Setting the "yes" button for the dialgue box
  	alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
  	      public void onClick(DialogInterface dialog, int which) {
  	    // cheking if both passwords are the same.
  	    	if(password.equals(passchk)){
  	    		int i=0;
  	    		httpconnector con = new httpconnector();
  	    		if(con.IsConnected()==true){
  	    		// calling the method new user
  	     i =	con.Newuser(android_id, uname, email, firstname, lastname, password,type);
  	    		}
  	    	Log.i("android_id",android_id);
  	  		Log.i("username",uname);
  	  	Log.i("First name",firstname);
  		Log.i("Last name",lastname);
  		Log.i("Password",password);
  		Log.i("user type",type);
  		
  		// Using the retuned result display the according message. 
  	    	if(i==1){
  	  	    	Toast.makeText( getApplicationContext(),"registration succesfull", Toast.LENGTH_LONG ).show();
  	  	    dbHelper.open();
  	  	    	
  	  	    	dbHelper.createUser(android_id, uname, email, firstname, lastname, password, type);
  	  		

  	  	    dbHelper.close();

  	  	  startActivity(intent);
  	  	  finish();
  	  	    
  	    	}
  	    	if(i==2){
  	  	    	Toast.makeText( getApplicationContext(),"user exisits", Toast.LENGTH_LONG ).show();
  	    	}
  	  	    	
  	    	if(i==0){
  	  	    	Toast.makeText( getApplicationContext(),"Some fields are not filled in", Toast.LENGTH_LONG ).show();
  	    	}
  	    		
  	  	 	}
  	    	
  	    	if(!password.equals(passchk)){
  	    	
  	    		Toast.makeText( getApplicationContext(),"Passwords do not match try again" , Toast.LENGTH_LONG ).show();
  	    		
  	    	}
  	      
  	 
  	    } });
  	alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
  	      public void onClick(DialogInterface dialog, int which) {
	        	 
      	  
      	 
      	    } });
  	
 
  	// calling the dialogu box.
	alertDialog.show();

	            	 
	            	 
	             }
	         });
	         
	         
	     
		        }
		        
		        
		        
	    }
	    

