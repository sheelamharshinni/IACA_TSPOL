package com.tecdatum.iaca_tspolice.DataEntry;

import android.Manifest;
import android.app.Activity;
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
import com.android.volley.toolbox.StringRequest;
;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Adapter.CustomYearPicker;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.CrimeSub_helper;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.Offline.Crime_Offline_Actvities.CrimeOfflineList;
import com.tecdatum.iaca_tspolice.DataBase.DbHandlerCrime;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
import com.tecdatum.iaca_tspolice.activity.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
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
import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

public class AddCrime extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String Crime_Master = URLS.CrimeMasters;
    private String AddCrime = URLS.AddCrime;
    private String GetCrimeRecordsData = URLS.GetCrimeRecordsData;
    ArrayList<Samplemyclass> year_array = new ArrayList<>();

    String year_Name, year_id;
    Spinner year;
    private String[] no_state = {"Complaint"};
    private String[] state = {"Select Status", "Detected", "Undetected"};
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = AddCrime.class.getSimpleName();
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
    Dialog C_dialog, vl_dialog, vdialog;
    TextView d_year, d_cno, d_ct, d_sct, d_dt, d_ad, d_ds, d_dis;
    Button C_cancel, C_confm;
    CustomDateTimePicker custom1, custom0;
    CustomYearPicker custom2;
    Timer timer = new Timer();
    int Code;
    Integer Code1;
    ArrayList<String> Cnumber_arraylist_ = new ArrayList<String>();
    ArrayList<String> Pscode_arraylist_ = new ArrayList<String>();
    ArrayList<String> Type_arraylist_ = new ArrayList<String>();
    ArrayList<String> Subtype_arraylist_ = new ArrayList<String>();
    ArrayList<String> Location_arraylist_ = new ArrayList<String>();
    ArrayList<String> Descr_arraylist_ = new ArrayList<String>();
    ArrayList<String> EnteredDate_arraylist_ = new ArrayList<String>();
    ArrayList<String> Status_arraylist_ = new ArrayList<String>();

    String Cnumber, Pscode, Type, Subtype, Location, Descr, EnteredDate, Status;
    TextView c_time;
    String formattedDate;
    ArrayList<Samplemyclass> countryList = new ArrayList<>();
    ArrayList<Samplemyclass> countryList1 = new ArrayList<>();
    ArrayList<Samplemyclass> d3 = new ArrayList<>();
    ArrayAdapter<Samplemyclass> adapter_state;
    ProgressDialog progressDialog;
    private Spinner Category, SCategory, detected_;
    Button Bt_c_dateReport, Bt_c_date, Bt_c_Year, Bt_cr_time, Bt_c_vl, Bt_c_rest, btn_c_offline, Bt_c_submit;
    int CrimeTypeid;
    Date strDate1,strDate2,strDate3,strDate4,strDate5,strDate6;
    String S_c_Year, item, item2, tv_Ad, tv_Long, tv_Lat, S_Descr, psJuri, S_Uname, S_Pass, s_Ps_name, S_longitude, S_latitude,
            Message, Message1, S_detected_id, S_detected_, S_psCode, S_psid, s_role, S_IMEi, S_CrimeNo, S_COMPYEAR, S_dateTime, S_dateTime1, S_location, Loc, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    String str11 = "1";
    EditText et_Locality, et_Desriptn, et_c_year, et_Crno;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_c_psname;
    RelativeLayout RL_sp_sct;

    DbHandlerCrime dbHandler;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crime);

        c_time = (TextView) findViewById(R.id.tv_time);
        tv_c_psname = (TextView) findViewById(R.id.tv_c__psname);
        RL_sp_sct = (RelativeLayout) findViewById(R.id.rl_sp_sct);
        Category = (Spinner) findViewById(R.id.spin_CrimeType);
        SCategory = (Spinner) findViewById(R.id.spin_Crimesubcatogry);
        detected_ = (Spinner) findViewById(R.id.spin_detected);
        Bt_c_date = (Button) findViewById(R.id.btn_c_date);
        Bt_c_dateReport = (Button) findViewById(R.id.Bt_c_dateReport);
        Bt_cr_time = (Button) findViewById(R.id.btn_c_time);
        Bt_c_Year = (Button) findViewById(R.id.Bt_c_Year);
        Bt_c_vl = (Button) findViewById(R.id.btn_c_vl);
        Bt_c_rest = (Button) findViewById(R.id.btn_c_reset);
        Bt_c_submit = (Button) findViewById(R.id.btn_c_submit);
        btn_c_offline = (Button) findViewById(R.id.btn_c_offline);
        et_Crno = (EditText) findViewById(R.id.et_c_cno);
        et_c_year = (EditText) findViewById(R.id.et_c_year);
        et_Locality = (EditText) findViewById(R.id.et_c_locality);
        et_Desriptn = (EditText) findViewById(R.id.et_CrimeDiscription);
        year = (Spinner) findViewById(R.id.spin_year);

        et_Locality.setFilters(new InputFilter[] { filter });
        et_Desriptn.setFilters(new InputFilter[] { filter });

//        et_Crno.addTextChangedListener(new TextWatcher() {
//            int keyDel;
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                et_Crno.setOnKeyListener(new View.OnKeyListener() {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//                        if (keyCode == KeyEvent.KEYCODE_DEL)
//                            keyDel = 1;
//                        return false;
//                    }
//                });
//                if (keyDel == 0) {
//                    int len = et_Crno.getText().length();
//                    if (len == 4) {
////                        et_Crno.setText(et_Crno.getText() + "/");
////                        et_Crno.setSelection(et_Crno.getText().length());
//                        keyDel = 1;
////
////                        d3 = new ArrayList<>();
////                        d6 = new ArrayList<>();
////                        d6.add(new Samplemyclass("0", "Complaint"));
////                        Log.e("Status1", "" + et_Crno.getText().toString().isEmpty());
////                        new getCrimeStatus().execute();
//                        setStatus();
//                    }
//                } else {
//                    keyDel = 0;
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//
//                //setStatus();
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//            }
//        });

        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        s_Ps_name = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        S_psCode = bb.getString("Pscode", "");

        tv_c_psname.setText(s_Ps_name);
        String s_OrgName = bb.getString("OrgName", "");
        TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        tv_OrgName.setText("" + s_OrgName);
        Bt_c_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To show current date in the datepicker
                custom1 = new CustomDateTimePicker(AddCrime.this,
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

                              S_dateTime=  year
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


                                            Bt_c_date.setText("");
                                            Bt_c_date.setText(year
                                                    + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                                    + " " + hour24 + ":" + min
                                                    + ":" + sec);
                                            S_dateTime = Bt_c_date.getText().toString();
                                            S_COMPYEAR = String.valueOf(year);

                                            Log.e("Date Final", "" + S_dateTime);


                                        } else {
                                            Toast.makeText(AddCrime.this, "Please Select Proper Crime Date", Toast.LENGTH_SHORT).show();
                                        }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }



//                                if (d > d1) {
//                                    Toast.makeText(AddCrime.this, "Please select proper date.", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Bt_c_date.setText("");
//                                    Bt_c_date.setText(year
//                                            + "-" + (monthNumber + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
//                                            + " " + hour24 + ":" + min
//                                            + ":" + sec);
//                                    S_dateTime = Bt_c_date.getText().toString();
//                                    S_COMPYEAR = String.valueOf(year);
//
//                                    Log.e("S_dateTime", "" + S_dateTime);
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
                custom0 = new CustomDateTimePicker(AddCrime.this,
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

                                S_dateTime1=  year
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
                                        Toast.makeText(AddCrime.this, "Please Select Proper Report Date", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }




//
//
//                                if (d > d1) {
//                                    Toast.makeText(AddCrime.this, "Please select proper date.", Toast.LENGTH_LONG).show();
//                                } else {
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
        Bt_c_Year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To show current date in the datepicker
                custom2 = new CustomYearPicker(AddCrime.this,
                        new CustomYearPicker.ICustomDateTimeListener() {
                            @Override
                            public void onSet(Dialog dialog, Calendar calendarSelected,
                                              Date dateSelected, int year, String monthFullName,
                                              String monthShortName, int monthNumber, int date,
                                              String weekDayFullName, String weekDayShortName,
                                              int hour24, int hour14, int min, int sec,
                                              String AM_PM) {


                                int d = Integer.parseInt(String.valueOf(calendarSelected.get(Calendar.YEAR)));
                                Calendar c = Calendar.getInstance();
                                int d1 = Integer.parseInt(String.valueOf(c.get(Calendar.YEAR)));

                                if (d > d1) {
                                    Toast.makeText(AddCrime.this, "Please Select Proper Year.", Toast.LENGTH_LONG).show();
                                } else {
                                    Bt_c_Year.setText("");
                                    Bt_c_Year.setText(year);
                                    S_c_Year = Bt_c_Year.getText().toString();
                                    Log.e("S_dateTime", "" + S_c_Year);
                                }

                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                custom2.set24HourFormat(true);
                custom2.setDate(Calendar.getInstance());
                custom2.showDialog();
            }
        });
        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckConnectivity1();
            }
        });
        btn_c_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CrimeOfflineList.class);
                startActivity(i);
            }
        });
        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_Desriptn.setText("");
                et_Crno.setText("");
                //  Et_yellow.setText("");
                Category.setSelection(0);
                SCategory.setSelection(0);
                detected_.setSelection(0);
                Bt_c_date.setText("Select Date and time");

                finish();
                Intent i = new Intent(getApplicationContext(), AddCrime.class);
                startActivity(i);
            }
        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                S_location = et_Locality.getText().toString();
                S_CrimeNo = et_Crno.getText().toString();
                S_c_Year = et_c_year.getText().toString();
                S_Descr = et_Desriptn.getText().toString();

                Log.e("1", "" + S_Uname);
                Log.e("2", "" + S_Pass);
                Log.e("3", "" + S_IMEi);
                Log.e("4", "" + S_CrimeNo);
                Log.e("5", "" + Cat1);
                Log.e("5.1", "" + item2);
                Log.e("6", "" + SubCat1);
                Log.e("6.1", "" + item);
                Log.e("7", "" + S_dateTime);
                Log.e("8", "" + S_location);
                Log.e("9", "" + S_detected_);
                Log.e("10", "" + S_latitude);
                Log.e("11", "" + S_longitude);
                Log.e("12", "" + S_Descr);
                if ((S_detected_ == null)) {
                    S_detected_ = "Complaint";

                    Log.e("13", "" + S_detected_);
                } else {
                    if ((S_detected_.trim().equalsIgnoreCase(""))) {
                        S_detected_ = "Complaint";
                        Log.e("133", "" + S_detected_);
                    } else {
                        Log.e("14", "" + S_detected_);
                    }
                    Log.e("14", "" + S_detected_);
                }
                Category = (Spinner) findViewById(R.id.spin_CrimeType);
                detected_ = (Spinner) findViewById(R.id.spin_detected);
                if (!(et_Crno.getText().toString().trim().isEmpty())) {
                    if (valFun(S_CrimeNo)) {
                        if (year != null && year.getSelectedItem() != null) {
                            if (!(year.getSelectedItem().toString().trim() == "Select Year")) {
                                if (Category != null && Category.getSelectedItem() != null) {
                                    if (!(Category.getSelectedItem().toString().trim() == "Select Crime Type")) {
                                        if (!(SCategory.getSelectedItem().toString().trim() == "Select Crime SubType")) {
                                            if (!(Bt_c_date.getText().toString().trim().equalsIgnoreCase("Crime Date and Time"))) {
                                                //          if (!(S_c_Year!=null)) {

//                                }else {}
//                                S_COMPYEAR
                                                if (!(detected_.getSelectedItem().toString().trim() == "Select Status")) {
                                                    if (!(et_Locality.getText().toString().trim().isEmpty())) {
                                                        if (!(et_Desriptn.getText().toString().trim().isEmpty())) {
//                                            if (year != null && year.getSelectedItem() != null) {
//                                                if (!(year.getSelectedItem().toString().trim() == "Select Year")) {

                                                          //  if (year_Name.equalsIgnoreCase(S_COMPYEAR)) {
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

//                                                                        if (strDate1.before(strDate2)) {
                                                                        if (strDate1.before(strDate2)|strDate1.equals(strDate2)) {
                                                                            alertDialogue_Conform();
                                                                        } else {
                                                                            Toast.makeText(AddCrime.this, "Please Select Proper Crime Date and Date Of Report", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    } else {
                                                                        alertDialogue_Conform();
                                                                    }
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }


                                                            } else {
                                                                Toast.makeText(AddCrime.this, "Please Select Proper Year / Proper Crime Date", Toast.LENGTH_SHORT).show();
                                                            }


//                                                } else {
//                                                    Toast.makeText(AddCrime.this, "Select Year", Toast.LENGTH_SHORT).show();
//                                                }
//                                            } else {
//                                                Toast.makeText(AddCrime.this, "Select Year", Toast.LENGTH_SHORT).show();}


                                                        } else {
                                                            Toast.makeText(AddCrime.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(AddCrime.this, "Location is mandatory", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(AddCrime.this, "Select Crime Status", Toast.LENGTH_SHORT).show();
                                                }
//                                } else {
//                                    Toast.makeText(AddCrime.this, "Select Date Of Report", Toast.LENGTH_SHORT).show();
//                                }
                                            } else {
                                                Toast.makeText(AddCrime.this, "Select Crime Date and Time", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(AddCrime.this, "Select Sub Crime Type", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(AddCrime.this, "Select Crime Type", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(AddCrime.this, "Select Crime Type", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddCrime.this, "Select Year", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddCrime.this, "Select Year", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddCrime.this, "Please Enter Valid Crime No", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddCrime.this, "Enter Crime No", Toast.LENGTH_SHORT).show();
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
        // setData();
        setData1();
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


    private void setStatus() {

        if (isConnectingToInternet(AddCrime.this)) {

            getCrimeStatus_Api();
        }


    }


    public static boolean valFun(String s) {
        if (s.length() != 4) {
            return false;
        } else {
            return true;
        }
    }

    private void alertDialogue_Conform() {
        C_dialog = new Dialog(AddCrime.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.crime_confm_new);
        C_dialog.show();
        d_cno = (TextView) C_dialog.findViewById(R.id.tv_c_cno);

        d_year = (TextView) C_dialog.findViewById(R.id.tv_c_year);
        d_ct = (TextView) C_dialog.findViewById(R.id.tv_c_ct);
        d_sct = (TextView) C_dialog.findViewById(R.id.tv_c_sct);
        d_dt = (TextView) C_dialog.findViewById(R.id.tv_c_dt);
        d_ad = (TextView) C_dialog.findViewById(R.id.tv_c_add);
        d_ds = (TextView) C_dialog.findViewById(R.id.tv_c_ds);
        d_dis = (TextView) C_dialog.findViewById(R.id.tv_c_dis);
        Log.e("S_detected_", "" + S_detected_);

        d_cno.setText(S_CrimeNo);
        d_year.setText(year_Name);
        d_ct.setText(item2);
        d_sct.setText(item);
        d_dt.setText(S_dateTime);
        d_ad.setText(S_location);
        d_ds.setText(S_detected_);
        d_dis.setText(S_Descr);

        C_confm = (Button) C_dialog.findViewById(R.id.btn_C_Confm);
        C_cancel = (Button) C_dialog.findViewById(R.id.tv_C_Cancel);

        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayLocation();


                if (isConnectingToInternet(AddCrime.this)) {
                    if(!(S_latitude.isEmpty()|S_longitude.isEmpty())) {
                        CheckConnectivity();
                    }else {
                        Toast.makeText(AddCrime.this, "Location is mandatory", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCrime.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("No Internet Connection..Do You Want to Save Data??");
                    alertDialog.setIcon(R.drawable.alert);
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            dialog.cancel();
                            Notnull_Crime();
//                            insertDB(S_CrimeNo, Cat1, item2, SubCat1, item,
//                                    S_dateTime, S_location, S_detected_, S_detected_id, S_latitude,
//                                    S_longitude, S_Descr);


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
            }
        });
        C_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });
    }

    private void alertDialogue_C1(int count) {
        C_dialog = new Dialog(AddCrime.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_confm);
        C_dialog.show();

        d_cno = (TextView) C_dialog.findViewById(R.id.tv_c_cno);
        d_ct = (TextView) C_dialog.findViewById(R.id.tv_c_ct);
        d_sct = (TextView) C_dialog.findViewById(R.id.tv_c_sct);
        d_dt = (TextView) C_dialog.findViewById(R.id.tv_c_dt);
        d_ad = (TextView) C_dialog.findViewById(R.id.tv_c_add);
        d_ds = (TextView) C_dialog.findViewById(R.id.tv_c_ds);
        d_dis = (TextView) C_dialog.findViewById(R.id.tv_c_dis);
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_c_pscode);
        pscode.setText(s_Ps_name);

//        if(!Cnumber_arraylist_.get(count)){
//Status_arraylist_
//        }
//

        d_cno.setText(Cnumber_arraylist_.get(count));


        d_ct.setText(Type_arraylist_.get(count));
        d_sct.setText(Subtype_arraylist_.get(count));
        d_dt.setText(EnteredDate_arraylist_.get(count));
        d_ad.setText(Location_arraylist_.get(count));
        d_dis.setText(Descr_arraylist_.get(count));
        d_ds.setText(Status_arraylist_.get(count));

        C_confm = (Button) C_dialog.findViewById(R.id.btn_C_Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });
    }

    private void SetValuesToLayout(int coun) {

        vdialog = new Dialog(AddCrime.this, R.style.HiddenTitleTheme);
        vdialog.setContentView(R.layout.vl_crime);
        vdialog.show();
        Log.e("t", "");
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

        if ((Type_arraylist_.size()) > 0 | Type_arraylist_.size() != 0) {
            Log.e("1", "" + Type_arraylist_.size());


            if ((Type_arraylist_.size()) == 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(EnteredDate_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(EnteredDate_arraylist_.get(3));
                t5.setText(Type_arraylist_.get(4));
                dt5.setText(EnteredDate_arraylist_.get(4));

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
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(EnteredDate_arraylist_.get(0));
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
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));

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
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(EnteredDate_arraylist_.get(2));

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
            if ((Type_arraylist_.size()) == 4) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(EnteredDate_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(EnteredDate_arraylist_.get(3));
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
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(EnteredDate_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(EnteredDate_arraylist_.get(3));
                t5.setText(Type_arraylist_.get(4));
                dt5.setText(EnteredDate_arraylist_.get(4));
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

    private void Crimetype(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            Category.setAdapter(adapter);
            Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {
                        RL_sp_sct.setVisibility(View.VISIBLE);
                        Samplemyclass list = (Samplemyclass) SCategory.getSelectedItem();
                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        Cat1 = country.getId();
                        CrimeTypeid = Integer.parseInt(Cat1);
                        item2 = country.getName();
                        Log.e("Cat1", "" + Cat1);

                        if (isConnectingToInternet(AddCrime.this)) {
                            getSubTypeCrimeType_Api(Cat1);


                        } else {
                            //verifysubtype(Cat1);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CrimeSubtype(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            SCategory.setAdapter(adapter);
            SCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {
                        Samplemyclass list = (Samplemyclass) SCategory.getSelectedItem();
                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                        SubCat1 = country.getId();
                        item = country.getName();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CheckConnectivity1() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            //    new getpay().execute();
            GetCrimeData();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
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

    private void CrimeStatus(ArrayList<Samplemyclass> str1) {

        Log.e("Status", "" + et_Crno.getText().toString().isEmpty());

//        if (et_Crno.getText().toString().isEmpty()) {
//            adapter_state = new ArrayAdapter<Samplemyclass>(getApplicationContext(),
//                    R.layout.spinner_item, d6);
//        } else {
        adapter_state = new ArrayAdapter<Samplemyclass>(getApplicationContext(),
                R.layout.spinner_item, d3);
        //   }

        detected_.setAdapter(adapter_state);
        detected_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {

                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    S_detected_id = country.getId();
                    S_detected_ = country.getName();
                    detected_.setSelection(position);
                    Log.e("crime_Genianty", "" + S_detected_);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    class getpay extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(AddCrime.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {
            String SOAP_ACTION = "http://tempuri.org/GetCrimeRecordsdata";
            String METHOD_NAME = "GetCrimeRecordsdata";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetCrimeRecordsdata";

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
                Subtype_arraylist_.clear();
                Location_arraylist_.clear();
                Descr_arraylist_.clear();
                EnteredDate_arraylist_.clear();
                Cnumber_arraylist_.clear();
                Status_arraylist_.clear();
                for (int j = 0; j < root.getPropertyCount(); j++) {
                    SoapObject s_deals = (SoapObject) root.getProperty(j);
                    System.out.println("********Count : " + s_deals.getPropertyCount());
                    for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                        Object property = s_deals.getProperty(i);
                        Cnumber = s_deals.getProperty("Cnumber").toString();
                        Pscode = s_deals.getProperty("Pscode").toString();
                        Type = s_deals.getProperty("Type").toString();
                        Subtype = s_deals.getProperty("Subtype").toString();
                        Location = s_deals.getProperty("Location").toString();
                        Descr = s_deals.getProperty("Descr").toString();
                        EnteredDate = s_deals.getProperty("Cdate").toString();
                        Status = s_deals.getProperty("Status").toString();
                    }
                    if (Cnumber == null || Cnumber.trim().equals("anyType{}") || Cnumber.trim()
                            .length() <= 0) {
                        Cnumber_arraylist_.add("");
                    } else {
                        Cnumber_arraylist_.add(Cnumber);
                    }
                    if (Status == null || Status.trim().equals("anyType{}") || Status.trim()
                            .length() <= 0) {
                        Status_arraylist_.add("");
                    } else {
                        Status_arraylist_.add(Status);
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
                    if (Subtype == null || Subtype.trim().equals("anyType{}") || Subtype.trim()
                            .length() <= 0) {
                        Subtype_arraylist_.add("");
                    } else {
                        Subtype_arraylist_.add(Subtype);
                    }
                    if (Location == null || Location.trim().equals("anyType{}") || Location.trim()
                            .length() <= 0) {
                        Location_arraylist_.add("");
                    } else {
                        Location_arraylist_.add(Location);
                    }
                    if (Descr == null || Descr.trim().equals("anyType{}") || Descr.trim()
                            .length() <= 0) {
                        Descr_arraylist_.add("");
                    } else {
                        Descr_arraylist_.add(Descr);
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
                Toast.makeText(AddCrime.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(AddCrime.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(AddCrime.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                //Log.d(TAG,  "Re "+final_result.toString());
                Log.e(TAG, "Cnumber : " + Cnumber_arraylist_);
                Log.e(TAG, "Cnumber : " + Pscode_arraylist_);
                Log.e(TAG, "Cnumber : " + Type_arraylist_);
                Log.e(TAG, "Cnumber : " + Subtype_arraylist_);
                Log.e(TAG, "Cnumber : " + Location_arraylist_);
                Log.e(TAG, "Cnumber : " + EnteredDate_arraylist_);

                int count = Type_arraylist_.size();

                Log.e(" count", "" + count);
                SetValuesToLayout(count);
                if (Code == 0) {
                } else {
                    Log.e("not sucess", "" + Code);
                    // Toast.makeText(AddCrime_View.this,""+Message,Toast.LENGTH_LONG).show();
                }
                Log.e("result", "done");
                //tableview();
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

    private void AddCrimeData() {
        progressDialog = new ProgressDialog(AddCrime.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("CrimeNumber", S_CrimeNo);
            jsonBody.put("CrimeYear", year_Name);
            jsonBody.put("CrimeTypeMaster_Id", Cat1);
            jsonBody.put("CrimeSubtypeMaster_Id", SubCat1);
            jsonBody.put("DateOfOffence", S_dateTime);
            jsonBody.put("Location", S_location);
            jsonBody.put("CrimeStatusMaster_Id", S_detected_id);
            jsonBody.put("Latitude", S_latitude);
            jsonBody.put("Longitude", S_longitude);
            jsonBody.put("Descr", S_Descr);
            jsonBody.put("PSID", S_psid);
            jsonBody.put("PSCode", S_psCode);
            jsonBody.put("crimeType", item2);
            jsonBody.put("CrimeSubType", item);
            jsonBody.put("DateofReport", S_dateTime1);
            jsonBody.put("CreatedBy", S_Uname);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:AddCrimeData" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AddCrime, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:AddCrimeData" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        if (code.equalsIgnoreCase("1")) {
                            progressDialog.dismiss();
                            Log.e("suceess", "" + Message);
                            C_dialog.dismiss();
                            et_Desriptn.setText("");
                            et_Crno.setText("");
                            //  Et_yellow.setText("");
                            Category.setSelection(0);
                            SCategory.setSelection(0);
                            detected_.setSelection(0);
                            year.setSelection(0);
                            et_c_year.setText("");
                            Bt_c_date.setText("Select Date and time");
                            S_CrimeNo = "";
                            Cat1 = "";
                            SubCat1 = "";
                            S_dateTime = "";
                            S_location = "";
                            S_detected_ = "";
                            S_latitude = "";
                            S_longitude = "";
                            S_Descr = "";
                            year_id = "";
                            year_Name = "";
//                            finish();
//                            Intent i = new Intent(getApplicationContext(), AddCrime.class);
//                            startActivity(i);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCrime.this);
                            alertDialog.setTitle("Success");
                            alertDialog.setMessage("Values Saved Succesfully");
                            alertDialog.setIcon(R.drawable.succs);
                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to invoke YES event
                                    dialog.cancel();
                                    finish();
                                    Intent i = new Intent(getApplicationContext(), AddCrime.class);
                                    startActivity(i);
                                }
                            });
                            alertDialog.show();
                        } else {

                            progressDialog.dismiss();

//                            Log.e("not sucess",""+Message);
//                            C_dialog.dismiss();
//                            et_Desriptn.setText("");
//                            et_Crno.setText("");
//                            //  Et_yellow.setText("");
//                            Category.setSelection(0);
//                            SCategory.setSelection(0);
//                            detected_.setSelection(0);
//                            Bt_c_date.setText("Select Date and time");

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCrime.this);
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    progressDialog.dismiss();
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
                    430000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void GetCrimeData() {
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetCrimeRecordsData, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:GetCrimeData" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("CrimeList");
                            int number = jArray.length();

                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Pscode_arraylist_.clear();
                                Type_arraylist_.clear();
                                Subtype_arraylist_.clear();
                                Location_arraylist_.clear();
                                Descr_arraylist_.clear();
                                EnteredDate_arraylist_.clear();
                                Cnumber_arraylist_.clear();
                                Status_arraylist_.clear();
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    //  String r_id=json_data.getString("Id").toString();

                                    Cnumber = json_data.getString("CrimeNumber").toString();
                                    Pscode = json_data.getString("PSCode").toString();
                                    Type = json_data.getString("CrimeTypeMaster_Id").toString();
                                    Subtype = json_data.getString("CrimeSubtypeMaster_Id").toString();
                                    Location = json_data.getString("Location").toString();
                                    Descr = json_data.getString("Descr").toString();
                                    EnteredDate = json_data.getString("DateOfEntry").toString();
                                    Status = json_data.getString("CrimeStatusMaster_Id").toString();

                                    if (Cnumber == null || Cnumber.trim().equals("anyType{}") || Cnumber.trim()
                                            .length() <= 0) {
                                        Cnumber_arraylist_.add("");
                                    } else {
                                        Cnumber_arraylist_.add(Cnumber);
                                    }
                                    if (Status == null || Status.trim().equals("anyType{}") || Status.trim()
                                            .length() <= 0) {
                                        Status_arraylist_.add("");
                                    } else {
                                        Status_arraylist_.add(Status);
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
                                    if (Subtype == null || Subtype.trim().equals("anyType{}") || Subtype.trim()
                                            .length() <= 0) {
                                        Subtype_arraylist_.add("");
                                    } else {
                                        Subtype_arraylist_.add(Subtype);
                                    }
                                    if (Location == null || Location.trim().equals("anyType{}") || Location.trim()
                                            .length() <= 0) {
                                        Location_arraylist_.add("");
                                    } else {
                                        Location_arraylist_.add(Location);
                                    }
                                    if (Descr == null || Descr.trim().equals("anyType{}") || Descr.trim()
                                            .length() <= 0) {
                                        Descr_arraylist_.add("");
                                    } else {
                                        Descr_arraylist_.add(Descr);
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
                            SetValuesToLayout(count);
                        } else {

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCrime.this);
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
            jsonBody.put("ActionName", "4");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Year" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Crime_Master, new Response.Listener<String>() {
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

    private void getCrimeStatus_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "3");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:CrimeStatus_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Crime_Master, new Response.Listener<String>() {
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

    private void getCrimeType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "6");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Crime_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Crime_Master, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Crime_Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        countryList.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Crime Type");
                                // Binds all strings into an array
                                countryList.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    countryList.add(wp);
                                }
                                if (countryList.size() > 0) {
                                    Crimetype(countryList);
                                }
                            }


                        } else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Crime Type");
                            // Binds all strings into an array
                            countryList.add(wp0);
                            if (countryList.size() > 0) {
                                Crimetype(countryList);
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

    private void getSubTypeCrimeType_Api(String Cid) {

        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("ActionName", "2");
            jsonBody.put("Id", "" + Cid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:CrimeSubtype_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Crime_Master, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:CrimeSubtype_Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        countryList1.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb0 = new Samplemyclass("0", "Select Crime SubType");
                                // Binds all strings into an array
                                countryList1.add(wb0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    countryList1.add(wp);
                                }
                                if (countryList1.size() > 0) {
                                    CrimeSubtype(countryList1);
                                }
                            }


                        } else {
                            Samplemyclass wb0 = new Samplemyclass("0", "Select Crime SubType");
                            // Binds all strings into an array
                            countryList1.add(wb0);
                            if (countryList.size() > 0) {
                                CrimeSubtype(countryList1);
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

    class pay extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(AddCrime.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/AddCrime_View";
            String METHOD_NAME = "AddCrime_View";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx";
            try {
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("username", S_Uname);
                request.addProperty("password", S_Pass);
                request.addProperty("imei", S_IMEi);
                request.addProperty("connectionType", "TabletPc");
                request.addProperty("crimeNumber", S_CrimeNo);
                request.addProperty("crimeTypeId", Cat1);
                request.addProperty("subCrimeTypeId", SubCat1);
                request.addProperty("dateTime", S_dateTime);
                request.addProperty("location", S_location);
                request.addProperty("detectedStatus", S_detected_);
                request.addProperty("latitude", S_latitude);
                request.addProperty("longitude", S_longitude);
                request.addProperty("description", S_Descr);
                envelope.setOutputSoapObject(request);

                Log.e("request", "" + request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.debug = true;
                Log.e("ADDCRIME_ENVOLOPE", "" + envelope);
                try {
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    if (envelope != null) {
                        String result1 = envelope.getResponse().toString();
                        Log.e("result_addcrime", "" + result1);
                    }
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
            } catch (Exception e) {
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
                Toast.makeText(AddCrime.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(AddCrime.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(AddCrime.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (Code1 != null) {
                    if (Code1 == 0) {
                        Log.e("suceess", "" + Message1);
                        C_dialog.dismiss();
                        et_Desriptn.setText("");
                        et_Crno.setText("");
                        //  Et_yellow.setText("");

                        Category.setSelection(0);
                        SCategory.setSelection(0);
                        detected_.setSelection(0);
                        Bt_c_date.setText("Select Date and time");
                        S_CrimeNo = "";
                        Cat1 = "";
                        SubCat1 = "";
                        S_dateTime = "";
                        S_location = "";
                        S_detected_ = "";
                        S_latitude = "";
                        S_longitude = "";
                        S_Descr = "";

                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCrime.this);
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
//                    et_Crno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCrime.this);
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("" + Message1);
                        alertDialog.setIcon(R.drawable.alertt);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        // Toast.makeText(AddCrime_View.this,""+Message,Toast.LENGTH_LONG).show();
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
            Notnull_Crime();
            //new pay().execute();

            AddCrimeData();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    public void Notnull_Crime() {


        if (year_Name == null) {
            year_Name = "";
        } else {
        }
        if (S_CrimeNo == null) {
            S_CrimeNo = "";
        } else {
        }
        if (Cat1 == null) {
            Cat1 = "";
        } else {
        }
        if (SubCat1 == null) {
            SubCat1 = "";
        } else {
        }
        if (item2 == null) {
            item2 = "";
        } else {
        }
        if (item == null) {
            item = "";
        } else {
        }
        if (S_dateTime == null) {
            S_dateTime = "";
        } else {
        }
        if (S_dateTime1 == null) {
            S_dateTime1 = "";
        } else {
        }
        if (S_location == null) {
            S_location = "";
        } else {
        }
        if (S_detected_ == null) {
            S_detected_ = "";
        } else {
        }

        if (S_detected_id == null) {
            S_detected_id = "";
        } else {
        }
        if (S_latitude == null) {
            S_latitude = "";
        } else {
        }
        if (S_longitude == null) {
            S_longitude = "";
        } else {
        }
        if (S_Descr == null) {
            S_Descr = "";
        } else {
        }
    }

    private void setData1() {

        if (isConnectingToInternet(AddCrime.this)) {

            ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connec != null && (
                    (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                            (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
                //  d6.add(new Samplemyclass("3", "Complaint"));
                getCrimeType_Api();
                getCrimeStatus_Api();
                getYear_Api();

            } else if (connec != null && (
                    (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                            (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                FragmentManager fm = getSupportFragmentManager();
                Connectivity td = new Connectivity();
                td.show(fm, "NO CONNECTION");

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e("msg", "onActivityResult");
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getApplicationContext(), "Permission Required", Toast.LENGTH_SHORT).show();
                        // splash();
                        finish();
                        break;
                    default:
                        break;
                }
                break;

        }
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
                final com.google.android.gms.common.api.Status status = result.getStatus();
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
                                    AddCrime.this, 1000);
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
                        et_Locality.setText(" " + S_loc);
                        // CurrentLocation.setText(Html.fromHtml(t66+S_loc));
                        // CurrentLocation.setText(S_loc);
                    }
                }
            } catch (IOException e) {

                et_Locality.setHint(" " + "Couldn't get the location.");
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

}