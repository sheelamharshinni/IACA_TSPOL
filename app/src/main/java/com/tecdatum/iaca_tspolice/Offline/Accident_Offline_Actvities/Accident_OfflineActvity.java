package com.tecdatum.iaca_tspolice.Offline.Accident_Offline_Actvities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.tecdatum.iaca_tspolice.Adapter.Connectivity;
import com.tecdatum.iaca_tspolice.DataBase.DbHandler;

import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.MainActivity;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Accident_OfflineActvity extends AppCompatActivity {

    private static final String TAG = Accident_OfflineActvity.class.getSimpleName();
    Button tv_acci_back, tv_acci_save, tv_acci_del,tv_acci_edit;
    String S_discription, S_roadNo, S_vap, S_aap, S_id, S_locality, S_Cno, S_Cla, S_Noi, S_Nod, S_latitude, S_longitude, S_dateTime, Message,
            s_Ps_name, S_Uname, S_Pass, S_psid, s_role, S_IMEi, S_detected_;
    String Sat, Srt, Ssvc, Ssvs, Ssac, Ssas;
    String Sat1, Srt1, Svc1, Svs1, Sac1, Sas1;
    Integer Code;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    DbHandler dbHandler;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_offline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_acci_back = (Button) findViewById(R.id.tv_acci_back);
        tv_acci_save = (Button) findViewById(R.id.tv_acci_save);
        tv_acci_edit = (Button) findViewById(R.id.tv_acci_edit);
        tv_acci_del= (Button) findViewById(R.id.tv_acci_del);
        TextView d_ano = (TextView) findViewById(R.id.tv_acc_cno);
        TextView d_at = (TextView) findViewById(R.id.tv_acc_at);
        TextView d_dt = (TextView) findViewById(R.id.tv_acc_dt);
        TextView d_ad = (TextView) findViewById(R.id.tv_acc_add);
        TextView d_ds = (TextView) findViewById(R.id.tv_acc_ds);
        TextView d_dis = (TextView) findViewById(R.id.tv_acc_dis);
        TextView d_rt = (TextView) findViewById(R.id.tv_acc_rt);
        TextView d_rn = (TextView) findViewById(R.id.tv_acc_rn);
        TextView d_noi = (TextView) findViewById(R.id.tv_acc_noi);
        TextView d_nod = (TextView) findViewById(R.id.tv_acc_nod);

        S_discription = getIntent().getStringExtra("description");
        S_roadNo = getIntent().getStringExtra("RoadNumber");


        S_id = getIntent().getStringExtra("id");
        S_locality = getIntent().getStringExtra("location");
        S_Cno = getIntent().getStringExtra("crimeNumber");
        S_Cla = getIntent().getStringExtra("Locality");
        S_Noi = getIntent().getStringExtra("NoOfInjuries");
        S_Nod = getIntent().getStringExtra("NoOfDeaths");
        S_latitude = getIntent().getStringExtra("latitude");
        S_longitude = getIntent().getStringExtra("longitude");
        S_dateTime = getIntent().getStringExtra("dateTime");
        S_detected_ = getIntent().getStringExtra("detectedStatus");
        Sat1 = getIntent().getStringExtra("AccidentType1");
        Srt1 = getIntent().getStringExtra("RoadType");
        Sat = getIntent().getStringExtra("AccidentType");
        Srt = getIntent().getStringExtra("RoadTypeId");

        Svc1 = getIntent().getStringExtra("Victimcategorie");
        Sac1 = getIntent().getStringExtra("AccusedCategorie");

        Ssvc = getIntent().getStringExtra("VictimcategorieId");
        Ssac = getIntent().getStringExtra("AccusedCategorieId");

        S_vap = getIntent().getStringExtra("VictimAlcoholicPercentage");
        S_aap = getIntent().getStringExtra("AccusedAlcoholicPercentage");

        Ssvs = getIntent().getStringExtra("VictimStatus");
        Ssas = getIntent().getStringExtra("AccusedStatus");

        Svs1 = getIntent().getStringExtra("VictimStatusName");
        Sas1 = getIntent().getStringExtra("AccusedStatusName");

        d_ano.setText(S_Cno);
        d_at.setText(Sat);
        d_dt.setText(S_dateTime);
        d_ad.setText(S_Cla);
        d_ds.setText(S_detected_);
        d_dis.setText(S_discription);
        d_rt.setText(Srt1);
        d_rn.setText(S_roadNo);
        d_noi.setText(S_Noi);
        d_nod.setText(S_Nod);

        tv_acci_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(getApplicationContext(), AccidentOfflineList.class);

                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                a.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                finish();

            }
        });
        tv_acci_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Accident_OfflineActvity.this);
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
        tv_acci_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotNull();
                CheckConnectivity();
            }
        });
        tv_acci_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotNull();
                CheckConnectivity1();
            }
        });
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
        if (Ssas == null) {
            Ssas = "";
        } else {
        }
        if (S_aap == null) {
            S_aap = "";
        } else {
        }

    }

    class pay extends AsyncTask<Void, Void, Void> {


        private final ProgressDialog dialog = new ProgressDialog(Accident_OfflineActvity.this);

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
            Log.e("Accident_OENVOLOPE", "" + envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();

                Log.e("result_Accident_Offline", "" + result1);
                SoapObject result = (SoapObject) envelope.bodyIn;
                String[] testValues = new String[result.getPropertyCount()];
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    Object property = result.getProperty(i);
                    testValues[i] = result.getProperty(i).toString();
                    SoapObject category_list = (SoapObject) property;
                    Code = Integer.parseInt(category_list.getProperty("Code").toString());
                    Message = category_list.getProperty("Message").toString();
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
                Toast.makeText(Accident_OfflineActvity.this, "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(Accident_OfflineActvity.this, "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(Accident_OfflineActvity.this, "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (Code != null) {
                    if (Code == 0) {
                        Log.e("suceess", "" + Message);
                        removeDB(S_id);
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Accident_OfflineActvity.this);
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
                        Log.e("not sucess", "" + Message);

//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Accident_OfflineActvity.this);
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("" + Message);
                        alertDialog.setIcon(R.drawable.alertt);
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();
                        // Toast.makeText(Accident_OfflineActvity.this,""+Message,Toast.LENGTH_LONG).show();
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
            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");

            Intent intent = new Intent(getApplicationContext(), EditAccidentData.class);

            intent.putExtra("id", S_id);
            intent.putExtra("crimeNumber",S_Cno);
            intent.putExtra("AccidentType", Sat1);
            intent.putExtra("dateTime", S_dateTime);
            intent.putExtra("location", S_locality);
            intent.putExtra("RoadTypeId", Srt);
            intent.putExtra("RoadNumber", S_roadNo);
            intent.putExtra("detectedStatus",S_detected_ );
            intent.putExtra("latitude", S_latitude);
            intent.putExtra("longitude", S_longitude);
            intent.putExtra("description",S_discription );
            intent.putExtra("NoOfInjuries", S_Noi);
            intent.putExtra("NoOfDeaths", S_Nod);
            intent.putExtra("VictimcategorieId",Ssvc );
            intent.putExtra("VictimStatus", Ssvs);
            intent.putExtra("VictimAlcoholicPercentage",S_vap );
            intent.putExtra("AccusedCategorieId",Ssac );
            intent.putExtra("AccusedStatus", Ssas);
            intent.putExtra("AccusedAlcoholicPercentage", S_aap);
            intent.putExtra("AccidentType1", Sat);
            intent.putExtra("RoadType", Srt1);
            intent.putExtra("Victimcategorie", Svc1);
            intent.putExtra("AccusedCategorie", Sac1);
            intent.putExtra("VictimStatusName",Svs1 );
            intent.putExtra("AccusedStatusName", Sas1);
            intent.putExtra("Locality", S_Cla);
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
        dbHandler = new DbHandler(Accident_OfflineActvity.this);
        database = dbHandler.getWritableDatabase();
        String updateQuery = "DELETE FROM Location where _id = '" + id + "'";
        database.execSQL(updateQuery);
        database.close();
        Log.d(TAG, "Deleting Location value from Db" + id);
        Intent i = new Intent(Accident_OfflineActvity.this, AccidentOfflineList.class);  //your class
        startActivity(i);
    }
}
