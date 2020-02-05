package com.tecdatum.iaca_tspolice.Constants;

/**
 * Created by HI on 4/11/2018.
 */

public class URLS {

    public static final String Str_master = "http://tecdatum.net/TSIACAApi/api/Master/";
    public static final String Str1 = "http://tecdatum.net/TSIACAApi/api/DataEntry/";

//    public static final String Str_master = "http://policeapps.in/TSIACADemo/api/Master/";
//    public static final String Str1 = "http://policeapps.in/TSIACADemo/api/DataEntry/";
//


    public static final String LoginCheck = Str1 + "LoginCheck";

    public static final String CrimeMasters = Str_master + "CrimeMasters";
    public static final String AccidentMasters = Str_master + "AccidentMasters";
    public static final String LandmarkMasters = Str_master + "LandmarkMasters";
    public static final String cctvMasters = Str_master + "cctvMasters";
    public static final String TrafficMasters = Str_master + "TrafficMasters";

    //3-waterlogin type 4- waterlogin Subtype

    //    http://tecdatum.net/TSIACAApi/api/DataEntry/IACADashboard
//
//    Nagendhar Reddy:2:30:12 PM
//    {
//        "HirarchyID":"57"
//
//    }

    public static final String IACADashboard = Str1 + "IACADashboard";

    public static final String HistoryEntry = Str1 + "HistoryEntry";
    public static final String HistoryList = Str1 + "HistoryList";
    public static final String HistorySheetListWithFilter = Str1 + "HistorySheetListWithFilter";

    public static final String WaterLoggingList = Str1 + "WaterLoggingList";
    public static final String WaterLoggingsWithFilter = Str1 + "WaterLoggingsWithFilter";
    public static final String TrafficList = Str1 + "TrafficList";

    public static final String inserttraffic = Str1 + "inserttraffic";
    public static final String TrafficListWithFilter = Str1 + "TrafficListWithFilter";

    public static final String cctvEntry = Str1 + "cctvEntry";
    public static final String cctvList = Str1 + "cctvList";
    public static final String cctvListWithFilter = Str1 + "cctvListWithFilter";
    public static final String NenuSaithamCctvEntry = Str1 + "NenuSaithamCctvEntry";
    public static final String NenuSaithamcctvList = Str1 + "NenuSaithamcctvList";

    public static final String GetAccidentRecordsData = Str1 + "AccidentList";
    public static final String AddAccident = Str1 + "AccidentEntry";
    public static final String AccidentListWithFilter = Str1 + "AccidentListWithFilter";

    public static final String GetCrimeRecordsData = Str1 + "CrimeList";
    public static final String AddCrime = Str1 + "CrimeEntry";
    public static final String CrimeListWithFilter = Str1 + "CrimeListWithFilter";

    public static final String AddHistoryData = Str1 + "AddHistoryData";
    public static final String GetHistorySheetRecordsData = Str1 + "GetHistorySheetRecordsData";

    public static final String LandmarkEntry = Str1 + "LandmarkEntry";
    public static final String LandmarkList = Str1 + "LandmarkList";
    public static final String LandmarkListWithFilter = Str1 + "LandmarkListWithFilter";
    public static final String LandmarkListwithImages = Str1 + "LandmarkListwithImages";


    public static final String getcrimebyid = Str1 + "getcrimebyid";
    public static final String updatecrime = Str1 + "updatecrime";
    public static final String crimeforupdate = Str1 + "crimeforupdate";


    public static final String HaltingPointsEntry = Str1 + "HaltingPointsEntry";
    public static final String HaltingList = Str1 + "HaltingList";
    public static final String updateHaltingPoints = Str1 + "updateHaltingPoints";
    public static final String HaltingPointUpdate = Str1 + "HaltingPointUpdate"; //for edit

    public static final String CommunityEntry = Str1 + "CommunityEntry"; //for edit

    //NewURLS:HArshini

    public static final String GetCctvCategory = "http://tecdatum.net/TSIACAApi/api/Master/GetCctvCategory";
    public static final String CCTVDataEntry = "http://tecdatum.net/TSIACAApi/api/DataEntry/CCTVDataEntry";
    public static final String GetRoadNumber = "http://tecdatum.net/TSIACAApi/api/Master/GetRoadNumber";

    public static final String GetRoadType = "http://tecdatum.net/TSIACAApi/api/Master/GetRoadType";
    public static final String RoadFeatures = "http://tecdatum.net/TSIACAApi/api/Master/RoadFeatures";
    public static final String GetCrimeVehicleManoeuvre = "http://tecdatum.net/TSIACAApi/api/Master/GetCrimeVehicleManoeuvre";

    public static final String GetTypeOfAccident = "http://tecdatum.net/TSIACAApi/api/Master/GetTypeOfAccident";
    public static final String GetAccidentSpot = "http://tecdatum.net/TSIACAApi/api/Master/GetAccidentSpot";
    public static final String AccidentDetails = "http://tecdatum.net/TSIACAApi/api/DataEntry/AccidentDetails";

    public static final String GetYear = "http://tecdatum.net/TSIACAApi/api/Master/GetYear";

    public static final String GetAccidentType = "http://tecdatum.net/TSIACAApi/api/Master/GetAccidentType";
    public static final String VerifyforAccidentDetails = "http://tecdatum.net/TSIACAApi/api/DataEntry/VerifyforAccidentDetails";

    public static final String GetVictimCategories = "http://tecdatum.net/TSIACAApi/api/Master/GetVictimCategories";
    public static final String GetAccusedCategory = "http://tecdatum.net/TSIACAApi/api/Master/GetAccusedCategory";

//    http://tecdatum.net/TSIACAApi/api/DataEntry/CommunityEntry
//
//    {
//        "CommunityName":"Maheshcommunity",
//            "PsID":"1843"
//    }






}