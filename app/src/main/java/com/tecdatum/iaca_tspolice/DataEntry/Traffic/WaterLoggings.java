package com.tecdatum.iaca_tspolice.DataEntry.Traffic;

import android.Manifest;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.AlertDialog;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.MainActivity;
import com.tecdatum.iaca_tspolice.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by HI on 6/14/2018.
 */


import android.Manifest;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.MainActivity;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by HI on 4/1/2017.
 */

public class WaterLoggings extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    ProgressDialog progressDialog;
    private String TrafficMasters = URLS.TrafficMasters;
    private String WaterLoggingList = URLS.WaterLoggingList;
    private String inserttraffic = URLS.inserttraffic;
    private String[] no_state = {"Complaint"};
    private String[] state = {"Select Status", "Detected", "Undetected"};
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = WaterLoggings.class.getSimpleName();
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
    public static final String MY_PREFS_NAME = "MyPrefsFile";
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
    String item, item2, tv_Ad, tv_Long, tv_Lat, S_Descr, psJuri, S_Uname, S_Pass, s_Ps_name, S_nearest_Landmarkitem,
            S_longitude, S_latitude, S_lm, S_vn, Message, S_detected_, S_psid, s_role, S_IMEi, S_CrimeNo,
            S_dateTime, S_location, Loc, S_vs, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    EditText et_Locality, et_Desriptn, et_lm_nearest_Landmark, et_Landmark;
    Spinner Category, subcat;
    RelativeLayout R_subtype;
    Integer Code1;
    String Message1;
    TextView tv_Trfficdata;

    public WaterLoggings() {
        // Required empty public constructor
    }

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_lm_pplace, container, false);


        Bt_c_vl = (Button) v.findViewById(R.id.btn_lm_pk_vl);
        Bt_c_rest = (Button) v.findViewById(R.id.btn_lm_pk_reset);
        Bt_c_submit = (Button) v.findViewById(R.id.btn_lm_pk_submit);
        et_lm_nearest_Landmark = (EditText) v.findViewById(R.id.et_lm_nearest_Landmark);
        tv_Trfficdata = (TextView) v.findViewById(R.id.tv_Trfficdata);
        et_Landmark = (EditText) v.findViewById(R.id.et_lm_pk_Landmark);
        et_Locality = (EditText) v.findViewById(R.id.et_lm_pk_locality);
        et_Desriptn = (EditText) v.findViewById(R.id.et_lm_pk_Remarks);
        Category = (Spinner) v.findViewById(R.id.spin_lm_pk_it);
        subcat = (Spinner) v.findViewById(R.id.spin_lm_pk_vs);
        c_time = (TextView) v.findViewById(R.id.tv_lm_pk_time);
        tv_c_psname = (TextView) v.findViewById(R.id.tv_lm_pk_psname);
        R_subtype = (RelativeLayout) v.findViewById(R.id.Rl1);

        et_lm_nearest_Landmark.setFilters(new InputFilter[]{filter});
        et_Landmark.setFilters(new InputFilter[]{filter});
        et_Locality.setFilters(new InputFilter[]{filter});
        et_Desriptn.setFilters(new InputFilter[]{filter});

        tv_Trfficdata.setText("Water Loggings");
        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckConnectivity1();
            }
        });
        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_Desriptn.setText("");
                et_Landmark.setText("");
                et_lm_nearest_Landmark.setText("");
                Category.setSelection(0);
                subcat.setSelection(0);

                getActivity().finish();
                Intent i = new Intent(getActivity(), Landmark_act.class);
                startActivity(i);


            }
        });

        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                S_nearest_Landmarkitem = et_lm_nearest_Landmark.getText().toString();
                S_lm = et_Landmark.getText().toString();
                S_Descr = et_Desriptn.getText().toString();
                S_location = et_Locality.getText().toString();

                if (Category != null && Category.getSelectedItem() != null) {
                    if (!(Category.getSelectedItem().toString().trim() == "Select Logging Type")) {
                        if (!(subcat.getSelectedItem().toString().trim() == "Select Logging subtype")) {
                            if (!(et_Locality.getText().toString().trim().isEmpty())) {
                                if (!(et_Desriptn.getText().toString().trim().isEmpty())) {
                                    alertDialogue_Conform();
                                } else {
                                    Toast.makeText(getActivity(), "Please Enter Remarks", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Location is mandatory", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Select Logging subtype", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Select Logging type", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        SharedPreferences bb = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        s_Ps_name = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        tv_c_psname.setText(s_Ps_name);

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

//
//        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
        return v;
    }


    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            AddTrafficDATA();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void CheckConnectivity2() {

        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            getVehicleType_Api();


        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    public void Notnull_Crime() {
        if (S_cSubca == null) {
            S_cSubca = "";
        } else {
        }
        if (S_vn == null) {
            S_vn = "";
        } else {
        }
        if (S_lm == null) {
            S_lm = "";
        } else {
        }
        if (S_location == null) {
            S_location = "";
        } else {
        }
        if (S_Descr == null) {
            S_Descr = "";
        } else {
        }


        if (S_CrimeNo == null) {
            S_CrimeNo = "";
        } else {
        }
        if (Cat1 == null) {
            Cat1 = "";
        } else {
        }
        if (SubCat1 == null) {
            SubCat1 = "";
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
        if (S_dateTime == null) {
            S_dateTime = "";
        } else {
        }
        if (S_location == null) {
            S_location = "";
        } else {
        }
        if (S_detected_ == null) {
            S_detected_ = "";
        } else {
        }


        if (Cat1 != null) {

        } else {
            Cat1 = "";
        }

        if (SubCat1 != null) {
        } else {
            SubCat1 = "";
        }
        if (S_cSubca != null) {
        } else {
            S_cSubca = "";
        }
        if (S_Descr != null) {
        } else {
            S_Descr = "";
        }
        if (S_cSubca != null) {
        } else {
            S_cSubca = "";
        }
    }

    private void AddTrafficDATA() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject jsonBody = new JSONObject();

        try {

            jsonBody.put("PSId", S_psid);
            jsonBody.put("Type", S_vn);
            jsonBody.put("Service", S_cSubca);
            jsonBody.put("Location", S_location);
            jsonBody.put("Remarks", S_Descr);
            jsonBody.put("NoOfVehicles", S_lm);
            jsonBody.put("Latitude", S_latitude);
            jsonBody.put("Longitude", S_longitude);


            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:inserttraffic" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, inserttraffic, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:inserttraffic" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();


                        if (code != null) {
                            if (code.equalsIgnoreCase("1")) {
                                Log.e("suceess", "" + Message);
                                C_dialog.dismiss();
                                et_Desriptn.setText("");
                                et_Landmark.setText("");
                                et_lm_nearest_Landmark.setText("");
                                //  Et_yellow.setText("");
                                Category.setSelection(0);
                                subcat.setSelection(0);
                                S_vn = "";
                                S_cSubca = "";
                                S_location = "";
                                S_Descr = "";
                                S_lm = "";
                                S_latitude = "";
                                S_longitude = "";
                                progressDialog.dismiss();
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
                                progressDialog.dismiss();
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("" + Message);
                                alertDialog.setIcon(R.drawable.alertt);
                                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                                // Toast.makeText(getActivity(),""+Message,Toast.LENGTH_LONG).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
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

    private void getServiceType_Api(String Cat) {
        if (Cat1 != null) {
        } else {
            Cat1 = "";
        }
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "4");
            jsonBody.put("Id", "" + Cat);

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
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Logging subtype");
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
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Logging subtype");
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

    //3-waterlogin type 4- waterlogin type
    private void getVehicleType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "3");
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
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Logging Type");
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
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Logging Type");
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

    private void VehicleData(ArrayList<Samplemyclass> str1) {
        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
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
        ArrayAdapter<Samplemyclass> adapter1 = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, countryList1);
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

    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            GetDataFromServer();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void SetValuesToLayout() {

        vdialog = new Dialog(getActivity(), R.style.HiddenTitleTheme);
        vdialog.setContentView(R.layout.vl_lm_parking);
        vdialog.show();
        Log.e("t", "");
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

        Log.e("t", "");
        if ((Type_arraylist_.size()) > 0 | (Type_arraylist_.size()) == 0) {
            Log.e("checked>0", "");
            if ((Type_arraylist_.size()) == 1) {

                Log.e("checked", "" + Type_arraylist_.size());
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Subtype_arraylist_.get(0));
            }
            if ((Type_arraylist_.size()) == 2) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Subtype_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Subtype_arraylist_.get(1));
            }
            if ((Type_arraylist_.size()) == 3) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);

                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Subtype_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Subtype_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(Subtype_arraylist_.get(2));

            }
            if ((Type_arraylist_.size()) == 4) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Subtype_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Subtype_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(Subtype_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(Subtype_arraylist_.get(3));
            }
            if ((Type_arraylist_.size()) == 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Subtype_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Subtype_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(Subtype_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(Subtype_arraylist_.get(3));
                t5.setText(Type_arraylist_.get(4));
                dt5.setText(Subtype_arraylist_.get(4));
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
                dt1.setText(Subtype_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(Subtype_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(Subtype_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(Subtype_arraylist_.get(3));
                t5.setText(Type_arraylist_.get(4));
                dt5.setText(Subtype_arraylist_.get(4));
            }

        } else {
            Log.e("requ", "");
            r0.setVisibility(View.GONE);
            r1.setVisibility(View.GONE);
            r2.setVisibility(View.GONE);
            r3.setVisibility(View.GONE);
            r4.setVisibility(View.GONE);
            r5.setVisibility(View.GONE);
            NoRecords.setVisibility(View.VISIBLE);
        }

        Log.e("r=u", "");


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
        C_dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_lm_pplace);
        C_dialog.show();

        TextView d_sv = (TextView) C_dialog.findViewById(R.id.tv_lm_v_sv);
        TextView d_vn = (TextView) C_dialog.findViewById(R.id.tv_lm_v_vt);
        TextView d_lm = (TextView) C_dialog.findViewById(R.id.tv_lm_v_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_lm_v_add);
        TextView d_dis = (TextView) C_dialog.findViewById(R.id.tv_lm_v_dis);
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_lm_v_pscode);
        pscode.setText(s_Ps_name);

        if (Type_arraylist_.size() > count) {
            d_vn.setText(Type_arraylist_.get(count));
        }
        if (Subtype_arraylist_.size() > count) {
            d_sv.setText(Subtype_arraylist_.get(count));
        }
        if (ldmk_arraylist_.size() > count) {
            d_lm.setText(ldmk_arraylist_.get(count));
        }
        if (Add_arraylist_.size() > count) {
            d_ln.setText(Add_arraylist_.get(count));
        }
        if (Remarks_arraylist_.size() > count) {
            d_dis.setText(Remarks_arraylist_.get(count));
        }

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_lm_v__Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });

    }

    private void alertDialogue_Conform() {
        C_dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.conform_lm_pplace);
        C_dialog.show();
        TextView d_sv = (TextView) C_dialog.findViewById(R.id.tv_lm_v_sv);
        TextView d_vn = (TextView) C_dialog.findViewById(R.id.tv_lm_v_vt);
        TextView d_lm = (TextView) C_dialog.findViewById(R.id.tv_lm_v_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_lm_v_add);
        TextView d_dis = (TextView) C_dialog.findViewById(R.id.tv_lm_v_dis);
        Notnull_Crime();
        d_sv.setText(S_cSubca);
        d_vn.setText(S_vn);
        d_lm.setText(S_lm);
        d_ln.setText(S_location);
        d_dis.setText(S_Descr);

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_lm_v__Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.btn_lm_v__Cancel);

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

    private void GetDataFromServer() {
        try {
            SharedPreferences bb = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");
            S_psid = bb.getString("Psid", "");


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("HirarchyID", S_psid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:TrafficList" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, WaterLoggingList, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:TrafficList" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("TList");
                            int number = jArray.length();

                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {


                                Type_arraylist_.clear();
                                Subtype_arraylist_.clear();
                                ldmk_arraylist_.clear();
                                Add_arraylist_.clear();
                                Remarks_arraylist_.clear();
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);

                                    Type = json_data.getString("Type").toString();
                                    Subtype = json_data.getString("Service").toString();
                                    ldmk = json_data.getString("NoOfVehicles").toString();
                                    Add = json_data.getString("Location").toString();
                                    Remarks = json_data.getString("Remarks").toString();
                                    if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
                                            .length() <= 0) {
                                        Remarks_arraylist_.add("");
                                    } else {
                                        Remarks_arraylist_.add(Remarks);
                                    }
                                    if (Type == null || Type.trim().equals("anyType{}") || Type.trim()
                                            .length() <= 0) {
                                        Type_arraylist_.add("");
                                    } else {
                                        Type_arraylist_.add(Type);
                                    }
                                    if (Subtype == null || Subtype.trim().equals("anyType{}") || Subtype.trim()
                                            .length() <= 0) {
                                        Subtype_arraylist_.add("");
                                    } else {
                                        Subtype_arraylist_.add(Subtype);
                                    }
                                    if (ldmk == null || ldmk.trim().equals("anyType{}") || ldmk.trim()
                                            .length() <= 0) {
                                        ldmk_arraylist_.add("");
                                    } else {
                                        ldmk_arraylist_.add(ldmk);
                                    }
                                    if (Add == null || Add.trim().equals("anyType{}") || Add.trim()
                                            .length() <= 0) {
                                        Add_arraylist_.add("");
                                    } else {
                                        Add_arraylist_.add(Add);
                                    }
                                }

                            }


                            int count = Type_arraylist_.size();
                            Log.e(" count", "" + count);
                            SetValuesToLayout();
                        } else {

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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

    public synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
                                    getActivity(), 1000);
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
    public void onResume() {
        super.onResume();
        active = true;
    }

    @Override
    public void onPause() {
        Log.w(TAG, "App onPause");

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.w(TAG, "App stopped");

        super.onStop();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        Log.w(TAG, "App destroyed");
        super.onDestroy();
    }

    @Override
    public void onStart() {
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
        try {
            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

            List<Address> addresses;
            try {
                geocoder = new Geocoder(getActivity(), Locale.getDefault());

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
//                    stringBuilder.append(",");
//                    stringBuilder.append(address1);
//                    stringBuilder.append(",");
//                    stringBuilder.append(address2);
                        String S_loc = stringBuilder.toString();
                        String t66 = "<font color='#ebedf5'>ldmk : </font>";
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
            // Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
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
            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
