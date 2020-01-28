package com.tecdatum.iaca_tspolice.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;


import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.DataEntry.AddNenuSaithamCCTV;
import com.tecdatum.iaca_tspolice.DataEntry.Traffic.Landmark_act;
import com.tecdatum.iaca_tspolice.DataEntry.cct.OLD_fragments.CCtv_Tabs;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.ViewData.List.AddAccident_View;
import com.tecdatum.iaca_tspolice.ViewData.List.AddCrime_View;
import com.tecdatum.iaca_tspolice.ViewData.List.AddHistorySheets_View;
import com.tecdatum.iaca_tspolice.ViewData.List.AddReligious_View;
import com.tecdatum.iaca_tspolice.ViewData.List.CCtv_View;
import com.tecdatum.iaca_tspolice.ViewData.List.NenuSaithamCCTV_View;
import com.tecdatum.iaca_tspolice.ViewData.List.TrafficView.Traffic_act_ViewTabs;
import com.tecdatum.iaca_tspolice.ViewData.List.Traffic_act_View;
import com.tecdatum.iaca_tspolice.activity.DataEntryView.Dataview;
import com.tecdatum.iaca_tspolice.activity.Settings.ContactUs;
import com.tecdatum.iaca_tspolice.activity.Settings.ResetPassword;

import com.tecdatum.iaca_tspolice.DataEntry.AddAccident;
import com.tecdatum.iaca_tspolice.DataEntry.AddCrime;
import com.tecdatum.iaca_tspolice.DataEntry.AddHistorySheets;
import com.tecdatum.iaca_tspolice.DataEntry.AddHp;
import com.tecdatum.iaca_tspolice.DataEntry.AddPCCTV;
import com.tecdatum.iaca_tspolice.DataEntry.AddReligious;
import com.tecdatum.iaca_tspolice.DataEntry.UpdateCrime_Acc;
import com.tecdatum.iaca_tspolice.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String IACADashboard = URLS.IACADashboard;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ImageView crime, acc, roadj, rel, lmks, hsheets, cctv, iv_nenusaithamcctv, pcctv;
    String S_Uname, S_Orgid, CPControlID, S_LevelId, s_OrgName;
    TextView tv_VersionCode;
    TextView tv_c_Total, tv_c_Murder, tv_c_GenderOffence, tv_c_PropertyOffence, tv_c_SCSTAtrocities, tv_c_SpecialandLocallaws;
    TextView tv_a_Total, tv_a_GrievousInjuries, tv_a_Fatal, tv_a_SimpleInjuries, tv_a_NoInjuries;
    String S_c_Total, S_c_Murder, S_c_GenderOffence, S_c_PropertyOffence, S_c_SCSTAtrocities, S_c_SpecialandLocallaws;
    String S_a_Total, S_a_GrievousInjuries, S_a_Fatal, S_a_SimpleInjuries, S_a_NoInjuries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tv_VersionCode = (TextView) findViewById(R.id.tv_version);

        tv_c_Total = (TextView) findViewById(R.id.tv_c_Total);

        tv_c_Murder = (TextView) findViewById(R.id.tv_c_Murder);
        tv_c_GenderOffence = (TextView) findViewById(R.id.tv_c_GenderOffence);
        tv_c_PropertyOffence = (TextView) findViewById(R.id.tv_c_PropertyOffence);
        tv_c_SCSTAtrocities = (TextView) findViewById(R.id.tv_c_SCSTAtrocities);
        tv_c_SpecialandLocallaws = (TextView) findViewById(R.id.tv_c_SpecialandLocallaws);

        tv_a_Total = (TextView) findViewById(R.id.tv_a_Total);

        tv_a_GrievousInjuries = (TextView) findViewById(R.id.tv_a_GrievousInjuries);
        tv_a_Fatal = (TextView) findViewById(R.id.tv_a_Fatal);
        tv_a_SimpleInjuries = (TextView) findViewById(R.id.tv_a_SimpleInjuries);
        tv_a_NoInjuries = (TextView) findViewById(R.id.tv_a_NoInjuries);

        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        s_OrgName = bb.getString("Orgname", "");
        S_LevelId = bb.getString("LevelId", "");
        CPControlID = bb.getString("CPControlID", "");
        S_Orgid = bb.getString("Orgid", "");
        Log.e("Volley1", "" + S_LevelId);
        TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        tv_OrgName.setText("" + s_OrgName);
//        TextView tv_unsme = (TextView) findViewById(R.id.tv_u_name);
//        tv_unsme.setText("" + S_Uname);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menuNav = navigationView.getMenu();


        if (S_Orgid.equalsIgnoreCase("31")) {
            if (S_LevelId.equalsIgnoreCase("2")) {
                if (CPControlID.equalsIgnoreCase("1")) {
                    MenuItem dataentry = menuNav.findItem(R.id.nav_DataEntry);
                    dataentry.setVisible(true);

                    MenuItem viewrecords = menuNav.findItem(R.id.nav_View);
                    viewrecords.setVisible(true);
                } else {
                    MenuItem dataentry = menuNav.findItem(R.id.nav_DataEntry);
                    dataentry.setVisible(false);


                    MenuItem viewrecords = menuNav.findItem(R.id.nav_View);
                    viewrecords.setVisible(true);
                }


            } else {
                if (S_LevelId.equalsIgnoreCase("5")) {
                    MenuItem dataentry = menuNav.findItem(R.id.nav_DataEntry);
                    dataentry.setVisible(true);

                    MenuItem viewrecords = menuNav.findItem(R.id.nav_View);
                    viewrecords.setVisible(true);


                } else {
                    MenuItem dataentry = menuNav.findItem(R.id.nav_DataEntry);
                    dataentry.setVisible(false);
                    MenuItem viewrecords = menuNav.findItem(R.id.nav_View);
                    viewrecords.setVisible(true);
                }
            }
        } else {
            if (S_LevelId.equalsIgnoreCase("5")) {
                MenuItem dataentry = menuNav.findItem(R.id.nav_DataEntry);
                dataentry.setVisible(true);

                MenuItem viewrecords = menuNav.findItem(R.id.nav_View);
                viewrecords.setVisible(true);


            } else {
                MenuItem dataentry = menuNav.findItem(R.id.nav_DataEntry);
                dataentry.setVisible(false);
                MenuItem viewrecords = menuNav.findItem(R.id.nav_View);
                viewrecords.setVisible(true);
            }
        }


        try {
            tv_VersionCode.setText("Version - " + getVersionCode());


        } catch (Exception e) {
            e.printStackTrace();
        }
        CheckInternet();

    }

    public void CheckInternet() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            GetDataFromServer();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    public String getVersionCode() {
        String v = "";
        try {
            v = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {


            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.clear();
            edit.commit();
            Intent a = new Intent(getApplicationContext(), LoginActivity.class);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        if (id == R.id.nav_View) {
            Intent intent = new Intent(getApplicationContext(), Dataview.class);
            startActivity(intent);
        }
        if (id == R.id.nav_DataEntry) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        if (id == R.id.nav_spatial) {

            Toast.makeText(getApplicationContext(), "Work in Progress", Toast.LENGTH_SHORT).show();

        }
        if (id == R.id.nav_Analytics) {
            Toast.makeText(getApplicationContext(), "Work in Progress", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_logout) {

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.clear();
            edit.commit();
            Intent a = new Intent(getApplicationContext(), LoginActivity.class);
            a.addCategory(Intent.CATEGORY_HOME);
            //  a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(a);
            finish();
        }
        if (id == R.id.nav_resetpassword) {
            startActivity(new Intent(Dashboard.this, ResetPassword.class));
        }

        if (id == R.id.nav_contact) {
            startActivity(new Intent(Dashboard.this, ContactUs.class));
        }

//        if (id == R.id.nav_crime1) {
//            startActivity(new Intent(Dashboard.this, ViewCrime.class));
//        }
//
//        if (id == R.id.nav_Accident1) {
//            startActivity(new Intent(Dashboard.this, ViewAccident.class));
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to exit from this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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

    private void GetDataFromServer() {
        try {
            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String S_HierarchyId = bb.getString("HierarchyId", "");

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("HirarchyID", S_HierarchyId);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:IACADashboard" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, IACADashboard, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", response);
                    try {
                        Log.e("VOLLEY", "Response:IACADashboard" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("IACADASHBOARD");
                            int number = jArray.length();

                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    S_c_Total = json_data.getString("TotalCrimes").toString();
                                    S_c_GenderOffence = json_data.getString("GenderOffence").toString();
                                    S_c_Murder = json_data.getString("Murder").toString();
                                    S_c_PropertyOffence = json_data.getString("PropertyOffence").toString();
                                    S_c_SCSTAtrocities = json_data.getString("SC_ST_Atrocities").toString();
                                    S_c_SpecialandLocallaws = json_data.getString("SpecialAndLocalLaws").toString();
                                    S_a_Total = json_data.getString("TotalAccident").toString();
                                    S_a_SimpleInjuries = json_data.getString("SimpleInjuries").toString();
                                    S_a_GrievousInjuries = json_data.getString("GrievousInjuries").toString();
                                    S_a_Fatal = json_data.getString("FatalInjuries").toString();
                                    S_a_NoInjuries = json_data.getString("NoInjuries").toString();


                                    tv_c_Total.setText("" + S_c_Total);
                                    tv_c_Murder.setText("" + S_c_Murder);
                                    tv_c_GenderOffence.setText("" + S_c_GenderOffence);
                                    tv_c_PropertyOffence.setText("" + S_c_PropertyOffence);
                                    tv_c_SCSTAtrocities.setText("" + S_c_SCSTAtrocities);
                                    tv_c_SpecialandLocallaws.setText("" + S_c_SpecialandLocallaws);

                                    tv_a_Total.setText("" + S_a_Total);
                                    tv_a_GrievousInjuries.setText("" + S_a_GrievousInjuries);
                                    tv_a_Fatal.setText("" + S_a_Fatal);
                                    tv_a_SimpleInjuries.setText("" + S_a_SimpleInjuries);
                                    tv_a_NoInjuries.setText("" + S_a_NoInjuries);
                                }

                            }


                        } else {

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dashboard.this);
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
                    3510000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}