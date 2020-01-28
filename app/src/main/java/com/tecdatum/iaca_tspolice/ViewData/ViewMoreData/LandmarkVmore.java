package com.tecdatum.iaca_tspolice.ViewData.ViewMoreData;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.ViewData.Adapters.CrimeAdapter;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.Crime_helper;
import com.tecdatum.iaca_tspolice.activity.Dashboard;
import com.tecdatum.iaca_tspolice.activity.MainActivity;
import com.tecdatum.iaca_tspolice.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LandmarkVmore extends AppCompatActivity {

    TextView tv_LandMarkMaster_Type, tv_LandMarkMaster_SubType, tv_NameoWorshipPlace,
            tv_AddressWorshipPlace, tv_Latitude, tv_Longitude, tv_NameoftheIncharge,
            tv_InchargeDesignation, tv_ContactNo, tv_Remarks, tv_PoliceStation,
            tv_sensitivityLevelId, tv_SensitivitySubLevelId;
    Button btn_C_LoadImages;
    private String LandmarkListwithImages = URLS.LandmarkListwithImages;
    String Id;
    ProgressDialog progressDialog;
    LinearLayout ll_images;
    ImageView iv_ivfront, iv_ivback, iv_ivtop, iv_ivside;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_vmore);
        ll_images = (LinearLayout) findViewById(R.id.ll_images);
        iv_ivfront = (ImageView) findViewById(R.id.iv_ivfront);
        iv_ivback = (ImageView) findViewById(R.id.iv_ivback);
        iv_ivside = (ImageView) findViewById(R.id.iv_ivside);
        iv_ivtop = (ImageView) findViewById(R.id.iv_ivtop);

        btn_C_LoadImages = (Button) findViewById(R.id.btn_C_LoadImages);
        tv_LandMarkMaster_Type = (TextView) findViewById(R.id.tv_LandMarkMaster_Type);
        tv_LandMarkMaster_SubType = (TextView) findViewById(R.id.tv_LandMarkMaster_SubType);
        tv_NameoWorshipPlace = (TextView) findViewById(R.id.tv_NameoWorshipPlace);
        tv_AddressWorshipPlace = (TextView) findViewById(R.id.tv_AddressWorshipPlace);
        tv_Latitude = (TextView) findViewById(R.id.tv_Latitude);
        tv_Longitude = (TextView) findViewById(R.id.tv_Longitude);
        tv_NameoftheIncharge = (TextView) findViewById(R.id.tv_NameoftheIncharge);
        tv_InchargeDesignation = (TextView) findViewById(R.id.tv_InchargeDesignation);
        tv_Remarks = (TextView) findViewById(R.id.tv_Remarks);
        tv_ContactNo = (TextView) findViewById(R.id.tv_ContactNo);
        tv_sensitivityLevelId = (TextView) findViewById(R.id.tv_sensitivityLevelId);
        tv_SensitivitySubLevelId = (TextView) findViewById(R.id.tv_SensitivitySubLevelId);
        tv_PoliceStation = (TextView) findViewById(R.id.tv_psname);

        Button C_confm = (Button) findViewById(R.id.btn_C_Confm);


        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Id = getIntent().getStringExtra("Id");
        String LandMarkMaster_Id = getIntent().getStringExtra("LandMarkMaster_Id");
        String LandMarkMaster_Type = getIntent().getStringExtra("LandMarkMaster_Type");
        String LandMarkMaster_SubType = getIntent().getStringExtra("LandMarkMaster_SubType");
        String NameoWorshipPlace = getIntent().getStringExtra("NameoWorshipPlace");
        String AddressWorshipPlace = getIntent().getStringExtra("AddressWorshipPlace");
        String NameoftheIncharge = getIntent().getStringExtra("NameoftheIncharge");
        String InchargeDesignation = getIntent().getStringExtra("InchargeDesignation");
        String Lattitude1 = getIntent().getStringExtra("Lattitude1");
        String Longitude1 = getIntent().getStringExtra("Longitude1");
        String ContactNo = getIntent().getStringExtra("ContactNo");
        String Remarks = getIntent().getStringExtra("Remarks");
        String PSId = getIntent().getStringExtra("PSId");
        String PoliceStation = getIntent().getStringExtra("PoliceStation");
        String sensitivityLevelId = getIntent().getStringExtra("sensitivityLevelId");
        String SensitivitySubLevelId = getIntent().getStringExtra("SensitivitySubLevelId");

        btn_C_LoadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckConnectivity();
            }
        });

        if (LandMarkMaster_Type.equalsIgnoreCase("null")) {

        } else {
            tv_LandMarkMaster_Type.setText("" + LandMarkMaster_Type);

        }

        if (LandMarkMaster_SubType.equalsIgnoreCase("null")) {

        } else {
            tv_LandMarkMaster_SubType.setText("" + LandMarkMaster_SubType);

        }
        if (NameoWorshipPlace.equalsIgnoreCase("null")) {

        } else {
            tv_NameoWorshipPlace.setText("" + NameoWorshipPlace);

        }
        if (AddressWorshipPlace.equalsIgnoreCase("null")) {

        } else {
            tv_AddressWorshipPlace.setText("" + AddressWorshipPlace);

        }
        if (NameoftheIncharge.equalsIgnoreCase("null")) {

        } else {
            tv_NameoftheIncharge.setText("" + NameoftheIncharge);

        }
        if (InchargeDesignation.equalsIgnoreCase("null")) {

        } else {
            tv_InchargeDesignation.setText("" + InchargeDesignation);

        }


        if (Lattitude1.equalsIgnoreCase("null")) {

        } else {
            tv_Latitude.setText("" + Lattitude1);


        }
        if (Longitude1.equalsIgnoreCase("null")) {

        } else {
            tv_Longitude.setText("" + Longitude1);

        }

        if (ContactNo.equalsIgnoreCase("null")) {

        } else {
            tv_ContactNo.setText("" + ContactNo);

        }
        if (Remarks.equalsIgnoreCase("null")) {

        } else {
            tv_Remarks.setText("" + Remarks);

        }
        if (PoliceStation.equalsIgnoreCase("null")) {

        } else {
            tv_PoliceStation.setText("" + PoliceStation);

        }
        if (sensitivityLevelId.equalsIgnoreCase("null")) {

        } else {
            tv_sensitivityLevelId.setText("" + sensitivityLevelId);

        }
        if (SensitivitySubLevelId.equalsIgnoreCase("null")) {

        } else {
            tv_SensitivitySubLevelId.setText("" + SensitivitySubLevelId);

        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), Dashboard.class);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                finish();
            }
        });
    }
    private void CheckConnectivity() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            GetIMagesData();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    private void GetIMagesData() {

        progressDialog = new ProgressDialog(LandmarkVmore.this);
        progressDialog.setMessage("Loading Images, Please Wait...");
        progressDialog.show();
        try {

            if (Id != null) {

            } else {
                Id = "";
            }
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("LandmarkID", "" + Id);


            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:LandmarkListwithImages" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkListwithImages, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "" + "Response:LandmarkListwithImages" + response);
                    try {

                        Log.e("VOLLEY", "Response:GetCrimeData" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {
                            ll_images.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();

                            JSONArray jArray = object.getJSONArray("LandMarkImageList");
                            int number = jArray.length();

                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);


                                    String ImageName1 = json_data.getString("ImageName1").toString();
                                    String ImageName2 = json_data.getString("ImageName2").toString();
                                    String ImageName3 = json_data.getString("ImageName3").toString();
                                    String ImageName4 = json_data.getString("ImageName4").toString();


                                    if (ImageName1 != null) {
                                        if (!(ImageName1.equalsIgnoreCase(""))) {

                                            byte[] decodedString = Base64.decode(ImageName1, Base64.DEFAULT);
                                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                            iv_ivfront.setImageBitmap(decodedByte);
                                        }
                                    }

                                    if (ImageName2 != null) {
                                        if (!(ImageName2.equalsIgnoreCase(""))) {

                                            byte[] decodedString = Base64.decode(ImageName2, Base64.DEFAULT);
                                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                            iv_ivback.setImageBitmap(decodedByte);
                                        }
                                    }
                                    if (ImageName3 != null) {
                                        if (!(ImageName3.equalsIgnoreCase(""))) {

                                            byte[] decodedString = Base64.decode(ImageName3, Base64.DEFAULT);
                                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                            iv_ivside.setImageBitmap(decodedByte);
                                        }
                                    }
                                    if (ImageName4 != null) {
                                        if (!(ImageName4.equalsIgnoreCase(""))) {

                                            byte[] decodedString = Base64.decode(ImageName4, Base64.DEFAULT);
                                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                            iv_ivtop.setImageBitmap(decodedByte);
                                        }
                                    }


                                }


                            }


                        } else {

                            ll_images.setVisibility(View.GONE);
                            progressDialog.dismiss();

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(LandmarkVmore.this);
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

}
