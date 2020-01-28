package com.tecdatum.iaca_tspolice.DataEntry;

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
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddPCCTV extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    boolean timeoutexcep=false,httpexcep=false,genexcep=false;
    private static final String TAG = AddPCCTV.class.getSimpleName();
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
    ArrayList<Samplemyclass> countryList;
    RelativeLayout RL_sp_sct;
    String item2, tv_Ad, tv_Long, tv_Lat, S_Descr, psJuri, S_Uname, S_Pass,s_Ps_name,S_longitude,S_latitude,
            Message,S_detected_,S_psid, s_role,formattedDate,S_IMEi,S_CrimeNo,S_dateTime,S_location, Loc,Cat1,SubCat1,provider,S_cCat,S_cSubca;
    ArrayList<String> Sch_arraylist_ = new ArrayList<String>();
    ArrayList<String> CGp_arraylist_ = new ArrayList<String>();
    ArrayList<String> SecNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> PoleNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> LandMark_arraylist_ = new ArrayList<String>();
    ArrayList<String> Add_arraylist_ = new ArrayList<String>();
    ArrayList<String> Fixed_arraylist_ = new ArrayList<String>();
    ArrayList<String> PTZ_arraylist_ = new ArrayList<String>();
    ArrayList<String> IR_arraylist_ = new ArrayList<String>();
    ArrayList<String> Othe_arraylist_ = new ArrayList<String>();
    String Sch,CGp,PoleNo,SecNo,LandMark,Add,Fixed,PTZ,IR,Othe;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_c_psname,c_time;
    Button Bt_c_rest,Bt_c_submit,Bt_c_vl;
    EditText cg,pNo,secNo,lm,location,fixed,ptz,ir,Others;
    String S_cg,S_pNo,S_secNo,S_lm,S_fixed,S_ptz,S_ir,S_Others;
    Dialog C_dialog,vl_dialog;
    Spinner Schtype;
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
        setContentView(R.layout.activity_add_pcctv);
        tv_c_psname=(TextView)findViewById(R.id.tv_pcctv_psname);
        c_time=(TextView)findViewById(R.id.tv_pcctv_time);

        cg=(EditText)findViewById(R.id.et_pcctv_cg);
        pNo=(EditText)findViewById(R.id.et_pcctv_pn);
        secNo=(EditText)findViewById(R.id.et_pcctv_sn);
        lm=(EditText)findViewById(R.id.et_pcctv_lm);
        location=(EditText)findViewById(R.id.et_pcctv_ln);
        fixed=(EditText)findViewById(R.id.et_pcctv_fixed);
        ptz=(EditText)findViewById(R.id.et_pcctv_ptz);
        ir=(EditText)findViewById(R.id.et_pcctv_ir);
        Others=(EditText)findViewById(R.id.et_pcctv_others);

        cg.setFilters(new InputFilter[]{filter});
        lm.setFilters(new InputFilter[]{filter});
        location .setFilters(new InputFilter[]{filter});

        Schtype=(Spinner)findViewById(R.id.spin_SchemeType) ;
        Bt_c_vl=(Button)findViewById(R.id.btn_pcctv_vl);
        Bt_c_rest=(Button)findViewById(R.id.btn_pcctv_reset);
        Bt_c_submit=(Button)findViewById(R.id.btn_pcctv_submit);
        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckConnectivity1();
            }
        });
        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cg.setText("");
                pNo.setText("");
                secNo.setText("");
                lm.setText("");
                fixed.setText("");
                ptz.setText("");
                ir.setText("");
                Others.setText("");
                Schtype.setSelection(0);
            }
        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                S_cg=cg.getText().toString();
                S_pNo=pNo.getText().toString();
                S_secNo=secNo.getText().toString();
                S_lm=lm.getText().toString();
                S_location=location.getText().toString();
                S_fixed=fixed.getText().toString();
                S_ptz=ptz.getText().toString();
                S_ir=ir.getText().toString();
                S_Others=Others.getText().toString();


                if(Schtype != null && Schtype.getSelectedItem() !=null ) {

                        if (!(Schtype.getSelectedItem().toString().trim() == "Select Scheme Type")) {
                            if (!(secNo.getText().toString().trim() .isEmpty())) {

                                alertDialogue_Conform();
                            }else{
                                Toast.makeText(AddPCCTV.this, " Sector Number is Mandatory", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(AddPCCTV.this, "Select Scheme Type", Toast.LENGTH_SHORT).show();
                        }
                }
                else {
                    alertDialogue_Conform();
                }
//                if(secNo.getText().toString().trim().isEmpty()){
//                    Toast.makeText(AddPCCTV.this, "Please Enter Sector No", Toast.LENGTH_SHORT).show();
//                }else{
//                    alertDialogue_Conform();
//                }
            }
        });

        setData();

        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname=bb.getString("UserName", "");
        S_Pass =bb.getString("password","");
        S_IMEi=bb.getString("imei","");
        S_psid=bb.getString("Psid", "");
        s_Ps_name =bb.getString("Psname","");
        s_role =bb.getString("Role","");
        tv_c_psname.setText(s_Ps_name);
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
    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
        {
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
    class getpay extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(AddPCCTV.this);
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/GetPrposedCCTVRecordsdata";
            String METHOD_NAME = "GetPrposedCCTVRecordsdata";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetPrposedCCTVRecordsdata";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            envelope.setOutputSoapObject(request);

            Log.e("1245", S_Pass+ "    " + S_IMEi);
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
                SoapObject root = (SoapObject) result.getProperty(0);
                System.out.println("******** : " + root.getPropertyCount());

                Sch_arraylist_.clear();
                CGp_arraylist_.clear();
                SecNo_arraylist_.clear();
                PoleNo_arraylist_.clear();
                LandMark_arraylist_.clear();
                Add_arraylist_.clear();
                Fixed_arraylist_.clear();
                PTZ_arraylist_.clear();
                IR_arraylist_.clear();
                Othe_arraylist_.clear();
                for (int j = 0; j < root.getPropertyCount();j++) {
                    SoapObject s_deals = (SoapObject) root.getProperty(j);
                    System.out.println("********Count : " + s_deals.getPropertyCount());

                    for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                        Object property = s_deals.getProperty(i);
                        System.out.println("******** : " + property);
                        Sch = s_deals.getProperty("Scheme").toString();
                        CGp = s_deals.getProperty("CommunityGroup").toString();
                        SecNo = s_deals.getProperty("SectorNo").toString();
                        PoleNo = s_deals.getProperty("PoleNo").toString();
                        LandMark = s_deals.getProperty("LandMark").toString();
                        Add = s_deals.getProperty("Address").toString();
                        Fixed = s_deals.getProperty("Fixed").toString();
                        PTZ = s_deals.getProperty("PTZ").toString();
                        IR = s_deals.getProperty("IR").toString();
                        Othe = s_deals.getProperty("Others").toString();
                    }
                    if(Othe == null || Othe.trim().equals("anyType{}") || Othe.trim()
                            .length() <= 0){
                        Othe_arraylist_.add("");
                    }else {
                        Othe_arraylist_.add(Othe);
                    }
                    if(Sch == null || Sch.trim().equals("anyType{}") || Sch.trim()
                            .length() <= 0){
                        Sch_arraylist_.add("");
                    }else {
                        Sch_arraylist_.add(Sch);
                    }
                    if(CGp == null || CGp.trim().equals("anyType{}") || CGp.trim()
                            .length() <= 0){
                        CGp_arraylist_.add("");
                    }else {
                        CGp_arraylist_.add(CGp);
                    }
                  
                    if(IR == null || IR.trim().equals("anyType{}") || IR.trim()
                            .length() <= 0){
                        IR_arraylist_.add("");
                    }else {
                        IR_arraylist_.add(IR);
                    }
                    if(PTZ == null || PTZ.trim().equals("anyType{}") || PTZ.trim()
                            .length() <= 0){
                        PTZ_arraylist_.add("");
                    }else {
                        PTZ_arraylist_.add(PTZ);
                    }
                    if(Fixed == null || Fixed.trim().equals("anyType{}") || Fixed.trim()
                            .length() <= 0){
                        Fixed_arraylist_.add("");
                    }else {
                        Fixed_arraylist_.add(Fixed);
                    }
                    if(LandMark == null || LandMark.trim().equals("anyType{}") || LandMark.trim()
                            .length() <= 0){
                        LandMark_arraylist_.add("");
                    }else {
                        LandMark_arraylist_.add(LandMark);
                    }
                    if(PoleNo == null || PoleNo.trim().equals("anyType{}") || PoleNo.trim()
                            .length() <= 0){
                        PoleNo_arraylist_.add("");
                    }else {
                        PoleNo_arraylist_.add(PoleNo);
                  
                    }
                    if(SecNo == null || SecNo.trim().equals("anyType{}") || SecNo.trim()
                            .length() <= 0){
                        SecNo_arraylist_.add("");
                    }else {
                        SecNo_arraylist_.add(SecNo);
                    }
                    if(Add == null || Add.trim().equals("anyType{}") || Add.trim()
                            .length() <= 0){
                        Add_arraylist_.add("");
                    }else {
                        Add_arraylist_.add(Add);
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
                Toast.makeText(AddPCCTV.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddPCCTV.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddPCCTV.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{
                //Log.d(TAG,  "Re "+final_result.toString());

                Log.e(TAG, " " + SecNo_arraylist_);
                Log.e(TAG, " " + PoleNo_arraylist_);
                Log.e(TAG, " " + LandMark_arraylist_);
                Log.e(TAG, " " + Fixed_arraylist_);
                Log.e(TAG, " " + Add_arraylist_);
                Log.e(TAG, " " + PTZ_arraylist_);
                Log.e(TAG, "" + IR_arraylist_);
                SetValuesToLayout();
                if (Code==0) {
                }else{
                    Log.e("not sucess",""+Code);
                    // Toast. makeText(AddPCCTV.this,""+Message,Toast.LENGTH_LONG).show();
                }
                Log.e("result","done");
                //tableview();
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }
    private void SetValuesToLayout() {
        vl_dialog = new Dialog(AddPCCTV.this, R.style.HiddenTitleTheme);
        vl_dialog.setContentView(R.layout.vl_pcctv);
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
        if((SecNo_arraylist_.size())>0|(SecNo_arraylist_.size())!=0) {
            Log.e("1", "" + SecNo_arraylist_.size());


            if ((SecNo_arraylist_.size()) == 1) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(SecNo_arraylist_.get(0));
                if ((CGp_arraylist_.size()) != 0) {
                    if ((CGp_arraylist_.size()) == 1) {
                        t1.setText(CGp_arraylist_.get(0));
                    }
                }

                if ((PoleNo_arraylist_.size())!=0) {
                    if ((PoleNo_arraylist_.size()) == 1) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                    }
                }
            }

            if ((SecNo_arraylist_.size()) == 2) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(SecNo_arraylist_.get(0));
                st2.setText(SecNo_arraylist_.get(1));




                if ((CGp_arraylist_.size()) != 0) {
                    if ((CGp_arraylist_.size()) == 1) {
                        t1.setText(CGp_arraylist_.get(0));
                    }
                    if ((CGp_arraylist_.size()) == 2) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                    }

                }

                if ((PoleNo_arraylist_.size())!=0) {
                    if ((PoleNo_arraylist_.size()) == 1) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                    }
                    if ((PoleNo_arraylist_.size()) == 2) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                    }

                }

            }
            if ((SecNo_arraylist_.size()) == 3) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);

                st1.setText(SecNo_arraylist_.get(0));
                st2.setText(SecNo_arraylist_.get(1));
                st3.setText(SecNo_arraylist_.get(2));
                if ((CGp_arraylist_.size()) != 0) {
                    if ((CGp_arraylist_.size()) == 1) {
                        t1.setText(CGp_arraylist_.get(0));
                    }
                    if ((CGp_arraylist_.size()) == 2) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                    }
                    if ((CGp_arraylist_.size()) == 3) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                        t3.setText(CGp_arraylist_.get(2));
                    }
                }

                if ((PoleNo_arraylist_.size())!=0) {
                    if ((PoleNo_arraylist_.size()) == 1) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                    }
                    if ((PoleNo_arraylist_.size()) == 2) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                    }
                    if ((PoleNo_arraylist_.size()) == 3) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                        dt3.setText(PoleNo_arraylist_.get(2));
                    }
                }

            }
            if ((SecNo_arraylist_.size()) == 4) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(SecNo_arraylist_.get(0));
                st2.setText(SecNo_arraylist_.get(1));
                st3.setText(SecNo_arraylist_.get(2));
                st4.setText(SecNo_arraylist_.get(3));

                if ((CGp_arraylist_.size()) != 0) {
                    if ((CGp_arraylist_.size()) == 1) {
                        t1.setText(CGp_arraylist_.get(0));
                    }
                    if ((CGp_arraylist_.size()) == 2) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                    }
                    if ((CGp_arraylist_.size()) == 3) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                        t3.setText(CGp_arraylist_.get(2));
                    }
                    if ((CGp_arraylist_.size()) == 4) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                        t3.setText(CGp_arraylist_.get(2));
                        t4.setText(CGp_arraylist_.get(3));
                    }

                }

                if ((PoleNo_arraylist_.size())!=0) {
                    if ((PoleNo_arraylist_.size()) == 1) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                    }
                    if ((PoleNo_arraylist_.size()) == 2) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                    }
                    if ((PoleNo_arraylist_.size()) == 3) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                        dt3.setText(PoleNo_arraylist_.get(2));
                    }
                    if ((PoleNo_arraylist_.size()) == 4) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                        dt3.setText(PoleNo_arraylist_.get(2));
                        dt4.setText(PoleNo_arraylist_.get(3));
                    }

                }



            }
            if ((SecNo_arraylist_.size()) == 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);

                st1.setText(SecNo_arraylist_.get(0));
                st2.setText(SecNo_arraylist_.get(1));
                st3.setText(SecNo_arraylist_.get(2));
                st4.setText(SecNo_arraylist_.get(3));
                st5.setText(SecNo_arraylist_.get(4));
                if ((CGp_arraylist_.size()) != 0) {
                    if ((CGp_arraylist_.size()) == 1) {
                        t1.setText(CGp_arraylist_.get(0));
                    }
                    if ((CGp_arraylist_.size()) == 2) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                    }
                    if ((CGp_arraylist_.size()) == 3) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                        t3.setText(CGp_arraylist_.get(2));
                    }
                    if ((CGp_arraylist_.size()) == 4) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                        t3.setText(CGp_arraylist_.get(2));
                        t4.setText(CGp_arraylist_.get(3));
                    }
                    if ((CGp_arraylist_.size()) == 5) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                        t3.setText(CGp_arraylist_.get(2));
                        t4.setText(CGp_arraylist_.get(3));
                        t5.setText(CGp_arraylist_.get(4));
                    }
                }

                if ((PoleNo_arraylist_.size())!=0) {
                    if ((PoleNo_arraylist_.size()) == 1) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                    }
                    if ((PoleNo_arraylist_.size()) == 2) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                    }
                    if ((PoleNo_arraylist_.size()) == 3) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                        dt3.setText(PoleNo_arraylist_.get(2));
                    }
                    if ((PoleNo_arraylist_.size()) == 4) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                        dt3.setText(PoleNo_arraylist_.get(2));
                        dt4.setText(PoleNo_arraylist_.get(3));
                    }
                    if ((PoleNo_arraylist_.size()) == 5) {
                        dt1.setText(PoleNo_arraylist_.get(0));
                        dt2.setText(PoleNo_arraylist_.get(1));
                        dt3.setText(PoleNo_arraylist_.get(2));
                        dt4.setText(PoleNo_arraylist_.get(3));
                        dt5.setText(PoleNo_arraylist_.get(4));
                    }
                }
            }
            if ((SecNo_arraylist_.size()) > 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                Log.e("yes", "" + CGp_arraylist_.size());
                st1.setText(SecNo_arraylist_.get(0));
                st2.setText(SecNo_arraylist_.get(1));
                st3.setText(SecNo_arraylist_.get(2));
                st4.setText(SecNo_arraylist_.get(3));
                st5.setText(SecNo_arraylist_.get(4));
                if ((CGp_arraylist_.size()) != 0) {
                    if ((CGp_arraylist_.size()) == 1) {
                        t1.setText(CGp_arraylist_.get(0));
                    }
                    if ((CGp_arraylist_.size()) == 2) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                    }
                    if ((CGp_arraylist_.size()) == 3) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                        t3.setText(CGp_arraylist_.get(2));
                    }
                    if ((CGp_arraylist_.size()) == 4) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                        t3.setText(CGp_arraylist_.get(2));
                        t4.setText(CGp_arraylist_.get(3));
                    }
                    if ((CGp_arraylist_.size()) == 5) {
                        t1.setText(CGp_arraylist_.get(0));
                        t2.setText(CGp_arraylist_.get(1));
                        t3.setText(CGp_arraylist_.get(2));
                        t4.setText(CGp_arraylist_.get(3));
                        t5.setText(CGp_arraylist_.get(4));
                    }
                }

                    if ((PoleNo_arraylist_.size())!=0) {
                        if ((PoleNo_arraylist_.size()) == 1) {
                            dt1.setText(PoleNo_arraylist_.get(0));
                        }
                        if ((PoleNo_arraylist_.size()) == 2) {
                            dt1.setText(PoleNo_arraylist_.get(0));
                            dt2.setText(PoleNo_arraylist_.get(1));
                        }
                        if ((PoleNo_arraylist_.size()) == 3) {
                            dt1.setText(PoleNo_arraylist_.get(0));
                            dt2.setText(PoleNo_arraylist_.get(1));
                            dt3.setText(PoleNo_arraylist_.get(2));
                        }
                        if ((PoleNo_arraylist_.size()) == 4) {
                            dt1.setText(PoleNo_arraylist_.get(0));
                            dt2.setText(PoleNo_arraylist_.get(1));
                            dt3.setText(PoleNo_arraylist_.get(2));
                            dt4.setText(PoleNo_arraylist_.get(3));
                        }
                        if ((PoleNo_arraylist_.size()) == 5) {
                            dt1.setText(PoleNo_arraylist_.get(0));
                            dt2.setText(PoleNo_arraylist_.get(1));
                            dt3.setText(PoleNo_arraylist_.get(2));
                            dt4.setText(PoleNo_arraylist_.get(3));
                            dt5.setText(PoleNo_arraylist_.get(4));
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

        C_dialog = new Dialog(AddPCCTV.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_confm_pcctv);
        C_dialog.show();
        TextView d_styp = (TextView) C_dialog.findViewById(R.id.tv_cctv_stype);
        TextView d_cg = (TextView) C_dialog.findViewById(R.id.tv_cctv_cg);
        TextView d_pno = (TextView) C_dialog.findViewById(R.id.tv_cctv_pno);
        TextView d_sc = (TextView) C_dialog.findViewById(R.id.tv_cctv_sno);
        TextView d_lm=(TextView) C_dialog.findViewById(R.id.tv_cctv_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_cctv_ln);
        TextView d_fixed=(TextView) C_dialog.findViewById(R.id.tv_cctv_fixed);
        TextView d_ptz=(TextView) C_dialog.findViewById(R.id.tv_cctv_ptz);
        TextView d_ir = (TextView) C_dialog.findViewById(R.id.tv_cctv_ir);
        TextView d_others=(TextView) C_dialog.findViewById(R.id.tv_cctv_others);

        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_pcctv_pscode);
        pscode.setText(""+s_Ps_name);

        if(Sch_arraylist_.size()>count){
            d_styp.setText(Sch_arraylist_.get(count));
        }
        if(CGp_arraylist_.size()>count){
            d_cg.setText(CGp_arraylist_.get(count));
        }
        if(PoleNo_arraylist_.size()>count){
            d_pno.setText(PoleNo_arraylist_.get(count));
        }
        if(SecNo_arraylist_.size()>count){
            d_sc.setText(SecNo_arraylist_.get(count));
        }
        if(LandMark_arraylist_.size()>count){
            d_lm.setText(LandMark_arraylist_.get(count));
        }
        if(Add_arraylist_.size()>count){
            d_ln.setText(Add_arraylist_.get(count));
        }
        if(Fixed_arraylist_.size()>count){
            d_fixed.setText(Fixed_arraylist_.get(count));
        }
        if(PTZ_arraylist_.size()>count){
            d_ptz.setText(PTZ_arraylist_.get(count));
        }
        if(IR_arraylist_.size()>count){
            d_ir.setText(IR_arraylist_.get(count));
        }
        if(Othe_arraylist_.size()>count){
            d_others.setText(Othe_arraylist_.get(count));
        }



        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_cctv_Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });
    }
    private void alertDialogue_Conform() {
        C_dialog = new Dialog(AddPCCTV.this,R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.confom_pcctv);
        C_dialog.show();
        TextView d_styp = (TextView) C_dialog.findViewById(R.id.tv_cctv_stype);
        TextView d_cg = (TextView) C_dialog.findViewById(R.id.tv_cctv_cg);
        TextView d_pno = (TextView) C_dialog.findViewById(R.id.tv_cctv_pno);
        TextView d_sc = (TextView) C_dialog.findViewById(R.id.tv_cctv_sno);
        TextView d_lm=(TextView) C_dialog.findViewById(R.id.tv_cctv_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_cctv_ln);
        TextView d_fixed=(TextView) C_dialog.findViewById(R.id.tv_cctv_fixed);
        TextView d_ptz=(TextView) C_dialog.findViewById(R.id.tv_cctv_ptz);
        TextView d_ir = (TextView) C_dialog.findViewById(R.id.tv_cctv_ir);
        TextView d_others=(TextView) C_dialog.findViewById(R.id.tv_cctv_others);

        d_styp.setText(item2);
        d_cg.setText(S_cg);
        d_pno.setText(S_pNo);
        d_sc.setText(S_secNo);
        d_lm.setText(S_lm);
        d_ln.setText(S_location);
        d_fixed.setText(S_fixed);
        d_ptz.setText(S_ptz);
        d_ir.setText(S_ir);
        d_others.setText(S_Others);


        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_cctv_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.btn_cctv_Cancel);

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

        private final ProgressDialog dialog = new ProgressDialog(AddPCCTV.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/AddPrposedCCTVData";
            String METHOD_NAME = "AddPrposedCCTVData";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username", S_Uname);
            request.addProperty("password", S_Pass);
            request.addProperty("imei", S_IMEi);
            request.addProperty("schemeType", item2);
            request.addProperty("CommunityGroup", S_cg);
            request.addProperty("SectorNo", S_secNo);
            request.addProperty("PoleNo", S_pNo);
            request.addProperty("Landmark", S_lm);


            request.addProperty("latitude", S_latitude);
            request.addProperty("longitude", S_longitude);
            request.addProperty("Adress", S_location);
            request.addProperty("Fixed", S_fixed);
            request.addProperty("PTZ", S_ptz);
            request.addProperty("IR", S_ir);
            request.addProperty("Others", S_Others);


            envelope.setOutputSoapObject(request);

            Log.e("request",""+request);





            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            Log.e("ADDCRIME_ENVOLOPE",""+envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();

                Log.e("result_addcctv",""+result1);
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
                Toast.makeText(AddPCCTV.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddPCCTV.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddPCCTV.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{

                if (Code==0) {
                    Log.e("suceess",""+Message);
                    C_dialog.dismiss();
                    cg.setText("");
                    pNo.setText("");
                    secNo.setText("");
                    lm.setText("");
                    fixed.setText("");
                    ptz.setText("");
                    ir.setText("");
                    Others.setText("");
                    Schtype.setSelection(0);
                    item2="";
                    S_cg="";
                    S_secNo="";
                    S_pNo="";
                    S_lm="";
                    S_latitude="";
                    S_longitude="";
                    S_location="";
                    S_fixed="";
                    S_ptz="";
                    S_ir="";
                    S_Others="";


//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddPCCTV.this);
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
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddPCCTV.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage(""+Message);
                    alertDialog.setIcon(R.drawable.alertt);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                    // Toast.makeText(AddPCCTV.this,""+Message,Toast.LENGTH_LONG).show();
                }

                Log.e("result","done");
                //tableview();
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }
    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
        {

            new pay().execute();

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

        new getSchemeType().execute();
       // countryList = new ArrayList<>();
//        countryList.add(new Samplemyclass("0", "Select Scheme Type"));
//        countryList.add(new Samplemyclass("1", "Government "));
//        countryList.add(new Samplemyclass("2", "Community"));

    }
    class getSchemeType extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(AddPCCTV.this);
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/CCTVSchemeType_Master";
            String METHOD_NAME = "CCTVSchemeType_Master";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=CCTVSchemeType_Master";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);

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


                SoapObject root = (SoapObject) result.getProperty(0);
                SoapObject s_deals = (SoapObject) root.getProperty("Data");
                System.out.println("********Count : "+ s_deals.getPropertyCount());


                countryList = new ArrayList<>();
                countryList.clear();
                Samplemyclass wb= new Samplemyclass("0", "Select Scheme Type");
                countryList.add( wb);
                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                    //  Object property = s_deals_1.getProperty(i);

                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    System.out.println("********Count : " + s_deals_1.getPropertyCount());
                    //  SoapObject category_list = (SoapObject) s_deals_1.getProperty(0);

                    String e_id = s_deals_1.getProperty("Id").toString();
                    String e_n = s_deals_1.getProperty("Scheme").toString();
                    Samplemyclass wb1 = new Samplemyclass(e_id, e_n);
                    countryList.add(wb1);
                }
                System.out.println("Crimetypes " + countryList);


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
                Toast.makeText(AddPCCTV.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddPCCTV.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddPCCTV.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{
                if(countryList.size()>0) {
                    Schemetype();
                }
                if (Code==0) {
                }else{
                }
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }
    private void Schemetype() {
        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, countryList);
        Schtype.setAdapter(adapter);
        Schtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                //  Toast.makeText(getApplicationContext(), "" + pos, Toast.LENGTH_SHORT).show();
                if(pos!= 0) {
                    if(pos== 1) {
                        cg.setVisibility(View.VISIBLE);
                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                        Cat1 = country.getId();
                        item2 = country.getName();
                        Log.e("Cat1", "" + Cat1);

                    }else {
                        cg.setVisibility(View.GONE);
                        // RL_sp_sct.setVisibility(View.VISIBLE);
                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                        Cat1 = country.getId();
                        item2 = country.getName();
                        Log.e("Cat1", "" + Cat1);
                    }

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, countryList);
//        if( adapter!=null) {
//            Category.setAdapter(adapter);
//            Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    int pos = parent.getSelectedItemPosition();
//                    if (pos != 0) {
//                        RL_sp_sct.setVisibility(View.VISIBLE);
//                        Samplemyclass list = (Samplemyclass) SCategory.getSelectedItem();
//                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
//
//                        Cat1 = country.getId();
//                        item2 = country.getName();
//                        Log.e("Cat1", "" + Cat1);
//
//                        // Toast.makeText(getApplicationContext(), "" + Cat1, Toast.LENGTH_SHORT).show();
//                        // Toast.makeText(getApplicationContext(), "Crime Type is Mandatory ", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                }
//            });
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e("msg","onActivityResult");
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
                                    AddPCCTV.this, 1000);
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
    public void onBackPressed() {

        new AlertDialog.Builder(this)

                .setIcon(R.drawable.alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                })
                .setNegativeButton("No", null)
                .show();

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
                    location.setText(" "+S_loc);
                    // CurrentLocation.setText(Html.fromHtml(t66+S_loc));
                    // CurrentLocation.setText(S_loc);
                }}
            } catch (IOException e) {

                location.setText(" "+"Couldn't get the location.");
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
}