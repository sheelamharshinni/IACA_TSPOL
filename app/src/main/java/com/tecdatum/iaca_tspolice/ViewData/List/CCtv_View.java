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

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.ViewData.Adapters.CCTVAdapter;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.CCTV_helper;
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

public class CCtv_View extends AppCompatActivity {

    CCTVAdapter accidentAdapter;
    ArrayList<CCTV_helper> arraylist = new ArrayList<CCTV_helper>();
    ListView list_acc;
    TableRow tr_person;
    TextView tv_norecords;
    Button btn_acc_submit, btn_acc_reset;
    ProgressDialog progressDialog;
    EditText et_search_activity;
    TableRow r0, r1, r2, r3, r4, r5;

    TextView t1, t2, t3, t4, t5;
    TextView st1, st2, st3, st4, st5;
    TextView dt1, dt2, dt3, dt4, dt5;
    TextView v1, v2, v3, v4, v5, NoRecords;
    private String cctvMasters = URLS.cctvMasters;
    private String cctvListWithFilter = URLS.cctvListWithFilter;
    private String cctvEntry = URLS.cctvEntry;
    String S_Orgid, HierarchyId;
    String Message1;
    int Code2;
    String Message2;
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = CCtv_View.class.getSimpleName();
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
            Code_new, Message, S_detected_, S_psid, s_role, formattedDate, S_IMEi, S_CrimeNo, S_dateTime, S_location,
            Loc, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_c_psname, c_time;
    Button Bt_c_rest, Bt_c_submit, Bt_c_vl;
    View V;
    EditText cg, pNo, secNo, lm, location;
    String cctvfounding_id,cctvfounding_name;
    Dialog C_dialog, vl_dialog;
    TextView tv_lat, tv_lng;
    Spinner spin_cctvspecificationtype, spin_cctvcapacitytype, spin_cctvfoundingtype,spin_cctvfundstype, spin_pslink, spin_vendor, spin_cctvreason, spin_cctvstatus, spin_cctvtype;
    ArrayList<Samplemyclass> al_vendor = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvreason = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvstatus = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvtype = new ArrayList<>();
    ArrayList<Samplemyclass> al_pslink = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvfunds = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvfouningtype = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvtypespecification = new ArrayList<>();
    // ArrayList<CCTVTypes_WithImages> String_CCTVTypes_WithImages = new ArrayList<>();
    final ArrayList<Samplemyclass> al = new ArrayList<Samplemyclass>();
    Spinner spin_sector;
    Spinner spin_ps;

    ArrayList<Samplemyclass> al_ps = new ArrayList<>();
    String S_cctv_NVRIPAddress, S_cctv_ChannelNo, S_cctv_IpAddress, S_cctv_InchargeName, S_cctv_InchargeNo, S_cctv_Remarks;
    EditText et_cctv_locality, et_cctv_NVRIPAddress, et_cctv_ChannelNo, et_cctv_IpAddress, et_cctv_InchargeName, et_cctv_InchargeNo, et_cctv_Remarks;
    String S_cctv_locality, pslink_id, pslink_name, vendor_id, vendor_name, cctvreason_id, cctvreason_name, cctvstatus_id, cctvstatus_name,
            cctvtype_id, cctvtype_name, cctvfunds_id, cctvfunds_name, cctvcapacity_id, cctvcapacity_name, cctvspecification_id, cctvspecification_name;
    TableRow tr_reasonofntworking;
    RadioGroup rg_specs;
    RadioButton rd_Sector, rd_Locality;
    RelativeLayout rl_reason;
    TextView tv_totalrecords;
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
    String string, string1, S_psCode;
    ArrayList<Samplemyclass> arrayList, arrayList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cctv);
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
        spin_cctvtype = (Spinner) findViewById(R.id.sp_accidentType);
        spin_cctvstatus = (Spinner) findViewById(R.id.sp_detected);
        spin_cctvfundstype = (Spinner) findViewById(R.id.spin_cctvfundstype);
        spin_cctvfoundingtype = (Spinner) findViewById(R.id.spin_cctvfoundingtype);
        tv_norecords = (TextView) findViewById(R.id.tv_norecords);
        tr_person = (TableRow) findViewById(R.id.tr_person);
        btn_acc_submit = (Button) findViewById(R.id.btn_acc_submit);
        btn_acc_reset = (Button) findViewById(R.id.btn_acc_reset);
        list_acc = (ListView) findViewById(R.id.list_accident);
        String s_OrgName = bb.getString("OrgName", "");
        TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        tv_OrgName.setText("" + s_OrgName);
        tv_totalrecords = (TextView) findViewById(R.id.tv_totalrecords);

        spin_ps = (Spinner) findViewById(R.id.spin_ps);

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

                if (spin_ps != null && spin_ps.getSelectedItem() != null) {
                    if (!(spin_ps.getSelectedItem().toString().trim() == "Select PS")) {
                        CheckConnectivity1();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select PS", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select PS", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_acc_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i = new Intent(getApplicationContext(), CCtv_View.class);
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
                                Toast.makeText(CCtv_View.this, " No Data Found", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(CCtv_View.this, " No Data Found", Toast.LENGTH_LONG).show();
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
    private void CCTVFunds(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(CCtv_View.this, R.layout.spinner_item, str1);
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
                        //Toast.makeText(getActivity(), "" + cctvfunds_id, Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }
    private void CCTVFoundingType(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(CCtv_View.this, R.layout.spinner_item, str1);
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


                    }else {
                        cctvtype_id ="";
                        cctvtype_name ="";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
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
                    }else {

                        cctvstatus_id ="";
                        cctvstatus_name ="";
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
                    3530000,
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
                    53530000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setData() {

        if (isConnectingToInternet(CCtv_View.this)) {
            CheckConnectivity2();
        }

    }

    private void CheckConnectivity2() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            getCCTVStatus_Api();
            getCCTVType_Api();
            getPSList_Api();
            getCCTVFunds_Api();
            getCCTVFoundingType_Api();
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
    private void GetDataFromServer() {

        progressDialog = new ProgressDialog(CCtv_View.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        if (cctvtype_id == null) {
            cctvtype_id = "";
        } else {

            if (cctvtype_id.equalsIgnoreCase("0")) {
                cctvtype_id = "";
            } else {


            }
        }

        if (cctvstatus_id == null) {
            cctvstatus_id = "";
        } else {

            if (cctvstatus_id.equalsIgnoreCase("0")) {
                cctvstatus_id = "";
            } else {


            }
        }

        if (S_psid == null) {
            S_psid = "";
        } else {

            if (S_psid.equalsIgnoreCase("0")) {
                S_psid = "";
            } else {


            }
        }
        if (cctvfounding_id == null) {
            cctvfounding_id = "";
        } else {

            if (cctvfounding_id.equalsIgnoreCase("0")) {
                cctvfounding_id = "";
            } else {


            }
        }

        if (cctvfunds_id == null) {
            cctvfunds_id = "";
        } else {

            if (cctvfunds_id.equalsIgnoreCase("0")) {
                cctvfunds_id = "";
            } else {


            }
        }


        try {


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("HirarchyID", S_psid);
            jsonBody.put("CCTVTypemaster_ID", cctvtype_id);
            jsonBody.put("CCtvWorkingStatus_Id", cctvstatus_id);
            jsonBody.put("CommunityGroup_Id", cctvfunds_id);
            jsonBody.put("FundingType_Id", cctvfounding_id);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:GetCrimeData" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvListWithFilter, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:GetCrimeData" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {
                            arraylist.clear();
                            et_search_activity.setVisibility(View.VISIBLE);
                            tv_norecords.setVisibility(View.GONE);
                            tr_person.setVisibility(View.VISIBLE);
                            list_acc.setVisibility(View.VISIBLE);
                            JSONArray jArray = object.getJSONArray("CCTVList");
                            int number = jArray.length();
                            progressDialog.dismiss();
                            tv_totalrecords.setVisibility(View.VISIBLE);

                            tv_totalrecords.setText("Total CCTV Records: " + number);
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String ID = json_data.getString("ID").toString();
                                    String CommunityGroupMasterID = json_data.getString("CommunityGroupMasterID").toString();
                                    String CCTVTypemasterID = json_data.getString("CCTVType").toString();
                                    String Latitude = json_data.getString("Latitude").toString();
                                    String Longitude = json_data.getString("Longitude").toString();
                                    String Policestation = json_data.getString("PsName").toString();
                                    String PsID = json_data.getString("PsID").toString();


                                    String CommunityGroup = json_data.getString("CommunityGroup").toString();
                                    String SectorNo = json_data.getString("SectorNo").toString();
                                    String PoleNo = json_data.getString("PoleNo").toString();
                                    String CCTVWorkingStatusID = json_data.getString("CCTVWorkingStatusID").toString();
                                    //String   LandMark = json_data.getString("LandMark").toString();
                                    String CCTVType = json_data.getString("CCTVType").toString();
                                    String Location = json_data.getString("Location").toString();

                                    String CCTVReasonID = json_data.getString("CCTVReasonID").toString();
                                    String CCTVVendorID = json_data.getString("CCTVVendorID").toString();
                                    String PsConnectionID = json_data.getString("PsConnectionID").toString();
                                    String IPAddress = json_data.getString("IPAddress").toString();
                                    String NVRIPAddress = json_data.getString("NVRIPAddress").toString();
                                    String ChannelNo = json_data.getString("ChannelNo").toString();
                                    String PersonName = json_data.getString("PersonName").toString();
                                    String MobileNo = json_data.getString("MobileNo").toString();
                                    String Remarks = json_data.getString("Remarks").toString();


                                    String EnteredDate = json_data.getString("EnteredDate").toString();
                                    String EnteredBy = json_data.getString("EnteredBy").toString();
                                    //String IsActive = json_data.getString("IsActive").toString();
                                    // String CCTVCategoryID = json_data.getString("CCTVCategoryID").toString();
                                    //String CameraCapacityMasterID = json_data.getString("Specification2").toString();


                                    String CameraSpecificationMasterID = json_data.getString("CameraSpecification").toString();
                                    String Locality = json_data.getString("Locality").toString();
                                    // String GovernmentSectorNo = json_data.getString("GovernmentSectorNo").toString();


                                    String VendorAddress = json_data.getString("VendorAddress").toString();
                                    String VendorMobile = json_data.getString("VendorMobile").toString();
                                    String VendorName = json_data.getString("VendorName").toString();
                                    String CameraFixedDate = json_data.getString("CctvMonth").toString();

                                    String CommunityName = json_data.getString("Cctvyear").toString();
                                    String NoOfCamerasCoverd = json_data.getString("FundType").toString();
                                     String NoCameraInstalled = json_data.getString("FundSource").toString();
                                    String StorageinDays = CameraFixedDate+" & "+CommunityName;
                                    String CCTVCategoryID = json_data.getString("CCTVCategoryID").toString();

                                    CCTV_helper wp = new CCTV_helper(ID, CommunityGroupMasterID, CommunityGroup, CCTVTypemasterID,
                                            CCTVType, "", Location, Latitude, Longitude, PsID, Policestation, SectorNo,
                                            CCTVWorkingStatusID, CCTVReasonID, CCTVVendorID, PsConnectionID, IPAddress,
                                            NVRIPAddress, ChannelNo, PersonName, MobileNo, Remarks,
                                            EnteredDate, EnteredBy, "", ""+CCTVCategoryID, PoleNo,
                                            "", CameraSpecificationMasterID, Locality, "", VendorName, VendorMobile, VendorAddress, "", "", ""+NoOfCamerasCoverd, ""+NoCameraInstalled, ""+StorageinDays);

                                    arraylist.add(wp);

                                }

                                // Pass results to ListViewAdapter Class
                                accidentAdapter = new CCTVAdapter(CCtv_View.this, arraylist);
                                // Binds the Adapter to the ListView
                                list_acc.setAdapter(accidentAdapter);
                                list_acc.setItemsCanFocus(false);
                                list_acc.setTextFilterEnabled(true);
                            }


                            int count = Add_arraylist_.size();
                            Log.e(" count", "" + count);
                            //   SetValuesToLayout();
                        } else {
                            tv_totalrecords.setVisibility(View.GONE);

                            et_search_activity.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            arraylist.clear();
                            tr_person.setVisibility(View.GONE);
                            list_acc.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            tv_norecords.setText("" + message);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CCtv_View.this);
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
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void SetValuesToLayout() {


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
        C_dialog = new Dialog(CCtv_View.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_confm_cctv);
        C_dialog.show();

        TextView d_cg = (TextView) C_dialog.findViewById(R.id.tv_cctv_cg);
        TextView d_pno = (TextView) C_dialog.findViewById(R.id.tv_cctv_pno);
        TextView tv_cctv_workingstatus = (TextView) C_dialog.findViewById(R.id.tv_cctv_workingstatus);
        TextView d_sc = (TextView) C_dialog.findViewById(R.id.tv_cctv_sno);
        TextView d_lm = (TextView) C_dialog.findViewById(R.id.tv_cctv_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_cctv_ln);

        TextView d_fixed = (TextView) C_dialog.findViewById(R.id.tv_cctv_fixed);
        TextView d_ptz = (TextView) C_dialog.findViewById(R.id.tv_cctv_ptz);
        TextView d_ir = (TextView) C_dialog.findViewById(R.id.tv_cctv_ir);
        TextView d_others = (TextView) C_dialog.findViewById(R.id.tv_cctv_others);
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_cctv_pscode);
        TextView tv_cctv_type = (TextView) C_dialog.findViewById(R.id.tv_cctv_type);
        TextView tv_cctv_InchargeName = (TextView) C_dialog.findViewById(R.id.tv_cctv_InchargeName);
        TextView tv_cctv_InchargeNo = (TextView) C_dialog.findViewById(R.id.tv_cctv_InchargeNo);
        TextView tv_cctv_Remarks = (TextView) C_dialog.findViewById(R.id.tv_cctv_Remarks);
        TextView tv_cctv_specification = (TextView) C_dialog.findViewById(R.id.tv_cctv_specification);
        TextView tv_cctv_capacity = (TextView) C_dialog.findViewById(R.id.tv_cctv_capacity);

        if (s_Ps_name != null) {
            pscode.setText(s_Ps_name);
        }
        if (CGp_arraylist_.size() > count) {
            d_cg.setText(CGp_arraylist_.get(count));
        }
        if (PoleNo_arraylist_.size() > count) {
            d_pno.setText(PoleNo_arraylist_.get(count));
        }

        if (status_arraylist_.size() > count) {
            tv_cctv_workingstatus.setText(status_arraylist_.get(count));
        }


        if (SecNo_arraylist_.size() > count) {
            d_sc.setText(SecNo_arraylist_.get(count));
        }
        if (LandMark_arraylist_.size() > count) {
            d_lm.setText(LandMark_arraylist_.get(count));
        }
        if (Add_arraylist_.size() > count) {
            d_ln.setText(Add_arraylist_.get(count));
        }
        if (Fixed_arraylist_.size() > count) {
            d_fixed.setText(Fixed_arraylist_.get(count));
        }
        if (PTZ_arraylist_.size() > count) {
            d_ptz.setText(PTZ_arraylist_.get(count));
        }
        if (IR_arraylist_.size() > count) {
            d_ir.setText(IR_arraylist_.get(count));
        }
        if (Other_arraylist_.size() > count) {
            d_others.setText(Other_arraylist_.get(count));
        }
        if (InchargeName_arraylist_.size() > count) {
            tv_cctv_InchargeName.setText(InchargeName_arraylist_.get(count));
        }
        if (InchargeNo_arraylist_.size() > count) {
            tv_cctv_InchargeNo.setText(InchargeNo_arraylist_.get(count));
        }
        if (Remarks_arraylist_.size() > count) {
            tv_cctv_Remarks.setText(Remarks_arraylist_.get(count));
        }
        if (CCTVType_arraylist_.size() > count) {
            tv_cctv_type.setText(CCTVType_arraylist_.get(count));
        }

        if (CCTVCapacity_arraylist_.size() > count) {
            tv_cctv_capacity.setText(CCTVCapacity_arraylist_.get(count));
        }

        if (Specification_arraylist_.size() > count) {
            tv_cctv_specification.setText(Specification_arraylist_.get(count));
        }

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_cctv_Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });

    }
    private void getPSList_Api() {
        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        S_Orgid = bb.getString("Orgid", "");
        HierarchyId = bb.getString("HierarchyId", "");
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
                        getCCTVFunds_Api();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
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