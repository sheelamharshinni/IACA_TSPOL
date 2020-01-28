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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
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
import java.util.Timer;

public class AddRoadJunction extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    boolean timeoutexcep=false,httpexcep=false,genexcep=false;
    private static final String TAG = AddRoadJunction.class.getSimpleName();
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
    Dialog C_dialog,vdialog;
    TextView d_cno,d_ct,d_sct,d_dt,d_ad,d_ds,d_dis;
    Button C_cancel,C_confm;
    CustomDateTimePicker custom, custom1;
    Timer timer = new Timer();
    Timer timer1 = new Timer();
    int Code;
    final long period = 1000;
    TextView c_time;
    String formattedDate;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_c_psname;
    String  S_Uname, S_Pass,s_Ps_name,S_longitude,S_latitude,
            Message,S_psid, s_role,S_IMEi;
    EditText et_jn,et_lm,et_Location,et_remarks;
    String S_et_jn,S_et_lm,S_et_Location,S_et_remarks;
    TextView dt_jn,d_lm,d_Location,d_remarks;
    Button Bt_c_vl,Bt_c_rest,Bt_c_submit;
    ArrayList<String> Remarks_arraylist_ = new ArrayList<String>();
    ArrayList<String> Type_arraylist_ = new ArrayList<String>();
    ArrayList<String> ldmk_arraylist_ = new ArrayList<String>();
    ArrayList<String> Add_arraylist_ = new ArrayList<String>();
    String Type, ldmk, Add, EnteredD;
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
        setContentView(R.layout.activity_add_road_junction);

        c_time=(TextView)findViewById(R.id.tv_rj_time) ;
        tv_c_psname=(TextView)findViewById(R.id.tv_rj_psname);
        et_jn=(EditText)findViewById(R.id.et_rj_jName) ;
        et_lm=(EditText)findViewById(R.id.et_rj_lm) ;
        et_remarks=(EditText)findViewById(R.id.et_rj_dis) ;
        et_Location=(EditText)findViewById(R.id.et_rj_ln) ;

        Bt_c_vl=(Button)findViewById(R.id.btn_rj_vl);
        Bt_c_rest=(Button)findViewById(R.id.btn_rj_reset);
        Bt_c_submit=(Button)findViewById(R.id.btn_rj_submit);

        et_jn.setFilters(new InputFilter[]{filter});
        et_lm.setFilters(new InputFilter[]{filter});
        et_remarks.setFilters(new InputFilter[]{filter});
        et_Location.setFilters(new InputFilter[]{filter});

        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckConnectivity1();
            }
        });
        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_jn.setText("");
                et_lm.setText("");
                et_remarks.setText("");
            }
        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                S_et_jn=et_jn.getText().toString();
                S_et_lm=et_lm.getText().toString();
                S_et_remarks=et_remarks.getText().toString();
                S_et_Location=et_Location.getText().toString();

                if (!(et_jn.getText().toString().trim() .isEmpty())) {
                    alertDialogue_Conform();
                }else{
                    Toast.makeText(AddRoadJunction.this, "Please Enter Junction Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        ConnectivityManager connec = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
        {
            new getpay().execute();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm =  getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    class getpay extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(AddRoadJunction.this);
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/GetLandmarkjunctions";
            String METHOD_NAME = "GetLandmarkjunctions";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetLandmarkjunctions";

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

                Type_arraylist_.clear();
                ldmk_arraylist_.clear();
                Add_arraylist_.clear();
                Remarks_arraylist_.clear();

                for (int j = 0; j < root.getPropertyCount();j++) {
                    SoapObject s_deals = (SoapObject) root.getProperty(j);
                    System.out.println("********Count : " + s_deals.getPropertyCount());
                    for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                        Object property = s_deals.getProperty(i);
                        System.out.println("******** : " + property);
                        Type = s_deals.getProperty("Junctionname").toString();
                        ldmk = s_deals.getProperty("Landmark").toString();
                        Add = s_deals.getProperty("Address").toString();
                        EnteredD = s_deals.getProperty("EnteredDate").toString();
                    }

                    if(EnteredD == null || EnteredD.trim().equals("anyType{}") || EnteredD.trim()
                            .length() <= 0){
                        Remarks_arraylist_.add("");
                    }else {
                        Remarks_arraylist_.add(EnteredD);
                    }
                    if(Add == null || Add.trim().equals("anyType{}") || Add.trim()
                            .length() <= 0){
                        Add_arraylist_.add("");
                    }else {
                        Add_arraylist_.add(Add);
                    }

                    if(ldmk == null || ldmk.trim().equals("anyType{}") || ldmk.trim()
                            .length() <= 0){
                        ldmk_arraylist_.add("");
                    }else {
                        ldmk_arraylist_.add(ldmk);
                    }

                    if(Type == null || Type.trim().equals("anyType{}") || Type.trim()
                            .length() <= 0){
                        Type_arraylist_.add("");
                    }else {
                        Type_arraylist_.add(Type);
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
                Toast.makeText(getApplicationContext(), "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(getApplicationContext(), "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(getApplicationContext(), "Please try later", Toast.LENGTH_LONG).show();
            }
            else{
                //Log.d(TAG,  "Re "+final_result.toString());

                Log.e(TAG, "Cnumber : " + Remarks_arraylist_);
                Log.e(TAG, "Cnumber : " + Type_arraylist_);
                Log.e(TAG, "Cnumber : " + ldmk_arraylist_);


                int count=  Type_arraylist_.size();

                Log.e(" count",""+count);
                SetValuesToLayout(count);
                if (Code==0) {
                }else{
                    Log.e("not sucess",""+Code);
                    // Toast.makeText(getActivity(),""+Message,Toast.LENGTH_LONG).show();
                }
                Log.e("result","done");
                //tableview();
            }
            timeoutexcep=false;httpexcep=false;genexcep=false;
        }
    }
    private void SetValuesToLayout(int coun) {

        vdialog = new Dialog(AddRoadJunction.this, R.style.HiddenTitleTheme);
        vdialog.setContentView(R.layout.vl_rj);
        vdialog.show();
        Log.e("t", "" );
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

//        TextView st1 = (TextView) vdialog.findViewById(R.id.tv_p1);
//        TextView st2 = (TextView) vdialog.findViewById(R.id.tv_p2);
//        TextView st3 = (TextView) vdialog.findViewById(R.id.tv_p3);
//        TextView st4 = (TextView) vdialog.findViewById(R.id.tv_p4);
//        TextView st5 = (TextView) vdialog.findViewById(R.id.tv_p5);

        TextView dt1 = (TextView) vdialog.findViewById(R.id.tv_p1);
        TextView dt2 = (TextView) vdialog.findViewById(R.id.tv_p2);
        TextView dt3 = (TextView) vdialog.findViewById(R.id.tv_p3);
        TextView dt4 = (TextView) vdialog.findViewById(R.id.tv_p4);
        TextView dt5 = (TextView) vdialog.findViewById(R.id.tv_p5);

        TextView v1 = (TextView) vdialog.findViewById(R.id.tv_m1);
        TextView v2 = (TextView) vdialog.findViewById(R.id.tv_m2);
        TextView v3 = (TextView) vdialog.findViewById(R.id.tv_m3);
        TextView v4 = (TextView) vdialog.findViewById(R.id.tv_m4);
        TextView v5 = (TextView) vdialog.findViewById(R.id.tv_m5);

        Log.e("t", "" );
        if((Type_arraylist_.size())>0|(Type_arraylist_.size())!=0) {
            Log.e("checked>0", "" );
            if ((Type_arraylist_.size())==1) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Remarks_arraylist_.get(0));
            }
            if ((Type_arraylist_.size())==2) {
                r0.setVisibility(View.VISIBLE);

                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Remarks_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Remarks_arraylist_.get(1));

            }
            if ((Type_arraylist_.size()) ==3) {
                r0.setVisibility(View.VISIBLE);

                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);

                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Remarks_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Remarks_arraylist_.get(1));

                t3.setText(Type_arraylist_.get(2));
                dt3.setText(Remarks_arraylist_.get(2));


            }

            if ((Type_arraylist_.size())==4) {
                r0.setVisibility(View.VISIBLE);

                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Remarks_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Remarks_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(Remarks_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(Remarks_arraylist_.get(3));


            }
            if ((Type_arraylist_.size())==5) {
                r0.setVisibility(View.VISIBLE);

                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Remarks_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Remarks_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(Remarks_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(Remarks_arraylist_.get(3));
                t5.setText(Type_arraylist_.get(4));
                dt5.setText(Remarks_arraylist_.get(4));
            }
            if ((Type_arraylist_.size()) > 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);

                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Remarks_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Remarks_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(Remarks_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(Remarks_arraylist_.get(3));
                t5.setText(Type_arraylist_.get(4));
                dt5.setText(Remarks_arraylist_.get(4));

            }

        }else {
            Log.e("requ", "" );
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
        C_dialog = new Dialog(AddRoadJunction.this,R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_roadjuncton);
        C_dialog.show();

        dt_jn = (TextView) C_dialog.findViewById(R.id.tv_rj_jn);
        d_lm = (TextView) C_dialog.findViewById(R.id.tv_rj_lm);
        d_ad = (TextView) C_dialog.findViewById(R.id.tv_rj_add);
        d_remarks = (TextView) C_dialog.findViewById(R.id.tv_rj_remarks);
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_rj_pscode);
        pscode.setText(s_Ps_name);

        if(Type_arraylist_.size()>count){
            dt_jn.setText(Type_arraylist_.get(count));
        }
        if(ldmk_arraylist_.size()>count){
            d_lm.setText(ldmk_arraylist_.get(count));
        }
        if(Add_arraylist_.size()>count){
            d_ad.setText(Add_arraylist_.get(count));
        }
        if(Remarks_arraylist_.size()>count){
            d_remarks.setText(Remarks_arraylist_.get(count));
        }
        C_confm = (Button) C_dialog.findViewById(R.id.btn_rj_Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });


    }
    private void alertDialogue_Conform() {
        C_dialog = new Dialog(AddRoadJunction.this,R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.confm_roadjuncton);
        C_dialog.show();
        dt_jn = (TextView) C_dialog.findViewById(R.id.tv_rj_jn);
        d_lm = (TextView) C_dialog.findViewById(R.id.tv_rj_lm);
        d_ad = (TextView) C_dialog.findViewById(R.id.tv_rj_add);
        d_remarks = (TextView) C_dialog.findViewById(R.id.tv_rj_remarks);

        dt_jn.setText(S_et_jn);
        d_lm.setText(S_et_lm);
        d_ad.setText(S_et_Location);
        d_remarks.setText(S_et_remarks);

        C_confm = (Button) C_dialog.findViewById(R.id.btn_rj_Confm);
        C_cancel = (Button) C_dialog.findViewById(R.id.btn_rj_Cancel);

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
        private final ProgressDialog dialog = new ProgressDialog(AddRoadJunction.this);
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/AddLandmarkJunctions";
            String METHOD_NAME = "AddLandmarkJunctions";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=AddLandmarkJunctions";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username", S_Uname);
            request.addProperty("password", S_Pass);
            request.addProperty("imei", S_IMEi);
            request.addProperty("JunctionName",S_et_jn);
            request.addProperty("Landmark", S_et_lm);
            request.addProperty("latitude", S_latitude);
            request.addProperty("longitude", S_longitude);
            request.addProperty("Address", S_et_Location);
            request.addProperty("Remarks", S_et_remarks);

            envelope.setOutputSoapObject(request);

            Log.e("request",""+request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            Log.e("ADDCRIME_ENVOLOPE",""+envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();

                Log.e("result_addcrime",""+result1);
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
                Toast.makeText(AddRoadJunction.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            }
            else if(httpexcep){
                Toast.makeText(AddRoadJunction.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            }
            else if(genexcep){
                Toast.makeText(AddRoadJunction.this, "Please try later", Toast.LENGTH_LONG).show();
            }
            else{

                Log.e("Code",""+Code);
                if (Code==0) {
                    Log.e("suceess",""+Message);
                    C_dialog.dismiss();
                    et_jn.setText("");
                    et_lm.setText("");
                    et_remarks.setText("");
//                    et_Desriptn.setText("");
//                    et_Crno.setText("");
                    //  Et_yellow.setText("");
                    S_et_jn="";
                    S_et_lm="";
                    S_latitude="";
                    S_longitude="";
                    S_et_Location="";
                    S_et_remarks="";
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddRoadJunction.this);
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
//                    et_Crno.setText("");
                    //  Et_yellow.setText("");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddRoadJunction.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage(""+Message);
                    alertDialog.setIcon(R.drawable.alertt);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                    // Toast.makeText(AddRoadJunction.this,""+Message,Toast.LENGTH_LONG).show();
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
                                    AddRoadJunction.this, 1000);
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
                    et_Location.setText(" "+S_loc);
                    // CurrentLocation.setText(Html.fromHtml(t66+S_loc));
                    // CurrentLocation.setText(S_loc);
                }}
            } catch (IOException e) {

                et_Location.setText(" "+"Couldn't get the location.");
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
