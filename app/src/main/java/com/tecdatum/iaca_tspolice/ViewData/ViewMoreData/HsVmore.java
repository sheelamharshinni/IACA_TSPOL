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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
import com.tecdatum.iaca_tspolice.activity.MainActivity;

public class HsVmore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hs_vmore);


        TextView d_no = (TextView) findViewById(R.id.tv_hs_no);
        TextView d_type = (TextView) findViewById(R.id.tv_hs_type);
        TextView d_pn = (TextView) findViewById(R.id.tv_hs_pn);
        TextView d_an = (TextView) findViewById(R.id.tv_hs_an);
        TextView d_fn=(TextView) findViewById(R.id.tv_hs_fn);
        TextView d_dob = (TextView) findViewById(R.id.tv_hs_dob);
        TextView d_add=(TextView) findViewById(R.id.tv_hs_add);
        Button C_confm=(Button)findViewById(R.id.btn_hs_Confm) ;
        TextView tv_hs_psname = (TextView) findViewById(R.id.tv_hs_psname);
        TextView tv_lm_v_Lattitude = (TextView) findViewById(R.id.tv_lm_v_Lattitude);
        TextView tv_lm_v_Longitude = (TextView) findViewById(R.id.tv_lm_v_Longitude);
        TextView tv_EnteryDate = (TextView) findViewById(R.id.tv_hs_EnteryDate);
        TextView tv_CreatedBy = (TextView) findViewById(R.id.tv_hs_CreatedBy);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             finish();
            }
        });


        String Id = getIntent().getStringExtra("Id");
        String Number = getIntent().getStringExtra("Number");
        String Type = getIntent().getStringExtra("Type");
        String PSCode = getIntent().getStringExtra("PSCode");
        String PersonName = getIntent().getStringExtra("PersonName");
        String AliasName = getIntent().getStringExtra("AliasName");
        String FatherName = getIntent().getStringExtra("FatherName");
        String Age = getIntent().getStringExtra("Age");
        String Latitude = getIntent().getStringExtra("Latitude");
        String Longitude = getIntent().getStringExtra("Longitude");
        String Address = getIntent().getStringExtra("Address");

        String EnteryDate = getIntent().getStringExtra("EnteryDate");
        String CreatedBy = getIntent().getStringExtra("CreatedBy");
        String Psid = getIntent().getStringExtra("Psid");


        if (Number.equalsIgnoreCase("null")) {

        } else {         d_no.setText(""+Number);

        }
        if (Type.equalsIgnoreCase("null")) {

        } else {         d_type.setText(""+Type);

        }
        Log.e("PSCode","sp_type_id"+PSCode);

//        if (Psid.equalsIgnoreCase("null")) {
//
//        } else {
//            tv_hs_psname.setText(""+Psid);
//
//        }

        if (PersonName.equalsIgnoreCase("null")) {

        } else {         d_pn.setText(""+PersonName);

        }
        if (AliasName.equalsIgnoreCase("null")) {

        } else {         d_an .setText(""+AliasName);

        }
        if (FatherName.equalsIgnoreCase("null")) {

        } else {         d_fn.setText(""+FatherName);

        }
        if (Age.equalsIgnoreCase("null")) {

        } else {         d_dob.setText(""+Age);

        }
        if (Latitude.equalsIgnoreCase("null")) {

        } else {         tv_lm_v_Lattitude.setText(""+Latitude);

        }
        if (Longitude.equalsIgnoreCase("null")) {

        } else {         tv_lm_v_Longitude.setText(""+Longitude);

        }
        if (Address.equalsIgnoreCase("null")) {

        } else {         d_add.setText(""+Address);

        }
//        if (Psid.equalsIgnoreCase("null")) {
//
//        } else {
//        }
        if (EnteryDate.equalsIgnoreCase("null")) {

        } else {         tv_EnteryDate.setText(""+EnteryDate);

        }
        if (CreatedBy.equalsIgnoreCase("null")) {

        } else {         tv_CreatedBy.setText(""+CreatedBy);

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
