package com.tecdatum.iaca_tspolice.ViewData.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
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
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.ViewData.Adapters.LandmarkAdapter;
import com.tecdatum.iaca_tspolice.ViewData.Helpers.Landmark_helper;
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

public class AddReligious_View extends AppCompatActivity {
    LandmarkAdapter accidentAdapter;
    ArrayList<Landmark_helper> arraylist = new ArrayList<Landmark_helper>();
    ListView list_acc;
    TableRow tr_person;
    TextView tv_norecords;
    TableRow r0, r1, r2, r3, r4, r5;
    EditText et_search_activity;
    TextView t1, t2, t3, t4, t5;
    TextView st1, st2, st3, st4, st5;
    TextView dt1, dt2, dt3, dt4, dt5;
    TextView v1, v2, v3, v4, v5, NoRecords;
Button  btn_acc_submit,btn_acc_reset;

    private String LandmarkMasters = URLS.LandmarkMasters;
    private String LandmarkEntry = URLS.LandmarkEntry;
    private String LandmarkListWithFilter = URLS.LandmarkListWithFilter;
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = AddReligious_View.class.getSimpleName();
    private LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x01;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 50;
    private static final int READ_CAMERA_PERMISSIONS_REQUEST = 20;
    private static final int WRITE_SETTINGS_PERMISSION = 20;
    private boolean active = false;
    protected Location mLastLocation;
    private static int UPDATE_INTERVAL = 60000;
    private boolean mRequestingLocationUpdates = true;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Dialog C_dialog, vdialog;

//    private String[]  countryList0= { "Select Type of Sensitive Place","High Sensitivity","Non Sensitivity",
//            "Low Sensitivity","Medium Sensitivity"};

    ArrayAdapter<String> adapter_state;
    ArrayList<Samplemyclass> countryList = new ArrayList<Samplemyclass>();
    ArrayList<Samplemyclass> countryList0 = new ArrayList<Samplemyclass>();//levl
    ArrayList<Samplemyclass> countryList1 = new ArrayList<Samplemyclass>();//type

    ArrayList<Samplemyclass> lm_cat = new ArrayList<Samplemyclass>();//levl
    ArrayList<Samplemyclass> lm_subcat = new ArrayList<Samplemyclass>();//type


    ArrayList<Samplemyclass> sen_cat = new ArrayList<Samplemyclass>();//levl
    ArrayList<Samplemyclass> sen_subcat = new ArrayList<Samplemyclass>();//type


    Button Bt_c_date, Bt_cr_time, Bt_c_vl, Bt_c_rest, Bt_c_submit;
    TextView d_wp, d_nowp, d_in, d_ph, d_dis, d_sp, d_add;
    EditText et_in, et_r_inDesig, et_lm_nearest_Landmark, et_ph, et_dis, et_Locality, et_lm_PrevIncidentHistory, et_wp;
    String S_in, S_inDesig, S_ph, S_dis, S_add, S_Locality, S_wp, Message, S_psid, S_IMEi, S_Pass, S_Uname, S_latitude, S_longitude, Cat1, item2, SubCat1, item, S_nearest_Landmarkitem, S_PrevIncidentHistory, S_sp_subtype_id, S_sp_subtype, S_sp_id, S_sp, s_Ps_name, formattedDate;
    Integer Code1;
    ProgressDialog progressDialog;
    String Message1;
    ArrayList<String> twp_arraylist_ = new ArrayList<String>();
    ArrayList<String> nwp_arraylist_ = new ArrayList<String>();
    ArrayList<String> add_arraylist_ = new ArrayList<String>();
    ArrayList<String> ni_arraylist_ = new ArrayList<String>();
    ArrayList<String> cNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> Remarks_arraylist_ = new ArrayList<String>();
    ArrayList<String> EnteredDate_arraylist_ = new ArrayList<String>();
    String s_role, S_psCode, twp, nwp, add, ni, cNo, Remarks, EnteredDate;
    ;
    Spinner Sp_tpwk, Sp_stype, Sp_sensitv, Sp_sensitivetype;
    int Code;
    RelativeLayout Rl_Ssubtype, R_subtype;
    TextView tv_c_psname, c_time;
    TextView tv_totalrecords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_religious);
        R_subtype = (RelativeLayout) findViewById(R.id.R_subtype);
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
        Sp_tpwk= (Spinner) findViewById(R.id.sp_accidentType);
        Sp_stype = (Spinner) findViewById(R.id.sp_detected);
        tv_norecords= (TextView) findViewById(R.id.tv_norecords);
        tr_person = (TableRow) findViewById(R.id.tr_person);
        btn_acc_submit = (Button) findViewById(R.id.btn_acc_submit);
        btn_acc_reset = (Button) findViewById(R.id.btn_acc_reset);
        list_acc = (ListView) findViewById(R.id.list_accident);
        String s_OrgName = bb.getString("OrgName", "");
        TextView tv_OrgName = (TextView) findViewById(R.id.tv_distname);
        tv_OrgName.setText("" + s_OrgName);
        tv_totalrecords= (TextView) findViewById(R.id.tv_totalrecords);


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
                Intent i = new Intent(getApplicationContext(),AddReligious_View.class);
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

    }
    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            //new getpay().execute();
            GetReligious();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    private void SetValuesToLayout(int coun) {


        if ((twp_arraylist_.size()) > 0 | (twp_arraylist_.size()) != 0) {
            Log.e("1", "" + twp_arraylist_.size());
            if ((twp_arraylist_.size()) == 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                t3.setText(twp_arraylist_.get(2));
                t4.setText(twp_arraylist_.get(3));
                t5.setText(twp_arraylist_.get(4));
                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                st3.setText(nwp_arraylist_.get(2));
                st4.setText(nwp_arraylist_.get(3));
                st5.setText(nwp_arraylist_.get(4));

                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));

                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }

                if (ni_arraylist_.size() == 3) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                }

                if (ni_arraylist_.size() == 4) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                    dt4.setText(ni_arraylist_.get(3));

                }
                if (ni_arraylist_.size() == 4) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                    dt4.setText(ni_arraylist_.get(3));
                    dt5.setText(ni_arraylist_.get(4));
                }

            }
            if ((twp_arraylist_.size()) == 1) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(twp_arraylist_.get(0));
                st1.setText(nwp_arraylist_.get(0));
                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));
                }

            }
            if ((twp_arraylist_.size()) == 2) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));

                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }


            }
            if ((twp_arraylist_.size()) == 3) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                t3.setText(twp_arraylist_.get(2));
                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                st3.setText(nwp_arraylist_.get(2));
                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));

                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }

                if (ni_arraylist_.size() == 3) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                }
            }
            if ((twp_arraylist_.size()) == 4) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);

                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                t3.setText(twp_arraylist_.get(2));
                t4.setText(twp_arraylist_.get(3));
                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                st3.setText(nwp_arraylist_.get(2));
                st4.setText(nwp_arraylist_.get(3));

                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));
                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }

                if (ni_arraylist_.size() == 3) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                }

                if (ni_arraylist_.size() == 4) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                    dt4.setText(ni_arraylist_.get(3));

                }
            }

            if ((twp_arraylist_.size()) > 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                Log.e("yes", "" + twp_arraylist_.size());
                t1.setText(twp_arraylist_.get(0));
                t2.setText(twp_arraylist_.get(1));
                t3.setText(twp_arraylist_.get(2));
                t4.setText(twp_arraylist_.get(3));
                t5.setText(twp_arraylist_.get(4));

                st1.setText(nwp_arraylist_.get(0));
                st2.setText(nwp_arraylist_.get(1));
                st3.setText(nwp_arraylist_.get(2));
                st4.setText(nwp_arraylist_.get(3));
                st5.setText(nwp_arraylist_.get(4));


                if (ni_arraylist_.size() == 1) {
                    dt1.setText(ni_arraylist_.get(0));

                }
                if (ni_arraylist_.size() == 2) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                }

                if (ni_arraylist_.size() == 3) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                }

                if (ni_arraylist_.size() == 4) {
                    dt1.setText(ni_arraylist_.get(0));
                    dt2.setText(ni_arraylist_.get(1));
                    dt3.setText(ni_arraylist_.get(2));
                    dt4.setText(ni_arraylist_.get(3));
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
    private void alertDialogue_C1(int count) {
        C_dialog = new Dialog(AddReligious_View.this, R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_cnfm_rel);
        C_dialog.show();

        TextView d_wp = (TextView) C_dialog.findViewById(R.id.tv_religiou_wp);
        TextView d_nowp = (TextView) C_dialog.findViewById(R.id.tv_religiou_name_wp);
        TextView d_in = (TextView) C_dialog.findViewById(R.id.tv_religiou_in);
        TextView d_ph = (TextView) C_dialog.findViewById(R.id.tv_religiou_ph);
        TextView d_dis = (TextView) C_dialog.findViewById(R.id.tv_religiou_dis);
        TextView d_sp = (TextView) C_dialog.findViewById(R.id.tv_religiou_sp);
        TextView d_add = (TextView) C_dialog.findViewById(R.id.tv_religiou_add);
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_religiou_pscode);

        pscode.setText(s_Ps_name);


        if (twp_arraylist_.size() > count) {
            d_wp.setText(twp_arraylist_.get(count));
        }
        if (nwp_arraylist_.size() > count) {
            d_nowp.setText(nwp_arraylist_.get(count));
        }
        if (ni_arraylist_.size() > count) {
            d_in.setText(ni_arraylist_.get(count));
        }
        if (cNo_arraylist_.size() > count) {
            d_ph.setText(cNo_arraylist_.get(count));
        }
        if (Remarks_arraylist_.size() > count) {
            d_dis.setText(Remarks_arraylist_.get(count));
        }
        if (EnteredDate_arraylist_.size() > count) {
            d_sp.setText(EnteredDate_arraylist_.get(count));
        }
        if (add_arraylist_.size() > count) {
            d_add.setText(add_arraylist_.get(count));
        }

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_r_Confm);


        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });

    }
    private void setData() {

        if (isConnectingToInternet(AddReligious_View.this)) {
            CheckConnectivity();
        }

    }
    private void Worship(ArrayList<Samplemyclass> Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        Sp_tpwk.setAdapter(adapter);
        Sp_tpwk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();

                //  Toast.makeText(getApplicationContext(), "" + pos, Toast.LENGTH_SHORT).show();



                if (pos != 0) {
                    R_subtype.setVisibility(View.VISIBLE);
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    SubCat1="";
                    Cat1 = country.getId();
                    item2 = country.getName();
                    Log.e("Cat1", "" + Cat1);
                    Log.e("Cat", "" + item2);

                    //  item2 = parent.getSelectedItem().toString();

                    Log.e("Cat1", "" + Cat1);
                    if (Cat1 != null) {
                        getSubworship_Api(Cat1);
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void Subworship(ArrayList<Samplemyclass> Str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getApplicationContext(), R.layout.spinner_item, Str1);
        Sp_stype.setAdapter(adapter);
        Sp_stype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();

                if (pos != 0) {
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    SubCat1 = country.getId();
                    item = country.getName();
                    Log.e("SubCat", "" + item);
                    Log.e("SubCat1", "" + SubCat1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void getworship_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "1");
            jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:AccidentMasters" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:AccidentMasters" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        lm_cat.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb1 = new Samplemyclass("0", "Select Landmark Category");
                                // Binds all strings into an array
                                lm_cat.add(wb1);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    lm_cat.add(wp);
                                }
                                if (lm_cat.size() > 0) {
                                    Worship(lm_cat);
                                }
                            }


                        } else {
                            Samplemyclass wb1 = new Samplemyclass("0", "Select Landmark Category");
                            // Binds all strings into an array
                            lm_cat.add(wb1);
                            if (lm_cat.size() > 0) {
                                Worship(lm_cat);

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

    private void getSubworship_Api(String SWtypeid) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "2");
            jsonBody.put("Id", "" + SWtypeid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:RoadType" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:RoadType" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        lm_subcat.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb1 = new Samplemyclass("0", "Select Landmark Sub Category");
                                // Binds all strings into an array
                                lm_subcat.add(wb1);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    lm_subcat.add(wp);
                                }
                                if (lm_subcat.size() > 0) {
                                    Subworship(lm_subcat);
                                }
                            }


                        } else {
                            Samplemyclass wb1 = new Samplemyclass("0", "Select Landmark Sub Category");
                            // Binds all strings into an array
                            lm_subcat.add(wb1);
                            if (lm_subcat.size() > 0) {
                                Subworship(lm_subcat);

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
    private void CheckConnectivity() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            //    new getpay().execute();

            getworship_Api();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    private void GetReligious() {
        progressDialog = new ProgressDialog(AddReligious_View.this);
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();


        if(Cat1!=null){
        }else {
            Cat1="";
        }
        if(SubCat1!=null){
        }else {
            SubCat1="";
        }

        try {
            SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");
            S_psid = bb.getString("Psid", "");


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("HirarchyID", S_psid);
            jsonBody.put("LandMarkType_Id", Cat1);
            jsonBody.put("LandSubMarkType_Id", SubCat1);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:LandmarkList" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LandmarkListWithFilter, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:LandmarkList" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {
                            arraylist.clear();
                            et_search_activity.setVisibility(View.VISIBLE);
                            tv_norecords.setVisibility(View.GONE);
                            tr_person.setVisibility(View.VISIBLE);
                            list_acc.setVisibility(View.VISIBLE);
                            JSONArray jArray = object.getJSONArray("LandMarkList");
                            int number = jArray.length();
                            progressDialog.dismiss();

                            tv_totalrecords.setVisibility(View.VISIBLE);

                            tv_totalrecords.setText("Total LandMark Records : "+number);
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getApplicationContext(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String r_id = json_data.getString("Id").toString();
                                    String LandMarkMaster_Id = json_data.getString("LandMarkMaster_Id").toString();
                                    String LandMarkMaster_Type = json_data.getString("LandMarkMaster_Type").toString();
                                    String LandMarkMaster_SubType = json_data.getString("LandMarkMaster_SubType").toString();
                                    String NameoWorshipPlace = json_data.getString("NameoWorshipPlace").toString();
                                    String AddressWorshipPlace = json_data.getString("AddressWorshipPlace").toString();
                                    String Lattitude1 = json_data.getString("Lattitude1").toString();
                                    String Longitude1 = json_data.getString("Longitude1").toString();

                                    String NameoftheIncharge = json_data.getString("NameoftheIncharge").toString();
                                    String InchargeDesignation = json_data.getString("InchargeDesignation").toString();
                                    String ContactNo = json_data.getString("ContactNo").toString();
                                    String Remarks = json_data.getString("Remarks").toString();
                                    String PSId = json_data.getString("PSId").toString();
                                    String PoliceStation = json_data.getString("PoliceStation").toString();
                                    String sensitivityLevelId = json_data.getString("sensitivityLevelId").toString();
                                    String SensitivitySubLevelId = json_data.getString("SensitivitySubLevelId").toString();


                                    Landmark_helper wp = new Landmark_helper(r_id, LandMarkMaster_Id, LandMarkMaster_Type,
                                            LandMarkMaster_SubType, NameoWorshipPlace, AddressWorshipPlace, Lattitude1, Longitude1,
                                            NameoftheIncharge, InchargeDesignation, ContactNo, Remarks, PSId, PoliceStation,
                                            sensitivityLevelId, SensitivitySubLevelId);
                                    arraylist.add(wp);
                                }
                                // Pass results to ListViewAdapter Class
                                accidentAdapter = new LandmarkAdapter(AddReligious_View.this, arraylist);
                                // Binds the Adapter to the ListView
                                list_acc.setAdapter(accidentAdapter);
                                list_acc.setItemsCanFocus(false);
                                list_acc.setTextFilterEnabled(true);

                            }

                        } else {

                            tv_totalrecords.setVisibility(View.GONE);

                            et_search_activity.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            arraylist.clear();
                            tr_person.setVisibility(View.GONE);
                            list_acc.setVisibility(View.GONE);
                            tv_norecords.setVisibility(View.VISIBLE);
                            tv_norecords.setText(""+message);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddReligious_View.this);
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


}
