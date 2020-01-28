package com.tecdatum.iaca_tspolice.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.R;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class LoginActivity extends AppCompatActivity {

    private String LoginCheck = URLS.LoginCheck;
    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    String VersionCode, S_Uname, S_Paswd, Psname, UserName, OrgName, Role, Message, IMEINO;
    String Code;
    String res, psJurid, Orgname, message, Orgid, Branchid, Userid, RoleType, Username, Psid, IMEI_Number;
    EditText Uname, Paswd;
    Button login;
    CheckBox cb_savedetails;
    ImageView alert, pv;
    TextView tv_VersionCode, error_message;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_VersionCode = (TextView) findViewById(R.id.tv_VersionCode);
        Uname = (EditText) findViewById(R.id.et_user_id);
        Paswd = (EditText) findViewById(R.id.et_password);
        cb_savedetails = (CheckBox) findViewById(R.id.cb_saveLoginCheckBox);
        alert = (ImageView) findViewById(R.id.alert_);
        //pv=(ImageView)findViewById(R.id.iv_visiblePassword);
        error_message = (TextView) findViewById(R.id.tv_error_tr);
        Button b = (Button) findViewById(R.id.btn_log_in);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            Uname.setText(loginPreferences.getString("username", ""));
            Paswd.setText(loginPreferences.getString("password", ""));
            cb_savedetails.setChecked(true);
        }

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

//                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(Uname.getWindowToken(), 0);
//                    TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//                    IMEI_Number = telephonyManager.getDeviceId();

                    //                 IMEINO = IMEI_Number.toString();
                    Log.e(TAG, "IMEINO" + IMEINO);
                    S_Uname = Uname.getText().toString();
                    S_Paswd = Paswd.getText().toString();
                    if (cb_savedetails.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", S_Uname);
                        loginPrefsEditor.putString("password", S_Paswd);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                    // new pay().execute();

                    getDataFromServer();
                } else if (connec != null && (
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                                (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

                    FragmentManager fm = getSupportFragmentManager();
                    Connectivity td = new Connectivity();
                    td.show(fm, "NO CONNECTION");
                }
            }
        });
        Paswd.setTransformationMethod(PasswordTransformationMethod.getInstance());

//        if (getVersionCode() == null) {
//
//        } else {
//
//        }
        try {
            tv_VersionCode.setText("Version - " + getVersionName());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getVersionName() {
        String v = "";
        try {
            v = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return v;
    }

    public int getVersionCode() {
        int v = 0;
        try {
            v = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return v;
    }

    public void getDataFromServer() {

        try {
            VersionCode = String.valueOf(getVersionCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("UserId", S_Uname);
            obj.put("Password", S_Paswd);
            obj.put("IMEI", "");
            obj.put("Version", "" + VersionCode);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = obj.toString();
        Log.e("VOLLEY", "Login" + "Input" + mRequestBody);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                LoginCheck, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "Login" + "Output" + response.toString());

                Code = response.optString("code").toString();
                Message = response.optString("Message").toString();


                if (Code.equalsIgnoreCase("1")) {

                    Orgname = response.optString("OrgName").toString();
                    Orgid = response.optString("OrgId").toString();
                    Userid = response.optString("UserID").toString();
                    Username = response.optString("UserName").toString();
                    String LevelId = response.optString("LevelId").toString();
                    String LevelName = response.optString("LevelName").toString();
                    String HierarchyName = response.optString("HierarchyName").toString();
                    String HierarchyId = response.optString("HierarchyId").toString();
                    String UserType = response.optString("UserType").toString();
                    String Pscode = response.optString("Pscode").toString();
                    String CPControlID = response.optString("CPControlID").toString();
                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor edit = prefs.edit();

                    edit.putString("UserName", Userid);
                    edit.putString("PassWord", S_Paswd);
                    edit.putString("IMEI", IMEINO);
                    edit.putString("Orgname", Orgname);
                    edit.putString("Orgid", Orgid);
                    edit.putString("Userid", Userid);
                    edit.putString("Username", Username);
                    edit.putString("CPControlID", CPControlID);
                    edit.putString("LevelId", LevelId);
                    edit.putString("LevelName", LevelName);

                    edit.putString("Psid", HierarchyId);
                    edit.putString("Psname", HierarchyName);
                    edit.putString("HierarchyName", HierarchyName);
                    edit.putString("HierarchyId", HierarchyId);
                    edit.putString("UserType", UserType);
                    edit.putString("Pscode", Pscode);
                    edit.commit();
                    Log.e("Orgname", "" + Orgname);
                    Toast.makeText(LoginActivity.this, "Logged in Succesfully", Toast.LENGTH_LONG).show();
                    alert.setVisibility(View.GONE);
                    error_message.setText("");
                    Intent i = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, Message, Toast.LENGTH_SHORT).show();
                    alert.setVisibility(View.VISIBLE);
                    error_message.setVisibility(View.VISIBLE);
                    error_message.setText(Message);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                alert.setVisibility(View.VISIBLE);
                error_message.setVisibility(View.VISIBLE);
                error_message.setText("Unable to Connect to Server");
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                3510000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);
    }

}