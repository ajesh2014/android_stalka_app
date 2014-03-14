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
 * 
 * *  SOURCE 3:
*  /// Code adapted from : http://androiddevelopement.blogspot.com/2011/09/connecting-to-remote-mysql-database.html
// Author:  Krishna //// Date accessed :12/01/2012
 * 
 * SOURCE 4:
 * from:http://code.google.com/p/custom-list-data-example-android/downloads/detail?name=CustomDataList_CodeProject.zip&can=2&q=
 * Date :Feb 11, 2011
 * author Joseph Fernandez 
 * 
 * 
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

public class FriendsList extends ListActivity {
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
		title.setText("Friends:"); 
        dbHelperUser = new DbAdapterUser(this);
        
        dbHelperUser.open();
        String PhoneID =  dbHelperUser.getUserPhoneID();
		 dbHelperUser.close();
        
        
        
        myProgressDialog = ProgressDialog.show(FriendsList.this,"Please wait...", "Loading...", true); 
        servconn = new httpconnector();
       dbHelper = new   DbAdapterLoc(this);
    //////////////////////////////////////////////////////////    
      if( servconn.IsConnected()==true){
	    	    fillData(PhoneID);
	    	    
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
 
    
   

    
    
    private void fillData(final String PhoneID) {
    	
    	servconn = new httpconnector();
    	 ///////////////////////////////////////////////////
	       /*
		       * code adpated to run as handler
		       * from : http://www.mopri.de/?p=1438
		       * date :26/11/2011
		       * 
		       */
        /////////////////////////////////////////////////////
   	    handler = new Handler();
   	      
   	    runnable = new Runnable() {
   	    	   @Override
   	    	   public void run() {

   	    		   //Open the location database and obtain location from location table for distance.
   	    		dbHelper.open();
   	    	// get distance.
   	    		 Location distance = dbHelper.getLocCords();
   	    		 dbHelper.close();
   	    	  /** Get all friends using the httpconnector's method, use the smartphoneid
   	    	    to identify which user.**/
   	    		  listarray = servconn.getFriends(PhoneID);
   	    		  if(listarray!=null){
   	    		  // check if the returned JSON array is not null before displaying data.
   	           adapter adp = new  adapter ( mCtx ,listarray,distance) ;
   	           setListAdapter(adp);
    adp.notifyDataSetChanged();
   	    		  }
    registerForContextMenu(getListView()); 
	Log.i("testing thread", "checked for distance updates");
 

 myProgressDialog.dismiss();
  handler.postDelayed(this, 5000);
	    	   }
	    	};
	    	 handler.postDelayed(runnable,5000);
    }

    
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
        
			try{
			 JSONObject data = listarray.getJSONObject(position);
			 Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
			 Intent i = new Intent(activity, GetFriendsLocation.class);
			i.putExtra("PHONE_ID",data.getString("PHONE_ID") );
			 startActivity(i);
			  finish();
		
			
       startActivity(i);
			}catch(Exception e){
				
			}
   }
    
    /**
     * creating the context menu
     * found code 
     * from: http://www.stealthcopter.com/blog/2010/04/android-context-menu-example-on-long-press-gridview/
     * Date accesed :15/02/2012
     * 
     */

    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
MenuInflater inflater = getMenuInflater();
//inflater.inflate(R.menu.menu, menu);
menu.setHeaderTitle("Context Menu");  
menu.add(0, 1, 0, "Stalk");  
menu.add(0, 2, 0, "Navigate");  
menu.add(0, 3, 0, "Message");  
menu.add(0, 4, 0, "Delete");  
}
    
    
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
        case 2:
        	
        	 try {
				JSONObject data = listarray.getJSONObject(info.position);
				 Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
				 Intent i = new Intent(this,Directuser.class );
				 i.putExtra("PHONE_ID",data.getString("PHONE_ID") );
				 startActivity(i);
				  finish();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
            return true;
            
        case 1:
            try{
        	 JSONObject data = listarray.getJSONObject(info.position);
			 Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
			 Intent i = new Intent(activity, GetFriendsLocation.class);
			i.putExtra("PHONE_ID",data.getString("PHONE_ID") );
			 startActivity(i);
			  finish();
            } catch (Exception e){
            	
            }
        	
            return true;
        case R.id.friendreq:
            try{
        	 JSONObject data = listarray.getJSONObject(info.position);
			// Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
			 dbHelperUser.open();
			 String id =dbHelperUser.getUserPhoneID();
			 dbHelperUser.close();
			 Toast.makeText( getApplicationContext(),id , Toast.LENGTH_LONG ).show();
			 
			 if(servconn.IsConnected()==true){
			 
			boolean result = servconn.FriendReq(id, data.getString("PHONE_ID"));
			if(result==true){
				
				 Toast.makeText( getApplicationContext(),"friend reqest sent" , Toast.LENGTH_LONG ).show();
				
			}if(result ==false){
				Toast.makeText( getApplicationContext(),"you have already requested or user no longer exists" , Toast.LENGTH_LONG ).show();
			}
			 }else{
				 
				 Toast.makeText( getApplicationContext(),"Please check internet connection" , Toast.LENGTH_LONG ).show();
				 
			 }
            } catch (Exception e){
            	
            }
        	
            return true;
            
        case 3:
            try{
        	 JSONObject data = listarray.getJSONObject(info.position);
			 Toast.makeText( getApplicationContext(),data.getString("ID_FriendLink2") , Toast.LENGTH_LONG ).show();
			 Intent i = new Intent(activity, View_message.class);
			i.putExtra("recipeient_id",data.getString("ID_FriendLink2") );
			 startActivity(i);
			  finish();
            } catch (Exception e){
            	
            }
        	
            return true;
        case 4:
          
        	  try{
             	 JSONObject data = listarray.getJSONObject(info.position);
     			// Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
     			 dbHelperUser.open();
     			 String id =dbHelperUser.getUserPhoneID();
     			 dbHelperUser.close();
     			 Toast.makeText( getApplicationContext(),id , Toast.LENGTH_LONG ).show();
     			 
     			 if(servconn.IsConnected()==true){
     			 
     			boolean result = servconn.delFriend(id, data.getString("PHONE_ID"));
     			if(result==true){
     				
     				 Toast.makeText( getApplicationContext(),"friend Deleted", Toast.LENGTH_LONG ).show();
     				 Intent i = new Intent(this,FriendsList.class );
     				startActivity(i);
     				  finish();
     			}if(result ==false){
     				Toast.makeText( getApplicationContext(),"you have already deleted friend or user no longer exists" , Toast.LENGTH_LONG ).show();
     			}
     			 }else{
     				 
     				 Toast.makeText( getApplicationContext(),"Please check internet connection" , Toast.LENGTH_LONG ).show();
     				 
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