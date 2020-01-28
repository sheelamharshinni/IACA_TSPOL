package com.tecdatum.iaca_tspolice.ViewData.Helpers;

/**
 * Created by HI on 4/18/2018.
 */

public class Landmark_helper {



    String Id,LandMarkMaster_Id,LandMarkMaster_Type,LandMarkMaster_SubType,NameoWorshipPlace,AddressWorshipPlace,Lattitude1,Longitude1,NameoftheIncharge
    ,InchargeDesignation,ContactNo,Remarks,PSId,PoliceStation,sensitivityLevelId,SensitivitySubLevelId;



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLandMarkMaster_Id() {
        return LandMarkMaster_Id;
    }

    public void setLandMarkMaster_Id(String landMarkMaster_Id) {
        LandMarkMaster_Id = landMarkMaster_Id;
    }

    public String getLandMarkMaster_Type() {
        return LandMarkMaster_Type;
    }

    public void setLandMarkMaster_Type(String landMarkMaster_Type) {
        LandMarkMaster_Type = landMarkMaster_Type;
    }

    public String getLandMarkMaster_SubType() {
        return LandMarkMaster_SubType;
    }

    public void setLandMarkMaster_SubType(String landMarkMaster_SubType) {
        LandMarkMaster_SubType = landMarkMaster_SubType;
    }

    public String getNameoWorshipPlace() {
        return NameoWorshipPlace;
    }

    public void setNameoWorshipPlace(String nameoWorshipPlace) {
        NameoWorshipPlace = nameoWorshipPlace;
    }

    public String getAddressWorshipPlace() {
        return AddressWorshipPlace;
    }

    public void setAddressWorshipPlace(String addressWorshipPlace) {
        AddressWorshipPlace = addressWorshipPlace;
    }

    public String getLattitude1() {
        return Lattitude1;
    }

    public void setLattitude1(String lattitude1) {
        Lattitude1 = lattitude1;
    }

    public String getLongitude1() {
        return Longitude1;
    }

    public void setLongitude1(String longitude1) {
        Longitude1 = longitude1;
    }

    public String getNameoftheIncharge() {
        return NameoftheIncharge;
    }

    public void setNameoftheIncharge(String nameoftheIncharge) {
        NameoftheIncharge = nameoftheIncharge;
    }

    public String getInchargeDesignation() {
        return InchargeDesignation;
    }

    public void setInchargeDesignation(String inchargeDesignation) {
        InchargeDesignation = inchargeDesignation;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getPSId() {
        return PSId;
    }

    public void setPSId(String PSId) {
        this.PSId = PSId;
    }

    public String getPoliceStation() {
        return PoliceStation;
    }

    public void setPoliceStation(String policeStation) {
        PoliceStation = policeStation;
    }

    public String getSensitivityLevelId() {
        return sensitivityLevelId;
    }

    public void setSensitivityLevelId(String sensitivityLevelId) {
        this.sensitivityLevelId = sensitivityLevelId;
    }

    public String getSensitivitySubLevelId() {
        return SensitivitySubLevelId;
    }

    public void setSensitivitySubLevelId(String sensitivitySubLevelId) {
        SensitivitySubLevelId = sensitivitySubLevelId;
    }

    public Landmark_helper(String id, String landMarkMaster_Id, String landMarkMaster_Type, String landMarkMaster_SubType, String nameoWorshipPlace, String addressWorshipPlace, String lattitude1, String longitude1, String nameoftheIncharge, String inchargeDesignation, String contactNo, String remarks, String PSId, String policeStation, String sensitivityLevelId, String sensitivitySubLevelId) {
        Id = id;
        LandMarkMaster_Id = landMarkMaster_Id;
        LandMarkMaster_Type = landMarkMaster_Type;
        LandMarkMaster_SubType = landMarkMaster_SubType;
        NameoWorshipPlace = nameoWorshipPlace;
        AddressWorshipPlace = addressWorshipPlace;
        Lattitude1 = lattitude1;
        Longitude1 = longitude1;
        NameoftheIncharge = nameoftheIncharge;
        InchargeDesignation = inchargeDesignation;
        ContactNo = contactNo;
        Remarks = remarks;
        this.PSId = PSId;
        PoliceStation = policeStation;
        this.sensitivityLevelId = sensitivityLevelId;
        SensitivitySubLevelId = sensitivitySubLevelId;
    }
}
