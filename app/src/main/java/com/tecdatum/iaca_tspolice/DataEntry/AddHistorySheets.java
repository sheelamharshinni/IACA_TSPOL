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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
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

public class AddHistorySheets extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private String LandmarkMasters = URLS.LandmarkMasters;

    private String HistoryList = URLS.HistoryList;
    private String HistoryEntry = URLS.HistoryEntry;



    private String urlJsonObj = URLS.AddHistoryData;
    private String url_getData =URLS.GetHistorySheetRecordsData;
    ProgressDialog progressDialog;
    boolean timeoutexcep=false,httpexcep=false,genexcep=false;
    private static final String TAG = AddHistorySheets.class.getSimpleName();
    private LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x01;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 50;
    private static final int READ_CAMERA_PERMISSIONS_REQUEST = 20;
    private static final int WRITE_SETTINGS_PERMISSION = 20;
    private boolean active = false;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    int Code;
    protected Location mLastLocation;
    private static int UPDATE_INTERVAL = 60000;
    private boolean mRequestingLocationUpdates = true;
    Integer Code1;
    String Message1;
    Dialog C_dialog,vdialog;
    Button Bt_c_dob,Bt_cr_time,Bt_c_vl,Bt_c_rest,Bt_c_submit;
    TextView c_time,hs_psname;
    String formattedDate;
    EditText Et_age,Et_no,Et_ps,Et_an,Et_fn,et_Locality;
    String S_Et_age,S_Et_no,S_Et_ps,S_Et_an,S_Et_fn,S_et_Locality,S_dateTime,S_latitude,S_longitude,S_dob
            ,sp_type_id,sp_type_name,Message,S_Pass,S_IMEi,S_Uname,S_psid,s_Ps_name,s_role;
    CustomDateTimePicker custom, custom1;
    ArrayList<Samplemyclass> countryList= new ArrayList<Samplemyclass>();
    Spinner sp_type;

    ArrayList<String> No_arraylist_ = new ArrayList<String>();
    ArrayList<String> Type_arraylist_ = new ArrayList<String>();
    ArrayList<String> PersonName_arraylist_ = new ArrayList<String>();
    ArrayList<String> AliasName_arraylist_ = new ArrayList<String>();
    ArrayList<String> FatherName_arraylist_ = new ArrayList<String>();
    ArrayList<String> Age_arraylist_ = new ArrayList<String>();
    ArrayList<String> Add_arraylist_ = new ArrayList<String>();
    String No,AliasName,Type,PersonName,FatherName,Age,Add;

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
        setContentView(R.layout.activity_add_history_sheets);
        Bt_c_dob=(Button)findViewById(R.id.btn_hs_dob);
        c_time=(TextView)findViewById(R.id.tv_hs_time);
        hs_psname=(TextView)findViewById(R.id.tv_hs_psname);

        Bt_c_vl=(Button)findViewById(R.id.btn_hs__vl);
        Bt_c_rest=(Button)findViewById(R.id.btn_hs__reset);
        Bt_c_submit=(Button)findViewById(R.id.btn_hs__submit);
        Et_age=(EditText)findViewById(R.id.et_hs_age);
        Et_no=(EditText)findViewById(R.id.et_hs_no);
        Et_ps=(EditText)findViewById(R.id.et_hs_pn);
        Et_an=(EditText)findViewById(R.id.et_hs_an);
        Et_fn=(EditText)findViewById(R.id.et_hs_fn);
        et_Locality=(EditText)findViewById(R.id.et_hs_Locality);

       sp_type=(Spinner)findViewById(R.id.sp_hs_type) ;


        et_Locality.setFilters(new InputFilter[]{filter});
        hs_psname.setFilters(new InputFilter[]{filter});


        Bt_c_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custom1 = new CustomDateTimePicker(AddHistorySheets.this,
                        new CustomDateTimePicker.ICustomDateTimeListener() {
                            @Override
                            public void onSet(Dialog dialog, Calendar calendarSelected,
                                              Date dateSelected, int year, String monthFullName,
                                              String monthShortName, int monthNo, int date,
                                              String weekDayFullName, String weekDayShortName,
                                              int hour24, int hour14, int min, int sec,
                                              String AM_PM) {
                                Bt_c_dob.setText("");
                                Bt_c_dob.setText(year
                                        + "-" + (monthNo + 1) + "-" + calendarSelected.get(Calendar.DAY_OF_MONTH)
                                        + " " + hour24 + ":" + min
                                        + ":" + sec);


                                getAge( year, monthNo + 1,  calendarSelected.get(Calendar.DAY_OF_MONTH));
                                S_dateTime=Bt_c_dob.getText().toString();
                                Log.e("S_dateTime",""+S_dateTime);
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

        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CheckConnectivity1();
            }
        });
        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Et_age.setText("");
                Et_no.setText("");
                Et_ps.setText("");
                Et_an.setText("");
                Et_fn.setText("");
                sp_type.setSelection(0);
                Bt_c_dob.setText("DOB");


                finish();
                Intent i = new Intent(getApplicationContext(), AddHistorySheets.class);
                startActivity(i);

            }
        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                 S_Et_age=Et_age.getText().toString();
                S_Et_no=Et_no.getText().toString();
                S_Et_ps=Et_ps.getText().toString();
                S_Et_an=Et_an.getText().toString();
                S_Et_fn= Et_fn.getText().toString();
                S_et_Locality= et_Locality.getText().toString();
//                Log.e("1",""+S_Uname);
//                Log.e("2",""+S_Pass);
//                Log.e("3",""+S_IMEi);
//                Log.e("4",""+S_CrimeNo);
//                Log.e("5",""+Cat1);
//                Log.e("5.1",""+item2);
//                Log.e("6",""+SubCat1);
//                Log.e("6.1",""+item);
//                Log.e("7",""+S_dateTime);
//                Log.e("8",""+S_location);
//                Log.e("9",""+S_detected_);
//                Log.e("10",""+S_latitude);
//                Log.e("11",""+S_longitude);
//                Log.e("14",""+S_Descr);
//                if(S_detected_==null);{
//                    S_detected_="Complaint";
//                    Log.e("13",""+S_detected_);
//                }

                if(sp_type != null && sp_type.getSelectedItem() !=null ) {
                    if (!(Et_no.getText().toString().trim() .isEmpty())) {
                    if (!(sp_type.getSelectedItem().toString().trim() == "Type")) {

                        if (!(Et_ps.getText().toString().trim() .isEmpty())) {
                            if (!(Et_age.getText().toString().trim() .isEmpty())) {
                                if (!(et_Locality.getText().toString().trim() .isEmpty())) {
                                    Log.e("S_Et_no1",""+S_Et_no);
                                       // Toast.makeText(AddHistorySheets_View.this, "All Feilds are entered", Toast.LENGTH_SHORT).show();
                                        alertDialogue_Conform();

                                }else{
                                    Toast.makeText(AddHistorySheets.this, "Location is Mandatory", Toast.LENGTH_SHORT).show();
                                }
                        }else{
                                Toast.makeText(AddHistorySheets.this, "Enter Age", Toast.LENGTH_SHORT).show();
                        }
                        }else{

                            Toast.makeText(AddHistorySheets.this, "Person Name is Mandatory", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(AddHistorySheets.this, "Select Type", Toast.LENGTH_SHORT).show();
                    }

                        }else{
                            Toast.makeText(AddHistorySheets.this, "No is Mandatory", Toast.LENGTH_SHORT).show();
                        }

                }
                else {
                    alertDialogue_Conform();
                }
                // new pay().execute();

            }
        });


        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname=bb.getString("UserName", "");
        S_Pass =bb.getString("password","");
        S_IMEi=bb.getString("imei","");
        S_psid=bb.getString("Psid", "");
        s_Ps_name =bb.getString("Psname","");
        s_role =bb.getString("Role","");
        hs_psname.setText(s_Ps_name);
        String s_OrgName =bb.getString("OrgName","");
        TextView tv_OrgName=(TextView)findViewById(R.id.tv_distname) ;
        tv_OrgName.setText(""+s_OrgName);
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

    private void getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        Log.e("age1",""+year);
        Log.e("age2",""+month);
        Log.e("age3",""+day);
        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }



        Integer ageInt = new Integer(age);
        String str1;
        if(ageInt>1){
             str1="yr";
        }else{
            str1="yrs";
        }
        S_dob = ageInt.toString();
        Bt_c_dob.setText("DOB"+"\t"+"\t"+S_dob+"\t"+str1);
        Log.e("age",""+S_dob);
    }
    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
        {

          //new pay().execute();

            AddHIstoryData();

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
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
        {

             getHistorySheetType_Api();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
        //    C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    class getpay extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(AddHistorySheets.this);
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/GetHistorySheetRecordsdata";
            String METHOD_NAME = "GetHistorySheetRecordsdata";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetHistorySheetRecordsdata";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            envelope.setOutputSoapObject(request);

            Log.e("1245", S_Pass+ "    " + S_IMEi);
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
                    System.out.println("******** : " + root.getPropertyCount());

                    No_arraylist_.clear();
                    Type_arraylist_.clear();
                    PersonName_arraylist_.clear();
                    AliasName_arraylist_.clear();
                    FatherName_arraylist_.clear();
                    Age_arraylist_.clear();
                    Add_arraylist_.clear();

                    for (int j = 0; j < root.getPropertyCount(); j++) {
                        SoapObject s_deals = (SoapObject) root.getProperty(j);
                        System.out.println("********Count : " + s_deals.getPropertyCount());

                        for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                            Object property = s_deals.getProperty(i);
                            System.out.println("******** : " + property);
                            No = s_deals.getProperty("Number").toString();
                            PersonName = s_deals.getProperty("PersonName").toString();
                            Type = s_deals.getProperty("Type").toString();
                            AliasName = s_deals.getProperty("AliasName").toString();
                            FatherName = s_deals.getProperty("FatherName").toString();
                            Age = s_deals.getProperty("Age").toString();
                            Add = s_deals.getProperty("Address").toString();
                        }
                        Type_arraylist_.add(Type);
                        Add_arraylist_.add(Add);
                        if (No == null || No.trim().equals("anyType{}") || No.trim()
                                .length() <= 0) {
                            No_arraylist_.add("");
                        } else {
                            No_arraylist_.add(No);
                        }

                        if (PersonName == null || PersonName.trim().equals("anyType{}") || PersonName.trim()
                                .length() <= 0) {
                            PersonName_arraylist_.add("");
                        } else {
                            PersonName_arraylist_.add(PersonName);
                        }
                        if (AliasName == null || AliasName.trim().equals("anyType{}") || AliasName.trim()
                                .length() <= 0) {
                            AliasName_arraylist_.add("");
                        } else {
                            AliasName_arraylist_.add(AliasName);
                        }
                        if (FatherName == null ||FatherName.trim().equals("anyType{}") || FatherName.trim()
                                .length() <= 0) {
                            FatherName_arraylist_.add("");
                        } else {
                            FatherName_arraylist_.add(FatherName);
                        }

                        if (Age == null ||Age.trim().equals("anyType{}") || Age.trim()
                                .length() <= 0) {
                            Age_arraylist_.add("");
                        } else {
                            Age_arraylist_.add(Age);
                        }
                    }


            }
            catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            Log.e("result",""+result);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if(timeoutexcep){
                Toast.makeText(AddHistorySheets.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddHistorySheets.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddHistorySheets.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{
                Log.e(TAG, " : " + PersonName_arraylist_);
                Log.e(TAG, " : " + No_arraylist_);
                Log.e(TAG, " : " + Type_arraylist_);
                Log.e(TAG, " : " + AliasName_arraylist_);
                Log.e(TAG, " : " + FatherName_arraylist_);
                Log.e(TAG, ": " + Add_arraylist_);
                SetValuesToLayout();
                if (Code==0) {
                    Log.e("suceess",""+Message);
                }else{
                    Log.e("not sucess",""+Message);
                }
                Log.e("result","done");
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }
    private void SetValuesToLayout() {
        vdialog = new Dialog(AddHistorySheets.this, R.style.HiddenTitleTheme);
        vdialog.setContentView(R.layout.vl_historysheets);
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


        Log.e("25644122", "" + No_arraylist_.size());

        if((No_arraylist_.size())>0|(No_arraylist_.size())!=0) {

                if ((No_arraylist_.size())==5) {
                    r0.setVisibility(View.VISIBLE);
                    r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    r3.setVisibility(View.VISIBLE);
                    r4.setVisibility(View.VISIBLE);
                    r5.setVisibility(View.VISIBLE);
                    NoRecords.setVisibility(View.GONE);



                    t1.setText(No_arraylist_.get(0));
                    t2.setText(No_arraylist_.get(1));
                    t3.setText(No_arraylist_.get(2));
                    t4.setText(No_arraylist_.get(3));
                    t5.setText(No_arraylist_.get(4));

                    st1.setText(Type_arraylist_.get(0));
                    st2.setText(Type_arraylist_.get(1));
                    st3.setText(Type_arraylist_.get(2));
                    st4.setText(Type_arraylist_.get(3));
                    st5.setText(Type_arraylist_.get(4));


                    dt1.setText(PersonName_arraylist_.get(0));
                    dt2.setText(PersonName_arraylist_.get(1));
                    dt3.setText(PersonName_arraylist_.get(2));
                    dt4.setText(PersonName_arraylist_.get(3));
                    dt5.setText(PersonName_arraylist_.get(4));

                }
                if ((No_arraylist_.size())==1) {
                    r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.GONE);
                    r3.setVisibility(View.GONE);
                    r0.setVisibility(View.VISIBLE);

                    r4.setVisibility(View.GONE);
                    r5.setVisibility(View.GONE);
                    NoRecords.setVisibility(View.GONE);
                    t1.setText(No_arraylist_.get(0));
                    st1.setText(Type_arraylist_.get(0));
                    dt1.setText(PersonName_arraylist_.get(0));

                }
                if ((No_arraylist_.size())==2) {
                    r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    r3.setVisibility(View.GONE);
                    r4.setVisibility(View.GONE);
                    r5.setVisibility(View.GONE);
                    r0.setVisibility(View.VISIBLE);

                    NoRecords.setVisibility(View.GONE);
                    t1.setText(No_arraylist_.get(0));
                    t2.setText(No_arraylist_.get(1));
                    st1.setText(Type_arraylist_.get(0));
                    st2.setText(Type_arraylist_.get(1));
                    dt1.setText(PersonName_arraylist_.get(0));
                    dt2.setText(PersonName_arraylist_.get(1));

                }
                if ((No_arraylist_.size()) ==3) {
                    r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    r0.setVisibility(View.VISIBLE);

                    r3.setVisibility(View.VISIBLE);
                    r4.setVisibility(View.GONE);
                    r5.setVisibility(View.GONE);
                    NoRecords.setVisibility(View.GONE);
                    t1.setText(No_arraylist_.get(0));
                    t2.setText(No_arraylist_.get(1));
                    t3.setText(No_arraylist_.get(2));
                    st1.setText(Type_arraylist_.get(0));
                    st2.setText(Type_arraylist_.get(1));
                    st3.setText(Type_arraylist_.get(2));
                    dt1.setText(PersonName_arraylist_.get(0));
                    dt2.setText(PersonName_arraylist_.get(1));
                    dt3.setText(PersonName_arraylist_.get(2));


                }
                if ((No_arraylist_.size()) ==4) {
                    r0.setVisibility(View.VISIBLE);

                    r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    r3.setVisibility(View.VISIBLE);
                    r4.setVisibility(View.VISIBLE);
                    r5.setVisibility(View.GONE);
                    NoRecords.setVisibility(View.GONE);
                    t1.setText(No_arraylist_.get(0));
                    t2.setText(No_arraylist_.get(1));
                    t3.setText(No_arraylist_.get(2));
                    t4.setText(No_arraylist_.get(3));


                    st1.setText(Type_arraylist_.get(0));
                    st2.setText(Type_arraylist_.get(1));
                    st3.setText(Type_arraylist_.get(2));
                    st4.setText(Type_arraylist_.get(3));



                    dt1.setText(PersonName_arraylist_.get(0));
                    dt2.setText(PersonName_arraylist_.get(1));
                    dt3.setText(PersonName_arraylist_.get(2));
                    dt4.setText(PersonName_arraylist_.get(3));


                }

                if ((No_arraylist_.size()) > 5) {
                    r1.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    r0.setVisibility(View.VISIBLE);

                    r3.setVisibility(View.VISIBLE);
                    r4.setVisibility(View.VISIBLE);
                    r5.setVisibility(View.VISIBLE);
                    NoRecords.setVisibility(View.GONE);
                    Log.e("yes", ""+No_arraylist_.size());
                    t1.setText(No_arraylist_.get(0));
                    t2.setText(No_arraylist_.get(1));
                    t3.setText(No_arraylist_.get(2));
                    t4.setText(No_arraylist_.get(3));
                    t5.setText(No_arraylist_.get(4));

                    st1.setText(Type_arraylist_.get(0));
                    st2.setText(Type_arraylist_.get(1));
                    st3.setText(Type_arraylist_.get(2));
                    st4.setText(Type_arraylist_.get(3));
                    st5.setText(Type_arraylist_.get(4));


                    dt1.setText(PersonName_arraylist_.get(0));
                    dt2.setText(PersonName_arraylist_.get(1));
                    dt3.setText(PersonName_arraylist_.get(2));
                    dt4.setText(PersonName_arraylist_.get(3));
                    dt5.setText(PersonName_arraylist_.get(4));
                }

            }else {
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
        C_dialog = new Dialog(AddHistorySheets.this,R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_confom_historysheets);
        C_dialog.show();
        TextView d_no = (TextView) C_dialog.findViewById(R.id.tv_hs_no);
        TextView d_type = (TextView) C_dialog.findViewById(R.id.tv_hs_type);
        TextView d_pn = (TextView) C_dialog.findViewById(R.id.tv_hs_pn);
        TextView d_an = (TextView) C_dialog.findViewById(R.id.tv_hs_an);
        TextView d_fn=(TextView) C_dialog.findViewById(R.id.tv_hs_fn);
        TextView d_dob = (TextView) C_dialog.findViewById(R.id.tv_hs_dob);
        TextView d_add=(TextView) C_dialog.findViewById(R.id.tv_hs_add);
        Button C_confm=(Button)C_dialog.findViewById(R.id.btn_hs_Confm) ;

        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_hs_pscode);
        pscode.setText(s_Ps_name);

        if(No_arraylist_.size()>count){
            d_no.setText(No_arraylist_.get(count));
        }

        if(Type_arraylist_.size()>count){
            d_type.setText(Type_arraylist_.get(count));
        }

        if(PersonName_arraylist_.size()>count){
            d_pn.setText(PersonName_arraylist_.get(count));
        }

        if(AliasName_arraylist_.size()>count){
            d_an.setText(AliasName_arraylist_.get(count));
        }

        if(FatherName_arraylist_.size()>count){
            d_fn.setText(FatherName_arraylist_.get(count));
        }

        if(Age_arraylist_.size()>count){
            d_dob.setText(Age_arraylist_.get(count));
        }
        if(Add_arraylist_.size()>count){
            d_add.setText(Add_arraylist_.get(count));;
        }

        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });


    }
    private void alertDialogue_Conform() {

        NotNull();
        C_dialog = new Dialog(AddHistorySheets.this,R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.confom_historysheets);
        C_dialog.show();
        TextView d_no = (TextView) C_dialog.findViewById(R.id.tv_hs_no);
        TextView d_type = (TextView) C_dialog.findViewById(R.id.tv_hs_type);
        TextView d_pn = (TextView) C_dialog.findViewById(R.id.tv_hs_pn);
        TextView d_an = (TextView) C_dialog.findViewById(R.id.tv_hs_an);
        TextView d_fn=(TextView) C_dialog.findViewById(R.id.tv_hs_fn);
        TextView d_dob = (TextView) C_dialog.findViewById(R.id.tv_hs_dob);
        TextView d_add=(TextView) C_dialog.findViewById(R.id.tv_hs_add);
        Button C_confm=(Button)C_dialog.findViewById(R.id.btn_hs_Confm) ;
        Button C_cancel=(Button)C_dialog.findViewById(R.id.btn_hs_Cancel) ;

        String S_dob= Bt_c_dob.getText().toString();

        d_no.setText(S_Et_no);
        d_type.setText(sp_type_name);
        d_pn.setText(S_Et_ps);
        d_an.setText(S_Et_an);
        d_fn.setText(S_Et_fn);
        d_dob.setText(S_Et_age);
        d_add.setText(S_et_Locality);

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
    class pay extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(AddHistorySheets.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/AddHistoryData";
            String METHOD_NAME = "AddHistoryData";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=AddHistoryData";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username", S_Uname);
            request.addProperty("password", S_Pass);
            request.addProperty("imei", S_IMEi);
            request.addProperty("connectionType", "TabletPc");
            request.addProperty("Number", S_Et_no);
            request.addProperty("Type", sp_type_name);
            request.addProperty("PersonName", S_Et_ps);
            request.addProperty("AliasName", S_Et_an);
            request.addProperty("FatherName", S_Et_fn);
            request.addProperty("Age", S_Et_age);
            request.addProperty("Latitude", S_latitude);
            request.addProperty("Longitude", S_longitude);
            request.addProperty("address", S_et_Locality);
            envelope.setOutputSoapObject(request);


            Log.e("request",""+request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            Log.e("AddHistoryShs_ENVOLOPE",""+envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();

                Log.e("result_AddHistorySheets",""+result1);
                SoapObject result=(SoapObject)envelope.bodyIn;
                String[] testValues=new String[result.getPropertyCount()];
                for(int i=0;i<result.getPropertyCount();i++)
                {
                    Object property = result.getProperty(i);
                    testValues[i]=result.getProperty(i).toString();
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

            Log.e("result",""+result);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();

            }
            if(timeoutexcep){
                Toast.makeText(AddHistorySheets.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddHistorySheets.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddHistorySheets.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{
                if (Code1!= null) {
                    if (Code1 == 0) {
                        Log.e("suceess", "" + Message1);
                        C_dialog.dismiss();
                        Et_no.setText("");
                        Et_age.setText("");
                        Et_ps.setText("");
                        Et_an.setText("");
                        Et_fn.setText("");
                        sp_type.setSelection(0);
                        S_Et_no = "";
                        S_Et_age = "";
                        sp_type_name = "";
                        S_Et_ps = "";
                        S_Et_an = "";
                        S_Et_fn = "";
                        S_dob = "";
                        S_latitude = "";
                        S_longitude = "";
                        S_et_Locality = "";
                        Bt_c_dob.setText("DOB");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHistorySheets.this);
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
                        Et_no.setText("");
                        Et_ps.setText("");
                        Et_an.setText("");
                        Et_age.setText("");
                        Et_fn.setText("");
                        sp_type.setSelection(0);
                        Bt_c_dob.setText("DOB");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHistorySheets.this);
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("" + Message1);
                        alertDialog.setIcon(R.drawable.alertt);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        // Toast.makeText(AddHistorySheets_View.this,""+Message,Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                }
                Log.e("result","done");
                //tableview();
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }
    private void HistorySheetData(ArrayList<Samplemyclass>str1) {
        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        sp_type.setAdapter(adapter);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if(parent!=null) {
                }
                if(pos!= 0){
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_type_id =country.getId();
                    sp_type_name=country.getName();
                    Log.e(TAG,"sp_type_id"+sp_type_id);
                    Log.e(TAG,"sp_type_name"+sp_type_name);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    private void setData() {
        CheckConnectivity2();
    }
    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
        {

            //new getpay().execute();
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
                                    AddHistorySheets.this, 1000);
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
        //**************************

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
            Log.e("msg",""+latitude+longitude);
            S_latitude= String.valueOf(latitude);
            S_longitude= String.valueOf(longitude);
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 7);
                if (addresses != null) {
                    if (addresses.size()>0) {
                    String address = addresses.get(0).getAddressLine(0);
                    String address1 = addresses.get(0).getSubLocality();
                    String address2 = addresses.get(0).getLocality();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(address);
//                    stringBuilder.append(",");
//                    stringBuilder.append(address1);
//                    stringBuilder.append(",");
//                    stringBuilder.append(address2);
                    String S_loc=stringBuilder.toString();
                    String t66 = "<font color='#ebedf5'>Location : </font>";
                    Log.e("msg",""+S_loc);
                    et_Locality.setText(" "+S_loc);
                    // CurrentLocation.setText(Html.fromHtml(t66+S_loc));
                    // CurrentLocation.setText(S_loc);
                }}
            } catch (IOException e) {

                et_Locality.setText(" "+"Couldn't get the location.");
                Log.e("msg",""+"Couldn't get the location.");
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
            Log.e("msg",""+"No latLong found");
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

    public void NotNull(){


        if (S_Et_no == null) {
            S_Et_no = "";
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
        if (sp_type_name == null) {
            sp_type_name = "";
        } else {
        }

        if (S_Et_ps == null) {
            S_Et_ps = "";
        } else {
        }
        if (S_Et_an == null) {
            S_Et_an = "";
        } else {
        }
        if (S_Et_fn == null) {
            S_Et_fn = "";
        } else {
        }
        if (S_Et_age == null) {
            S_Et_age = "";
        } else {
        }

        if (S_et_Locality == null) {
            S_et_Locality = "";
        } else {
        }

    }

    private void AddHIstoryData() {
        progressDialog = new ProgressDialog(AddHistorySheets.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("CreatedBy", S_Uname);
            jsonBody.put("PsId", S_psid);
            jsonBody.put("Number", S_Et_no);
            jsonBody.put("Type", sp_type_name);
            jsonBody.put("PersonName", S_Et_ps);
            jsonBody.put("AliasName", S_Et_an);
            jsonBody.put("FatherName", S_Et_fn);
            jsonBody.put("Age", S_Et_age);
            jsonBody.put("Latitude", S_latitude);
            jsonBody.put("Longitude", S_longitude);
            jsonBody.put("Address", S_et_Locality);



            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:LandmarkEntry" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HistoryEntry, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:LandmarkEntry" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();


                            if (code!= null) {
                                if (code.equalsIgnoreCase("1")) {
                                    progressDialog.dismiss();
                                    Log.e("suceess", "" + Message);
                                    C_dialog.dismiss();
                                    Et_no.setText("");
                                    Et_age.setText("");
                                    Et_ps.setText("");
                                    Et_an.setText("");
                                    Et_fn.setText("");
                                    sp_type.setSelection(0);
                                    S_Et_no = "";
                                    S_Et_age = "";
                                    sp_type_name = "";
                                    S_Et_ps = "";
                                    S_Et_an = "";
                                    S_Et_fn = "";
                                    S_dob = "";
                                    S_latitude = "";
                                    S_longitude = "";
                                    S_et_Locality = "";
                                    Bt_c_dob.setText("DOB");
                                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHistorySheets.this);
                                    alertDialog.setTitle("Success");
                                    alertDialog.setMessage("Values Saved Succesfully");
                                    alertDialog.setIcon(R.drawable.succs);
                                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Write your code here to invoke YES event


                                            finish();
                                            Intent i = new Intent(getApplicationContext(), AddHistorySheets.class);
                                            startActivity(i);
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                } else {
                                    Log.e("not sucess", "" + Message);
                                    C_dialog.dismiss();
                                    progressDialog.dismiss();

//                                    Et_no.setText("");
//                                    Et_ps.setText("");
//                                    Et_an.setText("");
//                                    Et_age.setText("");
//                                    Et_fn.setText("");
//                                    sp_type.setSelection(0);
//                                    Bt_c_dob.setText("DOB");
                                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHistorySheets.this);
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage("" + Message);
                                    alertDialog.setIcon(R.drawable.alertt);
                                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                    // Toast.makeText(AddHistorySheets_View.this,""+Message,Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
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
            Log.e("VOLLEY", "Request:TrafficList" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HistoryList, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:TrafficList" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("HsList");
                            int number = jArray.length();

                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                No_arraylist_.clear();
                                Type_arraylist_.clear();
                                PersonName_arraylist_.clear();
                                AliasName_arraylist_.clear();
                                FatherName_arraylist_.clear();
                                Age_arraylist_.clear();
                                Add_arraylist_.clear();
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);



                                    No = json_data.getString("Number").toString();
                                    PersonName = json_data.getString("PersonName").toString();
                                    Type = json_data.getString("Type").toString();
                                    AliasName = json_data.getString("AliasName").toString();
                                    FatherName = json_data.getString("FatherName").toString();
                                    Age = json_data.getString("Age").toString();
                                    Add = json_data.getString("Address").toString();



                                    if (Type == null || Type.trim().equals("anyType{}") || Type.trim()
                                            .length() <= 0) {
                                        Type_arraylist_.add("");
                                    } else {
                                        Type_arraylist_.add(Type);
                                    }

                                    if (Add == null || Add.trim().equals("anyType{}") || Add.trim()
                                            .length() <= 0) {
                                        Add_arraylist_.add("");
                                    } else {
                                        Add_arraylist_.add(Add);
                                    }
                                if (No == null || No.trim().equals("anyType{}") || No.trim()
                                        .length() <= 0) {
                                    No_arraylist_.add("");
                                } else {
                                    No_arraylist_.add(No);
                                }

                                if (PersonName == null || PersonName.trim().equals("anyType{}") || PersonName.trim()
                                        .length() <= 0) {
                                    PersonName_arraylist_.add("");
                                } else {
                                    PersonName_arraylist_.add(PersonName);
                                }
                                if (AliasName == null || AliasName.trim().equals("anyType{}") || AliasName.trim()
                                        .length() <= 0) {
                                    AliasName_arraylist_.add("");
                                } else {
                                    AliasName_arraylist_.add(AliasName);
                                }
                                if (FatherName == null ||FatherName.trim().equals("anyType{}") || FatherName.trim()
                                        .length() <= 0) {
                                    FatherName_arraylist_.add("");
                                } else {
                                    FatherName_arraylist_.add(FatherName);
                                }

                                if (Age == null ||Age.trim().equals("anyType{}") || Age.trim()
                                        .length() <= 0) {
                                    Age_arraylist_.add("");
                                } else {
                                    Age_arraylist_.add(Age);
                                }
                                }

                            }


                            int count = Type_arraylist_.size();
                            Log.e(" count", "" + count);
                            SetValuesToLayout();
                        } else {

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHistorySheets.this);
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
    private void getHistorySheetType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "5");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Crime_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkMasters, new Response.Listener<String>() {
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

                                Samplemyclass wp0 = new Samplemyclass("0", "Type");
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
                                    HistorySheetData(countryList);
                                }
                            }


                        } else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Type");
                            // Binds all strings into an array
                            countryList.add(wp0);
                            if (countryList.size() > 0) {
                                HistorySheetData(countryList);
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
}
