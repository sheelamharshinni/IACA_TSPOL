package com.tecdatum.iaca_tspolice.DataEntry.Traffic;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by HI on 4/1/2017.
 */

public class VitalF extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String[] no_state = {"Complaint"};
    private String[] state = {"Select Status", "Detected", "Undetected"};
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = VitalF.class.getSimpleName();
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
    ArrayList<Samplemyclass> countryList;
    Button Bt_c_date, Bt_cr_time, Bt_c_vl, Bt_c_rest, Bt_c_submit;
    String S_nearest_Landmarkitem,S_sp,item, item2, tv_Ad, tv_Long, tv_Lat, S_Descr, psJuri, S_Uname, S_Pass, s_Ps_name, S_longitude, S_latitude,
            S_lm, S_vn, Message, S_detected_, S_psid, s_role, S_IMEi, S_CrimeNo, S_dateTime, S_location, Loc, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    EditText et_Locality, et_Desriptn, et_lm_nearest_Landmark,et_Landmark;
    Spinner Sp_sensitv, Category;
    Geocoder geocoder;
    Integer Code1;
    String Message1;
    ArrayList<Samplemyclass> countryList0;
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
    public VitalF() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_lm__vital, container, false);
        Bt_c_vl = (Button) v.findViewById(R.id.btn_lm_vi_vl);
        Bt_c_rest = (Button) v.findViewById(R.id.btn_lm_vi_reset);
        Bt_c_submit = (Button) v.findViewById(R.id.btn_lm_vi_submit);
        et_lm_nearest_Landmark=(EditText)v.findViewById(R.id.et_lm_nearest_Landmark) ;
        et_Landmark = (EditText) v.findViewById(R.id.et_lm_vi_Landmark);
        et_Locality = (EditText) v.findViewById(R.id.et_lm_vi_locality);
        et_Desriptn = (EditText) v.findViewById(R.id.et_lm_vi_Remarks);
        Category = (Spinner) v.findViewById(R.id.spin_lm_vi_it);
        Sp_sensitv = (Spinner) v.findViewById(R.id.spin_lm_vi_sa);
        c_time = (TextView) v.findViewById(R.id.tv_lm_vi_time);
        tv_c_psname = (TextView) v.findViewById(R.id.tv_lm_vi_psname);

        et_lm_nearest_Landmark .setFilters(new InputFilter[]{filter});
        et_Landmark .setFilters(new InputFilter[]{filter});
        et_Locality.setFilters(new InputFilter[]{filter});
        et_Desriptn .setFilters(new InputFilter[]{filter});
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
                Sp_sensitv.setSelection(0);
            }
        });

        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                S_lm = et_Landmark.getText().toString();
                S_Descr = et_Desriptn.getText().toString();
                S_location = et_Locality.getText().toString();
                S_nearest_Landmarkitem= et_lm_nearest_Landmark.getText().toString();
                if (Category != null && Category.getSelectedItem() != null) {
                    if (!(Category.getSelectedItem().toString().trim() == "Select Vital Installation Type")) {
                        if (!(et_Locality.getText().toString().trim().isEmpty())) {
                            if (!(et_Desriptn.getText().toString().trim().isEmpty())) {
                                alertDialogue_Conform();
                            } else {
                                Toast.makeText(getActivity(), "Please Enter Remarks", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Location is Mandatory", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Select Vital Installation Type", Toast.LENGTH_SHORT).show();
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


        return v;
    }
    private void CheckConnectivity1() {

        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            new getpay().execute();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    class getpay extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {
            String SOAP_ACTION = "http://tempuri.org/GetVitalInstallationData";
            String METHOD_NAME = "GetVitalInstallationData";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=GetVitalInstallationData";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            envelope.setOutputSoapObject(request);

            Log.e("1245", S_Pass + "    " + S_IMEi);
            Log.e("request", "" + request);

            try {
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.debug = true;
                Log.e("envelope", "" + envelope);
                httpTransport.call(SOAP_ACTION, envelope);
                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", "" + respone);
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.d(TAG, " Response: \n" + result);
                SoapObject root = (SoapObject) result.getProperty(0);
                // System.out.println("******** : " + root.getPropertyCount());

                Type_arraylist_.clear();
                Subtype_arraylist_.clear();
                ldmk_arraylist_.clear();
                Add_arraylist_.clear();
                Remarks_arraylist_.clear();

                for (int j = 0; j < root.getPropertyCount(); j++) {
                    SoapObject s_deals = (SoapObject) root.getProperty(j);
                    // System.out.println("********Count : " + s_deals.getPropertyCount());
                    for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                        Object property = s_deals.getProperty(i);
                        System.out.println("******** : " + property);

                        Type = s_deals.getProperty("Type").toString();
                        Subtype = s_deals.getProperty("Subtype").toString();
                        ldmk = s_deals.getProperty("Landmark").toString();
                        Add = s_deals.getProperty("Address").toString();
                        Remarks = s_deals.getProperty("Remarks").toString();
                    }

//                    if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
//                            .length() <= 0) {
//                        Remarks_arraylist_.add("");
//                    } else {
//                        Remarks_arraylist_.add(Remarks);
//                    }

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
            } catch (Exception e) {
                // TODO: handle exception
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
                Toast.makeText(getActivity(), "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(getActivity(), "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_LONG).show();
            } else {
                //Log.d(TAG,  "Re "+final_result.toString());

                Log.e(TAG, "Cnumber : " + Remarks_arraylist_);
                Log.e(TAG, "Cnumber : " + Type_arraylist_);
                Log.e(TAG, "Cnumber : " + Subtype_arraylist_);
                Log.e(TAG, "Cnumber : " + ldmk_arraylist_);


                int count = Type_arraylist_.size();

                Log.e(" count", "" + count);
                SetValuesToLayout(count);
                if (Code == 0) {
                } else {
                    Log.e("not sucess", "" + Code);
                    // Toast.makeText(getActivity(),""+Message,Toast.LENGTH_LONG).show();
                }
                Log.e("result", "done");
                //tableview();
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }
    private void SetValuesToLayout(int coun) {

        vdialog = new Dialog(getActivity(), R.style.HiddenTitleTheme);
        vdialog.setContentView(R.layout.vl_lm_vtall);
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

        Log.e("testt", "" + Type_arraylist_.size());
        if ((Type_arraylist_.size()) > 0 | (Type_arraylist_.size()) != 0) {
            Log.e("checked>0", "");

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
            if ((Type_arraylist_.size()) == 1) {

                Log.e("checked", "" + Type_arraylist_.size());
                r1.setVisibility(View.VISIBLE);
                r0.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                t1.setText(Type_arraylist_.get(0));
                dt1.setText(Subtype_arraylist_.get(0));

            }
            if ((Type_arraylist_.size()) == 4) {
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r0.setVisibility(View.VISIBLE);
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
        C_dialog.setContentView(R.layout.vl_lm_vital);
        C_dialog.show();
        TextView d_vn = (TextView) C_dialog.findViewById(R.id.tv_lm_v_vt);
        TextView d_st = (TextView) C_dialog.findViewById(R.id.tv_lm_v_st);
        TextView d_lm = (TextView) C_dialog.findViewById(R.id.tv_lm_v_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_lm_v_add);
        TextView d_dis = (TextView) C_dialog.findViewById(R.id.tv_lm_v_dis);
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_lm_v_pscode);
        pscode.setText(s_Ps_name);

        if (Type_arraylist_.size() > count) {
            d_vn.setText(Type_arraylist_.get(count));
        }
        if (Subtype_arraylist_.size() > count) {
            d_st.setText(Subtype_arraylist_.get(count));
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
        C_dialog.setContentView(R.layout.conform_lm_vital);
        C_dialog.show();
        TextView d_vn = (TextView) C_dialog.findViewById(R.id.tv_lm_v_vt);
        TextView d_lm = (TextView) C_dialog.findViewById(R.id.tv_lm_v_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_lm_v_add);
        TextView d_dis = (TextView) C_dialog.findViewById(R.id.tv_lm_v_dis);


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
    class pay extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {
            String SOAP_ACTION = "http://tempuri.org/SaveVitalInstallations";
            String METHOD_NAME = "SaveVitalInstallations";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx";
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

//            request.addProperty("username", S_Uname);
//            request.addProperty("password", S_Pass);
//            request.addProperty("imei", S_IMEi);
//            request.addProperty("connectionType", "TabletPc");
//            request.addProperty("Maintype", "Vital Installation");
//            request.addProperty("Type", S_vn);
//            request.addProperty("Landmark", S_lm);
//            request.addProperty("Address", S_location);
//            request.addProperty("Latitude", S_latitude);
//            request.addProperty("Longitude", S_longitude);
//            request.addProperty("Remarks", S_Descr);
            request.addProperty("username", S_Uname);
            request.addProperty("password", S_Pass);
            request.addProperty("imei", S_IMEi);
            request.addProperty("connectionType", "TabletPc");
            request.addProperty("SensitiveSubTypeId", Cat1);
            request.addProperty("SensitiveSubType", S_vn);
            request.addProperty("NameOfVitalInstallation", S_lm);
            request.addProperty("Address", S_location);
            request.addProperty("Latitude", S_latitude);
            request.addProperty("Longitude", S_longitude);
            request.addProperty("Remarks", S_Descr);
            request.addProperty("SensitivityLevel", S_sp);
            request.addProperty("NearestLandmark", S_nearest_Landmarkitem);
            envelope.setOutputSoapObject(request);

            Log.e("request", "" + request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            Log.e("ADDCRIME_ENVOLOPE", "" + envelope);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result1 = envelope.getResponse().toString();

                Log.e("result_addcrime", "" + result1);
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
                Toast.makeText(getActivity(), "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(getActivity(), "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (Code1 != null) {
                    if (Code1 == 0) {
                        Log.e("suceess", "" + Message1);
                        C_dialog.dismiss();
                        et_Desriptn.setText("");
                        et_Landmark.setText("");
                         et_lm_nearest_Landmark.setText("");
                        Category.setSelection(0);
                        Sp_sensitv.setSelection(0);
                        S_vn = "";
                        S_lm = "";
                        S_location = "";
                        S_latitude = "";
                        S_longitude = "";
                        S_Descr = "";
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
                        try {

//                            Log.e("not sucess", "" + Message1);
//                            C_dialog.dismiss();
//                            et_Desriptn.setText("");
//                            et_Landmark.setText("");
//
//                            //  Et_yellow.setText("");
//                            Category.setSelection(0);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("" + Message1);
                            alertDialog.setIcon(R.drawable.alertt);
                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // Toast.makeText(getActivity(),""+Message,Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
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

        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            new pay().execute();

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    class getSensitivity extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/SensitivityLevel_Master";
            String METHOD_NAME = "SensitivityLevel_Master";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=SensitivityLevel_Master";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);

            envelope.setOutputSoapObject(request);
            Log.e("request", "" + request);

            try {
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.debug = true;
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
                httpTransport.call(SOAP_ACTION, envelope);
                Log.e("envelope", "" + envelope);
                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", "" + respone);
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.d(TAG, " Response: \n" + result);
                SoapObject root = (SoapObject) result.getProperty(0);
                SoapObject s_deals = (SoapObject) root.getProperty("Data");
                System.out.println("********Count : " + s_deals.getPropertyCount());
                countryList0 = new ArrayList<>();
                countryList0.clear();
                Samplemyclass wb = new Samplemyclass("0", "Select Type of Sensitive Place");
                countryList0.add(wb);
                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    System.out.println("********Count : " + s_deals_1.getPropertyCount());
                    String e_id = s_deals_1.getProperty("Id").toString();
                    String e_n = s_deals_1.getProperty("SensitivityLevel").toString();
                    Samplemyclass wb1 = new Samplemyclass(e_id, e_n);
                    countryList0.add(wb1);
                }
                System.out.println("Sensitivity" + countryList0);


            } catch (Exception e) {
                // TODO: handle exception
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
                Toast.makeText(getActivity(), "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(getActivity(), "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (countryList0 != null) {
                    if (countryList0.size() > 0) {
                        Sensitivity();
                    }
                    if (Code == 0) {
                    } else {
                    }
                }
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }
    private void Sensitivity() {
//        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddReligious.this,R.layout.spinner_item, countryList0);
//        Sp_sensitv.setAdapter(adapt);
//        Sp_sensitv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                int pos = adapterView.getSelectedItemPosition();
//
//                //  Toast.makeText(getApplicationContext(), "" + pos, Toast.LENGTH_SHORT).show();
//
//                if(pos!= 0) {
//                    Sp_sensitv.setSelection(i);
//                    S_sp = (String) Sp_sensitv.getSelectedItem();
//                    Log.e("crime_Genianty",""+S_sp);
//                    //Log.e("Cat1", "" + Cat1);
//                    // Toast.makeText(getApplicationContext(), "" + Cat1, Toast.LENGTH_SHORT).show();
//                    //Toast.makeText(getApplicationContext(), "Crime Type is Mandatory ", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, countryList0);
        Sp_sensitv.setAdapter(adapter);
        Sp_sensitv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    S_sp = country.getName();
                    Log.e("Sp_sensitv", "" + S_sp);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    class getVitalCat extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {

            String SOAP_ACTION = "http://tempuri.org/VitalInstallation_Master";
            String METHOD_NAME = "VitalInstallation_Master";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://www.tecdatum.net/MedakIACAApi/Services/CrimeDataEntry.asmx?op=VitalInstallation_Master";

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);

            envelope.setOutputSoapObject(request);
            Log.e("request", "" + request);

            try {
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.debug = true;
                httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding= \"UTF-8\"?>");
                httpTransport.call(SOAP_ACTION, envelope);
                Log.e("envelope", "" + envelope);
                Log.d(TAG, "HTTP Response: \n" + httpTransport.responseDump);
                SoapObject respone = (SoapObject) envelope.getResponse();
                Log.d("Res", "" + respone);
                SoapObject result = (SoapObject) envelope.bodyIn;
                Log.d(TAG, " Response: \n" + result);
                SoapObject root = (SoapObject) result.getProperty(0);
                SoapObject s_deals = (SoapObject) root.getProperty("Data");
                // System.out.println("********Count : "+ s_deals.getPropertyCount());

                countryList = new ArrayList<Samplemyclass>();
                countryList.clear();
                Samplemyclass wb = new Samplemyclass("0", "Select Vital Installation Type");
                countryList.add(wb);

                for (int i = 0; i < s_deals.getPropertyCount(); i++) {
                    //  Object property = s_deals_1.getProperty(i);
                    SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                    // System.out.println("********Count : " + s_deals_1.getPropertyCount());
                    String e_id = s_deals_1.getProperty("Id").toString();
                    String e_n = s_deals_1.getProperty("VitalInstallationType").toString();
                    Samplemyclass wb1 = new Samplemyclass(e_id, e_n);
                    countryList.add(wb1);
                }
                System.out.println("m" + countryList);
            } catch (Exception e) {
                // TODO: handle exception
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
                Toast.makeText(getActivity(), "Unable to connect to server, Please try again later", Toast.LENGTH_LONG).show();
            } else if (httpexcep) {
                Toast.makeText(getActivity(), "Please check your Internet connection", Toast.LENGTH_LONG).show();
            } else if (genexcep) {
                Toast.makeText(getActivity(), "Please try later", Toast.LENGTH_LONG).show();
            } else {
                if (Code == 0) {
                    if (countryList != null) {
                        if (countryList.size() > 0) {
                            VitaltData();
                        }
                    }

                } else {
                }
            }
            timeoutexcep = false;
            httpexcep = false;
            genexcep = false;
        }
    }
    private void CheckConnectivity2() {

        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            new getVitalCat().execute();
            new getSensitivity().execute();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }
    private void setData() {
        CheckConnectivity2();
//        countryList = new ArrayList<>();
//        countryList.add(new Samplemyclass("0", "Select Vital Installation Type"));
//        countryList.add(new Samplemyclass("1", "Bank and ATM"));
//        countryList.add(new Samplemyclass("2", "Central Govt Office"));
//        countryList.add(new Samplemyclass("3", "State Govt Office"));
//        countryList.add(new Samplemyclass("4", "Hospital"));
//        countryList.add(new Samplemyclass("5", "Hotels & Restaurant"));
//        countryList.add(new Samplemyclass("6", "Bus Depot"));
//        countryList.add(new Samplemyclass("7", "Railway Station"));
//        countryList.add(new Samplemyclass("8", "Electrical Sub Station"));
//        countryList.add(new Samplemyclass("9", "Schools & Colleges"));
//        countryList.add(new Samplemyclass("10", "Companies and Industries"));
//        countryList.add(new Samplemyclass("11", "Private Firm"));
//        countryList.add(new Samplemyclass("12", "IT Companies"));
//        countryList.add(new Samplemyclass("13", "Shopping Mall"));
//        countryList.add(new Samplemyclass("14", "Multiplex"));
//        countryList.add(new Samplemyclass("15", "Movie Theatre"));
//        countryList.add(new Samplemyclass("16", "Public Parks"));
//        countryList.add(new Samplemyclass("17", "Others"));
//        countryList.add(new Samplemyclass("18", "Function Halls"));
//        countryList.add(new Samplemyclass("19", "Lakess"));
    }
    private void VitaltData() {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, countryList);
        Category.setAdapter(adapter);
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getSelectedItemPosition();

                if (pos != 0) {
                    //    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                    Cat1 = country.getId();
                    S_vn = country.getName();
                    Log.e("Cat1", "" + Cat1);
                    Log.e("Cat", "" + S_vn);
                    // Toast.makeText(getActivity(), "" + Cat1, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getActivity(), "Crime Type is Mandatory ", Toast.LENGTH_SHORT).show();
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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