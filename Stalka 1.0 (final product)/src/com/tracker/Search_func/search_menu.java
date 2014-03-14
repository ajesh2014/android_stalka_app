package com.tracker.Search_func;

import java.util.ArrayList;

import org.json.JSONObject;

import com.tracker.R;
import com.tracker.menu_adp;
import com.tracker.menuitem;
import com.tracker.Message_func.Messages_menu;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class search_menu  extends ListActivity {
    private static Context mCtx;
    private static ArrayList <menuitem> menuarray;
    
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_menu);
        mCtx= this;
        menuarray = new ArrayList<menuitem>();
        
        menuitem users = new menuitem("Search for people","Search for users",0);
        menuitem people = new menuitem("Search type of people","Search for types of users",1);
        menuitem all = new menuitem("View all people","view all people",2);
       // menuitem Messages = new menuitem("View messages","view your messages",2);
        menuarray.add(users);
        menuarray.add(people);
        menuarray.add(all);
       
      //  menuarray.add(Messages);
        
       // Log.i("test",friends.getDescr());
        
    menu_adp adp = new  menu_adp ( mCtx ,menuarray) ;
         setListAdapter(adp);
        
    }
    
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
        if(menuarray.get(position).getPos()==0){
        	Intent i = new Intent(this, UserSearch.class);
			
    		startActivity(i);
        }

        
        
        
        if(menuarray.get(position).getPos()==1){
       	Intent i = new Intent(this, UserType_menu.class);
			
    		 startActivity(i);
        }
        if(menuarray.get(position).getPos()==2){
       	Intent i = new Intent(this, peopleList.class);
			
    		 startActivity(i);
        }

}
			// Intent i = new Intent(activity, GetFriendsLocation.class);
			
		//	 startActivity(i);
		//	  finish();
		
			
     //  startActivity(i);
			
   }
    

