package com.tecdatum.iaca_tspolice.DataEntry;

import android.Manifest;
import android.app.AlertDialog;
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


import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker_disablepast;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.DataBase.DbHandler;
import com.tecdatum.iaca_tspolice.Offline.Accident_Offline_Actvities.AccidentOfflineList;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
import com.tecdatum.iaca_tspolice.activity.MainActivity;
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
import com.tecdatum.iaca_tspolice.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

public class AddAccident extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String AccidentMasters = URLS.AccidentMasters;
    private String AddAccident = URLS.AddAccident;
    private String GetAccidentRecordsData = URLS.GetAccidentRecordsData;
    ArrayList<Samplemyclass> year_array = new ArrayList<>();
    String year_Name, year_id;
    Spinner year;
    ProgressDialog progressDialog;
    ArrayList<Samplemyclass> cl3 = new ArrayList<>();
    ArrayList<Samplemyclass> cl4 = new ArrayList<>();
    ArrayList<Samplemyclass> d1 = new ArrayList<>();
    ArrayList<Samplemyclass> d2 = new ArrayList<>();

//    ArrayList<Samplemyclass> d6 = new ArrayList<>();

    ArrayList<Samplemyclass> d3 = new ArrayList<>();
    ArrayList<Samplemyclass> d4 = new ArrayList<>();
    ArrayList<Samplemyclass> d5 = new ArrayList<>();
    ArrayAdapter<Samplemyclass> adapter_state;
    private static final String TAG = AddAccident.class.getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    CustomDateTimePicker custom1;
    CustomDateTimePicker custom0;
    String Sat, Srt, Ssvc, Ssvs, Ssac, Ssas;
    private Spinner at, rt, svc, svs, sac, sas, detected_;
    String Sat1, Srt1, Svc1, Svs1, Sac1, Sas1;
    RelativeLayout RL1, RL2;
    int Code;
    String Code_new;
    Date strDate1, strDate2, strDate3, strDate4, strDate5, strDate6;


    EditText et_discription, et_roadNo, et_vap, et_aap, et_locality, et_c_year, et_Cno, et_Cla, et_Noi, et_Nod;
    Button Bt_c_rest, Bt_c_dateReport, Bt_c_submit, Bt_c_vl;
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    String S_dateTime1, S_discription, S_roadNo, S_vap, S_aap, S_c_Year, S_locality, S_c_year, S_Cno, S_Cla, S_Noi, S_Nod, S_latitude, S_longitude, S_COMPYEAR, S_dateTime, Message, S_cuurentDate,
            s_Ps_name, S_Uname, S_Pass, S_psCode, S_psid, s_role, S_IMEi, S_detected_id, S_detected_;
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
    String AccidentENo, Cdate, Cnumber, Pscode, Type, Subtype, Location, EnteredDate, CStatus,
            RoadType, Descr, Road_Number, NOofIn, NOofD;
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

    private String blockCharacterSet = "'~#^|$%&*!";

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_accident);

        RL1 = (RelativeLayout) findViewById(R.id.Layout9);
        RL2 = (RelativeLayout) findViewById(R.id.Layout11);

        at = (Spinner) findViewById(R.id.sp_accidentType);
        rt = (Spinner) findViewById(R.id.sp_roadType);
        year = (Spinner) findViewById(R.id.sp_year);
        svc = (Spinner) findViewById(R.id.sp_vc);
        svs = (Spinner) findViewById(R.id.sp_vs);
        sac = (Spinner) findViewById(R.id.sp_ac);
        sas = (Spinner) findViewById(R.id.sp_as);
        detected_ = (Spinner) findViewById(R.id.sp_detected);

        et_discription = (EditText) findViewById(R.id.et_Description);
        et_locality = (EditText) findViewById(R.id.et_Locality);

        et_locality.setFilters(new InputFilter[] { filter });
        et_discription.setFilters(new InputFilter[] { filter });

        et_roadNo = (EditText) findViewById(R.id.et_RoadNO);
        et_vap = (EditText) findViewById(R.id.VictAlocoPer);
        et_aap = (EditText) findViewById(R.id.AccusedAlocoPer);
        et_Cla = (EditText) findViewById(R.id.et_cla);
        et_Noi = (EditText) findViewById(R.id.et_noi);
        et_Nod = (EditText) findViewById(R.id.et_nod);
        et_Cno = (EditText) findViewById(R.id.et_crime_no);
        //   Bt_c_Year= (Button) findViewById(R.id.Bt_c_Year);
        et_c_year = (EditText) findViewById(R.id.et_c_year);
        Bt_c_dateReport = (Button) findViewById(R.id.Bt_c_dateReport);
        Bt_c_vl = (Button) findViewById(R.id.btn_accid_vl);
        Bt_c_rest = (Button) findViewById(R.id.btn_accid_reset);
        Bt_c_submit = (Button) findViewById(R.id.btn_accid_submit);
        btn_accid_offline = (Button) findViewById(R.id.btn_accid_offline);
        Bt_acc_date = (Button) findViewById(R.id.btn_acci_date);

        c_time = (TextView) findViewById(R.id.tv_acci_time);
        tv_c_psname = (TextView) findViewById(R.id.tv_accic_psname);

//        et_Cno.addTextChangedListener(new TextWatcher() {
//            int keyDel;
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                et_Cno.setOnKeyListener(new View.OnKeyListener() {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                        if (keyCode == KeyEvent.KEYCODE_DEL)
//                            keyDel = 1;
//                        return false;
//                    }
//                });
//                if (keyDel == 0) {
//                    int len = et_Cno.getText().length();
//                    if (len == 4) {
//                        keyDel = 1;
////                        et_Cno.setText(et_Cno.getText() + "/");
////                        et_Cno.setSelection(et_Cno.getText().length());
////                        keyDel = 1;
//////
//////                        if (et_Cno.getText().toString().isEmpty()) {
//////                            adapter_state = new ArrayAdapter<String>(getApplicationContext(),
//////                                    R.layout.spinner_item, no_state);
//////                        } else {
//////                            adapter_state = new ArrayAdapter<String>(getApplicationContext(),
//////                                    R.layout.spinner_item, state);
//////                        }
//////
//////                        detected_.setAdapter(adapter_state);
//                        setStatus();
//                    }
//                } else {
//                    keyDel = 0;
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//
//
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//
//                // TODO Auto-generated method stub
//            }
//        });


        Bt_acc_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custom1 = new CustomDateTimePicker(AddAccident.this,
                        new CustomDateTimePicker.ICustomDateTimeListener() {
                            @Override
                            public void onSet(Dialog dialog, Calendar calendarSelected,
                                              Date dateSelected, int year, String monthFullName,
                                              String monthShortName, int monthNumber, int date,
                                              String weekDayFullName, String weekDayShortName,
                                              int hour24, int hour14, int min, int sec,
                                              String AM_PM) {

                                int d = Integer.parseInt(String.valueOf(calendarSelected.get(Calendar.DAY_OF_MONTH)));
                                Calendar c = Calendar.getInstance();
                                int d1 = Integer.parseInt(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
                                S_dateTime = year
                                        + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                        + " " + hour24 + ":" + min
                                        + ":" + sec;
                                SimpleDateFormat sdf =
                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Calendar calander1 = Calendar.getInstance();
                                SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String offdate = simpledateformat.format(calander1.getTime());
                                try {


                                    if (S_dateTime != null) {
                                        strDate3 = sdf.parse(S_dateTime);
                                    }
                                    if (offdate != null) {
                                        strDate4 = sdf.parse(offdate);
                                    }

                                    Log.e("Date", "Crime Select Offence" + strDate3);
                                    Log.e("Date", "Crime Current" + strDate4);

                                    if (strDate3.before(strDate4)) {

                                        Bt_acc_date.setText("");
                                        Bt_acc_date.setText(year
                                                + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                                + " " + hour24 + ":" + min
                                                + ":" + sec);

                                        S_dateTime = Bt_acc_date.getText().toString();
                                        S_COMPYEAR = String.valueOf(year);


                                        Log.e("Date Final", "" + S_dateTime);


                                    } else {
                                        Toast.makeText(AddAccident.this, "Please Select Proper Crime Date", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


//                                if (d > d1) {
//
//                                    Log.e("Date Test", "" + d+"" + d1);
//                                 //   Log.e("Date Test", "" + d1);
//                                    Toast.makeText(AddAccident.this, "Please select proper date.", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Log.e("Date Test", "" + d+"" + d1);
//                                    Bt_acc_date.setText("");
//                                    Bt_acc_date.setText(year
//                                            + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
//                                            + " " + hour24 + ":" + min
//                                            + ":" + sec);
//
//                                    S_dateTime = Bt_acc_date.getText().toString();
//                                    S_COMPYEAR = String.valueOf(year);
//
//                                    Log.e("S_dateTime", "" + S_dateTime);
//
//                                }
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


        Bt_c_dateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To show current date in the datepicker
                custom0 = new CustomDateTimePicker(AddAccident.this,
                        new CustomDateTimePicker.ICustomDateTimeListener() {
                            @Override
                            public void onSet(Dialog dialog, Calendar calendarSelected,
                                              Date dateSelected, int year, String monthFullName,
                                              String monthShortName, int monthNumber, int date,
                                              String weekDayFullName, String weekDayShortName,
                                              int hour24, int hour14, int min, int sec,
                                              String AM_PM) {


                                int d = Integer.parseInt(String.valueOf(calendarSelected.get(Calendar.DAY_OF_MONTH)));
                                Calendar c = Calendar.getInstance();
                                int d1 = Integer.parseInt(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));


                                S_dateTime1 = year
                                        + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                        + " " + hour24 + ":" + min
                                        + ":" + sec;
                                SimpleDateFormat sdf =
                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Calendar calander1 = Calendar.getInstance();
                                SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String offdate = simpledateformat.format(calander1.getTime());
                                try {


                                    if (S_dateTime1 != null) {
                                        strDate5 = sdf.parse(S_dateTime1);
                                    }
                                    if (offdate != null) {
                                        strDate6 = sdf.parse(offdate);
                                    }

                                    Log.e("Date", "Crime Select Report" + strDate5);
                                    Log.e("Date", "Crime Current" + strDate6);

                                    if (strDate5.before(strDate6)) {

                                        Bt_c_dateReport.setText("");
                                        Bt_c_dateReport.setText(year
                                                + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                                + " " + hour24 + ":" + min
                                                + ":" + sec);
                                        S_dateTime1 = Bt_c_dateReport.getText().toString();
                                        Log.e("Bt_c_dateReport", "" + S_dateTime1);


                                        Log.e("Date Final", "" + S_dateTime);


                                    } else {
                                        Toast.makeText(AddAccident.this, "Please Select Proper Report Date", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


//                                if (d < d1) {
//                                    Toast.makeText(AddAccident.this, "Please select proper date.", Toast.LENGTH_LONG).show();
//                                } else {
//
//                                    Bt_c_dateReport.setText("");
//                                    Bt_c_dateReport.setText(year
//                                            + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
//                                            + " " + hour24 + ":" + min
//                                            + ":" + sec);
//                                    S_dateTime1 = Bt_c_dateReport.getText().toString();
//                                    Log.e("Bt_c_dateReport", "" + S_dateTime1);
//                                }


                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                custom0.set24HourFormat(true);
                custom0.setDate(Calendar.getInstance());
                custom0.showDialog();
            }
        });


        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        S_psCode = bb.getString("Pscode", "");
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

                finish();
                Intent i = new Intent(getApplicationContext(), AddAccident.class);
                startActivity(i);
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

                S_c_year = et_c_year.getText().toString();

                if (!(et_Cno.getText().toString().trim().isEmpty())) {
                    if (valFun(S_Cno)) {
                        if (year != null && year.getSelectedItem() != null) {
                            if (!(year.getSelectedItem().toString().trim() == "Select Year")) {

                                if (at != null && at.getSelectedItem() != null) {
                                    if (!(at.getSelectedItem().toString().trim() == "Select Accident Type")) {
                                        if (!(Bt_acc_date.getText().toString().trim().equalsIgnoreCase("Accident Date and Time"))) {


                                            if (!(detected_.getSelectedItem().toString().trim() == "Select Status")) {
                                                if (!(et_locality.getText().toString().trim().isEmpty())) {
                                                    if (!(et_discription.getText().toString().trim().isEmpty())) {

                                                        int i = Integer.parseInt(year_Name);
                                                        int j = Integer.parseInt(S_COMPYEAR);

//                                                        if (year_Name.equalsIgnoreCase(S_COMPYEAR)) {
                                                        Log.e("Date", "Master Year " + i);
                                                        Log.e("Date", "Select Year" + j);

                                                            if (((Integer.parseInt(year_Name)) >
                                                                    (Integer.parseInt(S_COMPYEAR)))
                                                                    | ((((Integer.parseInt(year_Name)) ==
                                                                    (Integer.parseInt(S_COMPYEAR)))))) {

                                                            SimpleDateFormat sdf =
                                                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                            try {

                                                                if (!(Bt_c_dateReport.getText().
                                                                        toString().equalsIgnoreCase
                                                                        ("Date Of Report") | S_dateTime1 == null)) {
                                                                    if (S_dateTime != null) {
                                                                        strDate1 = sdf.parse(S_dateTime);
                                                                    }
                                                                    if (S_dateTime1 != null) {
                                                                        strDate2 = sdf.parse(S_dateTime1);
                                                                    }

                                                                    Log.e("Date", "Crime Offence" + S_dateTime);
                                                                    Log.e("Date", "Crime Reporrt" + S_dateTime1);

                                                                    if (strDate1.before(strDate2)|strDate1.equals(strDate2)) {


                                                                        if (Sat.equalsIgnoreCase("27")) {

                                                                            if (!(et_Noi.getText().toString().trim().isEmpty())) {
                                                                                alertDialogue_Conform();
                                                                            } else {
                                                                                Toast.makeText(AddAccident.this, "Please Select No Of Injuries", Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        }
                                                                        if (Sat.equalsIgnoreCase("28")) {

                                                                            if (!(et_Noi.getText().toString().trim().isEmpty())) {
                                                                                alertDialogue_Conform();
                                                                            } else {
                                                                                Toast.makeText(AddAccident.this, "Please Select No Of Injuries", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                        if (Sat.equalsIgnoreCase("29")) {

                                                                            if (!(et_Nod.getText().toString().trim().isEmpty())) {
                                                                                alertDialogue_Conform();
                                                                            } else {
                                                                                Toast.makeText(AddAccident.this, "Please Select No Of Deaths", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                        if (Sat.equalsIgnoreCase("30")) {
                                                                            alertDialogue_Conform();
                                                                        }


                                                                    } else {
                                                                        Toast.makeText(AddAccident.this, "Please Select Proper Crime Date and Date Of Report", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    if (Sat.equalsIgnoreCase("27")) {

                                                                        if (!(et_Noi.getText().toString().trim().isEmpty())) {
                                                                            alertDialogue_Conform();
                                                                        } else {
                                                                            Toast.makeText(AddAccident.this, "Please Select No Of Injuries", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    }
                                                                    if (Sat.equalsIgnoreCase("28")) {

                                                                        if (!(et_Noi.getText().toString().trim().isEmpty())) {
                                                                            alertDialogue_Conform();
                                                                        } else {
                                                                            Toast.makeText(AddAccident.this, "Please Select No Of Injuries", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                    if (Sat.equalsIgnoreCase("29")) {

                                                                        if (!(et_Nod.getText().toString().trim().isEmpty())) {
                                                                            alertDialogue_Conform();
                                                                        } else {
                                                                            Toast.makeText(AddAccident.this, "Please Select No Of Deaths", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                    if (Sat.equalsIgnoreCase("30")) {
                                                                        alertDialogue_Conform();
                                                                    }
                                                                }
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }


                                                        } else {
                                                            Toast.makeText(AddAccident.this, "Please Select Proper Year / Proper Crime Date", Toast.LENGTH_SHORT).show();
                                                        }


                                                    } else {
                                                        Toast.makeText(AddAccident.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(AddAccident.this, "Location is mandatory", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(AddAccident.this, "Select Status", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(AddAccident.this, "Select Accident Date and Time", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(AddAccident.this, "Select Accident Type", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(AddAccident.this, "Select Accident Type", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(AddAccident.this, "Select Year", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddAccident.this, "Select Year", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddAccident.this, "Please Enter Valid Crime No", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddAccident.this, "Enter Crime No", Toast.LENGTH_SHORT).show();
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
                        // RL1.setVisibility(View.VISIBLE);
                        Ssvs = country.getId();
                        Svs1 = country.getName();
                    }
                    if (pos == 0) {
                        // RL1.setVisibility(View.GONE);
                        Ssvs = country.getId();
                        Svs1 = country.getName();
                    }
                    if (pos == 2) {
                        // RL1.setVisibility(View.GONE);
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
                        // RL2.setVisibility(View.VISIBLE);
                        Ssas = country.getId();
                        Sas1 = country.getName();
                    }
                    if (pos == 0) {
                        // RL2.setVisibility(View.GONE);
                        Ssas = country.getId();
                        Sas1 = country.getName();
                    }
                    if (pos == 2) {
                        // RL2.setVisibility(View.GONE);
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
                Intent a = new Intent(getApplicationContext(), Dashboard.class);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                finish();
            }
        });

//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        Gson gson = new Gson();
//        String json = sharedPrefs.getString(TAG, null);
//        java.lang.reflect.Type type = new TypeToken<ArrayList<Samplemyclass>>() {}.getType();
//        type_list = gson.fromJson(json, type);
//        try {
//            if (type_list.size() > 0) {
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try{
//        SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = appSharedPrefs.getString("AccidentType", "");
//        Samplemyclass mStudentObject = gson.fromJson(json, Samplemyclass.class);
//            Log.e("Arraylist",""+mStudentObject);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }

    public static boolean valFun(String s) {
        if (s.length() != 4)
            return false;
        else {
            return true;
        }
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
                                    AddAccident.this, 1000);
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

                et_locality.setHint(" " + "Couldn't get the location.");

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
        C_dialog = new Dialog(AddAccident.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.conform_accident);
        C_dialog.show();

        TextView d_ano = (TextView) C_dialog.findViewById(R.id.tv_acc_cno);
        TextView d_year = (TextView) C_dialog.findViewById(R.id.tv_acc_year);
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
            S_detected_id = "3";
        }

        d_ano.setText(S_Cno);
        d_year.setText(year_Name);
        d_at.setText(Sat1);
        d_dt.setText(S_dateTime);
        d_ad.setText(S_locality);
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

                if (isConnectingToInternet(AddAccident.this)) {
                    if(!(S_latitude.isEmpty()|S_longitude.isEmpty())) {
                        CheckConnectivity();
                    }else {
                        Toast.makeText(AddAccident.this, "Location is mandatory", Toast.LENGTH_SHORT).show();
                    }
                } else {


                }
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

        if (year_Name == null) {
            year_Name = "";
        } else {
        }

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

    public void SendDataToServer() {

        progressDialog = new ProgressDialog(AddAccident.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject request = new JSONObject();
        try {

            request.put("CrimeNumber", S_Cno);
            request.put("CrimeYear", year_Name);
            request.put("CrimeTypeMaster_Id", "5");
            //  request.put("CrimeSubtypeMaster_Id", "");
            request.put("DateOfOffence", S_dateTime);
            request.put("CrimeStatusMaster_Id", S_detected_id);
            request.put("Latitude", S_latitude);
            request.put("Longitude", S_longitude);
            request.put("Descr", S_discription);
            request.put("PSID", S_psid);
            request.put("PSCode", S_psCode);
            request.put("DateofReport", S_dateTime1);
            request.put("CrimeSubtypeMaster_Id", Sat);
            request.put("Location", S_locality);
            request.put("RoadTypeID", Srt);
            request.put("RoadNumber", S_roadNo);
            request.put("NoofInjuries", S_Noi);
            request.put("NoofDeaths", S_Nod);
            request.put("VictimCategoryID", Ssvc);
            request.put("VictimAlcoholicORNot", Svs1);
            //request.put("VictimAlcoholicPercentage", S_vap);
            request.put("AccusedCategoryID", Ssac);
            request.put("AccusedAlcoholicORNot", Sas1);
            // request.put("AccusedAlcoholicPercentage", S_aap);
            request.put("VictimVehicleNo", S_vap);
            request.put("AccusedVehicleNo", S_aap);
            request.put("CreatedBy", S_Uname);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = request.toString();
        Log.e("VOLLEY", "AddAccident_View" + "Input" + mRequestBody);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                AddAccident, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "AddAccident_View" + "Output" + response.toString());
                Code_new = response.optString("code").toString();
                Message = response.optString("Message").toString();


                if (Code_new.equalsIgnoreCase("1")) {
                    Log.e("suceess", "" + Message1);
                    C_dialog.dismiss();
                    et_discription.setText("");
                    et_roadNo.setText("");
                    et_vap.setText("");
                    et_aap.setText("");
                    et_Cno.setText("");
                    et_Cla.setText("");
                    et_Noi.setText("");
                    et_c_year.setText("");
                    et_Nod.setText("");
                    Bt_acc_date.setText("Accident Date and Time");
                    progressDialog.dismiss();
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

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddAccident.this);
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Values Saved Succesfully");
                    alertDialog.setIcon(R.drawable.succs);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            finish();
                            Intent i = new Intent(getApplicationContext(), AddAccident.class);
                            startActivity(i);
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();


                } else {
                    Log.e("not sucess", "" + Message);
                    C_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    progressDialog.dismiss();

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddAccident.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("" + Message);
                    alertDialog.setIcon(R.drawable.alertt);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                    // Toast.makeText(AddAccident_View.this,""+Message,Toast.LENGTH_LONG).show();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                4308000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);
    }

    private void GetDataFromServer() {
        try {
            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");
            S_psid = bb.getString("Psid", "");


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("HirarchyID", S_psid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:GetCrimeData" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetAccidentRecordsData, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:GetCrimeData" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("AccList");
                            int number = jArray.length();

                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {


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
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    //  String r_id=json_data.getString("Id").toString();

                                    Cnumber = json_data.getString("CrimeNumber").toString();
                                    Pscode = json_data.getString("PSCode").toString();
                                    // Type = json_data.getString("CrimeTypeMaster_Id").toString();
                                    Type = json_data.getString("CrimeSubtype").toString();
                                    Location = json_data.getString("Location").toString();
                                    Descr = json_data.getString("Descr").toString();
                                    EnteredDate = json_data.getString("DateOfEntry").toString();
                                    CStatus = json_data.getString("CrimeStatusMaster_Id").toString();


                                    // AccidentENo = json_data.getString("AccidentEntryNumber").toString();

                                    RoadType = json_data.getString("RoadTypeID").toString();
                                    Road_Number = json_data.getString("RoadNumber").toString();
                                    NOofIn = json_data.getString("NoofInjuries").toString();
                                    NOofD = json_data.getString("NoofDeaths").toString();
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

                            }


                            int count = Type_arraylist_.size();
                            Log.e(" count", "" + count);
                            SetValuesToLayout();
                        } else {

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddAccident.this);
                            alertDialog.setTitle("Response");
                            alertDialog.setMessage("" + message);
                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to invoke YES event
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    3510000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCrimeStatus_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "4");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:CrimeStatus_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AccidentMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:CrimeStatus_Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        d3.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Status");
                                // Binds all strings into an array
                                d3.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    d3.add(wp);
                                }
                                if (d3.size() > 0) {
                                    CrimeStatus(d3);
                                }
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAccused_Categories_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "3");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AccidentMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        d5.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Type");
                                // Binds all strings into an array
                                d5.add(wb2);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    d5.add(wp);
                                }
                                if (d5.size() > 0) {
                                    AccusedCat(d5);
                                }
                            }


                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Type");
                            // Binds all strings into an array
                            d5.add(wb2);
                            if (d5.size() > 0) {
                                AccusedCat(d5);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getVictims_Categories_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "3");
            //jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Victims_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AccidentMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Victims_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        d4.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb2 = new Samplemyclass("0", "Select Victim Type");
                                // Binds all strings into an array
                                d4.add(wb2);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    d4.add(wp);
                                }
                                if (d4.size() > 0) {
                                    VictimCat(d4);
                                }
                            }


                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Victim Type");
                            // Binds all strings into an array
                            d4.add(wb2);
                            if (d4.size() > 0) {
                                VictimCat(d4);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAccidentType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "1");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:AccidentMasters" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AccidentMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:AccidentMasters" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        d1.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb1 = new Samplemyclass("0", "Select Accident Type");
                                // Binds all strings into an array
                                d1.add(wb1);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    d1.add(wp);
                                }
                                if (d1.size() > 0) {
                                    AccidentType(d1);
                                }
                            }


                        } else {
                            Samplemyclass wb1 = new Samplemyclass("0", "Select Accident Type");
                            // Binds all strings into an array
                            d1.add(wb1);
                            if (d1.size() > 0) {
                                AccidentType(d1);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getRoadType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "2");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AccidentMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        d2.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Road Type");
                                // Binds all strings into an array
                                d2.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    d2.add(wp);
                                }
                                if (d2.size() > 0) {
                                    RoadType(d2);
                                }
                            }


                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Road Type");
                            // Binds all strings into an array
                            d2.add(wb2);
                            if (d2.size() > 0) {
                                RoadType(d2);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getYear_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "6");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Year" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AccidentMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Year" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        year_array.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Year");
                                // Binds all strings into an array
                                year_array.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    year_array.add(wp);
                                }
                                if (year_array.size() > 0) {
                                    YEAR(year_array);
                                }
                            }


                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Year");
                            // Binds all strings into an array
                            year_array.add(wb2);
                            if (year_array.size() > 0) {
                                YEAR(year_array);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            // new pay().execute();

            SendDataToServer();

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
        private final ProgressDialog dialog = new ProgressDialog(AddAccident.this);

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
                Toast.makeText(AddAccident.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(AddAccident.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(AddAccident.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                //Log.d(TAG,  "Re "+final_result.toString());

                if (Code == 0) {
                    SetValuesToLayout();
                } else {

                    // Toast.makeText(AddAccident_View.this,""+Message,Toast.LENGTH_LONG).show();
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

        vdialog = new Dialog(AddAccident.this, R.style.HiddenTitleTheme);
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

                t1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(EnteredDate_arraylist_.get(2));
                t4.setText(EnteredDate_arraylist_.get(3));
                t5.setText(EnteredDate_arraylist_.get(4));
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
                t1.setText(EnteredDate_arraylist_.get(0));
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
                t1.setText(EnteredDate_arraylist_.get(0));
                dt2.setText(Type_arraylist_.get(1));
                t2.setText(EnteredDate_arraylist_.get(1));
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
                t1.setText(EnteredDate_arraylist_.get(0));
                dt2.setText(Type_arraylist_.get(1));
                t2.setText(EnteredDate_arraylist_.get(1));
                dt3.setText(Type_arraylist_.get(2));
                t3.setText(EnteredDate_arraylist_.get(2));

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
                t1.setText(EnteredDate_arraylist_.get(0));
                dt2.setText(Type_arraylist_.get(1));
                t2.setText(EnteredDate_arraylist_.get(1));
                dt3.setText(Type_arraylist_.get(2));
                t3.setText(EnteredDate_arraylist_.get(2));
                dt4.setText(Type_arraylist_.get(3));
                t4.setText(EnteredDate_arraylist_.get(3));
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

                t1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(EnteredDate_arraylist_.get(2));
                t4.setText(EnteredDate_arraylist_.get(3));
                t5.setText(EnteredDate_arraylist_.get(4));

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
        C_dialog = new Dialog(AddAccident.this, R.style.MyAlertDialogStyle);
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
        d_accno.setText(EnteredDate_arraylist_.get(count));
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

            // new getpay().execute();
            //  new getAcc().execute();
            GetDataFromServer();

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
//            new getAcc().execute();
//            new getRoadType().execute();
//
//            new getVictimCat().execute();
//            new getAccusedCat().execute();

            //    d6.add(new Samplemyclass("3", "Complaint"));
            getYear_Api();
            getCrimeStatus_Api();
            getAccused_Categories_Api();
            getVictims_Categories_Api();
            getRoadType_Api();
            getAccidentType_Api();


        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void RoadType(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
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

    private void YEAR(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        year.setAdapter(adapter1);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) year.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    year_id = country.getId();
                    year_Name = country.getName();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void AccidentType(ArrayList<Samplemyclass> Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
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
                // Toast.makeText(AddAccident_View.this, " acc type"+Sat1, Toast.LENGTH_SHORT).show();
                // }
                if (Sat.equalsIgnoreCase("27")) {
                    et_Noi.setVisibility(View.VISIBLE);
                    et_Nod.setVisibility(View.GONE);
                }
                if (Sat.equalsIgnoreCase("28")) {
                    et_Noi.setVisibility(View.VISIBLE);
                    et_Nod.setVisibility(View.GONE);
                }
                if (Sat.equalsIgnoreCase("29")) {
                    et_Noi.setVisibility(View.VISIBLE);
                    et_Nod.setVisibility(View.VISIBLE);
                }
                if (Sat.equalsIgnoreCase("30")) {
                    et_Noi.setVisibility(View.GONE);
                    et_Nod.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void CrimeStatus(ArrayList<Samplemyclass> str1) {

//        if (et_Cno.getText().toString().isEmpty()) {
//            adapter_state = new ArrayAdapter<Samplemyclass>(getApplicationContext(),
//                    R.layout.spinner_item, d6);
//        } else {
        adapter_state = new ArrayAdapter<Samplemyclass>(getApplicationContext(),
                R.layout.spinner_item, d3);
//        }

        ///error coomming here

        detected_.setAdapter(adapter_state);
        detected_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                //   if (pos != 0) {
                S_detected_ = "";
                Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                Ssvc = country.getId();
                S_detected_id = country.getId();
                S_detected_ = country.getName();
                detected_.setSelection(position);
                //  S_detected_ = (String) detected_.getSelectedItem();
                Log.e("crime_Genianty", "" + S_detected_);
                // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void VictimCat(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter2 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
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

    private void AccusedCat(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter4 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
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

        if (isConnectingToInternet(AddAccident.this)) {
            //   d6.add(new Samplemyclass("3", "Complaint"));
            getCrimeStatus_Api();
        }


    }

    private void setData() {

        if (isConnectingToInternet(AddAccident.this)) {
            CheckConnectivity2();
        }

        cl3.add(new Samplemyclass("0", "Select Victim Status"));
        cl3.add(new Samplemyclass("1", "Alcoholic"));
        cl3.add(new Samplemyclass("2", "Non Alcoholic"));


        cl4.add(new Samplemyclass("0", "Select Accused Status"));
        cl4.add(new Samplemyclass("1", "Alcoholic"));
        cl4.add(new Samplemyclass("2", "Non Alcoholic"));

    }

//
//    class getVictimCat extends AsyncTask<Void, Void, Void> {
//        private final ProgressDialog dialog = new ProgressDialog(AddAccident_View.this);
//
//        @Override
//        protected void onPreExecute() {
//            this.dialog.setMessage("Loading data");
//            this.dialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... unused) {
//
//            String SOAP_ACTION = "http://tempuri.org/VictimCategory_Master";
//            String METHOD_NAME = "VictimCategory_Master";
//            String NAMESPACE = "http://tempuri.org/";
//            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=VictimCategory_Master";
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            request.addProperty("userName", S_Uname);
//            request.addProperty("Password", S_Pass);
//            request.addProperty("ImeI", S_IMEi);
//            envelope.setOutputSoapObject(request);
//            Log.e("request", "" + request);
//
//            try {
//                HttpTransportSE httpTransport = new HttpTransportSE(URL);
//                httpTransport.debug = true;
//                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
//                httpTransport.call(SOAP_ACTION, envelope);
//                Log.e("envelope", "" + envelope);
//                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
//                SoapObject respone = (SoapObject) envelope.getResponse();
//                Log.d("Res", "" + respone);
//                SoapObject result = (SoapObject) envelope.bodyIn;
//                Log.d(TAG, " Response: \n" + result);
//
//
//                SoapObject root = (SoapObject) result.getProperty(0);
//                SoapObject s_deals = (SoapObject) root.getProperty("Data");
//
//
//                d4.clear();
//                Samplemyclass wb2 = new Samplemyclass("0", "Select Victim Type");
//                d4.add(wb2);
//                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
//                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
//                    String eid = s_deals_1.getProperty("Id").toString();
//                    String en = s_deals_1.getProperty("Categorie").toString();
//                    Samplemyclass wb1 = new Samplemyclass(eid, en);
//                    d4.add(wb1);
//                }
//                System.out.println("mStringSchoo " + d4);
//
//
//            } catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            Log.e("result", "" + result);
//
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//            }
//            if (timeoutexcep) {
//                Toast.makeText(AddAccident_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
//            } else if (httpexcep) {
//                Toast.makeText(AddAccident_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
//            } else if (genexcep) {
//                Toast.makeText(AddAccident_View.this, "Please try later", Toast.LENGTH_LONG).show();
//            } else {
//
//                //Log.d(TAG,  "Re "+final_result.toString());
//
//                if (Code == 0) {
//                    if (d4 == null) {
//                    } else {
//                        if (d4.size() > 0) {
//                            VictimCat();
//
//                            SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
//                            Gson gson = new Gson();
//                            String json = gson.toJson(d4);
//                            prefsEditor.putString("VictimCat", json);
//                            prefsEditor.commit();
//                        }
//                    }
//                    // SetValuesToLayout();
//                } else {
//                    // Toast.makeText(AddAccident_View.this,""+Message,Toast.LENGTH_LONG).show();
//                }
//
//                Log.e("result", "done");
//
//                //tableview();
//
//            }
//            timeoutexcep = false;
//            httpexcep = false;
//            genexcep = false;
//        }
//    }
//
//    class getAccusedCat extends AsyncTask<Void, Void, Void> {
//        private final ProgressDialog dialog = new ProgressDialog(AddAccident_View.this);
//
//        @Override
//        protected void onPreExecute() {
//            this.dialog.setMessage("Loading data");
//            this.dialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... unused) {
//
//            String SOAP_ACTION = "http://tempuri.org/AccusedCategory_Master";
//            String METHOD_NAME = "AccusedCategory_Master";
//            String NAMESPACE = "http://tempuri.org/";
//            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=AccusedCategory_Master";
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            request.addProperty("userName", S_Uname);
//            request.addProperty("Password", S_Pass);
//            request.addProperty("ImeI", S_IMEi);
//
//            envelope.setOutputSoapObject(request);
//
//
//            Log.e("request", "" + request);
//
//            try {
//                HttpTransportSE httpTransport = new HttpTransportSE(URL);
//                httpTransport.debug = true;
//                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
//                httpTransport.call(SOAP_ACTION, envelope);
//                Log.e("envelope", "" + envelope);
//                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
//                SoapObject respone = (SoapObject) envelope.getResponse();
//                Log.d("Res", "" + respone);
//                SoapObject result = (SoapObject) envelope.bodyIn;
//                Log.d(TAG, " Response: \n" + result);
//
//
//                SoapObject root = (SoapObject) result.getProperty(0);
//                SoapObject s_deals = (SoapObject) root.getProperty("Data");
//
//                d5.clear();
//                Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Type");
//                d5.add(wb2);
//                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
//                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
//                    String eid = s_deals_1.getProperty("Id").toString();
//                    String en = s_deals_1.getProperty("Categorie").toString();
//                    Samplemyclass wb1 = new Samplemyclass(eid, en);
//                    d5.add(wb1);
//                }
//                System.out.println("d5 " + d5);
//
//
//            } catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            Log.e("result", "" + result);
//
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//            }
//            if (timeoutexcep) {
//                Toast.makeText(AddAccident_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
//            } else if (httpexcep) {
//                Toast.makeText(AddAccident_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
//            } else if (genexcep) {
//                Toast.makeText(AddAccident_View.this, "Please try later", Toast.LENGTH_LONG).show();
//            } else {
//
//                //Log.d(TAG,  "Re "+final_result.toString());
//
//                if (Code == 0) {
//                    if (d5 == null) {
//                    } else {
//                        if (d5.size() > 0) {
//                            AccusedCat();
//
//                            SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
//                            Gson gson = new Gson();
//                            String json = gson.toJson(d5);
//                            prefsEditor.putString("AccusedCat", json);
//                            prefsEditor.commit();
//                        }
//                    }
//                    // SetValuesToLayout();
//                } else {
//                    // Toast.makeText(AddAccident_View.this,""+Message,Toast.LENGTH_LONG).show();
//                }
//
//                Log.e("result", "done");
//
//                //tableview();
//
//            }
//            timeoutexcep = false;
//            httpexcep = false;
//            genexcep = false;
//        }
//    }
//
//    class getRoadType extends AsyncTask<Void, Void, Void> {
//        private final ProgressDialog dialog = new ProgressDialog(AddAccident_View.this);
//
//        @Override
//        protected void onPreExecute() {
//            this.dialog.setMessage("Loading data");
//            this.dialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... unused) {
//
//            String SOAP_ACTION = "http://tempuri.org/RoadType_Master";
//            String METHOD_NAME = "RoadType_Master";
//            String NAMESPACE = "http://tempuri.org/";
//            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=RoadType_Master";
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            request.addProperty("userName", S_Uname);
//            request.addProperty("Password", S_Pass);
//            request.addProperty("ImeI", S_IMEi);
//            envelope.setOutputSoapObject(request);
//
//            Log.e("request", "" + request);
//
//            try {
//                HttpTransportSE httpTransport = new HttpTransportSE(URL);
//                httpTransport.debug = true;
//                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
//                httpTransport.call(SOAP_ACTION, envelope);
//                Log.e("envelope", "" + envelope);
//                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
//                SoapObject respone = (SoapObject) envelope.getResponse();
//                Log.d("Res", "" + respone);
//                SoapObject result = (SoapObject) envelope.bodyIn;
//                Log.d(TAG, " Response: \n" + result);
//                SoapObject root = (SoapObject) result.getProperty(0);
//                SoapObject s_deals = (SoapObject) root.getProperty("Data");
//
//
//                d2.clear();
//                Samplemyclass wb2 = new Samplemyclass("0", "Select Road Type");
//                d2.add(wb2);
//                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
//                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
//                    String eid = s_deals_1.getProperty("Id").toString();
//                    String en = s_deals_1.getProperty("RoadType").toString();
//                    Samplemyclass wb1 = new Samplemyclass(eid, en);
//                    d2.add(wb1);
//
//
//                }
//                System.out.println("mStringSchoo " + d2);
//
//
//            } catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            Log.e("result", "" + result);
//
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//            }
//            if (timeoutexcep) {
//                Toast.makeText(AddAccident_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
//            } else if (httpexcep) {
//                Toast.makeText(AddAccident_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
//            } else if (genexcep) {
//                Toast.makeText(AddAccident_View.this, "Please try later", Toast.LENGTH_LONG).show();
//            } else {
//
//                //Log.d(TAG,  "Re "+final_result.toString());
//
//                if (Code == 0) {
//                    if (d2 == null) {
//                    } else {
//                        if (d2.size() > 0) {
//                            RoadType();
//
//                            SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
//                            Gson gson = new Gson();
//                            String json = gson.toJson(d2);
//                            prefsEditor.putString("RoadType", json);
//                            prefsEditor.commit();
//                        }
//                    }
//                    // SetValuesToLayout();
//                } else {
//                    // Toast.makeText(AddAccident_View.this,""+Message,Toast.LENGTH_LONG).show();
//                }
//
//                Log.e("result", "done");
//
//                //tableview();
//
//            }
//            timeoutexcep = false;
//            httpexcep = false;
//            genexcep = false;
//        }
//    }
//
//    class getAcc extends AsyncTask<Void, Void, Void> {
//        private final ProgressDialog dialog = new ProgressDialog(AddAccident_View.this);
//
//        @Override
//        protected void onPreExecute() {
//            this.dialog.setMessage("Loading data");
//            this.dialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... unused) {
//
//            String SOAP_ACTION = "http://tempuri.org/AccidentType_Master";
//            String METHOD_NAME = "AccidentType_Master";
//            String NAMESPACE = "http://tempuri.org/";
//            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=AccidentType_Master";
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            request.addProperty("userName", S_Uname);
//            request.addProperty("Password", S_Pass);
//            request.addProperty("ImeI", S_IMEi);
//            envelope.setOutputSoapObject(request);
//            Log.e("request", "" + request);
//
//            try {
//                HttpTransportSE httpTransport = new HttpTransportSE(URL);
//                httpTransport.debug = true;
//                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
//                httpTransport.call(SOAP_ACTION, envelope);
//                Log.e("envelope", "" + envelope);
//                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
//                SoapObject respone = (SoapObject) envelope.getResponse();
//                Log.d("Res", "" + respone);
//                SoapObject result = (SoapObject) envelope.bodyIn;
//                Log.d(TAG, " Response: \n" + result);
//                SoapObject root = (SoapObject) result.getProperty(0);
//                SoapObject s_deals = (SoapObject) root.getProperty("Data");
//
//
//                d1.clear();
//                Samplemyclass wb1 = new Samplemyclass("0", "Select Accident Type");
//                d1.add(wb1);
//                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
//
//                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
//                    System.out.println("********Count : " + s_deals_1.getPropertyCount());
//
//
//                    e_id = s_deals_1.getProperty("Id").toString();
//                    e_n = s_deals_1.getProperty("AccType").toString();
//                    Samplemyclass wb = new Samplemyclass(e_id, e_n);
//                    d1.add(wb);
//
//
//                }
//                System.out.println("mStringSchoo " + d1);
//
//
//            } catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            Log.e("result", "" + result);
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//
//            }
//            if (timeoutexcep) {
//                Toast.makeText(AddAccident_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
//            } else if (httpexcep) {
//                Toast.makeText(AddAccident_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
//            } else if (genexcep) {
//                Toast.makeText(AddAccident_View.this, "Please try later", Toast.LENGTH_LONG).show();
//            } else {
//                //Log.d(TAG,  "Re "+final_result.toString());
//                System.out.println("mStringSchoolName " + e_id);
//                System.out.println("mStringSchoolGrade " + e_n);
//                if (Code == 0) {
//                    if (d1 == null) {
//                    } else {
//                        if (d1.size() > 0) {
//                            AccidentType();
//                            //  Student mStudentObject=new Student();
//                            SharedPreferences appSharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//                            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
//                            Gson gson = new Gson();
//                            String json = gson.toJson(d1);
//                            prefsEditor.putString("AccidentType", json);
//                            prefsEditor.commit();
//                        }
//                    }
//
////                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////                    SharedPreferences.Editor editor = sharedPrefs.edit();
////                    Gson gson = new Gson();
////                    String json = gson.toJson(d1);
////                    editor.putString(TAG, json);
////                    editor.commit();
//                    // SetValuesToLayout();
//                } else {
//
//                    // Toast.makeText(AddAccident_View.this,""+Message,Toast.LENGTH_LONG).show();
//                }
//
//                Log.e("result", "done");
//                //tableview();
//            }
//            timeoutexcep = false;
//            httpexcep = false;
//            genexcep = false;
//        }
//    }




}