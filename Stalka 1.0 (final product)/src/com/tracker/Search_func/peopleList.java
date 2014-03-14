package com.tracker.Search_func;

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
 * Source 3:
 * from http://developer.android.com/guide/topics/ui/dialogs.html
 *Date  20/01/2012
 * 
 */




import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.google.android.maps.GeoPoint;
import com.tracker.R;
import com.tracker.httpconnector;
import com.tracker.Direction_func.Directuser;
import com.tracker.Login_func.DbAdapterUser;
import com.tracker.Message_func.View_message;
import com.tracker.Mylocation_func.DbAdapterLoc;
import com.tracker.R.layout;








import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class peopleList extends ListActivity {
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
	
	 private Handler _handler;
	 
	 
	 
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
		title.setText("View All People:"); 
        
        myProgressDialog = ProgressDialog.show(this,"Please wait...", "Loading...", true); 
       
       dbHelper = new   DbAdapterLoc(this);
    //////////////////////////////////////////////////////////    
     
	    	    fillData();
	    	    
	    	    dbHelperUser = new DbAdapterUser(this);
	    	    
	    	 
	    	  
	    	
	    	
    
	    	
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
    	
    	servconn = new httpconnector();
    	
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

      
   	    		dbHelper.open();
   	    		 Location distance = dbHelper.getLocCords();
   	    		 dbHelper.close();
  
   	    		  listarray = servconn.getAllUsers();
   	           
   	           adapter adp = new  adapter ( mCtx ,listarray,distance) ;
   	           setListAdapter(adp);
    adp.notifyDataSetChanged();
   
    registerForContextMenu(getListView()); 
	Log.i("testing thread", "checked for distance updates");
 
 

 myProgressDialog.dismiss();
  handler.postDelayed(this, 5000);
	    	   }
	    	};
	    	 handler.postDelayed(runnable,5000);
    }

    // the list click handler method.
    protected void onListItemClick(ListView listView, View view, int position, long id) {
    	// super method
        super.onListItemClick(listView, view, position, id);
			try{
	  // get a JSON object from the JSON Array using the position of the list item click.
			 JSONObject data = listarray.getJSONObject(position);
			// Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
			// Create new intent to start the  GetPeopleLocation which is a Map Activity .
			 Intent i = new Intent(activity, GetPeopleLocation.class);
			 // Placing an Extra object.
			i.putExtra("PHONE_ID",data.getString("PHONE_ID") );
			// start the Activity.
			 startActivity(i);
			 // Finish with this Activity.
			  finish();
      			}catch(Exception e){
			}
   }

    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
MenuInflater inflater = getMenuInflater();
//inflater.inflate(R.menu.menu, menu);
menu.setHeaderTitle("Context Menu");  
menu.add(0, 1, 0, "Navigate");  
menu.add(0, 2, 0, "Stalk"); 
menu.add(0, 3, 0, "Friend request"); 
menu.add(0, 4, 0, "Message"); 
}
    
    
    public boolean onContextItemSelected(MenuItem item) {
    	
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
     // Get the item id selected and pass into the swtich block.
        switch (item.getItemId()) {
        
        case 1:
        	
        	 try {
        		 // get the postion of the list item clicked, and use it to obtain the JSONobject.
				JSONObject data = listarray.getJSONObject(info.position);
				 Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
				 //Initialise a new intent object with he parameter of the context and the Activity class.
				 Intent i = new Intent(this,Directuser.class );
				 // Place the values needed to pass to the Activity that is going to be started.
				 i.putExtra("PHONE_ID",data.getString("PHONE_ID") );
				 // Call a start for the Acvtiivty.
				 startActivity(i);
				 //Finish the current activity to free up resoruces. 
				  finish();
				  
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 // send return true to state the actction is complete. 
            return true;
            
        case 2:
            try{
        	 JSONObject data = listarray.getJSONObject(info.position);
			 Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
			 Intent i = new Intent(activity, GetPeopleLocation.class);
			i.putExtra("PHONE_ID",data.getString("PHONE_ID") );
			 startActivity(i);
			  finish();
            } catch (Exception e){
            	
            }
        	
            return true;
        case 3:
            try{
            	// Get the JSON object from the JSON array using the position of the list item click.
        	 JSONObject data = listarray.getJSONObject(info.position);
			 Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
			// open local user database.
			 dbHelperUser.open();
			 // Query the phoneID stored with in local user table and assign to a string variable.
			 String id =dbHelperUser.getUserPhoneID();
			 // closign the local user database.
			 dbHelperUser.close();
			 Toast.makeText( getApplicationContext(),id , Toast.LENGTH_LONG ).show();
			 // checking if Internet connection is available 
			 if(servconn.IsConnected()==true){
			 
				 // Calling the httpconnectors friend request method.
			boolean result = servconn.FriendReq(id, data.getString("PHONE_ID"));
			// if the friend request is successful then display.
			if(result==true){
				
				 Toast.makeText( getApplicationContext(),"friend reqest sent" , Toast.LENGTH_LONG ).show();
				
			}if(result ==false){
				// if not able to make the friend request because already made then display.
				Toast.makeText( getApplicationContext(),"you have already requested or user no longer exists" , Toast.LENGTH_LONG ).show();
			}
			 }else{
				 // if no Internet connection.
				 Toast.makeText( getApplicationContext(),"Please check internet connection" , Toast.LENGTH_LONG ).show();
				 
			 }
            } catch (Exception e){
            	
            }
        	
            return true;
            
            
            
        case 4:
            try{
        	 JSONObject data = listarray.getJSONObject(info.position);
        	 
        	 if(data.getString("MESS_CHK").equalsIgnoreCase("y")){
        	 
			 Toast.makeText( getApplicationContext(),data.getString("ID_USER") , Toast.LENGTH_LONG ).show();
			 Intent i = new Intent(activity, View_message.class);
			i.putExtra("recipeient_id",data.getString("ID_USER") );
			 startActivity(i);
			  finish();
        	 }
        	 else if (data.getString("MESS_CHK").equalsIgnoreCase("n")){
        		 
        		 Toast.makeText( getApplicationContext(),"sorry you are not allowed to message user" , Toast.LENGTH_LONG ).show();
        		 
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