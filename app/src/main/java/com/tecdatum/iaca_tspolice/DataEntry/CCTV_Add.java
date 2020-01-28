package com.tecdatum.iaca_tspolice.DataEntry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

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
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
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
import com.tecdatum.iaca_tspolice.DataEntry.cct.OLD_fragments.CCtv_Tabs;
import com.tecdatum.iaca_tspolice.DataEntry.cct.OLD_fragments.SurvFragment_new;
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

import static com.tecdatum.iaca_tspolice.activity.SplashScreen.MY_PREFS_NAME;

public class CCTV_Add extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private String CommunityEntry = URLS.CommunityEntry;
    String S_Uname, S_Pass, S_IMEi, S_psid, s_Ps_name, S_psid1, s_Ps_name1, s_role, S_Orgid, HierarchyId;
    String S_cctv_locality, sector_id, sector_name;
    Spinner spin_sector, spin_pcategory;
    RelativeLayout rl_CommunityGrp;
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
    private static final String TAG = CCTV_Add.class.getSimpleName();
    String S_latitude, S_longitude;
    Geocoder geocoder;
    TextView tv_lat, tv_lng, location, c_time;
    ArrayList<Samplemyclass> al_pslink = new ArrayList<>();
    String formattedDate, Message;
    Dialog C_dialog;
    ArrayList<Samplemyclass> al_sector = new ArrayList<>();
    ArrayList<Samplemyclass> al_category = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvmnth = new ArrayList<>();
    ArrayList<Samplemyclass> al_vendor = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvcapacity = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvreason = new ArrayList<>();

    private String cctvMasters = URLS.cctvMasters;
    private String GetCctvCategory = URLS.GetCctvCategory;
    String Category_id, Category_name;
    Spinner spin_ps, spin_cctvfundstype, spin_cctvfoundingtype;
    ArrayList<Samplemyclass> al_cctvfunds = new ArrayList<>();
    ArrayList<Samplemyclass> al_ps = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvtype = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvfouningtype = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvstatus = new ArrayList<>();

    String cctvfunds_id, cctvfunds_name, cctvfounding_id, cctvfounding_name;
    Button btn_acci_addcommunity;

    ArrayList<Samplemyclass> al_ps_community = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvyear = new ArrayList<>();
    Spinner spin_ps_community;
    String S_cctv_CommunityName;
    ProgressDialog progressDialog;
    Spinner spin_cctvyear, spin_cctvmnth, spin_cctvtype, spin_pslink, spin_vendor;
    String Message1;
    String cctvyear_id, cctvyear_name, cctvtype_id, cctvtype_name, pslink_id, pslink_name;
    String cctvmnth_id, cctvmnth_name, vendor_id, vendor_name;
    Spinner spin_cctvcapacitytype;
    String cctvcapacity_id, cctvcapacity_name;
    EditText et_cctv_locality, tv_cctv_CommunityName, et_cctv_VInchargeName, et_cctv_VInchargeNo, et_cctv_VInchargeAdress, et_cctv_Remarks, et_cctv_InchargeName, et_cctv_NVRIPAddress, et_cctv_ChannelNo, et_cctv_IpAddress, et_cctv_InchargeNo;
    private String blockCharacterSet = "'~#^|$%&*![]%";
    Spinner spin_cctvstatus, spin_cctvreason;
    RelativeLayout rl_reason;
    Button Bt_c_rest, Bt_c_submit;
    String cctvstatus_id, cctvstatus_name, cctvreason_id, cctvreason_name;
    String S_et_cctv_locality, S_tv_cctv_CommunityName, S_et_cctv_VInchargeName, S_et_cctv_VInchargeNo, S_et_cctv_VInchargeAdress, S_et_cctv_Remarks, S_et_cctv_InchargeName, S_et_cctv_NVRIPAddress, S_et_cctv_ChannelNo, S_et_cctv_IpAddress, S_et_cctv_InchargeNo;
    TableRow tr_reasonofntworking;
    String S_location;
    EditText et_cctv_FoundingSource, et_cctv_Specifications;
    TextView tv_c_psname;
    String S_cctv_FoundingSource, S_cctv_Specifications;
    private String cctvEntry = URLS.CCTVDataEntry;
    String Code_new;
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
        setContentView(R.layout.activity_cctv__add);
        widgetInitialization();
    }

    private void widgetInitialization() {
        TextView name_cctv = (TextView) findViewById(R.id.name_cctv);
        name_cctv.setText("CCTV Data Entry");

        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        et_cctv_locality = findViewById(R.id.et_cctv_locality);
        rl_CommunityGrp = findViewById(R.id.rl_CommunityGrp);
        spin_sector = findViewById(R.id.spin_cctvsector);
        tv_lat = (TextView) findViewById(R.id.tv_lat);
        tv_lng = (TextView) findViewById(R.id.tv_lng);
        location = findViewById(R.id.et_cctv_ln);
        c_time = findViewById(R.id.tv_cctv_time);
        spin_pcategory = findViewById(R.id.spin_pcategory);
        spin_ps = (Spinner) findViewById(R.id.spin_ps);
        spin_cctvfoundingtype = (Spinner) findViewById(R.id.spin_cctvfoundingtype);
        btn_acci_addcommunity = findViewById(R.id.btn_acci_addcommunity);
        spin_cctvfundstype = (Spinner) findViewById(R.id.spin_cctvfundstype);
        spin_cctvmnth = (Spinner) findViewById(R.id.spin_cctvmnth);
        spin_cctvyear = (Spinner) findViewById(R.id.spin_cctvyear);
        spin_cctvtype = (Spinner) findViewById(R.id.spin_cctvtype);
        spin_pslink = (Spinner) findViewById(R.id.spin_pslink);
        spin_vendor = (Spinner) findViewById(R.id.spin_vendor);
        et_cctv_Remarks = findViewById(R.id.et_cctv_Remarks);
        et_cctv_VInchargeName = findViewById(R.id.et_cctv_VInchargeName);
        et_cctv_VInchargeNo = findViewById(R.id.et_cctv_VInchargeNo);
        et_cctv_InchargeName = findViewById(R.id.et_cctv_InchargeName);
        et_cctv_VInchargeAdress = findViewById(R.id.et_cctv_VInchargeAdress);
        spin_cctvcapacitytype = findViewById(R.id.spin_cctvcapacitytype);
        spin_cctvstatus = findViewById(R.id.spin_cctvstatus);
        rl_reason = (RelativeLayout) findViewById(R.id.rl_reason);
        spin_cctvreason = findViewById(R.id.spin_cctvreason);
        Bt_c_rest = (Button) findViewById(R.id.btn_cctv_reset);
        et_cctv_NVRIPAddress = findViewById(R.id.et_cctv_NVRIPAddress);
        et_cctv_ChannelNo = findViewById(R.id.et_cctv_ChannelNo);
        et_cctv_IpAddress = findViewById(R.id.et_cctv_IpAddress);
        et_cctv_InchargeNo = findViewById(R.id.et_cctv_InchargeNo);
        et_cctv_InchargeNo = findViewById(R.id.et_cctv_InchargeNo);
        et_cctv_Specifications = (EditText) findViewById(R.id.et_cctv_Specifications);
        et_cctv_FoundingSource = (EditText) findViewById(R.id.et_cctv_FoundingSource);
        tv_c_psname = (TextView) findViewById(R.id.tv_cctv_psname);

        Bt_c_submit = findViewById(R.id.btn_cctv_submit);

        location.setFilters(new InputFilter[]{filter});
        et_cctv_locality.setFilters(new InputFilter[]{filter});
        et_cctv_Remarks.setFilters(new InputFilter[]{filter});
        et_cctv_InchargeName.setFilters(new InputFilter[]{filter});
        et_cctv_VInchargeAdress.setFilters(new InputFilter[]{filter});
        et_cctv_VInchargeName.setFilters(new InputFilter[]{filter});
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        s_Ps_name = bb.getString("Psname", "");
        S_psid1 = bb.getString("Psid", "");
        s_Ps_name1 = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        S_Orgid = bb.getString("Orgid", "");
        HierarchyId = bb.getString("HierarchyId", "");
        tv_c_psname.setText(s_Ps_name);
        if (S_Orgid.equalsIgnoreCase("31") | S_Orgid.equalsIgnoreCase("33") | S_Orgid.equalsIgnoreCase("38")) {
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
        Onckicks();
        btn_acci_addcommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Community_Entry();
            }
        });
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
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

    private void Onckicks() {
        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent kk = new Intent(getApplicationContext(), CCtv_Tabs.class);
                startActivity(kk);


                et_cctv_VInchargeNo.setText("");
                et_cctv_VInchargeName.setText("");
                et_cctv_VInchargeAdress.setText("");
                et_cctv_NVRIPAddress.setText("");
                et_cctv_ChannelNo.setText("");
                et_cctv_IpAddress.setText("");
                et_cctv_InchargeName.setText("");
                et_cctv_InchargeNo.setText("");
                et_cctv_Remarks.setText("");
                spin_pslink.setSelection(0);
                spin_vendor.setSelection(0);
                spin_sector.setSelection(0);
                spin_cctvreason.setSelection(0);
                spin_cctvstatus.setSelection(0);
                spin_cctvtype.setSelection(0);
                spin_cctvfundstype.setSelection(0);
                spin_cctvcapacitytype.setSelection(0);


            }


        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Validating", Toast.LENGTH_SHORT).show();

                StringConversion();
                StringNotNull();
                if (S_Orgid.equalsIgnoreCase("31") | S_Orgid.equalsIgnoreCase("33") | S_Orgid.equalsIgnoreCase("38")) {
                    if (spin_pcategory != null && spin_pcategory.getSelectedItem() != null) {
                        if (!(spin_pcategory.getSelectedItem().toString().trim() == "Select Sector")) {


                            if (spin_sector != null && spin_sector.getSelectedItem() != null) {
                                if (!(spin_sector.getSelectedItem().toString().trim() == "Select CCTV Category")) {
                                    if (spin_cctvfoundingtype != null && spin_cctvfoundingtype.getSelectedItem() != null) {
                                        if (!(spin_cctvfoundingtype.getSelectedItem().toString().trim() == "Select Funding Type")) {

                                            if (spin_cctvfundstype != null && spin_cctvfundstype.getSelectedItem() != null) {
                                                if (!(spin_cctvfundstype.getSelectedItem().toString().trim() == "Select Community Group")) {

                                                    if (spin_cctvyear != null && spin_cctvyear.getSelectedItem() != null) {
                                                        if (!(spin_cctvyear.getSelectedItem().toString().trim() == "Select Year")) {
                                                            //strat
                                                            if (cctvmnth_name != null && cctvyear_name != null) {
                                                                String valid_until = cctvmnth_name + "/" + cctvyear_name;
                                                                SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
                                                                Date strDate = null;
                                                                try {
                                                                    strDate = sdf.parse(valid_until);
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                if (new Date().after(strDate)) {

                                                                    if (spin_pslink != null && spin_pslink.getSelectedItem() != null) {
                                                                        if (!(spin_pslink.getSelectedItem().toString().trim() == "Select PS Link")) {
                                                                            if (spin_vendor != null && spin_vendor.getSelectedItem() != null) {
                                                                                if (!(spin_vendor.getSelectedItem().toString().trim() == "Select Vendor")) {
                                                                                    if (!S_et_cctv_VInchargeNo.equalsIgnoreCase("")) {
                                                                                        if (spin_cctvtype != null && spin_cctvtype.getSelectedItem() != null) {
                                                                                            if (!(spin_cctvtype.getSelectedItem().toString().trim() == "Select Camera Type")) {
                                                                                                if (spin_cctvstatus != null && spin_cctvstatus.getSelectedItem() != null) {
                                                                                                    if (!(spin_cctvstatus.getSelectedItem().toString().trim() == "Select Working Status")) {
                                                                                                        if (cctvstatus_id.equalsIgnoreCase("2")) {
                                                                                                            if (spin_cctvreason != null && spin_cctvreason.getSelectedItem() != null) {
                                                                                                                if (!(spin_cctvreason.getSelectedItem().toString().trim() == "Select Reason Of Not working")) {

                                                                                                                    StringConversion();
                                                                                                                    StringNotNull();
                                                                                                                    alertDialogue_Conform();
                                                                                                                } else {
                                                                                                                    Toast.makeText(getApplicationContext(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                                                                                                }
                                                                                                            } else {
                                                                                                                Toast.makeText(getApplicationContext(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        }
                                                                                                    } else {
                                                                                                        Toast.makeText(getApplicationContext(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                } else {
                                                                                                    Toast.makeText(getApplicationContext(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(getApplicationContext(), "Please Enter Vendor Mobile No", Toast.LENGTH_SHORT).show();

                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(getApplicationContext(), "Please Select Vendor", Toast.LENGTH_SHORT).show();

                                                                                }
                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Please Select Vendor", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "Please Select Community Group", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), "Please Select Community Group", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Select Proper Month and Year", Toast.LENGTH_SHORT).show();

                                                                }
                                                                //end
                                                            } else {

                                                            }
                                                        } else {
                                                            if (spin_pslink != null && spin_pslink.getSelectedItem() != null) {
                                                                if (!(spin_pslink.getSelectedItem().toString().trim() == "Select PS Link")) {
                                                                    if (spin_vendor != null && spin_vendor.getSelectedItem() != null) {
                                                                        if (!(spin_vendor.getSelectedItem().toString().trim() == "Select Vendor")) {
                                                                            if (!S_et_cctv_VInchargeNo.equalsIgnoreCase("")) {
                                                                                if (spin_cctvtype != null && spin_cctvtype.getSelectedItem() != null) {
                                                                                    if (!(spin_cctvtype.getSelectedItem().toString().trim() == "Select Camera Type")) {
                                                                                        if (spin_cctvstatus != null && spin_cctvstatus.getSelectedItem() != null) {
                                                                                            if (!(spin_cctvstatus.getSelectedItem().toString().trim() == "Select Working Status")) {
                                                                                                if (cctvstatus_id.equalsIgnoreCase("2")) {
                                                                                                    if (spin_cctvreason != null && spin_cctvreason.getSelectedItem() != null) {
                                                                                                        if (!(spin_cctvreason.getSelectedItem().toString().trim() == "Select Reason Of Not working")) {

                                                                                                            StringConversion();
                                                                                                            StringNotNull();
                                                                                                            alertDialogue_Conform();
                                                                                                        } else {
                                                                                                            Toast.makeText(getApplicationContext(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                                                                                        }
                                                                                                    } else {
                                                                                                        Toast.makeText(getApplicationContext(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                } else {
                                                                                                    StringConversion();
                                                                                                    StringNotNull();
                                                                                                    alertDialogue_Conform();
                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(getApplicationContext(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(getApplicationContext(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Please Enter Vendor Mobile No", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "Please Select Vendor", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), "Please Select Vendor", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Please Select PS Link", Toast.LENGTH_SHORT).show();

                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Please Select PS Link", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    } else {

                                                    }


                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Please Select Community Group", Toast.LENGTH_SHORT).show();

                                                }
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Please Select Community Group", Toast.LENGTH_SHORT).show();

                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please Select Funding Type", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please Select Funding Type", Toast.LENGTH_SHORT).show();

                                    }


                                } else {
                                    Toast.makeText(getApplicationContext(), "Please Select Sector", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Select Sector", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select CCTV Category", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select CCTV Category", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    if (!S_et_cctv_locality.equalsIgnoreCase("")) {
                        if (spin_pcategory != null && spin_pcategory.getSelectedItem() != null) {
                            if (!(spin_pcategory.getSelectedItem().toString().trim() == "Select CCTV Category")) {
                                if (spin_cctvfoundingtype != null && spin_cctvfoundingtype.getSelectedItem() != null) {
                                    if (!(spin_cctvfoundingtype.getSelectedItem().toString().trim() == "Select Funding Type")) {

                                        if (spin_cctvfundstype != null && spin_cctvfundstype.getSelectedItem() != null) {
                                            if (!(spin_cctvfundstype.getSelectedItem().toString().trim() == "Select Community Group")) {

                                                if (spin_cctvyear != null && spin_cctvyear.getSelectedItem() != null) {
                                                    if (!(spin_cctvyear.getSelectedItem().toString().trim() == "Select Year")) {
                                                        //strat
                                                        if (cctvmnth_name != null && cctvyear_name != null) {
                                                            String valid_until = cctvmnth_name + "/" + cctvyear_name;
                                                            SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
                                                            Date strDate = null;
                                                            try {
                                                                strDate = sdf.parse(valid_until);
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }
                                                            if (new Date().after(strDate)) {

                                                                if (spin_pslink != null && spin_pslink.getSelectedItem() != null) {
                                                                    if (!(spin_pslink.getSelectedItem().toString().trim() == "Select PS Link")) {
                                                                        if (spin_vendor != null && spin_vendor.getSelectedItem() != null) {
                                                                            if (!(spin_vendor.getSelectedItem().toString().trim() == "Select Vendor")) {
                                                                                if (!S_et_cctv_VInchargeNo.equalsIgnoreCase("")) {
                                                                                    if (spin_cctvtype != null && spin_cctvtype.getSelectedItem() != null) {
                                                                                        if (!(spin_cctvtype.getSelectedItem().toString().trim() == "Select Camera Type")) {
                                                                                            if (spin_cctvstatus != null && spin_cctvstatus.getSelectedItem() != null) {
                                                                                                if (!(spin_cctvstatus.getSelectedItem().toString().trim() == "Select Working Status")) {
                                                                                                    if (cctvstatus_id.equalsIgnoreCase("2")) {
                                                                                                        if (spin_cctvreason != null && spin_cctvreason.getSelectedItem() != null) {
                                                                                                            if (!(spin_cctvreason.getSelectedItem().toString().trim() == "Select Reason Of Not working")) {

                                                                                                                StringConversion();
                                                                                                                StringNotNull();
                                                                                                                alertDialogue_Conform();
                                                                                                            } else {
                                                                                                                Toast.makeText(getApplicationContext(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        } else {
                                                                                                            Toast.makeText(getApplicationContext(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                                                                                        }
                                                                                                    }
                                                                                                } else {
                                                                                                    Toast.makeText(getApplicationContext(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(getApplicationContext(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(getApplicationContext(), "Please Enter Vendor Mobile No", Toast.LENGTH_SHORT).show();

                                                                                }
                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Please Select Vendor", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "Please Select Vendor", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), "Please Select Community Group", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Please Select Community Group", Toast.LENGTH_SHORT).show();

                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Select Proper Month and Year", Toast.LENGTH_SHORT).show();

                                                            }
                                                            //end
                                                        } else {

                                                        }
                                                    } else {
                                                        if (spin_pslink != null && spin_pslink.getSelectedItem() != null) {
                                                            if (!(spin_pslink.getSelectedItem().toString().trim() == "Select PS Link")) {
                                                                if (spin_vendor != null && spin_vendor.getSelectedItem() != null) {
                                                                    if (!(spin_vendor.getSelectedItem().toString().trim() == "Select Vendor")) {
                                                                        if (!S_et_cctv_VInchargeNo.equalsIgnoreCase("")) {
                                                                            if (spin_cctvtype != null && spin_cctvtype.getSelectedItem() != null) {
                                                                                if (!(spin_cctvtype.getSelectedItem().toString().trim() == "Select Camera Type")) {
                                                                                    if (spin_cctvstatus != null && spin_cctvstatus.getSelectedItem() != null) {
                                                                                        if (!(spin_cctvstatus.getSelectedItem().toString().trim() == "Select Working Status")) {
                                                                                            if (cctvstatus_id.equalsIgnoreCase("2")) {
                                                                                                if (spin_cctvreason != null && spin_cctvreason.getSelectedItem() != null) {
                                                                                                    if (!(spin_cctvreason.getSelectedItem().toString().trim() == "Select Reason Of Not working")) {

                                                                                                        StringConversion();
                                                                                                        StringNotNull();
                                                                                                        alertDialogue_Conform();
                                                                                                    } else {
                                                                                                        Toast.makeText(getApplicationContext(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                } else {
                                                                                                    Toast.makeText(getApplicationContext(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            } else {
                                                                                                StringConversion();
                                                                                                StringNotNull();
                                                                                                alertDialogue_Conform();
                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(getApplicationContext(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(getApplicationContext(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            } else {
                                                                                Toast.makeText(getApplicationContext(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "Please Enter Vendor Mobile No", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(), "Please Select Vendor", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Please Select Vendor", Toast.LENGTH_SHORT).show();

                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Please Select PS Link", Toast.LENGTH_SHORT).show();

                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Please Select PS Link", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                } else {

                                                }


                                            } else {
                                                Toast.makeText(getApplicationContext(), "Please Select Community Group", Toast.LENGTH_SHORT).show();

                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please Select Community Group", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please Select Funding Type", Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please Select Funding Type", Toast.LENGTH_SHORT).show();

                                }


                            } else {
                                Toast.makeText(getApplicationContext(), "Please Select CCTV Category", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select CCTV Category", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter Locality", Toast.LENGTH_SHORT).show();

                    }
                }
            }


        });


    }

    private void alertDialogue_Conform() {
        C_dialog = new Dialog(CCTV_Add.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.confom_cctv_new);


        TextView d_cg = (TextView) C_dialog.findViewById(R.id.tv_cctv_cg);
        TextView d_pno = (TextView) C_dialog.findViewById(R.id.tv_cctv_pno);
        TextView d_sc = (TextView) C_dialog.findViewById(R.id.tv_cctv_sno);
        TextView d_lm = (TextView) C_dialog.findViewById(R.id.tv_cctv_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_cctv_ln);
        TextView tv_cctv_cctvtype = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvtype);
        TextView tv_cctv_cctvstatus = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvstatus);
        TextView tv_cctv_cctvreason = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvreason);
        TextView tv_cctv_cctvvendor = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvvendor);


        TextView tv_cctv_cctvspecificationtype = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvspecificationtype);
        TextView tv_cctv_cctvcapacitytype = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvcapacitytype);
        TextView tv_cctv_cctvpslink = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvpslink);
        TextView tv_cctv_cctvipAdress = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvipAdress);
        TextView tv_cctv_cctvinchargenam = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvinchargenam);
        TextView tv_cctv_cctvinchargeno = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvinchargeno);
        TextView tv_cctv_cctvremarks = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvremarks);

        tr_reasonofntworking = (TableRow) C_dialog.findViewById(R.id.tr_reasonofntworking);

        d_cg.setText(cctvfunds_name);
        d_pno.setText("" + cctvmnth_name + "&" + cctvyear_name);

        if (sector_id != null) {
            if (!(sector_id.equalsIgnoreCase("0"))) {
                d_sc.setText("" + sector_name);
            } else {
                d_sc.setText("");
            }
        } else {
            d_sc.setText("");
        }

        d_lm.setText(cctvfounding_name);
        d_ln.setText(S_location);


        if (cctvstatus_id.equalsIgnoreCase("2")) {
            tr_reasonofntworking.setVisibility(View.VISIBLE);
        } else {
            tr_reasonofntworking.setVisibility(View.GONE);
        }

        tv_cctv_cctvtype.setText(cctvtype_name);
        tv_cctv_cctvcapacitytype.setText(cctvcapacity_name);
        tv_cctv_cctvspecificationtype.setText("" + S_cctv_Specifications);
        tv_cctv_cctvstatus.setText(cctvstatus_name);
        tv_cctv_cctvreason.setText(cctvreason_name);
        tv_cctv_cctvvendor.setText(vendor_name);
        tv_cctv_cctvpslink.setText(pslink_name);

        tv_cctv_cctvipAdress.setText(S_et_cctv_IpAddress);
        tv_cctv_cctvinchargenam.setText(S_et_cctv_InchargeName);
        tv_cctv_cctvinchargeno.setText(S_et_cctv_InchargeNo);
        tv_cctv_cctvremarks.setText(S_et_cctv_Remarks);


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
        //tv_cctv_cctvspecificationtype.setText(string1);

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

        progressDialog = new ProgressDialog(CCTV_Add.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject request = new JSONObject();
        try {


            request.put("CCTVCommunityGroup_Id", "" + cctvfunds_id);
            request.put("CCTVTypemaster_ID", cctvtype_id);
            request.put("Location", S_location);
            request.put("lat", S_latitude);
            request.put("long", S_longitude);
            request.put("PsID", S_psid);
            request.put("Psname", s_Ps_name);
            request.put("Sector_Details_Id", sector_id);
            request.put("Locality_Village", S_cctv_locality);
            request.put("CCTVWorkingStatus_ID", cctvstatus_id);
            request.put("CCTVReason_ID", cctvreason_id);
            request.put("CCTVVendor_ID", vendor_id);
            request.put("Psconnection_ID", pslink_id);
            request.put("Ipaddress", S_et_cctv_IpAddress);
            request.put("CCTVCategory_ID", "1");
            request.put("inchargeName", S_et_cctv_InchargeName);
            request.put("inchargeMobileNo", S_et_cctv_InchargeNo);
            request.put("remarks", S_et_cctv_Remarks);
            request.put("EnteredBy", S_Uname);
            request.put("CameracapacityMaster_ID", cctvcapacity_id);
            request.put("CameraSpecifications", S_cctv_Specifications);
            request.put("VendorName", "A");
            request.put("VendorMobileNo", S_et_cctv_VInchargeNo);
            request.put("VendorAddress", S_et_cctv_VInchargeAdress);
            request.put("NVRIPAddress", S_et_cctv_NVRIPAddress);
            request.put("ChannelNo", S_et_cctv_ChannelNo);
            request.put("FoundTypeId", cctvfounding_id);
            request.put("CCTVFoundSource", S_cctv_FoundingSource);
            request.put("MonthId", cctvmnth_id);
            request.put("YearId", cctvyear_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = request.toString();
        Log.e("VOLLEY", "AddAccident_View" + "Input" + mRequestBody);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                cctvEntry, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "AddAccident_View" + "Output" + response.toString());
                Code_new = response.optString("code").toString();
                String Message = response.optString("Message").toString();


                if (Code_new.equalsIgnoreCase("1")) {
                    C_dialog.dismiss();
                    progressDialog.dismiss();

                    et_cctv_VInchargeNo.setText("");
                    et_cctv_VInchargeName.setText("");
                    et_cctv_VInchargeAdress.setText("");
                    et_cctv_NVRIPAddress.setText("");
                    et_cctv_ChannelNo.setText("");
                    et_cctv_IpAddress.setText("");
                    et_cctv_InchargeName.setText("");
                    et_cctv_InchargeNo.setText("");
                    et_cctv_Remarks.setText("");
                    spin_pslink.setSelection(0);
                    spin_vendor.setSelection(0);
                    spin_sector.setSelection(0);
                    spin_cctvreason.setSelection(0);
                    spin_cctvstatus.setSelection(0);
                    spin_cctvtype.setSelection(0);
                    spin_cctvfundstype.setSelection(0);
                    spin_cctvcapacitytype.setSelection(0);


                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CCTV_Add.this);
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("" + Message);
                    alertDialog.setIcon(R.drawable.succs);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            dialog.cancel();

                            finish();
                            Intent kk = new Intent(CCTV_Add.this, CCtv_Tabs.class);
                            startActivity(kk);
                        }
                    });
                    alertDialog.show();

                } else {
                    Log.e("not sucess", "" + Message);
                    C_dialog.dismiss();
                    progressDialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CCTV_Add.this);
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


                Toast.makeText(CCTV_Add.this, "Unable to Connect To Server", Toast.LENGTH_SHORT).show();

            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                4530000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);

    }

    private void StringNotNull() {
        if (S_et_cctv_VInchargeName != null) {

        } else {
            S_et_cctv_VInchargeName = "";
        }

        if (S_et_cctv_VInchargeNo != null) {

        } else {
            S_et_cctv_VInchargeNo = "";
        }

        if (S_et_cctv_VInchargeAdress != null) {

        } else {
            S_et_cctv_VInchargeAdress = "";
        }

        if (S_et_cctv_Remarks != null) {

        } else {
            S_et_cctv_Remarks = "";
        }
        if (S_et_cctv_InchargeName != null) {

        } else {
            S_et_cctv_InchargeName = "";
        }
        if (S_et_cctv_NVRIPAddress != null) {

        } else {
            S_et_cctv_NVRIPAddress = "";
        }
        if (S_et_cctv_ChannelNo != null) {

        } else {
            S_et_cctv_ChannelNo = "";
        }
        if (S_et_cctv_IpAddress != null) {

        } else {
            S_et_cctv_IpAddress = "";
        }
        if (S_et_cctv_locality != null) {

        } else {
            S_et_cctv_locality = "";
        }
        if (S_tv_cctv_CommunityName != null) {

        } else {
            S_tv_cctv_CommunityName = "";
        }
        if (S_et_cctv_InchargeNo != null) {

        } else {
            S_et_cctv_InchargeNo = "";
        }

        if (S_location != null) {

        } else {
            S_location = "";
        }


        if (cctvmnth_id != null) {

        } else {
            cctvmnth_id = "";
        }
        if (cctvyear_id != null) {

        } else {
            cctvyear_id = "";
        }
    }

    private void StringConversion() {

        S_et_cctv_locality = et_cctv_locality.getText().toString();

        S_et_cctv_VInchargeName = et_cctv_VInchargeName.getText().toString();

        S_et_cctv_VInchargeNo = et_cctv_VInchargeNo.getText().toString();

        S_et_cctv_VInchargeAdress = et_cctv_VInchargeAdress.getText().toString();

        S_et_cctv_Remarks = et_cctv_Remarks.getText().toString();

        S_et_cctv_InchargeName = et_cctv_InchargeName.getText().toString();

        S_et_cctv_NVRIPAddress = et_cctv_NVRIPAddress.getText().toString();
        S_et_cctv_ChannelNo = et_cctv_ChannelNo.getText().toString();
        S_et_cctv_IpAddress = et_cctv_IpAddress.getText().toString();
        S_et_cctv_InchargeNo = et_cctv_InchargeNo.getText().toString();
        S_location = location.getText().toString();
        S_cctv_Specifications = et_cctv_Specifications.getText().toString();
        S_cctv_FoundingSource = et_cctv_FoundingSource.getText().toString();


    }

    public void Community_Entry() {
        C_dialog = new Dialog(CCTV_Add.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.communityentry_cctv);
        C_dialog.show();

        tv_cctv_CommunityName = C_dialog.findViewById(R.id.tv_cctv_CommunityName);

        spin_ps_community = (Spinner) C_dialog.findViewById(R.id.spin_ps_community);
        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_cctv_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.btn_cctv_Cancel);
        getPSListCommunity_Api();
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                S_cctv_CommunityName = tv_cctv_CommunityName.getText().toString();

                if (spin_ps_community != null && spin_ps_community.getSelectedItem() != null) {
                    if (!(spin_ps_community.getSelectedItem().toString().trim() == "Select PS")) {
                        if (tv_cctv_CommunityName.getText().toString() != null) {

                            CheckConnectivity_addcommunity();

                        } else {
                            Toast.makeText(getApplicationContext(), "Please Enter Community Name", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select PS", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select PS", Toast.LENGTH_SHORT).show();
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

    public void CheckConnectivity_addcommunity() {
        Sendorddcommunity();
    }

    public void Sendorddcommunity() {


        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject request = new JSONObject();
        try {


            request.put("Community", S_cctv_CommunityName);
            request.put("PsID", S_psid);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = request.toString();
        Log.e("VOLLEY", "CommunityEntry" + "Input" + mRequestBody);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                CommunityEntry, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "CommunityEntry" + "Output" + response.toString());
                String Code_new = response.optString("code").toString();
                String Message = response.optString("Message").toString();


                if (Code_new.equalsIgnoreCase("1")) {
                    Log.e("suceess", "" + Message1);
                    C_dialog.dismiss();
                    progressDialog.dismiss();
                    tv_cctv_CommunityName.setText("");
                    getCCTVFunds_Api();
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
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
//                    finish();
//                    Intent i = new Intent(getApplicationContext(), CCtv_View.this);
//                    startActivity(i);

                } else {
                    Log.e("not sucess", "" + Message);
                    C_dialog.dismiss();
                    progressDialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
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
                Toast.makeText(getApplicationContext(), "Unable to Connect To Server", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                4530000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);


    }

    private void getPSListCommunity_Api() {

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
                        String Message = object.optString("Message").toString();

                        al_ps_community.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select PS");
                                // Binds all strings into an array
                                al_ps_community.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_ps_community.add(wp);
                                }
                                if (al_ps_community.size() > 0) {
                                    PSS_Community(al_ps_community);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select PS");
                            // Binds all strings into an array
                            al_ps_community.add(wb);
                            if (al_ps_community.size() > 0) {
                                PSS_Community(al_ps_community);

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

    private void PSS_Community(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_ps_community.setAdapter(adapter);
            spin_ps_community.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void InitialConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            al_pslink.add(new Samplemyclass("11", "Select PS Link"));
            al_pslink.add(new Samplemyclass("1", "Connected"));
            al_pslink.add(new Samplemyclass("2", "Not Connected"));
            getCCTVFunds_Api();
            getSectorType_Api();
            getCategory_Api();
            getPSList_Api();
            getCCTVFoundingType_Api();
            getCCTVYear_Api();
            getCCTVMnth_Api();
            getCCTVType_Api();
            getCCTVStatus_Api();
            PSLink(al_pslink);
            getVendorType_Api();
            getCCTVreason_Api();
            getCCTVCapacity_Api();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void getCCTVCapacity_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "5");
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

                        al_cctvcapacity.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Capacity Type");
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
                            Samplemyclass wb = new Samplemyclass("0", "Select Capacity Type");
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

    private void CCTVCapacity(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvcapacitytype.setAdapter(adapter);
            spin_cctvcapacitytype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void PSLink(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_pslink.setAdapter(adapter);
            spin_pslink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void getCCTVType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "1");
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

    private void CCTVType(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
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

    private void getVendorType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "2");
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

                        al_vendor.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Vendor");
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
                            Samplemyclass wb = new Samplemyclass("0", "Select Vendor");
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

    private void Vendor(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_vendor.setAdapter(adapter);
            spin_vendor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        vendor_id = country.getId();
                        vendor_name = country.getName();

//                        if (vendor_id.equalsIgnoreCase("10")) {
//                            ll_vendorOthers.setVisibility(View.VISIBLE);
//                        } else {
//                            ll_vendorOthers.setVisibility(View.GONE);
//
//                            et_cctv_VInchargeNo.setText("");
//                            et_cctv_VInchargeName.setText("");
//                            et_cctv_VInchargeAdress.setText("");
//                            S_cctv_VInchargeAdress = "";
//                            S_cctv_VInchargeName = "";
//                            S_cctv_VInchargeNo = "";
//                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void getCCTVYear_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "14");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:cctvyear" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:cctvyear" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvyear.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Year");
                                // Binds all strings into an array
                                al_cctvyear.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvyear.add(wp);
                                }
                                if (al_cctvyear.size() > 0) {
                                    CCTVYear(al_cctvyear);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Year");
                            // Binds all strings into an array
                            al_cctvyear.add(wb);
                            if (al_cctvyear.size() > 0) {
                                CCTVYear(al_cctvyear);

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

    private void CCTVYear(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvyear.setAdapter(adapter);
            spin_cctvyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvyear_id = country.getId();
                        cctvyear_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void getCCTVMnth_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "13");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:cctvmnth" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:cctvmnth" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvmnth.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Month");
                                // Binds all strings into an array
                                al_cctvmnth.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvmnth.add(wp);
                                }
                                if (al_cctvmnth.size() > 0) {
                                    CCTVMnth(al_cctvmnth);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Month");
                            // Binds all strings into an array
                            al_cctvmnth.add(wb);
                            if (al_cctvmnth.size() > 0) {
                                CCTVMnth(al_cctvmnth);

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

    private void CCTVMnth(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvmnth.setAdapter(adapter);
            spin_cctvmnth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvmnth_id = country.getId();
                        cctvmnth_name = country.getName();


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
            Log.e("VOLLEY", "Sectors:Accused_Categories" + mRequestBody);
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

    private void getCategory_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "9");


            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Sectors:GetCctvCategory" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GetCctvCategory, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:GetCctvCategory" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_category.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select CCTV Category");
                                // Binds all strings into an array
                                al_category.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("Name").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_category.add(wp);
                                }
                                if (al_category.size() > 0) {
                                    Category(al_category);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select CCTV Category");
                            // Binds all strings into an array
                            al_category.add(wb);
                            if (al_category.size() > 0) {
                                Category(al_category);

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

    private void Sector(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
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

    private void Category(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_pcategory.setAdapter(adapter);
            spin_pcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        Category_id = country.getId();
                        Category_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
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

    private void getCCTVFunds_Api() {


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "17");
            jsonBody.put("Id", "" + S_psid);

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

    private void CCTVFunds(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
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
                        Log.e("cctvfunds_id", "" + cctvfunds_id);

                        if (cctvfunds_id.equalsIgnoreCase("6")) {
                            //  ll_nenusaitham.setVisibility(View.VISIBLE);
                        } else {
                            // ll_nenusaitham.setVisibility(View.GONE);

                        }
                        //Toast.makeText(CCTV_Add.this, "" + cctvfunds_id, Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
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
                                    CCTV_Add.this, 1000);
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

    private void getCCTVFoundingType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "11");
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

                        al_cctvfouningtype.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Funding Type");
                                // Binds all strings into an array
                                al_cctvfouningtype.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvfouningtype.add(wp);
                                }
                                if (al_cctvfouningtype.size() > 0) {
                                    CCTVFoundingType(al_cctvfouningtype);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Funding Type");
                            // Binds all strings into an array
                            al_cctvfouningtype.add(wb);
                            if (al_cctvfouningtype.size() > 0) {
                                CCTVFoundingType(al_cctvfouningtype);

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

    private void CCTVFoundingType(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvfoundingtype.setAdapter(adapter);
            spin_cctvfoundingtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvfounding_id = country.getId();
                        cctvfounding_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
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

    private void CCTVStatus(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
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

    private void CCTVreason(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
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
    public void onResume() {

        super.onResume();
        Log.w(TAG, "App onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.w(TAG, "App onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w(TAG, "App onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.w(TAG, "App onStop");
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "App onDestroy");
    }

    @Override
    public void onLocationChanged(Location location) {
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
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        } catch (Throwable e) {
            e.printStackTrace();
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
            List<Address> addresses;
            try {
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            } catch (Throwable e) {
                e.printStackTrace();
            }
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 7);
                if (addresses != null) {
                    if (addresses.size() > 0) {
                        String address = addresses.get(0).getAddressLine(0);
                        String address1 = addresses.get(0).getSubLocality();
                        String address2 = addresses.get(0).getLocality();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(address);
//                        stringBuilder.append(",");
//                        stringBuilder.append(address1);
//                        stringBuilder.append(",");
//                        stringBuilder.append(address2);
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

        try {


            if (ActivityCompat.checkSelfPermission(getApplicationContext().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }
}
