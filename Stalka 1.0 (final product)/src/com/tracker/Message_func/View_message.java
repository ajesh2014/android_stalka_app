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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.tracker.R;
import com.tracker.httpconnector;
import com.tracker.Direction_func.Directuser;
import com.tracker.Login_func.DbAdapterUser;
import com.tracker.Mylocation_func.DbAdapterLoc;
/*SOURCE 1:
	 * from:http://code.google.com/p/custom-list-data-example-android/downloads/detail?name=CustomDataList_CodeProject.zip&can=2&q=
	 * Date :Feb 11, 2011
	 * author Joseph Fernandez
	 * 
	 *  SOURCE 2:
 * from : http://developer.android.com/resources/tutorials/notepad/notepad-ex2.html
 *  Date 19/11/2011
	 */
public class View_message  extends ListActivity {
	private Context mCtx;
	
	 private  DbAdapterLoc dbHelper;
	 private  DbAdapterUser dbHelperUser;
	 private final Activity activity = this;
	
	private  httpconnector servconn;
    private  JSONArray listarray= null;
	private String phoneid; 
	 private Handler _handler;
	private message_adapter adp;
	 private  String recipeient ;
	 
    /** Called when the activity is first created. */
	   double  lat=0;
	   double lng =0;
	   
	  
   
	 Handler handler;
	  Runnable runnable;
	    
	    ProgressDialog myProgressDialog = null;
	    
	
	    
	    
	    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_view);
     
        mCtx = this;
        
        Bundle extras = getIntent().getExtras();
        recipeient = extras.getString("recipeient_id");
 	   
    	servconn = new httpconnector();
        myProgressDialog = ProgressDialog.show(this,"Please wait...", "Loading...", true); 
       
       dbHelper = new   DbAdapterLoc(this);
       
       
      
       
    //////////////////////////////////////////////////////////    
       if(servconn.IsConnected()){
	    	    fillData(recipeient);
	    	    
	    	    dbHelperUser = new DbAdapterUser(this);
	    	    
       }
	    	 
	    	// instante the send button from the xml interface.
       Button snt = (Button)this.findViewById(R.id.send);
       // assign a On Click listener to the button
    snt.setOnClickListener(new OnClickListener() {
// the on click method
        public void onClick(View v) {
        	// checking for a internat conneciton
        	 if(servconn.IsConnected()){
        		 // instante the text box to obtain the message.
        	EditText MessageText = (EditText) findViewById (R.id.input);
        	String message= MessageText.getText().toString();
        	// open the user database table to obtain the smartphone id.
        	dbHelperUser.open();
        	phoneid = dbHelperUser.getUserPhoneID();
        	Log.i("test",phoneid);
        	// close the database for later use.
        	dbHelperUser.close();
        	// Sending the message to messages table usign the httpconnector's sendmessage method.
        servconn.sendmessage(phoneid,  recipeient, message);
       Log.i("testing service method upd",recipeient);
        Log.i("testing service method upd",message);
        // setting the text box empty when sent.
        MessageText.setText("");
        	 }
        }

    });
	    	
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
 
    
   

    
    
    private void fillData(final String sender) {
    	
   	    handler = new Handler();
   	 ///////////////////////////////////////////////////
	       /*
		       * code adpated to run as handler
		       * from : http://www.mopri.de/?p=1438
		       * date :26/11/2011
		       * 
		       */
   	    runnable = new Runnable() {
   	    	   @Override
   	    	   public void run() {

   	    	      // Open the user database, obtain the smartPhone ID.
   	    	       dbHelperUser.open();
   	    			phoneid = dbHelperUser.getUserPhoneID();
   	    			Log.i("test",phoneid);
   	    			dbHelperUser.close();
   	    			// checking to see if there is internet connectivity. 
   	    		 if(servconn.IsConnected()){
   	    			 // Calling the httpconnector's method to get a conversation 
   	    		  listarray = servconn.getSingleMessage(phoneid, sender);
   	    				
   	    		  // checking of the JSON array is not null so the adapter can 
   	    		  if(listarray!=null){
   	           // Creating the adaapter object,
   	            adp = new  message_adapter ( mCtx ,listarray) ;
   	            //setting the adapter object.
   	           setListAdapter(adp);
    adp.notifyDataSetChanged();

	Log.i("testing thread", "checked for distance updates");
   	    		  }
   	    		  }

 myProgressDialog.dismiss();
  handler.postDelayed(this, 5000);
	    	   }
	    	};
	    	 handler.postDelayed(runnable,5000);
    }

    
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
    
				
			}
   

  
    
    
  

    
  
        
 protected void onPause() {
	  super.onPause();
	  handler.removeCallbacks(runnable);
	
}
        
   
}
