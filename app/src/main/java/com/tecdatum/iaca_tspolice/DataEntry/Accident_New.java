package com.tecdatum.iaca_tspolice.DataEntry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CrimeAdapter;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
import com.tecdatum.iaca_tspolice.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.tecdatum.iaca_tspolice.DataEntry.AddAccident.MY_PREFS_NAME;

public class Accident_New extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = Accident_New.class.getSimpleName();

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
    String S_latitude, S_longitude;
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
    CheckBox cb_all;
    ProgressDialog progressDialog;

    String formattedDate;
    TextView c_time, tv_c_psname;
    Spinner at, detected_, rt;
    String Message, Code_new;
    String Sat1, Sat, S_detected_id, S_detected_, Ssvc, Svc1, Sas1;
    ;
    Dialog C_dialog, vdialog;
    ArrayAdapter<Samplemyclass> adapter_state;
    ArrayList<Samplemyclass> roadnumber_GetAccidentSpot = new ArrayList<>();

    ArrayList<Samplemyclass> d1 = new ArrayList<>();
    ArrayList<Samplemyclass> d3 = new ArrayList<>();
    ArrayList<Samplemyclass> d2 = new ArrayList<>();
    ArrayList<Samplemyclass> roadnumber_arraylist = new ArrayList<>();
    ArrayList<Samplemyclass> roadnumber_Geometry = new ArrayList<>();
    ArrayList<Samplemyclass> roadnumber_Manoeuvervitim = new ArrayList<>();
    ArrayList<Samplemyclass> roadnumber_Manoeuvercrime = new ArrayList<>();
    ArrayList<Samplemyclass> roadnumber_GetTypeOfAccident = new ArrayList<>();
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
    ArrayList<Samplemyclass> d5 = new ArrayList<>();
    ArrayList<String> roadnumber_ids = new ArrayList<>();
    ArrayList<Samplemyclass> d4 = new ArrayList<>();
    private String AccidentMasters = URLS.AccidentMasters;
    private String GetRoadNumber = URLS.GetRoadNumber;
    private String GetCrimeVehicleManoeuvre = URLS.GetCrimeVehicleManoeuvre;
    private String GetTypeOfAccident = URLS.GetTypeOfAccident;
    private String GetAccidentSpot = URLS.GetAccidentSpot;
    private String GetRoadType = URLS.GetRoadType;
    private String RoadFeatures = URLS.RoadFeatures;
    private String AccidentDetails = URLS.AccidentDetails;
    private String GetAccidentRecordsData = URLS.GetAccidentRecordsData;
    String AccidentENo, Cdate, Cnumber, Pscode, Type, Subtype, Location, EnteredDate, CStatus,
            RoadType, Descr, Road_Number, NOofIn, NOofD;

    CheckBox[] button;
    Integer Length_checbox;
    CustomDateTimePicker custom1;
    CustomDateTimePicker custom0;
    Button Bt_c_rest, Bt_c_dateReport, Bt_c_submit, Bt_c_vl;
    Button btn_accid_offline, Bt_acc_date;
    String S_dateTime, S_COMPYEAR, S_dateTime1, Srt, Srt1;
    Date strDate1, strDate2, strDate3, strDate4, strDate5, strDate6;
    Spinner sp_roadnumber, sp_roadGeometry, svc, sp_Manoeuver, sac, sp_Manoeuvecrimer;
    String sp_roadnumber_id, sp_roadnumber_Name, sp_roadGeometry_id, sp_roadGeometry_Name, sp_Manoeuver_id, sp_Manoeuver_Name, Ssac, Sac1;
    RadioGroup rg_victimstatus, rg_Accusedstatus;
    String S_rg_victimstatus, S_rg_victimstatus_name, S_rg_Accusedstatus, S_rg_Accusedstatus_name;
    EditText et_Cno, et_year, VictAlocoPer, AccusedAlocoPer, et_Noi, et_Nod, et_locality, et_roadnumber, et_Description;
    String s_Ps_name, S_Uname, S_Pass, S_psCode, S_psid, s_role, S_IMEi;
    String S_VictAlocoPer, S_AccusedAlocoPer, S_et_Noi, S_et_Nod, S_et_locality, S_et_roadnumber, S_et_Cno, S_et_year, S_et_Description;
    String sp_Manoeuvecrimer_id, sp_Manoeuvecrimer_Name;
    Spinner sp_GetTypeOfAccident;
    String sp_GetTypeOfAccident_id, sp_GetTypeOfAccident_Name;
    Spinner sp_GetAccidentSpot;
    LinearLayout iteamsList;
    private Button btn_accid_reset, btn_accid_submit;
    RelativeLayout rl_roadnumber, rl_roadnumberedit;
    private PopupWindow mPopupWindow;
    ArrayList<Samplemyclass> arrayList21 = new ArrayList<Samplemyclass>();
    final ArrayList<Samplemyclass> al = new ArrayList<Samplemyclass>();
    String stringnew, string11, news;
    EditText et_lattitude, et_Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident__new);
        widgetInitialization();
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
    }

    private void widgetInitialization() {
        et_locality = (EditText) findViewById(R.id.et_Locality);
        et_locality.setFilters(new InputFilter[]{filter});
        c_time = (TextView) findViewById(R.id.tv_acci_time);
        tv_c_psname = (TextView) findViewById(R.id.tv_accic_psname);
        at = (Spinner) findViewById(R.id.sp_accidentType);
        et_Noi = (EditText) findViewById(R.id.et_noi);
        et_Nod = (EditText) findViewById(R.id.et_nod);
        detected_ = (Spinner) findViewById(R.id.sp_detected);
        Bt_acc_date = (Button) findViewById(R.id.btn_acci_date);
        Bt_c_dateReport = (Button) findViewById(R.id.Bt_c_dateReport);
        rt = (Spinner) findViewById(R.id.sp_roadType);
        sp_roadnumber = (Spinner) findViewById(R.id.sp_roadnumber);
        sp_roadGeometry = (Spinner) findViewById(R.id.sp_roadGeometry);
        svc = (Spinner) findViewById(R.id.sp_vc);
        VictAlocoPer = findViewById(R.id.VictAlocoPer);
        sp_Manoeuver = findViewById(R.id.sp_Manoeuver);
        sac = (Spinner) findViewById(R.id.sp_ac);
        AccusedAlocoPer = findViewById(R.id.AccusedAlocoPer);
        sp_Manoeuvecrimer = findViewById(R.id.sp_Manoeuvecrimer);
        sp_GetTypeOfAccident = findViewById(R.id.sp_GetTypeOfAccident);
        sp_GetAccidentSpot = findViewById(R.id.sp_GetAccidentSpot);
        rg_victimstatus = findViewById(R.id.rg_victimstatus);
        rg_Accusedstatus = findViewById(R.id.rg_Accusedstatus);
        iteamsList = findViewById(R.id.ll_specification);
        et_roadnumber = findViewById(R.id.et_roadnumber);
        rl_roadnumber = findViewById(R.id.rl_roadnumber);
        rl_roadnumberedit = findViewById(R.id.rl_roadnumberedit);
        cb_all = (CheckBox) findViewById(R.id.cb_all);
        btn_accid_reset = findViewById(R.id.btn_accid_reset);
        et_Cno = findViewById(R.id.et_crime_no);
        btn_accid_submit = findViewById(R.id.btn_accid_submit);
        et_year = findViewById(R.id.et_year);
        et_Description = findViewById(R.id.et_Description);

        et_lattitude = findViewById(R.id.et_lattitude);
        et_Longitude = findViewById(R.id.et_Longitude);
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        S_psCode = bb.getString("Pscode", "");
        s_Ps_name = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        tv_c_psname.setText(s_Ps_name);
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
        btn_accid_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringConversion();
                StringNotNUll();
                Validation();
            }
        });
        btn_accid_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), Accident_New.class);
                startActivity(intent);
            }
        });
        Bt_acc_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custom1 = new CustomDateTimePicker(Accident_New.this,
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
                                        Toast.makeText(Accident_New.this, "Please Select Proper Crime Date", Toast.LENGTH_SHORT).show();
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
                custom0 = new CustomDateTimePicker(Accident_New.this,
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
                                        Toast.makeText(Accident_New.this, "Please Select Proper Report Date", Toast.LENGTH_SHORT).show();
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

        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (cb_all.isChecked()) {
                    //  Toast.makeText(getBaseContext(), "Checked", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < roadnumber_GetAccidentSpot.size(); i++) {
                        button[i].setChecked(true);

                    }
                } else {
                    for (int i = 0; i < roadnumber_GetAccidentSpot.size(); i++) {
                        button[i].setChecked(false);

                    }
                }

                if (cb_all.isChecked()) {
                    //  Toast.makeText(getBaseContext(), "Checked", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < roadnumber_GetAccidentSpot.size(); i++) {
                        button[i].setChecked(true);

                    }
                } else {
                    for (int i = 0; i < roadnumber_GetAccidentSpot.size(); i++) {
                        button[i].setChecked(false);

                    }
                }


            }
        });
        Bt_c_vl = (Button) findViewById(R.id.btn_accid_vl);

        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckConnectivity1();
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

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Accident_New.this);
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

    private void SetValuesToLayout() {

        vdialog = new Dialog(Accident_New.this, R.style.HiddenTitleTheme);
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
        C_dialog = new Dialog(Accident_New.this, R.style.MyAlertDialogStyle);
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

    private void Validation() {
        if (!S_et_Cno.equalsIgnoreCase("")) {
            if (valFun(S_et_Cno)) {
                if (!S_et_year.equalsIgnoreCase("")) {
                    if (at != null && at.getSelectedItem() != null) {
                        if (!(at.getSelectedItem().toString().trim() == "Select Type of Offence")) {


                            if (detected_ != null && detected_.getSelectedItem() != null) {
                                if (!(detected_.getSelectedItem().toString().trim() == "Select Accident Status")) {


                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    try {
                                        if (!(Bt_acc_date.getText().toString().equalsIgnoreCase("Accident Date and Time") | S_dateTime == null)) {
                                            if (!(Bt_c_dateReport.getText().toString().equalsIgnoreCase("Date Of Report") | S_dateTime1 == null)) {
                                                if (S_dateTime != null) {
                                                    strDate1 = sdf.parse(S_dateTime);
                                                }
                                                if (S_dateTime1 != null) {
                                                    strDate2 = sdf.parse(S_dateTime1);
                                                }

                                                Log.e("Date", "Crime Offence" + S_dateTime);
                                                Log.e("Date", "Crime Reporrt" + S_dateTime1);

                                                if (strDate1.before(strDate2) | strDate1.equals(strDate2)) {


                                                    if (Sat.equalsIgnoreCase("27")) {

                                                        if (!(et_Noi.getText().toString().trim().isEmpty())) {
                                                            if (rt != null && rt.getSelectedItem() != null) {
                                                                if (!(rt.getSelectedItem().toString().trim() == "Select Road Type")) {
                                                                    if (Srt.equalsIgnoreCase("1") || Srt.equalsIgnoreCase("2")) {
                                                                        if (sp_roadnumber != null && sp_roadnumber.getSelectedItem() != null) {
                                                                            if (!(sp_roadnumber.getSelectedItem().toString().trim() == "Select Road Number")) {

                                                                                if (sp_roadGeometry != null && sp_roadGeometry.getSelectedItem() != null) {
                                                                                    if (!(sp_roadGeometry.getSelectedItem().toString().trim() == "Select Road Geometry")) {
                                                                                        if (svc != null && svc.getSelectedItem() != null) {
                                                                                            if (!(svc.getSelectedItem().toString().trim() == "Select Victim Vehicle")) {
                                                                                                if (sp_Manoeuver != null && sp_Manoeuver.getSelectedItem() != null) {
                                                                                                    if (!(sp_Manoeuver.getSelectedItem().toString().trim() == "Select Victim Vehicle Manoeuver")) {

                                                                                                        if (sac != null && sac.getSelectedItem() != null) {
                                                                                                            if (!(sac.getSelectedItem().toString().trim() == "Select Crime Vehicle")) {

                                                                                                                if (sp_Manoeuvecrimer != null && sp_Manoeuvecrimer.getSelectedItem() != null) {
                                                                                                                    if (!(sp_Manoeuvecrimer.getSelectedItem().toString().trim() == "Select Crime Vehicle Manoeuver")) {

                                                                                                                        if (sp_GetTypeOfAccident != null && sp_GetTypeOfAccident.getSelectedItem() != null) {
                                                                                                                            if (!(sp_GetTypeOfAccident.getSelectedItem().toString().trim() == "Select Type of Accident")) {
                                                                                                                                arrayList21 = SubmitClicked();
                                                                                                                                if (cb_all.isChecked() || (!arrayList21.isEmpty())) {
                                                                                                                                    string11 = "";

                                                                                                                                    // arrayList21 = SubmitClicked();
                                                                                                                                    for (int i = 0; i < arrayList21.size(); i++) {
                                                                                                                                        if (i != 0)
                                                                                                                                            string11 = string11 + ",";
                                                                                                                                        string11 = string11 + arrayList21.get(i).getId() + "";
                                                                                                                                    }
                                                                                                                                    alertDialogue_Conform();

                                                                                                                                    Log.e("PSLIST", string11);


                                                                                                                                } else {
                                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Cause of Accident Prone spot / Accident Spot", Toast.LENGTH_SHORT).show();

                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                                    }
                                                                                                                } else {
                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                                }

                                                                                                            } else {
                                                                                                                Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                            }
                                                                                                        } else {
                                                                                                            Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                        }


                                                                                                    } else {
                                                                                                        Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                    }
                                                                                                } else {
                                                                                                    Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                }


                                                                                            } else {
                                                                                                Toast.makeText(Accident_New.this, "Please Select Victim Vehicle", Toast.LENGTH_SHORT).show();

                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(Accident_New.this, "Please Select Victim Vehicle ", Toast.LENGTH_SHORT).show();

                                                                                        }


                                                                                    } else {
                                                                                        Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                                }

                                                                            } else {
                                                                                Toast.makeText(Accident_New.this, "Please Select Road Number ", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        } else {
                                                                            Toast.makeText(Accident_New.this, "Please Select Road Number ", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    } else {
                                                                        if (sp_roadGeometry != null && sp_roadGeometry.getSelectedItem() != null) {
                                                                            if (!(sp_roadGeometry.getSelectedItem().toString().trim() == "Select Road Geometry")) {
                                                                                if (svc != null && svc.getSelectedItem() != null) {
                                                                                    if (!(svc.getSelectedItem().toString().trim() == "Select Victim Vehicle")) {
                                                                                        if (sp_Manoeuver != null && sp_Manoeuver.getSelectedItem() != null) {
                                                                                            if (!(sp_Manoeuver.getSelectedItem().toString().trim() == "Select Victim Vehicle Manoeuver")) {

                                                                                                if (sac != null && sac.getSelectedItem() != null) {
                                                                                                    if (!(sac.getSelectedItem().toString().trim() == "Select Crime Vehicle")) {

                                                                                                        if (sp_Manoeuvecrimer != null && sp_Manoeuvecrimer.getSelectedItem() != null) {
                                                                                                            if (!(sp_Manoeuvecrimer.getSelectedItem().toString().trim() == "Select Crime Vehicle Manoeuver")) {

                                                                                                                if (sp_GetTypeOfAccident != null && sp_GetTypeOfAccident.getSelectedItem() != null) {
                                                                                                                    if (!(sp_GetTypeOfAccident.getSelectedItem().toString().trim() == "Select Type of Accident")) {
                                                                                                                        arrayList21 = SubmitClicked();
                                                                                                                        if (cb_all.isChecked() || (!arrayList21.isEmpty())) {
                                                                                                                            string11 = "";

                                                                                                                            // arrayList21 = SubmitClicked();
                                                                                                                            for (int i = 0; i < arrayList21.size(); i++) {
                                                                                                                                if (i != 0)
                                                                                                                                    string11 = string11 + ",";
                                                                                                                                string11 = string11 + arrayList21.get(i).getId() + "";
                                                                                                                            }
                                                                                                                            Log.e("PSLIST", string11);
                                                                                                                            alertDialogue_Conform();

                                                                                                                        } else {
                                                                                                                            Toast.makeText(Accident_New.this, "Please Select Cause of Accident Prone spot / Accident Spot", Toast.LENGTH_SHORT).show();

                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                    }
                                                                                                                } else {
                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                }
                                                                                                            } else {
                                                                                                                Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                            }
                                                                                                        } else {
                                                                                                            Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                        }

                                                                                                    } else {
                                                                                                        Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                    }
                                                                                                } else {
                                                                                                    Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                }


                                                                                            } else {
                                                                                                Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                        }


                                                                                    } else {
                                                                                        Toast.makeText(Accident_New.this, "Please Select Victim Vehicle", Toast.LENGTH_SHORT).show();

                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(Accident_New.this, "Please Select Victim Vehicle ", Toast.LENGTH_SHORT).show();

                                                                                }


                                                                            } else {
                                                                                Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        } else {
                                                                            Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    }
                                                                } else {
                                                                    Toast.makeText(Accident_New.this, "Please Select Road Type ", Toast.LENGTH_SHORT).show();

                                                                }
                                                            } else {
                                                                Toast.makeText(Accident_New.this, "Please Select Road Type ", Toast.LENGTH_SHORT).show();

                                                            }

                                                        } else {
                                                            Toast.makeText(Accident_New.this, "Please Enter No Of Injuries", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                    if (Sat.equalsIgnoreCase("28")) {

                                                        if (!(et_Noi.getText().toString().trim().isEmpty())) {
                                                            if (rt != null && rt.getSelectedItem() != null) {
                                                                if (!(rt.getSelectedItem().toString().trim() == "Select Road Type")) {
                                                                    if (Srt.equalsIgnoreCase("1") || Srt.equalsIgnoreCase("2")) {
                                                                        if (sp_roadnumber != null && sp_roadnumber.getSelectedItem() != null) {
                                                                            if (!(sp_roadnumber.getSelectedItem().toString().trim() == "Select Road Number")) {

                                                                                if (sp_roadGeometry != null && sp_roadGeometry.getSelectedItem() != null) {
                                                                                    if (!(sp_roadGeometry.getSelectedItem().toString().trim() == "Select Road Geometry")) {
                                                                                        if (svc != null && svc.getSelectedItem() != null) {
                                                                                            if (!(svc.getSelectedItem().toString().trim() == "Select Victim Vehicle")) {
                                                                                                if (sp_Manoeuver != null && sp_Manoeuver.getSelectedItem() != null) {
                                                                                                    if (!(sp_Manoeuver.getSelectedItem().toString().trim() == "Select Victim Vehicle Manoeuver")) {

                                                                                                        if (sac != null && sac.getSelectedItem() != null) {
                                                                                                            if (!(sac.getSelectedItem().toString().trim() == "Select Crime Vehicle")) {

                                                                                                                if (sp_Manoeuvecrimer != null && sp_Manoeuvecrimer.getSelectedItem() != null) {
                                                                                                                    if (!(sp_Manoeuvecrimer.getSelectedItem().toString().trim() == "Select Crime Vehicle Manoeuver")) {

                                                                                                                        if (sp_GetTypeOfAccident != null && sp_GetTypeOfAccident.getSelectedItem() != null) {
                                                                                                                            if (!(sp_GetTypeOfAccident.getSelectedItem().toString().trim() == "Select Type of Accident")) {
                                                                                                                                arrayList21 = SubmitClicked();
                                                                                                                                if (cb_all.isChecked() || (!arrayList21.isEmpty())) {
                                                                                                                                    string11 = "";

                                                                                                                                    // arrayList21 = SubmitClicked();
                                                                                                                                    for (int i = 0; i < arrayList21.size(); i++) {
                                                                                                                                        if (i != 0)
                                                                                                                                            string11 = string11 + ",";
                                                                                                                                        string11 = string11 + arrayList21.get(i).getId() + "";
                                                                                                                                    }
                                                                                                                                    Log.e("PSLIST", string11);
                                                                                                                                    alertDialogue_Conform();

                                                                                                                                } else {
                                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Cause of Accident Prone spot / Accident Spot", Toast.LENGTH_SHORT).show();

                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                                    }
                                                                                                                } else {
                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                                }

                                                                                                            } else {
                                                                                                                Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                            }
                                                                                                        } else {
                                                                                                            Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                        }


                                                                                                    } else {
                                                                                                        Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                    }
                                                                                                } else {
                                                                                                    Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                }


                                                                                            } else {
                                                                                                Toast.makeText(Accident_New.this, "Please Select Victim Vehicle", Toast.LENGTH_SHORT).show();

                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(Accident_New.this, "Please Select Victim Vehicle ", Toast.LENGTH_SHORT).show();

                                                                                        }


                                                                                    } else {
                                                                                        Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                                }

                                                                            } else {
                                                                                Toast.makeText(Accident_New.this, "Please Select Road Number ", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        } else {
                                                                            Toast.makeText(Accident_New.this, "Please Select Road Number ", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    } else {
                                                                        if (sp_roadGeometry != null && sp_roadGeometry.getSelectedItem() != null) {
                                                                            if (!(sp_roadGeometry.getSelectedItem().toString().trim() == "Select Road Geometry")) {
                                                                                if (svc != null && svc.getSelectedItem() != null) {
                                                                                    if (!(svc.getSelectedItem().toString().trim() == "Select Victim Vehicle")) {
                                                                                        if (sp_Manoeuver != null && sp_Manoeuver.getSelectedItem() != null) {
                                                                                            if (!(sp_Manoeuver.getSelectedItem().toString().trim() == "Select Victim Vehicle Manoeuver")) {

                                                                                                if (sac != null && sac.getSelectedItem() != null) {
                                                                                                    if (!(sac.getSelectedItem().toString().trim() == "Select Crime Vehicle")) {

                                                                                                        if (sp_Manoeuvecrimer != null && sp_Manoeuvecrimer.getSelectedItem() != null) {
                                                                                                            if (!(sp_Manoeuvecrimer.getSelectedItem().toString().trim() == "Select Crime Vehicle Manoeuver")) {

                                                                                                                if (sp_GetTypeOfAccident != null && sp_GetTypeOfAccident.getSelectedItem() != null) {
                                                                                                                    if (!(sp_GetTypeOfAccident.getSelectedItem().toString().trim() == "Select Type of Accident")) {
                                                                                                                        arrayList21 = SubmitClicked();
                                                                                                                        if (cb_all.isChecked() || (!arrayList21.isEmpty())) {
                                                                                                                            string11 = "";

                                                                                                                            // arrayList21 = SubmitClicked();
                                                                                                                            for (int i = 0; i < arrayList21.size(); i++) {
                                                                                                                                if (i != 0)
                                                                                                                                    string11 = string11 + ",";
                                                                                                                                string11 = string11 + arrayList21.get(i).getId() + "";
                                                                                                                            }
                                                                                                                            Log.e("PSLIST", string11);

                                                                                                                            alertDialogue_Conform();
                                                                                                                        } else {
                                                                                                                            Toast.makeText(Accident_New.this, "Please Select Cause of Accident Prone spot / Accident Spot", Toast.LENGTH_SHORT).show();

                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                    }
                                                                                                                } else {
                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                }
                                                                                                            } else {
                                                                                                                Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                            }
                                                                                                        } else {
                                                                                                            Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                        }

                                                                                                    } else {
                                                                                                        Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                    }
                                                                                                } else {
                                                                                                    Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                }


                                                                                            } else {
                                                                                                Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                        }


                                                                                    } else {
                                                                                        Toast.makeText(Accident_New.this, "Please Select Victim Vehicle", Toast.LENGTH_SHORT).show();

                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(Accident_New.this, "Please Select Victim Vehicle ", Toast.LENGTH_SHORT).show();

                                                                                }


                                                                            } else {
                                                                                Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        } else {
                                                                            Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    }
                                                                } else {
                                                                    Toast.makeText(Accident_New.this, "Please Select Road Type ", Toast.LENGTH_SHORT).show();

                                                                }
                                                            } else {
                                                                Toast.makeText(Accident_New.this, "Please Select Road Type ", Toast.LENGTH_SHORT).show();

                                                            }
                                                        } else {
                                                            Toast.makeText(Accident_New.this, "Please Select No Of Injuries", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                    if (Sat.equalsIgnoreCase("29")) {

                                                        if (!(et_Nod.getText().toString().trim().isEmpty())) {
                                                            if (!(et_Noi.getText().toString().trim().isEmpty())) {
                                                                if (rt != null && rt.getSelectedItem() != null) {
                                                                    if (!(rt.getSelectedItem().toString().trim() == "Select Road Type")) {
                                                                        if (Srt.equalsIgnoreCase("1") || Srt.equalsIgnoreCase("2")) {
                                                                            if (sp_roadnumber != null && sp_roadnumber.getSelectedItem() != null) {
                                                                                if (!(sp_roadnumber.getSelectedItem().toString().trim() == "Select Road Number")) {

                                                                                    if (sp_roadGeometry != null && sp_roadGeometry.getSelectedItem() != null) {
                                                                                        if (!(sp_roadGeometry.getSelectedItem().toString().trim() == "Select Road Geometry")) {
                                                                                            if (svc != null && svc.getSelectedItem() != null) {
                                                                                                if (!(svc.getSelectedItem().toString().trim() == "Select Victim Vehicle")) {
                                                                                                    if (sp_Manoeuver != null && sp_Manoeuver.getSelectedItem() != null) {
                                                                                                        if (!(sp_Manoeuver.getSelectedItem().toString().trim() == "Select Victim Vehicle Manoeuver")) {

                                                                                                            if (sac != null && sac.getSelectedItem() != null) {
                                                                                                                if (!(sac.getSelectedItem().toString().trim() == "Select Crime Vehicle")) {

                                                                                                                    if (sp_Manoeuvecrimer != null && sp_Manoeuvecrimer.getSelectedItem() != null) {
                                                                                                                        if (!(sp_Manoeuvecrimer.getSelectedItem().toString().trim() == "Select Crime Vehicle Manoeuver")) {

                                                                                                                            if (sp_GetTypeOfAccident != null && sp_GetTypeOfAccident.getSelectedItem() != null) {
                                                                                                                                if (!(sp_GetTypeOfAccident.getSelectedItem().toString().trim() == "Select Type of Accident")) {
                                                                                                                                    arrayList21 = SubmitClicked();
                                                                                                                                    if (cb_all.isChecked() || (!arrayList21.isEmpty())) {
                                                                                                                                        string11 = "";

                                                                                                                                        // arrayList21 = SubmitClicked();
                                                                                                                                        for (int i = 0; i < arrayList21.size(); i++) {
                                                                                                                                            if (i != 0)
                                                                                                                                                string11 = string11 + ",";
                                                                                                                                            string11 = string11 + arrayList21.get(i).getId() + "";
                                                                                                                                        }
                                                                                                                                        Log.e("PSLIST", string11);

                                                                                                                                        alertDialogue_Conform();
                                                                                                                                    } else {
                                                                                                                                        Toast.makeText(Accident_New.this, "Please Select Cause of Accident Prone spot / Accident Spot", Toast.LENGTH_SHORT).show();

                                                                                                                                    }
                                                                                                                                } else {
                                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                                    }

                                                                                                                } else {
                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                                }
                                                                                                            } else {
                                                                                                                Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                            }


                                                                                                        } else {
                                                                                                            Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                        }
                                                                                                    } else {
                                                                                                        Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                    }


                                                                                                } else {
                                                                                                    Toast.makeText(Accident_New.this, "Please Select Victim Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(Accident_New.this, "Please Select Victim Vehicle ", Toast.LENGTH_SHORT).show();

                                                                                            }


                                                                                        } else {
                                                                                            Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                                    }

                                                                                } else {
                                                                                    Toast.makeText(Accident_New.this, "Please Select Road Number ", Toast.LENGTH_SHORT).show();

                                                                                }
                                                                            } else {
                                                                                Toast.makeText(Accident_New.this, "Please Select Road Number ", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        } else {
                                                                            if (sp_roadGeometry != null && sp_roadGeometry.getSelectedItem() != null) {
                                                                                if (!(sp_roadGeometry.getSelectedItem().toString().trim() == "Select Road Geometry")) {
                                                                                    if (svc != null && svc.getSelectedItem() != null) {
                                                                                        if (!(svc.getSelectedItem().toString().trim() == "Select Victim Vehicle")) {
                                                                                            if (sp_Manoeuver != null && sp_Manoeuver.getSelectedItem() != null) {
                                                                                                if (!(sp_Manoeuver.getSelectedItem().toString().trim() == "Select Victim Vehicle Manoeuver")) {

                                                                                                    if (sac != null && sac.getSelectedItem() != null) {
                                                                                                        if (!(sac.getSelectedItem().toString().trim() == "Select Crime Vehicle")) {

                                                                                                            if (sp_Manoeuvecrimer != null && sp_Manoeuvecrimer.getSelectedItem() != null) {
                                                                                                                if (!(sp_Manoeuvecrimer.getSelectedItem().toString().trim() == "Select Crime Vehicle Manoeuver")) {

                                                                                                                    if (sp_GetTypeOfAccident != null && sp_GetTypeOfAccident.getSelectedItem() != null) {
                                                                                                                        if (!(sp_GetTypeOfAccident.getSelectedItem().toString().trim() == "Select Type of Accident")) {
                                                                                                                            arrayList21 = SubmitClicked();
                                                                                                                            if (cb_all.isChecked() || (!arrayList21.isEmpty())) {
                                                                                                                                string11 = "";

                                                                                                                                // arrayList21 = SubmitClicked();
                                                                                                                                for (int i = 0; i < arrayList21.size(); i++) {
                                                                                                                                    if (i != 0)
                                                                                                                                        string11 = string11 + ",";
                                                                                                                                    string11 = string11 + arrayList21.get(i).getId() + "";
                                                                                                                                }
                                                                                                                                Log.e("PSLIST", string11);

                                                                                                                                alertDialogue_Conform();
                                                                                                                            } else {
                                                                                                                                Toast.makeText(Accident_New.this, "Please Select Cause of Accident Prone spot / Accident Spot", Toast.LENGTH_SHORT).show();

                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                    }
                                                                                                                } else {
                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                                }
                                                                                                            } else {
                                                                                                                Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                            }

                                                                                                        } else {
                                                                                                            Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                        }
                                                                                                    } else {
                                                                                                        Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                    }


                                                                                                } else {
                                                                                                    Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                            }


                                                                                        } else {
                                                                                            Toast.makeText(Accident_New.this, "Please Select Victim Vehicle", Toast.LENGTH_SHORT).show();

                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(Accident_New.this, "Please Select Victim Vehicle ", Toast.LENGTH_SHORT).show();

                                                                                    }


                                                                                } else {
                                                                                    Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                                }
                                                                            } else {
                                                                                Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        }
                                                                    } else {
                                                                        Toast.makeText(Accident_New.this, "Please Select Road Type ", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                } else {
                                                                    Toast.makeText(Accident_New.this, "Please Select Road Type ", Toast.LENGTH_SHORT).show();

                                                                }
                                                            } else {
                                                                Toast.makeText(Accident_New.this, "Please Select No Of Injuries", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(Accident_New.this, "Please Select No Of Deaths", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                    if (Sat.equalsIgnoreCase("30")) {
                                                        if (rt != null && rt.getSelectedItem() != null) {
                                                            if (!(rt.getSelectedItem().toString().trim() == "Select Road Type")) {
                                                                if (Srt.equalsIgnoreCase("1") || Srt.equalsIgnoreCase("2")) {
                                                                    if (sp_roadnumber != null && sp_roadnumber.getSelectedItem() != null) {
                                                                        if (!(sp_roadnumber.getSelectedItem().toString().trim() == "Select Road Number")) {

                                                                            if (sp_roadGeometry != null && sp_roadGeometry.getSelectedItem() != null) {
                                                                                if (!(sp_roadGeometry.getSelectedItem().toString().trim() == "Select Road Geometry")) {
                                                                                    if (svc != null && svc.getSelectedItem() != null) {
                                                                                        if (!(svc.getSelectedItem().toString().trim() == "Select Victim Vehicle")) {
                                                                                            if (sp_Manoeuver != null && sp_Manoeuver.getSelectedItem() != null) {
                                                                                                if (!(sp_Manoeuver.getSelectedItem().toString().trim() == "Select Victim Vehicle Manoeuver")) {

                                                                                                    if (sac != null && sac.getSelectedItem() != null) {
                                                                                                        if (!(sac.getSelectedItem().toString().trim() == "Select Crime Vehicle")) {

                                                                                                            if (sp_Manoeuvecrimer != null && sp_Manoeuvecrimer.getSelectedItem() != null) {
                                                                                                                if (!(sp_Manoeuvecrimer.getSelectedItem().toString().trim() == "Select Crime Vehicle Manoeuver")) {

                                                                                                                    if (sp_GetTypeOfAccident != null && sp_GetTypeOfAccident.getSelectedItem() != null) {
                                                                                                                        if (!(sp_GetTypeOfAccident.getSelectedItem().toString().trim() == "Select Type of Accident")) {
                                                                                                                            arrayList21 = SubmitClicked();
                                                                                                                            if (cb_all.isChecked() || (!arrayList21.isEmpty())) {
                                                                                                                                string11 = "";

                                                                                                                                // arrayList21 = SubmitClicked();
                                                                                                                                for (int i = 0; i < arrayList21.size(); i++) {
                                                                                                                                    if (i != 0)
                                                                                                                                        string11 = string11 + ",";
                                                                                                                                    string11 = string11 + arrayList21.get(i).getId() + "";
                                                                                                                                }
                                                                                                                                Log.e("PSLIST", string11);

                                                                                                                                alertDialogue_Conform();
                                                                                                                            } else {
                                                                                                                                Toast.makeText(Accident_New.this, "Please Select Cause of Accident Prone spot / Accident Spot", Toast.LENGTH_SHORT).show();

                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                    }
                                                                                                                } else {
                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                                }
                                                                                                            } else {
                                                                                                                Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                            }

                                                                                                        } else {
                                                                                                            Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                        }
                                                                                                    } else {
                                                                                                        Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                    }


                                                                                                } else {
                                                                                                    Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                            }


                                                                                        } else {
                                                                                            Toast.makeText(Accident_New.this, "Please Select Victim Vehicle", Toast.LENGTH_SHORT).show();

                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(Accident_New.this, "Please Select Victim Vehicle ", Toast.LENGTH_SHORT).show();

                                                                                    }


                                                                                } else {
                                                                                    Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                                }
                                                                            } else {
                                                                                Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                            }

                                                                        } else {
                                                                            Toast.makeText(Accident_New.this, "Please Select Road Number ", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    } else {
                                                                        Toast.makeText(Accident_New.this, "Please Select Road Number ", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                } else {
                                                                    if (sp_roadGeometry != null && sp_roadGeometry.getSelectedItem() != null) {
                                                                        if (!(sp_roadGeometry.getSelectedItem().toString().trim() == "Select Road Geometry")) {
                                                                            if (svc != null && svc.getSelectedItem() != null) {
                                                                                if (!(svc.getSelectedItem().toString().trim() == "Select Victim Vehicle")) {
                                                                                    if (sp_Manoeuver != null && sp_Manoeuver.getSelectedItem() != null) {
                                                                                        if (!(sp_Manoeuver.getSelectedItem().toString().trim() == "Select Victim Vehicle Manoeuver")) {

                                                                                            if (sac != null && sac.getSelectedItem() != null) {
                                                                                                if (!(sac.getSelectedItem().toString().trim() == "Select Crime Vehicle")) {

                                                                                                    if (sp_Manoeuvecrimer != null && sp_Manoeuvecrimer.getSelectedItem() != null) {
                                                                                                        if (!(sp_Manoeuvecrimer.getSelectedItem().toString().trim() == "Select Crime Vehicle Manoeuver")) {

                                                                                                            if (sp_GetTypeOfAccident != null && sp_GetTypeOfAccident.getSelectedItem() != null) {
                                                                                                                if (!(sp_GetTypeOfAccident.getSelectedItem().toString().trim() == "Select Type of Accident")) {
                                                                                                                    arrayList21 = SubmitClicked();
                                                                                                                    if (cb_all.isChecked() || (!arrayList21.isEmpty())) {
                                                                                                                        string11 = "";

                                                                                                                        // arrayList21 = SubmitClicked();
                                                                                                                        for (int i = 0; i < arrayList21.size(); i++) {
                                                                                                                            if (i != 0)
                                                                                                                                string11 = string11 + ",";
                                                                                                                            string11 = string11 + arrayList21.get(i).getId() + "";
                                                                                                                        }
                                                                                                                        Log.e("PSLIST", string11);

                                                                                                                        alertDialogue_Conform();
                                                                                                                    } else {
                                                                                                                        Toast.makeText(Accident_New.this, "Please Select Cause of Accident Prone spot / Accident Spot", Toast.LENGTH_SHORT).show();

                                                                                                                    }
                                                                                                                } else {
                                                                                                                    Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                                }
                                                                                                            } else {
                                                                                                                Toast.makeText(Accident_New.this, "Please Select Type of Accident", Toast.LENGTH_SHORT).show();

                                                                                                            }
                                                                                                        } else {
                                                                                                            Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                        }
                                                                                                    } else {
                                                                                                        Toast.makeText(Accident_New.this, "Please Select Crime Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                                    }

                                                                                                } else {
                                                                                                    Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(Accident_New.this, "Please Select Crime Vehicle", Toast.LENGTH_SHORT).show();

                                                                                            }


                                                                                        } else {
                                                                                            Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(Accident_New.this, "Please Select Victim Vehicle Manoeuver", Toast.LENGTH_SHORT).show();

                                                                                    }


                                                                                } else {
                                                                                    Toast.makeText(Accident_New.this, "Please Select Victim Vehicle", Toast.LENGTH_SHORT).show();

                                                                                }
                                                                            } else {
                                                                                Toast.makeText(Accident_New.this, "Please Select Victim Vehicle ", Toast.LENGTH_SHORT).show();

                                                                            }


                                                                        } else {
                                                                            Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    } else {
                                                                        Toast.makeText(Accident_New.this, "Please Select Road Geometry ", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }
                                                            } else {
                                                                Toast.makeText(Accident_New.this, "Please Select Road Type ", Toast.LENGTH_SHORT).show();

                                                            }
                                                        } else {
                                                            Toast.makeText(Accident_New.this, "Please Select Road Type ", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }


                                                } else {
                                                    Toast.makeText(Accident_New.this, "Please Select Proper Crime Date and Date Of Report", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(Accident_New.this, "Please Select Date Of Report", Toast.LENGTH_SHORT).show();

                                            }
                                        } else {
                                            Toast.makeText(Accident_New.this, "Please Select Accident Date", Toast.LENGTH_SHORT).show();


                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please Select Accident Status ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Select Accident Status", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select Type of Offence ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select Type of Offence ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Year", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Accident_New.this, "Please Enter Valid Crime No", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Crime No.(Accident No)", Toast.LENGTH_SHORT).show();
        }
    }

    private void alertDialogue_Conform() {
        C_dialog = new Dialog(Accident_New.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.conform_accident_new);

        TextView tv_acc_cno = C_dialog.findViewById(R.id.tv_acc_cno);
        TextView tv_typeofoffence = C_dialog.findViewById(R.id.tv_typeofoffence);
        TextView tv_status = C_dialog.findViewById(R.id.tv_status);
        TextView tv_accdate = C_dialog.findViewById(R.id.tv_accdate);
        TextView tv_dateofreport = C_dialog.findViewById(R.id.tv_dateofreport);
        TextView tv_injuredpersons = C_dialog.findViewById(R.id.tv_injuredpersons);
        TextView tv_deaths = C_dialog.findViewById(R.id.tv_deaths);
        TextView tv_roadtype = C_dialog.findViewById(R.id.tv_roadtype);
        TextView tv_roadnumber = C_dialog.findViewById(R.id.tv_roadnumber);
        TextView tv_roadgeometry = C_dialog.findViewById(R.id.tv_roadgeometry);
        TextView tv_victimstatus = C_dialog.findViewById(R.id.tv_victimstatus);
        TextView tv_victimvehicle = C_dialog.findViewById(R.id.tv_victimvehicle);
        TextView tv_victimvehicleno = C_dialog.findViewById(R.id.tv_victimvehicleno);
        TextView tv_victimManoeuver = C_dialog.findViewById(R.id.tv_victimManoeuver);
        TextView tv_crimevehicle = C_dialog.findViewById(R.id.tv_crimevehicle);
        TextView tv_crimevstatus = C_dialog.findViewById(R.id.tv_crimevstatus);
        TextView tv_crimevehicleno = C_dialog.findViewById(R.id.tv_crimevehicleno);
        TextView tv_crimeManoeuver = C_dialog.findViewById(R.id.tv_crimeManoeuver);
        TextView tv_typeofaccident = C_dialog.findViewById(R.id.tv_typeofaccident);
        TextView tv_Location = C_dialog.findViewById(R.id.tv_Location);


        tv_acc_cno.setText(S_et_Cno);
        tv_typeofoffence.setText(Sat1);
        tv_status.setText(S_detected_);
        tv_accdate.setText(S_dateTime);
        tv_dateofreport.setText(S_dateTime1);
        tv_injuredpersons.setText(S_et_Noi);
        tv_deaths.setText(S_et_Nod);
        tv_roadtype.setText(Srt1);
        tv_roadnumber.setText(sp_roadnumber_Name);
        tv_victimvehicle.setText(Svc1);
        tv_roadgeometry.setText(sp_roadGeometry_Name);
        tv_victimstatus.setText(S_rg_victimstatus_name);
        tv_victimvehicleno.setText(S_VictAlocoPer);
        tv_victimManoeuver.setText(sp_Manoeuver_Name);
        tv_crimevehicle.setText(Sac1);
        tv_crimevstatus.setText(S_rg_Accusedstatus_name);
        tv_crimevehicleno.setText(S_AccusedAlocoPer);
        tv_crimeManoeuver.setText(sp_Manoeuvecrimer_Name);
        tv_typeofaccident.setText(sp_GetTypeOfAccident_Name);
        tv_Location.setText(S_et_locality);

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_acci_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.tv_acci_Cancel);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayLocation();

                if (isConnectingToInternet(Accident_New.this)) {
                    if (!(S_latitude.isEmpty() | S_longitude.isEmpty())) {
                        CheckConnectivity();
                    } else {
                        Toast.makeText(Accident_New.this, "Location is mandatory", Toast.LENGTH_SHORT).show();
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
        C_dialog.show();
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

    public void SendDataToServer() {

        progressDialog = new ProgressDialog(Accident_New.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject request = new JSONObject();
        try {
            request.put("Action", "Save");
            request.put("CrimeTypeMaster_Id", Sat);

            request.put("CrimeNumber", S_et_Cno);
            request.put("CrimeYear", S_et_year);
            request.put("PSID", S_psid);
            request.put("PSCode", S_psCode);
            request.put("CrimeSubtypeMaster_Id", "1");
            request.put("Latitude", S_latitude);
            request.put("Longitude", S_longitude);
            request.put("Location", S_et_locality);
            request.put("Descr", S_et_Description);
            request.put("DateOfOffence", S_dateTime);
            request.put("DateOfEntry", S_dateTime1);
            //  request.put("CrimeStatusMaster_Id", Srt);
            request.put("Sector_Id", "");
            request.put("Patrol_Id", "");
            request.put("BC_Id", "");
            request.put("Dateofreport", "");
            request.put("NoofInjuries", S_et_Noi);
            request.put("NoofDeaths", S_et_Nod);
            request.put("RoadTypeID", Srt);
            request.put("RoadNumber", sp_roadnumber_id);
            request.put("VictimCategoryID", Ssvc);
            request.put("VictimAlcoholicORNot", S_rg_victimstatus);
            request.put("VictimVehicleNo", S_VictAlocoPer);
            request.put("AccusedCategoryID", Ssac);
            request.put("AccusedAlcoholicORNot", S_rg_Accusedstatus_name);
            request.put("AccusedVehicleNo", S_AccusedAlocoPer);
            request.put("CreatedBy", S_Uname);
            request.put("CrimeVehicleManoeuvre_iD", sp_Manoeuvecrimer_id);
            request.put("VictimVehicleManoeuvre_Id", sp_Manoeuver_id);
            request.put("TypeOfAccident_Id", sp_GetTypeOfAccident_id);
            request.put("AccidentSpotRemarks_Id", string11);
            request.put("RoadFeatures_Id", sp_roadGeometry_id);
            request.put("NationalHighways_Id", S_Uname);
            request.put("StateHighways_Id", S_Uname);
            request.put("Crimes_Id", S_Uname);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = request.toString();
        Log.e("VOLLEY", "AddAccident_View" + "Input" + mRequestBody);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                AccidentDetails, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "AddAccident_View" + "Output" + response.toString());
                Code_new = response.optString("code").toString();
                Message = response.optString("Message").toString();


                if (Code_new.equalsIgnoreCase("1")) {

                    C_dialog.dismiss();


                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Accident_New.this);
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Values Saved Succesfully");
                    alertDialog.setIcon(R.drawable.succs);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            finish();
                            Intent i = new Intent(getApplicationContext(), Accident_New.class);
                            startActivity(i);
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();


                } else {
                    Log.e("not sucess", "" + Message);
                    C_dialog.dismiss();

                    progressDialog.dismiss();

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Accident_New.this);
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

    public static boolean valFun(String s) {
        if (s.length() != 4)
            return false;
        else {
            return true;
        }
    }

    public ArrayList<Samplemyclass> SubmitClicked() {
        Log.e("Cromeporan", "List");
        for (int i = 0; i < roadnumber_GetAccidentSpot.size(); i++) {
            final int finalI = i;

            if (button[i].isChecked()) {
                al.add(roadnumber_GetAccidentSpot.get(finalI));
            } else {

            }
        }


        return al;


    }

    public void StringConversion() {
        S_et_year = et_year.getText().toString();
        S_et_Cno = et_Cno.getText().toString();
        S_VictAlocoPer = VictAlocoPer.getText().toString();
        S_AccusedAlocoPer = AccusedAlocoPer.getText().toString();
        S_et_Noi = et_Noi.getText().toString();
        S_et_Nod = et_Nod.getText().toString();
        S_et_locality = et_locality.getText().toString();
        S_et_roadnumber = et_roadnumber.getText().toString();
        S_et_Description = et_Description.getText().toString()
        ;
    }

    public void StringNotNUll() {

        if (S_et_year != null) {
        } else {
            S_et_year = "";
        }


        if (S_et_Cno != null) {
        } else {
            S_et_Cno = "";
        }


        if (S_VictAlocoPer != null) {
        } else {
            S_VictAlocoPer = "";
        }

        if (S_AccusedAlocoPer != null) {
        } else {
            S_AccusedAlocoPer = "";
        }
        if (S_et_Noi != null) {
        } else {
            S_et_Noi = "";
        }
        if (S_et_Nod != null) {
        } else {
            S_et_Nod = "";
        }
        if (S_et_locality != null) {
        } else {
            S_et_locality = "";
        }
        if (S_et_roadnumber != null) {
        } else {
            S_et_roadnumber = "";
        }

        if (S_et_Description != null) {
        } else {
            S_et_Description = "";
        }

        if (Ssac != null) {
        } else {
            Ssac = "";
        }
        if (Sac1 != null) {
        } else {
            Sac1 = "";
        }


        if (Srt != null) {
        } else {
            Srt = "";
        }
        if (Srt1 != null) {
        } else {
            Srt1 = "";
        }


        if (Ssvc != null) {
        } else {
            Ssvc = "";
        }


        if (Svc1 != null) {
        } else {
            Svc1 = "";
        }


        if (sp_GetTypeOfAccident_id != null) {
        } else {
            sp_GetTypeOfAccident_id = "";
        }
        if (sp_GetTypeOfAccident_Name != null) {
        } else {
            sp_GetTypeOfAccident_Name = "";
        }


        if (sp_Manoeuvecrimer_id != null) {
        } else {
            sp_Manoeuvecrimer_id = "";
        }


        if (sp_Manoeuvecrimer_Name != null) {
        } else {
            sp_Manoeuvecrimer_Name = "";
        }
        if (sp_Manoeuver_id != null) {
        } else {
            sp_Manoeuver_id = "";
        }


        if (sp_Manoeuver_Name != null) {
        } else {
            sp_Manoeuver_Name = "";
        }


        if (sp_roadGeometry_id != null) {
        } else {
            sp_roadGeometry_id = "";
        }


        if (sp_roadGeometry_Name != null) {
        } else {
            sp_roadGeometry_Name = "";
        }

        if (sp_roadnumber_id != null) {
        } else {
            sp_roadnumber_id = "";
        }


        if (sp_roadnumber_Name != null) {
        } else {
            sp_roadnumber_Name = "";
        }


        if (S_latitude != null) {
        } else {
            S_latitude = "";
        }
        if (S_longitude != null) {
        } else {
            S_longitude = "";
        }

    }

    private void setData() {

        if (isConnectingToInternet(Accident_New.this)) {
            CheckConnectivity2();
        }
        S_rg_victimstatus = "1";
        S_rg_victimstatus_name = "Alcoholic";
        rg_victimstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg_victimstatus.getCheckedRadioButtonId();
                RadioButton radiolngButton = findViewById(selectedId);

                if (radiolngButton.getText().toString().equalsIgnoreCase("Alcoholic")) {
                    S_rg_victimstatus = "1";
                    S_rg_victimstatus_name = "Alcoholic";
                }
                if (radiolngButton.getText().toString().equalsIgnoreCase("Non Alcoholic")) {
                    S_rg_victimstatus = "2";
                    S_rg_victimstatus_name = "Non Alcoholic";
                }
            }
        });

        S_rg_Accusedstatus = "1";
        S_rg_Accusedstatus_name = "Alcoholic";

        rg_Accusedstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg_Accusedstatus.getCheckedRadioButtonId();
                RadioButton radiolngButton = findViewById(selectedId);

                if (radiolngButton.getText().toString().equalsIgnoreCase("Alcoholic")) {
                    S_rg_Accusedstatus = "1";
                    S_rg_Accusedstatus_name = "Alcoholic";

                }
                if (radiolngButton.getText().toString().equalsIgnoreCase("Non Alcoholic")) {
                    S_rg_Accusedstatus = "2";
                    S_rg_Accusedstatus_name = "Non Alcoholic";

                }
            }
        });
    }

    private void CheckConnectivity2() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {


            getAccidentType_Api();
            getCrimeStatus_Api();
            getRoadType_Api();
            //getRoadNumber_Api("");
            getRoadGeometry_Api();
            getVictims_Categories_Api();
            getRoadManoeuvervictim_Api();
            getRoadManoeuvercrime_Api();
            getAccused_Categories_Api();
            getRoadGetTypeOfAccident_Api();
            getGetAccidentSpot_Api();
        } else if (connec != null && ((connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ==
                NetworkInfo.State.DISCONNECTED) ||
                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() ==
                        NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void getRoadNumber_Api(String Roadnumber) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "" + Roadnumber);
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetRoadNumber, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        roadnumber_arraylist.clear();


                        JSONArray jArray = object.getJSONArray("Master");
                        int number = jArray.length();
                        String num = Integer.toString(number);
                        if (number == 0) {
                            Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                        } else {

                            Samplemyclass wp0 = new Samplemyclass("0", "Select Road Number");
                            // Binds all strings into an array
                            roadnumber_arraylist.add(wp0);
                            for (int i = 0; i < number; i++) {
                                JSONObject json_data = jArray.getJSONObject(i);
                                String e_id = json_data.getString("AccidentMasterRoadNo_Id").toString();
                                String e_n = json_data.getString("NHNumber").toString();
                                Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                // Binds all strings into an array
                                roadnumber_arraylist.add(wp);
                            }
                            if (roadnumber_arraylist.size() > 0) {
                                RoadNumber(roadnumber_arraylist);
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

    private void RoadNumber(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        sp_roadnumber.setAdapter(adapter1);
        sp_roadnumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) sp_roadnumber.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_roadnumber_id = country.getId();
                    sp_roadnumber_Name = country.getName();
                } else {
                    sp_roadnumber_id = "";
                    sp_roadnumber_Name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getRoadGeometry_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "RoadFeatures");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, RoadFeatures, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        roadnumber_Geometry.clear();

                        if (code.equalsIgnoreCase("1")) {
                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Road Geometry");
                                // Binds all strings into an array
                                roadnumber_Geometry.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("AccidentMasterRoadGeometry_Id").toString();
                                    String e_n = json_data.getString("RoadFeatures").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    roadnumber_Geometry.add(wp);
                                }
                                if (roadnumber_Geometry.size() > 0) {
                                    RoadGeometry(roadnumber_Geometry);
                                }
                            }
                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Road Geometry");
                            // Binds all strings into an array
                            roadnumber_Geometry.add(wb2);
                            if (roadnumber_Geometry.size() > 0) {
                                RoadGeometry(d2);

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

    private void RoadGeometry(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        sp_roadGeometry.setAdapter(adapter1);
        sp_roadGeometry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) rt.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_roadGeometry_id = country.getId();
                    sp_roadGeometry_Name = country.getName();

                } else {
                    sp_roadGeometry_id = "";
                    sp_roadGeometry_Name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getRoadManoeuvervictim_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "CrimeVehicleManoeuvre");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetCrimeVehicleManoeuvre, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        roadnumber_Manoeuvervitim.clear();

                        if (code.equalsIgnoreCase("1")) {
                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Victim Vehicle Manoeuver");
                                // Binds all strings into an array
                                roadnumber_Manoeuvervitim.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("CrimeVehicleManoeuvr_Id").toString();
                                    String e_n = json_data.getString("CrimeVehicleManoeuvre").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    roadnumber_Manoeuvervitim.add(wp);
                                }
                                if (roadnumber_Manoeuvervitim.size() > 0) {
                                    RoadManoeuvervictim(roadnumber_Manoeuvervitim);
                                }
                            }
                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Victim Vehicle Manoeuver");
                            // Binds all strings into an array
                            roadnumber_Manoeuvervitim.add(wb2);
                            if (roadnumber_Manoeuvervitim.size() > 0) {
                                RoadManoeuvervictim(roadnumber_Manoeuvervitim);

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

    private void RoadManoeuvervictim(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        sp_Manoeuver.setAdapter(adapter1);
        sp_Manoeuver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) sp_Manoeuver.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_Manoeuver_id = country.getId();
                    sp_Manoeuver_Name = country.getName();
                } else {
                    sp_Manoeuver_id = "";
                    sp_Manoeuver_Name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getRoadManoeuvercrime_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "CrimeVehicleManoeuvre");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetCrimeVehicleManoeuvre, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        roadnumber_Manoeuvercrime.clear();

                        if (code.equalsIgnoreCase("1")) {
                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Crime Vehicle Manoeuver");
                                // Binds all strings into an array
                                roadnumber_Manoeuvercrime.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("CrimeVehicleManoeuvr_Id").toString();
                                    String e_n = json_data.getString("CrimeVehicleManoeuvre").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    roadnumber_Manoeuvercrime.add(wp);
                                }
                                if (roadnumber_Manoeuvercrime.size() > 0) {
                                    RoadManoeuvercrime(roadnumber_Manoeuvercrime);
                                }
                            }
                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Crime Vehicle Manoeuver");
                            // Binds all strings into an array
                            roadnumber_Manoeuvercrime.add(wb2);
                            if (roadnumber_Manoeuvercrime.size() > 0) {
                                RoadManoeuvercrime(roadnumber_Manoeuvercrime);

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

    private void RoadManoeuvercrime(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        sp_Manoeuvecrimer.setAdapter(adapter1);
        sp_Manoeuvecrimer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) rt.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_Manoeuvecrimer_id = country.getId();
                    sp_Manoeuvecrimer_Name = country.getName();
                } else {
                    sp_Manoeuvecrimer_id = "";
                    sp_Manoeuvecrimer_Name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getGetAccidentSpot_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "AccidentSpotRemarks");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetAccidentSpot, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String Message = object.optString("Message").toString();

                        roadnumber_GetAccidentSpot.clear();


                        JSONArray jArray = object.getJSONArray("Master");
                        int number = jArray.length();
                        String num = Integer.toString(number);
                        if (number == 0) {
                            Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                        } else {

                            for (int i = 0; i < number; i++) {
                                JSONObject json_data = jArray.getJSONObject(i);
                                String e_id = json_data.getString("AccidentSpot_Id").toString();
                                String e_n = json_data.getString("AccidentSpotRemarks").toString();
                                Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                roadnumber_GetAccidentSpot.add(wp);
                            }
                            if (roadnumber_GetAccidentSpot.size() > 0) {
                                CCTVSpecification();


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

    private void CCTVSpecification() {

        if (this.roadnumber_GetAccidentSpot.size() > 0) {
            button = new CheckBox[this.roadnumber_GetAccidentSpot.size()];
            for (int i = 0; i < this.roadnumber_GetAccidentSpot.size(); i++) {
                button[i] = new CheckBox(getApplicationContext());
                button[i].setText(this.roadnumber_GetAccidentSpot.get(i).getName() + "");

                button[i].setTextSize(14);
                button[i].setPadding(5, 5, 5, 5);
                button[i].setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                button[i].setTextColor(getResources().getColor(R.color.colorblack));
                iteamsList.addView(button[i]);
                Length_checbox = this.roadnumber_GetAccidentSpot.size();

            }

        } else {

        }
    }

    private void getRoadGetTypeOfAccident_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "TypeOfAccident_feature");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetTypeOfAccident, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        roadnumber_GetTypeOfAccident.clear();

                        if (code.equalsIgnoreCase("1")) {
                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Type of Accident");
                                // Binds all strings into an array
                                roadnumber_GetTypeOfAccident.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("TypeofAccident_Id").toString();
                                    String e_n = json_data.getString("TypeOfAccident").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    roadnumber_GetTypeOfAccident.add(wp);
                                }
                                if (roadnumber_GetTypeOfAccident.size() > 0) {
                                    RoadGetTypeOfAccident(roadnumber_GetTypeOfAccident);
                                }
                            }
                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Type of Accident");
                            // Binds all strings into an array
                            roadnumber_GetTypeOfAccident.add(wb2);
                            if (roadnumber_GetTypeOfAccident.size() > 0) {
                                RoadGetTypeOfAccident(d2);

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

    private void RoadGetTypeOfAccident(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        sp_GetTypeOfAccident.setAdapter(adapter1);
        sp_GetTypeOfAccident.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) rt.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_GetTypeOfAccident_id = country.getId();
                    sp_GetTypeOfAccident_Name = country.getName();
                } else {
                    sp_GetTypeOfAccident_id = "";
                    sp_GetTypeOfAccident_Name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

                                Samplemyclass wb2 = new Samplemyclass("0", "Select Victim Vehicle");
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
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Victim Vehicle");
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
                } else {
                    Ssvc = "";
                    Svc1 = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getRoadType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "RoadType");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetRoadType, new Response.Listener<String>() {
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
                                    String e_id = json_data.getString("AccidentMasterRoadType_Id").toString();
                                    String e_n = json_data.getString("Type").toString();
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

                    if (Srt.equalsIgnoreCase("1") || Srt.equalsIgnoreCase("2")) {
                        getRoadNumber_Api(Srt1);
                        et_roadnumber.setText("");
                        rl_roadnumberedit.setVisibility(View.GONE);
                        rl_roadnumber.setVisibility(View.VISIBLE);

                    } else {
                        rl_roadnumberedit.setVisibility(View.VISIBLE);
                        rl_roadnumber.setVisibility(View.GONE);
                    }

                } else {
                    Srt = "";
                    Srt1 = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Accident Status");
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

                                Samplemyclass wb1 = new Samplemyclass("0", "Select Type of Offence");
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
                            Samplemyclass wb1 = new Samplemyclass("0", "Select Type of Offence");
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

    private void CrimeStatus(ArrayList<Samplemyclass> str1) {


        adapter_state = new ArrayAdapter<Samplemyclass>(getApplicationContext(),
                R.layout.spinner_item, d3);

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
                    et_Noi.setEnabled(true);
                    et_Nod.setEnabled(false);
                }
                if (Sat.equalsIgnoreCase("28")) {
                    et_Noi.setEnabled(true);
                    et_Nod.setEnabled(false);
                }
                if (Sat.equalsIgnoreCase("29")) {
                    et_Noi.setEnabled(true);
                    et_Nod.setEnabled(true);
                }
                if (Sat.equalsIgnoreCase("30")) {
                    et_Noi.setEnabled(false);
                    et_Nod.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

                                Samplemyclass wb2 = new Samplemyclass("0", "Select Crime Vehicle");
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
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Crime Vehicle");
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

                } else {
                    Ssac = "";
                    Sac1 = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
                                    Accident_New.this, 1000);
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

            et_lattitude.setText(S_latitude);
            et_Longitude.setText(S_longitude);
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


}
