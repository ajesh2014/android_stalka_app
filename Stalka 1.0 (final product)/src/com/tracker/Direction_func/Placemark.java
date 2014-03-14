package com.tracker.Direction_func;
/**
SOURCE 1:
Adapted from 
* From:http://stackoverflow.com/questions/3109158/how-to-draw-a-path-on-a-map-using-kml-file
* Date 19:/03/2011
*/

public class Placemark {

String title;
String description;
String coordinates;
String address;
String lng;
String lat;


public String getTitle() {
    return title;
}
public void setTitle(String title) {
    this.title = title;
}
public String getDescription() {
    return description;
}
public void setDescription(String description) {
    this.description = description;
}
public String getCoordinates() {
    return coordinates;
}
public void setCoordinates(String coordinates) {
    this.coordinates = coordinates;
}
public String getAddress() {
    return address;
}
public void setAddress(String address) {
    this.address = address;
}

public String getLongitude() {

    return lng;
}
public void setLongitude(String lng) {
    this.lng = lng;
}

public String getLatitude() {

    return lat;
}
public void setLatitude(String lat) {
    this.lat = lat;
}

}