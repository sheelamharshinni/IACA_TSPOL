package com.tecdatum.iaca_tspolice.ViewData.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
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
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.material.tabs.TabLayout;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.DataEntry.lm.OLD_Fragments.ParkingF;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.ViewData.Adapters.TrafficAdapter;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.Traffic_helper;
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


public class Traffic_act_View extends AppCompatActivity {

    private String TrafficMasters = URLS.TrafficMasters;
    private String TrafficListWithFilter = URLS.TrafficListWithFilter;
    private String inserttraffic = URLS.inserttraffic;


    TrafficAdapter accidentAdapter;
    ArrayList<Traffic_helper> arraylist = new ArrayList<Traffic_helper>();

    TableRow r0, r1, r2, r3, r4, r5;
    TextView t1, t2, t3, t4, t5;
    TextView st1, st2, st3, st4, st5;
    TextView dt1, dt2, dt3, dt4, dt5;
    TextView v1, v2, v3, v4, v5, NoRecords;
    private String[] no_state = {"Complaint"};
    private String[] state = {"Select Status", "Detected", "Undetected"};
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = ParkingF.class.getSimpleName();
    private LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x01;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 50;
    private static final int READ_CAMERA_PERMISSIONS_REQUEST = 20;
    private static final int WRITE_SETTINGS_PERMISSION = 20;
    private boolean active = false;
    protected Location mLastLocation;
    Geocoder geocoder;
    private static int UPDATE_INTERVAL = 60000;
    private boolean mRequestingLocationUpdates = true;
    Dialog C_dialog, vdialog;
    int Code;
    TextView c_time, tv_c_psname;

    String formattedDate;
    ArrayList<String> Cnumber_arraylist_ = new ArrayList<String>();
    ArrayList<String> Remarks_arraylist_ = new ArrayList<String>();
    ArrayList<String> Type_arraylist_ = new ArrayList<String>();
    ArrayList<String> Subtype_arraylist_ = new ArrayList<String>();
    ArrayList<String> ldmk_arraylist_ = new ArrayList<String>();
    ArrayList<String> Add_arraylist_ = new ArrayList<String>();
    String Cnumber, Pscode, Type, Subtype, ldmk, Add, Remarks;
    ArrayList<Samplemyclass> countryList = new ArrayList<Samplemyclass>();
    ArrayList<Samplemyclass> countryList1 = new ArrayList<Samplemyclass>();

    Button Bt_c_date, Bt_cr_time, Bt_c_vl, Bt_c_rest, Bt_c_submit;
    String item, item2, tv_Ad, tv_Long, tv_Lat, S_Descr, psJuri, S_Uname, S_Pass, S_psCode, s_Ps_name, S_nearest_Landmarkitem, S_longitude, S_latitude,
            S_lm, S_vn, Message, S_detected_, S_psid, s_role, S_IMEi, S_CrimeNo, S_dateTime, S_location, Loc, S_vs, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    EditText et_Locality, et_Desriptn, et_lm_nearest_Landmark, et_Landmark;
    Spinner subcat;
    EditText et_search_activity;
    Button btn_acc_submit, btn_acc_reset;
    TableRow tr_person;
    TextView tv_norecords;
    ListView list_acc;
    private Spinner Category, SCategory, detected_;
    RelativeLayout R_subtype;
    Integer Code1;
    String Message1;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ProgressDialog progressDialog;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_totalrecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_traffic);
        R_subtype = (RelativeLayout) findViewById(R.id.R_subtype);
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

        Category = (Spinner) findViewById(R.id.sp_accidentType);
        subcat = (Spinner) findViewById(R.id.sp_detected);
        tv_totalrecords = (TextView) findViewById(R.id.tv_totalrecords);

        tv_norecords = (TextView) findViewById(R.id.tv_norecords);
        tr_person = (TableRow) findViewById(R.id.tr_person);
        btn_acc_submit = (Button) findViewById(R.id.btn_acc_submit);
        btn_acc_reset = (Button) findViewById(R.id.btn_acc_reset);
        list_acc = (ListView) findViewById(R.id.list_accident);
        String s_OrgName = bb.getString("OrgName", "");
        TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        tv_OrgName.setText("" + s_OrgName);


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
                CheckConnectivity1();
            }
        });
        btn_acc_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i = new Intent(getApplicationContext(), Traffic_act_View.class);
                startActivity(i);


            }
        });
        et_search_activity = (EditText) findViewById(R.id.et_search_activity);
        et_search_activity.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                if (et_search_activity.getText() != null) {
                    String text = et_search_activity.getText().toString().toLowerCase(Locale.getDefault());
                    try {
                        if (text != null) {
                            accidentAdapter.filter(text);
                        }
                    } catch (Exception e) {
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

    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

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

    private void CheckConnectivity2() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            getVehicleType_Api();


        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void VehicleData(ArrayList<Samplemyclass> str1) {
        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(Traffic_act_View.this, R.layout.spinner_item, str1);
        Category.setAdapter(adapter);
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
//                if(view!=null) {
//                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
//                    ((TextView) parent.getChildAt(0)).setTextSize(12);
//                }
                if (pos != 0) {
                    SubCat1 = "";
                    R_subtype.setVisibility(View.VISIBLE);
                    //  ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    Cat1 = country.getId();
                    S_vn = country.getName();
                    Log.e("Cat1", "" + Cat1);
                    Log.e("Cat", "" + S_vn);

                    getServiceType_Api(Cat1);
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void ServiceData(ArrayList<Samplemyclass> str1) {
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(Traffic_act_View.this, R.layout.spinner_item, countryList1);
        subcat.setAdapter(adapter1);
        subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
//                if(view!=null) {
//                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
//                    ((TextView) parent.getChildAt(0)).setTextSize(12);
//                }
                if (pos != 0) {
                    //  ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    SubCat1 = country.getId();
                    S_cSubca = country.getName();
                    Log.e("Cat1", "" + SubCat1);
                    Log.e("Cat", "" + S_cSubca);
                    // Toast.makeText(getActivity(), "" + Cat1, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getActivity(), "Crime Type is Mandatory ", Toast.LENGTH_SHORT).show();
                } else {
//                    if(view!=null) {
//                        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
//                        ((TextView) parent.getChildAt(0)).setTextSize(12);
//                    }
                    //Toast.makeText(getActivity(),"Select Vehicle Service ", Toast.LENGTH_SHORT).show();
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

    private void getServiceType_Api(String str1) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "1");
            jsonBody.put("Id", "" + str1);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Crime_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, TrafficMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Crime_Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        countryList1.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Vehicle Service Type");
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
                                    ServiceData(countryList1);
                                }
                            }


                        } else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Vehicle Service Type");
                            // Binds all strings into an array
                            countryList1.add(wp0);
                            if (countryList1.size() > 0) {
                                ServiceData(countryList1);
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

    private void getVehicleType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "2");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Crime_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, TrafficMasters, new Response.Listener<String>() {
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

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Vehicle Type");
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
                                    VehicleData(countryList);
                                }
                            }


                        } else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Vehicle Type");
                            // Binds all strings into an array
                            countryList.add(wp0);
                            if (countryList.size() > 0) {
                                VehicleData(countryList);
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

    private void GetDataFromServer() {

        progressDialog = new ProgressDialog(Traffic_act_View.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();

        if (S_vn != null) {
        } else {
            S_vn = "";
        }
        if (S_cSubca != null) {
        } else {
            S_cSubca = "";
        }
        try {
            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");
            S_psid = bb.getString("Psid", "");
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("TrafficType", S_vn);
            jsonBody.put("ServiceType", S_cSubca);
            jsonBody.put("HirarchyID", S_psid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:TrafficListWithFilter" + mRequestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, TrafficListWithFilter, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:TrafficListWithFilter" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {
                            arraylist.clear();
                            et_search_activity.setVisibility(View.VISIBLE);
                            tv_norecords.setVisibility(View.GONE);
                            tr_person.setVisibility(View.VISIBLE);

                            list_acc.setVisibility(View.VISIBLE);
                            JSONArray jArray = object.getJSONArray("TList");
                            int number = jArray.length();
                            progressDialog.dismiss();
                            String num = Integer.toString(number);
                            tv_totalrecords.setVisibility(View.VISIBLE);

                            tv_totalrecords.setText("Total Traffic Records : " + number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {


                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);

                                    String Location = json_data.getString("Location").toString();
                                    String Service = json_data.getString("Service").toString();
                                    String PS = json_data.getString("PS").toString();
                                    String PSId = json_data.getString("PSId").toString();
                                    String Id = json_data.getString("Id").toString();
                                    String Type = json_data.getString("Type").toString();
                                    String Longitude = json_data.getString("Longitude").toString();
                                    String Latitude = json_data.getString("Latitude").toString();
                                    String NoOfVehicles = json_data.getString("NoOfVehicles").toString();
                                    String Remarks = json_data.getString("Remarks").toString();


                                    Traffic_helper wp = new Traffic_helper(Id, PSId, PS, Type, Service, Location, Remarks, NoOfVehicles, Latitude, Longitude);

                                    arraylist.add(wp);

                                }


                                // Pass results to ListViewAdapter Class
                                accidentAdapter = new TrafficAdapter(Traffic_act_View.this, arraylist);
                                // Binds the Adapter to the ListView
                                list_acc.setAdapter(accidentAdapter);
                                list_acc.setItemsCanFocus(false);
                                list_acc.setTextFilterEnabled(true);
                            }

                        } else {

                            tv_totalrecords.setVisibility(View.GONE);

                            progressDialog.dismiss();
                            arraylist.clear();
                            tr_person.setVisibility(View.GONE);
                            list_acc.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            tv_norecords.setText("" + message);
                            et_search_activity.setVisibility(View.GONE);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Traffic_act_View.this);
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
                    4530000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}