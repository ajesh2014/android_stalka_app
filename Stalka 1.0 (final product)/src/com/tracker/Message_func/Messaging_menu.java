package com.tracker.Message_func;

import java.util.ArrayList;

import org.json.JSONObject;

import com.tracker.R;
import com.tracker.menu_adp;
import com.tracker.menuitem;
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

public class Messaging_menu  extends ListActivity {
    private static Context mCtx;
    private static ArrayList <menuitem> menuarray;
    
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_menu);
        mCtx= this;
        menuarray = new ArrayList<menuitem>();
        
       
        menuitem Messages = new menuitem("View messages","view your messages",1);
        menuitem MessagestoFri = new menuitem("Message Friends"," send messages to friends",2);
        menuitem SentMessages = new menuitem("View sent messsages"," sent messages to friends",3);
     
        menuarray.add(Messages);
        menuarray.add(MessagestoFri);
        menuarray.add(SentMessages);
        
        
        
       menu_adp adp = new menu_adp ( mCtx ,menuarray) ;
           setListAdapter(adp);
        
    }
    
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
    
        if(menuarray.get(position).getPos()==1){
        	Intent i = new Intent(this, Messages_menu.class);
			
    			 startActivity(i);
        }
        if(menuarray.get(position).getPos()==2){
        	Intent i = new Intent(this, Friends_message.class);
			
    			 startActivity(i);
        }
        if(menuarray.get(position).getPos()==3){
        	Intent i = new Intent(this, Sent_Messages_menu.class);
			
    			 startActivity(i);
        }
}
			// Intent i = new Intent(activity, GetFriendsLocation.class);
			
		//	 startActivity(i);
		//	  finish();
		
			
     //  startActivity(i);
			
   }
    

