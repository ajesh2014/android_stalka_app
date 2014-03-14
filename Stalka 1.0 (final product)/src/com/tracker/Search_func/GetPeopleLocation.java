package com.tracker.Search_func;

/**
* Class constructed  and adpted from a range of sources
* SOURCE 1:
* From:http://developer.android.com/guide/topics/location/obtaining-user-location.html
* Date 18:/11/2011
* 
* SOURCE 2:
* from : http://www.firstdroid.com/2010/04/29/android-development-using-gps-to-get-current-location-2/
* 
*  Date 19:/11/2011
**/
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;




import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.tracker.R;
import com.tracker.httpconnector;
import com.tracker.Mylocation_func.DbAdapterLoc;
import com.tracker.Mylocation_func.TrackerItemizedOverlay;
import com.tracker.R.drawable;
import com.tracker.R.id;
import com.tracker.R.layout;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class GetPeopleLocation extends  MapActivity {
    public static final String TEXT_DATA = null;
	/** Called when the activity is first created. */
	LinearLayout linearLayout;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	MapController controller;
	GeoPoint point ;
	 private  DbAdapterLoc dbHelper;
	 
	 TrackerItemizedOverlay itemizedOverlay ;
	 public  SQLiteDatabase db;
		public  Cursor cursor;
		int delay = 5000; // delay for 5 sec.

		int period = 1000; // repeat every sec.
		int lat =0;
		int lng =0;
		 String phone_id="";
		 Handler handler;
		  Runnable runnable;
		  httpconnector connector;
		  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylocation);
        
        TextView title = (TextView)  this.findViewById(R.id.textView1); 
		title.setText("Tracking:"); 
		
      //A bundle object to get the extras that have been passed.
       Bundle extras = getIntent().getExtras();
       // Obtain the phone id sent from the intent which are stored as extra's
       phone_id = extras.getString("PHONE_ID");
    // Getting as a MapView variable from the the parent view
        mapView = (MapView) findViewById(R.id.mapview);
        
        mapView.setBuiltInZoomControls(true);
        
        controller = mapView.getController();
        controller.setZoom(8); // Zoon 1 is world view
     // Get all overlay objects that may be on the map into an array.
        mapOverlays = mapView.getOverlays();
        // Initialise  a drawable for the marker which can be place on the MapView. 
        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        itemizedOverlay = new TrackerItemizedOverlay(drawable,mapView.getContext());
        
	// Initialise httpconnector
	connector = new httpconnector();
      // Checking for Internet connectivity.
	if(connector.IsConnected()==true){
		 // Call httpconnector's method to get a users current location as a GeoPoint
     GeoPoint point = connector.getUserLoc(phone_id) ;
     // adding the GeoPoint received from the connector. 
     OverlayItem overlayitem = new OverlayItem(point, "Tracked user", "They are here");
     // add the normal Overlay item to the personalized overlay.
     //this adds the custom image to the overlay.
     itemizedOverlay.addOverlay(overlayitem);
     // add the custom overlay to the Mapoverlays.
     mapOverlays.add(itemizedOverlay);
     // get the MapView controller to animate towards the GeoPoint.
		     controller.animateTo(point); 
	}
 ///////////////////////////////////////////////////
	       /*
		       * code adpated to run as handler
		       * from : http://www.mopri.de/?p=1438
		       * date :26/11/2011
		       * 
		       */
		     // Handler object
	   handler = new Handler();
	      // Runnable object encapsulating
	    runnable = new Runnable() {
	    	   @Override
	    	   public void run() {
	    		  // Removing the current overlay showingthe old locaiton. 
	    		   itemizedOverlay.removeOverlay(0);
	    	       // the connector recallls the getUserLoc, returning the GeoPoint with a new location.
	    		   GeoPoint point = connector.getUserLoc(phone_id) ;
	    		   //adding another placemark.
	    		     OverlayItem overlayitem = new OverlayItem(point, "insert", "insert");
	    		     itemizedOverlay.addOverlay(overlayitem);
	    		     mapOverlays.add(itemizedOverlay);
	    		 // moving the map to the new locaiton.
	    				     controller.animateTo(point); 
	    	     //pausing the runnable
	    	      handler.postDelayed(this, 5000);
	    	   }
	    	};// pausing the handler for 5 seconds.
	    	 handler.postDelayed(runnable,5000);
    }

    
 //////////////////////////////////////////////   
  
    
    protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
    }
    protected void onPause() {
    	  super.onPause();
    	handler.removeCallbacks(runnable);
    }
    
    protected void onDestroy() { 
    	handler.removeCallbacks(runnable);
        finish(); 
       
// TODO Auto-generated method stub 
        super.onDestroy(); } 
}





	