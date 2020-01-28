package com.tecdatum.iaca_tspolice.ViewData.Helpers;

/**
 * Created by HI on 6/23/2018.
 */



/**
 * Created by HI on 4/18/2018.
 */

public class NenuSaitha_helper {



String PsID,PSName,CommunityGroup,SectorId,Locality,PatrolCarRegion,BlueColtRegion,TypeOfEstablishment,NameOfEstablishment,ContactPerson,ContactPersonNo,Latitude
        ,Longitude,Location,CCTVSpecifications,StorageprovidedinDays,CCTVWorkingStatus,PsConnectionID,VendorName,VendorCompany,VendorAddress,VendorContactNo,CCTVReasonID
        ,EnteredBy,cctvtype;

    public String getCctvtype() {
        return cctvtype;
    }

    public void setCctvtype(String cctvtype) {
        this.cctvtype = cctvtype;
    }

    public String getPsID() {
        return PsID;
    }

    public void setPsID(String psID) {
        PsID = psID;
    }

    public String getPSName() {
        return PSName;
    }

    public void setPSName(String PSName) {
        this.PSName = PSName;
    }

    public String getCommunityGroup() {
        return CommunityGroup;
    }

    public void setCommunityGroup(String communityGroup) {
        CommunityGroup = communityGroup;
    }

    public String getSectorId() {
        return SectorId;
    }

    public void setSectorId(String sectorId) {
        SectorId = sectorId;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getPatrolCarRegion() {
        return PatrolCarRegion;
    }

    public void setPatrolCarRegion(String patrolCarRegion) {
        PatrolCarRegion = patrolCarRegion;
    }

    public String getBlueColtRegion() {
        return BlueColtRegion;
    }

    public void setBlueColtRegion(String blueColtRegion) {
        BlueColtRegion = blueColtRegion;
    }

    public String getTypeOfEstablishment() {
        return TypeOfEstablishment;
    }

    public void setTypeOfEstablishment(String typeOfEstablishment) {
        TypeOfEstablishment = typeOfEstablishment;
    }

    public String getNameOfEstablishment() {
        return NameOfEstablishment;
    }

    public void setNameOfEstablishment(String nameOfEstablishment) {
        NameOfEstablishment = nameOfEstablishment;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getContactPersonNo() {
        return ContactPersonNo;
    }

    public void setContactPersonNo(String contactPersonNo) {
        ContactPersonNo = contactPersonNo;
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

    public String getCCTVSpecifications() {
        return CCTVSpecifications;
    }

    public void setCCTVSpecifications(String CCTVSpecifications) {
        this.CCTVSpecifications = CCTVSpecifications;
    }

    public String getStorageprovidedinDays() {
        return StorageprovidedinDays;
    }

    public void setStorageprovidedinDays(String storageprovidedinDays) {
        StorageprovidedinDays = storageprovidedinDays;
    }

    public String getCCTVWorkingStatus() {
        return CCTVWorkingStatus;
    }

    public void setCCTVWorkingStatus(String CCTVWorkingStatus) {
        this.CCTVWorkingStatus = CCTVWorkingStatus;
    }

    public String getPsConnectionID() {
        return PsConnectionID;
    }

    public void setPsConnectionID(String psConnectionID) {
        PsConnectionID = psConnectionID;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getVendorCompany() {
        return VendorCompany;
    }

    public void setVendorCompany(String vendorCompany) {
        VendorCompany = vendorCompany;
    }

    public String getVendorAddress() {
        return VendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        VendorAddress = vendorAddress;
    }

    public String getVendorContactNo() {
        return VendorContactNo;
    }

    public void setVendorContactNo(String vendorContactNo) {
        VendorContactNo = vendorContactNo;
    }

    public String getCCTVReasonID() {
        return CCTVReasonID;
    }

    public void setCCTVReasonID(String CCTVReasonID) {
        this.CCTVReasonID = CCTVReasonID;
    }

    public String getEnteredBy() {
        return EnteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        EnteredBy = enteredBy;
    }

    public NenuSaitha_helper(String psID, String PSName, String communityGroup, String sectorId, String locality, String patrolCarRegion, String blueColtRegion, String typeOfEstablishment, String nameOfEstablishment, String contactPerson, String contactPersonNo, String latitude, String longitude, String location, String CCTVSpecifications, String storageprovidedinDays, String CCTVWorkingStatus, String psConnectionID, String vendorName, String vendorCompany, String vendorAddress, String vendorContactNo, String CCTVReasonID, String enteredBy, String cctvtype) {
        PsID = psID;
        this.PSName = PSName;
        CommunityGroup = communityGroup;
        SectorId = sectorId;
        Locality = locality;
        PatrolCarRegion = patrolCarRegion;
        BlueColtRegion = blueColtRegion;
        TypeOfEstablishment = typeOfEstablishment;
        NameOfEstablishment = nameOfEstablishment;
        ContactPerson = contactPerson;
        ContactPersonNo = contactPersonNo;
        Latitude = latitude;
        Longitude = longitude;
        Location = location;
        this.CCTVSpecifications = CCTVSpecifications;
        StorageprovidedinDays = storageprovidedinDays;
        this.CCTVWorkingStatus = CCTVWorkingStatus;
        PsConnectionID = psConnectionID;
        VendorName = vendorName;
        VendorCompany = vendorCompany;
        VendorAddress = vendorAddress;
        VendorContactNo = vendorContactNo;
        this.CCTVReasonID = CCTVReasonID;
        EnteredBy = enteredBy;
        this.cctvtype = cctvtype;
    }
}