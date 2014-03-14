package com.tracker.AddFav_func;


/* adapted from:
* SOURCE 2:
	 * from : http://developer.android.com/resources/tutorials/notepad/notepad-ex2.html
	 *  Date 19/11/2011
	 */



import org.json.JSONException;
import org.json.JSONObject;

import com.tracker.R;
import com.tracker.Login_func.DbAdapterUser;
import com.tracker.Message_func.Messages_menu;
import com.tracker.Message_func.View_message;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class Veiw_fav_place extends ListActivity {

	private static DbAdapterFPlaces dbhelper;
	private Cursor cursor;
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
      setContentView(R.layout.fav_places_list);
      dbhelper = new DbAdapterFPlaces(this);
      dbhelper.open();
      fillData();
      dbhelper.close();
	 }
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	        super.onListItemClick(l, v, position, id);
	        
	       Cursor c =  cursor;
	        c.moveToPosition(position);
	        Intent i = new Intent(this, Favlocation.class);
	        i.putExtra("ID", c.getString(
	                c.getColumnIndexOrThrow(DbAdapterFPlaces.ID)));
	        finish();
	        startActivity(i);
	        
	  }
	 private void fillData() {
	        // Get all of the rows from the database and create the item list
		 cursor =  dbhelper.getPlaces();
		 if(cursor!=null){
	        startManagingCursor(cursor);
//String name =cursor.getString(cursor.getColumnIndexOrThrow(DbAdapterFPlaces.PLACENAME));
	        // Create an array to specify the fields we want to display in the list (only TITLE)
	       String[] from = new String[]{DbAdapterFPlaces.PLACENAME};

	        // and an array of the fields we want to bind those fields to (in this case just text1)
	        int[] to = new int[]{R.id.firstName};
//	Toast.makeText( getApplicationContext(),"name", Toast.LENGTH_LONG ).show();
	//	 }
	        // Now create a simple cursor adapter and set it to display
	        SimpleCursorAdapter notes = 
	            new SimpleCursorAdapter(this, R.layout.fav_places_row, cursor, from, to);
	       setListAdapter(notes);
	       registerForContextMenu(getListView()); 
	     
	    }
}
	 /**
      * creating the context menu
      * found code 
      * from: http://www.stealthcopter.com/blog/2010/04/android-context-menu-example-on-long-press-gridview/
      * Date accesed :15/02/2012
      * 
      */
	 // Method to create the context menu
	    public void onCreateContextMenu(ContextMenu menu, View v,
	            ContextMenuInfo menuInfo) {
	super.onCreateContextMenu(menu, v, menuInfo);
	MenuInflater inflater = getMenuInflater();
	//inflater.inflate(R.menu.messages_menu, menu);
	// setting options for the context menu.
	menu.setHeaderTitle("Context Menu");  
	menu.add(0, 1, 0, "Navigate to");  
	
	menu.add(0, 3, 0, "Delete");  
	}
	 
	    // Method to handle the click events on the context menu.
	    public boolean onContextItemSelected(MenuItem item) {
	        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        switch (item.getItemId()) {
	        
	        case 1:
	        	// getting the cursor with all the records
	        	Cursor data =  cursor;
	        	// obtain the record usign the position.
	        	 data.moveToPosition(info.position);
	        	 //create intent to start the navigation actiivty
					Intent intent = new Intent(this, DirectuserToFav.class);
					//placing the id of the fav place into the intent.
					intent.putExtra("ID", data.getString(
							data.getColumnIndexOrThrow(DbAdapterFPlaces.ID)));
					finish();
					// starting the activtiy.
					startActivity(intent);
	        	 return true;
	        case 2:

	        	 Cursor c =  cursor;
				c.moveToPosition(info.position);
				Intent i = new Intent(this, FavPlace_edit.class);
				i.putExtra("ID", c.getString(
				        c.getColumnIndexOrThrow(DbAdapterFPlaces.ID)));
				finish();
				startActivity(i);
	            return true;

	        case 3 :
	        	Cursor cur =  cursor;
	        	dbhelper.open();
	       boolean check = 	dbhelper.deleteFav(cur.getLong( cur.getColumnIndexOrThrow(DbAdapterFPlaces.ID)));
	       dbhelper.close();
	       Intent act = new Intent(this, Veiw_fav_place.class);
	       finish();
	       startActivity(act);
	       if(check){
	    	   Toast.makeText( getApplicationContext(),"deleted" , Toast.LENGTH_LONG ).show();
	       }else{
	    	   Toast.makeText( getApplicationContext(),"not working" , Toast.LENGTH_LONG ).show();
	       }
	       
	       
	            return true;
	       
	            default:
	                return super.onContextItemSelected(item);
	        }
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
