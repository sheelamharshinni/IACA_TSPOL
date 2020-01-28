package com.tecdatum.iaca_tspolice.Offline.Crime_Offline_Actvities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.DataBase.DbHandlerCrime;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.MainActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Crime_OfflineActvity extends AppCompatActivity {

    private static final String TAG = Crime_OfflineActvity.class.getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    TextView d_cno, d_ct, d_sct, d_dt, d_ad, d_ds, d_dis;
    Button tv_crime_edit,tv_crime_del,C_cancel, C_confm;
    String S_id;
    String item, item2, tv_Ad, tv_Long, tv_Lat, S_Descr, psJuri, S_Uname, S_Pass, s_Ps_name, S_longitude, S_latitude,
            Message, Message1, S_detected_id, S_detected_, S_psid, s_role, S_IMEi, S_CrimeNo, S_dateTime, S_location, Loc, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    int Code;
    Integer Code1;

    // for Db
    DbHandlerCrime dbHandler;
    SQLiteDatabase database;
    SQLiteStatement statement;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_offline_actvity);


        d_cno = (TextView) findViewById(R.id.tv_c_cno);
        d_ct = (TextView) findViewById(R.id.tv_c_ct);
        d_sct = (TextView) findViewById(R.id.tv_c_sct);
        d_dt = (TextView) findViewById(R.id.tv_c_dt);
        d_ad = (TextView) findViewById(R.id.tv_c_add);
        d_ds = (TextView) findViewById(R.id.tv_c_ds);
        d_dis = (TextView) findViewById(R.id.tv_c_dis);
        C_confm = (Button) findViewById(R.id.btn_C_Confm);
        C_cancel = (Button) findViewById(R.id.tv_C_Cancel);
        tv_crime_edit = (Button) findViewById(R.id.tv_crime_edit);
        tv_crime_del= (Button) findViewById(R.id.tv_crime_del);

        S_id = getIntent().getStringExtra("id");
        S_location = getIntent().getStringExtra("location");
        S_CrimeNo = getIntent().getStringExtra("crimeNumber");
        S_latitude = getIntent().getStringExtra("latitude");
        S_longitude = getIntent().getStringExtra("longitude");
        S_dateTime = getIntent().getStringExtra("dateTime");
        S_detected_ = getIntent().getStringExtra("detectedStatus");
        S_detected_id = getIntent().getStringExtra("detectedStatusId");
        Cat1 = getIntent().getStringExtra("crimeTypeId");
        item2 = getIntent().getStringExtra("crimeType");
        SubCat1 = getIntent().getStringExtra("subCrimeTypeId");
        item = getIntent().getStringExtra("subCrimeType");
        S_Descr = getIntent().getStringExtra("description");

        d_cno.setText(S_CrimeNo);
        d_ct.setText(item2);
        d_sct.setText(item);
        d_dt.setText(S_dateTime);
        d_ad.setText(S_location);
        d_ds.setText(S_detected_);
        d_dis.setText(S_Descr);

        C_confm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notnull_Crime();
                CheckConnectivity1();
            }
        });
        tv_crime_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notnull_Crime();
                CheckConnectivity();
            }
        });

        C_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(getApplicationContext(), Crime_OfflineActvity.class);

                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                finish();

            }
        });

        tv_crime_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Crime_OfflineActvity.this);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are You Sure..Do You Want to Delete This Record?");
                alertDialog.setIcon(R.drawable.alertt);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke YES event
                        dialog.cancel();
                        removeDB(S_id);
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke YES event
                        dialog.cancel();

                    }
                });
                alertDialog.show();

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
    public void Notnull_Crime() {



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

        if (S_detected_id == null) {
            S_detected_id = "";
        } else {
        }
        if (S_latitude == null) {
            S_latitude = "";
        } else {
        }
        if (S_longitude == null) {
            S_longitude = "";
        } else {
        }
        if (S_Descr == null) {
            S_Descr = "";
        } else {
        }
    }
    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");

            new pay().execute();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            Intent intent = new Intent(getApplicationContext(), EditCrimeData.class);
            intent.putExtra("id", S_id);
            intent.putExtra("crimeNumber",S_CrimeNo);
            intent.putExtra("crimetypeid", Cat1);
            intent.putExtra("crimetype", item2);
            intent.putExtra("crimesubtypeid",SubCat1 );
            intent.putExtra("crimesubtype",item );
            intent.putExtra("dateTime", S_dateTime);
            intent.putExtra("location", S_location);
            intent.putExtra("detectedStatus",S_detected_ );
            intent.putExtra("detectedStatus",S_detected_id);
            intent.putExtra("latitude", S_latitude);
            intent.putExtra("longitude", S_longitude);
            intent.putExtra("description",S_Descr );

            startActivity(intent);

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    public void removeDB(String id) {
        dbHandler = new DbHandlerCrime(Crime_OfflineActvity.this);
        database = dbHandler.getWritableDatabase();
        String updateQuery = "DELETE FROM Crime where _id = '" + id + "'";
        database.execSQL(updateQuery);
        database.close();
        Log.d(TAG, "Deleting Crime value from Db" + id);
        Intent i = new Intent(Crime_OfflineActvity.this, CrimeOfflineList.class);  //your class
        startActivity(i);
    }
    class pay extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(Crime_OfflineActvity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/AddCrime_View";
            String METHOD_NAME = "AddCrime_View";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx";
            try {
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("username", S_Uname);
                request.addProperty("password", S_Pass);
                request.addProperty("imei", S_IMEi);
                request.addProperty("connectionType", "TabletPc");
                request.addProperty("crimeNumber", S_CrimeNo);
                request.addProperty("crimeTypeId", Cat1);
                request.addProperty("subCrimeTypeId", SubCat1);
                request.addProperty("dateTime", S_dateTime);
                request.addProperty("location", S_location);
                request.addProperty("detectedStatus", S_detected_);
                request.addProperty("latitude", S_latitude);
                request.addProperty("longitude", S_longitude);
                request.addProperty("description", S_Descr);
                envelope.setOutputSoapObject(request);

                Log.e("request", "" + request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.debug = true;
                Log.e("Crime_OffeAct_ENVOLOPE", "" + envelope);
                try {
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    if (envelope != null) {
                        String result1 = envelope.getResponse().toString();
                        Log.e("result_Crime_Off", "" + result1);
                    }
                    SoapObject result = (SoapObject) envelope.bodyIn;
                    String[] testValues = new String[result.getPropertyCount()];
                    for (int i = 0; i < result.getPropertyCount(); i++) {
                        Object property = result.getProperty(i);
                        testValues[i] = result.getProperty(i).toString();
                        SoapObject category_list = (SoapObject) property;
                        Code1 = Integer.parseInt(category_list.getProperty("Code").toString());
                        Message1 = category_list.getProperty("Message").toString();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (timeoutexcep) {
                Toast.makeText(Crime_OfflineActvity.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(Crime_OfflineActvity.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(Crime_OfflineActvity.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (Code1 != null) {
                    if (Code1 == 0) {
                        Log.e("suceess", "" + Message1);


                        removeDB(S_id);
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Crime_OfflineActvity.this);
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
                        Log.e("not sucess", "" + Message1);

//                    et_Desriptn.setText("");
//                    et_Crno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Crime_OfflineActvity.this);
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("" + Message1);
                        alertDialog.setIcon(R.drawable.alertt);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        // Toast.makeText(Crime_OfflineActvity.this,""+Message,Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
                Log.e("result", "done");
                //tableview();
            }

            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }
    @Override
    public void onBackPressed() {

            Intent a = new Intent(getApplicationContext(), Crime_OfflineActvity.class);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            finish();

    }

}