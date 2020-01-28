package com.tecdatum.iaca_tspolice.activity.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {
    TextView c_time, tv_c_psname;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String formattedDate,s_Ps_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        c_time = (TextView) findViewById(R.id.tv_acci_time);
        tv_c_psname = (TextView) findViewById(R.id.tv_accic_psname);

        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {
            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c.getTime());
                c_time.setText(formattedDate);
            }

            public void onFinish() {
            }
        };
        newtimer.start();
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        s_Ps_name = bb.getString("Psname", "");
        tv_c_psname.setText(s_Ps_name);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
