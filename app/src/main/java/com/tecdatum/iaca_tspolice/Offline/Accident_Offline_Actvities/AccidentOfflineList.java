package com.tecdatum.iaca_tspolice.Offline.Accident_Offline_Actvities;

/**
 * Created by HI on 1/9/2018.
 */

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.tecdatum.iaca_tspolice.Adapter.AccidentAdapter;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.DataBase.DbHandler;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.MainActivity;
import com.tecdatum.iaca_tspolice.Helper.helper_acci;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public class AccidentOfflineList extends AppCompatActivity {

    ArrayList<Samplemyclass> cl3, cl4, d1, d2, d3, d4, d5, d6;
    ArrayAdapter<Samplemyclass> adapter_state;
    private static final String TAG = AccidentOfflineList.class.getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    CustomDateTimePicker custom1;
    String Sat, Srt, Ssvc, Ssvs, S_id, Ssac, Ssas;
    private Spinner at, rt, svc, svs, sac, sas, detected_;
    String Sat1, Srt1, Svc1, Svs1, Sac1, Sas1;
    RelativeLayout RL1, RL2;
    int Code;
    EditText et_discription, et_roadNo, et_vap, et_aap, et_locality, et_Cno, et_Cla, et_Noi, et_Nod;
    Button Bt_c_rest, Bt_c_submit, Bt_c_vl;
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    String S_discription, S_roadNo, S_vap, S_aap, S_locality, S_Cno, S_Cla, S_Noi, S_Nod, S_latitude, S_longitude, S_dateTime, Message, s_Ps_name, S_Uname, S_Pass, S_psid, s_role, S_IMEi, S_detected_;
    Button Bt_acc_date;
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
    ArrayList<String> type_list = new ArrayList<String>();
    ArrayList<helper_acci> arraylist = new ArrayList<helper_acci>();

    // for Db
    DbHandler dbHandler;
    SQLiteDatabase database;
    SQLiteStatement statement;
    Cursor cursor;
    EditText searchBox;
    ListView list;
    TextView tv_nodata;
    ImageView btn_refresh;
    AccidentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_accident);

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
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//            }
//        });
    }
    public void checkInternet() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            if (arraylist != null) {
                if (arraylist.size() > 0) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccidentOfflineList.this);
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
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccidentOfflineList.this);
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

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccidentOfflineList.this);
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
        dbHandler = new DbHandler(AccidentOfflineList.this);
        database = dbHandler.getReadableDatabase();
        String query = ("select * from Location");
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
                        S_Cno = cursor.getString(cursor.getColumnIndex("crimeNumber"));
                        Sat1= cursor.getString(cursor.getColumnIndex("AccidentType"));
                        S_dateTime = cursor.getString(cursor.getColumnIndex("dateTime"));
                        S_locality= cursor.getString(cursor.getColumnIndex("location"));
                        Srt= cursor.getString(cursor.getColumnIndex("RoadTypeId"));
                        S_roadNo = cursor.getString(cursor.getColumnIndex("RoadNumber"));

                        S_detected_ = cursor.getString(cursor.getColumnIndex("detectedStatus"));
                        S_latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                        S_longitude = cursor.getString(cursor.getColumnIndex("longitude"));

                        S_discription = cursor.getString(cursor.getColumnIndex("description"));
                        S_Noi = cursor.getString(cursor.getColumnIndex("Noofinjuries"));
                        S_Nod = cursor.getString(cursor.getColumnIndex("Noofdeaths"));
                        Ssvc = cursor.getString(cursor.getColumnIndex("VictimcategorieId"));
                        Ssvs = cursor.getString(cursor.getColumnIndex("VictimStatus"));
                        S_vap = cursor.getString(cursor.getColumnIndex("VictimAlcoholicPercentage"));
                        Ssac = cursor.getString(cursor.getColumnIndex("AccusedCategorieId"));
                        Ssas = cursor.getString(cursor.getColumnIndex("AccusedStatus"));
                        S_aap = cursor.getString(cursor.getColumnIndex("AccusedAlcoholicPercentage"));
                        Sat= cursor.getString(cursor.getColumnIndex("AccidentType1"));
                        Srt1 = cursor.getString(cursor.getColumnIndex("RoadType"));

                        Svc1 = cursor.getString(cursor.getColumnIndex("Victimcategorie"));
                        Sac1 = cursor.getString(cursor.getColumnIndex("VictimStatusName"));
                        Svs1 = cursor.getString(cursor.getColumnIndex("AccusedCategorie"));
                        Sas1 = cursor.getString(cursor.getColumnIndex("AccusedStatusName"));


                        S_Cla= cursor.getString(cursor.getColumnIndex("Locality"));
                        cursor.moveToNext();

                        helper_acci wp = new helper_acci(S_id,S_Cno,Sat1,S_dateTime,S_locality,Srt,
                                S_roadNo,S_detected_,S_latitude,S_longitude,S_discription,
                                S_Noi,S_Nod,Ssvc,Ssvs,S_vap,
                                Ssac,Ssas,S_aap,Sat,Srt1,Svc1,Sac1,Svs1,Sas1,S_Cla);

                        Log.d(TAG, " "+""+S_Cno);
                        // Binds all strings into an array
                        arraylist.add(wp);
                        // Pass results to ListViewAdapter Class
                        try {
                            // Pass results to ListViewAdapter Class
                            adapter = new AccidentAdapter(getApplicationContext(), arraylist);
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
    class pay extends AsyncTask<Void, Void, Void> {


        private final ProgressDialog dialog = new ProgressDialog(AccidentOfflineList.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {


            String SOAP_ACTION = "http://tempuri.org/AddAccident_View";
            String METHOD_NAME = "AddAccident_View";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username", S_Uname);
            request.addProperty("password", S_Pass);
            request.addProperty("imei", S_IMEi);
            request.addProperty("connectionType", "TabletPc");
            request.addProperty("crimeNumber", S_Cno);
            request.addProperty("AccidentType", Sat1);
            request.addProperty("dateTime", S_dateTime);

            request.addProperty("location", S_locality);
            request.addProperty("RoadTypeId", Srt);
            request.addProperty("RoadNumber", S_roadNo);
            request.addProperty("detectedStatus", S_detected_);
            request.addProperty("latitude", S_latitude);
            request.addProperty("longitude", S_longitude);
            request.addProperty("description", S_discription);
            request.addProperty("NoOfInjuries", S_Noi);
            request.addProperty("NoOfDeaths", S_Nod);
            request.addProperty("VictimcategorieId", Ssvc);
            request.addProperty("VictimStatus", Ssvs);
            request.addProperty("VictimAlcoholicPercentage", S_vap);
            request.addProperty("AccusedCategorieId", Ssac);
            request.addProperty("AccusedStatus", Ssas);
            request.addProperty("AccusedAlcoholicPercentage", S_aap);

            request.addProperty("Locality", S_Cla);
            request.addProperty("base64Image", "");

            envelope.setOutputSoapObject(request);

            Log.e("request", "" + request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            Log.e("OfflineData_ENVOLOPE", "" + envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();

                Log.e("result_OfflineData", "" + result1);
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
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.e("result", "" + result);
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (timeoutexcep) {
                Toast.makeText(AccidentOfflineList.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(AccidentOfflineList.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(AccidentOfflineList.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if(Code1!=null) {
                    if (Code1 == 0) {
                        Log.e("suceess", "" + Message1);
                        //C_dialog.dismiss();
                        removeDB(S_id);

//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccidentOfflineList.this);
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
                       // removeDB(S_id);
                        Log.e("not sucess", "" + Message1);
                       // C_dialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccidentOfflineList.this);
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("" + Message1);
                        alertDialog.setIcon(R.drawable.alertt);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        // Toast.makeText(AccidentOfflineList.this,""+Message,Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                }

                Log.e("result", "done");
                //tableview();
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }
    private void CheckConnectivity() {

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
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    private void GetOfflineServer() {

        arraylist.clear();
        dbHandler = new DbHandler(AccidentOfflineList.this);
        database = dbHandler.getReadableDatabase();
        String query = ("select * from Location");
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
                        S_Cno = cursor.getString(cursor.getColumnIndex("crimeNumber"));
                        Sat1= cursor.getString(cursor.getColumnIndex("AccidentType"));
                        S_dateTime = cursor.getString(cursor.getColumnIndex("dateTime"));
                        S_locality= cursor.getString(cursor.getColumnIndex("location"));
                        Srt= cursor.getString(cursor.getColumnIndex("RoadTypeId"));
                        S_roadNo = cursor.getString(cursor.getColumnIndex("RoadNumber"));

                        S_detected_ = cursor.getString(cursor.getColumnIndex("detectedStatus"));
                        S_latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                        S_longitude = cursor.getString(cursor.getColumnIndex("longitude"));

                        S_discription = cursor.getString(cursor.getColumnIndex("description"));
                        S_Noi = cursor.getString(cursor.getColumnIndex("Noofinjuries"));
                        S_Nod = cursor.getString(cursor.getColumnIndex("Noofdeaths"));
                        Ssvc = cursor.getString(cursor.getColumnIndex("VictimcategorieId"));
                        Ssvs = cursor.getString(cursor.getColumnIndex("VictimStatus"));
                        S_vap = cursor.getString(cursor.getColumnIndex("VictimAlcoholicPercentage"));
                        Ssac = cursor.getString(cursor.getColumnIndex("AccusedCategorieId"));
                        Ssas = cursor.getString(cursor.getColumnIndex("AccusedStatus"));
                        S_aap = cursor.getString(cursor.getColumnIndex("AccusedAlcoholicPercentage"));
                        Sat= cursor.getString(cursor.getColumnIndex("AccidentType1"));
                        Srt1 = cursor.getString(cursor.getColumnIndex("RoadType"));

                        Svc1 = cursor.getString(cursor.getColumnIndex("Victimcategorie"));
                        Sac1 = cursor.getString(cursor.getColumnIndex("VictimStatusName"));
                        Svs1 = cursor.getString(cursor.getColumnIndex("AccusedCategorie"));
                        Sas1 = cursor.getString(cursor.getColumnIndex("AccusedStatusName"));


                        S_Cla= cursor.getString(cursor.getColumnIndex("Locality"));
                        cursor.moveToNext();

                        helper_acci wp = new helper_acci(S_id,S_Cno,Sat1,S_dateTime,S_locality,Srt,
                                S_roadNo,S_detected_,S_latitude,S_longitude,S_discription,
                                S_Noi,S_Nod,Ssvc,Ssvs,S_vap,
                                Ssac,Ssas,S_aap,Sat,Srt1,Svc1,Sac1,Svs1,Sas1,S_Cla);

                        Log.d(TAG, " "+""+S_Cno);
                        // Binds all strings into an array
                        arraylist.add(wp);

                    }
                    try {
                        // Pass results to ListViewAdapter Class
                        adapter = new AccidentAdapter(getApplicationContext(), arraylist);
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
    public void removeDB(String id) {

        database = dbHandler.getWritableDatabase();
        String updateQuery = "DELETE FROM Location where _id = '" + id + "'";
        database.execSQL(updateQuery);
        database.close();
        Log.d(TAG, "Deleting Location value from Db" + id);
        Intent i = new Intent(AccidentOfflineList.this, AccidentOfflineList.class);  //your class
        startActivity(i);
    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

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
