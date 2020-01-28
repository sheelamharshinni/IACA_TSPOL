package com.tecdatum.iaca_tspolice.ViewData.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.ViewData.Adapters.HistorySheetsAdapter;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.HistorySheets_helper;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
import com.tecdatum.iaca_tspolice.activity.MainActivity;
import com.tecdatum.iaca_tspolice.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddHistorySheets_View extends AppCompatActivity {


    HistorySheetsAdapter accidentAdapter;
    ArrayList<HistorySheets_helper> arraylist = new ArrayList<HistorySheets_helper>();
    ListView list_acc;
    TableRow tr_person;
    TextView tv_norecords;
    TableRow r0, r1, r2, r3, r4, r5;

    TextView t1, t2, t3, t4, t5;
    TextView st1, st2, st3, st4, st5;
    TextView dt1, dt2, dt3, dt4, dt5;
    TextView v1, v2, v3, v4, v5, NoRecords;
    Button  btn_acc_submit,btn_acc_reset;
    EditText et_search_activity;

    private String LandmarkMasters = URLS.LandmarkMasters;

    private String urlJsonObj = URLS.AddHistoryData;
    private String HistoryEntry = URLS.HistoryEntry;
    private String HistorySheetListWithFilter =URLS.HistorySheetListWithFilter;
    ProgressDialog progressDialog;
    boolean timeoutexcep=false,httpexcep=false,genexcep=false;
    private static final String TAG = AddHistorySheets_View.class.getSimpleName();
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
    ArrayList<Samplemyclass> countryList = new ArrayList<Samplemyclass>();
    Spinner sp_type;
    ArrayList<String> No_arraylist_ = new ArrayList<String>();
    ArrayList<String> Type_arraylist_ = new ArrayList<String>();
    ArrayList<String> PersonName_arraylist_ = new ArrayList<String>();
    ArrayList<String> AliasName_arraylist_ = new ArrayList<String>();
    ArrayList<String> FatherName_arraylist_ = new ArrayList<String>();
    ArrayList<String> Age_arraylist_ = new ArrayList<String>();
    ArrayList<String> Add_arraylist_ = new ArrayList<String>();
    String No,AliasName,Type,PersonName,FatherName,Age,Add,S_psCode;
    TextView tv_c_psname;

    TextView tv_totalrecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_historysheet);
        Et_no= (EditText) findViewById(R.id.et_hs_no);
        c_time = (TextView) findViewById(R.id.tv_acci_time);
        tv_c_psname = (TextView) findViewById(R.id.tv_accic_psname);
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        S_psCode = bb.getString("Pscode", "");
        s_Ps_name = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        tv_c_psname.setText(s_Ps_name);
        sp_type= (Spinner) findViewById(R.id.sp_accidentType);
        //Sp_stype = (Spinner) findViewById(R.id.sp_detected);
        tv_norecords= (TextView) findViewById(R.id.tv_norecords);
        tr_person = (TableRow) findViewById(R.id.tr_person);
        btn_acc_submit = (Button) findViewById(R.id.btn_acc_submit);
        btn_acc_reset = (Button) findViewById(R.id.btn_acc_reset);
        list_acc = (ListView) findViewById(R.id.list_accident);
        String s_OrgName = bb.getString("OrgName", "");
        TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        tv_OrgName.setText("" + s_OrgName);
        tv_totalrecords= (TextView) findViewById(R.id.tv_totalrecords);


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


        btn_acc_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                S_Et_no=Et_no.getText().toString();

                CheckConnectivity1();
            }
        });


        btn_acc_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i = new Intent(getApplicationContext(),AddHistorySheets_View.class);
                startActivity(i);


            }
        });

        r0 = (TableRow) findViewById(R.id.tr0);
        r1 = (TableRow) findViewById(R.id.tr1);
        r2 = (TableRow) findViewById(R.id.tableRow2);
        r3 = (TableRow) findViewById(R.id.tableRow3);
        r4 = (TableRow) findViewById(R.id.tableRow4);
        r5 = (TableRow) findViewById(R.id.tableRow5);
        NoRecords = (TextView) findViewById(R.id.tv_norecords);

        t1 = (TextView) findViewById(R.id.tv_t1);
        t2 = (TextView) findViewById(R.id.tv_t2);
        t3 = (TextView) findViewById(R.id.tv_t3);
        t4 = (TextView) findViewById(R.id.tv_t4);
        t5 = (TextView) findViewById(R.id.tv_t5);

        st1 = (TextView) findViewById(R.id.tv_p1);
        st2 = (TextView) findViewById(R.id.tv_p2);
        st3 = (TextView) findViewById(R.id.tv_p3);
        st4 = (TextView) findViewById(R.id.tv_p4);
        st5 = (TextView) findViewById(R.id.tv_p5);

        dt1 = (TextView) findViewById(R.id.tv_c1);
        dt2 = (TextView) findViewById(R.id.tv_c2);
        dt3 = (TextView) findViewById(R.id.tv_c3);
        dt4 = (TextView) findViewById(R.id.tv_c4);
        dt5 = (TextView) findViewById(R.id.tv_c5);

        v1 = (TextView) findViewById(R.id.tv_m1);
        v2 = (TextView) findViewById(R.id.tv_m2);
        v3 = (TextView) findViewById(R.id.tv_m3);
        v4 = (TextView) findViewById(R.id.tv_m4);
        v5 = (TextView) findViewById(R.id.tv_m5);
        et_search_activity = (EditText) findViewById(R.id.et_search_activity);
        et_search_activity.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                if( et_search_activity.getText()!=null) {
                    String text = et_search_activity.getText().toString().toLowerCase(Locale.getDefault());
                    try {
                        if (text != null) {
                            accidentAdapter.filter(text);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
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


    private void GetDataFromServer() {
        if(sp_type_id!=null){
        }else {
            sp_type_id="";
        }
        if(S_Et_no!=null){
        }else {
            S_Et_no="";
        }
        progressDialog = new ProgressDialog(AddHistorySheets_View.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        try {
            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");
            S_psid = bb.getString("Psid", "");
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("Number", S_Et_no);
            jsonBody.put("Type", sp_type_name);
            jsonBody.put("PersonName", "");
            jsonBody.put("AliasName", "");
            jsonBody.put("FatherName", "");
            jsonBody.put("Age", "");
            jsonBody.put("FromAge", "");
            jsonBody.put("ToAge", "");
            jsonBody.put("HirarchyID", S_psid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:HistorySheetListWithFilter" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HistorySheetListWithFilter, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:HistorySheetListWithFilter" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {
                            arraylist.clear();
                            progressDialog.dismiss();
                            et_search_activity.setVisibility(View.VISIBLE);
                            tv_norecords.setVisibility(View.GONE);
                            tr_person.setVisibility(View.VISIBLE);
                            list_acc.setVisibility(View.VISIBLE);
                            JSONArray jArray = object.getJSONArray("HsList");
                            int number = jArray.length();
                            tv_totalrecords.setVisibility(View.VISIBLE);

                            tv_totalrecords.setText("Total HistorySheet Records: "+number);
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {


                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);

                                    String   Id = json_data.getString("Id").toString();
                                    String   Number = json_data.getString("Number").toString();
                                    String  PersonName = json_data.getString("PersonName").toString();
                                    String  Type = json_data.getString("Type").toString();
                                    String  AliasName = json_data.getString("AliasName").toString();
                                    String  FatherName = json_data.getString("FatherName").toString();
                                    String   Age = json_data.getString("Age").toString();
                                    String   Add = json_data.getString("Address").toString();
                                    String  Latitude = json_data.getString("Latitude").toString();
                                    String   Longitude = json_data.getString("Longitude").toString();
                                    String   Address = json_data.getString("Address").toString();
                                    String  Psid = json_data.getString("Psid").toString();
                                    String   EnteryDate = json_data.getString("EnteryDate").toString();
                                    String   CreatedBy = json_data.getString("CreatedBy").toString();
                                    HistorySheets_helper wp = new HistorySheets_helper(Id,Number,Type,PersonName,AliasName,FatherName,Age,Latitude,Longitude,Address,Psid,EnteryDate,CreatedBy);

                                    arraylist.add(wp);
                                }
                                // Pass results to ListViewAdapter Class
                                accidentAdapter = new HistorySheetsAdapter(AddHistorySheets_View.this, arraylist);
                                // Binds the Adapter to the ListView
                                list_acc.setAdapter(accidentAdapter);
                                list_acc.setItemsCanFocus(false);
                                list_acc.setTextFilterEnabled(true);
                            }

                        } else {

                            tv_totalrecords.setVisibility(View.GONE);

                            progressDialog.dismiss();
                            arraylist.clear();
                            et_search_activity.setVisibility(View.GONE);
                            tr_person.setVisibility(View.GONE);
                            list_acc.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            tv_norecords.setText(""+message);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddHistorySheets_View.this);
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
                    3760000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)))
        {

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


}
