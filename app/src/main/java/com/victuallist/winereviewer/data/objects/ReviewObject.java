package com.victuallist.winereviewer.data.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewObject implements Serializable{
	
	private static final long serialVersionUID = 7835157809000337100L;
	
	int sqlID = 0;
	String upc = "";
	String name = "";
	String vineyard = "";
	String varietal = "";
	String vintage = "";
	String origin = "";
	String rating = "";
	int numberOfReviewsInt = 0;
	String numberOfReviewsString = "";
	String price = "";
	String pairing = "";
	String taste = "";
	String smell = "";
	String color = "";
	Boolean uploaded = false;
	Boolean shared = false;
	Boolean upcFound = false;

	ArrayList<CellarObject> relatedCellarItems = new ArrayList();
	
	public ReviewObject (String receivedVineyard, String receivedVarietal, String receivedVintage, String receivedRating) {
		this.vineyard = receivedVineyard;
		this.varietal = receivedVarietal;
		this.vintage = receivedVintage;
		this.rating = receivedRating;
	
	}
	
	public void setSQLID (int receivedID) {
		this.sqlID = receivedID;
	}
	
	public int getSQLID () {
		return this.sqlID;
	}
	
	public void setName (String receivedName) {
		this.name = receivedName;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setUPC (String receivedUPC) {
		this.upc = receivedUPC;
	}
	
	public String getUPC(){
		return this.upc;
	}
	
	public void setUPCFound (boolean found) {
		this.upcFound = found;
	}
	
	public Boolean getUPCFound () {
		return this.upcFound;
	}
	
	public void setVineyard (String receivedVineyard) {
		this.vineyard = receivedVineyard;
	}
	
	public String getVineyard () {
		return this.vineyard;
	}
	
	public void setVarietal (String receivedVarietal) {
		this.varietal = receivedVarietal;
	}
	
	public String getVarietal () {
		return this.varietal;
	}
	
	public void setVintage (String receivedVintage) {
		this.vintage = receivedVintage;
	}
	
	public String getVintage () {
		return this.vintage;
	}
	
	public void setOrigin(String receivedOrigin) {
		this.origin = receivedOrigin;
	}
	
	public String getOrigin() {
		return this.origin;
	}
	
	public void setRating (String receivedRating) {
		this.rating = receivedRating;
	}
	
	public String getRating () {
		return this.rating;
	}
	
	public void setPrice (String receivedPrice) {
		this.price = receivedPrice;
	}
	
	public String getPrice () {
		return this.price;
	}
	
	public void setPairing(String receivedPairing) {
		this.pairing = receivedPairing;
	}
	
	public String getPairing() {
		return this.pairing;
	}
	
	public void setTaste (String receivedTaste) {
		this.taste = receivedTaste;
	}
	
	public String getTaste () {
		return this.taste;
	}
	
	public void setSmell (String receivedSmell) {
		this.smell = receivedSmell;
	}
	
	public String getSmell () {
		return this.smell;
	}
	
	public void setColor (String receivedColor) {
		this.color = receivedColor;
	}
	
	public String getColor () {
		return this.color;
	}
	
	public void setUploaded (Boolean receivedUploaded) {
		this.uploaded = receivedUploaded;
	}
	
	public Boolean getUploaded () {
		return this.uploaded;
	}
	
	public void setShared (Boolean receivedShared) {
		this.shared = receivedShared;
	}
	
	public Boolean getShared () {
		return this.shared;
	}

	public float getFloatRating(){
		float rating = 0;
		try{
			rating = Float.valueOf(this.rating);
		}catch(ClassCastException e){

		}
		return rating;
	}

	public void setRelatedCellarItems(ArrayList<CellarObject> list){
		this.relatedCellarItems = list;
	}

	public ArrayList<CellarObject> getRelatedCellarItems(){
		return this.relatedCellarItems;
	}

	public int getNumberOfReviewsInt() {
		return numberOfReviewsInt;
	}

	public void setNumberOfReviewsInt(int numberOfReviewsInt) {
		this.numberOfReviewsInt = numberOfReviewsInt;
	}

	public String getNumberOfReviewsString() {
		numberOfReviewsString = "(" + String.valueOf(this.getNumberOfReviewsInt()) + ")";
		return numberOfReviewsString;
	}

	public void setNumberOfReviewsString(String numberOfReviewsString) {
		this.numberOfReviewsString = numberOfReviewsString;
	}
	
}
