package com.tecdatum.iaca_tspolice.ViewData.Helpers;

/**
 * Created by HI on 4/17/2018.
 */

public class Accident_helper {

    String Id,CrimeNumber,PSID,PSCode,CrimeTypeMaster_Id,CrimeType,CrimeSubtypeMaster_Id,CrimeSubtype,Latitude,Longitude,Location,Descr,
    DateOfOffence,DateOfEntry,CrimeStatusMaster_Id,DateofReport,NoofInjuries,NoofDeaths,RoadTypeID,RoadNumber,VictimCategoryID,VictimAlcoholicORNot,VictimVehicleNo,
            AccusedCategoryID,AccusedAlcoholicORNot,AccusedVehicleNo;




    public Accident_helper(String id, String crimeNumber, String PSID, String PSCode, String crimeTypeMaster_Id,
                           String crimeType, String crimeSubtypeMaster_Id, String crimeSubtype, String latitude,
                           String longitude, String location, String descr, String dateOfOffence, String dateOfEntry,
                           String crimeStatusMaster_Id, String dateofReport, String noofInjuries, String noofDeaths,
                           String roadTypeID, String roadNumber, String victimCategoryID, String victimAlcoholicORNot,
                           String victimVehicleNo, String accusedCategoryID, String accusedAlcoholicORNot, String accusedVehicleNo) {
        Id = id;
        CrimeNumber = crimeNumber;
        this.PSID = PSID;
        this.PSCode = PSCode;
        CrimeTypeMaster_Id = crimeTypeMaster_Id;
        CrimeType = crimeType;
        CrimeSubtypeMaster_Id = crimeSubtypeMaster_Id;
        CrimeSubtype = crimeSubtype;
        Latitude = latitude;
        Longitude = longitude;
        Location = location;
        Descr = descr;
        DateOfOffence = dateOfOffence;
        DateOfEntry = dateOfEntry;
        CrimeStatusMaster_Id = crimeStatusMaster_Id;
        DateofReport = dateofReport;
        NoofInjuries = noofInjuries;
        NoofDeaths = noofDeaths;
        RoadTypeID = roadTypeID;
        RoadNumber = roadNumber;
        VictimCategoryID = victimCategoryID;
        VictimAlcoholicORNot = victimAlcoholicORNot;
        VictimVehicleNo = victimVehicleNo;
        AccusedCategoryID = accusedCategoryID;
        AccusedAlcoholicORNot = accusedAlcoholicORNot;
        AccusedVehicleNo = accusedVehicleNo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCrimeNumber() {
        return CrimeNumber;
    }

    public void setCrimeNumber(String crimeNumber) {
        CrimeNumber = crimeNumber;
    }

    public String getPSID() {
        return PSID;
    }

    public void setPSID(String PSID) {
        this.PSID = PSID;
    }

    public String getPSCode() {
        return PSCode;
    }

    public void setPSCode(String PSCode) {
        this.PSCode = PSCode;
    }

    public String getCrimeTypeMaster_Id() {
        return CrimeTypeMaster_Id;
    }

    public void setCrimeTypeMaster_Id(String crimeTypeMaster_Id) {
        CrimeTypeMaster_Id = crimeTypeMaster_Id;
    }

    public String getCrimeType() {
        return CrimeType;
    }

    public void setCrimeType(String crimeType) {
        CrimeType = crimeType;
    }

    public String getCrimeSubtypeMaster_Id() {
        return CrimeSubtypeMaster_Id;
    }

    public void setCrimeSubtypeMaster_Id(String crimeSubtypeMaster_Id) {
        CrimeSubtypeMaster_Id = crimeSubtypeMaster_Id;
    }

    public String getCrimeSubtype() {
        return CrimeSubtype;
    }

    public void setCrimeSubtype(String crimeSubtype) {
        CrimeSubtype = crimeSubtype;
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

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public String getDateOfOffence() {
        return DateOfOffence;
    }

    public void setDateOfOffence(String dateOfOffence) {
        DateOfOffence = dateOfOffence;
    }

    public String getDateOfEntry() {
        return DateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        DateOfEntry = dateOfEntry;
    }

    public String getCrimeStatusMaster_Id() {
        return CrimeStatusMaster_Id;
    }

    public void setCrimeStatusMaster_Id(String crimeStatusMaster_Id) {
        CrimeStatusMaster_Id = crimeStatusMaster_Id;
    }

    public String getDateofReport() {
        return DateofReport;
    }

    public void setDateofReport(String dateofReport) {
        DateofReport = dateofReport;
    }

    public String getNoofInjuries() {
        return NoofInjuries;
    }

    public void setNoofInjuries(String noofInjuries) {
        NoofInjuries = noofInjuries;
    }

    public String getNoofDeaths() {
        return NoofDeaths;
    }

    public void setNoofDeaths(String noofDeaths) {
        NoofDeaths = noofDeaths;
    }

    public String getRoadTypeID() {
        return RoadTypeID;
    }

    public void setRoadTypeID(String roadTypeID) {
        RoadTypeID = roadTypeID;
    }

    public String getRoadNumber() {
        return RoadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        RoadNumber = roadNumber;
    }

    public String getVictimCategoryID() {
        return VictimCategoryID;
    }

    public void setVictimCategoryID(String victimCategoryID) {
        VictimCategoryID = victimCategoryID;
    }

    public String getVictimAlcoholicORNot() {
        return VictimAlcoholicORNot;
    }

    public void setVictimAlcoholicORNot(String victimAlcoholicORNot) {
        VictimAlcoholicORNot = victimAlcoholicORNot;
    }

    public String getVictimVehicleNo() {
        return VictimVehicleNo;
    }

    public void setVictimVehicleNo(String victimVehicleNo) {
        VictimVehicleNo = victimVehicleNo;
    }

    public String getAccusedCategoryID() {
        return AccusedCategoryID;
    }

    public void setAccusedCategoryID(String accusedCategoryID) {
        AccusedCategoryID = accusedCategoryID;
    }

    public String getAccusedAlcoholicORNot() {
        return AccusedAlcoholicORNot;
    }

    public void setAccusedAlcoholicORNot(String accusedAlcoholicORNot) {
        AccusedAlcoholicORNot = accusedAlcoholicORNot;
    }

    public String getAccusedVehicleNo() {
        return AccusedVehicleNo;
    }

    public void setAccusedVehicleNo(String accusedVehicleNo) {
        AccusedVehicleNo = accusedVehicleNo;
    }
}
