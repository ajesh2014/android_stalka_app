package com.tracker.Find_freinds_func;

import java.util.ArrayList;

import org.json.JSONObject;

import com.tracker.R;
import com.tracker.Message_func.Friends_message;
import com.tracker.Message_func.Messages_menu;
import com.tracker.Message_func.Sent_Messages_menu;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Friends_menu  extends ListActivity {
    private static Context mCtx;
    private static ArrayList <menuitem> menuarray;
    
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_menu);
        mCtx= this;
        menuarray = new ArrayList<menuitem>();
        
        menuitem friends = new menuitem("friends","view your friends",0);
        menuitem Requests = new menuitem("View requests","view your friend requests",1);
       
        menuarray.add(friends);
        menuarray.add(Requests);
       
        
        Log.i("test",friends.getDescr());
        
        Friends_menu_adp adp = new  Friends_menu_adp ( mCtx ,menuarray) ;
           setListAdapter(adp);
        
    }
    
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
        if(menuarray.get(position).getPos()==0){
        	Intent i = new Intent(this, FriendsList.class);
			
    			 startActivity(i);
        }

        
        
        
        if(menuarray.get(position).getPos()==1){
        	Intent i = new Intent(this, Friend_Requests.class);
			
    			 startActivity(i);
        }
     
}
			// Intent i = new Intent(activity, GetFriendsLocation.class);
			
		//	 startActivity(i);
		//	  finish();
		
			
     //  startActivity(i);
			
   }
    

