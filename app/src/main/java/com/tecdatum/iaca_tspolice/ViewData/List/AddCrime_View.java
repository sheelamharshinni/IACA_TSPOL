package com.tecdatum.iaca_tspolice.ViewData.List;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tecdatum.iaca_tspolice.Adapter.Connectivity;

import com.tecdatum.iaca_tspolice.Adapter.CustomDateTimePicker;
import com.tecdatum.iaca_tspolice.Adapter.CustomYearPicker;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.DataBase.DbHandlerCrime;
import com.tecdatum.iaca_tspolice.Helper.CrimeSub_helper;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

public class AddCrime_View extends AppCompatActivity {

    CrimeAdapter accidentAdapter;
    ArrayList<Crime_helper> arraylist = new ArrayList<Crime_helper>();
    ListView list_acc;
    TableRow tr_person;
    TextView tv_norecords,tv_totalrecords;
    EditText et_search_activity;
    private String Crime_Master = URLS.CrimeMasters;
    ArrayList<Samplemyclass> countryList_year= new ArrayList<Samplemyclass>();
    Spinner spin_year;
    String    yearid,yearName;
    private String AddCrime = URLS.AddCrime;
    private String CrimeListWithFilter = URLS.CrimeListWithFilter;
    private String[] no_state = {"Complaint"};
    private String[] state = {"Select Status", "Detected", "Undetected"};
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = AddCrime_View.class.getSimpleName();
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
    TextView d_year, d_cno, d_ct, d_sct, d_dt, d_ad, d_ds, d_dis;
    Button C_cancel, C_confm;
    CustomDateTimePicker custom1, custom0;
    CustomYearPicker custom2;
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
    ArrayList<Samplemyclass> countryList = new ArrayList<>();
    ArrayList<Samplemyclass> countryList1 = new ArrayList<>();
    ArrayList<Samplemyclass> d3 = new ArrayList<>();
    ArrayAdapter<Samplemyclass> adapter_state;
    ArrayList<CrimeSub_helper> CrimeSub_array;
    ArrayList<Samplemyclass> new_subtype;
    private Spinner at, rt, svc, svs, sac, sas;
    Button btn_acc_submit, btn_acc_reset;
    ProgressDialog progressDialog;
    private Spinner Category, SCategory, detected_;
    Button Bt_c_dateReport, Bt_c_date, Bt_c_Year, Bt_cr_time, Bt_c_vl, Bt_c_rest, btn_c_offline, Bt_c_submit;
    int CrimeTypeid;
    String S_c_Year, item, item2, tv_Ad, tv_Long, tv_Lat, S_Descr, psJuri, S_Uname, S_Pass, s_Ps_name, S_longitude, S_latitude,
            Message, Message1, S_detected_id, S_detected_, S_psCode, S_psid, s_role, S_IMEi, S_CrimeNo, S_dateTime, S_dateTime1, S_location, Loc, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    String str11 = "1";
    EditText et_Locality, et_Desriptn, et_c_year, et_Crno;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_c_psname;
    RelativeLayout RL_sp_sct;
    // for Db
    DbHandlerCrime dbHandler;
    SQLiteDatabase database;
    SQLiteStatement statement;
    Cursor cursor;
    TableRow r0, r1, r2, r3, r4, r5;
    TextView t1, t2, t3, t4, t5;
    TextView st1, st2, st3, st4, st5;
    TextView dt1, dt2, dt3, dt4, dt5;
    TextView v1, v2, v3, v4, v5, NoRecords;
    RelativeLayout Rl_Ssubtype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_crime);
        spin_year = (Spinner) findViewById(R.id.spin_Year);

        Rl_Ssubtype= (RelativeLayout) findViewById(R.id.Rl_Ssubtype);
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
        Category = (Spinner) findViewById(R.id.sp_accidentType);
        SCategory = (Spinner) findViewById(R.id.sp_detected);
        tv_norecords= (TextView) findViewById(R.id.tv_norecords);
        tv_totalrecords= (TextView) findViewById(R.id.tv_totalrecords);
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
        r0 = (TableRow) findViewById(R.id.tr0);
        r1 = (TableRow) findViewById(R.id.tr1);
        r2 = (TableRow) findViewById(R.id.tableRow2);
        r3 = (TableRow) findViewById(R.id.tableRow3);
        r4 = (TableRow) findViewById(R.id.tableRow4);
        r5 = (TableRow) findViewById(R.id.tableRow5);
        NoRecords = (TextView) findViewById(R.id.tv_norecords);

        t1 = (TextView) findViewById(R.id.tv_t1);
        t2 = (TextView) findViewById(R.id.tv_t2);
        t3 = (TextView) findViewById(R.id.tv_t3);
        t4 = (TextView) findViewById(R.id.tv_t4);
        t5 = (TextView) findViewById(R.id.tv_t5);

        st1 = (TextView) findViewById(R.id.tv_p1);
        st2 = (TextView) findViewById(R.id.tv_p2);
        st3 = (TextView) findViewById(R.id.tv_p3);
        st4 = (TextView) findViewById(R.id.tv_p4);
        st5 = (TextView) findViewById(R.id.tv_p5);

        dt1 = (TextView) findViewById(R.id.tv_c1);
        dt2 = (TextView) findViewById(R.id.tv_c2);
        dt3 = (TextView) findViewById(R.id.tv_c3);
        dt4 = (TextView) findViewById(R.id.tv_c4);
        dt5 = (TextView) findViewById(R.id.tv_c5);

        v1 = (TextView) findViewById(R.id.tv_m1);
        v2 = (TextView) findViewById(R.id.tv_m2);
        v3 = (TextView) findViewById(R.id.tv_m3);
        v4 = (TextView) findViewById(R.id.tv_m4);
        v5 = (TextView) findViewById(R.id.tv_m5);

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
                Intent i = new Intent(getApplicationContext(), AddCrime_View.class);
                startActivity(i);


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

                        countryList_year.clear();
                        if (code.equalsIgnoreCase("1")){

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Year");
                                // Binds all strings into an array
                                countryList_year.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    countryList_year.add(wp);
                                }
                                if (countryList_year.size() > 0) {
                                    YearMaster(countryList_year);
                                }
                            }



                        }else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Year");
                            // Binds all strings into an array
                            countryList_year.add(wp0);
                            if (countryList_year.size() > 0) {
                                YearMaster(countryList_year);
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


    private void alertDialogue_C1(int count) {
        C_dialog = new Dialog(AddCrime_View.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_confm);
        C_dialog.show();

        d_cno = (TextView) C_dialog.findViewById(R.id.tv_c_cno);
        d_ct = (TextView) C_dialog.findViewById(R.id.tv_c_ct);
        d_sct = (TextView) C_dialog.findViewById(R.id.tv_c_sct);
        d_dt = (TextView) C_dialog.findViewById(R.id.tv_c_dt);
        d_ad = (TextView) C_dialog.findViewById(R.id.tv_c_add);
        d_ds = (TextView) C_dialog.findViewById(R.id.tv_c_ds);
        d_dis = (TextView) C_dialog.findViewById(R.id.tv_c_dis);
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_c_pscode);
        pscode.setText(s_Ps_name);

//        if(!Cnumber_arraylist_.get(count)){
//Status_arraylist_
//        }
//

        d_cno.setText(Cnumber_arraylist_.get(count));


        d_ct.setText(Type_arraylist_.get(count));
        d_sct.setText(Subtype_arraylist_.get(count));
        d_dt.setText(EnteredDate_arraylist_.get(count));
        d_ad.setText(Location_arraylist_.get(count));
        d_dis.setText(Descr_arraylist_.get(count));
        d_ds.setText(Status_arraylist_.get(count));

        C_confm = (Button) C_dialog.findViewById(R.id.btn_C_Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });
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
    private void SetValuesToLayout(int coun) {


        if ((Type_arraylist_.size()) > 0 | Type_arraylist_.size() != 0) {
            Log.e("1", "" + Type_arraylist_.size());


            if ((Type_arraylist_.size()) == 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(EnteredDate_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(EnteredDate_arraylist_.get(3));
                t5.setText(Type_arraylist_.get(4));
                dt5.setText(EnteredDate_arraylist_.get(4));

                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }
                    if ((Cnumber_arraylist_.size()) == 3) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                    }
                    if ((Cnumber_arraylist_.size()) == 4) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                    }
                    if ((Cnumber_arraylist_.size()) == 5) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                        st5.setText(Cnumber_arraylist_.get(4));
                    }
                }


            }
            if ((Type_arraylist_.size()) == 1) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(EnteredDate_arraylist_.get(0));
                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }


                }

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
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));

                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }

                }
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
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(EnteredDate_arraylist_.get(2));

                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }

                }
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
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(EnteredDate_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(EnteredDate_arraylist_.get(3));
                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }
                    if ((Cnumber_arraylist_.size()) == 3) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                    }
                    if ((Cnumber_arraylist_.size()) == 4) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                    }

                }
            }

            if ((Type_arraylist_.size()) > 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                Log.e("yes", "" + Type_arraylist_.size());
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(EnteredDate_arraylist_.get(0));
                t2.setText(Type_arraylist_.get(1));
                dt2.setText(EnteredDate_arraylist_.get(1));
                t3.setText(Type_arraylist_.get(2));
                dt3.setText(EnteredDate_arraylist_.get(2));
                t4.setText(Type_arraylist_.get(3));
                dt4.setText(EnteredDate_arraylist_.get(3));
                t5.setText(Type_arraylist_.get(4));
                dt5.setText(EnteredDate_arraylist_.get(4));
                if ((Cnumber_arraylist_.size()) != 0) {
                    if ((Cnumber_arraylist_.size()) == 1) {
                        st1.setText(Cnumber_arraylist_.get(0));
                    }
                    if ((Cnumber_arraylist_.size()) == 2) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                    }
                    if ((Cnumber_arraylist_.size()) == 3) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                    }
                    if ((Cnumber_arraylist_.size()) == 4) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                    }
                    if ((Cnumber_arraylist_.size()) == 5) {
                        st1.setText(Cnumber_arraylist_.get(0));
                        st2.setText(Cnumber_arraylist_.get(1));
                        st3.setText(Cnumber_arraylist_.get(2));
                        st4.setText(Cnumber_arraylist_.get(3));
                        st5.setText(Cnumber_arraylist_.get(4));
                    }
                }
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

    private void setData() {

        if (isConnectingToInternet(AddCrime_View.this)) {
            CheckConnectivity();
        }

    }
    private void CrimeSubtype(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            SCategory.setAdapter(adapter);
            SCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {
                        Samplemyclass list = (Samplemyclass) SCategory.getSelectedItem();
                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                        SubCat1 = country.getId();
                        item = country.getName();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }
    private void getSubTypeCrimeType_Api(String Cid) {

        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("ActionName", "2");
            jsonBody.put("Id", ""+Cid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:CrimeSubtype_Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Crime_Master, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:CrimeSubtype_Master" + response);
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

                                Samplemyclass wb0 = new Samplemyclass("0", "Select Crime SubType");
                                // Binds all strings into an array
                                countryList1.add(wb0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    countryList1.add(wp);
                                }
                                if (countryList1.size() > 0) {
                                    CrimeSubtype(countryList1);
                                }
                            }



                        }else {
                            Samplemyclass wb0 = new Samplemyclass("0", "Select Crime SubType");
                            // Binds all strings into an array
                            countryList1.add(wb0);
                            if (countryList.size() > 0) {
                                CrimeSubtype(countryList1);
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
    private void Crimetype(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, str1);
        if (adapter != null) {
            Category.setAdapter(adapter);
            Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {
                        Rl_Ssubtype.setVisibility(View.VISIBLE);
                        SubCat1="";
                       // Samplemyclass list = (Samplemyclass) SCategory.getSelectedItem();
                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        Cat1 = country.getId();
                        CrimeTypeid = Integer.parseInt(Cat1);
                        item2 = country.getName();
                        Log.e("Cat1", "" + Cat1);


                        if(Cat1!=null){
                            if (isConnectingToInternet(AddCrime_View.this)) {
                                getSubTypeCrimeType_Api(Cat1);
                            } else {
                                //verifysubtype(Cat1);
                                Toast.makeText(getApplicationContext(),"Please Check Your Intenet Connectivity",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    if (pos == 0) {
                        Rl_Ssubtype.setVisibility(View.GONE);
                        SubCat1="";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void getCrimeType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "6");
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

                        countryList.clear();
                        if (code.equalsIgnoreCase("1")){

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select Crime Type");
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
                                    Crimetype(countryList);
                                }
                            }



                        }else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Select Crime Type");
                            // Binds all strings into an array
                            countryList.add(wp0);
                            if (countryList.size() > 0) {
                                Crimetype(countryList);
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
    private void CheckConnectivity() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            //    new getpay().execute();

            getCrimeType_Api();
           // getCrimeStatus_Api();
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
    public void NotNull() {


        if (S_dateTime == null) {
            S_dateTime = "";
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

        if (S_detected_ == null) {
            S_detected_ = "";
        } else {
        }

        if (yearName == null) {
            yearName = "";
        } else {

            if (yearName.equalsIgnoreCase("0")) {
                yearName = "";
            } else {


            }
        }
        if (SubCat1 == null) {
            SubCat1 = "";
        } else {

            if (SubCat1.equalsIgnoreCase("0")) {
                SubCat1 = "";
            } else {
            }
        }

        if (Cat1 == null) {
            Cat1 = "";
        } else {

            if (Cat1.equalsIgnoreCase("0")) {
                Cat1 = "";
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


    }
    private void CheckConnectivity1() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            NotNull();
            GetCrimeData();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
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
    private void GetCrimeData() {

        progressDialog = new ProgressDialog(AddCrime_View.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        try {
            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");
            S_psid = bb.getString("Psid", "");


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Crimes_Id", "");
            jsonBody.put("CrimeTypeMaster_Id",Cat1);
            jsonBody.put("CrimeSubtypeMaster_Id", ""+SubCat1);
            jsonBody.put("CrimeStatusMaster_Id", "" );
            jsonBody.put("HirarchyID", S_psid);
            jsonBody.put("CrimeYear", "" + yearName);


            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:GetCrimeData" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CrimeListWithFilter, new Response.Listener<String>() {
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
                            tv_totalrecords.setVisibility(View.VISIBLE);
                            JSONArray jArray = object.getJSONArray("CrimeList");
                            int number = jArray.length();
                            tv_totalrecords.setText("Total Crime Records : "+number);
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);



                                    String Id = json_data.getString("Id").toString();
                                    String CrimeNumber = json_data.getString("CrimeNumber").toString();
                                    String PSID = json_data.getString("PSID").toString();
                                    String PSCode = json_data.getString("PSCode").toString();
                                    String CrimeTypeMaster_Id = json_data.getString("CrimeTypeMaster_Id").toString();
                                    String CrimeType = json_data.getString("CrimeType").toString();
                                    String CrimeSubtypeMaster_Id = json_data.getString("CrimeSubtypeMaster_Id").toString();
                                    String CrimeSubtype = json_data.getString("CrimeSubType").toString();
                                    String Latitude = json_data.getString("Latitude").toString();
                                    String Longitude = json_data.getString("Longitude").toString();
                                    String Location = json_data.getString("Location").toString();
                                    String Descr = json_data.getString("Descr").toString();
                                    String DateOfOffence = json_data.getString("DateOfOffence").toString();
                                    String DateOfEntry = json_data.getString("DateOfEntry").toString();
                                    String CrimeStatusMaster_Id = json_data.getString("CrimeStatusMaster_Id").toString();




                                    Crime_helper wp = new Crime_helper(Id, CrimeNumber, PSID, PSCode, CrimeTypeMaster_Id,
                                            CrimeType, CrimeSubtypeMaster_Id, CrimeSubtype, Latitude, Longitude, Location, Descr,
                                            DateOfOffence, DateOfEntry, CrimeStatusMaster_Id);
                                    arraylist.add(wp);
                                }
                                // Pass results to ListViewAdapter Class
                                accidentAdapter = new CrimeAdapter(AddCrime_View.this, arraylist);
                                // Binds the Adapter to the ListView
                                list_acc.setAdapter(accidentAdapter);
                                list_acc.setItemsCanFocus(false);
                                list_acc.setTextFilterEnabled(true);

                            }

                            int count = Type_arraylist_.size();
                            Log.e(" count", "" + count);

                        } else {
                            tv_totalrecords.setVisibility(View.GONE);

                            et_search_activity.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            arraylist.clear();
                            tr_person.setVisibility(View.GONE);
                            list_acc.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            tv_norecords.setText(""+message);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCrime_View.this);
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

}