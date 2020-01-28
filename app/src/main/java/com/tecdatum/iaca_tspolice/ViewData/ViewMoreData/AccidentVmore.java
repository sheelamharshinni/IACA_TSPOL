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

public class AccidentVmore extends AppCompatActivity {

    TextView tv_DateOfOffence, tv_VictimCategoryID, tv_VictimAlcoholicORNot, tv_VictimVehicleNo, tv_AccusedCategoryID,
            tv_AccusedAlcoholicORNot, tv_AccusedVehicleNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_vmore);

        TextView pscode = (TextView) findViewById(R.id.tv_acc_pscode);
        TextView d_accno = (TextView) findViewById(R.id.tv_acc_accno);
        TextView d_cno = (TextView) findViewById(R.id.tv_acc_cno);
        TextView d_at = (TextView) findViewById(R.id.tv_acc_at);
        TextView d_dt = (TextView) findViewById(R.id.tv_acc_dt);
        TextView d_ad = (TextView) findViewById(R.id.tv_acc_add);
        TextView tv_acc_Latitude = (TextView) findViewById(R.id.tv_acc_Latitude);
        TextView tv_acc_Longitude = (TextView) findViewById(R.id.tv_acc_Longitude);

        TextView d_ds = (TextView) findViewById(R.id.tv_acc_ds);
        TextView d_dis = (TextView) findViewById(R.id.tv_acc_dis);
        TextView d_rt = (TextView) findViewById(R.id.tv_acc_rt);
        TextView d_rn = (TextView) findViewById(R.id.tv_acc_rn);
        TextView d_noi = (TextView) findViewById(R.id.tv_acc_noi);
        TextView d_nod = (TextView) findViewById(R.id.tv_acc_nod);
        tv_DateOfOffence=(TextView)findViewById(R.id.tv_DateOfOffence);
        tv_VictimCategoryID=(TextView)findViewById(R.id.tv_VictimCategoryID);
        tv_VictimAlcoholicORNot=(TextView)findViewById(R.id.tv_VictimAlcoholicORNot);
        tv_VictimVehicleNo=(TextView)findViewById(R.id.tv_VictimVehicleNo);
        tv_AccusedCategoryID=(TextView)findViewById(R.id.tv_AccusedCategoryID);
        tv_AccusedAlcoholicORNot=(TextView)findViewById(R.id.tv_AccusedAlcoholicORNot);
        tv_AccusedVehicleNo=(TextView)findViewById(R.id.tv_AccusedVehicleNo);

        String  Id = getIntent().getStringExtra("Id");
        String  CrimeNumber = getIntent().getStringExtra("CrimeNumber");
        String  PSID = getIntent().getStringExtra("PSID");
        String  PSCode = getIntent().getStringExtra("PSCode");
        String  CrimeTypeMaster_Id = getIntent().getStringExtra("CrimeTypeMaster_Id");
        String  CrimeType = getIntent().getStringExtra("CrimeType");
        String  CrimeSubtypeMaster_Id = getIntent().getStringExtra("CrimeSubtypeMaster_Id");
        String  CrimeSubtype = getIntent().getStringExtra("CrimeSubtype");
        String  Latitude = getIntent().getStringExtra("Latitude");
        String  Longitude = getIntent().getStringExtra("Longitude");
        String  Location = getIntent().getStringExtra("Location");
        String  Descr = getIntent().getStringExtra("Descr");
        String  DateOfOffence = getIntent().getStringExtra("DateOfOffence");
        String  DateOfEntry = getIntent().getStringExtra("DateOfEntry");
        String  CrimeStatusMaster_Id = getIntent().getStringExtra("CrimeStatusMaster_Id");
        String  DateofReport = getIntent().getStringExtra("DateofReport");
        String  NoofInjuries = getIntent().getStringExtra("NoofInjuries");
        String  NoofDeaths = getIntent().getStringExtra("NoofDeaths");
        String  RoadTypeID = getIntent().getStringExtra("RoadTypeID");
        String  RoadNumber = getIntent().getStringExtra("RoadNumber");
        String  VictimCategoryID = getIntent().getStringExtra("VictimCategoryID");
        String  VictimAlcoholicORNot = getIntent().getStringExtra("VictimAlcoholicORNot");
        String  VictimVehicleNo = getIntent().getStringExtra("VictimVehicleNo");
        String  AccusedCategoryID = getIntent().getStringExtra("AccusedCategoryID");
        String  AccusedAlcoholicORNot = getIntent().getStringExtra("AccusedAlcoholicORNot");
        String  AccusedVehicleNo = getIntent().getStringExtra("AccusedVehicleNo");


        if (CrimeNumber.equalsIgnoreCase("null")) {

        } else {

            d_cno.setText(""+CrimeNumber);

        }


        if (PSCode.equalsIgnoreCase("null")) {

        } else {
            pscode.setText(""+PSCode);
        }

        if (CrimeType.equalsIgnoreCase("null")) {

        } else {
        }

        if (CrimeSubtype.equalsIgnoreCase("null")) {

        } else {
            d_at.setText(""+CrimeSubtype);
        }
        if (Latitude.equalsIgnoreCase("null")) {

        } else {tv_acc_Latitude.setText(""+Latitude);
        }
        if (Longitude.equalsIgnoreCase("null")) {

        } else {tv_acc_Longitude.setText(""+Longitude);
        }
        if (Location.equalsIgnoreCase("null")) {

        } else {
            d_ad.setText(""+Location);
        }
        if (Descr.equalsIgnoreCase("null")) {

        } else {
            d_dis.setText(""+Descr);

        }
        if (DateOfOffence.equalsIgnoreCase("null")) {

        } else {tv_DateOfOffence.setText(""+DateOfOffence);
        }
        if (DateOfEntry.equalsIgnoreCase("null")) {

        } else {d_dt.setText(""+DateOfEntry);
        }
        if (CrimeStatusMaster_Id.equalsIgnoreCase("null")) {

        } else {
            d_ds.setText(""+CrimeStatusMaster_Id);
        }
        if (DateofReport.equalsIgnoreCase("null")) {

        } else {
        }
        if (NoofInjuries.equalsIgnoreCase("null")) {

        } else {
            d_noi.setText(""+NoofInjuries);
        }
        if (NoofDeaths.equalsIgnoreCase("null")) {

        } else {
            d_nod.setText(""+NoofDeaths);
        }


        if (RoadTypeID.equalsIgnoreCase("null")) {

        } else {
            d_rt.setText(""+RoadTypeID);
        }
        if (RoadNumber.equalsIgnoreCase("null")) {

        } else {
            d_rn.setText(""+RoadNumber);
        }

        if (VictimCategoryID.equalsIgnoreCase("null")) {

        } else {  tv_VictimCategoryID.setText(""+VictimCategoryID);
        }
        if (VictimAlcoholicORNot.equalsIgnoreCase("null")) {

        } else {        tv_VictimAlcoholicORNot.setText(""+VictimAlcoholicORNot);
        }
        if (VictimVehicleNo.equalsIgnoreCase("null")) {

        } else { tv_VictimVehicleNo.setText(""+VictimVehicleNo);
        }
        if (AccusedCategoryID.equalsIgnoreCase("null")) {

        } else {  tv_AccusedCategoryID.setText(""+AccusedCategoryID);
        }
        if (AccusedAlcoholicORNot.equalsIgnoreCase("null")) {

        } else {tv_AccusedAlcoholicORNot.setText(""+AccusedAlcoholicORNot);
        }
        if (AccusedVehicleNo.equalsIgnoreCase("null")) {

        } else {tv_AccusedVehicleNo.setText(""+AccusedVehicleNo);
        }



        Button C_confm = (Button) findViewById(R.id.btn_acci_Confm);
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
    }

}
