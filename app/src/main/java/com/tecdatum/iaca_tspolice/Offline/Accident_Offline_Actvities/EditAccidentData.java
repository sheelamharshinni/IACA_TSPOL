package com.tecdatum.iaca_tspolice.Offline.Accident_Offline_Actvities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.DataBase.DbHandler;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.MainActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditAccidentData extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    ArrayAdapter<Samplemyclass> adapter_at;
    ArrayList<Samplemyclass> cl3, cl4, d1, d2, d3, d4, d5, d6;
    ArrayAdapter<Samplemyclass> adapter_state;
    private static final String TAG = EditAccidentData.class.getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    CustomDateTimePicker custom1;
    String Sat, Srt, Ssvc, Ssvs, Ssac, Ssas;
    private Spinner at, rt, svc, svs, sac, sas, detected_;
    String Sat1, Srt1, Svc1, Svs1, Sac1, Sas1;
    RelativeLayout RL1, RL2;
    int Code;
    EditText et_discription, et_roadNo, et_vap, et_aap, et_locality, et_Cno, et_Cla, et_Noi, et_Nod;
    Button Bt_c_rest, Bt_c_submit, Bt_c_vl;
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    String S_discription, S_roadNo, S_vap, S_aap, S_id, S_locality, S_Cno, S_Cla, S_Noi, S_Nod, S_latitude, S_longitude, S_dateTime, Message,
            s_Ps_name, S_Uname, S_Pass, S_psid, s_role, S_IMEi, S_detected_;
    Button btn_accid_offline, Bt_acc_date;
    TextView c_time, tv_c_psname;
    String formattedDate, e_id, e_n;
    Dialog C_dialog, vdialog;
    Integer Code1;
    String Message1;
    private LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x01;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 50;
    private static final int READ_CAMERA_PERMISSIONS_REQUEST = 20;
    private static final int WRITE_SETTINGS_PERMISSION = 20;
    private boolean active = false;
    protected android.location.Location mLastLocation;
    private static int UPDATE_INTERVAL = 60000;
    private boolean mRequestingLocationUpdates = true;
    String AccidentENo, Cdate, Cnumber, Pscode, Type, Subtype, Location, EnteredDate, CStatus, RoadType, Descr, Road_Number, NOofIn, NOofD;
    ArrayList<String> AccidentENo_arraylist_ = new ArrayList<String>();
    ArrayList<String> Cnumber_arraylist_ = new ArrayList<String>();
    ArrayList<String> Pscode_arraylist_ = new ArrayList<String>();
    ArrayList<String> Cdate_arraylist_ = new ArrayList<String>();
    ArrayList<String> Type_arraylist_ = new ArrayList<String>();
    ArrayList<String> Location_arraylist_ = new ArrayList<String>();
    ArrayList<String> EnteredDate_arraylist_ = new ArrayList<String>();
    ArrayList<String> CStatus_arraylist_ = new ArrayList<String>();
    ArrayList<String> RoadType_arraylist_ = new ArrayList<String>();
    ArrayList<String> Descr_arraylist_ = new ArrayList<String>();
    ArrayList<String> Road_Number_arraylist_ = new ArrayList<String>();
    ArrayList<String> NOofIn_arraylist_ = new ArrayList<String>();
    ArrayList<String> NOofD_arraylist_ = new ArrayList<String>();
    ArrayList<String> type_list = new ArrayList<String>();


    // for Db
    DbHandler dbHandler;
    SQLiteDatabase database;
    SQLiteStatement statement;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_accident_data);

        RL1 = (RelativeLayout) findViewById(R.id.Layout9);
        RL2 = (RelativeLayout) findViewById(R.id.Layout11);

        at = (Spinner) findViewById(R.id.sp_accidentType);
        rt = (Spinner) findViewById(R.id.sp_roadType);
        svc = (Spinner) findViewById(R.id.sp_vc);
        svs = (Spinner) findViewById(R.id.sp_vs);
        sac = (Spinner) findViewById(R.id.sp_ac);
        sas = (Spinner) findViewById(R.id.sp_as);
        detected_ = (Spinner) findViewById(R.id.sp_detected);

        et_discription = (EditText) findViewById(R.id.et_Description);
        et_locality = (EditText) findViewById(R.id.et_Locality);
        et_roadNo = (EditText) findViewById(R.id.et_RoadNO);
        et_vap = (EditText) findViewById(R.id.VictAlocoPer);
        et_aap = (EditText) findViewById(R.id.AccusedAlocoPer);
        et_Cla = (EditText) findViewById(R.id.et_cla);
        et_Noi = (EditText) findViewById(R.id.et_noi);
        et_Nod = (EditText) findViewById(R.id.et_nod);
        et_Cno = (EditText) findViewById(R.id.et_crime_no);

        Bt_c_vl = (Button) findViewById(R.id.btn_accid_vl);
        Bt_c_rest = (Button) findViewById(R.id.btn_accid_reset);
        Bt_c_submit = (Button) findViewById(R.id.btn_accid_submit);
        btn_accid_offline = (Button) findViewById(R.id.btn_accid_offline);
        Bt_acc_date = (Button) findViewById(R.id.btn_acci_date);

        c_time = (TextView) findViewById(R.id.tv_acci_time);
        tv_c_psname = (TextView) findViewById(R.id.tv_accic_psname);


        et_Cno.addTextChangedListener(new TextWatcher() {
            int keyDel;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                et_Cno.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = et_Cno.getText().length();
                    if (len == 4) {
                        et_Cno.setText(et_Cno.getText() + "/");
                        et_Cno.setSelection(et_Cno.getText().length());
                        keyDel = 1;
//
//                        if (et_Cno.getText().toString().isEmpty()) {
//                            adapter_state = new ArrayAdapter<String>(getApplicationContext(),
//                                    R.layout.spinner_item, no_state);
//                        } else {
//                            adapter_state = new ArrayAdapter<String>(getApplicationContext(),
//                                    R.layout.spinner_item, state);
//                        }
//
//                        detected_.setAdapter(adapter_state);


                        setStatus();


                    }
                } else {
                    keyDel = 0;
                }

            }

            @Override
            public void afterTextChanged(Editable arg0) {


                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                // TODO Auto-generated method stub
            }
        });
        Bt_acc_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custom1 = new CustomDateTimePicker(EditAccidentData.this,
                        new CustomDateTimePicker.ICustomDateTimeListener() {
                            @Override
                            public void onSet(Dialog dialog, Calendar calendarSelected,
                                              Date dateSelected, int year, String monthFullName,
                                              String monthShortName, int monthNumber, int date,
                                              String weekDayFullName, String weekDayShortName,
                                              int hour24, int hour14, int min, int sec,
                                              String AM_PM) {
                                Bt_acc_date.setText("");
                                Bt_acc_date.setText(year
                                        + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                        + " " + hour24 + ":" + min
                                        + ":" + sec);

                                S_dateTime = Bt_acc_date.getText().toString();
                                Log.e("S_dateTime", "" + S_dateTime);
                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                custom1.set24HourFormat(true);
                custom1.setDate(Calendar.getInstance());
                custom1.showDialog();
            }
        });


        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        s_Ps_name = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        tv_c_psname.setText(s_Ps_name);

        String s_OrgName = bb.getString("OrgName", "");
        TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        tv_OrgName.setText("" + s_OrgName);

        btn_accid_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AccidentOfflineList.class);
                startActivity(i);
            }
        });
        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckConnectivity1();
            }
        });
        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_discription.setText("");
                et_roadNo.setText("");
                et_vap.setText("");
                et_aap.setText("");
                et_Cno.setText("");
                et_Cla.setText("");
                et_Noi.setText("");
                et_Nod.setText("");
                Bt_acc_date.setText("Accident Date and Time");
                at.setSelection(0);
                rt.setSelection(0);
                svc.setSelection(0);
                svs.setSelection(0);
                sac.setSelection(0);
                sas.setSelection(0);
                detected_.setSelection(0);
                // Bt_c_date.setText("Select Date and time");
            }
        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                S_discription = et_discription.getText().toString();
                S_locality = et_locality.getText().toString();
                S_roadNo = et_roadNo.getText().toString();

                S_vap = et_vap.getText().toString();
                S_aap = et_aap.getText().toString();

                S_Cla = et_Cla.getText().toString();
                S_Noi = et_Noi.getText().toString();
                S_Nod = et_Nod.getText().toString();
                S_Cno = et_Cno.getText().toString();


                if (at != null && at.getSelectedItem() != null) {
                    if (!(at.getSelectedItem().toString().trim() == "Select Accident Type")) {

                        if (!(Bt_acc_date.getText().toString().trim().equalsIgnoreCase("Accident Date and Time"))) {
                            if (!(detected_.getSelectedItem().toString().trim() == "Select Status")) {
                                if (!(et_locality.getText().toString().trim().isEmpty())) {
                                    if (!(et_discription.getText().toString().trim().isEmpty())) {
                                        alertDialogue_Conform();
                                    } else {
                                        Toast.makeText(EditAccidentData.this, "Please Enter Discription", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(EditAccidentData.this, "Location is mandatory", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EditAccidentData.this, "Select Crime Status", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EditAccidentData.this, "Select Accident Date and Time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditAccidentData.this, "Select Accident Type", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        buildGoogleApiClient();
        createLocationRequest();
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
        setData();
        ArrayAdapter<Samplemyclass> adapter3 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, cl3);
        svs.setAdapter(adapter3);
        svs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {


                    Samplemyclass list = (Samplemyclass) svs.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    Ssvs = country.getId();
                    Svs1 = country.getName();
                    if (pos == 1) {
                        RL1.setVisibility(View.VISIBLE);
                        Ssvs = country.getId();
                        Svs1 = country.getName();
                    }
                    if (pos == 0) {
                        RL1.setVisibility(View.GONE);
                        Ssvs = country.getId();
                        Svs1 = country.getName();
                    }
                    if (pos == 2) {
                        RL1.setVisibility(View.GONE);
                        Ssvs = country.getId();
                        Svs1 = country.getName();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<Samplemyclass> adapter5 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, cl4);
        sas.setAdapter(adapter5);
        sas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();

                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) sas.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    if (pos == 1) {
                        RL2.setVisibility(View.VISIBLE);
                        Ssas = country.getId();
                        Sas1 = country.getName();
                    }
                    if (pos == 0) {
                        RL2.setVisibility(View.GONE);
                        Ssas = country.getId();
                        Sas1 = country.getName();
                    }
                    if (pos == 2) {
                        RL2.setVisibility(View.GONE);
                        Ssas = country.getId();
                        Sas1 = country.getName();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditAccidentData.this);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are You Sure..Do You Want to Edit Data??");
        alertDialog.setIcon(R.drawable.alert);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke YES event
                dialog.cancel();

                NotNull();

                S_id = getIntent().getStringExtra("id");
                S_locality = getIntent().getStringExtra("location");
                S_Cno = getIntent().getStringExtra("crimeNumber");
                S_Cla = getIntent().getStringExtra("Locality");
                S_Noi = getIntent().getStringExtra("NoOfInjuries");
                S_Nod = getIntent().getStringExtra("NoOfDeaths");
                S_latitude = getIntent().getStringExtra("latitude");
                S_longitude = getIntent().getStringExtra("longitude");
                S_dateTime = getIntent().getStringExtra("dateTime");
                S_detected_ = getIntent().getStringExtra("detectedStatus");
                Sat1 = getIntent().getStringExtra("AccidentType1");
                S_discription = getIntent().getStringExtra("description");
                S_roadNo = getIntent().getStringExtra("RoadNumber");
                Srt1 = getIntent().getStringExtra("RoadType");
                Sat = getIntent().getStringExtra("AccidentType");
                Srt = getIntent().getStringExtra("RoadTypeId");
                Svc1 = getIntent().getStringExtra("Victimcategorie");
                Sac1 = getIntent().getStringExtra("AccusedCategorie");

                Ssvc = getIntent().getStringExtra("VictimcategorieId");
                Ssac = getIntent().getStringExtra("AccusedCategorieId");

                S_vap = getIntent().getStringExtra("VictimAlcoholicPercentage");
                S_aap = getIntent().getStringExtra("AccusedAlcoholicPercentage");

                Ssvs = getIntent().getStringExtra("VictimStatus");
                Ssas = getIntent().getStringExtra("AccusedStatus");

                Svs1 = getIntent().getStringExtra("VictimStatusName");
                Sas1 = getIntent().getStringExtra("AccusedStatusName");

                //   private Spinner at, rt, svc, svs, sac, sas, detected_;
                Log.e(TAG, "*" + Sat);
                Log.e(TAG, "**" + Sat1);
                et_discription.setText(""+S_discription);
                et_roadNo.setText(""+S_roadNo);
                et_vap.setText(""+S_vap);
                et_aap.setText(""+S_aap);
                et_Cno.setText(""+S_Cno);
                et_Cla.setText(""+S_Cla);
                et_Noi.setText(""+S_Noi);
                et_Nod.setText(""+S_Nod);
                et_locality.setText(""+S_locality);
                Bt_acc_date.setText(""+S_dateTime);
                if (Srt != null) {
                    if (!Srt.equalsIgnoreCase("")) {
                        rt.setSelection(Integer.parseInt(Srt));
                    }
                }
                if (Sat != null) {
                    if (!Sat.equalsIgnoreCase("")) {
                        at.setSelection(Integer.parseInt(Sat));
                    }
                }

                if (Ssvc != null) {
                    if (!Ssvc.equalsIgnoreCase("")) {
                        svc.setSelection(Integer.parseInt(Ssvc));
                    }
                }
                if (Ssac != null) {
                    if (!Ssac.equalsIgnoreCase("")) {
                        sac.setSelection(Integer.parseInt(Ssac));
                    }
                }
                if (Ssvs != null) {
                    if (!Ssvs.equalsIgnoreCase("")) {
                        svs.setSelection(Integer.parseInt(Ssvs));
                    }
                }
                if (Ssas != null) {
                    if (!Ssas.equalsIgnoreCase("")) {
                        sas.setSelection(Integer.parseInt(Ssas));
                    }
                }

//                if (S_detected_ != null) {
//                    if (!S_detected_.equalsIgnoreCase("")) {
//                        detected_.setSelection(Integer.parseInt(Srt));
//                    }
//                }




            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke YES event
                dialog.cancel();
            }
        });
        alertDialog.show();

    }

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    EditAccidentData.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    public void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //  mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            //startLocationUpdates();
        } else {
            Log.e(TAG, "Couldn't connect to google ");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        active = true;
    }

    @Override
    protected void onPause() {
        Log.w(TAG, "App onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.w(TAG, "App stopped");

        super.onStop();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    protected void onDestroy() {
        Log.w(TAG, "App destroyed");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        active = true;
        //FineLocationPermission();

        super.onStart();
    }

    //    @Override
//    public void onBackPressed() {
//
//        new AlertDialog.Builder(this)
//
//                .setIcon(R.drawable.alert)
//                .setTitle("Closing Application")
//                .setMessage("Are you sure you want to exit?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent a = new Intent(Intent.ACTION_MAIN);
//                        a.addCategory(Intent.CATEGORY_HOME);
//                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(a);
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//
//    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        mLastLocation = location;
        // Displaying the new location on UI
        displayLocation();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Once connected with google api, get the location
        displayLocation();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            Log.e("msg", "" + latitude + longitude);

            S_latitude = String.valueOf(latitude);
            S_longitude = String.valueOf(longitude);
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 7);
                if (addresses != null) {
                    if (addresses.size() > 0) {
                        String address = addresses.get(0).getAddressLine(0);
                        String address1 = addresses.get(0).getSubLocality();
                        String address2 = addresses.get(0).getLocality();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(address);
//                    stringBuilder.append(",");
//                    stringBuilder.append(address1);
//                    stringBuilder.append(",");
//                    stringBuilder.append(address2);
                        String S_loc = stringBuilder.toString();
                        String t66 = "<font color='#ebedf5'>Location : </font>";
                        Log.e("msg", "" + S_loc);
                        et_locality.setText(" " + S_loc);
                        // CurrentLocation.setText(Html.fromHtml(t66+S_loc));
                        // CurrentLocation.setText(S_loc);
                    }
                }
            } catch (IOException e) {

                et_locality.setText(" " + "Couldn't get the location.");
                Log.e("msg", "" + "Couldn't get the location.");
                //CurrentLocation.setText("Couldn't get the location.");
                e.printStackTrace();
            }
        } else {
//            vlat.setText("0.0");
//            vlon.setText("0.0");
//            Lat= String.valueOf(0.0);
//            Longi= String.valueOf(0.0);
            // Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
            // CurrentLocation.setText("Couldn't get the location.Verify Your Internet and Gps Connectivity ");
            Log.e("msg", "" + "No latLong found");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    private void alertDialogue_Conform() {
        C_dialog = new Dialog(EditAccidentData.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.conform_accident);
        C_dialog.show();

        TextView d_ano = (TextView) C_dialog.findViewById(R.id.tv_acc_cno);
        TextView d_at = (TextView) C_dialog.findViewById(R.id.tv_acc_at);
        TextView d_dt = (TextView) C_dialog.findViewById(R.id.tv_acc_dt);
        TextView d_ad = (TextView) C_dialog.findViewById(R.id.tv_acc_add);
        TextView d_ds = (TextView) C_dialog.findViewById(R.id.tv_acc_ds);
        TextView d_dis = (TextView) C_dialog.findViewById(R.id.tv_acc_dis);
        TextView d_rt = (TextView) C_dialog.findViewById(R.id.tv_acc_rt);
        TextView d_rn = (TextView) C_dialog.findViewById(R.id.tv_acc_rn);
        TextView d_noi = (TextView) C_dialog.findViewById(R.id.tv_acc_noi);
        TextView d_nod = (TextView) C_dialog.findViewById(R.id.tv_acc_nod);

        Log.e("1", "" + S_Uname);
        Log.e("2", "" + S_Pass);
        Log.e("3", "" + S_IMEi);
        Log.e("4", "" + Sat1);
//                Log.e("5",""+Cat1);
//                Log.e("5.1",""+item2);
        Log.e("6", "" + S_Cno);
        Log.e("6.1", "" + S_dateTime);
        Log.e("7", "" + S_Cla);
        Log.e("8", "" + S_detected_);
        Log.e("9", "" + Srt1);
        Log.e("10", "" + S_roadNo);
        Log.e("11", "" + S_locality);
        Log.e("12", "" + S_discription);
        Log.e("11", "" + S_Noi);
        Log.e("12", "" + S_Nod);

        if (S_detected_ == null) {
            S_detected_ = "Complaint";
            Log.e("13", "" + S_detected_);
        }

        d_ano.setText(S_Cno);
        d_at.setText(Sat1);
        d_dt.setText(S_dateTime);
        d_ad.setText(S_Cla);
        d_ds.setText(S_detected_);
        d_dis.setText(S_discription);
        d_rt.setText(Srt1);
        d_rn.setText(S_roadNo);
        d_noi.setText(S_Noi);
        d_nod.setText(S_Nod);

        NotNull();

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_acci_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.tv_acci_Cancel);

        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayLocation();
                CheckConnectivity();

            }
        });
        C_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });
    }


    public void NotNull() {
        if (Sas1 == null) {
            Sas1 = "";
        } else {
        }

        if (Svs1 == null) {
            Svs1 = "";
        } else {
        }


        if (Sat1 == null) {
            Sat1 = "";
        } else {
        }

        if (Srt1 == null) {
            Srt1 = "";
        } else {
        }
        if (Svc1 == null) {
            Svc1 = "";
        } else {
        }

        if (Sac1 == null) {
            Sac1 = "";
        } else {
        }

        if (S_Cla == null) {
            S_Cla = "";
        } else {
        }

        if (S_Cno == null) {
            S_Cno = "";
        } else {
        }
        if (Sat1 == null) {
            Sat1 = "";
        } else {
        }
        if (S_dateTime == null) {
            S_dateTime = "";
        } else {
        }
        if (S_locality == null) {
            S_locality = "";
        } else {
        }

        if (S_longitude == null) {
            S_longitude = "";
        } else {
        }
        if (S_latitude == null) {
            S_latitude = "";
        } else {
        }

        if (Srt == null) {
            Srt = "";
        } else {
        }
        if (S_roadNo == null) {
            S_roadNo = "";
        } else {
        }
        if (S_detected_ == null) {
            S_detected_ = "";
        } else {
        }
        if (S_discription == null) {
            S_discription = "";
        } else {
        }
        if (S_Noi == null) {
            S_Noi = "";
        } else {
        }
        if (S_Nod == null) {
            S_Nod = "";
        } else {
        }
        if (Ssvc == null) {
            Ssvc = "";
        } else {
        }
        if (Ssvs == null) {
            Ssvs = "";
        } else {
        }

        if (S_vap == null) {
            S_vap = "";
        } else {
        }
        if (Ssac == null) {
            Ssac = "";
        } else {
        }
        if (Ssas == null) {
            Ssas = "";
        } else {
        }
        if (S_aap == null) {
            S_aap = "";
        } else {
        }

    }

    private void insertDB(String crimeNumber, String AccidentType, String dateTime,
                          String location, String RoadTypeId, String RoadNumber, String detectedStatus,
                          String latitude, String longitude, String description,
                          String NoOfInjuries, String NoOfDeaths,
                          String VictimcategorieId, String VictimStatus, String VictimAlcoholicPercentage,
                          String AccusedCategorieId, String AccusedStatus, String AccusedAlcoholicPercentage,
                          String accidentName, String Roadtype, String Victimcategorie, String AccusedCategorie,
                          String VictimStatusName, String AccusedStatusName, String Locality) {


        Log.d(TAG, "insert into Location values" + crimeNumber + AccidentType + dateTime);
        dbHandler = new DbHandler(EditAccidentData.this);
        database = dbHandler.getReadableDatabase();
        statement = database.compileStatement("insert into Location values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        statement.bindString(2, crimeNumber);
        statement.bindString(3, AccidentType);
        statement.bindString(4, dateTime);
        statement.bindString(5, location);
        statement.bindString(6, RoadTypeId);
        statement.bindString(7, RoadNumber);
        statement.bindString(8, detectedStatus);
        statement.bindString(9, latitude);
        statement.bindString(10, longitude);
        statement.bindString(11, description);
        statement.bindString(12, NoOfInjuries);
        statement.bindString(13, NoOfDeaths);
        statement.bindString(14, VictimcategorieId);
        statement.bindString(15, VictimStatus);
        statement.bindString(16, VictimAlcoholicPercentage);
        statement.bindString(17, AccusedCategorieId);
        statement.bindString(18, AccusedStatus);
        statement.bindString(19, AccusedAlcoholicPercentage);
        statement.bindString(20, accidentName);
        statement.bindString(21, Roadtype);
        statement.bindString(22, Victimcategorie);
        statement.bindString(23, AccusedCategorie);
        statement.bindString(24, VictimStatusName);
        statement.bindString(25, AccusedStatusName);
        statement.bindString(26, Locality);
        statement.executeInsert();
        database.close();
        C_dialog.dismiss();
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditAccidentData.this);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Data Saved in OfflineList");
        alertDialog.setIcon(R.drawable.alert);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                C_dialog.dismiss();
//                sp_type.setSelection(0);
//                et_hp_landmark.setText("");
//                et_hp_add.setText("");
//                et_hp_name.setText("");
                // Write your code here to invoke YES event
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void acctype() {
        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, d1);
        at.setAdapter(adapter);
        at.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                int pos = parent.getSelectedItemPosition();
//                if (pos != 0) {
                Samplemyclass list = (Samplemyclass) at.getSelectedItem();
                Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                Sat1 = country.getName();
                Sat = country.getId();
                // Toast.makeText(EditAccidentData.this, " acc type"+Sat1, Toast.LENGTH_SHORT).show();
                // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    class pay extends AsyncTask<Void, Void, Void> {


        private final ProgressDialog dialog = new ProgressDialog(EditAccidentData.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {


            String SOAP_ACTION = "http://tempuri.org/AddAccident_View";
            String METHOD_NAME = "AddAccident_View";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username", S_Uname);
            request.addProperty("password", S_Pass);
            request.addProperty("imei", S_IMEi);
            request.addProperty("connectionType", "TabletPc");
            request.addProperty("crimeNumber", S_Cno);
            request.addProperty("AccidentType", Sat1);
            request.addProperty("dateTime", S_dateTime);

            request.addProperty("location", S_locality);
            request.addProperty("RoadTypeId", Srt);
            request.addProperty("RoadNumber", S_roadNo);
            request.addProperty("detectedStatus", S_detected_);
            request.addProperty("latitude", S_latitude);
            request.addProperty("longitude", S_longitude);
            request.addProperty("description", S_discription);
            request.addProperty("NoOfInjuries", S_Noi);
            request.addProperty("NoOfDeaths", S_Nod);
            request.addProperty("VictimcategorieId", Ssvc);
            request.addProperty("VictimStatus", Ssvs);
            request.addProperty("VictimAlcoholicPercentage", S_vap);
            request.addProperty("AccusedCategorieId", Ssac);
            request.addProperty("AccusedStatus", Ssas);
            request.addProperty("AccusedAlcoholicPercentage", S_aap);
            request.addProperty("Locality", S_Cla);
            request.addProperty("base64Image", "");

            envelope.setOutputSoapObject(request);

            Log.e("request", "" + request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            Log.e("EditAcc_ENVOLOPE", "" + envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();

                Log.e("result_EditAccidentData", "" + result1);
                SoapObject result = (SoapObject) envelope.bodyIn;
                String[] testValues = new String[result.getPropertyCount()];
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    Object property = result.getProperty(i);
                    testValues[i] = result.getProperty(i).toString();
                    SoapObject category_list = (SoapObject) property;
                    Code1 = Integer.parseInt(category_list.getProperty("Code").toString());
                    Message1 = category_list.getProperty("Message").toString();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.e("result", "" + result);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (timeoutexcep) {
                Toast.makeText(EditAccidentData.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(EditAccidentData.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(EditAccidentData.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (Code1 != null) {
                    if (Code1 == 0) {
                        removeDB(S_id);
                        Log.e("suceess", "" + Message1);
                        C_dialog.dismiss();
                        et_discription.setText("");
                        et_roadNo.setText("");
                        et_vap.setText("");
                        et_aap.setText("");
                        et_Cno.setText("");
                        et_Cla.setText("");
                        et_Noi.setText("");
                        et_Nod.setText("");
                        Bt_acc_date.setText("Accident Date and Time");
                        S_Cno = "";
                        Sat1 = "";
                        S_dateTime = "";
                        S_Cla = "";
                        S_detected_ = "";
                        S_discription = "";
                        Srt1 = "";
                        S_roadNo = "";
                        S_Noi = "";
                        S_Nod = "";
                        at.setSelection(0);
                        rt.setSelection(0);
                        svc.setSelection(0);
                        svs.setSelection(0);
                        sac.setSelection(0);
                        sas.setSelection(0);
                        detected_.setSelection(0);
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditAccidentData.this);
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("Values Saved Succesfully");
                        alertDialog.setIcon(R.drawable.succs);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to invoke YES event
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                    } else {
                        Log.e("not sucess", "" + Message1);
                        C_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditAccidentData.this);
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("" + Message1);
                        alertDialog.setIcon(R.drawable.alertt);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        // Toast.makeText(EditAccidentData.this,""+Message,Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }

                Log.e("result", "done");
                //tableview();
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            new pay().execute();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    class getpay extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(EditAccidentData.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/GetAccidentRecordsdata";
            String METHOD_NAME = "GetAccidentRecordsdata";
            String NAMESPACE = "http://tempuri.org/";

            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetAccidentRecordsdata";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            envelope.setOutputSoapObject(request);
            Log.e("1245", S_Pass + "    " + S_IMEi);
            Log.e("request", "" + request);

            try {
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.debug = true;
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
                httpTransport.call(SOAP_ACTION, envelope);
                Log.e("envelope", "" + envelope);
                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", "" + respone);
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.d(TAG, " Response: \n" + result);
                SoapObject root = (SoapObject) result.getProperty(0);

                Pscode_arraylist_.clear();
                Type_arraylist_.clear();
                Location_arraylist_.clear();
                Cdate_arraylist_.clear();
                EnteredDate_arraylist_.clear();
                Cnumber_arraylist_.clear();
                AccidentENo_arraylist_.clear();

                CStatus_arraylist_.clear();
                Descr_arraylist_.clear();
                RoadType_arraylist_.clear();
                Road_Number_arraylist_.clear();
                NOofIn_arraylist_.clear();
                NOofD_arraylist_.clear();

                for (int j = 0; j < root.getPropertyCount(); j++) {
                    SoapObject s_deals = (SoapObject) root.getProperty(j);

                    for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                        Object property = s_deals.getProperty(i);

                        AccidentENo = s_deals.getProperty("AccidentEntryNumber").toString();
                        Cnumber = s_deals.getProperty("Cnumber").toString();
                        Pscode = s_deals.getProperty("Pscode").toString();
                        Cdate = s_deals.getProperty("Cdate").toString();
                        Type = s_deals.getProperty("AccidentType").toString();
                        Location = s_deals.getProperty("Location").toString();
                        EnteredDate = s_deals.getProperty("Cdate").toString();

                        RoadType = s_deals.getProperty("RoadType").toString();
                        Road_Number = s_deals.getProperty("RoadNo").toString();
                        NOofIn = s_deals.getProperty("NoOfinjurious").toString();
                        NOofD = s_deals.getProperty("NoOfDeaths").toString();
                        CStatus = s_deals.getProperty("DetectedStatus").toString();
                        Descr = s_deals.getProperty("Description").toString();

                    }

                    if (CStatus == null || CStatus.trim().equals("anyType{}") || CStatus.trim()
                            .length() <= 0) {
                        CStatus_arraylist_.add("");
                    } else {
                        CStatus_arraylist_.add(CStatus);
                    }

                    if (Descr == null || Descr.trim().equals("anyType{}") || Descr.trim()
                            .length() <= 0) {
                        Descr_arraylist_.add("");
                    } else {
                        Descr_arraylist_.add(Descr);
                    }
                    if (RoadType == null || RoadType.trim().equals("anyType{}") || RoadType.trim()
                            .length() <= 0) {
                        RoadType_arraylist_.add("");
                    } else {
                        RoadType_arraylist_.add(RoadType);
                    }
                    if (Road_Number == null || Road_Number.trim().equals("anyType{}") || Road_Number.trim()
                            .length() <= 0) {
                        Road_Number_arraylist_.add("");
                    } else {
                        Road_Number_arraylist_.add(Road_Number);
                    }
                    if (NOofIn == null || NOofIn.trim().equals("anyType{}") || NOofIn.trim()
                            .length() <= 0) {
                        NOofIn_arraylist_.add("");
                    } else {
                        NOofIn_arraylist_.add(NOofIn);
                    }
                    if (NOofD == null || NOofD.trim().equals("anyType{}") || NOofD.trim()
                            .length() <= 0) {
                        NOofD_arraylist_.add("");
                    } else {
                        NOofD_arraylist_.add(NOofD);
                    }

                    if (Cnumber == null || Cnumber.trim().equals("anyType{}") || Cnumber.trim()
                            .length() <= 0) {
                        Cnumber_arraylist_.add("");
                    } else {
                        Cnumber_arraylist_.add(Cnumber);
                    }

                    if (Pscode == null || Pscode.trim().equals("anyType{}") || Pscode.trim()
                            .length() <= 0) {
                        Pscode_arraylist_.add("");
                    } else {
                        Pscode_arraylist_.add(Pscode);
                    }
                    if (Type == null || Type.trim().equals("anyType{}") || Type.trim()
                            .length() <= 0) {
                        Type_arraylist_.add("");
                    } else {
                        Type_arraylist_.add(Type);
                    }
                    if (AccidentENo == null || AccidentENo.trim().equals("anyType{}") || AccidentENo.trim()
                            .length() <= 0) {
                        AccidentENo_arraylist_.add("");
                    } else {
                        AccidentENo_arraylist_.add(AccidentENo);
                    }
                    if (Location == null || Location.trim().equals("anyType{}") || Location.trim()
                            .length() <= 0) {
                        Location_arraylist_.add("");
                    } else {
                        Location_arraylist_.add(Location);
                    }
                    if (Cdate == null || Cdate.trim().equals("anyType{}") || Cdate.trim()
                            .length() <= 0) {
                        Cdate_arraylist_.add("");
                    } else {
                        Cdate_arraylist_.add(Cdate);
                    }
                    if (EnteredDate == null || EnteredDate.trim().equals("anyType{}") || EnteredDate.trim()
                            .length() <= 0) {
                        EnteredDate_arraylist_.add("");
                    } else {
                        EnteredDate_arraylist_.add(EnteredDate);
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.e("result", "" + result);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();

            }
            if (timeoutexcep) {
                Toast.makeText(EditAccidentData.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(EditAccidentData.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(EditAccidentData.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                //Log.d(TAG,  "Re "+final_result.toString());

                if (Code == 0) {
                    SetValuesToLayout();
                } else {

                    // Toast.makeText(EditAccidentData.this,""+Message,Toast.LENGTH_LONG).show();
                }

                Log.e("result", "done");
                //tableview();
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

    private void SetValuesToLayout() {

        vdialog = new Dialog(EditAccidentData.this, R.style.HiddenTitleTheme);
        vdialog.setContentView(R.layout.vl_accdnt);
        vdialog.show();
        TableRow r0 = (TableRow) vdialog.findViewById(R.id.tr0);
        TableRow r1 = (TableRow) vdialog.findViewById(R.id.tr1);
        TableRow r2 = (TableRow) vdialog.findViewById(R.id.tableRow2);
        TableRow r3 = (TableRow) vdialog.findViewById(R.id.tableRow3);
        TableRow r4 = (TableRow) vdialog.findViewById(R.id.tableRow4);
        TableRow r5 = (TableRow) vdialog.findViewById(R.id.tableRow5);
        TextView NoRecords = (TextView) vdialog.findViewById(R.id.tv_norecords);

        TextView t1 = (TextView) vdialog.findViewById(R.id.tv_t1);
        TextView t2 = (TextView) vdialog.findViewById(R.id.tv_t2);
        TextView t3 = (TextView) vdialog.findViewById(R.id.tv_t3);
        TextView t4 = (TextView) vdialog.findViewById(R.id.tv_t4);
        TextView t5 = (TextView) vdialog.findViewById(R.id.tv_t5);

        TextView st1 = (TextView) vdialog.findViewById(R.id.tv_p1);
        TextView st2 = (TextView) vdialog.findViewById(R.id.tv_p2);
        TextView st3 = (TextView) vdialog.findViewById(R.id.tv_p3);
        TextView st4 = (TextView) vdialog.findViewById(R.id.tv_p4);
        TextView st5 = (TextView) vdialog.findViewById(R.id.tv_p5);

        TextView dt1 = (TextView) vdialog.findViewById(R.id.tv_c1);
        TextView dt2 = (TextView) vdialog.findViewById(R.id.tv_c2);
        TextView dt3 = (TextView) vdialog.findViewById(R.id.tv_c3);
        TextView dt4 = (TextView) vdialog.findViewById(R.id.tv_c4);
        TextView dt5 = (TextView) vdialog.findViewById(R.id.tv_c5);

        TextView v1 = (TextView) vdialog.findViewById(R.id.tv_m1);
        TextView v2 = (TextView) vdialog.findViewById(R.id.tv_m2);
        TextView v3 = (TextView) vdialog.findViewById(R.id.tv_m3);
        TextView v4 = (TextView) vdialog.findViewById(R.id.tv_m4);
        TextView v5 = (TextView) vdialog.findViewById(R.id.tv_m5);


        if ((Type_arraylist_.size()) > 0 | (Type_arraylist_.size()) != 0) {
            Log.e("1", "" + Type_arraylist_.size());

            if ((Type_arraylist_.size()) == 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                dt1.setText(Type_arraylist_.get(0));
                dt2.setText(Type_arraylist_.get(1));
                dt3.setText(Type_arraylist_.get(2));
                dt4.setText(Type_arraylist_.get(3));
                dt5.setText(Type_arraylist_.get(4));

                t1.setText(AccidentENo_arraylist_.get(0));
                t2.setText(AccidentENo_arraylist_.get(1));
                t3.setText(AccidentENo_arraylist_.get(2));
                t4.setText(AccidentENo_arraylist_.get(3));
                t5.setText(AccidentENo_arraylist_.get(4));
                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }
                    if ((Cnumber_arraylist_.size()) == 3) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                    }
                    if ((Cnumber_arraylist_.size()) == 4) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                    }
                    if ((Cnumber_arraylist_.size()) == 5) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                        st5.setText(Cnumber_arraylist_.get(4));
                    }
                }
            }
            if ((Type_arraylist_.size()) == 1) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                dt1.setText(Type_arraylist_.get(0));
                t1.setText(AccidentENo_arraylist_.get(0));
                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                }
            }
            if ((Type_arraylist_.size()) == 2) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                dt1.setText(Type_arraylist_.get(0));
                t1.setText(AccidentENo_arraylist_.get(0));
                dt2.setText(Type_arraylist_.get(1));
                t2.setText(AccidentENo_arraylist_.get(1));
                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }
                }


            }
            if ((Type_arraylist_.size()) == 3) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                dt1.setText(Type_arraylist_.get(0));
                t1.setText(AccidentENo_arraylist_.get(0));
                dt2.setText(Type_arraylist_.get(1));
                t2.setText(AccidentENo_arraylist_.get(1));
                dt3.setText(Type_arraylist_.get(2));
                t3.setText(AccidentENo_arraylist_.get(2));

                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }
                    if ((Cnumber_arraylist_.size()) == 3) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                    }
                }

            }
            if ((Type_arraylist_.size()) == 4) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                dt1.setText(Type_arraylist_.get(0));
                t1.setText(AccidentENo_arraylist_.get(0));
                dt2.setText(Type_arraylist_.get(1));
                t2.setText(AccidentENo_arraylist_.get(1));
                dt3.setText(Type_arraylist_.get(2));
                t3.setText(AccidentENo_arraylist_.get(2));
                dt4.setText(Type_arraylist_.get(3));
                t4.setText(AccidentENo_arraylist_.get(3));
                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }
                    if ((Cnumber_arraylist_.size()) == 3) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                    }
                    if ((Cnumber_arraylist_.size()) == 4) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                    }

                }
            }

            if ((Type_arraylist_.size()) > 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                Log.e("yes", "" + Type_arraylist_.size());
                dt1.setText(Type_arraylist_.get(0));
                dt2.setText(Type_arraylist_.get(1));
                dt3.setText(Type_arraylist_.get(2));
                dt4.setText(Type_arraylist_.get(3));
                dt5.setText(Type_arraylist_.get(4));

                t1.setText(AccidentENo_arraylist_.get(0));
                t2.setText(AccidentENo_arraylist_.get(1));
                t3.setText(AccidentENo_arraylist_.get(2));
                t4.setText(AccidentENo_arraylist_.get(3));
                t5.setText(AccidentENo_arraylist_.get(4));

                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }
                    if ((Cnumber_arraylist_.size()) == 3) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                    }
                    if ((Cnumber_arraylist_.size()) == 4) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                    }
                    if ((Cnumber_arraylist_.size()) == 5) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                        st5.setText(Cnumber_arraylist_.get(4));
                    }
                }
            }

        } else {
            r0.setVisibility(View.GONE);
            r1.setVisibility(View.GONE);
            r2.setVisibility(View.GONE);
            r3.setVisibility(View.GONE);
            r4.setVisibility(View.GONE);
            r5.setVisibility(View.GONE);
            NoRecords.setVisibility(View.VISIBLE);
        }

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogue_C1(0);
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogue_C1(1);
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogue_C1(2);
            }
        });
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogue_C1(3);
            }
        });
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogue_C1(4);
            }
        });
    }

    private void alertDialogue_C1(int count) {
        C_dialog = new Dialog(EditAccidentData.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_confm_accident);
        C_dialog.show();
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_acc_pscode);
        TextView d_accno = (TextView) C_dialog.findViewById(R.id.tv_acc_accno);
        TextView d_ano = (TextView) C_dialog.findViewById(R.id.tv_acc_cno);
        TextView d_at = (TextView) C_dialog.findViewById(R.id.tv_acc_at);
        TextView d_dt = (TextView) C_dialog.findViewById(R.id.tv_acc_dt);
        TextView d_ad = (TextView) C_dialog.findViewById(R.id.tv_acc_add);
        TextView d_ds = (TextView) C_dialog.findViewById(R.id.tv_acc_ds);
        TextView d_dis = (TextView) C_dialog.findViewById(R.id.tv_acc_dis);
        TextView d_rt = (TextView) C_dialog.findViewById(R.id.tv_acc_rt);
        TextView d_rn = (TextView) C_dialog.findViewById(R.id.tv_acc_rn);
        TextView d_noi = (TextView) C_dialog.findViewById(R.id.tv_acc_noi);
        TextView d_nod = (TextView) C_dialog.findViewById(R.id.tv_acc_nod);
//        d_cno = (TextView) C_dialog.findViewById(R.id.tv_c_cno);
//        d_ct = (TextView) C_dialog.findViewById(R.id.tv_c_ct);
//        d_sct = (TextView) C_dialog.findViewById(R.id.tv_c_sct);
//        d_dt = (TextView) C_dialog.findViewById(R.id.tv_c_dt);
//        d_ad = (TextView) C_dialog.findViewById(R.id.tv_c_add);
//        d_ds = (TextView) C_dialog.findViewById(R.id.tv_c_ds);
//        d_dis = (TextView) C_dialog.findViewById(R.id.tv_c_dis);
        //    TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_acc_pscode);

        pscode.setText(s_Ps_name);
        d_accno.setText(AccidentENo_arraylist_.get(count));
        if (Cnumber_arraylist_.size() > count) {
            d_ano.setText(Cnumber_arraylist_.get(count));
        } else {
            d_ano.setText("");
        }
        d_at.setText(Type_arraylist_.get(count));
        //    d_sct.setText(Cdate_arraylist_.get(0));
        // d_ds.setText(CStatus_arraylist_.get(0));
        d_dt.setText(EnteredDate_arraylist_.get(count));
        d_ad.setText(Location_arraylist_.get(count));

        if (CStatus_arraylist_.size() > count) {
            d_ds.setText(CStatus_arraylist_.get(count));
        } else {
            d_ds.setText("");
        }
        if (Descr_arraylist_.size() > count) {
            d_dis.setText(Descr_arraylist_.get(count));
        } else {
            d_dis.setText("");
        }
        if (RoadType_arraylist_.size() > count) {
            d_rt.setText(RoadType_arraylist_.get(count));
        } else {
            d_rt.setText("");
        }
        if (Road_Number_arraylist_.size() > count) {
            d_rn.setText(Road_Number_arraylist_.get(count));
        } else {
            d_rn.setText("");
        }
        if (NOofIn_arraylist_.size() > count) {
            d_noi.setText(NOofIn_arraylist_.get(count));
        } else {
            d_noi.setText("");
        }
        if (NOofD_arraylist_.size() > count) {
            d_nod.setText(NOofD_arraylist_.get(count));
        } else {
            d_nod.setText("");
        }
        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_acci_Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });
    }

    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            new getpay().execute();
            //  new getAcc().execute();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void CheckConnectivity2() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            //  new getpay().execute();
            new getAcc().execute();
            new getRoadType().execute();
            new getCrimeStatus().execute();
            new getVictimCat().execute();
            new getAccusedCat().execute();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void RoadType() {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, d2);
        rt.setAdapter(adapter1);
        rt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) rt.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    Srt = country.getId();
                    Srt1 = country.getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void AccidentType() {

        adapter_at = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, d1);
        at.setAdapter(adapter_at);
        at.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                int pos = parent.getSelectedItemPosition();
//                if (pos != 0) {
                Samplemyclass list = (Samplemyclass) at.getSelectedItem();
                Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                Sat1 = country.getName();
                Sat = country.getId();
                // Toast.makeText(EditAccidentData.this, " acc type"+Sat1, Toast.LENGTH_SHORT).show();
                // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void CrimeStatus() {
        if (et_Cno.getText().toString().isEmpty()) {
            adapter_state = new ArrayAdapter<Samplemyclass>(getApplicationContext(),
                    R.layout.spinner_item, d6);
        } else {
            adapter_state = new ArrayAdapter<Samplemyclass>(getApplicationContext(),
                    R.layout.spinner_item, d3);
        }
        ///error coomming here

        detected_.setAdapter(adapter_state);
        detected_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    S_detected_ = "";
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    // Ssvc =country.getId();
                    S_detected_ = country.getName();
                    detected_.setSelection(position);
                    //  S_detected_ = (String) detected_.getSelectedItem();
                    Log.e("crime_Genianty", "" + S_detected_);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void VictimCat() {
        ArrayAdapter<Samplemyclass> adapter2 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, d4);
        svc.setAdapter(adapter2);
        svc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) svc.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    Ssvc = country.getId();
                    Svc1 = country.getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void AccusedCat() {
        ArrayAdapter<Samplemyclass> adapter4 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, d5);
        sac.setAdapter(adapter4);
        sac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) sac.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    Ssac = country.getId();
                    Sac1 = country.getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setStatus() {

        if (isConnectingToInternet(EditAccidentData.this)) {
            d3 = new ArrayList<>();
            d6 = new ArrayList<>();
            d6.add(new Samplemyclass("0", "Complaint"));
            new getCrimeStatus().execute();
        } else {
            d3 = new ArrayList<>();
            d6 = new ArrayList<>();
            d6.add(new Samplemyclass("0", "Complaint"));
            try {
                SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                Gson gson = new Gson();
                String json = appSharedPrefs.getString("CrimeStatus", "");
                java.lang.reflect.Type type = new TypeToken<List<Samplemyclass>>() {
                }.getType();
                d3 = gson.fromJson(json, type);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("Arraylist", "CrimeStatus" + d3);
            if (d3.size() > 0) {
                CrimeStatus();
            }
        }


    }

    private void setData() {

        setStatus();
        d1 = new ArrayList<>();
        d2 = new ArrayList<>();
        d4 = new ArrayList<>();
        d5 = new ArrayList<>();
        d6 = new ArrayList<>();
        d6.add(new Samplemyclass("0", "Complaint"));

        if (isConnectingToInternet(EditAccidentData.this)) {
            CheckConnectivity2();
        } else {

            try {
                SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                Gson gson = new Gson();
                String json = appSharedPrefs.getString("AccidentType", "");
                Type type = new TypeToken<List<Samplemyclass>>() {
                }.getType();
                d1 = gson.fromJson(json, type);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("Arraylist", "" + d1);
            if (d1.size() > 0) {
                AccidentType();
            }
            try {
                SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                Gson gson = new Gson();
                String json = appSharedPrefs.getString("RoadType", "");
                Type type = new TypeToken<List<Samplemyclass>>() {
                }.getType();
                d2 = gson.fromJson(json, type);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("Arraylist", "RoadType" + d2);
            if (d2.size() > 0) {
                RoadType();
            }

            try {
                SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                Gson gson = new Gson();
                String json = appSharedPrefs.getString("CrimeStatus", "");
                Type type = new TypeToken<List<Samplemyclass>>() {
                }.getType();
                d3 = gson.fromJson(json, type);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("CrimeStatus", "CrimeStatus" + d3);
            if (d3.size() > 0) {
                CrimeStatus();
            }

            try {
                SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                Gson gson = new Gson();
                String json = appSharedPrefs.getString("VictimCat", "");
                Type type = new TypeToken<List<Samplemyclass>>() {
                }.getType();
                d4 = gson.fromJson(json, type);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("VictimCat", "VictimCat" + d4);
            if (d4.size() > 0) {
                VictimCat();
            }
            try {
                SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                Gson gson = new Gson();
                String json = appSharedPrefs.getString("AccusedCat", "");
                Type type = new TypeToken<List<Samplemyclass>>() {
                }.getType();
                d5 = gson.fromJson(json, type);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("AccusedCat", "AccusedCat" + d5);
            if (d5.size() > 0) {
                AccusedCat();
            }
        }

//        cl0 = new ArrayList<>();
//        cl0.add(new Samplemyclass("0", "Select Accident Type"));
//        cl0.add(new Samplemyclass("1", "Simple Injuries"));
//        cl0.add(new Samplemyclass("2", "Fatal"));
//        cl0.add(new Samplemyclass("3", "Greivious Injuries"));
//        cl0.add(new Samplemyclass("4", "No Injuries"));

//        cl = new ArrayList<>();
//        cl.add(new Samplemyclass("0", "Select Road Type"));
//        cl.add(new Samplemyclass("1", "InnerRing Road"));
//        cl.add(new Samplemyclass("2", "Major Road"));
//        cl.add(new Samplemyclass("3", "Minor / Colony Road"));
//        cl.add(new Samplemyclass("4", "National HighWay"));
//        cl.add(new Samplemyclass("5", "State Highway"));
//        cl.add(new Samplemyclass("6", "Outer Ring Road"));
//
//        cl1 = new ArrayList<>();
//        cl1.add(new Samplemyclass("0", "Select Victim Type"));
//        cl1.add(new Samplemyclass("1", "Ambulence"));
//        cl1.add(new Samplemyclass("2", "Auto"));
//        cl1.add(new Samplemyclass("3", "Auto -Goods"));
//        cl1.add(new Samplemyclass("4", "Bus"));
//        cl1.add(new Samplemyclass("5", "Bus-Mini"));
//        cl1.add(new Samplemyclass("6", "Bus-RTC"));
//        cl1.add(new Samplemyclass("7", "Bus-Private"));
//        cl1.add(new Samplemyclass("8", "Bus-School"));
//        cl1.add(new Samplemyclass("9", "Car"));
//        cl1.add(new Samplemyclass("10", "Cycliyst"));
//        cl1.add(new Samplemyclass("11", "DCIM"));
//        cl1.add(new Samplemyclass("12", "Pickup Van"));
//        cl1.add(new Samplemyclass("13", "Pickup Van"));
//        cl1.add(new Samplemyclass("12", "Self"));
//        cl1.add(new Samplemyclass("15", "Tanker"));
//        cl1.add(new Samplemyclass("16", "Truck-Medium"));
//        cl1.add(new Samplemyclass("17", "truck-Heavy"));
//        cl1.add(new Samplemyclass("18", "Trailer"));
//        cl1.add(new Samplemyclass("19", "Tracker"));
//        cl1.add(new Samplemyclass("20", "Two Wheeler"));
//        cl1.add(new Samplemyclass("21", "Others"));
//
//        cl2 = new ArrayList<>();
//        cl2.add(new Samplemyclass("0", "Select Accused Type"));
//        cl2.add(new Samplemyclass("1", "Ambulence"));
//        cl2.add(new Samplemyclass("2", "Auto"));
//        cl2.add(new Samplemyclass("3", "Auto -Goods"));
//        cl2.add(new Samplemyclass("4", "Bus"));
//        cl2.add(new Samplemyclass("5", "Bus-Mini"));
//        cl2.add(new Samplemyclass("6", "Bus-RTC"));
//        cl2.add(new Samplemyclass("7", "Bus-Private"));
//        cl2.add(new Samplemyclass("8", "Bus-School"));
//        cl2.add(new Samplemyclass("9", "Car"));
//        cl2.add(new Samplemyclass("10", "Cycliyst"));
//        cl2.add(new Samplemyclass("11", "DCIM"));
//        cl2.add(new Samplemyclass("12", "Pickup Van"));
//        cl2.add(new Samplemyclass("13", "Pickup Van"));
//        cl2.add(new Samplemyclass("12", "Self"));
//        cl2.add(new Samplemyclass("15", "Tanker"));
//        cl2.add(new Samplemyclass("16", "Truck-Medium"));
//        cl2.add(new Samplemyclass("17", "truck-Heavy"));
//        cl2.add(new Samplemyclass("18", "Trailer"));
//        cl2.add(new Samplemyclass("19", "Tracker"));
//        cl2.add(new Samplemyclass("20", "Two Wheeler"));
//        cl2.add(new Samplemyclass("21", "Others"));

        cl3 = new ArrayList<>();
        cl3.add(new Samplemyclass("0", "Select Victim Status"));
        cl3.add(new Samplemyclass("1", "Drunk"));
        cl3.add(new Samplemyclass("2", "Normal"));

        cl4 = new ArrayList<>();
        cl4.add(new Samplemyclass("0", "Select Accused Status"));
        cl4.add(new Samplemyclass("1", "Drunk"));
        cl4.add(new Samplemyclass("2", "Normal"));

    }

    class getCrimeStatus extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(EditAccidentData.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/CrimeOrAccidentStatus_Master";
            String METHOD_NAME = "CrimeOrAccidentStatus_Master";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=CrimeOrAccidentStatus_Master";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            envelope.setOutputSoapObject(request);
            Log.e("request", "" + request);

            try {
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.debug = true;
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
                httpTransport.call(SOAP_ACTION, envelope);
                Log.e("envelope", "" + envelope);
                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", "" + respone);
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.d(TAG, " Response: \n" + result);


                SoapObject root = (SoapObject) result.getProperty(0);
                SoapObject s_deals = (SoapObject) root.getProperty("Data");


                d3.clear();
                Samplemyclass wb2 = new Samplemyclass("0", "Select Status");
                d3.add(wb2);
                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);

                    String eid = s_deals_1.getProperty("Id").toString();
                    String en = s_deals_1.getProperty("Status").toString();
                    Samplemyclass wb1 = new Samplemyclass(eid, en);
                    d3.add(wb1);
                }
                System.out.println("mStringSchoo " + d3);


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (timeoutexcep) {
                Toast.makeText(EditAccidentData.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(EditAccidentData.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(EditAccidentData.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (Code == 0) {
                    if (d3 == null) {
                    } else {
                        if (d3.size() > 0) {
                            CrimeStatus();
                            SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(d3);
                            prefsEditor.putString("CrimeStatus", json);
                            prefsEditor.commit();

                        }
                    }

                } else {
                }
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

    class getVictimCat extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(EditAccidentData.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/VictimCategory_Master";
            String METHOD_NAME = "VictimCategory_Master";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=VictimCategory_Master";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            envelope.setOutputSoapObject(request);
            Log.e("request", "" + request);

            try {
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.debug = true;
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
                httpTransport.call(SOAP_ACTION, envelope);
                Log.e("envelope", "" + envelope);
                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", "" + respone);
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.d(TAG, " Response: \n" + result);


                SoapObject root = (SoapObject) result.getProperty(0);
                SoapObject s_deals = (SoapObject) root.getProperty("Data");


                d4.clear();
                Samplemyclass wb2 = new Samplemyclass("0", "Select Victim Type");
                d4.add(wb2);
                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    String eid = s_deals_1.getProperty("Id").toString();
                    String en = s_deals_1.getProperty("Categorie").toString();
                    Samplemyclass wb1 = new Samplemyclass(eid, en);
                    d4.add(wb1);
                }
                System.out.println("mStringSchoo " + d4);


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.e("result", "" + result);

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (timeoutexcep) {
                Toast.makeText(EditAccidentData.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(EditAccidentData.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(EditAccidentData.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {

                //Log.d(TAG,  "Re "+final_result.toString());

                if (Code == 0) {
                    if (d4 == null) {
                    } else {
                        if (d4.size() > 0) {
                            VictimCat();

                            SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(d4);
                            prefsEditor.putString("VictimCat", json);
                            prefsEditor.commit();
                        }
                    }
                    // SetValuesToLayout();
                } else {
                    // Toast.makeText(EditAccidentData.this,""+Message,Toast.LENGTH_LONG).show();
                }

                Log.e("result", "done");

                //tableview();

            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

    class getAccusedCat extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(EditAccidentData.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/AccusedCategory_Master";
            String METHOD_NAME = "AccusedCategory_Master";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=AccusedCategory_Master";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);

            envelope.setOutputSoapObject(request);


            Log.e("request", "" + request);

            try {
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.debug = true;
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
                httpTransport.call(SOAP_ACTION, envelope);
                Log.e("envelope", "" + envelope);
                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", "" + respone);
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.d(TAG, " Response: \n" + result);


                SoapObject root = (SoapObject) result.getProperty(0);
                SoapObject s_deals = (SoapObject) root.getProperty("Data");

                d5.clear();
                Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Type");
                d5.add(wb2);
                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    String eid = s_deals_1.getProperty("Id").toString();
                    String en = s_deals_1.getProperty("Categorie").toString();
                    Samplemyclass wb1 = new Samplemyclass(eid, en);
                    d5.add(wb1);
                }
                System.out.println("d5 " + d5);


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.e("result", "" + result);

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (timeoutexcep) {
                Toast.makeText(EditAccidentData.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(EditAccidentData.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(EditAccidentData.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {

                //Log.d(TAG,  "Re "+final_result.toString());

                if (Code == 0) {
                    if (d5 == null) {
                    } else {
                        if (d5.size() > 0) {
                            AccusedCat();

                            SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(d5);
                            prefsEditor.putString("AccusedCat", json);
                            prefsEditor.commit();
                        }
                    }
                    // SetValuesToLayout();
                } else {
                    // Toast.makeText(EditAccidentData.this,""+Message,Toast.LENGTH_LONG).show();
                }

                Log.e("result", "done");

                //tableview();

            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

    class getRoadType extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(EditAccidentData.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/RoadType_Master";
            String METHOD_NAME = "RoadType_Master";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=RoadType_Master";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            envelope.setOutputSoapObject(request);

            Log.e("request", "" + request);

            try {
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.debug = true;
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
                httpTransport.call(SOAP_ACTION, envelope);
                Log.e("envelope", "" + envelope);
                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", "" + respone);
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.d(TAG, " Response: \n" + result);
                SoapObject root = (SoapObject) result.getProperty(0);
                SoapObject s_deals = (SoapObject) root.getProperty("Data");


                d2.clear();
                Samplemyclass wb2 = new Samplemyclass("0", "Select Road Type");
                d2.add(wb2);
                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    String eid = s_deals_1.getProperty("Id").toString();
                    String en = s_deals_1.getProperty("RoadType").toString();
                    Samplemyclass wb1 = new Samplemyclass(eid, en);
                    d2.add(wb1);


                }
                System.out.println("mStringSchoo " + d2);


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.e("result", "" + result);

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (timeoutexcep) {
                Toast.makeText(EditAccidentData.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(EditAccidentData.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(EditAccidentData.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {

                //Log.d(TAG,  "Re "+final_result.toString());

                if (Code == 0) {
                    if (d2 == null) {
                    } else {
                        if (d2.size() > 0) {
                            RoadType();

                            SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(d2);
                            prefsEditor.putString("RoadType", json);
                            prefsEditor.commit();
                        }
                    }
                    // SetValuesToLayout();
                } else {
                    // Toast.makeText(EditAccidentData.this,""+Message,Toast.LENGTH_LONG).show();
                }

                Log.e("result", "done");

                //tableview();

            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

    class getAcc extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(EditAccidentData.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/AccidentType_Master";
            String METHOD_NAME = "AccidentType_Master";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=AccidentType_Master";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            envelope.setOutputSoapObject(request);
            Log.e("request", "" + request);

            try {
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.debug = true;
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
                httpTransport.call(SOAP_ACTION, envelope);
                Log.e("envelope", "" + envelope);
                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", "" + respone);
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.d(TAG, " Response: \n" + result);
                SoapObject root = (SoapObject) result.getProperty(0);
                SoapObject s_deals = (SoapObject) root.getProperty("Data");


                d1.clear();
                Samplemyclass wb1 = new Samplemyclass("0", "Select Accident Type");
                d1.add(wb1);
                for (int i = 0; i < s_deals.getPropertyCount(); i++) {

                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    System.out.println("********Count : " + s_deals_1.getPropertyCount());


                    e_id = s_deals_1.getProperty("Id").toString();
                    e_n = s_deals_1.getProperty("AccType").toString();
                    Samplemyclass wb = new Samplemyclass(e_id, e_n);
                    d1.add(wb);


                }
                System.out.println("mStringSchoo " + d1);


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.e("result", "" + result);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();

            }
            if (timeoutexcep) {
                Toast.makeText(EditAccidentData.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(EditAccidentData.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(EditAccidentData.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                //Log.d(TAG,  "Re "+final_result.toString());
                System.out.println("mStringSchoolName " + e_id);
                System.out.println("mStringSchoolGrade " + e_n);
                if (Code == 0) {
                    if (d1 == null) {
                    } else {
                        if (d1.size() > 0) {
                            AccidentType();
                            //  Student mStudentObject=new Student();
                            SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(d1);
                            prefsEditor.putString("AccidentType", json);
                            prefsEditor.commit();
                        }
                    }

//                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    SharedPreferences.Editor editor = sharedPrefs.edit();
//                    Gson gson = new Gson();
//                    String json = gson.toJson(d1);
//                    editor.putString(TAG, json);
//                    editor.commit();
                    // SetValuesToLayout();
                } else {

                    // Toast.makeText(EditAccidentData.this,""+Message,Toast.LENGTH_LONG).show();
                }

                Log.e("result", "done");
                //tableview();
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

    public void removeDB(String id) {
        dbHandler = new DbHandler(EditAccidentData.this);
        database = dbHandler.getWritableDatabase();
        String updateQuery = "DELETE FROM Location where _id = '" + id + "'";
        database.execSQL(updateQuery);
        database.close();
        Log.d(TAG, "Deleting Location value from Db" + id);
        Intent i = new Intent(EditAccidentData.this, AccidentOfflineList.class);  //your class
        startActivity(i);
    }
}
