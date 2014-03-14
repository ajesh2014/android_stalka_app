package com.tracker;

/**
 * Class constrcuted and adpateed 
 * 
 * Source 1
 * from http://marakana.com/forums/android/examples/60.html
 * Date :27/11/2011
 * author :Serete Itebete
 * 
 *  SOURCE 2:
 * from : http://www.firstdroid.com/2010/04/29/android-development-using-gps-to-get-current-location-2/
 * 
 *  Date 30:/10/2011
 * 
 * 
 */

import com.tracker.Login_func.DbAdapterUser;
import com.tracker.Mylocation_func.DbAdapterLoc;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

	public class LocUpdService extends Service {
	private  DbAdapterLoc dbHelper;
	private  DbAdapterUser dbHelperuser;
	private Context context = this;
	private LocationManager local;
	double lat;
	double lng;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}	

   @Override
   	public void onCreate() {
	   //code to execute when the service is first created
	   //Initialising of the database adapter for the location database.
	   dbHelper = new DbAdapterLoc(this);
	 //  Toast.makeText( getApplicationContext(), "oncreate"  , Toast.LENGTH_SHORT ).show();
	 //Initialising of the database adapter for the user database database.
	   dbHelperuser = new DbAdapterUser(this);
   	}

   @Override
   	public void onDestroy() {
	   
   }
   
   	@Override
   	public void onStart(Intent intent, int startid) {
   		//code to execute when the service is starting up
   		//Toast.makeText( getApplicationContext(), "start"  , Toast.LENGTH_SHORT ).show();
	   // Initialisation of a Location Manager to get the smart phones location services. 
   		LocationManager local = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
   		// Method to get the current provider available. 
   		getProvider(local);
   		
   }
   
   
   
   	public class TrackeLocListener implements LocationListener
   {
   		@Override
   		public void onLocationChanged(Location loc)
       {
   			int lat = (int) (loc.getLatitude() * 1E6);
   			int lng = (int) (loc.getLongitude() * 1E6);
   		
   			// storing location to local database table first
   		 dbHelper.open();
   			boolean a = dbHelper.updateCords(loc.getLongitude(),loc.getLatitude());
   		
   			Log.i("Local update"," Latitude = " + loc.getLatitude() +
   	                " Longitude = " + loc.getLongitude());
   			
   			if (a==true){	
   			String phone   = Secure.getString(getBaseContext().getContentResolver(),Secure.ANDROID_ID);
   				httpconnector con = new httpconnector();
   				// Checking to see Internet connection
   			if(con.IsConnected()==true){
   		con.updateLocation(loc.getLatitude(),loc.getLongitude(), phone);
   		
   		Log.i("Online update"," Latitude = " + loc.getLatitude() +
                " Longitude = " + loc.getLongitude());
   			}
   		
   			dbHelper.close();
   			// if not upated check if there is a user
   			} if (a==false){
   			
   			 dbHelperuser.open();
   		int user =	dbHelperuser.getUser();
   			// if there is no user there is a system error
   			if(user ==0){
   			
   				Log.i("service check", "malfunction..");
   				
   				dbHelperuser.close();
   			}
   			
       else{
    	   // if user then update local database table with location data.
    	   dbHelper.open();
   				dbHelper.createCords(loc.getLongitude(),loc.getLatitude());
   				dbHelper.close();
   			//	dbHelperuser.close();
       }
   				
       }
   			
       
       }

       @Override
       public void onProviderDisabled(String provider)
       {
           Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
     // local.removeUpdates(this);
          //getProvider(local);
       }

       @Override
       public void onProviderEnabled(String provider)
       {
           Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
           //getProvider(local);
       }

       @Override
       public void onStatusChanged(String provider, int status, Bundle extras)
       {
       }

       
   
   
   

   }

   	public void getProvider (LocationManager localman){
   		if(localman.isProviderEnabled(LocationManager.GPS_PROVIDER)==true && localman.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==false){

			Log.i("service check", "gps chosen");
			localman.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, new TrackeLocListener());
		
		} else if(localman.isProviderEnabled(LocationManager.GPS_PROVIDER)==false && localman.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==true){
			localman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, new TrackeLocListener());

			Log.i("service check", "network chosenh");
		}else if(localman.isProviderEnabled(LocationManager.GPS_PROVIDER)==true && localman.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==true){
			Log.i("service check", "network chosenh");
			localman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, new TrackeLocListener());
			if(localman.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)== null)

				Log.i("service check", "chosne gps because of no network updates");
			
			localman.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, new TrackeLocListener());
	
		}else if(localman.isProviderEnabled(LocationManager.GPS_PROVIDER)==false && localman.isProviderEnabled(LocationManager.NETWORK_PROVIDER)==false){
			Log.i("service check", "no serivesce");
		}
   	}
   		
}