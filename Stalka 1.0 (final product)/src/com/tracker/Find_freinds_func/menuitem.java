package com.tracker.Find_freinds_func;

public class menuitem {
	private String title;
private String Descr;
private int possiton;
	public  menuitem (String t, String D, int pos){
		title = t;
		Descr = D;
		possiton = pos;
	}
	
	public String getTitle(){
		return title;
	}
	public String getDescr(){
		return Descr;
	}
	public int getPos(){
		return possiton ;
	}
}
