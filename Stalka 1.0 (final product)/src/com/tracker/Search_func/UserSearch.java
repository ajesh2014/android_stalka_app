package com.tracker.Search_func;

/*SOURCE 1:
	 * from : http://developer.android.com/resources/tutorials/notepad/notepad-ex2.html
	 *  Date 19/11/2011
	 *  
	 *  Source 2:
	 *  From : http://developer.android.com/resources/tutorials/views/hello-formstuff.html
	 *  Date:10/03/2012
	 *  
	 *   Source 3:
 * from http://developer.android.com/guide/topics/ui/dialogs.html
 *Date  20/01/2012
 * 
	*/ 
import java.util.Collections;

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
import com.tracker.Message_func.View_message;
import com.tracker.Mylocation_func.DbAdapterLoc;

public class UserSearch  extends ListActivity {
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
	 private  String sender ;
	 private Intent i;
    /** Called when the activity is first created. */
	   double  lat=0;
	   double lng =0;
	   
	  private int searchcount =0;
   
	 Handler handler;
	  Runnable runnable;
	    
	    ProgressDialog myProgressDialog = null;
	    
	
	    
	    
	    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersearch_view);
     
        mCtx = this;
        
     i = new Intent(this, UserSearch.class);
 	   
    	servconn = new httpconnector();
       
       
       dbHelper = new   DbAdapterLoc(this);
       
  
      
       
    //////////////////////////////////////////////////////////    
       
	    	    dbHelperUser = new DbAdapterUser(this);
	    	    
       
	    	 
	    // Button variable assigned the button from the xml interface.
       Button snt = (Button)this.findViewById(R.id.search);
       // Setting the OnClick listener to handle the button click.
    snt.setOnClickListener(new OnClickListener() {
// on Click method.
        public void onClick(View v) {
        	if(searchcount>0){
        		adp.notifyDataSetChanged();
        	
        	}
        	// instantating the Edit text to obtain the typed criteria.
        	EditText userText = (EditText) findViewById (R.id.input);
        	String user= userText.getText().toString();
        	
       	if(servconn.IsConnected()){
       		// the httpconnector's method with the passed criteria.
  listarray =  servconn.searchUser(user);
   	    				
   	    		  
   	    		  if(listarray!=null){
   	    			dbHelper.open();
      	    		 Location distance = dbHelper.getLocCords();
      	    		 dbHelper.close();
     
   	           
   	            adp = new  adapter ( mCtx ,listarray,distance) ;
   	           setListAdapter(adp);
 //   adp.notifyDataSetChanged();

    registerForContextMenu(getListView()); 
	Log.i("testing thread", "checked for distance updates");
 
   	    		  }	   
   	    		searchcount++;
				
      }
      
        userText.setText("");
        
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
 
    
   

    
  

    
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
        try{
			 JSONObject data = listarray.getJSONObject(position);
			 Toast.makeText( getApplicationContext(),data.getString("PHONE_ID") , Toast.LENGTH_LONG ).show();
		 Intent i = new Intent(activity, GetPeopleLocation.class);
		i.putExtra("PHONE_ID",data.getString("PHONE_ID") );
		 startActivity(i);
		  finish();
		
	
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
	  
	
}
        
   
}
