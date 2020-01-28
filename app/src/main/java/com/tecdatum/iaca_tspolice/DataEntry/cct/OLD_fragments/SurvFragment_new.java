package com.tecdatum.iaca_tspolice.DataEntry.cct.OLD_fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.text.InputType;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
import com.tecdatum.iaca_tspolice.Adapter.CustomDatePicker;
import com.tecdatum.iaca_tspolice.Constants.URLS;
import com.tecdatum.iaca_tspolice.Helper.Samplemyclass;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
//all are madatory

public class SurvFragment_new extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String cctvMasters = URLS.cctvMasters;
    private String cctvList = URLS.cctvList;
    private String cctvEntry = URLS.cctvEntry;
    private String CommunityEntry = URLS.CommunityEntry;
    String Message1;
    int Code2;
    String Message2;
    boolean timeoutexcep = false, httpexcep = false, genexcep = false;
    private static final String TAG = SurvFragment_new.class.getSimpleName();
    private LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x01;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 50;
    private static final int READ_CAMERA_PERMISSIONS_REQUEST = 20;
    private static final int WRITE_SETTINGS_PERMISSION = 20;
    private boolean active = false;
    protected Location mLastLocation;
    private static int UPDATE_INTERVAL = 30000;
    private boolean mRequestingLocationUpdates = true;
    int Code;
    String CameraFixedDate, NoOfCamerasCoverd, NoCameraInstalled, StorageinDays, CommunityName;
    ArrayList<String> CameraFixedDate_arraylist_ = new ArrayList<String>();
    ArrayList<String> NoOfCamerasCoverd_arraylist_ = new ArrayList<String>();
    ArrayList<String> NoCameraInstalled_arraylist_ = new ArrayList<String>();
    ArrayList<String> StorageinDays_arraylist_ = new ArrayList<String>();
    ArrayList<String> CommunityName_arraylist_ = new ArrayList<String>();

    ArrayList<String> CGp_arraylist_ = new ArrayList<String>();
    ArrayList<String> SecNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> PoleNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> LandMark_arraylist_ = new ArrayList<String>();
    ArrayList<String> Add_arraylist_ = new ArrayList<String>();
    ArrayList<String> Fixed_arraylist_ = new ArrayList<String>();
    ArrayList<String> PTZ_arraylist_ = new ArrayList<String>();
    ArrayList<String> IR_arraylist_ = new ArrayList<String>();
    ArrayList<String> Other_arraylist_ = new ArrayList<String>();
    ArrayList<String> InchargeName_arraylist_ = new ArrayList<String>();
    ArrayList<String> InchargeNo_arraylist_ = new ArrayList<String>();
    ArrayList<String> Remarks_arraylist_ = new ArrayList<String>();
    ArrayList<String> CCTVCapacity_arraylist_ = new ArrayList<String>();
    ArrayList<String> Specification_arraylist_ = new ArrayList<String>();
    ArrayList<String> CCTVType_arraylist_ = new ArrayList<String>();
    ArrayList<String> status_arraylist_ = new ArrayList<String>();
    ProgressDialog progressDialog;
    Geocoder geocoder;

    String CGp, status, PoleNo, SecNo, LandMark, CCTVType, Add, Fixed, PTZ, IR, Other, InchargeName, InchargeNo, CCTVCapacity, Specification, Remarks;
    String S_CommunityGroup_Name, S_NoOfCamerasCoverd, S_NoCameraInstalled, S_StorageinDays, S_Uname, S_Pass, s_Ps_name, S_longitude, S_latitude,
            Code_new, Message, S_detected_, s_Ps_name1, S_psid1, S_psid, S_Orgid, s_role, formattedDate, S_IMEi, S_CrimeNo, S_dateTime, S_location,
            Loc, Cat1, SubCat1, provider, S_cCat, S_cSubca;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    TextView tv_c_psname, c_time;
    Button Bt_c_rest, Bt_acc_date, Bt_c_submit, Bt_c_vl;
    View V;
    EditText cg, pNo, secNo, lm, location;
    String S_cg, S_pNo, S_secNo, S_lm;
    Dialog C_dialog, vl_dialog;
    TextView tv_lat, tv_lng;
    Spinner spin_cctvspecificationtype, spin_cctvcapacitytype, spin_cctvfundstype, spin_pslink, spin_vendor, spin_cctvreason, spin_cctvstatus, spin_cctvtype;

    CustomDatePicker custom1;
    String sector_id, sector_name;
    Spinner spin_sector;
    Spinner spin_ps;
    String HierarchyId;
    ArrayList<Samplemyclass> al_ps = new ArrayList<>();
    ArrayList<Samplemyclass> al_sector = new ArrayList<>();
    ArrayList<Samplemyclass> al_vendor = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvreason = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvstatus = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvtype = new ArrayList<>();
    ArrayList<Samplemyclass> al_pslink = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvfunds = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvcapacity = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvtypespecification = new ArrayList<>();
    // ArrayList<CCTVTypes_WithImages> String_CCTVTypes_WithImages = new ArrayList<>();
    final ArrayList<Samplemyclass> al = new ArrayList<Samplemyclass>();

    String S_cctv_VInchargeNo, S_cctv_VInchargeName, S_cctv_VInchargeAdress, S_cctv_NVRIPAddress, S_cctv_ChannelNo, S_cctv_IpAddress, S_cctv_InchargeName, S_cctv_InchargeNo, S_cctv_Remarks;
    EditText et_cctv_VInchargeNo, et_cctv_VInchargeName, et_cctv_VInchargeAdress, et_cctv_locality, et_cctv_NVRIPAddress,
            et_cctv_ChannelNo, et_cctv_IpAddress, et_cctv_InchargeName, et_cctv_InchargeNo, et_cctv_Remarks;
    String S_cctv_locality, pslink_id, pslink_name, vendor_id, vendor_name, cctvreason_id, cctvreason_name, cctvstatus_id, cctvstatus_name,
            cctvtype_id, cctvtype_name, cctvfunds_id, cctvfunds_name, cctvcapacity_id, cctvcapacity_name,
            cctvspecification_id, cctvspecification_name;
    TableRow tr_reasonofntworking;
    RadioGroup rg_specs, rg_specs1;
    RadioButton rd_Sector, rd_Locality, rd_Sector1, rd_Locality1;
    RelativeLayout rl_reason;

    RelativeLayout rl_CommunityGrp;
    CheckBox button[];
    TextView tv_name[];
    TextView tv_discription[];
    ImageView iv_image[];
    GridLayout iteamsList_name;
    GridLayout iteamsList_discription;
    GridLayout iteamsList_image;
    GridLayout iteamsList;
    Integer Length_checbox;
    String string, string1;

    String S_cctv_secno;
    RelativeLayout rl_communitygroup;
    EditText et_cctv_secno;
    LinearLayout ll_vendorOthers;

    LinearLayout ll_nenusaitham;
    EditText et_Communityname, et_cctv_StorageinDays, et_cctv_NoOfCamerasCoverd, et_cctv_NoCameraInstalled;
    ArrayList<Samplemyclass> al_cctvfouningtype = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvyear = new ArrayList<>();
    ArrayList<Samplemyclass> al_cctvmnth = new ArrayList<>();
    Spinner spin_cctvyear, spin_cctvmnth, spin_cctvfoundingtype;
    String S_cctv_FoundingSource, S_cctv_Specifications, cctvyear_id, cctvyear_name, cctvmnth_name, cctvmnth_id, cctvfounding_id, cctvfounding_name;
    EditText et_cctv_FoundingSource, et_cctv_Specifications;
    Button btn_acci_addcommunity;
    ArrayList<Samplemyclass> arrayList, arrayList2;
    EditText tv_cctv_CommunityName;
    String S_cctv_CommunityName;
    Spinner spin_ps_community;
    ArrayList<Samplemyclass> al_ps_community = new ArrayList<>();
    private String blockCharacterSet = "'~#^|$%&*![]%";
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    public SurvFragment_new() {
        // Required empty public constructor
    }

    ArrayList<String> arraylist_string = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_surveillance, container, false);

        et_cctv_VInchargeAdress = (EditText) v.findViewById(R.id.et_cctv_VInchargeAdress);
        et_cctv_VInchargeName = (EditText) v.findViewById(R.id.et_cctv_VInchargeName);
        et_cctv_VInchargeNo = (EditText) v.findViewById(R.id.et_cctv_VInchargeNo);
        ll_vendorOthers = (LinearLayout) v.findViewById(R.id.ll_vendorOthers);
        btn_acci_addcommunity = (Button) v.findViewById(R.id.btn_acci_addcommunity);
        tv_c_psname = (TextView) v.findViewById(R.id.tv_cctv_psname);
        c_time = (TextView) v.findViewById(R.id.tv_cctv_time);
        tv_lat = (TextView) v.findViewById(R.id.tv_lat);
        tv_lng = (TextView) v.findViewById(R.id.tv_lng);
        // cg = (EditText) v.findViewById(R.id.et_cctv_cg);
        pNo = (EditText) v.findViewById(R.id.et_cctv_pn);
        secNo = (EditText) v.findViewById(R.id.et_cctv_sn);
        lm = (EditText) v.findViewById(R.id.et_cctv_lm);
        location = (EditText) v.findViewById(R.id.et_cctv_ln);
        iteamsList = (GridLayout) v.findViewById(R.id.ll_specification);
        iteamsList_name = (GridLayout) v.findViewById(R.id.ll_text);
        iteamsList_discription = (GridLayout) v.findViewById(R.id.ll_discr);
        iteamsList_image = (GridLayout) v.findViewById(R.id.ll_image);
        et_cctv_IpAddress = (EditText) v.findViewById(R.id.et_cctv_IpAddress);
        et_cctv_InchargeName = (EditText) v.findViewById(R.id.et_cctv_InchargeName);
        et_cctv_InchargeNo = (EditText) v.findViewById(R.id.et_cctv_InchargeNo);
        et_cctv_Remarks = (EditText) v.findViewById(R.id.et_cctv_Remarks);
        et_cctv_locality = (EditText) v.findViewById(R.id.et_cctv_locality);
        et_cctv_NVRIPAddress = (EditText) v.findViewById(R.id.et_cctv_NVRIPAddress);
        et_cctv_ChannelNo = (EditText) v.findViewById(R.id.et_cctv_ChannelNo);
        rl_CommunityGrp = (RelativeLayout) v.findViewById(R.id.rl_CommunityGrp);

        rl_communitygroup = (RelativeLayout) v.findViewById(R.id.rl_communitygroup);
        et_cctv_secno = (EditText) v.findViewById(R.id.et_cctv_secno);

        rg_specs = (RadioGroup) v.findViewById(R.id.rg_specs);
        //  rd_Sector = (RadioButton) v.findViewById(R.id.rd_Sector);
        //  rd_Locality = (RadioButton) v.findViewById(R.id.rd_Locality);

        location.setFilters(new InputFilter[]{filter});
        lm.setFilters(new InputFilter[]{filter});
        et_cctv_locality.setFilters(new InputFilter[]{filter});
        et_cctv_Remarks.setFilters(new InputFilter[]{filter});
        et_cctv_InchargeName.setFilters(new InputFilter[]{filter});
        et_cctv_VInchargeAdress.setFilters(new InputFilter[]{filter});
        et_cctv_VInchargeName.setFilters(new InputFilter[]{filter});

        btn_acci_addcommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Community_Entry();
            }
        });

        rg_specs1 = (RadioGroup) v.findViewById(R.id.rg_specs1);
        rd_Sector1 = (RadioButton) v.findViewById(R.id.rd_Sector1);
        rd_Locality1 = (RadioButton) v.findViewById(R.id.rd_Locality1);
        spin_cctvtype = (Spinner) v.findViewById(R.id.spin_cctvtype);
        spin_cctvstatus = (Spinner) v.findViewById(R.id.spin_cctvstatus);
        spin_cctvreason = (Spinner) v.findViewById(R.id.spin_cctvreason);
        spin_vendor = (Spinner) v.findViewById(R.id.spin_vendor);
        spin_pslink = (Spinner) v.findViewById(R.id.spin_pslink);
        spin_sector = (Spinner) v.findViewById(R.id.spin_cctvsector);
        spin_ps = (Spinner) v.findViewById(R.id.spin_ps);


        spin_cctvcapacitytype = (Spinner) v.findViewById(R.id.spin_cctvcapacitytype);

        spin_cctvspecificationtype = (Spinner) v.findViewById(R.id.spin_cctvspecificationtype);
        spin_cctvfundstype = (Spinner) v.findViewById(R.id.spin_cctvfundstype);
        spin_cctvfoundingtype = (Spinner) v.findViewById(R.id.spin_cctvfoundingtype);
        spin_cctvmnth = (Spinner) v.findViewById(R.id.spin_cctvmnth);
        spin_cctvyear = (Spinner) v.findViewById(R.id.spin_cctvyear);
        et_cctv_Specifications = (EditText) v.findViewById(R.id.et_cctv_Specifications);
        et_cctv_FoundingSource = (EditText) v.findViewById(R.id.et_cctv_FoundingSource);

        rl_reason = (RelativeLayout) v.findViewById(R.id.rl_reason);
        Bt_c_vl = (Button) v.findViewById(R.id.btn_cctv_vl);
        Bt_c_rest = (Button) v.findViewById(R.id.btn_cctv_reset);
        Bt_c_submit = (Button) v.findViewById(R.id.btn_cctv_submit);
        TextView name_cctv = (TextView) v.findViewById(R.id.name_cctv);

        name_cctv.setText("CCTV Data Entry");
        V = v;

        Bt_acc_date = (Button) v.findViewById(R.id.btn_acci_date);

//        rg_specs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int selectedId = rg_specs.getCheckedRadioButtonId();
//                RadioButton radiolngButton = (RadioButton) V.findViewById(selectedId);
//
//                Log.e("radiolngButton", "" + radiolngButton.getText().toString());
//
//
//                if (radiolngButton.getText().toString().equalsIgnoreCase("Locality")) {
//                    et_cctv_locality.setVisibility(View.VISIBLE);
//                    rl_CommunityGrp.setVisibility(View.GONE);
//                    sector_id = "";
//                    sector_name = "";
//                    spin_sector.setSelection(0);
//                }
//                if (radiolngButton.getText().toString().equalsIgnoreCase("Sector")) {
//                    et_cctv_locality.setVisibility(View.GONE);
//                    rl_CommunityGrp.setVisibility(View.VISIBLE);
//                    S_cctv_locality = "";
//                    et_cctv_locality.setText("");
//                }
//            }
//        });

        rg_specs1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = rg_specs1.getCheckedRadioButtonId();
                RadioButton radiolngButton = (RadioButton) V.findViewById(selectedId);

                Log.e("radiolngButton", "" + radiolngButton.getText().toString());

                if (radiolngButton.getText().toString().equalsIgnoreCase("Community Group")) {
                    et_cctv_secno.setVisibility(View.GONE);
                    rl_communitygroup.setVisibility(View.VISIBLE);
                    S_cctv_secno = "";
                    et_cctv_secno.setText("");
                }
                if (radiolngButton.getText().toString().equalsIgnoreCase("Government Sector No.")) {
                    et_cctv_secno.setVisibility(View.VISIBLE);
                    rl_communitygroup.setVisibility(View.GONE);
                    cctvfunds_id = "";
                    cctvfunds_name = "";
                    spin_cctvfundstype.setSelection(0);
                }
            }
        });
        Bt_c_vl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckConnectivity1();
            }
        });
        Bt_c_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent kk = new Intent(getActivity(), CCtv_Tabs.class);
                startActivity(kk);
                // cg.setText("");
                pNo.setText("");
                secNo.setText("");
                lm.setText("");
                al.clear();
                et_cctv_secno.setText("");

//                et_Communityname.setText("");
//                et_cctv_StorageinDays.setText("");
//                et_cctv_NoOfCamerasCoverd.setText("");
//                et_cctv_NoCameraInstalled.setText("");

                et_cctv_VInchargeNo.setText("");
                et_cctv_VInchargeName.setText("");
                et_cctv_VInchargeAdress.setText("");
                et_cctv_NVRIPAddress.setText("");
                et_cctv_ChannelNo.setText("");
                et_cctv_IpAddress.setText("");
                et_cctv_InchargeName.setText("");
                et_cctv_InchargeNo.setText("");
                et_cctv_Remarks.setText("");
                spin_pslink.setSelection(0);
                spin_vendor.setSelection(0);
                Bt_acc_date.setText("" + "CCTV Installation Date");
                et_cctv_secno.setText("");
                spin_sector.setSelection(0);
                spin_cctvreason.setSelection(0);
                al_cctvtypespecification.clear();
                spin_cctvstatus.setSelection(0);
                spin_cctvtype.setSelection(0);
                spin_cctvfundstype.setSelection(0);
                spin_cctvcapacitytype.setSelection(0);

                Log.e(TAG, "TEST" + Length_checbox);
                Log.e(TAG, "TESTT" + Length_checbox);


            }


        });
        Bt_c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // S_cg = cg.getText().toString();
                S_pNo = pNo.getText().toString();
                S_secNo = secNo.getText().toString();
                S_lm = lm.getText().toString();
                S_location = location.getText().toString();
                S_cctv_secno = et_cctv_secno.getText().toString();

                S_cctv_IpAddress = et_cctv_IpAddress.getText().toString();
                S_cctv_InchargeName = et_cctv_InchargeName.getText().toString();
                S_cctv_InchargeNo = et_cctv_InchargeNo.getText().toString();
                S_cctv_Remarks = et_cctv_Remarks.getText().toString();
                S_cctv_NVRIPAddress = et_cctv_NVRIPAddress.getText().toString();
                S_cctv_ChannelNo = et_cctv_ChannelNo.getText().toString();
                S_cctv_locality = et_cctv_locality.getText().toString();
                S_cctv_VInchargeNo = et_cctv_VInchargeNo.getText().toString();
                S_cctv_VInchargeName = et_cctv_VInchargeName.getText().toString();
                S_cctv_VInchargeAdress = et_cctv_VInchargeAdress.getText().toString();
//
//                S_StorageinDays = et_cctv_StorageinDays.getText().toString();
//                S_NoOfCamerasCoverd = et_cctv_NoOfCamerasCoverd.getText().toString();
//                S_NoCameraInstalled = et_cctv_NoCameraInstalled.getText().toString();
//                S_CommunityGroup_Name= et_Communityname.getText().toString();
                S_cctv_Specifications = et_cctv_Specifications.getText().toString();
                S_cctv_FoundingSource = et_cctv_FoundingSource.getText().toString();



                al.clear();

                string = "";
                arrayList2 = SubmitClicked();
                for (int i = 0; i < arrayList2.size(); i++) {
                    if (i != 0) string = string + ",";
                    string = string + arrayList2.get(i).getId() + "";
                    Log.d("Specificatins", string);
                }
                string1 = "";
                arrayList2.clear();
                arrayList2 = SubmitClicked();
                for (int i = 0; i < arrayList2.size(); i++) {
                    if (i != 0) string1 = string1 + ",";
                    string1 = string1 + arrayList2.get(i).getName() + "";

                }
                Log.e("result7", "" + string);
                Log.e("result7", "" + string1);

                if (spin_ps != null && spin_ps.getSelectedItem() != null) {
                    if (!(spin_ps.getSelectedItem().toString().trim() == "Select PS")) {
                        if (spin_cctvtype != null && spin_cctvtype.getSelectedItem() != null) {
                            if (!(spin_cctvtype.getSelectedItem().toString().trim() == "Select Camera Type")) {
                                if (spin_cctvstatus != null && spin_cctvstatus.getSelectedItem() != null) {
                                    if (!(spin_cctvstatus.getSelectedItem().toString().trim() == "Select Working Status")) {


                                        if (cctvstatus_id.equalsIgnoreCase("2")) {

                                            if (spin_cctvreason != null && spin_cctvreason.getSelectedItem() != null) {
                                                if (!(spin_cctvreason.getSelectedItem().toString().trim() ==
                                                        "Select Reason Of Not working")) {

                                                    if (spin_cctvyear != null && spin_cctvyear.getSelectedItem() != null) {
                                                        if (!(spin_cctvyear.getSelectedItem().toString().trim() ==
                                                                "Select Year")) {
                                                            //strat
                                                            if (cctvmnth_name != null && cctvyear_name != null) {
                                                                String valid_until = cctvmnth_name + "/" + cctvyear_name;
                                                                SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
                                                                Date strDate = null;
                                                                try {
                                                                    strDate = sdf.parse(valid_until);
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                if (new Date().after(strDate)) {
                                                                    Notnull_Crime();
                                                                    alertDialogue_Conform();

                                                                } else {
                                                                    Toast.makeText(getActivity(), "Select Proper Month and Year", Toast.LENGTH_SHORT).show();

                                                                }
                                                                //end
                                                            } else {
                                                                Notnull_Crime();
                                                                alertDialogue_Conform();
                                                            }
                                                        } else {
                                                            Notnull_Crime();
                                                            alertDialogue_Conform();
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getActivity(), "Select Reason Of Not working", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            if (spin_cctvyear != null && spin_cctvyear.getSelectedItem() != null) {
                                                if (!(spin_cctvyear.getSelectedItem().toString().trim() ==
                                                        "Select Year")) {
                                                    //strat
                                                    if (cctvmnth_name != null && cctvyear_name != null) {
                                                        String valid_until = cctvmnth_name + "/" + cctvyear_name;
                                                        SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
                                                        Date strDate = null;
                                                        try {
                                                            strDate = sdf.parse(valid_until);
                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if (new Date().after(strDate)) {
                                                            Notnull_Crime();
                                                            alertDialogue_Conform();

                                                        } else {
                                                            Toast.makeText(getActivity(), "Select Proper Month and Year", Toast.LENGTH_SHORT).show();

                                                        }
                                                        //end
                                                    } else {
                                                        Notnull_Crime();
                                                        alertDialogue_Conform();
                                                    }
                                                } else {
                                                    Notnull_Crime();
                                                    alertDialogue_Conform();
                                                }
                                            }

                                        }


                                    } else {
                                        Toast.makeText(getActivity(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Please Select Working Status", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please Select Camera Type", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Select PS", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Select PS", Toast.LENGTH_SHORT).show();
                }

                // }
            }

            public ArrayList<Samplemyclass> SubmitClicked() {

                for (int i = 0; i < al_cctvtypespecification.size(); i++) {
                    final int finalI = i;
//                    button[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                        }
//                    });
                    if (button[i].isChecked()) {
                        al.add(al_cctvtypespecification.get(finalI));
                    } else {

                    }
                }
                Log.d("Specificatins test", "" + al);
                return al;
            }
        });
        SharedPreferences bb = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        S_Uname = bb.getString("UserName", "");
        S_Pass = bb.getString("password", "");
        S_IMEi = bb.getString("imei", "");
        S_psid = bb.getString("Psid", "");
        s_Ps_name = bb.getString("Psname", "");
        S_psid1 = bb.getString("Psid", "");
        s_Ps_name1 = bb.getString("Psname", "");
        s_role = bb.getString("Role", "");
        S_Orgid = bb.getString("Orgid", "");
        HierarchyId = bb.getString("HierarchyId", "");
        tv_c_psname.setText(s_Ps_name);

        if (S_Orgid.equalsIgnoreCase("31") | S_Orgid.equalsIgnoreCase("33") | S_Orgid.equalsIgnoreCase("38")) {
            et_cctv_locality.setVisibility(View.GONE);
            rl_CommunityGrp.setVisibility(View.VISIBLE);
            S_cctv_locality = "";
            et_cctv_locality.setText("");
        } else {
            et_cctv_locality.setVisibility(View.VISIBLE);
            rl_CommunityGrp.setVisibility(View.GONE);
            sector_id = "";
            sector_name = "";
            spin_sector.setSelection(0);
        }

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
        InitialConnectivity();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
        return v;
    }

    private void Sector(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_sector.setAdapter(adapter);
            spin_sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        sector_id = country.getId();
                        sector_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void PSS(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_ps.setAdapter(adapter);
            spin_ps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                        S_psid = country.getId();
                        s_Ps_name = country.getName();
                        getSectorType_Api();
                        getCCTVFunds_Api();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void PSS_Community(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_ps_community.setAdapter(adapter);
            spin_ps_community.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();
                        S_psid = country.getId();
                        s_Ps_name = country.getName();
                        getSectorType_Api();
                        getCCTVFunds_Api();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void Vendor(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_vendor.setAdapter(adapter);
            spin_vendor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        vendor_id = country.getId();
                        vendor_name = country.getName();

//                        if (vendor_id.equalsIgnoreCase("10")) {
//                            ll_vendorOthers.setVisibility(View.VISIBLE);
//                        } else {
//                            ll_vendorOthers.setVisibility(View.GONE);
//
//                            et_cctv_VInchargeNo.setText("");
//                            et_cctv_VInchargeName.setText("");
//                            et_cctv_VInchargeAdress.setText("");
//                            S_cctv_VInchargeAdress = "";
//                            S_cctv_VInchargeName = "";
//                            S_cctv_VInchargeNo = "";
//                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVreason(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvreason.setAdapter(adapter);
            spin_cctvreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvreason_id = country.getId();
                        cctvreason_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVFunds(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvfundstype.setAdapter(adapter);
            spin_cctvfundstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvfunds_id = country.getId();
                        cctvfunds_name = country.getName();
                        Log.e("cctvfunds_id", "" + cctvfunds_id);

                        if (cctvfunds_id.equalsIgnoreCase("6")) {
                            //  ll_nenusaitham.setVisibility(View.VISIBLE);
                        } else {
                            // ll_nenusaitham.setVisibility(View.GONE);

                        }
                        //Toast.makeText(getActivity(), "" + cctvfunds_id, Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVFoundingType(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvfoundingtype.setAdapter(adapter);
            spin_cctvfoundingtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvfounding_id = country.getId();
                        cctvfounding_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVCapacity(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvcapacitytype.setAdapter(adapter);
            spin_cctvcapacitytype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvcapacity_id = country.getId();
                        cctvcapacity_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }


    private void CCTVMnth(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvmnth.setAdapter(adapter);
            spin_cctvmnth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvmnth_id = country.getId();
                        cctvmnth_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVYear(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvyear.setAdapter(adapter);
            spin_cctvyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvyear_id = country.getId();
                        cctvyear_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVSpecification() {


        if (al_cctvtypespecification.size() > 0) {
            button = new CheckBox[al_cctvtypespecification.size()];
            for (int i = 0; i < al_cctvtypespecification.size(); i++) {
                button[i] = new CheckBox(getActivity());
                button[i].setText(al_cctvtypespecification.get(i).getName() + "");
                button[i].setTextSize(12);
                button[i].setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                button[i].setTextColor(Color.BLUE);
                iteamsList.addView(button[i]);
                Length_checbox = al_cctvtypespecification.size();
            }

        } else {

            Log.e("arraylist_string", "" + Length_checbox);
        }


    }

    public void Sendorddcommunity() {


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject request = new JSONObject();
        try {


            request.put("Community", S_cctv_CommunityName);
            request.put("PsID", S_psid);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = request.toString();
        Log.e("VOLLEY", "CommunityEntry" + "Input" + mRequestBody);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                CommunityEntry, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "CommunityEntry" + "Output" + response.toString());
                String Code_new = response.optString("code").toString();
                String Message = response.optString("Message").toString();


                if (Code_new.equalsIgnoreCase("1")) {
                    Log.e("suceess", "" + Message1);
                    C_dialog.dismiss();
                    progressDialog.dismiss();
                    tv_cctv_CommunityName.setText("");
                    getCCTVFunds_Api();
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("" + Message);
                    alertDialog.setIcon(R.drawable.succs);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
//                    finish();
//                    Intent i = new Intent(getApplicationContext(), CCtv_View.this);
//                    startActivity(i);

                } else {
                    Log.e("not sucess", "" + Message);
                    C_dialog.dismiss();
                    progressDialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("" + Message);
                    alertDialog.setIcon(R.drawable.alertt);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                    // Toast.makeText(AddAccident_View.this,""+Message,Toast.LENGTH_LONG).show();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Unable to Connect To Server", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                4530000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);


    }

    public void CheckConnectivity_addcommunity() {
        Sendorddcommunity();
    }

    public void Community_Entry() {
        C_dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.communityentry_cctv);
        C_dialog.show();

        tv_cctv_CommunityName = (EditText) C_dialog.findViewById(R.id.tv_cctv_CommunityName);

        spin_ps_community = (Spinner) C_dialog.findViewById(R.id.spin_ps_community);
        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_cctv_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.btn_cctv_Cancel);
        getPSListCommunity_Api();
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                S_cctv_CommunityName = tv_cctv_CommunityName.getText().toString();

                if (spin_ps_community != null && spin_ps_community.getSelectedItem() != null) {
                    if (!(spin_ps_community.getSelectedItem().toString().trim() == "Select PS")) {
                        if (tv_cctv_CommunityName.getText().toString() != null) {

                            CheckConnectivity_addcommunity();

                        } else {
                            Toast.makeText(getActivity(), "Please Enter Community Name", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Please Select PS", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Select PS", Toast.LENGTH_SHORT).show();
                }
            }


        });

        C_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void CCTVType(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvtype.setAdapter(adapter);
            spin_cctvtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvtype_id = country.getId();
                        cctvtype_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CCTVStatus(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_cctvstatus.setAdapter(adapter);
            spin_cctvstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        cctvstatus_id = country.getId();
                        cctvstatus_name = country.getName();

                        if (cctvstatus_id.equalsIgnoreCase("2")) {
                            rl_reason.setVisibility(View.VISIBLE);


                        } else {
                            rl_reason.setVisibility(View.GONE);

                            if (cctvreason_id != null) {
                                cctvreason_id = "";
                                cctvreason_name = "";
                            } else {
                                cctvreason_id = "";
                                cctvreason_name = "";
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void PSLink(ArrayList<Samplemyclass> str1) {

        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(), R.layout.spinner_item, str1);
        if (adapter != null) {
            spin_pslink.setAdapter(adapter);
            spin_pslink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getSelectedItemPosition();
                    if (pos != 0) {

                        Samplemyclass country = (Samplemyclass) parent.getSelectedItem();

                        pslink_id = country.getId();
                        pslink_name = country.getName();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void CheckConnectivity1() {
        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {
            //   new getpay().execute();
            GetDataFromServer();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void getPSList_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "19");
            jsonBody.put("Id", "" + HierarchyId);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:PSList" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:PSList" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_ps.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select PS");
                                // Binds all strings into an array
                                al_ps.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_ps.add(wp);
                                }
                                if (al_ps.size() > 0) {
                                    PSS(al_ps);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select PS");
                            // Binds all strings into an array
                            al_ps.add(wb);
                            if (al_ps.size() > 0) {
                                PSS(al_ps);

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

    private void getPSListCommunity_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "19");
            jsonBody.put("Id", "" + HierarchyId);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:PSList" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:PSList" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String Message = object.optString("Message").toString();

                        al_ps_community.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select PS");
                                // Binds all strings into an array
                                al_ps_community.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_ps_community.add(wp);
                                }
                                if (al_ps_community.size() > 0) {
                                    PSS_Community(al_ps_community);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select PS");
                            // Binds all strings into an array
                            al_ps_community.add(wb);
                            if (al_ps_community.size() > 0) {
                                PSS_Community(al_ps_community);

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

    private void getSectorType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "7");
            jsonBody.put("Id", S_psid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Sectors:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_sector.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Sector");
                                // Binds all strings into an array
                                al_sector.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_sector.add(wp);
                                }
                                if (al_sector.size() > 0) {
                                    Sector(al_sector);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Sector");
                            // Binds all strings into an array
                            al_sector.add(wb);
                            if (al_sector.size() > 0) {
                                Sector(al_sector);

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

    private void getVendorType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "2");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_vendor.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Vendor");
                                // Binds all strings into an array
                                al_vendor.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_vendor.add(wp);
                                }
                                if (al_vendor.size() > 0) {
                                    Vendor(al_vendor);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Vendor");
                            // Binds all strings into an array
                            al_vendor.add(wb);
                            if (al_vendor.size() > 0) {
                                Vendor(al_vendor);

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

    private void getCCTVFunds_Api() {


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "17");
            jsonBody.put("Id", "" + S_psid);

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvfunds.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Community Group");
                                // Binds all strings into an array
                                al_cctvfunds.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvfunds.add(wp);
                                }
                                if (al_cctvfunds.size() > 0) {
                                    CCTVFunds(al_cctvfunds);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Community Group");
                            // Binds all strings into an array
                            al_cctvfunds.add(wb);
                            if (al_cctvfunds.size() > 0) {
                                CCTVFunds(al_cctvfunds);

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

    private void getCCTVFoundingType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "11");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvfouningtype.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Funding Type");
                                // Binds all strings into an array
                                al_cctvfouningtype.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvfouningtype.add(wp);
                                }
                                if (al_cctvfouningtype.size() > 0) {
                                    CCTVFoundingType(al_cctvfouningtype);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Funding Type");
                            // Binds all strings into an array
                            al_cctvfouningtype.add(wb);
                            if (al_cctvfouningtype.size() > 0) {
                                CCTVFoundingType(al_cctvfouningtype);

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


    private void getCCTVYear_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "14");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:cctvyear" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:cctvyear" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvyear.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Year");
                                // Binds all strings into an array
                                al_cctvyear.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvyear.add(wp);
                                }
                                if (al_cctvyear.size() > 0) {
                                    CCTVYear(al_cctvyear);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Year");
                            // Binds all strings into an array
                            al_cctvyear.add(wb);
                            if (al_cctvyear.size() > 0) {
                                CCTVYear(al_cctvyear);

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

    private void getCCTVMnth_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "13");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:cctvmnth" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:cctvmnth" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvmnth.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Month");
                                // Binds all strings into an array
                                al_cctvmnth.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvmnth.add(wp);
                                }
                                if (al_cctvmnth.size() > 0) {
                                    CCTVMnth(al_cctvmnth);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Month");
                            // Binds all strings into an array
                            al_cctvmnth.add(wb);
                            if (al_cctvmnth.size() > 0) {
                                CCTVMnth(al_cctvmnth);

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

    private void getCCTVCapacity_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "5");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvcapacity.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Capacity Type");
                                // Binds all strings into an array
                                al_cctvcapacity.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvcapacity.add(wp);
                                }
                                if (al_cctvcapacity.size() > 0) {
                                    CCTVCapacity(al_cctvcapacity);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Capacity Type");
                            // Binds all strings into an array
                            al_cctvcapacity.add(wb);
                            if (al_cctvcapacity.size() > 0) {
                                CCTVCapacity(al_cctvcapacity);

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

    private void getCCTVSpecification_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "6");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvtypespecification.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                //  Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Type");
                                // Binds all strings into an array
                                //  al_cctvtypespecification.add(wb2);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvtypespecification.add(wp);
                                }
                                if (al_cctvtypespecification.size() > 0) {
                                    CCTVSpecification();
                                }
                            }


                        } else {
                            //Samplemyclass wb2 = new Samplemyclass("0", "Select Accused Type");
                            // Binds all strings into an array
                            //  al_cctvtypespecification.add(wb2);
                            if (al_cctvtypespecification.size() > 0) {
                                CCTVSpecification();

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

    private void getCCTVType_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "1");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvtype.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Camera Type");
                                // Binds all strings into an array
                                al_cctvtype.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvtype.add(wp);
                                }
                                if (al_cctvtype.size() > 0) {
                                    CCTVType(al_cctvtype);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Camera Type");
                            // Binds all strings into an array
                            al_cctvtype.add(wb);
                            if (al_cctvtype.size() > 0) {
                                CCTVType(al_cctvtype);

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

    private void getCCTVStatus_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "4");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvstatus.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Working Status");
                                // Binds all strings into an array
                                al_cctvstatus.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvstatus.add(wp);
                                }
                                if (al_cctvstatus.size() > 0) {
                                    CCTVStatus(al_cctvstatus);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Working Status");
                            // Binds all strings into an array
                            al_cctvstatus.add(wb);
                            if (al_cctvstatus.size() > 0) {
                                CCTVStatus(al_cctvstatus);

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

    private void getCCTVreason_Api() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("ActionName", "3");
            // jsonBody.put("Id", "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:Accused_Categories" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvMasters, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Response:Accused_Categories" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        Message = object.optString("Message").toString();

                        al_cctvreason.clear();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("Master");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wb = new Samplemyclass("0", "Select Reason Of Not working");
                                // Binds all strings into an array
                                al_cctvreason.add(wb);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("Id").toString();
                                    String e_n = json_data.getString("MType").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    // Binds all strings into an array
                                    al_cctvreason.add(wp);
                                }
                                if (al_cctvreason.size() > 0) {
                                    CCTVreason(al_cctvreason);
                                }
                            }


                        } else {
                            Samplemyclass wb = new Samplemyclass("0", "Select Reason Of Not working");
                            // Binds all strings into an array
                            al_cctvreason.add(wb);
                            if (al_cctvreason.size() > 0) {
                                CCTVreason(al_cctvreason);

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

    public void Notnull_Crime() {


        if (cctvreason_id == null) {
            cctvreason_id = "";
        } else {
        }
        if (cctvstatus_id == null) {
            cctvstatus_id = "";
        } else {
        }
        if (sector_id == null) {
            sector_id = "";
        } else {
        }

        if (sector_name == null) {
            sector_name = "";
        } else {
        }

        if (cctvyear_name == null) {
            cctvyear_name = "";
        } else {
        }
        if (cctvmnth_name == null) {
            cctvmnth_name = "";
        } else {
        }
        if (S_secNo == null) {
            S_secNo = "";
        } else {
        }
        if (cctvfunds_id == null) {
            cctvfunds_id = "";
        } else {
        }


        if (cctvreason_id == null) {
            cctvreason_id = "";
        } else {
        }
        if (vendor_id == null) {
            vendor_id = "";
        } else {
        }
        if (pslink_id == null) {
            pslink_id = "";
        } else {
        }


        if (S_cctv_IpAddress == null) {
            S_cctv_IpAddress = "";
        } else {
        }
        if (S_cctv_NVRIPAddress == null) {
            S_cctv_NVRIPAddress = "";
        } else {
        }


        if (S_cctv_ChannelNo == null) {
            S_cctv_ChannelNo = "";
        } else {
        }
        if (S_cctv_InchargeName == null) {
            S_cctv_InchargeName = "";
        } else {
        }


        if (S_cctv_InchargeNo == null) {
            S_cctv_InchargeNo = "";
        } else {
        }
        if (S_cctv_Remarks == null) {
            S_cctv_Remarks = "";
        } else {
        }


        if (cctvcapacity_id == null) {
            cctvcapacity_id = "";
        } else {
        }
        if (S_psid == null) {
            S_psid = "";
        } else {
        }


        if (s_Ps_name == null) {
            s_Ps_name = "";
        } else {
        }
        if (S_cctv_VInchargeName == null) {
            S_cctv_VInchargeName = "";
        } else {
        }


        if (S_cctv_VInchargeNo == null) {
            S_cctv_VInchargeNo = "";
        } else {
        }
        if (S_psid == null) {
            S_psid = "";
        } else {
        }


        if (S_cctv_VInchargeAdress == null) {
            S_cctv_VInchargeAdress = "";
        } else {
        }
        if (cctvfounding_id == null) {
            cctvfounding_id = "";
        } else {
        }


        if (S_cctv_FoundingSource == null) {
            S_cctv_FoundingSource = "";
        } else {
        }
        if (cctvmnth_id == null) {
            cctvmnth_id = "";
        } else {
        }
        if (cctvyear_id == null) {
            cctvyear_id = "";
        } else {
        }
        if (S_cctv_Specifications == null) {
            S_cctv_Specifications = "";
        } else {
        }

        if (S_cctv_FoundingSource == null) {
            S_cctv_FoundingSource = "";
        } else {
        }
        if (cctvmnth_id == null) {
            cctvmnth_id = "";
        } else {
        }


        if (S_cctv_locality == null) {
            S_cctv_locality = "";
        } else {
        }
        if (pslink_id == null) {
            pslink_id = "";
        } else {
        }
        if (pslink_name == null) {
            pslink_name = "";
        } else {
        }
        if (vendor_id == null) {
            vendor_id = "";
        } else {
        }
        if (vendor_name == null) {
            vendor_name = "";
        } else {
        }
        if (cctvtype_name == null) {
            cctvtype_name = "";
        } else {
        }
        if (cctvfunds_id == null) {
            cctvfunds_id = "";
        } else {
        }
        if (cctvfunds_name == null) {
            cctvfunds_name = "";
        } else {
        }
        if (cctvcapacity_id == null) {
            cctvcapacity_id = "";
        } else {
        }
        if (cctvcapacity_name == null) {
            cctvcapacity_name = "";
        } else {
        }
        if (cctvspecification_id == null) {
            cctvspecification_id = "";
        } else {
        }

        if (cctvspecification_name == null) {
            cctvspecification_name = "";
        } else {
        }
        if (cctvreason_id == null) {
            cctvreason_id = "";
        } else {
        }
        if (cctvreason_name == null) {
            cctvreason_name = "";
        } else {
        }
        if (cctvstatus_id == null) {
            cctvstatus_id = "";
        } else {
        }
        if (cctvstatus_name == null) {
            cctvstatus_name = "";
        } else {
        }
        if (cctvtype_id == null) {
            cctvtype_id = "";
        } else {
        }


        if (S_cctv_IpAddress == null) {
            S_cctv_IpAddress = "";
        } else {
        }
        if (S_cctv_InchargeName == null) {
            S_cctv_InchargeName = "";
        } else {
        }
        if (S_cctv_VInchargeAdress == null) {
            S_cctv_VInchargeAdress = "";
        } else {
        }
        if (S_cctv_InchargeNo == null) {
            S_cctv_InchargeNo = "";
        } else {
        }
        if (S_cctv_Remarks == null) {
            S_cctv_Remarks = "";
        } else {
        }

        if (S_cctv_VInchargeNo == null) {
            S_cctv_VInchargeNo = "";
        } else {
        }
        if (S_cctv_VInchargeName == null) {
            S_cctv_VInchargeName = "";
        } else {
        }
        if (S_cctv_VInchargeAdress == null) {
            S_cctv_VInchargeAdress = "";
        } else {
        }
        if (S_cctv_NVRIPAddress == null) {
            S_cctv_NVRIPAddress = "";
        } else {
        }
        if (S_cctv_ChannelNo == null) {
            S_cctv_ChannelNo = "";
        } else {
        }


        if (S_dateTime == null) {
            S_dateTime = "";
        } else {
        }
        if (S_CommunityGroup_Name == null) {
            S_CommunityGroup_Name = "";
        } else {
        }
        if (S_NoOfCamerasCoverd == null) {
            S_NoOfCamerasCoverd = "";
        } else {
        }
        if (S_NoCameraInstalled == null) {
            S_NoCameraInstalled = "";
        } else {
        }
        if (S_StorageinDays == null) {
            S_StorageinDays = "";
        } else {
        }


        if (S_cctv_secno == null) {
            S_cctv_secno = "";
        } else {
        }
        if (S_dateTime == null) {
            S_dateTime = "";
        } else {
        }
        if (S_lm == null) {
            S_lm = "";
        } else {
        }
        if (S_location == null) {
            S_location = "";
        } else {
        }


        if (S_cg == null) {
            S_cg = "";
        } else {
        }
        if (S_pNo == null) {
            S_pNo = "";
        } else {
        }
        if (S_secNo == null) {
            S_secNo = "";
        } else {
        }
        if (S_cctv_IpAddress == null) {
            S_cctv_IpAddress = "";
        } else {
        }
        if (S_cctv_InchargeName == null) {
            S_cctv_InchargeName = "";
        } else {
        }
        if (S_cctv_InchargeNo == null) {
            S_cctv_InchargeNo = "";
        } else {
        }
        if (S_location == null) {
            S_location = "";
        } else {
        }
        if (S_cctv_Remarks == null) {
            S_cctv_Remarks = "";
        } else {
        }


        if (pslink_id == null) {
            pslink_id = "";
        } else {
        }
        if (vendor_id == null) {
            vendor_id = "";
        } else {
        }
        if (vendor_name == null) {
            vendor_name = "";
        } else {
        }
        if (pslink_name == null) {
            pslink_name = "";
        } else {
        }
        if (cctvreason_id == null) {
            cctvreason_id = "";
        } else {
        }
        if (cctvreason_name == null) {
            cctvreason_name = "";
        } else {
        }


        if (cctvstatus_id == null) {
            cctvstatus_id = "";
        } else {
        }
        if (cctvstatus_name == null) {
            cctvstatus_name = "";
        } else {
        }
        if (cctvfunds_id == null) {
            cctvfunds_id = "";
        } else {
        }


        if (string == null) {
            string = "";
        } else {
        }

        if (cctvcapacity_id == null) {
            cctvcapacity_id = "";
        } else {
        }


        if (cctvtype_id == null) {
            cctvtype_id = "";
        } else {
        }
        if (cctvtype_name == null) {
            cctvtype_name = "";
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
        if (pslink_name == null) {
            pslink_name = "";
        } else {
        }
    }

    private void alertDialogue_Conform() {
        C_dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.confom_cctv_new);
        C_dialog.show();

        TextView d_cg = (TextView) C_dialog.findViewById(R.id.tv_cctv_cg);
        TextView d_pno = (TextView) C_dialog.findViewById(R.id.tv_cctv_pno);
        TextView d_sc = (TextView) C_dialog.findViewById(R.id.tv_cctv_sno);
        TextView d_lm = (TextView) C_dialog.findViewById(R.id.tv_cctv_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_cctv_ln);
        TextView tv_cctv_cctvtype = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvtype);
        TextView tv_cctv_cctvstatus = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvstatus);
        TextView tv_cctv_cctvreason = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvreason);
        TextView tv_cctv_cctvvendor = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvvendor);


        TextView tv_cctv_cctvspecificationtype = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvspecificationtype);
        TextView tv_cctv_cctvcapacitytype = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvcapacitytype);
        TextView tv_cctv_cctvpslink = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvpslink);
        TextView tv_cctv_cctvipAdress = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvipAdress);
        TextView tv_cctv_cctvinchargenam = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvinchargenam);
        TextView tv_cctv_cctvinchargeno = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvinchargeno);
        TextView tv_cctv_cctvremarks = (TextView) C_dialog.findViewById(R.id.tv_cctv_cctvremarks);

        tr_reasonofntworking = (TableRow) C_dialog.findViewById(R.id.tr_reasonofntworking);

        d_cg.setText(cctvfunds_name);
        d_pno.setText("" + cctvmnth_name + "&" + cctvyear_name);

        if (sector_id != null) {
            if (!(sector_id.equalsIgnoreCase("0"))) {
                d_sc.setText("" + sector_name);
            } else {
                d_sc.setText("");
            }
        } else {
            d_sc.setText("");
        }

        d_lm.setText(cctvfounding_name);
        d_ln.setText(S_location);


        if (cctvstatus_id.equalsIgnoreCase("2")) {
            tr_reasonofntworking.setVisibility(View.VISIBLE);
        } else {
            tr_reasonofntworking.setVisibility(View.GONE);
        }

        tv_cctv_cctvtype.setText(cctvtype_name);
        tv_cctv_cctvcapacitytype.setText(cctvcapacity_name);
        tv_cctv_cctvspecificationtype.setText("" + S_cctv_Specifications);
        tv_cctv_cctvstatus.setText(cctvstatus_name);
        tv_cctv_cctvreason.setText(cctvreason_name);
        tv_cctv_cctvvendor.setText(vendor_name);
        tv_cctv_cctvpslink.setText(pslink_name);

        tv_cctv_cctvipAdress.setText(S_cctv_IpAddress);
        tv_cctv_cctvinchargenam.setText(S_cctv_InchargeName);
        tv_cctv_cctvinchargeno.setText(S_cctv_InchargeNo);
        tv_cctv_cctvremarks.setText(S_cctv_Remarks);


        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_cctv_Confm);
        Button C_cancel = (Button) C_dialog.findViewById(R.id.btn_cctv_Cancel);

        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayLocation();
                CheckConnectivity();


                //  query=string;


                //  sendZones(string, "", S_et_Message, ActionName);


            }


        });
        //tv_cctv_cctvspecificationtype.setText(string1);

        C_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });
    }

    public void SendDataToServer() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading, Please Wait...");
        progressDialog.show();
        JSONObject request = new JSONObject();
        try {

            request.put("ORGID", S_Orgid);
            request.put("CommunityGroupMasterID", cctvfunds_id);
            //    request.put("Landmark", S_lm);
            request.put("Latitude", S_latitude);
            request.put("Longitude", S_longitude);
            request.put("Location", S_location);
            request.put("SectorNo", sector_id);
            request.put("CCTVTypemasterID", cctvtype_id);
            request.put("CCTVWorkingStatusID", cctvstatus_id);
            request.put("CCTVReasonID", cctvreason_id);
            request.put("CCTVVendorID", vendor_id);
            request.put("PsConnectionID", pslink_id);
            request.put("IPAddress", S_cctv_IpAddress);
            request.put("NVRIPAddress", S_cctv_NVRIPAddress);
            request.put("ChannelNo", S_cctv_ChannelNo);
            request.put("PersonName", S_cctv_InchargeName);
            request.put("MobileNo", S_cctv_InchargeNo);
            request.put("Remarks", S_cctv_Remarks);
            request.put("EnteredBy", S_Uname);
            // request.put("PoleNo", S_pNo);
            // request.put("CameraSpecificationMasterID", string);

            request.put("CameraCapacityMasterID", cctvcapacity_id);
            request.put("CCTVCategoryID", "1");
            request.put("Locality", S_cctv_locality);
            request.put("PsID", S_psid);
            request.put("PsName", s_Ps_name);
            //  request.put("LandMark", S_lm);
            // request.put("GovernmentSectorNo", "" + S_cctv_secno);

            //  request.put("VendorName", "" + S_cctv_VInchargeName);
            request.put("VendorMobile", "" + S_cctv_VInchargeNo);
            request.put("VendorAddress", "" + S_cctv_VInchargeAdress);
            //new Paremeters
            //   request.put("CommunityGroup", S_CommunityGroup_Name);
            request.put("FundTypeId", "" + cctvfounding_id);
            request.put("FundSource", "" + S_cctv_FoundingSource);
            request.put("CCTVMonthId", "" + cctvmnth_id);
            request.put("CCTVYearId", "" + cctvyear_id);
            request.put("CameraSpecification", "" + S_cctv_Specifications);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = request.toString();
        Log.e("VOLLEY", "AddAccident_View" + "Input" + mRequestBody);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                cctvEntry, request, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "AddAccident_View" + "Output" + response.toString());
                Code_new = response.optString("code").toString();
                String Message = response.optString("Message").toString();


                if (Code_new.equalsIgnoreCase("1")) {
                    Log.e("suceess", "" + Message1);
                    C_dialog.dismiss();
                    progressDialog.dismiss();
                    //cg.setText("  progressDialog.dismiss();");
                    pNo.setText("");
                    secNo.setText("");
                    lm.setText("");
                    Bt_acc_date.setText("" + "CCTV Installation Date");
                    S_cg = "";
                    S_secNo = "";
                    S_pNo = "";
                    S_lm = "";
                    S_latitude = "";
                    S_longitude = "";
                    S_location = "";
//                    et_Communityname.setText("");
//                    et_cctv_StorageinDays.setText("");
//                    et_cctv_NoOfCamerasCoverd.setText("");
//                    et_cctv_NoCameraInstalled.setText("");
                    et_cctv_VInchargeNo.setText("");
                    et_cctv_VInchargeName.setText("");
                    et_cctv_VInchargeAdress.setText("");
                    // Log.d("Checkedornot",  ""+ button[i].setChecked(false));
                    // cg.setText("");
                    pNo.setText("");
                    secNo.setText("");
                    lm.setText("");
                    et_cctv_NVRIPAddress.setText("");
                    et_cctv_ChannelNo.setText("");
                    et_cctv_IpAddress.setText("");
                    et_cctv_InchargeName.setText("");
                    et_cctv_InchargeNo.setText("");
                    et_cctv_Remarks.setText("");
                    spin_pslink.setSelection(0);
                    spin_vendor.setSelection(0);
                    spin_cctvreason.setSelection(0);
                    spin_cctvstatus.setSelection(0);
                    spin_cctvtype.setSelection(0);
                    et_cctv_secno.setText("");
                    spin_sector.setSelection(0);
                    pNo.setText("");
                    secNo.setText("");
                    lm.setText("");
                    al.clear();

//                    for (int i = 0; i < Length_checbox; i++) {
//                        button[i].setChecked(false);
//                    }

                    spin_cctvfundstype.setSelection(0);
                    spin_cctvcapacitytype.setSelection(0);


//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Success");
                    alertDialog.setMessage("" + Message);
                    alertDialog.setIcon(R.drawable.succs);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke YES event
                            dialog.cancel();

                            getActivity().finish();
                            Intent kk = new Intent(getActivity(), CCtv_Tabs.class);
                            startActivity(kk);
                        }
                    });
                    alertDialog.show();
//                    finish();
//                    Intent i = new Intent(getApplicationContext(), CCtv_View.this);
//                    startActivity(i);

                } else {
                    Log.e("not sucess", "" + Message);
                    C_dialog.dismiss();
                    progressDialog.dismiss();
//                    et_Desriptn.setText("");
//                    et_Cno.setText("");
//                    //  Et_yellow.setText("");
//                    Category.setSelection(0);
//                    SCategory.setSelection(0);
//                    detected_.setSelection(0);
//                    Bt_c_date.setText("Select Date and time");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("" + Message);
                    alertDialog.setIcon(R.drawable.alertt);
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                    // Toast.makeText(AddAccident_View.this,""+Message,Toast.LENGTH_LONG).show();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();


                Toast.makeText(getActivity(), "Unable to Connect To Server", Toast.LENGTH_SHORT).show();

            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                4530000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(jsonObjReq);
    }

    private void GetDataFromServer() {
        try {
            SharedPreferences bb = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            S_Uname = bb.getString("UserName", "");
            S_Pass = bb.getString("password", "");
            S_IMEi = bb.getString("imei", "");
            S_psid = bb.getString("Psid", "");


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("HirarchyID", S_psid);
            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request:cctvList" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, cctvList, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", response);
                    try {

                        Log.e("VOLLEY", "Response:cctvList" + response);
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {

                            JSONArray jArray = object.getJSONArray("CCTVList");
                            int number = jArray.length();

                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                CameraFixedDate_arraylist_.clear();
                                NoOfCamerasCoverd_arraylist_.clear();
                                NoCameraInstalled_arraylist_.clear();
                                StorageinDays_arraylist_.clear();
                                CommunityName_arraylist_.clear();
                                CGp_arraylist_.clear();
                                SecNo_arraylist_.clear();
                                PoleNo_arraylist_.clear();
                                LandMark_arraylist_.clear();
                                Add_arraylist_.clear();
                                al_cctvtypespecification.clear();
                                Fixed_arraylist_.clear();
                                PTZ_arraylist_.clear();
                                IR_arraylist_.clear();
                                Other_arraylist_.clear();
                                InchargeName_arraylist_.clear();
                                InchargeNo_arraylist_.clear();
                                Remarks_arraylist_.clear();
                                CCTVType_arraylist_.clear();
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    //  String r_id=json_data.getString("Id").toString();
                                    CGp = json_data.getString("CommunityGroup").toString();
                                    SecNo = json_data.getString("SectorNo").toString();
                                    String CctvMonth = json_data.getString("CctvMonth").toString();
                                    String Cctvyear = json_data.getString("Cctvyear").toString();

                                    PoleNo = CctvMonth + " & " + Cctvyear;

                                    status = json_data.getString("CCTVWorkingStatusID").toString();
                                    LandMark = json_data.getString("FundType").toString();
                                    CCTVType = json_data.getString("CCTVType").toString();
                                    Add = json_data.getString("Location").toString();

                                    Fixed = json_data.getString("CCTVReasonID").toString();
                                    PTZ = json_data.getString("CCTVVendorID").toString();
                                    IR = json_data.getString("PsConnectionID").toString();
                                    Other = json_data.getString("IPAddress").toString();

                                    InchargeName = json_data.getString("PersonName").toString();
                                    InchargeNo = json_data.getString("MobileNo").toString();
                                    Remarks = json_data.getString("Remarks").toString();

                                    CCTVCapacity = json_data.getString("CameraCapacityMasterID").toString();
                                    Specification = json_data.getString("CameraSpecification").toString();

                                    CameraFixedDate = json_data.getString("CameraFixedDate").toString();
//                                    NoOfCamerasCoverd = json_data.getString("NoOfCamerasCoverd").toString();
//                                    NoCameraInstalled = json_data.getString("NoCameraInstalled").toString();
//                                    StorageinDays = json_data.getString("StorageinDays").toString();
//                                    CommunityName = json_data.getString("CommunityName").toString();


                                    if (CameraFixedDate == null || CameraFixedDate.trim().equals("anyType{}") || CameraFixedDate.trim()
                                            .length() <= 0 || CameraFixedDate.equalsIgnoreCase("null")) {
                                        CameraFixedDate_arraylist_.add("");
                                    } else {
                                        CameraFixedDate_arraylist_.add(CameraFixedDate);
                                    }
                                    if (NoOfCamerasCoverd == null || NoOfCamerasCoverd.trim().equals("anyType{}") || NoOfCamerasCoverd.trim()
                                            .length() <= 0 || NoOfCamerasCoverd.equalsIgnoreCase("null")) {
                                        NoOfCamerasCoverd_arraylist_.add("");
                                    } else {
                                        NoOfCamerasCoverd_arraylist_.add(NoOfCamerasCoverd);
                                    }
                                    if (NoCameraInstalled == null || NoCameraInstalled.trim().equals("anyType{}") || NoCameraInstalled.trim()
                                            .length() <= 0 || NoCameraInstalled.equalsIgnoreCase("null")) {
                                        NoCameraInstalled_arraylist_.add("");
                                    } else {
                                        NoCameraInstalled_arraylist_.add(NoCameraInstalled);
                                    }
                                    if (StorageinDays == null || StorageinDays.trim().equals("anyType{}") || StorageinDays.trim()
                                            .length() <= 0 || StorageinDays.equalsIgnoreCase("null")) {
                                        StorageinDays_arraylist_.add("");
                                    } else {
                                        StorageinDays_arraylist_.add(StorageinDays);
                                    }
                                    if (CommunityName == null || CommunityName.trim().equals("anyType{}") || CommunityName.trim()
                                            .length() <= 0 || CommunityName.equalsIgnoreCase("null")) {
                                        CommunityName_arraylist_.add("");
                                    } else {
                                        CommunityName_arraylist_.add(CommunityName);
                                    }


                                    if (status == null || status.trim().equals("anyType{}") || status.trim()
                                            .length() <= 0 || status.equalsIgnoreCase("null")) {
                                        status_arraylist_.add("");
                                    } else {
                                        status_arraylist_.add(status);
                                    }
                                    if (CCTVCapacity == null || CCTVCapacity.trim().equals("anyType{}") || CCTVCapacity.trim()
                                            .length() <= 0 || CCTVCapacity.equalsIgnoreCase("null")) {
                                        CCTVCapacity_arraylist_.add("");
                                    } else {
                                        CCTVCapacity_arraylist_.add(CCTVCapacity);
                                    }
                                    if (Specification == null || Specification.trim().equals("anyType{}") || Specification.trim()
                                            .length() <= 0 || Specification.equalsIgnoreCase("null")) {
                                        Specification_arraylist_.add("");
                                    } else {
                                        Specification_arraylist_.add(Specification);
                                    }

                                    if (CCTVType == null || CCTVType.trim().equals("anyType{}") || CCTVType.trim()
                                            .length() <= 0 || CCTVType.equalsIgnoreCase("null")) {
                                        CCTVType_arraylist_.add("");
                                    } else {
                                        CCTVType_arraylist_.add(CCTVType);
                                        Log.d("Sttausssssss", "" + CCTVType_arraylist_);

                                    }


                                    if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
                                            .length() <= 0 || Remarks.equalsIgnoreCase("null")) {
                                        Remarks_arraylist_.add("");
                                    } else {
                                        Remarks_arraylist_.add(Remarks);
                                    }

                                    if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
                                            .length() <= 0 || Remarks.equalsIgnoreCase("null")) {
                                        Remarks_arraylist_.add("");
                                    } else {
                                        Remarks_arraylist_.add(Remarks);
                                    }
                                    if (InchargeNo == null || InchargeNo.trim().equals("anyType{}") || InchargeNo.trim()
                                            .length() <= 0 || InchargeNo.equalsIgnoreCase("null")) {
                                        InchargeNo_arraylist_.add("");
                                    } else {
                                        InchargeNo_arraylist_.add(InchargeNo);
                                    }

                                    if (InchargeName == null || InchargeName.trim().equals("anyType{}") || InchargeName.trim()
                                            .length() <= 0 || InchargeName.equalsIgnoreCase("null")) {
                                        InchargeName_arraylist_.add("");
                                    } else {
                                        InchargeName_arraylist_.add(InchargeName);
                                    }
                                    //test
                                    if (Other == null || Other.trim().equals("anyType{}") || Other.trim()
                                            .length() <= 0 || Other.equalsIgnoreCase("null")) {
                                        Other_arraylist_.add("");
                                    } else {
                                        Other_arraylist_.add(Other);
                                    }
                                    if (CGp == null || CGp.trim().equals("anyType{}") || CGp.trim()
                                            .length() <= 0 || CGp.equalsIgnoreCase("null")) {
                                        CGp_arraylist_.add("");
                                    } else {
                                        CGp_arraylist_.add(CGp);
                                    }

                                    if (LandMark == null || LandMark.trim().equals("anyType{}") || LandMark.trim()
                                            .length() <= 0 || LandMark.equalsIgnoreCase("null")) {
                                        LandMark_arraylist_.add("");
                                    } else {
                                        LandMark_arraylist_.add(LandMark);
                                    }
                                    if (Fixed == null || Fixed.trim().equals("anyType{}") || Fixed.trim()
                                            .length() <= 0 || Fixed.equalsIgnoreCase("null")) {
                                        Fixed_arraylist_.add("");
                                    } else {
                                        Fixed_arraylist_.add(Fixed);
                                    }
                                    if (PTZ == null || PTZ.trim().equals("anyType{}") || PTZ.trim()
                                            .length() <= 0 || PTZ.equalsIgnoreCase("null")) {
                                        PTZ_arraylist_.add("");
                                    } else {
                                        PTZ_arraylist_.add(PTZ);
                                    }
                                    if (IR == null || IR.trim().equals("anyType{}") || IR.trim()
                                            .length() <= 0 || IR.equalsIgnoreCase("null")) {
                                        IR_arraylist_.add("");
                                    } else {
                                        IR_arraylist_.add(IR);
                                    }
                                    if (SecNo == null || SecNo.trim().equals("anyType{}") || SecNo.trim()
                                            .length() <= 0 || SecNo.equalsIgnoreCase("null")) {
                                        SecNo_arraylist_.add("");
                                    } else {
                                        SecNo_arraylist_.add(SecNo);
                                    }
                                    if (PoleNo == null || PoleNo.trim().equals("anyType{}") || PoleNo.trim()
                                            .length() <= 0 || PoleNo.equalsIgnoreCase("null")) {
                                        PoleNo_arraylist_.add("");
                                    } else {
                                        PoleNo_arraylist_.add(PoleNo);
                                    }
                                    if (Add == null || Add.trim().equals("anyType{}") || Add.trim()
                                            .length() <= 0 || Add.equalsIgnoreCase("null")) {
                                        Add_arraylist_.add("");
                                    } else {
                                        Add_arraylist_.add(Add);
                                    }
                                }

                            }


                            int count = Add_arraylist_.size();
                            Log.e(" count", "" + count);
                            SetValuesToLayout();
                        } else {

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
                    Toast.makeText(getActivity(), "Unable to Connect To Server", Toast.LENGTH_SHORT).show();

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

    class getpay extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Loading data");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {
            String SOAP_ACTION = "http://tempuri.org/ViewCrimeCCTVInfo";
            String METHOD_NAME = "ViewCrimeCCTVInfo";
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://tecdatum.net/Analytics/services/CrimeDataEntry.asmx?op=ViewCrimeCCTVInfo";
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", S_Uname);
            request.addProperty("Password", S_Pass);
            request.addProperty("ImeI", S_IMEi);
            //  request.addProperty("Type", "Surveillance");
            envelope.setOutputSoapObject(request);
            Log.e("1245", S_Pass + "    " + S_IMEi);
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
                System.out.println("******** : " + root.getPropertyCount());

                CGp_arraylist_.clear();
                SecNo_arraylist_.clear();
                PoleNo_arraylist_.clear();
                LandMark_arraylist_.clear();
                Add_arraylist_.clear();
                al_cctvtypespecification.clear();
                Fixed_arraylist_.clear();
                PTZ_arraylist_.clear();
                IR_arraylist_.clear();
                Other_arraylist_.clear();


                InchargeName_arraylist_.clear();
                InchargeNo_arraylist_.clear();
                Remarks_arraylist_.clear();
                CCTVType_arraylist_.clear();
                for (int j = 0; j < root.getPropertyCount(); j++) {
                    SoapObject s_deals = (SoapObject) root.getProperty(j);
                    System.out.println("********Count : " + s_deals.getPropertyCount());
                    for (int i = 0; i < s_deals.getPropertyCount(); i++) {

                        Object property = s_deals.getProperty(i);
                        System.out.println("******** : " + property);
                        CGp = s_deals.getProperty("CommunityGroup").toString();
                        SecNo = s_deals.getProperty("SectorNo").toString();
                        PoleNo = s_deals.getProperty("PoleNo").toString();
                        status = s_deals.getProperty("status").toString();
                        LandMark = s_deals.getProperty("Landmark").toString();
                        CCTVType = s_deals.getProperty("CCTVType").toString();
                        Add = s_deals.getProperty("Location").toString();

                        Fixed = s_deals.getProperty("ReasonOfNotWrking").toString();
                        PTZ = s_deals.getProperty("vendor").toString();
                        IR = s_deals.getProperty("PSlink").toString();
                        Other = s_deals.getProperty("IPAddress").toString();

                        InchargeName = s_deals.getProperty("PersonName").toString();
                        InchargeNo = s_deals.getProperty("MobileNo").toString();
                        Remarks = s_deals.getProperty("Remarks").toString();

                        CCTVCapacity = s_deals.getProperty("CCTVCapacity").toString();
                        Specification = s_deals.getProperty("Specification").toString();
                    }
                    if (status == null || status.trim().equals("anyType{}") || status.trim()
                            .length() <= 0) {
                        status_arraylist_.add("");
                    } else {
                        status_arraylist_.add(status);
                    }
                    if (CCTVCapacity == null || CCTVCapacity.trim().equals("anyType{}") || CCTVCapacity.trim()
                            .length() <= 0) {
                        CCTVCapacity_arraylist_.add("");
                    } else {
                        CCTVCapacity_arraylist_.add(CCTVCapacity);
                    }
                    if (Specification == null || Specification.trim().equals("anyType{}") || Specification.trim()
                            .length() <= 0) {
                        Specification_arraylist_.add("");
                    } else {
                        Specification_arraylist_.add(Specification);
                    }

                    if (CCTVType == null || CCTVType.trim().equals("anyType{}") || CCTVType.trim()
                            .length() <= 0) {
                        CCTVType_arraylist_.add("");
                    } else {
                        CCTVType_arraylist_.add(CCTVType);
                        Log.d("Sttausssssss", "" + CCTVType_arraylist_);

                    }


                    if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
                            .length() <= 0) {
                        Remarks_arraylist_.add("");
                    } else {
                        Remarks_arraylist_.add(Remarks);
                    }

                    if (Remarks == null || Remarks.trim().equals("anyType{}") || Remarks.trim()
                            .length() <= 0) {
                        Remarks_arraylist_.add("");
                    } else {
                        Remarks_arraylist_.add(Remarks);
                    }
                    if (InchargeNo == null || InchargeNo.trim().equals("anyType{}") || InchargeNo.trim()
                            .length() <= 0) {
                        InchargeNo_arraylist_.add("");
                    } else {
                        InchargeNo_arraylist_.add(InchargeNo);
                    }

                    if (InchargeName == null || InchargeName.trim().equals("anyType{}") || InchargeName.trim()
                            .length() <= 0) {
                        InchargeName_arraylist_.add("");
                    } else {
                        InchargeName_arraylist_.add(InchargeName);
                    }
                    //test
                    if (Other == null || Other.trim().equals("anyType{}") || Other.trim()
                            .length() <= 0) {
                        Other_arraylist_.add("");
                    } else {
                        Other_arraylist_.add(Other);
                    }
                    if (CGp == null || CGp.trim().equals("anyType{}") || CGp.trim()
                            .length() <= 0) {
                        CGp_arraylist_.add("");
                    } else {
                        CGp_arraylist_.add(CGp);
                    }

                    if (LandMark == null || LandMark.trim().equals("anyType{}") || LandMark.trim()
                            .length() <= 0) {
                        LandMark_arraylist_.add("");
                    } else {
                        LandMark_arraylist_.add(LandMark);
                    }
                    if (Fixed == null || Fixed.trim().equals("anyType{}") || Fixed.trim()
                            .length() <= 0) {
                        Fixed_arraylist_.add("");
                    } else {
                        Fixed_arraylist_.add(Fixed);
                    }
                    if (PTZ == null || PTZ.trim().equals("anyType{}") || PTZ.trim()
                            .length() <= 0) {
                        PTZ_arraylist_.add("");
                    } else {
                        PTZ_arraylist_.add(PTZ);
                    }
                    if (IR == null || IR.trim().equals("anyType{}") || IR.trim()
                            .length() <= 0) {
                        IR_arraylist_.add("");
                    } else {
                        IR_arraylist_.add(IR);
                    }
                    if (SecNo == null || SecNo.trim().equals("anyType{}") || SecNo.trim()
                            .length() <= 0) {
                        SecNo_arraylist_.add("");
                    } else {
                        SecNo_arraylist_.add(SecNo);
                    }
                    if (PoleNo == null || PoleNo.trim().equals("anyType{}") || PoleNo.trim()
                            .length() <= 0) {
                        PoleNo_arraylist_.add("");
                    } else {
                        PoleNo_arraylist_.add(PoleNo);
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

                Log.e(TAG, " " + SecNo_arraylist_.size());
                Log.e("Sttausssssss", " " + PoleNo_arraylist_.size());
                Log.e(TAG, " " + LandMark_arraylist_.size());
                Log.e(TAG, " " + Fixed_arraylist_.size());
                Log.e(TAG, " " + Add_arraylist_.size());
                Log.e(TAG, " " + PTZ_arraylist_.size());
                Log.e(TAG, "Group" + CGp_arraylist_.size());
                SetValuesToLayout();

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

    private void SetValuesToLayout() {

        vl_dialog = new Dialog(getActivity(), R.style.HiddenTitleTheme);
        vl_dialog.setContentView(R.layout.vl_cctv);
        vl_dialog.show();
        TableRow r0 = (TableRow) vl_dialog.findViewById(R.id.tr0);
        TableRow r1 = (TableRow) vl_dialog.findViewById(R.id.tr1);
        TableRow r2 = (TableRow) vl_dialog.findViewById(R.id.tableRow2);
        TableRow r3 = (TableRow) vl_dialog.findViewById(R.id.tableRow3);
        TableRow r4 = (TableRow) vl_dialog.findViewById(R.id.tableRow4);
        TableRow r5 = (TableRow) vl_dialog.findViewById(R.id.tableRow5);
        TextView NoRecords = (TextView) vl_dialog.findViewById(R.id.tv_norecords);

        TextView t1 = (TextView) vl_dialog.findViewById(R.id.tv_t1);
        TextView t2 = (TextView) vl_dialog.findViewById(R.id.tv_t2);
        TextView t3 = (TextView) vl_dialog.findViewById(R.id.tv_t3);
        TextView t4 = (TextView) vl_dialog.findViewById(R.id.tv_t4);
        TextView t5 = (TextView) vl_dialog.findViewById(R.id.tv_t5);

        TextView st1 = (TextView) vl_dialog.findViewById(R.id.tv_p1);
        TextView st2 = (TextView) vl_dialog.findViewById(R.id.tv_p2);
        TextView st3 = (TextView) vl_dialog.findViewById(R.id.tv_p3);
        TextView st4 = (TextView) vl_dialog.findViewById(R.id.tv_p4);
        TextView st5 = (TextView) vl_dialog.findViewById(R.id.tv_p5);

        TextView dt1 = (TextView) vl_dialog.findViewById(R.id.tv_c1);
        TextView dt2 = (TextView) vl_dialog.findViewById(R.id.tv_c2);
        TextView dt3 = (TextView) vl_dialog.findViewById(R.id.tv_c3);
        TextView dt4 = (TextView) vl_dialog.findViewById(R.id.tv_c4);
        TextView dt5 = (TextView) vl_dialog.findViewById(R.id.tv_c5);

        TextView v1 = (TextView) vl_dialog.findViewById(R.id.tv_m1);
        TextView v2 = (TextView) vl_dialog.findViewById(R.id.tv_m2);
        TextView v3 = (TextView) vl_dialog.findViewById(R.id.tv_m3);
        TextView v4 = (TextView) vl_dialog.findViewById(R.id.tv_m4);
        TextView v5 = (TextView) vl_dialog.findViewById(R.id.tv_m5);

        if ((CCTVType_arraylist_.size()) > 0 | (CCTVType_arraylist_.size()) != 0) {


            Log.e(TAG, "Group" + CGp_arraylist_.size());

            if ((CCTVType_arraylist_.size()) == 1) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(CCTVType_arraylist_.get(0));
                if ((CGp_arraylist_.size()) != 0) {
                    if ((CGp_arraylist_.size()) == 1) {
                        t1.setText(CGp_arraylist_.get(0));

                    }
                }
                if ((status_arraylist_.size()) != 0) {
                    if ((status_arraylist_.size()) == 1) {
                        dt1.setText(status_arraylist_.get(0));
                    }
                }

            }
            if ((CCTVType_arraylist_.size()) == 2) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.GONE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));

                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));

//
//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//
//                }
//
//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                    }
//
//                }

            }
            if ((CCTVType_arraylist_.size()) == 3) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.GONE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));
                st3.setText(CCTVType_arraylist_.get(2));

                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));
                dt3.setText(status_arraylist_.get(2));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));
                t3.setText(CGp_arraylist_.get(2));

//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//                    if ((CGp_arraylist_.size()) == 3) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                    }
//                }
//
//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        Log.d("Sttausssssss", "" + status_arraylist_);
//                    }
//                    if ((status_arraylist_.size()) == 3) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                    }
//                }

            }

            if ((CCTVType_arraylist_.size()) == 4) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.GONE);
                NoRecords.setVisibility(View.GONE);
                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));
                st3.setText(CCTVType_arraylist_.get(2));
                st4.setText(CCTVType_arraylist_.get(3));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));
                t3.setText(CGp_arraylist_.get(2));
                t4.setText(CGp_arraylist_.get(3));

                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));
                dt3.setText(status_arraylist_.get(2));
                dt4.setText(status_arraylist_.get(3));

//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//                    if ((CGp_arraylist_.size()) == 3) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                    }
//                    if ((CGp_arraylist_.size()) == 4) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                        Log.d("Sttausssssss", "" + CGp_arraylist_);
//                    }
//
//                }

//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                    }
//                    if ((status_arraylist_.size()) == 3) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                    }
//                    if ((status_arraylist_.size()) == 4) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                    }
//
//                }


            }
            if ((CCTVType_arraylist_.size()) == 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);

                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));
                st3.setText(CCTVType_arraylist_.get(2));
                st4.setText(CCTVType_arraylist_.get(3));
                st5.setText(CCTVType_arraylist_.get(4));
                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));
                dt3.setText(status_arraylist_.get(2));
                dt4.setText(status_arraylist_.get(3));
                dt5.setText(status_arraylist_.get(4));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));
                t3.setText(CGp_arraylist_.get(2));
                t4.setText(CGp_arraylist_.get(3));
                t5.setText(CGp_arraylist_.get(4));
//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//                    if ((CGp_arraylist_.size()) == 3) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                    }
//                    if ((CGp_arraylist_.size()) == 4) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                    }
//                    if ((CGp_arraylist_.size()) == 5) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                        t5.setText(CGp_arraylist_.get(4));
//                    }
//                }
//
//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                    }
//                    if ((status_arraylist_.size()) == 3) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                    }
//                    if ((status_arraylist_.size()) == 4) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                    }
//                    if ((PoleNo_arraylist_.size()) == 5) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                        dt5.setText(status_arraylist_.get(4));
//                        Log.d("Sttausssssss", " " + status_arraylist_);
//                    }
//                }
            }
            if ((CCTVType_arraylist_.size()) > 5) {
                r0.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                NoRecords.setVisibility(View.GONE);
                Log.e("yes", "" + CGp_arraylist_.size());

                st1.setText(CCTVType_arraylist_.get(0));
                st2.setText(CCTVType_arraylist_.get(1));
                st3.setText(CCTVType_arraylist_.get(2));
                st4.setText(CCTVType_arraylist_.get(3));
                st5.setText(CCTVType_arraylist_.get(4));

                dt1.setText(status_arraylist_.get(0));
                dt2.setText(status_arraylist_.get(1));
                dt3.setText(status_arraylist_.get(2));
                dt4.setText(status_arraylist_.get(3));
                dt5.setText(status_arraylist_.get(4));


                t1.setText(CGp_arraylist_.get(0));
                t2.setText(CGp_arraylist_.get(1));
                t3.setText(CGp_arraylist_.get(2));
                t4.setText(CGp_arraylist_.get(3));
                t5.setText(CGp_arraylist_.get(4));

//                if ((CGp_arraylist_.size()) != 0) {
//                    if ((CGp_arraylist_.size()) == 1) {
//                        t1.setText(CGp_arraylist_.get(0));
//                    }
//                    if ((CGp_arraylist_.size()) == 2) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                    }
//                    if ((CGp_arraylist_.size()) == 3) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                    }
//                    if ((CGp_arraylist_.size()) == 4) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                    }
//                    if ((CGp_arraylist_.size()) == 5) {
//                        t1.setText(CGp_arraylist_.get(0));
//                        t2.setText(CGp_arraylist_.get(1));
//                        t3.setText(CGp_arraylist_.get(2));
//                        t4.setText(CGp_arraylist_.get(3));
//                        t5.setText(CGp_arraylist_.get(4));
//                    }
//                }
//
//                if ((status_arraylist_.size()) != 0) {
//                    if ((status_arraylist_.size()) == 1) {
//                        dt1.setText(status_arraylist_.get(0));
//                    }
//                    if ((status_arraylist_.size()) == 2) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                    }
//                    if ((status_arraylist_.size()) == 3) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                    }
//                    if ((status_arraylist_.size()) == 4) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                    }
//                    if ((status_arraylist_.size()) == 5) {
//                        dt1.setText(status_arraylist_.get(0));
//                        dt2.setText(status_arraylist_.get(1));
//                        dt3.setText(status_arraylist_.get(2));
//                        dt4.setText(status_arraylist_.get(3));
//                        dt5.setText(status_arraylist_.get(4));
//                    }
//                }

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
        C_dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
        C_dialog.setContentView(R.layout.vl_confm_cctv);
        C_dialog.show();

        TextView d_cg = (TextView) C_dialog.findViewById(R.id.tv_cctv_cg);
        TextView d_pno = (TextView) C_dialog.findViewById(R.id.tv_cctv_pno);
        TextView tv_cctv_workingstatus = (TextView) C_dialog.findViewById(R.id.tv_cctv_workingstatus);
        TextView d_sc = (TextView) C_dialog.findViewById(R.id.tv_cctv_sno);
        TextView d_lm = (TextView) C_dialog.findViewById(R.id.tv_cctv_lm);
        TextView d_ln = (TextView) C_dialog.findViewById(R.id.tv_cctv_ln);

        TextView d_fixed = (TextView) C_dialog.findViewById(R.id.tv_cctv_fixed);
        TextView d_ptz = (TextView) C_dialog.findViewById(R.id.tv_cctv_ptz);
        TextView d_ir = (TextView) C_dialog.findViewById(R.id.tv_cctv_ir);
        TextView d_others = (TextView) C_dialog.findViewById(R.id.tv_cctv_others);
        TextView pscode = (TextView) C_dialog.findViewById(R.id.tv_cctv_pscode);
        TextView tv_cctv_type = (TextView) C_dialog.findViewById(R.id.tv_cctv_type);
        TextView tv_cctv_InchargeName = (TextView) C_dialog.findViewById(R.id.tv_cctv_InchargeName);
        TextView tv_cctv_InchargeNo = (TextView) C_dialog.findViewById(R.id.tv_cctv_InchargeNo);
        TextView tv_cctv_Remarks = (TextView) C_dialog.findViewById(R.id.tv_cctv_Remarks);
        TextView tv_cctv_specification = (TextView) C_dialog.findViewById(R.id.tv_cctv_specification);
        TextView tv_cctv_capacity = (TextView) C_dialog.findViewById(R.id.tv_cctv_capacity);

        TextView tv_cctv_Communityname = (TextView) C_dialog.findViewById(R.id.tv_cctv_Communityname);
        TextView tv_cctv_NoOfCamerasCoverd = (TextView) C_dialog.findViewById(R.id.tv_cctv_NoOfCamerasCoverd);
        TextView tv_cctv_NoCameraInstalled = (TextView) C_dialog.findViewById(R.id.tv_cctv_NoCameraInstalled);
        TextView tv_cctv_StorageinDays = (TextView) C_dialog.findViewById(R.id.tv_cctv_StorageinDays);
        TextView tv_cctv_CameraFixedDate = (TextView) C_dialog.findViewById(R.id.tv_cctv_CameraFixedDate);


        if (CommunityName_arraylist_.size() > count) {
            tv_cctv_Communityname.setText(CGp_arraylist_.get(count));
        }
        if (NoOfCamerasCoverd_arraylist_.size() > count) {
            tv_cctv_NoOfCamerasCoverd.setText(CGp_arraylist_.get(count));
        }
        if (NoCameraInstalled_arraylist_.size() > count) {
            tv_cctv_NoCameraInstalled.setText(CGp_arraylist_.get(count));
        }
        if (StorageinDays_arraylist_.size() > count) {
            tv_cctv_StorageinDays.setText(CGp_arraylist_.get(count));
        }
        if (CameraFixedDate_arraylist_.size() > count) {
            tv_cctv_CameraFixedDate.setText(CGp_arraylist_.get(count));
        }

        if (s_Ps_name != null) {
            pscode.setText(s_Ps_name);
        }

        if (CGp_arraylist_.size() > count) {
            d_cg.setText(CGp_arraylist_.get(count));
        }
        if (PoleNo_arraylist_.size() > count) {
            d_pno.setText(PoleNo_arraylist_.get(count));
        }

        if (status_arraylist_.size() > count) {
            tv_cctv_workingstatus.setText(status_arraylist_.get(count));
        }


        if (SecNo_arraylist_.size() > count) {
            d_sc.setText(SecNo_arraylist_.get(count));
        }
        if (LandMark_arraylist_.size() > count) {
            d_lm.setText(LandMark_arraylist_.get(count));
        }
        if (Add_arraylist_.size() > count) {
            d_ln.setText(Add_arraylist_.get(count));
        }
        if (Fixed_arraylist_.size() > count) {
            d_fixed.setText(Fixed_arraylist_.get(count));
        }
        if (PTZ_arraylist_.size() > count) {
            d_ptz.setText(PTZ_arraylist_.get(count));
        }
        if (IR_arraylist_.size() > count) {
            d_ir.setText(IR_arraylist_.get(count));
        }
        if (Other_arraylist_.size() > count) {
            d_others.setText(Other_arraylist_.get(count));
        }
        if (InchargeName_arraylist_.size() > count) {
            tv_cctv_InchargeName.setText(InchargeName_arraylist_.get(count));
        }
        if (InchargeNo_arraylist_.size() > count) {
            tv_cctv_InchargeNo.setText(InchargeNo_arraylist_.get(count));
        }
        if (Remarks_arraylist_.size() > count) {
            tv_cctv_Remarks.setText(Remarks_arraylist_.get(count));
        }
        if (CCTVType_arraylist_.size() > count) {
            tv_cctv_type.setText(CCTVType_arraylist_.get(count));
        }

        if (CCTVCapacity_arraylist_.size() > count) {
            tv_cctv_capacity.setText(CCTVCapacity_arraylist_.get(count));
        }

        if (Specification_arraylist_.size() > count) {
            tv_cctv_specification.setText(Specification_arraylist_.get(count));
        }

        Button C_confm = (Button) C_dialog.findViewById(R.id.btn_cctv_Confm);
        C_confm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                C_dialog.dismiss();
            }
        });

    }

    private void InitialConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            al_pslink.add(new Samplemyclass("11", "Select PS Link"));
            al_pslink.add(new Samplemyclass("1", "Connected"));
            al_pslink.add(new Samplemyclass("2", "Not Connected"));


//            new getCCTVFunds().execute();
//            new getCCTVCapacity().execute();
//            new getCCTVSpecification().execute();
//            new getVendorType().execute();
//            new getCCTVType().execute();
//            new getCCTVStatus().execute();
//            new getCCTVreason().execute();
            getSectorType_Api();
            getVendorType_Api();
            getCCTVFunds_Api();
            getPSList_Api();
            // getCCTVSpecification_Api();
            getCCTVCapacity_Api();
            getCCTVType_Api();
            getCCTVreason_Api();
            getCCTVStatus_Api();
//
            getCCTVFoundingType_Api();
            getCCTVYear_Api();
            getCCTVMnth_Api();


            PSLink(al_pslink);

        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void CheckConnectivity() {

        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            // new pay().execute();
            SendDataToServer();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {
            C_dialog.dismiss();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
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
        Log.w(TAG, "App onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.w(TAG, "App onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w(TAG, "App onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.w(TAG, "App onStop");
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
        super.onDestroy();
        Log.w(TAG, "App onDestroy");
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
            tv_lat.setText("" + S_latitude);
            tv_lng.setText("" + S_longitude);
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
//                        stringBuilder.append(",");
//                        stringBuilder.append(address1);
//                        stringBuilder.append(",");
//                        stringBuilder.append(address2);
                        String S_loc = stringBuilder.toString();
                        String t66 = "<font color='#ebedf5'>Location : </font>";
                        Log.e("msg", "" + S_loc);
                        location.setText(" " + S_loc);
                        // CurrentLocation.setText(Html.fromHtml(t66+S_loc));
                        // CurrentLocation.setText(S_loc);
                    }
                }
            } catch (IOException e) {
                location.setHint(" " + "Enter Your location Address");

                Log.e("msg", "" + "Couldn't get the location.");
                //CurrentLocation.setText("Couldn't get the location.");
                e.printStackTrace();
            }
        } else {
//            vlat.setText("0.0");
//            vlon.setText("0.0");
//            Lat= String.valueOf(0.0);
//            Longi= String.valueOf(0.0);
            // Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
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