package com.tracker.Message_func;

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
import com.tracker.Search_func.GetPeopleLocation;
/*
 * Class constructed  and adpted from a few sources
 * 
 * SOURCE 1:
 * from : http://developer.android.com/resources/tutorials/notepad/notepad-ex2.html
 *  Date 19/11/2011
 *  
 *   Source 3:
 * from http://developer.android.com/guide/topics/ui/dialogs.html
 *Date  20/01/2012
 * 
 * 
 */
public class Messages_menu  extends ListActivity {
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
	private adapter adp;
	 
	 
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
		title.setText("Recived Messages:"); 
    	servconn = new httpconnector();
        myProgressDialog = ProgressDialog.show(this,"Please wait...", "Loading...", true); 
       
       dbHelper = new   DbAdapterLoc(this);
    //////////////////////////////////////////////////////////    
       if(servconn.IsConnected()){
	    	    fillData();
	    	    
	    	    dbHelperUser = new DbAdapterUser(this);
	    	    
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

      
   	    		

 	    		dbHelperUser.open();
    	    		phoneid = dbHelperUser.getUserPhoneID();
    	    		Log.i("test",phoneid);
    	    		dbHelperUser.close();
   	    		  listarray = servconn.getMessages(phoneid);
   	    		  
   	    		  if(listarray!=null){
   	           
   	            adp = new  adapter ( mCtx ,listarray) ;
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
			// get the JSON OBject form the array based on posoiton	of the list item click
			data = listarray.getJSONObject(position);

			// TODO Auto-generated catch block
		 Toast.makeText( getApplicationContext(),data.getString("from_id") , Toast.LENGTH_LONG ).show();
		// intent with the extra identifying choice of messages to read.
	 Intent i = new Intent(activity, View_message.class);
		i.putExtra("recipeient_id",data.getString("from_id") );
		startActivity(i);
		 finish();
		
	} catch (JSONException e) {
		e.printStackTrace();}
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
//inflater.inflate(R.menu.messages_menu, menu);
menu.setHeaderTitle("Context Menu");  
menu.add(0, 1, 0, "Message");  
menu.add(0, 2, 0, "Delete");  
}
    
    
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
        case 1:
        	
        	 try {
				JSONObject data = listarray.getJSONObject(info.position);
				 Toast.makeText( getApplicationContext(),data.getString("from_id") , Toast.LENGTH_LONG ).show();
				 Intent i = new Intent(activity, View_message.class);
			i.putExtra("recipeient_id",data.getString("from_id") );
					 startActivity(i);
					  finish();
					 startActivity(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
            return true;
            
        case 2 :
            try{
        	 JSONObject data = listarray.getJSONObject(info.position);
			 Toast.makeText( getApplicationContext(),data.getString("from_id") , Toast.LENGTH_LONG ).show();
			if(servconn.DeleteMessages(data.getString("from_id"), data.getString("to_id"))){
				 Toast.makeText( getApplicationContext()," working" , Toast.LENGTH_LONG ).show();
				 
				 Intent i = new Intent(activity, Messages_menu.class);
					//i.putExtra("sender_id",data.getString("from_id") );
							 startActivity(i);
							  finish();
							 startActivity(i);
				 
			}else{
				
				Toast.makeText( getApplicationContext(),"not working" , Toast.LENGTH_LONG ).show();
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
