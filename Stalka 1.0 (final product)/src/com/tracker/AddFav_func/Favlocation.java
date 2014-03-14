package com.tracker.AddFav_func;

/*
 * Class constructed  and adpted from a range of sources
 * SOURCE 1:
 * From:http://developer.android.com/guide/topics/location/obtaining-user-location.html
 * Date 29:/10/2011
 * 
 * SOURCE 2:
 * from : http://www.firstdroid.com/2010/04/29/android-development-using-gps-to-get-current-location-2/
 * 
 *  Date 30:/10/2011
 *  Source 3:
	 *  From : http://developer.android.com/resources/tutorials/views/hello-formstuff.html
	 *  Date:10/03/2012
 */

import java.util.List;




import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.tracker.R;
import com.tracker.httpconnector;
import com.tracker.R.drawable;
import com.tracker.R.id;
import com.tracker.R.layout;
import com.tracker.Mylocation_func.TrackerItemizedOverlay;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Favlocation extends  MapActivity {
    public static final String TEXT_DATA = null;
	/** Called when the activity is first created. */
	LinearLayout linearLayout;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	MapController controller;
	GeoPoint point ;
	
	 TrackerItemizedOverlay itemizedOverlay ;
	 public  SQLiteDatabase db;
		public  Cursor cursor;
		 private  DbAdapterFPlaces dbHelper;
		private Context conte = this;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylocation);
        
        
        Bundle extras = getIntent().getExtras();
        String ide = extras.getString("ID");
      long   id = Long.parseLong(ide);
      dbHelper = new  DbAdapterFPlaces(this);
      dbHelper.open();
        
      TextView title = (TextView)  this.findViewById(R.id.textView1); 
		title.setText("Favourite place"); 
      
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
     
        
       
        controller = mapView.getController();
        controller.setZoom(5); // Zoon 1 is world view
		
        
	        
	     
	       
        
       GeoPoint  loc =dbHelper.getPlacePoint(id);
       dbHelper.close();
       if(loc!=null){
    	   mapOverlays = mapView.getOverlays();
	        
	        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
	        itemizedOverlay = new TrackerItemizedOverlay(drawable,mapView.getContext());
	        
	     OverlayItem overlayitem = new OverlayItem(loc, "insert", "insert");
	     itemizedOverlay.addOverlay(overlayitem);
	     mapOverlays.add(itemizedOverlay);
	 
			     controller.animateTo(loc); 
       }
	       
    }

    
    
  
	
	
    protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
    }
    
    
}





	