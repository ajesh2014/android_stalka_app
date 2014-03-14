package com.tracker.Find_freinds_func;
/*
* Class constructed  and adpted from a range of sources
* SOURCE 1:
* From:http://developer.android.com/guide/topics/location/obtaining-user-location.html
* Date 19/11/2011
* 
* SOURCE 2:
* from : http://developer.android.com/resources/tutorials/notepad/notepad-ex2.html
*  Date 19/11/2011
*  
*  SOURCE 3:
*  /// Code adapted from : http://androiddevelopement.blogspot.com/2011/09/connecting-to-remote-mysql-database.html
// Author:  Krishna //// Date accessed :12/01/2012
* 
*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.tracker.R;
import com.tracker.httpconnector;
import com.tracker.Direction_func.Directuser;
import com.tracker.Login_func.DbAdapterUser;
import com.tracker.Mylocation_func.DbAdapterLoc;

public class Friend_Requests  extends ListActivity {
	private Context mCtx;
	protected EditText searchText;
	protected SQLiteDatabase db;
	protected Cursor cursor;
//	protected ListAdapter adapter;
	protected ListView List;
	 private static final int ACTIVITY_EDIT=1;
	 private  DbAdapterLoc dbHelper;
	 private  DbAdapterUser dbHelperUser;
	 private final Activity activity = this;
	 private Cursor listCursor;
	private  httpconnector servconn;
    private  JSONArray listarray= null;
	private String phoneid; 
	 private Handler _handler;
	 private  adapter adp ;
	 
	 
    /** Called when the activity is first created. */
	   double  lat=0;
	   double lng =0;
   
	 Handler handler;
	  Runnable runnable;
	    
	    ProgressDialog myProgressDialog = null;
	    
	
	    
	    
	    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
     
        mCtx = this;
        TextView title = (TextView)  this.findViewById(R.id.title); 
        		title.setText("Friend Requests:"); 
        		
    	servconn = new httpconnector();
        myProgressDialog = ProgressDialog.show(this,"Please wait...", "Loading...", true); 
        
       dbHelper = new   DbAdapterLoc(this);
    //////////////////////////////////////////////////////////    
       //checking for internet connection.
       if(servconn.IsConnected()){
	    	    fillData();
	    	    // Initialising user database adapter
	    	    dbHelperUser = new DbAdapterUser(this);
	    	    
       }else{
    	   Toast.makeText( getApplicationContext(),"No internet please connect" , Toast.LENGTH_LONG ).show();
       }
	    	 
	    	
	    	
    
	    	
    }
    
       
  
    
 //dbHelper.close();
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0,"Main Menu");
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case 0:
            //    createNote();
            	finish();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
 
    
   

    
    
    private void fillData() {
    	
    	
    	  /*
	       * code adpated to run as handler
	       * from : http://www.mopri.de/?p=1438
	       * date :26/11/2011
	       * 
	       */
    	// Handler object
   	    handler = new Handler();
   	      
   	    runnable = new Runnable() {
   	    	   @Override
   	    	   public void run() {

      //Open the location databse and obtain location from locaiton table. for distance.
   	    		dbHelper.open();
   	    		// get distance.
   	    		 Location distance = dbHelper.getLocCords();
   	    		 //close the locaiton database.
   	    		 dbHelper.close();
      // Open the user database, obtain the smartPhone ID.
 	    		dbHelperUser.open();
    	    		phoneid = dbHelperUser.getUserPhoneID();
    	    		Log.i("test",phoneid);
    	    		dbHelperUser.close();
    	  // checking for internet connectivity
    	    		 if(servconn.IsConnected()){
    	    			   /** Get all friend request using the httpconnector's method, use the smartphoneid
    	    	    	    to identify which user.**/
   	    		  listarray = servconn.getFriendReq(phoneid);
    	    		 } else {
    	    			 Toast.makeText( getApplicationContext(),"No internet connection" , Toast.LENGTH_LONG ).show();
    	    		 }
   	    		  // check if the returned JSON array is not null before displaying data.
   	    		  if(listarray!=null){
   	           //The list adapter which is converts the JSON data into a list.
   	            adp = new  adapter ( mCtx ,listarray,distance) ;
   	            // setting the returned list to the main view.
   	           setListAdapter(adp);
   	           /** Telling the adapter that the list's items may have changed 
   	            so the list can be updated.  **/
   	             adp.notifyDataSetChanged();
   // setting a context menu (options menu) for each list item.
    registerForContextMenu(getListView()); 
	Log.i("testing thread", "checked for distance updates");
 
   	    		  }
// dismissing the progress dialog when called the first time.
 myProgressDialog.dismiss();
 // delayign the runnable sent to the message stack.
  handler.postDelayed(this, 5000);
	    	   }
	    	};
	    	 handler.postDelayed(runnable,5000);
    }

    
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
        activity.openContextMenu(view);
				
			}
   

    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
MenuInflater inflater = getMenuInflater();
inflater.inflate(R.menu.friends_req_menu, menu);
}
    
    
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
        case R.id.Accept:
        	
        	 try {
        		 
        		 // getting a JSON object which identfies the requesters infromation.
				JSONObject data = listarray.getJSONObject(info.position);
				
	Toast.makeText( getApplicationContext(),data.getString("sender_user_ID") , Toast.LENGTH_LONG ).show();
	         // Sendign off answer of accepting.
			boolean check =	 servconn.AcceptFriendReq(phoneid, data.getString("sender_user_ID"));
			
			// As friend accepted restart actiivty.
				if(check==true){
					 Toast.makeText( getApplicationContext(),"friend added succfully" , Toast.LENGTH_LONG ).show();
					
						  adp.notifyDataSetChanged();
						  // intent identifying to rester activity.
				        	Intent i = new Intent(this, Friend_Requests.class);
							finish();
			    			 startActivity(i);
				}else{
					 Toast.makeText( getApplicationContext()," freind not added succefully" , Toast.LENGTH_LONG ).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
            return true;
            
        case R.id.Dec:
            try{
        	 JSONObject data = listarray.getJSONObject(info.position);
			 Toast.makeText( getApplicationContext(),data.getString("sender_user_ID") , Toast.LENGTH_LONG ).show();
			 boolean check =	 servconn.DeclineFriendReq(phoneid, data.getString("sender_user_ID"));
			 if(check==true){
				 Toast.makeText( getApplicationContext(),"declined" , Toast.LENGTH_LONG ).show();
				
					  adp.notifyDataSetChanged();
			        	Intent i = new Intent(this, Friend_Requests.class);
			        	finish();
		    			 startActivity(i);
			}else{
				 Toast.makeText( getApplicationContext()," not working" , Toast.LENGTH_LONG ).show();
			}
            } catch (Exception e){
            	
            }
        	
            return true;
       
            default:
                return super.onContextItemSelected(item);
        }
    }

    
  
        
 protected void onPause() {
	  super.onPause();
	  handler.removeCallbacks(runnable);
	
}
        
   
}
