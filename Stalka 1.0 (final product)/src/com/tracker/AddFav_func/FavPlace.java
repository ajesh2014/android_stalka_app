	package com.tracker.AddFav_func;

/**
 * Code reserched 
 * from: http://developer.android.com/guide/topics/search/index.html
 * date accessed :10/2011 - 12/2011
 * 
 *  Source 2:
	 *  From : http://developer.android.com/resources/tutorials/views/hello-formstuff.html
	 *  Date:10/03/2012
 * 
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

public class FavPlace extends Activity  {
	protected EditText placename;
	
	
	private String  pname ;

	
	
 
 	private TextView info;
 	
  private  static Location loc;
 	
	
	  private DbAdapterFPlaces dbHelper;
	
	
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.fav_place);
	        
	        
	        
	        dbHelper = new  DbAdapterFPlaces (this);
	        
	        info = (TextView)findViewById(R.id.info);
	        // Initialisation of a Location Manager to get the smart phones location services. 
	        LocationManager local = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			local.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, new  TrackerLocListener());
			// Get the last known location from the GPS services.
	 loc=	 local.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	 
	 // Create a GeoCoder object.
		Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
		/** if the location object has location coordiantes then get adress of the place using 
		the GeoCoder**/
		if(loc!=null){
			// info.setText("No Address returned!");

			
			/* Source 1
			 * Geocoder
			 * from http://android-er.blogspot.co.uk/2011/02/get-address-from-location-using.html
			 * Date :03/03/2012
			 * author : unknown
			*/
			
		try {
			//Get the Adressses using the GeoCoder.
			List<Address> addresses	=	geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(), 1);
			
			if(addresses != null) {
				   Address returnedAddress = addresses.get(0);
				   // String builder to join each line of the Adress from an adress object.
				   StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
				   for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
				    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				   }// setting the TextView the adress if there is one.
				 info.setText(strReturnedAddress.toString());
			}	 else{
				// if there is no adress then set a message
				info.setText("No Address returned!");
				 }
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
	 info.setText("Service is not avalible please type in the name");
			e.printStackTrace();
		}
		
		}
		/*
		 * Source : android development guide on dialguge buttons.
		Url :http://developer.android.com/guide/topics/ui/dialogs.html
		Date accessed : 03/03/2012
	*/
		  //instante the submit button 
	        final Button button = (Button) findViewById(R.id.submit);
	        // set a On click listner. 
	         button.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	// Declare new Alert dialogue
	  AlertDialog alertDialog = new AlertDialog.Builder(FavPlace.this).create();
	  // Set title and message in the aleart dialogue 
  	alertDialog.setTitle("Saving");
  	alertDialog.setMessage("Are the details correct?");
	// Setting the "yes" button for the dialgue box
  	alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
  	      public void onClick(DialogInterface dialog, int which) {
  	    	 long result;
  	    	 // if the GeoCoder is not working get the text that is entered
  	    	if(info.getText().equals("Service is not avalible please type in the name")){
  				// Instantiate   the EditText and retive values typed.
  	    		placename = (EditText) findViewById (R.id.placename);
  	    		String place = placename.getText().toString();
  	    		// Open the Favourite places database and table.
  	    		dbHelper.open();
  	    		// insert the place name and coordinates into the favourite places table.
  	    		result =    dbHelper.createFavPlace(place, loc.getLatitude(), loc.getLongitude());
  	    		dbHelper.close();
  	    	}
  	    	//if the GeoCoder has information.
  	  	   String place = info.getText().toString();
  	  	 dbHelper.open();
  	  	 //Insert the data from the GeoCoder into the favourite places table.
  	  	 result =  dbHelper.createFavPlace(place, loc.getLatitude(), loc.getLongitude());
  	  	  // using the result of the method to identify which message to display.
  	    	if(result >-1){
  	  	Toast.makeText( getApplicationContext(),"Place Saved", Toast.LENGTH_LONG ).show();
  	  dbHelper.close();
  	    	}else if(result ==-1){
  	    		Toast.makeText( getApplicationContext(),"unsuccesfull", Toast.LENGTH_LONG ).show();
  	    	}
  	     
  	  	 	}
  	      
  	    	
  	      
  	     });
  	alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
  	      public void onClick(DialogInterface dialog, int which) {
	        	 
      	    
      	 
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
		            	
		            	
		           
		            loc = loc;
		    		   
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
		        
		        
		        
	    
	    

