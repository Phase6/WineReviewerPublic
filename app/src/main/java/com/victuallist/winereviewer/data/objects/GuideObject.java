package com.victuallist.winereviewer.data.objects;

import java.io.Serializable;

public class GuideObject implements Serializable{

    /**
     *
     */
//    private static final long serialVersionUID = 6648686166239077707L;

    String type, variety, origin, grapes, flavor, pairing;

    public GuideObject(){
        this.type = "";
        this.variety = "";
        this.origin = "";
        this.grapes = "";
        this.flavor = "";
        this.pairing = "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getGrapes() {
        return grapes;
    }

    public void setGrapes(String grapes) {
        this.grapes = grapes;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getPairing() {
        return pairing;
    }

    public void setPairing(String pairing) {
        this.pairing = pairing;
    }

}
