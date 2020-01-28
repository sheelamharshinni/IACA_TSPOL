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

public class NenuSaithamCCtvMore extends AppCompatActivity {

    String PsID,PSName,CommunityGroup,SectorId,Locality,PatrolCarRegion,BlueColtRegion,TypeOfEstablishment,NameOfEstablishment,ContactPerson,ContactPersonNo,Latitude
            ,Longitude,Location,CCTVSpecifications,StorageprovidedinDays,CCTVWorkingStatus,PsConnectionID,VendorName,VendorCompany,VendorAddress,VendorContactNo,CCTVReasonID
            ,EnteredBy,cctvtype;
Button btn_cctv_Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nenusaithamcctv_more);

        TextView tv_cctv_psname = (TextView) findViewById(R.id.tv_cctv_psname);;
        TextView d_cg = (TextView) findViewById(R.id.tv_cctv_sno);;
        TextView d_ln = (TextView) findViewById(R.id.tv_cctv_locality);
        TextView tv_cctv_cctvtype = (TextView) findViewById(R.id.tv_cctv_cctvtype);

        TextView tv_cctv_cctvspecification = (TextView) findViewById(R.id.tv_cctv_cctvspecification);
        TextView tv_cctv_cctvEstablishmentType = (TextView) findViewById(R.id.tv_cctv_cctvEstablishmentType);
        TextView tv_cctv_cctvEstablishmentName = (TextView) findViewById(R.id.tv_cctv_cctvEstablishmentName);

        TextView tv_cctv_cctvinchargenam = (TextView) findViewById(R.id.tv_cctv_cctvinchargenam);
        TextView tv_cctv_cctvinchargeno = (TextView) findViewById(R.id.tv_cctv_cctvinchargeno);


        TextView tv_cctv_cctvVendorAddress = (TextView) findViewById(R.id.tv_cctv_cctvVendorAddress);
        TextView tv_cctv_cctvVendorCompany = (TextView) findViewById(R.id.tv_cctv_cctvVendorCompany);
        TextView tv_cctv_cctvVendornam = (TextView) findViewById(R.id.tv_cctv_cctvVendornam);
        TextView tv_cctv_cctvVendorno = (TextView) findViewById(R.id.tv_cctv_cctvVendorno);

        TextView tv_cctv_cctvCamerasInstalled = (TextView) findViewById(R.id.tv_cctv_cctvCamerasInstalled);
        TextView tv_cctv_cctvStorageProvided = (TextView) findViewById(R.id.tv_cctv_cctvStorageProvided);
        TextView tv_cctv_cctvLocation = (TextView) findViewById(R.id.tv_cctv_cctvLocation);





        btn_cctv_Cancel= (Button) findViewById(R.id.btn_cctv_Cancel);
        btn_cctv_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        EnteredBy = getIntent().getStringExtra("EnteredBy");
         PSName = getIntent().getStringExtra("PSName");
         CommunityGroup = getIntent().getStringExtra("CommunityGroup");
        SectorId = getIntent().getStringExtra("SectorId");
        Locality = getIntent().getStringExtra("Locality");
        TypeOfEstablishment = getIntent().getStringExtra("TypeOfEstablishment");
        NameOfEstablishment = getIntent().getStringExtra("NameOfEstablishment");
        ContactPerson = getIntent().getStringExtra("ContactPerson");
        ContactPersonNo = getIntent().getStringExtra("ContactPersonNo");
        Latitude = getIntent().getStringExtra("Latitude");
        Longitude = getIntent().getStringExtra("Longitude");
        CCTVSpecifications = getIntent().getStringExtra("CCTVSpecifications");
        StorageprovidedinDays = getIntent().getStringExtra("StorageprovidedinDays");

        PatrolCarRegion = getIntent().getStringExtra("PatrolCarRegion");
        VendorName = getIntent().getStringExtra("VendorName");
        VendorCompany = getIntent().getStringExtra("VendorCompany");
        VendorContactNo = getIntent().getStringExtra("VendorContactNo");
        VendorAddress = getIntent().getStringExtra("VendorAddress");

        cctvtype = getIntent().getStringExtra("cctvtype");
        Location = getIntent().getStringExtra("Location");



         tv_cctv_psname.setText(""+PSName);
         d_cg.setText(""+SectorId);
         d_ln.setText(""+Locality);
         tv_cctv_cctvtype .setText(""+cctvtype);

         tv_cctv_cctvspecification .setText(""+CCTVSpecifications);
         tv_cctv_cctvEstablishmentType .setText(""+TypeOfEstablishment);
         tv_cctv_cctvEstablishmentName.setText(""+NameOfEstablishment);

         tv_cctv_cctvinchargenam.setText(""+ContactPerson);
         tv_cctv_cctvinchargeno.setText(""+ContactPersonNo);


         tv_cctv_cctvVendorAddress .setText(""+VendorAddress);
         tv_cctv_cctvVendorCompany .setText(""+VendorCompany);
         tv_cctv_cctvVendornam.setText(""+VendorName);
         tv_cctv_cctvVendorno .setText(""+VendorContactNo);

         tv_cctv_cctvCamerasInstalled.setText(""+PatrolCarRegion);
        if(StorageprovidedinDays!=null){
            if(StorageprovidedinDays.equalsIgnoreCase("null")){}else {
                tv_cctv_cctvStorageProvided .setText(""+StorageprovidedinDays);

            }
        }else {}

         tv_cctv_cctvLocation.setText(""+Location);
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
    }



}
