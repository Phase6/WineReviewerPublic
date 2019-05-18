package com.victuallist.winereviewer.data.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class CoreReviewObject implements Serializable {

    private static final long serialVersionUID = 7835157809000337100L;

    int sqlID = 0;
    String name = "";
    String vineyard = "";
    String varietal = "";
    String vintage = "";
    String origin = "";
    String rating = "";
    int numberOfReviewsInt = 0;
    String numberOfReviewsString = "";
    boolean isFavorite = false;
    boolean userHasReviewed = false;

    ArrayList<DetailReviewObject> reviewDetails = new ArrayList<>();

    public CoreReviewObject (String receivedVineyard, String receivedVarietal, String receivedVintage, String receivedRating) {
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

    public float getFloatRating(){
        float rating = 0;
        try{
            rating = Float.valueOf(this.rating);
        }catch(ClassCastException e){

        }
        return rating;
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

    public ArrayList<DetailReviewObject> getReviewDetails() {
        return reviewDetails;
    }

    public void setReviewDetails(ArrayList<DetailReviewObject> reviewDetails) {
        this.reviewDetails = reviewDetails;
    }

    public void addReviewDetail(DetailReviewObject detail){
        reviewDetails.add(detail);
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void toggleFavorite(){
        this.isFavorite = !this.isFavorite;
    }

    public boolean isUserHasReviewed() {
        return userHasReviewed;
    }

    public void setUserHasReviewed(boolean userHasReviewed) {
        this.userHasReviewed = userHasReviewed;
    }
}
