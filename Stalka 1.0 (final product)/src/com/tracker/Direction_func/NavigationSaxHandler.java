package com.tracker.Direction_func;



import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
SOURCE 1:
Adapted from 
* From:http://stackoverflow.com/questions/3109158/how-to-draw-a-path-on-a-map-using-kml-file
* Date 19:/03/2011

SOURCE 2:
* From:http://stackoverflow.com/questions/3109158/how-to-draw-a-path-on-a-map-using-kml-file/3109723#3109723
* Date 18:/11/2011

*/


public class NavigationSaxHandler extends DefaultHandler{ 



private ArrayList<Placemark> placemarks = new ArrayList<Placemark>();
	
 private boolean in_kmltag = false; 
 private boolean in_placemarktag = false; 
 private boolean in_nametag = false;
 private boolean in_descriptiontag = false;
 private boolean in_geometrycollectiontag = false;
 private boolean in_linestringtag = false;
 private boolean in_lookattag = false;
 private boolean in_lattag = false;
 private boolean in_lngtag = false;
 private boolean in_pointtag = false;
 private boolean in_coordinatestag = false;
 private boolean in_rangetag = false;
 private boolean in_tilttag = false;
 private Placemark currentPlacemark;
 






 public ArrayList<Placemark>  getParsedData() {
	 String s= "";
	    for (Iterator<Placemark> iter=placemarks.iterator();iter.hasNext();) {
	        Placemark p = (Placemark)iter.next();
	        s += p.getTitle() + "\n" + p.getDescription() + "\n"+p.getLongitude()+"\n"+p.getLatitude()+"\n";
	    }
	    Log.i("sax parser: ", s);
      return placemarks; 
 } 

 
 public void startDocument() throws SAXException { 
  
 } 

 @Override 
 public void endDocument() throws SAXException { 
   
 } 


 
 @Override 
 // start element called at the beginning element tag
 public void startElement(String namespaceURI, String localName, 
           String qName, Attributes atts) throws SAXException { 
	 
      if (localName.equals("kml")) { 
           this.in_kmltag = true;
           
      } else if (localName.equals("Placemark")) { 
           this.in_placemarktag = true; 
           // Initialise a empty placemark object.
         currentPlacemark =new Placemark();
           // setting a expression true so the character element can read the string.
      } else if (localName.equals("name")) { 
           this.in_nametag = true;
      } else if (localName.equals("description")) { 
          this.in_descriptiontag = true;
      } else if (localName.equals("GeometryCollection")) { 
          this.in_geometrycollectiontag = true;
      } else if (localName.equals("LineString")) { 
          this.in_linestringtag = true;     
      }    
          else if (localName.equals("point")) { 
              this.in_pointtag = true;          
          } else if (localName.equals("coordinates")) {
             
              this.in_coordinatestag = true;                        
          }
          //////////// change
          
      else if (localName.equals("lookat")) { 
          this.in_lookattag = true;      
          
          
      } 
      else if (localName.equals("longitude")) {
        
          this.in_lngtag = true;                    
          
      }
      else if (localName.equals("latitude")) {
        
          this.in_lattag = true;                        
      }else if (localName.equals("range")) {
        
          this.in_rangetag = true;                        
      }
 } 

 
 @Override 
 // end element method
 public void endElement(String namespaceURI, String localName, String qName) 
           throws SAXException { 
       if (localName.equals("kml")) {
           this.in_kmltag = false; 
       } else if (localName.equals("Placemark")) { 
    	   // Changing the variable indicating placemark back to false.
           this.in_placemarktag = false;
     // Adding the placemark object into the placemark Array list.
       placemarks.add(currentPlacemark);

       } else if (localName.equals("name")) { 
           this.in_nametag = false;           
       } else if (localName.equals("description")) { 
           this.in_descriptiontag = false;
       } else if (localName.equals("GeometryCollection")) { 
           this.in_geometrycollectiontag = false;
       } else if (localName.equals("LineString")) { 
           this.in_linestringtag = false; 
           
       } else if (localName.equals("point")) { 
           this.in_pointtag = false;          
       } else if (localName.equals("coordinates")) { 
           this.in_coordinatestag = false;
       
       } else if (localName.equals("lookat")) { 
           this.in_lookattag = false;          
       } else if (localName.equals("longitude")) { 
           this.in_lngtag = false;
       }
       
           else if (localName.equals("latitude")) {
               
               this.in_lattag = false;                        
           }
           else if (localName.equals("range")) {
               
               this.in_rangetag = false;                        
           }
 } 

 
 @Override 
 // the characters method.
public void characters(char ch[], int start, int length) { 
    if(this.in_nametag){ 
    	// checking if the placemark object is not null, if it is then initialise it 
        if( currentPlacemark==null) currentPlacemark =new Placemark();
        // setting the title of the place mark
        currentPlacemark.setTitle(new String(ch, start, length));      
        //Log.i("sax parser: ",new String(ch, start, length));
    } else 
    if(this.in_descriptiontag){ 
    	 if( currentPlacemark==null) currentPlacemark =new Placemark();
    // setting the description.
    	 currentPlacemark.setDescription(new String(ch, start, length)); 
       // Log.i("sax parser: ",new String(ch, start, length));
    } else
    if(this.in_lngtag){        
    	 if( currentPlacemark==null) currentPlacemark =new Placemark();
    	 // setting the longitude 
    	 currentPlacemark.setLongitude(new String(ch, start, length));
       // Log.i("sax parser: ",new String(ch, start, length));
      
    }else
    if(this.in_lattag){        
   	 if( currentPlacemark==null) currentPlacemark =new Placemark();
   	 // setting the latitude. 
   	 currentPlacemark.setLatitude(new String(ch, start, length));
      //Log.i("sax parser: ",new String(ch, start, length));
     
   }
} 
}
