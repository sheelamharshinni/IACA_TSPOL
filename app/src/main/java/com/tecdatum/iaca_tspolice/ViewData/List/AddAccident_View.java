package com.tecdatum.iaca_tspolice.ViewData.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.DataEntry.AddCrime;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.ViewData.Adapters.AccidentAdapter;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.Accident_helper;
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

public class AddAccident_View extends AppCompatActivity {
    private String Crime_Master = URLS.CrimeMasters;
    ArrayList<Samplemyclass> countryList1= new ArrayList<Samplemyclass>();
   Spinner spin_year;
    String    yearid,yearName;

    AccidentAdapter accidentAdapter;
    ArrayList<Accident_helper> arraylist = new ArrayList<Accident_helper>();
    ListView list_acc;
    TableRow tr_person;
    TextView tv_norecords;
    Button btn_acc_submit, btn_acc_reset;
    private String AccidentMasters = URLS.AccidentMasters;
    private String AddAccident = URLS.AddAccident;
    private String AccidentListWithFilter = URLS.AccidentListWithFilter;
    ArrayList<Samplemyclass> cl3 = new ArrayList<>();
    ArrayList<Samplemyclass> cl4 = new ArrayList<>();
    ArrayList<Samplemyclass> d1 = new ArrayList<>();
    ArrayList<Samplemyclass> d2 = new ArrayList<>();
    ArrayList<Samplemyclass> d3 = new ArrayList<>();
    ArrayList<Samplemyclass> d4 = new ArrayList<>();
    ArrayList<Samplemyclass> d5 = new ArrayList<>();
    ArrayAdapter<Samplemyclass> adapter_state;
    private static final String TAG = AddAccident_View.class.getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    CustomDateTimePicker custom0, custom1;
    String Sat, Srt, Ssvc, Ssvs, Ssac, Ssas;
    private Spinner at, rt, svc, svs, sac, sas, detected_;
    String Sat1, Srt1, Svc1, Svs1, Sac1, Sas1;
    RelativeLayout RL1, RL2;
    int Code;
    String Code_new;


    ProgressDialog progressDialog;
    EditText et_discription, et_roadNo, et_vap, et_aap, et_locality, et_c_year, et_Cno, et_Cla, et_Noi, et_Nod;
    Button Bt_c_rest, Bt_c_dateReport, Bt_c_submit, Bt_c_vl;
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    String S_dateTime1, S_discription, S_roadNo, S_vap, S_aap, S_c_Year, S_locality, S_c_year, S_Cno, S_Cla, S_Noi, S_Nod, S_latitude, S_longitude, S_dateTime, Message,
            s_Ps_name, S_Uname, S_Pass, S_psCode, S_psid, s_role, S_IMEi, S_detected_id, S_detected_;
    Button btn_accid_offline, Bt_acc_date;
    TextView c_time, tv_c_psname;
    String formattedDate, e_id, e_n;
    Dialog C_dialog, vdialog;
    Integer Code1;
    String Message1;
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
    String AccidentENo, Cdate, Cnumber, Pscode, Type, Subtype, Location, EnteredDate, CStatus, RoadType, Descr, Road_Number, NOofIn, NOofD;
    ArrayList<String> AccidentENo_arraylist_ = new ArrayList<String>();
    ArrayList<String> Cnumber_arraylist_ = new ArrayList<String>();
    ArrayList<String> Pscode_arraylist_ = new ArrayList<String>();
    ArrayList<String> Cdate_arraylist_ = new ArrayList<String>();
    ArrayList<String> Type_arraylist_ = new ArrayList<String>();
    ArrayList<String> Location_arraylist_ = new ArrayList<String>();
    ArrayList<String> EnteredDate_arraylist_ = new ArrayList<String>();
    ArrayList<String> CStatus_arraylist_ = new ArrayList<String>();
    ArrayList<String> RoadType_arraylist_ = new ArrayList<String>();
    ArrayList<String> Descr_arraylist_ = new ArrayList<String>();
    ArrayList<String> Road_Number_arraylist_ = new ArrayList<String>();
    ArrayList<String> NOofIn_arraylist_ = new ArrayList<String>();
    ArrayList<String> NOofD_arraylist_ = new ArrayList<String>();


    EditText et_search_activity;
    TextView tv_totalrecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accident);
        spin_year = (Spinner) findViewById(R.id.spin_Year);
        tv_totalrecords = (TextView) findViewById(R.id.tv_totalrecords);
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
        at = (Spinner) findViewById(R.id.sp_accidentType);
        detected_ = (Spinner) findViewById(R.id.sp_detected);
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
                Intent i = new Intent(getApplicationContext(), AddAccident_View.class);
                startActivity(i);


            }
        });
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

    private void getYear_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "4");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Crime_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Crime_Master, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Crime_Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        countryList1.clear();
                        if (code.equalsIgnoreCase("1")){

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Year");
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
                                    YearMaster(countryList1);
                                }
                            }



                        }else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Crime Type");
                            // Binds all strings into an array
                            countryList1.add(wp0);
                            if (countryList1.size() > 0) {
                                YearMaster(countryList1);
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
    private void YearMaster(ArrayList<Samplemyclass>Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        if (adapter != null) {


            spin_year.setAdapter(adapter);
//
//            // textView.setSelection(0);
//            spin_year.setTitle("Select Crime No");
//            // textView.setTitle();
//            spin_year.setPositiveButton("OK");
//            spin_year.setOnSearchTextChangedListener(new SearchableListDialog.OnSearchTextChanged() {
//                @Override
//                public void onSearchTextChanged(String strText) {
//                    // Toast.makeText(getApplicationContext(), strText + " selected", Toast.LENGTH_SHORT).show();
//                }
//            });
            spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {
                        //  Samplemyclass list = (Samplemyclass) SCategory.getSelectedItem();
                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                        yearid = country.getId();
                        yearName = country.getName();

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

    private void AccidentType(ArrayList<Samplemyclass> Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        at.setAdapter(adapter);
        at.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                int pos = parent.getSelectedItemPosition();
//                if (pos != 0) {
                Samplemyclass list = (Samplemyclass) at.getSelectedItem();
                Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                Sat1 = country.getName();
                Sat = country.getId();
                // Toast.makeText(AddAccident_View.this, " acc type"+Sat1, Toast.LENGTH_SHORT).show();
                // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void CrimeStatus(ArrayList<Samplemyclass> str1) {


        adapter_state = new ArrayAdapter<Samplemyclass>(getApplicationContext(),
                R.layout.spinner_item, d3);


        detected_.setAdapter(adapter_state);
        detected_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                //   if (pos != 0) {
                S_detected_ = "";
                Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                Ssvc = country.getId();
                S_detected_id = country.getId();
                S_detected_ = country.getName();
                detected_.setSelection(position);
                //  S_detected_ = (String) detected_.getSelectedItem();
                Log.e("crime_Genianty", "" + S_detected_);
                // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    private void getAccidentType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "1");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:AccidentMasters" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AccidentMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:AccidentMasters" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        d1.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb1 = new Samplemyclass("0", "Select Accident Type");
                                // Binds all strings into an array
                                d1.add(wb1);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    d1.add(wp);
                                }
                                if (d1.size() > 0) {
                                    AccidentType(d1);
                                }
                            }


                        } else {
                            Samplemyclass wb1 = new Samplemyclass("0", "Select Accident Type");
                            // Binds all strings into an array
                            d1.add(wb1);
                            if (d1.size() > 0) {
                                AccidentType(d1);

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
    private void getCrimeStatus_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "8");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:CrimeStatus_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Crime_Master, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:CrimeStatus_Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        d3.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Status");
                                // Binds all strings into an array
                                d3.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    d3.add(wp);
                                }
                                if (d3.size() > 0) {
                                    CrimeStatus(d3);
                                }
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
    public void NotNull() {


        if (Sas1 == null) {
            Sas1 = "";
        } else {
        }

        if (Svs1 == null) {
            Svs1 = "";
        } else {
        }


        if (Sat1 == null) {
            Sat1 = "";
        } else {
        }

        if (Srt1 == null) {
            Srt1 = "";
        } else {
        }
        if (Svc1 == null) {
            Svc1 = "";
        } else {
        }

        if (Sac1 == null) {
            Sac1 = "";
        } else {
        }

        if (S_Cla == null) {
            S_Cla = "";
        } else {
        }

        if (S_Cno == null) {
            S_Cno = "";
        } else {
        }
        if (Sat1 == null) {
            Sat1 = "";
        } else {
        }
        if (S_dateTime == null) {
            S_dateTime = "";
        } else {
        }
        if (S_locality == null) {
            S_locality = "";
        } else {
        }

        if (S_longitude == null) {
            S_longitude = "";
        } else {
        }
        if (S_latitude == null) {
            S_latitude = "";
        } else {
        }

        if (Srt == null) {
            Srt = "";
        } else {
        }
        if (S_roadNo == null) {
            S_roadNo = "";
        } else {
        }
        if (S_detected_ == null) {
            S_detected_ = "";
        } else {
        }
        if (S_discription == null) {
            S_discription = "";
        } else {
        }
        if (S_Noi == null) {
            S_Noi = "";
        } else {
        }
        if (S_Nod == null) {
            S_Nod = "";
        } else {
        }
        if (Ssvc == null) {
            Ssvc = "";
        } else {
        }
        if (Ssvs == null) {
            Ssvs = "";
        } else {
        }

        if (S_vap == null) {
            S_vap = "";
        } else {
        }
        if (Ssac == null) {
            Ssac = "";
        } else {
        }
        if (Sat == null) {
            Sat = "";
        } else {
            if (Sat.equalsIgnoreCase("0")) {
                Sat = "";
            } else {


            }
        }
        if (S_detected_id == null) {
            S_detected_id = "";
        } else {

            if (S_detected_id.equalsIgnoreCase("0")) {
                S_detected_id = "";
            } else {


            }
        }
        if (S_detected_id == null) {
            S_detected_id = "";
        } else {

            if (S_detected_id.equalsIgnoreCase("0")) {
                S_detected_id = "";
            } else {


            }
        }
        if (yearName == null) {
            yearName = "";
        } else {

            if (yearName.equalsIgnoreCase("0")) {
                yearName = "";
            } else {


            }
        }

    }

    private void GetDataFromServer() {
        progressDialog = new ProgressDialog(AddAccident_View.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        NotNull();
        try {

            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");
            S_psid = bb.getString("Psid", "");


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("HirarchyID", S_psid);

            jsonBody.put("Crimes_Id", "");
            jsonBody.put("CrimeTypeMaster_Id", "5");
            jsonBody.put("CrimeSubtypeMaster_Id", "" + Sat);
            jsonBody.put("CrimeStatusMaster_Id", "" + S_detected_id);
            jsonBody.put("CrimeYear", "" + yearName);



            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:GetCrimeData" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AccidentListWithFilter, new Response.Listener<String>() {
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
                            progressDialog.dismiss();

                            et_search_activity.setVisibility(View.VISIBLE);
                            tv_norecords.setVisibility(View.GONE);
                            tr_person.setVisibility(View.VISIBLE);
                            list_acc.setVisibility(View.VISIBLE);
                            JSONArray jArray = object.getJSONArray("AccList");
                            int number = jArray.length();
                            tv_totalrecords.setVisibility(View.VISIBLE);
                            tv_totalrecords.setText("Total Accidents Records : "+number);
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String Id = json_data.getString("Id").toString();

                                    String CrimeNumber = json_data.getString("CrimeNumber").toString();
                                    String PSID = json_data.getString("PSID").toString();
                                    // Type = json_data.getString("CrimeTypeMaster_Id").toString();
                                    String PSCode = json_data.getString("PSCode").toString();
                                    String CrimeTypeMaster_Id = json_data.getString("CrimeTypeMaster_Id").toString();
                                    String CrimeType = json_data.getString("CrimeType").toString();
                                    String CrimeSubtypeMaster_Id = json_data.getString("CrimeSubtypeMaster_Id").toString();
                                    String CrimeSubtype = json_data.getString("CrimeSubtype").toString();

                                    String Latitude = json_data.getString("Latitude").toString();
                                    String Longitude = json_data.getString("Longitude").toString();
                                    String Location = json_data.getString("Location").toString();
                                    String Descr = json_data.getString("Descr").toString();


                                    String DateOfOffence = json_data.getString("DateOfOffence").toString();
                                    String DateOfEntry = json_data.getString("DateOfEntry").toString();

                                    String CrimeStatusMaster_Id = json_data.getString("CrimeStatusMaster_Id").toString();
                                    String DateofReport = json_data.getString("DateofReport").toString();
                                    String NoofInjuries = json_data.getString("NoofInjuries").toString();
                                    String NoofDeaths = json_data.getString("NoofDeaths").toString();


                                    String RoadTypeID = json_data.getString("RoadTypeID").toString();
                                    String RoadNumber = json_data.getString("RoadNumber").toString();
                                    String VictimCategoryID = json_data.getString("VictimCategoryID").toString();
                                    String VictimAlcoholicORNot = json_data.getString("VictimAlcoholicORNot").toString();


                                    String VictimVehicleNo = json_data.getString("VictimVehicleNo").toString();
                                    String AccusedCategoryID = json_data.getString("AccusedCategoryID").toString();

                                    String AccusedAlcoholicORNot = json_data.getString("AccusedAlcoholicORNot").toString();
                                    String AccusedVehicleNo = json_data.getString("AccusedVehicleNo").toString();


                                    Accident_helper wp = new Accident_helper(Id, CrimeNumber, PSID, PSCode, CrimeTypeMaster_Id,
                                            CrimeType, CrimeSubtypeMaster_Id, CrimeSubtype, Latitude, Longitude, Location, Descr,
                                            DateOfOffence, DateOfEntry, CrimeStatusMaster_Id, DateofReport, NoofInjuries, NoofDeaths,
                                            RoadTypeID, RoadNumber, VictimCategoryID, VictimAlcoholicORNot, VictimVehicleNo,
                                            AccusedCategoryID, AccusedAlcoholicORNot, AccusedVehicleNo);

                                    arraylist.add(wp);

                                }


                                // Pass results to ListViewAdapter Class
                                accidentAdapter = new AccidentAdapter(AddAccident_View.this, arraylist);
                                // Binds the Adapter to the ListView
                                list_acc.setAdapter(accidentAdapter);
                                list_acc.setItemsCanFocus(false);
                                list_acc.setTextFilterEnabled(true);
                            }

                        } else {
                            tv_totalrecords.setVisibility(View.INVISIBLE);

                            et_search_activity.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            arraylist.clear();
                            tr_person.setVisibility(View.GONE);
                            list_acc.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            tv_norecords.setText("" + message);

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddAccident_View.this);
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
                    130232000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setData() {

        if (isConnectingToInternet(AddAccident_View.this)) {
            CheckConnectivity2();
        }

    }
    private void CheckConnectivity2() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            getCrimeStatus_Api();
            getAccidentType_Api();
            getYear_Api();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
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

}
