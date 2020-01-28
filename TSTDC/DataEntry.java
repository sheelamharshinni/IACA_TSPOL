package com.example.ndev5.tstdc.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.ndev5.tstdc.Connectivity.Connectivity;
import com.example.ndev5.tstdc.Connectivity.MyPasswordTransformationMethod;
import com.example.ndev5.tstdc.Constants.URLS;
import com.example.ndev5.tstdc.Date_Picker.CustomDateTimePickerNo;
import com.example.ndev5.tstdc.Helprr.Samplemyclass;
import com.example.ndev5.tstdc.R;
import com.example.ndev5.tstdc.Utilities.ListUtils;
import com.example.ndev5.tstdc.volley.VolleySingleton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class DataEntry extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View v;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String mParam1;
    private String mParam2;

    String S_et_dateofcollection, S_et_collectionamount, S_et_collectionamountFood, S_et_paytm, S_et_creditcard, S_et_cro, S_et_online, S_et_unitmr, S_et_remarks, S_et_creditsales,
            S_et_remmitance, S_et_collection, S_et_currentbalance;
    CardView cv_save;
    Date date1, date2;

    TextView tv_cwv_asondate;
    String S_cwv_asondate, S_cwv_asondate_Date;
    ImageView iv_cwv_asondate;
    CustomDateTimePickerNo custom;
    String month, day;
    String UnitName, PhoneNo, AccountNo, BankID, Activity, Password, UserId, RoleId;
    Spinner sp_activitname;
    String sp_unit_id, sp_unit_Name, Dateofcollection, Dates;
    String sp_activitname_id, sp_activitname_name;
    private String SaveDailyCollection = URLS.SaveDailyCollection;
    private String DailyCollections = URLS.DailyCollections;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBERSp
    ArrayList<Samplemyclass> Year_arraylist = new ArrayList<>();
    ArrayList<Samplemyclass> unit_arraylist = new ArrayList<>();
    private String Master = URLS.Master;
    ArrayList<TSTDC_Helper> view_arraylist = new ArrayList<>();
    ArrayList<String> DateArraylist = new ArrayList<>();
    ArrayList<String> DateArraylist1 = new ArrayList<>();
    String CollectionDate, S_et_currentbalance_Balance;
    SearchableSpinner sp_unit;
    ImageView tv_image;
    ImageView iv_iamge;
    String S_iv_iamge;
    TextView tv_unitname;
    EditText et_cheque,
            et_advanceincome,
            et_catering,
            et_barincome,
            et_otherincome,
            et_toatl1, et_grandtotal, et_total;
    ;
    EditText et_dateofcollection,
            et_collectionamount,
            et_collectionamountFood, et_paytm,
            et_creditcard, et_cro, et_online, et_unitmr, et_creditsales, et_remmitance, et_collection, et_currentbalance, et_remarks;
    ;
    String S_et_cheque, S_et_advanceincome, S_et_catering, S_et_barincome, S_et_otherincome, S_et_toatl1, S_et_grandtotal, S_et_total;
    String dayDifference;
    RelativeLayout spinnear_unit;
    float sumofAll_gf2;
    int Remitanceamount,
            Collectionamount, CollectionamountFood, Toata1, S_TotalCash, S_Sales;

    public DataEntry() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataEntry newInstance(String param1, String param2) {
        DataEntry fragment = new DataEntry();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.dataentry, container, false);


        Widgets_initilisation();
        DateTimepickers_OnClicks_Birth();
        ButtonOnclicks();
        SpinnerData();
        LoginData();
        Get_ViewList(UserId);
        ;
        return v;

    }

    private void SpinnerData() {
        //  GetActivityName();
        Get_Unit();
    }

    public void DateTimepickers_OnClicks_Birth() {


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);
        tv_cwv_asondate.setText("" + formattedDate);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        S_cwv_asondate = df1.format(c);
        S_cwv_asondate_Date = df1.format(c);
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputDateStr = S_cwv_asondate;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
            S_cwv_asondate_Date = outputFormat.format(date);
            Log.d("DATETIME", S_cwv_asondate_Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Log.d("YESRMONHDAY", S_cwv_asondate_Date);
        tv_cwv_asondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custom = new CustomDateTimePickerNo(getActivity(),
                        new CustomDateTimePickerNo.ICustomDateTimeListener() {
                            @Override
                            public void onSet(Dialog dialog, Calendar calendarSelected,
                                              Date dateSelected, int year, String monthFullName,
                                              String monthShortName, int monthNumber, int date,
                                              String weekDayFullName, String weekDayShortName,
                                              int hour24, int hour14, int min, int sec,
                                              String AM_PM) {
                                tv_cwv_asondate.setText("");

                                tv_cwv_asondate.setText((calendarSelected.get(Calendar.DAY_OF_MONTH)) + "-" + (monthFullName) + "-" + year);
                                if ((monthNumber + 1) < 10) {

                                    month = "0" + (monthNumber + 1);
                                } else {
                                    month = "" + (monthNumber + 1);
                                }
                                if (calendarSelected.get(Calendar.DAY_OF_MONTH) < 10) {

                                    day = "0" + calendarSelected.get(Calendar.DAY_OF_MONTH);
                                } else {
                                    day = "" + calendarSelected.get(Calendar.DAY_OF_MONTH);
                                }
                                S_cwv_asondate = (day + "-" + month + "-" + year);
                                S_cwv_asondate_Date = (year + "-" + month + "-" + day);
                                Log.e("dateTime", "" + tv_cwv_asondate);
                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                // custom1.set24HourFormat(true);
                custom.setDate(Calendar.getInstance());
                custom.showDialog();
            }
        });
        iv_cwv_asondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                custom = new CustomDateTimePickerNo(getActivity(),
                        new CustomDateTimePickerNo.ICustomDateTimeListener() {
                            @Override
                            public void onSet(Dialog dialog, Calendar calendarSelected,
                                              Date dateSelected, int year, String monthFullName,
                                              String monthShortName, int monthNumber, int date,
                                              String weekDayFullName, String weekDayShortName,
                                              int hour24, int hour14, int min, int sec,
                                              String AM_PM) {
                                tv_cwv_asondate.setText("");

                                tv_cwv_asondate.setText((calendarSelected.get(Calendar.DAY_OF_MONTH)) + "-" + (monthFullName) + "-" + year);

                                if ((monthNumber + 1) < 10) {

                                    month = "0" + (monthNumber + 1);
                                } else {
                                    month = "" + (monthNumber + 1);
                                }
                                if (calendarSelected.get(Calendar.DAY_OF_MONTH) < 10) {

                                    day = "0" + calendarSelected.get(Calendar.DAY_OF_MONTH);
                                } else {
                                    day = "" + calendarSelected.get(Calendar.DAY_OF_MONTH);
                                }
                                S_cwv_asondate = (day + "-" + month + "-" + year);

                                S_cwv_asondate_Date = (year + "-" + month + "-" + day);
                                Log.e("Selected Date Road", "" + S_cwv_asondate);
                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                // custom1.set24HourFormat(true);
                custom.setDate(Calendar.getInstance());
                custom.showDialog();
            }
        });


    }

    private void ButtonOnclicks() {

        tv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.tv_image:
                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, 1);


                }
            }
        });
        cv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Dateofcollection.equals("")) {
                    if (RoleId.equals("2")) {
                        Validations1();
                    } else {
                        Validations();
                    }

                } else {
                    //CalCulatetotal();

                    Validate(S_cwv_asondate, Dateofcollection);
                }


                // StringCOnversion();
                // StringNotNull();
                //Validations();
            }
        });
    }

    private void Validations1() {
        S_TotalCash = (Integer.parseInt(S_et_total));
        S_Sales = (Integer.parseInt(S_et_toatl1));
        if (sp_unit != null && sp_unit.getSelectedItem() != null) {
            if (!(sp_unit.getSelectedItem().toString().trim() == "Select")) {
                if (!(S_et_collectionamount.equals("") | S_et_collectionamount.equals("0"))) {
                    if (!(S_et_catering.equals("") | S_et_catering.equals("0"))) {
                        if (!(S_et_barincome.equals("") | S_et_barincome.equals("0"))) {
                            if (!(S_et_toatl1.equals(""))) {

                                if (!(S_et_collectionamountFood.equals(""))) {
                                    if (CollectionamountFood <= Toata1) {
                                        if (!(S_et_online.equals(""))) {
                                            if (!(S_et_cro.equals(""))) {
                                                if (!(S_et_cro.equals(""))) {
                                                    if (!(S_et_creditcard.equals(""))) {
                                                        if (!(S_et_paytm.equals(""))) {
                                                            if (!(S_et_cheque.equals(""))) {
                                                                if (!(S_et_creditsales.equals(""))) {
                                                                    if (!(S_et_advanceincome.equals(""))) {
                                                                        if (!(S_et_remmitance.equals("")) | (S_et_remmitance.equals("0"))) {
                                                                            if (Remitanceamount <= Collectionamount) {
                                                                                if (S_TotalCash == S_Sales) {

                                                                                    Log.d("TOTAL", "Falss" + "Done");
                                                                                    Log.d("TOTAL", "Falss" + S_TotalCash);
                                                                                    Log.d("TOTAL", "Falss" + S_Sales);

                                                                                    checkInternet_save();
                                                                                } else {
                                                                                    Toast.makeText(getContext(), "Total amount of Sales and  Total Should be Equal", Toast.LENGTH_SHORT).show();
                                                                                    Log.d("TOTAL", "Falss" + S_TotalCash);
                                                                                    Log.d("TOTAL", "Falss" + S_Sales);

                                                                                }

                                                                            } else {
                                                                                et_remmitance.setError("Bank Remitance Should not be Greater than Cash Collection amount");
                                                                                et_currentbalance.setText(S_et_currentbalance_Balance);
                                                                                //Toast.makeText(getContext(), "Bank Remitance Should not be Greater than Cash Collection amount", Toast.LENGTH_SHORT).show();
                                                                                Log.d("TOTAL", "Falss" + "Done");
                                                                            }
                                                                        } else {
                                                                            Toast.makeText(getContext(), "Please Enter Bank Remittance", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    } else {
                                                                        Toast.makeText(getContext(), "Please Enter Advance Income", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(getContext(), "Please Enter Credit Sales", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getContext(), "Please Enter Cheque amount", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(getContext(), "Please Enter Paytmr", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), "Please Enter Credit Card Amount", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), "Please Enter CRO", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "Please Enter Credit Card", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Please Enter Online Amount", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Cash Collection Amount should be Less than or Equal To Total Sales", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Please Enter Before Values of Total ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // et_collectionamountFood.setError("Bank Remitance Should not be Greater than Cash Collection amount");
                            }

                        } else {
                            Toast.makeText(getContext(), "Please Enter Bar Income", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please Enter Cash Catering", Toast.LENGTH_SHORT).show();
                    }
                } else

                {
                    Toast.makeText(getContext(), "Please Enter Cash Accommodation", Toast.LENGTH_SHORT).show();
                }

            } else

            {
                //  Toast.makeText(getContext(), "Please Select Unit", Toast.LENGTH_SHORT).show();
                Toast toast = Toast.makeText(getContext(), "Please Select Unit", Toast.LENGTH_SHORT);

                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                textView.setTextColor(Color.BLACK);
                toast.getView().setBackgroundColor(getResources().getColor(R.color.view_head));
                toast.show();
            }
        } else

        {
            //Toast.makeText(getContext(), "Please Select Unit", Toast.LENGTH_SHORT).show();

            Toast toast = Toast.makeText(getContext(), "Please Select Unit", Toast.LENGTH_SHORT);

            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            textView.setTextColor(Color.BLACK);
            toast.getView().setBackgroundColor(getResources().getColor(R.color.view_head));
            toast.show();
        }

    }

    private void Validate(String s_cwv_asondate, String dateofcollection) {
        try {
            Date date1;
            Date date2;
            String FinalDate = dateofcollection;
            String CurrentDate = s_cwv_asondate;
            SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);

            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            dayDifference = Long.toString(differenceDates);

            if (date1.after(date2) && dayDifference.equals("1")) {
                // Toast.makeText(getContext(), "Succes", Toast.LENGTH_SHORT).show();
                Log.d("HERE", "" + date1);
                Log.d("HERE", "" + date2);
                Log.d("HERE", "" + dayDifference);
                if (RoleId.equals("2")) {
                    Validations1();
                } else {
                    Validations();
                }
            } else {
                Log.d("HERE", "" + date1);
                Log.d("HERE", "" + date2);
                Log.d("HERE", "" + dayDifference);
                //Toast.makeText(getContext(), "Please Select Before Date", Toast.LENGTH_SHORT).show();

                Toast toast = Toast.makeText(getContext(), "Please Select Before Date", Toast.LENGTH_SHORT);

                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                textView.setTextColor(Color.BLACK);
                toast.getView().setBackgroundColor(getResources().getColor(R.color.view_head));
                toast.show();
            }


        } catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
        }
    }


    private void Validations() {
        S_TotalCash = (Integer.parseInt(S_et_total));
        S_Sales = (Integer.parseInt(S_et_toatl1));
        if (!(S_et_collectionamount.equals("") | S_et_collectionamount.equals("0"))) {
            if (!(S_et_catering.equals("") | S_et_catering.equals("0"))) {
                if (!(S_et_barincome.equals("") | S_et_barincome.equals("0"))) {
                    if (!(S_et_toatl1.equals(""))) {

                        if (!(S_et_collectionamountFood.equals(""))) {
                            if (CollectionamountFood <= Toata1) {
                                if (!(S_et_online.equals(""))) {
                                    if (!(S_et_cro.equals(""))) {
                                        if (!(S_et_cro.equals(""))) {
                                            if (!(S_et_creditcard.equals(""))) {
                                                if (!(S_et_paytm.equals(""))) {
                                                    if (!(S_et_cheque.equals(""))) {
                                                        if (!(S_et_creditsales.equals(""))) {
                                                            if (!(S_et_advanceincome.equals(""))) {
                                                                if (!(S_et_remmitance.equals("")) | (S_et_remmitance.equals("0"))) {
                                                                    if (Remitanceamount <= Collectionamount) {
                                                                        if (S_TotalCash == S_Sales) {

                                                                            Log.d("TOTAL", "Falss" + "Done");
                                                                            Log.d("TOTAL", "Falss" + S_TotalCash);
                                                                            Log.d("TOTAL", "Falss" + S_Sales);

                                                                            checkInternet_save();
                                                                        } else {
                                                                            Toast.makeText(getContext(), "Total amount of Sales and  Total Should be Equal", Toast.LENGTH_SHORT).show();
                                                                            Log.d("TOTAL", "Falss" + S_TotalCash);
                                                                            Log.d("TOTAL", "Falss" + S_Sales);

                                                                        }

                                                                    } else {
                                                                        et_remmitance.setError("Bank Remitance Should not be Greater than Cash Collection amount");
                                                                        et_currentbalance.setText(S_et_currentbalance_Balance);
                                                                        //Toast.makeText(getContext(), "Bank Remitance Should not be Greater than Cash Collection amount", Toast.LENGTH_SHORT).show();
                                                                        Log.d("TOTAL", "Falss" + "Done");
                                                                    }
                                                                } else {
                                                                    Toast.makeText(getContext(), "Please Enter Bank Remittance", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getContext(), "Please Enter Advance Income", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(getContext(), "Please Enter Credit Sales", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), "Please Enter Cheque amount", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), "Please Enter Paytmr", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "Please Enter Credit Card Amount", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Please Enter CRO", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Please Enter Credit Card", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Please Enter Online Amount", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Cash Collection Amount should be Less than or Equal To Total Sales", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Please Enter Before Values of Total ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // et_collectionamountFood.setError("Bank Remitance Should not be Greater than Cash Collection amount");
                    }

                } else {
                    Toast.makeText(getContext(), "Please Enter Bar Income", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please Enter Cash Catering", Toast.LENGTH_SHORT).show();
            }
        } else

        {
            Toast.makeText(getContext(), "Please Enter Cash Accommodation", Toast.LENGTH_SHORT).show();
        }


    }

    public void checkInternet_save() {
        ConnectivityManager connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))) {

            save_Details();
        } else if (connec != null && (
                (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) ||
                        (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED))) {

            FragmentManager fm = getActivity().getSupportFragmentManager();
            Connectivity td = new Connectivity();
            td.show(fm, "NO CONNECTION");
        }
    }

    private void save_Details() {
        StringCOnversion();
        StringNotNull();
        LoginData();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("UserId", "" + UserId);
            jsonBody.put("Password", "" + Password);
            jsonBody.put("UnitId", "" + UserId);
            jsonBody.put("Activity", "" + Activity);
            jsonBody.put("ActionType", "" + "0");
            jsonBody.put("BarIncome", "" + S_et_barincome);
            jsonBody.put("OtherIncome", "" + S_et_otherincome);
            jsonBody.put("Total", "" + S_et_toatl1);
            jsonBody.put("Paytm", "" + S_et_paytm);
            jsonBody.put("Cash", "" + S_et_collectionamountFood);
            jsonBody.put("CreditCard", "" + S_et_creditcard);
            jsonBody.put("Cash_Accommodation", S_et_collectionamount);
            jsonBody.put("Cash_Food", "" + S_et_catering);

            jsonBody.put("CRO", "" + S_et_cro);
            jsonBody.put("Online", "" + S_et_online);
            jsonBody.put("Cheque", "" + S_et_cheque);
            jsonBody.put("AdvanceIncome", "" + S_et_advanceincome);
            jsonBody.put("TotalCash", "" + S_et_total);
            jsonBody.put("CreditSales", "" + S_et_creditsales);
            jsonBody.put("Remmitance", "" + S_et_remmitance);
            jsonBody.put("BalanceToRemmitance", "" + S_et_currentbalance);
            jsonBody.put("CollectionDate", "" + S_cwv_asondate_Date);
            jsonBody.put("Remarks", "" + "");
            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "SaveDailyCollection Insert " + "Input" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, SaveDailyCollection, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "SaveDailyCollection Insert" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("Code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("1")) {
                            //  Toast.makeText(getContext(), "" + message, Toast.LENGTH_LONG).show();

                            et_collectionamount.setText("");


                            et_collectionamount.setText("");
                            et_catering.setText("");
                            et_barincome.setText("");
                            et_otherincome.setText("");
                            et_toatl1.setText("");
                            et_collectionamountFood.setText("");
                            et_online.setText("");
                            et_cro.setText("");
                            et_creditcard.setText("");
                            et_paytm.setText("");
                            et_cheque.setText("");
                            et_creditsales.setText("");
                            et_advanceincome.setText("");
                            et_total.setText("");
                            et_remmitance.setText("");
                            et_currentbalance.setText("");


                            Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);

                            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                            textView.setTextColor(Color.BLACK);
                            toast.getView().setBackgroundColor(getResources().getColor(R.color.view_head));
                            toast.show();

                        } else {
                            Toast.makeText(getContext(), "" + message, Toast.LENGTH_LONG).show();

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
                    34530000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void LoginData() {
        SharedPreferences bb = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        UnitName = bb.getString("UnitName", "");
        PhoneNo = bb.getString("PhoneNo", "");
        AccountNo = bb.getString("AccountNo", "");
        BankID = bb.getString("BankID", "");
        Activity = bb.getString("Activity", "");
        UserId = bb.getString("UserId", "");
        Password = bb.getString("Password", "");
        RoleId = bb.getString("RoleId", "");


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("OnActivityResult");

        switch (requestCode) {


            case 1:
                if (requestCode == 1 && resultCode == RESULT_OK) {
                    if (requestCode == 1) {
                        Bundle extras = data.getExtras();
                        Bitmap bmp = (Bitmap) extras.get("data");
                        Uri picUri = data.getData();
                        //  performCrop(picUri);
                        iv_iamge.setImageBitmap(bmp);
                        S_iv_iamge = timestampItAndSave(bmp);
                        Log.d("String Image", S_iv_iamge);
                    }
                }
            case 7:
                if (data != null) {
                    Bundle extras1 = data.getExtras();
                    if (extras1 != null) {
                        Bitmap bmp = (Bitmap) extras1.get("data");
                        iv_iamge.setImageBitmap(bmp);
                        S_iv_iamge = timestampItAndSave(bmp);
                        Log.d("String Image", S_iv_iamge);
                    }
                }
                break;


//            case 6:
//                if (requestCode == 6 && resultCode ==RESULT_OK) {
//                    Bundle extras = data.getExtras();
//                    Bitmap bmp = (Bitmap) extras.get("data");
//                    Uri picUri = data.getData();
//                    performCrop6(picUri);
//                    iv_6.setImageBitmap(bmp);
//                    iv6 = timestampItAndSave(bmp);
//                }
//                break;
//            case 12 :
//                Bundle extras6 = data.getExtras();
//                if (extras6 != null) {
//                    Bitmap bmp = (Bitmap) extras6.get("data");
//                    iv_6.setImageBitmap(bmp);
//                    iv6 = timestampItAndSave(bmp);
//                }
//                break;
        }
    }

    private String timestampItAndSave(Bitmap toEdit) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        toEdit.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void Widgets_initilisation() {
        TextView tv_fragmentName = (TextView) v.findViewById(R.id.tv_fragmentName);
        tv_fragmentName.setText("Daily Collection Data");
        et_dateofcollection = v.findViewById(R.id.et_dateofcollection);
        et_collectionamount = v.findViewById(R.id.et_collectionamount);
        et_collectionamountFood = v.findViewById(R.id.et_collectionamountFood);
        et_paytm = v.findViewById(R.id.et_paytm);
        et_creditcard = v.findViewById(R.id.et_creditcard);
        et_cro = v.findViewById(R.id.et_cro);
        et_online = v.findViewById(R.id.et_online);
        et_unitmr = v.findViewById(R.id.et_unitmr);
        et_creditsales = v.findViewById(R.id.et_creditsales);
        et_collection = v.findViewById(R.id.et_collection);
        et_remmitance = v.findViewById(R.id.et_remmitance);
        et_currentbalance = v.findViewById(R.id.et_currentbalance);
        cv_save = v.findViewById(R.id.cv_save);
        tv_cwv_asondate = v.findViewById(R.id.tv_cwv_asondate);
        iv_cwv_asondate = v.findViewById(R.id.iv_cwv_asondate);
        et_remarks = v.findViewById(R.id.et_remarks);
        sp_activitname = v.findViewById(R.id.sp_activitname);
        iv_iamge = v.findViewById(R.id.iv_iamge);
        tv_image = v.findViewById(R.id.tv_image);
        tv_unitname = v.findViewById(R.id.tv_unitname);


        et_grandtotal = v.findViewById(R.id.et_grandtotal);
        et_cheque = v.findViewById(R.id.et_cheque);
        et_advanceincome = v.findViewById(R.id.et_advanceincome);
        et_catering = v.findViewById(R.id.et_catering);
        et_barincome = v.findViewById(R.id.et_barincome);
        et_otherincome = v.findViewById(R.id.et_otherincome);
        et_toatl1 = v.findViewById(R.id.et_toatl1);
        et_total = v.findViewById(R.id.et_total);
        sp_unit = v.findViewById(R.id.sp_unit);
        et_currentbalance.setEnabled(false);
        spinnear_unit = v.findViewById(R.id.spinnear_unit);
        et_total.setEnabled(false);
        // IPUTTYPE();
        LoginData();
        if (RoleId.equals("2")) {
            spinnear_unit.setVisibility(View.VISIBLE);
            sp_unit.setVisibility(View.VISIBLE);
            tv_unitname.setVisibility(View.GONE);
            Log.d("ADMN", "Checked" + RoleId.equalsIgnoreCase("2"));
        } else {
            tv_unitname.setText(UnitName + "");
            spinnear_unit.setVisibility(View.GONE);
            sp_unit.setVisibility(View.GONE);
            tv_unitname.setVisibility(View.VISIBLE);
            Log.d("ADMN", "UnChecked" + RoleId.equalsIgnoreCase("2"));
        }
        StringCOnversion();
        StringNotNull();
        CalCulatetotal();


    }

    private void StringCOnversion() {

        S_et_cheque = et_cheque.getText().toString();
        S_et_advanceincome = et_advanceincome.getText().toString();
        S_et_catering = et_catering.getText().toString();
        S_et_barincome = et_barincome.getText().toString();
        S_et_otherincome = et_otherincome.getText().toString();
        S_et_toatl1 = et_toatl1.getText().toString();
        S_et_grandtotal = et_grandtotal.getText().toString();
        S_et_total = et_total.getText().toString();

        ;
        S_et_collectionamount = et_collectionamount.getText().toString();
        S_et_collectionamountFood = et_collectionamountFood.getText().toString();
        S_et_paytm = et_paytm.getText().toString();
        S_et_creditcard = et_creditcard.getText().toString();
        S_et_cro = et_cro.getText().toString();
        S_et_online = et_online.getText().toString();
        S_et_creditsales = et_creditsales.getText().toString();
        S_et_remmitance = et_remmitance.getText().toString();
        S_et_currentbalance = et_currentbalance.getText().toString();
        S_et_currentbalance = et_currentbalance.getText().toString();
    }

    private void StringNotNull() {


        if (S_et_dateofcollection != null) {

        } else {
            S_et_dateofcollection = "";
        }
        ;

        if (S_et_collectionamount == null || S_et_collectionamount.isEmpty()) {
            S_et_collectionamount = "0";
        } else {
            Log.d("Collectiondata", S_et_collectionamount);
        }

        if (S_et_catering == null || S_et_catering.isEmpty()) {
            S_et_catering = "0";
            Log.d("Collectiondata", S_et_catering);
        } else {

            Log.d("Collectiondata", S_et_catering);
        }
        if (S_et_barincome == null || S_et_barincome.isEmpty()) {
            S_et_barincome = "0";
            Log.d("Collectiondata", S_et_barincome);
        } else {


            Log.d("Collectiondata", S_et_barincome);
        }
        if ((S_et_otherincome == null) || S_et_otherincome.isEmpty()) {
            S_et_otherincome = "0";

            Log.d("Collectiondata", S_et_otherincome);
        } else {


            Log.d("Collectiondata", S_et_otherincome);
        }


        if ((S_et_collectionamountFood == null) || S_et_collectionamountFood.isEmpty()) {
            S_et_collectionamountFood
                    = "0";
            Log.d("Collectiondata", S_et_otherincome);
        } else {

            Log.d("Collectiondata", S_et_otherincome);
        }
        if ((S_et_online == null) || S_et_online.isEmpty()) {
            S_et_online
                    = "0";
        } else {

        }
        if ((S_et_cro == null) || S_et_cro.isEmpty()) {
            S_et_cro
                    = "0";
        } else {

        }
        if ((S_et_creditcard == null) || S_et_creditcard.isEmpty()) {

            S_et_creditcard
                    = "0";
        } else {

        }
        if ((S_et_paytm == null) || S_et_paytm.isEmpty()) {

            S_et_paytm
                    = "0";
        } else {

        }
        if ((S_et_cheque == null) || S_et_cheque.isEmpty()) {
            S_et_cheque
                    = "0";
        } else {

        }
        if ((S_et_creditsales == null) || S_et_creditsales.isEmpty()) {
            S_et_creditsales
                    = "0";

        } else {

        }
        if ((S_et_advanceincome == null) || S_et_advanceincome.isEmpty()) {

            S_et_advanceincome
                    = "0";
        } else {

        }
        if ((S_et_remmitance == null) || S_et_remmitance.isEmpty()) {

            S_et_remmitance
                    = "0";
        } else {

        }
        if (S_et_toatl1 == null || S_et_toatl1.isEmpty()) {
            S_et_toatl1 = "0";
        } else {

        }
    }

    private void CalCulatetotal() {
        StringCOnversion();
        StringNotNull();


        et_collectionamount.addTextChangedListener(new TextWatcher() {
            int keyDel;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                et_collectionamount.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = et_collectionamount.getText().length();

                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {


                boolean isValidate = true;

                if (et_collectionamount.getText().toString().length() < 0) {
                    et_collectionamount.setError("Value Required");
                    isValidate = false;
                }
                if (et_catering
                        .getText().toString().length() < 0) {
                    et_catering
                            .setError("Value Required");
                    isValidate = false;
                }

                if (et_barincome
                        .getText().toString().length() < 0) {
                    et_barincome
                            .setError("Value Required");
                    isValidate = false;
                }
                if (et_otherincome

                        .getText().toString().length() < 0) {
                    et_otherincome

                            .setError("Value Required");
                    isValidate = false;
                }

                if (isValidate) {
                    Log.e("VOLLEY", "test" + "Input YES");


                    if ((!(S_et_collectionamount).isEmpty()) && (!(S_et_catering).isEmpty()) && (!(S_et_barincome).isEmpty()) && (!(S_et_otherincome).isEmpty())) {
                        try {
                            StringCOnversion();
                            StringNotNull();

                            float sumofAll_gf = addition(Float.parseFloat(S_et_collectionamount),
                                    Float.parseFloat(S_et_catering.toString()),
                                    Float.parseFloat(S_et_barincome.toString()),
                                    Float.parseFloat(S_et_otherincome.toString()));
                            String Total = String.valueOf(sumofAll_gf);

                            et_toatl1.setText("" + Total.replace(".0", ""));
                            Log.d("GETTOTAL", "" + sumofAll_gf);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.e("VOLLEY", "test" + "Input NO 1 ");
                    }
                } else {
                    Log.e("VOLLEY", "test" + "Input NO");
                }
                //setStatus();
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
        et_catering.addTextChangedListener(new TextWatcher() {
            int keyDel;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                et_catering.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = et_catering.getText().length();

                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {


                boolean isValidate = true;

                if (et_collectionamount.getText().toString().length() < 0) {
                    et_collectionamount.setError("Value Required");
                    isValidate = false;
                }
                if (et_catering
                        .length() < 0) {
                    et_catering
                            .setError("Value Required");
                    isValidate = false;
                }

                if (et_barincome
                        .getText().toString().length() < 0) {
                    et_barincome
                            .setError("Value Required");
                    isValidate = false;
                }
                if (et_otherincome

                        .getText().toString().length() < 0) {
                    et_otherincome

                            .setError("Value Required");
                    isValidate = false;
                }

                if (isValidate) {
                    Log.e("VOLLEY", "test" + "Input YES");


                    if ((!(S_et_collectionamount).isEmpty()) && (!(S_et_catering).isEmpty()) && (!(S_et_barincome).isEmpty()) && (!(S_et_otherincome).isEmpty())) {
                        try {
                            StringCOnversion();
                            StringNotNull();
                            float sumofAll_gf = addition(Float.parseFloat(S_et_collectionamount),
                                    Float.parseFloat(S_et_catering.toString()),
                                    Float.parseFloat(S_et_barincome.toString()),
                                    Float.parseFloat(S_et_otherincome.toString()));
                            //et_toatl1.setText("" + sumofAll_gf);
                            String Total = String.valueOf(sumofAll_gf);

                            et_toatl1.setText("" + Total.replace(".0", ""));
                            Log.d("GETTOTAL", "" + sumofAll_gf);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("VOLLEY", "test" + "Input NO 1 ");
                    }
                } else {
                    Log.e("VOLLEY", "test" + "Input NO");
                }
                //setStatus();
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
        et_barincome.addTextChangedListener(new TextWatcher() {
            int keyDel;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                et_barincome.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = et_barincome.getText().length();

                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {


                boolean isValidate = true;

                if (et_collectionamount.getText().toString().length() < 0) {
                    et_collectionamount.setError("Value Required");
                    isValidate = false;
                }
                if (et_catering
                        .getText().toString().length() < 0) {
                    et_catering
                            .setError("Value Required");
                    isValidate = false;
                }

                if (et_barincome
                        .getText().toString().length() < 0) {
                    et_barincome
                            .setError("Value Required");
                    isValidate = false;
                }
                if (et_otherincome

                        .getText().toString().length() < 0) {
                    et_otherincome

                            .setError("Value Required");
                    isValidate = false;
                }

                if (isValidate) {
                    Log.e("VOLLEY", "test" + "Input YES");


                    if ((!(S_et_collectionamount).isEmpty()) && (!(S_et_catering).isEmpty()) && (!(S_et_barincome).isEmpty()) && (!(S_et_otherincome).isEmpty())) {
                        try {
                            StringCOnversion();
                            StringNotNull();
                            float sumofAll_gf = addition(Float.parseFloat(S_et_collectionamount),
                                    Float.parseFloat(S_et_catering.toString()),
                                    Float.parseFloat(S_et_barincome.toString()),
                                    Float.parseFloat(S_et_otherincome.toString()));
                            String Total = String.valueOf(sumofAll_gf);

                            et_toatl1.setText("" + Total.replace(".0", ""));
                            Log.d("GETTOTAL", "" + sumofAll_gf);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("VOLLEY", "test" + "Input NO 1 ");
                    }
                } else {
                    Log.e("VOLLEY", "test" + "Input NO");
                }
                //setStatus();
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
        et_otherincome.addTextChangedListener(new TextWatcher() {
            int keyDel;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                et_otherincome.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = et_otherincome.getText().length();

                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {


                boolean isValidate = true;

                if (et_collectionamount.getText().toString().length() < 0) {
                    et_collectionamount.setError("Value Required");
                    isValidate = false;
                }
                if (et_catering
                        .getText().toString().length() < 0) {
                    et_catering
                            .setError("Value Required");
                    isValidate = false;
                }

                if (et_barincome
                        .getText().toString().length() < 0) {
                    et_barincome
                            .setError("Value Required");
                    isValidate = false;
                }
                if (et_otherincome

                        .getText().toString().length() < 0) {
                    et_otherincome

                            .setError("Value Required");
                    isValidate = false;
                }

                if (isValidate) {
                    Log.e("VOLLEY", "test" + "Input YES");


                    if ((!(S_et_collectionamount).isEmpty()) && (!(S_et_catering).isEmpty()) && (!(S_et_barincome).isEmpty()) && (!(S_et_otherincome).isEmpty())) {
                        try {
                            StringCOnversion();
                            StringNotNull();
                            float sumofAll_gf = addition(Float.parseFloat(S_et_collectionamount),
                                    Float.parseFloat(S_et_catering.toString()),
                                    Float.parseFloat(S_et_barincome.toString()),
                                    Float.parseFloat(S_et_otherincome.toString()));
                            String Total = String.valueOf(sumofAll_gf);

                            et_toatl1.setText("" + Total.replace(".0", ""));
                            Log.d("GETTOTAL", "" + sumofAll_gf);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Log.e("VOLLEY", "test" + "Input NO 1 ");
                    }
                } else {
                    Log.e("VOLLEY", "test" + "Input NO");
                }
                //setStatus();
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                boolean isValidate = true;

            }
        });


        et_collectionamountFood
                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_collectionamountFood.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_collectionamountFood.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;

                        if (et_collectionamountFood.getText().toString().length() < 0) {
                            et_collectionamountFood.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_online

                                .getText().toString().length() < 0) {
                            et_online

                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (et_cro
                                .getText().toString().length() < 0) {
                            et_cro
                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditcard


                                .getText().toString().length() < 0) {
                            et_creditcard


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_paytm


                                .getText().toString().length() < 0) {
                            et_paytm


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_cheque


                                .getText().toString().length() < 0) {
                            et_cheque


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditsales


                                .getText().toString().length() < 0) {
                            et_creditsales


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_advanceincome


                                .getText().toString().length() < 0) {
                            et_advanceincome


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");
                            StringCOnversion();
                            StringNotNull();


                            if ((!S_et_collectionamountFood.toString().isEmpty()) && (!S_et_online.toString().isEmpty()) && (!S_et_cro.toString().isEmpty()) &&
                                    (!(S_et_creditcard.toString().isEmpty()) && !S_et_paytm.toString().isEmpty() && (!S_et_cheque.toString()
                                            .isEmpty()) && (!S_et_creditsales.toString().isEmpty()) && (!S_et_advanceincome.toString().isEmpty()))) {

                                StringCOnversion();
                                StringNotNull();

                                try {
                                    float sumofAll_gf = addition1(Float.parseFloat(S_et_collectionamountFood.toString()
                                            ),
                                            Float.parseFloat(S_et_online.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cro.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditcard.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_paytm.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cheque.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditsales.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_advanceincome.toString().toString()
                                            ));

                                    // et_total.setText("" + sumofAll_gf);
                                    String Total = String.valueOf(sumofAll_gf);

                                    et_total.setText("" + Total.replace(".0", ""));
                                    CollectionamountFood = Integer.parseInt(S_et_collectionamountFood.toString());
                                    Toata1 = Integer.parseInt(S_et_toatl1.toString());

                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
        et_online
                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_online.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_online.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;

                        if (et_collectionamountFood.getText().toString().length() < 0) {
                            et_collectionamountFood.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_online

                                .getText().toString().length() < 0) {
                            et_online

                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (et_cro
                                .getText().toString().length() < 0) {
                            et_cro
                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditcard


                                .getText().toString().length() < 0) {
                            et_creditcard


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_paytm


                                .getText().toString().length() < 0) {
                            et_paytm


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_cheque


                                .getText().toString().length() < 0) {
                            et_cheque


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditsales


                                .getText().toString().length() < 0) {
                            et_creditsales


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_advanceincome


                                .getText().toString().length() < 0) {
                            et_advanceincome


                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");

                            if ((!S_et_collectionamountFood.toString().isEmpty()) && (!S_et_online.toString().isEmpty()) && (!S_et_cro.toString().isEmpty()) &&
                                    (!(S_et_creditcard.toString().isEmpty()) && !S_et_paytm.toString().isEmpty() && (!S_et_cheque.toString()
                                            .isEmpty()) && (!S_et_creditsales.toString().isEmpty()) && (!S_et_advanceincome.toString().isEmpty()))) {
                                StringCOnversion();
                                StringNotNull();

                                try {
                                    float sumofAll_gf = addition1(Float.parseFloat(S_et_collectionamountFood.toString()
                                            ),
                                            Float.parseFloat(S_et_online.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cro.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditcard.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_paytm.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cheque.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditsales.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_advanceincome.toString().toString()
                                            ));

                                    String Total = String.valueOf(sumofAll_gf);

                                    et_total.setText("" + Total.replace(".0", ""));
                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
        et_cro
                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_cro.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_cro.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;

                        if (et_collectionamountFood.getText().toString().length() < 0) {
                            et_collectionamountFood.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_online

                                .getText().toString().length() < 0) {
                            et_online

                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (et_cro
                                .getText().toString().length() < 0) {
                            et_cro
                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditcard


                                .getText().toString().length() < 0) {
                            et_creditcard


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_paytm


                                .getText().toString().length() < 0) {
                            et_paytm


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_cheque


                                .getText().toString().length() < 0) {
                            et_cheque


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditsales


                                .getText().toString().length() < 0) {
                            et_creditsales


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_advanceincome


                                .getText().toString().length() < 0) {
                            et_advanceincome


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");


                            if ((!S_et_collectionamountFood.toString().isEmpty()) && (!S_et_online.toString().isEmpty()) && (!S_et_cro.toString().isEmpty()) &&
                                    (!(S_et_creditcard.toString().isEmpty()) && !S_et_paytm.toString().isEmpty() && (!S_et_cheque.toString()
                                            .isEmpty()) && (!S_et_creditsales.toString().isEmpty()) && (!S_et_advanceincome.toString().isEmpty()))) {
                                StringCOnversion();
                                StringNotNull();

                                try {
                                    float sumofAll_gf = addition1(Float.parseFloat(S_et_collectionamountFood.toString()
                                            ),
                                            Float.parseFloat(S_et_online.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cro.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditcard.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_paytm.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cheque.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditsales.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_advanceincome.toString().toString()
                                            ));

                                    String Total = String.valueOf(sumofAll_gf);

                                    et_total.setText("" + Total.replace(".0", ""));
                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
        et_creditcard
                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_creditcard.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_creditcard.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;
                        if (et_collectionamountFood.getText().toString().length() < 0) {
                            et_collectionamountFood.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_online

                                .getText().toString().length() < 0) {
                            et_online

                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (et_cro
                                .getText().toString().length() < 0) {
                            et_cro
                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditcard


                                .getText().toString().length() < 0) {
                            et_creditcard


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_paytm


                                .getText().toString().length() < 0) {
                            et_paytm


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_cheque


                                .getText().toString().length() < 0) {
                            et_cheque


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditsales


                                .getText().toString().length() < 0) {
                            et_creditsales


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_advanceincome


                                .getText().toString().length() < 0) {
                            et_advanceincome


                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");

                            if ((!S_et_collectionamountFood.toString().isEmpty()) && (!S_et_online.toString().isEmpty()) && (!S_et_cro.toString().isEmpty()) &&
                                    (!(S_et_creditcard.toString().isEmpty()) && !S_et_paytm.toString().isEmpty() && (!S_et_cheque.toString()
                                            .isEmpty()) && (!S_et_creditsales.toString().isEmpty()) && (!S_et_advanceincome.toString().isEmpty()))) {
                                StringCOnversion();
                                StringNotNull();

                                try {
                                    float sumofAll_gf = addition1(Float.parseFloat(S_et_collectionamountFood.toString()
                                            ),
                                            Float.parseFloat(S_et_online.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cro.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditcard.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_paytm.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cheque.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditsales.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_advanceincome.toString().toString()
                                            ));

                                    String Total = String.valueOf(sumofAll_gf);

                                    et_total.setText("" + Total.replace(".0", ""));
                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });


        et_paytm

                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_paytm.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_paytm.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;

                        if (et_collectionamountFood.getText().toString().length() < 0) {
                            et_collectionamountFood.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_online

                                .getText().toString().length() < 0) {
                            et_online

                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (et_cro
                                .getText().toString().length() < 0) {
                            et_cro
                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditcard


                                .getText().toString().length() < 0) {
                            et_creditcard


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_paytm


                                .getText().toString().length() < 0) {
                            et_paytm


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_cheque


                                .getText().toString().length() < 0) {
                            et_cheque


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditsales


                                .getText().toString().length() < 0) {
                            et_creditsales


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_advanceincome


                                .getText().toString().length() < 0) {
                            et_advanceincome


                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");

                            if ((!S_et_collectionamountFood.toString().isEmpty()) && (!S_et_online.toString().isEmpty()) && (!S_et_cro.toString().isEmpty()) &&
                                    (!(S_et_creditcard.toString().isEmpty()) && !S_et_paytm.toString().isEmpty() && (!S_et_cheque.toString()
                                            .isEmpty()) && (!S_et_creditsales.toString().isEmpty()) && (!S_et_advanceincome.toString().isEmpty()))) {
                                StringCOnversion();
                                StringNotNull();

                                try {
                                    float sumofAll_gf = addition1(Float.parseFloat(S_et_collectionamountFood.toString()
                                            ),
                                            Float.parseFloat(S_et_online.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cro.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditcard.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_paytm.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cheque.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditsales.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_advanceincome.toString().toString()
                                            ));

                                    String Total = String.valueOf(sumofAll_gf);

                                    et_total.setText("" + Total.replace(".0", ""));
                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
        et_cheque

                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_cheque.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_cheque.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;

                        if (et_collectionamountFood.getText().toString().length() < 0) {
                            et_collectionamountFood.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_online

                                .getText().toString().length() < 0) {
                            et_online

                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (et_cro
                                .getText().toString().length() < 0) {
                            et_cro
                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditcard


                                .getText().toString().length() < 0) {
                            et_creditcard


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_paytm


                                .getText().toString().length() < 0) {
                            et_paytm


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_cheque


                                .getText().toString().length() < 0) {
                            et_cheque


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditsales


                                .getText().toString().length() < 0) {
                            et_creditsales


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_advanceincome


                                .getText().toString().length() < 0) {
                            et_advanceincome


                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");

                            if ((!S_et_collectionamountFood.toString().isEmpty()) && (!S_et_online.toString().isEmpty()) && (!S_et_cro.toString().isEmpty()) &&
                                    (!(S_et_creditcard.toString().isEmpty()) && !S_et_paytm.toString().isEmpty() && (!S_et_cheque.toString()
                                            .isEmpty()) && (!S_et_creditsales.toString().isEmpty()) && (!S_et_advanceincome.toString().isEmpty()))) {
                                StringCOnversion();
                                StringNotNull();

                                try {
                                    float sumofAll_gf = addition1(Float.parseFloat(S_et_collectionamountFood.toString()
                                            ),
                                            Float.parseFloat(S_et_online.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cro.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditcard.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_paytm.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cheque.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditsales.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_advanceincome.toString().toString()
                                            ));

                                    String Total = String.valueOf(sumofAll_gf);

                                    et_total.setText("" + Total.replace(".0", ""));
                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
        et_cro
                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_cro.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_cro.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;

                        if (et_collectionamountFood.getText().toString().length() < 0) {
                            et_collectionamountFood.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_online

                                .getText().toString().length() < 0) {
                            et_online

                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (et_cro
                                .getText().toString().length() < 0) {
                            et_cro
                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditcard


                                .getText().toString().length() < 0) {
                            et_creditcard


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_paytm


                                .getText().toString().length() < 0) {
                            et_paytm


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_cheque


                                .getText().toString().length() < 0) {
                            et_cheque


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditsales


                                .getText().toString().length() < 0) {
                            et_creditsales


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_advanceincome


                                .getText().toString().length() < 0) {
                            et_advanceincome


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");


                            if ((!S_et_collectionamountFood.toString().isEmpty()) && (!S_et_online.toString().isEmpty()) && (!S_et_cro.toString().isEmpty()) &&
                                    (!(S_et_creditcard.toString().isEmpty()) && !S_et_paytm.toString().isEmpty() && (!S_et_cheque.toString()
                                            .isEmpty()) && (!S_et_creditsales.toString().isEmpty()) && (!S_et_advanceincome.toString().isEmpty()))) {
                                StringCOnversion();
                                StringNotNull();

                                try {
                                    float sumofAll_gf = addition1(Float.parseFloat(S_et_collectionamountFood.toString()
                                            ),
                                            Float.parseFloat(S_et_online.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cro.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditcard.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_paytm.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cheque.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditsales.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_advanceincome.toString().toString()
                                            ));

                                    String Total = String.valueOf(sumofAll_gf);

                                    et_total.setText("" + Total.replace(".0", ""));
                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
        et_creditsales
                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_creditsales.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_creditsales.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;

                        if (et_collectionamountFood.getText().toString().length() < 0) {
                            et_collectionamountFood.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_online

                                .getText().toString().length() < 0) {
                            et_online

                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (et_cro
                                .getText().toString().length() < 0) {
                            et_cro
                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditcard


                                .getText().toString().length() < 0) {
                            et_creditcard


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_paytm


                                .getText().toString().length() < 0) {
                            et_paytm


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_cheque


                                .getText().toString().length() < 0) {
                            et_cheque


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditsales


                                .getText().toString().length() < 0) {
                            et_creditsales


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_advanceincome


                                .getText().toString().length() < 0) {
                            et_advanceincome


                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");


                            if ((!S_et_collectionamountFood.toString().isEmpty()) && (!S_et_online.toString().isEmpty()) && (!S_et_cro.toString().isEmpty()) &&
                                    (!(S_et_creditcard.toString().isEmpty()) && !S_et_paytm.toString().isEmpty() && (!S_et_cheque.toString()
                                            .isEmpty()) && (!S_et_creditsales.toString().isEmpty()) && (!S_et_advanceincome.toString().isEmpty()))) {
                                StringCOnversion();
                                StringNotNull();

                                try {
                                    float sumofAll_gf = addition1(Float.parseFloat(S_et_collectionamountFood.toString()
                                            ),
                                            Float.parseFloat(S_et_online.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cro.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditcard.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_paytm.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cheque.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditsales.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_advanceincome.toString().toString()
                                            ));

                                    String Total = String.valueOf(sumofAll_gf);

                                    et_total.setText("" + Total.replace(".0", ""));
                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
        et_advanceincome

                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_advanceincome.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_advanceincome.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;

                        if (et_collectionamountFood.getText().toString().length() < 0) {
                            et_collectionamountFood.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_online

                                .getText().toString().length() < 0) {
                            et_online

                                    .setError("Value Required");
                            isValidate = false;
                        }

                        if (et_cro
                                .getText().toString().length() < 0) {
                            et_cro
                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditcard


                                .getText().toString().length() < 0) {
                            et_creditcard


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_paytm


                                .getText().toString().length() < 0) {
                            et_paytm


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_cheque


                                .getText().toString().length() < 0) {
                            et_cheque


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_creditsales


                                .getText().toString().length() < 0) {
                            et_creditsales


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (et_advanceincome


                                .getText().toString().length() < 0) {
                            et_advanceincome


                                    .setError("Value Required");
                            isValidate = false;
                        }
                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");


                            if ((!S_et_collectionamountFood.toString().isEmpty()) && (!S_et_online.toString().isEmpty()) && (!S_et_cro.toString().isEmpty()) &&
                                    (!(S_et_creditcard.toString().isEmpty()) && !S_et_paytm.toString().isEmpty() && (!S_et_cheque.toString()
                                            .isEmpty()) && (!S_et_creditsales.toString().isEmpty()) && (!S_et_advanceincome.toString().isEmpty()))) {
                                StringCOnversion();
                                StringNotNull();

                                try {
                                    float sumofAll_gf = addition1(Float.parseFloat(S_et_collectionamountFood.toString()
                                            ),
                                            Float.parseFloat(S_et_online.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cro.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditcard.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_paytm.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_cheque.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_creditsales.toString().toString()
                                            ),
                                            Float.parseFloat(S_et_advanceincome.toString().toString()
                                            ));

                                    String Total = String.valueOf(sumofAll_gf);

                                    et_total.setText("" + Total.replace(".0", ""));
                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
        et_remmitance

                .addTextChangedListener(new TextWatcher() {
                    int keyDel;

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        et_remmitance.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });
                        if (keyDel == 0) {
                            int len = et_remmitance.getText().length();

                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {


                        boolean isValidate = true;

                        if (et_remmitance.getText().toString().length() < 0) {
                            et_remmitance.setError("Value Required");
                            isValidate = false;
                        }
                        if (et_collectionamountFood

                                .getText().toString().length() < 0) {
                            et_collectionamountFood

                                    .setError("Value Required");
                            isValidate = false;
                        }


                        if (isValidate) {
                            Log.e("VOLLEY", "test" + "Input YES");
                            et_currentbalance.setText(S_et_currentbalance_Balance);
                            Get_ViewList(UserId);
                            if ((!et_remmitance.getText().toString().isEmpty()) && (!et_collectionamountFood.getText().toString().isEmpty())) {
                                Remitanceamount = Integer.parseInt(et_remmitance.getText().toString());

                                Collectionamount = Integer.parseInt(et_collectionamountFood.getText().toString());


                                try {
                                    sumofAll_gf2 = addition2(Float.parseFloat(S_et_currentbalance.toString()
                                            ),
                                            Float.parseFloat(et_collectionamountFood.getText().toString()
                                            )
                                    );
                                    Log.d("GETTOTAL", "" + sumofAll_gf2);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    float sumofAll_gf = addition3(sumofAll_gf2, Float.parseFloat(et_remmitance.getText().toString()
                                            )


                                    );

                                    et_currentbalance.setText("" + sumofAll_gf);
                                    Log.d("GETTOTAL", "" + sumofAll_gf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                            }
                        } else

                        {
                            Log.e("VOLLEY", "test" + "Input NO");
                        }
                        //setStatus();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }
                });


        et_collectionamountFood

                .addTextChangedListener(new

                                                TextWatcher() {
                                                    int keyDel;

                                                    @Override
                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                        et_collectionamountFood.setOnKeyListener(new View.OnKeyListener() {
                                                            @Override
                                                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                                                    keyDel = 1;
                                                                return false;
                                                            }
                                                        });
                                                        if (keyDel == 0) {
                                                            int len = et_collectionamountFood.getText().length();

                                                        } else {
                                                            keyDel = 0;
                                                        }
                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable arg0) {


                                                        boolean isValidate = true;

                                                        if (et_collectionamountFood.getText().toString().length() < 0) {
                                                            et_collectionamountFood.setError("Value Required");
                                                            isValidate = false;
                                                        }
                                                        if (et_remmitance

                                                                .getText().toString().length() < 0) {
                                                            et_remmitance

                                                                    .setError("Value Required");
                                                            isValidate = false;
                                                        }


                                                        if (isValidate) {
                                                            Log.e("VOLLEY", "test" + "Input YES");

                                                            if ((!S_et_remmitance.toString().isEmpty()) && (!S_et_collectionamountFood.toString().isEmpty())) {

                                                                StringCOnversion();
                                                                StringNotNull();
                                                                Get_ViewList(UserId);

                                                                try {
                                                                    sumofAll_gf2 = addition2(Float.parseFloat(S_et_currentbalance.toString()
                                                                            ),
                                                                            Float.parseFloat(et_collectionamountFood.getText().toString()
                                                                            )
                                                                    );
                                                                    et_currentbalance.setText("" + sumofAll_gf2);
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }
                                                                try {
                                                                    float sumofAll_gf = addition3(sumofAll_gf2, Float.parseFloat(S_et_remmitance.toString()
                                                                            )


                                                                    );

                                                                    et_currentbalance.setText("" + sumofAll_gf);
                                                                    Log.d("GETTOTAL", "Substractio" + sumofAll_gf);
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }

                                                            } else {
                                                                Log.e("VOLLEY", "test" + "Input NO 1 ");
                                                            }
                                                        } else {
                                                            Log.e("VOLLEY", "test" + "Input NO");
                                                        }
                                                        //setStatus();
                                                        // TODO Auto-generated method stub
                                                    }

                                                    @Override
                                                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                                        // TODO Auto-generated method stub
                                                    }
                                                });
    }

    private float addition2(float v, float v1) {
        return (float) v + v1;

    }

    private float addition3(float v, float v1) {
        return (float) v - v1;

    }


    public float addition(float numa, float numb, float c, float d) {
        // explicitly cast to int
        return (float) numa + numb + c + d;
    }

    public float addition1(float numa, float numb, float c, float d, float numa1, float numb1, float c1, float d1) {
        // explicitly cast to int
        return (float) numa + numb + c + d + numa1 + numb1 + c1 + d1;
    }

    private void IPUTTYPE() {

        et_collectionamount.setTransformationMethod(new MyPasswordTransformationMethod());
        et_collectionamountFood.setTransformationMethod(new MyPasswordTransformationMethod());
        et_paytm.setTransformationMethod(new MyPasswordTransformationMethod());
        et_creditcard.setTransformationMethod(new MyPasswordTransformationMethod());
        et_online.setTransformationMethod(new MyPasswordTransformationMethod());
        et_cro.setTransformationMethod(new MyPasswordTransformationMethod());
        et_creditsales.setTransformationMethod(new MyPasswordTransformationMethod());
        et_remmitance.setTransformationMethod(new MyPasswordTransformationMethod());
        et_currentbalance.setTransformationMethod(new MyPasswordTransformationMethod());
        et_cheque.setTransformationMethod(new MyPasswordTransformationMethod());
        et_advanceincome.setTransformationMethod(new MyPasswordTransformationMethod());
        et_catering.setTransformationMethod(new MyPasswordTransformationMethod());
        et_barincome.setTransformationMethod(new MyPasswordTransformationMethod());
        et_otherincome.setTransformationMethod(new MyPasswordTransformationMethod());
        et_grandtotal.setTransformationMethod(new MyPasswordTransformationMethod());
        et_toatl1.setTransformationMethod(new MyPasswordTransformationMethod());
        et_total.setTransformationMethod(new MyPasswordTransformationMethod());
    }

    private void Unit(ArrayList<Samplemyclass> str1) {


        ArrayAdapter<Samplemyclass> adapter = new ArrayAdapter<Samplemyclass>(getActivity(),
                R.layout.spinner_item, str1);

        sp_unit.setAdapter(adapter);

        sp_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (view != null) {
                    // ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }
                int pos = adapterView.getSelectedItemPosition();
                if (pos != 0) {
                    Samplemyclass country = (Samplemyclass) adapterView.getSelectedItem();

                    UserId = country.getId();
                    sp_unit_Name = country.getName();

                    Get_ViewList(UserId);
                } else {
                    UserId = "";
                    sp_unit_Name = "";
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void Get_Unit() {

        unit_arraylist.clear();
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("ActionType", "" + "unit");
            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "Request Master" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Master, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "Master" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("Code").toString();
                        String message = object.optString("Message").toString();


                        if (code.equalsIgnoreCase("0")) {
                            unit_arraylist.clear();
                            JSONArray jArray = object.getJSONArray("Activity");
                            int number = jArray.length();
                            String num = Integer.toString(number);
                            if (number == 0) {
                                Toast.makeText(getActivity(), " No Data Found", Toast.LENGTH_LONG).show();
                            } else {

                                Samplemyclass wp0 = new Samplemyclass("0", "Select");
                                unit_arraylist.add(wp0);
                                for (int i = 0; i < number; i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    String e_id = json_data.getString("id").toString();
                                    String e_n = json_data.getString("Name").toString();
                                    Samplemyclass wp = new Samplemyclass(e_id, e_n);
                                    unit_arraylist.add(wp);


                                }
                            }
                            if (unit_arraylist.size() > 0) {
                                Unit(unit_arraylist);
                            }


                        } else {
                            Samplemyclass wp0 = new Samplemyclass("0", "Select");
                            // Binds all strings into an array
                            unit_arraylist.add(wp0);
                            if (unit_arraylist.size() > 0) {
                                Unit(unit_arraylist);
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
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void Get_ViewList(String unitId) {
        LoginData();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("UserId", "" + UserId);
            jsonBody.put("Password", "" + Password);
            jsonBody.put("UnitId", "" + unitId);
            jsonBody.put("TableId", "" + "");
            jsonBody.put("AcionType", "" + "");
            jsonBody.put("FromDate", "" + "");
            jsonBody.put("ToDate", "" + "");

            final String mRequestBody = jsonBody.toString();
            Log.e("VOLLEY", "GetNurseryOperations" + "Input" + mRequestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DailyCollections, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("VOLLEY", "GetNurseryOperations" + response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String code = object.optString("Code").toString();
                        String message = object.optString("Message").toString();
                        if (code.equalsIgnoreCase("0")) {

                            view_arraylist.clear();
                            DateArraylist.clear();
                            JSONArray jArray = object.getJSONArray("TstdcData");
                            if (!object.isNull("TstdcData")) {
                                int number = jArray.length();
                                String num = Integer.toString(number);
                                if (number == 0) {
                                    Toast.makeText(getContext(), " No Data Found", Toast.LENGTH_LONG).show();


                                } else {

                                    for (int i = 0; i < number; i++) {
                                        JSONObject json_data = jArray.getJSONObject(i);

                                        String DailyCollectionId = json_data.getString("DailyCollectionId").toString();
                                        String UnitId = json_data.getString("UnitId").toString();
                                        String Cash_Accommodation = json_data.getString("Cash_Accommodation").toString();
                                        String Cash_Food = json_data.getString("Cash_Food").toString();
                                        String Paytm = json_data.getString("Paytm").toString();
                                        String CreditCard = json_data.getString("CreditCard").toString();
                                        String CRO = json_data.getString("CRO").toString();
                                        String Online = json_data.getString("Online").toString();
                                        String CreditSales = json_data.getString("CreditSales").toString();
                                        String Remmitance = json_data.getString("Remmitance").toString();
                                        S_et_currentbalance_Balance = json_data.getString("BalanceToRemetance").toString();

                                        String Remarks = json_data.getString("Remarks").toString();
                                        CollectionDate = json_data.getString("CollectionDate").toString().replace("00:00:00", "");
                                        TSTDC_Helper wp = new TSTDC_Helper(DailyCollectionId, UnitId, Cash_Accommodation, Cash_Food
                                                , Paytm, CreditCard, CRO, Online, "", CreditSales, Remmitance, Remarks, CollectionDate);

                                        // Binds all strings into an array
                                        view_arraylist.add(wp);
                                        DateArraylist.add(CollectionDate);

                                        try {
                                            String date = DateArraylist.get(DateArraylist.size() - 1);
                                            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                            Date oneWayTripDate = inputFormat.parse(date);
                                            Dateofcollection = outputFormat.format(oneWayTripDate);
                                            Log.e("VOLLEY", "DateofInspection final" + Dateofcollection);
                                            et_currentbalance.setText(S_et_currentbalance_Balance);

                                        } catch (Exception ex) {
                                            System.out.println("Date error");

                                        }

                                    }


                                }
                            } else {
                                Dateofcollection = "";
                                S_et_currentbalance = "0";

                            }
                        } else {
                            Dateofcollection = "";
                            S_et_currentbalance = "0";

                        }

                    } catch (
                            JSONException e)

                    {
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
                    34530000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
        } catch (
                JSONException e)

        {
            e.printStackTrace();
        }
    }

    private class TSTDC_Helper {

        String DailyCollectionId, UnitId, Cash_Accommodation, Cash_Food, Paytm, CreditCard, CRO, Online, UnitMR, CreditSales, Remmitance, Remarks, CollectionDate;

        public String getDailyCollectionId() {
            return DailyCollectionId;
        }

        public TSTDC_Helper(String dailyCollectionId, String unitId, String cash_Accommodation, String cash_Food, String paytm, String creditCard, String CRO, String online, String unitMR, String creditSales, String remmitance, String remarks, String collectionDate) {
            DailyCollectionId = dailyCollectionId;
            UnitId = unitId;
            Cash_Accommodation = cash_Accommodation;
            Cash_Food = cash_Food;
            Paytm = paytm;
            CreditCard = creditCard;
            this.CRO = CRO;
            Online = online;
            UnitMR = unitMR;
            CreditSales = creditSales;
            Remmitance = remmitance;
            Remarks = remarks;
            CollectionDate = collectionDate;
        }

        public void setDailyCollectionId(String dailyCollectionId) {
            DailyCollectionId = dailyCollectionId;
        }

        public String getUnitId() {
            return UnitId;
        }

        public void setUnitId(String unitId) {
            UnitId = unitId;
        }

        public String getCash_Accommodation() {
            return Cash_Accommodation;
        }

        public void setCash_Accommodation(String cash_Accommodation) {
            Cash_Accommodation = cash_Accommodation;
        }

        public String getCash_Food() {
            return Cash_Food;
        }

        public void setCash_Food(String cash_Food) {
            Cash_Food = cash_Food;
        }

        public String getPaytm() {
            return Paytm;
        }

        public void setPaytm(String paytm) {
            Paytm = paytm;
        }

        public String getCreditCard() {
            return CreditCard;
        }

        public void setCreditCard(String creditCard) {
            CreditCard = creditCard;
        }

        public String getCRO() {
            return CRO;
        }

        public void setCRO(String CRO) {
            this.CRO = CRO;
        }

        public String getOnline() {
            return Online;
        }

        public void setOnline(String online) {
            Online = online;
        }

        public String getUnitMR() {
            return UnitMR;
        }

        public void setUnitMR(String unitMR) {
            UnitMR = unitMR;
        }

        public String getCreditSales() {
            return CreditSales;
        }

        public void setCreditSales(String creditSales) {
            CreditSales = creditSales;
        }

        public String getRemmitance() {
            return Remmitance;
        }

        public void setRemmitance(String remmitance) {
            Remmitance = remmitance;
        }

        public String getRemarks() {
            return Remarks;
        }

        public void setRemarks(String remarks) {
            Remarks = remarks;
        }

        public String getCollectionDate() {
            return CollectionDate;
        }

        public void setCollectionDate(String collectionDate) {
            CollectionDate = collectionDate;
        }
    }
}
