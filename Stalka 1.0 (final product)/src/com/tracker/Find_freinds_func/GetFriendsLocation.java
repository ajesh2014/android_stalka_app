package com.tracker.Find_freinds_func;

/**
* Class constructed  and adpted from a range of sources
* SOURCE 1:
* From:http://developer.android.com/guide/topics/location/obtaining-user-location.html
* Date 18:/11/2011
* 
* SOURCE 2:
* from : http://www.firstdroid.com/2010/04/29/android-development-using-gps-to-get-current-location-2/
*  Date 19:/11/2011
*  
*  SOURCE 3:
*  /// Code adapted from : http://androiddevelopement.blogspot.com/2011/09/connecting-to-remote-mysql-database.html
// Author:  Krishna //// Date accessed :12/01/2012

*  
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


public class GetFriendsLocation extends  MapActivity {
    public static final String TEXT_DATA = null;
	/** Called when the activity is first created. */
	LinearLayout linearLayout;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	MapController con;
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
        
      //  dbHelper  = new DbAdapterLoc(this);
      //  dbHelper.open();
        
       Bundle extras = getIntent().getExtras();
       phone_id = extras.getString("PHONE_ID");
      
       TextView title = (TextView)  this.findViewById(R.id.textView1); 
    		title.setText("Tracking:"); 

     
       connector = new httpconnector();
     
     
       //System.out.print(test);
       //Toast.makeText( getApplicationContext(), s , Toast.LENGTH_SHORT ).show();
      
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        
     
       
        con = mapView.getController();
	con.setZoom(8); // Zoon 1 is world view
		
		 
        
     GeoPoint point = connector.getUserLoc(phone_id) ;
     OverlayItem  overlayitem = new OverlayItem(point, "hello", "bugger");
        
		  mapOverlays = mapView.getOverlays();
	        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
	   
	        itemizedOverlay = new TrackerItemizedOverlay(drawable,mapView.getContext());
	        mapOverlays.add(itemizedOverlay);
	      con.animateTo(point); 
 ///////////////////////////////////////////////////
	       /*
		       * code adpated to run as handler
		       * from : http://www.mopri.de/?p=1438
		       * date :26/11/2011
		       * 
		       */
	    handler = new Handler();
	      
	    runnable = new Runnable() {
	    	   @Override
	    	   public void run() {
	    	    
	    	        GeoPoint point = connector.getUserLoc(phone_id);
	    	     
	    	        OverlayItem   overlayitem = new OverlayItem(point, "hello", "bugger");
	    	        mapOverlays.add(itemizedOverlay);
	    	      con.animateTo(point);
	    	      mapOverlays.remove(0);
	    	        Toast.makeText( getApplicationContext(),"Checked for updated Cords" , Toast.LENGTH_SHORT ).show();
	    	      /* and here comes the "trick" */
	    	      handler.postDelayed(this, 5000);
	    	   }
	    	};
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





	