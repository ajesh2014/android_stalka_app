package com.tracker.Message_func;

/*
 * Class constructed  and adpted from a few sources
 * 
 * SOURCE 1:
 * from : http://developer.android.com/resources/tutorials/notepad/notepad-ex2.html
 *  Date 19/11/2011
 *  
 * SOURCE 2:
 * from:http://code.google.com/p/custom-list-data-example-android/downloads/detail?name=CustomDataList_CodeProject.zip&can=2&q=
 * Date :Feb 11, 2011
 * author Joseph Fernandez 
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
import com.tracker.Find_freinds_func.adapter;
import com.tracker.Login_func.DbAdapterUser;
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

public class Friends_message extends ListActivity {
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
		title.setText("Message Friends:"); 
        
        dbHelperUser = new DbAdapterUser(this);
        
        dbHelperUser.open();
        String PhoneID =  dbHelperUser.getUserPhoneID();
		 dbHelperUser.close();
        
        
      
        
        myProgressDialog = ProgressDialog.show(Friends_message.this,"Please wait...", "Loading...", true); 
       
       dbHelper = new   DbAdapterLoc(this);
    //////////////////////////////////////////////////////////    
     if(servconn.IsConnected()==true){
	    	    fillData(PhoneID);
     }
     else{	 
    	 Toast.makeText( getApplicationContext(),"not connected", Toast.LENGTH_LONG ).show();
			// intent with the extra identifying choice.
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
        
   	    handler = new Handler();
   	      
   	    runnable = new Runnable() {
   	    	   @Override
   	    	   public void run() {

      
   	    		dbHelper.open();
   	    		 Location distance = dbHelper.getLocCords();
   	    		 dbHelper.close();
  
   	    		  listarray = servconn.getFriends(PhoneID);
   	           if(listarray!=null){
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
				// get the JSON OBject form the array based on posoiton	of the list item click
			 JSONObject data = listarray.getJSONObject(position);
			// Toast.makeText( getApplicationContext(),data.getString("ID_FriendLink2") , Toast.LENGTH_LONG ).show();
			// intent with the extra identifying choice.
			 Intent i = new Intent(activity,View_message.class);
			i.putExtra("recipeient_id",data.getString("ID_FriendLink2") );
			// Start the Acitivty
			 startActivity(i);
			 // finish the current Activity.
			  finish();
	
			}catch(Exception e){
				
			}
   }

    

    
  
        
 protected void onPause() {
	  super.onPause();
	  handler.removeCallbacks(runnable);
	
}
        
   
}