package com.tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.google.android.maps.GeoPoint;
import com.tracker.Direction_func.Placemark;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/// Code adapted from : http://androiddevelopement.blogspot.com/2011/09/connecting-to-remote-mysql-database.html
// Author:  Krishna //// Date accessed :12/01/2012


public class httpconnector {
	
	private String url_allitems = "http://10.0.2.2/android/index.php";
	
	private String test = "http://10.0.2.2/android/insert.php";
	private  HttpClient httpclient;
	private  HttpPost httppost;
	private HttpResponse response;
	private HttpEntity entity ;
	private   ArrayList<NameValuePair> methodIns;
	
	
	
	public httpconnector(){
		
	}
	
	
	public void updateLocation(double lat,double lng, String phone) {
	   	 InputStream is = null;
	   	   String result = "";
	   	   JSONArray jArray = null;
	   	   boolean returnval = true;
	   	   
	   	   
	   	   // converting the location to string (latitude and longitude).
	   	   String Strlat = Double.toString(lat);
	   	   String Strlng = Double.toString(lng);
	   	   
	   	    //the data to send and the indicator of method
	   	   methodIns = new ArrayList<NameValuePair>();
	   	   methodIns.add(new BasicNameValuePair("method","2"));
	   	  methodIns.add(new BasicNameValuePair("lat",Strlat));
		  methodIns.add(new BasicNameValuePair("lng",Strlng));
		  methodIns.add(new BasicNameValuePair("phone",phone));
		  
	   	    //http post client and paramer intlization
	   	    try{
	   	            httpclient = new DefaultHttpClient();
	   	             httppost = new HttpPost(url_allitems);
	   	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
	   	            // connecting to the remote server and obtaining http response 
	   	            response = httpclient.execute(httppost);
	   	         entity = response.getEntity();
	   	            is = entity.getContent();

	   	    }catch(Exception e){
	   	            Log.e("log_tag", "Error in http connection "+e.toString());
	   	    }
	   	    
	   	 try{
	   		 //Buffer reader to read the returning result 
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                    sb.append(line + "\n");
	            }
	            is.close();
	            // converting the result for debug.
	            result=sb.toString();
	            
	            Log.i("testing service method upd", result);
	           
	            
	    }catch(Exception e){
	            Log.e("log_tag", "Error converting result "+e.toString());
	    }

	
	   }
	
	
	
	
	
	public JSONArray  getAllUsers() {
   	 InputStream is = null;
   	   String result = "";
   	   JSONArray jArrayout = null;
   	   
   	    //the data to send and the indicator of method
   	   methodIns = new ArrayList<NameValuePair>();
   	   methodIns.add(new BasicNameValuePair("method","1"));


   	    //http post client and paramer intlization
   	    try{
   	            httpclient = new DefaultHttpClient();
   	             httppost = new HttpPost(url_allitems);
   	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
   	            response = httpclient.execute(httppost);
   	         entity = response.getEntity();
   	            is = entity.getContent();


   	    }catch(Exception e){
   	            Log.e("log_tag", "Error in http connection "+e.toString());
   	    }


   	    //convert response to string
   	  
   	    
   	 try{
         BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
         StringBuilder sb = new StringBuilder();
         String line = null;
         while ((line = reader.readLine()) != null) {
                 sb.append(line + "\n");
         }
         is.close();
         result=sb.toString();
 }catch(Exception e){
         Log.e("log_tag", "Error converting result "+e.toString());
 }
 //parse json data
 try{
   	   
   	    
   	             jArrayout = new JSONArray(result);
   	        
   	    }catch(JSONException e){
   	            Log.e("log_tag", "Error parsing data "+e.toString());
   	    }
   	    return jArrayout; 
   }
   


public int Newuser(String phone , String UserName, String email, String firstname, String lastname, String Password, String Type) {
  	 InputStream is = null;
  	   String result = "";
  	   JSONArray jArray = null;
  	 
  	   boolean val = false;
  	   String msg_check ="";
  	   if(Type.equalsIgnoreCase("student")){
  		   msg_check = "n";
  	   }
  	  if(Type.equalsIgnoreCase("nurse")){
 		   msg_check = "y";
 	   }
  	 int i =0;
  	   
  	 
  	 if(UserName.equalsIgnoreCase("")||email.equalsIgnoreCase("")||firstname.equalsIgnoreCase("")||lastname.equalsIgnoreCase("")
  			||Password.equalsIgnoreCase("")){
  		 return 0;
  	 }
  	 
  	 
  	 
  	    //the data to send and the indicator of method
  	   methodIns = new ArrayList<NameValuePair>();
  	   methodIns.add(new BasicNameValuePair("method","3"));
  	 methodIns.add(new BasicNameValuePair("username",UserName));
	  methodIns.add(new BasicNameValuePair("phone",phone));
	 methodIns.add(new BasicNameValuePair("email",email));
	 methodIns.add(new BasicNameValuePair("firstname",firstname));
	 methodIns.add(new BasicNameValuePair("lastname",lastname));
	 methodIns.add(new BasicNameValuePair("password",Password));
	 methodIns.add(new BasicNameValuePair("type",Type));
	 methodIns.add(new BasicNameValuePair("msg", msg_check));
  	    //http post client and paramer intlization
  	    try{
  	            httpclient = new DefaultHttpClient();
  	             httppost = new HttpPost(url_allitems);
  	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
  	            response = httpclient.execute(httppost);
  	         entity = response.getEntity();
  	            is = entity.getContent();


  	    }catch(Exception e){
  	            Log.e("log_tag", "Error in http connection "+e.toString());
  	            
  	    }
  	    
  	    
  	 try{
           BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
           StringBuilder sb = new StringBuilder();
           String line = null;
           line = reader.readLine();
                 
         
           is.close();
         
           
           Log.i("testing service method upd", result);
           
       
     String check = line.toString();
      i = Integer.parseInt(check);
         
         
           
   }catch(Exception e){
           Log.e("log_tag", "Error converting result "+e.toString());
   }
  	    
  	 


  	    //convert response to string
  	  
  	
  		  	    //convert response to string
  		  	  
  		  

	return i;
  	
  	
  	
  	
  }


public GeoPoint  getUserLoc(String phone) {
  	 InputStream is = null;
  	   String result = "";
  GeoPoint point	 = null;
  Location loc = new Location("userloc");
  JSONArray jArray = null;
  
  
  	    //the data to send and the indicator of method
  	   methodIns = new ArrayList<NameValuePair>();
  	   methodIns.add(new BasicNameValuePair("method","4"));
  	 methodIns.add(new BasicNameValuePair("phone",phone));

  	    //http post client and paramer intlization
  	    try{
  	            httpclient = new DefaultHttpClient();
  	             httppost = new HttpPost(url_allitems);
  	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
  	            response = httpclient.execute(httppost);
  	         entity = response.getEntity();
  	            is = entity.getContent();


  	    }catch(Exception e){
  	            Log.e("log_tag", "Error in http connection "+e.toString());
  	    }


  	    //convert response to string
  	    try{
  	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
  	            StringBuilder sb = new StringBuilder();
  	            String line = null;
  	            while ((line = reader.readLine()) != null) {
  	                    sb.append(line + "\n");
  	            }
  	            is.close();
  	            result=sb.toString();
  	    }catch(Exception e){
  	            Log.e("log_tag", "Error converting result "+e.toString());
  	    }
  	    //parse json data
  	    try{
  	             jArray = new JSONArray(result);
  	           JSONObject data =  jArray.getJSONObject(0);
  	       loc.setLatitude(data.getDouble("LAT"));
  	     loc.setLongitude(data.getDouble("LNG"));
  	     
  	   int lat = (int) (loc.getLatitude() * 1E6);
		int lng = (int) (loc.getLongitude() * 1E6);
		 point = new GeoPoint(lat, lng);
  	           
  	    }catch(JSONException e){
  	            Log.e("log_tag", "Error parsing data "+e.toString());
  	    }
  	    return point; 
  }



// method sourced from :http://android-pro.blogspot.com/2010/10/how-to-check-if-phone-is-connected-to.html
// Author: Mina Samy //// Date accessed :24/02/2012

public Boolean IsConnected(){
	
	
	
	try {
		
		InetAddress.getByName("google.com").isReachable(3);
		
		return true;
		
		} catch (UnknownHostException e){
		
		return false;
		
		} catch (IOException e){
		
		return false;
		
		}

}

/*Adapted from 
* From:http://stackoverflow.com/questions/3109158/how-to-draw-a-path-on-a-map-using-kml-file
* Date 19:/03/2011
* */
public static ArrayList<com.tracker.Direction_func.Placemark> getNavigationInstructions(String url) {
	 ArrayList<com.tracker.Direction_func.Placemark> data = null;
   
 
    try
        {           
        final URL aUrl = new URL(url);
        final URLConnection conn = aUrl.openConnection();
    
        conn.connect();

        // Using a SAXParserFactory get a SAXParser Instance.
        SAXParserFactory spf = SAXParserFactory.newInstance(); 
        SAXParser sp = spf.newSAXParser(); 

        //Get the XMLReader of the SAXParser.
        XMLReader xr = sp.getXMLReader();

        // Initialise the custom ContentHandler 
        com.tracker.Direction_func.NavigationSaxHandler navSax2Handler = new com.tracker.Direction_func.NavigationSaxHandler(); 
       // Set the custom content handler
        xr.setContentHandler(navSax2Handler); 

        // Parse the KML file from URL. 
        xr.parse(new InputSource(aUrl.openStream()));

        //Assign the data collected from the custom content provider to an array list of  type placemark object
      data = navSax2Handler.getParsedData() ;

    

    } catch (Exception e) {
      
    }   
   
    // Debugging
    if(data!=null){
       
    	Log.i("sax parser: ", "works");
    	
    } if(data==null){
    	Log.i("sax parser: ", "datais ");
    }
        
    //returning the array list of placemark objects.
    return data;
}


/* Source 1
* from http://blog.synyx.de/2010/06/routing-driving-directions-on-android-part-1-get-the-route/
* Date :14/02/2012
* author :Tobias Knell
*/
public String getUrl(GeoPoint src, GeoPoint dest){
 //Construct the url with a String builder obeject.
StringBuilder urlString = new StringBuilder();
// url which sets the directions by foot and language in English
urlString.append("http://maps.google.com/maps?f=d&hl=en");
// setting start address
urlString.append("&saddr=");
urlString.append(Double.toString((double) src.getLatitudeE6() / 1.0E6));
urlString.append(",");
urlString.append(Double.toString((double) src.getLongitudeE6() / 1.0E6));
// setting destination address
urlString.append("&daddr=");// to
urlString.append(Double.toString((double) dest.getLatitudeE6() / 1.0E6));
urlString.append(",");
urlString.append(Double.toString((double) dest.getLongitudeE6() / 1.0E6));
// setting the output format of the requested data.
urlString.append("&ie=UTF8&0&om=0&output=kml");
// return the a string 
return urlString.toString();
}


public boolean FriendReq(String Req , String User) {
 	 InputStream is = null;
 	   String result = "";
 	   JSONArray jArray = null;
 	 
 	   boolean val = false;

 	 int i =0;
 	   
 	    //the data to send and the indicator of method
 	   methodIns = new ArrayList<NameValuePair>();
 	   methodIns.add(new BasicNameValuePair("method","5"));
 	 methodIns.add(new BasicNameValuePair("req",Req));
	  methodIns.add(new BasicNameValuePair("user",User));
	
 	    //http post client and parameter intlisation
 	    try{
 	            httpclient = new DefaultHttpClient();
 	             httppost = new HttpPost(url_allitems);
 	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
 	            response = httpclient.execute(httppost);
 	         entity = response.getEntity();
 	            is = entity.getContent();

 	    }catch(Exception e){
 	            Log.e("log_tag", "Error in http connection "+e.toString());
     
 	    }
 	     	    
 	 try{
 		 //Using string buffer to read the response  sent back.
          BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
          StringBuilder sb = new StringBuilder();
          String line = null;
          line = reader.readLine();
                
          // Converting resposne to a String.
          String check = line.toString();
          Log.i("testing service method upd", check);
          // converting the string to an interger.
           i = Integer.parseInt(check);
          is.close();
        
          
  }catch(Exception e){
          Log.e("log_tag", "Error converting result "+e.toString());
  }
 	    // converting the integer to a boolean expression.
if(i==0){
	val =false;
	
}if(i==1){
	val = true;
}
// Returning the expression.
	return val;
 	
 	
 }



public JSONArray  getFriendReq(String phoneid) {
  	 InputStream is = null;
  	   String result = "";
  	   JSONArray jArrayout = null;
  	   
  	   
  	   methodIns = new ArrayList<NameValuePair>();
   methodIns.add(new BasicNameValuePair("method","6"));
  	  methodIns.add(new BasicNameValuePair("phone_id",phoneid));
  	 try{
            httpclient = new DefaultHttpClient();
             httppost = new HttpPost(url_allitems);
            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
            response = httpclient.execute(httppost);
         entity = response.getEntity();
            is = entity.getContent();


    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
    }

	    
	 try{
      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
      StringBuilder sb = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
              sb.append(line + "\n");
      }
      is.close();
      result=sb.toString();
}catch(Exception e){
      Log.e("log_tag", "Error converting result "+e.toString());
}
Log.i("test",result);
         try {
			jArrayout = new JSONArray(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return jArrayout; 
}


  
public JSONArray  getFriends(String PhoneID) {
  	 InputStream is = null;
  	   String result = "";
  	   JSONArray jArrayout = null;
  	   
  	    //the data to send and the indicator of method
  	   methodIns = new ArrayList<NameValuePair>();
  	   methodIns.add(new BasicNameValuePair("method","7"));
  	 methodIns.add(new BasicNameValuePair("phoneID",PhoneID));

  	    //http post client and paramer intlization
  	    try{
  	            httpclient = new DefaultHttpClient();
  	             httppost = new HttpPost(url_allitems);
  	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
  	            response = httpclient.execute(httppost);
  	         entity = response.getEntity();
  	            is = entity.getContent();


  	    }catch(Exception e){
  	            Log.e("log_tag", "Error in http connection "+e.toString());
  	    }


  	    //convert response to string
  	  
  	    
  	 try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
        }
        is.close();
        result=sb.toString();
}catch(Exception e){
        Log.e("log_tag", "Error converting result "+e.toString());
}
//parse json data
try{
  	   
  	    
  	             jArrayout = new JSONArray(result);
  	        
  	    }catch(JSONException e){
  	            Log.e("log_tag", "Error parsing data "+e.toString());
  	    }
  	    return jArrayout; 
  }


public boolean AcceptFriendReq( String accpt,String req ) {
	 InputStream is = null;
	   String result = "";
	   JSONArray jArray = null;
	 
	   boolean val = false;
	   
	   
	 int i =0;
	   
	    //the data to send and the indicator of method
	   methodIns = new ArrayList<NameValuePair>();
	   methodIns.add(new BasicNameValuePair("method","8"));
	   methodIns.add(new BasicNameValuePair("acc",accpt));
	 methodIns.add(new BasicNameValuePair("req",req));
	  
	
	    //http post client and paramer intlization
	    try{
	            httpclient = new DefaultHttpClient();
	             httppost = new HttpPost(url_allitems);
	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
	            response = httpclient.execute(httppost);
	         entity = response.getEntity();
	            is = entity.getContent();


	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	            
	    }
	    
	    
	 try{
         BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
         StringBuilder sb = new StringBuilder();
     
         String  line  = null;
         while ((line = reader.readLine()) != null) {
             sb.append(line);
     }
         
         
        
               
         String check = sb.toString();
         Log.i("testing service method upd", check);
          i = Integer.parseInt(check);
         is.close();
       
         
       
         
 }catch(Exception e){
         Log.e("log_tag", "Error converting result "+e.toString());
 }
	    
	 


	    //convert response to string
	  
	
		  	    //convert response to string
		  
		  
if(i==0){
	val =false;
	
}if(i==1){
	val = true;
}
	return val;
	
	
	
	
}





public boolean DeclineFriendReq( String accpt,String req ) {
	 InputStream is = null;
	   String result = "";
	   JSONArray jArray = null;
	 
	   boolean val = false;
	   
	   
	 int i =0;
	   
	    //the data to send and the indicator of method
	   methodIns = new ArrayList<NameValuePair>();
	   methodIns.add(new BasicNameValuePair("method","9"));
	   methodIns.add(new BasicNameValuePair("acc",accpt));
	 methodIns.add(new BasicNameValuePair("req",req));
	  
	
	    //http post client and paramer intlization
	    try{
	            httpclient = new DefaultHttpClient();
	             httppost = new HttpPost(url_allitems);
	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
	            response = httpclient.execute(httppost);
	         entity = response.getEntity();
	            is = entity.getContent();


	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	            
	    }
	    
	    
	 try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
        StringBuilder sb = new StringBuilder();
    
        String  line  = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
    }
        
        
       
              
        String check = sb.toString();
        Log.i("testing service method upd", check);
         i = Integer.parseInt(check);
        is.close();
      
        
      
        
}catch(Exception e){
        Log.e("log_tag", "Error converting result "+e.toString());
}
	    
	 


	    //convert response to string
	  
	
		  	    //convert response to string
		  
		  
if(i==0){
	val =false;
	
}if(i==1){
	val = true;
}
	return val;
	
	
	
	
}


public boolean delFriend( String phoneID,String friendID ) {
	 InputStream is = null;
	   String result = "";
	   JSONArray jArray = null;
	 
	   boolean val = false;
	   
	   
	 int i =0;
	   
	    //the data to send and the indicator of method
	   methodIns = new ArrayList<NameValuePair>();
	   methodIns.add(new BasicNameValuePair("method","18"));
	   methodIns.add(new BasicNameValuePair("phone",phoneID));
	 methodIns.add(new BasicNameValuePair("friendID",friendID));
	  
	
	    //http post client and paramer intlization
	    try{
	            httpclient = new DefaultHttpClient();
	             httppost = new HttpPost(url_allitems);
	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
	            response = httpclient.execute(httppost);
	         entity = response.getEntity();
	            is = entity.getContent();


	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	            
	    }
	    
	    
	 try{
       BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
       StringBuilder sb = new StringBuilder();
   
       String  line  = null;
       while ((line = reader.readLine()) != null) {
           sb.append(line);
   }
       
       
      
             
       String check = sb.toString();
       Log.i("testing service method upd", check);
        i = Integer.parseInt(check);
       is.close();
     
       
     
       
}catch(Exception e){
       Log.e("log_tag", "Error converting result "+e.toString());
}
	    
	 


	    //convert response to string
	  
	
		  	    //convert response to string
		  
		  
if(i==0){
	val =false;
	
}if(i==1){
	val = true;
}
	return val;
	
	
	
	
}










public JSONArray  getMessages(String PhoneID) {
  	 InputStream is = null;
  	   String result = "";
  	   JSONArray jArrayout = null;
  	   
  	    //the data to send and the indicator of method
  	   methodIns = new ArrayList<NameValuePair>();
  	   methodIns.add(new BasicNameValuePair("method","10"));
  	 methodIns.add(new BasicNameValuePair("phoneID",PhoneID));

  	    //http post client and paramer intlization
  	    try{
  	            httpclient = new DefaultHttpClient();
  	             httppost = new HttpPost(url_allitems);
  	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
  	            response = httpclient.execute(httppost);
  	         entity = response.getEntity();
  	            is = entity.getContent();


  	    }catch(Exception e){
  	            Log.e("log_tag", "Error in http connection "+e.toString());
  	    }


  	    //convert response to string
  	  
  	    
  	 try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
        }
        is.close();
        result=sb.toString();
}catch(Exception e){
        Log.e("log_tag", "Error converting result "+e.toString());
}
//parse json data
try{
  	   
	Log.i("test",result);
	
	
	
	
	
  	             jArrayout = new JSONArray(result);
  	             
  	           JSONObject data = jArrayout.getJSONObject(0);
  	           
  	           if(data.getString("no_mess").equals("0")){
  	        	 jArrayout =null;
  	           }
  	           
  	        
  	    }catch(JSONException e){
  	            Log.e("log_tag", "Error parsing data "+e.toString());
  	    }
  	    return jArrayout; 
  }


public JSONArray  getSingleMessage(String PhoneID,String senderID) {
 	 InputStream is = null;
 	   String result = "";
 	   JSONArray jArrayout = null;
 	   
 	    //the data to send and the indicator of method
 	   methodIns = new ArrayList<NameValuePair>();
 	   methodIns.add(new BasicNameValuePair("method","11"));
 	 methodIns.add(new BasicNameValuePair("phoneID",PhoneID));
 	 methodIns.add(new BasicNameValuePair("senderID",senderID));

 	    //http post client and paramer intlization
 	    try{
 	            httpclient = new DefaultHttpClient();
 	             httppost = new HttpPost(url_allitems);
 	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
 	            response = httpclient.execute(httppost);
 	         entity = response.getEntity();
 	            is = entity.getContent();


 	    }catch(Exception e){
 	            Log.e("log_tag", "Error in http connection "+e.toString());
 	    }


 	    //convert response to string
 	  
 	    
 	 try{
       BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
       StringBuilder sb = new StringBuilder();
       String line = null;
       while ((line = reader.readLine()) != null) {
               sb.append(line + "\n");
       }
       is.close();
       result=sb.toString();
}catch(Exception e){
       Log.e("log_tag", "Error converting result "+e.toString());
}
//parse json data
try{
 	   
	Log.i("test",result);
 	             jArrayout = new JSONArray(result);
 	        
 	    }catch(JSONException e){
 	            Log.e("log_tag", "Error parsing data "+e.toString());
 	    }
 	    return jArrayout; 
 }




public void sendmessage( String phoneid ,String to, String message) {
	 InputStream is = null;
	   String result = "";
	   JSONArray jArray = null;
	 
	   boolean val = false;
	   
	   
	 int i =0;
	   
	    //the data to send and the indicator of method
	   methodIns = new ArrayList<NameValuePair>();
	   methodIns.add(new BasicNameValuePair("method","12"));
	   methodIns.add(new BasicNameValuePair("phoneid",phoneid));
	 methodIns.add(new BasicNameValuePair("to", to));
	 methodIns.add(new BasicNameValuePair("message", message));
	
	    //http post client and paramer intlization
	    try{
	            httpclient = new DefaultHttpClient();
	             httppost = new HttpPost(url_allitems);
	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
	            response = httpclient.execute(httppost);
	         entity = response.getEntity();
	            is = entity.getContent();


	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	            
	    }
	    
	    
	 try{
       BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
       StringBuilder sb = new StringBuilder();
   
       String  line  = null;
       while ((line = reader.readLine()) != null) {
           sb.append(line);
   }
       
       
      
             
       String check = sb.toString();
       Log.i("testing service method upd", check);
        i = Integer.parseInt(check);
       is.close();
     
       
     
       
}catch(Exception e){
       Log.e("log_tag", "Error converting result "+e.toString());
}
	    
	 


	    //convert response to string
	  
	
		  	    //convert response to string
		  
		  
if(i==0){
//	val =false;
	  Log.i("testing service method upd", "0");
}if(i==1){
	//val = true;
	  Log.i("testing service method upd", "1");
}
	//return val;
	
	
	
	
}









public boolean DeleteMessages( String from,String to ) {
	 InputStream is = null;
	   String result = "";
	   JSONArray jArray = null;
	 
	   boolean val = false;
	   
	   
	 int i =0;
	   
	    //the data to send and the indicator of method
	   methodIns = new ArrayList<NameValuePair>();
	   methodIns.add(new BasicNameValuePair("method","13"));
	   methodIns.add(new BasicNameValuePair("from",from));
	 methodIns.add(new BasicNameValuePair("to",to));
	  
	
	    //http post client and paramer intlization
	    try{
	            httpclient = new DefaultHttpClient();
	             httppost = new HttpPost(url_allitems);
	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
	            response = httpclient.execute(httppost);
	         entity = response.getEntity();
	            is = entity.getContent();


	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	            
	    }
	    
	    
	 try{
       BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
       StringBuilder sb = new StringBuilder();
   
       String  line  = null;
       while ((line = reader.readLine()) != null) {
           sb.append(line);
   }
       
       
      
             
       String check = sb.toString();
       Log.i("testing service method upd", check);
        i = Integer.parseInt(check);
       is.close();
     
       
     
       
}catch(Exception e){
       Log.e("log_tag", "Error converting result "+e.toString());
}
	    
	 


	    //convert response to string
	  
	
		  	    //convert response to string
		  
		  
if(i==0){
	val =false;
	
}if(i==1){
	val = true;
}
	return val;
	
	
	
	
}

public JSONArray  getUserTypes() {
  	 InputStream is = null;
  	   String result = "";
  	   JSONArray jArrayout = null;
  	   
  	    //the data to send and the indicator of method
  	   methodIns = new ArrayList<NameValuePair>();
  	   methodIns.add(new BasicNameValuePair("method","14"));


  	    //http post client and paramer intlization
  	    try{
  	            httpclient = new DefaultHttpClient();
  	             httppost = new HttpPost(url_allitems);
  	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
  	            response = httpclient.execute(httppost);
  	         entity = response.getEntity();
  	            is = entity.getContent();


  	    }catch(Exception e){
  	            Log.e("log_tag", "Error in http connection "+e.toString());
  	    }


  	    //convert response to string
  	  
  	    
  	 try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
        }
        is.close();
        result=sb.toString();
}catch(Exception e){
        Log.e("log_tag", "Error converting result "+e.toString());
}
//parse json data
try{
  	   
  	    
  	             jArrayout = new JSONArray(result);
  	        
  	    }catch(JSONException e){
  	            Log.e("log_tag", "Error parsing data "+e.toString());
  	    }
  	    return jArrayout; 
  }
  
public JSONArray  getListTypes(String type) {
 	 InputStream is = null;
 	   String result = "";
 	   JSONArray jArrayout = null;
 	   
 	    //the data to send and the indicator of method
 	   methodIns = new ArrayList<NameValuePair>();
 	   methodIns.add(new BasicNameValuePair("method","15"));
 	   methodIns.add(new BasicNameValuePair("type",type));

 	    //http post client and paramer intlization
 	    try{
 	            httpclient = new DefaultHttpClient();
 	             httppost = new HttpPost(url_allitems);
 	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
 	            response = httpclient.execute(httppost);
 	         entity = response.getEntity();
 	            is = entity.getContent();


 	    }catch(Exception e){
 	            Log.e("log_tag", "Error in http connection "+e.toString());
 	    }


 	    //convert response to string
 	  
 	    
 	 try{
       BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
       StringBuilder sb = new StringBuilder();
       String line = null;
       while ((line = reader.readLine()) != null) {
               sb.append(line + "\n");
       }
       is.close();
       result=sb.toString();
}catch(Exception e){
       Log.e("log_tag", "Error converting result "+e.toString());
       Log.i("test",result);
}
//parse json data
try{
 	   
 	    
 	             jArrayout = new JSONArray(result);
 	        
 	             
 	    }catch(JSONException e){
 	            Log.e("log_tag", "Error parsing data "+e.toString());
 	    }
 	    return jArrayout; 
 }


public JSONArray  searchUser(String user) {
	 InputStream is = null;
	   String result = "";
	   JSONArray jArrayout = null;
	   
	    //the data to send and the indicator of method
	   methodIns = new ArrayList<NameValuePair>();
	   methodIns.add(new BasicNameValuePair("method","16"));
	   methodIns.add(new BasicNameValuePair("user",user));

	    //http post client and paramer intlization
	    try{
	            httpclient = new DefaultHttpClient();
	             httppost = new HttpPost(url_allitems);
	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
	            response = httpclient.execute(httppost);
	         entity = response.getEntity();
	            is = entity.getContent();


	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	    }


	    //convert response to string
	  
	    
	 try{
      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
      StringBuilder sb = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
              sb.append(line + "\n");
      }
      is.close();
      result=sb.toString();
}catch(Exception e){
      Log.e("log_tag", "Error converting result "+e.toString());
      Log.i("test",result);
}
//parse json data
try{
	   
	    
	             jArrayout = new JSONArray(result);
	        
	             
	    }catch(JSONException e){
	            Log.e("log_tag", "Error parsing data "+e.toString());
	    }
	    return jArrayout; 
}




public JSONArray  getSentMessages(String PhoneID) {
	 InputStream is = null;
	   String result = "";
	   JSONArray jArrayout = null;
	   
	    //the data to send and the indicator of method
	   methodIns = new ArrayList<NameValuePair>();
	   methodIns.add(new BasicNameValuePair("method","17"));
	 methodIns.add(new BasicNameValuePair("phoneID",PhoneID));
	

	    //http post client and paramer intlization
	    try{
	            httpclient = new DefaultHttpClient();
	             httppost = new HttpPost(url_allitems);
	            httppost.setEntity(new UrlEncodedFormEntity(methodIns));
	            response = httpclient.execute(httppost);
	         entity = response.getEntity();
	            is = entity.getContent();


	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	    }


	    //convert response to string
	  
	    
	 try{
      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
      StringBuilder sb = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
              sb.append(line + "\n");
      }
      is.close();
      result=sb.toString();
}catch(Exception e){
      Log.e("log_tag", "Error converting result "+e.toString());
}
//parse json data
try{
	   
	Log.i("test",result);
	             jArrayout = new JSONArray(result);
	        
	    }catch(JSONException e){
	            Log.e("log_tag", "Error parsing data "+e.toString());
	    }
	    return jArrayout; 
}
}
