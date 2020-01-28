package com.tecdatum.iaca_tspolice.ViewData.List;

import android.Manifest;
import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
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
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
import com.tecdatum.iaca_tspolice.activity.MainActivity;
import com.tecdatum.iaca_tspolice.volley.VolleySingleton;
import com.toptoche.searchablespinnerlibrary.SearchableListDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

//public class UpdateCrime_Acc_View extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_crime_acc);
//
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }
//
//}

public class UpdateCrime_Acc_View extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private String Crime_Master = URLS.CrimeMasters;

    private String crimeforupdate = URLS.crimeforupdate;

    private String getcrimebyid = URLS.getcrimebyid;
    private String updatecrime = URLS.updatecrime;

    ProgressDialog progressDialog;

    private String[] no_state = {"Complaint"};
    private String[] state = {"Select Status", "Detected", "Undetected"};
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = UpdateCrime_Acc_View.class.getSimpleName();
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
    TextView d_cno, d_ct, d_sct, d_dt, d_ad, d_ds, d_dis;
    Button C_cancel, C_confm;
    CustomDateTimePicker custom1;
    Timer timer = new Timer();
    int Code;
    TableRow Rl_subtype1;
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
    ArrayList<Samplemyclass> countryList= new ArrayList<Samplemyclass>();
    ArrayList<Samplemyclass> countryList1= new ArrayList<Samplemyclass>();
    ArrayList<Samplemyclass> d3, d6, countryList2, countryList3, countryList4;
    ArrayAdapter<Samplemyclass> adapter_state;
    // private Spinner Category, SCategory, detected_;
    SearchableSpinner Category;
    Spinner spin_year;
    Button Bt_c_date, Bt_cr_time, Bt_c_vl, Bt_c_rest, Bt_c_submit;
    String get_type, get_Cdate, get_SubType;
    int CrimeTypeid;
    String yearid, yearName, item, item2, tv_Ad, tv_Long, tv_Lat, S_Descr, psJuri, S_Uname, S_Pass, s_Ps_name, S_longitude, S_latitude,
            Code_new,Message, Message1, S_detected_id, S_detected_, S_psid, s_role, S_IMEi, S_CrimeNo, S_dateTime, S_location, Loc, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    EditText et_Locality, et_Desriptn, et_Crno;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_crimedate, tv_crimesubtype, tv_crimetype, tv_lonitude, tv_latitude, tv_c_psname;
    RelativeLayout RL_sp_sct;
    RadioButton rdcr_type, rdacc_type;
    RadioGroup rg_type;
    String crime_Type;
    TableRow tr_crimetype, tr_crimesubtype, tr_crimedate;
    RelativeLayout crimeno_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_crime_acc);
        c_time = (TextView) findViewById(R.id.tv_time);
        tv_c_psname = (TextView) findViewById(R.id.tv_c__psname);
        Category = (SearchableSpinner) findViewById(R.id.spin_CrimeType);
        spin_year = (Spinner) findViewById(R.id.spin_Year);
        crimeno_ll = (RelativeLayout) findViewById(R.id.crimeno_ll);
        Bt_c_submit = (Button) findViewById(R.id.btn_c_submit);
        et_Locality = (EditText) findViewById(R.id.et_c_locality);
        tv_latitude = (TextView) findViewById(R.id.tv_lat);
        tv_lonitude = (TextView) findViewById(R.id.tv_lng);
        tv_crimetype = (TextView) findViewById(R.id.tv_crimetype);
        tv_crimesubtype = (TextView) findViewById(R.id.tv_crimsubtype);
        tv_crimedate = (TextView) findViewById(R.id.tv_crimedate);
        tr_crimetype = (TableRow) findViewById(R.id.tr_crimetype);
        tr_crimesubtype = (TableRow) findViewById(R.id.tr_crimesubtype);
        tr_crimedate = (TableRow) findViewById(R.id.tr_crimedate);
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        s_Ps_name = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        tv_c_psname.setText(s_Ps_name);
        String s_OrgName = bb.getString("OrgName", "");
        //     TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        //   tv_OrgName.setText("" + s_OrgName);

        rg_type = (RadioGroup) findViewById(R.id.rg_type);
        rdcr_type = (RadioButton) findViewById(R.id.rdcr_type);
        rdacc_type = (RadioButton) findViewById(R.id.rdacc_type);
        crime_Type = "Crime";
        crimeno_ll.setVisibility(View.GONE);
        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg_type.getCheckedRadioButtonId();
                RadioButton radiolngButton = (RadioButton) findViewById(selectedId);

                if (radiolngButton.getText().toString().equalsIgnoreCase("Crime")) {
                    crime_Type = "Crime";
                    setData();
                    crimeno_ll.setVisibility(View.GONE);
                    tr_crimetype.setVisibility(View.GONE);
                    tr_crimedate.setVisibility(View.GONE);
                    tr_crimesubtype.setVisibility(View.GONE);
                }
                if (radiolngButton.getText().toString().equalsIgnoreCase("Accident")) {
                    crime_Type = "Accident";
                    setData();
                    crimeno_ll.setVisibility(View.GONE);
                    tr_crimetype.setVisibility(View.GONE);
                    tr_crimedate.setVisibility(View.GONE);
                    tr_crimesubtype.setVisibility(View.GONE);
                }

            }
        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spin_year != null && spin_year.getSelectedItem() != null) {
                    if (!(spin_year.getSelectedItem().toString().trim() == "Select Year")) {

                if (Category != null && Category.getSelectedItem() != null) {
                    if (!(Category.getSelectedItem().toString().trim() == "Select Crime No")) {
                        alertDialogue_Conform();
                    } else {
                        Toast.makeText(getApplicationContext(), "Select Crime No", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Select Crime No", Toast.LENGTH_SHORT).show();
                }
                    } else {
                        Toast.makeText(getApplicationContext(), "Select Year", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Select Year", Toast.LENGTH_SHORT).show();
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

    public static boolean valFun(String s) {
        if (s.length() != 7)
            return false;
        else {
            if (s.charAt(4) == '/') {
                return true;
            } else
                return false;
        }
    }

    private void alertDialogue_Conform() {
        C_dialog = new Dialog(UpdateCrime_Acc_View.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.crime_confm_update);
        C_dialog.show();
        Rl_subtype1=(TableRow) C_dialog.findViewById(R.id.Rl_subtype1);
        d_cno = (TextView) C_dialog.findViewById(R.id.tv_c_cno);
        d_ct = (TextView) C_dialog.findViewById(R.id.tv_c_ct);
        d_sct = (TextView) C_dialog.findViewById(R.id.tv_c_sct);
        d_dt = (TextView) C_dialog.findViewById(R.id.tv_c_dt);
        d_ad = (TextView) C_dialog.findViewById(R.id.tv_c_add);
        d_ds = (TextView) C_dialog.findViewById(R.id.tv_c_ds);
        d_dis = (TextView) C_dialog.findViewById(R.id.tv_c_dis);
        TextView tv_c_lat = (TextView) C_dialog.findViewById(R.id.tv_c_lat);
        TextView tv_c_lng = (TextView) C_dialog.findViewById(R.id.tv_c_lng);
        Log.e("S_detected_", "" + S_detected_);


        if(crime_Type.equalsIgnoreCase("A")){
            Rl_subtype1.setVisibility(View.GONE);
        }else {
            Rl_subtype1.setVisibility(View.VISIBLE);
        }
        d_cno.setText(S_CrimeNo);
        d_ct.setText(get_type);
        d_sct.setText(get_SubType);
        d_dt.setText(get_Cdate);
        d_ad.setText(S_location);
        //  d_ds.setText(S_detected_);
        // d_dis.setText(S_Descr);
        tv_c_lat.setText(S_latitude);
        tv_c_lng.setText(S_longitude);

        C_confm = (Button) C_dialog.findViewById(R.id.btn_C_Confm);
        C_cancel = (Button) C_dialog.findViewById(R.id.tv_C_Cancel);

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

    private void alertDialogue_C1(int count) {
        C_dialog = new Dialog(UpdateCrime_Acc_View.this, R.style.MyAlertDialogStyle);
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

        vdialog = new Dialog(UpdateCrime_Acc_View.this, R.style.HiddenTitleTheme);
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

    private void Crimetype(ArrayList<Samplemyclass>Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        if (adapter != null) {


            Category.setAdapter(adapter);

            // textView.setSelection(0);
            Category.setTitle("Select Crime No");
            // textView.setTitle();
            Category.setPositiveButton("OK");
            Category.setOnSearchTextChangedListener(new SearchableListDialog.OnSearchTextChanged() {
                @Override
                public void onSearchTextChanged(String strText) {
                    // Toast.makeText(getApplicationContext(), strText + " selected", Toast.LENGTH_SHORT).show();
                }
            });
            Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos == 0) {

                        tr_crimetype.setVisibility(View.GONE);
                        tr_crimedate.setVisibility(View.GONE);
                        tr_crimesubtype.setVisibility(View.GONE);
                    }

                    if (pos != 0) {

                        //  Samplemyclass list = (Samplemyclass) SCategory.getSelectedItem();
                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        Cat1 = country.getId();
                        CrimeTypeid = Integer.parseInt(Cat1);
                        S_CrimeNo = country.getName();
                        Log.e("Cat1", "" + Cat1);
                        // new getCrimeSubT().execute();

                        if (crime_Type.equalsIgnoreCase("Crime")) {
                            //new GetCrimeDetails().execute();
                             GetCrimeDetails_Api(Cat1);
                            tr_crimetype.setVisibility(View.VISIBLE);
                            tr_crimedate.setVisibility(View.VISIBLE);
                            tr_crimesubtype.setVisibility(View.VISIBLE);
                        } else {
                           // new GetAccidentDetails().execute();
                            GetAccidentDetails_Api(Cat1);
                            tr_crimetype.setVisibility(View.VISIBLE);
                            tr_crimedate.setVisibility(View.VISIBLE);
                            tr_crimesubtype.setVisibility(View.GONE);

                        }

                        // Toast.makeText(getApplicationContext(), "" + Cat1, Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(), "Crime Type is Mandatory ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void YearMaster(ArrayList<Samplemyclass>Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        if (adapter != null) {


            spin_year.setAdapter(adapter);
//
//            // textView.setSelection(0);
//            spin_year.setTitle("Select Crime No");
//            // textView.setTitle();
//            spin_year.setPositiveButton("OK");
//            spin_year.setOnSearchTextChangedListener(new SearchableListDialog.OnSearchTextChanged() {
//                @Override
//                public void onSearchTextChanged(String strText) {
//                    // Toast.makeText(getApplicationContext(), strText + " selected", Toast.LENGTH_SHORT).show();
//                }
//            });
            spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos == 0) {
                        tr_crimetype.setVisibility(View.GONE);
                        tr_crimedate.setVisibility(View.GONE);
                        tr_crimesubtype.setVisibility(View.GONE);
                        crimeno_ll.setVisibility(View.GONE);
                    }
                    if (pos != 0) {

                        //  Samplemyclass list = (Samplemyclass) SCategory.getSelectedItem();
                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                        yearid = country.getId();
                        yearName = country.getName();

                        if (yearid != null) {
                            crimeno_ll.setVisibility(View.VISIBLE);
                            getCrimeType_Api(yearid);
                        }

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
            new getpay().execute();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    class GetAccidentDetails extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(UpdateCrime_Acc_View.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/GetAccidentDetails";
            String METHOD_NAME = "GetAccidentDetails";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetAccidentDetails";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("CrimeID", CrimeTypeid);
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
                get_type = "";
                get_SubType = "";
                get_Cdate = "";
                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    get_type = s_deals_1.getProperty("Type").toString();
                    //get_SubType= s_deals_1.getProperty("SubType").toString();
                    get_Cdate = s_deals_1.getProperty("Cdate").toString();
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
                Toast.makeText(UpdateCrime_Acc_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(UpdateCrime_Acc_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(UpdateCrime_Acc_View.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {

                tv_crimesubtype.setText("");
                tv_crimetype.setText("" + get_type);
                tv_crimedate.setText("" + get_Cdate);

            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

    class GetCrimeDetails extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(UpdateCrime_Acc_View.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/GetCrimeDetails";
            String METHOD_NAME = "GetCrimeDetails";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetCrimeDetails";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("CrimeID", CrimeTypeid);
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
                get_type = "";
                get_SubType = "";
                get_Cdate = "";
                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    get_type = s_deals_1.getProperty("Type").toString();
                    get_SubType = s_deals_1.getProperty("SubType").toString();
                    get_Cdate = s_deals_1.getProperty("Cdate").toString();

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
                Toast.makeText(UpdateCrime_Acc_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(UpdateCrime_Acc_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(UpdateCrime_Acc_View.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {

                tv_crimesubtype.setText("" + get_SubType);
                tv_crimetype.setText("" + get_type);
                tv_crimedate.setText("" + get_Cdate);
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }


    class getpay extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(UpdateCrime_Acc_View.this);

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
                Toast.makeText(UpdateCrime_Acc_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(UpdateCrime_Acc_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(UpdateCrime_Acc_View.this, "Please try later", Toast.LENGTH_LONG).show();
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
                    // Toast.makeText(UpdateCrime_Acc_View.this,""+Message,Toast.LENGTH_LONG).show();
                }
                Log.e("result", "done");
                //tableview();
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }

//    class getYearMaster extends AsyncTask<Void, Void, Void> {
//        private final ProgressDialog dialog = new ProgressDialog(UpdateCrime_Acc_View.this);
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
//            String SOAP_ACTION = "http://tempuri.org/YearType_Master";
//            String METHOD_NAME = "YearType_Master";
//            String NAMESPACE = "http://tempuri.org/";
//            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=YearType_Master";
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            request.addProperty("userName", S_Uname);
//            request.addProperty("Password", S_Pass);
//            request.addProperty("ImeI", S_IMEi);
//
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
//                countryList1 = new ArrayList<>();
//                countryList1.clear();
//                Samplemyclass wb = new Samplemyclass("0", "Select Year");
//                countryList1.add(wb);
//                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
//                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
//                    String e_id = s_deals_1.getProperty("Id").toString();
//                    String e_n = s_deals_1.getProperty("Year").toString();
//                    Samplemyclass wb1 = new Samplemyclass(e_id, e_n);
//                    countryList1.add(wb1);
//                }
//                System.out.println("Year " + countryList1);
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
//                Toast.makeText(UpdateCrime_Acc_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
//            } else if (httpexcep) {
//                Toast.makeText(UpdateCrime_Acc_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
//            } else if (genexcep) {
//                Toast.makeText(UpdateCrime_Acc_View.this, "Please try later", Toast.LENGTH_LONG).show();
//            } else {
//                if (countryList1.size() > 0) {
//                    YearMaster();
//                }
//                if (Code == 0) {
//                } else {
//                }
//            }
//            timeoutexcep = false;
//            httpexcep = false;
//            genexcep = false;
//        }
//    }
//
//
//    class getCrimeType extends AsyncTask<Void, Void, Void> {
//
//
//        private final ProgressDialog dialog = new ProgressDialog(UpdateCrime_Acc_View.this);
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
//            String SOAP_ACTION = "http://tempuri.org/GetAccidentCrimeNo";
//            String METHOD_NAME = "GetAccidentCrimeNo";
//            String NAMESPACE = "http://tempuri.org/";
//            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetAccidentCrimeNo";
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            request.addProperty("Action", crime_Type);
//            request.addProperty("PSId", S_psid);
//            request.addProperty("year", yearid);
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
//                countryList = new ArrayList<>();
//                countryList.clear();
//                Samplemyclass wb = new Samplemyclass("0", "Select Crime No");
//                countryList.add(wb);
//                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
//                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
//                    String e_id = s_deals_1.getProperty("Id").toString();
//                    String e_n = s_deals_1.getProperty("Data").toString();
//                    Samplemyclass wb1 = new Samplemyclass(e_id, e_n);
//                    countryList.add(wb1);
//                }
//                System.out.println("Crimetypes " + countryList);
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
//                Toast.makeText(UpdateCrime_Acc_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
//            } else if (httpexcep) {
//                Toast.makeText(UpdateCrime_Acc_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
//            } else if (genexcep) {
//                Toast.makeText(UpdateCrime_Acc_View.this, "Please try later", Toast.LENGTH_LONG).show();
//            } else {
//                if (countryList.size() > 0) {
//                    //Crimetype();
//                }
//                if (Code == 0) {
//                } else {
//                }
//            }
//            timeoutexcep = false;
//            httpexcep = false;
//            genexcep = false;
//        }
//    }
//   // getCrimeType_Api();

    private void getCrimeType_Api(String Year_Id) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("TypeOF", crime_Type);
            jsonBody.put("Year", Year_Id);
            jsonBody.put("HirarchyID", S_psid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:crimeforupdate" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, crimeforupdate, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:crimeforupdate" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        countryList.clear();
                        if (code.equalsIgnoreCase("1")){

                            JSONArray jArray = object.getJSONArray("CNOList");
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
                                    String e_id = json_data.getString("CrimeID").toString();
                                    String e_n = json_data.getString("CrimeNo").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    countryList.add(wp);
                                }
                                if (countryList.size() > 0) {
                                    Crimetype(countryList);
                                }
                            }



                        }else {
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

    private void getYear_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "4");
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

                        countryList1.clear();
                        if (code.equalsIgnoreCase("1")){

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Year");
                                // Binds all strings into an array
                                countryList1.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    countryList1.add(wp);
                                }
                                if (countryList1.size() > 0) {
                                    YearMaster(countryList1);
                                }
                            }



                        }else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Crime Type");
                            // Binds all strings into an array
                            countryList1.add(wp0);
                            if (countryList1.size() > 0) {
                                YearMaster(countryList1);
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

    private void GetAccidentDetails_Api(String CrimeID) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("CrimeID", CrimeID);


            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Crime_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getcrimebyid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Crime_Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        get_type = "";
                        get_SubType = "";
                        get_Cdate = "";
                        if (code.equalsIgnoreCase("1")){

                            JSONArray jArray = object.getJSONArray("CList");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    get_SubType = json_data.getString("CrimeSubType").toString();
                                    get_type = json_data.getString("CrimeType").toString();

                                    get_Cdate = json_data.getString("Dateofoffence").toString();
                                    String CrimeStatus = json_data.getString("CrimeStatus").toString();

                                    String CrimeNo = json_data.getString("CrimeNo").toString();
                                    String Psname = json_data.getString("Psname").toString();

                                    String Description = json_data.getString("Description").toString();
                                    String Psid = json_data.getString("Psid").toString();

                                    tv_crimesubtype.setText(""+get_SubType);
                                    tv_crimetype.setText("" + get_type);
                                    tv_crimedate.setText("" + get_Cdate);



                                }

                            }


                        }else {

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
    private void GetCrimeDetails_Api(String CrimeID) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("CrimeID", CrimeID);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Crime_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getcrimebyid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Crime_Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        get_type = "";
                        get_SubType = "";
                        get_Cdate = "";
                        if (code.equalsIgnoreCase("1")){

                            JSONArray jArray = object.getJSONArray("CList");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    get_SubType = json_data.getString("CrimeSubType").toString();
                                    get_type = json_data.getString("CrimeType").toString();

                                    get_Cdate = json_data.getString("Dateofoffence").toString();
                                    String CrimeStatus = json_data.getString("CrimeStatus").toString();

                                    String CrimeNo = json_data.getString("CrimeNo").toString();
                                    String Psname = json_data.getString("Psname").toString();

                                    String Description = json_data.getString("Description").toString();
                                    String Psid = json_data.getString("Psid").toString();

                                    tv_crimesubtype.setText(""+get_SubType);
                                    tv_crimetype.setText("" + get_type);
                                    tv_crimedate.setText("" + get_Cdate);


                                }

                            }

                        }else {
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


    private void SendDataToServer() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("CrimeID", CrimeTypeid);
            jsonBody.put("Latitude", S_latitude);
            jsonBody.put("Longitude", S_longitude);
            jsonBody.put("Address", S_location);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:crime DATATo Server" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, updatecrime, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:crime DATATo Server" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        if (code != null) {
                        if (code.equalsIgnoreCase("1")){
                            Log.e("suceess", "" + Message);
                            C_dialog.dismiss();
                            spin_year.setSelection(0);
                            Category.setSelection(0);
                            S_CrimeNo = "";
                            S_location = "";
                            rdcr_type.setChecked(true);
                            tr_crimetype.setVisibility(View.GONE);
                            tr_crimedate.setVisibility(View.GONE);
                            tr_crimesubtype.setVisibility(View.GONE);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateCrime_Acc_View.this);
                            alertDialog.setTitle("Success");
                            alertDialog.setMessage("" + Message);
                            alertDialog.setIcon(R.drawable.succs);
                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to invoke YES event
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();

                        }else {
                            Log.e("not sucess", "" + Message);
                            C_dialog.dismiss();

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateCrime_Acc_View.this);
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("" + Message);
                            alertDialog.setIcon(R.drawable.alertt);
                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
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

    private void CheckConnectivity2() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            d3 = new ArrayList<>();
            d6 = new ArrayList<>();
            d6.add(new Samplemyclass("0", "Complaint"));


           // new getYearMaster().execute();

            getYear_Api();
            //

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    class pay extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(UpdateCrime_Acc_View.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/UpdateLocation";
            String METHOD_NAME = "UpdateLocation";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx";
            try {
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Action", crime_Type);
                request.addProperty("CrimeID", CrimeTypeid);
                request.addProperty("Latitude", S_latitude);
                request.addProperty("Longitude", S_longitude);
                request.addProperty("Location", S_location);

                envelope.setOutputSoapObject(request);


                Log.e("request", "" + request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.debug = true;
                Log.e("UpdateCri ENVOLOPE", "" + envelope);
                try {
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    if (envelope != null) {
                        String result1 = envelope.getResponse().toString();
                        Log.e("result_UpdateCrime_Acc", "" + result1);
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
                Toast.makeText(UpdateCrime_Acc_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(UpdateCrime_Acc_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(UpdateCrime_Acc_View.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (Code1 != null) {
                    if (Code1 == 1) {
                        Log.e("suceess", "" + Message1);
                        C_dialog.dismiss();
                        spin_year.setSelection(0);
                        Category.setSelection(0);
                        S_CrimeNo = "";
                        S_location = "";
                        rdcr_type.setChecked(true);
                        tr_crimetype.setVisibility(View.GONE);
                        tr_crimedate.setVisibility(View.GONE);
                        tr_crimesubtype.setVisibility(View.GONE);
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateCrime_Acc_View.this);
                        alertDialog.setTitle("Success");
                        alertDialog.setMessage("" + Message1);
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
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateCrime_Acc_View.this);
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("" + Message1);
                        alertDialog.setIcon(R.drawable.alertt);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        // Toast.makeText(UpdateCrime_Acc_View.this,""+Message,Toast.LENGTH_LONG).show();
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

          //  new pay().execute();
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

    private void setData() {
        CheckConnectivity2();
//        countryList = new ArrayList<>();
//        countryList.add(new Samplemyclass("0", "Select Crime Type"));
//        countryList.add(new Samplemyclass("1", "Gender Offence"));
//        countryList.add(new Samplemyclass("2", "Murder"));
//        countryList.add(new Samplemyclass("3", "Property Offence"));
//        countryList.add(new Samplemyclass("4", "SC/ST Atrocities"));
//
//
//        countryList1 = new ArrayList<>();
//        countryList1.add(new Samplemyclass("0", "Select Crime SubType"));
//        countryList1.add(new Samplemyclass("1", "Rape"));
//        countryList1.add(new Samplemyclass("1", "Dowry Harassment"));
//        countryList1.add(new Samplemyclass("1", "Outranging Modesty"));
//        countryList1.add(new Samplemyclass("1", "Insulting Modesty"));
//        countryList1.add(new Samplemyclass("1", "Dowry Death"));
//        countryList1.add(new Samplemyclass("1", "Offence Related to Marraige"));
//
//
//        countryList2 = new ArrayList<>();
//        countryList2.add(new Samplemyclass("0", "Select Crime SubType"));
//        countryList2.add(new Samplemyclass("2", "Others"));
//        countryList2.add(new Samplemyclass("2", "Communel"));
//        countryList2.add(new Samplemyclass("2", "Sexual Jealously"));
//        countryList2.add(new Samplemyclass("2", "Faction"));
//        countryList2.add(new Samplemyclass("2", "Political"));
//        countryList2.add(new Samplemyclass("2", "Terrorist"));
//        countryList2.add(new Samplemyclass("2", "Property Disputes"));
//
//        countryList3 = new ArrayList<>();
//        countryList3.add(new Samplemyclass("0", "Select Crime SubType"));
//        countryList3.add(new Samplemyclass("3", "Decoit"));
//        countryList3.add(new Samplemyclass("3", "Murder of Gain"));
//        countryList3.add(new Samplemyclass("3", "Diverting Attension"));
//        countryList3.add(new Samplemyclass("3", "Ordinary theft"));
//        countryList3.add(new Samplemyclass("3", "Snatching"));
//        countryList3.add(new Samplemyclass("3", "Ambulance theft"));
//        countryList3.add(new Samplemyclass("3", "HB Day"));
//        countryList3.add(new Samplemyclass("3", "HB Night"));
//        countryList3.add(new Samplemyclass("3", "Pseudo Police"));
//        countryList3.add(new Samplemyclass("3", "Ordinary theft"));
//        countryList3.add(new Samplemyclass("3", "Robbery"));
//
//        countryList4 = new ArrayList<>();
//        countryList4.add(new Samplemyclass("0", "Select Crime SubType"));
//        countryList4.add(new Samplemyclass("4", "Verbal Abuse"));
//        countryList4.add(new Samplemyclass("4", "Physical Assault"));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                                    UpdateCrime_Acc_View.this, 1000);
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

            tv_lonitude.setText("" + S_latitude);
            tv_latitude.setText("" + S_longitude);
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
                        S_location = stringBuilder.toString();
                        String t66 = "<font color='#ebedf5'>Location : </font>";
                        Log.e("msg", "" + S_location);
                        et_Locality.setText(" " + S_location);

                        // CurrentLocation.setText(Html.fromHtml(t66+S_loc));
                        // CurrentLocation.setText(S_loc);
                    }
                }
            } catch (IOException e) {

                et_Locality.setHint(" " + "Enter your Location");
                Log.e("msg", "" + "Couldn't get the location.");
                S_location = "";
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