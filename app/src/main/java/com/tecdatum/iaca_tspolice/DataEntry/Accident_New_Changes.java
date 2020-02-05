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
import android.view.View;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import static com.tecdatum.iaca_tspolice.DataEntry.AddAccident.MY_PREFS_NAME;

public class Accident_New_Changes extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = Accident_New_Changes.class.getSimpleName();

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

    String Message, Code_new;


    Dialog C_dialog, vdialog;
    ArrayAdapter<Samplemyclass> adapter_state;
    ArrayList<Samplemyclass> roadnumber_GetAccidentSpot = new ArrayList<>();
    ArrayList<Samplemyclass> Accident_arraylist = new ArrayList<>();
    ArrayList<Samplemyclass> Accidenttype_arraylist = new ArrayList<>();
    ArrayList<Samplemyclass> roadtype_arraylist = new ArrayList<>();
    ArrayList<Samplemyclass> Year_arraylist = new ArrayList<>();
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
    ArrayList<Samplemyclass> Accusedcatergory_arralist = new ArrayList<>();
    ArrayList<Samplemyclass> Victimcatergory_arralist = new ArrayList<>();
    ArrayList<String> roadnumber_ids = new ArrayList<>();
    private String AccidentMasters = URLS.AccidentMasters;

    private String GetAccusedCategory = URLS.GetAccusedCategory;
    private String GetVictimCategories = URLS.GetVictimCategories;
    private String GetAccidentType = URLS.GetAccidentType;
    private String GetRoadNumber = URLS.GetRoadNumber;
    private String GetYear = URLS.GetYear;
    private String VerifyforAccidentDetails = URLS.VerifyforAccidentDetails;

    private String GetCrimeVehicleManoeuvre = URLS.GetCrimeVehicleManoeuvre;
    private String GetTypeOfAccident = URLS.GetTypeOfAccident;
    private String GetAccidentSpot = URLS.GetAccidentSpot;
    private String GetRoadType = URLS.GetRoadType;
    private String RoadFeatures = URLS.RoadFeatures;
    private String AccidentDetails = URLS.AccidentDetails;
    private String GetAccidentRecordsData = URLS.GetAccidentRecordsData;
    String Cdate, Cnumber, Pscode, Type, Subtype, Location, EnteredDate, CStatus,
            RoadType, Descr;

    CheckBox[] button;

    CustomDateTimePicker custom1;
    CustomDateTimePicker custom0;

    Button Bt_c_rest, Bt_c_submit, Bt_c_vl;
    Date strDate1, strDate2, strDate3, strDate4, strDate5, strDate6;
    String S_dateTime;
    String S_COMPYEAR, S_dateTime1, Srt, Srt1;
    RadioGroup rg_victimstatus, rg_Accusedstatus;
    String S_rg_victimstatus, S_rg_victimstatus_name, S_rg_Accusedstatus, S_rg_Accusedstatus_name;
    EditText et_locality;
    String s_Ps_name, S_Uname, S_Pass, S_psCode, S_psid, s_role, S_IMEi;
    //NEW//
    EditText et_lattitude, et_Longitude;
    EditText et_crime_no;
    String S_et_crime_no;
    Spinner sp_year, sp_Accidentstatus, sp_accidentType, sp_roadType, sp_roadnumber, sp_roadGeometry, sp_GetTypeOfAccident, sp_accusedCategory, sp_Manoeuvecrimer;
    String sp_yearid, sp_yearName, sp_AccidentstatusId, sp_AccidentstatusNAme, sp_accidentTypeID, sp_accidentTypeName, sp_roadTypeID, sp_roadTypeName, sp_roadnumberID,
            sp_roadnumberNAme, sp_roadGeometryID, sp_roadGeometryName, sp_GetTypeOfAccidentId, sp_GetTypeOfAccidentNAme, sp_accusedCategoryID, sp_accusedCategoryName, sp_Manoeuvecrimer_id, sp_Manoeuvecrimer_Name;
    Button btn_verify;
    TextView crimecheck;
    Button Bt_acc_date, Bt_c_dateReport;
    EditText et_no_Accused, et_nod_accused, et_noi_victim, et_nod_Victim, et_Description, et_roadnumber, AccusedAlocoPer;
    String S_et_no_Accused, S_et_nod_accused, S_et_noi_victim, S_et_nod_Victim, S_et_Description, S_et_roadnumber, S_AccusedAlocoPer;
    RelativeLayout rl_roadnumberedit, rl_roadnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident__new);
        widgetInitialization();
        ButtonOnclicks();
        getSharedPreferences();
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

    private void ButtonOnclicks() {

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringConvertion();
                stringNotNull();
                if (!S_et_crime_no.equalsIgnoreCase("")) {
                    if (valFun(S_et_crime_no)) {
                        CheckCrimenumber();

                    } else {
                        Toast.makeText(Accident_New_Changes.this, "Please Enter Valid Crime No.(Accident No)", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Crime No.(Accident No)", Toast.LENGTH_SHORT).show();

                }
            }
        });
        Bt_acc_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custom1 = new CustomDateTimePicker(Accident_New_Changes.this,
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
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                                        Toast.makeText(Accident_New_Changes.this, "Please Select Proper Crime Date", Toast.LENGTH_SHORT).show();
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
                custom0 = new CustomDateTimePicker(Accident_New_Changes.this,
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
                                        Toast.makeText(Accident_New_Changes.this, "Please Select Proper Report Date", Toast.LENGTH_SHORT).show();
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
    }

    public void CheckCrimenumber() {


        JSONObject request = new JSONObject();
        try {


            request.put("CrimeNumber", S_et_crime_no);
            request.put("CrimeYear", sp_yearid);
            request.put("PSID", S_psid);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = request.toString();
        Log.e("VOLLEY", "VerifyforAccidentDetails" + "Input" + mRequestBody);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                VerifyforAccidentDetails, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "VerifyforAccidentDetails" + "Output" + response.toString());
                Code_new = response.optString("code").toString();
                Message = response.optString("Message").toString();


                if (Code_new.equalsIgnoreCase("0")) {

                    crimecheck.setText("" + Message);
                } else {

                    crimecheck.setText("" + Message);

                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

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

    private void getSharedPreferences() {

        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        S_psCode = bb.getString("Pscode", "");
        s_Ps_name = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        tv_c_psname = (TextView) findViewById(R.id.tv_accic_psname);
        tv_c_psname.setText(s_Ps_name);
    }

    private void widgetInitialization() {
        et_locality = (EditText) findViewById(R.id.et_Locality);
        et_locality.setFilters(new InputFilter[]{filter});
        c_time = (TextView) findViewById(R.id.tv_acci_time);
        et_lattitude = findViewById(R.id.et_lattitude);
        et_Longitude = findViewById(R.id.et_Longitude);
        rg_victimstatus = findViewById(R.id.rg_victimstatus);
        rg_Accusedstatus = findViewById(R.id.rg_Accusedstatus);
        et_crime_no = findViewById(R.id.et_crime_no);
        sp_year = findViewById(R.id.sp_year);
        btn_verify = findViewById(R.id.btn_verify);
        crimecheck = findViewById(R.id.crimecheck);
        sp_Accidentstatus = findViewById(R.id.sp_Accidentstatus);
        Bt_acc_date = (Button) findViewById(R.id.btn_acci_date);
        Bt_c_dateReport = (Button) findViewById(R.id.Bt_c_dateReport);
        sp_accidentType = findViewById(R.id.sp_accidentType);
        et_no_Accused = findViewById(R.id.et_no_Accused);
        et_nod_accused = findViewById(R.id.et_nod_accused);
        et_noi_victim = findViewById(R.id.et_noi_victim);
        et_nod_Victim = findViewById(R.id.et_nod_Victim);
        et_Description = findViewById(R.id.et_Description);
        sp_roadType = findViewById(R.id.sp_roadType);
        rl_roadnumber = findViewById(R.id.rl_roadnumber);
        rl_roadnumberedit = findViewById(R.id.rl_roadnumberedit);
        et_roadnumber = findViewById(R.id.et_roadnumber);
        sp_roadnumber = findViewById(R.id.sp_roadnumber);
        sp_roadGeometry = findViewById(R.id.sp_roadGeometry);
        sp_accusedCategory = findViewById(R.id.sp_accusedCategory);
        AccusedAlocoPer = findViewById(R.id.AccusedAlocoPer);
        sp_Manoeuvecrimer = findViewById(R.id.sp_Manoeuvecrimer);


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


        Bt_c_vl = (Button) findViewById(R.id.btn_accid_vl);

        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void stringConvertion() {
        S_et_crime_no = et_crime_no.getText().toString();
        S_et_no_Accused = et_no_Accused.getText().toString();
        S_et_nod_accused = et_nod_accused.getText().toString();
        S_et_noi_victim = et_noi_victim.getText().toString();
        S_et_nod_Victim = et_nod_Victim.getText().toString();
        S_et_Description = et_Description.getText().toString();
        S_et_roadnumber = et_roadnumber.getText().toString();
        S_AccusedAlocoPer = AccusedAlocoPer.getText().toString();
    }

    public void stringNotNull() {
        if (S_et_crime_no != null) {

        } else {
            S_et_crime_no = "";
        }
        if (sp_yearid != null) {

        } else {
            sp_yearid = "";
        }

        if (sp_AccidentstatusId != null) {

        } else {
            sp_AccidentstatusId = "";
        }

        if (S_et_no_Accused != null) {

        } else {
            S_et_no_Accused = "";
        }
        if (S_et_nod_accused != null) {

        } else {
            S_et_nod_accused = "";
        }
        if (S_et_noi_victim != null) {

        } else {
            S_et_noi_victim = "";
        }
        if (S_et_nod_Victim != null) {

        } else {
            S_et_nod_Victim = "";
        }

        if (S_et_Description != null) {

        } else {
            S_et_Description = "";
        }

        if (S_et_roadnumber != null) {

        } else {
            S_et_roadnumber = "";
        }
        if (sp_roadGeometryID != null) {
        } else {
            sp_roadGeometryID = "";
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

                        Accident_arraylist.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Accident Status");
                                // Binds all strings into an array
                                Accident_arraylist.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    Accident_arraylist.add(wp);
                                }
                                if (Accident_arraylist.size() > 0) {
                                    CrimeStatus(Accident_arraylist);
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

    private void CrimeStatus(ArrayList<Samplemyclass> str1) {


        adapter_state = new ArrayAdapter<Samplemyclass>(getApplicationContext(),
                R.layout.spinner_item, Accident_arraylist);

        sp_Accidentstatus.setAdapter(adapter_state);
        sp_Accidentstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {

                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                    sp_AccidentstatusId = country.getId();
                    sp_AccidentstatusNAme = country.getName();
                    sp_Accidentstatus.setSelection(position);

                } else {
                    sp_AccidentstatusId = "";
                    sp_AccidentstatusNAme = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void getAccidentType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "AccidentTypes");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:GetAccidentType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetAccidentType, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:GetAccidentType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        Accidenttype_arraylist.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb1 = new Samplemyclass("0", "Select Accident Type");
                                // Binds all strings into an array
                                Accidenttype_arraylist.add(wb1);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    Accidenttype_arraylist.add(wp);
                                }
                                if (Accidenttype_arraylist.size() > 0) {
                                    AccidentType(Accidenttype_arraylist);
                                }
                            }


                        } else {
                            Samplemyclass wb1 = new Samplemyclass("0", "Select Accident Type");
                            // Binds all strings into an array
                            Accidenttype_arraylist.add(wb1);
                            if (Accidenttype_arraylist.size() > 0) {
                                AccidentType(Accidenttype_arraylist);

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

    private void AccidentType(ArrayList<Samplemyclass> Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        sp_accidentType.setAdapter(adapter);
        sp_accidentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Samplemyclass list = (Samplemyclass) sp_accidentType.getSelectedItem();
                Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                sp_accidentTypeName = country.getName();
                sp_accidentTypeID = country.getId();


                if (sp_accidentTypeID.equalsIgnoreCase("27")) {

                    et_nod_accused.setEnabled(false);
                    et_nod_Victim.setEnabled(false);
                    et_noi_victim.setEnabled(true);
                    et_no_Accused.setEnabled(true);


                    et_nod_accused.setBackgroundResource(R.drawable.btn_background);
                    et_nod_Victim.setBackgroundResource(R.drawable.btn_background);
                    et_noi_victim.setBackgroundResource(R.drawable.edittext_baground);
                    et_no_Accused.setBackgroundResource(R.drawable.edittext_baground);
                }
                if (sp_accidentTypeID.equalsIgnoreCase("28")) {

                    et_nod_accused.setEnabled(false);
                    et_nod_Victim.setEnabled(false);
                    et_no_Accused.setEnabled(true);
                    et_noi_victim.setEnabled(true);

                    et_nod_accused.setBackgroundResource(R.drawable.btn_background);
                    et_nod_Victim.setBackgroundResource(R.drawable.btn_background);
                    et_noi_victim.setBackgroundResource(R.drawable.edittext_baground);
                    et_no_Accused.setBackgroundResource(R.drawable.edittext_baground);

                }
                if (sp_accidentTypeID.equalsIgnoreCase("29")) {
                    et_no_Accused.setEnabled(true);
                    et_nod_accused.setEnabled(true);
                    et_noi_victim.setEnabled(true);
                    et_nod_Victim.setEnabled(true);
                    et_nod_accused.setBackgroundResource(R.drawable.edittext_baground);
                    et_nod_Victim.setBackgroundResource(R.drawable.edittext_baground);
                    et_noi_victim.setBackgroundResource(R.drawable.edittext_baground);
                    et_no_Accused.setBackgroundResource(R.drawable.edittext_baground);

                }
                if (sp_accidentTypeID.equalsIgnoreCase("30")) {
                    et_no_Accused.setEnabled(false);
                    et_nod_accused.setEnabled(false);
                    et_noi_victim.setEnabled(false);
                    et_nod_Victim.setEnabled(false);
                    et_nod_accused.setBackgroundResource(R.drawable.btn_background);
                    et_nod_Victim.setBackgroundResource(R.drawable.btn_background);
                    et_noi_victim.setBackgroundResource(R.drawable.btn_background);
                    et_no_Accused.setBackgroundResource(R.drawable.btn_background);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setData() {
        if (isConnectingToInternet(Accident_New_Changes.this)) {
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

            getYear_Api();
            getAccidentType_Api();
            getCrimeStatus_Api();
            getRoadType_Api();
            getRoadGeometry_Api();
            getRoadNumber_Api("");
            getAccused_Categories_Api();
            getRoadGetTypeOfAccident_Api();
            getRoadManoeuvercrime_Api();
        } else if (connec != null && ((connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ==
                NetworkInfo.State.DISCONNECTED) ||
                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() ==
                        NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void getYear_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "" + "CrimeYears");
            //  jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:GetYear" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetYear, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:GetYear" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        Year_arraylist.clear();

                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Road Number");
                                // Binds all strings into an array
                                Year_arraylist.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("AccidentMasterRoadNo_Id").toString();
                                    String e_n = json_data.getString("NHNumber").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    Year_arraylist.add(wp);
                                }
                                if (Year_arraylist.size() > 0) {
                                    YearApi(Year_arraylist);
                                }
                            }

                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Type of Accident");
                            // Binds all strings into an array
                            roadnumber_GetTypeOfAccident.add(wb2);
                            if (Year_arraylist.size() > 0) {
                                YearApi(Year_arraylist);
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

    private void YearApi(ArrayList<Samplemyclass> Str1) {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);

        sp_year.setAdapter(adapter1);
        for (int i = 0; i < Year_arraylist.size(); i++) {
            if (Year_arraylist.get(i).getName().equalsIgnoreCase(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))) {
                sp_year.setSelection(i);
                Log.d("Year", "Current YES" + Calendar.getInstance().get(Calendar.YEAR));
            }


        }

        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) sp_year.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_yearid = country.getId();
                    sp_yearName = country.getName();
                } else {
                    sp_yearid = "";
                    sp_yearName = "";
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

                        roadtype_arraylist.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Road Type");
                                // Binds all strings into an array
                                roadtype_arraylist.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("AccidentMasterRoadType_Id").toString();
                                    String e_n = json_data.getString("Type").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    roadtype_arraylist.add(wp);
                                }
                                if (roadtype_arraylist.size() > 0) {
                                    RoadType(roadtype_arraylist);
                                }
                            }


                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Road Type");
                            // Binds all strings into an array
                            roadtype_arraylist.add(wb2);
                            if (roadtype_arraylist.size() > 0) {
                                RoadType(roadtype_arraylist);

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
        sp_roadType.setAdapter(adapter1);
        sp_roadType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) sp_roadType.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_roadTypeID = country.getId();
                    sp_roadTypeName = country.getName();

                    if (sp_roadTypeID.equalsIgnoreCase("3")) {

                        rl_roadnumberedit.setVisibility(View.VISIBLE);
                        rl_roadnumber.setVisibility(View.GONE);

                    } else {

                    }
                    if (sp_roadTypeID.equalsIgnoreCase("1")) {
                        getRoadNumber_Api("NationalHighways");
                        et_roadnumber.setText("");
                        rl_roadnumberedit.setVisibility(View.GONE);
                        rl_roadnumber.setVisibility(View.VISIBLE);

                    } else {
                    }

                    if (sp_roadTypeID.equalsIgnoreCase("2")) {
                        getRoadNumber_Api("StateHighways");
                        et_roadnumber.setText("");
                        rl_roadnumberedit.setVisibility(View.GONE);
                        rl_roadnumber.setVisibility(View.VISIBLE);

                    } else {
                    }

                    if (sp_roadTypeID.equalsIgnoreCase("11")) {
                        getRoadNumber_Api("OtherRoads");
                        et_roadnumber.setText("");
                        rl_roadnumberedit.setVisibility(View.GONE);
                        rl_roadnumber.setVisibility(View.VISIBLE);

                    } else {

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
                        if (code.equalsIgnoreCase("1")) {

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
                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Select Road Number");
                            // Binds all strings into an array
                            roadnumber_arraylist.add(wb2);
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
                    sp_roadnumberID = country.getId();
                    sp_roadnumberNAme = country.getName();
                } else {
                    sp_roadnumberID = "";
                    sp_roadnumberNAme = "";
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
                                RoadGeometry(roadnumber_Geometry);

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
                    Samplemyclass list = (Samplemyclass) sp_roadGeometry.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_roadGeometryID = country.getId();
                    sp_roadGeometryName = country.getName();

                } else {
                    sp_roadGeometryID = "";
                    sp_roadGeometryName = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
                                RoadGetTypeOfAccident(roadnumber_GetTypeOfAccident);

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
                    Samplemyclass list = (Samplemyclass) sp_GetTypeOfAccident.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_GetTypeOfAccidentId = country.getId();
                    sp_GetTypeOfAccidentNAme = country.getName();
                } else {
                    sp_GetTypeOfAccidentId = "";
                    sp_GetTypeOfAccidentNAme = "";
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
            jsonBody.put("ActionName", "AccusedCategories");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetAccusedCategory, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        Accusedcatergory_arralist.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Category:");
                                // Binds all strings into an array
                                Accusedcatergory_arralist.add(wb2);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("AccusedCategory_Id").toString();
                                    String e_n = json_data.getString("Categorietype").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    Accusedcatergory_arralist.add(wp);
                                }
                                if (Accusedcatergory_arralist.size() > 0) {
                                    AccusedCat(Accusedcatergory_arralist);
                                }
                            }


                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Category:");
                            // Binds all strings into an array
                            Accusedcatergory_arralist.add(wb2);
                            if (Accusedcatergory_arralist.size() > 0) {
                                AccusedCat(Accusedcatergory_arralist);

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
        sp_accusedCategory.setAdapter(adapter4);
        sp_accusedCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass list = (Samplemyclass) sp_accusedCategory.getSelectedItem();
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_accusedCategoryID = country.getId();
                    sp_accusedCategoryName = country.getName();

                } else {
                    sp_accusedCategoryID = "";
                    sp_accusedCategoryName = "";
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
                    Samplemyclass list = (Samplemyclass) sp_Manoeuvecrimer.getSelectedItem();
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
                                    Accident_New_Changes.this, 1000);
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
