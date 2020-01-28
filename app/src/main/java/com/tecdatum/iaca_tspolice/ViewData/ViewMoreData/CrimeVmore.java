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

public class CrimeVmore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_vmore);

        TextView d_cno = (TextView) findViewById(R.id.tv_c_cno);
        TextView d_ct = (TextView) findViewById(R.id.tv_c_ct);
        TextView d_sct = (TextView) findViewById(R.id.tv_c_sct);
        TextView d_dt = (TextView) findViewById(R.id.tv_c_dt);
        TextView d_DateOfOffence = (TextView) findViewById(R.id.tv_c_DateOfOffence);

        TextView d_ad = (TextView) findViewById(R.id.tv_c_add);
        TextView d_ds = (TextView) findViewById(R.id.tv_c_ds);
        TextView d_dis = (TextView) findViewById(R.id.tv_c_dis);
        TextView pscode = (TextView) findViewById(R.id.tv_c_pscode);
        TextView tv_c_Latitude = (TextView) findViewById(R.id.tv_c_Latitude);
        TextView tv_c_Longitude = (TextView) findViewById(R.id.tv_c_Longitude);


        Button C_confm = (Button) findViewById(R.id.btn_C_Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });




        String Id = getIntent().getStringExtra("Id");
        String CrimeNumber = getIntent().getStringExtra("CrimeNumber");
        String PSID = getIntent().getStringExtra("PSID");
        String PSCode = getIntent().getStringExtra("PSCode");
        String CrimeTypeMaster_Id = getIntent().getStringExtra("CrimeTypeMaster_Id");
        String CrimeType = getIntent().getStringExtra("CrimeType");
        String CrimeSubtype = getIntent().getStringExtra("CrimeSubtype");
        String Latitude = getIntent().getStringExtra("Latitude");
        String Longitude = getIntent().getStringExtra("Longitude");
        String Location = getIntent().getStringExtra("Location");
        String Descr = getIntent().getStringExtra("Descr");
        String DateOfOffence = getIntent().getStringExtra("DateOfOffence");
        String DateOfEntry = getIntent().getStringExtra("DateOfEntry");
        String CrimeStatusMaster_Id = getIntent().getStringExtra("CrimeStatusMaster_Id");




        if (CrimeNumber.equalsIgnoreCase("null")) {

        } else { d_cno.setText(""+CrimeNumber);
        }

        if (PSCode.equalsIgnoreCase("null")) {

        } else {        pscode .setText(""+PSCode);

        }

        if (CrimeType.equalsIgnoreCase("null")) {

        } else {d_ct.setText(""+CrimeType);
        }
        if (CrimeSubtype.equalsIgnoreCase("null")) {

        } else { d_sct.setText(""+CrimeSubtype);
        }
        if (Latitude.equalsIgnoreCase("null")) {

        } else {        tv_c_Latitude .setText(""+Latitude);

        }
        if (Longitude.equalsIgnoreCase("null")) {

        } else {        tv_c_Longitude.setText(""+Longitude);

        }
        if (Location.equalsIgnoreCase("null")) {

        } else { d_ad .setText(""+Location);
        }
        if (Descr.equalsIgnoreCase("null")) {

        } else {        d_dis.setText(""+Descr);

        }
        if (DateOfOffence.equalsIgnoreCase("null")) {

        } else {        d_DateOfOffence .setText(""+DateOfOffence);

        }
        if (DateOfEntry.equalsIgnoreCase("null")) {

        } else {  d_dt.setText(""+DateOfEntry);
        }
        if (CrimeStatusMaster_Id.equalsIgnoreCase("null")) {

        } else { d_ds .setText(""+CrimeStatusMaster_Id);
        }


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
