package com.tecdatum.iaca_tspolice.Helper;

/**
 * Created by HI on 1/11/2018.
 */

public class helper_crime {

    String id;
    String CrimeNo;
    String Crimetypeid;
    String Crimetype;
    String Crimesubtypeid;
    String Crimesubtype;
    String dateTime;
    String location;
    String detected_;
    String detected_id;
    String latitude;
    String longitude;
    String Descr;

    public helper_crime(String id, String crimeNo, String crimetypeid, String crimetype,
                        String crimesubtypeid, String crimesubtype, String dateTime, String location,
                        String detected_, String detected_id, String latitude, String longitude, String descr) {
        this.id = id;
        CrimeNo = crimeNo;
        Crimetypeid = crimetypeid;
        Crimetype = crimetype;
        Crimesubtypeid = crimesubtypeid;
        Crimesubtype = crimesubtype;
        this.dateTime = dateTime;
        this.location = location;
        this.detected_ = detected_;
        this.detected_id = detected_id;
        this.latitude = latitude;
        this.longitude = longitude;
        Descr = descr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrimeNo() {
        return CrimeNo;
    }

    public void setCrimeNo(String crimeNo) {
        CrimeNo = crimeNo;
    }

    public String getCrimetypeid() {
        return Crimetypeid;
    }

    public void setCrimetypeid(String crimetypeid) {
        Crimetypeid = crimetypeid;
    }

    public String getCrimetype() {
        return Crimetype;
    }

    public void setCrimetype(String crimetype) {
        Crimetype = crimetype;
    }

    public String getCrimesubtypeid() {
        return Crimesubtypeid;
    }

    public void setCrimesubtypeid(String crimesubtypeid) {
        Crimesubtypeid = crimesubtypeid;
    }

    public String getCrimesubtype() {
        return Crimesubtype;
    }

    public void setCrimesubtype(String crimesubtype) {
        Crimesubtype = crimesubtype;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetected_() {
        return detected_;
    }

    public void setDetected_(String detected_) {
        this.detected_ = detected_;
    }

    public String getDetected_id() {
        return detected_id;
    }

    public void setDetected_id(String detected_id) {
        this.detected_id = detected_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }
}
