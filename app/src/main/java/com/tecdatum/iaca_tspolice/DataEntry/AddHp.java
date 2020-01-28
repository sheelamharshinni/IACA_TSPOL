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
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

public class AddHp extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String HaltingPointsEntry = URLS.HaltingPointsEntry;
    private String HaltingList = URLS.HaltingList;
    private String updateHaltingPoints = URLS.updateHaltingPoints;
    private String HaltingPointUpdate = URLS.HaltingPointUpdate;
    private String LandmarkMasters = URLS.LandmarkMasters;


    private static final String TAG = AddHp.class.getSimpleName();
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

    ArrayList<Samplemyclass> countryList=new ArrayList<>();
    ArrayList<Samplemyclass> countryList2=new ArrayList<>();
    ArrayList<SampleHPclass> countryList1=new ArrayList<SampleHPclass>();


    CustomDateTimePicker custom1;
    String Sat, Srt, Ssvc, Ssvs, Ssac, Ssas;
    private Spinner sp_type;
    String Sat1,Srt1,Svc1,Svs1,Sac1,Sas1;
    RelativeLayout RL1,RL2;
    int Code;
    EditText et_locality,et_Cno,et_Cla,et_Noi,et_Nod;
    Button Bt_c_rest,Bt_c_submit,Bt_c_vl, Bt_c_edit,Bt_c_add;
    boolean timeoutexcep=false,httpexcep=false,genexcep=false;
    String S_HaltingPointName,edit_Hp_Name,S_discription,S_roadNo,S_vap,S_aap,S_locality,hp_name,hp_id,S_Cno,S_Cla,S_Noi,S_Nod,S_latitude,S_longitude,S_dateTime,Message
            , s_Ps_name,S_Uname, S_Pass,S_psid, s_role,S_IMEi,S_detected_,sp_type_id,sp_type_name;
    Button Bt_acc_date;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    TextView c_time,tv_c_psname;
    String formattedDate,e_id,e_n;
    AutoCompleteTextView actv,et_hp_a;
    Dialog C_dialog,vdialog,edit_dialog,add_dialog;
    LinearLayout ll_Latlong;
    TextView tv_Lat,tv_Lng,tv_alert;
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
        setContentView(R.layout.activity_add_hp);

        c_time=(TextView) findViewById(R.id.tv_hp_time);
        tv_c_psname=(TextView) findViewById(R.id.tv_hp_psname);
        et_locality=(EditText) findViewById(R.id.et_hp_locality);
        sp_type=(Spinner)findViewById(R.id.sp_hp_type) ;
        Bt_c_rest = (Button) findViewById(R.id.btn_hp_reset);
        Bt_c_submit = (Button) findViewById(R.id.btn_hp_submit);
        Bt_c_edit = (Button) findViewById(R.id.btn_hp_edit);
        Bt_c_add = (Button) findViewById(R.id.btn_hp_add);

        et_locality.setFilters(new InputFilter[]{filter});
        Bt_c_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(AddHp.this, "Edit", Toast.LENGTH_SHORT).show();

                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
                {

                    alertDialogue_Edit();

                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
                    C_dialog.dismiss();
                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }


            }
        });
        Bt_c_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
                {

                    alertDialogue_Add();

                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
                    C_dialog.dismiss();
                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }


              //  Toast.makeText(AddHp.this, "Add", Toast.LENGTH_SHORT).show();
            }
        });

        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_type.setSelection(0);
            }
        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S_locality = et_locality.getText().toString();
                if(sp_type != null && sp_type.getSelectedItem() !=null ) {
                    if (!(sp_type.getSelectedItem().toString().equals("Select Halting Point Name"))) {
                            if (!(et_locality.getText().toString().trim().isEmpty())) {
                                if (!(et_locality.getText().toString().equalsIgnoreCase(" "+"Couldn't get your the location"))) {
                                    alertDialogue_Conform();
                                } else {
                                Toast.makeText(AddHp.this, "Enter Your Location", Toast.LENGTH_SHORT).show();}
                               } else {
                                Toast.makeText(AddHp.this, "Location is mandatory", Toast.LENGTH_SHORT).show();}
                        } else {
                        Toast.makeText(AddHp.this, "Select Halting Point Name", Toast.LENGTH_SHORT).show();}}
                else {
                    Toast.makeText(AddHp.this, "Select Halting Point Name", Toast.LENGTH_SHORT).show();}
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
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        s_Ps_name = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        tv_c_psname.setText(s_Ps_name);
        String s_OrgName =bb.getString("OrgName","");
        TextView tv_OrgName=(TextView)findViewById(R.id.tv_distname) ;
        tv_OrgName.setText(""+s_OrgName);
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
    private void setData() {
        CheckConnectivity2();
    }
    private void CheckConnectivity2() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
        {
//            new getUpdateHP().execute();
//            new getAllHP().execute();

            getUpdateHP_Api();
            getAllHP_Api();


        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    private void HistorySheetData() {
        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, countryList);
        sp_type.setAdapter(adapter);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if(parent!=null) {
                }
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    sp_type_id =country.getId();
                    sp_type_name=country.getName();

                    Log.e(TAG,"sp_type_id"+sp_type_id);
                    Log.e(TAG,"sp_type_name"+sp_type_name);


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
//    class getUpdateHP extends AsyncTask<Void, Void, Void> {
//        private final ProgressDialog dialog = new ProgressDialog(AddHp.this);
//        @Override
//        protected void onPreExecute() {
//            this.dialog.setMessage("Loading data");
//            this.dialog.show();
//        }
//        @Override
//        protected Void doInBackground(Void... unused) {
//
//            String SOAP_ACTION = "http://tempuri.org/GetHaltingpoints";
//            String METHOD_NAME = "GetHaltingpoints";
//            String NAMESPACE = "http://tempuri.org/";
//            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetHaltingpoints";
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//            request.addProperty("userName", S_Uname);
//            request.addProperty("Password", S_Pass);
//            request.addProperty("ImeI", S_IMEi);
//            request.addProperty("VehicleId", "0");
//
//            envelope.setOutputSoapObject(request);
//            Log.e("request", "" + request);
//
//            try{
//                HttpTransportSE httpTransport=new HttpTransportSE(URL);
//                httpTransport.debug=true;
//                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
//                httpTransport.call(SOAP_ACTION, envelope);
//                Log.e("envelope", "" + envelope);
//                Log.d(TAG,"HTTP Response: \n"+ httpTransport.responseDump);
//                SoapObject respone = (SoapObject) envelope.getResponse();
//                Log.d("Res", ""+respone);
//                SoapObject result=(SoapObject)envelope.bodyIn;
//                Log.d(TAG," Response: \n"+result);
//                SoapObject root = (SoapObject) result.getProperty(0);
//                System.out.println("******** : " + root.getPropertyCount());
//                System.out.println("********Count : "+ root.getPropertyCount());
//
//                countryList = new ArrayList<Samplemyclass>();
//                countryList.clear();
////                Samplemyclass wb= new Samplemyclass("0", "Select");
////                countryList.add(wb);
//
//                for (int i = 0; i < root.getPropertyCount(); i++) {
//                    //  Object property = s_deals_1.getProperty(i);
//                    SoapObject s_deals_1 = (SoapObject) root.getProperty(i);
//                    System.out.println("********Count : " + s_deals_1.getPropertyCount());
//                    String e_id = s_deals_1.getProperty("PointId").toString();
//                    String e_n = s_deals_1.getProperty("PointName").toString();
//                    Samplemyclass wb1 = new Samplemyclass(e_id, e_n);
//                    countryList.add(wb1);
//
//                }
//
//                System.out.println("m " + countryList);
//            }
//            catch (Exception e) {
//                // TODO: handle exception
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void result) {
//
//            Log.e("result",""+result);
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//
//            }
//            if(timeoutexcep){
//                Toast.makeText(AddHp.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
//            }
//            else if(httpexcep){
//                Toast.makeText(AddHp.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
//            }
//            else if(genexcep){
//                Toast.makeText(AddHp.this, "Please try later", Toast.LENGTH_LONG).show();
//            }
//            else{
//                if (Code==0) {
//                    if (countryList == null) {
//                    }else {
//                        if (countryList.size() > 0) {
//                            HistorySheetData();
//                            Log.e("Type List",""+countryList);
//                        }
//                    }
//                }else{
//                }
//            }
//            timeoutexcep=false;httpexcep=false;genexcep=false;
//        }
//    }
    class getAllHP extends AsyncTask<Void, Void, Void> {

        int PSID= Integer.parseInt(S_psid);
        private final ProgressDialog dialog = new ProgressDialog(AddHp.this);
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/HaltingPointsView";
            String METHOD_NAME = "HaltingPointsView";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            request.addProperty("PSid", PSID);

            envelope.setOutputSoapObject(request);
            Log.e("request", "" + request);

            try{
                HttpTransportSE httpTransport=new HttpTransportSE(URL);
                httpTransport.debug=true;
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
                httpTransport.call(SOAP_ACTION, envelope);
                Log.e("envelope", "" + envelope);
                Log.d(TAG,"HTTP Response: \n"+ httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", ""+respone);
                SoapObject result=(SoapObject)envelope.bodyIn;
                Log.d(TAG," Response: \n"+result);

                Log.d(TAG," Response: \n"+result);
                SoapObject root = (SoapObject) result.getProperty(0);
                SoapObject s_deals = (SoapObject) root.getProperty("Data");
                System.out.println("********Count : "+ s_deals.getPropertyCount());

                countryList1 = new ArrayList<SampleHPclass>();
                countryList1.clear();
                countryList2 = new ArrayList<Samplemyclass>();
                countryList2.clear();

                for (int i = 0; i < s_deals.getPropertyCount(); i++) {

                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    String e_id = s_deals_1.getProperty("Id").toString();
                    String e_n = s_deals_1.getProperty("HaltingPointName").toString();
                    String e_lat = s_deals_1.getProperty("Latitude").toString();
                    String e_lng = s_deals_1.getProperty("Longitude").toString();
                    String e_loc = s_deals_1.getProperty("LandMark").toString();
                    SampleHPclass wb1 = new SampleHPclass(e_id, e_n,e_lat,e_lng,e_loc);
                    Samplemyclass wb2 = new Samplemyclass(e_id, e_n);




                    countryList2.add(wb2);
                    countryList1.add(wb1);
                }
                System.out.println("m " + countryList1);
                System.out.println("m " + countryList2);
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
                Toast.makeText(AddHp.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddHp.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddHp.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{
                if (Code==0) {
                    if (countryList1 == null) {
                    }else {
                        if (countryList1.size() > 0) {

                            //    HistorySheetData();
                        }
                    }
                }else{
                }
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }
    class pay extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(AddHp.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/UpdateJobChart";
            String METHOD_NAME = "UpdateJobChart";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=UpdateJobChart";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username",S_Uname);
            request.addProperty("password", S_Pass);
            request.addProperty("imei", S_IMEi);
            request.addProperty("HaltingPointId", sp_type_id);
            request.addProperty("latitude", S_latitude);
            request.addProperty("longitude", S_longitude);
            request.addProperty("Landmark", S_locality);
            envelope.setOutputSoapObject(request);
            Log.e("request",""+request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            Log.e("AddAccident_ENVOLOPE",""+envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();

                Log.e("result_AddAccident",""+result1);
                SoapObject result=(SoapObject)envelope.bodyIn;
                String[] testValues=new String[result.getPropertyCount()];
                for(int i=0;i<result.getPropertyCount();i++)
                {
                    Object property = result.getProperty(i);
                    testValues[i]=result.getProperty(i).toString();
                    SoapObject category_list = (SoapObject) property;
                    Code = Integer.parseInt(category_list.getProperty("Code").toString());
                    Message = category_list.getProperty("Message").toString();
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
                Toast.makeText(AddHp.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddHp.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddHp.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{
                if (Code==0) {
                    Log.e("suceess",""+Message);
                    C_dialog.dismiss();
                    sp_type.setSelection(0);
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
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

                    setData();
                }else{
                    Log.e("not sucess",""+Message);
                    C_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage(""+Message);
                    alertDialog.setIcon(R.drawable.alertt);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                    // Toast.makeText(AddHp.this,""+Message,Toast.LENGTH_LONG).show();
                }

                Log.e("result","done");
                //tableview();
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }
    class Addpay extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(AddHp.this);
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }
        int psid= Integer.parseInt(S_psid);
        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/AddNewHaltingPointsName";
            String METHOD_NAME = "AddNewHaltingPointsName";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=AddNewHaltingPointsName";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName",S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            request.addProperty("HaltingPointName", S_HaltingPointName);
            request.addProperty("PSid", psid);
            Log.e("HaltingPointName",""+S_HaltingPointName);
            envelope.setOutputSoapObject(request);
            Log.e("request",""+request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            Log.e("AddAccident_ENVOLOPE",""+envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();
                Log.e("result_AddAccident",""+result1);
                SoapObject result=(SoapObject)envelope.bodyIn;
                String[] testValues=new String[result.getPropertyCount()];
                for(int i=0;i<result.getPropertyCount();i++)
                {
                    Object property = result.getProperty(i);
                    testValues[i]=result.getProperty(i).toString();
                    SoapObject category_list = (SoapObject) property;
                    Code = Integer.parseInt(category_list.getProperty("Code").toString());
                    Message = category_list.getProperty("Message").toString();
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
                Toast.makeText(AddHp.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddHp.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddHp.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{
                if (Code==0) {
                    Log.e("suceess",""+Message);
                    add_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("Halting Point Added Succesfully");
                    alertDialog.setIcon(R.drawable.succs);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                    setData();
                }else{
                    Log.e("not sucess",""+Message);
                    add_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage(""+Message);
                    alertDialog.setIcon(R.drawable.alertt);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                    // Toast.makeText(AddHp.this,""+Message,Toast.LENGTH_LONG).show();
                }
                Log.e("result","done");
                //tableview();
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }
    class Editpay extends AsyncTask<Void, Void, Void> {
        int psid= Integer.parseInt(S_psid);
        private final ProgressDialog dialog = new ProgressDialog(AddHp.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/HaltingPointUpdate";
            String METHOD_NAME = "HaltingPointUpdate";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=HaltingPointUpdate";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName",S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            request.addProperty("HaltingPointName", edit_Hp_Name);
            request.addProperty("PSid", psid);
            request.addProperty("HaltingPointId", hp_id);

            envelope.setOutputSoapObject(request);
            Log.e("request",""+request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            Log.e("AddAccident_ENVOLOPE",""+envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();

                Log.e("result_AddAccident",""+result1);
                SoapObject result=(SoapObject)envelope.bodyIn;
                String[] testValues=new String[result.getPropertyCount()];
                for(int i=0;i<result.getPropertyCount();i++)
                {
                    Object property = result.getProperty(i);
                    testValues[i]=result.getProperty(i).toString();
                    SoapObject category_list = (SoapObject) property;
                    Code = Integer.parseInt(category_list.getProperty("Code").toString());
                    Message = category_list.getProperty("Message").toString();
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
                Toast.makeText(AddHp.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddHp.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddHp.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{
                if (Code==0) {
                    Log.e("suceess",""+Message);
                    edit_dialog.dismiss();
                    sp_type.setSelection(0);


//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
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

                    setData();
                }else{
                    Log.e("not sucess",""+Message);
                    edit_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage(""+Message);
                    alertDialog.setIcon(R.drawable.alertt);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                    // Toast.makeText(AddHp.this,""+Message,Toast.LENGTH_LONG).show();
                }

                Log.e("result","done");
                //tableview();
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }



    private void getUpdateHP_Api() {
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "7");
            jsonBody.put("Id", ""+S_psid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Crime_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Crime_Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String Message_LandmarkMasters = object.optString("Message").toString();

                        countryList.clear();
                        if (code.equalsIgnoreCase("1")){

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {
                                Samplemyclass wp0 = new Samplemyclass("0", "Select Halting Point Name");
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
                                    HistorySheetData();
                                    Log.e("Type List",""+countryList);
                                }
                            }



                        }else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Halting Point Name");
                            // Binds all strings into an array
                            countryList.add(wp0);
                            if (countryList.size() > 0) {
                                HistorySheetData();
                                Log.e("Type List",""+countryList);
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

    private void getAllHP_Api() {

        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("HirarchyID", S_psid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:HaltingList" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HaltingList, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:HaltingList" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String  Message_HaltingList = object.optString("Message").toString();


                        countryList1.clear();
                        countryList2.clear();

                        if (code.equalsIgnoreCase("1")){

                            JSONArray jArray = object.getJSONArray("HaltingList");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {


                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);


                                    String e_id = json_data.getString("Hid").toString();
                                    String e_n = json_data.getString("HaltingPointName").toString();
                                    String e_lat = json_data.getString("Latitude").toString();
                                    String e_lng = json_data.getString("Longitude").toString();
                                    String e_loc = json_data.getString("Landmark").toString();
                                    SampleHPclass wb1 = new SampleHPclass(e_id, e_n,e_lat,e_lng,e_loc);
                                    Samplemyclass wb2 = new Samplemyclass(e_id, e_n);


                                        countryList2.add(wb2);
                                        countryList1.add(wb1);


                                }
                                if (countryList.size() > 0) {
                                   // Crimetype(countryList);
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

    private void Addpay_Api() {

        JSONObject jsonBody = new JSONObject();
        try {


            jsonBody.put("HaltingPointName", S_HaltingPointName);
            jsonBody.put("Psid", S_psid);
            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:HaltingPointsEntry" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HaltingPointsEntry, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:HaltingPointsEntry" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String    Message_Addpay = object.optString("Message").toString();


                        if (code.equalsIgnoreCase("1")){
                                Log.e("suceess",""+Message_Addpay);
                                add_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
                                alertDialog.setTitle("Success");
                                alertDialog.setMessage("Halting Point Added Succesfully");
                                alertDialog.setIcon(R.drawable.succs);
                                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to invoke YES event
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                                setData();
                            }else{
                                Log.e("not sucess",""+Message_Addpay);
                                add_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage(""+Message_Addpay);
                                alertDialog.setIcon(R.drawable.alertt);
                                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                                // Toast.makeText(AddHp.this,""+Message,Toast.LENGTH_LONG).show();
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
                    530000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void Editpay_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            //jsonBody.put("HaltingPointName", S_HaltingPointName);

            jsonBody.put("HaltingPointName", edit_Hp_Name);
            jsonBody.put("Psid", S_psid);
            jsonBody.put("Hid", hp_id);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:HaltingPointUpdate" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HaltingPointUpdate, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:HaltingPointUpdate" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String    Message_editpay = object.optString("Message").toString();


                        if (code.equalsIgnoreCase("1")){

                                Log.e("suceess",""+Message_editpay);
                                edit_dialog.dismiss();
                                sp_type.setSelection(0);


//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
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

                                setData();
                            }else{
                                Log.e("not sucess",""+Message);
                                edit_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage(""+Message_editpay);
                                alertDialog.setIcon(R.drawable.alertt);
                                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                                // Toast.makeText(AddHp.this,""+Message,Toast.LENGTH_LONG).show();
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
                    5530000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void pay_Api() {

        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("HaltingPointName", S_HaltingPointName);
            jsonBody.put("PSid", S_psid);
            jsonBody.put("Hid", sp_type_id);
            jsonBody.put("Latitude", S_latitude);
            jsonBody.put("Longitude", S_longitude);
            jsonBody.put("Landmark", S_locality);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:updateHaltingPoints" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, updateHaltingPoints, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:updateHaltingPoints" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String    Message_update = object.optString("Message").toString();


                        if (code.equalsIgnoreCase("1")){

                                Log.e("suceess",""+Message_update);
                                C_dialog.dismiss();
                                sp_type.setSelection(0);

                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
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

                                setData();
                            }else{
                                Log.e("not sucess",""+Message_update);
                                C_dialog.dismiss();

                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHp.this);
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage(""+Message_update);
                                alertDialog.setIcon(R.drawable.alertt);
                                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                                // Toast.makeText(AddHp.this,""+Message,Toast.LENGTH_LONG).show();
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
                    5530000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void alertDialogue_Conform() {
        C_dialog = new Dialog(AddHp.this,R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.confom_hp);
        C_dialog.show();

        TextView d_ano = (TextView) C_dialog.findViewById(R.id.tv_hp_hp);
        TextView d_at = (TextView) C_dialog.findViewById(R.id.tv_hp_add);
        TextView d_lat = (TextView) C_dialog.findViewById(R.id.tv_hp_lat);
        TextView d_lng = (TextView) C_dialog.findViewById(R.id.tv_hp_lng);

        d_lat.setText(S_latitude);
        d_lng.setText(S_longitude);
        Log.e("5",""+S_locality);
        Log.e("5.1",""+sp_type_name);
        Log.e("6",""+S_locality);
        Log.e("6.1",""+sp_type_name);
       

        d_ano.setText(sp_type_name);
        d_at.setText(S_locality);
      

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_hp_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.btn_hp_Cancel);

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
    private void alertDialogue_Edit() {



        edit_dialog = new Dialog(AddHp.this,R.style.MyAlertDialogStyle);
        edit_dialog.setContentView(R.layout.confom_edit);
        edit_dialog.show();

        ll_Latlong = (LinearLayout)edit_dialog. findViewById(R.id.lt_latlong);
        tv_Lat = (TextView)edit_dialog. findViewById(R.id.at_hp_lat);
        tv_Lng = (TextView)edit_dialog. findViewById(R.id.at_hp_lng);
        tv_alert = (TextView)edit_dialog. findViewById(R.id.alert);
        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, countryList2);
        actv = (AutoCompleteTextView)edit_dialog. findViewById(R.id.at_hp_add);
        actv.setThreshold(1);//will start working from first characte
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);
        actv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(parent!=null) {
//                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
//                    if(country!=null) {
//                        hp_id = country.getId();
//                        hp_name = country.getName();
//                        Log.e(TAG, "hp_1" + country);
//                        Log.e(TAG, "hp_id1" + hp_id);
//                        Log.e(TAG, "hp_name1" + hp_name);
//                    }
//                    else{
//                        Log.e(TAG,"hp_id1"+hp_id);
//                        Log.e(TAG,"hp_name1"+hp_name);
//                    }
//                }else{
//                    Log.e(TAG,"hp_id1"+hp_id);
//                    Log.e(TAG,"hp_name1"+hp_name);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (parent != null) {
                    Samplemyclass country = (Samplemyclass) parent.getItemAtPosition(position);
                    if (country != null) {
                        hp_id = country.getId();
                        hp_name = country.getName();
                        Log.e(TAG, "hp_" + country);
                        Log.e(TAG, "hp_id" + hp_id);
                        Log.e(TAG, "hp_name" + hp_name);

                        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putString("hpid", hp_id);
                        edit.commit();


                        Log.d("HospitalName", hp_id);
                        for (int j = 0; j < countryList1.size(); j++) {
                            String arrString = countryList1.get(j).getId();
                            if (arrString.equals(hp_id)) {

//                                String query = countryList1.get(j).getLat();
                                String lat = countryList1.get(j).getLat();
                                String ln= countryList1.get(j).getLng();
                                String loc= countryList1.get(j).getLoc();
                              //  anyType{}anyType{}anyType{}

//                                if(lat!="anyType{}"){
//                                    ll_Latlong.setVisibility(View.VISIBLE);
//                                    tv_Lat.setText(lat);
//                                    tv_Lng.setText(ln);
//                                }
                                if(!lat.equalsIgnoreCase("anyType{}")){
                                    ll_Latlong.setVisibility(View.VISIBLE);
                                    tv_alert.setVisibility(View.GONE);
                                    tv_Lat.setText(lat);
                                    tv_Lng.setText(ln);
                                }else{
                                    ll_Latlong.setVisibility(View.VISIBLE);
                                    tv_alert.setVisibility(View.GONE);
                                    tv_Lat.setText("");
                                    tv_Lng.setText("");
                                }
                                Log.d("query", ""+lat+ln+loc);
                            }
                        }


                    } else {
                        Log.e(TAG, "hp_id" + hp_id);
                        Log.e(TAG, "hp_name" + hp_name);
                    }
                } else {
                    Log.e(TAG, "hp_id" + hp_id);
                    Log.e(TAG, "hp_name" + hp_name);
                }
                    Log.e(TAG + "Autocomplete", "Id:" + hp_id + " Halting Point:" + parent.getSelectedItem());

            }
        });


        Button C_confm = (Button) edit_dialog.findViewById(R.id.btn_hp_C_edit);
        Button C_cancel = (Button) edit_dialog.findViewById(R.id.btn_hp_C_Cancel);

        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edit_Hp_Name=actv.getText().toString();
                Log.e(TAG + "result", "Id:" + hp_id + " Halting Point:" + edit_Hp_Name);
                if(actv.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Halting Point cannot be Empty",Toast.LENGTH_SHORT).show();

                }else {
                    ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connec != null && (
                            (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                    (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

                       // new Editpay().execute();
                        Editpay_Api();

                    } else if (connec != null && (
                            (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                    (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
                        edit_dialog.dismiss();
                        FragmentManager fm = getSupportFragmentManager();
                        Connectivity td = new Connectivity();
                        td.show(fm, "NO CONNECTION");
                    }

                }
//                displayLocation();
//                CheckConnectivity();
            }
        });
        C_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_dialog.dismiss();
            }
        });
    }
    private void alertDialogue_Add() {
        add_dialog = new Dialog(AddHp.this,R.style.MyAlertDialogStyle);
        add_dialog.setContentView(R.layout.confom_add);
        add_dialog.show();

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, countryList2);
        et_hp_a = (AutoCompleteTextView)add_dialog. findViewById(R.id.et_hp_add);
        et_hp_a.setThreshold(1);//will start working from first character
        et_hp_a.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        et_hp_a.setTextColor(Color.RED);
        et_hp_a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(parent!=null) {
//                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
//                    if(country!=null) {
//                        hp_id = country.getId();
//                        hp_name = country.getName();
//                        Log.e(TAG, "hp_1" + country);
//                        Log.e(TAG, "hp_id1" + hp_id);
//                        Log.e(TAG, "hp_name1" + hp_name);
//                    }
//                    else{
//                        Log.e(TAG,"hp_id1"+hp_id);
//                        Log.e(TAG,"hp_name1"+hp_name);
//                    }
//                }else{
//                    Log.e(TAG,"hp_id1"+hp_id);
//                    Log.e(TAG,"hp_name1"+hp_name);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        et_hp_a.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (parent != null) {
                }

            }
        });


        Button C_confm = (Button) add_dialog.findViewById(R.id.btn_hp_C_ad);
        Button C_cancel = (Button) add_dialog.findViewById(R.id.btn_hp_C_ad_Cancel);



        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              S_HaltingPointName=  et_hp_a.getText().toString();
                if(et_hp_a.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Halting Point cannot be Empty",Toast.LENGTH_SHORT).show();

                }
                else{
                        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        if (connec != null && (
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

                           // new Addpay().execute();
                            Addpay_Api();

                        } else if (connec != null && (
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
                            add_dialog.dismiss();
                            FragmentManager fm = getSupportFragmentManager();
                            Connectivity td = new Connectivity();
                            td.show(fm, "NO CONNECTION");

                    }

                }

//                displayLocation();
//                CheckConnectivity();
            }
        });
        C_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_dialog.dismiss();
            }
        });
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
                                    AddHp.this, 1000);
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
//                        stringBuilder.append(",");
//                        stringBuilder.append(address1);
//                        stringBuilder.append(",");
//                        stringBuilder.append(address2);
                        String S_loc=stringBuilder.toString();
                        String t66 = "<font color='#ebedf5'>Location : </font>";
                        Log.e("msg",""+S_loc);
                        et_locality.setText(" "+S_loc);
                        // CurrentLocation.setText(Html.fromHtml(t66+S_loc));
                        // CurrentLocation.setText(S_loc);
                    }}
            } catch (IOException e) {
                //S_latitude= String.valueOf("0.0");
               // S_longitude= String.valueOf("0.0");
                et_locality.setText(" "+"Couldn't get your the location");
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



    private void CheckConnectivity() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
        {
           // new pay().execute();

            pay_Api();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
}
