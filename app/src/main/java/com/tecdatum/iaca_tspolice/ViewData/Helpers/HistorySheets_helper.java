package com.tecdatum.iaca_tspolice.ViewData.Helpers;

/**
 * Created by HI on 4/18/2018.
 */

public class HistorySheets_helper {
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getAliasName() {
        return AliasName;
    }

    public void setAliasName(String aliasName) {
        AliasName = aliasName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPsid() {
        return Psid;
    }

    public void setPsid(String psid) {
        Psid = psid;
    }

    public String getEnteryDate() {
        return EnteryDate;
    }

    public void setEnteryDate(String enteryDate) {
        EnteryDate = enteryDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    String Id,Number,Type,PersonName,AliasName,FatherName,Age,Latitude,Longitude,Address,Psid,EnteryDate,CreatedBy;

    public HistorySheets_helper(String id, String number, String type, String personName, String aliasName, String fatherName, String age, String latitude, String longitude, String address, String psid, String enteryDate, String createdBy) {
        Id = id;
        Number = number;
        Type = type;
        PersonName = personName;
        AliasName = aliasName;
        FatherName = fatherName;
        Age = age;
        Latitude = latitude;
        Longitude = longitude;
        Address = address;
        Psid = psid;
        EnteryDate = enteryDate;
        CreatedBy = createdBy;
    }
}
