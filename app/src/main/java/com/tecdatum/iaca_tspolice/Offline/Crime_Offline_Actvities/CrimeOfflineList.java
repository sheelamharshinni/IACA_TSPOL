package com.tecdatum.iaca_tspolice.Offline.Crime_Offline_Actvities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CrimeAdapter;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.DataBase.DbHandlerCrime;
import com.tecdatum.iaca_tspolice.Helper.helper_crime;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.MainActivity;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;

public class CrimeOfflineList extends AppCompatActivity {
    private String[] no_state = {"Complaint"};
    private String[] state = {"Select Status", "Detected", "Undetected"};
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = CrimeOfflineList.class.getSimpleName();
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
    Dialog C_dialog, vl_dialog, vdialog;
    TextView d_cno, d_ct, d_sct, d_dt, d_ad, d_ds, d_dis;
    Button C_cancel, C_confm;
    CustomDateTimePicker custom1;
    Timer timer = new Timer();
    int Code;
    Integer Code1;
    ArrayList<String> Cnumber_arraylist_ = new ArrayList<String>();
    ArrayList<String> Pscode_arraylist_ = new ArrayList<String>();
    ArrayList<String> Type_arraylist_ = new ArrayList<String>();
    ArrayList<String> Subtype_arraylist_ = new ArrayList<String>();
    ArrayList<String> Location_arraylist_ = new ArrayList<String>();
    ArrayList<String> Descr_arraylist_ = new ArrayList<String>();
    ArrayList<String> EnteredDate_arraylist_ = new ArrayList<String>();
    ArrayList<String> Status_arraylist_ = new ArrayList<String>();

    String Cnumber, Pscode, Type, Subtype, Location, Descr, EnteredDate, Status;
    TextView c_time;
    String formattedDate;
    String[] array;
    String str1, str2, str3, str4, str5, str6;
    ArrayList<Samplemyclass> countryList, countryList1, d3, d6, countryList2, countryList3, countryList4, countryList5;
    ArrayAdapter<Samplemyclass> adapter_state;
    private Spinner Category, SCategory, detected_;
    Button Bt_c_date, Bt_cr_time, Bt_c_vl, Bt_c_rest, btn_c_offline,Bt_c_submit;
    int CrimeTypeid;
    String S_id,item, item2, tv_Ad, tv_Long, tv_Lat, S_Descr, psJuri, S_Uname, S_Pass, s_Ps_name, S_longitude, S_latitude,
            Message, Message1, S_detected_id, S_detected_, S_psid, s_role, S_IMEi, S_CrimeNo, S_dateTime, S_location, Loc, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    EditText et_Locality, et_Desriptn, et_Crno;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_c_psname;
    RelativeLayout RL_sp_sct;
    ArrayList<helper_crime> arraylist = new ArrayList<helper_crime>();

    // for Db
    DbHandlerCrime dbHandler;
    SQLiteDatabase database;
    SQLiteStatement statement;
    Cursor cursor;
    EditText searchBox;
    ListView list;
    TextView tv_nodata;
    ImageView btn_refresh;
    CrimeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_offline_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchBox = (EditText) findViewById(R.id.search_box);
        list = (ListView) findViewById(R.id.item_list);
        btn_refresh = (ImageView) findViewById(R.id.btn_refresh);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkInternet();

            }
        });

        GetOfflineServer();
        searchBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (searchBox.getText() != null) {
                    String text = searchBox.getText().toString().toLowerCase(Locale.getDefault());
                    try {
                        adapter.filter(text);
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
        
        
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        
        
        
    }

    
    
    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            Notnull_Crime();
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
    class pay extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(CrimeOfflineList.this);

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
                Log.e("Crime_ENVOLOPE", "" + envelope);
                try {
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    if (envelope != null) {
                        String result1 = envelope.getResponse().toString();
                        Log.e("result_CrimeOfflineList", "" + result1);
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
                Toast.makeText(CrimeOfflineList.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(CrimeOfflineList.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(CrimeOfflineList.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (Code1 != null) {
                    if (Code1 == 0) {
                        Log.e("suceess", "" + Message1);
                        C_dialog.dismiss();
                        et_Desriptn.setText("");
                        et_Crno.setText("");
                        //  Et_yellow.setText("");

                        Category.setSelection(0);
                        SCategory.setSelection(0);
                        detected_.setSelection(0);
                        Bt_c_date.setText("Select Date and time");
                        S_CrimeNo = "";
                        Cat1 = "";
                        SubCat1 = "";
                        S_dateTime = "";
                        S_location = "";
                        S_detected_ = "";
                        S_latitude = "";
                        S_longitude = "";
                        S_Descr = "";

                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CrimeOfflineList.this);
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
                        C_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Crno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CrimeOfflineList.this);
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("" + Message1);
                        alertDialog.setIcon(R.drawable.alertt);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        // Toast.makeText(CrimeOfflineList.this,""+Message,Toast.LENGTH_LONG).show();
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



    public void checkInternet() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            if (arraylist != null) {
                if (arraylist.size() > 0) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CrimeOfflineList.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Are You Sure Do You Want To Send All Data To Server ");
                    alertDialog.setIcon(R.drawable.alert);
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            dialog.cancel();
                            try {
                                SendOfflineLatLongToServer();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                } else {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CrimeOfflineList.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("No Offline Data ");
                    alertDialog.setIcon(R.drawable.alert);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            } else {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CrimeOfflineList.this);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("No Offline Data ");
                alertDialog.setIcon(R.drawable.alert);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke YES event
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }


        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    private void SendOfflineLatLongToServer() {
        arraylist.clear();
        dbHandler = new DbHandlerCrime(CrimeOfflineList.this);
        database = dbHandler.getReadableDatabase();
        String query = ("select * from Crime");
        cursor = database.rawQuery(query, null);
        int j = cursor.getCount();
        Log.d("count", "" + j);
        if (j > 0) {
            tv_nodata.setVisibility(View.GONE);
            if (cursor != null) {
                tv_nodata.setVisibility(View.GONE);
                if (cursor.moveToFirst()) {
                    tv_nodata.setVisibility(View.GONE);

                    while (cursor.isAfterLast() == false) {

                        S_id = cursor.getString(cursor.getColumnIndex("_id"));
                        S_CrimeNo = cursor.getString(cursor.getColumnIndex("crimeNumber"));
                        Cat1= cursor.getString(cursor.getColumnIndex("crimeTypeId"));
                        item2 = cursor.getString(cursor.getColumnIndex("crimeType"));
                        SubCat1= cursor.getString(cursor.getColumnIndex("subCrimeTypeId"));
                        item= cursor.getString(cursor.getColumnIndex("subCrimeType"));
                        S_dateTime = cursor.getString(cursor.getColumnIndex("dateTime"));

                        S_detected_ = cursor.getString(cursor.getColumnIndex("detectedStatus"));
                        S_detected_id = cursor.getString(cursor.getColumnIndex("detectedStatusId"));
                        S_latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                        S_longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                        S_location= cursor.getString(cursor.getColumnIndex("location"));
                        S_Descr = cursor.getString(cursor.getColumnIndex("description"));

                        cursor.moveToNext();

                        helper_crime wp = new helper_crime(S_id,S_CrimeNo,Cat1,item2,SubCat1,item,
                                S_dateTime,S_location,S_detected_,S_detected_id,S_latitude,S_longitude,S_Descr);



                        Log.d(TAG, " "+""+S_CrimeNo);
                        // Binds all strings into an array
                        arraylist.add(wp);
                        // Pass results to ListViewAdapter Class
                        try {
                            // Pass results to ListViewAdapter Class
                            adapter = new CrimeAdapter(getApplicationContext(), arraylist);
                            // Binds the Adapter to the ListView
                            list.setAdapter(adapter);
                            list.setItemsCanFocus(false);
                            list.setTextFilterEnabled(true);
                            Log.e("VOLLEY", "arraylist" + arraylist);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        CheckConnectivity();

                    }
                }
            }
        } else {
            Log.d(TAG, " Sending data to the server  ");

            tv_nodata.setVisibility(View.VISIBLE);

        }
    }
   

    private void GetOfflineServer() {

        arraylist.clear();
        dbHandler = new DbHandlerCrime(CrimeOfflineList.this);
        database = dbHandler.getReadableDatabase();
        String query = ("select * from Crime");
        cursor = database.rawQuery(query, null);
        int j = cursor.getCount();
        Log.d("count", "" + j);
        if (j > 0) {
            tv_nodata.setVisibility(View.GONE);
            if (cursor != null) {
                tv_nodata.setVisibility(View.GONE);
                if (cursor.moveToFirst()) {
                    tv_nodata.setVisibility(View.GONE);

                    while (cursor.isAfterLast() == false) {


                        S_id = cursor.getString(cursor.getColumnIndex("_id"));
                        S_CrimeNo = cursor.getString(cursor.getColumnIndex("crimeNumber"));
                        Cat1= cursor.getString(cursor.getColumnIndex("crimeTypeId"));
                        item2 = cursor.getString(cursor.getColumnIndex("crimeType"));
                        SubCat1= cursor.getString(cursor.getColumnIndex("subCrimeTypeId"));
                        item= cursor.getString(cursor.getColumnIndex("subCrimeType"));
                        S_dateTime = cursor.getString(cursor.getColumnIndex("dateTime"));

                        S_detected_ = cursor.getString(cursor.getColumnIndex("detectedStatus"));
                        S_detected_id = cursor.getString(cursor.getColumnIndex("detectedStatusId"));
                        S_latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                        S_longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                        S_location= cursor.getString(cursor.getColumnIndex("location"));
                        S_Descr = cursor.getString(cursor.getColumnIndex("description"));

                        cursor.moveToNext();

                        helper_crime wp = new helper_crime(S_id,S_CrimeNo,Cat1,item2,SubCat1,item,
                                S_dateTime,S_location,S_detected_,S_detected_id,S_latitude,S_longitude,S_Descr);



                        Log.d(TAG, " "+""+S_CrimeNo);
                        // Binds all strings into an array
                        arraylist.add(wp);

                    }
                    try {
                        // Pass results to ListViewAdapter Class
                        adapter = new CrimeAdapter(getApplicationContext(), arraylist);
                        // Binds the Adapter to the ListView
                        list.setAdapter(adapter);
                        list.setItemsCanFocus(false);
                        list.setTextFilterEnabled(true);
                        //      Log.e("VOLLEY", "arraylist" + arraylist);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Log.d(TAG, " Sending data to the server  ");

            tv_nodata.setVisibility(View.VISIBLE);

        }
    }
}
