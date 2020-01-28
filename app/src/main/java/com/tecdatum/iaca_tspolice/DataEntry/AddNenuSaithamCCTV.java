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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
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
;
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
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
import com.tecdatum.iaca_tspolice.activity.MainActivity;
import com.tecdatum.iaca_tspolice.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

public class AddNenuSaithamCCTV extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String cctvMasters = URLS.cctvMasters;
    private String NenuSaithamcctvList = URLS.NenuSaithamcctvList;
    private String NenuSaithamCctvEntry = URLS.NenuSaithamCctvEntry;
    String Message1;
    int Code2;
    String Message2;
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = AddNenuSaithamCCTV.class.getSimpleName();
    private LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x01;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 50;
    private static final int READ_CAMERA_PERMISSIONS_REQUEST = 20;
    private static final int WRITE_SETTINGS_PERMISSION = 20;
    private boolean active = false;
    protected Location mLastLocation;
    private static int UPDATE_INTERVAL = 30000;
    private boolean mRequestingLocationUpdates = true;
    int Code;

    ArrayList<String> CGp_arraylist_ = new ArrayList<String>();
    ArrayList<String> SecNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> PoleNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> LandMark_arraylist_ = new ArrayList<String>();
    ArrayList<String> Add_arraylist_ = new ArrayList<String>();
    ArrayList<String> Fixed_arraylist_ = new ArrayList<String>();
    ArrayList<String> PTZ_arraylist_ = new ArrayList<String>();
    ArrayList<String> IR_arraylist_ = new ArrayList<String>();
    ArrayList<String> Other_arraylist_ = new ArrayList<String>();
    ArrayList<String> InchargeName_arraylist_ = new ArrayList<String>();
    ArrayList<String> InchargeNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> Remarks_arraylist_ = new ArrayList<String>();
    ArrayList<String> CCTVCapacity_arraylist_ = new ArrayList<String>();
    ArrayList<String> Specification_arraylist_ = new ArrayList<String>();
    ArrayList<String> CCTVType_arraylist_ = new ArrayList<String>();
    ArrayList<String> status_arraylist_ = new ArrayList<String>();

    Geocoder geocoder;
    String CGp, status, PoleNo, SecNo, LandMark, CCTVType, Add, Fixed, PTZ, IR, Other, InchargeName, InchargeNo, CCTVCapacity, Specification, Remarks;
    String S_Uname, S_Pass, s_Ps_name, S_longitude, S_latitude,
            Code_new, Message, S_detected_, S_psid, S_Orgid, s_role, formattedDate, S_IMEi, S_CrimeNo, S_dateTime, S_location,
            Loc, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_c_psname, c_time;
    Button Bt_c_rest, Bt_c_submit, Bt_c_vl;
    View V;
    EditText cg, pNo, secNo, lm, location;
    String S_cg, S_pNo, S_secNo, S_lm;
    Dialog C_dialog, vl_dialog;
    TextView tv_lat, tv_lng;
    Spinner spin_ps;
    String HierarchyId;
    ArrayList<Samplemyclass> al_ps = new ArrayList<>();
    Spinner spin_sector, spin_PatrolCarRegion, spin_BlueColtRegion, spin_TypeOfEstablishment, spin_cctvspecificationtype, spin_cctvcapacitytype, spin_cctvfundstype, spin_pslink, spin_vendor, spin_cctvreason, spin_cctvstatus, spin_cctvtype;
    ArrayList<Samplemyclass> al_sector = new ArrayList<>();
    ArrayList<Samplemyclass> al_vendor = new ArrayList<>();//Patrol Car Region
    ArrayList<Samplemyclass> al_cctvreason = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvstatus = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvtype = new ArrayList<>();
    ArrayList<Samplemyclass> al_pslink = new ArrayList<>();//Blue Colt Region
    ArrayList<Samplemyclass> al_cctvfunds = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvcapacity = new ArrayList<>();//Type Of Establishment
    ArrayList<Samplemyclass> al_cctvtypespecification = new ArrayList<>();
    // ArrayList<CCTVTypes_WithImages> String_CCTVTypes_WithImages = new ArrayList<>();
    final ArrayList<Samplemyclass> al = new ArrayList<Samplemyclass>();

    String S_cctv_Specifications, S_cctv_NVRIPAddress, S_cctv_ChannelNo, S_cctv_IpAddress, S_cctv_InchargeName, S_cctv_InchargeNo, S_cctv_Remarks;
    EditText et_cctv_locality, et_cctv_NVRIPAddress, et_cctv_Specifications, et_cctv_ChannelNo, et_cctv_IpAddress, et_cctv_InchargeName, et_cctv_InchargeNo, et_cctv_Remarks;

    String sector_id, sector_name, S_cctv_locality, pslink_id, pslink_name, vendor_id, vendor_name, cctvreason_id, cctvreason_name, cctvstatus_id, cctvstatus_name,
            cctvtype_id, cctvtype_name, cctvfunds_id, cctvfunds_name, cctvcapacity_id, cctvcapacity_name, cctvspecification_id, cctvspecification_name;
    TableRow tr_reasonofntworking;
    RadioGroup rg_specs;
    RadioButton rd_Sector, rd_Locality;
    RelativeLayout rl_reason;
    RelativeLayout rl_CommunityGrp;
    CheckBox button[];
    TextView tv_name[];
    TextView tv_discription[];
    ImageView iv_image[];
    GridLayout iteamsList_name;
    GridLayout iteamsList_discription;
    GridLayout iteamsList_image;
    GridLayout iteamsList;
    Integer Length_checbox;
    String string, string1;
    ArrayList<Samplemyclass> arrayList, arrayList2;
    EditText et_cctv_VCompany, et_cctv_VInchargeName, et_cctv_VInchargeNo, et_cctv_VInchargeAdress, et_cctv_NameOfEstablishment, et_cctv_ContactPerson, et_cctv_ContactPersonNo, et_cctv_StorageinDays, et_cctv_NoOfCamerasCoverd, et_cctv_NoCameraInstalled;

    String S_cctv_NameOfEstablishment, S_cctv_ContactPerson, S_cctv_ContactPersonNo,
            S_cctv_VendorName, S_cctv_VendorCompany, S_cctv_VendorAddress, S_cctv_VendorContactNo, S_NoOfCamerasCoverd, S_NoCameraInstalled, S_StorageinDays;
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
        setContentView(R.layout.activity_add_nenusaitham_cctv);
//        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        String s_OrgName =bb.getString("OrgName","");
//        TextView tv_OrgName=(TextView)findViewById(R.id.tv_distname) ;
//        tv_OrgName.setText(""+s_OrgName);
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);

        tv_c_psname = (TextView) findViewById(R.id.tv_cctv_psname);
        c_time = (TextView) findViewById(R.id.tv_cctv_time);
        tv_lat = (TextView) findViewById(R.id.tv_lat);
        tv_lng = (TextView) findViewById(R.id.tv_lng);
        cg = (EditText) findViewById(R.id.et_cctv_cg);
        pNo = (EditText) findViewById(R.id.et_cctv_pn);
        secNo = (EditText) findViewById(R.id.et_cctv_sn);
        lm = (EditText) findViewById(R.id.et_cctv_lm);
        location = (EditText) findViewById(R.id.et_cctv_ln);
        iteamsList = (GridLayout) findViewById(R.id.ll_specification);
        iteamsList_name = (GridLayout) findViewById(R.id.ll_text);
        iteamsList_discription = (GridLayout) findViewById(R.id.ll_discr);
        iteamsList_image = (GridLayout) findViewById(R.id.ll_image);
        et_cctv_IpAddress = (EditText) findViewById(R.id.et_cctv_IpAddress);
        et_cctv_InchargeName = (EditText) findViewById(R.id.et_cctv_InchargeName);
        et_cctv_InchargeNo = (EditText) findViewById(R.id.et_cctv_InchargeNo);
        et_cctv_Remarks = (EditText) findViewById(R.id.et_cctv_Remarks);
        et_cctv_locality = (EditText) findViewById(R.id.et_cctv_locality);
        et_cctv_NVRIPAddress = (EditText) findViewById(R.id.et_cctv_NVRIPAddress);
        et_cctv_ChannelNo = (EditText) findViewById(R.id.et_cctv_ChannelNo);
        et_cctv_Specifications = (EditText) findViewById(R.id.et_cctv_Specifications);
        et_cctv_VCompany = (EditText) findViewById(R.id.et_cctv_VCompany);
        et_cctv_VInchargeName = (EditText) findViewById(R.id.et_cctv_VInchargeName);
        et_cctv_VInchargeNo = (EditText) findViewById(R.id.et_cctv_VInchargeNo);
        et_cctv_VInchargeAdress = (EditText) findViewById(R.id.et_cctv_VInchargeAdress);

        et_cctv_NameOfEstablishment = (EditText) findViewById(R.id.et_cctv_NameOfEstablishment);

        et_cctv_ContactPerson = (EditText) findViewById(R.id.et_cctv_ContactPerson);
        et_cctv_ContactPersonNo = (EditText) findViewById(R.id.et_cctv_ContactPersonNo);

        et_cctv_StorageinDays = (EditText) findViewById(R.id.et_cctv_StorageinDays);
        et_cctv_NoOfCamerasCoverd = (EditText) findViewById(R.id.et_cctv_NoOfCamerasCoverd);
        et_cctv_NoCameraInstalled = (EditText) findViewById(R.id.et_cctv_NoCameraInstalled);


        rl_CommunityGrp = (RelativeLayout) findViewById(R.id.rl_CommunityGrp);
        rg_specs = (RadioGroup) findViewById(R.id.rg_specs);
        rd_Sector = (RadioButton) findViewById(R.id.rd_Sector);
        rd_Locality = (RadioButton) findViewById(R.id.rd_Locality);
        spin_cctvtype = (Spinner) findViewById(R.id.spin_cctvtype);
        spin_cctvstatus = (Spinner) findViewById(R.id.spin_cctvstatus);
        spin_cctvreason = (Spinner) findViewById(R.id.spin_cctvreason);
        spin_vendor = (Spinner) findViewById(R.id.spin_vendor);
        spin_pslink = (Spinner) findViewById(R.id.spin_pslink);

        spin_cctvfundstype = (Spinner) findViewById(R.id.spin_cctvfundstype);
        spin_cctvcapacitytype = (Spinner) findViewById(R.id.spin_cctvcapacitytype);
        spin_cctvspecificationtype = (Spinner) findViewById(R.id.spin_cctvspecificationtype);
        spin_ps = (Spinner) findViewById(R.id.spin_ps);

        spin_sector = (Spinner) findViewById(R.id.spin_cctvsector);
        spin_PatrolCarRegion = (Spinner) findViewById(R.id.spin_PatrolCarRegion);
        spin_BlueColtRegion = (Spinner) findViewById(R.id.spin_BlueColtRegion);
        spin_TypeOfEstablishment = (Spinner) findViewById(R.id.spin_TypeOfEstablishment);


        rl_reason = (RelativeLayout) findViewById(R.id.rl_reason);
        Bt_c_vl = (Button) findViewById(R.id.btn_cctv_vl);
        Bt_c_rest = (Button) findViewById(R.id.btn_cctv_reset);
        Bt_c_submit = (Button) findViewById(R.id.btn_cctv_submit);


        et_cctv_locality.setFilters(new InputFilter[]{filter});
        et_cctv_NVRIPAddress.setFilters(new InputFilter[]{filter});
        et_cctv_Specifications.setFilters(new InputFilter[]{filter});
        et_cctv_InchargeName.setFilters(new InputFilter[]{filter});
        et_cctv_Remarks.setFilters(new InputFilter[]{filter});
        lm.setFilters(new InputFilter[]{filter});
        location.setFilters(new InputFilter[]{filter});

        rg_specs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg_specs.getCheckedRadioButtonId();
                RadioButton radiolngButton = (RadioButton) findViewById(selectedId);

                Log.e("radiolngButton", "" + radiolngButton.getText().toString());

                if (radiolngButton.getText().toString().equalsIgnoreCase("Locality")) {
                    et_cctv_locality.setVisibility(View.VISIBLE);
                    rl_CommunityGrp.setVisibility(View.GONE);
                    cctvfunds_id = "";
                    cctvfunds_name = "";
                }
                if (radiolngButton.getText().toString().equalsIgnoreCase("Sector")) {
                    et_cctv_locality.setVisibility(View.GONE);
                    rl_CommunityGrp.setVisibility(View.VISIBLE);
                    S_cctv_locality = "";
                }
            }
        });
        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckConnectivity1();
            }
        });
        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();
                Intent kk = new Intent(getApplicationContext(), AddNenuSaithamCCTV.class);
                startActivity(kk);

            }


        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // S_cg = cg.getText().toString();
                S_pNo = pNo.getText().toString();
                S_secNo = secNo.getText().toString();
                S_lm = lm.getText().toString();
                S_location = location.getText().toString();

                S_cctv_IpAddress = et_cctv_IpAddress.getText().toString();
                S_cctv_InchargeName = et_cctv_InchargeName.getText().toString();
                S_cctv_InchargeNo = et_cctv_InchargeNo.getText().toString();
                S_cctv_Remarks = et_cctv_Remarks.getText().toString();
                S_cctv_NVRIPAddress = et_cctv_NVRIPAddress.getText().toString();
                S_cctv_ChannelNo = et_cctv_ChannelNo.getText().toString();
                S_cctv_locality = et_cctv_locality.getText().toString();
                S_cctv_Specifications = et_cctv_Specifications.getText().toString();


                S_cctv_NameOfEstablishment = et_cctv_NameOfEstablishment.getText().toString();

                S_cctv_ContactPerson = et_cctv_ContactPerson.getText().toString();
                S_cctv_ContactPersonNo = et_cctv_ContactPersonNo.getText().toString();

                S_cctv_VendorName = et_cctv_VInchargeName.getText().toString();
                S_cctv_VendorCompany = et_cctv_VCompany.getText().toString();
                S_cctv_VendorAddress = et_cctv_VInchargeAdress.getText().toString();
                S_cctv_VendorContactNo = et_cctv_VInchargeNo.getText().toString();
                // S_NoOfCamerasCoverd= et_cctv_NoOfCamerasCoverd.getText().toString();
                S_NoCameraInstalled = et_cctv_NoCameraInstalled.getText().toString();
                S_StorageinDays = et_cctv_StorageinDays.getText().toString();

                al.clear();

                string = "";
                arrayList2 = SubmitClicked();
                for (int i = 0; i < arrayList2.size(); i++) {
                    if (i != 0) string = string + ",";
                    string = string + arrayList2.get(i).getId() + "";
                    Log.d("Specificatins", string);
                }
                string1 = "";
                arrayList2.clear();
                arrayList2 = SubmitClicked();
                for (int i = 0; i < arrayList2.size(); i++) {
                    if (i != 0) string1 = string1 + ",";
                    string1 = string1 + arrayList2.get(i).getName() + "";

                }
                Log.e("result7", "" + string);
                Log.e("result7", "" + string1);
//                if (secNo.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Please Enter Sector No", Toast.LENGTH_SHORT).show();
//                } else {

                if (spin_ps != null && spin_ps.getSelectedItem() != null) {
                    if (!(spin_ps.getSelectedItem().toString().trim() == "Select PS")) {
                        if (spin_cctvtype != null && spin_cctvtype.getSelectedItem() != null) {
                            if (!(spin_cctvtype.getSelectedItem().toString().trim() == "Select Camera Type")) {

                                Notnull_Crime();
                                alertDialogue_Conform();


                            } else {
                                Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select PS", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select PS", Toast.LENGTH_SHORT).show();
                }
                // }
            }

            public ArrayList<Samplemyclass> SubmitClicked() {

                for (int i = 0; i < al_cctvtypespecification.size(); i++) {
                    final int finalI = i;
//                    button[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                        }
//                    });
                    if (button[i].isChecked()) {
                        al.add(al_cctvtypespecification.get(finalI));
                    } else {

                    }
                }
                Log.d("Specificatins test", "" + al);
                return al;
            }
        });
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        s_Ps_name = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        S_Orgid = bb.getString("Orgid", "");
        HierarchyId = bb.getString("HierarchyId", "");
        tv_c_psname.setText(s_Ps_name);
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
        InitialConnectivity();
        if (S_Orgid.equalsIgnoreCase("31") |
                S_Orgid.equalsIgnoreCase("33") |
                S_Orgid.equalsIgnoreCase("38")) {
            et_cctv_locality.setVisibility(View.GONE);
            rl_CommunityGrp.setVisibility(View.VISIBLE);
            S_cctv_locality = "";
            et_cctv_locality.setText("");
        } else {
            et_cctv_locality.setVisibility(View.VISIBLE);
            rl_CommunityGrp.setVisibility(View.GONE);
            sector_id = "";
            sector_name = "";
            spin_sector.setSelection(0);
        }

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
    }

    private void Sector(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(AddNenuSaithamCCTV.this, R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_sector.setAdapter(adapter);
            spin_sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        sector_id = country.getId();
                        sector_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void Vendor(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(AddNenuSaithamCCTV.this, R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_PatrolCarRegion.setAdapter(adapter);
            spin_PatrolCarRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        vendor_id = country.getId();
                        vendor_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVreason(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(AddNenuSaithamCCTV.this, R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvreason.setAdapter(adapter);
            spin_cctvreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvreason_id = country.getId();
                        cctvreason_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVFunds(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(AddNenuSaithamCCTV.this, R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvfundstype.setAdapter(adapter);
            spin_cctvfundstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvfunds_id = country.getId();
                        cctvfunds_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVCapacity(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(AddNenuSaithamCCTV.this, R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_TypeOfEstablishment.setAdapter(adapter);
            spin_TypeOfEstablishment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvcapacity_id = country.getId();
                        cctvcapacity_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVSpecification() {


        if (al_cctvtypespecification.size() > 0) {
            button = new CheckBox[al_cctvtypespecification.size()];
            for (int i = 0; i < al_cctvtypespecification.size(); i++) {
                button[i] = new CheckBox(AddNenuSaithamCCTV.this);
                button[i].setText(al_cctvtypespecification.get(i).getName() + "");
                button[i].setTextSize(12);
                button[i].setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                button[i].setTextColor(Color.BLUE);
                iteamsList.addView(button[i]);
                Length_checbox = al_cctvtypespecification.size();
            }

        } else {

            Log.e("arraylist_string", "" + Length_checbox);
        }


    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void CCTVType(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(AddNenuSaithamCCTV.this, R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvtype.setAdapter(adapter);
            spin_cctvtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvtype_id = country.getId();
                        cctvtype_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVStatus(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(AddNenuSaithamCCTV.this, R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvstatus.setAdapter(adapter);
            spin_cctvstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvstatus_id = country.getId();
                        cctvstatus_name = country.getName();

                        if (cctvstatus_id.equalsIgnoreCase("2")) {
                            rl_reason.setVisibility(View.VISIBLE);


                        } else {
                            rl_reason.setVisibility(View.GONE);

                            if (cctvreason_id != null) {
                                cctvreason_id = "";
                                cctvreason_name = "";
                            } else {
                                cctvreason_id = "";
                                cctvreason_name = "";
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void PSLink(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(AddNenuSaithamCCTV.this, R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_BlueColtRegion.setAdapter(adapter);
            spin_BlueColtRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        pslink_id = country.getId();
                        pslink_name = country.getName();


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
            //   new getpay().execute();
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

    private void getPSList_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "19");
            jsonBody.put("Id", "" + HierarchyId);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:PSList" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:PSList" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_ps.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select PS");
                                // Binds all strings into an array
                                al_ps.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_ps.add(wp);
                                }
                                if (al_ps.size() > 0) {
                                    PSS(al_ps);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select PS");
                            // Binds all strings into an array
                            al_ps.add(wb);
                            if (al_ps.size() > 0) {
                                PSS(al_ps);

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

    private void PSS(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_ps.setAdapter(adapter);
            spin_ps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                        S_psid = country.getId();
                        s_Ps_name = country.getName();
                        getSectorType_Api();
                        getCCTVFunds_Api();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void getSectorType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "7");
            jsonBody.put("Id", S_psid);
            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_sector.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Sector");
                                // Binds all strings into an array
                                al_sector.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_sector.add(wp);
                                }
                                if (al_sector.size() > 0) {
                                    Sector(al_sector);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Sector");
                            // Binds all strings into an array
                            al_sector.add(wb);
                            if (al_sector.size() > 0) {
                                Sector(al_sector);

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

    private void getVendorType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "15");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Patrol Car Region" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Patrol Car Region" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_vendor.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Patrol Car Region");
                                // Binds all strings into an array
                                al_vendor.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_vendor.add(wp);
                                }
                                if (al_vendor.size() > 0) {
                                    Vendor(al_vendor);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Patrol Car Region");
                            // Binds all strings into an array
                            al_vendor.add(wb);
                            if (al_vendor.size() > 0) {
                                Vendor(al_vendor);

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

    private void getCCTVFunds_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "8");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:CCTVFunds" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:CCTVFunds" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvfunds.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Community Group");
                                // Binds all strings into an array
                                al_cctvfunds.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvfunds.add(wp);
                                }
                                if (al_cctvfunds.size() > 0) {
                                    CCTVFunds(al_cctvfunds);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Community Group");
                            // Binds all strings into an array
                            al_cctvfunds.add(wb);
                            if (al_cctvfunds.size() > 0) {
                                CCTVFunds(al_cctvfunds);

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

    private void getCCTVCapacity_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "10");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Type Of Establishment" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Type Of Establishment" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvcapacity.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Type Of Establishment");
                                // Binds all strings into an array
                                al_cctvcapacity.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvcapacity.add(wp);
                                }
                                if (al_cctvcapacity.size() > 0) {
                                    CCTVCapacity(al_cctvcapacity);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Type Of Establishment");
                            // Binds all strings into an array
                            al_cctvcapacity.add(wb);
                            if (al_cctvcapacity.size() > 0) {
                                CCTVCapacity(al_cctvcapacity);

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

    private void getCCTVSpecification_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "6");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvtypespecification.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                //  Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Type");
                                // Binds all strings into an array
                                //  al_cctvtypespecification.add(wb2);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvtypespecification.add(wp);
                                }
                                if (al_cctvtypespecification.size() > 0) {
                                    CCTVSpecification();
                                }
                            }


                        } else {
                            //Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Type");
                            // Binds all strings into an array
                            //  al_cctvtypespecification.add(wb2);
                            if (al_cctvtypespecification.size() > 0) {
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

    private void getCCTVType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "18");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvtype.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Camera Type");
                                // Binds all strings into an array
                                al_cctvtype.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvtype.add(wp);
                                }
                                if (al_cctvtype.size() > 0) {
                                    CCTVType(al_cctvtype);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Camera Type");
                            // Binds all strings into an array
                            al_cctvtype.add(wb);
                            if (al_cctvtype.size() > 0) {
                                CCTVType(al_cctvtype);

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

    private void getCCTVStatus_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "4");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvstatus.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Working Status");
                                // Binds all strings into an array
                                al_cctvstatus.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvstatus.add(wp);
                                }
                                if (al_cctvstatus.size() > 0) {
                                    CCTVStatus(al_cctvstatus);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Working Status");
                            // Binds all strings into an array
                            al_cctvstatus.add(wb);
                            if (al_cctvstatus.size() > 0) {
                                CCTVStatus(al_cctvstatus);

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

    private void getBlueColtRegion_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "16");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Blue Colt Region" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Blue Colt Region" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_pslink.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Blue Colt Region");
                                // Binds all strings into an array
                                al_pslink.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_pslink.add(wp);
                                }
                                if (al_pslink.size() > 0) {


                                    PSLink(al_pslink);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Blue Colt Region");
                            // Binds all strings into an array
                            al_pslink.add(wb);
                            if (al_pslink.size() > 0) {
                                PSLink(al_pslink);

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

    private void getCCTVreason_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "3");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvreason.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Reason Of Not working");
                                // Binds all strings into an array
                                al_cctvreason.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvreason.add(wp);
                                }
                                if (al_cctvreason.size() > 0) {
                                    CCTVreason(al_cctvreason);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Reason Of Not working");
                            // Binds all strings into an array
                            al_cctvreason.add(wb);
                            if (al_cctvreason.size() > 0) {
                                CCTVreason(al_cctvreason);

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

    public void Notnull_Crime() {


        if (S_psid == null) {
            S_psid = "";
        } else {
        }

        if (S_cctv_Specifications == null) {
            S_cctv_Specifications = "";
        } else {
        }
        if (cctvtype_id == null) {
            cctvtype_id = "";
        } else {
        }
        if (S_cctv_locality == null) {
            S_cctv_locality = "";
        } else {
        }
        if (sector_id == null) {
            sector_id = "";
        } else {
        }


        if (S_cctv_ContactPersonNo == null) {
            S_cctv_ContactPersonNo = "";
        } else {
        }
        if (S_cctv_ContactPerson == null) {
            S_cctv_ContactPerson = "";
        } else {
        }
        if (S_cctv_NameOfEstablishment == null) {
            S_cctv_NameOfEstablishment = "";
        } else {
        }
        if (cctvcapacity_id == null) {
            cctvcapacity_id = "";
        } else {
        }


        if (S_cctv_VendorName == null) {
            S_cctv_VendorName = "";
        } else {
        }
        if (S_cctv_VendorCompany == null) {
            S_cctv_VendorCompany = "";
        } else {
        }
        if (S_cctv_VendorAddress == null) {
            S_cctv_VendorAddress = "";
        } else {
        }
        if (S_cctv_VendorContactNo == null) {
            S_cctv_VendorContactNo = "";
        } else {
        }

        if (S_NoCameraInstalled == null) {
            S_NoCameraInstalled = "";
        } else {
        }
        if (S_StorageinDays == null) {
            S_StorageinDays = "";
        } else {
        }


        if (sector_id == null) {
            sector_id = "";
        } else {
        }
        if (sector_name == null) {
            sector_name = "";
        } else {
        }

        if (S_lm == null) {
            S_lm = "";
        } else {
        }
        if (S_location == null) {
            S_location = "";
        } else {
        }


        if (S_cg == null) {
            S_cg = "";
        } else {
        }
        if (S_pNo == null) {
            S_pNo = "";
        } else {
        }
        if (S_secNo == null) {
            S_secNo = "";
        } else {
        }
        if (S_cctv_IpAddress == null) {
            S_cctv_IpAddress = "";
        } else {
        }
        if (S_cctv_InchargeName == null) {
            S_cctv_InchargeName = "";
        } else {
        }
        if (S_cctv_InchargeNo == null) {
            S_cctv_InchargeNo = "";
        } else {
        }
        if (S_location == null) {
            S_location = "";
        } else {
        }
        if (S_cctv_Remarks == null) {
            S_cctv_Remarks = "";
        } else {
        }


        if (pslink_id == null) {
            pslink_id = "";
        } else {
        }
        if (vendor_id == null) {
            vendor_id = "";
        } else {
        }
        if (vendor_name == null) {
            vendor_name = "";
        } else {
        }
        if (pslink_name == null) {
            pslink_name = "";
        } else {
        }
        if (cctvreason_id == null) {
            cctvreason_id = "";
        } else {
        }
        if (cctvreason_name == null) {
            cctvreason_name = "";
        } else {
        }


        if (cctvstatus_id == null) {
            cctvstatus_id = "";
        } else {
        }
        if (cctvstatus_name == null) {
            cctvstatus_name = "";
        } else {
        }
        if (cctvfunds_id == null) {
            cctvfunds_id = "";
        } else {
        }


        if (string == null) {
            string = "";
        } else {
        }

        if (cctvcapacity_id == null) {
            cctvcapacity_id = "";
        } else {
        }

        if (sector_id == null) {
            sector_id = "";
        } else {
        }
        if (sector_name == null) {
            sector_name = "";
        } else {
        }


        if (cctvtype_id == null) {
            cctvtype_id = "";
        } else {
        }
        if (cctvtype_name == null) {
            cctvtype_name = "";
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
        if (pslink_name == null) {
            pslink_name = "";
        } else {
        }
    }

    private void alertDialogue_Conform() {
        C_dialog = new Dialog(AddNenuSaithamCCTV.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.confom_cctv_nenusaitham_new);
        C_dialog.show();

        TextView tv_cctv_psname = (TextView) C_dialog.findViewById(R.id.tv_cctv_psname);
        ;
        TextView d_cg = (TextView) C_dialog.findViewById(R.id.tv_cctv_sno);
        ;
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_cctv_locality);
        TextView tv_cctv_cctvtype = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvtype);

        TextView tv_cctv_cctvspecification = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvspecification);
        TextView tv_cctv_cctvEstablishmentType = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvEstablishmentType);
        TextView tv_cctv_cctvEstablishmentName = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvEstablishmentName);

        TextView tv_cctv_cctvinchargenam = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvinchargenam);
        TextView tv_cctv_cctvinchargeno = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvinchargeno);


        TextView tv_cctv_cctvVendorAddress = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvVendorAddress);
        TextView tv_cctv_cctvVendorCompany = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvVendorCompany);
        TextView tv_cctv_cctvVendornam = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvVendornam);
        TextView tv_cctv_cctvVendorno = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvVendorno);

        TextView tv_cctv_cctvCamerasInstalled = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvCamerasInstalled);
        TextView tv_cctv_cctvStorageProvided = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvStorageProvided);
        TextView tv_cctv_cctvLocation = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvLocation);


        if (s_Ps_name != null) {
            tv_cctv_psname.setText(s_Ps_name);
        }
        d_cg.setText("" + sector_name);
        d_ln.setText("" + S_cctv_locality);

        tv_cctv_cctvtype.setText("" + cctvtype_name);
        tv_cctv_cctvspecification.setText("" + S_cctv_Specifications);
        tv_cctv_cctvEstablishmentName.setText("" + S_cctv_NameOfEstablishment);
        tv_cctv_cctvEstablishmentType.setText("" + cctvcapacity_name);

        tv_cctv_cctvinchargenam.setText("" + S_cctv_ContactPerson);
        tv_cctv_cctvinchargeno.setText("" + S_cctv_ContactPersonNo);

        tv_cctv_cctvVendorAddress.setText("" + S_cctv_VendorAddress);
        tv_cctv_cctvVendorCompany.setText("" + S_cctv_VendorCompany);
        tv_cctv_cctvVendornam.setText("" + S_cctv_VendorName);
        tv_cctv_cctvVendorno.setText("" + S_cctv_VendorContactNo);

        tv_cctv_cctvStorageProvided.setText("" + S_StorageinDays);
        tv_cctv_cctvCamerasInstalled.setText("" + S_NoCameraInstalled);

        tv_cctv_cctvLocation.setText("" + S_location);

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_cctv_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.btn_cctv_Cancel);

        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayLocation();
                CheckConnectivity();


                //  query=string;


                //  sendZones(string, "", S_et_Message, ActionName);


            }


        });


        C_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });
    }


    public void SendDataToServer() {


        JSONObject request = new JSONObject();
        try {
            //  request.put("CCTVWorkingStatusID", cctvstatus_id);
            // request.put("CCTVReasonID", cctvreason_id);
            //  request.put("PatrolCarRegion", vendor_id);
            //  request.put("BlueColtRegion", pslink_id);


            request.put("ORGID", S_Orgid);
            request.put("PsID", S_psid);
            request.put("SectorId", sector_id);
            request.put("Locality", S_cctv_locality);
            request.put("Latitude", S_latitude);
            request.put("Longitude", S_longitude);
            request.put("Location", S_location);

            request.put("CCTVType", cctvtype_id);
            request.put("EnteredBy", S_Uname);
            request.put("CCTVSpecifications", S_cctv_Specifications);
            request.put("TypeOfEstablishment", cctvcapacity_id);
            request.put("NameOfEstablishment", S_cctv_NameOfEstablishment);
            request.put("ContactPerson", S_cctv_ContactPerson);
            request.put("ContactPersonNo", S_cctv_ContactPersonNo);
            request.put("VendorName", S_cctv_VendorName);
            request.put("VendorCompany", S_cctv_VendorCompany);
            request.put("VendorAddress", S_cctv_VendorAddress);
            request.put("VendorContactNo", S_cctv_VendorContactNo);
            //  request.put("NoofCamerastocovercompletesourroundings", "" + S_NoOfCamerasCoverd);
            request.put("NoofCamerasInstalled", "" + S_NoCameraInstalled);
            request.put("StorageprovidedinDays", "" + S_StorageinDays);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = request.toString();
        Log.e("VOLLEY", "NenuSaithamCctvEntry" + "Input" + mRequestBody);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                NenuSaithamCctvEntry, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "NenuSaithamCctvEntry" + "Output" + response.toString());
                String Code_new = response.optString("code").toString();
                String Message = response.optString("Message").toString();


                if (Code_new.equalsIgnoreCase("1")) {
                    Log.e("suceess", "" + Message1);
                    C_dialog.dismiss();

                    //cg.setText("");
                    pNo.setText("");
                    secNo.setText("");
                    lm.setText("");

                    S_cg = "";
                    S_secNo = "";
                    S_pNo = "";
                    S_lm = "";
                    S_latitude = "";
                    S_longitude = "";
                    S_location = "";

                    // Log.d("Checkedornot",  ""+ button[i].setChecked(false));
                    // cg.setText("");
                    pNo.setText("");
                    secNo.setText("");
                    lm.setText("");
                    et_cctv_VCompany.setText("");
                    et_cctv_VInchargeName.setText("");
                    et_cctv_VInchargeNo.setText("");
                    et_cctv_VInchargeAdress.setText("");
                    et_cctv_NameOfEstablishment.setText("");
                    et_cctv_ContactPerson.setText("");
                    et_cctv_ContactPersonNo.setText("");
                    et_cctv_StorageinDays.setText("");
                    // et_cctv_NoOfCamerasCoverd.setText("");
                    et_cctv_NoCameraInstalled.setText("");
                    et_cctv_NVRIPAddress.setText("");
                    et_cctv_ChannelNo.setText("");
                    et_cctv_IpAddress.setText("");
                    et_cctv_InchargeName.setText("");
                    et_cctv_InchargeNo.setText("");
                    et_cctv_Remarks.setText("");
                    spin_pslink.setSelection(0);
                    spin_vendor.setSelection(0);
                    spin_cctvreason.setSelection(0);
                    spin_cctvstatus.setSelection(0);
                    spin_cctvtype.setSelection(0);

                    pNo.setText("");
                    secNo.setText("");
                    lm.setText("");
                    al.clear();


                    spin_cctvfundstype.setSelection(0);
                    spin_cctvcapacitytype.setSelection(0);


//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddNenuSaithamCCTV.this);
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Values Saved Succesfully");
                    alertDialog.setIcon(R.drawable.succs);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            dialog.cancel();

                            finish();
                            Intent kk = new Intent(getApplicationContext(), AddNenuSaithamCCTV.class);
                            startActivity(kk);
                        }
                    });
                    alertDialog.show();
//                    finish();
//                    Intent i = new Intent(getApplicationContext(), CCtv_View.this);
//                    startActivity(i);

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
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddNenuSaithamCCTV.this);
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

                Toast.makeText(AddNenuSaithamCCTV.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
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
            Log.e("VOLLEY", "Request:NenuSaithamCCTV" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, NenuSaithamcctvList,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("VOLLEY", response);
                            try {

                                Log.e("VOLLEY", "Response:NenuSaithamCCTV" + response);
                                JSONObject object = new JSONObject(response);
                                String code = object.optString("code").toString();
                                String message = object.optString("Message").toString();
                                if (code.equalsIgnoreCase("1")) {

                                    JSONArray jArray = object.getJSONArray("NenuSaithamCCTV");
                                    int number = jArray.length();

                                    String num = Integer.toString(number);
                                    if (number == 0) {
                                        Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                                    } else {

                                        CGp_arraylist_.clear();
                                        SecNo_arraylist_.clear();
                                        PoleNo_arraylist_.clear();
                                        LandMark_arraylist_.clear();
                                        Add_arraylist_.clear();
                                        al_cctvtypespecification.clear();
                                        Fixed_arraylist_.clear();
                                        PTZ_arraylist_.clear();
                                        IR_arraylist_.clear();
                                        Other_arraylist_.clear();
                                        InchargeName_arraylist_.clear();
                                        InchargeNo_arraylist_.clear();
                                        Remarks_arraylist_.clear();
                                        CCTVType_arraylist_.clear();
                                        for (int i = 0; i < number; i++) {
                                            JSONObject json_data = jArray.getJSONObject(i);
                                            //  String r_id=json_data.getString("Id").toString();
                                            CGp = json_data.getString("TypeOfEstablishment").toString();
                                            SecNo = json_data.getString("SectorId").toString();
                                            PoleNo = json_data.getString("Locality").toString();
                                            status = json_data.getString("NameOfEstablishment").toString();

                                            LandMark = json_data.getString("Locality").toString();
                                            CCTVType = json_data.getString("CCTVType").toString();
                                            Add = json_data.getString("Location").toString();

                                            Fixed = json_data.getString("VendorAddress").toString();
                                            PTZ = json_data.getString("VendorCompany").toString();
                                            IR = json_data.getString("VendorContactNo").toString();
                                            Other = json_data.getString("VendorName").toString();

                                            InchargeName = json_data.getString("ContactPerson").toString();
                                            InchargeNo = json_data.getString("ContactPersonNo").toString();

                                            Remarks = json_data.getString("StorageprovidedinDays").toString();
                                            CCTVCapacity = json_data.getString("NoofCamerasInstalled").toString();

                                            Specification = json_data.getString("CCTVSpecifications").toString();


                                            if (status == null || status.trim().equals("anyType{}") || status.trim()
                                                    .length() <= 0 || status.equalsIgnoreCase("null")) {
                                                status_arraylist_.add("");
                                            } else {
                                                status_arraylist_.add(status);
                                            }
                                            if (CCTVCapacity == null || CCTVCapacity.trim().equals("anyType{}") || CCTVCapacity.trim()
                                                    .length() <= 0 || CCTVCapacity.equalsIgnoreCase("null")) {
                                                CCTVCapacity_arraylist_.add("");
                                            } else {
                                                CCTVCapacity_arraylist_.add(CCTVCapacity);
                                            }
                                            if (Specification == null || Specification.trim().equals("anyType{}") || Specification.trim()
                                                    .length() <= 0 || Specification.equalsIgnoreCase("null")) {
                                                Specification_arraylist_.add("");
                                            } else {
                                                Specification_arraylist_.add(Specification);
                                            }

                                            if (CCTVType == null || CCTVType.trim().equals("anyType{}") || CCTVType.trim()
                                                    .length() <= 0 || CCTVType.equalsIgnoreCase("null")) {
                                                CCTVType_arraylist_.add("");
                                            } else {
                                                CCTVType_arraylist_.add(CCTVType);
                                                // Log.d("Sttausssssss", "" + CCTVType_arraylist_);

                                            }


                                            if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
                                                    .length() <= 0 || Remarks.equalsIgnoreCase("null")) {
                                                Remarks_arraylist_.add("");
                                            } else {
                                                Remarks_arraylist_.add(Remarks);
                                            }

                                            if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
                                                    .length() <= 0 || Remarks.equalsIgnoreCase("null")) {
                                                Remarks_arraylist_.add("");
                                            } else {
                                                Remarks_arraylist_.add(Remarks);
                                            }
                                            if (InchargeNo == null || InchargeNo.trim().equals("anyType{}") || InchargeNo.trim()
                                                    .length() <= 0 || InchargeNo.equalsIgnoreCase("null")) {
                                                InchargeNo_arraylist_.add("");
                                            } else {
                                                InchargeNo_arraylist_.add(InchargeNo);
                                            }

                                            if (InchargeName == null || InchargeName.trim().equals("anyType{}") || InchargeName.trim()
                                                    .length() <= 0 || InchargeName.equalsIgnoreCase("null")) {
                                                InchargeName_arraylist_.add("");
                                            } else {
                                                InchargeName_arraylist_.add(InchargeName);
                                            }
                                            //test
                                            if (Other == null || Other.trim().equals("anyType{}") || Other.trim()
                                                    .length() <= 0 || Other.equalsIgnoreCase("null")) {
                                                Other_arraylist_.add("");
                                            } else {
                                                Other_arraylist_.add(Other);
                                            }
                                            if (CGp == null || CGp.trim().equals("anyType{}") || CGp.trim()
                                                    .length() <= 0 || CGp.equalsIgnoreCase("null")) {
                                                CGp_arraylist_.add("");
                                            } else {
                                                CGp_arraylist_.add(CGp);
                                            }

                                            if (LandMark == null || LandMark.trim().equals("anyType{}") || LandMark.trim()
                                                    .length() <= 0 || LandMark.equalsIgnoreCase("null")) {
                                                LandMark_arraylist_.add("");
                                            } else {
                                                LandMark_arraylist_.add(LandMark);
                                            }
                                            if (Fixed == null || Fixed.trim().equals("anyType{}") || Fixed.trim()
                                                    .length() <= 0 || Fixed.equalsIgnoreCase("null")) {
                                                Fixed_arraylist_.add("");
                                            } else {
                                                Fixed_arraylist_.add(Fixed);
                                            }
                                            if (PTZ == null || PTZ.trim().equals("anyType{}") || PTZ.trim()
                                                    .length() <= 0 || PTZ.equalsIgnoreCase("null")) {
                                                PTZ_arraylist_.add("");
                                            } else {
                                                PTZ_arraylist_.add(PTZ);
                                            }
                                            if (IR == null || IR.trim().equals("anyType{}") || IR.trim()
                                                    .length() <= 0 || IR.equalsIgnoreCase("null")) {
                                                IR_arraylist_.add("");
                                            } else {
                                                IR_arraylist_.add(IR);
                                            }
                                            if (SecNo == null || SecNo.trim().equals("anyType{}") || SecNo.trim()
                                                    .length() <= 0 || SecNo.equalsIgnoreCase("null")) {
                                                SecNo_arraylist_.add("");
                                            } else {
                                                SecNo_arraylist_.add(SecNo);
                                            }
                                            if (PoleNo == null || PoleNo.trim().equals("anyType{}") || PoleNo.trim()
                                                    .length() <= 0 || PoleNo.equalsIgnoreCase("null")) {
                                                PoleNo_arraylist_.add("");
                                            } else {
                                                PoleNo_arraylist_.add(PoleNo);
                                            }
                                            if (Add == null || Add.trim().equals("anyType{}") || Add.trim()
                                                    .length() <= 0 || Add.equalsIgnoreCase("null")) {
                                                Add_arraylist_.add("");
                                            } else {
                                                Add_arraylist_.add(Add);
                                            }
                                        }

                                    }


                                    int count = Add_arraylist_.size();
                                    Log.e(" count", "" + count);
                                    SetValuesToLayout();
                                } else {

                                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddNenuSaithamCCTV.this);
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

    private void SetValuesToLayout() {

        vl_dialog = new Dialog(AddNenuSaithamCCTV.this, R.style.HiddenTitleTheme);
        vl_dialog.setContentView(R.layout.vl_nenusaitham_cctv);
        vl_dialog.show();
        TableRow r0 = (TableRow) vl_dialog.findViewById(R.id.tr0);
        TableRow r1 = (TableRow) vl_dialog.findViewById(R.id.tr1);
        TableRow r2 = (TableRow) vl_dialog.findViewById(R.id.tableRow2);
        TableRow r3 = (TableRow) vl_dialog.findViewById(R.id.tableRow3);
        TableRow r4 = (TableRow) vl_dialog.findViewById(R.id.tableRow4);
        TableRow r5 = (TableRow) vl_dialog.findViewById(R.id.tableRow5);
        TextView NoRecords = (TextView) vl_dialog.findViewById(R.id.tv_norecords);

        TextView t1 = (TextView) vl_dialog.findViewById(R.id.tv_t1);
        TextView t2 = (TextView) vl_dialog.findViewById(R.id.tv_t2);
        TextView t3 = (TextView) vl_dialog.findViewById(R.id.tv_t3);
        TextView t4 = (TextView) vl_dialog.findViewById(R.id.tv_t4);
        TextView t5 = (TextView) vl_dialog.findViewById(R.id.tv_t5);

        TextView st1 = (TextView) vl_dialog.findViewById(R.id.tv_p1);
        TextView st2 = (TextView) vl_dialog.findViewById(R.id.tv_p2);
        TextView st3 = (TextView) vl_dialog.findViewById(R.id.tv_p3);
        TextView st4 = (TextView) vl_dialog.findViewById(R.id.tv_p4);
        TextView st5 = (TextView) vl_dialog.findViewById(R.id.tv_p5);

        TextView dt1 = (TextView) vl_dialog.findViewById(R.id.tv_c1);
        TextView dt2 = (TextView) vl_dialog.findViewById(R.id.tv_c2);
        TextView dt3 = (TextView) vl_dialog.findViewById(R.id.tv_c3);
        TextView dt4 = (TextView) vl_dialog.findViewById(R.id.tv_c4);
        TextView dt5 = (TextView) vl_dialog.findViewById(R.id.tv_c5);

        TextView v1 = (TextView) vl_dialog.findViewById(R.id.tv_m1);
        TextView v2 = (TextView) vl_dialog.findViewById(R.id.tv_m2);
        TextView v3 = (TextView) vl_dialog.findViewById(R.id.tv_m3);
        TextView v4 = (TextView) vl_dialog.findViewById(R.id.tv_m4);
        TextView v5 = (TextView) vl_dialog.findViewById(R.id.tv_m5);

        if ((CCTVType_arraylist_.size()) > 0 | (CCTVType_arraylist_.size()) != 0) {


            Log.e(TAG, "Group" + CGp_arraylist_.size());

            if ((CCTVType_arraylist_.size()) == 1) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(CCTVType_arraylist_.get(0));
                if ((CGp_arraylist_.size()) != 0) {
                    if ((CGp_arraylist_.size()) == 1) {
                        t1.setText(CGp_arraylist_.get(0));

                    }
                }
                if ((status_arraylist_.size()) != 0) {
                    if ((status_arraylist_.size()) == 1) {
                        dt1.setText(status_arraylist_.get(0));
                    }
                }

            }
            if ((CCTVType_arraylist_.size()) == 2) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));

                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));

//
//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//
//                }
//
//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                    }
//
//                }

            }
            if ((CCTVType_arraylist_.size()) == 3) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));
                st3.setText(CCTVType_arraylist_.get(2));

                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));
                dt3.setText(status_arraylist_.get(2));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));
                t3.setText(CGp_arraylist_.get(2));

//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//                    if ((CGp_arraylist_.size()) == 3) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                    }
//                }
//
//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        Log.d("Sttausssssss", "" + status_arraylist_);
//                    }
//                    if ((status_arraylist_.size()) == 3) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                    }
//                }

            }

            if ((CCTVType_arraylist_.size()) == 4) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));
                st3.setText(CCTVType_arraylist_.get(2));
                st4.setText(CCTVType_arraylist_.get(3));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));
                t3.setText(CGp_arraylist_.get(2));
                t4.setText(CGp_arraylist_.get(3));

                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));
                dt3.setText(status_arraylist_.get(2));
                dt4.setText(status_arraylist_.get(3));

//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//                    if ((CGp_arraylist_.size()) == 3) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                    }
//                    if ((CGp_arraylist_.size()) == 4) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                        Log.d("Sttausssssss", "" + CGp_arraylist_);
//                    }
//
//                }

//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                    }
//                    if ((status_arraylist_.size()) == 3) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                    }
//                    if ((status_arraylist_.size()) == 4) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                    }
//
//                }


            }
            if ((CCTVType_arraylist_.size()) == 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);

                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));
                st3.setText(CCTVType_arraylist_.get(2));
                st4.setText(CCTVType_arraylist_.get(3));
                st5.setText(CCTVType_arraylist_.get(4));
                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));
                dt3.setText(status_arraylist_.get(2));
                dt4.setText(status_arraylist_.get(3));
                dt5.setText(status_arraylist_.get(4));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));
                t3.setText(CGp_arraylist_.get(2));
                t4.setText(CGp_arraylist_.get(3));
                t5.setText(CGp_arraylist_.get(4));
//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//                    if ((CGp_arraylist_.size()) == 3) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                    }
//                    if ((CGp_arraylist_.size()) == 4) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                    }
//                    if ((CGp_arraylist_.size()) == 5) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                        t5.setText(CGp_arraylist_.get(4));
//                    }
//                }
//
//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                    }
//                    if ((status_arraylist_.size()) == 3) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                    }
//                    if ((status_arraylist_.size()) == 4) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                    }
//                    if ((PoleNo_arraylist_.size()) == 5) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                        dt5.setText(status_arraylist_.get(4));
//                        Log.d("Sttausssssss", " " + status_arraylist_);
//                    }
//                }
            }
            if ((CCTVType_arraylist_.size()) > 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                Log.e("yes", "" + CGp_arraylist_.size());

                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));
                st3.setText(CCTVType_arraylist_.get(2));
                st4.setText(CCTVType_arraylist_.get(3));
                st5.setText(CCTVType_arraylist_.get(4));

                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));
                dt3.setText(status_arraylist_.get(2));
                dt4.setText(status_arraylist_.get(3));
                dt5.setText(status_arraylist_.get(4));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));
                t3.setText(CGp_arraylist_.get(2));
                t4.setText(CGp_arraylist_.get(3));
                t5.setText(CGp_arraylist_.get(4));

//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//                    if ((CGp_arraylist_.size()) == 3) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                    }
//                    if ((CGp_arraylist_.size()) == 4) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                    }
//                    if ((CGp_arraylist_.size()) == 5) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                        t5.setText(CGp_arraylist_.get(4));
//                    }
//                }
//
//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                    }
//                    if ((status_arraylist_.size()) == 3) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                    }
//                    if ((status_arraylist_.size()) == 4) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                    }
//                    if ((status_arraylist_.size()) == 5) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                        dt5.setText(status_arraylist_.get(4));
//                    }
//                }

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
        C_dialog = new Dialog(AddNenuSaithamCCTV.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.confom_cctv_nenusaitham_new);
        C_dialog.show();

        TextView tv_cctv_psname = (TextView) C_dialog.findViewById(R.id.tv_cctv_psname);
        ;
        TextView d_cg = (TextView) C_dialog.findViewById(R.id.tv_cctv_sno);
        ;
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_cctv_locality);
        TextView tv_cctv_cctvtype = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvtype);

        TextView tv_cctv_cctvspecification = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvspecification);
        TextView tv_cctv_cctvEstablishmentType = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvEstablishmentType);
        TextView tv_cctv_cctvEstablishmentName = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvEstablishmentName);

        TextView tv_cctv_cctvinchargenam = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvinchargenam);
        TextView tv_cctv_cctvinchargeno = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvinchargeno);


        TextView tv_cctv_cctvVendorAddress = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvVendorAddress);
        TextView tv_cctv_cctvVendorCompany = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvVendorCompany);
        TextView tv_cctv_cctvVendornam = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvVendornam);
        TextView tv_cctv_cctvVendorno = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvVendorno);

        TextView tv_cctv_cctvCamerasInstalled = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvCamerasInstalled);
        TextView tv_cctv_cctvStorageProvided = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvStorageProvided);
        TextView tv_cctv_cctvLocation = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvLocation);

        if (s_Ps_name != null) {
            tv_cctv_psname.setText(s_Ps_name);
        }

        if (CGp_arraylist_.size() > count) {
            tv_cctv_cctvEstablishmentType.setText(CGp_arraylist_.get(count));
        }
        if (PoleNo_arraylist_.size() > count) {
            d_ln.setText(PoleNo_arraylist_.get(count));
        }

        if (status_arraylist_.size() > count) {
            tv_cctv_cctvEstablishmentName.setText(status_arraylist_.get(count));
        }


        if (SecNo_arraylist_.size() > count) {
            d_cg.setText(SecNo_arraylist_.get(count));
        }
        if (Add_arraylist_.size() > count) {
            tv_cctv_cctvLocation.setText(Add_arraylist_.get(count));
        }
        if (LandMark_arraylist_.size() > count) {
            d_ln.setText(LandMark_arraylist_.get(count));
        }
        if (Fixed_arraylist_.size() > count) {
            tv_cctv_cctvVendorAddress.setText(Fixed_arraylist_.get(count));
        }
        if (PTZ_arraylist_.size() > count) {
            tv_cctv_cctvVendorCompany.setText(PTZ_arraylist_.get(count));
        }
        if (IR_arraylist_.size() > count) {
            tv_cctv_cctvVendorno.setText(IR_arraylist_.get(count));
        }
        if (Other_arraylist_.size() > count) {
            tv_cctv_cctvVendornam.setText(Other_arraylist_.get(count));
        }


        if (InchargeName_arraylist_.size() > count) {
            tv_cctv_cctvinchargenam.setText(InchargeName_arraylist_.get(count));
        }
        if (InchargeNo_arraylist_.size() > count) {
            tv_cctv_cctvinchargeno.setText(InchargeNo_arraylist_.get(count));
        }
        if (Remarks_arraylist_.size() > count) {
            tv_cctv_cctvStorageProvided.setText(Remarks_arraylist_.get(count));
        }
        if (CCTVType_arraylist_.size() > count) {
            tv_cctv_cctvtype.setText(CCTVType_arraylist_.get(count));
        }

        if (CCTVCapacity_arraylist_.size() > count) {
            tv_cctv_cctvCamerasInstalled.setText(CCTVCapacity_arraylist_.get(count));
        }

        if (Specification_arraylist_.size() > count) {
            tv_cctv_cctvspecification.setText(Specification_arraylist_.get(count));
        }

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_cctv_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.btn_cctv_Cancel);

        C_confm.setText("Ok");
        C_confm.setVisibility(View.GONE);

        C_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });

    }

    private void InitialConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            //  getVendorType_Api();
            // getCCTVFunds_Api();
            // getCCTVSpecification_Api();
            getCCTVCapacity_Api();
            getCCTVType_Api();

            // getCCTVreason_Api();
            // getCCTVStatus_Api();
            // getBlueColtRegion_Api();
            getSectorType_Api();
            getPSList_Api();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            //new pay().execute();
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
                                    AddNenuSaithamCCTV.this, 1000);
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

            tv_lat.setText("" + S_latitude);
            tv_lng.setText("" + S_longitude);
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
                        location.setText(" " + S_loc);
                        // CurrentLocation.setText(Html.fromHtml(t66+S_loc));
                        // CurrentLocation.setText(S_loc);
                    }
                }
            } catch (IOException e) {

                location.setHint(" " + "Enter Your location Address");

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