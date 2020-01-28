package com.tecdatum.iaca_tspolice.ViewData.Helpers;

/**
 * Created by HI on 4/18/2018.
 */

public class Traffic_helper {


    String Id,PSId,PS,Type,Service,Location,Remarks,NoOfVehicles,Latitude,Longitude;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPSId() {
        return PSId;
    }

    public void setPSId(String PSId) {
        this.PSId = PSId;
    }

    public String getPS() {
        return PS;
    }

    public void setPS(String PS) {
        this.PS = PS;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getNoOfVehicles() {
        return NoOfVehicles;
    }

    public void setNoOfVehicles(String noOfVehicles) {
        NoOfVehicles = noOfVehicles;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public Traffic_helper(String id, String PSId, String PS, String type, String service, String location, String remarks, String noOfVehicles, String latitude, String longitude) {
        Id = id;
        this.PSId = PSId;
        this.PS = PS;
        Type = type;
        Service = service;
        Location = location;
        Remarks = remarks;
        NoOfVehicles = noOfVehicles;
        Latitude = latitude;
        Longitude = longitude;
    }
}
