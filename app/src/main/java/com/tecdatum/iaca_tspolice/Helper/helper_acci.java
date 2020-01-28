package com.tecdatum.iaca_tspolice.Helper;

/**
 * Created by HI on 1/9/2018.
 */

public class helper_acci {

    String id;
    String crimeNumber;
    String AccidentType;
    String dateTime;
    String location;
    String RoadTypeId;
    String RoadNumber;
    String detectedStatus;
    String latitude;
    String longitude;
    String description;
    String NoOfInjuries;
    String NoOfDeaths;
    String VictimcategorieId;
    String VictimStatus;
    String VictimAlcoholicPercentage;
    String AccusedCategorieId;
    String AccusedStatus;
    String AccusedAlcoholicPercentage;
    String AccidentType1;
    String RoadType;
    String Victimcategorie;
    String AccusedCategorie;
    String VictimStatusName;
    String AccusedStatusName;
    String Locality;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrimeNumber() {
        return crimeNumber;
    }

    public void setCrimeNumber(String crimeNumber) {
        this.crimeNumber = crimeNumber;
    }

    public String getAccidentType() {
        return AccidentType;
    }

    public void setAccidentType(String accidentType) {
        AccidentType = accidentType;
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

    public String getRoadTypeId() {
        return RoadTypeId;
    }

    public void setRoadTypeId(String roadTypeId) {
        RoadTypeId = roadTypeId;
    }

    public String getRoadNumber() {
        return RoadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        RoadNumber = roadNumber;
    }

    public String getDetectedStatus() {
        return detectedStatus;
    }

    public void setDetectedStatus(String detectedStatus) {
        this.detectedStatus = detectedStatus;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoOfInjuries() {
        return NoOfInjuries;
    }

    public void setNoOfInjuries(String noOfInjuries) {
        NoOfInjuries = noOfInjuries;
    }

    public String getNoOfDeaths() {
        return NoOfDeaths;
    }

    public void setNoOfDeaths(String noOfDeaths) {
        NoOfDeaths = noOfDeaths;
    }

    public String getVictimcategorieId() {
        return VictimcategorieId;
    }

    public void setVictimcategorieId(String victimcategorieId) {
        VictimcategorieId = victimcategorieId;
    }

    public String getVictimStatus() {
        return VictimStatus;
    }

    public void setVictimStatus(String victimStatus) {
        VictimStatus = victimStatus;
    }

    public String getVictimAlcoholicPercentage() {
        return VictimAlcoholicPercentage;
    }

    public void setVictimAlcoholicPercentage(String victimAlcoholicPercentage) {
        VictimAlcoholicPercentage = victimAlcoholicPercentage;
    }

    public String getAccusedCategorieId() {
        return AccusedCategorieId;
    }

    public void setAccusedCategorieId(String accusedCategorieId) {
        AccusedCategorieId = accusedCategorieId;
    }

    public String getAccusedStatus() {
        return AccusedStatus;
    }

    public void setAccusedStatus(String accusedStatus) {
        AccusedStatus = accusedStatus;
    }

    public String getAccusedAlcoholicPercentage() {
        return AccusedAlcoholicPercentage;
    }

    public void setAccusedAlcoholicPercentage(String accusedAlcoholicPercentage) {
        AccusedAlcoholicPercentage = accusedAlcoholicPercentage;
    }

    public String getAccidentType1() {
        return AccidentType1;
    }

    public void setAccidentType1(String accidentType1) {
        AccidentType1 = accidentType1;
    }

    public String getRoadType() {
        return RoadType;
    }

    public void setRoadType(String roadType) {
        RoadType = roadType;
    }

    public String getVictimcategorie() {
        return Victimcategorie;
    }

    public void setVictimcategorie(String victimcategorie) {
        Victimcategorie = victimcategorie;
    }

    public String getAccusedCategorie() {
        return AccusedCategorie;
    }

    public void setAccusedCategorie(String accusedCategorie) {
        AccusedCategorie = accusedCategorie;
    }

    public String getVictimStatusName() {
        return VictimStatusName;
    }

    public void setVictimStatusName(String victimStatusName) {
        VictimStatusName = victimStatusName;
    }

    public String getAccusedStatusName() {
        return AccusedStatusName;
    }

    public void setAccusedStatusName(String accusedStatusName) {
        AccusedStatusName = accusedStatusName;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public helper_acci(String id, String crimeNumber, String accidentType, String dateTime, String location, String roadTypeId, String roadNumber, String detectedStatus, String latitude, String longitude, String description, String noOfInjuries, String noOfDeaths, String victimcategorieId, String victimStatus, String victimAlcoholicPercentage, String accusedCategorieId, String accusedStatus, String accusedAlcoholicPercentage, String accidentType1, String roadType, String victimcategorie,
                       String accusedCategorie, String victimStatusName, String accusedStatusName, String locality) {
        this.id = id;
        this.crimeNumber = crimeNumber;
        AccidentType = accidentType;
        this.dateTime = dateTime;
        this.location = location;
        RoadTypeId = roadTypeId;
        RoadNumber = roadNumber;
        this.detectedStatus = detectedStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        NoOfInjuries = noOfInjuries;
        NoOfDeaths = noOfDeaths;
        VictimcategorieId = victimcategorieId;
        VictimStatus = victimStatus;
        VictimAlcoholicPercentage = victimAlcoholicPercentage;
        AccusedCategorieId = accusedCategorieId;
        AccusedStatus = accusedStatus;
        AccusedAlcoholicPercentage = accusedAlcoholicPercentage;
        AccidentType1 = accidentType1;
        RoadType = roadType;
        Victimcategorie = victimcategorie;
        AccusedCategorie = accusedCategorie;
        VictimStatusName = victimStatusName;
        AccusedStatusName = accusedStatusName;
        Locality = locality;
    }
}