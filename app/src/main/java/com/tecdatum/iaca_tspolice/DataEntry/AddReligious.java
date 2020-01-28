package com.tecdatum.iaca_tspolice.DataEntry;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.AlertDialog;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.TEST.ChoosePhoto;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddReligious extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    ImageView iv_1, iv_2, iv_3, iv_4;
    String iv1, iv2, iv3, iv4;
    private ChoosePhoto choosePhoto=null;
    private String LandmarkMasters = URLS.LandmarkMasters;
    private String LandmarkEntry = URLS.LandmarkEntry;
    private String LandmarkList = URLS.LandmarkList;
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = AddReligious.class.getSimpleName();
    private LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x01;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 50;
    private static final int READ_CAMERA_PERMISSIONS_REQUEST = 20;
    private static final int WRITE_SETTINGS_PERMISSION = 20;
    private boolean active = false;
    protected Location mLastLocation;
    private static int UPDATE_INTERVAL = 60000;
    private boolean mRequestingLocationUpdates = true;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Dialog C_dialog, vdialog;

//    private String[]  countryList0= { "Select Type of Sensitive Place","High Sensitivity","Non Sensitivity",
//            "Low Sensitivity","Medium Sensitivity"};

    ArrayAdapter<String> adapter_state;
    ArrayList<Samplemyclass> countryList = new ArrayList<Samplemyclass>();
    ArrayList<Samplemyclass> countryList0 = new ArrayList<Samplemyclass>();//levl
    ArrayList<Samplemyclass> countryList1 = new ArrayList<Samplemyclass>();//type

    ArrayList<Samplemyclass> lm_cat = new ArrayList<Samplemyclass>();//levl
    ArrayList<Samplemyclass> lm_subcat = new ArrayList<Samplemyclass>();//type


    ArrayList<Samplemyclass> sen_cat = new ArrayList<Samplemyclass>();//levl
    ArrayList<Samplemyclass> sen_subcat = new ArrayList<Samplemyclass>();//type


    Button Bt_c_date, Bt_cr_time, Bt_c_vl, Bt_c_rest, Bt_c_submit;
    TextView d_wp, d_nowp, d_in, d_ph, d_dis, d_sp, d_add;
    EditText et_in, et_r_inDesig, et_lm_nearest_Landmark, et_ph, et_dis, et_Locality, et_lm_PrevIncidentHistory, et_wp;
    String S_in, S_inDesig, S_ph, S_dis, S_add, S_Locality, S_wp, Message, S_psid, S_IMEi, S_Pass, S_Uname, S_latitude, S_longitude, Cat1, item2, SubCat1, item, S_nearest_Landmarkitem, S_PrevIncidentHistory, S_sp_subtype_id, S_sp_subtype, S_sp_id, S_sp, s_Ps_name, formattedDate;
    Integer Code1;
    ProgressDialog progressDialog;
    String Message1;
    ArrayList<String> twp_arraylist_ = new ArrayList<String>();
    ArrayList<String> nwp_arraylist_ = new ArrayList<String>();
    ArrayList<String> add_arraylist_ = new ArrayList<String>();
    ArrayList<String> ni_arraylist_ = new ArrayList<String>();
    ArrayList<String> cNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> Remarks_arraylist_ = new ArrayList<String>();
    ArrayList<String> EnteredDate_arraylist_ = new ArrayList<String>();
    String twp, nwp, add, ni, cNo, Remarks, EnteredDate;
    ;
    Spinner Sp_tpwk, Sp_stype, Sp_sensitv, Sp_sensitivetype;
    int Code;
    RelativeLayout Rl_Ssubtype, R_subtype;
    TextView tv_c_psname, c_time;
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
        setContentView(R.layout.activity_add_religious);
        R_subtype = (RelativeLayout) findViewById(R.id.Rl_subtype);
        Rl_Ssubtype = (RelativeLayout) findViewById(R.id.Rl_Ssubtype);
        c_time = (TextView) findViewById(R.id.tv_r_time);
        tv_c_psname = (TextView) findViewById(R.id.tv_r_psname);
        et_lm_nearest_Landmark = (EditText) findViewById(R.id.et_lm_nearest_Landmark);
        Bt_c_vl = (Button) findViewById(R.id.btn_r_vl);
        Bt_c_rest = (Button) findViewById(R.id.btn_r_reset);
        Bt_c_submit = (Button) findViewById(R.id.btn_r_submit);

        et_in = (EditText) findViewById(R.id.et_r_in);
        et_r_inDesig = (EditText) findViewById(R.id.et_r_inDesig);
        et_ph = (EditText) findViewById(R.id.et_r_pno);
        et_dis = (EditText) findViewById(R.id.et_r_Discription);
        et_Locality = (EditText) findViewById(R.id.et_r_locality);
        et_wp = (EditText) findViewById(R.id.et_r_wp);
        et_lm_PrevIncidentHistory = (EditText) findViewById(R.id.et_lm_PrevIncidentHistory);
        Sp_tpwk = (Spinner) findViewById(R.id.spin_r_wp);
        Sp_stype = (Spinner) findViewById(R.id.spin_r_ws);
        Sp_sensitv = (Spinner) findViewById(R.id.spin_r_sa);
        Sp_sensitivetype = (Spinner) findViewById(R.id.spin_r_sensitivetype);

        et_Locality .setFilters(new InputFilter[]{filter});
        et_lm_nearest_Landmark.setFilters(new InputFilter[]{filter});
        et_in .setFilters(new InputFilter[]{filter});
        et_r_inDesig.setFilters(new InputFilter[]{filter});
        et_dis.setFilters(new InputFilter[]{filter});
        et_wp .setFilters(new InputFilter[]{filter});
        et_lm_PrevIncidentHistory.setFilters(new InputFilter[]{filter});


        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        s_Ps_name = bb.getString("Psname", "");
//        s_role =bb.getString("Role","");
        tv_c_psname.setText(s_Ps_name);
        String s_OrgName = bb.getString("OrgName", "");
        TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        tv_OrgName.setText("" + s_OrgName);
        CheckConnectivity2();
        iv_1 = (ImageView) findViewById(R.id.iv_ivfront);
        iv_2 = (ImageView) findViewById(R.id.iv_ivback);
        iv_3 = (ImageView) findViewById(R.id.iv_ivside);
        iv_4 = (ImageView) findViewById(R.id.iv_ivtop);


        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   choosePhoto = new ChoosePhoto(AddReligious.this);
                switch (v.getId()) {

                    case R.id.iv_ivfront:
                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, 1);

                }
            }
        });
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
//                    case R.id.iv_1:
//                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(i, 1);
//
//                        break;
                    case R.id.iv_ivback:
                        Intent ii = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(ii, 2);
                        break;
//                    case R.id.iv_3:
//                        Intent i3 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(i3, 3);
//                        break;
//
//                    case R.id.iv_4:
//                        Intent ii3 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(ii3, 4);
//
//                    case R.id.iv_5:
//                        Intent i4 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(i4, 5);
//                        break;

                }
            }
        });
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

//                    case R.id.iv_1:
//                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(i, 1);
//
//                        break;
//                    case R.id.iv_2:
//                        Intent ii = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(ii, 2);
                    case R.id.iv_ivside:
                        Intent i3 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i3, 3);
                        break;

//                    case R.id.iv_4:
//                        Intent ii3 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(ii3, 4);
//
//                    case R.id.iv_5:
//                        Intent i4 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(i4, 5);
//                        break;

                }
            }
        });
        iv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

//                    case R.id.iv_1:
//                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(i, 1);
//
//                        break;
//                    case R.id.iv_2:
//                        Intent ii = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(ii, 2);
//                    case R.id.iv_3:
//                        Intent i3 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(i3, 3);
//                        break;

                    case R.id.iv_ivtop:
                        Intent ii3 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(ii3, 4);

//                    case R.id.iv_5:
//                        Intent i4 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(i4, 5);
//                        break;
                }
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
                et_wp.setText("");
                et_ph.setText("");
                et_in.setText("");
                et_dis.setText("");
                et_lm_nearest_Landmark.setText("");
                //  Et_yellow.setText("");
                Sp_tpwk.setSelection(0);
                Sp_stype.setSelection(0);
                Sp_sensitv.setSelection(0);

                finish();
                Intent i = new Intent(getApplicationContext(), AddReligious.class);
                startActivity(i);

            }
        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                S_nearest_Landmarkitem = et_lm_nearest_Landmark.getText().toString();
                S_in = et_in.getText().toString();
                S_PrevIncidentHistory = et_lm_PrevIncidentHistory.getText().toString();
                S_inDesig = et_r_inDesig.getText().toString();
                S_ph = et_ph.getText().toString();
                S_dis = et_dis.getText().toString();
                S_wp = et_wp.getText().toString();
                S_Locality = et_Locality.getText().toString();
                if (!(et_wp.getText().toString().trim().isEmpty())) {
                    if (Sp_tpwk != null && Sp_tpwk.getSelectedItem() != null) {
                        if (!(Sp_tpwk.getSelectedItem().toString().trim() == "Select Landmark Category")) {
                            if (Sp_stype != null && Sp_stype.getSelectedItem() != null) {
                                if (!(Sp_stype.getSelectedItem().toString().trim() == "Select Landmark Sub Category")) {
                                    if (Sp_sensitv != null && Sp_sensitv.getSelectedItem() != null) {
                                        if (!(Sp_sensitv.getSelectedItem().toString().trim() == "Select Sensitive Category")) {
//                                            if (Sp_sensitivetype != null && Sp_sensitivetype.getSelectedItem() != null) {
//                                                if (!(Sp_sensitivetype.getSelectedItem().toString().trim() == "Select Sensitive Sub Category")) {
                                            if (!(et_Locality.getText().toString().trim().isEmpty())) {
                                                if (!(et_dis.getText().toString().trim().isEmpty())) {

                                                    alertDialogue_Conform();

                                                } else {
                                                    Toast.makeText(AddReligious.this, "Please Enter Remarks", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(AddReligious.this, "Location is mandatory", Toast.LENGTH_SHORT).show();
                                            }
//                                                } else {
//                                                    Toast.makeText(AddReligious.this, "Select Sensitive Sub Category", Toast.LENGTH_SHORT).show();
//                                                }
//                                            } else {
//                                                Toast.makeText(AddReligious.this, "Select Sensitive Sub Category", Toast.LENGTH_SHORT).show();
//                                            }

                                        } else {
                                            Toast.makeText(AddReligious.this, "Select Sensitive Category", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(AddReligious.this, "Select Sensitive Category", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(AddReligious.this, "Select Landmark Sub Category", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddReligious.this, "Select Landmark Sub Category", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(AddReligious.this, "Select Landmark Category", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddReligious.this, "Select Landmark Category", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddReligious.this, "Please Enter Name Of Landmark", Toast.LENGTH_SHORT).show();
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
//        adapter_state = new ArrayAdapter<String>(getApplicationContext(),
//                android.R.layout.simple_spinner_item, countryList0);
//        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        Sp_sensitv.setAdapter(adapter_state);
//
//        Sp_sensitv.setSelection(0, true);
//        View k = Sp_sensitv.getSelectedView();
//        ((TextView)k).setTextColor(Color.parseColor("#004D92"));
//
//        Sp_sensitv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
//                int pos = adapterView.getSelectedItemPosition();
//                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
//                ((TextView) adapterView.getChildAt(0)).setTextSize(12);
//                if(pos!= 0){
//                    Sp_sensitv.setSelection(j);
//                  String  S_detected_ = (String) Sp_sensitv.getSelectedItem();
//                    Log.e("crime_Genianty",""+S_detected_);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

//        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddReligious_View.this,R.layout.spinner_item, countryList0);
//        Sp_sensitv.setAdapter(adapt);
//        Sp_sensitv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                              @Override
//                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                                  int pos = adapterView.getSelectedItemPosition();
//
//                                                  //  Toast.makeText(getApplicationContext(), "" + pos, Toast.LENGTH_SHORT).show();
//
//                                                  if(pos!= 0) {
//                                                      Sp_sensitv.setSelection(i);
//                                                        S_sp = (String) Sp_sensitv.getSelectedItem();
//                                                      Log.e("crime_Genianty",""+S_sp);
//                                                          //Log.e("Cat1", "" + Cat1);
//                                                         // Toast.makeText(getApplicationContext(), "" + Cat1, Toast.LENGTH_SHORT).show();
//                                                          //Toast.makeText(getApplicationContext(), "Crime Type is Mandatory ", Toast.LENGTH_SHORT).show();
//                                                  }
//
//                                              }
//
//                                              @Override
//                                              public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                              }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()

        {
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

    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            //new getpay().execute();
            GetReligious();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
//
//    class getpay extends AsyncTask<Void, Void, Void> {
//        private final ProgressDialog dialog = new ProgressDialog(AddReligious_View.this);
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
//            String SOAP_ACTION = "http://tempuri.org/GetReligiousRecordsdata";
//            String METHOD_NAME = "GetReligiousRecordsdata";
//            String NAMESPACE = "http://tempuri.org/";
//            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetReligiousRecordsdata";
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            request.addProperty("userName", S_Uname);
//            request.addProperty("Password", S_Pass);
//            request.addProperty("ImeI", S_IMEi);
//            envelope.setOutputSoapObject(request);
//
//            Log.e("1245", S_Pass + "    " + S_IMEi);
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
//                System.out.println("******** : " + root.getPropertyCount());
//
//                twp_arraylist_.clear();
//                nwp_arraylist_.clear();
//                add_arraylist_.clear();
//                ni_arraylist_.clear();
//                cNo_arraylist_.clear();
//                Remarks_arraylist_.clear();
//                EnteredDate_arraylist_.clear();
//
//                for (int j = 0; j < root.getPropertyCount(); j++) {
//                    SoapObject s_deals = (SoapObject) root.getProperty(j);
//                    System.out.println("********Count : " + s_deals.getPropertyCount());
//                    for (int i = 0; i < s_deals.getPropertyCount(); i++) {
//                        Object property = s_deals.getProperty(i);
//                        System.out.println("******** : " + property);
//                        twp = s_deals.getProperty("TypeofWorshiPlace").toString();
//                        nwp = s_deals.getProperty("NameoftheWorshipPlace").toString();
//                        add = s_deals.getProperty("Address").toString();
//                        ni = s_deals.getProperty("NameoftheIncharge").toString();
//                        cNo = s_deals.getProperty("ContactNo").toString();
//                        Remarks = s_deals.getProperty("Remarks").toString();
//                        EnteredDate = s_deals.getProperty("SensitivityLevel").toString();
//                    }
//                    if (twp == null || twp.trim().equals("anyType{}") || twp.trim()
//                            .length() <= 0) {
//                        twp_arraylist_.add("");
//                    } else {
//                        twp_arraylist_.add(twp);
//                    }
//                    if (nwp == null || nwp.trim().equals("anyType{}") || nwp.trim()
//                            .length() <= 0) {
//                        nwp_arraylist_.add("");
//                    } else {
//                        nwp_arraylist_.add(nwp);
//                    }
//                    if (add == null || add.trim().equals("anyType{}") || add.trim()
//                            .length() <= 0) {
//                        add_arraylist_.add("");
//                    } else {
//                        add_arraylist_.add(add);
//                    }
//                    if (ni == null || ni.trim().equals("anyType{}") || ni.trim()
//                            .length() <= 0) {
//                        ni_arraylist_.add("");
//                    } else {
//                        ni_arraylist_.add(ni);
//                    }
//                    if (cNo == null || cNo.trim().equals("anyType{}") || cNo.trim()
//                            .length() <= 0) {
//                        cNo_arraylist_.add("");
//                    } else {
//                        cNo_arraylist_.add(cNo);
//                    }
//
//                    if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
//                            .length() <= 0) {
//                        Remarks_arraylist_.add("");
//                    } else {
//                        Remarks_arraylist_.add(Remarks);
//                    }
//                    if (EnteredDate == null || EnteredDate.trim().equals("anyType{}") || EnteredDate.trim()
//                            .length() <= 0) {
//                        EnteredDate_arraylist_.add("");
//                    } else {
//                        EnteredDate_arraylist_.add(EnteredDate);
//                    }
//
//
//                }
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
//                Toast.makeText(AddReligious_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
//            } else if (httpexcep) {
//                Toast.makeText(AddReligious_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
//            } else if (genexcep) {
//                Toast.makeText(AddReligious_View.this, "Please try later", Toast.LENGTH_LONG).show();
//            } else {
//                //Log.d(TAG,  "Re "+final_result.toString());
//                Log.e(TAG, "Cnumber : " + twp_arraylist_);
//                Log.e(TAG, "Cnumber : " + nwp_arraylist_);
//                Log.e(TAG, "Cnumber : " + add_arraylist_);
//                Log.e(TAG, "Cnumber : " + ni_arraylist_);
//                Log.e(TAG, "Cnumber : " + cNo_arraylist_);
//                Log.e(TAG, "Cnumber : " + EnteredDate_arraylist_);
//
//                int count = twp_arraylist_.size();
//
//                Log.e(" count", "" + count);
//                SetValuesToLayout(count);
//                if (Code == 0) {
//                } else {
//                    Log.e("not sucess", "" + Code);
//                    // Toast.makeText(AddReligious_View.this,""+Message,Toast.LENGTH_LONG).show();
//                }
//                Log.e("result", "done");
//                //tableview();
//            }
//            timeoutexcep = false;
//            httpexcep = false;
//            genexcep = false;
//        }
//    }

    private void SetValuesToLayout(int coun) {

        vdialog = new Dialog(AddReligious.this, R.style.HiddenTitleTheme);
        vdialog.setContentView(R.layout.vl_rel);
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

        if ((twp_arraylist_.size()) > 0 | (twp_arraylist_.size()) != 0) {
            Log.e("1", "" + twp_arraylist_.size());
            if ((twp_arraylist_.size()) == 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                t3.setText(twp_arraylist_.get(2));
                t4.setText(twp_arraylist_.get(3));
                t5.setText(twp_arraylist_.get(4));
                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                st3.setText(nwp_arraylist_.get(2));
                st4.setText(nwp_arraylist_.get(3));
                st5.setText(nwp_arraylist_.get(4));

                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));

                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }

                if (ni_arraylist_.size() == 3) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                }

                if (ni_arraylist_.size() == 4) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                    dt4.setText(ni_arraylist_.get(3));

                }
                if (ni_arraylist_.size() == 4) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                    dt4.setText(ni_arraylist_.get(3));
                    dt5.setText(ni_arraylist_.get(4));
                }

            }
            if ((twp_arraylist_.size()) == 1) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(twp_arraylist_.get(0));
                st1.setText(nwp_arraylist_.get(0));
                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));
                }

            }
            if ((twp_arraylist_.size()) == 2) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));

                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }


            }
            if ((twp_arraylist_.size()) == 3) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                t3.setText(twp_arraylist_.get(2));
                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                st3.setText(nwp_arraylist_.get(2));
                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));

                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }

                if (ni_arraylist_.size() == 3) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                }
            }
            if ((twp_arraylist_.size()) == 4) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);

                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                t3.setText(twp_arraylist_.get(2));
                t4.setText(twp_arraylist_.get(3));
                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                st3.setText(nwp_arraylist_.get(2));
                st4.setText(nwp_arraylist_.get(3));

                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));
                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }

                if (ni_arraylist_.size() == 3) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                }

                if (ni_arraylist_.size() == 4) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                    dt4.setText(ni_arraylist_.get(3));

                }
            }

            if ((twp_arraylist_.size()) > 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                Log.e("yes", "" + twp_arraylist_.size());
                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                t3.setText(twp_arraylist_.get(2));
                t4.setText(twp_arraylist_.get(3));
                t5.setText(twp_arraylist_.get(4));

                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                st3.setText(nwp_arraylist_.get(2));
                st4.setText(nwp_arraylist_.get(3));
                st5.setText(nwp_arraylist_.get(4));


                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));

                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }

                if (ni_arraylist_.size() == 3) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                }

                if (ni_arraylist_.size() == 4) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                    dt4.setText(ni_arraylist_.get(3));
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
        C_dialog = new Dialog(AddReligious.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_cnfm_rel);
        C_dialog.show();

        TextView d_wp = (TextView) C_dialog.findViewById(R.id.tv_religiou_wp);
        TextView d_nowp = (TextView) C_dialog.findViewById(R.id.tv_religiou_name_wp);
        TextView d_in = (TextView) C_dialog.findViewById(R.id.tv_religiou_in);
        TextView d_ph = (TextView) C_dialog.findViewById(R.id.tv_religiou_ph);
        TextView d_dis = (TextView) C_dialog.findViewById(R.id.tv_religiou_dis);
        TextView d_sp = (TextView) C_dialog.findViewById(R.id.tv_religiou_sp);
        TextView d_add = (TextView) C_dialog.findViewById(R.id.tv_religiou_add);
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_religiou_pscode);

        pscode.setText(s_Ps_name);


        if (twp_arraylist_.size() > count) {
            d_wp.setText(twp_arraylist_.get(count));
        }
        if (nwp_arraylist_.size() > count) {
            d_nowp.setText(nwp_arraylist_.get(count));
        }
        if (ni_arraylist_.size() > count) {
            d_in.setText(ni_arraylist_.get(count));
        }
        if (cNo_arraylist_.size() > count) {
            d_ph.setText(cNo_arraylist_.get(count));
        }
        if (Remarks_arraylist_.size() > count) {
            d_dis.setText(Remarks_arraylist_.get(count));
        }
        if (EnteredDate_arraylist_.size() > count) {
            d_sp.setText(EnteredDate_arraylist_.get(count));
        }
        if (add_arraylist_.size() > count) {
            d_add.setText(add_arraylist_.get(count));
        }

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_r_Confm);


        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });

    }

    private void alertDialogue_Conform() {

        NotNull();

        C_dialog = new Dialog(AddReligious.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.confrom_religious);
        C_dialog.show();

        TextView d_wp = (TextView) C_dialog.findViewById(R.id.tv_religiou_wp);
        TextView d_nowp = (TextView) C_dialog.findViewById(R.id.tv_religiou_name_swp);
        TextView d_now = (TextView) C_dialog.findViewById(R.id.tv_religiou_name_wp);
        TextView d_in = (TextView) C_dialog.findViewById(R.id.tv_religiou_in);
        TextView d_ph = (TextView) C_dialog.findViewById(R.id.tv_religiou_ph);
        TextView d_dis = (TextView) C_dialog.findViewById(R.id.tv_religiou_dis);
        TextView d_sp = (TextView) C_dialog.findViewById(R.id.tv_religiou_sp);
        TextView d_add = (TextView) C_dialog.findViewById(R.id.tv_religiou_add);


        d_wp.setText(item2);
        d_nowp.setText(item);
        d_now.setText(S_wp);
        d_in.setText(S_in);
        d_ph.setText(S_ph);
        d_dis.setText(S_dis);
        d_sp.setText(S_sp);
        d_add.setText(S_Locality);

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_r_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.btn_r_Cancel);

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

//    private void AddReligious_View() {
//        progressDialog = new ProgressDialog(AddReligious_View.this);
//        progressDialog.setMessage("Loading, Please Wait...");
//        progressDialog.show();
//        JSONObject jsonBody = new JSONObject();
//        try {
//
//            request.addProperty("username", S_Uname);
//            request.addProperty("password", S_Pass);
//            request.addProperty("imei", S_IMEi);
//            request.addProperty("connectionType", "TabletPc");
//            request.addProperty("TypeofworshipPlaceId",SubCat1);
//            request.addProperty("TypeofworshipPlace",item);
//            request.addProperty("AddressoftheWorshipPlace", S_Locality);
//
//            request.addProperty("NameofWorshipPlace",S_wp);
//            request.addProperty("PersonName", S_in);
//            request.addProperty("PhoneNumber", S_ph);
//            request.addProperty("latitude", S_latitude);
//            request.addProperty("longitude", S_longitude);
//            request.addProperty("description", S_dis);
//            request.addProperty("SensitivePlace", S_sp);
//
//            request.addProperty("NearestLandmark", S_nearest_Landmarkitem);
//
//            //test
//
//
//            jsonBody.put("LandMarkMaster_Id",item);
//            jsonBody.put("AddressWorshipPlace", S_Locality);
//
//            jsonBody.put("NameoWorshipPlace",S_wp);
//            jsonBody.put("NameoftheIncharge", S_in);
//
//            jsonBody.put("InchargeDesignation", S_in);
//            jsonBody.put("ContactNo", S_ph);
//            jsonBody.put("Lattitude", S_latitude);
//            jsonBody.put("Longitude", S_longitude);
//
//            jsonBody.put("Remarks", S_dis);
//            jsonBody.put("SensitivePlace", S_sp);
//            jsonBody.put("PSId",S_psid);
//
//
////
////
////            {
////                "LandMarkMaster_Id":"1",
////                    "NameoWorshipPlace":"testing",
////                    "AddressWorshipPlace":"testing",
////                    "Lattitude":"17.123",
////                    "Longitude":"78.123",
////                    "NameoftheIncharge":"nagendhar",
////                    "InchargeDesignation":"sp",
////                    "ContactNo":"99999999999",
////                    "Remarks":"testing",
////                    "PSId":"57",
////                    "sensitivityLevelId":"1",
////                    "SensitivitySubLevelId":"1",
////                    "PrevIncidentHistory":"testing",
////                    "CreatedBy":"spmdk"
////
////            }
//
//
//            final String mRequestBody = jsonBody.toString();
//            Log.e("VOLLEY", "Request:AddReligious_View" + mRequestBody);
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonObj, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.e("VOLLEY", "Response:AddReligious_View" + response);
//                    try {
//                        JSONObject object = new JSONObject(response);
//                        String code = object.optString("Code").toString();
//                        Message = object.optString("Message").toString();
//
//                        if (code.equalsIgnoreCase("0")){
//                            progressDialog.dismiss();
//                            Log.e("suceess",""+Message);
//                            C_dialog.dismiss();
//                            et_wp.setText("");
//                            et_ph.setText("");
//                            et_in.setText("");
//                            et_dis.setText("");
//                            //  Et_yellow.setText("");
//                            Sp_tpwk.setSelection(0);
//                            Sp_stype.setSelection(0);
//                            Sp_sensitv.setSelection(0);
//                            item2="";
//                            S_Locality="";
//                            item="";
//                            S_in="";
//                            S_ph="";
//                            S_latitude="";
//                            S_longitude="";
//                            S_dis="";
//                            S_sp="";
//                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReligious_View.this);
//                            alertDialog.setTitle("Success");
//                            alertDialog.setMessage("Values Saved Succesfully");
//                            alertDialog.setIcon(R.drawable.succs);
//                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Write your code here to invoke YES event
//                                    dialog.cancel();
//                                }
//                            });
//                            alertDialog.show();
//                        }else{
//                            progressDialog.dismiss();
//                            Log.e("not sucess",""+Message);
//                            C_dialog.dismiss();
//                            et_wp.setText("");
//                            et_ph.setText("");
//                            et_in.setText("");
//                            et_dis.setText("");
//                            //  Et_yellow.setText("");
//                            Sp_tpwk.setSelection(0);
//                            Sp_stype.setSelection(0);
//                            Sp_sensitv.setSelection(0);
//                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReligious_View.this);
//                            alertDialog.setTitle("Alert");
//                            alertDialog.setMessage(""+Message);
//                            alertDialog.setIcon(R.drawable.alertt);
//                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            });
//                            alertDialog.show();
//                            // Toast.makeText(AddAccident_View.this,""+Message,Toast.LENGTH_LONG).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        progressDialog.dismiss();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("VOLLEY", error.toString());
//                    progressDialog.dismiss();
//                }
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    try {
//                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                        return null;
//                    }
//                }
//            };
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                    30000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//    private void GetReligious() {
//        try {
//            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//            S_Uname = bb.getString("UserName", "");
//            S_Pass = bb.getString("password", "");
//            S_IMEi = bb.getString("imei", "");
//            S_psid = bb.getString("Psid", "");
//
//
//            JSONObject jsonBody = new JSONObject();
//            jsonBody.put("userName", S_Uname);
//            jsonBody.put("Password", S_Pass);
//            jsonBody.put("ImeI", S_IMEi);
//            jsonBody.put("Psid", S_psid);
//            final String mRequestBody = jsonBody.toString();
//            Log.e("VOLLEY", "Request:GetCrimeData" + mRequestBody);
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_getData, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("VOLLEY", response);
//                    try {
//
//                        Log.e("VOLLEY", "Response:GetCrimeData" + response);
//                        JSONObject object=new JSONObject(response);
//                        int code = Integer.parseInt(object.optString("Code").toString());
//                        String message = object.optString("Message").toString();
//                        if (code == 0) {
//
//                            JSONArray jArray = object.getJSONArray("Data");
//                            int number = jArray.length();
//
//                            String num = Integer.toString(number);
//                            if (number == 0) {
//                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
//                            } else {
//                                twp_arraylist_.clear();
//                                nwp_arraylist_.clear();
//                                add_arraylist_.clear();
//                                ni_arraylist_.clear();
//                                cNo_arraylist_.clear();
//                                Remarks_arraylist_.clear();
//                                EnteredDate_arraylist_.clear();
//                                for( int i = 0; i < number; i++ ) {
//                                    JSONObject json_data = jArray.getJSONObject(i);
//                                    //  String r_id=json_data.getString("Id").toString();
//                                    twp = json_data.getString("TypeofWorshiPlace").toString();
//                                    nwp = json_data.getString("NameoftheWorshipPlace").toString();
//                                    add = json_data.getString("Address").toString();
//                                    ni = json_data.getString("NameoftheIncharge").toString();
//                                    cNo = json_data.getString("ContactNo").toString();
//                                    Remarks = json_data.getString("Remarks").toString();
//                                    EnteredDate = json_data.getString("SensitivityLevel").toString();
//
//                                    if(twp == null || twp.trim().equals("anyType{}") || twp.trim()
//                                            .length() <= 0){
//                                        twp_arraylist_.add("");
//                                    }else {
//                                        twp_arraylist_.add(twp);
//                                    }
//                                    if(nwp == null || nwp.trim().equals("anyType{}") || nwp.trim()
//                                            .length() <= 0){
//                                        nwp_arraylist_.add("");
//                                    }else {
//                                        nwp_arraylist_.add(nwp);
//                                    }
//                                    if(add == null || add.trim().equals("anyType{}") || add.trim()
//                                            .length() <= 0){
//                                        add_arraylist_.add("");
//                                    }else {
//                                        add_arraylist_.add(add);
//                                    }
//                                    if(ni == null || ni.trim().equals("anyType{}") || ni.trim()
//                                            .length() <= 0){
//                                        ni_arraylist_.add("");
//                                    }else {
//                                        ni_arraylist_.add(ni);
//                                    }
//                                    if(cNo == null || cNo.trim().equals("anyType{}") || cNo.trim()
//                                            .length() <= 0){
//                                        cNo_arraylist_.add("");
//                                    }else {
//                                        cNo_arraylist_.add(cNo);
//                                    }
//
//                                    if(Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
//                                            .length() <= 0){
//                                        Remarks_arraylist_.add("");
//                                    }else {
//                                        Remarks_arraylist_.add(Remarks);
//                                    }
//                                    if(EnteredDate == null || EnteredDate.trim().equals("anyType{}") || EnteredDate.trim()
//                                            .length() <= 0){
//                                        EnteredDate_arraylist_.add("");
//                                    }else {
//                                        EnteredDate_arraylist_.add(EnteredDate);
//                                    }
//                                }
//
//                            }
//
//                            int count=  twp_arraylist_.size();
//                            Log.e(" count",""+count);
//                            SetValuesToLayout(count);
//                        }else{
//
//                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReligious_View.this);
//                            alertDialog.setTitle("Response");
//                            alertDialog.setMessage(""+message);
//                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Write your code here to invoke YES event
//                                    dialog.cancel();
//                                }
//                            });
//                            alertDialog.show();
//                        }
//                    }catch(JSONException e){
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("VOLLEY", error.toString());
//                }
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    try {
//                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                        return null;
//                    }
//                }
//            };
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                    30000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    class pay extends AsyncTask<Void, Void, Void> {
//
//        private final ProgressDialog dialog = new ProgressDialog(AddReligious_View.this);
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
//            String SOAP_ACTION = "http://tempuri.org/AddLandMarks";
//            String METHOD_NAME = "" +
//                    "";
//            String NAMESPACE = "http://tempuri.org/";
//            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx";
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            request.addProperty("username", S_Uname);
//            request.addProperty("password", S_Pass);
//            request.addProperty("imei", S_IMEi);
//            request.addProperty("connectionType", "TabletPc");
//            request.addProperty("TypeofworshipPlaceId", SubCat1);
//            request.addProperty("TypeofworshipPlace", item);
//            request.addProperty("AddressoftheWorshipPlace", S_Locality);
//
//            request.addProperty("NameofWorshipPlace", S_wp);
//            request.addProperty("PersonName", S_in);
//            request.addProperty("PhoneNumber", S_ph);
//            request.addProperty("latitude", S_latitude);
//            request.addProperty("longitude", S_longitude);
//            request.addProperty("description", S_dis);
//            request.addProperty("SensitivePlace", S_sp);
//
//            request.addProperty("NearestLandmark", S_nearest_Landmarkitem);
//            envelope.setOutputSoapObject(request);
//
//
//            Log.e("request", "" + request);
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//            androidHttpTransport.debug = true;
//            Log.e("AddReligious_ENVOLOPE", "" + envelope);
//            try {
//                androidHttpTransport.call(SOAP_ACTION, envelope);
//                String result1 = envelope.getResponse().toString();
//
//                Log.e("result_AddReligious", "" + result1);
//                SoapObject result = (SoapObject) envelope.bodyIn;
//                String[] testValues = new String[result.getPropertyCount()];
//                for (int i = 0; i < result.getPropertyCount(); i++) {
//                    Object property = result.getProperty(i);
//                    testValues[i] = result.getProperty(i).toString();
//                    SoapObject category_list = (SoapObject) property;
//                    Code1 = Integer.parseInt(category_list.getProperty("Code").toString());
//                    Message1 = category_list.getProperty("Message").toString();
//
//                }
//            } catch (XmlPullParserException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
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
//                Toast.makeText(AddReligious_View.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
//            } else if (httpexcep) {
//                Toast.makeText(AddReligious_View.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
//            } else if (genexcep) {
//                Toast.makeText(AddReligious_View.this, "Please try later", Toast.LENGTH_LONG).show();
//            } else {
//                if (Code1 != null) {
//                    if (Code1 == 0) {
//                        Log.e("suceess", "" + Message1);
//                        C_dialog.dismiss();
//                        et_wp.setText("");
//                        et_ph.setText("");
//                        et_in.setText("");
//                        et_dis.setText("");
//                        et_lm_nearest_Landmark.setText("");
//                        //  Et_yellow.setText("");
//                        Sp_tpwk.setSelection(0);
//                        Sp_stype.setSelection(0);
//                        Sp_sensitv.setSelection(0);
//                        item2 = "";
//                        S_Locality = "";
//                        item = "";
//                        S_in = "";
//                        S_ph = "";
//                        S_latitude = "";
//                        S_longitude = "";
//                        S_dis = "";
//                        S_sp = "";
//                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReligious_View.this);
//                        alertDialog.setTitle("Success");
//                        alertDialog.setMessage("Values Saved Succesfully");
//                        alertDialog.setIcon(R.drawable.succs);
//                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Write your code here to invoke YES event
//                                dialog.cancel();
//                            }
//                        });
//                        alertDialog.show();
//                    } else {
////                    Log.e("not sucess",""+Message1);
////                    C_dialog.dismiss();
////                    et_wp.setText("");
////                    et_ph.setText("");
////                    et_in.setText("");
////                    et_dis.setText("");
////                    //  Et_yellow.setText("");
////                    Sp_tpwk.setSelection(0);
////                    Sp_stype.setSelection(0);
////                    Sp_sensitv.setSelection(0);
//                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReligious_View.this);
//                        alertDialog.setTitle("Alert");
//                        alertDialog.setMessage("" + Message1);
//                        alertDialog.setIcon(R.drawable.alertt);
//                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                        alertDialog.show();
//                        // Toast.makeText(AddReligious_View.this,""+Message,Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
//                }
//                Log.e("result", "done");
//                //tableview();
//            }
//            timeoutexcep = false;
//            httpexcep = false;
//            genexcep = false;
//        }
//    }

    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            // new pay().execute();
            AddReligious();

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
            //    new getworship().execute();

            getworship_Api();
            getSensitivity_Api();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void getSensitivity_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "3");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:AccidentMasters" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:AccidentMasters" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        sen_cat.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb1 = new Samplemyclass("0", "Select Sensitive Category");
                                // Binds all strings into an array
                                sen_cat.add(wb1);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    sen_cat.add(wp);
                                }
                                if (sen_cat.size() > 0) {
                                    Sensitivity(sen_cat);
                                }
                            }


                        } else {
                            Samplemyclass wb1 = new Samplemyclass("0", "Select Sensitive Category");
                            // Binds all strings into an array
                            sen_cat.add(wb1);
                            if (sen_cat.size() > 0) {
                                Sensitivity(sen_cat);

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

    private void getSubSensitivity_Api(String Stypeid) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "4");
            jsonBody.put("Id", "" + Stypeid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        sen_subcat.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Sensitive Sub Category");
                                // Binds all strings into an array
                                sen_subcat.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    sen_subcat.add(wp);
                                }
                                if (sen_subcat.size() > 0) {
                                    SubSensitivity(sen_subcat);
                                }
                            }


                        } else {
                            Samplemyclass wb2 = new Samplemyclass("0", "Select Sensitive Sub Category");
                            // Binds all strings into an array
                            sen_subcat.add(wb2);
                            if (sen_subcat.size() > 0) {
                                SubSensitivity(sen_subcat);

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

    private void getworship_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "1");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:AccidentMasters" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:AccidentMasters" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        lm_cat.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb1 = new Samplemyclass("0", "Select Landmark Category");
                                // Binds all strings into an array
                                lm_cat.add(wb1);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    lm_cat.add(wp);
                                }
                                if (lm_cat.size() > 0) {
                                    Worship(lm_cat);
                                }
                            }


                        } else {
                            Samplemyclass wb1 = new Samplemyclass("0", "Select Landmark Category");
                            // Binds all strings into an array
                            lm_cat.add(wb1);
                            if (lm_cat.size() > 0) {
                                Worship(lm_cat);

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

    private void getSubworship_Api(String SWtypeid) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "2");
            jsonBody.put("Id", "" + SWtypeid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        lm_subcat.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb1 = new Samplemyclass("0", "Select Landmark Sub Category");
                                // Binds all strings into an array
                                lm_subcat.add(wb1);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    lm_subcat.add(wp);
                                }
                                if (lm_subcat.size() > 0) {
                                    Subworship(lm_subcat);
                                }
                            }


                        } else {
                            Samplemyclass wb1 = new Samplemyclass("0", "Select Landmark Sub Category");
                            // Binds all strings into an array
                            lm_subcat.add(wb1);
                            if (lm_subcat.size() > 0) {
                                Subworship(lm_subcat);

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

    private void Sensitivity(ArrayList<Samplemyclass> Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        Sp_sensitv.setAdapter(adapter);
        Sp_sensitv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Rl_Ssubtype.setVisibility(View.VISIBLE);
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    S_sp = country.getName();
                    S_sp_id = country.getId();
                    Log.e("Sp_sensitv", "" + S_sp);
                    if (S_sp_id != null) {
                        getSubSensitivity_Api(S_sp_id);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void SubSensitivity(ArrayList<Samplemyclass> Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        Sp_sensitivetype.setAdapter(adapter);
        Sp_sensitivetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    S_sp_subtype = country.getName();
                    S_sp_subtype_id = country.getId();
                    Log.e("Sp_sensitv", "" + S_sp);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void Worship(ArrayList<Samplemyclass> Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        Sp_tpwk.setAdapter(adapter);
        Sp_tpwk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();

                //  Toast.makeText(getApplicationContext(), "" + pos, Toast.LENGTH_SHORT).show();

                if (pos != 0) {
                    R_subtype.setVisibility(View.VISIBLE);

                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                    Cat1 = country.getId();
                    item2 = country.getName();
                    Log.e("Cat1", "" + Cat1);
                    Log.e("Cat", "" + item2);

                    //  item2 = parent.getSelectedItem().toString();

                    Log.e("Cat1", "" + Cat1);
                    if (Cat1 != null) {
                        getSubworship_Api(Cat1);
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void Subworship(ArrayList<Samplemyclass> Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        Sp_stype.setAdapter(adapter);
        Sp_stype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();

                if (pos != 0) {
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    SubCat1 = country.getId();
                    item = country.getName();
                    Log.e("SubCat", "" + item);
                    Log.e("SubCat1", "" + SubCat1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setData() {


//        countryList = new ArrayList<>();
//        countryList.add(new Samplemyclass("0", "Select Type of Worship"));
//        countryList.add(new Samplemyclass("1", "Christian"));
//        countryList.add(new Samplemyclass("2", "Hindu"));
//        countryList.add(new Samplemyclass("3", "Muslim"));
//        countryList.add(new Samplemyclass("4", "Others"));

//        countryList1 = new ArrayList<>();
//        countryList1.add(new Samplemyclass("0", "Select SubType of Worship"));
//        countryList1.add(new Samplemyclass("1", "Church"));
//
//        countryList2 = new ArrayList<>();
//        countryList2.add(new Samplemyclass("0", "Select SubType of Worship"));
//        countryList2.add(new Samplemyclass("2", "Temple"));
//        countryList2.add(new Samplemyclass("2", "Matam"));
//        countryList2.add(new Samplemyclass("2", "Uharadhi"));
//        countryList2.add(new Samplemyclass("2", "Goshala"));
//
//        countryList3 = new ArrayList<>();
//        countryList3.add(new Samplemyclass("0","Select SubType of Worship"));
//        countryList3.add(new Samplemyclass("3", "Eidgah"));
//        countryList3.add(new Samplemyclass("3", "Madarsa"));
//        countryList3.add(new Samplemyclass("3", "Masjid"));
//        countryList3.add(new Samplemyclass("3", "Mosque"));
//
//        countryList4 = new ArrayList<>();
//        countryList4.add(new Samplemyclass("0","Select SubType of Worship"));
//        countryList4.add(new Samplemyclass("4", "Jain Mandir"));
//        countryList4.add(new Samplemyclass("4", "Gurudwara"));

    }

    public void NotNull() {

        if (iv1 == null) {
            iv1 = "";
        } else {
        }
        if (iv2 == null) {
            iv2 = "";
        } else {
        }
        if (iv3 == null) {
            iv3 = "";
        } else {
        }
        if (iv4 == null) {
            iv4 = "";
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


        if (S_Locality == null) {
            S_Locality = "";
        } else {
        }

        if (S_sp == null) {
            S_sp = "";
        } else {
        }

        if (S_dis == null) {
            S_dis = "";
        } else {
        }


        if (S_ph == null) {
            S_ph = "";
        } else {
        }

        if (S_in == null) {
            S_in = "";
        } else {
        }
        if (S_wp == null) {
            S_wp = "";
        } else {
        }


        if (SubCat1 == null) {
            SubCat1 = "";
        } else {
        }

        if (S_Locality == null) {
            S_Locality = "";
        } else {
        }

        if (S_wp == null) {
            S_wp = "";
        } else {
        }


        if (S_in == null) {
            S_in = "";
        } else {
        }

        if (S_inDesig == null) {
            S_inDesig = "";
        } else {
        }
        if (S_ph == null) {
            S_ph = "";
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

        if (S_dis == null) {
            S_dis = "";
        } else {
        }
        if (S_sp_id == null) {
            S_sp_id = "";
        } else {
        }
        if (S_sp == null) {
            S_sp = "";
        } else {
        }
        if (S_sp_subtype_id == null) {
            S_sp_subtype_id = "";
        } else {
        }

        if (S_sp_subtype == null) {
            S_sp_subtype = "";
        } else {
        }
        if (S_PrevIncidentHistory == null) {
            S_PrevIncidentHistory = "";
        } else {
        }


    }

    private void AddReligious() {
        progressDialog = new ProgressDialog(AddReligious.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("LandMarkMaster_Id", SubCat1);
            jsonBody.put("AddressWorshipPlace", S_Locality);
            jsonBody.put("NameoWorshipPlace", S_wp);

            jsonBody.put("NameoftheIncharge", S_in);
            jsonBody.put("InchargeDesignation", S_inDesig);
            jsonBody.put("ContactNo", S_ph);

            jsonBody.put("Lattitude", S_latitude);
            jsonBody.put("Longitude", S_longitude);
            jsonBody.put("Remarks", S_dis);

            jsonBody.put("sensitivityLevelId", S_sp_id);
            jsonBody.put("SensitivitySubLevelId", S_sp_subtype_id);
            jsonBody.put("PrevIncidentHistory", S_PrevIncidentHistory);
            jsonBody.put("CreatedBy", S_Uname);
            jsonBody.put("PSId", S_psid);


            jsonBody.put("FrontImageType", "Front");
            jsonBody.put("BackImageType", "Back");
            jsonBody.put("SideImageType", "Side");
            jsonBody.put("TopImageType", "Top");

//images
            jsonBody.put("FrontImage", iv1);
            jsonBody.put("BackImage", iv2);
            jsonBody.put("SideImage", iv3);
            jsonBody.put("TopImage", iv4);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:LandmarkEntry" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkEntry, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:LandmarkEntry" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        if (code.equalsIgnoreCase("1")) {
                            progressDialog.dismiss();
                            Log.e("suceess", "" + Message);
                            C_dialog.dismiss();
                            et_wp.setText("");
                            et_ph.setText("");
                            et_in.setText("");
                            et_dis.setText("");
                            //  Et_yellow.setText("");
                            Sp_tpwk.setSelection(0);
                            Sp_stype.setSelection(0);
                            Sp_sensitv.setSelection(0);
                            item2 = "";
                            S_Locality = "";
                            item = "";
                            S_in = "";
                            S_ph = "";
                            S_latitude = "";
                            S_longitude = "";
                            S_dis = "";
                            S_sp = "";

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReligious.this);
                            alertDialog.setTitle("Success");
                            alertDialog.setMessage("Values Saved Succesfully");
                            alertDialog.setIcon(R.drawable.succs);
                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to invoke YES event
                                    dialog.cancel();
                                    try {


                                        finish();
                                        Intent i = new Intent(getApplicationContext(), AddReligious.class);
                                        startActivity(i);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            alertDialog.show();
                        } else {

                            progressDialog.dismiss();
                            Log.e("not sucess", "" + Message);
                            C_dialog.dismiss();
//                            et_wp.setText("");
//                            et_ph.setText("");
//                            et_in.setText("");
//                            et_dis.setText("");
//                            //  Et_yellow.setText("");
//                            Sp_tpwk.setSelection(0);
//                            Sp_stype.setSelection(0);
//                            Sp_sensitv.setSelection(0);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReligious.this);
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
                    4430000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void GetReligious() {
        try {
            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");
            S_psid = bb.getString("Psid", "");


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("HirarchyID", S_psid);
            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:LandmarkList" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkList, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:LandmarkList" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("LandMarkList");
                            int number = jArray.length();

                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {
                                twp_arraylist_.clear();
                                nwp_arraylist_.clear();
                                add_arraylist_.clear();
                                ni_arraylist_.clear();
                                cNo_arraylist_.clear();
                                Remarks_arraylist_.clear();
                                EnteredDate_arraylist_.clear();
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    //  String r_id=json_data.getString("Id").toString();
                                    twp = json_data.getString("LandMarkMaster_SubType").toString();
                                    nwp = json_data.getString("NameoWorshipPlace").toString();
                                    add = json_data.getString("AddressWorshipPlace").toString();
                                    ni = json_data.getString("NameoftheIncharge").toString();
                                    cNo = json_data.getString("ContactNo").toString();
                                    Remarks = json_data.getString("Remarks").toString();
                                    //      EnteredDate = json_data.getString("SensitivityLevel").toString();

//
//                                    "LandMarkMaster_Id": null,
//
//                                            "LandMarkMaster_SubType": "Temple",
//
//
//                                            "InchargeDesignation": "test",
//
//                                            "PSId": "1843",
//                                            "PoliceStation": "Toopran",


                                    if (twp == null || twp.trim().equals("anyType{}") || twp.trim()
                                            .length() <= 0) {
                                        twp_arraylist_.add("");
                                    } else {
                                        twp_arraylist_.add(twp);
                                    }
                                    if (nwp == null || nwp.trim().equals("anyType{}") || nwp.trim()
                                            .length() <= 0) {
                                        nwp_arraylist_.add("");
                                    } else {
                                        nwp_arraylist_.add(nwp);
                                    }
                                    if (add == null || add.trim().equals("anyType{}") || add.trim()
                                            .length() <= 0) {
                                        add_arraylist_.add("");
                                    } else {
                                        add_arraylist_.add(add);
                                    }
                                    if (ni == null || ni.trim().equals("anyType{}") || ni.trim()
                                            .length() <= 0) {
                                        ni_arraylist_.add("");
                                    } else {
                                        ni_arraylist_.add(ni);
                                    }
                                    if (cNo == null || cNo.trim().equals("anyType{}") || cNo.trim()
                                            .length() <= 0) {
                                        cNo_arraylist_.add("");
                                    } else {
                                        cNo_arraylist_.add(cNo);
                                    }

                                    if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
                                            .length() <= 0) {
                                        Remarks_arraylist_.add("");
                                    } else {
                                        Remarks_arraylist_.add(Remarks);
                                    }
                                    if (EnteredDate == null || EnteredDate.trim().equals("anyType{}") || EnteredDate.trim()
                                            .length() <= 0) {
                                        EnteredDate_arraylist_.add("");
                                    } else {
                                        EnteredDate_arraylist_.add(EnteredDate);
                                    }
                                }

                            }

                            int count = twp_arraylist_.size();
                            Log.e(" count", "" + count);
                            SetValuesToLayout(count);
                        } else {

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReligious.this);
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case REQUEST_CHECK_SETTINGS:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        Log.e("msg", "onActivityResult");
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(getApplicationContext(), "Permission Required", Toast.LENGTH_SHORT).show();
//                        // splash();
//                        finish();
//                        break;
//                    default:
//                        break;
//                }
//                break;
//
//        }
//    }

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
                                    AddReligious.this, 1000);
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

                et_Locality.setText(" " + "Couldn't get the location.");
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == ChoosePhoto.CHOOSE_PHOTO_INTENT) {
//                if (data != null && data.getData() != null) {
//                    choosePhoto.handleGalleryResult(data);
//                } else {
//                    choosePhoto.handleCameraResult(choosePhoto.getCameraUri());
//                }
//            }else if (requestCode == ChoosePhoto.SELECTED_IMG_CROP) {
//                iv_1.setImageURI(choosePhoto.getCropImageUrl());
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == ChoosePhoto.SELECT_PICTURE_CAMERA) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            choosePhoto.showAlertDialog();
    }
}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("OnActivityResult");

        switch (requestCode) {
            case 1:
                if (requestCode == 1 && resultCode == RESULT_OK) {
                    if (requestCode == 1) {
                        Bundle extras = data.getExtras();
                        Bitmap bmp = (Bitmap) extras.get("data");
                        Uri picUri = data.getData();
                        //performCrop(picUri);
                        iv_1.setImageBitmap(bmp);
                        iv1 = timestampItAndSave(bmp);


                    }
                }

                break;


            case 2:
                if (requestCode == 2 && resultCode == RESULT_OK) {
                    if (requestCode == 2) {
                        Bundle extras = data.getExtras();
                        Bitmap bmp = (Bitmap) extras.get("data");
                        Uri picUri = data.getData();
                     //   performCrop(picUri);
                        iv_2.setImageBitmap(bmp);
                        iv2 = timestampItAndSave(bmp);


                    }
                }

                break;
            case 3:
                if (requestCode == 3 && resultCode == RESULT_OK) {
                    if (requestCode == 3) {
                        Bundle extras = data.getExtras();
                        Bitmap bmp = (Bitmap) extras.get("data");
                        Uri picUri = data.getData();
                      //  performCrop(picUri);
                        iv_3.setImageBitmap(bmp);
                        iv3 = timestampItAndSave(bmp);


                    }
                }
                break;

            case 4:
                if (requestCode == 4 && resultCode == RESULT_OK) {
                    if (requestCode == 4) {
                        Bundle extras = data.getExtras();
                        Bitmap bmp = (Bitmap) extras.get("data");
                        Uri picUri = data.getData();
                       // performCrop(picUri);
                        iv_4.setImageBitmap(bmp);
                        iv4 = timestampItAndSave(bmp);


                    }
                }
                break;

        }
    }

    private String timestampItAndSave(Bitmap toEdit) {
//        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                Calendar c = Calendar.getInstance();
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                formattedDate = df.format(c.getTime());
//            }
//            public void onFinish() {
//            }
//        };
//        newtimer.start();
        Bitmap bmOverlay = Bitmap.createBitmap(toEdit.getWidth(), toEdit.getHeight(), Bitmap.Config.ARGB_4444);
//        Canvas canvas = new Canvas(bmOverlay);
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(9);
//        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        canvas.drawBitmap(toEdit, 0, 0, null);
//        canvas.drawPoint(3f, 12f, paint);
//        if(formattedDate!=null){
//            canvas.drawText(formattedDate, 3f + 3, 12f + 3, paint);
//        }else {
//            canvas.drawText("", 3f + 3, 12f + 3, paint);
//        }
//        imageView.setImageBitmap(toEdit);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        toEdit.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
