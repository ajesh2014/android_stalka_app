package com.tracker.Search_func;
/* SOURCE 1:local database interactiivty
 * from : http://developer.android.com/resources/tutorials/notepad/notepad-ex2.html
 *  Date 19/11/2011
 *  
 *  SOURCE 2:
 * from:http://code.google.com/p/custom-list-data-example-android/downloads/detail?name=CustomDataList_CodeProject.zip&can=2&q=
 * Date :Feb 11, 2011
 * author Joseph Fernandez 
 * 
 *   Source 3:
 * from http://developer.android.com/guide/topics/ui/dialogs.html
 *Date  20/01/2012
 * 
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
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.tracker.R;
import com.tracker.httpconnector;
import com.tracker.Direction_func.Directuser;
import com.tracker.Login_func.DbAdapterUser;
import com.tracker.Mylocation_func.DbAdapterLoc;
import com.tracker.Search_func.GetPeopleLocation;

public class UserType_menu  extends ListActivity {
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
	private usertype_search_adapter adp;
	 
	 
    /** Called when the activity is first created. */
	   double  lat=0;
	   double lng =0;
   
	 Handler handler;
	  Runnable runnable;
	    
	    ProgressDialog myProgressDialog = null;
	    
	
	    
	    
	    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertype_menu);
     
        mCtx = this;
      
    	servconn = new httpconnector();
        myProgressDialog = ProgressDialog.show(this,"Please wait...", "Loading...", true); 
       
       dbHelper = new   DbAdapterLoc(this);
    //////////////////////////////////////////////////////////    
   //    if(servconn.IsConnected()){
	    	    fillData();
	    	    
	    	    dbHelperUser = new DbAdapterUser(this);
	    	    
      // }
	    	 
	    	
	    	
    
	    	
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
    	
    
    	
        
   	    handler = new Handler();
   	      
   	    runnable = new Runnable() {
   	    	   @Override
   	    	   public void run() {

      
   	    		

 	    		dbHelperUser.open();
    	    		phoneid = dbHelperUser.getUserPhoneID();
    	    		Log.i("test",phoneid);
    	    		dbHelperUser.close();
   	    		  listarray = servconn.getUserTypes();
   	    		  
   	    		  if(listarray!=null){
   	           
   	            adp = new  usertype_search_adapter ( mCtx ,listarray) ;
   	           setListAdapter(adp);
    adp.notifyDataSetChanged();
   
    registerForContextMenu(getListView()); 
	Log.i("testing thread", "checked for distance updates");
 
   	    		  }

 myProgressDialog.dismiss();
  handler.postDelayed(this, 5000);
	    	   }
	    	};
	    	 handler.postDelayed(runnable,5000);
    }

    
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
        JSONObject data;
		try {
			 // get the postion of the list item clicked, and use it to obtain the JSONobject.
			data = listarray.getJSONObject(position);
		 Toast.makeText( getApplicationContext(),data.getString("Type") , Toast.LENGTH_LONG ).show();
		 //Initialise a new intent object with he parameter of the context and the Activity class.
	 Intent i = new Intent(activity, search_usertype.class);
	 // Place the values needed to pass to the Activity that is going to be started.
	i.putExtra("type",data.getString("Type") );
	 // Call a start for the Acvtiivty.
		startActivity(i);
		 //Finish the current activity to free up resoruces. 
		 finish();
	} catch (JSONException e) {
		e.printStackTrace();}
	}
		

				
			
   

    
    
    
  
        
 protected void onPause() {
	  super.onPause();
	  handler.removeCallbacks(runnable);
	
}
        
   
}
