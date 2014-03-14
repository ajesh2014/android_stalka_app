package com.tracker.Mylocation_func;

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
 */

import java.util.List;




import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.tracker.R;
import com.tracker.LocUpdService.TrackeLocListener;
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
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Mylocation extends  MapActivity {

	/** Called when the activity is first created. */
   
	private MapView mapView;
	private	List<Overlay> mapOverlays ;
	private	Drawable drawable;
	private	MapController controller;
	private	GeoPoint point ;
	
	 TrackerItemizedOverlay itemizedOverlay ; 
	 private  SQLiteDatabase db;
		private  Cursor cursor;
		 private  DbAdapterLoc dbHelper;
		private Context context= this;
		
		public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting the view
        setContentView(R.layout.mylocation);
      // Getting as a MapView variable from the the parent view
        mapView = (MapView) findViewById(R.id.mapview);
        // Enabling the Mapview controls as true, so the user can zoom in.
        mapView.setBuiltInZoomControls(true);
       // Getting a Mapview's controller to allow panning and zoom programmaticly.
        controller = mapView.getController();
        // Set the zoom on the map to 1 which is street level.
	    controller.setZoom(8);
	    // Location manager provides a layer to access the smartphone's location services.
		LocationManager local = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		/** Method to get and set the provider that is available in order to get location updates 
		 at regular intervals.**/
		String provider=getProvider(local);
		// getting a location from the location provider specified on first start.
	   	Location loc=	local.getLastKnownLocation(provider);
	   	/** if both location providers are not on, as the activity starts this will stop
	   	 the placement of map overlays on the map, and the map centring around a null point **/
		if(loc != null){
			/** converting a location object to a GeoPoint object from Location obejct to allow marker to 
			be added.**/
	   		GeoPoint	point = new GeoPoint((int)(loc.getLatitude()* 1E6),(int)( loc.getLongitude()* 1E6));
		// Get all overlay objects that may be on the map into an array.
	   	 mapOverlays = mapView.getOverlays();
       // Initialise  a drawable for the marker which can be place on the MapView. 
	      drawable = this.getResources().getDrawable(R.drawable.androidmarker);
	   // Initialize a personalized MapOVerlay item.
	      itemizedOverlay = new TrackerItemizedOverlay(drawable,mapView.getContext());
	      OverlayItem   overlayitem = new OverlayItem(point, "Notice", "You are here");
		      // add the normal Overlay item to the personalized overlay.
	      //this adds the custom image to the overlay.
		    itemizedOverlay.addOverlay(overlayitem);
		    // add the custom overlay to the Mapoverlays.
		    mapOverlays.add(itemizedOverlay);
		    // get the MapView controller to animate towards the GeoPoint.
		     controller.animateTo(point); 
		}
    }

    
    
  
   // Location listener which interfaces hardware to detect changes in location.
	public class TrackerLocListener implements LocationListener
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
			 point = new GeoPoint(lat, lng);
			 
			 /** remove the previous overlay so the another
			 can be added **/
			 itemizedOverlay.removeOverlay(0);
			 
		
			  //this follows the same steps in the OnCreate function at start up.
			 //   //Initialize a normal MapOVerlay item with new point.
			 OverlayItem overlayitem = new OverlayItem(point, "kiddda!", "sasri kal!");
		        
		        itemizedOverlay.addOverlay(overlayitem);
		        mapOverlays.add(itemizedOverlay);
		        // move to new point.
			controller.animateTo(point); 
			 
	
			//debug message to say the location has been read.
			Log.i("Listener", "location read");
			 
		

		        
        //    String Text = "My current location is: " +
			Log.i("service check"," Latitude = " + loc.getLatitude() +
                 " Longitude = " + loc.getLongitude());
 
         //   Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_LONG).show();
        
        }
 
        @Override
        // methods to be called when change in state of the selected provider.
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
    
    // method to select a location provider that is available.
   	public String getProvider (LocationManager localman){
   		String provider ="";
   		
   		// if gps is one and network is off then user gps
   		if(localman.isProviderEnabled(LocationManager.GPS_PROVIDER)==true && localman.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==false){

			Log.i("service check", "gps chosen");
			localman.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, new TrackerLocListener());
	 provider =LocationManager.GPS_PROVIDER;
			
	 
	 // if network is on and gps is off then chose gps
		} else if(localman.isProviderEnabled(LocationManager.GPS_PROVIDER)==false && localman.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==true){
			localman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, new TrackerLocListener());
			 provider =LocationManager.NETWORK_PROVIDER;
		
			Log.i("service check", "network chosen");
			
			// if both are on then chose network
		}else if(localman.isProviderEnabled(LocationManager.GPS_PROVIDER)==true && localman.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==true){
			Log.i("service check", "network chosen");
			localman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, new TrackerLocListener());
		provider =	LocationManager.NETWORK_PROVIDER;
		
		// check if network is supported by getting a location fix.
		// if no location fix then chose gps.
			if(localman.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)== null)

				Log.i("service check", "chosen gps because of no network updates");
			
			localman.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, new TrackerLocListener());
		provider =	LocationManager.GPS_PROVIDER;
		}
   		return provider;
   	}
    
  
	
	
    protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
    }
    
    
}





	