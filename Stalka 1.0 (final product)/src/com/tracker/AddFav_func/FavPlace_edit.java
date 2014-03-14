	package com.tracker.AddFav_func;

/**
 * Code reserched 
 * from: http://developer.android.com/guide/topics/search/index.html
 * date accessed :10/2011 - 12/2011
 * 
 * 
 */
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import com.tracker.R;
import com.tracker.Mylocation_func.Mylocation.TrackerLocListener;
import com.tracker.R.id;
import com.tracker.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.TextView;
import android.widget.Toast;

public class FavPlace_edit extends Activity  {
	protected EditText placename;
	
	
	private String  pname ;

	
	
 
 	private TextView info;
 	
  private  static Location loc;
 	
	private static Intent i ;
	  private DbAdapterFPlaces dbHelper;
	
	private long id;
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.fav_place);
	        
	     i = new Intent(this, Veiw_fav_place.class);
	        
	        /**
	         * Getting android ID method
	         * found code 
	         * from: http://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id
	         * Author :Anthony Forloney
	         * Edited :7/05/2010
	         * Date accesed :15/11/2011
	         * 
	         */
	        Bundle extras = getIntent().getExtras();
	        String ide = extras.getString("ID");
	         id = Long.parseLong(ide);
	        
	        dbHelper = new  DbAdapterFPlaces (this);
	        
	        info = (TextView)findViewById(R.id.info);
	       
	        dbHelper.open();
	        String name =  dbHelper.getPlace(id);
	        dbHelper.close();
	 info.setText(name);
			
		
	  /*      
	  	 
	    
	
	         
		try {
			List<Address> addresses	=	geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
			
			if(addresses != null) {
				   Address returnedAddress = addresses.get(0);
				   StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
				   for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
				    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				   }
				 info.setText(strReturnedAddress.toString());
			
			
		}else{
			   info.setText("No Address returned!");
		  }*/
		
	       
		
	
	   
	        final Button button = (Button) findViewById(R.id.submit);
	         button.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	
	            	 
	            	 

	     	    	
	            	 //placename = (EditText) findViewById (R.id.placename);
	     	    
	     	    	
	     	    	
	            	// pname = placename.getText().toString();
	     	    	
	     	    	
	     	    	
	     	    	
	     	    	
		        	// create instert query	
		        	
	  AlertDialog alertDialog = new AlertDialog.Builder(FavPlace_edit.this).create();
  	alertDialog.setTitle("Reset...");
  	alertDialog.setMessage("R u sure?");
  	
  	
  	
  	
  	alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
  	      public void onClick(DialogInterface dialog, int which) {
  	    
  	    
  				
  			
  	    	
  	    		
  	    		placename = (EditText) findViewById (R.id.placename);
  String place = placename.getText().toString();
  	    	
  	  	    	
  	  	    dbHelper.open();
  	  	    	
  	  boolean result =    dbHelper.updatePlace(id, place);
  	  dbHelper.close();
  	    	if(result ==true){
  	  	Toast.makeText( getApplicationContext(),"works", Toast.LENGTH_LONG ).show();
 
  	finish();

	
	 startActivity(i);
  	    	}else if(result == false){
  	    		Toast.makeText( getApplicationContext(),"unsuccesfull", Toast.LENGTH_LONG ).show();
  	    	}
  	 
  	  	 	}
  	      
  	    	
  	       //here you can add functions
  	 
  	     });
  	alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
  	      public void onClick(DialogInterface dialog, int which) {
	        	 
      	       //here you can add functions
      	 
      	    } });
  	
  	//alertDialog.setIcon(R.drawable.androidmarker);
  	
  	
  	
  	
  	
	alertDialog.show();

	            	 
	            	 
	             }
	         });
	         
	    
	     
		        }public class TrackerLocListener implements LocationListener
		        {
		            @Override
		            public void onLocationChanged(Location loc)
		            {
		            	
		            	
		            	/*
		            	 * converting to a geopoint:
		            	 * from :http://stackoverflow.com/questions/5810898/adding-path-between-two-overlay-items-using-gps-android
		            	 * Date accessed 1/11/2011
		            	 * 
		            	 * 
		            	 * 
		            	 */
		            	int lat = (int) (loc.getLatitude() * 1E6);
		    			int lng = (int) (loc.getLongitude() * 1E6);
		    			
		    			
		    		   
		    		//	mapController.setCenter(point);
		    			 
		    	
		    			Log.i("DebugMessage", "works..");
		    			 
		    		

		    		        
		                String Text = "My current location is: " +
		                              "Latitude = " + loc.getLatitude() +
		                              "Longitude = " + loc.getLongitude();
		     
		                Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_LONG).show();
		            
		            }
		     
		            @Override
		            public void onProviderDisabled(String provider)
		            {
		                Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
		            }
		     
		            @Override
		            public void onProviderEnabled(String provider)
		            {
		                Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
		            }
		     
		            @Override
		            public void onStatusChanged(String provider, int status, Bundle extras)
		            {
		            }
		        }
}
		        
		        
		        
	    
	    

