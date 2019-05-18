package com.victuallist.winereviewer.data.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class CellarObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6648686166239077707L;
	
	
	int sqlID = 0;
	String upc = "";
	String name = "";
	String vineyard = "";
	String varietal = "";
	String vintage = "";
	String origin = "";
	String purchasePrice = "";
	int bottleCount = 0;
	String bottleSize = "";
	String purchaseDate = "";
	String location = "";
	
	String drinkByDate = "";
	String currentValue = "";
	String notes = "";
	
	ArrayList<ReviewObject> relatedReviews = new ArrayList<ReviewObject>();
	Boolean uploaded = false;
	Boolean shared = false;
	Boolean upcFound = false;
	
	public CellarObject () {
		
	}
	
	
	public int getSqlID() {
		return sqlID;
	}
	public void setSqlID(int sqlID) {
		this.sqlID = sqlID;
	}
	public String getUPC() {
		return upc;
	}
	public void setUPC(String upc) {
		this.upc = upc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVineyard() {
		return vineyard;
	}
	public void setVineyard(String vineyard) {
		this.vineyard = vineyard;
	}
	public String getVarietal() {
		return varietal;
	}
	public void setVarietal(String varietal) {
		this.varietal = varietal;
	}
	public String getVintage() {
		return vintage;
	}
	public void setVintage(String vintage) {
		this.vintage = vintage;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public int getBottleCount() {
		return bottleCount;
	}
	public void setBottleCount(int bottleCount) {
		this.bottleCount = bottleCount;
	}
	public String getBottleSize() {
		return bottleSize;
	}
	public void setBottleSize(String bottleSize) {
		this.bottleSize = bottleSize;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getDrinkByDate() {
		return drinkByDate;
	}
	public void setDrinkByDate(String drinkByDate) {
		this.drinkByDate = drinkByDate;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Boolean getUploaded() {
		return uploaded;
	}
	public void setUploaded(Boolean uploaded) {
		this.uploaded = uploaded;
	}
	public Boolean getShared() {
		return shared;
	}
	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	public Boolean getUPCFound() {
		return upcFound;
	}
	public void setUPCFound(Boolean upcFound) {
		this.upcFound = upcFound;
	}
	
	public ArrayList<ReviewObject> getRelatedReviews(){
		return relatedReviews;
	}
	
	public void setRelatedReviews(ArrayList<ReviewObject> reviews) {
		this.relatedReviews = reviews;
	}
	
	
	
	
}
