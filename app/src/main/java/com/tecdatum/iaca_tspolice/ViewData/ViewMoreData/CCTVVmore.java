package com.tecdatum.iaca_tspolice.ViewData.ViewMoreData;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
import com.tecdatum.iaca_tspolice.activity.MainActivity;

public class CCTVVmore extends AppCompatActivity {
    TextView tv_ID,
            tv_CommunityGroupMasterID,
            tv_CommunityGroup,
            tv_CCTVTypemasterID,
            tv_CCTVType,
            tv_LandMark,
            tv_Location,
            tv_Latitude,
            tv_Longitude,
            tv_PsID,
            tv_Policestation,
            tv_SectorNo,
            tv_CCTVWorkingStatusID,
            tv_CCTVReasonID,
            tv_CCTVVendorID,
            tv_PsConnectionID,
            tv_IPAddress,
            tv_NVRIPAddress,
            tv_ChannelNo,
            tv_PersonName,
            tv_MobileNo,
            tv_Remarks,
            tv_EnteredDate,
            tv_EnteredBy,
            tv_IsActive,
            tv_CCTVCategoryID,
            tv_PoleNo,
            tv_CameraCapacityMasterID,
            tv_CameraSpecificationMasterID,
            tv_Locality,
            tv_GovernmentSectorNo, tv_VendorAddress, tv_VendorNo, tv_VendorName, tv_CameraFixedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctvvmore);


        tv_CommunityGroup = (TextView) findViewById(R.id.tv_CommunityGroup);
        tv_CCTVType = (TextView) findViewById(R.id.tv_CCTVType);
        tv_LandMark = (TextView) findViewById(R.id.tv_LandMark);
        tv_Location = (TextView) findViewById(R.id.tv_Location);
        tv_Latitude = (TextView) findViewById(R.id.tv_Latitude);
        tv_Longitude = (TextView) findViewById(R.id.tv_Longitude);

        tv_Policestation = (TextView) findViewById(R.id.tv_psname);
        tv_SectorNo = (TextView) findViewById(R.id.tv_SectorNo);
        tv_CCTVWorkingStatusID = (TextView) findViewById(R.id.tv_CCTVWorkingStatusID);
        tv_CCTVReasonID = (TextView) findViewById(R.id.tv_CCTVReasonID);
        tv_CCTVVendorID = (TextView) findViewById(R.id.tv_CCTVVendorID);
        tv_PsConnectionID = (TextView) findViewById(R.id.tv_PsConnectionID);
        tv_IPAddress = (TextView) findViewById(R.id.tv_IPAddress);
        tv_NVRIPAddress = (TextView) findViewById(R.id.tv_NVRIPAddress);
        tv_ChannelNo = (TextView) findViewById(R.id.tv_ChannelNo);
        tv_PersonName = (TextView) findViewById(R.id.tv_PersonName);
        tv_MobileNo = (TextView) findViewById(R.id.tv_MobileNo);
        tv_Remarks = (TextView) findViewById(R.id.tv_Remarks);
        tv_EnteredDate = (TextView) findViewById(R.id.tv_EnteredDate);
        tv_EnteredBy = (TextView) findViewById(R.id.tv_EnteredBy);
        tv_IsActive = (TextView) findViewById(R.id.tv_IsActive);
        tv_CCTVCategoryID = (TextView) findViewById(R.id.tv_CCTVCategoryID);
        tv_PoleNo = (TextView) findViewById(R.id.tv_PoleNo);
        tv_CameraCapacityMasterID = (TextView) findViewById(R.id.tv_CameraCapacityMasterID);
        tv_CameraSpecificationMasterID = (TextView) findViewById(R.id.tv_CameraSpecificationMasterID);
        tv_Locality = (TextView) findViewById(R.id.tv_Locality);
        tv_GovernmentSectorNo = (TextView) findViewById(R.id.tv_GovernmentSectorNo);
        tv_VendorName = (TextView) findViewById(R.id.tv_VendorName);
        tv_VendorNo = (TextView) findViewById(R.id.tv_VendorNo);
        tv_VendorAddress = (TextView) findViewById(R.id.tv_VendorAddress);
        tv_CameraFixedDate = (TextView) findViewById(R.id.tv_CameraFixedDate);


        TextView tv_cctv_Communityname = (TextView) findViewById(R.id.tv_cctv_Communityname);
        TextView tv_cctv_NoOfCamerasCoverd = (TextView) findViewById(R.id.tv_cctv_NoOfCamerasCoverd);
        TextView tv_cctv_NoCameraInstalled = (TextView) findViewById(R.id.tv_cctv_NoCameraInstalled);
        TextView tv_cctv_StorageinDays = (TextView) findViewById(R.id.tv_cctv_StorageinDays);





        Button C_confm = (Button) findViewById(R.id.btn_cctv_Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(),Dashboard.class);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                finish();
            }
        });


        String ID = getIntent().getStringExtra("ID");
        String CommunityGroupMasterID = getIntent().getStringExtra("CommunityGroupMasterID");
        String CommunityGroup = getIntent().getStringExtra("CommunityGroup");
        String CCTVTypemasterID = getIntent().getStringExtra("CCTVTypemasterID");
        String CCTVType = getIntent().getStringExtra("CCTVType");
        String LandMark = getIntent().getStringExtra("LandMark");
        String Policestation = getIntent().getStringExtra("Policestation");
        String PSCode = getIntent().getStringExtra("PSCode");
        String SectorNo = getIntent().getStringExtra("SectorNo");
        String CCTVWorkingStatusID = getIntent().getStringExtra("CCTVWorkingStatusID");
        String CCTVReasonID = getIntent().getStringExtra("CCTVReasonID");
        String CCTVVendorID = getIntent().getStringExtra("CCTVVendorID");
        String Latitude = getIntent().getStringExtra("Latitude");
        String Longitude = getIntent().getStringExtra("Longitude");
        String Location = getIntent().getStringExtra("Location");
        String PsID = getIntent().getStringExtra("PsID");
        String PsConnectionID = getIntent().getStringExtra("PsConnectionID");
        String IPAddress = getIntent().getStringExtra("IPAddress");
        String NVRIPAddress = getIntent().getStringExtra("NVRIPAddress");
        String ChannelNo = getIntent().getStringExtra("ChannelNo");
        String Remarks = getIntent().getStringExtra("Remarks");
        String MobileNo = getIntent().getStringExtra("MobileNo");
        String PersonName = getIntent().getStringExtra("PersonName");
        String EnteredDate = getIntent().getStringExtra("EnteredDate");
        String EnteredBy = getIntent().getStringExtra("EnteredBy");
        String IsActive = getIntent().getStringExtra("IsActive");
        String CCTVCategoryID = getIntent().getStringExtra("CCTVCategoryID");
        String PoleNo = getIntent().getStringExtra("PoleNo");
        String CameraCapacityMasterID = getIntent().getStringExtra("CameraCapacityMasterID");
        String CameraSpecificationMasterID = getIntent().getStringExtra("CameraSpecificationMasterID");
        String Locality = getIntent().getStringExtra("Locality");
        String GovernmentSectorNo = getIntent().getStringExtra("GovernmentSectorNo");



        String CommunityName = getIntent().getStringExtra("CommunityName");
        String NoOfCamerasCoverd = getIntent().getStringExtra("NoOfCamerasCoverd");
        String NoCameraInstalled = getIntent().getStringExtra("NoCameraInstalled");
        String StorageinDays = getIntent().getStringExtra("StorageinDays");

        String Vname = getIntent().getStringExtra("vname");
        String Vno = getIntent().getStringExtra("vno");
        String Vadd = getIntent().getStringExtra("vadd");
        String CameraFixedDate = getIntent().getStringExtra("CameraFixedDate");
        if (CommunityName.equalsIgnoreCase("null")) {

        } else {
            tv_cctv_Communityname.setText("" + CommunityName);
        }
        if (NoOfCamerasCoverd.equalsIgnoreCase("null")) {

        } else {
            tv_cctv_NoOfCamerasCoverd.setText("" + NoOfCamerasCoverd);
        }
        if (NoCameraInstalled.equalsIgnoreCase("null")) {

        } else {
            tv_cctv_NoCameraInstalled.setText("" + NoCameraInstalled);
        }
        if (StorageinDays.equalsIgnoreCase("null")) {

        } else {
            tv_cctv_StorageinDays.setText("" + StorageinDays);
        }

        if (Vadd.equalsIgnoreCase("null")) {

        } else {
            tv_VendorAddress.setText("" + Vadd);
        }
        if (Vno.equalsIgnoreCase("null")) {

        } else {
            tv_VendorNo.setText("" + Vno);
        }
        if (Vname.equalsIgnoreCase("null")) {

        } else {
            tv_VendorName.setText("" + Vname);
        }
        if (CommunityGroup.equalsIgnoreCase("null")) {

        } else {
            tv_CommunityGroup.setText("" + CommunityGroup);
        }

        if (CCTVType.equalsIgnoreCase("null")) {

        } else {
            tv_CCTVType.setText("" + CCTVType);
        }
        if (LandMark.equalsIgnoreCase("null")) {

        } else {
            tv_LandMark.setText("" + LandMark);
        }
        if (Policestation.equalsIgnoreCase("null")) {

        } else {
            tv_Policestation.setText("" + Policestation);
        }


        if (SectorNo.equalsIgnoreCase("null")) {

        } else {
            tv_SectorNo.setText("" + SectorNo);
        }
        if (CCTVWorkingStatusID.equalsIgnoreCase("null")) {

        } else {
            tv_CCTVWorkingStatusID.setText("" + CCTVWorkingStatusID);
        }
        if (CCTVReasonID.equalsIgnoreCase("null")) {

        } else {
            tv_CCTVReasonID.setText("" + CCTVReasonID);
        }
        if (CCTVVendorID.equalsIgnoreCase("null")) {

        } else {
            tv_CCTVVendorID.setText("" + CCTVVendorID);
        }
        if (Latitude.equalsIgnoreCase("null")) {

        } else {
            tv_Latitude.setText("" + Latitude);
        }
        if (Longitude.equalsIgnoreCase("null")) {

        } else {
            tv_Longitude.setText("" + Longitude);

        }
        if (Location.equalsIgnoreCase("null")) {

        } else {
            tv_Location.setText("" + Location);

        }

        if (PsConnectionID.equalsIgnoreCase("null")) {

        } else {
            tv_PsConnectionID.setText("" + PsConnectionID);
        }
        if (IPAddress.equalsIgnoreCase("null")) {

        } else {
            tv_IPAddress.setText("" + IPAddress);
        }
        if (NVRIPAddress.equalsIgnoreCase("null")) {

        } else {
            tv_NVRIPAddress.setText("" + NVRIPAddress);
        }
        if (ChannelNo.equalsIgnoreCase("null")) {

        } else {
            tv_ChannelNo.setText("" + ChannelNo);
        }
        if (MobileNo.equalsIgnoreCase("null")) {

        } else {
            tv_MobileNo.setText("" + MobileNo);
        }
        if (Remarks.equalsIgnoreCase("null")) {

        } else {
            tv_Remarks.setText("" + Remarks);
        }
        if (PersonName.equalsIgnoreCase("null")) {

        } else {
            tv_PersonName.setText("" + PersonName);
        }
        if (EnteredDate.equalsIgnoreCase("null")) {

        } else {
            tv_EnteredDate.setText("" + EnteredDate);
        }
        if (EnteredBy.equalsIgnoreCase("null")) {

        } else {
            tv_EnteredBy.setText("" + EnteredBy);
        }
        if (IsActive.equalsIgnoreCase("null")) {

        } else {
            tv_IsActive.setText("" + IsActive);
        }
        if (CCTVCategoryID.equalsIgnoreCase("null")) {

        } else {
            tv_CCTVCategoryID.setText("" + CCTVCategoryID);
        }
        if (PoleNo.equalsIgnoreCase("null")) {

        } else {
            tv_PoleNo.setText("" + PoleNo);
        }
        if (CameraCapacityMasterID.equalsIgnoreCase("null")) {

        } else {
            tv_CameraCapacityMasterID.setText("" + CameraCapacityMasterID);
        }
        if (CameraSpecificationMasterID.equalsIgnoreCase("null")) {

        } else {
            tv_CameraSpecificationMasterID.setText("" + CameraSpecificationMasterID);
        }
        if (Locality.equalsIgnoreCase("null")) {

        } else {
            tv_Locality.setText("" + Locality);
        }
        if (GovernmentSectorNo.equalsIgnoreCase("null")) {

        } else {
            tv_GovernmentSectorNo.setText("" + GovernmentSectorNo);
        }

        if (CameraFixedDate.equalsIgnoreCase("null")) {

        } else {
            tv_CameraFixedDate.setText("" + CameraFixedDate);
        }
    }

}
