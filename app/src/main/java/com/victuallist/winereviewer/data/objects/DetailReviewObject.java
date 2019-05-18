package com.victuallist.winereviewer.data.objects;

import java.io.Serializable;

public class DetailReviewObject implements Serializable{

    private static final long serialVersionUID = 7835157809000337100L;

    String rating = "";
    String price = "";
    String pairing = "";
    String taste = "";
    String smell = "";
    String color = "";

    public DetailReviewObject(){

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

    public float getFloatRating(){
        float rating = 0;
        try{
            rating = Float.valueOf(this.rating);
        }catch(ClassCastException e){

        }
        return rating;
    }



}
