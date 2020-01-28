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

public class TrafficVmore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_vmore);

        TextView tv_lm_v_sv = (TextView) findViewById(R.id.tv_lm_v_sv);
        TextView tv_lm_v_vt = (TextView) findViewById(R.id.tv_lm_v_vt);
        TextView tv_lm_v_lm = (TextView) findViewById(R.id.tv_lm_v_lm);
        TextView tv_lm_v_add = (TextView) findViewById(R.id.tv_lm_v_add);
        TextView tv_lm_v_dis = (TextView) findViewById(R.id.tv_lm_v_dis);
        TextView tv_lm_v_pscode = (TextView) findViewById(R.id.tv_lm_v_pscode);
        TextView tv_lm_v_Lattitude = (TextView) findViewById(R.id.tv_lm_v_Lattitude);
        TextView tv_lm_v_Longitude = (TextView) findViewById(R.id.tv_lm_v_Longitude);
        Button C_confm = (Button) findViewById(R.id.btn_lm_v__Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        String Id = getIntent().getStringExtra("Id");
        String PSId = getIntent().getStringExtra("PSId");
        String PS = getIntent().getStringExtra("PS");
        String Type = getIntent().getStringExtra("Type");
        String Service = getIntent().getStringExtra("Service");
        String Location = getIntent().getStringExtra("Location");
        String Remarks = getIntent().getStringExtra("Remarks");
        String NoOfVehicles = getIntent().getStringExtra("NoOfVehicles");
        String Latitude = getIntent().getStringExtra("Latitude");
        String Longitude = getIntent().getStringExtra("Longitude");



        if (PS.equalsIgnoreCase("null")) {

        } else {   tv_lm_v_pscode.setText(""+PS);
        }

        if (Type.equalsIgnoreCase("null")) {

        } else {  tv_lm_v_vt.setText(""+Type);
        }
        if (Service.equalsIgnoreCase("null")) {

        } else {   tv_lm_v_sv.setText(""+Service);
        }
        if (Location.equalsIgnoreCase("null")) {

        } else { tv_lm_v_add.setText(""+Location);
        }
        if (Remarks.equalsIgnoreCase("null")) {

        } else {tv_lm_v_dis.setText(""+Remarks);
        }
        if (NoOfVehicles.equalsIgnoreCase("null")) {

        } else {    tv_lm_v_lm.setText(""+NoOfVehicles);
        }


        if (Latitude.equalsIgnoreCase("null")) {

        } else {tv_lm_v_Lattitude.setText(""+Latitude);
        }
        if (Longitude.equalsIgnoreCase("null")) {

        } else {      tv_lm_v_Longitude.setText(""+Longitude);
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
