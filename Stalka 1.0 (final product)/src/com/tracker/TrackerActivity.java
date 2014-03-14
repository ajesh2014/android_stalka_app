package com.tracker;
/**
 * Code reserched 
 * from: http://developer.android.com/guide/topics/search/index.html
 * date accessed :10/2011 - 12/2011
 * 
 * Source 2:
	 *  From : http://developer.android.com/resources/tutorials/views/hello-formstuff.html
	 *  Date:10/03/2012
 * 
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tracker.AddFav_func.FavPlace;
import com.tracker.AddFav_func.FavPlace_menu;
import com.tracker.AddFav_func.Veiw_fav_place;
import com.tracker.Find_freinds_func.Friends_menu;
import com.tracker.Message_func.Messaging_menu;
import com.tracker.Mylocation_func.Mylocation;
import com.tracker.Search_func.search_menu;


public class TrackerActivity extends Activity {
    /** Called when the activity is first created. */
 

	  private String android_id;
	
	  private Context context = this;
	

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      android_id = Secure.getString(getBaseContext().getContentResolver(),Secure.ANDROID_ID);
       //db = (new LoginDbHelper(this)).getWritableDatabase();
     // cursor = db.rawQuery("SELECT Phone_ID FROM login  WHERE Phone_ID = ?",new String[]{"%"+android_id +"%"});
  
	// Location location = local.getLastKnownLocation(LocationManager.GPS_PROVIDER);
     
      Intent myIntent = new Intent(getApplicationContext(), LocUpdService.class);
      
      startService(myIntent);
    
	//update(1,lng, lat);
       // TelephonyManager telemngr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
       // String device_id = telemngr.getDeviceId();
      
       //Toast.makeText( getApplicationContext(), android_id  , Toast.LENGTH_SHORT ).show();
	 //Toast.makeText( getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
	 
     //  if( cursor.getCount()==0){
    		  
	
  } 
    
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0,"Exit App");
      
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case 0:
            //    createNote();
            	onDestroy();
            	stopService(new Intent(this, LocUpdService.class));
                return true;
                
        
        }

        return super.onMenuItemSelected(featureId, item);
    }
 

    		  
    		// this.newuser();
    //	  }
    
    
    
  public void MyLocation(View v) {
    	
        //create a new Intent
        Intent i = new Intent(this, Mylocation.class);
        //start the activity
        startActivity(i);
    }
  public void MyFriendLocation(View v) {
  	
      String thefriends = "Ajesh";
     
      //create a new Intent
      Intent i = new Intent(this,Friends_menu.class);
      //add theText to the intent
     // i.putExtra(Myfriends.friends_DATA, thefriends);
      //start the activity
      startActivity(i);
  }
  public void favplaces(View v){
	  Intent i = new Intent(this,FavPlace_menu.class);
	  startActivity(i);
  }
  
  public void SearchPeople(View v){
	  Intent i = new Intent(this,search_menu.class);
	  startActivity(i);
  }
  public void Messaging(View v){
	  Intent i = new Intent(this,Messaging_menu.class);
	  startActivity(i);
  }

  
 /** public void newuser() {
	  	
	  //Toast.makeText( getApplicationContext(), android_id  , Toast.LENGTH_SHORT ).show();
	//  
	Intent i = new Intent(this, Newuser.class);
      //add theText to the intent
   
      //start the activity
     startActivity(i);
  }**/

  
  
  
    
  
  
  protected void onPause() {
	  super.onPause();
	//handler.removeCallbacks(runnable);
}

protected void onDestroy() { 

    finish(); 
   
//TODO Auto-generated method stub 
    super.onDestroy(); } 



}
  
