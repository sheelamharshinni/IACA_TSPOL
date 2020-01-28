package com.tecdatum.iaca_tspolice.DataEntry;

/**
 * Created by HI on 5/22/2017.
 */

class SampleHPclass {
    private String id;
    private String name;
    private String lat;
    private String lng;
    private String loc;

    public SampleHPclass(String id, String name, String lat, String lng, String loc) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.loc = loc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
